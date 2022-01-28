package chess;

/**
 * @author Jinrui Li & Jiazhao Shi
 */

import android.util.Pair;

import chess.action.*;
import chess.exceptions.Checkmate;
import chess.exceptions.ChessException;
import chess.exceptions.IllegalMove;
import chess.exceptions.RequirePromotion;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * This is the main class of Chess Program.
 */
public class Chess {
    public static boolean isWhite = true;//is it white's turn
    public static boolean isWin = false;//is there any one win
    public static boolean isDraw = false;//is there request for draw
    public static boolean isPromotion = false;//Pawn promotion
    public static boolean wCheck = false;//is white king in check
    public static boolean bCheck = false;//is black king in check
    public static boolean checking = false;//is there any king being checked?
    public static boolean bpassanting = false;
    public static boolean wpassanting = false;
    public static boolean pbpassanting = false;
    public static boolean pwpassanting = false;
    public static boolean castling = false;
    public static boolean castwK = true;
    public static boolean castbK = true;
    public static boolean cast70 = true;
    public static boolean cast77 = true;
    public static boolean cast00 = true;
    public static boolean cast07 = true;
    public static int position1 = 0;//the position of current piece
    public static int position2 = 0;
    public static int dest1 = 0;//the destination of current piece
    public static int dest2 = 0;
    public static String[] keys = new String[]{"bR", "bN", "bB", "bQ", "bK", "bp", "wp", "wR", "wN", "wB", "wQ", "wK"};
    public static int wKpos1 = 7;//white king's current position
    public static int wKpos2 = 4;
    public static int bKpos1 = 0;//black king's current position
    public static int bKpos2 = 4;
    public static int pwKpos1 = 7;//white king's last position
    public static int pwKpos2 = 4;
    public static int pbKpos1 = 0;//black king's last position
    public static int pbKpos2 = 4;
    public static int bpassant = 0;//En Passant
    public static int wpassant = 0;
    public static int bpawn = 0;
    public static int wpawn = 0;
    public static ArrayList<String> checkList = new ArrayList<String>();//the list of pieces that causes check. Format example: "K04"
    public static String[][] keyBoard = FunctionsForBoard.keyBoardGen();
    public static String[][] keyBoard2 = FunctionsForBoard.keyBoardGen();
    public static String[][] keyBoard3 = FunctionsForBoard.keyBoardGen();
    public static Piece[][] pieceBoard = FunctionsForBoard.pieceBoardGen();
    public static Piece[][] pieceBoard2 = FunctionsForBoard.pieceBoardGen();
    private static int round = 0;

    public static Stack<Pair<String[][], Piece[][]>> histories = new Stack<>();
    public static String[][] prevKeyboard = null;
    public static Piece[][] prevPieceBoard = null;

    public static void init() {
        isWhite = true;//is it white's turn
        isWin = false;//is there any one win
        isDraw = false;//is there request for draw
        isPromotion = false;//Pawn promotion
        wCheck = false;//is white king in check
        bCheck = false;//is black king in check
        checking = false;//is there any king being checked?
        bpassanting = false;
        wpassanting = false;
        pbpassanting = false;
        pwpassanting = false;
        castling = false;
        castwK = true;
        castbK = true;
        cast70 = true;
        cast77 = true;
        cast00 = true;
        cast07 = true;
        position1 = 0;//the position of current piece
        position2 = 0;
        dest1 = 0;//the destination of current piece
        dest2 = 0;
        keys = new String[]{"bR", "bN", "bB", "bQ", "bK", "bp", "wp", "wR", "wN", "wB", "wQ", "wK"};
        wKpos1 = 7;//white king's current position
        wKpos2 = 4;
        bKpos1 = 0;//black king's current position
        bKpos2 = 4;
        pwKpos1 = 7;//white king's last position
        pwKpos2 = 4;
        pbKpos1 = 0;//black king's last position
        pbKpos2 = 4;
        bpassant = 0;//En Passant
        wpassant = 0;
        bpawn = 0;
        wpawn = 0;
        checkList = new ArrayList<String>();//the list of pieces that causes check. Format example: "K04"

        keyBoard = FunctionsForBoard.keyBoardGen();
        keyBoard2 = FunctionsForBoard.keyBoardGen();
        keyBoard3 = FunctionsForBoard.keyBoardGen();
        pieceBoard = FunctionsForBoard.pieceBoardGen();
        pieceBoard2 = FunctionsForBoard.pieceBoardGen();
    }

    public static void main(String[] args) {
        init();

        Scanner scanner = new Scanner(System.in);

        while (!isWin) {
            String line = scanner.nextLine();
            Action action = null;

            if (line.contains(" ")) {
                String[] arg = line.split(" ");
                action = new MoveAction(arg[0], arg[1]);
            }

            if (action == null) {
                System.out.println();
                return;
            }

            try {
                doAction(action);
            } catch (ChessException e) {
                e.printStackTrace();
            }
            FunctionsForBoard.printBoard(keyBoard);
        }

    }

    public static void doResign() throws Checkmate {
        throw new Checkmate(isWhite ? "White" : "Black");
    }

    public static void doMove(MoveAction action) throws ChessException {

        String input = action.getFrom() + " " + action.getTo();
        String key = FunctionsForBoard.getKey(input, keyBoard);

        if (!FunctionsForBoard.checkRound(key)) {
            round = -1;
            throw new IllegalMove();
        }

        if (!FunctionsForBoard.canMove(input, pieceBoard, keyBoard)) {
            throw new IllegalMove();
        }

        pieceBoard = FunctionsForBoard.movePiece(pieceBoard);
        keyBoard = FunctionsForBoard.moveKeys(key, keyBoard, keyBoard2);

        if (!keyBoard[wKpos1][wKpos2].equals("wK ")) {
            isWin = true;
            System.out.println("Black wins");
            throw new Checkmate("Black");
        } else if (!keyBoard[bKpos1][bKpos2].equals("bK ")) {
            isWin = true;
            System.out.println("White wins");
            throw new Checkmate("White");
        } else {
            //White king being check
            wCheck = IsCheck.isCheck(wKpos1, wKpos2, pieceBoard, keyBoard);
            //Black king being check
            bCheck = IsCheck.isCheck(bKpos1, bKpos2, pieceBoard, keyBoard);
            if (!wCheck && !bCheck)
                checking = false;
            if (!checking) //see if the move will lead to immediate check, and if not, print check
            {
                if (FunctionsForBoard.invalidMove()) {
                    throw new IllegalMove();
//                    keyBoard = FunctionsForBoard.equalKBoard(keyBoard3);
//                    pieceBoard = FunctionsForBoard.equalPBoard(pieceBoard2);
//                    wKpos1 = pwKpos1;
//                    wKpos2 = pwKpos2;
//                    bKpos1 = pbKpos1;
//                    bKpos2 = pbKpos2;
//                    bpassanting = pbpassanting;
//                    wpassanting = pwpassanting;
                } else if (wCheck || bCheck) {
                    System.out.println("Check");
                    checking = true;
                    //Checkmate
                    if (IsCheckmate.isCheckmate(!isWhite, pieceBoard, keyBoard)) {
                        if (isWhite)
                            System.out.println("Black is in checkmate");
                        else
                            System.out.println("White is in checkmate");
                        throw new Checkmate(isWhite ? "White" : "Black");
//                        isWin = true;
                    }
                }
            } else {
                doUndo();
                throw new IllegalMove();
            }
            if (isPromotion) //pawn can be able to promote
            {
                throw new RequirePromotion();
            }
        }
        isWhite = !isWhite;
    }

    public static void promotion(String to) throws RequirePromotion {
        String promInput = to;
        promInput = FunctionsForBoard.checkProm(promInput);
        if (promInput.length() == 1) //wrong input format
        {
            System.out.println("Illegal input, try again");
            throw new RequirePromotion();
        } else {
            keyBoard[dest1][dest2] = promInput + " ";
            pieceBoard[dest1][dest2] = FunctionsForBoard.promObject(promInput);
            isPromotion = false;
            System.out.println();
        }
    }

    public static void doPromote(PromptAction action) {
    }

    public static boolean canUndo() {
        return prevPieceBoard != null;
    }

    public static void doUndo() {
        keyBoard = prevKeyboard;
        pieceBoard = prevPieceBoard;
        prevKeyboard = null;
        prevPieceBoard = null;
    }

    private static void makeHistory() {
        prevPieceBoard = new Piece[8][8];
        prevKeyboard = new String[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                prevPieceBoard[i][j] = pieceBoard[i][j];
                prevKeyboard[i][j] = keyBoard[i][j];
            }
        }
    }

    private static void doRandomMove() throws ChessException {
        String player = isWhite ? "w" : "b";
        String charSeq = "abcdefghijk";
        while (true) {

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (Chess.keyBoard[i][j] != null && Chess.keyBoard[i][j].startsWith(player)) {
                        for (int m = 0; m < 8; m++) {
                            for (int n = 0; n < 8; n++) {
                                Chess.position1 = i;
                                Chess.position2 = j;
                                if (FunctionsForBoard.canMove(
                                        String.format("%s%d %s%d", charSeq.charAt(i), 8 - j, charSeq.charAt(m), 8 - n),
                                        pieceBoard, keyBoard
                                )) {
                                    try {
                                        makeHistory();
                                        doMove(new MoveAction(
                                                String.format("%s%d", charSeq.charAt(i), 8 - j),
                                                String.format("%s%d", charSeq.charAt(m), 8 - n)
                                        ));
                                        return;
                                    } catch (IllegalMove e) {
                                        continue;
                                    } catch (ChessException e) {
                                        throw e;
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void doAction(Action action) throws ChessException {

        FunctionsForBoard.printBoard(keyBoard);
//        FunctionsForBoard.printMove();

        if (action instanceof ResignAction) {
            doResign();
            return;
        }

        if (action instanceof MoveAction) {
            makeHistory();
            doMove((MoveAction) action);
            return;
        }

        if (action instanceof DrawAction) {
            return;
        }

        if (action instanceof UndoAction) {
            doUndo();
            return;
        }

        if (action instanceof RandomMoveAction) {
            makeHistory();
            doRandomMove();
            return;
        }
    }
}

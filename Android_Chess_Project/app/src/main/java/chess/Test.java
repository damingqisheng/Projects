package chess;

/**
 * @author Jinrui Li & Jiazhao Shi
 */

public class Test {

	public static void main(String[] args) {
		Piece[][] pieceBoard = new Piece[8][8];
		
		Piece bpa = new Pawn(false, 1, 0);
		pieceBoard[1][0] = bpa;
		Piece bpb = new Pawn(false, 1, 1);
		pieceBoard[1][1] = bpb;
		Piece bpc = new Pawn(false, 1, 2);
		pieceBoard[1][2] = bpc;
		Piece bpd = new Pawn(false, 1, 3);
		pieceBoard[1][3] = bpd;
		Piece bpe = new Pawn(false, 1, 4);
		pieceBoard[1][4] = bpe;
		Piece bpf = new Pawn(false, 1, 5);
		pieceBoard[1][5] = bpf;
		Piece bpg = new Pawn(false, 1, 6);
		pieceBoard[1][6] = bpg;
		Piece bph = new Pawn(false, 1, 7);
		pieceBoard[1][7] = bph;
		
		Piece wpa = new Pawn(true, 6, 0);
		pieceBoard[6][0] = wpa;
		Piece wpb = new Pawn(true, 6, 1);
		pieceBoard[6][1] = wpb;
		Piece wpc = new Pawn(true, 6, 2);
		pieceBoard[6][2] = wpc;
		Piece wpd = new Pawn(true, 6, 3);
		pieceBoard[6][3] = wpd;
		Piece wpe = new Pawn(true, 6, 4);
		pieceBoard[6][4] = wpe;
		Piece wpf = new Pawn(true, 6, 5);
		pieceBoard[6][5] = wpf;
		Piece wpg = new Pawn(true, 6, 6);
		pieceBoard[6][6] = wpg;
		Piece wph = new Pawn(true, 6, 7);
		pieceBoard[6][7] = wph;
		
		Piece bRa = new Rook(false, 0, 0);
		pieceBoard[0][0] = bRa;
		Piece bRh = new Rook(false, 0, 7);
		pieceBoard[0][7] = bRh;
		Piece wRa = new Rook(true, 7, 0);
		pieceBoard[7][0] = wRa;
		Piece wRh = new Rook(true, 7, 7);
		pieceBoard[7][7] = wRh;
		
		Piece bNb = new Knight(false, 0, 1);
		pieceBoard[0][1] = bNb;
		Piece bNg = new Knight(false, 0, 6);
		pieceBoard[0][6] = bNg;
		Piece wNb = new Knight(true, 7, 1);
		pieceBoard[7][1] = wNb;
		Piece wNg = new Knight(true, 7, 6);
		pieceBoard[7][6] = wNg;
		
		Piece bBc = new Bishop(false, 0, 2);
		pieceBoard[0][2] = bBc;
		Piece bBf = new Bishop(false, 0, 5);
		pieceBoard[0][5] = bBf;
		Piece wBc = new Bishop(true, 7, 2);
		pieceBoard[7][2] = wBc;
		Piece wBf = new Bishop(true, 7, 5);
		pieceBoard[7][5] = wBf;
		
		Piece bQd = new Queen(false, 0, 3);
		pieceBoard[0][3] = bQd;
		Piece wQd = new Queen(true, 7, 3);
		pieceBoard[7][3] = wQd;
		
		Piece bKe = new King(false, 0, 4);
		pieceBoard[0][4] = bKe;
		Piece wKe = new King(true, 7, 4);
		pieceBoard[7][4] = wKe;
		char s = pieceBoard[7][1].getName();
		System.out.print(s+" ");
		s = pieceBoard[6][1].getName();
		System.out.println(s);
		s = pieceBoard[6][4].getName();
		System.out.println(s);
		pieceBoard[5][4] = pieceBoard[6][4];
		pieceBoard[6][4] = null;
		
		FunctionsForBoard.printPieceBoard(pieceBoard);
//		Piece[][] pieceBoard2 = FunctionsForBoard.pieceBoardGen();
//		pieceBoard2 = FunctionsForBoard.equalPBoard(pieceBoard);
//		FunctionsForBoard.printPieceBoard(pieceBoard2);
//		pieceBoard2[0][0] = null;
//		FunctionsForBoard.printPieceBoard(pieceBoard);
//		FunctionsForBoard.printPieceBoard(pieceBoard2);		
	}
}

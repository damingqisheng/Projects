package chess;

/**
 * @author Jinrui Li & Jiazhao Shi
 */

/**
 * For interaction between pieces and board
 */
public class FunctionsForBoard {
	/**
	 * store an initial chess board
	 * @return	reteun the keyboard
	 */
	public static String[][] keyBoardGen(){
		String[][] keyBoard = {
				  {"bR ", "bN ", "bB ", "bQ ", "bK ", "bB ", "bN ", "bR ", "8"},
				  {"bp ", "bp ", "bp ", "bp ", "bp ", "bp ", "bp ", "bp ", "7"},
				  {"   ", "## ", "   ", "## ", "   ", "## ", "   ", "## ", "6"},
				  {"## ", "   ", "## ", "   ", "## ", "   ", "## ", "   ", "5"},
				  {"   ", "## ", "   ", "## ", "   ", "## ", "   ", "## ", "4"},
				  {"## ", "   ", "## ", "   ", "## ", "   ", "## ", "   ", "3"},
				  {"wp ", "wp ", "wp ", "wp ", "wp ", "wp ", "wp ", "wp ", "2"},
				  {"wR ", "wN ", "wB ", "wQ ", "wK ", "wB ", "wN ", "wR ", "1"},
				  {" a ", " b ", " c ", " d ", " e ", " f ", " g ", " h ", " "}
				};
		return keyBoard;
	}

	/**
	 * put the initial pieces into the board
	 * @return	return the board
	 */
	public static Piece[][] pieceBoardGen(){
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
		
		return pieceBoard;
	}

	/**
	 * to pring the board in the command line
	 * @param keyBoard	keyboard in the program
	 */
	public static void printBoard(String[][] keyBoard) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				System.out.print(keyBoard[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * to pring the board in the command line
	 * @param pieceBoard pieceboard in the program
	 */
	public static void printPieceBoard(Piece[][] pieceBoard) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(pieceBoard[i][j] != null) {
					System.out.print( pieceBoard[i][j].getName() + "    ");
				}
				else {
					System.out.print("null ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}


	/**
	 * print the move of the piece
	 */
	public static void printMove() {
		if(Chess.isWhite) {
			System.out.print("White's move: ");
		}
		else {
			System.out.print("black's move: ");
		}
	}


	/**
	 * print the result of the game
	 */
	public static void printWin() {
		if(Chess.isWhite) {
			System.out.println("Black wins");
		}
		else {
			System.out.println("White wins");
		}
	}


	/**
	 * check the format of input is correct
	 * @param input input from user
	 * @return		return the flag of input result
	 */
	public static int checkInput(String input) {
			int result = 0;
			if(input.length() < 4) {
				return 0;
			}
			if(input.equals("resign")) {
				return 1;
			}
			if(input.equals("draw")) {
				if(Chess.isDraw) {
					return 2;
				}
				return 0;
			}
			if(input.length() > 4) {
				if(Character.isLetter(input.charAt(0)) && Character.isDigit(input.charAt(1))
						&& input.charAt(2) == ' ' && Character.isLetter(input.charAt(3)) 
						&& Character.isDigit(input.charAt(4))){
					if(input.length() == 5) {
						return 3;
					}
					else if(input.substring(6).equals("draw?")) {
						return 4;
					}
					return 0;
				}
			}
			return result;
		}

	/**
	 * find the piece according the first input by users
	 * @param input		input string from user
	 * @param keyBoard	current chess board
	 * @return			reteun the piece name
	 */
		public static String getKey(String input, String[][] keyBoard)
		{
			String result = "";
			int index2 = input.charAt(0) - 97;
			int index1 = 8 - (input.charAt(1) - 48);
			if(index2 < 8 && index1 >= 0) {
				result = keyBoard[index1][index2];
			}
			Chess.position1 = index1;
			Chess.position2 = index2;
			return result;		
		}

	/**
	 * to check whose turn to play
	 * @param 	key input name of a piece
	 * @return	is white's turn or not
	 */
	public static boolean checkRound(String key) {
			boolean result =false;
			if(key.charAt(0) == 'w'&& Chess.isWhite) {
				result = true;	
			}
			else if(key.charAt(0) == 'b'&& !Chess.isWhite) {
				result = true;
			}
			return result;
		}

	/**
	 * is the piece can move or not?
	 * @param input
	 * @param pieceBoard
	 * @param keyBoard
	 * @return
	 */
		public static boolean canMove(String input, Piece[][] pieceBoard ,String[][] keyBoard ) {
			Chess.dest1 = 8 - (input.charAt(4) - 48);
			Chess.dest2 = input.charAt(3) - 97;
			if(Chess.dest1 >= 0 && Chess.dest2 < 8) {
				 return pieceBoard[Chess.position1][Chess.position2].movable(Chess.position1, Chess.position2, Chess.dest1, Chess.dest2,Chess.isWhite, pieceBoard, keyBoard);			 
			}			
			return false;	
		}
		
		//��piece�������ƶ���Ŀ�ĵ�
		public static Piece[][] movePiece(Piece[][] pieceBoard) {
			pieceBoard[Chess.position1][Chess.position2].setPosition1(Chess.dest1);
			pieceBoard[Chess.position1][Chess.position2].setPosition2(Chess.dest2);
			if(Chess.bpassanting)
			{
				pieceBoard[3][Chess.bpawn] = null;
			}
			if(Chess.wpassanting)
			{
				pieceBoard[4][Chess.wpawn] = null;
			}
			if(Chess.castling == true)
			{
				if(Chess.dest1 == 0)
				{
					if(Chess.dest2 == 2)
					{
						pieceBoard[0][0].setPosition1(0);
						pieceBoard[0][0].setPosition2(3);
						pieceBoard[0][2] = pieceBoard[0][4];
						pieceBoard[0][3] = pieceBoard[0][0];
						pieceBoard[0][0] = null;
						pieceBoard[0][4] = null;
					}
					else if(Chess.dest2 == 6)
					{
						pieceBoard[0][7].setPosition1(0);
						pieceBoard[0][7].setPosition2(5);
						pieceBoard[0][6] = pieceBoard[0][4];
						pieceBoard[0][5] = pieceBoard[0][7];
						pieceBoard[0][7] = null;
						pieceBoard[0][4] = null;
						
					}
				}
				if(Chess.dest1 == 7)
				{
					if(Chess.dest2 == 2)
					{
						pieceBoard[7][0].setPosition1(7);
						pieceBoard[7][0].setPosition2(3);
						pieceBoard[7][2] = pieceBoard[7][4];
						pieceBoard[7][3] = pieceBoard[7][0];
						pieceBoard[7][0] = null;
						pieceBoard[7][4] = null;
						
					}
					else if(Chess.dest2 == 6)
					{
						pieceBoard[7][7].setPosition1(7);
						pieceBoard[7][7].setPosition2(5);
						pieceBoard[7][6] = pieceBoard[7][4];
						pieceBoard[7][5] = pieceBoard[7][7];
						pieceBoard[7][7] = null;
						pieceBoard[7][4] = null;
						
					}
				}
				return pieceBoard;
			}
//			System.out.println(pieceBoard[Chess.position1][Chess.position2].getPosition1());
//			System.out.println(pieceBoard[Chess.position1][Chess.position2].getPosition2());
			pieceBoard[Chess.dest1][Chess.dest2] = pieceBoard[Chess.position1][Chess.position2];
			pieceBoard[Chess.position1][Chess.position2] = null;
			return pieceBoard;	
		}
		
		//��key�������ƶ���Ŀ�ĵ�
		public static String[][] moveKeys(String key, String[][] keyBoard, String[][] keyBoard2){
			if(key.equals("wR "))
			{
				if(Chess.position1 == 7 && Chess.position2 == 0)
				{
					Chess.cast70 = false;
				}
				if(Chess.position1 == 7 && Chess.position2 == 7)
				{
					Chess.cast77 = false;
				}
			}
			if(key.equals("bR "))
			{
				if(Chess.position1 == 0 && Chess.position2 == 0)
				{
					Chess.cast00 = false;
				}
				if(Chess.position1 == 0 && Chess.position2 == 7)
				{
					Chess.cast07 = false;
				}
			}
			
			if(key.equals("wK "))
			{
				Chess.castwK = false;
				Chess.wKpos1 = Chess.dest1;
				Chess.wKpos2 = Chess.dest2;
			}
			if(key.equals("bK "))
			{
				Chess.castbK = false;
				Chess.bKpos1 = Chess.dest1;
				Chess.bKpos2 = Chess.dest2;
			}
			if(Chess.castling == true)
			{
				if(Chess.dest1 == 0)
				{
					if(Chess.dest2 == 2)
					{
						keyBoard[0][0] = "   ";
						keyBoard[0][4] = "   ";
						keyBoard[0][2] = "bK ";
						keyBoard[0][3] = "bR ";
						Chess.cast00 = false;
					}
					else if(Chess.dest2 == 6)
					{
						keyBoard[0][7] = "   ";
						keyBoard[0][4] = "   ";
						keyBoard[0][6] = "bK ";
						keyBoard[0][5] = "bR ";
						Chess.cast07 = false;
					}
				}
				if(Chess.dest1 == 7)
				{
					if(Chess.dest2 == 2)
					{
						keyBoard[7][0] = "   ";
						keyBoard[7][4] = "   ";
						keyBoard[7][2] = "wK ";
						keyBoard[7][3] = "wR ";
						Chess.cast70 = false;
					}
					else if(Chess.dest2 == 6)
					{
						keyBoard[7][7] = "   ";
						keyBoard[7][4] = "   ";
						keyBoard[7][6] = "wK ";
						keyBoard[7][5] = "wR ";
						Chess.cast77 = false;
					}
				}
				Chess.castling = false;
				return keyBoard;
			}
			if(Chess.bpassanting)
			{
				keyBoard[3][Chess.bpawn] = keyBoard2[3][Chess.bpawn];
				Chess.bpassanting = false;
			}
			if(Chess.wpassanting)
			{
				keyBoard[4][Chess.wpawn] = keyBoard2[4][Chess.wpawn];
				Chess.wpassanting = false;
			}
			keyBoard[Chess.position1][Chess.position2] = "   ";
			if(keyBoard2[Chess.position1][Chess.position2].equals("   ")){
				keyBoard[Chess.position1][Chess.position2] = "   ";	
			}
			else if(keyBoard2[Chess.position1][Chess.position2].equals("## ")){
				keyBoard[Chess.position1][Chess.position2] = "## ";								
			}
			keyBoard[Chess.dest1][Chess.dest2] = key;
			
			if(key.charAt(1) == 'p') {
//				System.out.println(Chess.dest1);
				if(Chess.dest1 == 7 || Chess.dest1 == 0)
					Chess.isPromotion = true;
			}
			
			return keyBoard;		
		}
		
		//�鿴��Ҫpromote����̬
		public static String checkProm(String promInput) {
			String result = "";
			if(Chess.isWhite) {
				result = "w";
			}
			else {
				result = "b";
			}
			if(promInput.toLowerCase().equals("queen")) {
				result = result + "Q";
			}
			else if(promInput.toLowerCase().equals("rook")){
				result = result + "R";
			}
			else if(promInput.toLowerCase().equals("bishop")){
				result = result + "B";
			}
			else if(promInput.toLowerCase().equals("knight")){
				result = result + "N";
			}
			return result;			
		}
		
		//����promote object
		public static Piece promObject (String promInput) {
			Piece temp = null;
			if(promInput.charAt(1) == 'Q') {
				temp = new Queen(Chess.isWhite, Chess.dest1, Chess.dest2);
			}
			else if(promInput.charAt(1) == 'R') {
				temp = new Rook(Chess.isWhite, Chess.dest1, Chess.dest2);
			}
			else if(promInput.charAt(1) == 'B') {
				temp = new Bishop(Chess.isWhite, Chess.dest1, Chess.dest2);
			}
			else if(promInput.charAt(1) == 'N') {
				temp = new Knight(Chess.isWhite, Chess.dest1, Chess.dest2);
			}
			return temp;		
		}
		
		public static boolean invalidMove()
		{
			if(Chess.isWhite && Chess.wCheck)//the move will lead to immediate check, which is invalid
			{										
				System.out.println("Illegal move, try again");
				Chess.isDraw = false;
				return true;
			}
			else if(!Chess.isWhite && Chess.bCheck)
			{
				System.out.println("Illegal move, try again");
				Chess.isDraw = false;
				return true;
			}
			return false;
		}
		
		public static String[][] equalKBoard(String[][] keyBoard1)
		{
			String[][] keyBoard2 = new String[9][9];
			for(int i = 0; i < 9; i++) {
				for(int j = 0; j < 9; j++) {
					keyBoard2[i][j] = keyBoard1[i][j];
				}
			}
			return keyBoard2;
		}
		
		public static Piece[][] equalPBoard(Piece[][] pieceBoard1)
		{
			Piece[][] pieceBoard2 = new Piece[8][8];
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(pieceBoard1[i][j] != null) {
						pieceBoard2[i][j] = pieceBoard1[i][j];
					}
					
				}
			}
			return pieceBoard2;
		}
		
		public static String[][] testMoveKeys(int pos1, int pos2, int dest1, int dest2, String key, String[][] keyBoard, String[][] keyBoard2){
			if(Chess.bpassanting)
			{
				keyBoard[3][Chess.bpawn] = keyBoard2[3][Chess.bpawn];
				Chess.bpassanting = false;
			}
			if(Chess.wpassanting)
			{
				keyBoard[4][Chess.wpawn] = keyBoard2[4][Chess.wpawn];
				Chess.wpassanting = false;
			}
			if(key.equals("wK "))
			{
				Chess.wKpos1 = Chess.dest1;
				Chess.wKpos2 = Chess.dest2;
			}
			if(key.equals("bK "))
			{
				Chess.bKpos1 = Chess.dest1;
				Chess.bKpos2 = Chess.dest2;
			}
			if(Chess.castling == true)
			{
				if(Chess.dest1 == 0)
				{
					if(Chess.dest2 == 2)
					{
						keyBoard[0][0] = "   ";
						keyBoard[0][4] = "   ";
						keyBoard[0][2] = "bK ";
						keyBoard[0][3] = "bR ";
					}
					else if(Chess.dest2 == 6)
					{
						keyBoard[0][7] = "   ";
						keyBoard[0][4] = "   ";
						keyBoard[0][6] = "bK ";
						keyBoard[0][5] = "bR ";					
					}
				}
				if(Chess.dest1 == 7)
				{
					if(Chess.dest2 == 2)
					{
						keyBoard[7][0] = "   ";
						keyBoard[7][4] = "   ";
						keyBoard[7][2] = "wK ";
						keyBoard[7][3] = "wR ";
						
					}
					else if(Chess.dest2 == 6)
					{
						keyBoard[7][7] = "   ";
						keyBoard[7][4] = "   ";
						keyBoard[7][6] = "wK ";
						keyBoard[7][5] = "wR ";
					}
				}
				Chess.castling = false;
				return keyBoard;
			}
			keyBoard[pos1][pos2] = "   ";
			if(keyBoard2[pos1][pos2].equals("   ")){
				keyBoard[pos1][pos2] = "   ";	
			}
			else if(keyBoard2[pos1][pos2].equals("## ")){
				keyBoard[pos1][pos2] = "## ";								
			}
			keyBoard[dest1][dest2] = key;
			
			return keyBoard;		
		}
		public static Piece[][] testMovePiece(int pos1, int pos2, int dest1, int dest2, Piece[][] pieceBoard) {
			pieceBoard[pos1][pos2].setPosition1(dest1);
			pieceBoard[pos1][pos2].setPosition2(dest2);
			if(Chess.bpassanting)
			{
				pieceBoard[3][Chess.bpawn] = null;
				Chess.bpassanting = false;
			}
			if(Chess.wpassanting)
			{
				pieceBoard[4][Chess.wpawn] = null;
				Chess.wpassanting = false;
			}
			if(Chess.castling == true)
			{
				if(Chess.dest1 == 0)
				{
					if(Chess.dest2 == 2)
					{
						pieceBoard[0][0].setPosition1(0);
						pieceBoard[0][0].setPosition2(3);
						pieceBoard[0][2] = pieceBoard[0][4];
						pieceBoard[0][3] = pieceBoard[0][0];
						pieceBoard[0][0] = null;
						pieceBoard[0][4] = null;
					}
					else if(Chess.dest2 == 6)
					{
						pieceBoard[0][7].setPosition1(0);
						pieceBoard[0][7].setPosition2(5);
						pieceBoard[0][6] = pieceBoard[0][4];
						pieceBoard[0][5] = pieceBoard[0][7];
						pieceBoard[0][7] = null;
						pieceBoard[0][4] = null;
						
					}
				}
				if(Chess.dest1 == 7)
				{
					if(Chess.dest2 == 2)
					{
						pieceBoard[7][0].setPosition1(7);
						pieceBoard[7][0].setPosition2(3);
						pieceBoard[7][2] = pieceBoard[7][4];
						pieceBoard[7][3] = pieceBoard[7][0];
						pieceBoard[7][0] = null;
						pieceBoard[7][4] = null;
						
					}
					else if(Chess.dest2 == 6)
					{
						pieceBoard[7][7].setPosition1(7);
						pieceBoard[7][7].setPosition2(5);
						pieceBoard[7][6] = pieceBoard[7][4];
						pieceBoard[7][5] = pieceBoard[7][7];
						pieceBoard[7][7] = null;
						pieceBoard[7][4] = null;
						
					}
				}
				return pieceBoard;
			}
			pieceBoard[dest1][dest2] = pieceBoard[pos1][pos2];
			pieceBoard[pos1][pos2] = null;
			return pieceBoard;	
		}
}

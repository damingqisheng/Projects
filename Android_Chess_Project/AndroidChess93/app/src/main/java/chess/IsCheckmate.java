package chess;

/**
 * @author Jinrui Li & Jiazhao Shi
 */

public class IsCheckmate {
	public static boolean isCheckmate(boolean white, Piece[][] pieceBoard, String[][] keyBoard)
	{
		String[][] keyBoard1 = FunctionsForBoard.equalKBoard(keyBoard);	
		Piece[][] pieceBoard1 = FunctionsForBoard.equalPBoard(pieceBoard);
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(pieceBoard1[i][j] != null)
				{
					if((keyBoard1[i][j].charAt(0) == 'w' && white) 
							||(keyBoard1[i][j].charAt(0) == 'b' && !white))
					{
						if(checkSingleKey(i, j, white, pieceBoard1, keyBoard1))
						{
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	public static boolean checkSingleKey(int pos1, int pos2, boolean white, Piece[][] pieceBoard ,String[][] keyBoard)
	{
		String[][] keyBoard1 = FunctionsForBoard.equalKBoard(keyBoard);	
		Piece[][] pieceBoard1 = FunctionsForBoard.equalPBoard(pieceBoard);
		String[][] keyBoard2 = FunctionsForBoard.equalKBoard(keyBoard);
		Piece[][] pieceBoard2 = FunctionsForBoard.equalPBoard(pieceBoard);
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(pieceBoard1[pos1][pos2].movable(pos1, pos2, i, j, white, pieceBoard1, keyBoard1))
				{
					keyBoard1 = FunctionsForBoard.testMoveKeys(pos1, pos2, i, j, keyBoard1[pos1][pos2], keyBoard1, keyBoard2);
					pieceBoard1 = FunctionsForBoard.testMovePiece(pos1, pos2, i, j, pieceBoard1);
					if(white && !IsCheck.isCheck(Chess.wKpos1, Chess.wKpos2, pieceBoard1, keyBoard1))
					{
						return true;
					}
					else if(!white && !IsCheck.isCheck(Chess.bKpos1, Chess.bKpos2, pieceBoard1, keyBoard1))
					{
						return true;
					}
					
					keyBoard1 = FunctionsForBoard.equalKBoard(keyBoard2);
					pieceBoard1 = FunctionsForBoard.equalPBoard(pieceBoard2);
					Chess.wKpos1 = Chess.pwKpos1;
					Chess.wKpos2 = Chess.pwKpos2;
					Chess.bKpos1 = Chess.pbKpos1;
					Chess.bKpos2 = Chess.pbKpos2;
					
					
				}
			}
		}
		return false;
	}
	
}

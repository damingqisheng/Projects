package chess;

/**
 * @author Jinrui Li & Jiazhao Shi
 */

public class King extends Piece {
	public char name = 'K';
	
	public King(boolean isWhite, int position1, int position2) {
		this.isWhite = isWhite;
		this.position1 = position1;
		this.position2 = position2;
	}
	public boolean getColor() {
		return isWhite;
	}

	public void setColor(boolean isWhite) {
		this.isWhite = isWhite;
	}

	public int getPosition1() {
		return position1;
	}

	public void setPosition1(int position1) {
		this.position1 = position1;
	}

	public int getPosition2() {
		return position2;
	}

	public void setPosition2(int position2) {
		this.position2 = position2;
	}
	
	public char getName() {
		return name;
	}

	public void setName(char name) {
		this.name = name;
	}
	@Override
	public boolean movable(int pos1, int pos2, int dest1, int dest2,  boolean white, Piece[][] pieceBoard, String[][] keyBoard) {
		if(dest1 == pos1 && dest2 == pos2)
		{
			return false;
		}
		if(isCastling(pos1, pos2, dest1, dest2, white, pieceBoard, keyBoard))
		{
			Chess.castling = true;
			return true;
		}
		if(keyBoard[dest1][dest2].charAt(0) == 'w' && white)
		{
			return false;
		}
		if(keyBoard[dest1][dest2].charAt(0) == 'b' && !white)
		{
			return false;
		}
		int a = dest1 - pos1;
		int b = dest2 - pos2;
		
		if(dest1 == pos1)
		{
			if(b == 1 || b== -1)
			{
				return true;
			}
			
		}
		else if(dest2 == pos2)
		{
			if(a == 1 || a == -1)
			{
				return true;
			}
			
		}
		else if(a == 1 && b == 1)
		{
			return true;
		}
		else if(a == 1 && b == -1)
		{
			return true;
		}
		else if(a == -1 && b == 1)
		{
			return true;
		}
		else if(a == -1 && b == -1)
		{
			return true;
		}
		return false;
	}
	private static boolean isCastling(int pos1, int pos2, int dest1, int dest2,  boolean white, Piece[][] pieceBoard, String[][] keyBoard)
	{
		if(!Chess.castwK && white)
		{
			return false;
		}
		if(!Chess.castbK && !white)
		{
			return false;
		}
		if(dest1 == 0)
		{
			if(dest2 == 2)
			{
				if(pieceBoard[0][1] == null && pieceBoard[0][2] == null && pieceBoard[0][3] == null && Chess.cast00 == true && !white)
				{
					return true;
				}
			}
			if(dest2 == 6)
			{
				if(pieceBoard[0][5] == null && pieceBoard[0][6] == null && Chess.cast07 == true && !white)
				{
					return true;
				}
			}
		}
		else if(dest1 == 7)
		{
			if(dest2 == 2)
			{
				if(pieceBoard[7][1] == null && pieceBoard[7][2] == null && pieceBoard[7][3] == null && Chess.cast70 == true && white)
				{
					return true;
				}
			}
			if(dest2 == 6)
			{
				if(pieceBoard[7][5] == null && pieceBoard[7][6] == null && Chess.cast77 == true && white)
				{
					return true;
				}
			}
		}
		return false;
	}
}

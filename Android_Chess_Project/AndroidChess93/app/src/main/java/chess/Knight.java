package chess;

/**
 * @author Jinrui Li & Jiazhao Shi
 */

public class Knight extends Piece{
	public char name = 'N';
	
	
	public Knight(boolean isWhite, int position1, int position2) {
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
		if(dest1 == pos1 + 1)
		{
			if(dest2 == pos2 + 2 || dest2 == pos2 - 2)
			{
				if(keyBoard[dest1][dest2].charAt(0) != 'w' && white)
				{
					return true;
				}
				if(keyBoard[dest1][dest2].charAt(0) != 'b' && !white)
				{
					return true;
				}	
				
			}
		}
		else if(dest1 == pos1 - 1)
		{
			if(dest2 == pos2 + 2 || dest2 == pos2 - 2)
			{
				if(keyBoard[dest1][dest2].charAt(0) != 'w' && white)
				{
					return true;
				}
				if(keyBoard[dest1][dest2].charAt(0) != 'b' && !white)
				{
					return true;
				}
			}
		}
		else if(dest1 == pos1 + 2)
		{
			if(dest2 == pos2 + 1 || dest2 == pos2 - 1)
			{
				if(keyBoard[dest1][dest2].charAt(0) != 'w' && white)
				{
					return true;
				}
				if(keyBoard[dest1][dest2].charAt(0) != 'b' && !white)
				{
					return true;
				}
			}
		}
		else if(dest1 == pos1 - 2)
		{
			if(dest2 == pos2 + 1 || dest2 == pos2 - 1)
			{
				if(keyBoard[dest1][dest2].charAt(0) != 'w' && white)
				{
					return true;
				}
				if(keyBoard[dest1][dest2].charAt(0) != 'b' && !white)
				{
					return true;
				}
			}
		}
		return false;
	}
}

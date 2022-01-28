package chess;

/**
 * @author Jinrui Li & Jiazhao Shi
 */

public class Rook extends Piece{
	public char name = 'R';
	
	public Rook(boolean isWhite, int position1, int position2) {
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
	public boolean movable(int pos1, int pos2, int dest1, int dest2, boolean white, Piece[][] pieceBoard, String[][] keyBoard) {
		if(dest1 != pos1 && dest2 != pos2) 
		{
			return false;
		}
		else if(dest2 == pos2 && dest1 == pos1)
		{
			return false;
		}
		else if(dest1 == pos1)
		{
			int i = pos2 - 1;
			int j = pos2 + 1;
			if(dest2 < pos2)
			{
				while(i > dest2)
				{
					if(pieceBoard[dest1][i] != null)
					{
						return false;
					}
					i--;
				}
				if(pieceBoard[dest1][i] != null)
				{
					if(pieceBoard[dest1][i].getColor() == white)
					{
						return false;
					}
				}
			}
			else if(dest2 > pos2)
			{
				while(j < dest2)
				{
					if(pieceBoard[dest1][j] != null)
					{
						return false;
					}
					j++;
				}
				if(pieceBoard[dest1][j] != null)
				{
					if(pieceBoard[dest1][j].getColor() == white)
					{
						return false;
					}
				}
			}
			
		}
		else if(dest2 == pos2)
		{
			int i = pos1 - 1;
			int j = pos1 + 1;
			if(dest1 < pos1)
			{
				while(i > dest1)
				{
					if(pieceBoard[i][dest2] != null)
					{
						return false;
					}
					i--;
				}
				if(pieceBoard[i][dest2] != null)
				{
					if(pieceBoard[i][dest2].getColor() == white)
					{
						return false;
					}
				}
			}
			else if(dest1 > pos1)
			{
				while(j < dest1)
				{
					if(pieceBoard[j][dest2] != null)
					{
						return false;
					}
					j++;
				}
				if(pieceBoard[j][dest2] != null)
				{
					if(pieceBoard[j][dest2].getColor() == white)
					{
						return false;
					}
				}
			}
		}
		
		return true;
	}
}

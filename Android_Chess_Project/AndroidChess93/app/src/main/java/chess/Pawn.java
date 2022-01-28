package chess;

/**
 * @author Jinrui Li & Jiazhao Shi
 */

public class Pawn extends Piece {
	public char name;
	
	public Pawn(boolean isWhite, int position1, int position2) {
		this.name = 'p';
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
		if (dest2 == pos2 && dest1 == pos1)
		{
			return false;
		}
		if(dest2 != pos2) //Ŀ�ĵ�λ�ò���һ��������
		{
			if(dest2 == pos2 + 1 || dest2 == pos2 - 1) 
			{
				if(white && dest1 == pos1 - 1) 
				{
					if(pieceBoard[dest1][dest2] != null) {
						return true;
					}
					else if(Chess.bpawn != 0) {
						if(Chess.bpawn == dest2 && pos1 == 3)
						{
							if(pieceBoard[3][Chess.bpawn].getName()=='p')
							{
								Chess.bpassanting = true;
								return true;
							}
						}
					}
				}
				else if(!white && dest1 == pos1 + 1)
				{
					if(pieceBoard[dest1][dest2] != null) {
						return true;
					}
					else if((Chess.wpawn != 0)) {
						if(Chess.wpawn == dest2 && pos1 == 4)
						{
							if(pieceBoard[4][Chess.wpawn].getName()=='p')
							{
								Chess.wpassanting = true;
								return true;
							}
						}
					}
				}
			}
		}
		else //Ŀ�ĵ�λ����һ��������
		{
			if(white) 
			{
				if(pos1 == 6) 
				{
					if(dest1 == 4) 
					{
						if(dest1 == 4 && pieceBoard[4][dest2] == null) 
						{
							if(pieceBoard[5][dest2] == null) 
							{
								Chess.wpawn = dest2;
								Chess.wpassant = 1;
								return true;
							}
						}
					}
					else if (dest1 == 5)
					{
						if(pieceBoard[5][dest2] == null) 
						{
							return true;
						}
					}
				}
				else
				{
					if(dest1 == pos1 - 1) 
					{
						if(pieceBoard[dest1][dest2] == null) 
						{ 							
							return true;
						}
					}
				}
			}
			else 
			{
				if(dest1 < pos1)
				{
					return false;
				}
				if(pos1 == 1)
				{
				    if (dest1 - pos1 > 2) return false;

					if(dest1 == 3) 
					{
						if(dest1 == 3 && pieceBoard[dest1][dest2] == null) 
						{						
							if (pieceBoard[2][dest2] == null)
							{
								Chess.bpawn = dest2;
								Chess.bpassant = 1;
								return true;
							}
								
						}
					}
					else
					{
						if (pieceBoard[2][dest2] == null)
						{
							return true;
						}
					}
				}
				else 
				{
					if(dest1 == pos1 + 1) 
					{
						if(pieceBoard[dest1][dest2] == null) 
						{ 							
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}

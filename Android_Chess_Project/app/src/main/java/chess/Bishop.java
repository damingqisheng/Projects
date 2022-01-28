package chess;

/**
 * @author Jinrui Li & Jiazhao Shi
 *
 * This class is the piece "bishop"
 */
public class Bishop extends Piece {
	public char name = 'B';

	/**
	 * constructor
	 * @param isWhite	determine if this bishop is white
	 * @param position1	pass the position from board to bishop object
	 * @param position2	pass the position from board to bishop object
	 */
	public Bishop(boolean isWhite, int position1, int position2) {
		this.isWhite = isWhite;
		this.position1 = position1;
		this.position2 = position2;
	}

	/**
	 * return the color of the piece
	 * @return
	 */
	public boolean getColor() {
		return isWhite;
	}

	/**
	 * @param isWhite	pass the color to the object
	 */
	public void setColor(boolean isWhite) {
		this.isWhite = isWhite;
	}

	/**
	 * @return	return the position of the piece
	 */
	public int getPosition1() {
		return position1;
	}

	/**
	 * @param position1 pass the position to the object
	 */
	public void setPosition1(int position1) {
		this.position1 = position1;
	}

	/**
	 * @return	return the position of the piece
	 */
	public int getPosition2() {
		return position2;
	}

	/**
	 * @param position1 pass the position to the object
	 */
	public void setPosition2(int position2) {
		this.position2 = position2;
	}

	/**
	 * @return return the name of the piece
	 */
	public char getName() {
		return name;
	}

	/**
	 * @param name pass the name to the object
	 */
	public void setName(char name) {
		this.name = name;
	}

	/**
	 * determine if this step is legal
	 * @param pos1			current posision of the piece
	 * @param pos2			current posision of the piece
	 * @param dest1			destination of the piece
	 * @param dest2			destination of the piece
	 * @param white			is the piece white or black?
	 * @param pieceBoard	chessboard without "##"
	 * @param keyBoard		chessboard with "##"
	 * @return				return the boolean value of "if the piece can move or not"
	 */
	@Override
	public boolean movable(int pos1, int pos2, int dest1, int dest2,  boolean white, Piece[][] pieceBoard, String[][] keyBoard) {
		if(dest1 == pos1 && dest2 == pos2)
		{
			return false;
		}
		int a = dest1 - pos1;
		int b = dest2 - pos2;
		if(a == b || a == -b)
		{
			if(a > 0)
			{
				if(b > 0)
				{
					for(int i = 1; i < a; i++)
					{
						if(pieceBoard[pos1+i][pos2+i] != null )
						{
							return false;
						}
					}
					
				}
				if(b < 0)
				{
					for(int i = 1; i < a; i++)
					{
						if(pieceBoard[pos1+i][pos2-i] != null )
						{
							return false;
						}
					}
				}
			}
			if(a < 0)
			{
				if(b > 0)
				{
					for(int i = 1; i < -a; i++)
					{
						if(pieceBoard[pos1-i][pos2+i] != null )
						{
							return false;
						}
					}
				}
				if(b < 0)
				{
					for(int i = 1; i < -a; i++)
					{
						if(pieceBoard[pos1-i][pos2-i] != null )
						{
							return false;
						}
					}
				}
			}
		}
		else
		{
			return false;
		}
		if(keyBoard[dest1][dest2].charAt(0) != 'w' && white)
		{
			return true;
		}
		if(keyBoard[dest1][dest2].charAt(0) != 'b' && !white)
		{
			return true;
		}
		return false;
	}
}

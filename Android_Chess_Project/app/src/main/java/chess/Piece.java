package chess;

/**
 * @author Jinrui Li & Jiazhao Shi
 */

public abstract class Piece {
	
	public char name;
	public boolean isWhite;
	public int position1;

	public int position2;
	
	public abstract boolean movable(int pos1, int pos2, int dest1, int dest2, boolean white, Piece[][] pieceBoard, String[][] keyBoard);
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
}

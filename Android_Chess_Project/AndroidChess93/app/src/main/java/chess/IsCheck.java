package chess;

/**
 * @author Jinrui Li & Jiazhao Shi
 */

public class IsCheck {
	public static boolean isCheck(int pos1, int pos2, Piece[][] pieceBoard, String[][] keyBoard) {		
		if(checkStr(pos1, pos2, pieceBoard, keyBoard) || checkN(pos1, pos2, pieceBoard, keyBoard) || checkObli(pos1, pos2, pieceBoard, keyBoard))
		{
			return true;
		}
		return false;
	}
	public static boolean checkN(int pos1, int pos2, Piece[][] pieceBoard, String[][] keyBoard) {
		boolean result = false;
		if(pos1-2 >= 0)
		{
			if(pos2+1 <7)
			{
				if(keyBoard[pos1-2][pos2+1].charAt(1) == 'N') //it is a knight at the correct location
				{
					if(keyBoard[pos1-2][pos2+1].charAt(0) != keyBoard[pos1][pos2].charAt(0))//it is a knight with correct color
					{
						Chess.checkList.add(keyBoard[pos1-2][pos2+1].charAt(1) + pos1-2 + pos2+1 + "");
						result = true;
					}
				}
			}
			if(pos2-1 > 0)
			{
				if(keyBoard[pos1-2][pos2-1].charAt(1) == 'N') //it is a knight at the correct location
				{
					if(keyBoard[pos1-2][pos2-1].charAt(0) != keyBoard[pos1][pos2].charAt(0))//it is a knight with correct color
					{
						Chess.checkList.add(keyBoard[pos1-2][pos2-1].charAt(1) + pos1-2 + pos2-1 + "");
						result = true;
					}
				}
			}
		}
		if(pos1+2 < 7)
		{
			if(pos2+1 <7)
			{
				if(keyBoard[pos1+2][pos2+1].charAt(1) == 'N') //it is a knight at the correct location
				{
					if(keyBoard[pos1+2][pos2+1].charAt(0) != keyBoard[pos1][pos2].charAt(0))//it is a knight with correct color
					{
						Chess.checkList.add(keyBoard[pos1+2][pos2+1].charAt(1) + pos1+2 + pos2+1 + "");
						result = true;
					}
				}
			}
			if(pos2-1 > 0)
			{
				if(keyBoard[pos1+2][pos2-1].charAt(1) == 'N') //it is a knight at the correct location
				{
					if(keyBoard[pos1+2][pos2-1].charAt(0) != keyBoard[pos1][pos2].charAt(0))//it is a knight with correct color
					{
						Chess.checkList.add(keyBoard[pos1+2][pos2-1].charAt(1) + pos1+2 + pos2-1 + "");
						result = true;
					}
				}
			}
			
		}
		if(pos2-2 >= 0)
		{
			if(pos1+1 <7)
			{
				if(keyBoard[pos1+1][pos2-2].charAt(1) == 'N') //it is a knight at the correct location
				{
					if(keyBoard[pos1+1][pos2-2].charAt(0) != keyBoard[pos1][pos2].charAt(0))//it is a knight with correct color
					{
						Chess.checkList.add(keyBoard[pos1+1][pos2-2].charAt(1) + pos1+1 + pos2-2 + "");
						result = true;
					}
				}
			}
			if(pos1-1 > 0)
			{
				if(keyBoard[pos1-1][pos2-2].charAt(1) == 'N') //it is a knight at the correct location
				{
					if(keyBoard[pos1-1][pos2-2].charAt(0) != keyBoard[pos1][pos2].charAt(0))//it is a knight with correct color
					{
						Chess.checkList.add(keyBoard[pos1-1][pos2-2].charAt(1) + pos1-1 + pos2-2 + "");
						result = true;
					}
				}
			}
		}
		if(pos2+2 < 7)
		{
			if(pos1+1 <7)
			{
				if(keyBoard[pos1+1][pos2+2].charAt(1) == 'N') //it is a knight at the correct location
				{
					if(keyBoard[pos1+1][pos2+2].charAt(0) != keyBoard[pos1][pos2].charAt(0))//it is a knight with correct color
					{
						Chess.checkList.add(keyBoard[pos1+1][pos2+2].charAt(1) + pos1+1 + pos2+2 + "");
						result = true;
					}
				}
			}
			if(pos1-1 > 0)
			{
				if(keyBoard[pos1-1][pos2+2].charAt(1) == 'N') //it is a knight at the correct location
				{
					if(keyBoard[pos1-1][pos2+2].charAt(0) != keyBoard[pos1][pos2].charAt(0))//it is a knight with correct color
					{
						Chess.checkList.add(keyBoard[pos1-1][pos2+2].charAt(1) + pos1-1 + pos2+2 + "");
						result = true;
					}
				}
			}
		}
			
		return result;	
	}
	
	
	public static boolean checkStr(int pos1, int pos2, Piece[][] pieceBoard, String[][] keyBoard) {
		if(checkCol(pos1, pos2, pieceBoard, keyBoard) || checkRow(pos1, pos2, pieceBoard, keyBoard))
		{
			return true;
		}
		return false;	
	}
	
	
	public static boolean checkObli(int pos1, int pos2, Piece[][] pieceBoard, String[][] keyBoard) {
		boolean result = false;
		int a = pos1 - 1;
		int b = pos1 + 1;
		int c = pos2 - 1;
		int d = pos2 + 1;
		while(a >= 0 && c >= 0)
		{
			if(pieceBoard[a][c] != null)
			{
				if(keyBoard[a][c].charAt(0) != keyBoard[pos1][pos2].charAt(0))
				{
					if(a == pos1 - 1 && c == pos2 - 1)
					{
						if(keyBoard[a][c].charAt(1)=='K' || keyBoard[a][c].charAt(1)=='p')
						{
							Chess.checkList.add(keyBoard[a][c].charAt(1) + a + c + "");
							result = true;
						}
					}
					if(keyBoard[a][c].charAt(1)=='Q' || keyBoard[a][c].charAt(1)=='B')
					{
						Chess.checkList.add(keyBoard[a][c].charAt(1) + a + c + "");
						result = true;
					}
				}
				break;
			}
			a--;
			c--;
		}
		while(b < 8 && d < 8)
		{
			if(pieceBoard[b][d] != null)
			{
				if(keyBoard[b][d].charAt(0) != keyBoard[pos1][pos2].charAt(0))
				{
					if(b == pos1 + 1 && d == pos2 + 1)
					{
						if(keyBoard[b][d].charAt(1)=='K' || keyBoard[b][d].charAt(1)=='p')
						{
							Chess.checkList.add(keyBoard[b][d].charAt(1) + b + d + "");
							result = true;
						}
					}
					if(keyBoard[b][d].charAt(1)=='Q' || keyBoard[b][d].charAt(1)=='B')
					{
						Chess.checkList.add(keyBoard[b][d].charAt(1) + b + d + "");
						result = true;
					}
				}
				break;
			}
			
			b++;
			d++;			
		}
		
		
		a = pos1 - 1;
		b = pos1 + 1;
		c = pos2 - 1;
		d = pos2 + 1;	
		while(a >= 0 && d < 8)
		{
			if(pieceBoard[a][d] != null)
			{
				if(keyBoard[a][d].charAt(0) != keyBoard[pos1][pos2].charAt(0))
				{
					if(a == pos1 - 1 && d == pos2 + 1)
					{
						if(keyBoard[a][d].charAt(1)=='K' || keyBoard[a][d].charAt(1)=='p')
						{
							Chess.checkList.add(keyBoard[a][d].charAt(1) + a + d + "");
							result = true;
						}
					}
					if(keyBoard[a][d].charAt(1)=='Q' || keyBoard[a][d].charAt(1)=='B')
					{
						Chess.checkList.add(keyBoard[a][d].charAt(1) + a + d + "");
						result = true;
					}
				}
				break;
			}
			
			a--;
			d++;
		}
		while(b < 8 && c >= 0)
		{
			if(pieceBoard[b][c] != null)
			{
				if(keyBoard[b][c].charAt(0) != keyBoard[pos1][pos2].charAt(0))
				{
					if(b == pos1 + 1 && c == pos2 - 1)
					{
						if(keyBoard[b][c].charAt(1)=='K' || keyBoard[b][c].charAt(1)=='p')
						{
							Chess.checkList.add(keyBoard[b][c].charAt(1) + b + c + "");
							result = true;
						}
					}
					if(keyBoard[b][c].charAt(1)=='Q' || keyBoard[b][c].charAt(1)=='B')
					{
						Chess.checkList.add(keyBoard[b][c].charAt(1) + b + c + "");
						result = true;
					}
				}
				break;
			}
			
			b++;
			c--;
		}
		return result;	
	}
	
	
	
	
	
	
	
	
	public static boolean checkCol(int pos1, int pos2, Piece[][] pieceBoard, String[][] keyBoard) {
		boolean result = false;
		int i = pos1 - 1;
		int j = pos1 + 1;
		while(i >= 0) 
		{
			if(pieceBoard[i][pos2] != null)
			{
				if(keyBoard[i][pos2].charAt(0) != keyBoard[pos1][pos2].charAt(0))
				{
					
					if(keyBoard[i][pos2].charAt(1)=='K' && pos1 - i == 1)//check by King
					{
						Chess.checkList.add(keyBoard[i][pos2].charAt(1) + i + pos2 + "");
						result = true;						
					}	
					
					if(keyBoard[i][pos2].charAt(1)=='Q' || keyBoard[i][pos2].charAt(1)=='R')//check by Queen or Rook
					{
						Chess.checkList.add(keyBoard[i][pos2].charAt(1) + i + pos2 + "");
						result = true;					
					}
					break;
				
				}
				
				break;
				
			}
			i--;
		}
		while(j < 8)
		{
			if(pieceBoard[j][pos2] != null)
			{
				if(keyBoard[j][pos2].charAt(0) != keyBoard[pos1][pos2].charAt(0))
				{
					if(keyBoard[j][pos2].charAt(1)=='K' && pos1 - j == -1)//check by King
					{
						Chess.checkList.add(keyBoard[j][pos2].charAt(1) + j + pos2 + "");
						result = true;						
					}						
					if(keyBoard[j][pos2].charAt(1)=='Q' || keyBoard[j][pos2].charAt(1)=='R')//check by Queen or Rook
					{
						Chess.checkList.add(keyBoard[j][pos2].charAt(1) + j + pos2 + "");
						result = true;					
					}
					break;
				}
				
				break;
				
			}
			j++;
		}
		return result;	
	}
	
	public static boolean checkRow(int pos1, int pos2, Piece[][] pieceBoard, String[][] keyBoard) {
		boolean result = false;
		int i = pos2 - 1;
		int j = pos2 + 1;
		
		while(i >= 0) 
		{
			if(pieceBoard[pos1][i] != null)
			{
				if(keyBoard[pos1][i].charAt(0) != keyBoard[pos1][pos2].charAt(0))
				{
					
					if(keyBoard[pos1][i].charAt(1) == 'K' && pos2 - i == 1)//check by King
					{
						Chess.checkList.add(keyBoard[pos1][i].charAt(1) + pos1 + i + "");
						result = true;						
					}	
					
					if(keyBoard[pos1][i].charAt(1) == 'Q' || keyBoard[pos1][i].charAt(1) == 'R')//check by Queen or Rook
					{
						Chess.checkList.add(keyBoard[pos1][i].charAt(1) + pos1 + i + "");
						result = true;					
					}
					break;
				
				}
				
				break;
				
			}
			i--;
		}
		while(j < 8)
		{
			if(pieceBoard[pos1][j] != null)
			{
				if(keyBoard[pos1][j].charAt(0) != keyBoard[pos1][pos2].charAt(0))
				{
					if(keyBoard[pos1][j].charAt(1)=='K' && pos2 - j == -1)//check by King
					{
						Chess.checkList.add(keyBoard[pos1][j].charAt(1) + pos1 + j + "");
						result = true;						
					}						
					if(keyBoard[pos1][j].charAt(1)=='Q' || keyBoard[pos1][j].charAt(1)=='R')//check by Queen or Rook
					{
						Chess.checkList.add(keyBoard[pos1][j].charAt(1) + pos1 + j + "");
						result = true;					
					}
					break;
				}
				
				break;
				
			}
			j++;
		}
		return result;	
	}
	
}

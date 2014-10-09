import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/*
 * GameOfLife is a class that plays the game of life, which is set up on 
 * a square grid, with each cell either "alive" or "dead". The rules for 
 * how the next generation of the board exists are as follows 
 * 1)Any live cell with fewer than two live neighbors dies
 * 2)Any live cell with two or three live neighbors lives
 * 3)Any live cell with more than three live neighbours dies
 * 4)Any dead cell with exactly three live neighbours becomes a live cell
 * 
 * In this variation of the game, the goal is to eliminate all life. To do 
 * so, players can add cells whenever they want. If done correctly, this 
 * can kill off cells. A new generation is spawned every half a second.
 * 
 */


public class GameOfLife implements ActionListener
{
	Board startPosition;
	Board b; 
	int numTurns = 0;
	int numAlive = -1;
	boolean runToggle = false;
	
	/*
	 * CTORS
	 */
	/*
	sets up a game of life with random start configuration
	*/
	public GameOfLife(int numRows, int numCol, double seed, boolean changeStartPos) 
	{
		Board board = new Board(numRows, numCol);
		
		for(int row = 0; row<board.numRow; row++){
			for(int col = 0; col<board.numCol; col++){
				if(Math.random() > seed) //seed is the likelihood, out of 1, that a cell will be alive when the board is created
					board.board[row][col] = 0;
				else
					board.board[row][col] = 1;
			}
		}
		this.b = board;
		this.startPosition = boardCopy(board);
		System.out.println("new game of life created");
		this.setNumAlive();
	}
	
	
	/*
	 * setNumAlive() sets the number of alive cells
	 */
	public void setNumAlive()
	{
		numAlive = 0;
		for(int i = 0; i<this.b.numRow; i++){
			for(int j = 0; j<this.b.numCol; j++){
				if(b.board[i][j] == 1)
					numAlive++;
			}
		}
	}
	
	/*
	 * coordToVal() converts a pixel value to a usable row/column value
	 */
	public static int coordToVal(int coord)
	{
		int val = coord/20;
		return val;
	}
	
	
	/*
	 * boardCopy() copies each position of a board over to a new board
	 */
	public static Board boardCopy (Board original) 
	{
		Board copy = new Board(original.numRow, original.numCol);
		int x = 0;
		for(int[] e : original.board)
		{
			for(int i = 0; i < original.board[0].length; i++)
			{
				
				if(original.board[i][x] == 1)
				{
					copy.board[i][x] = 1;
				}
				else
				{
					copy.board[i][x] = 0;
				}
				
			}
			x++;
		}
		return copy;
	}
	
	
	/*
	 * getOccupiedNeighbors() gets the number of alive cells surrounding the cell that 
	 * it's checking.
	 */
	public int getOccupiedNeighbors(int rowNum, int colNum)
	{
		int numNeighbors = 0;
		int checkCol = colNum - 1;
		int checkRow = rowNum - 1;
		while(checkCol<= colNum + 1){
			while(checkRow <= rowNum + 1)
			{
				if(!(checkRow == rowNum && checkCol == colNum)) //case of checking itself
				{
					if(b.locationIsValid(checkRow, checkCol))
					{
						if(b.board[checkRow][checkCol] == 1)
						{
							numNeighbors++;
						}
					}
				}
				checkRow++;
			}
			checkCol++;
			checkRow = rowNum - 1;
		}
		return numNeighbors;
	}
	
	/*
	 * playTurn() plays one turn of the game of life. It sets up a new board with 
	 * the new configuration based on the old board and checks whether each 
	 * cell will be alive or dead
	 */
	
	void playTurn()
	{
		numTurns++;
		Board newGen = new Board(this.b.numRow, this.b.numCol); 
		for(int i = 0; i < newGen.numRow; i++)
		{
			for(int x = 0; x < newGen.numCol; x++)
			{
				if(this.b.board[i][x] == 1)
				{
					if(this.getOccupiedNeighbors(i, x) == 2 || this.getOccupiedNeighbors(i, x) == 3)
						newGen.board[i][x] = 1;
					else{
					newGen.board[i][x] = 0;
					}
					//code for if the cell is already alive
				}
				else
				{
				
				if(this.getOccupiedNeighbors(i, x) == 3)
					newGen.board[i][x] = 1;
				else{
				newGen.board[i][x] = 0;
				}
				//code for if the cell isn't living
				}
			}
		}
		this.b.board = newGen.board;
	}
	public void actionPerformed(ActionEvent e) {	
	}
}


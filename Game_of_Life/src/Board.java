import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/*
 * Sets up a rectangular game board to be used by the game of life.

 */
public class Board extends JComponent
{
	int numCol;
	int numRow;
	int[][] board = new int[numRow][numCol];
	/*
	 * Constructors
	 */
	
	/*
	 * constructs a game of life given a number of columns and rows
	 */
	public Board(int numRow, int numCol)
	{
		this.numCol = numCol;
		this.numRow = numRow;
		this.board = new int[numRow][numCol];
		
	}
	
	/*
	 * Constructs a game of life given a two dimensional array
	 */
	public Board(int[][] boardInput)
	{
		this.board = boardInput;
	}
	
	/*
	 * METHODS
	 */
	
	/*
	 * getNumCol() gets the number of columns from the class data
	 */
	public int getNumCol()
	{
		return numCol;
	}
	
	/*
	 * getNumRow() gets the number of columns from the class data
	 */
	public int getNumRow()
	{
		return numRow;
	}

	/*
	 * sets the state of the cell
	 */
	void setSpot(int rowNum, int colNum, int piece)
	{
		this.board[rowNum][colNum] = piece;
	}
	
	/*
	 * returns the state of the cell
	 */
	int getSpot(int rowNum, int colNum)
	{
		return board[rowNum][colNum];
	}
	/*
	 * makes sure that the location given is actually on the board
	 */
	boolean locationIsValid(int rowNum, int colNum)
	{
		if(rowNum < this.getNumRow() && rowNum >= 0 && colNum < this.getNumCol() && colNum >= 0)
			return true;
		return false;	
	}
	
	
	/*
	 * prints out the board onto the console
	 */
	void boardPrint()
	{
		for(int i = 0; i < this.getNumCol(); i++)
		{
			for(int e = 0; e < this.getNumRow(); e++)
				System.out.print(this.board[e][i]);
			System.out.println();
		}
	}
	/*
	 * clears the board of any pieces, setting all of the states of the cells to 0
	 */
	void clearBoard()
	{	
		for(int row = 0; row<numRow; row++){
			for(int col = 0; col<numCol; col++){
				board[row][col] = 0;
			}
		}
	}
	
}
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

/*
 * This class displays a GameOfLife for the user to interact with it.
 * It includes all of the buttons(start, pause, and reset), and also 
 * the graphical representation of the board.
 * The methods included are:
 * paintComponent(Graphics g): paints the board 
 * drawBoard(): draws the board
 * setSpot(int rowNum, int colNum, int piece): sets a spot on the board
 * clickChange(): sets a cell to alive if clicked
 * playTurnPaint(): plays a turn, then repaints
 * autonomousPlay(): runs the game of life on a time interval
 * startButton(): starts timer
 * pauseButton(): stops timer
 * resetButton(): resets board to start position.
 * setBoard(): sets the gameOfLife board equal to the board stored in it's Board
 * 
 */
public class GameOfLifeViewer extends JComponent
{
	JFrame f = new JFrame();
	GameOfLife g;
	Timer t;
	
	/*
	 * CONSTRUCTOR. Takes in a game of life
	 */
	public GameOfLifeViewer(GameOfLife g)
	{
		this.g = g;
		f.setSize(20*g.b.getNumCol()+200, 20*g.b.getNumRow()+60);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setBackground(new Color(20, 20, 20));
	}
	/*
	 * METHODS
	 */
	
	/*
	 * paintComponent() paints the board in a grid on to the frame. Also, it paints
	 * on some text relating to number alive, turn number, and, if the player has won,
	 * the number of turns that they won in.
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;	
		
		
		int x = 0;
		for(int[] e : this.g.b.board)
		{
			for(int i = 0; i < this.g.b.board[0].length; i++){
				g2.setColor(new Color(20, 20, 20));
				Rectangle2D.Double gridSquare = new Rectangle2D.Double((int) i * 20, (int) x*20, 20, 20);
				if(this.g.b.board[i][x] == 1)
				{
				g2.setColor(new Color(204, 255, 51));
				g2.draw(gridSquare);
				g2.fill(gridSquare);
				g2.setColor(new Color(20, 20, 20));
				//System.out.println(x + " " + i);
				}
				else{
				//g2.setColor(new Color(204, 255, 51));
				g2.draw(gridSquare);
				g2.setColor(Color.BLACK);
				g2.fill(gridSquare);
				Rectangle2D.Double gridSquare2 = new Rectangle2D.Double((int) i * 20, (int) x*20, 20, 20);
				g2.setColor(new Color(130, 0, 51));
				g2.draw(gridSquare2);
				}
				
			}
			x++;
		}
		if(this.g.numAlive > 0)
		{
			g2.setColor(Color.WHITE);
			g2.drawString("number of alive cells: " + this.g.numAlive, 20*this.g.b.numCol + 20, 200);
		}
		else{
			g2.setColor(Color.BLUE);
			g2.drawString("Winner! You won in " + this.g.numTurns + " turns", 20*this.g.b.numCol + 20, 200);
		}
		g2.drawString("Turn Number:" + this.g.numTurns, 20*this.g.b.numCol + 20, 170);
		this.g.numAlive = 0;
	}
	
	
	/* 
	 * draws the board
	 */
	public void drawBoard()
	{
		//System.out.println(board[3][3]);
	
		f.setVisible(true);
		f.add(this);
	}
	
	/*
	 * Sets a spot on the board
	 */
	void setSpot(int rowNum, int colNum, int piece)
	{
		this.g.b.board[rowNum][colNum] = piece;
	}
	/*
	 * Sets the cell clicked to alive. Does nothing if cell is already alive
	 */
	public void clickChange()
	{
	
		class MousePressListener implements MouseListener
		{
			public void mousePressed(MouseEvent event)
			{
				int x = event.getX();
				int y = event.getY();
				
				GameOfLifeViewer.this.g.b.setSpot(GameOfLife.coordToVal(x), GameOfLife.coordToVal(y), 1);
				GameOfLifeViewer.this.g.setNumAlive();
				GameOfLifeViewer.this.repaint();
				
			}
			public void mouseReleased(MouseEvent event) {}
			public void mouseClicked(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
			}
		
		
		
		MouseListener clicker = new MousePressListener();
		this.addMouseListener(clicker);
	}
	
	
	/*
	 * plays a turn using the playTurn() in GameOfLife, then repaints the board
	 */
	public void playTurnPaint()
	{
		this.g.playTurn();
		this.repaint();
	}
	
	/*
	 * autonomousPlay() runs playAgain() on a time interval, and adds the buttons to the game
	 */
	
	public void autonomousPlay()
	{
		this.drawBoard();
		class TimerListener implements ActionListener
		{
			
			public void actionPerformed(ActionEvent event)
			{
				
				GameOfLifeViewer.this.clickChange();
				GameOfLifeViewer.this.startButton();
				GameOfLifeViewer.this.pauseButton();
				GameOfLifeViewer.this.resetButton();
				GameOfLifeViewer.this.g.setNumAlive();
				if(GameOfLifeViewer.this.g.numAlive>0){
					GameOfLifeViewer.this.playTurnPaint();
				}
				else
				{
					GameOfLifeViewer.this.repaint();
					GameOfLifeViewer.this.t.stop();
				}
			}
		} 
		ActionListener listener = new TimerListener();
		final int DELAY = 1000;
		t = new Timer(DELAY, listener);
		t.start();
	}
	
	/*
	 * Starts the timer when clicked
	 */
	
	public void startButton()
	{
		final JButton runButton = new JButton("PLAY");
		runButton.setSize(160, 50);
		runButton.setLocation(this.g.b.numCol*20 + 20, 50);
		runButton.setFont(this.getFont().deriveFont(25.0f));
		this.add(runButton);
		class ClickListener implements ActionListener{
			public void actionPerformed(ActionEvent event)
			{
				GameOfLifeViewer.this.g.setNumAlive();
				if(GameOfLifeViewer.this.g.numAlive>0)
					GameOfLifeViewer.this.autonomousPlay();
				runButton.setVisible(false);
				GameOfLifeViewer.this.t.start();
			}
		}
		ActionListener listener = new ClickListener();
		runButton.addActionListener(listener);
	}
	
	/*
	 * Stops the timer when clicked
	 */
	public void pauseButton()
	{
		final JButton runButton = new JButton("PAUSE");
		runButton.setSize(160, 50);
		runButton.setLocation(this.g.b.numCol*20 + 20, 0);
		//runButton.setBackground(new Color(0, 0, 220));
		runButton.setFont(this.getFont().deriveFont(25.0f));
		this.add(runButton);
		class ClickListener implements ActionListener{
			public void actionPerformed(ActionEvent event)
			{
				GameOfLifeViewer.this.t.stop();
				//GameOfLife.this.runToggle = true;
			}
		}
		ActionListener listener = new ClickListener();
		runButton.addActionListener(listener);
		//System.out.println(g.numAlive);
	}
	/*
	 * resetButton() sets the board back to the start position and resets the numTurns value
	 * to 0
	 */
	public void resetButton(){
		final JButton runButton = new JButton("RESTART");
		runButton.setSize(160, 50);
		runButton.setLocation(this.g.b.numCol*20 + 20, 100);
		//runButton.setBackground(new Color(204, 255, 51));
		runButton.setFont(this.getFont().deriveFont(25.0f));
		this.add(runButton);
		class ClickListener implements ActionListener{
			public void actionPerformed(ActionEvent event)
			{
				GameOfLifeViewer.this.setBoard(GameOfLifeViewer.this.g.startPosition);
				GameOfLifeViewer.this.g.numTurns = 0;
				GameOfLifeViewer.this.repaint();
			}
		}
		ActionListener listener = new ClickListener();
		runButton.addActionListener(listener);
	}
	
	/*
	 * setBoard() sets the Board of the game of life equal to that of the board contained 
	 * in Board. Also, it stops the timer.
	 */
	public void setBoard(Board b)
	{
		this.g.b.board = b.board;
		this.g.setNumAlive();
		this.t.stop();
	}
	
	public static void main(String[] args)
	{
		GameOfLife g = new GameOfLife(40, 40,.5, true);
		GameOfLifeViewer v = new GameOfLifeViewer(g);
		v.autonomousPlay();
	}

}

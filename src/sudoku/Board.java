package sudoku;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import javax.swing.*;

public class Board extends JPanel {
	protected int[][] fullBoard = new int[9][9];			// [row][col]
	private int[][] originalPuzzle = new int[9][9];
	private static boolean[][] isUnchangeable = new boolean[9][9];
	private static boolean[][] isCircled = new boolean[9][9];
	public int[][] currentBoard = new int[9][9];
	protected boolean isSolved = false;
	int	SIZE, size;
	int highlightedCol = -1;
	int highlightedRow;
	int highlightedNum;
	SudokuWindow window;
	
	MouseListener boardMouseListener = new MouseAdapter() {
		public void mousePressed(MouseEvent evt) {
			requestFocus();
			if (evt.getButton() == MouseEvent.BUTTON1) {
				int x = evt.getX();
				int y = evt.getY();
				highlightedCol = (x-3) / ((SIZE-6)/9);
				highlightedRow = (y-3) / ((SIZE-6)/9);
				if ((highlightedRow < 9) && (highlightedCol < 9)) {
					if (isUnchangeable[highlightedRow][highlightedCol]) {
						highlightedNum = currentBoard[highlightedRow][highlightedCol];
						SudokuWindow.contentPane.repaint();
						repaint();
						System.out.println("Unchangeable " + highlightedNum);
					}
					else {
						currentBoard[highlightedRow][highlightedCol] = highlightedNum;
						repaint();
						checkCompleted();
						System.out.println("Inserting " + highlightedNum);
					}
				}
			}
		}
	};

	KeyListener boardKeyListener = new KeyListener() {
		public void keyPressed(KeyEvent evt) { 
			int key = evt.getKeyCode();
			if (isUnchangeable[highlightedRow][highlightedCol] == false) {
				switch (key) {
					case KeyEvent.VK_0: updateNum(highlightedRow, highlightedCol, 0); break;
					case KeyEvent.VK_1: updateNum(highlightedRow, highlightedCol, 1); break;
					case KeyEvent.VK_2: updateNum(highlightedRow, highlightedCol, 2); break;
					case KeyEvent.VK_3: updateNum(highlightedRow, highlightedCol, 3); break;
					case KeyEvent.VK_4: updateNum(highlightedRow, highlightedCol, 4); break;
					case KeyEvent.VK_5: updateNum(highlightedRow, highlightedCol, 5); break;
					case KeyEvent.VK_6: updateNum(highlightedRow, highlightedCol, 6); break;
					case KeyEvent.VK_7: updateNum(highlightedRow, highlightedCol, 7); break;
					case KeyEvent.VK_8: updateNum(highlightedRow, highlightedCol, 8); break;
					case KeyEvent.VK_9: updateNum(highlightedRow, highlightedCol, 9); break;
					case KeyEvent.VK_LEFT: if (highlightedCol > 0) {highlightedCol--;}; break;
					case KeyEvent.VK_RIGHT: if (highlightedCol < 8) {highlightedCol++;}; break;
					case KeyEvent.VK_UP: if (highlightedRow > 0) {highlightedRow--;}; break;
					case KeyEvent.VK_DOWN: if (highlightedRow < 8) {highlightedRow++;}; break;
					case KeyEvent.VK_ENTER: highlightedNum = currentBoard[highlightedRow][highlightedCol]; break;
				}
			}
			else {
				switch (key) {
					case KeyEvent.VK_LEFT: if (highlightedCol > 0) {highlightedCol--;}; break;
					case KeyEvent.VK_RIGHT: if (highlightedCol < 8) {highlightedCol++;}; break;
					case KeyEvent.VK_UP: if (highlightedRow > 0) {highlightedRow--;}; break;
					case KeyEvent.VK_DOWN: if (highlightedRow < 8) {highlightedRow++;}; break;
					case KeyEvent.VK_ENTER: highlightedNum = currentBoard[highlightedRow][highlightedCol]; break;
				}
			}
			SudokuWindow.contentPane.repaint();
		}
		public void keyReleased(KeyEvent evt) { }
		public void keyTyped(KeyEvent evt) { }
	};
	
	public Board(int SIZE) {
		addMouseListener(boardMouseListener);
		addKeyListener(boardKeyListener);
		this.SIZE = SIZE;
		this.size = SIZE - 6;
		setPreferredSize(new Dimension(SIZE, SIZE));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		Color gray = new Color(70, 70, 100);
		BasicStroke boldStroke2 = new BasicStroke(2);
		BasicStroke boldStroke3 = new BasicStroke(3);
		BasicStroke boldStroke4 = new BasicStroke(4);
		BasicStroke boldStroke6 = new BasicStroke(6);
		
		g2d.setStroke(boldStroke2);
		g2d.setColor(gray);
		for (int x = 1; x < 9; x++) {
			g.drawLine(3, x*size/9+3, size+3, x*size/9+3);
			g.drawLine(x*size/9+3, 3, x*size/9+3, size+3);
		}		
		
		g2d.setStroke(boldStroke4);
		g2d.setColor(Color.BLACK);
		g.drawRect(3, 3, size, size);
		for (int x = 1; x < 3; x++) {
			g.drawLine(x*size/3+3, 3, x*size/3+3, size+3);
			g.drawLine(3, x*size/3+3, size+3, x*size/3+3);
		}
		
		g.setColor(Color.BLUE);
		if (highlightedCol >= 0) {
			g2d.setStroke(boldStroke4);
			g.drawRect(highlightedCol*size/9+3, highlightedRow*size/9+3, size/9, size/9);				
		}
		
		g.setColor(Color.BLACK);
		Font numFont = new Font("Monospaced", Font.BOLD, SIZE/17);
		g.setFont(numFont);
		FontMetrics metrics = g.getFontMetrics(numFont);
		int stringWidth = g.getFontMetrics().stringWidth("0");
		int stringHeight = metrics.getHeight();
		int ascent = metrics.getAscent();		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (currentBoard[row][col] != 0) {
					if (isUnchangeable[row][col] == true) {
						g2d.setColor(new Color(150, 150, 0));	// DARK YELLOW
						g2d.drawString(String.valueOf(currentBoard[row][col]), 
									   (int)((col+1.0/2)*size/9-stringWidth/2) + 3, 
									   (int)((row+1.0/2)*size/9) 
									   - stringHeight/2 + ascent + 3
									   );
						
					}
					else 
						g2d.setColor(Color.BLACK);
						g2d.drawString(String.valueOf(currentBoard[row][col]), 
									   (int)((col+1.0/2)*size/9-stringWidth/2) + 3, 
									   (int)((row+1.0/2)*size/9) - stringHeight/2 + ascent + 3
									   );
				}
				
				if ((highlightedNum != 0) && (currentBoard[row][col] == highlightedNum)) {
						isCircled[row][col] = true;
						g2d.setColor(new Color(0, 200, 0));	// DARK GREEN
						g2d.setStroke(boldStroke2);
						g2d.drawOval((int)((col+1.0/9)*size/9)+3, (int)((row+1.0/9)*size/9)+3, size/9-2*size/81, size/9-2*size/81);
				}
			}
		}

	} // End of painComponent method
		
	protected void updateNum(int Row, int Col, int digit) {
		currentBoard[Row][Col] = digit;
		if (digit != 0) { 
			highlightedNum = digit;
		}
		checkCompleted();
	}
	
	/**
	 * 
	 * @param puzzle		int[9][9], holds the data for a sudoku puzzle
	 * @postcondition		This subroutine load the puzzle in the parameter to the board object.
	 * 						The originalPuzzle and currentBoard of the board are update accordingly.
	 * 						The numbers that are first loaded from the puzzle cannot be changed by the player.
 	 */
	protected void loadPuzzle(int[][] puzzle) { 
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				originalPuzzle[row][col] = puzzle[row][col];
				currentBoard[row][col] = puzzle[row][col];
				if (originalPuzzle[row][col] > 0) 
					isUnchangeable[row][col] = true;
				else
					isUnchangeable[row][col] = false;
			}
		}
	} // End of subroutine load()
	
	public int[][] getFullBoard() {
		return fullBoard;
	}
	public void printBoard() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				System.out.print(currentBoard[row][col]);
			}
			System.out.println("");
		}
	}

	public void reset() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++)
				currentBoard[row][col] = originalPuzzle[row][col];
		}
		highlightedCol = -1;
		SudokuWindow.contentPane.repaint();
	}
	
	public void checkCompleted() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (currentBoard[row][col] != fullBoard[row][col]) {
					return;
				}
			}
		}
		JOptionPane.showMessageDialog(new JFrame(), "Good Job!");
		
		isSolved = true;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				isUnchangeable[row][col] = true;
			}
		}
		
		Play playObj = (Play)SudokuWindow.contentPane;
		int levelIndex = playObj.WINDOW_TYPE;
		int prevRecord = SudokuWindow.player.timeRecord[levelIndex/100 - 1][levelIndex % 100];
		if (prevRecord != 0 & prevRecord < playObj.time)
			return;
		SudokuWindow.player.updateTimeRecord(levelIndex, playObj.time);
		return;
	}
} // End of class Board

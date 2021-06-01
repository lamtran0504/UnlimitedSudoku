package sudoku;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Play extends JPanel {
	private static SudokuWindow window = new SudokuWindow();
	
	
	public final int puzzleNumber;
	private final String difficulty;
	private static int windowWidth = SudokuWindow.windowWidth,
						 windowHeight = SudokuWindow.windowHeight;
	public static Board board = new Board(windowWidth-6);
	public final int WINDOW_TYPE;
	public static int time;
	public Timer timer;

	static class LowerPanel extends JPanel {
		int WIDTH, HEIGHT;
		final int WINDOW_TYPE;
		protected LowerPanel(int WINDOW_TYPE, int w, int h) {
			super();
			this.WINDOW_TYPE = WINDOW_TYPE;
			WIDTH = w; HEIGHT = h;
			Color darkGray = new Color(30, 30, 30);
			JButton checkButton = new JButton("Check");
			checkButton.setSize(new Dimension(windowWidth*3/10, windowHeight/22));
		
			ButtonPanel buttonPanel = new Play.ButtonPanel((windowWidth-6)/10);
			buttonPanel.setLayout(null);
			buttonPanel.setBounds(0, HEIGHT/10, WIDTH, HEIGHT/2);
			buttonPanel.setBackground(darkGray);
			
			sudoku.ButtonPanel buttonPanel2 = new sudoku.ButtonPanel(WINDOW_TYPE); 
			buttonPanel2.setBounds(0, HEIGHT - 50, WIDTH, 50);
			
			this.setLayout(null);
			this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
			this.add(buttonPanel);
			add(buttonPanel2);
			this.setBackground(darkGray);
			checkButton.setBounds(WIDTH*19/25, HEIGHT*4/7, WIDTH/5, HEIGHT*2/5);
		}; // End of LowerPanel() constructor
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			repaint();
		}
	} // End of nested class LowerPanel
		
	static class NumButton extends JPanel {
		private int size;
		private int digit;
		protected void paintComponent(Graphics g) {
			this.setPreferredSize(new Dimension(size, size));
			Graphics2D g2d = (Graphics2D)g;
			g2d.setBackground(Color.DARK_GRAY);
			g2d.setColor(Color.WHITE);
			g2d.setStroke(new BasicStroke(2));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setFont(new Font("Monospaced", Font.BOLD, size/2));	
			FontMetrics metrics = g.getFontMetrics(new Font("Monospaced", Font.PLAIN, size/2));
			int stringWidth = g.getFontMetrics().stringWidth("0");
			int stringHeight = metrics.getHeight();
			int ascent = metrics.getAscent();
			
			if (digit == board.highlightedNum) {
				g2d.setColor(Color.GREEN);
				g2d.fillOval(2, 2, size-5, size-5);
				g2d.setColor(Color.WHITE);
				g2d.drawOval(2, 2, size-5, size-5);
				g2d.drawString(String.valueOf(digit), (size-stringWidth)/2, (size-stringHeight)/2 + ascent);
			}			
			else {
				g2d.setColor(Color.WHITE);
				g2d.drawOval(2, 2, size-5, size-5);
				g2d.drawString(String.valueOf(digit), (size-stringWidth)/2, (size-stringHeight)/2 + ascent);
			}			
			g2d.drawString(String.valueOf(digit), (size-stringWidth)/2, (size-stringHeight)/2 + ascent);
			this.setVisible(true);
		} // End of painComponent
		public NumButton(int digit, int size) {
			this.digit = digit;
			this.size = size;
		}
	} // End of nested class NumButton
		
	static class ButtonPanel extends JPanel {
		int size;
		protected ButtonPanel(int size) {
			this.size = size;
			NumButton button1 = new NumButton(1, size);
			NumButton button2 = new NumButton(2, size);
			NumButton button3 = new NumButton(3, size);
			NumButton button4 = new NumButton(4, size);
			NumButton button5 = new NumButton(5, size);
			NumButton button6 = new NumButton(6, size);
			NumButton button7 = new NumButton(7, size);
			NumButton button8 = new NumButton(8, size);
			NumButton button9 = new NumButton(9, size);
			NumButton button0 = new NumButton(0, size);
			NumButton[] NumButtonList = new NumButton[] {button0, button1, button2, button3, button4, button5, button6, button7, button8, button9};
			MouseListener numButtonListener = new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					requestFocus();
					if (evt.getButton() == MouseEvent.BUTTON1) {
						Object source = evt.getSource();
						try {
							NumButton numButton = (NumButton)source;
							board.highlightedNum = numButton.digit;
							board.highlightedCol = -1;
							board.repaint();
							repaint();
						}
						catch (ClassCastException e) {
							return;
						}
					}
				}
			};
			for (int i = 0; i < 10; i++) {
				NumButtonList[i].addMouseListener(numButtonListener);
				this.add(NumButtonList[i]);
				NumButtonList[i].setBounds(i*button0.size + 3, 0, size, size);
			}
		} // End of constructor ButtonPanel()
	} // End of nested class ButtonPanel

	static class UpperPanel extends JPanel {
		SudokuEngine.Difficulty difficulty;
		int puzzleNumber;
		int WIDTH = windowWidth - 6;
		int HEIGHT= (windowHeight - 9 - windowWidth)*7/17;
		int x = 10;

		static String timeToString(int time) {
			int hour = time/3600,
				min = (time%3600)/60,
				sec = time%60;
			if (hour == 0) {
				if (min < 10) {
					if (sec > 9) {
						return ("0" + String.valueOf(min) + ":" + String.valueOf(sec));
					}
					return ("0" + String.valueOf(min) + ":0" + String.valueOf(sec));
				}
				if (sec > 9) {
					return (String.valueOf(min) + ":" + String.valueOf(sec));
				}
				return (String.valueOf(min) + ":0" + String.valueOf(sec));
			}
			return String.valueOf(hour) + ":" + timeToString(time%3600);
		};
			
		protected void paintComponent(Graphics g) {
			
			String puzzleNumberString = "Puzzle " + this.puzzleNumber;
			this.setBackground(Color.DARK_GRAY);
			g.fillRect(0, 0, WIDTH, HEIGHT+2);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Monospaced", Font.BOLD, HEIGHT*3/7));
			int stringWidth = g.getFontMetrics().stringWidth(puzzleNumberString);
			g.drawString(puzzleNumberString, WIDTH/2 - stringWidth/2, HEIGHT*4/7);
			
			switch (difficulty) {
				case EASY: g.setColor(new Color(0, 200, 0)); break;
				case NORMAL: g.setColor(new Color(0, 0, 200)); break;
				case HARD: g.setColor(new Color(255, 140, 0)); break;
			}
			g.setFont(new Font("Momospaced", Font.BOLD, windowHeight/35));
			stringWidth = g.getFontMetrics().stringWidth(difficulty.toString());
			g.drawString(difficulty.toString(), WIDTH*39/40 - stringWidth, HEIGHT*2/7);
			
			g.setColor(new Color(0, 200, 0));
			stringWidth = g.getFontMetrics().stringWidth(timeToString(time));
			g.drawString(timeToString(time), WIDTH/2 - stringWidth/2, HEIGHT*18/20);
			setVisible(true);	
		}
		
		protected UpperPanel(SudokuEngine.Difficulty difficulty, int puzzleNumber, Timer timer) {
			this.difficulty = difficulty;
			this.puzzleNumber = puzzleNumber;
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
		}
	}
	
	protected Play(SudokuEngine.Difficulty difficulty, int puzzleNumber) {
		String puzzleString = "";
		this.puzzleNumber = puzzleNumber;
		time = 0;
		timer = new Timer(1000,  new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!board.isSolved) {
					time++;
					repaint();
				}
				else
					timer.stop();
			}
		});
		timer.start();
		
		int windowType = 200;
		switch (difficulty) {
			case EASY: this.difficulty = "Easy"; windowType -= 100; break;
			case NORMAL: this.difficulty = "Normal"; break;
			case HARD: this.difficulty = "Hard"; windowType += 100; break;
			default: this.difficulty = "Normal"; break;
		}
		windowType += puzzleNumber - 1;
		WINDOW_TYPE = windowType;
		
		int[][] puzzle = new int[9][9];
		File file = new File(this.difficulty);
		try {
			Scanner puzzleInput = new Scanner(file);
			for (int i = 0; i < puzzleNumber; i++) {
				puzzleString = puzzleInput.nextLine();
			}
			puzzleInput.close();
		}
		catch (FileNotFoundException e){
			System.out.println("Puzzle Data File Not Found");
		};
	
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				puzzle[row][col] = Integer.parseInt(puzzleString.substring(row*9+col, row*9+col+1));
			}
		}

		board.loadPuzzle(puzzle);
		board.highlightedCol = -1;
		board.highlightedNum = 0;
		board.highlightedRow = 0;
		board.fullBoard = SudokuEngine.solve(puzzle);
		UpperPanel upperPanel = new UpperPanel(difficulty, puzzleNumber, timer);
		LowerPanel lowerPanel = new LowerPanel(WINDOW_TYPE, windowWidth - 6, windowHeight - 29 - upperPanel.HEIGHT - board.SIZE);	
		this.setLayout(new BorderLayout());
		this.add(upperPanel, BorderLayout.NORTH);
		this.add(board, BorderLayout.CENTER);
		this.add(lowerPanel, BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(windowWidth, windowHeight));
		this.setVisible(true);	
	}
	
	public void exit() {
		timer.stop();
		timer = null;
	}
} // End of class Play()


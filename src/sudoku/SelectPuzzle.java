package sudoku;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

import sudoku.Play.NumButton;

public class SelectPuzzle extends JPanel {
	private static int windowWidth = SudokuWindow.windowWidth;
	private static int windowHeight = SudokuWindow.windowHeight;
	private SudokuEngine.Difficulty difficulty;
	private int pageNumber;
	public final int WINDOW_TYPE = 2;
	
	protected SelectPuzzle(SudokuEngine.Difficulty difficulty, int pageNumber) {
		super();
		setLayout(null);
		
		add(new StringPanel());
		this.difficulty = difficulty;
		this.pageNumber = pageNumber;
		add(new PuzzleButtonPanel(difficulty, pageNumber));
		add(new ChangePagePanel(difficulty, pageNumber));
		add(new ButtonPanel(WINDOW_TYPE));
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(SudokuWindow.backgroundImage, 0, 0, null);
		g2d.setColor(new Color(180, 180, 180, 200));
		g2d.fillRect(0, 0, windowWidth, windowHeight);
	}
		
	private static class StringPanel extends JPanel {		
		StringPanel() {
			setBounds(0, windowHeight/20, windowWidth, windowHeight/4);	
		}
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			int width = this.getWidth();
			Font stringFont = new Font("Monospaced", Font.BOLD, width/10);
			g2d.setFont(stringFont);
			g2d.setColor(Color.BLACK);
			
			FontMetrics fm = g2d.getFontMetrics(stringFont);
			int ascent = fm.getAscent();
			int stringWidth = g2d.getFontMetrics().stringWidth("Select Puzzle");
			g2d.setStroke(new BasicStroke(5));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.drawString("Select Puzzle", (width-stringWidth)/2, ascent);
			g2d.dispose();
		}
	}
	
	private class PuzzleButtonPanel extends JPanel {
		private PuzzleButtonPanel(SudokuEngine.Difficulty difficulty, int pageNumber) {
			setBounds(50, windowHeight/3, windowWidth - 100, windowWidth - 100);
			setLayout(null);
			for (int i = 0; i < 25; i++) {
				this.add(new PuzzleButton(difficulty, (pageNumber-1)*25 + i + 1));
			}
		}
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			BufferedImage buttonBackground = SudokuWindow.backgroundImage.getSubimage(50, windowHeight/3, windowWidth - 100, windowWidth - 100);
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(buttonBackground, 0, 0, this.getWidth(), this.getHeight(), null);
			g2d.setColor(new Color(180, 180, 180, 200));
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
			this.repaint();
		}
	}
	
	protected class PuzzleButton extends JPanel {
		protected SudokuEngine.Difficulty difficulty;
		protected int puzzleNumber;
		private int size = (windowWidth - 80)/5 - 20;
		PuzzleButton(SudokuEngine.Difficulty difficulty, int puzzleNumber) {
			this.difficulty = difficulty;
			this.puzzleNumber = puzzleNumber;
			int row = ((puzzleNumber-1)%25)/5;
			int column = ((puzzleNumber-1)%25)%5;
			this.setBounds((windowWidth-80)/5*column, (windowWidth-80)/5*row, (windowWidth-80)/5-19, (windowWidth-80)/5-19);
			this.addMouseListener(ButtonListener.puzzleBtnListener);
		}
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setColor(Color.GRAY);
			g2d.fillRoundRect(0, 0, size, size, 20, 20);
			
			switch (this.difficulty) {
				case HARD: g2d.setColor(new Color(255, 140, 0)); break;
				case NORMAL: g2d.setColor(new Color(0, 0, 200)); break;
				case EASY: g2d.setColor(new Color(0, 200, 0));
			}
			BasicStroke boldStroke5 = new BasicStroke(5);
			g2d.setStroke(boldStroke5);
			g2d.drawRoundRect(0, 0, size, size, 20, 20);
			g2d.drawRect(0, 0, size, size);
				
			int fastestTime = SudokuWindow.player.getFastestTime(difficulty, puzzleNumber);
			if (fastestTime <= 0) {
				g2d.setColor(Color.BLACK);
				g2d.setFont(new Font("Momospaced", Font.BOLD, 25));
				FontMetrics metrics = g.getFontMetrics(new Font("Monospaced", Font.BOLD, 25));
				int stringWidth = g.getFontMetrics().stringWidth(String.valueOf(puzzleNumber));
				int stringHeight = metrics.getHeight();
				int ascent = metrics.getAscent();
				g2d.drawString(String.valueOf(puzzleNumber), this.getWidth()/2 - stringWidth/2, this.getHeight()/2 - stringHeight/2 + ascent);
			}
			
			else {
				g2d.setColor(Color.GREEN);
				g2d.setFont(new Font("Momospaced", Font.BOLD, 15));
				FontMetrics metrics = g.getFontMetrics(new Font("Monospaced", Font.BOLD, 15));
				int stringWidth = g.getFontMetrics().stringWidth(timeToString(fastestTime));
				int stringHeight = metrics.getHeight();
				int ascent = metrics.getAscent();
				g2d.drawString(timeToString(fastestTime), this.getWidth()/2 - stringWidth/2, this.getHeight()/2 - stringHeight/2 + ascent);
			}
			g2d.dispose();
		}
	}
	
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

	protected class ChangePagePanel extends JPanel {
		protected BufferedImage nextImg, lastImg;
		protected int pageNumber;
		protected SudokuEngine.Difficulty difficulty;
		private ChangePagePanel(SudokuEngine.Difficulty difficulty, int pageNumber) {
			setBounds(30, 80, 340, 130);
			nextImg = GameResources.getBufferedImage("resources\\Images\\Next.png");
			lastImg = GameResources.getBufferedImage("resources\\Images\\Last.png");
			addMouseListener(ButtonListener.ChangePageListener);
			this.pageNumber = pageNumber;
			this.difficulty = difficulty;
		}
		
		protected void paintComponent(Graphics g) {
			g.drawImage(lastImg, 0, 0, null);
			g.drawImage(nextImg, 210, 0, null);
		}
	}
}

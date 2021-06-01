package sudoku;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class SelectDifficulty extends JPanel {
	static int windowWidth = SudokuWindow.windowWidth;
	static int windowHeight = SudokuWindow.windowHeight;
	public final int WINDOW_TYPE = 1;
	
	protected SelectDifficulty() {
		super();
		setLayout(null);
		
		
		JButton easyButton = new JButton("Easy");
		JButton normalButton = new JButton("Normal");
		JButton hardButton = new JButton("Hard");
		easyButton.setBounds(windowWidth/5, windowHeight*2/5, windowWidth*3/5, windowHeight/10);
		normalButton.setBounds(easyButton.getX(), easyButton.getY() + easyButton.getHeight() + windowHeight/30, easyButton.getWidth(), easyButton.getHeight());
		hardButton.setBounds(normalButton.getX(), normalButton.getY() + normalButton.getHeight()+windowHeight/30, normalButton.getWidth(), normalButton.getHeight());
		Listener listener = new Listener();
		easyButton.addActionListener(listener);
		normalButton.addActionListener(listener);
		hardButton.addActionListener(listener);
		
		add(new StringPanel());
		add(easyButton);
		add(normalButton);
		add(hardButton);
		add(new ButtonPanel(WINDOW_TYPE));
	}
	
	public void paintComponent(Graphics g) {
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
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			FontMetrics fm = g2d.getFontMetrics(stringFont);
			int ascent = fm.getAscent();
			int stringHeight = fm.getHeight();
			int stringWidth = g2d.getFontMetrics().stringWidth("Select");
			g2d.setStroke(new BasicStroke(5));
			
			g2d.drawString("Select", (width-stringWidth)/2, ascent);
			stringWidth = g2d.getFontMetrics().stringWidth("Difficulty");
			g2d.drawString("Difficulty", (width-stringWidth)/2, ascent + stringHeight);
			g2d.dispose();
		}
	}
	
	protected static class Listener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			String cmd = evt.getActionCommand(); 					
			SudokuWindow.window.getContentPane().removeAll();
			switch(cmd) {
				case "Hard": SudokuWindow.contentPane = new SelectPuzzle(SudokuEngine.Difficulty.HARD, 1); break;
				case "Normal": SudokuWindow.contentPane = new SelectPuzzle(SudokuEngine.Difficulty.NORMAL, 1); break;
				case "Easy": SudokuWindow.contentPane = new SelectPuzzle(SudokuEngine.Difficulty.EASY, 1); break;
			}
			SudokuWindow.window.add(SudokuWindow.contentPane);
			SudokuWindow.window.setVisible(true);
		}
	}
}


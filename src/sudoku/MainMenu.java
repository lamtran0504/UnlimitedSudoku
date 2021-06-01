package sudoku;

import java.awt.*;
import javax.swing.*;

public class MainMenu extends JPanel {
	static int windowWidth = SudokuWindow.windowWidth;
	static int windowHeight = SudokuWindow.windowHeight;
	public final int WINDOW_TYPE = 0;
	
	public MainMenu() {
		super();
		setLayout(null);
		
		add(new GameTitlePanel());
		
		ButtonListener listener = new ButtonListener();
		JButton playButton = new JButton("Play");
		JButton achievementsButton = new JButton("Achievements");
		JButton quitButton = new JButton("Quit");
		playButton.addActionListener(listener);
		achievementsButton.addActionListener(listener);
		quitButton.addActionListener(listener);
		playButton.setBounds(windowWidth/5, windowHeight*2/5, windowWidth*3/5, windowHeight/10);
		achievementsButton.setBounds(playButton.getX(), playButton.getY() + playButton.getHeight() + windowHeight/30, playButton.getWidth(), playButton.getHeight());
		quitButton.setBounds(playButton.getX(), playButton.getY() + playButton.getHeight()*2 + windowHeight/15, playButton.getWidth(), playButton.getHeight());
		
		repaint();
		add(playButton);
		add(achievementsButton);	
		add(quitButton);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(SudokuWindow.backgroundImage, 0, 0, null);
		g2d.setColor(new Color(180, 180, 180, 200));
		g2d.fillRect(0, 0, windowWidth, windowHeight);
	}
	
	private static class GameTitlePanel extends JPanel {		
		GameTitlePanel() {
			setBounds(0, windowHeight/20, windowWidth, windowHeight/4);	
		}
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			int width = this.getWidth();
			Font stringFont = new Font("Monospaced", Font.BOLD, width/7);
			g2d.setFont(stringFont);
			g2d.setColor(Color.BLACK);
			
			FontMetrics fm = g2d.getFontMetrics(stringFont);
			int ascent = fm.getAscent();
			int stringHeight = fm.getHeight();
			int stringWidth = g2d.getFontMetrics().stringWidth("Unlimited");
			int stringWidth2 = g2d.getFontMetrics().stringWidth("Sudoku");
			g2d.setStroke(new BasicStroke(5));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.drawString("Unlimited", (width-stringWidth)/2, ascent);
			g2d.drawString("Sudoku", (width-stringWidth2)/2, ascent+stringHeight);
			g2d.dispose();
		}
	}
}

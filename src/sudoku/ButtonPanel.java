package sudoku;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {
	private BufferedImage exitImg, homeImg, eraseImg;
	private int btnSize;
	public final int WINDOW_TYPE;
	public ButtonPanel(int WINDOW_TYPE) {		
		this.WINDOW_TYPE = WINDOW_TYPE;
		exitImg = GameResources.getBufferedImage("Resources\\Images\\Exit.png");
		homeImg = GameResources.getBufferedImage("Resources\\Images\\Home.png");
		btnSize = exitImg.getWidth();
		
		setLocation(0, SudokuWindow.windowHeight*5/6);
		setSize(SudokuWindow.windowWidth - 6, btnSize);
		addMouseListener(ButtonListener.NavigationListener);
		
		if (WINDOW_TYPE >= 100) {
			eraseImg = GameResources.getBufferedImage("Resources\\Images\\Erase.png");
		}
	}
	
	protected void paintComponent(Graphics g) {
		g.drawImage(exitImg, SudokuWindow.windowWidth - btnSize - 21, 0, null);
		g.drawImage(homeImg, SudokuWindow.windowWidth - 2*btnSize - 36, 0, null);
		g.drawImage(eraseImg, SudokuWindow.windowWidth - 3*btnSize - 51, 0, null);
	}

}


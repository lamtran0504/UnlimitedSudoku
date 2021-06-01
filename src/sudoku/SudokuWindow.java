package sudoku;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


public class SudokuWindow extends JFrame {
	public static int windowWidth = 400;
	public static int windowHeight = 640;
	public static JPanel contentPane = new JPanel();
	public static SudokuWindow window = new SudokuWindow();
	public static BufferedImage backgroundImage
		= GameResources.scale(GameResources.getBufferedImage("resources\\images\\Background.bmp"), windowWidth, windowHeight);
	public static BufferedImage backgroundImageARGB = new BufferedImage(windowWidth,windowHeight, BufferedImage.TYPE_INT_ARGB);
	
	public static Player player;
	
	public static void main(String[] args) {
		player = new Player();
		player.loadTimeRecord();
		contentPane = new MainMenu();
		window.add(contentPane);	
		window.setVisible(true);	
	}
	
	
	protected static class homeListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			window.getContentPane().removeAll();
			contentPane = new MainMenu();
			window.add(contentPane);
			window.setVisible(true);
		}
	}

	
	public SudokuWindow() {
		super("Unlimited Sudoku");
		setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - windowWidth)/2, (screenSize.height - windowHeight)/2);
		super.setSize(new Dimension(windowWidth, windowHeight));
	}
	
	
	public void changeScene(int WINDOW_TYPE) {
		try {
			Play playObj = (Play)SudokuWindow.contentPane;
			playObj.exit();
		}
		catch (Exception e) {
			
		}
		SudokuWindow.contentPane = null;
		getContentPane().removeAll();
		switch (WINDOW_TYPE) {
			case 0: contentPane = new MainMenu(); break;
			case 1: contentPane = new SelectDifficulty(); break;
			case 10: contentPane = new SelectPuzzle(SudokuEngine.Difficulty.EASY, 1); break;
			case 11: contentPane = new SelectPuzzle(SudokuEngine.Difficulty.EASY, 2); break;
			case 12: contentPane = new SelectPuzzle(SudokuEngine.Difficulty.EASY, 3); break;
			case 13: contentPane = new SelectPuzzle(SudokuEngine.Difficulty.EASY, 4); break;
			case 20: contentPane = new SelectPuzzle(SudokuEngine.Difficulty.NORMAL, 1); break;
			case 21: contentPane = new SelectPuzzle(SudokuEngine.Difficulty.NORMAL, 2); break;
			case 22: contentPane = new SelectPuzzle(SudokuEngine.Difficulty.NORMAL, 3); break;
			case 23: contentPane = new SelectPuzzle(SudokuEngine.Difficulty.NORMAL, 4); break;
			case 30: contentPane = new SelectPuzzle(SudokuEngine.Difficulty.HARD, 1); break;
			case 31: contentPane = new SelectPuzzle(SudokuEngine.Difficulty.HARD, 2); break;
			case 32: contentPane = new SelectPuzzle(SudokuEngine.Difficulty.HARD, 3); break;
			case 33: contentPane = new SelectPuzzle(SudokuEngine.Difficulty.HARD, 4); break;
		}
		add(SudokuWindow.contentPane);
		setVisible(true);
	}
}
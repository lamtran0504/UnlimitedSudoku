package sudoku;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import sudoku.SelectPuzzle.ChangePagePanel;
import sudoku.SelectPuzzle.PuzzleButton;

public class ButtonListener implements ActionListener {
	
	public void actionPerformed(ActionEvent evt) {
		String cmd = evt.getActionCommand();
		switch(cmd) {
			case "Play": 					
				SudokuWindow.window.getContentPane().removeAll();
				SudokuWindow.contentPane = new SelectDifficulty();
				SudokuWindow.window.add(SudokuWindow.contentPane);
				SudokuWindow.window.setVisible(true);
				break;
			case "Quit":
				System.exit(0);
		}
	}
	
	protected static MouseListener puzzleBtnListener = new MouseAdapter() {
		public void mousePressed(MouseEvent evt) {
			if (evt.getButton() == MouseEvent.BUTTON1) {
				Object source = evt.getSource();
				try {
					PuzzleButton puzzleButton = (PuzzleButton)source;
					SudokuWindow.window.getContentPane().removeAll();
					SudokuWindow.contentPane = new Play(puzzleButton.difficulty, puzzleButton.puzzleNumber);
					SudokuWindow.window.add(SudokuWindow.contentPane);
					SudokuWindow.window.setVisible(true);
				}
				catch (ClassCastException e) {
					return;
				}
			}
		}
	};
	
	protected static MouseListener ChangePageListener = new MouseAdapter() {
		public void mousePressed(MouseEvent evt) {
			if (evt.getButton() == MouseEvent.BUTTON1) {
				int x = evt.getX();
				int y = evt.getY();
				if (F(x, y) == 1) {
					ChangePagePanel p = (ChangePagePanel)evt.getSource();
					if (p.pageNumber == 1)
						return;
					SudokuWindow.window.getContentPane().removeAll();
					SudokuWindow.contentPane = new SelectPuzzle(p.difficulty, p.pageNumber - 1);
					SudokuWindow.window.add(SudokuWindow.contentPane);
					SudokuWindow.window.setVisible(true);
				}
					
				else if (F(x, y) == 2) {
					ChangePagePanel p = (ChangePagePanel)evt.getSource();
					if (p.pageNumber == 4)
						return;
					SudokuWindow.window.getContentPane().removeAll();
					SudokuWindow.contentPane = new SelectPuzzle(p.difficulty, p.pageNumber + 1);
					SudokuWindow.window.add(SudokuWindow.contentPane);
					SudokuWindow.window.setVisible(true);
				}
			}
		}
		
		private int F(int x, int y) {
			if (x < 120 && y > -(60f/115)*x + 62 && y < (60f/115)*x + 62)
				return 1;
			if (x > 220 && y < -(60f/115)*x + 240 && y > (60f/115)*x - 110)
				return 2;
			return 0;
		}
	};
	
	protected static MouseListener NavigationListener = new MouseAdapter() {
		public void mousePressed(MouseEvent evt) {
			if (evt.getButton() == MouseEvent.BUTTON1) {
				ButtonPanel p = (ButtonPanel) evt.getSource();
				int windowType = p.WINDOW_TYPE;
				int x = evt.getX();
				if (F(x) == 1) {
					if (windowType < 3)
						SudokuWindow.window.changeScene(windowType - 1);
					else 
						SudokuWindow.window.changeScene(windowType/10 + (windowType  % 100)/25);
				}
					
				else if (F(x) == 2) {
					System.out.println("Returning to Main Menu.");
					SudokuWindow.window.changeScene(0);
				}
				
				else if (windowType > 100 & F(x) == 3) {
					Play.board.reset();
					System.out.println("Resetting Board.");
				}
			}
		}
		
		private int F(int x) {
			if (x > SudokuWindow.windowWidth - 60)
				return 1;
			else if (x > SudokuWindow.windowWidth - 114 & x < SudokuWindow.windowWidth - 75)
				return 2;
			else if (x > SudokuWindow.windowWidth - 168 & x < SudokuWindow.windowWidth - 129)
				return 3;
			return 0;
		}
	};

}

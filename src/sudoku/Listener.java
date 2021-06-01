package sudoku;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;
import javax.swing.*;

public class Listener implements KeyListener, MouseListener {
	public static void main(String[] args) {
		JFrame window = new JFrame("Sudoku");
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(500, 500));
		Listener listener = new Listener();
		mainPanel.addKeyListener(listener);
		mainPanel.addMouseListener(listener);
		window.getContentPane().add((mainPanel));
		window.pack();
		window.setVisible(true);
	}
	
	public void mousePressed(MouseEvent evt) { }
	public void mouseReleased(MouseEvent evt) { }
	public void mouseClicked(MouseEvent evt) { }
	public void mouseEntered(MouseEvent evt) { }
	public void mouseExited(MouseEvent evt) { }
	
	public void keyPressed(KeyEvent evt) {
		int key = evt.getKeyCode();
		if (key == KeyEvent.VK_LEFT) { }
	}
	public void keyTyped(KeyEvent evt) { }
	public void keyReleased(KeyEvent evt) { }
}
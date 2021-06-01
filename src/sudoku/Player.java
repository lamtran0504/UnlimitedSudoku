package sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Player {
	public int[][] timeRecord;
	
	public void updateTimeRecord(int[][] timeRecord) {
		File outputStream = new File("Time Record");
		try {
			PrintWriter printWriter = new PrintWriter(outputStream);
			for (int d = 0; d < 3; d++) {
				for (int p = 0; p < 100; p++) {
					printWriter.print(timeRecord[d][p] + " ");
				}
				printWriter.println();
			}
			printWriter.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Time Data File Not Found.");
		};
	}
	
	public void updateTimeRecord(int levelIndex, int time) {
		int diff = levelIndex/100 - 1;
		int puzzleNum = levelIndex % 100;
		timeRecord[diff][puzzleNum] = time;
		File outputStream = new File("Time Record");
		try {
			PrintWriter printWriter = new PrintWriter(outputStream);
			for (int d = 0; d < 3; d++) {
				for (int p = 0; p < 100; p++) {
					printWriter.print(timeRecord[d][p] + " ");
				}
				printWriter.println();
			}
			printWriter.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Time Data File Not Found.");
		};
	}
	
	public void loadTimeRecord() {
		int[][] timeRecord = new int[3][100];
		File inputStream = new File("Time Record");
		try {
			Scanner scanner =  new Scanner(inputStream);
			for (int d = 0; d < 3; d++) {
				for (int p = 0; p < 100; p++) {
					if (scanner.hasNext())
						timeRecord[d][p] = scanner.nextInt();
					else
						System.out.println("Player Data File Compromised or not yet Initialized.");
				}
			}
			scanner.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Time Data File Not Found.");
			System.out.println("Failed to Load Time Record.");
		}
		this.timeRecord = timeRecord;
	}
	
	public int getFastestTime(SudokuEngine.Difficulty difficulty, int puzzleNum) {
		int index = 0;
		int time = -1;
		switch (difficulty) {
			case EASY: break;
			case NORMAL: index += 100; break;
			case HARD: index += 200; break;
		}
		index += puzzleNum - 1;
		
		File inputStream = new File("Time Record");
		try {
			Scanner scanner =  new Scanner(inputStream);
			for (int i = 0; i <= index; i++) {
				if (scanner.hasNext())
					time = scanner.nextInt();
				else
					System.out.println("Time Record Data Compromised or Not Yet Initialized.");
			}
			scanner.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Time Data File Not Found.");
			System.out.println("Failed to Load Time Record.");
		}
		return time;
	}
}

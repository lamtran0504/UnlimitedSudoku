package sudoku;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class SudokuEngine {
	public static void main(String[] args) {
		File outputFile = new File("Hard 2");
		int[][] cell;
		int puzzleCount = 0;
		SudokuEngine sudokuEngine = new SudokuEngine();
		try { 
			PrintWriter puzzleWriter = new PrintWriter(outputFile);
			String puzzleString = "";
			for (int puzzleNum = 0; puzzleNum < 100; puzzleNum++) {
				cell = sudokuEngine.generate(Difficulty.HARD);
				for (int pos = 0; pos < 81; pos++) {
					puzzleString += String.valueOf(cell[row(pos)][col(pos)]);
				}
				puzzleWriter.println(puzzleString);
				puzzleString = "";
				puzzleCount++;
				System.out.println(puzzleCount);
			}
			puzzleWriter.close();
		}	
		catch (FileNotFoundException e){
			System.out.println("Destination File Not Found");
		}		
	} // End of main()
	
	protected static enum Difficulty {EASY, NORMAL, HARD};
	
	public int[][] generate(Difficulty difficulty) {
		double coveredCell = 0;
		int[] availableNum;
		int[][] cellValue;
		while (true) {
			cellValue = new int[9][9];
			int currentPos = 0;
			int[][] testedNum = new int[81][10];
			while (currentPos < 81) { 
				availableNum = availableNum(cellValue, testedNum[currentPos], row(currentPos), col(currentPos));					
				if (availableNum.length > 0) {
					cellValue[row(currentPos)][col(currentPos)] = random(availableNum);		// randomly insert 1 possible value to currentPos	
					currentPos++;
				}
				else {
					testedNum[currentPos] = new int[10];
					currentPos--;
					testedNum[currentPos][cellValue[row(currentPos)][col(currentPos)]] = cellValue[row(currentPos)][col(currentPos)];
					cellValue[row(currentPos)][col(currentPos)] = 0; 
				}	
			}; // End of while pos < 81
			switch (difficulty) {
				case EASY: 		coveredCell = 30; break;
				case NORMAL: 	coveredCell = 45; break;
				case HARD: 		coveredCell = 52; break;
			}
			
			for (int pos = 0; pos < 81; pos++) {
				if (Math.random() < coveredCell/(81-pos)) {
					cellValue[row(pos)][col(pos)] = 0;
					coveredCell--;
				}
			}
			if (hasOneSolution(cellValue)) {
				break;
			}
		} // end of while true
		
		return cellValue;
		} // End of generate()
	
	/**
	 * 
	 * @param 	difficulty
	 * @return 	int[9][9]		Data for a new puzzle with specified difficulty.
	 * 							If a cell is blank, the number at the cell's coordinate is 0.
	 */
	protected int[][] getNewPuzzle(Difficulty difficulty) {
		SudokuEngine SudokuEngine = new SudokuEngine();
		return SudokuEngine.generate(difficulty);
	}
	
	/**
	 * 
	 * @param 	difficulty		EASY, NORMAL, or HARD. 
	 * @param 	puzzleNum		
	 * @return	int[9][9]		Data for the puzzle specified by difficulty and puzzleNum.
	 * 							If a cell is blank, the number at the cell's coordinate is 0.
	 */
	protected int[][] getNewPuzzle(Difficulty difficulty, int puzzleNumber) {
		int[][] puzzle = new int[9][9];
		String inputFile;
		String puzzleString = "";
		switch (difficulty) {
			case EASY: inputFile = "Easy"; break;
			case NORMAL: inputFile = "Normal"; break;
			case HARD: inputFile = "Hard"; break;
			default: inputFile = "Normal"; break;
		}
		
		File file = new File(inputFile);
		try {
			Scanner puzzleInput = new Scanner(file);
			for (int i = 0; i < puzzleNumber; i++) {
				puzzleString = puzzleInput.nextLine();
			}
			puzzleInput.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Puzzle Data File Not Found");
		}
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				puzzle[row][col] = Integer.parseInt(puzzleString.substring(row*9+col, row*9+col+1));
			}
		}
		return puzzle;
	}
	 
	/**
	 * 
	 * @param puzzle	A 2D array of type int[][] that holds the data for a sudoku puzzle
	 * @return			A 2D array of type int[][] that holds the data for the solved sudoku puzzle
	 */
	protected static int[][] solve(int[][] puzzle) {
		int[][] coveredCellCoord = new int[81][2];			
		int coveredPos;
		int coveredCellCount = 0;
		int testedDigit;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (puzzle[row][col] == 0) {
					coveredCellCoord[coveredCellCount] = new int[] {row, col};
					coveredCellCount++;
				}
			}
		}
		int[][] testedNum = new int[coveredCellCount][10];
		coveredPos= 0;
		int[] availableNum;
		while (coveredPos < coveredCellCount) {
			availableNum = availableNum(puzzle, testedNum[coveredPos], coveredCellCoord[coveredPos][0], coveredCellCoord[coveredPos][1]);
			if (availableNum.length > 0){
				puzzle[coveredCellCoord[coveredPos][0]][coveredCellCoord[coveredPos][1]] = availableNum[0];
				coveredPos++;
			}
			else {
				if (coveredPos != 0) {
					testedNum[coveredPos] = new int[10];
					coveredPos--;
					testedDigit = puzzle[coveredCellCoord[coveredPos][0]][coveredCellCoord[coveredPos][1]];
					testedNum[coveredPos][testedDigit] = testedDigit;
					puzzle[coveredCellCoord[coveredPos][0]][coveredCellCoord[coveredPos][1]] = 0;
				}
				else {
					System.out.println("There is no solution to the puzzle");
					break;
				}
			}
		}
		return puzzle;
	}
	
	protected boolean hasOneSolution(int[][] puzzle) {
		int solutionCount = 0;
		int[][] coveredCellCoord = new int[81][2];			
		int coveredPos;
		int coveredCellCount = 0;
		int testedDigit;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (puzzle[row][col] == 0) {
					coveredCellCoord[coveredCellCount] = new int[] {row, col};
					coveredCellCount++;
				}
			}
		}
		int[][] testedNum = new int[coveredCellCount][10];
		int[] availableNum;
		coveredPos = 0;
		while (coveredPos >= 0) {
			
			if (coveredPos >= coveredCellCount) {
				solutionCount++;
				if (solutionCount > 1) {
					return false;
				}
				coveredPos--;
				testedDigit = puzzle[coveredCellCoord[coveredPos][0]][coveredCellCoord[coveredPos][1]];
				testedNum[coveredPos][testedDigit] = testedDigit;
				puzzle[coveredCellCoord[coveredPos][0]][coveredCellCoord[coveredPos][1]] = 0;
			}
			else {
				availableNum = availableNum(puzzle, testedNum[coveredPos], coveredCellCoord[coveredPos][0], coveredCellCoord[coveredPos][1]);
				if (availableNum.length > 0){
					puzzle[coveredCellCoord[coveredPos][0]][coveredCellCoord[coveredPos][1]] = availableNum[0];
					coveredPos++;
					}
				else {
					if (coveredPos != 0) {
						testedNum[coveredPos] = new int[10];
						coveredPos--;
						testedDigit = puzzle[coveredCellCoord[coveredPos][0]][coveredCellCoord[coveredPos][1]];
						testedNum[coveredPos][testedDigit] = testedDigit;
						puzzle[coveredCellCoord[coveredPos][0]][coveredCellCoord[coveredPos][1]] = 0;
					}
					else
						coveredPos--;
				}
			}
		}
		if (solutionCount == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param cellValue		2D array of type int[][] that holds the data for a sudoku puzzle
	 * @param row			specifies the cell
	 * @param col			specifies the cell
	 * @return				An array of int[10] that specifies the available numbers for the cell,
	 * 						having crossed out numbers in the same row, column, and 3x3 area.
	 * 						
	 */
	private static int[] availableNum(int[][] cellValue, int[] testedNum, int row, int col) {						
		int[] list = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		for (int i = 0; i < 9; i++) {
			list[cellValue[i][col]] = 0;
		}
		for (int i = 0; i < 9; i++) {
			list[cellValue[row][i]] = 0;
		}
		int areaY = row/3, areaX = col/3;
		for (int i = 3*areaY; i < 3*areaY + 3; i++) {
			for (int k = 3*areaX; k < 3*areaX + 3; k++) {
				list[cellValue[i][k]] = 0;
			}
		}
		for (int digit : testedNum) {
			list[digit] = 0;
		}
		int counter = 0;
		for (int i = 0; i < 10; i++) {
			if (list[i] != 0) {
				counter++;
			}
		}
		int[] availableNum = new int[counter];
		int counter2 = 0;
		for (int i = 0; i < 10; i++) {
			if (list[i] != 0) {
				availableNum[counter2] = list[i];
				counter2++;
			}
		}
		return availableNum;
	} // End of availabeNum();
	
	private static int row(int pos) {
		return pos/9;
	}
	private static int col(int pos) {
		return pos%9;
	}
	
	/**
	 * 
	 * @param list	any array of type int[]
	 * @return		a random int item in the array
	 */
	private static int random(int[] list) {
		if (list.length == 0) {
			return -1;
		}
		return list[(int)(Math.random()*list.length)];
	} // End of random(int[])
} // End of class SudokuEngine



package sudoku;

import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PlayerDataManager {
	
	static void updateTimeRecord(int[][] timeRecord) {
		File outputStream = new File("");
		try {
			PrintWriter printWriter = new PrintWriter(outputStream);
			for (int d = 0; d < 4; d++) {
				for (int p = 0; p < 100; p++) {
					printWriter.print(timeRecord[d][p] + " ");
				}
				printWriter.println();
			}
			printWriter.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Time Data File Not Found");
		};
	}
	
	static int[][] getTimeRecord() {
		int[][] timeRecord = new int[4][100];
		File inputStream = new File("");
		try {
			Scanner scanner =  new Scanner(inputStream);
			
		}
		catch (FileNotFoundException e) {
			System.out.println("Time Data File Not Found");
		}
		return timeRecord;
	}
	
	OutputStream outputStream = System.out;
	PrintWriter printWriter = new PrintWriter(outputStream);
	
	static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
        
        public long nextLong() {
        	return Long.parseLong(next());
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

package test;

import java.util.Random;
import java.util.Scanner;

public class Life {

	
	public static void main(String[] a){
		Scanner in = new Scanner (System.in);
		int rows = in.nextInt();
		int cols = in.nextInt();
		long seed = in.nextLong();
		
		int birthLow = in.nextInt();
		int birthHigh = in.nextInt();
		int liveLow = in.nextInt();
		int liveHigh = in.nextInt();
		
		in.close();
		boolean[][] matrix = populateMatrix(rows, cols, seed);
		for (int i=0; i<5; ++i){
			printMatrix(matrix);
			System.out.println();
			matrix = updateMatrix(matrix, birthLow, birthHigh, liveLow, liveHigh);
			
		}
		
	}
	
	public static boolean[][] populateMatrix(int rows, int cols, long seed){
		boolean[][] matrix = new boolean[rows][cols];
		Random rand = new Random(seed);
		for (int r=1; r<rows-1; ++r)
			for (int c=1; c<cols-1; ++c)
				matrix[r][c] = rand.nextBoolean();
		return matrix;
	}
	
	public static boolean[][] updateMatrix(boolean[][] m, int birthLow, int birthHigh, int liveLow, int liveHigh){
		boolean[][] clone = cloneMatrix(m);
		for (int row=1; row<clone.length-1; ++row){
			for (int col=1; col<clone[0].length-1; ++col){
				if (m[row][col]){
					int count = countSymbolAtPosition(m, row, col, true);
					clone[row][col] = (count > liveLow && count < liveHigh) ? true : false; 
				} else {
					int count = countSymbolAtPosition(m, row, col, false);
					clone[row][col] = (count > birthLow && count < birthHigh) ? true : false;
				}
			}
		}
		
		return clone;
	}
	
	// if # then pass true, if - then pass false for symbol
	public static int countSymbolAtPosition(boolean[][] m, int r, int c, boolean symbol){
		int count = 0;
		for (int i=r-1; i<r+1; ++i){
			for (int j=c-1; j<c+1; ++j){
				count += m[i][j]==symbol ? 1 : 0;
			}
		}
		return count;
	}
	
	public static boolean[][] cloneMatrix(boolean[][] myMatrix){
		boolean[][] myNewMatrix = myMatrix.clone();
		for (int row = 0; row < myMatrix.length; ++row) {
		  myNewMatrix[row] = myMatrix[row].clone();
		}
		
		return myNewMatrix;
	}
	
	public static void printMatrix(boolean[][] m){
		for (int i=0; i<m.length; ++i){
			for (int j=0; j<m[0].length; ++j){
				System.out.print(m[i][j] ? "# " : "- ");
			}
			System.out.println();
		}
	}
}

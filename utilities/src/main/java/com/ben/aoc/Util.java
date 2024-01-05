package com.ben.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Util {
	
	public static List<String> readFile(Class<?> klass, String fileName){
		List<String> lines = null ;
		try {
			Path path = Paths.get(klass.getClassLoader().getResource(fileName).toURI());

			lines = Files.lines(path).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	public static char[][] transposeMatrix(char[][] matrix){
		char[][] result = new char[matrix[0].length][matrix.length];
		for(int i = 0; i<matrix.length; i++) {
			for(int j = 0; j<matrix[0].length; j++) {
				result[j][i] = matrix[i][j];
			}
		}
		return result;
	}
	
	public static int[][] transposeMatrix(int[][] matrix){
		int[][] result = new int[matrix[0].length][matrix.length];
		for(int i = 0; i<matrix.length; i++) {
			for(int j = 0; j<matrix[0].length; j++) {
				result[j][i] = matrix[i][j];
			}
		}
		return result;
	}
	
	public static long[][] transposeMatrix(long[][] matrix){
		long[][] result = new long[matrix[0].length][matrix.length];
		for(int i = 0; i<matrix.length; i++) {
			for(int j = 0; j<matrix[0].length; j++) {
				result[j][i] = matrix[i][j];
			}
		}
		return result;
	}
	
	public static Object[][] transposeMatrix(Object[][] matrix){
		Object[][] result = new Object[matrix[0].length][matrix.length];
		for(int i = 0; i<matrix.length; i++) {
			for(int j = 0; j<matrix[0].length; j++) {
				result[j][i] = matrix[i][j];
			}
		}
		return result;
	}
	
	/**
	 * [a,b] -> [c,a]
	 * [c,d]    [d,b]
	 * @param matrix
	 * @return
	 */
	public static char[][] rotateMatrix(char[][] matrix){
		char[][] result = new char[matrix[0].length][matrix.length];
		for(int i = 0; i<matrix.length; i++) {
			for(int j = 0; j<matrix[0].length; j++) {
				result[j][(matrix.length-1) -i] = matrix[i][j];
			}
		}
		return result;
	}
	
	
	public static int[][] rotateMatrix(int[][] matrix){
		int[][] result = new int[matrix[0].length][matrix.length];
		for(int i = 0; i<matrix.length; i++) {
			for(int j = 0; j<matrix[0].length; j++) {
				result[j][(matrix.length-1) -i] = matrix[i][j];
			}
		}
		return result;
	}
	
	public static long[][] rotateMatrix(long[][] matrix){
		long[][] result = new long[matrix[0].length][matrix.length];
		for(int i = 0; i<matrix.length; i++) {
			for(int j = 0; j<matrix[0].length; j++) {
				result[j][(matrix.length-1) -i] = matrix[i][j];
			}
		}
		return result;
	}
	
	public static Object[][] rotateMatrix(Object[][] matrix){
		Object[][] result = new Object[matrix[0].length][matrix.length];
		for(int i = 0; i<matrix.length; i++) {
			for(int j = 0; j<matrix[0].length; j++) {
				result[j][(matrix.length-1) -i] = matrix[i][j];
			}
		}
		return result;
	}
	
	/**
	 * [a,b] -> [d,c]
	 * [c,d]    [b,a]
	 * @param matrix
	 * @return
	 */
	public static char[][]rotateMatrixTwice(char[][] matrix){
		char[][] result = new char[matrix.length][matrix[0].length];
		for(int i = 0; i<matrix.length; i++) {
			for (int j = 0; j<matrix[0].length; j++) {
				result[(matrix.length-1) -i][(matrix[0].length-1 -j)] = matrix[i][j];
			}
		}
		return result;
	}
	
	/**
	 * [a,b] -> [b,d]
	 * [c,d]    [a,c]
	 * @param matrix
	 * @return
	 */
	public static char[][]rotateMatrixAntiClock(char[][] matrix){
		char[][] result = new char[matrix[0].length][matrix.length];
		for(int i = 0; i<matrix.length; i++) {
			for (int j = 0; j<matrix[0].length; j++) {
				result[(matrix[0].length-1) -j][i] = matrix[i][j];
			}
		}
		return result;
	}
	
	public static char[][]flipMatrixNS(char[][] matrix){
		char[][] result = new char[matrix.length][matrix[0].length];
		for(int i = 0; i<matrix.length; i++) {
			for(int j = 0; j<matrix[0].length; j++) {
				result[(matrix.length-1)-i][j] = matrix[i][j];
			}
		}
		return result;
	}
	
	public static char[][]flipMatrixEW(char[][] matrix){
		char[][] result = new char[matrix.length][matrix[0].length];
		for(int i = 0; i<matrix.length; i++) {
			for(int j = 0; j<matrix[0].length; j++) {
				result[i][(matrix[0].length-1) -j] = matrix[i][j];
			}
		}
		return result;
	}
	
	public static void printMatrix(char[][] matrix) {
		for(int i = 0; i<matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}
	}
	
	public static void printMatrix(int[][] matrix) {
		for(int i = 0; i<matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}
	}
	
	public static void printMatrix(long[][] matrix) {
		for(int i = 0; i<matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}
	}
	
	public static void printMatrix(Object[][] matrix) {
		for(int i = 0; i<matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}
	}
}

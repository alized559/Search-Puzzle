import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SearchPuzzle {
  public static void main(String[] args) throws FileNotFoundException {
    Scanner input = new Scanner(System.in);
    System.out.print("Please enter a word to search: ");
    char[][] grid = createPuzzle("grid.txt");
    int n = grid.length, m = grid[0].length;
    boolean running = true;
    while (running) {
      String word = input.next();
      System.out.println("Puzzle : ");
      int[] result = search(grid, n, m, word);
      printGrid(grid);
      if (result[0] == -1) {
        System.out.println(word + " not found.");
      } else {
        if (result[2] == 1) {
          System.out.println(word + " found: row " + (result[0] + 1) + ", column "
                             + (result[1] + 1) + ", " + "going right");
        }
        if (result[2] == 0) {
          System.out.println(word + " found: row " + (result[0] + 1) + ", column "
                             + (result[1] + 1) + ", " + "going down");
        }
        if (result[2] == -2) {
          System.out.println(word + " found: row " + (result[0] + 1) + ", column "
                             + (result[1] + 1) + ", " + "going left");
        }
        if (result[2] == 2) {
          System.out.println(word + " found: row " + (result[0] + 1) + ", column "
                             + (result[1] + 1) + ", " + "going up");
        }
        System.out.println("\nDo you want to continue? (enter # to stop): ");
        String c = input.next();
        if (c.equalsIgnoreCase("#")) {
          running = false;
        } else {
          System.out.print("Please enter a word to search: ");
        }
      }
    }
  }

  /*
   * This function creates and fills the puzzle read from file
   * @param file  A string that represents grid file name
   * @return 2D array represeting the grid
  */
  public static char[][] createPuzzle(String file)
             throws FileNotFoundException {
    Scanner readFile = new Scanner(new File("grid.txt"));
    int n = readFile.nextInt(), m = readFile.nextInt();
    char[][] grid = new char[n][m];
    for (int i = 0; i < n; i++) {
      String line = readFile.next();
      for (int j = 0; j < m; j++) {
        grid[i][j] = line.charAt(j);
      }
    }
    return grid;
  }

  /*
   * Prints the grid
   * @param grid  2D array representing the grid
  */
  public static void printGrid(char[][] grid) {
    for (int i = 0; i < grid.length; ++i) {
      for (int j = 0; j < grid[i].length; ++j) {
        if (j > 0)
          System.out.print(' ');
        System.out.print(grid[i][j]);
      }
      System.out.println();
    }
  }

  /*
   * Search for a word in the right direction
   * @param grid  A 2D array representing grid
   * @param i     An integer for row
   * @param j     An integer for column
   * @param m     An integer representing the number of columns
   * @param word  A string for the word to search for
   * @return true whether word is found in the grid otherwise false
  */
  public static boolean searchRight(char[][] grid, int i, int j, int m,
                                    String word) {
    // if remaning chars less than word length, not possible to find it
    if ((m - j) < word.length()) {
      return false;
    }
    int index = 0;
    for (int k = j; k < m && index < word.length(); k++) {
      if (grid[i][k] != word.charAt(index))
        return false;
      index++;
    }
    return true;
  }

	/*
   * Search for a word in the left direction
   * @param grid  A 2D array representing grid
   * @param i     An integer for row
   * @param j     An integer for column
   * @param n     An integer representing the number of rows
   * @param word  A string for the word to search for
   * @return true whether word is found in the grid otherwise false
  */
  public static boolean searchLeft(char[][] grid, int i, int j, int m, String word) {
  	// when going left from position (i, j), you can only read j characters
    if (j < word.length()) {
      return false;
    }
    int index = 0;
    for (int k = j; k >= 0 && index < word.length(); k--) {
      if (grid[i][k] != word.charAt(index))
        return false;
      index++;
    }
    return true;
  }

  /*
   * Search for a word in the downward direction
   * @param grid  A 2D array representing grid
   * @param i     An integer for row
   * @param j     An integer for column
   * @param n     An integer representing the number of rows
   * @param word  A string for the word to search for
   * @return true whether word is found in the grid otherwise false
  */
  public static boolean searchDown(char[][] grid, int i, int j, int n,
                                   String word) {
    // if remaning chars less than word length, not possible to find it
    if ((n - i) < word.length()) {
      return false;
    }
    int index = 0;
    for (int k = i; k < n && index < word.length(); k++) {
      if (grid[k][j] != word.charAt(index))
        return false;
      index++;
    }
    return true;
  }

	/*
   * Search for a word in the upward direction
   * @param grid  A 2D array representing grid
   * @param i     An integer for row
   * @param j     An integer for column
   * @param n     An integer representing the number of rows
   * @param word  A string for the word to search for
   * @return true whether word is found in the grid otherwise false
  */
  public static boolean searchUp(char[][] grid, int i, int j, int n,
                                 String word) {
    // when going up from position (i, j), you can only read i characters
    if (i < word.length()) {
      return false;
    }
    int index = 0;
    for (int k = i; k >= 0 && index < word.length(); k--) {
      if (grid[k][j] != word.charAt(index))
        return false;
      index++;
    }
    return true;
  }

  /*
   * Search for a word in the grid
   * @param grid  A 2D array representing grid
   * @param n     An integer representing the number of rows
   * @param m     An integer representing the number of columns
   * @param word  A string for the word to search for
   * @return An array storing the row, column, and direction if word found.
  */
  public static int[] search(char[][] grid, int n, int m, String word) {
    int[] result = new int[3]; // save here row, col, direction
    result[0] = -1; // used to indicate word not found
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] != word.charAt(0))
          continue;
        if (searchRight(grid, i, j, m, word)) {
            result[0] = i;
            result[1] = j;
            result[2] = 1;
            return result;
        }
        if (searchDown(grid, i, j, n, word)) {
            result[0] = i;
            result[1] = j;
            result[2] = 0;
            return result;
        }
        if (searchUp(grid, i, j, n, word)) {
					result[0] = i;
					result[1] = j;
					result[2] = 2;
					return result;
				}
				if (searchLeft(grid, i, j, m, word)) {
					result[0] = i;
					result[1] = j;
					result[2] = -2;
					return result;
				}
      }
    }
    return result;
  }
}

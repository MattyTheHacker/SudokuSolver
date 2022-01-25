import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.sqrt;

public class SudokuSolver {
    private static final int GRID_SIZE = 9;

    public static void main(String[] args) {
        int[][] board = {
                {0, 7, 0, 0, 0, 4, 0, 0, 0},
                {1, 2, 6, 0, 9, 5, 0, 0, 0},
                {3, 4, 0, 6, 0, 0, 5, 0, 0},
                {0, 8, 2, 0, 5, 0, 9, 0, 1},
                {0, 0, 1, 2, 0, 0, 0, 0, 0},
                {4, 5, 3, 9, 6, 1, 0, 0, 0},
                {8, 3, 7, 5, 0, 0, 0, 4, 6},
                {0, 0, 4, 0, 0, 0, 0, 5, 0},
                {6, 1, 0, 7, 4, 0, 8, 0, 3}
        };
        recursiveSolveBoard(board);
        String b1 = Arrays.deepToString(board);
        smartSolveBoard(board);
        String b2 = Arrays.deepToString(board);
        boolean identical = b1.equals(b2);
        printBoard(board);
        if (!identical) {
            System.out.println("[WARN] The solution provided by the two methods did not match.");
        } else {
            System.out.println("[INFO] The solutions provided by the two methods were identical.");
        }
    }

    public static void printBoard(int[][] board) {
        for (int i = 0; i < 9; i++) {
            System.out.print("\n");
            if (i % 3 == 0)
                System.out.print("\n");
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0)
                    System.out.print(" ");
                if (board[i][j] == 0)
                    System.out.print(". ");
                if (board[i][j] == 1)
                    System.out.print("1 ");
                if (board[i][j] == 2)
                    System.out.print("2 ");
                if (board[i][j] == 3)
                    System.out.print("3 ");
                if (board[i][j] == 4)
                    System.out.print("4 ");
                if (board[i][j] == 5)
                    System.out.print("5 ");
                if (board[i][j] == 6)
                    System.out.print("6 ");
                if (board[i][j] == 7)
                    System.out.print("7 ");
                if (board[i][j] == 8)
                    System.out.print("8 ");
                if (board[i][j] == 9)
                    System.out.print("9 ");
            }
        }
        System.out.println();
    }

    private static boolean existsInRow(int[][] board, int numberToCheck, int row) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == numberToCheck) {
                return true;
            }
        }
        return false;
    }

    private static boolean existsInColumn(int[][] board, int numberToCheck, int column) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][column] == numberToCheck) {
                return true;
            }
        }
        return false;
    }

    private static boolean existsInBox(int[][] board, int numberToCheck, int row, int column) {
        int boxRow = (int) (row - (row % (sqrt(GRID_SIZE))));
        int boxColumn = (int) (column - (column % (sqrt(GRID_SIZE))));

        for (int i = boxRow; i < boxRow + sqrt(GRID_SIZE); i++) {
            for (int j = boxColumn; j < boxColumn + sqrt(GRID_SIZE); j++) {
                if (board[i][j] == numberToCheck) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isValidLocation(int[][] board, int numberToCheck, int row, int column) {
        return !existsInRow(board, numberToCheck, row) &&
                !existsInColumn(board, numberToCheck, column) &&
                !existsInBox(board, numberToCheck, row, column);
    }

    private static void smartSolveBoard(int[][] board) {
        boolean solved = false;
        ArrayList<Integer> options = new ArrayList<>();
        while (!solved) {
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    if (board[row][col] == 0) {
                        for (int numToCheck = 1; numToCheck <= GRID_SIZE; numToCheck++) {
                            if (isValidLocation(board, numToCheck, row, col)) {
                                options.add(numToCheck);
                            }
                        }
                        if (options.size() == 1) {
                            board[row][col] = options.get(0);
                        }
                        options = new ArrayList<>();
                    }
                }
            }
            solved = true;
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    if (board[row][col] == 0) {
                        solved = false;
                        break;
                    }
                }
            }
        }
    }

    private static boolean recursiveSolveBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                if (board[row][column] == 0) {
                    for (int numberToCheck = 1; numberToCheck <= GRID_SIZE; numberToCheck++) {
                        if (isValidLocation(board, numberToCheck, row, column)) {
                            board[row][column] = numberToCheck;
                            if (recursiveSolveBoard(board)) {
                                return true;
                            } else {
                                board[row][column] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}

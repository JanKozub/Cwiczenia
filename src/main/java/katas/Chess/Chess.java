package katas.Chess;
    /*
    Given two different positions on a chess board, find the least number of moves it would take a knight to get from one to the other.
    The positions will be passed as two arguments in algebraic notation. For example, knight("a3", "b5") should return 1.

The knight is not allowed to move off the board. The board is 8x8.

For information on knight moves, see https://en.wikipedia.org/wiki/Knight_%28chess%29

For information on algebraic notation, see https://en.wikipedia.org/wiki/Algebraic_notation_%28chess%29

(Warning: many of the tests were generated randomly. If any do not work, the test cases will return the input, output, and expected output; please post them.)
     */



/*
  > 0 - ilosc krokow DO danego punktu
  = 0 - pierwszy wezel
  = -1 - wezel nie odwiedzony
  = -2 - wezel do odwiedzenia

  ----------------------------------
 5 | 0 | 1 | 0 | 1 | 0 | 0 | 0 | 0 | 0
  ----------------------------------
 4 | - | 0 | 0 | 2 | - | 2 | 0 | 0 | 0
  ----------------------------------
 3 | 2 | 0 | X | 0 | - | 0 | 0 | 0 | 0
  ----------------------------------
 2 | - | - | 0 | 0 | - | 0 | 0 | 0 | 0
  ----------------------------------
 1 | 0 | - | 0 | - | 0 | 0 | 0 | 0 | 0
  ----------------------------------
     A       C

C3 -> F4

 */

import java.util.Arrays;

public class Chess {

    public static void main(String[] args) {
        System.out.println(knight("a1", "c1"));
    }

    public static int knight(String start, String finish) {

        int[] startCor = getPositionFromString(start);
        int[] finishCor = getPositionFromString(finish);

        return findBestPath(startCor, finishCor);
    }

    private static int findBestPath(int[] start, int[] finish) {

        int[][] chessboard = new int[8][8];
        for (int[] i : chessboard) {
            Arrays.fill(i, -1);
        }
        chessboard[start[0]][start[1]] = 0;

        int step = 0;
        while (true) {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    setFieldsValues(row, col, chessboard, step);
                }
            }

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if(chessboard[row][col] == step)
                        markNextSteps(row, col, chessboard);

                    if (chessboard[finish[0]][finish[1]] > 0) {
                        return chessboard[finish[0]][finish[1]];
                    }
                }
            }
            step = step + 1;
        }
    }

    private static void setFieldsValues(int row, int col, int[][] chessboard, int step) {
        if (isToGo(row, col, chessboard))
            chessboard[row][col] = step;
    }

    private static void markNextSteps(int row, int col, int[][] chessboard) {
        if (isValid(row - 2, col - 1) && isNotUsed(row - 2, col - 1, chessboard)) {
            chessboard[row - 2][col - 1] = -2;
        }
        if (isValid(row - 2, col + 1) && isNotUsed(row - 2, col + 1, chessboard)) {
            chessboard[row - 2][col + 1] = -2;
        }
        if (isValid(row - 1, col + 2) && isNotUsed(row - 1, col + 2, chessboard)) {
            chessboard[row - 1][col + 2] = -2;
        }
        if (isValid(row + 1, col + 2) && isNotUsed(row + 1, col + 2, chessboard)) {
            chessboard[row + 1][col + 2] = -2;
        }
        if (isValid(row + 2, col + 1) && isNotUsed(row + 2, col + 1, chessboard)) {
            chessboard[row + 2][col + 1] = -2;
        }
        if (isValid(row + 2, col - 1) && isNotUsed(row + 2, col - 1, chessboard)) {
            chessboard[row + 2][col - 1] = -2;
        }
        if (isValid(row - 1, col - 2) && isNotUsed(row - 1, col - 2, chessboard)) {
            chessboard[row - 1][col - 2] = -2;
        }
        if (isValid(row + 1, col - 2) && isNotUsed(row + 1, col - 2, chessboard)) {
            chessboard[row + 1][col - 2] = -2;
        }
    }

    private static int[] getPositionFromString(String s) {
        int col = (int)s.charAt(0) - (int)'a';
        int row = (int)s.charAt(1) - (int)'1';
        return new int[] {row, col};
    }

    private static boolean isNotUsed(int row, int col, int[][] chessboard) {
        return chessboard[row][col] == -1;
    }

    private static boolean isToGo(int row, int col, int[][] chessboard) {
        return chessboard[row][col] == -2;
    }

    private static boolean isValid(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

}
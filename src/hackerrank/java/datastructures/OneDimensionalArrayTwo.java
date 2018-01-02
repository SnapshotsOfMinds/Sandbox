package hackerrank.java.datastructures;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-1d-array/problem
 */
public class OneDimensionalArrayTwo {
    public static void main(String... args) {
        try (Scanner scan = new Scanner(System.in)) {
            int q = scan.nextInt();
            while (q-- > 0) {
                int n = scan.nextInt();
                int leap = scan.nextInt();

                int[] game = new int[n];
                for (int i = 0; i < n; i++) {
                    game[i] = scan.nextInt();
                }

                System.out.println((canWin(leap, game)) ? "YES" : "NO");
            }
        }
    }

    public static boolean canWin(int leap, int[] game) {
        // Return true if you can win the game; otherwise, return false.
        return move(leap, game, 0);
    }

    private static boolean move(int leap, int[] board, int location) {
        if (location < 0 || board[location] != 0) {
            return false;
        }
        if (location == board.length - 1 || location + leap > board.length - 1) {
            return true;
        }

        board[location] = 1;
        return move(leap, board, location + leap) || move(leap, board, location + 1) || move(leap, board, location - 1);
    }
}

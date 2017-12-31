package hackerrank.java.datastructures;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-2d-array/problem
 */
public class TwoDimensionalArray {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            int arr[][] = new int[6][6];
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    arr[i][j] = in.nextInt();
                }
            }

            int highest = Integer.MIN_VALUE;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    int temp = arr[i][j] + arr[i][j + 1] + arr[i][j + 2] + arr[i + 1][j + 1] + arr[i + 2][j] + arr[i + 2][j + 1] + arr[i + 2][j + 2];
                    if (temp > highest) {
                        highest = temp;
                    }
                }
            }

            System.out.println(highest);
        }
    }
}

package hackerrank.java.datastructures;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-1d-array-introduction/problem
 */
public class OneDimensionalArray {
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();

            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                int val = scan.nextInt();
                a[i] = val;
            }

            // Prints each sequential element in array a
            for (int i = 0; i < a.length; i++) {
                System.out.println(a[i]);
            }
        }
    }
}

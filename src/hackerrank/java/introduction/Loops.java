package hackerrank.java.introduction;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-loops-i/problem
 */
public class Loops {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            int N = in.nextInt();

            for (int i = 1; i <= 10; i++) {
                System.out.println(N + " x " + i + " = " + (N * i));
            }
        }
    }
}

package hackerrank.java.introduction;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-loops/problem
 */
public class LoopsTwo {
    public static void main(String... args) {
        try (Scanner in = new Scanner(System.in)) {
            int t = in.nextInt();
            for (int i = 0; i < t; i++) {
                int a = in.nextInt();
                int b = in.nextInt();
                int n = in.nextInt();

                int runningValue = a;
                for (int j = 0; j < n; j++) {
                    runningValue = runningValue + (1 << j) * b;
                    System.out.print(runningValue + " ");
                }
                System.out.println();
            }
        }
    }
}

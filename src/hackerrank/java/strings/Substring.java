package hackerrank.java.strings;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-substring/problem
 */
public class Substring {
    public static void main(String... args) {
        try (Scanner in = new Scanner(System.in)) {
            String S = in.next();
            int start = in.nextInt();
            int end = in.nextInt();
            System.out.println(S.substring(start, end));
        }
    }
}

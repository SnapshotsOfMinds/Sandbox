package hackerrank.java.introduction;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-end-of-file/problem
 */
public class EOFJava7 {
    public static void main(String... args) {
        try (Scanner sc = new Scanner(System.in)) {
            int i = 1;
            while (sc.hasNext()) {
                System.out.println(i + " " + sc.nextLine());
                i++;
            }
        }
    }
}

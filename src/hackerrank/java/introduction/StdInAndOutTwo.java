package hackerrank.java.introduction;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-stdin-stdout/problem
 */
public class StdInAndOutTwo {
    public static void main(String... args) {
        try (Scanner scan = new Scanner(System.in)) {
            int i = scan.nextInt();
            scan.nextLine();
            double d = scan.nextDouble();
            scan.nextLine();
            String s = scan.nextLine();
            scan.close();
            // Write your code here.

            System.out.println("String: " + s);
            System.out.println("Double: " + d);
            System.out.println("Int: " + i);
        }
    }
}

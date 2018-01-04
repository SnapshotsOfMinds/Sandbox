package hackerrank.java.exceptionhandling;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-exception-handling-try-catch/problem
 */
public class TryCatch {
    public static void main(String... args) {
        try (Scanner sc = new Scanner(System.in)) {
            int x = sc.nextInt();
            int y = sc.nextInt();

            System.out.println(x / y);
        } catch (ArithmeticException | InputMismatchException e) {
            System.out.println(e);
        }
    }
}

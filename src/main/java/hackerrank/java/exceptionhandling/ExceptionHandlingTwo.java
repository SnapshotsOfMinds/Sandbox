package hackerrank.java.exceptionhandling;

import java.util.Scanner;

public class ExceptionHandlingTwo {
    public static final MyCalculator my_calculator = new MyCalculator();

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            while (in.hasNextInt()) {
                int n = in.nextInt();
                int p = in.nextInt();

                try {
                    System.out.println(my_calculator.power(n, p));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    static class MyCalculator {
        long power(int n, int p) throws Exception {
            if (n < 0 || p < 0) {
                throw new Exception("n or p should not be negative.");
            } else if (n == 0 && p == 0) {
                throw new Exception("n and p should not be zero.");
            }

            return ((int) Math.pow(n, p));
        }
    }
}

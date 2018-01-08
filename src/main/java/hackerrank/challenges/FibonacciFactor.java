package hackerrank.challenges;

import java.util.Scanner;

/**
 * Given a number K, find the smallest Fibonacci number ( other than 1 ) that
 * shares a common factor with it. A number is said to be a common factor of two
 * numbers if it exactly divides both of them.
 * <p>
 * Output two separate numbers, F and D, where F is the smallest fibonacci
 * number and D is the smallest number other than 1 which divides K and F.
 * <p>
 * Input Format: - First line of the input contains an integer T, the number of
 * test cases. - Then follows T lines, each containing an integer K.
 * <p>
 * Output Format: - Output T lines, each containing the required answer for each
 * corresponding test case.
 * <p>
 * Sample Input * (T =) 3 (K =) 3 (K =) 5 (K =) 161
 * <p>
 * Sample Output (F D =) 3 3 (F D =) 5 5 (F D =) 21 7
 * <p>
 * Explanation There are three test cases. The first test case is 3, the
 * smallest required fibonacci number 3. The second test case is 5 and the third
 * is 161. For 161 the smallest fibonacci number sharing a common divisor with
 * it is 21 and the smallest number other than 1 dividing 161 and 7 is 7.
 * <p>
 * Constraints : 1 <= T <= 5 2 <= K <= 1000,000 The required fibonacci number is
 * guaranteed to be less than 10^18.
 */
public class FibonacciFactor {
    public static void main(String... args) {
        try (Scanner sc = new Scanner(System.in)) {
            int numCases = sc.nextInt();

            while (numCases-- > 0) {
                System.out.println(getFibonacciFactor(sc.nextLong()));
            }
        }
    }

    /**
     * Get the smallest fibonacci number that shares a factor with n
     */
    private static String getFibonacciFactor(long value) {
        long prev = 1;
        long current = 2;

        do {
            long divisor = findCommonFactor(value, current);

            if (divisor != 1) {
                return current + " " + divisor;
            }

            long temp = prev;
            prev = current;
            current += temp;
        } while (current <= Math.pow(10, 18));
        return "1 1";
    }

    /**
     * Is there a common factor between value and currentFib
     */
    private static long findCommonFactor(long value, long currentFib) {
        if (value % 2 == 0) {
            return 2;
        }
        // euclidean algorithm for GCD
        while (currentFib > 0) {
            long temp = currentFib;
            currentFib = value % currentFib;
            value = temp;
        }

        return value;
    }
}

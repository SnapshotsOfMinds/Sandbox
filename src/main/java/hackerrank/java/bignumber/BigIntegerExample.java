package hackerrank.java.bignumber;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-biginteger/problem
 */
public class BigIntegerExample {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String a = sc.nextLine();
            String b = sc.nextLine();
            if (a == null || b == null || a.isEmpty() || b.isEmpty() || a.length() > 200 || b.length() > 200 || a.startsWith("-") || b.startsWith("-")) {
                System.out.println(0);
                return;
            }

            BigInteger left = new BigInteger(a);
            BigInteger right = new BigInteger(b);

            System.out.println(left.add(right));
            System.out.println(left.multiply(right));
        }
    }
}

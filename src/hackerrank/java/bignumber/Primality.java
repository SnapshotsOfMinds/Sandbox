package hackerrank.java.bignumber;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-primality-test/problem
 */
public class Primality {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            BigInteger n = in.nextBigInteger();

            System.out.println(n.isProbablePrime(1) ? "prime" : "not prime");
        }
    }
}

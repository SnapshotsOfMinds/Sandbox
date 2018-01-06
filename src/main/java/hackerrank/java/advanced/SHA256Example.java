package hackerrank.java.advanced;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/sha-256/problem
 */
public class SHA256Example {
    public static void main(String... args) {
        try (Scanner sc = new Scanner(System.in)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(sc.nextLine().trim().getBytes());
            //System.out.println(DatatypeConverter.printHexBinary(digest.digest()).toLowerCase());
        } catch (NoSuchAlgorithmException e) {
            // Ignore
        }
    }
}

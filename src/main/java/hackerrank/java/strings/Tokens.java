package hackerrank.java.strings;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-string-tokens/problem
 */
public class Tokens {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String s = sc.nextLine().trim();

            if (s.isEmpty()) {
                System.out.println(0);
            } else if (s.length() > 400000) {
                return;
            } else {
                String[] sArray = s.split("[ !,?._'@]+");
                System.out.println(sArray.length);
                for (String tokens : sArray) {
                    System.out.println(tokens);
                }
            }
        }
    }
}

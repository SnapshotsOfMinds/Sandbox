package hackerrank.java.strings;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * https://www.hackerrank.com/challenges/pattern-syntax-checker/problem
 */
public class SyntaxChecker {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int testCases = Integer.parseInt(sc.nextLine());
            while (testCases-- > 0) {
                String pattern = sc.nextLine();
                try {
                    Pattern.compile(pattern);
                    System.out.println("Valid");
                } catch (PatternSyntaxException e) {
                    System.out.println("Invalid");
                }
            }
        }
    }
}

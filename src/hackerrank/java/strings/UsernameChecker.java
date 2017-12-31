package hackerrank.java.strings;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/valid-username-checker/problem
 */
public class UsernameChecker {
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = Integer.parseInt(scan.nextLine());
            while (n-- != 0) {
                String userName = scan.nextLine();

                if (userName.matches(UsernameValidator.regularExpression)) {
                    System.out.println("Valid");
                } else {
                    System.out.println("Invalid");
                }
            }
        }
    }
}

class UsernameValidator {
    public static final String regularExpression = "^[a-zA-Z]\\w{7,29}$";
}
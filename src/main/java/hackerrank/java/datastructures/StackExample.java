package hackerrank.java.datastructures;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * https://www.hackerrank.com/challenges/java-stack/problem
 */
public class StackExample {
    private static final List<Character> OPEN = Arrays.asList('[', '{', '(');
    private static final List<Character> CLOSE = Arrays.asList(']', '}', ')');

    public static void main(String... args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNext()) {
                String input = sc.next();
                Stack<Character> inputStack = new Stack<>();
                boolean unbalanced = false;
                for (int i = 0; i < input.length(); i++) {
                    if (CLOSE.contains(input.charAt(i)) && inputStack.isEmpty()) {
                        unbalanced = true;
                        break;
                    }
                    if (OPEN.contains(input.charAt(i))) {
                        inputStack.push(input.charAt(i));
                    } else if (inputStack.peek() == OPEN.get(CLOSE.indexOf(input.charAt(i)))) {
                        inputStack.pop();
                    } else {
                        unbalanced = true;
                        break;
                    }
                }

                if (inputStack.isEmpty() && !unbalanced) {
                    System.out.println("true");
                } else {
                    System.out.println("false");
                }
            }
        }
    }
}

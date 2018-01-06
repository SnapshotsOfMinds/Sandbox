package hackerrank.java.advanced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 * https://www.hackerrank.com/challenges/java-lambda-expressions/problem
 */
public class LambdaExpressions {
    public static void main(String... args) throws IOException {
        MyMath ob = new MyMath();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int T = Integer.parseInt(br.readLine());
            PerformOperation op;
            boolean ret = false;
            String ans = null;
            while (T-- > 0) {
                String s = br.readLine().trim();
                StringTokenizer st = new StringTokenizer(s);
                int ch = Integer.parseInt(st.nextToken());
                int num = Integer.parseInt(st.nextToken());
                if (ch == 1) {
                    op = ob.isOdd();
                    ret = ob.checker(op, num);
                    ans = (ret) ? "ODD" : "EVEN";
                } else if (ch == 2) {
                    op = ob.isPrime();
                    ret = ob.checker(op, num);
                    ans = (ret) ? "PRIME" : "COMPOSITE";
                } else if (ch == 3) {
                    op = ob.isPalindrome();
                    ret = ob.checker(op, num);
                    ans = (ret) ? "PALINDROME" : "NOT PALINDROME";

                }
                System.out.println(ans);
            }
        }
    }
}

@FunctionalInterface
interface PerformOperation {
    boolean check(int a);
}

class MyMath {
    public static boolean checker(PerformOperation p, int num) {
        return p.check(num);
    }

    public static PerformOperation isOdd() {
        return a -> a % 2 == 0;
    }

    public static PerformOperation isPrime() {
        return a -> {
            if (a < 2) {
                return false;
            }
            int sqrt = (int) Math.sqrt(a);
            for (int i = 2; i <= sqrt; i++) {
                if (a % i == 0) {
                    return false;
                }
            }
            return true;
        };
    }

    public static PerformOperation isPalindrome() {
        return a -> {
            String val = Integer.toString(a);
            return IntStream.range(0, val.length() / 2).noneMatch(i -> val.charAt(i) != val.charAt(val.length() - i - 1));
        };
    }
}
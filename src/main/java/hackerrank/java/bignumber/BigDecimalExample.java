package hackerrank.java.bignumber;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-bigdecimal/problem
 */
public class BigDecimalExample {
    public static void main(String[] args) {
        //Input
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            String[] s = new String[n + 2];
            for (int i = 0; i < n; i++) {
                s[i] = sc.next();
            }

            //Java 7
            /*Arrays.sort(s, 0, n, Collections.reverseOrder(new Comparator<String>() {
                @Override
                public int compare(String a1, String a2) {
                    BigDecimal a = new BigDecimal(a1);
                    BigDecimal b = new BigDecimal(a2);
                    return a.compareTo(b);
                }
            }));*/

            // The "0, n" in the sort parameters exists because the provided code sets the array to size n+2.
            Arrays.sort(s, 0, n, Collections.reverseOrder((String a1, String a2) -> {
                        BigDecimal a = new BigDecimal(a1);
                        BigDecimal b = new BigDecimal(a2);
                        return a.compareTo(b);
                    }
            ));

            //Output
            for (int i = 0; i < n; i++) {
                System.out.println(s[i]);
            }
        }
    }
}

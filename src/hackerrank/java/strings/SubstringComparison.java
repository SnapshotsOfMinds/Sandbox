package hackerrank.java.strings;

import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * https://www.hackerrank.com/challenges/java-string-compare/problem
 */
public class SubstringComparison {
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            String s = scan.next();
            int k = scan.nextInt();
            scan.close();

            System.out.println(getSmallestAndLargest(s, k));
        }
    }

    public static String getSmallestAndLargest(String s, int k) {
        SortedSet<String> strings = new TreeSet<>();
        for (int i = 0; i <= s.length() - k; i++) {
            strings.add(s.substring(i, i + k));
        }

        String smallest = strings.first();
        String largest = strings.last();
        return smallest + "\n" + largest;
    }
}

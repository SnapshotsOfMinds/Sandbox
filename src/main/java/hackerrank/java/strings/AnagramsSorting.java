package hackerrank.java.strings;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-anagrams/problem
 */
public class AnagramsSorting {
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            String a = scan.next();
            String b = scan.next();
            boolean ret = isAnagram(a, b);
            System.out.println((ret) ? "Anagrams" : "Not Anagrams");
        }
    }

    static boolean isAnagram(String a, String b) {
        char[] one = a.toLowerCase().toCharArray();
        char[] two = b.toLowerCase().toCharArray();
        Arrays.sort(one);
        Arrays.sort(two);
        return Arrays.equals(one, two);
    }
}

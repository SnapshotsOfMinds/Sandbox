package hackerrank.java.datastructures;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * https://www.hackerrank.com/challenges/java-hashset/problem
 */
public class HashSetExample {
    public static void main(String... args) {
        try (Scanner s = new Scanner(System.in)) {
            int t = s.nextInt();
            String[] pair_left = new String[t];
            String[] pair_right = new String[t];

            for (int i = 0; i < t; i++) {
                pair_left[i] = s.next();
                pair_right[i] = s.next();
            }

            Set<javafx.util.Pair<String, String>> input = new HashSet<>();
            IntStream.range(0, t).boxed().forEach(i -> {
                input.add(new javafx.util.Pair(pair_left[i], pair_right[i]));
                System.out.println(input.size());
            });
        }
    }
}

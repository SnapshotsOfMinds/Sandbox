package hackerrank.java.datastructures;

import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * https://www.hackerrank.com/challenges/java-negative-subarray/problem
 */
public class Subarray {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            final int n = sc.nextInt();
            final int[] arr = IntStream.generate(sc::nextInt).limit(n).toArray();
            System.out.println(IntStream.rangeClosed(1, n).flatMap(m -> IntStream.rangeClosed(0, n - m)
                    .map(i -> IntStream.of(arr).skip(i).limit(m).sum())).filter(sum -> sum < 0).count());
        }
    }
}

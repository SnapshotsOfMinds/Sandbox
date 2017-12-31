package hackerrank.java.datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://www.hackerrank.com/challenges/java-arraylist/problem
 */
public class ArrayListExample {
    public static void main(String... args) {
        try (Scanner sc = new Scanner(System.in)) {
            int numArrays = Integer.parseInt(sc.nextLine());
            List<List<Integer>> input = new ArrayList<>();

            while (numArrays-- > 0) {
                input.add(Stream.of(sc.nextLine().split(" ")).skip(1).map(Integer::parseInt).collect(Collectors.toList()));
            }

            int numQueries = sc.nextInt();
            while (numQueries-- > 0) {
                try {
                    int x = sc.nextInt() - 1;
                    int y = sc.nextInt() - 1;
                    System.out.println(input.get(x).get(y));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("ERROR!");
                }
            }
        }
    }
}

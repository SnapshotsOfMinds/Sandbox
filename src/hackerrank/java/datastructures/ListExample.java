package hackerrank.java.datastructures;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListExample {
    public static void main(String... args) {
        try (Scanner sc = new Scanner(System.in)) {
            sc.nextLine(); // Skip the line for the size of the array since it isn't used.
            List<Integer> input = Stream.of(sc.nextLine().split(" ")).map(Integer::parseInt).collect(Collectors.toList());

            int numQueries = sc.nextInt();
            while (numQueries-- > 0) {
                String query = sc.next();
                if (query.equalsIgnoreCase("insert")) {
                    input.add(sc.nextInt(), sc.nextInt());
                } else if (query.equalsIgnoreCase("delete")) {
                    input.remove(sc.nextInt());
                }
            }
            System.out.println(input.stream().map(Object::toString).collect(Collectors.joining(" ")));
        }
    }
}

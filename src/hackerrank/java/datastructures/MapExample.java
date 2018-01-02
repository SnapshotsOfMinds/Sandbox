package hackerrank.java.datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://www.hackerrank.com/challenges/phone-book/problem
 */
public class MapExample {
    public static void main(String... args) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            int n = Integer.parseInt(in.readLine());
            List<String> input = in.lines().limit(n * 2).collect(Collectors.toList());
            Map<String, String> mappedInput = IntStream.range(0, input.size() / 2).boxed().collect(Collectors.toMap(i -> input.get(i * 2), i -> input.get(i * 2 + 1)));

            String name = "";
            while ((name = in.readLine()) != null && !name.isEmpty()) {
                if (mappedInput.containsKey(name)) {
                    System.out.println(name + "=" + mappedInput.get(name));
                } else {
                    System.out.println("Not found");
                }
            }
//            in.lines().forEach(name -> {
//                if (mappedInput.containsKey(name)) {
//                    System.out.println(name + "=" + mappedInput.get(name));
//                } else {
//                    System.out.println("Not found");
//                }
//            });
        } catch (IOException e) {
            // Error exists with buffered reader.
        }
    }
}

package hackerrank.java.introduction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * https://www.hackerrank.com/challenges/java-end-of-file/problem
 */
public class EOFJava8 {
    public static void main(String... args) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            Stream<String> stream = in.lines();
            AtomicInteger i = new AtomicInteger(1);
            stream.forEach(line -> System.out.println(i.getAndIncrement() + " " + line));
        } catch (IOException e) {
            System.out.println("IO Exception occurred.");
        }
    }
}

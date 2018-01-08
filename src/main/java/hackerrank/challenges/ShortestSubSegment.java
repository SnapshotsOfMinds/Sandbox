package hackerrank.challenges;

import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * https://www.hackerrank.com/contests/amazon/challenges/shortest-sub-segment
 */
public class ShortestSubSegment {
    public static void main(String... args) {
        try (Scanner sc = new Scanner(System.in)) {
            String inputString = sc.nextLine().replaceAll("[^\\w\\s]", "");
            String[] input = inputString.split(" ");
            String[] lowerCaseInput = inputString.toLowerCase().split(" ");

            int numTerms = sc.nextInt();

            // The linked list should always only contain the number of elements equal to the frequency the word shows up.
            Map<String, LinkedList<Integer>> searchTerms = new TreeMap<>();
            for (int i = 0; i < numTerms; i++) {
                String word = sc.next();
                if (!searchTerms.containsKey(word)) {
                    searchTerms.put(word.toLowerCase(), new LinkedList<>());
                }
            }

            int count = 0;
            int head = -1;
            int tail = -1;
            int subStringLength = Integer.MAX_VALUE;
            for (int i = 0; i < lowerCaseInput.length; i++) {
                if (searchTerms.containsKey(lowerCaseInput[i])) {
                    if (searchTerms.get(lowerCaseInput[i]).isEmpty()) {
                        // Start looking for the shortest substring when the count equals the unique number of terms.
                        count++;
                    }
                    searchTerms.get(lowerCaseInput[i]).add(i);
                }
                if (count >= searchTerms.keySet().size()) {
                    int low = Integer.MAX_VALUE;
                    int high = Integer.MIN_VALUE;
                    for (LinkedList<Integer> lists : searchTerms.values()) {
                        if (lists.getLast() > high) {
                            high = lists.getLast();
                        }
                        if (lists.getLast() < low) {
                            low = lists.getLast();
                        }
                    }

                    if (high - low < subStringLength) {
                        subStringLength = (high - low) + 1;
                        head = low;
                        tail = high;
                    }
                }
            }

            if (subStringLength == Integer.MAX_VALUE) {
                System.out.println("NO SUBSEGMENT FOUND");
            } else {
                IntStream.rangeClosed(head, tail).forEach(i -> System.out.print(input[i] + " "));
                System.out.println();
            }
        }
    }
}

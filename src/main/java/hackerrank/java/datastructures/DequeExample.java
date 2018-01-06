package hackerrank.java.datastructures;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-dequeue/problem
 */
public class DequeExample {
    public static void main(String... args) {
        Deque<Integer> deque = new ArrayDeque<>();
        Map<Integer, Integer> map = new HashMap<>();

        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            int max = map.size();

            for (int i = 0; i < n; i++) {
                if (i >= m) {
                    int head = deque.remove();
                    if(map.get(head) == 1) {
                        map.remove(head);
                    } else {
                        map.put(head, map.get(head) - 1);
                    }
                }

                int num = sc.nextInt();
                deque.add(num);
                map.merge(num, 1, Integer::sum);
                max = Math.max(max, map.size());
            }
            System.out.println(max);
        }
    }
}

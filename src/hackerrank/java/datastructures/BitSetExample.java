package hackerrank.java.datastructures;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

/**
 * https://www.hackerrank.com/challenges/java-bitset/problem
 */
public class BitSetExample {
    public static void main(String... args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            BitSet[] bitsets = { new BitSet(n), new BitSet(n) };

            Map<String, BiConsumer<Integer, Integer>> biConsumer = new HashMap<>();
            biConsumer.put("AND", (leftOperand, rightOperand) -> bitsets[leftOperand - 1].and(bitsets[rightOperand - 1]));
            biConsumer.put("OR", (leftOperand, rightOperand) -> bitsets[leftOperand - 1].or(bitsets[rightOperand - 1]));
            biConsumer.put("XOR", (leftOperand, rightOperand) -> bitsets[leftOperand - 1].xor(bitsets[rightOperand - 1]));
            biConsumer.put("SET", (leftOperand, rightOperand) -> bitsets[leftOperand - 1].set(rightOperand));
            biConsumer.put("FLIP", (leftOperand, rightOperand) -> bitsets[leftOperand - 1].flip(rightOperand));

            IntStream.range(0, m).forEach(i -> {
                biConsumer.get(sc.next().toUpperCase()).accept(sc.nextInt(), sc.nextInt());
                System.out.println(bitsets[0].cardinality() + " " + bitsets[1].cardinality());
            });

            // Java 7 solution//
//            while (m-- > 0) {
//                String command = sc.next();
//                int leftOperand = sc.nextInt();
//                int rightOperand = sc.nextInt();
//
//                if (command.equalsIgnoreCase("AND")) {
//                    bitsets[leftOperand - 1].and(bitsets[rightOperand - 1]);
//                } else if (command.equalsIgnoreCase("OR")) {
//                    bitsets[leftOperand - 1].or(bitsets[rightOperand - 1]);
//                } else if (command.equalsIgnoreCase("XOR")) {
//                    bitsets[leftOperand - 1].xor(bitsets[rightOperand - 1]);
//                } else if (command.equalsIgnoreCase("FLIP")) {
//                    bitsets[leftOperand - 1].flip(rightOperand);
//                } else if (command.equalsIgnoreCase("SET")) {
//                    bitsets[leftOperand - 1].set(rightOperand);
//                }
//
//                System.out.println(bitsets[0].cardinality() + " " + bitsets[1].cardinality());
//            }
        }
    }
}

package hackerrank.java.oop;

import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * https://www.hackerrank.com/challenges/java-interface/problem
 */
public class InterfaceExample {
    public static void main(String... args) {
        MyCalculator my_calculator = new MyCalculator();
        System.out.print("I implemented: ");
        ImplementedInterfaceNames(my_calculator);
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            System.out.print(my_calculator.divisor_sum(n) + "\n");
        }
    }

    /*
     *  ImplementedInterfaceNames method takes an object and prints the name of the interfaces it implemented
     */
    static void ImplementedInterfaceNames(Object o) {
        Class[] theInterfaces = o.getClass().getInterfaces();
        for (int i = 0; i < theInterfaces.length; i++) {
            String interfaceName = theInterfaces[i].getName();
            System.out.println(interfaceName);
        }
    }

    interface AdvancedArithmetic {
        int divisor_sum(int n);
    }

    static class MyCalculator implements AdvancedArithmetic {
        public int divisor_sum(int n) {
            return IntStream.rangeClosed(1, n).filter(i -> n % i == 0).sum();
        }
    }
}

package practice;

public class Fibonacci {
    public static void main(String... args) {
        int n = 40;
        long startTime = System.nanoTime();
        int result = FibonacciLoop(n);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("At n = " + n + " the result is " + result);
        System.out.println("Loop duration = " + duration);

        long startTime2 = System.nanoTime();
        int result2 = FibonacciRecursion(n);
        long endTime2 = System.nanoTime();
        long duration2 = endTime2 - startTime2;
        System.out.println("At n = " + n + " the result is " + result2);
        System.out.println("Recursive duration = " + duration2);
    }

    private static int FibonacciLoop(int n) {
        int a = 1;
        int b = 1;
        int c = a + b;

        for (int i = 0; i < n - 2; i++) {
            c = a + b;
            a = b;
            b = c;
        }

        return c;
    }

    private static int FibonacciRecursion(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return FibonacciRecursion(n - 1) + FibonacciRecursion(n - 2);
        }
    }
}

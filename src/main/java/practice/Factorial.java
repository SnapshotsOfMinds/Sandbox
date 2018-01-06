package practice;

public class Factorial {
    public static void main(String... args) {
        int n = 10;

        long startTime = System.nanoTime();
        int result = FactorialLoop(n);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("At n = " + n + " the result is " + result);
        System.out.println("Loop duration = " + duration);

        long startTime2 = System.nanoTime();
        int result2 = FactorialRecursion(n);
        long endTime2 = System.nanoTime();
        long duration2 = endTime2 - startTime2;
        System.out.println("At n = " + n + " the result is " + result2);
        System.out.println("Recursive duration = " + duration2);

    }

    private static int FactorialLoop(int n) {
        int factor = 1;

        for (int i = 1; i <= n; i++) {
            factor *= i;
        }

        return factor;
    }

    private static int FactorialRecursion(int n) {
        if (n == 1) {
            return 1;
        }
        return FactorialRecursion(n - 1) * n;
    }
}

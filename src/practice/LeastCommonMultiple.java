package practice;

/**
 * Class to determine the least common multiple of two numbers.
 */
public class LeastCommonMultiple {
    public static void main(String... args) {
        System.out.println(lcm(8, 12));
    }

    /**
     * Determine the LCM of two numbers.
     *
     * @param a The left operand.
     * @param b The right operand.
     * @return The LCM of the provided numbers.
     */
    private static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    /**
     * Determine LCM using an input array.
     *
     * @param input The array containing values to determine the LCM.
     * @return The LCM of the provided numbers.
     */
    private static long lcm(long[] input) {
        long result = input[0];
        for (int i = 1; i < input.length; i++) {
            result = lcm(result, input[i]);
        }
        return result;
    }

    /**
     * Determine the greatest common divisor of two numbers.
     *
     * @param a The left operand.
     * @param b The right operand.
     * @return The value of the GCD.
     */
    private static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }

    /**
     * Determine the GCD of an array of values.
     *
     * @param input The array containing the input values.
     * @return The GCD of the array values.
     */
    private static long gcd(long[] input) {
        long result = input[0];
        for (int i = 1; i < input.length; i++) {
            result = gcd(result, input[i]);
        }
        return result;
    }


}

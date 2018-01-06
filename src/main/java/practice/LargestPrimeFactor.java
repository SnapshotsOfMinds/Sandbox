package practice;

public class LargestPrimeFactor {
    double number = 6.00851475143;
    double modifier = Math.pow(10, 11);
    long value = (long) (number * modifier);

    public static void main(String[] args) {
        new LargestPrimeFactor();
    }

    public LargestPrimeFactor() {
        long factor = 0;
        long max = 100000;
        long i = 1;

        while (i <= max) {
            factor = largestPrimeFactor(value);
            i++;
        }

        System.out.println(factor);
    }

    public long largestPrimeFactor(long n) {
        long largestFactor = 1;
        long p = 3;
        long div = n;

        while (div % 2 == 0) {
            largestFactor = 2;
            div /= 2;
        }

        while (div != 1) {
            while (div % p == 0) {
                largestFactor = p;
                div /= p;
            }
            p = p + 2;
        }

        return largestFactor;
    }
}

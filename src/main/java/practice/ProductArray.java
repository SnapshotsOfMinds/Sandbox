package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Given a number (N) of integers, calculate a list of integers such that the output for index 'i' is the product of all input values except the input value at index 'i'.
 */
public class ProductArray {
    //Variables input by the user
    private int numValues;
    private long[] values;

    //Variables used in calculating the results
    private long product;
    private int zeroCount;

    /**
     * Entry point into the program.
     *
     * @param args Command line arguments
     */
    public static void main(String... args) {
        new ProductArray();
    }

    /**
     * Default constructor to control the behavior of the program.
     */
    private ProductArray() {
        collectInput();
        printResult();
    }

    /**
     * Collects the user input. While collecting input, keep track of entered zeros and the product of the entire input.
     */
    private void collectInput() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            numValues = Integer.parseInt(reader.readLine());
            values = new long[numValues];
            product = 1;
            zeroCount = 0;
            for (int i = 0; i < numValues; i++) {
                values[i] = Integer.parseInt(reader.readLine());
                if (values[i] == 0) {
                    zeroCount++;
                } else {
                    product *= values[i];
                }
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print the final result to the terminal.
     */
    private void printResult() {
        if (zeroCount > 1) { //More than one zero exists in the input
            for (int i = 0; i < numValues; i++) {
                System.out.println(0);
            }
        } else {//One or less zeros exist
            for (int i = 0; i < numValues; i++) {
                if (values[i] == 0) {//Zero is the current term
                    System.out.println(product);
                } else {
                    if (zeroCount > 0) {//One zero exists
                        System.out.println(0);
                    } else {//No zeros exist
                        System.out.println(product / values[i]);
                    }
                }
            }
        }
    }
}

package practice;

import java.util.Arrays;
import java.util.Random;

public class Mergesort {
    private int[] numbers;
    private int[] helper;


    public void sort(int[] values) {
        int number;

        // Numbers array = Values array
        this.numbers = values;

        // Number = length of the array
        number = values.length;

        // Helper array is a new int array same length as Numbers array
        this.helper = new int[number];

        // Call to sort the array (low index, high index)
        mergesort(0, number - 1);
    }

    private void mergesort(int low, int high) {
        // check if low is smaller then high, if not then the array is sorted
        if (low < high) {
            // Get the index of the element which is in the middle
            int middle = low + (high - low) / 2;
            System.out.println("low = " + low + " middle = " + middle);

            // Sort the left side of the array
            for (int i = low; i <= high; i++) {
                System.out.print(numbers[i] + " ");
            }
            System.out.println();
            mergesort(low, middle);


            // Sort the right side of the array
            System.out.println("middle + 1 = " + (middle + 1) + " high = " + high);
            for (int i = middle + 1; i <= high; i++) {
                System.out.print(numbers[i] + " ");
            }
            System.out.println();
            mergesort(middle + 1, high);


            // Combine them both
            System.out.println("Before Merge: " + "low = " + low + " " + "middle = " + middle + " " + "high = " + high);

            merge(low, middle, high);
        }
    }

    private void merge(int low, int middle, int high) {
        // Copy both parts into the helper array
        for (int i = low; i <= high; i++) {
            helper[i] = numbers[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;
        // Copy the smallest values from either the left or the right side back
        // to the original array
        while (i <= middle && j <= high) {
            if (helper[i] <= helper[j]) {
                numbers[k] = helper[i];
                i++;
            } else {
                numbers[k] = helper[j];
                j++;
            }
            k++;
        }
        // Copy the rest of the left side of the array into the target array
        while (i <= middle) {
            numbers[k] = helper[i];
            k++;
            i++;
        }
        System.out.println("Sorting...");
        for (int number : numbers) {
            System.out.print(number + " ");
        }
        System.out.println();
    }
}

class MergesortTest {

    private static int[] numbers;
    private final static int SIZE = 6;
    private final static int MAX = 20;

    public static void main(String[] args) {
        setUp();
        //testStandardSort();
        testMergeSort();
        //itWorksRepeatably();

    }

    public static void setUp() {
        numbers = new int[SIZE];
        Random generator = new Random();
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = generator.nextInt(MAX);
        }
    }

    private static void testMergeSort() {
        long startTime = System.currentTimeMillis();

        System.out.println("Unsorted Array");
        for (int i = 0; i <= numbers.length - 1; i++) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println();

        Mergesort sorter = new Mergesort();
        sorter.sort(numbers);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Mergesort " + elapsedTime);

        System.out.println("Sorted Array");
        for (int i = 0; i <= numbers.length - 1; i++) {
            System.out.print(numbers[i] + " ");
        }

        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] > numbers[i + 1]) {
                System.out.println("Should not happen");
            }
        }

    }

    public static void itWorksRepeatably() {
        for (int i = 0; i < 200; i++) {
            numbers = new int[SIZE];
            Random generator = new Random();
            for (int a = 0; a < numbers.length; a++) {
                numbers[a] = generator.nextInt(MAX);
            }
            Mergesort sorter = new Mergesort();
            sorter.sort(numbers);
            for (int j = 0; j < numbers.length - 1; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    System.out.println("Should not happen");
                }
            }
        }
    }

    public static void testStandardSort() {
        long startTime = System.currentTimeMillis();
        Arrays.sort(numbers);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Standard Java sort " + elapsedTime);

        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] > numbers[i + 1]) {
                System.out.println("Should not happen");
            }
        }
    }

}
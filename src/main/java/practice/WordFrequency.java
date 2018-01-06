package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * A program that counts the frequency of words. Given a number of input terms (N) and the terms, calculate
 * the frequency each of the terms appears. Then print out the k most occurring terms.
 */
public class WordFrequency {
    //Map containing initial counting to be sorted
    private Map<String, Integer> inputMap = new TreeMap<>();

    //The number of terms input
    private int numTerms;

    //The number of results to display
    private int display;

    /**
     * Create a new instance of the Frequency class.
     *
     * @param args Program arguments from command line.
     */
    public static void main(String... args) {
        new WordFrequency();
    }

    /**
     * Constructor to control the flow of the program.
     */
    private WordFrequency() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            if ((input = br.readLine()) != null) {
                numTerms = Integer.parseInt(input);
            }

            for (int i = 0; i < numTerms; i++) {
                input = br.readLine();
                Integer n = inputMap.get(input);
                n = (n == null) ? 1 : ++n;
                inputMap.put(input, n);
            }
            if ((input = br.readLine()) != null) {
                display = Integer.parseInt(input);
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        printResult();
    }

    /**
     * Handles the sorting for the program. The entries of the map are sorted using a comparator. If the value of two keys are the same,
     * the keys are sorted lexicographically.
     *
     * @param map The map to be sorted based on the values
     * @return A sorted list of entries
     */
    private <K extends Comparable, V extends Comparable> List<Map.Entry<K, V>> sortResult(Map<K, V> map) {
        //LinkedList containing the entries of the map
        List<Map.Entry<K, V>> entries = new LinkedList<>(map.entrySet());

        //Comparator used to compare the values for sorting
        entries.sort((k, v) -> {
                    int value1 = (Integer) k.getValue();
                    int value2 = (Integer) v.getValue();
                    if (value1 == value2) {
                        return ((String) k.getKey()).compareToIgnoreCase((String) v.getKey());
                    }
                    return value2 - value1;
                }
        );

        return entries;
    }

    /**
     * Prints the results to the terminal using the sorted list of entries obtained by the comparator.
     */
    private void printResult() {
        Iterator<Entry<String, Integer>> iterator = sortResult(inputMap).iterator();
        while (iterator.hasNext() && display > 0) {
            Map.Entry entry = iterator.next();
            if (display != 1) {
                System.out.println(entry.getKey());
            } else {
                System.out.print(entry.getKey());
            }
            display--;
        }
    }
}

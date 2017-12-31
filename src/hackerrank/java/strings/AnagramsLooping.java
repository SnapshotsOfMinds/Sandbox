package hackerrank.java.strings;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-anagrams/submissions/code/62109748
 */
public class AnagramsLooping {
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            String a = scan.next();
            String b = scan.next();

            boolean ret = isAnagram(a, b);
            System.out.println((ret) ? "Anagrams" : "Not Anagrams");
        }
    }

    static boolean isAnagram(String a, String b) {
        if (a == null || b == null || a.isEmpty() || b.isEmpty() || a.length() != b.length()) {
            return false;
        }
        char[] aArray = a.toLowerCase().toCharArray();
        char[] bArray = b.toLowerCase().toCharArray();

        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (int i = 0; i < aArray.length; i++) {
            if (!frequencyMap.containsKey(aArray[i])) {
                frequencyMap.put(aArray[i], 1);
            } else {
                int frequency = frequencyMap.get(aArray[i]);
                frequencyMap.put(aArray[i], ++frequency);
            }
        }

        for (int i = 0; i < bArray.length; i++) {
            if (!frequencyMap.containsKey(bArray[i])) {
                return false;
            } else if (frequencyMap.get(bArray[i]) == 0) {
                return false;
            } else {
                int frequency = frequencyMap.get(bArray[i]);
                frequencyMap.put(bArray[i], --frequency);
            }
        }
        return true;
    }
}

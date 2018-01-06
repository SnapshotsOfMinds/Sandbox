package hackerrank.java.strings;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://www.hackerrank.com/challenges/tag-content-extractor/problem
 */
public class ContentExtractor {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int testCases = Integer.parseInt(sc.nextLine());
            while (testCases-- > 0) {
                String line = sc.nextLine();
                String regex = "<(.+)>([^<]+)</\\1>";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(line);
                boolean found = false;
                while (m.find()) {
                    System.out.println(m.group(2));
                    found = true;
                }

                if (!found) {
                    System.out.println("None");
                }
            }
        }
    }
}

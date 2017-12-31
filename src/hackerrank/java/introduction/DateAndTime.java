package hackerrank.java.introduction;

import java.time.LocalDate;
import java.time.Month;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-date-and-time/problem
 */
public class DateAndTime {
    public static void main(String... args) {
        try (Scanner in = new Scanner(System.in)) {
            String month = in.next();
            String day = in.next();
            String year = in.next();

            System.out.println(getDay(day, month, year));
        }
    }

    public static String getDay(String day, String month, String year) {
        LocalDate date = LocalDate.of(Integer.parseInt(year), Month.of(Integer.parseInt(month)), Integer.parseInt(day));
        return date.getDayOfWeek().toString();
    }
}

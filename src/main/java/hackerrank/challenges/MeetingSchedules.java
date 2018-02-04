package hackerrank.challenges;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/contests/amazon/challenges/meeting-schedules
 */
public class MeetingSchedules {
    public static void main(String[] args) {
        int arr[] = new int[1440];
        int K;
        try (Scanner scan = new Scanner(System.in)) {

            int M = scan.nextInt();
            K = scan.nextInt();
            while (M-- > 0) {
                int sh = scan.nextInt();
                int sm = scan.nextInt();
                int eh = scan.nextInt();
                int em = scan.nextInt();
                for (int i = sh * 60 + sm; i < eh * 60 + em; i++) {
                    arr[i]++;
                }
            }
        }
        int start, end;
        for (int i = 0; i < 1440; i++) {
            if (arr[i] == 0) {
                start = i;
                while (i < 1440 && arr[i++] == 0) {
                }
                end = i - 1;
                if (end - start >= K) {
                    if (end / 60 == 23 && end % 60 == 59) {
                        end = 0;
                    }
                    System.out.println(String.format("%02d", start / 60) + " " + String.format("%02d", start % 60) + " " + String.format("%02d", end / 60) + " " + String.format("%02d", end % 60));
                }
            }
        }
    }
}

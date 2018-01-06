package hackerrank.java.introduction;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-static-initializer-block/problem
 */
public class StaticBlock {
    static Scanner sc = new Scanner(System.in);
    static boolean flag = true;
    static int B = sc.nextInt();
    static int H = sc.nextInt();

    static {
        try {
            if (B <= 0 || H <= 0) {
                flag = false;
                throw new Exception("Breadth and height must be positive");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            sc.close();
        }
    }
}

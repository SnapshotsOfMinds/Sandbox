package practice;

public class Staircase {
    public static void main(String... args) {
        StairCase(6);
    }

    /**
     * Print '#' symbols in the shape of a staircase with n height.
     *
     * @param n The height of the stairs.
     */
    static void StairCase(int n) {
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                System.out.print(" ");
            }
            for (int j = n - i; j >= 0; j--) {
                System.out.print("#");
            }
            System.out.println();
        }
    }
}

package hackerrank.java.oop;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-abstract-class/problem
 */
public class AbstractExample {
    public static void main(String... args) {
        //Book new_novel=new Book(); This line prHMain.java:25: error: Book is abstract; cannot be instantiated
        try (Scanner sc = new Scanner(System.in)) {
            String title = sc.nextLine();
            MyBook new_novel = new MyBook();
            new_novel.setTitle(title);
            System.out.println("The title is: " + new_novel.getTitle());
        }
    }

    static abstract class Book {
        String title;

        abstract void setTitle(String s);

        String getTitle() {
            return title;
        }
    }

    static class MyBook extends Book {
        void setTitle(String s) {
            super.title = s;
        }
    }
}

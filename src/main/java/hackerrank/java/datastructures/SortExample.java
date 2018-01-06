package hackerrank.java.datastructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-sort/problem
 */
public class SortExample {
    public static void main(String... args) {
        try(Scanner in = new Scanner(System.in)) {
            int testCases = Integer.parseInt(in.nextLine());

            List<Student> studentList = new ArrayList<>();
            while (testCases-- > 0) {
                studentList.add(new Student(in.nextInt(), in.next(), in.nextDouble()));
            }

            studentList.sort(Comparator.comparing(Student::getCgpa).reversed().thenComparing(Student::getFname).thenComparing(Student::getId));
            for (Student st : studentList) {
                System.out.println(st.getFname());
            }
        }
    }
}

class Student {
    private int id;
    private String fname;
    private double cgpa;

    public Student(int id, String fname, double cgpa) {
        super();
        this.id = id;
        this.fname = fname;
        this.cgpa = cgpa;
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public double getCgpa() {
        return cgpa;
    }
}
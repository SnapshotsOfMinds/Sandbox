package hackerrank.java.datastructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-priority-queue/problem
 */
public class PriorityQueueExample {
    private final static Priorities priorities = new Priorities();

    public static void main(String... args) {
        try (Scanner scan = new Scanner(System.in)) {
            int totalEvents = Integer.parseInt(scan.nextLine());
            List<String> events = new ArrayList<>();

            while (totalEvents-- != 0) {
                String event = scan.nextLine();
                events.add(event);
            }

            List<Student> students = priorities.getStudents(events);

            if (students.isEmpty()) {
                System.out.println("EMPTY");
            } else {
                for (Student st : students) {
                    System.out.println(st.getName());
                }
            }
        }
    }

    static class Priorities {
        public List<Student> getStudents(List<String> events) {
            Queue<Student> priorityQueue = new PriorityQueue<>(events.size(),
                    Comparator.comparing(Student::getCGPA)
                            .reversed()
                            .thenComparing(Student::getName)
                            .thenComparing(Student::getID));

            for (String event : events) {
                String[] values = event.split(" ");
                if(values[0].equalsIgnoreCase("ENTER")) {
                    priorityQueue.offer(new Student(values[1], Double.parseDouble(values[2]), Integer.parseInt(values[3])));
                } else if(values[0].equalsIgnoreCase("SERVED")){
                    priorityQueue.poll();
                }
            }

            List<Student> students = new ArrayList<>();
            while(!priorityQueue.isEmpty())
            {
                students.add(priorityQueue.poll());
            }
            return students;
        }
    }

    static class Student {
        private int id;
        private String name;
        private double cgpa;

        public Student(String name, double cgpa, int id) {
            this.id = id;
            this.name = name;
            this.cgpa = cgpa;
        }

        public int getID() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getCGPA() {
            return cgpa;
        }
    }
}
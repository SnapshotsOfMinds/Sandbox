package hackerrank.java.advanced;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://www.hackerrank.com/challenges/java-reflection-attributes/problem
 */
public class Reflection {
    public static void main(String[] args) {
        Class student = Student.class;
        Method[] methods = student.getDeclaredMethods();

        List<String> methodList = Arrays.stream(methods).map(m -> m.getName()).collect(Collectors.toList());

        Collections.sort(methodList);
        for (String name : methodList) {
            System.out.println(name);
        }
    }

    static class Student {
        private String name;
        private String id;
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getID() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}

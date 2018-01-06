package hackerrank.java.advanced;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * https://www.hackerrank.com/challenges/java-annotations/problem
 */
public class AnnotationsExample {
    public static void main(String... args) {
        try (Scanner sc = new Scanner(System.in)) {
            int testCases = Integer.parseInt(sc.nextLine());
            while (testCases > 0) {
                String role = sc.next();
                int spend = sc.nextInt();
                try {
                    Class annotatedClass = FamilyMember.class;
                    Method[] methods = annotatedClass.getMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(FamilyBudget.class)) {
                            FamilyBudget family = method.getAnnotation(FamilyBudget.class);
                            String userRole = family.userRole();
                            int budgetLimit = family.budgetLimit();
                            if (userRole.equals(role)) {
                                if (spend <= budgetLimit) {
                                    method.invoke(new FamilyMember(), budgetLimit, spend);
                                } else {
                                    System.out.println("Budget Limit Over");
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                testCases--;
            }
        }
    }
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface FamilyBudget {
    String userRole() default "GUEST";

    int budgetLimit() default 0;
}

class FamilyMember {
    @FamilyBudget(userRole = "SENIOR", budgetLimit = 100)
    public void seniorMember(int budget, int moneySpend) {
        System.out.println("Senior Member");
        System.out.println("Spend: " + moneySpend);
        System.out.println("Budget Left: " + (budget - moneySpend));
    }

    @FamilyBudget(userRole = "JUNIOR", budgetLimit = 50)
    public void juniorUser(int budget, int moneySpend) {
        System.out.println("Junior Member");
        System.out.println("Spend: " + moneySpend);
        System.out.println("Budget Left: " + (budget - moneySpend));
    }
}

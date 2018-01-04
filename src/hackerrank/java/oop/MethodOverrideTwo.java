package hackerrank.java.oop;

/**
 * https://www.hackerrank.com/challenges/java-method-overriding-2-super-keyword/problem
 */
public class MethodOverrideTwo {
    public static void main(String... args) {
        MotorCycle M = new MotorCycle();
    }

    static class BiCycle {
        String define_me() {
            return "a vehicle with pedals.";
        }
    }

    static class MotorCycle extends BiCycle {
        String define_me() {
            return "a cycle with an engine.";
        }

        MotorCycle() {
            System.out.println("Hello I am a motorcycle, I am " + define_me());
            String temp = super.define_me(); //Fix this line
            System.out.println("My ancestor is a cycle who is " + temp);
        }
    }
}
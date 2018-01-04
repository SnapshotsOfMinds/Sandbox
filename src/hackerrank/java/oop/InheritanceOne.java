package hackerrank.java.oop;

/**
 * https://www.hackerrank.com/challenges/java-inheritance-1/problem
 */
public class InheritanceOne {
    public static void main(String... args){

        Bird bird = new Bird();
        bird.walk();
        bird.fly();
        bird.sing();

    }

    static class Animal{
        void walk()
        {
            System.out.println("I am walking");
        }
    }

    static class Bird extends Animal
    {
        void fly()
        {
            System.out.println("I am flying");
        }
        void sing()
        {
            System.out.println("I am singing");
        }
    }
}

package hackerrank.java.advanced;

/**
 * https://www.hackerrank.com/challenges/java-singleton/problem
 */
public class SingletonPattern {
    private static final SingletonPattern INSTANCE = new SingletonPattern();
    public String str;

    private SingletonPattern() {

    }

    public static SingletonPattern getSingleInstance() {
        return INSTANCE;
    }
}

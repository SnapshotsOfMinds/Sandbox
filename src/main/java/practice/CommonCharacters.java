package practice;

/**
 * Two strings are given. Modify the first string such that all the common characters of the second string
 * are removed and the uncommon characters of the second string are concatenated with the uncommon characters
 * of the first string.
 * Given:
 * s1 = aacdb
 * s2 = gafd
 * return = cbgf
 * Given:
 * s1 = abcs
 * s2 = cxzca
 * return = bsxz
 */
public class CommonCharacters {
    static String s1 = "aabcad";
    static String s2 = "abc";

    public static void main(String... args) {
        if (s1 == null || s1.length() == 0) {
            System.out.println(s2);
        } else if (s2 == null || s2.length() == 0) {
            System.out.println(s1);
        }

        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                if(s1.charAt(i) == s2.charAt(j))
                {
                    s1 = s1.replace(s1.charAt(i), Character.MIN_VALUE);
                    s2 = s2.replace(s2.charAt(j), Character.MIN_VALUE);
                    break;
                }
            }
        }

        System.out.println(s1 + s2);
    }
}

package practice;

/**
 * Class to determine the longest palindrome within a given string.
 */
public class LongestPalindrome {
    public static void main(String... args) {
        new LongestPalindrome();
    }

    public LongestPalindrome() {
        System.out.println(longestPalindrome("babadda"));
    }

    /**
     * Determine the longest palindrome
     *
     * @param s The string to check
     * @return The string representing the longest palindrome.
     */
    public String longestPalindrome(String s) {
        String res = "";
        int currLength = 0;
        for (int i = 0; i < s.length(); i++) {
            if (isPalindrome(s, i - currLength - 1, i)) {
                res = s.substring(i - currLength - 1, i + 1);
                currLength = currLength + 2;
            } else if (isPalindrome(s, i - currLength, i)) {
                res = s.substring(i - currLength, i + 1);
                currLength = currLength + 1;
            }
        }
        return res;
    }

    /**
     * Check if a string is a palindrome between the {@code begin} and {@code end} indecies.
     *
     * @param s The string to check for a palindrome
     * @param begin The beginning index
     * @param end The ending index
     * @return True if the string is a palindrome, false otherwise.
     */
    public boolean isPalindrome(String s, int begin, int end) {
        if (begin < 0) {
            return false;
        }
        while (begin < end) {
            if (s.charAt(begin++) != s.charAt(end--)) {
                return false;
            }
        }
        return true;
    }
}

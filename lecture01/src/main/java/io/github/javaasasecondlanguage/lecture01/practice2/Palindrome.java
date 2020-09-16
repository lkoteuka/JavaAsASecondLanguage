package io.github.javaasasecondlanguage.lecture01.practice2;

public class Palindrome {
    /**
     * @param str - Nullable string
     * @return True if str is a palindrome
     * 1. Empty string is a palindrome.
     * 2. "aba" is a palindrome.
     * 3. "ab" is not a palindrome.
     * 4. "abA" is not a palindrome.
     * @throws IllegalArgumentException if str is `null`
     */
    public static boolean isPalindrome(String str) throws IllegalArgumentException{
        if (str == null){
            throw new  IllegalArgumentException();
        }
        int reverse_i = str.length() - 1;
        for (int i = 0; i < str.length() / 2; i++, reverse_i--){
            if (str.charAt(i) != str.charAt(reverse_i)){
                return false;
            }
        }
        return true;
//        throw new RuntimeException("Not implemented");
    }
}

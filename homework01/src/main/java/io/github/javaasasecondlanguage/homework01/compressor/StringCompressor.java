package io.github.javaasasecondlanguage.homework01.compressor;

import java.util.Arrays;

public class StringCompressor {
    /**
     * Given an array of characters, compress it using the following algorithm:
     * <p>
     * Begin with an empty string s.
     * For each group of consecutive repeating characters in chars:
     * If the group's length is 1, append the character to s.
     * Otherwise, append the character followed by the group's length.
     * Return a compressed string.
     * </p>
     * Follow up:
     * Could you solve it using only O(1) extra space?
     * </p>
     * Examples:
     * a -> a
     * aa -> a2
     * aaa -> a3
     * aaabb -> a3b2
     * "" -> ""
     * null -> Illegal argument
     * 234 sdf -> Illegal argument
     *
     * @param str nullable array of chars to compress
     *            str may contain illegal characters
     * @return a compressed array
     * @throws IllegalArgumentException if str is null
     * @throws IllegalArgumentException if any char is not in range 'a'..'z'
     */
    public char[] compress(char[] str) throws IllegalArgumentException {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        if (str.length == 0) {
            return str;
        }
        int chang_ind = 0;
        int acum = 0;
        for (int i = 1; i < str.length; i++) {
            while (str[i] == str[chang_ind]) {
                if ((str[i] < 97) || (str[i] > 122)) {
                    throw new IllegalArgumentException();
                }
                acum++;
                i++;
                if (i >= str.length) {
                    break;
                }
            }
            chang_ind++;
            if (acum > 0) {
                str[chang_ind] = Character.forDigit(acum + 1, 10);
                chang_ind++;
                if (i >= str.length) {
                    break;
                }
                str[chang_ind] = str[i];
                acum = 0;
            } else {
                str[chang_ind] = str[i];
            }
        }
        if (acum == 0) chang_ind++;
        char[] res = Arrays.copyOf(str, chang_ind);
        return res;
    }
}

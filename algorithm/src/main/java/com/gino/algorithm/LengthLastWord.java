package com.gino.algorithm;

public class LengthLastWord {
    public static int lengthOfLastWord(String s) {
        int res = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ' ') {
                if (res > 0) {
                    break;
                }
            } else {
                res++;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        String str = "    ";
        lengthOfLastWord(str);
    }
}
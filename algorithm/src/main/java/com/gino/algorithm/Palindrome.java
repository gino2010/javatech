package com.gino.algorithm;

public class Palindrome {
    public static boolean isPalindrome(int x) {
        String s = Integer.toString(x);
        StringBuilder target = new StringBuilder();

        // append a string into StringBuilder input1
        target.append(s);

        // reverse StringBuilder input1
        return target.reverse().toString().equals(s);

    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(101));
    }
}

package com.gino.algorithm;

import java.util.*;

public class ValidParentheses {
    public static boolean isValid(String s) {
        if (s.length() % 2 != 0) {
            return false;
        }
        ArrayDeque<Character> stack = new ArrayDeque<>();
        HashMap<Character, Character> rightToLeft = new HashMap<>();
        rightToLeft.put(')', '(');
        rightToLeft.put(']', '[');
        rightToLeft.put('}', '{');
        for (int i = 0; i < s.length(); i++) {
            if (rightToLeft.get(s.charAt(i)) != null) {
                if (!stack.isEmpty() && stack.getLast() == rightToLeft.get(s.charAt(i))) {
                    stack.removeLast();
                } else {
                    return false;
                }
            }else{
                stack.add(s.charAt(i));
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isValid("(){}}{"));
    }
}

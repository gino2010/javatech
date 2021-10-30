package com.gino.algorithm;

import java.util.HashMap;

public class RomanToInt {
    public static int romanToInt(String s) {
        HashMap<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int total = 0;
        int prev = 0;
        for (int i = s.length()-1; i >=0 ; i--) {
            int curr = romanMap.get(s.charAt(i));
            if (prev <= curr) {
                total += curr;
            } else {
                total -= curr;
            }
            prev = curr;
        }
        return total;
    }
}

package com.gino.algorithm;

public class PlusOne {
    public static int[] plusOne(int[] digits){
        for (int i = digits.length-1; i >= 0; i--) {
            if(digits[i] == 9){
                digits[i] = 0;
            }else{
                digits[i] = digits[i] + 1;
                return digits;
            }
        }
        int[] newDigits = new int[digits.length+1];
        newDigits[0] = 1;
        System.arraycopy(digits, 0, newDigits, 1, digits.length);
        return newDigits;
    }

    public static void main(String[] args) {
        int[] test = {1,2,3,4};
        int[] ints = plusOne(test);
        System.out.println(ints);
    }
}

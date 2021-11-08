package com.gino.algorithm;

import java.math.BigInteger;

public class AddBinary {
//    public static String addBin(String a, String b){
//        return (new BigInteger(a, 2)).add(new BigInteger(b, 2)).toString(2);
//    }

    public static String addBin(String a, String b) {
        char[] aArray = a.toCharArray(), bArray = b.toCharArray();
        StringBuilder sb = new StringBuilder();

        int i = aArray.length - 1, j = bArray.length - 1, carry = 0;

        while (i >= 0 && j >= 0) {
            int sum = aArray[i] - '0' + bArray[j] - '0' + carry;

            if (sum >= 2) {
                sb.append(sum - 2);
                carry = 1;
            } else {
                sb.append(sum);
                carry = 0;
            }

            i--;
            j--;
        }


        while (i >= 0) {
            int sum = aArray[i] - '0' + carry;
            if (sum == 2) {
                sb.append(0);
                carry = 1;
            } else {
                sb.append(sum);
                carry = 0;
            }
            i--;
        }

        while (j >= 0) {
            int sum = bArray[j] - '0' + carry;
            if (sum == 2) {
                sb.append(0);
                carry = 1;
            } else {
                sb.append(sum);
                carry = 0;
            }
            j--;
        }

        if (carry != 0) {
            sb.append(carry);
        }


        return sb.reverse().toString();
    }

    public static void main(String[] args) {
//        String a = "11";
//        String b = "1";
//        System.out.println(addBin(a, b));

        System.out.println(4>>1);
    }

    public int mySqrt(int x) {
        if (x < 2)
            return x;
        if (x < 4)
            return 1;
        int low = 0;
        int high = x;
        int mid = 0;
        long sqr = 0;
        while (low <= high) {
            mid = low + ((high - low) >> 1);
            sqr = (long) mid * mid;
            if ((int) sqr == x)
                return mid;
            else if (sqr < x)
                low = mid;
            else
                high = mid;
            if (mid == low && mid == (high - 1))
                return mid;
        }
        return mid;
    }
}

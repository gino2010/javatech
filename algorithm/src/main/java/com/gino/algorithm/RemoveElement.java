package com.gino.algorithm;

import java.util.ArrayList;
import java.util.Arrays;

public class RemoveElement {
    public static int removeElement(int[] nums, int val) {
        if (nums.length == 0) {
            return 0;
        }
        int[] ints = Arrays.stream(nums).filter(n -> n != val).toArray();
        for (int i = 0; i < ints.length; i++) {
            nums[i] = ints[i];
        }
        return ints.length;
    }

    public static int removeElement2(int[] nums, int val) {
        if (nums.length == 0) {
            return 0;
        }
        ArrayList<Integer> integers = new ArrayList<>();
        for (int num : nums) {
            if (num != val) {
                integers.add(num);
            }
        }
        for (int i = 0; i < integers.size(); i++) {
            nums[i] = integers.get(i);
        }
        return integers.size();
    }

    public static void main(String[] args) {
        int[] data = new int[]{1, 2, 2, 3, 4, 5, 5};
        System.out.println(removeElement(data, 2));
        Arrays.stream(data).forEach(System.out::print);
    }
}

package com.gino.algorithm;

import java.util.Arrays;

public class RemoveDuplicates {
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] result = new int[nums.length];
        result[0] = nums[0];
        int size = 1;
        for (int i = 1; i < nums.length; i++) {
            if (result[size - 1] != nums[i]) {
                result[size] = nums[i];
                size++;
            }
        }
        System.arraycopy(result, 0, nums, 0, size);
        return size;
    }

    public static void main(String[] args) {
        int[] data = new int[]{1, 2, 2, 3, 4, 5, 5};
        System.out.println(removeDuplicates(data));
        Arrays.stream(data).forEach(System.out::print);
    }
}

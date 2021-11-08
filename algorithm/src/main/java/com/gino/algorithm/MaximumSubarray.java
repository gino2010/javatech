package com.gino.algorithm;

public class MaximumSubarray {
    public static int maxSubArray(int[] nums) {
        int sum = nums[0];
        int high = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum += nums[i];
            if (sum < nums[i]) {
                sum = nums[i];
            }

            if (sum > high) {
                high = sum;
            }

        }
        return high;
    }
}

package com.gino.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveDuplicates {
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        List<Integer> result = new ArrayList<>();
        result.add(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            if (result.get(result.size()-1) != nums[i]) {
                result.add(nums[i]);
            }
        }
        for (int i = 0; i < result.size(); i++) {
            nums[i] = result.get(i);
        }
        return result.size();
    }

    public static void main(String[] args) {
        int[] data = new int[]{1, 2, 2, 3, 4, 5, 5};
        System.out.println(removeDuplicates(data));
        Arrays.stream(data).forEach(System.out::print);
    }
}

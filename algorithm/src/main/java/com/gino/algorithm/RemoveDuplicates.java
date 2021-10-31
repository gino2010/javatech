package com.gino.algorithm;

import java.util.*;
import java.util.stream.Collectors;

public class RemoveDuplicates {
    public static int removeDuplicates(int[] nums) {
        Set<Integer> integerSet = Arrays.stream(nums).boxed().collect(Collectors.toSet());
        int[] ints = integerSet.stream().mapToInt(Integer::intValue).sorted().toArray();
        for(int i=0; i<ints.length; i++){
            nums[i] = ints[i];
        }
        return integerSet.size();
    }

    public static void main(String[] args) {
        int[] data = new int[]{1, 2, 2, 3, 4};
        System.out.println(removeDuplicates(data));
        Arrays.stream(data).forEach(System.out::print);
    }
}

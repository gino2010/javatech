package com.gino.algorithm;

import java.util.Scanner;

/**
 * Simple Bucket Sort 桶排序 简易版
 *
 * @author gino
 * Created on 2018/9/22
 * <p>
 * 这是一个非常好理解的排序算法，也很接近与现实人们的操作习惯。
 * 处理效率很高，典型的空间换时间，时间复杂度为O(n)
 * 什么时候用，最直观的感觉就是扑克牌类，手里的牌进行排序，你想想是不是这个道理。
 * <p>
 * 模拟，10以内的数字进行排序
 * <p>
 * for example: 1,2,9,1,5,7,6,3,4,8,8,2,3
 */
public class SimpleBucketSort {
    private static int[] bucket = new int[11];
    private static int[] data;

    /**
     * init bucket
     */
    private static void initBucket() {
        for (int i = 0; i < bucket.length; i++) {
            bucket[i] = 0;
        }
    }

    /**
     * sort
     */
    private static void sort() {
        // put in bucket
        for (int aData : data) {
            bucket[aData]++;
        }

        // out of bucket
        for (int i = 0; i < bucket.length; i++) {
            for (int j = 0; j < bucket[i]; j++) {
                System.out.print(i + " ");
            }
        }
    }

    public static void main(String[] args) {
        initBucket();

        // input data
        Scanner scanner = new Scanner(System.in);
        System.out.println("input your 0~9 number, split by comma: ");
        String number = scanner.next();
        String[] split = number.split(",");

        // transfer data to numbers
        data = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            data[i] = Integer.valueOf(split[i]);
        }

        sort();
    }
}

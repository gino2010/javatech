package com.gino.algorithm;

import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Bubble Sort 冒泡排序
 *
 * @author gino
 * Created on 2018/9/26
 *
 * 时间复杂度O(N^2)，这个算是非常慢的了，不推荐使用
 */
public class BubbleSort {
    private static int[] data;

    private static void sort() {
        for (int i = 1; i < data.length - 1; i++) {
            for (int j = 0; j < data.length - i; j++) {
                if (data[j] < data[j + 1]) {
                    // swap data
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }

        for (int temp : data) {
            System.out.print(temp + " ");
        }
    }

    public static void main(String[] args) {
        // input data
        Scanner scanner = new Scanner(System.in);
        System.out.println("input your 0~9 number, split by comma: ");
        String number = scanner.next();
        data = Stream.of(number.split(",")).mapToInt(Integer::parseInt).toArray();
        sort();
    }
}

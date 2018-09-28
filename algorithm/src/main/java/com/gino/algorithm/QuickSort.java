package com.gino.algorithm;

import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Quick Sort 快速排序
 *
 * @author gino
 * Created on 2018/9/26
 * <p>
 * 时间复杂度：最差O(N^2) 平均O(NlogN)
 * 性能非常好，值得推荐使用
 * <p>
 * inventor: Charles Antony Richard Hoare 1960
 */
public class QuickSort {
    private static int[] data;

    private static void sort(int left, int right) {
        int i, j, t, temp;

        if (left > right) {
            return;
        }

        temp = data[left];
        i = left;
        j = right;

        while (i != j) {
            while (data[j] >= temp && i < j) {
                j--;
            }

            while (data[i] <= temp && i < j) {
                i++;
            }

            if (i < j) {
                t = data[i];
                data[i] = data[j];
                data[j] = t;
            }
        }

        data[left] = data[i];
        data[i] = temp;

        sort(left, i - 1);
        sort(i + 1, right);
    }

    public static void main(String[] args) {
        // input data
        Scanner scanner = new Scanner(System.in);
        System.out.println("input your 0~9 number, split by comma: ");
        String number = scanner.next();
        data = Stream.of(number.split(",")).mapToInt(Integer::parseInt).toArray();
        sort(0, data.length - 1);
        for (int item : data) {
            System.out.print(item + " ");
        }
    }
}

package com.gino.algorithm;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 硬币问题
 *
 * @author gino
 * Created on 2021/6/1
 * <p>
 *     硬币种类有限，例如程序中为1，7，10，每个种类的个数无限
 *     给出一个指定金额，用最少的硬币来满足它
 *     难点在于，贪婪算法会有时拿不到最优解，应该需要考虑动态规划
 *     此算法为递归思路不是动态规划，但是解题很巧妙
 */
public class Coin {

    public static List<List<Integer>> solutions = new ArrayList<>();

    public int count(List<Integer> coins, int num, int charge, List<Integer> solution) {
        if (charge == 0) {
            solutions.add(solution);
//            solution.stream().map(x -> x + " ").forEach(System.out::print);
//            System.out.println(Arrays.toString(solution.toArray()));
            return 1;
        }
        if (charge < 0) {
            return 0;
        }
        if (num <= 0 && charge >= 1) {
            return 0;
        }

        List<Integer> copy = new ArrayList<>(solution);
        solution.add(coins.get(num - 1));
        return count(coins, num - 1, charge, copy) + count(coins, num, charge - coins.get(num - 1), solution);
    }

    public static void main(String[] args) {
        System.out.print("Enter your charge: ");
        Scanner scanner = new Scanner(System.in);
        int charge = scanner.nextInt();
        Coin coin = new Coin();
        List<Integer> coins = Arrays.asList(1, 7, 10);

        System.out.println(coin.count(coins, coins.size(), charge, new ArrayList<>()));

        System.out.println(Arrays.toString(solutions.toArray()));
        List<Integer> min = solutions.stream().min(Comparator.comparingInt(List::size)).orElse(new ArrayList<>());
        System.out.println(Arrays.toString(min.toArray()));

        List<List<Integer>> collect = solutions.stream().min(Comparator.comparingInt(List::size)).stream().collect(Collectors.toList());
        System.out.println(Arrays.toString(collect.toArray()));

        System.out.println(solutions.stream().mapToInt(List::size).min().orElse(0));
    }
}
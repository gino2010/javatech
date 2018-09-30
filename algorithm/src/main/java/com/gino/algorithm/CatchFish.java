package com.gino.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * 钓鱼 模拟
 *
 * @author gino
 * Created on 2018/9/29
 * <p>
 * 扑克牌钓鱼游戏大家应该都不陌生吧，两人依次出牌，遇到相同的牌，就拿走两牌之间所有的牌，最后谁没有牌了就输了。
 * 那么有点数学尝试的人，都应该知道，其实在拿到牌时两人的胜负已经确定，你们只是执行了演算过程而已。
 * 是不是有点无聊呢，小朋友有时就是喜欢这样无聊的游戏，呵呵～～～
 * <p>
 * 如果手中的牌全部用数组维护会比较麻烦，也没有什么实用的意义。所以还是实用arraylist比较方便。发牌用了数组，毕竟数量是明确的。
 * 这个程序稍加改动就可以支持多幅牌，这里我就不写了。
 * <p>
 * 程序难度极低，有基本顺序执行流程思维加上一些小心思考，都可以实现。
 * 如果哪里我写错了，请告诉我，那绝对是我犯二了
 */
public class CatchFish {

    private static final int POKER_CARD_NUM = 54;
    private int[] cards;
    private int players;
    private ArrayList<Integer>[] playerQueues;
    private ArrayList<Integer> desktopStack;

    // 初始化游戏
    private boolean initGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(String.format("How many players? (No more than %s) : ", POKER_CARD_NUM));
        this.players = scanner.nextInt();
        if (this.players > POKER_CARD_NUM) {
            System.out.println("More players, game can not be start!");
            return false;
        }

        // 初始化扑克牌，1～13，不分花色
        this.cards = new int[POKER_CARD_NUM];
        for (int i = 0; i < POKER_CARD_NUM - 2; i++) {
            this.cards[i] = i % 13 + 1;
        }
        // 将大小王作为一对设置为0
        this.cards[52] = this.cards[53] = 0;

        // 初始化玩家
        this.playerQueues = new ArrayList[players];
        for (int i = 0; i < players; i++) {
            this.playerQueues[i] = new ArrayList<>();
        }

        // 初始化桌面
        this.desktopStack = new ArrayList<>();
        return true;
    }

    // 发牌
    private void dealCard() {
        // 发牌，54张牌随机发出
        int left = POKER_CARD_NUM - 1;
        Random random = new Random();
        for (int i = 0; i < POKER_CARD_NUM; i++) {
            int whichCard = random.nextInt(left + 1);
            playerQueues[i % players].add(cards[whichCard]);
            cards[whichCard] = cards[left];

            // 打个标记便于观察，可以不执行
            cards[left] = -1;

            left--;
        }
    }

    // 模拟玩的过程
    private void play() {
        int leftPlayers = players;
        int i = 0;
        while (leftPlayers > 1) {
            ArrayList<Integer> currentPlayer = playerQueues[i % players];
            i++;

            // 玩家是否还有牌
            if (currentPlayer.size() != 0) {
                int tempCard = currentPlayer.remove(0);
                // 出牌
                int index = desktopStack.indexOf(tempCard);
                desktopStack.add(tempCard);

                // 判断是否有相同的牌
                if (index != -1) {
                    List<Integer> fishes = desktopStack.subList(index, desktopStack.size());
                    currentPlayer.addAll(fishes);
                    desktopStack.subList(index, desktopStack.size()).clear();
                }
            } else {
                continue;
            }

            // 如果手中无牌，则推出比赛
            if (currentPlayer.size() == 0) {
                leftPlayers--;
            }
        }
    }

    // 结果输出
    private void result() {
        for (int i = 0; i < players; i++) {
            if (playerQueues[i].size() != 0) {
                System.out.println(String.format("Players %d is winner!", i));
            }
        }
    }

    public static void main(String[] args) {
        CatchFish catchFish = new CatchFish();
        if (catchFish.initGame()) {
            catchFish.dealCard();
            // 玩家手中的牌输出
            System.out.println("output player's cards: ");
            for (ArrayList<Integer> temp : catchFish.playerQueues) {
                for (Integer i : temp) {
                    System.out.print(i + " ");
                }
                System.out.println();
            }
            catchFish.play();
            catchFish.result();
        }
    }
}

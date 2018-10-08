package com.gino.algorithm;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 自定义数字排序
 *
 * @author gino
 * Created on 2018/10/8
 * <p>
 * 假设一组数字， 463175892 对其进行解密排序后 615947283
 * 解密规则规则，移走第一个数字到末尾，取第二个数字，往复，直到全部取出。取出的数字序列则为结果
 * 加密规则，与其逻辑相反
 *
 * 使用arraylist完成此逻辑
 */
public class CustomSort {
    private static ArrayList<Character> strToList(String text) {
        return text.chars().mapToObj((i) -> (char) i).collect(Collectors.toCollection(ArrayList::new));
    }

    public static String encrypt(String text) {
        ArrayList<Character> textList = strToList(text);
        ArrayList<Character> tempList = new ArrayList<>();
        while (textList.size() != 0) {
            tempList.add(0, textList.remove(textList.size() - 1));
            tempList.add(0, tempList.remove(tempList.size() - 1));
        }
        return tempList.stream().map(Object::toString).reduce((acc, e) -> acc + e).get();
    }

    public static String decrypt(String text) {
        ArrayList<Character> textList = strToList(text);
        ArrayList<Character> tempList = new ArrayList<>();

        while (textList.size() != 0) {
            textList.add(textList.remove(0));
            tempList.add(textList.remove(0));
        }
        return tempList.stream().map(Object::toString).reduce((acc, e) -> acc + e).get();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("input your text");
        String text = scanner.next();
        System.out.println(String.format("encrypt text: %s", encrypt(text)));
        System.out.println(String.format("original text: %s", decrypt(encrypt(text))));
    }
}

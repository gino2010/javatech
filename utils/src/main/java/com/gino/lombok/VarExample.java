package com.gino.lombok;

import lombok.val;
import lombok.var;

import java.util.ArrayList;

/**
 * @author gino
 * Created on 2018/7/4
 */
public class VarExample {
    public static void main(String[] args) {
        val example = new ArrayList<String>();
        final ArrayList<String> jExample = new ArrayList<>();

        //native support in Java 10
        var varExmaple = new ArrayList<String>();
        ArrayList<String> jVarExample = new ArrayList<>();
    }
}

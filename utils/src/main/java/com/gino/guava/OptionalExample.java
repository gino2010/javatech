package com.gino.guava;

import java.util.Optional;

/**
 * @author gino
 * Created on 2018/7/4
 */
public class OptionalExample {
    public static void main(String[] args) {
        Optional<Integer> possible = Optional.of(5);
        possible.isPresent();
    }
}

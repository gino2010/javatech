package com.gino.lombok;

import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;

/**
 * @author gino
 * Created on 2018/7/4
 */
public class SneakyThrowsExample {
    @SneakyThrows(UnsupportedEncodingException.class)
    public String utf8ToString(byte[] bytes) {
        return new String(bytes, "UTF-8");
    }

    @SneakyThrows
    public void run() {
        throw new Throwable();
    }
}

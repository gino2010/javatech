package com.gino.aio;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

/**
 * @author gino
 * Created on 2018/6/28
 */
public class Client {
    private AsynchronousSocketChannel asc;

    public Client() throws Exception {
        asc = AsynchronousSocketChannel.open();
    }

    public void connect() {
        try {
            asc.connect(new InetSocketAddress("127.0.0.1", 8765)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void write(String request) {
        try {
            asc.write(ByteBuffer.wrap(request.getBytes())).get();
            read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void read() {
        ByteBuffer buf = ByteBuffer.allocate(1024);
        try {
            asc.read(buf).get();
            buf.flip();
            byte[] respByte = new byte[buf.remaining()];
            buf.get(respByte);
            System.out.println(new String(respByte, "utf-8").trim());
        } catch (InterruptedException | ExecutionException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Client c1 = new Client();
        Client c2 = new Client();
        Client c3 = new Client();

        new Thread(() -> {
            c1.connect();
            c1.write("c1 aaa");
        }).start();

        new Thread(() -> {
            c2.connect();
            c2.write("c2 bbbb");
        }).start();

        new Thread(() -> {
            c3.connect();
            c3.write("c3 ccccc");
        }).start();
    }
}

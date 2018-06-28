package com.gino.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author gino
 * Created on 2018/6/28
 */
public class Server implements Runnable {
    private Selector selector;
    private ByteBuffer readBuf = ByteBuffer.allocate(1024);
    private ServerSocketChannel ssc;

    public Server(int port) {
        try {
            this.selector = Selector.open();
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(port));
            ssc.register(this.selector, SelectionKey.OP_ACCEPT);

            System.out.println("Server start, port :" + port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.selector.select();
                Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            this.accept(key);
                            continue;
                        }
                        if (key.isReadable()) {
                            this.read(key);
                            continue;
                        }
                        if (key.isWritable()) {
                            this.write(key);
                            continue;
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void accept(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel sc = ssc.accept();
            sc.configureBlocking(false);
            sc.register(this.selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read(SelectionKey key) {
        try {
            this.readBuf.clear();
            SocketChannel sc = (SocketChannel) key.channel();
            int count = sc.read(this.readBuf);
            if (count == -1) {
                key.channel().close();
                key.cancel();
                ssc.register(this.selector, SelectionKey.OP_ACCEPT);
                return;
            }
            this.readBuf.flip();
            byte[] bytes = new byte[this.readBuf.remaining()];
            this.readBuf.get(bytes);
            String body = new String(bytes).trim();
            System.out.println("Server : " + body);

            sc.register(this.selector, SelectionKey.OP_WRITE);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void write(SelectionKey key) {
        SocketChannel sc = (SocketChannel) key.channel();
        try {
            sc.write(ByteBuffer.wrap("server back".getBytes()));
            sc.register(this.selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new Server(8765)).start();
    }


}

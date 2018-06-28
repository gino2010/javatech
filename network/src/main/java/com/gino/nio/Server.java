package com.gino.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author gino
 * Created on 2018/6/28
 */
public class Server implements Runnable {
    private Selector seletor;
    private ByteBuffer readBuf = ByteBuffer.allocate(1024);
    private ByteBuffer writeBuf = ByteBuffer.allocate(1024);

    public Server(int port) {
        try {
            this.seletor = Selector.open();
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(port));
            ssc.register(this.seletor, SelectionKey.OP_ACCEPT);

            System.out.println("Server start, port :" + port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.seletor.select();
                Iterator<SelectionKey> keys = this.seletor.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            this.accept(key);
                        }
                        if (key.isReadable()) {
                            this.read(key);
                        }
                        if (key.isWritable()) {
                            this.write(key);
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void write(SelectionKey key) {
        SocketChannel sc = (SocketChannel) key.channel();
        try {
            sc.write(ByteBuffer.wrap("server back".getBytes()));
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
                return;
            }
            this.readBuf.flip();
            byte[] bytes = new byte[this.readBuf.remaining()];
            this.readBuf.get(bytes);
            String body = new String(bytes).trim();
            System.out.println("Server : " + body);

            // 9..可以写回给客户端数据
            sc.register(this.seletor, SelectionKey.OP_WRITE);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void accept(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel sc = ssc.accept();
            sc.configureBlocking(false);
            sc.register(this.seletor, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new Server(8765)).start();
    }


}

package com.gino.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author gino
 * Created on 2018/6/28
 */
public class Client {
    public static void main(String[] args) {

        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8765);

        SocketChannel sc = null;

        ByteBuffer buf = ByteBuffer.allocate(1024);

        try {
            sc = SocketChannel.open();
            sc.connect(address);

            while (true) {
                byte[] bytes = new byte[1024];
                System.in.read(bytes);

                buf.put(bytes);
                buf.flip();
                sc.write(buf);
                buf.clear();
                sc.read(buf);
                buf.flip();
                System.out.println(new String(buf.array(),0,buf.limit()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sc != null) {
                try {
                    sc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

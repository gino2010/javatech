package com.gino.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author gino
 * Created on 2018/6/28
 */
public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {


    @Override
    public void completed(AsynchronousSocketChannel asc, Server attachment) {
        attachment.assc.accept(attachment, this);
        read(asc);
    }

    private void read(final AsynchronousSocketChannel asc) {
        ByteBuffer buf = ByteBuffer.allocate(1024);
        asc.read(buf, buf, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer resultSize, ByteBuffer attachment) {
                attachment.flip();
                System.out.println("Server recv data size: " + resultSize);
                String resultData = new String(attachment.array()).trim();
                System.out.println("Server recv data: " + resultData);
                String response = "Server back: " + resultData;
                write(asc, response);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });
    }

    private void write(AsynchronousSocketChannel asc, String response) {
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(response.getBytes());
        buf.flip();
        asc.write(buf);
    }

    @Override
    public void failed(Throwable exc, Server attachment) {
        exc.printStackTrace();
    }
}
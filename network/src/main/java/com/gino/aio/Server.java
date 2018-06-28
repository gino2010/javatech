package com.gino.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gino
 * Created on 2018/6/28
 */
public class Server {
    private ExecutorService executorService;
    private AsynchronousChannelGroup threadGroup;
    public AsynchronousServerSocketChannel assc;

    public Server(int port) {
        try {
            executorService = Executors.newCachedThreadPool();
            threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
            assc = AsynchronousServerSocketChannel.open(threadGroup);
            assc.bind(new InetSocketAddress(port));
            System.out.println("server start , port : " + port);
            assc.accept(this, new ServerCompletionHandler());

            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server(8765);
    }
}

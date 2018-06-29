package com.gino.netty.helloworld;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author gino
 * Created on 2018/6/28
 */
public class Client {
    public static void main(String[] args) throws Exception {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MyClientHandler());
                    }
                });
        ChannelFuture future = bootstrap.connect("127.0.0.1", 8765).sync();
        future.channel().writeAndFlush(Unpooled.copiedBuffer("client".getBytes()));


        future.channel().closeFuture().sync();
        workGroup.shutdownGracefully();
    }

}

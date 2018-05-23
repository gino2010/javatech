package com.gino.grpc.example.helloworldtls;

import com.gino.grpc.example.helloworld.GreeterGrpc;
import com.gino.grpc.example.helloworld.HelloReply;
import com.gino.grpc.example.helloworld.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import javax.net.ssl.SSLException;
import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tls 加密的rpc通讯
 *
 * @author gino
 * Created on 2018/5/22
 */
public class HelloWorldClientTls {
    public static final Logger logger = Logger.getLogger(HelloWorldClientTls.class.getName());
    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    private static SslContext buildSslContext(String trustCertCollectionFilePath,
                                              String clientCertChainFilePath,
                                              String clientPrivateKeyFilePath) throws SSLException {
        SslContextBuilder builder = GrpcSslContexts.forClient();
        if (trustCertCollectionFilePath != null) {
            builder.trustManager(new File(trustCertCollectionFilePath));
        }
        if (clientCertChainFilePath != null && clientPrivateKeyFilePath != null) {
            builder.keyManager(new File(clientCertChainFilePath), new File(clientPrivateKeyFilePath));
        }
        return builder.build();
    }

    public HelloWorldClientTls(String host, int port, SslContext sslContext) throws SSLException {
        this(NettyChannelBuilder.forAddress(host, port)
                .negotiationType(NegotiationType.TLS)
                .sslContext(sslContext).build());
    }

    public HelloWorldClientTls(ManagedChannel channel) {
        this.channel = channel;
        this.blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void greet(String name) {
        logger.info("Will try to greet " + name + " ...");
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response;
        try {
            response = blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Greeting: " + response.getMessage());
    }

    public static void main(String[] args) throws Exception {
        //Prevent to get IPV6 address,this way only work in debug mode
        //But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
        System.setProperty("java.net.preferIPv4Stack", "true");

        if (args.length < 2 || args.length == 4 || args.length > 5) {
            System.out.println("USAGE: HelloWorldClientTls host port [trustCertCollectionFilePath] " +
                    "[clientCertChainFilePath] [clientPrivateKeyFilePath]\n  Note: clientCertChainFilePath and " +
                    "clientPrivateKeyFilePath are only needed if mutual auth is desired. And if you specify " +
                    "clientCertChainFilePath you must also specify clientPrivateKeyFilePath");
            System.exit(0);
        }

        HelloWorldClientTls client;
        switch (args.length) {
            case 2:
                client = new HelloWorldClientTls(args[0], Integer.parseInt(args[1]),
                        buildSslContext(null, null, null));
                break;
            case 3:
                client = new HelloWorldClientTls(args[0], Integer.parseInt(args[1]),
                        buildSslContext(args[2], null, null));
                break;
            default:
                client = new HelloWorldClientTls(args[0], Integer.parseInt(args[1]),
                        buildSslContext(args[2], args[3], args[4]));
        }

        try {
            /* Access a service running on the local machine on port 50051 */
            String user = "world";
            if (args.length > 0) {
                user = args[0]; /* Use the arg as the name to greet if provided */
            }
            client.greet(user);
        } finally {
            client.shutdown();
        }
    }
}

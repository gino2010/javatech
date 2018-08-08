package com.gino.chatrom.server;

import com.gino.chatrom.Chat;
import com.gino.chatrom.ChatServiceGrpc;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gino
 * Created on 2018/8/6
 */
@GRpcService
public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {
    // @aiborisov mentioned this needs to be thread safe. It was using non-thread-safe HashSet
    private static Set<StreamObserver<Chat.ChatMessageFromServer>> observers = ConcurrentHashMap.newKeySet();
//      Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Override
    public StreamObserver<Chat.ChatMessage> chat(StreamObserver<Chat.ChatMessageFromServer> responseObserver) {
        observers.add(responseObserver);

        return new StreamObserver<Chat.ChatMessage>() {
            @Override
            public void onNext(Chat.ChatMessage value) {
                System.out.println(value);
                Chat.ChatMessageFromServer message = Chat.ChatMessageFromServer.newBuilder()
                        .setMessage(value)
                        .setTimestamp(Timestamp.newBuilder().setSeconds(System.currentTimeMillis() / 1000))
                        .build();

                for (StreamObserver<Chat.ChatMessageFromServer> observer : observers) {
                    observer.onNext(message);
                }
            }

            @Override
            public void onError(Throwable t) {
                // do something;
            }

            @Override
            public void onCompleted() {
                observers.remove(responseObserver);
            }
        };
    }
}

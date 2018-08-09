package com.gino.chatrom.server;

import com.gino.chatrom.Chat;
import com.gino.chatrom.ChatServiceGrpc;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gino
 * Created on 2018/8/6
 */
@Slf4j
@GRpcService
public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {
    private static Set<StreamObserver<Chat.ChatMessageFromServer>> observers = ConcurrentHashMap.newKeySet();
    private static ConcurrentHashMap<String, StreamObserver<Chat.ChatMessageFromServer>> loginObservers = new ConcurrentHashMap<>();

    @Override
    public StreamObserver<Chat.ChatMessage> chat(StreamObserver<Chat.ChatMessageFromServer> responseObserver) {
        observers.add(responseObserver);

        return new StreamObserver<Chat.ChatMessage>() {
            @Override
            public void onNext(Chat.ChatMessage value) {
                log.info(value.toString());
                Chat.ChatMessageFromServer.Builder builder = Chat.ChatMessageFromServer.newBuilder();

                // some one login
                if ("login".equals(value.getMessage())) {
                    loginObservers.put(value.getFrom(), responseObserver);
                    observers.remove(responseObserver);
                    Chat.ChatMessage loginMessage = Chat.ChatMessage.newBuilder()
                            .setFrom("system")
                            .setMessage(value.getFrom() + " login")
                            .build();
                    Chat.ChatMessageFromServer message = builder
                            .setMessage(loginMessage)
                            .setTimestamp(Timestamp.newBuilder().setSeconds(System.currentTimeMillis() / 1000))
                            .build();
                    broadcast(message);
                } else {

                    if (observers.contains(responseObserver)) {
                        Chat.ChatMessage login_message = Chat.ChatMessage.newBuilder()
                                .setFrom("system")
                                .setMessage("login first")
                                .build();

                        Chat.ChatMessageFromServer message = builder
                                .setMessage(login_message)
                                .setTimestamp(Timestamp.newBuilder().setSeconds(System.currentTimeMillis() / 1000))
                                .build();

                        responseObserver.onNext(message);
                        return;
                    }
                    Chat.ChatMessageFromServer message = builder
                            .setMessage(value)
                            .setTimestamp(Timestamp.newBuilder().setSeconds(System.currentTimeMillis() / 1000))
                            .build();
                    if (value.getMessage().startsWith("@")) {
                        // for someone message
                        someone(message, value.getMessage().split(" ")[0].substring(1), responseObserver);
                    } else {
                        // normal message broadcast
                        broadcast(message);
                    }
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

            private void broadcast(Chat.ChatMessageFromServer message) {
                for (StreamObserver<Chat.ChatMessageFromServer> observer : loginObservers.values()) {
                    observer.onNext(message);
                }
            }

            private void someone(Chat.ChatMessageFromServer message, String one, StreamObserver<Chat.ChatMessageFromServer> selfObserver) {
                loginObservers.get(one).onNext(message);
                selfObserver.onNext(message);
            }
        };
    }
}

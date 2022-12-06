package ru.otus.client;

import ru.otus.message.Message;
import ru.otus.message.MessageType;

public interface MsClient {

    <T extends ResultDataType> boolean sendMessage(Message<T> msg);

    <T extends ResultDataType> void handle(Message<T> msg);

    String getName();

    <T extends ResultDataType> Message<T> produceMessage(String to, T data, MessageType msgType, MessageCallback<T> callback);
}

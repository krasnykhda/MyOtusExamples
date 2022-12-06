package ru.otus;

import ru.otus.client.MsClient;
import ru.otus.client.ResultDataType;
import ru.otus.message.Message;

public interface MessageSystem {

    void addClient(MsClient msClient);

    void removeClient(String clientId);

    <T extends ResultDataType> boolean newMessage(Message<T> msg);

    void dispose() throws InterruptedException;

    void dispose(Runnable callback) throws InterruptedException;

    void start();

    int currentQueueSize();
}


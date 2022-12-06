package ru.otus;

import ru.otus.message.MessageType;

public interface HandlersStore {
    RequestHandler getHandlerByType(MessageType messageType);

    void addHandler(MessageType messageType, RequestHandler handler);
}

package ru.otus;


import ru.otus.client.ResultDataType;
import ru.otus.message.Message;

import java.util.Optional;


public interface RequestHandler {
    <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg);
}

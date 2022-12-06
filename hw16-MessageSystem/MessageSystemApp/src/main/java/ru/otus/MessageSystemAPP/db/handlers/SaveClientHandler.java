package ru.otus.MessageSystemAPP.db.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.MessageSystemAPP.dto.ClientData;
import ru.otus.MessageSystemAPP.crm.service.DBServiceClient;
import ru.otus.RequestHandler;
import ru.otus.client.ResultDataType;
import ru.otus.message.Message;
import ru.otus.message.MessageBuilder;
import ru.otus.message.MessageType;

import java.util.Optional;

@Component
public class SaveClientHandler implements RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(SaveClientHandler.class);

    private final DBServiceClient dbServiceClient;

    @Autowired
    public SaveClientHandler(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }


    @Override
    public <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg) {
        var clientData = (ClientData) msg.getData();
        dbServiceClient.saveClient(clientData.clients().get(0));
        return Optional.of(MessageBuilder.buildReplyMessage(msg, (T) clientData));
    }
}

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
public class GetClientsHandler implements RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(GetClientsHandler.class);

    private final DBServiceClient dbServiceClient;

    @Autowired
    public GetClientsHandler(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg) {
        try {
            ClientData clientData = new ClientData(dbServiceClient.findAll());
            return Optional.of(MessageBuilder.buildReplyMessage(msg, (T) clientData));
        } catch (Exception e) {
            logger.error("failed to get clients list", e);
        }
        return Optional.empty();
    }
}

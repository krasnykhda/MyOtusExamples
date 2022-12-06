package ru.otus.MessageSystemAPP;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.otus.*;
import ru.otus.MessageSystemAPP.crm.service.DBServiceClient;
import ru.otus.MessageSystemAPP.db.handlers.GetClientsHandler;
import ru.otus.MessageSystemAPP.db.handlers.SaveClientHandler;
import ru.otus.MessageSystemAPP.front.handlers.GetClientDataResponseHandler;
import ru.otus.client.MsClient;
import ru.otus.client.MsClientImpl;
import ru.otus.message.MessageType;

import java.time.format.DateTimeFormatter;

@Configuration
public class ApplConfig {
    @Bean
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }
    @Bean
    @Scope(value = "prototype")
    public HandlersStore requestHandlerDatabaseStore() {
        return new HandlersStoreImpl();
    }
    @Bean("dbClient")
    public MsClient dbMsClient(MessageSystem messageSystem, HandlersStore requestHandlerDatabaseStore
            , DBServiceClient dbServiceClient) {
        requestHandlerDatabaseStore.addHandler(MessageType.GET_CLIENTS, new GetClientsHandler(dbServiceClient));
        requestHandlerDatabaseStore.addHandler(MessageType.ADD_CLIENT, new SaveClientHandler(dbServiceClient));
        MsClient msClient = new MsClientImpl("dbClient", messageSystem, requestHandlerDatabaseStore);
        messageSystem.addClient(msClient);
        return msClient;
    }

    @Bean("frontClient")
    public MsClient frontMsClient(MessageSystem messageSystem, HandlersStore requestHandlerDatabaseStore) {
        RequestHandler requestHandler = new GetClientDataResponseHandler();
        requestHandlerDatabaseStore.addHandler(MessageType.ADD_CLIENT, requestHandler);
        requestHandlerDatabaseStore.addHandler(MessageType.GET_CLIENTS, requestHandler);
        MsClient msClient = new MsClientImpl("frontClient", messageSystem, requestHandlerDatabaseStore);
        messageSystem.addClient(msClient);
        return msClient;
    }
}

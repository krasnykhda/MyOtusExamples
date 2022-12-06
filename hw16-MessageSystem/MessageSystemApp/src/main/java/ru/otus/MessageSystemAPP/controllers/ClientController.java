package ru.otus.MessageSystemAPP.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.MessageSystemAPP.crm.model.Adress;
import ru.otus.MessageSystemAPP.crm.model.Client;
import ru.otus.MessageSystemAPP.crm.service.DBServiceClient;
import ru.otus.MessageSystemAPP.dto.ClientData;
import ru.otus.client.MsClient;
import ru.otus.message.Message;
import ru.otus.message.MessageType;

import java.util.List;

@Controller
public class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final MsClient frontMsClient;
    private final MsClient dbMsClient;
    private final DBServiceClient clientService;
    private final SimpMessagingTemplate template;

    public ClientController(@Qualifier("frontClient") MsClient frontMsClient,@Qualifier("dbClient") MsClient dbMsClient, DBServiceClient clientService, SimpMessagingTemplate template) {
        this.frontMsClient = frontMsClient;
        this.dbMsClient = dbMsClient;
        this.clientService = clientService;
        this.template = template;
    }

    @MessageMapping("/clients")
    public void clients() {
        logger.info("get clients");
        Message outMsg = frontMsClient.produceMessage(
                dbMsClient.getName(),
                ClientData.empty(),
                MessageType.GET_CLIENTS,
                resultDataType -> template.convertAndSend("/topic/response", resultDataType.clients())
        );
        frontMsClient.sendMessage(outMsg);

    }

    @MessageMapping("/createClient")
    public void createUser(Client client) {
        logger.info("create client {}", client);
        Message outMsg = frontMsClient.produceMessage(
                dbMsClient.getName(),
                ClientData.fromOne(client),
                MessageType.ADD_CLIENT,
                resultDataType -> clients()
        );
        frontMsClient.sendMessage(outMsg);
       }

}

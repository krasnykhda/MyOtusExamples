package ru.otus.MessageSystemAPP.crm.service;

import ru.otus.MessageSystemAPP.crm.model.Client;

import java.util.List;

public interface DBServiceClient {

    Client saveClient(Client client);
    List<Client> findAll();
}

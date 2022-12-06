package ru.otus.MessageSystemAPP.dto;

import ru.otus.MessageSystemAPP.crm.model.Client;
import ru.otus.client.ResultDataType;

import java.util.Collections;
import java.util.List;

public class ClientData implements ResultDataType {

    private final List<Client> clients;

    public ClientData(List<Client> clients) {
        this.clients = clients;
    }

    public static ClientData empty() {
        return new ClientData(Collections.emptyList());
    }

    public static ClientData fromOne(Client client) {
        return new ClientData(Collections.singletonList(client));
    }

    public List<Client> clients() {
        return clients;
    }
}

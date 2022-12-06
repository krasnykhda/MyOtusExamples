package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.model.Client;

import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl<T extends Client> implements DBServiceClient<T> {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<T> clientDataTemplate;
    private final TransactionRunner transactionRunner;

    public DbServiceClientImpl(TransactionRunner transactionRunner, DataTemplate<T> clientDataTemplate) {
        this.transactionRunner = transactionRunner;
        this.clientDataTemplate = clientDataTemplate;
    }

    @Override
    public T saveClient(T client) {

        return transactionRunner.doInTransaction(connection -> {
            if (client.getId() == null) {
                var clientId = clientDataTemplate.insert(connection, client);
                //var createdClient = new Client(clientId, client.getName());
                var createdClient = client.clone(clientId,client);
                log.info("created client: {}", createdClient);
                return (T) createdClient;
            }
            clientDataTemplate.update(connection,client);
            log.info("updated client: {}", client);
            return (T) client;
        });
    }

    /*@Override
    public Optional<T> getClient(long id) {
        return transactionRunner.doInTransaction(connection -> {
            var clientOptional = clientDataTemplate.findById(connection, id);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public List<T> findAll() {
        return transactionRunner.doInTransaction(connection -> {
            var clientList = clientDataTemplate.findAll(connection);
            log.info("clientList:{}", clientList);
            return clientList;
       });
    }*/
}

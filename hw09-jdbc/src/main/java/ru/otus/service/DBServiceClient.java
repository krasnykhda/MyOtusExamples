package ru.otus.service;

import ru.otus.model.Client;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient<T> {

    T saveClient(T client);

  /*  Optional<Client> getClient(long id);

    List<Client> findAll();*/
}

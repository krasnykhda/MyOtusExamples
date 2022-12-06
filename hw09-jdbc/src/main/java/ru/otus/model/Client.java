package ru.otus.model;

import ru.otus.jdbc.mapper.Guid;
public class Client {
    @Guid
    private Long id;
    private String name;


    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }
    public Client(Long id,Client client) {
        this.id = id;
        this.name = client.getName();
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Client(Object id, Object name) {
        this.id = (Long)id;
        this.name = (String) name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    public Client clone(Long id,Client client){
        return new Client(id,client);
    }
}

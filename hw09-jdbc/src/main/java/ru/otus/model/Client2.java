package ru.otus.model;

public class Client2 extends Client {
    public Client2() {
        super();
    }

    public Client2(String name) {
        super(name);
    }

    public Client2(Long id, String name) {
        super(id, name);
    }

    public Client2(Object id, Object name) {
        super(id, name);
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

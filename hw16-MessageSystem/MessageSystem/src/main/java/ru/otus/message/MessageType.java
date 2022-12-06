package ru.otus.message;

public enum MessageType {
    VOID_MESSAGE("voidMessage"),
    GET_CLIENTS("clientsList"),
    ADD_CLIENT("newClient");


    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

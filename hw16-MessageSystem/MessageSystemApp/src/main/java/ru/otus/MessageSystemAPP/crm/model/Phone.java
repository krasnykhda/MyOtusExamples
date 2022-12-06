package ru.otus.MessageSystemAPP.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Table("phone")
public class Phone {
    @Id
    private Long id;
    @Nonnull
    private Long clientId;
    @Nonnull
    private String number;

    public Long getId() {
        return id;
    }


    public String getNumber() {
        return number;
    }


    public Long getClientId() {
        return clientId;
    }

    public void setNumber(@Nonnull String number) {
        this.number = number;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClientId(@Nonnull Long clientId) {
        this.clientId = clientId;
    }

    @PersistenceConstructor
    public Phone(Long id, String number, Long clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }

    public Phone(){

    }

    @Nonnull
    public Phone(String number, Long clientId) {
        this(null, number, clientId);
    }

}

package ru.otus.MessageSystemAPP.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Table("adress")
public class Adress {
    @Id
    private Long id;
    @Nonnull
    private String street;
    @Nonnull
    private Long clientId;

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }


    public Long getClientId() {
        return clientId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Adress() {
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Adress(String street, Long clientId) {
        this(null,street,clientId);
    }

    @PersistenceConstructor
    public Adress(Long id, String street, Long clientId) {
        this.id = id;
        this.street = street;
        this.clientId = clientId;
    }
}

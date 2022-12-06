package ru.otus.MessageSystemAPP.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.otus.client.ResultDataType;

import javax.annotation.Nonnull;
import java.util.Set;


@Table("client")
public class Client {

    @Id
    private Long id;
    @Nonnull
    private String name;
    @MappedCollection(idColumn = "client_id")
    private Adress adress;
    @MappedCollection(idColumn = "client_id")
    private Set<Phone> phones;

    @Nonnull
    public Client(String name, Adress adress, Set<Phone> phones) {
        this(null, name, adress, phones);
    }

    @PersistenceConstructor
    public Client(Long id, String name, Adress adress, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.phones = phones;

    }

    public Client() {
    }

    public Client(@Nonnull String name) {
        this.name = name;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

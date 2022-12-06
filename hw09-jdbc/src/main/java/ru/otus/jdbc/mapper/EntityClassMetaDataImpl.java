package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Constructor<T> constructor;
    private final String name;
    private Field id;
    private final List<Field> allFields = new ArrayList<>();
    private final List<Field> fieldsWithoutId = new ArrayList<>();

    public EntityClassMetaDataImpl(Class<T> clazz) {

        var fields = clazz.getDeclaredFields();
        var params = new Class[fields.length];
        this.name = clazz.getSimpleName();
        for (int i = 0; i < fields.length; i++) {
            params[i] = Object.class;

            if (fields[i].isAnnotationPresent(Guid.class)) {
                id = fields[i];
                allFields.add(fields[i]);
            } else {
                allFields.add(fields[i]);
                fieldsWithoutId.add(fields[i]);
            }
        }
        try {
            this.constructor = clazz.getConstructor(params);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public Field getIdField() {
        return id;
    }

    public void setId(Field id) {
        this.id = id;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }


    @Override
    public List<Field> getAllFields() {

        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }

}

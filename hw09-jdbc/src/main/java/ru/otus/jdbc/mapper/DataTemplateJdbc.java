package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.model.Client;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    List<Field> fields = entitySQLMetaData.getMetaData().getAllFields();
                    Object[] params = new Object[fields.size()];
                    for (int i = 0; i < fields.size(); i++) {
                        params[i] = rs.getObject(fields.get(i).getName());
                    }
                    return (T) entitySQLMetaData.getMetaData().getConstructor().newInstance(params);
                }
            } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new DataTemplateException(e);
            }
            return null;
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var clientList = new ArrayList<T>();
            try {
                int fieldsCount = entitySQLMetaData.getMetaData().getAllFields().size();
                while (rs.next()) {
                    Object[] params = new Object[fieldsCount];
                    for (int i = 0; i < fieldsCount; i++) {
                        params[i] = rs.getObject(i + 1);
                    }
                    clientList.add((T) entitySQLMetaData.getMetaData().getConstructor().newInstance(params));
                }
                return clientList;
            } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new DataTemplateException(e);
            }

        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            List<Object> params = new ArrayList();
            List<Field> fields = entitySQLMetaData.getMetaData().getFieldsWithoutId();
            for (Field field : fields) {
                field.setAccessible(true);
                params.add(field.get(client));
            }
            long id=dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    params);
            return id;
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            List<Object> params = new ArrayList();
            List<Field> fields = entitySQLMetaData.getMetaData().getFieldsWithoutId();
            for (Field field : fields) {
                field.setAccessible(true);
                params.add(field.get(client));
            }
            Field idField = entitySQLMetaData.getMetaData().getIdField();
            idField.setAccessible(true);
            params.add(idField.get(client));
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                    params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}

package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {
    private final EntityClassMetaData<T> metaData;


    public EntitySQLMetaDataImpl(EntityClassMetaData<T> metaData) {
        this.metaData = metaData;
    }

    @Override
    public String getSelectAllSql() {
        return "Select * from " + metaData.getName();

    }

    public EntityClassMetaData<T> getMetaData() {
        return metaData;
    }

    @Override
    public String getSelectByIdSql() {
        return new StringBuilder("Select * from ").append(metaData.getName())
                .append(" where ")
                .append(metaData.getIdField().getName())
                .append("=?").toString();
    }

    @Override
    public String getInsertSql() {
        StringBuilder queryBegin = new StringBuilder();
        StringBuilder queryEnd = new StringBuilder("values(");
        queryBegin.append("insert into ").append(metaData.getName()).append("(");
        int count = 1;
        List<Field> fields = metaData.getFieldsWithoutId();
        for (Field field : fields) {
            queryBegin.append(field.getName());
            queryEnd.append("?");
            if (count < fields.size()) {
                queryBegin.append(",");
                queryEnd.append(",");
            } else {
                queryBegin.append(")");
                queryEnd.append(")");
            }
            count++;
        }
        return queryBegin.append(queryEnd).toString();

    }

    @Override
    public String getUpdateSql() {
        StringBuilder queryBegin = new StringBuilder("update ");
        StringBuilder queryEnd = new StringBuilder("where ");
        queryBegin.append(metaData.getName()).append(" set ") ;
        int count = 1;
        List<Field> fields = metaData.getFieldsWithoutId();
        for (Field field : fields) {
            queryBegin = queryBegin.append(field.getName() ).append("= ? ");
            if (count < fields.size()) {
                queryBegin.append(",");
            }
            count++;
        }
        var idFields = metaData.getIdField();
        queryEnd.append(idFields.getName()).append("=?");
        return queryBegin.append(queryEnd).toString();
    }
}

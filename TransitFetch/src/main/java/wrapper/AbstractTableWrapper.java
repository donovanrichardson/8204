package main.java.wrapper;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class AbstractTableWrapper<R extends Record> implements TableWrapper {

    Table<R> table;
    Map<String, Field<?>> fieldMap;//todo initialize these in subclasses' constructors lol

    @Override
    public void dbImport(DSLContext dsl, InputStream inputStream){
        Scanner s = new Scanner(inputStream);
        String[] columns = s.nextLine().split(","); //todo how to do this correctly
        List<Field<?>> fields = new ArrayList();
        for (String col : columns){
            fields.add(fieldMap.get(col));
        }

        dsl.loadInto(table).loadCSV(inputStream).fields(fields).execute();



    }
}

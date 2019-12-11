package main.java.wrapper;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public abstract class AbstractTableWrapper<R extends Record> implements TableWrapper {

    Table<R> table;
    Map<String,Field<?>> columns;

    @Override
    public void dbImport(DSLContext dsl, InputStream inputStream) throws IOException {
        inputStream.mark(2000);
        Scanner s = new Scanner(inputStream);
        String[] columns = s.nextLine().split("\"*\\s*,\\s*\"*"); //todo how to do this correctly
        List<Field<?>> fields = new ArrayList();
        for (String col : columns){
            fields.add(this.columns.get(col));
        }
        inputStream.reset();
        dsl.loadInto(table).loadCSV(inputStream).fields(fields).execute();



    }

}

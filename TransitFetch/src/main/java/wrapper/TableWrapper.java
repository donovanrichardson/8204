package main.java.wrapper;

import org.jooq.DSLContext;

import java.io.InputStream;

public interface TableWrapper {
    void dbImport(DSLContext dsl, InputStream inputStream);
}

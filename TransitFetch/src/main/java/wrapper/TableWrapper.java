package main.java.wrapper;

import main.java.wrapper.exception.OptionalTableException;
import org.jooq.DSLContext;

import java.io.IOException;
import java.io.InputStream;

public interface TableWrapper {
    void dbImport(DSLContext dsl, InputStream inputStream) throws IOException, OptionalTableException;
}

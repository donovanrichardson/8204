package com.transitfetch.java.dbAccess.wrapper;

import com.transitfetch.java.dbAccess.wrapper.exception.OptionalTableException;
import org.jooq.DSLContext;

import java.io.IOException;
import java.io.InputStream;

public interface TableWrapper {
    void dbImport(DSLContext dsl, InputStream inputStream) throws IOException, OptionalTableException;
}

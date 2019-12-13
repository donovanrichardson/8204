package main.java.wrapper;

import main.java.wrapper.exception.OptionalTableException;
import org.jooq.Record;

import java.io.InputStream;

public abstract class OptionalTableWrapper<R extends Record> extends AbstractTableWrapper<R> {

    @Override
    void verifyNotNull(InputStream inputStream) throws OptionalTableException {
        try{
            super.verifyNotNull(inputStream);
        } catch(NullPointerException n){
            throw new OptionalTableException(n.getMessage());
        }
    }

}

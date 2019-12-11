package main.java.wrapper;

import com.schema.tables.records.ServiceExceptionRecord;
import org.jooq.DSLContext;
import org.jooq.Field;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.schema.tables.Service.SERVICE;
import static com.schema.tables.ServiceException.SERVICE_EXCEPTION;

public class ServiceExceptionWrapper extends AbstractTableWrapper<ServiceExceptionRecord> {

    public ServiceExceptionWrapper() {
        this.table = SERVICE_EXCEPTION;

        this.columns.put("date", SERVICE_EXCEPTION.DATE);
        this.columns.put("exception_type", SERVICE_EXCEPTION.EXCEPTION_TYPE);
        this.columns.put("service_id", SERVICE_EXCEPTION.SERVICE_ID);


    }

    @Override
    public void dbImport(DSLContext dsl, InputStream inputStream) throws IOException{

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] b = new byte[1024];
        int l;
        try {
            while ((l = inputStream.read(b)) > 0) {
                out.write(b, 0, l);
            }
            out.flush();
        }catch(EOFException e){
            System.out.println("there was a problem while processing calendar_dates.txt");
            throw e;
        }

        InputStream forService = new ByteArrayInputStream(out.toByteArray());
        InputStream forExceptions = new ByteArrayInputStream(out.toByteArray());


        forService.mark(2000);
        Scanner s = new Scanner(forService);
        String[] columns = s.nextLine().split("\"*\\s*,\\s*\"*"); //todo how to do this correctly
        List<Field<?>> fields = new ArrayList();
        for (String col : columns){
            if(col.equals("service_id")){
                fields.add(SERVICE.SERVICE_ID);
            } else {
                fields.add(null);
            }
        }
        forService.reset();
        dsl.loadInto(SERVICE).onDuplicateKeyIgnore().loadCSV(forService).fields(fields).execute();

        super.dbImport(dsl, forExceptions);
    }

}

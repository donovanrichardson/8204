import com.job.DSLSetup;
import com.job.JobAccessor;
import novemb.jooq.model.Tables;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.meta.derby.sys.Sys;
import org.junit.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;

import novemb.jooq.model.tables.Job;

import static junit.framework.TestCase.*;
import static novemb.jooq.model.Tables.STATUS;
import static novemb.jooq.model.Tables.JOB;
import static novemb.jooq.model.Tables.STATUS_TYPE;
import static novemb.jooq.model.Tables.LOCATION;

public class ConnectionTest {

    //TODO db password has changed

    @Test
    public void readLineTest(){
        File access = new File("test/db_access.txt");
        Scanner accessScan = new Scanner("");
        String username = "";
        String password = "";
        try{
            accessScan = new Scanner(access);
        } catch (FileNotFoundException e){
            e.printStackTrace();
            fail();
        }
        try{
            username = accessScan.nextLine();
            password = accessScan.nextLine();
//            System.out.println(username);
//            System.out.println(password);
            assertEquals("name", username);
            assertEquals("word", password);
        }catch(NoSuchElementException e){
            e.printStackTrace();
            fail();
        }


    }

//    static DSLContext setup() throws SQLException  {
//        Scanner s = new Scanner(System.in);
//        System.out.println("provide username");
//        String user = s.nextLine();
//        System.out.println("provide password");
//        String pass = s.nextLine();
//        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/lexicon?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, pass); //the password is exposed lol
//        Configuration conf = new DefaultConfiguration().set(conn).set(SQLDialect.MYSQL_8_0);
//        DSLContext dsl = DSL.using(conf);
//        return dsl;
//    }


//    @Test
//    public void dbAccessTest(){
//        try {
//            DSLContext dsl = DSLSetup.get();
//            System.out.println(dsl.selectFrom(STATUS).fetch());
//            System.out.println(dsl.selectFrom(JOB).fetch());
//            System.out.println(dsl.selectFrom(LOCATION).fetch());
//            System.out.println(dsl.selectFrom(STATUS_TYPE).fetch());
//            dsl.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            fail();
//        }
//    }

//    @Test
//    public void dbQueryTest(){
//        try {
//            DSLContext dsl = DSLSetup.get();
//            String name = "test_name";
//            String location = "test_loc";
//            String url = "test_url";
//            dsl.insertInto(JOB, JOB.JOB_NAME, JOB.LOCATION_NAME, JOB.URL).values(name, location, url).execute();
//            Object insert1 = dsl.fetch("select last_insert_id()").getValue(0,0);
////            System.out.println(insert1);
//            dsl.insertInto(JOB, JOB.JOB_NAME, JOB.LOCATION_NAME, JOB.URL).values(name, location, url).execute();
//            Object insert2 = dsl.fetch("select last_insert_id()").getValue(0,0);
////            System.out.println(insert2);
//            assert(!insert1.equals(insert2));
//            Collection inserts = new ArrayList<>();
//            inserts.add(insert1);
//            inserts.add(insert2);
//            Result insertedRows = dsl.selectFrom(JOB).where(JOB.ID.in(inserts)).fetch();
////            System.out.println(insertedRows);
//            assertEquals(2,insertedRows.size());
//            dsl.deleteFrom(JOB).where(JOB.ID.in(inserts)).execute(); //remember execute and fetch
//            Result deletedRows = dsl.selectFrom(JOB).where(JOB.ID.in(inserts)).fetch();
////            System.out.println(deletedRows);
//            assertEquals(0,deletedRows.size());
//            dsl.close();
//           /* String.format("insert into status(job_id, type, notes) values (%s, %s, \"%s\")", 10, "status", "notes");
//            String.format("select id, job_name, location_name, url, stat.type, stat.name, stat.as_of from job left join(select job_id, as_of, type, type.name from status left join (select status_type_id, name from status_type) as type on status.type = type.status_type_id) as stat on job.id = stat.job_id where id = %s", 10);*/
//        } catch (SQLException e) {
//            e.printStackTrace();
//            fail();
//        }
//    }

//    @Test
//    public void retryTest() {
//        //program asks user to retry when nothing is found and user can successfully complete task after retrying.
//        try{
//            InputStream inputs = new FileInputStream("test/queryA.txt");
//            InputStream input2 = new FileInputStream("test/queryB.txt");
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            PrintStream output = new PrintStream(baos);
//            DSLContext dsl = DSLSetup.get("root", "rag6tenutilisar(");
//            JobAccessor ja = new JobAccessor(output, inputs, dsl);
//            ja.newJob(new Scanner(inputs));
//            System.out.println(baos.toString());
//            output.flush();
//            ja.modJob(new Scanner(input2));
//            System.out.println(baos.toString());
//        } catch (FileNotFoundException | SQLException e){
//            e.printStackTrace();
//            fail();
//        }
//
//
//    }

    @Test
    // Tests that the program can handle correct inputs, incorrect inputs, and retrying.
    public void retry2Test() {
        boolean byebyeMatch = false;
//        boolean new_jobMatch = false;
        boolean blue_jobMatch = false;
        boolean pass;
        try {
            InputStream inputs = new FileInputStream("test/query3.txt");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream output = new PrintStream(baos);
            DSLContext dsl = DSLSetup.get("root", "rag6tenutilisar(");
            JobAccessor ja = new JobAccessor(output, inputs, dsl);
            try{
                ja.run();
            }catch(Exception e){
                throw e;
            }finally {
                String bString = baos.toString();
                System.out.println(bString);
                String[] bLines = bString.split("\\r?\\n");
//                Scanner bScanner = new Scanner(bString);
                for(String line: bLines){
                    System.out.println(line);
                    pass = byebyeMatch/* && new_jobMatch*/ && blue_jobMatch;
                    if(pass){
                        assert(pass);
                        break;
                    }
                    if(!byebyeMatch){
                        byebyeMatch = line.matches(".*bye-bye.*");
                        System.out.println("bye-bye");
                        System.out.println(byebyeMatch);
                    }
//                    if(!new_jobMatch){
//                        new_jobMatch = line.matches(".*new_job.*");
//                        System.out.println("new_job");
//                        System.out.println(new_jobMatch);
//
//                    }
                    if(!blue_jobMatch){
                        blue_jobMatch = line.matches(".*blue_job.*");
                        System.out.println("blue_job");
                        System.out.println(blue_jobMatch);
                    }
                }
//                while(bScanner.hasNextLine()){
//                    System.out.println("can I get a line?");
//                    String bLine = bScanner.nextLine();
//                    System.out.println(bLine);

//                }
                pass = byebyeMatch && blue_jobMatch;
                assert(pass);
            }

        }catch(FileNotFoundException | SQLException e){
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void integerValidationTest(){
//        It should not be possible to enter a non-integer or non-included integer into certain rows. What should happen is that sql should govern whether an error comes up, and then I will prompt the user for appropriate input.

    }

    @Test
    public void nonIntegerErrorHandlingTest(){
//        When other errors occur, the program should print the error message and reset.
    }

}

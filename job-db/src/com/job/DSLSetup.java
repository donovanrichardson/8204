package com.job;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DSLSetup {

    private static String baseURL = "jdbc:mysql://localhost/jobs?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false"; //&serverTimezone=EST

    public static DSLContext get()throws SQLException {
        Scanner s = new Scanner(System.in);
        System.out.println("provide username");
        String user = s.nextLine();
        System.out.println("provide password");
        String pass = s.nextLine();
        Connection conn = DriverManager.getConnection(baseURL, user, pass); //the password is exposed lol
        Configuration conf = new DefaultConfiguration().set(conn).set(SQLDialect.MYSQL_8_0);
        DSLContext dsl = DSL.using(conf);
        return dsl;
    }

    /**
     * Provides a <code>DSLContext</code> object for accessing a MySQL database.
     * @param user Database username.
     * @param pass Database password.
     * @return
     * @throws SQLException if unable to connect to the MySQL database.
     */
    public static DSLContext get(String user, String pass) throws SQLException {
        Connection conn = DriverManager.getConnection(baseURL, user, pass); //the password is exposed lol
        Configuration conf = new DefaultConfiguration().set(conn).set(SQLDialect.MYSQL_8_0);
        DSLContext dsl = DSL.using(conf);
        return dsl;
    }
}

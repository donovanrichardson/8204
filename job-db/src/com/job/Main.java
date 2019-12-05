package com.job;

import org.jooq.DSLContext;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("provide db username");
        String user = s.nextLine();
        System.out.println("provide db password");
        String pass = s.nextLine();
        try{
            DSLContext db = DSLSetup.get(user, pass);
            JobAccessor ja = new JobAccessor(System.out, System.in, db);
            ja.run();
        }catch (SQLException e){
            e.printStackTrace();
        }


    }
}

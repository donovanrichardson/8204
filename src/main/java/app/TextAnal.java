package app;

import app.texthandler.TextHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class TextAnal {

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        if (args[0].equals("plain")){
            String user = args[1];
            String password = args[2];
            String doc = args[3];
            File f = new File(doc);
            Scanner s = new Scanner(f); //FNF exception
            String url = "jdbc:mysql://localhost/lexicon?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            Connection conn = DriverManager.getConnection(url, user, password); //SQLException
            TextHandler handler = new TextHandler(conn);
            if (doc.length() > 100){
                int len = doc.length();
                doc = doc.substring(len-100, len);
            }
            handler.newDoc(doc);
            handler.addDoc(s);
        }
//adds all txt files in command line directory. needs to handle null pointer exc when there are no files
        if (args[0].equals("dir")) {
            String user = args[1];
            String password = args[2];
            String url = "jdbc:mysql://localhost/lexicon?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            Connection conn = DriverManager.getConnection(url, user, password); //SQLException
            TextHandler handler = new TextHandler(conn);
            for (File f : new File(".").listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String fname){
                    return fname.toLowerCase().endsWith(".txt");
                } //if none satisfy, for loop fails silently

            })){
                String doc = f.getName();
                Scanner s = new Scanner(f);
                if (doc.length() > 100){
                    int len = doc.length();
                    doc = doc.substring(len-100, len);
                }
                handler.newDoc(doc);
                handler.addDoc(s);
            }
        }
        if (args[0].equals("w")){
            int i;
            String user = args[1];
            String password = args[2]; //TODO haha refactor this bc it repeats
            String url = "jdbc:mysql://localhost/lexicon?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            Connection conn = DriverManager.getConnection(url, user, password); //SQLException
            TextHandler handler = new TextHandler(conn);
            try{
               i = Integer.valueOf(args[1]);
               for(int j = 0; j < i; j++){
                   handler.attemptRandom(); //TODO TEST THIS. This has the wikihavior as well as the doc duplication checking
               }
            }catch(NumberFormatException n){
                System.out.println("The second argument was not a valid number.");
            }
        }
    }
}
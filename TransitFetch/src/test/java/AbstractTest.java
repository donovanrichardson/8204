package test.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AbstractTest {

    static File access = new File("access.txt");
    static String username;
    static String password;
    static String apiKey;

    void initialize() throws FileNotFoundException {
        Scanner s = new Scanner(access);
        username = s.nextLine();
        password = s.nextLine();
        apiKey = s.nextLine();
    }
}

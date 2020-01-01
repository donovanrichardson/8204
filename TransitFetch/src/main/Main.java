package main;

import main.java.AppController;
import main.java.FeedTable;
import main.java.GTFSController;
import main.java.VersionFeedQuery;
import org.jooq.DSLContext;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        System.out.println("provide db username");
        String user = s.nextLine();
        System.out.println("provide db password");//todo what if incorrect credentials
        String pass = s.nextLine();

        try{
            GTFSController ac = new AppController(user, pass, "f700defc-6fcc-4c3f-9045-5ac5e91d7623");
            while(true){
                System.out.println("press n for new feed, t for timetable, or q for quit");
                String option = s.nextLine();
                if (option.equals("n")){
                    System.out.println("what is the feed version id for the new feed?");
                    String id = s.nextLine();
                    ac.addFeeds(new VersionFeedQuery(id));
                } else if (option.equals("t")){
                    System.out.println("what is the feed version id for the feed you'd like to query?");
                    String ver = s.nextLine();
                    DSLContext dsl = ((AppController) ac).dsl; //todo lol this casting is wild, just make a method
                    FeedTable ft = new FeedTable(ver, dsl);
                    System.out.println("what is the origin?");
                    String ori = s.nextLine();
                    ft.setOrigin(ori);
                    System.out.println("what is the destination?");
                    String dest = s.nextLine();
                    ft.setDestination("dest");
                    System.out.println("what is the year?");
                    int year = Integer.valueOf(s.nextLine());
                    System.out.println("what is the month?");
                    int mon = Integer.valueOf(s.nextLine());
                    System.out.println("what is the date?");
                    int dat = Integer.valueOf(s.nextLine());
                    ft.setDate(year,mon,dat);
                    System.out.print(ft.getTimetable());
                } else if (option.equals("q")){
                    System.out.println("bye-bye!");
                    break;
                }else{
                    continue; //superfluous code
                }
            }
            s.close();
        }catch (SQLException | IOException e){
            e.printStackTrace();
        }finally{
            s.close();
        }

    }

}

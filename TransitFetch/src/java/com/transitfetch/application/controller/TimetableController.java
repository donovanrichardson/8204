package com.transitfetch.application.controller;

import com.mysql.cj.jdbc.ConnectionImpl;
import com.transitfetch.java.dbAccess.table.FeedTable;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.schema.tables.Route.ROUTE;
import static com.schema.tables.Stop.STOP;
import static com.schema.tables.Trip.TRIP;

@Controller
public class TimetableController {

    String feedVersion = "mta/86/20191231"; //todo add column in feed to refer to the latest feedVersion. then add feed to each controller (there are many controllers; one for each city)

    DSLContext connect() throws SQLException {
        java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/gtfs?autoReconnect=true&useSSL=false&useUnicode=true&useLegacyDatetimeCode=false&autoCommit=false&relaxAutoCommit=true", "root", "suwaajii&)ganguro"); //In earlier version I was causing the time zones to be switched without warrant.
        Configuration conf = new DefaultConfiguration().set(conn).set(SQLDialect.MYSQL_8_0);
        ConnectionImpl cImpl = (ConnectionImpl)conf.connectionProvider().acquire();
        cImpl.getSession().getServerSession().setAutoCommit(false);
        Configuration conf2 = new DefaultConfiguration().set(cImpl).set(SQLDialect.MYSQL_8_0);
        DSLContext dsl = DSL.using(conf2);
        return dsl;
    }

    @RequestMapping("/routes")
    @ResponseBody
    public List<Map<String, Object>> routes(@RequestParam(value = "name", required = false) String name ) {

        DSLContext dsl;
        try{
            dsl = this.connect();
        } catch (SQLException e) {
            e.printStackTrace();
            List error = new ArrayList<Map<String, Object>>();
            Map<String, Object> info = new HashMap();
            info.put("error", "unable to connect to database");
            error.add(info);
            return error;
        }

        if (name != null){
            return dsl.selectFrom(ROUTE).where(ROUTE.DEFAULT_NAME.like("%"+name+"%")).and(ROUTE.FEED_VERSION.eq(this.feedVersion)).fetchMaps();

        }else{
        return dsl.selectFrom(ROUTE).where(ROUTE.FEED_VERSION.eq(this.feedVersion)).fetchMaps();
        }



    }

    //todo too much copying and pasting


    @RequestMapping("/stops")
    @ResponseBody
    public List<Map<String, Object>> stops(@RequestParam(value= "route", required = false) String routeId, @RequestParam(value = "name", required = false) String name ) {
        DSLContext dsl;
        try{
            dsl = this.connect();
        } catch (SQLException e) {
            e.printStackTrace();
            List error = new ArrayList<Map<String, Object>>();
            Map<String, Object> info = new HashMap();
            info.put("error", "unable to connect to database");
            error.add(info);
            return error;
        }

        FeedTable ft = new FeedTable(this.feedVersion, dsl);
        if (routeId != null){
            ft.setRoute(routeId);
        }
        if (name != null){
            return ft.getStopsMaps(name);
        }else return ft.getStopsMaps("");


    }

    @RequestMapping("/dests")
    @ResponseBody
    public List<Map<String, Object>> dests(@RequestParam(value= "origin") String origin, @RequestParam(value= "route", required = false) String routeId, @RequestParam(value = "name", required = false) String name ) {
        DSLContext dsl;
        try{
            dsl = this.connect();
        } catch (SQLException e) {
            e.printStackTrace();
            List error = new ArrayList<Map<String, Object>>();
            Map<String, Object> info = new HashMap();
            info.put("error", "unable to connect to database");
            error.add(info);
            return error;
        }

        FeedTable ft = new FeedTable(this.feedVersion, dsl);

        ft.setOrigin(origin);

        if (routeId != null){
            ft.setRoute(routeId);
        }
        if (name != null){
            return ft.getDestinationsMaps(name);
        } else return ft.getDestinationsMaps("");


    }

    @RequestMapping("/timetable")
    @ResponseBody
    public Map<String,Object> timetable(@RequestParam(value= "origin") String origin, @RequestParam(value= "dest") String dest, @RequestParam(value= "route", required = false) String routeId, @RequestParam(value= "year") int year, @RequestParam(value= "month") int month, @RequestParam(value= "date") int date) {
        DSLContext dsl;
        try{
            dsl = this.connect();
        } catch (SQLException e) {
            e.printStackTrace();
//            List error = new ArrayList<Map<String, Object>>();
            Map<String, Object> info = new HashMap();
            info.put("error", "unable to connect to database");
//            error.add(info);
            return info;
        }

        Map<String,Object> result = new HashMap<>();

        FeedTable ft = new FeedTable(this.feedVersion, dsl);

        ft.setOrigin(origin);
        ft.setDestination(dest);
        ft.setDate(year, month, date);

        if (routeId !=  null){
            ft.setRoute(routeId);
            result.put("route", dsl.selectFrom(ROUTE).where(ROUTE.ROUTE_ID.eq(routeId)).and(ROUTE.FEED_VERSION.eq(this.feedVersion)).fetchOneMap());
        }

        result.put("origin", dsl.selectFrom(STOP).where(STOP.STOP_ID.eq(origin)).fetchOneMap());
        result.put("destination", dsl.selectFrom(STOP).where(STOP.STOP_ID.eq(dest)).fetchOneMap());
        result.put("result", ft.getTimetableMaps());
        result.put("date", ft.dateString());
        return result;
        //todo make sure to specify feed_version
    }

}
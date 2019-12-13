/*
 * This file is generated by jOOQ.
 */
package com.schema;


import com.schema.tables.Agency;
import com.schema.tables.Feed;
import com.schema.tables.FeedVersion;
import com.schema.tables.Frequency;
import com.schema.tables.Route;
import com.schema.tables.Service;
import com.schema.tables.ServiceException;
import com.schema.tables.Shape;
import com.schema.tables.Stop;
import com.schema.tables.StopTime;
import com.schema.tables.Trip;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>gtfs</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index AGENCY_AGENCY_ID = Indexes0.AGENCY_AGENCY_ID;
    public static final Index AGENCY_FEED_VERSION = Indexes0.AGENCY_FEED_VERSION;
    public static final Index AGENCY_PRIMARY = Indexes0.AGENCY_PRIMARY;
    public static final Index FEED_PRIMARY = Indexes0.FEED_PRIMARY;
    public static final Index FEED_VERSION_FEED = Indexes0.FEED_VERSION_FEED;
    public static final Index FEED_VERSION_PRIMARY = Indexes0.FEED_VERSION_PRIMARY;
    public static final Index FREQUENCY_FREQUENCY_FEED_VERSION_FK = Indexes0.FREQUENCY_FREQUENCY_FEED_VERSION_FK;
    public static final Index FREQUENCY_PRIMARY = Indexes0.FREQUENCY_PRIMARY;
    public static final Index FREQUENCY_TRIP_ID = Indexes0.FREQUENCY_TRIP_ID;
    public static final Index ROUTE_AGENCY_ID = Indexes0.ROUTE_AGENCY_ID;
    public static final Index ROUTE_PRIMARY = Indexes0.ROUTE_PRIMARY;
    public static final Index ROUTE_ROUTE_FEED_VERSION_FK = Indexes0.ROUTE_ROUTE_FEED_VERSION_FK;
    public static final Index ROUTE_ROUTE_ID = Indexes0.ROUTE_ROUTE_ID;
    public static final Index SERVICE_PRIMARY = Indexes0.SERVICE_PRIMARY;
    public static final Index SERVICE_SERVICE_FEED_VERSION_FK = Indexes0.SERVICE_SERVICE_FEED_VERSION_FK;
    public static final Index SERVICE_SERVICE_ID = Indexes0.SERVICE_SERVICE_ID;
    public static final Index SERVICE_EXCEPTION_PRIMARY = Indexes0.SERVICE_EXCEPTION_PRIMARY;
    public static final Index SERVICE_EXCEPTION_SERVICE_EXCEPTION_FEED_VERSION_FK = Indexes0.SERVICE_EXCEPTION_SERVICE_EXCEPTION_FEED_VERSION_FK;
    public static final Index SHAPE_PRIMARY = Indexes0.SHAPE_PRIMARY;
    public static final Index SHAPE_SHAPE_FEED_VERSION_FK = Indexes0.SHAPE_SHAPE_FEED_VERSION_FK;
    public static final Index SHAPE_SHAPE_ID = Indexes0.SHAPE_SHAPE_ID;
    public static final Index STOP_PARENT_STATION = Indexes0.STOP_PARENT_STATION;
    public static final Index STOP_PRIMARY = Indexes0.STOP_PRIMARY;
    public static final Index STOP_STOP_FEED_VERSION_FK = Indexes0.STOP_STOP_FEED_VERSION_FK;
    public static final Index STOP_TIME_PRIMARY = Indexes0.STOP_TIME_PRIMARY;
    public static final Index STOP_TIME_STOP_TIME_FEED_VERSION_FK = Indexes0.STOP_TIME_STOP_TIME_FEED_VERSION_FK;
    public static final Index TRIP_PRIMARY = Indexes0.TRIP_PRIMARY;
    public static final Index TRIP_ROUTE_ID_2 = Indexes0.TRIP_ROUTE_ID_2;
    public static final Index TRIP_SERVICE_ID = Indexes0.TRIP_SERVICE_ID;
    public static final Index TRIP_SHAPE_ID = Indexes0.TRIP_SHAPE_ID;
    public static final Index TRIP_TRIP_FEED_VERSION_FK = Indexes0.TRIP_TRIP_FEED_VERSION_FK;
    public static final Index TRIP_TRIP_ID = Indexes0.TRIP_TRIP_ID;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index AGENCY_AGENCY_ID = Internal.createIndex("agency_id", Agency.AGENCY, new OrderField[] { Agency.AGENCY.AGENCY_ID }, false);
        public static Index AGENCY_FEED_VERSION = Internal.createIndex("feed_version", Agency.AGENCY, new OrderField[] { Agency.AGENCY.FEED_VERSION }, false);
        public static Index AGENCY_PRIMARY = Internal.createIndex("PRIMARY", Agency.AGENCY, new OrderField[] { Agency.AGENCY.KEY }, true);
        public static Index FEED_PRIMARY = Internal.createIndex("PRIMARY", Feed.FEED, new OrderField[] { Feed.FEED.ID }, true);
        public static Index FEED_VERSION_FEED = Internal.createIndex("feed", FeedVersion.FEED_VERSION, new OrderField[] { FeedVersion.FEED_VERSION.FEED }, false);
        public static Index FEED_VERSION_PRIMARY = Internal.createIndex("PRIMARY", FeedVersion.FEED_VERSION, new OrderField[] { FeedVersion.FEED_VERSION.ID }, true);
        public static Index FREQUENCY_FREQUENCY_FEED_VERSION_FK = Internal.createIndex("frequency_feed_version_fk", Frequency.FREQUENCY, new OrderField[] { Frequency.FREQUENCY.FEED_VERSION }, false);
        public static Index FREQUENCY_PRIMARY = Internal.createIndex("PRIMARY", Frequency.FREQUENCY, new OrderField[] { Frequency.FREQUENCY.START_TIME, Frequency.FREQUENCY.TRIP_ID, Frequency.FREQUENCY.FEED_VERSION }, true);
        public static Index FREQUENCY_TRIP_ID = Internal.createIndex("trip_id", Frequency.FREQUENCY, new OrderField[] { Frequency.FREQUENCY.TRIP_ID }, false);
        public static Index ROUTE_AGENCY_ID = Internal.createIndex("agency_id", Route.ROUTE, new OrderField[] { Route.ROUTE.AGENCY_ID }, false);
        public static Index ROUTE_PRIMARY = Internal.createIndex("PRIMARY", Route.ROUTE, new OrderField[] { Route.ROUTE.ROUTE_ID, Route.ROUTE.FEED_VERSION }, true);
        public static Index ROUTE_ROUTE_FEED_VERSION_FK = Internal.createIndex("route_feed_version_fk", Route.ROUTE, new OrderField[] { Route.ROUTE.FEED_VERSION }, false);
        public static Index ROUTE_ROUTE_ID = Internal.createIndex("route_id", Route.ROUTE, new OrderField[] { Route.ROUTE.ROUTE_ID }, false);
        public static Index SERVICE_PRIMARY = Internal.createIndex("PRIMARY", Service.SERVICE, new OrderField[] { Service.SERVICE.SERVICE_ID, Service.SERVICE.FEED_VERSION }, true);
        public static Index SERVICE_SERVICE_FEED_VERSION_FK = Internal.createIndex("service_feed_version_fk", Service.SERVICE, new OrderField[] { Service.SERVICE.FEED_VERSION }, false);
        public static Index SERVICE_SERVICE_ID = Internal.createIndex("service_id", Service.SERVICE, new OrderField[] { Service.SERVICE.SERVICE_ID }, false);
        public static Index SERVICE_EXCEPTION_PRIMARY = Internal.createIndex("PRIMARY", ServiceException.SERVICE_EXCEPTION, new OrderField[] { ServiceException.SERVICE_EXCEPTION.SERVICE_ID, ServiceException.SERVICE_EXCEPTION.DATE, ServiceException.SERVICE_EXCEPTION.FEED_VERSION }, true);
        public static Index SERVICE_EXCEPTION_SERVICE_EXCEPTION_FEED_VERSION_FK = Internal.createIndex("service_exception_feed_version_fk", ServiceException.SERVICE_EXCEPTION, new OrderField[] { ServiceException.SERVICE_EXCEPTION.FEED_VERSION }, false);
        public static Index SHAPE_PRIMARY = Internal.createIndex("PRIMARY", Shape.SHAPE, new OrderField[] { Shape.SHAPE.SHAPE_ID, Shape.SHAPE.FEED_VERSION, Shape.SHAPE.SHAPE_PT_SEQUENCE }, true);
        public static Index SHAPE_SHAPE_FEED_VERSION_FK = Internal.createIndex("shape_feed_version_fk", Shape.SHAPE, new OrderField[] { Shape.SHAPE.FEED_VERSION }, false);
        public static Index SHAPE_SHAPE_ID = Internal.createIndex("shape_id", Shape.SHAPE, new OrderField[] { Shape.SHAPE.SHAPE_ID }, false);
        public static Index STOP_PARENT_STATION = Internal.createIndex("parent_station", Stop.STOP, new OrderField[] { Stop.STOP.PARENT_STATION }, false);
        public static Index STOP_PRIMARY = Internal.createIndex("PRIMARY", Stop.STOP, new OrderField[] { Stop.STOP.STOP_ID }, true);
        public static Index STOP_STOP_FEED_VERSION_FK = Internal.createIndex("stop_feed_version_fk", Stop.STOP, new OrderField[] { Stop.STOP.FEED_VERSION }, false);
        public static Index STOP_TIME_PRIMARY = Internal.createIndex("PRIMARY", StopTime.STOP_TIME, new OrderField[] { StopTime.STOP_TIME.TRIP_ID, StopTime.STOP_TIME.STOP_SEQUENCE, StopTime.STOP_TIME.FEED_VERSION }, true);
        public static Index STOP_TIME_STOP_TIME_FEED_VERSION_FK = Internal.createIndex("stop_time_feed_version_fk", StopTime.STOP_TIME, new OrderField[] { StopTime.STOP_TIME.FEED_VERSION }, false);
        public static Index TRIP_PRIMARY = Internal.createIndex("PRIMARY", Trip.TRIP, new OrderField[] { Trip.TRIP.TRIP_ID, Trip.TRIP.FEED_VERSION }, true);
        public static Index TRIP_ROUTE_ID_2 = Internal.createIndex("route_id_2", Trip.TRIP, new OrderField[] { Trip.TRIP.ROUTE_ID }, false);
        public static Index TRIP_SERVICE_ID = Internal.createIndex("service_id", Trip.TRIP, new OrderField[] { Trip.TRIP.SERVICE_ID }, false);
        public static Index TRIP_SHAPE_ID = Internal.createIndex("shape_id", Trip.TRIP, new OrderField[] { Trip.TRIP.SHAPE_ID }, false);
        public static Index TRIP_TRIP_FEED_VERSION_FK = Internal.createIndex("trip_feed_version_fk", Trip.TRIP, new OrderField[] { Trip.TRIP.FEED_VERSION }, false);
        public static Index TRIP_TRIP_ID = Internal.createIndex("trip_id", Trip.TRIP, new OrderField[] { Trip.TRIP.TRIP_ID }, false);
    }
}

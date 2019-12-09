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
import com.schema.tables.records.AgencyRecord;
import com.schema.tables.records.FeedRecord;
import com.schema.tables.records.FeedVersionRecord;
import com.schema.tables.records.FrequencyRecord;
import com.schema.tables.records.RouteRecord;
import com.schema.tables.records.ServiceExceptionRecord;
import com.schema.tables.records.ServiceRecord;
import com.schema.tables.records.ShapeRecord;
import com.schema.tables.records.StopRecord;
import com.schema.tables.records.StopTimeRecord;
import com.schema.tables.records.TripRecord;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;
import org.jooq.types.UInteger;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>gtfs</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<AgencyRecord, UInteger> IDENTITY_AGENCY = Identities0.IDENTITY_AGENCY;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AgencyRecord> KEY_AGENCY_PRIMARY = UniqueKeys0.KEY_AGENCY_PRIMARY;
    public static final UniqueKey<FeedRecord> KEY_FEED_PRIMARY = UniqueKeys0.KEY_FEED_PRIMARY;
    public static final UniqueKey<FeedVersionRecord> KEY_FEED_VERSION_PRIMARY = UniqueKeys0.KEY_FEED_VERSION_PRIMARY;
    public static final UniqueKey<FrequencyRecord> KEY_FREQUENCY_PRIMARY = UniqueKeys0.KEY_FREQUENCY_PRIMARY;
    public static final UniqueKey<RouteRecord> KEY_ROUTE_PRIMARY = UniqueKeys0.KEY_ROUTE_PRIMARY;
    public static final UniqueKey<ServiceRecord> KEY_SERVICE_PRIMARY = UniqueKeys0.KEY_SERVICE_PRIMARY;
    public static final UniqueKey<ServiceExceptionRecord> KEY_SERVICE_EXCEPTION_PRIMARY = UniqueKeys0.KEY_SERVICE_EXCEPTION_PRIMARY;
    public static final UniqueKey<ShapeRecord> KEY_SHAPE_PRIMARY = UniqueKeys0.KEY_SHAPE_PRIMARY;
    public static final UniqueKey<StopRecord> KEY_STOP_PRIMARY = UniqueKeys0.KEY_STOP_PRIMARY;
    public static final UniqueKey<StopTimeRecord> KEY_STOP_TIME_PRIMARY = UniqueKeys0.KEY_STOP_TIME_PRIMARY;
    public static final UniqueKey<TripRecord> KEY_TRIP_PRIMARY = UniqueKeys0.KEY_TRIP_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<AgencyRecord, FeedVersionRecord> AGENCY_IBFK_1 = ForeignKeys0.AGENCY_IBFK_1;
    public static final ForeignKey<FeedVersionRecord, FeedRecord> FEED_VERSION_IBFK_1 = ForeignKeys0.FEED_VERSION_IBFK_1;
    public static final ForeignKey<FrequencyRecord, TripRecord> FREQUENCY_IBFK_1 = ForeignKeys0.FREQUENCY_IBFK_1;
    public static final ForeignKey<FrequencyRecord, FeedVersionRecord> FREQUENCY_FEED_VERSION_FK = ForeignKeys0.FREQUENCY_FEED_VERSION_FK;
    public static final ForeignKey<RouteRecord, FeedVersionRecord> ROUTE_FEED_VERSION_FK = ForeignKeys0.ROUTE_FEED_VERSION_FK;
    public static final ForeignKey<ServiceRecord, FeedVersionRecord> SERVICE_FEED_VERSION_FK = ForeignKeys0.SERVICE_FEED_VERSION_FK;
    public static final ForeignKey<ServiceExceptionRecord, ServiceRecord> SERVICE_EXCEPTION_IBFK_1 = ForeignKeys0.SERVICE_EXCEPTION_IBFK_1;
    public static final ForeignKey<ServiceExceptionRecord, FeedVersionRecord> SERVICE_EXCEPTION_FEED_VERSION_FK = ForeignKeys0.SERVICE_EXCEPTION_FEED_VERSION_FK;
    public static final ForeignKey<ShapeRecord, FeedVersionRecord> SHAPE_FEED_VERSION_FK = ForeignKeys0.SHAPE_FEED_VERSION_FK;
    public static final ForeignKey<StopRecord, StopRecord> STOP_IBFK_1 = ForeignKeys0.STOP_IBFK_1;
    public static final ForeignKey<StopRecord, FeedVersionRecord> STOP_FEED_VERSION_FK = ForeignKeys0.STOP_FEED_VERSION_FK;
    public static final ForeignKey<StopTimeRecord, FeedVersionRecord> STOP_TIME_FEED_VERSION_FK = ForeignKeys0.STOP_TIME_FEED_VERSION_FK;
    public static final ForeignKey<TripRecord, RouteRecord> TRIP_IBFK_1 = ForeignKeys0.TRIP_IBFK_1;
    public static final ForeignKey<TripRecord, ServiceRecord> TRIP_IBFK_2 = ForeignKeys0.TRIP_IBFK_2;
    public static final ForeignKey<TripRecord, ShapeRecord> TRIP_IBFK_3 = ForeignKeys0.TRIP_IBFK_3;
    public static final ForeignKey<TripRecord, FeedVersionRecord> TRIP_FEED_VERSION_FK = ForeignKeys0.TRIP_FEED_VERSION_FK;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<AgencyRecord, UInteger> IDENTITY_AGENCY = Internal.createIdentity(Agency.AGENCY, Agency.AGENCY.KEY);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<AgencyRecord> KEY_AGENCY_PRIMARY = Internal.createUniqueKey(Agency.AGENCY, "KEY_agency_PRIMARY", Agency.AGENCY.KEY);
        public static final UniqueKey<FeedRecord> KEY_FEED_PRIMARY = Internal.createUniqueKey(Feed.FEED, "KEY_feed_PRIMARY", Feed.FEED.ID);
        public static final UniqueKey<FeedVersionRecord> KEY_FEED_VERSION_PRIMARY = Internal.createUniqueKey(FeedVersion.FEED_VERSION, "KEY_feed_version_PRIMARY", FeedVersion.FEED_VERSION.ID);
        public static final UniqueKey<FrequencyRecord> KEY_FREQUENCY_PRIMARY = Internal.createUniqueKey(Frequency.FREQUENCY, "KEY_frequency_PRIMARY", Frequency.FREQUENCY.START_TIME, Frequency.FREQUENCY.TRIP_ID, Frequency.FREQUENCY.FEED_VERSION);
        public static final UniqueKey<RouteRecord> KEY_ROUTE_PRIMARY = Internal.createUniqueKey(Route.ROUTE, "KEY_route_PRIMARY", Route.ROUTE.ROUTE_ID, Route.ROUTE.FEED_VERSION);
        public static final UniqueKey<ServiceRecord> KEY_SERVICE_PRIMARY = Internal.createUniqueKey(Service.SERVICE, "KEY_service_PRIMARY", Service.SERVICE.SERVICE_ID, Service.SERVICE.FEED_VERSION);
        public static final UniqueKey<ServiceExceptionRecord> KEY_SERVICE_EXCEPTION_PRIMARY = Internal.createUniqueKey(ServiceException.SERVICE_EXCEPTION, "KEY_service_exception_PRIMARY", ServiceException.SERVICE_EXCEPTION.SERVICE_ID, ServiceException.SERVICE_EXCEPTION.DATE, ServiceException.SERVICE_EXCEPTION.FEED_VERSION);
        public static final UniqueKey<ShapeRecord> KEY_SHAPE_PRIMARY = Internal.createUniqueKey(Shape.SHAPE, "KEY_shape_PRIMARY", Shape.SHAPE.SHAPE_ID, Shape.SHAPE.FEED_VERSION);
        public static final UniqueKey<StopRecord> KEY_STOP_PRIMARY = Internal.createUniqueKey(Stop.STOP, "KEY_stop_PRIMARY", Stop.STOP.STOP_ID);
        public static final UniqueKey<StopTimeRecord> KEY_STOP_TIME_PRIMARY = Internal.createUniqueKey(StopTime.STOP_TIME, "KEY_stop_time_PRIMARY", StopTime.STOP_TIME.TRIP_ID, StopTime.STOP_TIME.STOP_SEQUENCE, StopTime.STOP_TIME.FEED_VERSION);
        public static final UniqueKey<TripRecord> KEY_TRIP_PRIMARY = Internal.createUniqueKey(Trip.TRIP, "KEY_trip_PRIMARY", Trip.TRIP.TRIP_ID, Trip.TRIP.FEED_VERSION);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<AgencyRecord, FeedVersionRecord> AGENCY_IBFK_1 = Internal.createForeignKey(com.schema.Keys.KEY_FEED_VERSION_PRIMARY, Agency.AGENCY, "agency_ibfk_1", Agency.AGENCY.FEED_VERSION);
        public static final ForeignKey<FeedVersionRecord, FeedRecord> FEED_VERSION_IBFK_1 = Internal.createForeignKey(com.schema.Keys.KEY_FEED_PRIMARY, FeedVersion.FEED_VERSION, "feed_version_ibfk_1", FeedVersion.FEED_VERSION.FEED);
        public static final ForeignKey<FrequencyRecord, TripRecord> FREQUENCY_IBFK_1 = Internal.createForeignKey(com.schema.Keys.KEY_TRIP_PRIMARY, Frequency.FREQUENCY, "frequency_ibfk_1", Frequency.FREQUENCY.TRIP_ID);
        public static final ForeignKey<FrequencyRecord, FeedVersionRecord> FREQUENCY_FEED_VERSION_FK = Internal.createForeignKey(com.schema.Keys.KEY_FEED_VERSION_PRIMARY, Frequency.FREQUENCY, "frequency_feed_version_fk", Frequency.FREQUENCY.FEED_VERSION);
        public static final ForeignKey<RouteRecord, FeedVersionRecord> ROUTE_FEED_VERSION_FK = Internal.createForeignKey(com.schema.Keys.KEY_FEED_VERSION_PRIMARY, Route.ROUTE, "route_feed_version_fk", Route.ROUTE.FEED_VERSION);
        public static final ForeignKey<ServiceRecord, FeedVersionRecord> SERVICE_FEED_VERSION_FK = Internal.createForeignKey(com.schema.Keys.KEY_FEED_VERSION_PRIMARY, Service.SERVICE, "service_feed_version_fk", Service.SERVICE.FEED_VERSION);
        public static final ForeignKey<ServiceExceptionRecord, ServiceRecord> SERVICE_EXCEPTION_IBFK_1 = Internal.createForeignKey(com.schema.Keys.KEY_SERVICE_PRIMARY, ServiceException.SERVICE_EXCEPTION, "service_exception_ibfk_1", ServiceException.SERVICE_EXCEPTION.SERVICE_ID);
        public static final ForeignKey<ServiceExceptionRecord, FeedVersionRecord> SERVICE_EXCEPTION_FEED_VERSION_FK = Internal.createForeignKey(com.schema.Keys.KEY_FEED_VERSION_PRIMARY, ServiceException.SERVICE_EXCEPTION, "service_exception_feed_version_fk", ServiceException.SERVICE_EXCEPTION.FEED_VERSION);
        public static final ForeignKey<ShapeRecord, FeedVersionRecord> SHAPE_FEED_VERSION_FK = Internal.createForeignKey(com.schema.Keys.KEY_FEED_VERSION_PRIMARY, Shape.SHAPE, "shape_feed_version_fk", Shape.SHAPE.FEED_VERSION);
        public static final ForeignKey<StopRecord, StopRecord> STOP_IBFK_1 = Internal.createForeignKey(com.schema.Keys.KEY_STOP_PRIMARY, Stop.STOP, "stop_ibfk_1", Stop.STOP.PARENT_STATION);
        public static final ForeignKey<StopRecord, FeedVersionRecord> STOP_FEED_VERSION_FK = Internal.createForeignKey(com.schema.Keys.KEY_FEED_VERSION_PRIMARY, Stop.STOP, "stop_feed_version_fk", Stop.STOP.FEED_VERSION);
        public static final ForeignKey<StopTimeRecord, FeedVersionRecord> STOP_TIME_FEED_VERSION_FK = Internal.createForeignKey(com.schema.Keys.KEY_FEED_VERSION_PRIMARY, StopTime.STOP_TIME, "stop_time_feed_version_fk", StopTime.STOP_TIME.FEED_VERSION);
        public static final ForeignKey<TripRecord, RouteRecord> TRIP_IBFK_1 = Internal.createForeignKey(com.schema.Keys.KEY_ROUTE_PRIMARY, Trip.TRIP, "trip_ibfk_1", Trip.TRIP.ROUTE_ID);
        public static final ForeignKey<TripRecord, ServiceRecord> TRIP_IBFK_2 = Internal.createForeignKey(com.schema.Keys.KEY_SERVICE_PRIMARY, Trip.TRIP, "trip_ibfk_2", Trip.TRIP.SERVICE_ID);
        public static final ForeignKey<TripRecord, ShapeRecord> TRIP_IBFK_3 = Internal.createForeignKey(com.schema.Keys.KEY_SHAPE_PRIMARY, Trip.TRIP, "trip_ibfk_3", Trip.TRIP.SHAPE_ID);
        public static final ForeignKey<TripRecord, FeedVersionRecord> TRIP_FEED_VERSION_FK = Internal.createForeignKey(com.schema.Keys.KEY_FEED_VERSION_PRIMARY, Trip.TRIP, "trip_feed_version_fk", Trip.TRIP.FEED_VERSION);
    }
}
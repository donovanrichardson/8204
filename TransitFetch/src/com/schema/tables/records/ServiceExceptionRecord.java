/*
 * This file is generated by jOOQ.
 */
package com.schema.tables.records;


import com.schema.tables.ServiceException;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UByte;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ServiceExceptionRecord extends UpdatableRecordImpl<ServiceExceptionRecord> implements Record4<String, String, UByte, String> {

    private static final long serialVersionUID = 1433345346;

    /**
     * Setter for <code>gtfs.service_exception.service_id</code>. calendar_dates.txt
     */
    public void setServiceId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>gtfs.service_exception.service_id</code>. calendar_dates.txt
     */
    public String getServiceId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>gtfs.service_exception.date</code>.
     */
    public void setDate(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>gtfs.service_exception.date</code>.
     */
    public String getDate() {
        return (String) get(1);
    }

    /**
     * Setter for <code>gtfs.service_exception.exception_type</code>.
     */
    public void setExceptionType(UByte value) {
        set(2, value);
    }

    /**
     * Getter for <code>gtfs.service_exception.exception_type</code>.
     */
    public UByte getExceptionType() {
        return (UByte) get(2);
    }

    /**
     * Setter for <code>gtfs.service_exception.feed_version</code>.
     */
    public void setFeedVersion(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>gtfs.service_exception.feed_version</code>.
     */
    public String getFeedVersion() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record3<String, String, String> key() {
        return (Record3) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<String, String, UByte, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<String, String, UByte, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return ServiceException.SERVICE_EXCEPTION.SERVICE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ServiceException.SERVICE_EXCEPTION.DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UByte> field3() {
        return ServiceException.SERVICE_EXCEPTION.EXCEPTION_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ServiceException.SERVICE_EXCEPTION.FEED_VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getServiceId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UByte component3() {
        return getExceptionType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getFeedVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getServiceId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UByte value3() {
        return getExceptionType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getFeedVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceExceptionRecord value1(String value) {
        setServiceId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceExceptionRecord value2(String value) {
        setDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceExceptionRecord value3(UByte value) {
        setExceptionType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceExceptionRecord value4(String value) {
        setFeedVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceExceptionRecord values(String value1, String value2, UByte value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ServiceExceptionRecord
     */
    public ServiceExceptionRecord() {
        super(ServiceException.SERVICE_EXCEPTION);
    }

    /**
     * Create a detached, initialised ServiceExceptionRecord
     */
    public ServiceExceptionRecord(String serviceId, String date, UByte exceptionType, String feedVersion) {
        super(ServiceException.SERVICE_EXCEPTION);

        set(0, serviceId);
        set(1, date);
        set(2, exceptionType);
        set(3, feedVersion);
    }
}

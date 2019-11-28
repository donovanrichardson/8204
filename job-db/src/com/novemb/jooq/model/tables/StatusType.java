/*
 * This file is generated by jOOQ.
 */
package novemb.jooq.model.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import novemb.jooq.model.Indexes;
import novemb.jooq.model.Jobs;
import novemb.jooq.model.Keys;
import novemb.jooq.model.tables.records.StatusTypeRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.jooq.types.UInteger;


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
public class StatusType extends TableImpl<StatusTypeRecord> {

    private static final long serialVersionUID = -629404973;

    /**
     * The reference instance of <code>jobs.status_type</code>
     */
    public static final StatusType STATUS_TYPE = new StatusType();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StatusTypeRecord> getRecordType() {
        return StatusTypeRecord.class;
    }

    /**
     * The column <code>jobs.status_type.status_type_id</code>.
     */
    public final TableField<StatusTypeRecord, UInteger> STATUS_TYPE_ID = createField("status_type_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>jobs.status_type.name</code>.
     */
    public final TableField<StatusTypeRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>jobs.status_type.description</code>.
     */
    public final TableField<StatusTypeRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>jobs.status_type</code> table reference
     */
    public StatusType() {
        this(DSL.name("status_type"), null);
    }

    /**
     * Create an aliased <code>jobs.status_type</code> table reference
     */
    public StatusType(String alias) {
        this(DSL.name(alias), STATUS_TYPE);
    }

    /**
     * Create an aliased <code>jobs.status_type</code> table reference
     */
    public StatusType(Name alias) {
        this(alias, STATUS_TYPE);
    }

    private StatusType(Name alias, Table<StatusTypeRecord> aliased) {
        this(alias, aliased, null);
    }

    private StatusType(Name alias, Table<StatusTypeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> StatusType(Table<O> child, ForeignKey<O, StatusTypeRecord> key) {
        super(child, key, STATUS_TYPE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Jobs.JOBS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.STATUS_TYPE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<StatusTypeRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_STATUS_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<StatusTypeRecord> getPrimaryKey() {
        return Keys.KEY_STATUS_TYPE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<StatusTypeRecord>> getKeys() {
        return Arrays.<UniqueKey<StatusTypeRecord>>asList(Keys.KEY_STATUS_TYPE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StatusType as(String alias) {
        return new StatusType(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StatusType as(Name alias) {
        return new StatusType(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public StatusType rename(String name) {
        return new StatusType(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public StatusType rename(Name name) {
        return new StatusType(name, null);
    }
}

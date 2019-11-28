/*
 * This file is generated by jOOQ.
 */
package novemb.jooq.model;


import javax.annotation.Generated;

import novemb.jooq.model.tables.Job;
import novemb.jooq.model.tables.Location;
import novemb.jooq.model.tables.Status;
import novemb.jooq.model.tables.StatusType;


/**
 * Convenience access to all tables in jobs
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>jobs.job</code>.
     */
    public static final Job JOB = novemb.jooq.model.tables.Job.JOB;

    /**
     * The table <code>jobs.location</code>.
     */
    public static final Location LOCATION = novemb.jooq.model.tables.Location.LOCATION;

    /**
     * The table <code>jobs.status</code>.
     */
    public static final Status STATUS = novemb.jooq.model.tables.Status.STATUS;

    /**
     * The table <code>jobs.status_type</code>.
     */
    public static final StatusType STATUS_TYPE = novemb.jooq.model.tables.StatusType.STATUS_TYPE;
}

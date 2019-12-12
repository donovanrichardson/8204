package main.java;

import com.schema.tables.records.FeedRecord;
import com.schema.tables.records.FeedVersionRecord;
import org.jooq.Result;

import java.io.IOException;

public interface GTFSController {

    void addFeeds(FeedQuery q) throws IOException;

    Result<FeedRecord> getFeeds();

    void addFeedVersion(String feedId) throws IOException;

    Result<FeedVersionRecord> getFeedVersions();

    String getLatest(String feed);
}

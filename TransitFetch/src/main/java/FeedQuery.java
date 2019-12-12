package main.java;

import java.io.IOException;

/**
 * Interface for objects that describe a set of gtfs feeds. Objects of this interface provide parameters by which   feeds can be accessed from the TransitFeeds api.
 */
public interface FeedQuery {
    void addSpecifiedFeeds(AppController dsl, String apiKey) throws IOException;
}

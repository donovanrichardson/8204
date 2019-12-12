package main.java;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

import static com.schema.Tables.FEED;

/**
 * Represents info sufficient to retrieve a singular GTFS feed.
 */
public class IdFeedQuery implements FeedQuery {

    String feedId;

    /**
     *
     * @param feedId a single feed's id.
     */
    public IdFeedQuery(String feedId) {
        this.feedId = feedId;
    }

    @Override
    public void addSpecifiedFeeds(AppController ac, String apiKey) throws IOException {

        String requestUrl = String.format("https://api.transitfeeds.com/v1/getFeedVersions?key=%s&feed=%s&page=1&limit=10&err=1&warn=1",apiKey,feedId);
        Connection api = Jsoup.connect(requestUrl);
        String jString = api.ignoreContentType(true).get().body().text(); //Throws IOException
        JSONObject feedJSON = new JSONObject(jString);
        JSONObject relevantJSON = feedJSON.getJSONObject("results").getJSONArray("versions").getJSONObject(0).getJSONObject("f");

        String feedid2 = relevantJSON.getString("id");
        String feedType = relevantJSON.getString("ty");
        String feedTitle = relevantJSON.getString("t");
        int location = relevantJSON.getJSONObject("l").getInt("id");

        ac.dsl.insertInto(FEED, FEED.ID, FEED.TYPE, FEED.TITLE, FEED.LOCATION).values(feedid2, feedType, feedTitle, location).execute();
        ac.addFeedVersion(feedid2);
    }
}

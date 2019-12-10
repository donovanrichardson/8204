package main.java;

import com.schema.tables.FeedVersion;
import com.schema.tables.records.FeedRecord;
import com.schema.tables.records.FeedVersionRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.types.ULong;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.schema.Tables.FEED;
import static com.schema.Tables.FEED_VERSION;

public class AppController implements GTFSController {

    String dbuser;
    String dbpass;
    String apiKey;
    DSLContext dsl;

    public AppController(String dbuser, String dbpass, String apiKey) throws SQLException {
        this.dbuser = dbuser;
        this.dbpass = dbpass;
        this.apiKey = apiKey;
//        System.out.println(this.dbuser);
//        System.out.println(this.dbpass);
//        System.out.println(this.apiKey);
        java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/lexicon?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", this.dbuser, this.dbpass);
        Configuration conf = new DefaultConfiguration().set(conn).set(SQLDialect.MYSQL_8_0);
        this.dsl = DSL.using(conf);
    }

    @Override
    public void addFeeds(FeedQuery q) throws IOException {

        q.addSpecifiedFeeds(this.dsl, this.apiKey);

    }

    private JSONObject getFeedJSON(String feedId) throws IOException {
        String requestUrl = String.format("https://api.transitfeeds.com/v1/getFeedVersions?key=%s&feed=%s&page=1&limit=10&err=1&warn=1",this.apiKey,feedId);
        Connection api = Jsoup.connect(requestUrl);
        String jString = api.ignoreContentType(true).get().body().text(); //Throws IOException
        return new JSONObject(jString);
    }

    @Override
    public Result<FeedRecord> getFeeds() {
        return dsl.selectFrom(FEED).fetch();
    }

    @Override
    public void addFeedVersion(FeedRecord f) throws IOException {
        FeedVersion fv = FEED_VERSION;
        String feedId = f.get(FEED.ID);
        JSONObject versionsJson = getFeedJSON(feedId);
        JSONObject latest = versionsJson.getJSONObject("results").getJSONArray("versions").getJSONObject(0);
        String versionId = latest.getString("id");
        ULong timestamp = ULong.valueOf(latest.getLong("ts"));
        ULong size = ULong.valueOf(latest.getLong("size"));
        String downloadUrl = latest.getString("url");
        String start = latest.getJSONObject("d").getString("s");//these two will need to be cast to unsigned to be at all useful
        String finish = latest.getJSONObject("d").getString("f");

        dsl.insertInto(fv, fv.ID, fv.FEED, fv.TIMESTAMP, fv.SIZE, fv.URL, fv.START, fv.FINISH).values(versionId, feedId, timestamp, size, downloadUrl, start, finish).execute();
    }

    @Override
    public Result<FeedVersionRecord> getFeedVersions() {
        return dsl.selectFrom(FEED_VERSION).fetch();
    }
}

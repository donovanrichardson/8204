package main.java;

import com.schema.tables.FeedVersion;
import com.schema.tables.records.FeedRecord;
import com.schema.tables.records.FeedVersionRecord;
import main.java.wrapper.exception.OptionalTableException;
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

import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.schema.Tables.FEED;
import static com.schema.Tables.FEED_VERSION;
import static org.junit.jupiter.api.Assertions.fail;

public class AppController implements GTFSController {

    String dbuser;
    String dbpass;
    String apiKey;
    DSLContext dsl;
    private static final String[] order = {"agency.txt", "stops.txt", "routes.txt", "calendar.txt", "calendar_dates.txt", "shapes.txt", "trips.txt", "frequencies.txt", "stop_times.txt"};

    public AppController(String dbuser, String dbpass, String apiKey) throws SQLException {
        this.dbuser = dbuser;
        this.dbpass = dbpass;
        this.apiKey = apiKey;
//        System.out.println(this.dbuser);
//        System.out.println(this.dbpass);
//        System.out.println(this.apiKey);
        java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/gtfs?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", this.dbuser, this.dbpass);
        Configuration conf = new DefaultConfiguration().set(conn).set(SQLDialect.MYSQL_8_0);
        this.dsl = DSL.using(conf);
    }

    @Override
    public void addFeeds(FeedQuery q) throws IOException {

        q.addSpecifiedFeeds(this, this.apiKey);

    }

    private JSONObject  getFeedJSON(String feedId) throws IOException {
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
    public void addFeedVersion(String feedId) throws IOException {
        FeedVersion fv = FEED_VERSION;
        JSONObject versionsJson = null;
        try {
            versionsJson = getFeedJSON(feedId);
        } catch (IOException e) {
            System.out.println("Could not GET the GTFS versions");
            throw e;
        }
        JSONObject latest = versionsJson.getJSONObject("results").getJSONArray("versions").getJSONObject(0);
        String versionId = latest.getString("id");
        ULong timestamp = ULong.valueOf(latest.getLong("ts"));
        ULong size = ULong.valueOf(latest.getLong("size"));
        String downloadUrl = latest.getString("url");
        String start = latest.getJSONObject("d").getString("s");//these two will need to be cast to unsigned to be at all useful
        String finish = latest.getJSONObject("d").getString("f");

        dsl.insertInto(fv, fv.ID, fv.FEED, fv.TIMESTAMP, fv.SIZE, fv.URL, fv.START, fv.FINISH).values(versionId, feedId, timestamp, size, downloadUrl, start, finish).execute();

        try(ZipInputStream verZip = this.getGtfsZip(versionId)){ //IOException may be thrown on this line
            GtfsImporter imp = new GtfsImporter(this.dsl);
            Map<String, InputStream> zipMap;
            try{
                zipMap = this.unzip(verZip);
            } catch (Exception e){
                throw e;
            } finally{
                verZip.close();
            }
            try {
                for (String txt : order) {
                    try{
                        imp.addTxt(txt, zipMap.get(txt)); //IOException may be thrown on this line
                    } catch (OptionalTableException o){
                        continue;
                    }
                }
            } catch (IOException e){
                this.dsl.deleteFrom(FEED_VERSION).where(FEED_VERSION.ID.eq(versionId)).execute();
                e.printStackTrace();
                System.out.println(String.format("All records associated with %s have been removed to avoid incomplete data import", versionId));
            }
        }catch (IOException io1){
            System.out.println("Could not retrieve the GTFS zip file.");
            throw io1;
        }

    }

    @Override
    public Result<FeedVersionRecord> getFeedVersions() {
        return dsl.selectFrom(FEED_VERSION).fetch();
    }

    /**
     * gets the datebase's latest feed version id from a feed
     * @param feed feed id
     * @return
     */
    @Override
    public String getLatest(String feed) {
        return this.dsl.select(FEED_VERSION.ID).from(FEED_VERSION).where(FEED_VERSION.FEED.eq(feed)).orderBy(FEED_VERSION.TIMESTAMP.desc()).fetchOne(FEED_VERSION.ID);
    }

//    @Override
//    public void importFeeds() {
//
//        List<String> importVersions = this.dsl.select(FEED_VERSION.ID, AGENCY.KEY).from(FEED_VERSION.leftJoin(AGENCY).on(AGENCY.FEED_VERSION.eq(FEED_VERSION.ID))).where(AGENCY.FEED_VERSION.isNull()).fetch().getValues(FEED_VERSION.ID);
//
//        for(String v : importVersions){
//            try(ZipInputStream verZip = this.getGtfsZip(v)){
//                GtfsImporter imp = new GtfsImporter(this.dsl);
//                Map<String, InputStream> zipMap = this.unzip(verZip);
//                for(String txt : order){
//                    imp.addTxt(txt, zipMap.get(txt));
//                }
//            }
//        }
//
//    }

    private Map<String, InputStream> unzip(ZipInputStream verZip) { //todo memory constraints are preventing this

        Map<String, InputStream> result = new HashMap<>();

        while (true) {
            ZipEntry entry;
            byte[] b = new byte[1024];
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int l;

            try {
                entry = verZip.getNextEntry();
            } catch (IOException e) {
                break;
            }

            if (entry == null) {
                break;
            }

            try {
                while ((l = verZip.read(b)) > 0) {
                    out.write(b, 0, l);
                }
                out.flush();
            }catch(EOFException e){
                e.printStackTrace();
            }
            catch (IOException i) {
                System.out.println("there was an ioexception");
                i.printStackTrace();
                fail();
            }
            result.put(entry.getName(), new ByteArrayInputStream(out.toByteArray()));
        }
        return result;
    }

    private ZipInputStream getGtfsZip(String v) throws IOException {
        String apiUrl = this.dsl.select(FEED_VERSION.URL).from(FEED_VERSION).where(FEED_VERSION.ID.eq(v)).fetchOne(FEED_VERSION.URL);
        Connection zipConnect = Jsoup.connect(apiUrl);
        Connection.Response almostZipStream = zipConnect.ignoreContentType(true).execute();
        ZipInputStream zis = new ZipInputStream(almostZipStream.bodyStream());
        return zis;
    }
}

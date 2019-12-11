package test.java;

import main.java.AppController;
import main.java.FeedQuery;
import main.java.GTFSController;
import main.java.IdFeedQuery;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import static com.schema.tables.FeedVersion.FEED_VERSION;
import static org.junit.jupiter.api.Assertions.fail;
import static com.schema.tables.Feed.FEED;

public class FeedImportTest extends AbstractTest {

    GTFSController c;

    @Override
    void setup() throws FileNotFoundException, SQLException {
        super.setup();
        this.c = new AppController(this.username, this.password, this.apiKey);
    }



    FeedQuery mexico = new IdFeedQuery("mexico-city-federal-district-government/70");
    FeedQuery sfmuni = new IdFeedQuery("sfmta/60");

    @Test
    public void selectTest() throws FileNotFoundException, SQLException {
        this.setup();
        System.out.println(this.dsl.select(FEED.ID, FEED_VERSION.ID).from(FEED.leftJoin(FEED_VERSION).on(FEED.ID.eq(FEED_VERSION.FEED))).where(FEED_VERSION.ID.isNull()).fetch().getValues(FEED.ID));
    }

    @Test
    public void importFeedsTest(){
        try{
            this.c.addFeeds(mexico);
            this.c.addFeeds(sfmuni);
        } catch (IOException e){
            e.printStackTrace();
            fail();
        }

        this.c.importFeeds();

        String mexicoVer = c.getLatest(mexico);
        String sfVer = c.getLatest(sfmuni);


        System.out.println(this.dsl.fetch(String.format("select count(*) from stop_time where feed_version = %s group by feed_version", mexicoVer)));
        System.out.println(this.dsl.fetch(String.format("select count(*) from stop_time where feed_version = %s group by feed_version", sfVer)));

    }

}

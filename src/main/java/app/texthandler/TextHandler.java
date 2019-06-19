package app.texthandler;

import com.march.jooq.model.Routines;
import com.march.jooq.model.Tables;
import com.march.jooq.model.routines.AddContinuance;
import com.march.jooq.model.routines.DocAdd;
import com.march.jooq.model.routines.Kotoba;
import com.march.jooq.model.routines.WordAdd;
import com.march.jooq.model.tables.records.TextstringRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;


import java.sql.Connection;
import java.util.Scanner;

import static com.march.jooq.model.tables.Docs.DOCS;

/**
 * Handles adding of text data to database
 */
public class TextHandler {

    Connection c;
    Configuration f;
    DSLContext d;

    public TextHandler(Connection c) {
        this.c = c;
        this.f = new DefaultConfiguration().set(this.c).set(SQLDialect.MYSQL_8_0);
        this.d = DSL.using(this.f);
    }

    /**
     * Provides a new document for referencing in the database
     * @param title title of the document
     */
    public void newDoc(String title) {

        /*TODO change this to procedure from new schema
        1: insertInto will now be a procedure
        2: this will no longer be void because it will return the result of a select statement in the procedure, which the program will use to track how many documents are added.
         */

        //TODO idk why im writing this here, but the main function can have a method which calls this repeatedly a number of times. like 30,000 times.
        Routines.docAdd(this.f, title);
        //TODO at some point I would just like to make sure that I won't fail if a title is longer than 256, even though that is vv unlikely
        //TODO also, I want to make a table that connects doc titles to wiki revision ids. Actually, the primary key for docs must be a combo of doc_name and revision id. I want this from the beginning because I don't want the rev id to be a mystery if I implement it later.

    }

    /**
     * Adds a word to the database, associatin them to the current document. If a word is too long, it is split into multiple words.
     * @param theWord the word to be added
     */
    public void addWord(String theWord) {
        if(theWord.length() > 100) {
            String beginning = theWord.substring(0,100);
            this.addWord(beginning);
            this.addContinuance(theWord.substring(100));
        }else {
            Routines.kotoba(this.f, theWord);
        }
    }

    /**
     * Splits a word to be added (by addWord()) into multiple words if it is too long. Handles relation of split words to each other.
     * @param substring remainder of split word yet-to-be-added
     */
    private void addContinuance(String substring) {
        if(substring.length() > 100){
            String middle = substring.substring(0,100);
            this.addContinuance(middle);
            this.addContinuance(substring.substring(100));
        } else {
            Routines.addContinuance(this.f, substring);
        }
    }

    /**
     * Recursively calls method addWord() to add words to the database, associating them with the current doc.
     *
     * @param s scanner which contains document contents.
     */
    public void addDoc(Scanner s) {
        while (s.hasNext()){
            this.addWord(s.next());
        }
        Result<TextstringRecord> recentTen = this.d.selectFrom(Tables.TEXTSTRING).orderBy(Tables.TEXTSTRING.DOC_ID.desc()).limit(10).fetch();
        System.out.print(recentTen);
    }
}

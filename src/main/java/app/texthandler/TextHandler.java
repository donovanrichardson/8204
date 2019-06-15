package app.texthandler;

import com.march.jooq.model.Tables;
import com.march.jooq.model.routines.AddContinuance;
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

        this.d.insertInto(DOCS, DOCS.DOC_NAME).values(title).execute();

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
            Kotoba add = new Kotoba();
            add.setOneWord(theWord);
            add.execute(this.f);
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
            AddContinuance cntn = new AddContinuance();
            cntn.setItem(substring);
            cntn.execute(this.f);
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

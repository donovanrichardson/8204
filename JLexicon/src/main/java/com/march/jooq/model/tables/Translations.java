/*
 * This file is generated by jOOQ.
 */
package com.march.jooq.model.tables;


import com.march.jooq.model.Indexes;
import com.march.jooq.model.Keys;
import com.march.jooq.model.Lexicon;
import com.march.jooq.model.tables.records.TranslationsRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

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
public class Translations extends TableImpl<TranslationsRecord> {

    private static final long serialVersionUID = -570136979;

    /**
     * The reference instance of <code>lexicon.translations</code>
     */
    public static final Translations TRANSLATIONS = new Translations();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TranslationsRecord> getRecordType() {
        return TranslationsRecord.class;
    }

    /**
     * The column <code>lexicon.translations.trans_id</code>.
     */
    public final TableField<TranslationsRecord, Integer> TRANS_ID = createField("trans_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>lexicon.translations.trans</code>.
     */
    public final TableField<TranslationsRecord, String> TRANS = createField("trans", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>lexicon.translations.from_word</code>.
     */
    public final TableField<TranslationsRecord, String> FROM_WORD = createField("from_word", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>lexicon.translations.to_lang</code>.
     */
    public final TableField<TranslationsRecord, Integer> TO_LANG = createField("to_lang", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>lexicon.translations</code> table reference
     */
    public Translations() {
        this(DSL.name("translations"), null);
    }

    /**
     * Create an aliased <code>lexicon.translations</code> table reference
     */
    public Translations(String alias) {
        this(DSL.name(alias), TRANSLATIONS);
    }

    /**
     * Create an aliased <code>lexicon.translations</code> table reference
     */
    public Translations(Name alias) {
        this(alias, TRANSLATIONS);
    }

    private Translations(Name alias, Table<TranslationsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Translations(Name alias, Table<TranslationsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Translations(Table<O> child, ForeignKey<O, TranslationsRecord> key) {
        super(child, key, TRANSLATIONS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Lexicon.LEXICON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.TRANSLATIONS_FROM_WORD_TRANS_FK, Indexes.TRANSLATIONS_PRIMARY, Indexes.TRANSLATIONS_TO_LANG_TRANS_FK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TranslationsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TRANSLATIONS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TranslationsRecord> getPrimaryKey() {
        return Keys.KEY_TRANSLATIONS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TranslationsRecord>> getKeys() {
        return Arrays.<UniqueKey<TranslationsRecord>>asList(Keys.KEY_TRANSLATIONS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TranslationsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TranslationsRecord, ?>>asList(Keys.FROM_WORD_TRANS_FK, Keys.TO_LANG_TRANS_FK);
    }

    public Words words() {
        return new Words(this, Keys.FROM_WORD_TRANS_FK);
    }

    public Langs langs() {
        return new Langs(this, Keys.TO_LANG_TRANS_FK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Translations as(String alias) {
        return new Translations(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Translations as(Name alias) {
        return new Translations(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Translations rename(String name) {
        return new Translations(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Translations rename(Name name) {
        return new Translations(name, null);
    }
}

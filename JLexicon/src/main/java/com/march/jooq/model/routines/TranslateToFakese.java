/*
 * This file is generated by jOOQ.
 */
package com.march.jooq.model.routines;


import com.march.jooq.model.Lexicon;

import javax.annotation.Generated;

import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;


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
public class TranslateToFakese extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -973372862;

    /**
     * The parameter <code>lexicon.translate_to_fakese.origin_word</code>.
     */
    public static final Parameter<String> ORIGIN_WORD = createParameter("origin_word", org.jooq.impl.SQLDataType.VARCHAR(100), false, false);

    /**
     * The parameter <code>lexicon.translate_to_fakese.fakese_word</code>.
     */
    public static final Parameter<String> FAKESE_WORD = createParameter("fakese_word", org.jooq.impl.SQLDataType.VARCHAR(100), false, false);

    /**
     * Create a new routine call instance
     */
    public TranslateToFakese() {
        super("translate_to_fakese", Lexicon.LEXICON);

        addInParameter(ORIGIN_WORD);
        addInParameter(FAKESE_WORD);
    }

    /**
     * Set the <code>origin_word</code> parameter IN value to the routine
     */
    public void setOriginWord(String value) {
        setValue(ORIGIN_WORD, value);
    }

    /**
     * Set the <code>fakese_word</code> parameter IN value to the routine
     */
    public void setFakeseWord(String value) {
        setValue(FAKESE_WORD, value);
    }
}
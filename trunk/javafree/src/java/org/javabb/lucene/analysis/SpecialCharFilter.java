/*
 * Copyright 28/03/2005 - Vicinity - www.vicinity.com.br All rights reserveds
 */
package org.javabb.lucene.analysis;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

/**
 * Avoid special chars in token terms. Special chars includes only accentuateds
 * letters like "ב" , "Ó", etc, and "c" with a cedilla. Note that it is case
 * insensitive and it always replace with a lower case letter.
 * 
 * @author Marcos Silva Pereira - marcos.pereira@vicinity.com.br
 * @version $Id: SpecialCharFilter.java,v 1.1 2009/05/11 20:27:05 daltoncamargo
 *          Exp $
 */
class SpecialCharFilter extends TokenFilter {

    private static final String[] REPLACES;

    private static final Pattern[] PATTERNS;

    static {

	REPLACES = new String[] { "a", "e", "i", "o", "u", "c" };

	PATTERNS = new Pattern[REPLACES.length];

	// pre compile patterns
	PATTERNS[0] = Pattern.compile("[גדבאה]", Pattern.CASE_INSENSITIVE);
	PATTERNS[1] = Pattern.compile("[יטךכ]", Pattern.CASE_INSENSITIVE);
	PATTERNS[2] = Pattern.compile("[םלמן]", Pattern.CASE_INSENSITIVE);
	PATTERNS[3] = Pattern.compile("[ףעפץצ]", Pattern.CASE_INSENSITIVE);
	PATTERNS[4] = Pattern.compile("[תשûü]", Pattern.CASE_INSENSITIVE);
	PATTERNS[5] = Pattern.compile("ח", Pattern.CASE_INSENSITIVE);

    }

    /**
     * @param in
     */
    public SpecialCharFilter(TokenStream in) {

	super(in);

    }

    /**
     * @see org.apache.lucene.analysis.TokenStream#next()
     */
    public Token next() throws IOException {

	Token t = input.next();

	if (t == null) {

	    return null;

	}

	String termText = replaceSpecial(t.termText());
	Token token = new Token(termText, t.startOffset(), t.endOffset());

	return token;

    }

    private String replaceSpecial(String text) {

	String result = text;

	for (int i = 0; i < PATTERNS.length; i++) {

	    Matcher matcher = PATTERNS[i].matcher(result);
	    result = matcher.replaceAll(REPLACES[i]);

	}

	return result;

    }
}

package org.javabb.infra;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharFilter {

    public static String[] REPLACES = { "a", "e", "i", "o", "u", "c" };

    public static Pattern[] PATTERNS = null;

    public static void compilePatterns() {
	PATTERNS = new Pattern[REPLACES.length];
	PATTERNS[0] = Pattern.compile("[גדבאה]", Pattern.CASE_INSENSITIVE);
	PATTERNS[1] = Pattern.compile("[יטךכ]", Pattern.CASE_INSENSITIVE);
	PATTERNS[2] = Pattern.compile("[םלמן]", Pattern.CASE_INSENSITIVE);
	PATTERNS[3] = Pattern.compile("[ףעפץצ]", Pattern.CASE_INSENSITIVE);
	PATTERNS[4] = Pattern.compile("[תשûü]", Pattern.CASE_INSENSITIVE);
	PATTERNS[5] = Pattern.compile("[ח]", Pattern.CASE_INSENSITIVE);
    }

    public static String replaceSpecial(String text) {
	if (PATTERNS == null) {
	    compilePatterns();
	}

	String result = text;
	for (int i = 0; i < PATTERNS.length; i++) {
	    Matcher matcher = PATTERNS[i].matcher(result);
	    result = matcher.replaceAll(REPLACES[i]);
	}
	return result;
    }

}

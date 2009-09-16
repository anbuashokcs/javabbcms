package org.javabb.infra.replacer;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.vo.BadWord;

public class WordStringReplacer {

    protected final Log log = LogFactory.getLog(WordStringReplacer.class);
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private HashMap<String, CommonPrefixList> map = new HashMap<String, CommonPrefixList>();

    private HashSet<Character> scape;

    private String canonize(String key) {
	return key.toLowerCase();
    }

    public void fill(List<BadWord> provider) {
	int capacity = findNextPrime(provider.size() * 2);

	HashMap<String, CommonPrefixList> newMap = new HashMap<String, CommonPrefixList>(
		capacity);

	for (BadWord pair : provider) {
	    CommonPrefixList prefix = null;
	    int pos = getNextWord(pair.getWord(), 0);
	    String key = pair.getWord().substring(0, pos);
	    prefix = newMap.get(key);

	    if (prefix == null) {
		prefix = new CommonPrefixList();
	    }

	    prefix.addPair(pair);
	    newMap.put(canonize(key), prefix);
	}

	try {
	    lock.writeLock().lock();

	    map = newMap;
	} finally {
	    lock.writeLock().unlock();
	}

    }

    private int findNextPrime(Integer n) {
	return (new BigInteger(n.toString())).nextProbablePrime().intValue();
    }

    private int getNextWord(CharSequence text, int pos) {
	int len = text.length();

	while (pos < len && !isEscape(text.charAt(pos))) {
	    pos++;
	}

	return pos;
    }

    private boolean isEscape(char charAt) {
	if (scape == null) {
	    Character[] escapeChars = { ' ', '\n', '\r', '.', ',', ';', ':',
		    '!', '?', '\'', '"', '/', '-', '(', ')' };
	    scape = new HashSet<Character>(escapeChars.length);
	    for (Character c : escapeChars) {
		scape.add(c);
	    }
	}
	return scape.contains(charAt);
    }

    public String replace(String textEntry) {
	int pos = 0;
	int start = 0;
	StringBuilder text = new StringBuilder(textEntry);
	StringBuilder response = new StringBuilder(textEntry.length());

	while ((pos = getNextWord(text, pos)) <= text.length()) {
	    CharSequence key = text.subSequence(start, pos);
	    char afterChar = 0;
	    CommonPrefixList commomPrefix = null;

	    if ((pos + 1) < text.length()) {
		afterChar = text.charAt(pos);
	    }

	    try {
		lock.readLock().lock();
		commomPrefix = this.map.get(canonize(key.toString()));

	    } finally {
		lock.readLock().unlock();
	    }

	    BadWord pair = null;

	    if ((commomPrefix != null)
		    && ((pair = commomPrefix.getValue(text, start)) != null)) {
		response.append(pair.getReplacement());
		start += pair.getWord().length();
		pos = start;
	    } else {
		response.append(key).append(afterChar);
		start = ++pos;
	    }
	}
	if ((pos - 1) < response.length()) {
	    response.setLength(pos - 1);
	} else {
	    response.setLength(response.length() - 1);
	}
	return response.toString();
    }

    public class CommonPrefixList {

	private LinkedList<BadWord> pairs = new LinkedList<BadWord>();

	public void addPair(BadWord newPair) {
	    ListIterator<BadWord> listIterator = pairs.listIterator();

	    while (listIterator.hasNext()) {
		BadWord pair = listIterator.next();

		if (newPair.getWord().length() > pair.getWord().length()) {
		    listIterator.previous();

		    break;
		}
	    }

	    listIterator.add(newPair);
	}

	public BadWord getValue(CharSequence text, int pos) {
	    for (BadWord p : pairs) {
		log.info(text + "/" + p.getWord());
		log
			.info(text.length() + "/" + p.getWord().length() + "/"
				+ pos);
		if ((p.getWord().length() + pos - 1) < text.length()) {
		    CharSequence candidate = text.subSequence(pos, pos
			    + p.getWord().length());
		    if (p.getWord().compareToIgnoreCase(candidate.toString()) == 0) {
			return p;
		    }
		}
	    }

	    return null;
	}
    }
}

/*
 * Copyright 28/03/2005 - Vicinity - www.vicinity.com.br All rights reserveds
 */
package org.javabb.lucene.search;


import java.util.Iterator;
import java.util.Map;

import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.TokenGroup;


/**
 * Simple {@link org.apache.lucene.search.highlight.Formatter Formatter} to
 * highlight a term with a <a href="http://www.w3c.org/CSS">Cascade Style Sheet</a>
 * class. With CSS class you can alter highlight form easily.
 *
 * @author Marcos Silva Pereira - marcos.pereira@vicinity.com.br
 *
 * @since 05/03/2005
 *
 * @version $Id: CSSFormatter.java,v 1.1 2009/05/11 20:27:10 daltoncamargo Exp $
 *
 * @see org.apache.lucene.search.highlight.SpanGradientFormatter
 */
public class CSSFormatter implements Formatter {

	private String cssClass;

	private String cssStyle;

	/**
	 * @param cssClass
	 */
	public CSSFormatter ( String cssClass ) {

		this.cssClass = cssClass;

	}

	/**
	 * @param properties
	 */
	public CSSFormatter ( Map properties ) {

		cssStyle = constructStyle(properties);

	}

	/**
	 * @see Formatter#highlightTerm(String, TokenGroup)
	 */
	public String highlightTerm( String originalText, TokenGroup tokenGroup ) {

		if (tokenGroup.getTotalScore() > 0) {

			return doHighlightTerm(originalText);

		}

		return originalText;

	}

	private String doHighlightTerm( String originalText ) {

		StringBuffer buffer = new StringBuffer();

		if ((cssClass != null) && !cssClass.trim().equals("")) {

			buffer.append("<span class=\"").append(cssClass).append("\">");
			buffer.append(originalText);
			buffer.append("</span>");

		} else {

			buffer.append("<span style=\"").append(cssStyle).append("\">");
			buffer.append(originalText);
			buffer.append("</span>");

		}

		return buffer.toString();

	}

	private String constructStyle( Map cssProperties ) {

		StringBuffer buffer = new StringBuffer();
		Iterator iterator = cssProperties.entrySet().iterator();

		while (iterator.hasNext()) {

			Map.Entry entry = (Map.Entry) iterator.next();

			buffer.append(entry.getKey()).append(":");
			buffer.append(entry.getValue()).append(";");

		}

		return buffer.toString();

	}

}

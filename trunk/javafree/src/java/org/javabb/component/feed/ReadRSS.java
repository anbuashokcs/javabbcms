package org.javabb.component.feed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.nava.informa.core.ChannelIF;
import de.nava.informa.core.ItemIF;
import de.nava.informa.impl.basic.ChannelBuilder;
import de.nava.informa.parsers.FeedParser;

public class ReadRSS {

	public static List getFeeds(String feedURL) throws Exception{
		List feeds = new ArrayList();
		ChannelIF channel = FeedParser.parse(new ChannelBuilder(), feedURL);

		Iterator itens = channel.getItems().iterator();
		while (itens.hasNext()) {
			ItenFeed iten = new ItenFeed();
			ItemIF feed = (ItemIF) itens.next();
			iten.setTitle(feed.getTitle());
			iten.setDesc(feed.getDescription());
			iten.setLink(feed.getLink().toString());
			iten.setDate(feed.getDate());
			feeds.add(iten);
		}

		return feeds;
	}

}

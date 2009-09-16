
package org.javabb.component.feed;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.gnu.stealthp.rsslib.RSSChannel;
import org.gnu.stealthp.rsslib.RSSHandler;
import org.gnu.stealthp.rsslib.RSSItem;


public class RSSReader {

    public SimpleDateFormat RFC822DATEFORMAT = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);
    public SimpleDateFormat JFDATEFORMAT = new SimpleDateFormat("EEE' 'MMM' 'dd' 'HH:mm:ss' 'z' 'yyyy", Locale.US);
	
    private Icon icon; 
    private String channelTitle;
    
    private Iterator itemsIterator;
    private RSSItem item;
    
    public RSSReader(String feed) throws Exception {
        // Parser to XML
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        
        SAXParser parser = factory.newSAXParser();
        RSSHandler hand = new RSSHandler();
        
        // Create the builder and parsing the file
        // For proxy connections gets Proxy configurations.
        parser.parse(new URL(feed).openStream(),hand);
        
        // gets the channel.
        RSSChannel ch = hand.getRSSChannel();

        // Image to display
        icon = null;
        if (ch.getRSSImage() != null) {
            icon = new ImageIcon(new URL(ch.getRSSImage().getUrl()));
        }
        
        channelTitle = ch.getTitle();
        
        // sort items
        List itemsList = new ArrayList(ch.getItems()); 
        Collections.sort(itemsList, new DateComparator());
        itemsIterator = itemsList.iterator();
    }
    
    public Icon getIcon() {
        return icon;
    }
    
    public boolean ifHasNextThenNext() {
        if (itemsIterator.hasNext()) {
            item = (RSSItem)itemsIterator.next();
            return true;
        }
        return false;
    }
    
    public String getChannelTitle() {
        return channelTitle;
    }
    
    public String getItemTitle() {
        return item.getTitle();
    }
    
    public String getDescription() {
        return item.getDescription();
    }
    
    public String getLink() {
        return item.getLink();
    }
    
    public Date getDate() {
        try {
        	if(item.getDate() == null){
        		return null;
        	} else {
        		return RFC822DATEFORMAT.parse(item.getDate());
        	}
        } catch (ParseException e) {
            try {
                return JFDATEFORMAT.parse(item.getDate());
            } catch (ParseException e1) {
                return new Date();    
            }
        }
    }
    
    public Date getPubDate() {
        try {
        	if(item.getPubDate() == null){
        		return null;
        	} else {
        		return RFC822DATEFORMAT.parse(item.getPubDate());
        	}
        } catch (ParseException e) {
            try {
                return JFDATEFORMAT.parse(item.getPubDate());
            } catch (ParseException e1) {
                return new Date();    
            }
        }
    }    
    
    
}

class DateComparator implements Comparator{ 
    public SimpleDateFormat RFC822DATEFORMAT = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);
    public SimpleDateFormat JFDATEFORMAT = new SimpleDateFormat("EEE' 'MMM' 'dd' 'HH:mm:ss' 'z' 'yyyy", Locale.US);
    
    public int compare(Object arg0, Object arg1) {
        RSSItem item1 = (RSSItem) arg0;
        RSSItem item2 = (RSSItem) arg1;
        
        if (item1.getDate() == null && item2.getDate() == null) return 0;
        if (item1.getDate() == null) return -1;
        if (item2.getDate() == null) return 1;
        
        try {
            return RFC822DATEFORMAT.parse(item1.getDate()).compareTo(RFC822DATEFORMAT.parse(item2.getDate()));
        } catch (ParseException e) {
            try {
                return JFDATEFORMAT.parse(item1.getDate()).compareTo(JFDATEFORMAT.parse(item2.getDate()));
            } catch (ParseException e1) {
                return 0;    
            }
        }
    }        
    
}

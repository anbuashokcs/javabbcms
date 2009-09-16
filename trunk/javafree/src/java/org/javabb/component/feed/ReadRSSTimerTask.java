package org.javabb.component.feed;

import java.util.Timer;
import java.util.TimerTask;

import de.nava.informa.core.ChannelIF;
import de.nava.informa.impl.basic.ChannelBuilder;
import de.nava.informa.parsers.FeedParser;

public class ReadRSSTimerTask {
	Timer timer;
	ChannelIF channel;
	boolean timesUp = false;
	String blogFeedURL;
	
	public ReadRSSTimerTask(int seconds){
		timer = new Timer();
        timer.schedule(new RemindTask(), seconds*1000);
	}

	public ChannelIF getChannelIF(String blogFeedURL){
		this.blogFeedURL = blogFeedURL;
		return channel;
	}
	
    class RemindTask extends TimerTask {
        public void run() {
            try {
    			channel = FeedParser.parse(new ChannelBuilder(), blogFeedURL);
    		} catch (Exception e) {
    			//e.printStackTrace();
    		}

            System.out.println("Time's up!");
            timer.cancel();
            timesUp = false;
        }
    }
}

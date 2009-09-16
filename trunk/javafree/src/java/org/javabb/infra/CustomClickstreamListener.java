package org.javabb.infra;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.clickstream.Clickstream;
import com.opensymphony.clickstream.ClickstreamListener;

/**
 * The listener that keeps track of all clickstreams in the container as well as the creating new
 * Clickstream objects and initiating logging when the clickstream dies (session has been
 * invalidated).
 * @author <a href="plightbo@hotmail.com">Patrick Lightbody </a>
 * @author <a href="yoavs@apache.org">Yoav Shapira </a>
 */
public class CustomClickstreamListener extends ClickstreamListener {
    private final Log log = LogFactory.getLog(getClass());

    /**
     * Notification that the ServletContext has been destroyed.
     * @param evt The context event
     */
    public void contextDestroyed(ServletContextEvent evt) {
        Map clickstreams = (Map) evt.getServletContext().getAttribute(CLICKSTREAMS_ATTRIBUTE_KEY);

		if(clickstreams == null) {

			return;

		}

        // invalidates all sessions, to force them to be recreated on context re-initialization.
        Iterator it = clickstreams.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            Clickstream stream = (Clickstream) clickstreams.get(key);

            if (log.isDebugEnabled()) {
                log.debug("Invalidating session: " + key);
            }

            stream.getSession().invalidate();
            clickstreams.remove(key);
        }

        super.contextDestroyed(evt);
    }
}
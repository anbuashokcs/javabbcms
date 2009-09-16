package org.javabb.infra;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.opensymphony.clickstream.Clickstream;
import com.opensymphony.clickstream.ClickstreamFilter;
import com.opensymphony.clickstream.ClickstreamListener;

/**
 * The filter that keeps track of a new entry in the clickstream for <b>every request </b>.
 * @author <a href="plightbo@hotmail.com">Patrick Lightbody </a>
 */
public class CustomClickstreamFilter extends ClickstreamFilter {
    /**
     * Processes the given request and/or response.
     * @param request The request
     * @param response The response
     * @param chain The processing chain
     * @throws IOException If an error occurs
     * @throws ServletException If an error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException,
            ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();
        Map clickstreams = (Map) session.getServletContext()
            .getAttribute(ClickstreamListener.CLICKSTREAMS_ATTRIBUTE_KEY);
        if (!clickstreams.containsKey(session.getId())) {
            Clickstream clickstream = new Clickstream();
            session.setAttribute(ClickstreamListener.SESSION_ATTRIBUTE_KEY, clickstream);
            clickstreams.put(session.getId(), clickstream);
        }

        super.doFilter(request, response, chain);
    }
}
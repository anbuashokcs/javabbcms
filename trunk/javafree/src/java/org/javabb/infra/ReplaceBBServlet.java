package org.javabb.infra;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet que substitui o /javabb/ para o /
 * 
 * @author Dalton
 * 
 */
@SuppressWarnings("serial")
public class ReplaceBBServlet extends HttpServlet {
    /**
     * Called once at startup
     */
    public void init() throws ServletException {
	System.out.println("Init() JavaBB Replacer Servlet");
    }

    /**
     * 
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void replaceViewContent(HttpServletRequest req,
	    HttpServletResponse resp) throws ServletException, IOException {

	Long idContent = 0L;
	if (req.getQueryString() != null) {
	    try {
		idContent = new Long(req.getQueryString().replaceAll(
			"idContent=", ""));
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

	StringBuffer url = new StringBuffer();
	url.append(req.getRequestURI().replaceAll("content/view.jf", ""));

	if (idContent != null) {
	    Integer migratedArti = Constants.getIdNewArticleMigrated(idContent);
	    if (migratedArti.intValue() > 0) {
		url.append("artigo/857152/migrated");
	    }
	}

	try {
	    resp.sendRedirect(url.toString());
	} catch (Exception e) {
	    System.out.println("Erro no response: " + e.getMessage());
	}
    }

    /**
     * 
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void replaceBB(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	StringBuffer url = new StringBuffer();
	url.append(req.getRequestURL().toString().replaceAll("/javabb/", "/"));

	if (req.getQueryString() != null) {
	    url.append("?");
	    url.append(req.getQueryString());
	} else {
	    url.append("index");
	}
	
	try {
	    System.out.println(url);
	    resp.sendRedirect(url.toString());
	} catch (Exception e) {
	    System.out.println("Erro no response: " + e.getMessage());
	}
    }

    /**
     * 
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void replaceRSS(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	StringBuffer url = new StringBuffer();
	url.append(req.getRequestURL().toString().replaceAll("/javabb/", "/"));

	if (req.getQueryString() != null) {
	    url.append("?");
	    url.append(req.getQueryString());
	}
	try {
	    resp.sendRedirect(url.toString());
	} catch (Exception e) {
	    System.out.println("Erro no response: " + e.getMessage());
	}
    }

}

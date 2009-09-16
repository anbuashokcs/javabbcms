package org.javabb.infra;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * This class is used to abstract Spring ApplicationContext creation. Here you
 * can put a lot of locations based on classpath. Course, for web applications
 * use Spring support instead it.
 *
 * @author Marcos Silva Pereira - marcos.pereira@vicinity.com.br
 * @since 08/07/2004
 * @version $Id: ApplicationContextFactory.java,v 1.1 2009/05/11 20:27:02 daltoncamargo Exp $
 */
public final class ApplicationContextFactory {

	private static Log log = LogFactory.getLog(ApplicationContextFactory.class);

	private boolean init;

	private List locations = new ArrayList();

	/**
	 * @param location
	 * @return
	 */
	public ApplicationContextFactory addLocation( String location ) {

		if ((location != null) && !locations.contains(location)) {

			locations.add(location);

		}

		return this;

	}

	/**
	 * @param location
	 * @return
	 */
	public ApplicationContextFactory addLocation( File location ) {

		return addLocation(location.getPath());

	}

	/**
	 * @param location
	 * @return
	 */
	public boolean containsLocation( File location ) {

		return containsLocation(location.getPath());
	}

	/**
	 * @param path
	 * @return
	 */
	public boolean containsLocation( String path ) {

		return locations.contains(path);

	}

	/**
	 * @return
	 */
	public ApplicationContext init() {

		if (init) {

			String msg = "ApplicationContextFactory already initialized.";

			log.debug(msg);
			throw new RuntimeException(msg);

		}

		String[] locals = new String[locations.size()];

		locations.toArray(locals);

		ApplicationContext context;
		context = new ClassPathXmlApplicationContext(locals);

		init = true;

		return context;

	}

}

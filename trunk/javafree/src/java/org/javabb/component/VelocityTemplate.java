package org.javabb.component;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.opensymphony.webwork.views.velocity.VelocityManager;

/*
 * Copyright 2004 JavaFree.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * $Id: VelocityTemplate.java,v 1.1 2009/05/11 20:27:08 daltoncamargo Exp $
 * @author: Dalton Camargo
 */
public class VelocityTemplate {
	protected static final Log log = LogFactory.getLog(VelocityTemplate.class);

	/**
	 * Obtem o resultado gerado por um template velocity
	 * @param velValues - Parametros a serem inseridos no contexto
	 * @param template - Template a ser realizado o parser
	 * @return Template renderizado
	 */
	public static String makeTemplate(Map velValues, String template) {
		String htmlDoVelocity = "";
		try {
			VelocityManager vle = VelocityManager.getInstance();
			VelocityEngine ve = vle.getVelocityEngine();
			Template t = ve.getTemplate(template);
			VelocityContext context = new VelocityContext();
			Iterator itChaves = velValues.keySet().iterator();
			while (itChaves.hasNext()) {
				String chave = (String) itChaves.next();
				context.put(chave, velValues.get(chave));
			}
			StringWriter writer = new StringWriter();
			t.merge(context, writer);

			htmlDoVelocity = writer.toString();

		} catch (Exception e) {
			log.error("Error at VelocityTemplate:" + e);
			e.printStackTrace();
		}

		return htmlDoVelocity;
	}

}
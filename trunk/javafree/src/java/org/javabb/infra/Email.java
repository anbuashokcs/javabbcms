package org.javabb.infra;

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
 * $Id: Email.java,v 1.1 2009/05/11 20:27:00 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org</a>
 */
public class Email {

	/**
	 * @param mailFrom - Mail from
	 * @param mailTo -Use comma to multiple receivers
	 * @param subject -Subject of mail
	 * @param bodyMessage - Mail message
	 * @param htmlMail - Use true to send an HTML mail
	 */
	public static void sendMail(String mailFrom, String mailTo, String subject,
			String bodyMessage, boolean htmlMail) {

		new Thread(new ThreadMail(mailFrom, mailTo, subject, bodyMessage,
				htmlMail)).start();
	}
}
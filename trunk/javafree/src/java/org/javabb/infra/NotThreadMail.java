package org.javabb.infra;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;


/**
 * $Id: NotThreadMail.java,v 1.1 2009/05/11 20:27:00 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 */
public class NotThreadMail{

	private static final Log log = LogFactory.getLog(NotThreadMail.class);

	// System objects
	private MimeMessage message;

	private JavaMailSenderImpl sender;

	// parameters
	private String mailFrom;

	private String mailTo;

	private String subject;

	private String bodyMessage;

	private boolean htmlMail;

	/**
	 * @param mailFrom -
	 *            Mail from
	 * @param mailTo -
	 *            Use comma to multiple receivers
	 * @param subject -
	 *            Subject of mail
	 * @param bodyMessage -
	 *            Mail message
	 * @param htmlMail -
	 *            Use true to send an HTML mail
	 */
	public NotThreadMail(String mailFrom, String mailTo, String subject,
			String bodyMessage, boolean htmlMail) {

		this.mailFrom = mailFrom;
		this.mailTo = mailTo;
		this.subject = subject;
		this.bodyMessage = bodyMessage;
		this.htmlMail = htmlMail;
	}

	/**
	 * Method to send mail
	 */
	public void sendMail() throws MailException, MessagingException {

        //Configura��es do APP
        Configuration conf = new Configuration();

		// Iniciando a configura��o do email
		sender = new JavaMailSenderImpl();
		sender.setHost(conf.smtpServerHost);

		Properties mailProps = new Properties();
		mailProps.put("mail.smtp.auth", "true");
		mailProps.put("mail.smtp.port", "25");
		sender.setJavaMailProperties(mailProps);

        sender.setUsername(conf.smtpServerUserName);
        sender.setUsername(conf.smtpServerUserName);
        sender.setPassword(conf.smtpServerUserPassword);

		message = sender.createMimeMessage();
		message.setHeader("Content-Type", "text/html");
		message.setContent("html", "text/html; charset=UTF-8" );

		
		
		String reportMails = mailTo.replaceAll(",", ";");
		String[] dests = reportMails.split(";");

		for (int i = 0; i < dests.length; i++) {
			// use the true flag to indicate you need a multipart message
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			// helper.setFrom(conf.adminMail);
			helper.setFrom(mailFrom);
			helper.setSubject(subject);

			String destSendMail = dests[i].trim();

			helper.setTo(destSendMail);

			// use the true flag to indicate the text included is HTML
			helper.setText(bodyMessage, htmlMail);

			/*
			 * To attach some file, uncomment this block File file = new
			 * File("/mnt/c/dalton/java/javabb.war");
			 * helper.addAttachment("javabb.war",file);
			 */

			log.info("Sending mail to " + destSendMail);
			sender.send(message);
			log.info("Mail sent!");
		}
	}

}
package org.javabb.infra;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

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
 * $Id: ThreadMail.java,v 1.1 2009/05/11 20:26:59 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 */
public class ThreadMail extends Thread {

    private static final Log log = LogFactory.getLog(ThreadMail.class);

    //System objects
    private MimeMessage message;

    private JavaMailSenderImpl sender;

    //parameters
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
    public ThreadMail(String mailFrom, String mailTo, String subject,
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

        //Configurações do APP
        Configuration conf = new Configuration();

        //Iniciando a configuração do email
        sender = new JavaMailSenderImpl();
        sender.setHost(conf.smtpServerHost);

        if (conf.smtpServerUserName != null
                && !"".equals(conf.smtpServerUserName)) {

            Properties mailProps = new Properties();
            mailProps.put("mail.smtp.auth", "true");
            mailProps.put("mail.smtp.port", "25");
            sender.setJavaMailProperties(mailProps);

            sender.setUsername(conf.smtpServerUserName);
            sender.setPassword(conf.smtpServerUserPassword);
        }

        message = sender.createMimeMessage();
        String reportMails = mailTo.replaceAll(",", ";");
        String[] dests = reportMails.split(";");

        for (int i = 0; i < dests.length; i++) {
            //use the true flag to indicate you need a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //helper.setFrom(conf.adminMail);
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
            log.info("Mail was sent!");
        }
    }

    /*
     * @see java.lang.Runnable#run()
     */
    public void run() {
        try {
            sleep(2000);
            sendMail();
        } catch (MailException e) {
            log.error(e);
        } catch (MessagingException e) {
            log.error(e);
        } catch (InterruptedException e) {
            log.error(e);
        }
    }

}
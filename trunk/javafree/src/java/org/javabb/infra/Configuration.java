package org.javabb.infra;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
 * $Id: Configuration.java,v 1.1 2009/05/11 20:27:00 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a>
 */
public class Configuration {

	public static String realPath;
	
	/********* CONFIG APP ************/
	public static String showProperties = "";
    public String theme = "";
    public String domain = "";
    public String forumName = "";
    public String lang = "";
    public String dateFormat = "";
    public String timeFormat = "";
    public String buttonLang = "";
    public Integer topicsPage;
    public Integer postsPage;
    public String adminMail = "";
    public String smtpServerHost = "";
    public String smtpServerUserName = "";
    public String smtpServerUserPassword = "";
    
    public String emailNofityTopic = "";
    public String floodControl = "";
    public String forumAnnounceText = "";
    public String activeCaptcha = "";
    
    public Configuration() {
        try {
            String realPath = Configuration.realPath;
            
            if (realPath != null) {
                FileInputStream file = new FileInputStream(realPath + File.separator
                        +"WEB-INF"+ File.separator
                        +"appconf" + File.separator + "javabb.properties");

                MaintainProperties maintain = new MaintainProperties(file);

                /********* LOADING PROPERTIES ************/
                showProperties = maintain.getProperty("config.show.property");
                theme = maintain.getProperty("config.forum.theme");
                domain = maintain.getProperty("config.forum.domain");
                forumName = maintain.getProperty("config.forum.forum.name");
                lang = maintain.getProperty("config.forum.lang");
                dateFormat = maintain.getProperty("config.forum.date.format");
                timeFormat = maintain.getProperty("config.forum.time.format");
                buttonLang = maintain.getProperty("config.forum.button.lang");
                topicsPage = new Integer(maintain.getProperty("config.forum.topics.page"));
                postsPage = new Integer(maintain.getProperty("config.forum.posts.page"));
                smtpServerHost = maintain.getProperty("config.forum.smtp.server.host");
                smtpServerUserName = maintain.getProperty("config.forum.smtp.server.user");
                smtpServerUserPassword = maintain.getProperty("config.forum.smtp.server.senha");
                adminMail = maintain.getProperty("config.forum.admin.mail");
                emailNofityTopic = maintain.getProperty("config.email.notify.topic");
                floodControl = maintain.getProperty("config.forum.flood_control");
                forumAnnounceText = maintain.getProperty("config.forum.posts.announce.text");
                activeCaptcha =  maintain.getProperty("config.forum.captcha.active");

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    
    //Just to Velocity Template Engine Integration
    /**
     * @return Returns the adminMail.
     */
    public String getAdminMail() {
        return adminMail;
    }
    /**
     * @param adminMail The adminMail to set.
     */
    public void setAdminMail(String adminMail) {
        this.adminMail = adminMail;
    }
    /**
     * @return Returns the buttonLang.
     */
    public String getButtonLang() {
        return buttonLang;
    }
    /**
     * @param buttonLang The buttonLang to set.
     */
    public void setButtonLang(String buttonLang) {
        this.buttonLang = buttonLang;
    }
    /**
     * @return Returns the dateFormat.
     */
    public String getDateFormat() {
        return dateFormat;
    }
    /**
     * @param dateFormat The dateFormat to set.
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
    /**
     * @return Returns the domain.
     */
    public String getDomain() {
        return domain;
    }
    /**
     * @param domain The domain to set.
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }
    /**
     * @return Returns the emailNofityTopic.
     */
    public String getEmailNofityTopic() {
        return emailNofityTopic;
    }
    /**
     * @param emailNofityTopic The emailNofityTopic to set.
     */
    public void setEmailNofityTopic(String emailNofityTopic) {
        this.emailNofityTopic = emailNofityTopic;
    }
    /**
     * @return Returns the forumName.
     */
    public String getForumName() {
        return forumName;
    }
    /**
     * @param forumName The forumName to set.
     */
    public void setForumName(String forumName) {
        this.forumName = forumName;
    }
    /**
     * @return Returns the lang.
     */
    public String getLang() {
        return lang;
    }
    /**
     * @param lang The lang to set.
     */
    public void setLang(String lang) {
        this.lang = lang;
    }
    /**
     * @return Returns the postsPage.
     */
    public Integer getPostsPage() {
        return postsPage;
    }
    /**
     * @param postsPage The postsPage to set.
     */
    public void setPostsPage(Integer postsPage) {
        this.postsPage = postsPage;
    }
    /**
     * @return Returns the smtpServerHost.
     */
    public String getSmtpServerHost() {
        return smtpServerHost;
    }
    /**
     * @param smtpServerHost The smtpServerHost to set.
     */
    public void setSmtpServerHost(String smtpServerHost) {
        this.smtpServerHost = smtpServerHost;
    }
    /**
     * @return Returns the smtpServerUserName.
     */
    public String getSmtpServerUserName() {
        return smtpServerUserName;
    }
    /**
     * @param smtpServerUserName The smtpServerUserName to set.
     */
    public void setSmtpServerUserName(String smtpServerUserName) {
        this.smtpServerUserName = smtpServerUserName;
    }
    /**
     * @return Returns the smtpServerUserPassword.
     */
    public String getSmtpServerUserPassword() {
        return smtpServerUserPassword;
    }
    /**
     * @param smtpServerUserPassword The smtpServerUserPassword to set.
     */
    public void setSmtpServerUserPassword(String smtpServerUserPassword) {
        this.smtpServerUserPassword = smtpServerUserPassword;
    }
    /**
     * @return Returns the theme.
     */
    public String getTheme() {
        return theme;
    }
    /**
     * @param theme The theme to set.
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }
    /**
     * @return Returns the timeFormat.
     */
    public String getTimeFormat() {
        return timeFormat;
    }
    /**
     * @param timeFormat The timeFormat to set.
     */
    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }
    /**
     * @return Returns the topicsPage.
     */
    public Integer getTopicsPage() {
        return topicsPage;
    }
    /**
     * @param topicsPage The topicsPage to set.
     */
    public void setTopicsPage(Integer topicsPage) {
        this.topicsPage = topicsPage;
    }
}
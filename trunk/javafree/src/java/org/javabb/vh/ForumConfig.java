package org.javabb.vh;

import org.javabb.infra.ConfigurationFactory;

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
 * Class utilized to Helper the Velocity to see to configurations
 * $Id: ForumConfig.java,v 1.1 2009/05/11 20:27:19 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 */
public class ForumConfig {
	
	/**
	 * @return Returns the domain.
	 */
	public String getDomain() {
		return ConfigurationFactory.getConf().domain;
	}
	/**
	 * @return Returns the forumName.
	 */
	public String getForumName() {
		return ConfigurationFactory.getConf().forumName;
	}
	/**
	 * @return Returns the lang.
	 */
	public String getLang() {
		return ConfigurationFactory.getConf().lang;
	}
	/**
	 * @return Returns the theme.
	 */
	public String getTheme() {
		return ConfigurationFactory.getConf().theme;
	}
	/**
	 * @return Returns the dateFormat.
	 */
	public String getDateFormat() {
		return ConfigurationFactory.getConf().dateFormat;
	}
	/**
	 * @return Returns the postsPage.
	 */
	public String getPostsPage() {
		return ConfigurationFactory.getConf().postsPage.toString();
	}
	/**
	 * @return Returns the timeFormat.
	 */
	public String getTimeFormat() {
		return ConfigurationFactory.getConf().timeFormat;
	}
	/**
	 * @return Returns the topicsPage.
	 */
	public String getTopicsPage() {
		return ConfigurationFactory.getConf().topicsPage.toString();
	}
	/**
	 * @return Returns the buttonLang.
	 */
	public String getButtonLang() {
		return ConfigurationFactory.getConf().buttonLang.toString();
	}
    /**
     * @return Returns the notifyTopic.
     */
    public String getNotifyTopic() {
        return ConfigurationFactory.getConf().emailNofityTopic.toString();
    }
    /**
     * @return Returns the smtpHost.
     */
    public String getSmtpHost() {
        return ConfigurationFactory.getConf().smtpServerHost;
    }
    /**
     * @return Returns the smtpPassword.
     */
    public String getSmtpPassword() {
        return ConfigurationFactory.getConf().smtpServerUserPassword;
    }
    /**
     * @return Returns the smtpUser.
     */
    public String getSmtpUser() {
        return ConfigurationFactory.getConf().smtpServerUserName;
    }
    /**
     * @return Returns the adminMail.
     */
    public String getAdminMail() {
        return ConfigurationFactory.getConf().adminMail;
    }
    /**
     * @return Returns the floodControl.
     */
    public String getFloodControl() {
        return ConfigurationFactory.getConf().floodControl;
    }
    
	public String getForumAnnounceText() {
		return ConfigurationFactory.getConf().forumAnnounceText;
	}
	
	public String getActiveCaptcha() {
		return ConfigurationFactory.getConf().activeCaptcha;
	}
	
}

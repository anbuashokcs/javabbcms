package org.javabb.thread;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.component.NewsletterStatus;
import org.javabb.infra.Configuration;
import org.javabb.infra.NotThreadMail;
import org.javabb.vo.Newsletter;
import org.javabb.vo.User;

public class NewsThread implements Runnable {
	
	protected final Log _log = LogFactory.getLog(this.getClass());

	private List users;

	private Newsletter newsletter;

	public NewsThread() {
		// TODO Auto-generated constructor stub
	}

	public NewsThread(List users, Newsletter newsletter) {
		this.users = users;
		this.newsletter = newsletter;
	}

	public void run() {
		try {
			_log.debug("Initializing send of Newsletters");
			Configuration conf = new Configuration();
			NewsletterStatus.setStatusNews(newsletter.getId(),
					new Boolean(true));
			for (int i = 0; i < users.size(); i++) {
				try {
					_log.debug("Sending newsletter number " + i +  "/" + users.size());
	
					User user = (User) users.get(i);
					NotThreadMail notThreadMail = new NotThreadMail(conf.adminMail,
							user.getEmail(), newsletter.getName(), newsletter
									.getHtmlText(), true);
					notThreadMail.sendMail();
				} catch (Exception e) {
					_log.error("Error Sending newsletter:" + e);
				}
			}
			_log.debug("Newsletter's job is finished!");
		} catch (Exception e) {
			_log.error("Error Sending newsletter:" + e);
		}
		NewsletterStatus.setStatusNews(newsletter.getId(), new Boolean(false));
	}
}

package org.javabb.vh;

public class ForumConfigView {
	private String theme;

	private String domain;

	private String forumName;

	private String lang;

	private String dateFormat;

	private String timeFormat;

	private String buttonLang;

	private String topicsPage;

	private String postsPage;

	private String notifyTopic;

	private String smtpHost;

	private String smtpUser;

	private String smtpPassword;

	private String adminMail;

	private String floodControl;

	private String forumAnnounceText;
	
	private String activeCaptcha;
	
	/**
	 * @return Returns the dateFormat.
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * @param dateFormat
	 *            The dateFormat to set.
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
	 * @param domain
	 *            The domain to set.
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return Returns the forumName.
	 */
	public String getForumName() {
		return forumName;
	}

	/**
	 * @param forumName
	 *            The forumName to set.
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
	 * @param lang
	 *            The lang to set.
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return Returns the postsPage.
	 */
	public String getPostsPage() {
		return postsPage;
	}

	/**
	 * @param postsPage
	 *            The postsPage to set.
	 */
	public void setPostsPage(String postsPage) {
		this.postsPage = postsPage;
	}

	/**
	 * @return Returns the theme.
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * @param theme
	 *            The theme to set.
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
	 * @param timeFormat
	 *            The timeFormat to set.
	 */
	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	/**
	 * @return Returns the topicsPage.
	 */
	public String getTopicsPage() {
		return topicsPage;
	}

	/**
	 * @param topicsPage
	 *            The topicsPage to set.
	 */
	public void setTopicsPage(String topicsPage) {
		this.topicsPage = topicsPage;
	}

	/**
	 * @return Returns the buttonLang.
	 */
	public String getButtonLang() {
		return buttonLang;
	}

	/**
	 * @param buttonLang
	 *            The buttonLang to set.
	 */
	public void setButtonLang(String buttonLang) {
		this.buttonLang = buttonLang;
	}

	/**
	 * @return Returns the notifyTopic.
	 */
	public String getNotifyTopic() {
		return notifyTopic;
	}

	/**
	 * @param notifyTopic
	 *            The notifyTopic to set.
	 */
	public void setNotifyTopic(String notifyTopic) {
		this.notifyTopic = notifyTopic;
	}

	/**
	 * @return Returns the smtpHost.
	 */
	public String getSmtpHost() {
		return smtpHost;
	}

	/**
	 * @param smtpHost
	 *            The smtpHost to set.
	 */
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	/**
	 * @return Returns the smtpPassword.
	 */
	public String getSmtpPassword() {
		return smtpPassword;
	}

	/**
	 * @param smtpPassword
	 *            The smtpPassword to set.
	 */
	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	/**
	 * @return Returns the smtpUser.
	 */
	public String getSmtpUser() {
		return smtpUser;
	}

	/**
	 * @param smtpUser
	 *            The smtpUser to set.
	 */
	public void setSmtpUser(String smtpUser) {
		this.smtpUser = smtpUser;
	}

	/**
	 * @return Returns the adminMail.
	 */
	public String getAdminMail() {
		return adminMail;
	}

	/**
	 * @param adminMail
	 *            The adminMail to set.
	 */
	public void setAdminMail(String adminMail) {
		this.adminMail = adminMail;
	}

	/**
	 * @return Returns the floodControl.
	 */
	public String getFloodControl() {
		return floodControl;
	}

	/**
	 * @param floodControl
	 *            The floodControl to set.
	 */
	public void setFloodControl(String floodControl) {
		this.floodControl = floodControl;
	}

	public String getForumAnnounceText() {
		return forumAnnounceText;
	}

	public void setForumAnnounceText(String forumAnnounceText) {
		this.forumAnnounceText = (forumAnnounceText != null && !""
				.equals(forumAnnounceText)) ? forumAnnounceText.replaceAll(
				"\"", "'") : forumAnnounceText;
	}

	public String getActiveCaptcha() {
		return activeCaptcha;
	}

	public void setActiveCaptcha(String activeCaptcha) {
		this.activeCaptcha = activeCaptcha;
	}
}
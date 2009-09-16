package org.javabb.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.javabb.action.infra.BaseAction;
import org.javabb.component.UserFormatter;
import org.javabb.exception.FileTransferException;
import org.javabb.infra.CharFilter;
import org.javabb.infra.PostSupport;
import org.javabb.infra.UserContext;
import org.javabb.infra.Utils;
import org.javabb.infra.VelocityHelper;
import org.javabb.transaction.BadWordTransaction;
import org.javabb.transaction.ForumTransaction;
import org.javabb.transaction.PostTransaction;
import org.javabb.transaction.UserTransaction;
import org.javabb.vo.Forum;
import org.javabb.vo.Post;
import org.javabb.vo.PostText;
import org.javabb.vo.Topic;
import org.javabb.vo.User;
import org.javabb.vo.Warning;

import sun.misc.ExtensionInstallationException;

import com.opensymphony.webwork.ServletActionContext;

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
 * $Id: PostAction.java,v 1.1 2009/05/11 20:26:52 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Lucas Frare Teixeira - <a
 *         href="mailto:lucas@javabb.org">lucas@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class PostAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private PostText _post = new PostText();

	private ForumTransaction _forumTransaction;

	private PostTransaction _postTransaction;

	private Topic _topic = new Topic();

	private BadWordTransaction badWordTransaction;

	private UserTransaction _userTransaction;

	private UserFormatter userFormatter;

	private List _posts = new ArrayList();

	private String quote;

	private String whoQuote;

	private String[] attachFiles;

	private String msgBBCoded;
	private Warning warning = new Warning();
	private Integer replyMail = 0;

	// ####################################################################
	// Dependencies
	// ####################################################################

	/**
	 * @param forumTransaction
	 *            The forumTransaction to set.
	 */
	public void setForumTransaction(ForumTransaction forumTransaction) {
		this._forumTransaction = forumTransaction;
	}

	/**
	 * @param userTransaction
	 *            the new userTransaction value
	 */
	public void setUserTransaction(UserTransaction userTransaction) {
		this._userTransaction = userTransaction;
	}

	/**
	 * @param postTransaction
	 *            the new postTransaction value
	 */
	public void setPostTransaction(PostTransaction postTransaction) {
		this._postTransaction = postTransaction;
	}

	/**
	 * @param badWordComponent
	 *            the new badWordComponent value
	 */
	public void setBadWordTransaction(BadWordTransaction badWordComponent) {
		this.badWordTransaction = badWordComponent;
	}

	public void setUserFormatter(UserFormatter userFormatter) {
		this.userFormatter = userFormatter;
	}

	// ####################################################################
	// Actions
	// ####################################################################

	public String doUpload() {
		return SUCCESS;
	}

	/**
	 * @return Action status
	 */
	public String listaPost() {
		_posts = _postTransaction.findByTopic(_post.getTopic().getIdTopic(),
				getPage());
		return SUCCESS;
	}

	public String findLastTenPosts() throws Exception {
		_posts = _postTransaction.findLastShortPosts(20);
		return SUCCESS;
	}

	/**
	 * This method is used as much as to reply a post as to quote some text.
	 * 
	 * @return Action status
	 */
	public String returnQuoteMsg() {
		if (log.isDebugEnabled()) {
			log.debug("whoQuote = '" + whoQuote + "'");
		}
		Long postId = _post.getId();
		if (StringUtils.isNotEmpty(whoQuote)) {
			whoQuote = CharFilter.replaceSpecial(whoQuote);
			_post = (PostText) _postTransaction.loadPost(postId);
			quote = "[quote=\"" + whoQuote + "\"]" + _post.getPostBody() + " [/quote]";
		}

		if (_post != null && _post.getTopic() != null) {
			_topic.setId(_post.getTopic().getId());
		}

		if (_topic.getId() != null) {
			if (UserContext.getContext().isAuthenticated()) {
				watchTopic = topicTransaction.isWatchTopic(_topic.getId(),
						UserContext.getContext().getUser().getIdUser());
			}
		}

		return SUCCESS;
	}

	/**
	 * @return Action status
	 */
	public String loadPost() {
		if (_post.getIdPost() != null) {
			_postId = _post.getIdPost();
		}
		_post = (PostText) _postTransaction.loadPost(_postId);

		Long topicId = _post.getTopic().getId();
		if (topicId != null) {
			if (UserContext.getContext().isAuthenticated()) {
				watchTopic = topicTransaction.isWatchTopic(topicId, _post
						.getUser().getIdUser());
			}
		}

		return SUCCESS;
	}

	/**
	 * @return Action status
	 */
	public String updatePost() throws Exception {

		checkMessage();

		Post alterPost = _postTransaction.loadPost(_post.getId());
		User userLogado = UserContext.getContext().getUser();

		// se o usuário não for administrador:
		if (userLogado.getAdmin().equals(new Integer(0))) {
			// se ele não for o dono da mensagem:
			if (!alterPost.getUser().equals(userLogado)) {
				addActionError(getText("you_can_edit_yours_messages"));

				return ERROR;
			}
		}

		try {
			// Uploading all Post files
			Set files = PostSupport.uploadPostFiles(_post, ServletActionContext
					.getRequest());
			alterPost.setPostFiles(files);
		} catch (ExtensionInstallationException ex) {
			throw new FileTransferException(
					getText("exception_file_extensionnotallowed"));
		} catch (Exception e) {
			throw new FileTransferException(
					getText("exception_file_file_too_large"));
		}

		alterPost.setSubject(getSubject());
		alterPost.setPostBody(getMessage());
		alterPost.setSig(getCheckSign());

		if (replyMail != null && replyMail.intValue() == 1) {
			alterPost.setMailReply(1);
		} else {
			alterPost.setMailReply(0);
		}

		_postTransaction.updatePost(alterPost);

		if (getUrl() != null && "topic".equals(getUrl())) {
			setUrl("viewtopic.jbb?t=" + _post.getTopic().getIdTopic());
		} else {
			setUrl("viewtopic.jbb?t=" + _post.getTopic().getIdTopic()
					+ "&page=" + _page + "#" + _post.getIdPost());
		}

		// Atualiza a informacao dos posts
		_post.setIdPost(_post.getId());
		_forumTransaction.refreshPost(_post.getId());

		return SUCCESS;
	}

	/**
	 * @return Action status
	 */
	public String updatePostTopic() throws Exception {

		checkMessage();
		checkSubject();

		Post alterPost = null;
		User userLogado = null;

		try {
			alterPost = _postTransaction.loadPost(_post.getId());
			userLogado = UserContext.getContext().getUser();

			// se o usuário não for administrador:
			if (userLogado.getAdmin().equals(new Integer(0))) {
				// se ele não for o dono da mensagem:
				if (!alterPost.getUser().equals(userLogado)) {
					addActionError(getText("you_can_edit_yours_messages"));
					return ERROR;
				}
			}

			// Uploading all Post files
			Set files = PostSupport.uploadPostFiles(_post, ServletActionContext
					.getRequest());
			alterPost.setPostFiles(files);
		} catch (ExtensionInstallationException ex) {
			throw new FileTransferException(
					getText("exception_file_extensionnotallowed"));
		} catch (Exception e) {
			throw new FileTransferException(
					getText("exception_file_file_too_large"));
		}

		// alterPost = getPostPopulated(_post);

		alterPost.setSig(getCheckSign());
		alterPost.setPostBody(getMessage());

		if (replyMail != null && replyMail.intValue() == 1) {
			alterPost.setMailReply(1);
		} else {
			alterPost.setMailReply(0);
		}

		_postTransaction.updatePost(alterPost);

		Topic alterTopic = topicTransaction.loadTopic(_post.getTopic().getId());
		// alterTopic = getTopicPopulated(_post.getTopic());
		alterTopic.setTitleTopic(Utils.replaceHTML(getSubject()));
		alterTopic.setTopicModel(_post.getTopic().getTopicModel());
		alterTopic.setNotifyMe(_post.getTopic().getNotifyMe());

		if (userLogado.getAdmin().intValue() != 1) {
			alterTopic.setTopicModel(new Integer(0));
		}

		topicTransaction.updateTopic(alterTopic);

		setUrl("viewtopic.jbb?t=" + _post.getTopic().getIdTopic());

		// Atualiza a informacao dos posts
		_post.setIdPost(_post.getId());
		_forumTransaction.refreshPost(_post.getId());
		// Atualiza a informaçao dos topicos
		_forumTransaction.refreshTopic(_post.getTopic().getIdTopic());

		return SUCCESS;
	}

	/**
	 * @return Action status
	 */
	public String insertPost() throws Exception {
		checkMessage();

		if (!"quick".equals(getTemp())) {
			try {
				// Uploading all Post files
				Set files = PostSupport.uploadPostFiles(_post,
						ServletActionContext.getRequest());
				_post.setPostFiles(files);
			} catch (ExtensionInstallationException ex) {
				throw new FileTransferException(
						getText("exception_file_extensionnotallowed"));
			} catch (Exception e) {
				throw new FileTransferException(
						getText("exception_file_file_too_large"));
			}
		}

		Topic topic = _post.getTopic();
		topic = topicTransaction.loadTopic(topic.getId());

		// mark as unread
		UserContext.getContext().setTopicUnread(topic.getId());

		String retFlood = checkFlood();
		if (StringUtils.isNotEmpty(retFlood)) {
			return retFlood;
		}

		if ((topic.getTopicStatus() != null)
				&& topic.getTopicStatus().equals(new Integer(1))) {
			this.addActionError(this.getText("answer_locked_topic"));
			return ERROR;
		}

		_post.setPostDate(new Date());
		_post.setIp(ServletActionContext.getRequest().getRemoteAddr());
		_post.setPost_state(new Integer(0));

		String title = getSubject().replaceAll("\\<.*?", "");
		title = title.replaceAll("\\>.*?", "");

		_post.setSubject(title);

		_post.setPostBody(getMessage());
		_post.setSig(getCheckSign());
		_post.setTopic(topic);

		_postTransaction.sendAllNotifyEmailsReply(topic, getUserLogged());

		if (replyMail != null && replyMail.intValue() == 1) {
			_post.setMailReply(1);
		} else {
			_post.setMailReply(0);
		}

		User up = UserContext.getContext().getUser();
		_post.setUser(up);

		Long idPost = _postTransaction.createPost(_post);

		topicTransaction.sumNumberReplysByTopic(_post.getTopic().getIdTopic());
		Date lastPostDate = new Date();
		topicTransaction.updateDatePostTopic(_post.getTopic().getIdTopic(), lastPostDate);

		topic.setLastPostDate(lastPostDate);
		UserContext.getContext().setForumTopic(topic.getForum().getId(), topic);

		if (getSessionAttribute("categoryChosed") != null) {
			_categoryId = (Long) getSessionAttribute("categoryChosed");
			UserContext.getContext().setCatTopic(_categoryId, topic);
		} else {
			UserContext.getContext().setCatTopic(
					topic.getForum().getCategory().getId(), topic);
		}

		_userTransaction.sumNumberMsgUser(up.getIdUser());

		// Getting number of page and setting for redirect
		Integer currentPage = _postTransaction.getPageOfLastPostByTopic(topic);
		setPage(currentPage.intValue());

		int lastId = idPost.intValue();
		int topicId = _post.getTopic().getIdTopic().intValue();

		// http://www.javafree.org/topic-871910-Problemas-com-JTable-visualizacao-da-tabela-com-Scroll.html?page=1#164367
		VelocityHelper vh = new VelocityHelper();
		String parsedTitle = vh.parseStringTitle(topic.getTitleTopic());

		String url = "topic-" + topicId + "-" + parsedTitle + "&page="
				+ currentPage + "#" + lastId;
		setUrl(url);

		// Atualiza a informacao dos posts
		_post.setIdPost(idPost);
		_forumTransaction.refreshPost(_post.getId());
		// Atualiza a informaçao dos topicos
		_forumTransaction.refreshTopic(_post.getTopic().getId());

		// Verify and send an email notification to User if the notify flag is
		// set
		// _postTransaction.notifyUserTopicByMail(topic);

		// Send an email to all users that watch this topic
		_postTransaction.nofityWatchUsers(topic, url,
				getText("watch_topics_message1"),
				getText("watch_topics_message2"), getText("topic"),
				getText("watch_topic"));

		// Watching topic
		if (watchTopic == 1) {
			// Insert a new Watched topic
			topicTransaction.insertWatchTopicUser(new Long(topicId), _post
					.getUser().getId());
		}

		return SUCCESS;
	}

	/**
	 * Verifica se o forum que o usuario esta postando não é um fórum de
	 * tutoriais
	 * 
	 * @return
	 */
	private boolean canPostInForum() {
		int[] fIds = new int[] { 31, 15, 16, 17, 18, 19, 11, 20, 29, 39, 52 };
		Arrays.sort(fIds);
		if (Arrays.binarySearch(fIds, _topic.getForum().getId().intValue()) > 0) {
			return false;
		}
		return true;
	}

	public String checkIfHasPermission() throws Exception {
		if (getUserLogged().getAdmin() != 1 && !canPostInForum()) {
			addActionError("Você não pode criar novos tópicos neste fórum. <br>"
					+ " Esta área é destinada a comentários"
					+ " em <a href='artigos/'>tutoriais e artigos</a>. Caso você esteja querendo criar uma nova discussão, navegue até a "
					+ " <a href='cat-1-Java'>catagoria Java</a> e "
					+ "busque o fórum de acordo com sua dúvida. Caso você esteja querendo publicar um artigo ou tutorial, envie seu"
					+ " artigo no formato HTML ou <a href='http://pt.wikipedia.org/wiki/BBCode' target='_blank'>BBCODE</a> com todas imagens anexadas para o email info@javafree.org"
					+ "");
			return ERROR;
		}
		return SUCCESS;
	}

	public String formatBBCodePreviewTopic() throws Exception {
		setMsgBBCoded(formatTextToBBCode(getMessage()));
		return SUCCESS;
	}

	public String formatBBCodePreviewPost() throws Exception {
		quote = getMessage();
		setMessage(formatTextToBBCode(getMessage()));
		return SUCCESS;
	}

	/**
	 * Insert a new topic and a new post
	 * 
	 * @return Action status
	 */
	public String insertTopicPost() throws Exception {

		checkMessage();
		checkSubject();
		if (getUserLogged().getAdmin() != 1 && !canPostInForum()) {

		}

		try {
			// Uploading all Post files
			Set files = PostSupport.uploadPostFiles(_post, ServletActionContext
					.getRequest());
			_post.setPostFiles(files);
		} catch (ExtensionInstallationException ex) {
			throw new FileTransferException(
					getText("exception_file_extensionnotallowed"));
		} catch (Exception e) {
			throw new FileTransferException(
					getText("exception_file_file_too_large"));
		}

		Date lastPostDate = new Date();

		String title = Utils.replaceHTML(getSubject());
		_topic.setTitleTopic(title);

		Long idTopicInserted = topicTransaction.createTopic(_topic, lastPostDate);

		_post.getUser().setId(UserContext.getContext().getUser().getId());
		_post.setPost_state(new Integer(0));
		_post.setPostDate(new Date());

		if (replyMail != null && replyMail.intValue() == 1) {
			_post.setMailReply(1);
		} else {
			_post.setMailReply(0);
		}

		// _post.setSubject(badWordTransaction.verifyBadWords(_post.getSubject()));

		Topic topic = null;
		Long lastPostId = null;
		try {
			_post.setSubject(badWordTransaction.verifyBadWords(title));
			_post.setPostBody(getMessage());
			_post.setSig(getCheckSign());

			topic = topicTransaction.loadTopic(idTopicInserted);
			topic.setTitleTopic(badWordTransaction.verifyBadWords(title));
			topic.setTopicStatus(new Integer(0));
			topic.setPageLastPost(new Integer(1));
			topic.setLastPostDate(lastPostDate);
			_post.setTopic(topic);

			lastPostId = _postTransaction.createPost(_post);
			_topic.setLastPost(_postTransaction.loadPost(lastPostId));

			UserContext.getContext().setForumTopic(topic.getForum().getId(), topic);

			if (getSessionAttribute("categoryChosed") != null) {
				_categoryId = (Long) getSessionAttribute("categoryChosed");
				UserContext.getContext().setCatTopic(_categoryId, topic);
			} else {
				UserContext.getContext().setCatTopic(topic.getForum().getCategory().getId(), topic);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// manutenção de mensagens do usuário
		User up = UserContext.getContext().getUser();
		_userTransaction.sumNumberMsgUser(up.getIdUser());

		int topicId = _post.getTopic().getIdTopic().intValue();
		// setUrl("viewtopic.jbb?t=" + topicId + "#" + lastPostId.toString());

		try {
			VelocityHelper vh = new VelocityHelper();
			String parsedTitle = vh.parseStringTitle(topic.getTitleTopic());
			String url = "topic-" + topicId + "-" + parsedTitle + "#" + lastPostId;
			setUrl(url);

			// Atualiza a informacao dos posts
			_post.setIdPost(lastPostId);
			_forumTransaction.refreshPost(_post.getId());
			// Atualiza a informaçao dos topicos

			_forumTransaction.refreshTopic(topic.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * @return Action status
	 */
	public String deletePost() {
		Topic topic = null;
		Forum forum = null;

		// Obtém o usuário logado da sessão
		User up = UserContext.getContext().getUser();

		// Logging
		log.debug("Deleting post User:" + up.getUser());
		log.debug("Deleting post ID:" + up.getIdUser());

		// Verifica se o usuário tem privilégios para excluir o post
		if (_postTransaction.canDeletePost(_post)) {

			// guardando referencias para refresh posterior
			topic = _post.getTopic();
			forum = topic.getForum();

			_postTransaction.deletePost(_post.getId());

			// manutenção de mensagens do usuário
			_userTransaction.subNumberMsgUser(_post.getUser().getId());

			// Verifica se o post que foi excluído
			// é o único post do tópico, se for, ele exclui
			// o tópico também.
			List postsByT = _postTransaction.findByTopic(_post.getTopic()
					.getIdTopic(), getPage());

			if (postsByT.size() <= 0) {
				// Exclui o topico do post
				_topic = new Topic();
				_topic.setIdTopic(_post.getTopic().getIdTopic());
				topicTransaction.deleteTopic(_topic.getId());

				// nao dar refresh no topico, pois foi excluido
				topic = new Topic();

				setUrl("viewforum.jbb?f="
						+ _post.getTopic().getForum().getIdForum());
			} else {

				// manutenção de resposta de posts
				topicTransaction.subNumberReplysByTopic(_post.getTopic()
						.getId());

				// Exclui apenas o post
				int topicId = _post.getTopic().getIdTopic().intValue();
				setUrl("viewtopic.jbb?t=" + topicId);
			}

			// Atualiza a informacao do topico
			_forumTransaction.refreshTopic(topic.getId());
			// Atualiza a informaçao do forum
			_forumTransaction.refreshForum(forum.getId());

			return SUCCESS;
		}

		addActionError(getText("no_permission_to_delete_post"));

		return ERROR;
	}

	public String addMessageWarn() throws Exception {
		Warning warn = new Warning();
		Post pst = new Post(_postId);
		warn.setPost(pst);
		warn.setWarnText(getMessage());
		warn.setWarnDate(new Date());
		warn.setUser(getUserLogged());
		_postTransaction.add(warn);
		_postTransaction.notifyWarnMessage(_postId);
		setUrl("../viewtopic.jbb?p=" + _postId + "#" + _postId);
		return SUCCESS;
	}

	public String deleteMessageWarn() throws Exception {
		_postTransaction.delete(getWarning());
		setUrl("../viewtopic.jbb?t=" + getTopicId());
		return SUCCESS;
	}

	/**
	 * @return result
	 */
	public String searchAuthor() {
		_posts = _postTransaction.listPostsByUser(_userId, _page);
		return SUCCESS;
	}

	/**
	 * @return result
	 */
	public String listUnAnswaredPosts() {
		_posts = _postTransaction.listUnAnswaredPosts(_page);
		return SUCCESS;
	}

	public String listAllLastPosts() {
		_posts = _postTransaction.findLasPosts();
		return SUCCESS;
	}

	// ####################################################################
	// View objects accessors
	// ####################################################################

	/**
	 * @return Returns the post.
	 */
	public Post getPost() {
		return _post;
	}

	/**
	 * @return Returns the posts.
	 */
	public List getPosts() {
		return _posts;
	}

	/**
	 * @return Returns the quote.
	 */
	public String getQuote() {
		return quote;
	}

	/**
	 * @param quote
	 *            The quote to set.
	 */
	public void setQuote(String quote) {
		this.quote = quote;
	}

	/**
	 * @return Returns the whoQuote.
	 */
	public String getWhoQuote() {
		return whoQuote;
	}

	/**
	 * @param whoQuote
	 *            The whoQuote to set.
	 */
	public void setWhoQuote(String whoQuote) {
		this.whoQuote = whoQuote;
	}

	/**
	 * @return Returns the topic.
	 */
	public Topic getTopic() {
		return _topic;
	}

	public UserFormatter getUserFormatter() {
		return userFormatter;
	}

	public String[] getAttachFiles() {
		return attachFiles;
	}

	public void setAttachFiles(String[] attachFiles) {
		this.attachFiles = attachFiles;
	}

	public String getMsgBBCoded() {
		return msgBBCoded;
	}

	public void setMsgBBCoded(String msgBBCoded) {
		this.msgBBCoded = msgBBCoded;
	}

	public Warning getWarning() {
		return warning;
	}

	public void setWarning(Warning warning) {
		this.warning = warning;
	}

	public Integer getReplyMail() {
		return replyMail;
	}

	public void setReplyMail(Integer replyMail) {
		this.replyMail = replyMail;
	}

}
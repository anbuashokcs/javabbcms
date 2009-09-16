package org.javabb.action.ajax;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.javabb.action.infra.BaseAction;
import org.javabb.component.Spy;
import org.javabb.component.VelocityTemplate;
import org.javabb.infra.FileTransfer;
import org.javabb.transaction.PostTransaction;
import org.javabb.transaction.TopicTransaction;
import org.javabb.vo.FavUserTopic;
import org.javabb.vo.FavUserTopicPK;
import org.javabb.vo.PostFile;
import org.javabb.vo.User;

import uk.ltd.getahead.dwr.WebContext;
import uk.ltd.getahead.dwr.WebContextFactory;

public class ActionFacade extends BaseAction {
    private static final long serialVersionUID = 1L;

    private TopicTransaction topicTransaction;

    private PostTransaction postTransaction;

    public void setTopicTransaction(TopicTransaction topicTransaction) {
	this.topicTransaction = topicTransaction;
    }

    public void setPostTransaction(PostTransaction postTransaction) {
	this.postTransaction = postTransaction;
    }

    private HttpServletRequest getRequest() {
	WebContext ctx = WebContextFactory.get();
	return ctx.getHttpServletRequest();
    }

    public String getContextPath(){
	WebContext ctx = WebContextFactory.get();
	String basePath = ctx.getServletContext().getContextPath();
	return basePath;
    }
    
    public String addFavoriteTopic(Long topicId) {
	try {

	    User user = (User) getRequest().getSession()
		    .getAttribute("jbbuser");

	    if (topicId != null && user != null && user.getId() != null) {
		Long userId = user.getId();
		FavUserTopic favUserTopic = new FavUserTopic(
			new FavUserTopicPK(topicId, userId));

		topicTransaction.add(favUserTopic);
	    }
	} catch (Exception e) {
	    log.debug("Error at addFavoriteTopic = " + e.getMessage());
	    return "addFavoriteTopic=0";
	}
	return "addFavoriteTopic=1";
    }

    public String deleteFavoriteTopic(Long topicId) {
	try {
	    User user = (User) getRequest().getSession()
		    .getAttribute("jbbuser");

	    if (topicId != null && user != null && user.getId() != null) {
		Long userId = user.getId();
		FavUserTopic favUserTopic = new FavUserTopic(
			new FavUserTopicPK(topicId, userId));
		topicTransaction.delete(favUserTopic);
	    }
	} catch (Exception e) {
	    log.debug("Error at deleteFavoriteTopic = " + e.getMessage());
	    return "deleteFavoriteTopic=0";
	}
	return "deleteFavoriteTopic=1";
    }

    public String addWatchTopic(Long topicId) {
	try {
	    User user = (User) getRequest().getSession()
		    .getAttribute("jbbuser");

	    if (topicId != null && user != null && user.getId() != null) {
		Long userId = user.getId();
		topicTransaction.insertWatchTopicUser(topicId, userId);
	    }
	} catch (Exception e) {
	    log.debug("Error at addWatchTopic = " + e.getMessage());
	    return "addWatchTopic=0";
	}
	return "addWatchTopic=1";
    }

    public String deleteWatchTopic(Long topicId) {
	try {
	    User user = (User) getRequest().getSession()
		    .getAttribute("jbbuser");

	    if (topicId != null && user != null && user.getId() != null) {
		Long userId = user.getId();
		topicTransaction.deleteWatchTopicUser(topicId, userId);
	    }
	} catch (Exception e) {
	    log.debug("Error at deleteWatchTopic = " + e.getMessage());
	    return "deleteWatchTopic=0";
	}
	return "deleteWatchTopic=1";
    }

    public String spyTemplate() {
	HashMap map = new HashMap();
	map.put("msgs", Spy.topicViews);
	return VelocityTemplate.makeTemplate(map, "spy_table.vm");
    }

    public String deleteFile(Long fileId) {
	try {
	    if (fileId != null) {
		User user = (User) getRequest().getSession().getAttribute(
			"jbbuser");
		PostFile postFile = postTransaction.loadPostFile(fileId);
		if (user != null) {
		    if (postFile != null) {
			if ((postFile.getPost().getUser().getId().intValue() == user
				.getIdUser().intValue())
				|| user.isAdministrator()) {
			    FileTransfer.deleteFile(postFile.getFilePath(),postFile.getFileName());
			    postTransaction.deletePostFile(postFile.getFileId());
			}
		    }
		}
	    }
	} catch (Exception e) {
	    return "deleteFile=0";
	}
	return "deleteFile=1";
    }

    
    public String ajaxBBCODE(String post) {
	try {
	    if(StringUtils.isNotEmpty(post)){
		return formatTextToBBCode(post, getContextPath());
	    } else {
		return "nobbcoded";
	    }
	} catch (Exception e) {
	    return "nobbcoded";
	}
    }
    
}

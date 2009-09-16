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

package org.javabb.action;

import java.util.Date;
import java.util.List;

import org.javabb.action.infra.BaseAction;
import org.javabb.component.PostFormatter;
import org.javabb.infra.UserContext;
import org.javabb.transaction.PrivMsgTransaction;
import org.javabb.vo.PrivMsg;
import org.javabb.vo.User;

import com.opensymphony.webwork.ServletActionContext;

/**
 * @author Lucas Teixeira - <a
 *         href="mailto:lucas@javabb.org">lucas@javabb.org</a>
 */
/* $Id: PrivMsgAction.java,v 1.1 2009/05/11 20:26:52 daltoncamargo Exp $ */
public class PrivMsgAction extends BaseAction {

    private PrivMsg pm;
    private List lstMsgs;
    private PrivMsgTransaction pmTransaction = null;

    // Reply
    private String title;
    private Long userId;
    private String username;
    private String msg;

    // action identifier
    private String act;

    // multiple selector
    private Long[] idPm;

    public String inbox() throws Exception {
	User logado = UserContext.getContext().getUser();
	lstMsgs = pmTransaction.getUserInbox(logado);
	return SUCCESS;
    }

    public String outbox() throws Exception {
	User logado = UserContext.getContext().getUser();
	lstMsgs = pmTransaction.getUserOutbox(logado);
	return SUCCESS;
    }

    public String readIn() throws Exception {
	pm = pmTransaction.loadRecivied(pm);
	act = "readIn";
	if (pm.getUserTo().equals(UserContext.getContext().getUser()))
	    return SUCCESS;
	else
	    throw new Exception("Você pode ler apenas as suas mensagens."); // i18n
									    // ?
    }

    public String readOut() throws Exception {
	pm = pmTransaction.loadSent(pm);
	act = "readOut";
	if (pm.getUserFrom().equals(UserContext.getContext().getUser()))
	    return SUCCESS;
	else
	    throw new Exception("Você pode ler apenas as suas mensagens."); // i18n
									    // ?
    }

    public String deleteSelectedInbox() throws Exception {
	if (idPm != null){
	    pmTransaction.deleteSelectedInbox(pmTransaction.asPrivMsgList(idPm));
	}

	return SUCCESS;
    }

    public String deleteInbox() throws Exception {
	lstMsgs = pmTransaction
		.getUserInbox(UserContext.getContext().getUser());
	pmTransaction.deleteSelectedInbox(lstMsgs);
	return SUCCESS;
    }

    public String deleteSelectedOutbox() throws Exception {
	if (idPm != null)
	    pmTransaction.deleteSelectedOutbox(pmTransaction
		    .asPrivMsgList(idPm));

	return SUCCESS;
    }

    public String deleteOutbox() throws Exception {
	lstMsgs = pmTransaction.getUserOutbox(UserContext.getContext()
		.getUser());
	pmTransaction.deleteSelectedOutbox(lstMsgs);
	return SUCCESS;
    }

    public String send() throws Exception {
	pm.setUserTo(new User(userId));
	pm.setData(new Date());
	pm.setRead(new Integer(0));
	pm.setUserFrom(UserContext.getContext().getUser());
	Long mpId = pmTransaction.send(pm);

	pmTransaction.delegateMail(getText("pm_you_have_new_message"), userId,
		mpId);

	return SUCCESS;
    }

    public String newPm() throws Exception {
	act = "new";
	return SUCCESS;
    }

    public String quote() throws Exception {
	// i18n in this 'RE:'
	PrivMsg _pm = pmTransaction.loadRecivied(pm);
	title = "Re: " + _pm.getTopic();
	username = _pm.getUserFrom().getUser();
	userId = _pm.getUserFrom().getId();
	// hardcoded, bleh
	msg = "[quote=\"" + username + "\"]" + _pm.getText() + "[/quote]\n\n";
	act = "quote";
	return SUCCESS;
    }

    public String reply() throws Exception {
	// i18n in this 'RE:'
	PrivMsg _pm = pmTransaction.loadRecivied(pm);
	title = "Re: " + _pm.getTopic();
	username = _pm.getUserFrom().getUser();
	userId = _pm.getUserFrom().getId();
	act = "reply";
	return SUCCESS;
    }

    /**
     * Send a message on click of PM Button (viewpost or viewprovile)
     * 
     * @throws Exception
     */
    public String externalSend() throws Exception {
	act = "reply";
	return SUCCESS;
    }

    /**
     * @param post
     * @return formated post
     */
    public String formatTextToBBCode(String text) {
	String basePath = ServletActionContext.getRequest().getContextPath();
	return postFormatter.formatTextToBBCode(basePath, text);
    }

    public List getLstMsgs() {
	return lstMsgs;
    }

    public void setLstMsgs(List lstMsgs) {
	this.lstMsgs = lstMsgs;
    }

    public PrivMsg getPm() {
	return pm;
    }

    public void setPm(PrivMsg pm) {
	this.pm = pm;
    }

    public Long[] getIdPm() {
	return idPm;
    }

    public void setIdPm(Long[] idPm) {
	this.idPm = idPm;
    }

    // DI
    public void setPmTransaction(PrivMsgTransaction pmTransaction) {
	this.pmTransaction = pmTransaction;
    }

    /**
     * @return Returns the title.
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            The title to set.
     */
    public void setTitle(String title) {
	this.title = title;
    }

    /**
     * @return Returns the msg.
     */
    public String getMsg() {
	return msg;
    }

    /**
     * @param msg
     *            The msg to set.
     */
    public void setMsg(String msg) {
	this.msg = msg;
    }

    /**
     * @return Returns the username.
     */
    public String getUsername() {
	return username;
    }

    /**
     * @param username
     *            The username to set.
     */
    public void setUsername(String username) {
	this.username = username;
    }

    /**
     * @return Returns the userId.
     */
    public Long getUserId() {
	return userId;
    }

    /**
     * @param userId
     *            The userId to set.
     */
    public void setUserId(Long userId) {
	this.userId = userId;
    }

    /**
     * @return Returns the action.
     */
    public String getAct() {
	return act;
    }

    /**
     * @param action
     *            The action to set.
     */
    public void setAct(String action) {
	this.act = action;
    }
}
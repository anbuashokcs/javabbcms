/*
 * Created on 05/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.javabb.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lucas
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PrivMsg extends VOObject implements Serializable {
    
    private User userFrom;
    private User userTo;
    private String topic;
    private String text;
    private Date data;
    private Integer allowBBCode = new Integer(0);
    private Integer allowSmiles = new Integer(0);
    private Integer attachSign = new Integer(0);
    private Integer read = new Integer(0);
    
    public PrivMsg() {
        
    }
    
    public PrivMsg(PrivMsg p) {
        setAllowBBCode(p.getAllowBBCode());
        setAllowSmiles(p.getAllowSmiles());
        setAttachSign(p.getAttachSign());
        setData(p.getData());
        setId(p.getId());
        setText(p.getText());
        setTopic(p.getTopic());
        setUserFrom(p.getUserFrom());
        setUserTo(p.getUserTo());   
        setRead(p.getRead());
    }
    
    /**
	 * @param long1
	 */
	public PrivMsg(Long id) {
        setId(id);
	}

	public Long getIdMsg() {
        return getId();
    }
    
    public void setIdMsg(Long id) {
        setId(id);
    }

    /**
     * @return Returns the allowBBCode.
     */
    public Integer getAllowBBCode() {
        return allowBBCode;
    }
    /**
     * @param allowBBCode The allowBBCode to set.
     */
    public void setAllowBBCode(Integer allowBBCode) {
        this.allowBBCode = allowBBCode;
    }
    /**
     * @return Returns the allowSmiles.
     */
    public Integer getAllowSmiles() {
        return allowSmiles;
    }
    /**
     * @param allowSmiles The allowSmiles to set.
     */
    public void setAllowSmiles(Integer allowSmiles) {
        this.allowSmiles = allowSmiles;
    }
    /**
     * @return Returns the attachSign.
     */
    public Integer getAttachSign() {
        return attachSign;
    }
    /**
     * @param attachSign The attachSign to set.
     */
    public void setAttachSign(Integer attachSign) {
        this.attachSign = attachSign;
    }
    /**
     * @return Returns the data.
     */
    public Date getData() {
        return data;
    }
    /**
     * @param data The data to set.
     */
    public void setData(Date data) {
        this.data = data;
    }
    /**
     * @return Returns the text.
     */
    public String getText() {
        return text;
    }
    /**
     * @param text The text to set.
     */
    public void setText(String text) {
        this.text = text;
    }
    /**
     * @return Returns the topic.
     */
    public String getTopic() {
        return topic;
    }
    /**
     * @param topic The topic to set.
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }
    /**
     * @return Returns the userFrom.
     */
    public User getUserFrom() {
        return userFrom;
    }
    /**
     * @param userFrom The userFrom to set.
     */
    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }
    /**
     * @return Returns the userTo.
     */
    public User getUserTo() {
        return userTo;
    }
    /**
     * @param userTo The userTo to set.
     */
    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

	/**
	 * @return Returns the read.
	 */
	public Integer getRead() {
		return read;
	}
	/**
	 * @param read The read to set.
	 */
	public void setRead(Integer read) {
		this.read = read;
	}
}

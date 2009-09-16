package org.javabb.vo;

import java.util.Date;

public class BBLog {
    
    private Long logId;
    private Long topicId;
    private String userName;
    private Long userId;
    private String subject;
    private String obs;
    private Date actionDate;
    
    
    public Long getLogId() {
        return logId;
    }
    public void setLogId(Long logId) {
        this.logId = logId;
    }
    public Long getTopicId() {
        return topicId;
    }
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Date getActionDate() {
        return actionDate;
    }
    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }
    public String getObs() {
        return obs;
    }
    public void setObs(String obs) {
        this.obs = obs;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    
    

}

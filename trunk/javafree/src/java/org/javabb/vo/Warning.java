package org.javabb.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Warning implements Serializable {

    private Long warnId;
    private Post post;
    private User user;
    private Date warnDate;
    private String warnText;
    

    public Long getWarnId() {
        return warnId;
    }
    public void setWarnId(Long warnId) {
        this.warnId = warnId;
    }
    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }

    public Date getWarnDate() {
        return warnDate;
    }
    public void setWarnDate(Date warnDate) {
        this.warnDate = warnDate;
    }
    public String getWarnText() {
        return warnText;
    }
    public void setWarnText(String warnText) {
        this.warnText = warnText;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    
    
    
}

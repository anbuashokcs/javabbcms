package org.javabb.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Wiki implements Serializable {

    private Long id;
    private String word;
    private String body;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    
}

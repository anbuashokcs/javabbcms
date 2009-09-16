package org.javabb.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Banners implements Serializable {

    private Long bannerId;
    private String name;
    private Date initialDate;
    private Date expirationDate;
    private Integer clicks;
    private String url;
    private String imagem;
    private String alt;
    private String html;
    private Integer bannerLocation;
    private Integer views;
    
    public Banners(Long _bannerId){
	this.bannerId = _bannerId;
    }
    
    public Banners(){
	
    }

    public Long getBannerId() {
        return bannerId;
    }
    public void setBannerId(Long bannerId) {
        this.bannerId = bannerId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getInitialDate() {
        return initialDate;
    }
    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }
    public Date getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    public Integer getClicks() {
        return clicks;
    }
    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getImagem() {
        return imagem;
    }
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
    public String getAlt() {
        return alt;
    }
    public void setAlt(String alt) {
        this.alt = alt;
    }
    public String getHtml() {
        return html;
    }
    public void setHtml(String html) {
        this.html = html;
    }

    public Integer getBannerLocation() {
        return bannerLocation;
    }

    public void setBannerLocation(Integer bannerLocation) {
        this.bannerLocation = bannerLocation;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
    
    
    
    
}

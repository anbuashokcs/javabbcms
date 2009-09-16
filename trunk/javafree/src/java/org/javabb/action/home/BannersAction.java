package org.javabb.action.home;

import java.util.List;

import org.javabb.action.infra.BaseAction;
import org.javabb.vo.Banners;

@SuppressWarnings("serial")
public class BannersAction extends BaseAction {

    private Banners banner;
    private List<Banners> banners;


    public String listAllBanners() throws Exception {
	banners = bannerTransaction.loadAllBanners();
	return SUCCESS;
    }
    
    public String loadEditBanner() throws Exception {
	banner = (Banners) bannerTransaction.load(new Banners(), banner.getBannerId());
	return SUCCESS;
    }
    
    public String saveBanner() throws Exception {
	bannerTransaction.updateBanner(banner);
	return SUCCESS;
    }

    public String deleteBanner() throws Exception {
	bannerTransaction.deleteBanner(banner.getBannerId());
	return SUCCESS;
    }

    public String insertBanner() throws Exception {
	bannerTransaction.add(banner);
	return SUCCESS;
    }
    
    public String displayBanner() throws Exception {
	banner = bannerTransaction.displayBanner(banner.getBannerLocation());
	return SUCCESS;
    }


    public Banners getBanner() {
	return banner;
    }

    public void setBanner(Banners banner) {
	this.banner = banner;
    }

    public List<Banners> getBanners() {
	return banners;
    }

    public void setBanners(List<Banners> banners) {
	this.banners = banners;
    }

}

package org.javabb.transaction;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.javabb.dao.entity.IBannerDAO;
import org.javabb.infra.CacheUtils;
import org.javabb.vo.Banners;

public class BannerTransaction extends Transaction {

	private IBannerDAO bannerDAO;

	public void setBannerDAO(IBannerDAO bannerDAO) {
		this.bannerDAO = bannerDAO;
	}

	@SuppressWarnings("unchecked")
	public void putBannerViewOnCache(Long bannerId, Integer viewCount) {
		if (CacheUtils._VIEWS_BANNER == null) {
			CacheUtils._VIEWS_BANNER = new HashMap();
		}
		Integer view = CacheUtils.getViewBannerId(bannerId);
		if (view != null) {
			view += 1;
			CacheUtils._VIEWS_BANNER.put(bannerId, view);
		} else {
			CacheUtils._VIEWS_BANNER.put(bannerId, viewCount);
		}
	}

	private int getBannerIndex(int size) {
		return ((int) (Math.random() * size));
	}

	public Banners displayBanner(Integer location) throws Exception {
		List<Banners> banners = bannerDAO.findAllBanners(location);
		if (banners != null) {
			int index = getBannerIndex(banners.size());
			Banners banner = banners.get(index);
			Integer bannerView = banner.getViews();
			if (bannerView == null) {
				bannerView = 0;
			}
			Integer viewCount = new Integer(1 + bannerView);
			putBannerViewOnCache(banner.getBannerId(), viewCount);

			return banner;
		}
		return null;
	}

	public void insertBanner(Banners banner) throws Exception {
		bannerDAO.add(banner);
		bannerDAO.invalidateCache();
	}

	public void updateBanner(Banners banner) throws Exception {
		bannerDAO.saveOrUpdate(banner);
		bannerDAO.invalidateCache();
	}

	public void deleteBanner(Long bannerId) throws Exception {
		delete(new Banners(bannerId));
		bannerDAO.invalidateCache();
	}

	@SuppressWarnings("unchecked")
	public List<Banners> loadAllBanners() throws Exception {
		List<Banners> banners = loadAll(new Banners());
		return banners;
	}

	/**
	 * This method will be called by and quartz to persist all cached view of
	 * topics
	 * 
	 * @param cacheViews
	 */
	@SuppressWarnings("unchecked")
	public void persistViewCacheInTable(HashMap cacheViews) throws Exception {
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		try {
			lock.writeLock().lock();
			bannerDAO.persistViews(cacheViews);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}

}

package org.javabb.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.javabb.cache.local.BannerSingleCache;
import org.javabb.dao.entity.IBannerDAO;
import org.javabb.vo.Banners;

public class BannerHibernateDAO extends HibernateDAO implements IBannerDAO {

	private BannerSingleCache bannerCache;

	public void setBannerCache(BannerSingleCache bannerCache) {
		this.bannerCache = bannerCache;
	}

	/**
	 * Invalidates a list of categories in the cache
	 */
	public void invalidateCache() {
		bannerCache.invalidateCache();
	}

	@SuppressWarnings("unchecked")
	public List<Banners> findAllBanners(Integer location) throws Exception {
		List<Banners> banners = bannerCache.getFindAllBanners(location);
		if (banners == null) {

			String hql = " from Banners b where b.bannerLocation = :location and"
					+ " b.expirationDate >= :expirationDate ";

			Calendar dateIni = GregorianCalendar.getInstance();
			dateIni.setTime(new Date());
			dateIni.set(Calendar.HOUR_OF_DAY, 00);
			dateIni.set(Calendar.MINUTE, 01);

			Calendar dateEnd = GregorianCalendar.getInstance();
			dateEnd.setTime(new Date());
			dateEnd.set(Calendar.HOUR_OF_DAY, 23);
			dateEnd.set(Calendar.MINUTE, 59);

			Map values = new HashMap();
			values.put("location", location);
			// values.put("initialDate", new Date());
			values.put("expirationDate", new Date());

			banners = findByQueryParam(hql, values, 0, Integer.MAX_VALUE);

			bannerCache.setFindAllBanners(banners, location);
		}
		return banners;
	}

	@SuppressWarnings("unchecked")
	public synchronized void persistViews(HashMap cacheViews) {
		if (cacheViews != null) {
			Iterator it = cacheViews.keySet().iterator();
			while (it.hasNext()) {
				Long bannerId = (Long) it.next();
				Integer view = (Integer) cacheViews.get(bannerId);
				/*
				 * String sql = "update banners set views=" + view +
				 * " where banner_id= " + bannerId; this.executeSQL(sql);
				 */
				Banners b = (Banners) load(Banners.class, bannerId);
				b.setViews(view);
			}
		}

	}

}

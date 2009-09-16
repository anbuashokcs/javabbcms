package org.javabb.dao.entity;

import java.util.HashMap;
import java.util.List;

import org.javabb.dao.DAOConstants;
import org.javabb.vo.Banners;

public interface IBannerDAO extends DAOConstants {

    
    @SuppressWarnings("unchecked")
    public void persistViews(HashMap map) throws Exception;
    public List<Banners> findAllBanners(Integer location) throws Exception;
    public void invalidateCache();

}

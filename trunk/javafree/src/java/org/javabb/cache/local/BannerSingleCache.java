package org.javabb.cache.local;

import java.util.HashMap;
import java.util.List;

public class BannerSingleCache {

    @SuppressWarnings("unchecked")
    private static HashMap cachValues = new HashMap();

    @SuppressWarnings("unchecked")
    public void invalidateCache() {
	cachValues = new HashMap();
    }

    @SuppressWarnings("unchecked")
    public void setFindAllBanners(List input, Integer location) {
	String key = "findAllBanners-" + location;
	cachValues.put(key, input);
    }

    @SuppressWarnings("unchecked")
    public List getFindAllBanners(Integer location) {
	String key = "findAllBanners-" + location;
	return (List) cachValues.get(key);
    }


}

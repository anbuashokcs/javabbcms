package org.javabb.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class PersistenceCacheable {
	
	protected static List<String> categoryKeys = new ArrayList<String>();
	
	

	@SuppressWarnings("unchecked")
	protected String getKeyMapValues(String prefix, Map map){
		String key = prefix + "-"; 
		Iterator it = map.keySet().iterator();
		if(map != null){
			while (it.hasNext()) {
				Object itElement = it.next();
				Object element = map.get(itElement);
				if(element == null){
					continue;
				}
				if(element instanceof Date){
				} else {
					key += element+"";
				}
			}
		}
		return key;
	}
	
	
}

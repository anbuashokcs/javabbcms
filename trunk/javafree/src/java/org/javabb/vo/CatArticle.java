package org.javabb.vo;

import java.io.Serializable;
import java.util.List;

/*
 * Copyright 2004 JavaFree.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * $Id: CatArticle.java,v 1.1 2009/05/11 20:26:51 daltoncamargo Exp $
 */
/**
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 */
@SuppressWarnings("serial")
public class CatArticle implements Serializable {

	private Long catId;
	
    /** not nullable persistent field */
    private String catName;


    /** persistent field * */
    private List articles;


	public Long getCatId() {
		return catId;
	}


	public void setCatId(Long catId) {
		this.catId = catId;
	}


	public List getArticles() {
		return articles;
	}


	public void setArticles(List articles) {
		this.articles = articles;
	}


	public String getCatName() {
		return catName;
	}


	public void setCatName(String catName) {
		this.catName = catName;
	}

    
}

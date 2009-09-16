package org.javabb.vo;

import java.io.Serializable;
import java.util.Date;

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
 * $Id: SearchLockUp.java,v 1.1 2009/05/11 20:26:50 daltoncamargo Exp $
 * @author Dalton Camargo
 */
public class SearchLockUp implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long lockupId;
	private String keySearch;
	private Date searchDate;
	private Long forumId;
	
	
	public String getKeySearch() {
		return keySearch;
	}
	public void setKeySearch(String keySearch) {
		this.keySearch = keySearch;
	}
	public Long getLockupId() {
		return lockupId;
	}
	public void setLockupId(Long lockupId) {
		this.lockupId = lockupId;
	}
	public Long getForumId() {
		return forumId;
	}
	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}
	public Date getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}
}

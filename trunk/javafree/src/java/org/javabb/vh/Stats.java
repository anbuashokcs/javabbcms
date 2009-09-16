package org.javabb.vh;

import java.io.Serializable;

import org.javabb.vo.VOObject;

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
 * $Id: Stats.java,v 1.1 2009/05/11 20:27:20 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class Stats extends VOObject implements Serializable {

    private Long idStats;
    private Long recordUsersOnline;

    /* DUMMY ATTRIBUTES */
    private Integer totalPostCount;
    private Integer totalTopicCount;
    private Long lastRegisteredUserId;
    private String lastRegisteredUserName;
    private Integer totalRegisteredUsers;

    /**
     * @return Returns the idStats.
     */
    public Long getIdStats() {
        return idStats;
    }

    /**
     * @param idStats The idStats to set.
     */
    public void setIdStats(Long idStats) {
        this.idStats = idStats;
    }

    /**
     * @return Returns the recordUsersOnline.
     */
    public Long getRecordUsersOnline() {
        return recordUsersOnline;
    }

    /**
     * @param recordUsersOnline The recordUsersOnline to set.
     */
    public void setRecordUsersOnline(Long recordUsersOnline) {
        this.recordUsersOnline = recordUsersOnline;
    }

    /**
     * @return Returns the totalPostCount.
     */
    public Integer getTotalPostCount() {
        return totalPostCount;
    }

    /**
     * @param totalPostCount The totalPostCount to set.
     */
    public void setTotalPostCount(Integer totalPostCount) {
        this.totalPostCount = totalPostCount;
    }

    /**
     * @return Returns the totalTopicCount.
     */
    public Integer getTotalTopicCount() {
        return totalTopicCount;
    }

    /**
     * @param totalTopicCount The totalTopicCount to set.
     */
    public void setTotalTopicCount(Integer totalTopicCount) {
        this.totalTopicCount = totalTopicCount;
    }

    /**
     * @return Returns the lastRegisteredUserId.
     */
    public Long getLastRegisteredUserId() {
        return lastRegisteredUserId;
    }

    /**
     * @param lastRegisteredUserId The lastRegisteredUserId to set.
     */
    public void setLastRegisteredUserId(Long lastRegisteredUserId) {
        this.lastRegisteredUserId = lastRegisteredUserId;
    }

    /**
     * @return Returns the lastRegisteredUserName.
     */
    public String getLastRegisteredUserName() {
        return lastRegisteredUserName;
    }

    /**
     * @param lastRegisteredUserName The lastRegisteredUserName to set.
     */
    public void setLastRegisteredUserName(String lastRegisteredUserName) {
        this.lastRegisteredUserName = lastRegisteredUserName;
    }

    /**
     * @return Returns the totalRegisteredUsers.
     */
    public Integer getTotalRegisteredUsers() {
        return totalRegisteredUsers;
    }

    /**
     * @param totalRegisteredUsers The totalRegisteredUsers to set.
     */
    public void setTotalRegisteredUsers(Integer totalRegisteredUsers) {
        this.totalRegisteredUsers = totalRegisteredUsers;
    }
}
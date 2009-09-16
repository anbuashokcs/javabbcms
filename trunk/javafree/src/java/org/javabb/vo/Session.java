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
 * $Id: Session.java,v 1.1 2009/05/11 20:26:50 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class Session extends VOObject implements Serializable {
    private Long idUser;

    private String idSession;

    private String ip;

    private Date dataSession;

    private User user;

    /**
     * @return Returns the ip.
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip The ip to set.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return Returns the dataSession.
     */
    public Date getDataSession() {
        return dataSession;
    }

    /**
     * @param dataSession The dataSession to set.
     */
    public void setDataSession(Date dataSession) {
        this.dataSession = dataSession;
    }

    /**
     * @return Returns the idUser.
     */
    public Long getIdUser() {
        return idUser;
    }

    /**
     * @param idUser The idUser to set.
     */
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    /**
     * @return Returns the idSession.
     */
    public String getIdSession() {
        return idSession;
    }

    /**
     * @param idSession The idSession to set.
     */
    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    /**
     * @return Returns the idS.
     */
    public Long getIdS() {
        return getId();
    }

    /**
     * @param id The idS to set.
     */
    public void setIdS(Long id) {
        this.setId(id);
    }

    /**
     * @return Returns the usersPortal.
     */
    public User getUser() {
        return user;
    }

    /**
     * @param usersPortal The usersPortal to set.
     */
    public void setUser(User user) {
        this.user = user;
    }
}

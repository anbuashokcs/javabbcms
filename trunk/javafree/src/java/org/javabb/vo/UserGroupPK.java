package org.javabb.vo;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

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
 * $Id: UserGroupPK.java,v 1.1 2009/05/11 20:26:50 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 */
public class UserGroupPK implements Serializable {

    /** identifier field */
    private Long groupId;

    /** identifier field */
    private Long idUser;

    /** full constructor */
    public UserGroupPK(Long groupId, Long idUser) {
        this.groupId = groupId;
        this.idUser = idUser;
    }

    /** default constructor */
    public UserGroupPK() {
    }

    /** 
     *                @hibernate.property
     *                 column="group_id"
     *             
     */
    public Long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /** 
     *                @hibernate.property
     *                 column="id_user"
     *             
     */
    public Long getIdUser() {
        return this.idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("groupId", getGroupId())
            .append("idUser", getIdUser())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserGroupPK) ) return false;
        UserGroupPK castOther = (UserGroupPK) other;
        return new EqualsBuilder()
            .append(this.getGroupId(), castOther.getGroupId())
            .append(this.getIdUser(), castOther.getIdUser())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getGroupId())
            .append(getIdUser())
            .toHashCode();
    }

}

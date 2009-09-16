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
 * $Id: UserGroup.java,v 1.1 2009/05/11 20:26:49 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 */
/** 
 *        @hibernate.class
 *         table="jbb_user_group"
 *     
*/
public class UserGroup implements Serializable {

    /** identifier field */
    private org.javabb.vo.UserGroupPK comp_id;

    /** nullable persistent field */
    private org.javabb.vo.Group group;

    /** nullable persistent field */
    private org.javabb.vo.User user;

    /** full constructor */
    public UserGroup(org.javabb.vo.UserGroupPK comp_id, org.javabb.vo.Group jbbGroup, org.javabb.vo.User user) {
        this.comp_id = comp_id;
        this.group = jbbGroup;
        this.user = user;
    }

    /** default constructor */
    public UserGroup() {
    }

    /** minimal constructor */
    public UserGroup(org.javabb.vo.UserGroupPK comp_id) {
        this.comp_id = comp_id;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *         
     */
    public org.javabb.vo.UserGroupPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(org.javabb.vo.UserGroupPK comp_id) {
        this.comp_id = comp_id;
    }

    /** 
     *            @hibernate.many-to-one
     *             update="false"
     *             insert="false"
     *         
     *            @hibernate.column
     *             name="group_id"
     *         
     */
    public org.javabb.vo.Group getGroup() {
        return this.group;
    }

    public void setGroup(org.javabb.vo.Group jbbGroup) {
        this.group = jbbGroup;
    }

    /** 
     *            @hibernate.many-to-one
     *             update="false"
     *             insert="false"
     *         
     *            @hibernate.column
     *             name="id_user"
     *         
     */
    public org.javabb.vo.User getUser() {
        return this.user;
    }

    public void setUser(org.javabb.vo.User user) {
        this.user = user;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserGroup) ) return false;
        UserGroup castOther = (UserGroup) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

}

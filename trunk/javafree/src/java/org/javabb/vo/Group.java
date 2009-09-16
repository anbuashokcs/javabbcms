package org.javabb.vo;

import java.io.Serializable;
import java.util.Set;
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
 * $Id: Group.java,v 1.1 2009/05/11 20:26:50 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 */
/** 
 *        @hibernate.class
 *         table="jbb_group"
 *     
*/
public class Group implements Serializable {

    /** identifier field */
    private Long groupId;

    /** nullable persistent field */
    private String groupName;

    /** persistent field */
    private org.javabb.vo.User user;

    /** persistent field */
    private Set userGroups;

    /** persistent field */
    private Set GroupModerators;

    /** persistent field */
    private Set GroupPermissions;

    /** full constructor */
    public Group(Long groupId, String groupName, org.javabb.vo.User user, Set userGroups, Set GroupModerators, Set GroupPermissions) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.user = user;
        this.userGroups = userGroups;
        this.GroupModerators = GroupModerators;
        this.GroupPermissions = GroupPermissions;
    }

    /** default constructor */
    public Group() {
    }

    /** minimal constructor */
    public Group(Long groupId, org.javabb.vo.User user, Set userGroups, Set GroupModerators, Set GroupPermissions) {
        this.groupId = groupId;
        this.user = user;
        this.userGroups = userGroups;
        this.GroupModerators = GroupModerators;
        this.GroupPermissions = GroupPermissions;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Long"
     *             column="group_id"
     *         
     */
    public Long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /** 
     *            @hibernate.property
     *             column="group_name"
     *             length="255"
     *         
     */
    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="id_user"         
     *         
     */
    public org.javabb.vo.User getUser() {
        return this.user;
    }

    public void setUser(org.javabb.vo.User user) {
        this.user = user;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="group_id"
     *            @hibernate.collection-one-to-many
     *             class="org.javabb.vo.UserGroup"
     *         
     */
    public Set getUserGroups() {
        return this.userGroups;
    }

    public void setUserGroups(Set userGroups) {
        this.userGroups = userGroups;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="group_id"
     *            @hibernate.collection-one-to-many
     *             class="org.javabb.vo.GroupModerator"
     *         
     */
    public Set getGroupModerators() {
        return this.GroupModerators;
    }

    public void setGroupModerators(Set GroupModerators) {
        this.GroupModerators = GroupModerators;
    }

    /** 
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="group_id"
     *            @hibernate.collection-one-to-many
     *             class="org.javabb.vo.GroupPermission"
     *         
     */
    public Set getGroupPermissions() {
        return this.GroupPermissions;
    }

    public void setGroupPermissions(Set GroupPermissions) {
        this.GroupPermissions = GroupPermissions;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("groupId", getGroupId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Group) ) return false;
        Group castOther = (Group) other;
        return new EqualsBuilder()
            .append(this.getGroupId(), castOther.getGroupId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getGroupId())
            .toHashCode();
    }

}

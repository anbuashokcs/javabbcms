package org.javabb.vo;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="jbb_answer_notify"
 *     
*/
public class AnswerNotify implements Serializable {

    /** identifier field */
    private org.javabb.vo.AnswerNotifyPK comp_id;

    /** nullable persistent field */
    private org.javabb.vo.User user;

    /** nullable persistent field */
    private org.javabb.vo.Topic topic;

    /** full constructor */
    public AnswerNotify(org.javabb.vo.AnswerNotifyPK comp_id, org.javabb.vo.User user, org.javabb.vo.Topic topic) {
        this.comp_id = comp_id;
        this.user = user;
        this.topic = topic;
    }

    /** default constructor */
    public AnswerNotify() {
    }

    /** minimal constructor */
    public AnswerNotify(org.javabb.vo.AnswerNotifyPK comp_id) {
        this.comp_id = comp_id;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *         
     */
    public org.javabb.vo.AnswerNotifyPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(org.javabb.vo.AnswerNotifyPK comp_id) {
        this.comp_id = comp_id;
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

    /** 
     *            @hibernate.many-to-one
     *             update="false"
     *             insert="false"
     *         
     *            @hibernate.column
     *             name="id_topic"
     *         
     */
    public org.javabb.vo.Topic getTopic() {
        return this.topic;
    }

    public void setTopic(org.javabb.vo.Topic topic) {
        this.topic = topic;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof AnswerNotify) ) return false;
        AnswerNotify castOther = (AnswerNotify) other;
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

package org.javabb.vo;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FavUserTopicPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** identifier field */
    private Long idTopic;

    /** identifier field */
    private Long idUser;

    /** full constructor */
    public FavUserTopicPK(Long idTopic, Long idUser) {
        this.idTopic = idTopic;
        this.idUser = idUser;
    }

    /** default constructor */
    public FavUserTopicPK() {
    }

    /** 
     *                @hibernate.property
     *                 column="id_topic"
     *             
     */
    public Long getIdTopic() {
        return this.idTopic;
    }

    public void setIdTopic(Long idTopic) {
        this.idTopic = idTopic;
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
            .append("idTopic", getIdTopic())
            .append("idUser", getIdUser())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof FavUserTopicPK) ) return false;
        FavUserTopicPK castOther = (FavUserTopicPK) other;
        return new EqualsBuilder()
            .append(this.getIdTopic(), castOther.getIdTopic())
            .append(this.getIdUser(), castOther.getIdUser())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getIdTopic())
            .append(getIdUser())
            .toHashCode();
    }

}

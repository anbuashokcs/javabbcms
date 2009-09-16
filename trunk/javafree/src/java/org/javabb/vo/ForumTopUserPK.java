package org.javabb.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ForumTopUserPK implements Serializable {

	private Long idForum;
	private Long idUser;
	
    public boolean equals(Object other) {
        if ( !(other instanceof ForumTopUserPK) ) return false;
        ForumTopUserPK castOther = (ForumTopUserPK) other;
        return new EqualsBuilder()
            .append(this.getIdForum(), castOther.getIdForum())
            .append(this.getIdUser(), castOther.getIdUser())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getIdForum())
            .append(getIdUser())
            .toHashCode();
    }
    
	public Long getIdForum() {
		return idForum;
	}
	public void setIdForum(Long idForum) {
		this.idForum = idForum;
	}
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	
	
}

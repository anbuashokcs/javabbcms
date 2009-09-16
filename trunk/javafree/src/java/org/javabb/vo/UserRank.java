package org.javabb.vo;

import java.io.Serializable;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Dalton Camargo
 */
public class UserRank implements Comparable, Serializable {
	private Long rankId;
	private String rankName;
	private Integer rankMin;
	private Integer rankMax;
	private String rankImage;
	

	public Long getRankId() {
		return rankId;
	}
	public void setRankId(Long rankId) {
		this.rankId = rankId;
	}
	public String getRankImage() {
		return rankImage;
	}
	public void setRankImage(String rankImage) {
		this.rankImage = rankImage;
	}
	public Integer getRankMax() {
		return rankMax;
	}
	public void setRankMax(Integer rankMax) {
		this.rankMax = rankMax;
	}
	public Integer getRankMin() {
		return rankMin;
	}
	public void setRankMin(Integer rankMin) {
		this.rankMin = rankMin;
	}
	public String getRankName() {
		return rankName;
	}
	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		UserRank myClass = (UserRank) object;
		return new CompareToBuilder().append(this.rankId, myClass.rankId)
				.toComparison();
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof UserRank)) {
			return false;
		}
		UserRank rhs = (UserRank) object;
		return new EqualsBuilder().appendSuper(super.equals(object))
				.append(this.rankId, rhs.rankId)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-602096293, -334566575).appendSuper(
				super.hashCode()).append(
						this.rankId).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("rankMin", this.rankMin)
				.append("rankName", this.rankName)
				.append("rankId", this.rankId).append("rankImage",
						this.rankImage).append("rankMax", this.rankMax)
				.toString();
	}
	
	
}

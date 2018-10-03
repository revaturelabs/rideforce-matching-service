package com.revature.rideshare.matching.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;

@Embeddable
public class Pair implements Serializable {
	
	private static final long serialVersionUID = 3070299328660926894L;

	@Min(value=1)
	@Column
	private int userId;
	
	@Min(value=1)
	@Column
	private int affectedId;
	
	public Pair() {}
	
	public Pair(int userId, int affectedId) {
		this.userId = userId;
		this.affectedId = affectedId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAffectedId() {
		return affectedId;
	}

	public void setAffectedId(int affectedId) {
		this.affectedId = affectedId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + affectedId;
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (affectedId != other.affectedId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "userId=" + userId + ", affectedId=" + affectedId;
	}
}


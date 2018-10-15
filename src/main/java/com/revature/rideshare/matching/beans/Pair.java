package com.revature.rideshare.matching.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;

/**
 * The Class Pair. Holds the IDs of two users, representing a user and an affected
 * user; maps affect of second user as set by the first user.
 */
@Embeddable
public class Pair implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3070299328660926894L;

	/** The user id. Cannot be a value below 1 */
	@Min(value = 1)
	@Column
	private int userId;

	/** The affected id. Cannot be a value below 1 */
	@Min(value = 1)
	@Column
	private int affectedId;

	/**
	 * Instantiates a new pair.
	 */
	public Pair() {
	}

	/**
	 * Instantiates a new pair.
	 *
	 * @param userId     the user id
	 * @param affectedId the affected id
	 */
	public Pair(int userId, int affectedId) {
		this.userId = userId;
		this.affectedId = affectedId;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Gets the affected id.
	 *
	 * @return the affected id
	 */
	public int getAffectedId() {
		return affectedId;
	}

	/**
	 * Sets the affected id.
	 *
	 * @param affectedId the new affected id
	 */
	public void setAffectedId(int affectedId) {
		this.affectedId = affectedId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + affectedId;
		result = prime * result + userId;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "userId=" + userId + ", affectedId=" + affectedId;
	}
}

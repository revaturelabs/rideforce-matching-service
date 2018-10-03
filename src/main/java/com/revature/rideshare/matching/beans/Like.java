package com.revature.rideshare.matching.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

// TODO: Auto-generated Javadoc
/**
 * The Class Like. This class represents the like affect of 
 * the relationship between a user and the affected user.
 */

@Entity
@Table(name="LIKES")
public class Like {
	
	/** The pair. */
	@EmbeddedId
	@NotNull
	private Pair pair;
	
	/**
	 * Instantiates a new like.
	 */
	public Like() {}
	
	/**
	 * Instantiates a new like.
	 *
	 * @param pair the pair
	 */
	public Like(Pair pair) {
		super();
		this.pair = pair;
	}

	/**
	 * Gets the pair.
	 *
	 * @return the pair
	 */
	public Pair getPair() {
		return pair;
	}

	/**
	 * Sets the pair.
	 *
	 * @param pair the new pair
	 */
	public void setPair(Pair pair) {
		this.pair = pair;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Like [" + pair.toString() + "]";
	}

}

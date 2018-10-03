package com.revature.rideshare.matching.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class Dislike. It is used to represent the relationship between a user and a different
 * user that they have marked as not liking.
 */
@Entity
@Table(name="DISLIKES")
public class Dislike {

	/** The pair. */
	@EmbeddedId
	private Pair pair;
	
	/**
	 * Instantiates a new dislike.
	 */
	public Dislike() {}

	/**
	 * Instantiates a new dislike.
	 *
	 * @param pair the pair
	 */
	public Dislike(Pair pair) {
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
		return "Dislike [" + pair.toString() + "]";
	}
	
	
	
}

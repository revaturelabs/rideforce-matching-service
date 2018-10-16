<<<<<<< HEAD
package com.revature.rideshare.matching.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;;
// TODO: Auto-generated Javadoc
/**
 * The Class Dislike. Represents the dislike affect of 
 * the relationship between a user and the affected user.
 */
@Entity
@Table(name="DISLIKES")
public class Dislike {

	/** The pair. */
	@NotNull
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pair == null) ? 0 : pair.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Dislike other = (Dislike) obj;
		if (pair == null) {
			if (other.pair != null) {
				return false;
			}
		} else if (!pair.equals(other.pair)) {
			return false;
		}
		return true;
	}	
}
=======
package com.revature.rideshare.matching.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;

/**
 * The Class Dislike. Represents the dislike affect of 
 * the relationship between a user and the affected user.
 */
@Entity
@Table(name="DISLIKES")
public class Dislike {

	/** The pair. */
	@NotNull
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pair == null) ? 0 : pair.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Dislike other = (Dislike) obj;
		if (pair == null) {
			if (other.pair != null) {
				return false;
			}
		} else if (!pair.equals(other.pair)) {
			return false;
		}
		return true;
	}	
}
>>>>>>> dev

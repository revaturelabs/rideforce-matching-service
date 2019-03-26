package com.revature.rideshare.matching.beans.interfaces;

/**
 * An interface representing the property of having an integer ID.
 */
public interface Identifiable {
	/**
	 * Gets the ID of this object.
	 * 
	 * @return the ID of this object, which can be 0 to represent having no assigned
	 *         ID
	 */
	int getId();

	/**
	 * Sets the ID of this object.
	 * 
	 * @param id the new ID of the object, which can be 0 to represent having no
	 *           assigned ID
	 */
	void setId(int id);
}

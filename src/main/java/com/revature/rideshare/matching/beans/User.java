package com.revature.rideshare.matching.beans;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class User. Used by UserClient, a FeignClient for accessing the User
 * Service. Used in MatchService to match User rider to User driver.
 */

public class User {

	/** User's id. */
	@Min(value = 0)
	private int id;

	/** User's first name. */
	@NotEmpty
	private String firstName;

	/** User's last name. */
	@NotEmpty
	private String lastName;

	/** User's email. */
	@NotEmpty
	private String email;

	/** User's password. */
	@JsonIgnore
	private String password;

	/** User's photo url. */
	private String photoUrl;

	/** User's bio */
	private String bio;

	/**
	 * User's active status. Indicates whether rider is searching for ride or driver
	 * is available to give ride
	 */
	private String active;

	/**
	 * User's role. Indicates whether associate, trainer, admin, driver, or rider
	 */
	@NotEmpty
	private String role;

	/** User's office. Indicates user's work site */
	@NotEmpty
	private String office;

	/** User's location. Indicates the the coordinates of where the user lives */
	@NotNull
	private CachedLocation location;

	/** User's start time. */
	@Min(value = 1)
	private float startTime;

	/**
	 * User's batch end. Indicates when user will complete Revature training and no
	 * longer need Rideshare services
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
	@NotNull
	private Date batchEnd;

	/** User's cars. */
	@NotNull
	private Set<String> cars;

	/**
	 * User's contact info. Indicates user's preferred means and methods of being
	 * contacted
	 */
	@NotNull
	private Set<String> contactInfo;

	/**
	 * Instantiates a new user.
	 */
	public User() {
		super();
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param id          user's id
	 * @param firstName   user's first name
	 * @param lastName    user's last name
	 * @param email       user's email
	 * @param password    user's password
	 * @param photoUrl    user's photo url
	 * @param bio         user's bio
	 * @param active      active status
	 * @param role        user, trainer, admin, rider, driver
	 * @param office      user's Revature worksite
	 * @param location    user's home address
	 * @param startTime   user's daily start time for training
	 * @param batchEnd    predicted end of service for user
	 * @param cars        user's cars
	 * @param contactInfo user's contact info
	 */
	public User(int id, @NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty String email, String password,
			String photoUrl, String bio, String active, @NotEmpty String role, @NotEmpty String office,
			@NotNull CachedLocation location, @Min(value = 1) float startTime, @NotNull Date batchEnd,
			@NotNull Set<String> cars, @NotNull Set<String> contactInfo) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.photoUrl = photoUrl;
		this.bio = bio;
		this.active = active;
		this.role = role;
		this.office = office;
		this.location = location;
		this.startTime = startTime;
		this.batchEnd = batchEnd;
		this.cars = cars;
		this.contactInfo = contactInfo;
	}

	/**
	 * Instantiates a new user using only required data.
	 *
	 * @param id          user's id
	 * @param firstName   user's first name
	 * @param lastName    user's last name
	 * @param email       user's email
	 * @param role        user, trainer, admin, rider, driver
	 * @param office      user's Revature worksite
	 * @param address     user's address
	 * @param batchEnd    predicted end of service for user
	 * @param cars        user's cars
	 * @param contactInfo user's contact info
	 * @param startTime   user's start time
	 */
	public User(int id, @NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty String email,
			@NotEmpty String role, @NotEmpty String office, @NotNull CachedLocation location,
			@Min(value = 1) float startTime, @NotNull Date batchEnd, @NotNull Set<String> cars,
			@NotNull Set<String> contactInfo) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.office = office;
		this.location = location;
		this.startTime = startTime;
		this.batchEnd = batchEnd;
		this.cars = cars;
		this.contactInfo = contactInfo;
	}

	/**
	 * Gets user id.
	 *
	 * @return user id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets user id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets user first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets user first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets user last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets user last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets user email.
	 *
	 * @return user email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets user email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets user password.
	 *
	 * @return user password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets user password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets user photo url.
	 *
	 * @return user photo url
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

	/**
	 * Sets user photo url.
	 *
	 * @param photoUrl the new photo url
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	/**
	 * Checks if user is active.
	 *
	 * @return true, if user is active
	 */
	public String isActive() {
		return active;
	}

	/**
	 * Sets user status as active or inactive.
	 *
	 * @param active the new status
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Gets user role.
	 *
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets user role.
	 *
	 * @param role the new role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Gets user office.
	 *
	 * @return user office
	 */
	public String getOffice() {
		return office;
	}

	/**
	 * Sets user office.
	 *
	 * @param office the new office
	 */
	public void setOffice(String office) {
		this.office = office;
	}

	/**
	 * Gets user batch end.
	 *
	 * @return user batch end
	 */
	public Date getBatchEnd() {
		return batchEnd;
	}

	/**
	 * Sets user batch end.
	 *
	 * @param batchEnd the new batch end
	 */
	public void setBatchEnd(Date batchEnd) {
		this.batchEnd = batchEnd;
	}

	/**
	 * Gets user cars.
	 *
	 * @return user cars
	 */
	public Set<String> getCars() {
		return cars;
	}

	/**
	 * Sets user cars.
	 *
	 * @param cars the new cars
	 */
	public void setCars(Set<String> cars) {
		this.cars = cars;
	}

	/**
	 * Gets user contact info.
	 *
	 * @return user contact info
	 */
	public Set<String> getContactInfo() {
		return contactInfo;
	}

	/**
	 * Sets user contact info.
	 *
	 * @param contactInfo the new contact info
	 */
	public void setContactInfo(Set<String> contactInfo) {
		this.contactInfo = contactInfo;
	}

	/**
	 * Gets user start time.
	 *
	 * @return user start time
	 */
	public float getStartTime() {
		return startTime;
	}

	public CachedLocation getLocation() {
		return location;
	}

	public void setLocation(CachedLocation location) {
		this.location = location;
	}

	/**
	 * Sets user start time.
	 *
	 * @param startTime the new start time
	 */
	public void setStartTime(float startTime) {
		this.startTime = startTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", photoUrl=" + photoUrl + ", bio=" + bio + ", active=" + active
				+ ", role=" + role + ", office=" + office + ", location=" + location + ", startTime=" + startTime
				+ ", batchEnd=" + batchEnd + ", cars=" + cars + ", contactInfo=" + contactInfo + "]";
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
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((batchEnd == null) ? 0 : batchEnd.hashCode());
		result = prime * result + ((cars == null) ? 0 : cars.hashCode());
		result = prime * result + ((contactInfo == null) ? 0 : contactInfo.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((office == null) ? 0 : office.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((bio == null) ? 0 : bio.hashCode());
		result = prime * result + ((photoUrl == null) ? 0 : photoUrl.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + Float.floatToIntBits(startTime);
		return result;
	}

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
		User other = (User) obj;
		if (active == null) {
			if (other.active != null) {
				return false;
			}
		} else if (!active.equals(other.active)) {
			return false;
		}
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		if (batchEnd == null) {
			if (other.batchEnd != null) {
				return false;
			}
		} else if (!batchEnd.equals(other.batchEnd)) {
			return false;
		}
		if (cars == null) {
			if (other.cars != null) {
				return false;
			}
		} else if (!cars.equals(other.cars)) {
			return false;
		}
		if (contactInfo == null) {
			if (other.contactInfo != null) {
				return false;
			}
		} else if (!contactInfo.equals(other.contactInfo)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (office == null) {
			if (other.office != null) {
				return false;
			}
		} else if (!office.equals(other.office)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (bio == null) {
			if (other.bio != null) {
				return false;
			}
		} else if (!bio.equals(other.bio)) {
			return false;
		}
		if (photoUrl == null) {
			if (other.photoUrl != null) {
				return false;
			}
		} else if (!photoUrl.equals(other.photoUrl)) {
			return false;
		}
		if (role == null) {
			if (other.role != null) {
				return false;
			}
		} else if (!role.equals(other.role)) {
			return false;
		}
		if (Float.floatToIntBits(startTime) != Float.floatToIntBits(other.startTime)) {
			return false;
		}
		return true;
	}
}
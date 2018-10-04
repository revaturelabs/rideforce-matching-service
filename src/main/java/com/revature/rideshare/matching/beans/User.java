package com.revature.rideshare.matching.beans;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

// TODO: Auto-generated Javadoc
/**
 * The Class User. 
 */
public class User {
	
	/** The id. */
	private int id;

	/** The first name. */
	@NotEmpty
	private String firstName;

	/** The last name. */
	@NotEmpty
	private String lastName;

	/** The email. */
	@NotEmpty
	private String email;

	/** The password. */
	@JsonIgnore
	private String password;

	/** The photo url. */
	private String photoUrl;

	/** The active. */
	private boolean active;

	/** The role. */
	@NotEmpty
	private String role;

	/** The office. */
	@NotEmpty
	private String office;

	/** The address. */
	@NotEmpty
	private String address;

	/** The batch end. */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	@NotNull
	private Date batchEnd;

	/** The cars. */
	@NotNull
	private Set<String> cars;

	/** The venmo. */
	private String venmo;

	/** The contact info. */
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
	 * @param id the id
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param email the email
	 * @param password the password
	 * @param photoUrl the photo url
	 * @param active the active
	 * @param role the role
	 * @param office the office
	 * @param address the address
	 * @param batchEnd the batch end
	 * @param cars the cars
	 * @param venmo the venmo
	 * @param contactInfo the contact info
	 */
	public User(int id, @NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty String email, String password,
			String photoUrl, boolean active, @NotEmpty String role, @NotEmpty String office, @NotEmpty String address,
			@NotNull Date batchEnd, @NotNull Set<String> cars, String venmo, @NotNull Set<String> contactInfo) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.photoUrl = photoUrl;
		this.active = active;
		this.role = role;
		this.office = office;
		this.address = address;
		this.batchEnd = batchEnd;
		this.cars = cars;
		this.venmo = venmo;
		this.contactInfo = contactInfo;
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param id the id
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param email the email
	 * @param role the role
	 * @param office the office
	 * @param address the address
	 * @param batchEnd the batch end
	 * @param cars the cars
	 * @param contactInfo the contact info
	 */
	public User(int id, @NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty String email,
			@NotEmpty String role, @NotEmpty String office, @NotEmpty String address, @NotNull Date batchEnd,
			@NotNull Set<String> cars, @NotNull Set<String> contactInfo) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.office = office;
		this.address = address;
		this.batchEnd = batchEnd;
		this.cars = cars;
		this.contactInfo = contactInfo;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the photo url.
	 *
	 * @return the photo url
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

	/**
	 * Sets the photo url.
	 *
	 * @param photoUrl the new photo url
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Gets the office.
	 *
	 * @return the office
	 */
	public String getOffice() {
		return office;
	}

	/**
	 * Sets the office.
	 *
	 * @param office the new office
	 */
	public void setOffice(String office) {
		this.office = office;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the batch end.
	 *
	 * @return the batch end
	 */
	public Date getBatchEnd() {
		return batchEnd;
	}

	/**
	 * Sets the batch end.
	 *
	 * @param batchEnd the new batch end
	 */
	public void setBatchEnd(Date batchEnd) {
		this.batchEnd = batchEnd;
	}

	/**
	 * Gets the cars.
	 *
	 * @return the cars
	 */
	public Set<String> getCars() {
		return cars;
	}

	/**
	 * Sets the cars.
	 *
	 * @param cars the new cars
	 */
	public void setCars(Set<String> cars) {
		this.cars = cars;
	}

	/**
	 * Gets the venmo.
	 *
	 * @return the venmo
	 */
	public String getVenmo() {
		return venmo;
	}

	/**
	 * Sets the venmo.
	 *
	 * @param venmo the new venmo
	 */
	public void setVenmo(String venmo) {
		this.venmo = venmo;
	}

	/**
	 * Gets the contact info.
	 *
	 * @return the contact info
	 */
	public Set<String> getContactInfo() {
		return contactInfo;
	}

	/**
	 * Sets the contact info.
	 *
	 * @param contactInfo the new contact info
	 */
	public void setContactInfo(Set<String> contactInfo) {
		this.contactInfo = contactInfo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", photoUrl=" + photoUrl + ", active=" + active + ", role=" + role
				+ ", office=" + office + ", address=" + address + ", batchEnd=" + batchEnd + ", cars=" + cars
				+ ", venmo=" + venmo + ", contactInfo=" + contactInfo + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((batchEnd == null) ? 0 : batchEnd.hashCode());
		result = prime * result + ((cars == null) ? 0 : cars.hashCode());
		result = prime * result + ((contactInfo == null) ? 0 : contactInfo.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((office == null) ? 0 : office.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((photoUrl == null) ? 0 : photoUrl.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((venmo == null) ? 0 : venmo.hashCode());
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
		User other = (User) obj;
		if (active != other.active) {
			return false;
		}
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
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
		if (venmo == null) {
			if (other.venmo != null) {
				return false;
			}
		} else if (!venmo.equals(other.venmo)) {
			return false;
		}
		return true;
	}
	
	
}

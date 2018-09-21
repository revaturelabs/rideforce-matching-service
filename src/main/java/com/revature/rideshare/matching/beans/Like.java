package com.revature.rideshare.matching.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Like {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LIKE_SEQ")
	@SequenceGenerator(sequenceName="like_sequence", allocationSize=1, name="LIKE_SEQ")
	int id;
	
	@Column(nullable=false)
	int userId;
	
	@Column(nullable=false)
	int likedId;
	
	public Like() {}

	public Like(int userId, int likedId) {
		super();
		this.userId = userId;
		this.likedId = likedId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getLikedId() {
		return likedId;
	}

	public void setLikedId(int likedId) {
		this.likedId = likedId;
	}

	@Override
	public String toString() {
		return "Like [id=" + id + ", userId=" + userId + ", likedId=" + likedId + "]";
	}

}

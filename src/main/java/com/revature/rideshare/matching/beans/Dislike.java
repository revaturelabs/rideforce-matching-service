package com.revature.rideshare.matching.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Dislike {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DISLIKE_SEQ")
	@SequenceGenerator(sequenceName="dislike_sequence", allocationSize=1, name="DISLIKE_SEQ")
	int id;
	
	@Column(nullable=false)
	int userId;
	
	@Column(nullable=false)
	int dislikedId;
	
	public Dislike() {}
	
	public Dislike(int userId, int dislikedId) {
		super();
		this.userId = userId;
		this.dislikedId = dislikedId;
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

	public int getDislikedId() {
		return dislikedId;
	}

	public void setDislikedId(int dislikedId) {
		this.dislikedId = dislikedId;
	}

	@Override
	public String toString() {
		return "Dislike [id=" + id + ", userId=" + userId + ", dislikedId=" + dislikedId + "]";
	}
	
}

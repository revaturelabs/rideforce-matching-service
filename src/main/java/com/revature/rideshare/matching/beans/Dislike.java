package com.revature.rideshare.matching.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="DISLIKES")
public class Dislike {

	@EmbeddedId
	private Pair pair;
	
	public Dislike() {}

	public Dislike(Pair pair) {
		super();
		this.pair = pair;
	}

	public Pair getPair() {
		return pair;
	}

	public void setPair(Pair pair) {
		this.pair = pair;
	}

	@Override
	public String toString() {
		return "Dislike [" + pair.toString() + "]";
	}
	
	
	
}

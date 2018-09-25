package com.revature.rideshare.matching.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="LIKES")
public class Like {
	
	@EmbeddedId
	private Pair pair;
	
	public Like() {}
	
	public Like(Pair pair) {
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
		return "Like [" + pair.toString() + "]";
	}

}

package com.revature.rideshare.matching.interfaces;

@FunctionalInterface
public interface ListFilter<T> {
	
	public boolean filter(T e);
	
}

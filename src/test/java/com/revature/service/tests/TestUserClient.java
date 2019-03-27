package com.revature.service.tests;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.clients.UserClient;

public class TestUserClient implements UserClient {

	private Set<User> users;
	
	@Override
	public List<User> findByOfficeAndRole(int officeId, String role) {
		return users.stream()
				.collect(Collectors.toList());
	}

	@Override
	public User findById(int id, String auth) {
		Optional<User> user =  users.stream()
				.filter(u -> u.getId() == id)
				.findFirst();
		return user.orElse(null);
	}

	@Override
	public List<User> findByRole(String role) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDrivers(List<User> users) {
		this.users = users.stream()
				.collect(Collectors.toSet());
	}
	
	public void addUsers(User user) {
		users.add(user);
	}
}

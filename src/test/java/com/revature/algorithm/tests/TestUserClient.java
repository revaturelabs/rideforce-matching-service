package com.revature.algorithm.tests;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.clients.UserClient;

public class TestUserClient implements UserClient {
	
	Set<User> users;

	@Override
	public List<User> findByOfficeAndRole(int officeId, String role) {
		return users.stream()
				.collect(Collectors.toList());
	}

	@Override
	public User findById(int id) {
		Optional<User> user = users.stream()
				.filter(u -> u.getId() == id)
				.findFirst();
		return user.orElse(null);
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}
	
	

}

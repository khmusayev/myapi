package com.myapi.web.user.crud.impl;

import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.myapi.web.user.crud.api.UserCrudService;
import com.myapi.web.user.entity.User;

//@Service
final class InMemoryUsers implements UserCrudService {

	Map<String, User> users = new HashMap<>();

	@Override
	public void addToLoggedInUsers(String uuid, User user) {
		users.put(uuid, user);
	}

	@Override
	public Optional<User> findLoggedInUser(final String id) {
		return ofNullable(users.get(id));
	}

	@Override
	public Optional<User> findByUsername(final String username) {
		return users.values().stream().filter(u -> Objects.equals(username, u.getUsername())).findFirst();
	}

	@Override
	public User register(User user) {
		return null;
	}

	@Override
	public User exists(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findCurrentUser(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean logout(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeFromLoggedInList(String username) {
		// TODO Auto-generated method stub
		
	}

}

package com.myapi.web.user.crud.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapi.web.user.crud.api.UserCrudService;
import com.myapi.web.user.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

@Service
public class UserService implements UserCrudService {

	public static Map<String, User> loggedInUsers = new HashMap<>();

	@Autowired
	private UserRepository userRepository;

	@Override
	public void addToLoggedInUsers(final String token, final User user) {
		loggedInUsers.put(token, user);
	}

	@Override
	public Optional<User> findLoggedInUser(final String id) {
		return ofNullable(loggedInUsers.get(id));
	}

	@Override
	public Optional<User> findByUsername(final String username) {
		return loggedInUsers.values().stream().filter(u -> Objects.equals(username, u.getUsername())).findFirst();
	}

	@Override
	public User register(User user) {
		User userInDB = userRepository.findByUsername(user.getUsername());
		if(userInDB != null)
			throw new RuntimeException("Username already exists " + user.getUsername());
		user.setId(new Long(0));
		return userRepository.save(user);
	}

	@Override
	public User exists(String username, String password) {
		User userInDB = userRepository.findByUsername(username);
		if(userInDB == null)
			throw new RuntimeException("No such username - " + username);
		if(!userInDB.getPassword().equals(password))
			throw new RuntimeException("Wrong password for the username - " + username);
		return userInDB;
	}

	@Override
	public User findCurrentUser(String token) {
		return null;
	}

	@Override
	public boolean logout(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeFromLoggedInList(String username) {
		loggedInUsers.entrySet().removeIf(entry -> entry.getValue().getUsername().equals(username));
	}

	public void save(User user) {
		userRepository.save(user);
	}
	
}

package com.myapi.web.auth.impl;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import com.myapi.web.auth.api.UserAuthenticationService;
import com.myapi.web.user.crud.api.UserCrudService;
import com.myapi.web.user.entity.User;

import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class UUIDAuthenticationService implements UserAuthenticationService {
	@NonNull
	UserCrudService users;

	@Override
	public Optional<User> login(final String username, final String password) {

		User loggedInUser = users.exists(username, password);
		if (loggedInUser != null) {
			final String uuid = UUID.randomUUID().toString();
			loggedInUser.setToken(uuid);
			users.addToLoggedInUsers(uuid, loggedInUser);
			loggedInUser.setToken(uuid);
			return Optional.of(loggedInUser);
		}
		return Optional.empty();
	}

	@Override
	public Optional<User> findLoggedInUserByToken(final String token) {
		return users.findLoggedInUser(token);
	}

	@Override
	public boolean logout(final User user) {
		User loggedInUser = users.exists(user.getUsername(), user.getPassword());
		if (loggedInUser != null) {
			users.removeFromLoggedInList(user.getUsername());
			return true;
		}
		throw new Error("Credentials do not match.");
	}
}

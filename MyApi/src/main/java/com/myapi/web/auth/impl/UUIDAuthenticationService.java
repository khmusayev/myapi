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
	public Optional<String> login(final String username, final String password) {
		final String uuid = UUID.randomUUID().toString();
		final User user = new User(new Long(0), username, password, null);

		User loggedInUser = users.exists(user);
		if (loggedInUser != null) {
			loggedInUser.setToken(uuid);
			users.addToLoggedInUsers(uuid, loggedInUser);
			return Optional.of(uuid);
		}
		return Optional.empty();
	}

	@Override
	public Optional<User> findLoggedInUserByToken(final String token) {
		return users.findLoggedInUser(token);
	}

	@Override
	public boolean logout(final User user) {
		User loggedInUser = users.exists(user);
		if (loggedInUser != null) {
			users.removeFromLoggedInList(user.getUsername());
			return true;
		}
		throw new Error("Credentials do not match.");
	}
}

package com.myapi.web.user.crud.api;

import java.util.Optional;

import com.myapi.web.user.entity.User;

/**
 * User security operations like login and logout, and CRUD operations on
 * {@link User}.
 * 
 * @author jerome
 *
 */
public interface UserCrudService {

	Optional<User> findLoggedInUser(String id);

	Optional<User> findByUsername(String username);

	User register(User user);

	User exists(User user);

	User findCurrentUser(String token);

	boolean logout(String token);

	void addToLoggedInUsers(String uuid, User user);

	void removeFromLoggedInList(String username);

}

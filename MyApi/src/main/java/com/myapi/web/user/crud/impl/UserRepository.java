package com.myapi.web.user.crud.impl;

import org.springframework.data.repository.CrudRepository;

import com.myapi.web.user.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
}

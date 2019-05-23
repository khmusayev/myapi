package com.myapi.web.repository;

import org.springframework.data.repository.CrudRepository;

import com.myapi.web.entities.UserContact;

public interface UserContactRepository extends CrudRepository<UserContact, Long> {
	UserContact findByUserId(long userId);
}

package com.myapi.web.services;

import com.myapi.web.entities.UserContact;

public interface UserContactService {

	UserContact findByUserId(Long userId);

	void save(UserContact theContact);

	void delete(UserContact theContact);
	
}

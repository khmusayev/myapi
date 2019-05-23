package com.myapi.web.services;

import java.util.List;

import com.myapi.web.entities.UserEducation;

public interface UserEducationService {

	List<UserEducation> findAll(Long userId);

	void save(UserEducation theEdu);

	void delete(UserEducation userEdu);
	
}

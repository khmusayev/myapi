package com.myapi.web.services;

import java.util.List;
import java.util.Optional;

import com.myapi.web.entities.UserEducation;

public interface UserEducationService {

	List<UserEducation> findAll(Long userId);

	void save(UserEducation theEdu);

	void delete(UserEducation userEdu);
	
	Optional<UserEducation>  findById(Long id);
	
}

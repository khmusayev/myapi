package com.myapi.web.services;

import java.util.List;

import com.myapi.web.entities.UserJob;

public interface UserJobService {

	List<UserJob> findAll(Long userId);

	void save(UserJob theJob);

	void delete(UserJob userJob);
	
}

package com.myapi.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapi.web.entities.UserJob;
import com.myapi.web.repository.UserJobRepository;

@Service
public class UserJobServiceImpl implements UserJobService {

	@Autowired
	private UserJobRepository userJobRepository;

	@Override
	public List<UserJob> findAll(Long userId) {
		return userJobRepository.findByUserId(userId);
	}

	@Override
	public void save(UserJob theJob) {
		userJobRepository.save(theJob);
	}

	@Override
	public void delete(UserJob userJob) {
		userJobRepository.delete(userJob);
	}

}

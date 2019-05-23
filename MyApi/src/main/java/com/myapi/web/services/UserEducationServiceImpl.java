package com.myapi.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapi.web.entities.UserEducation;
import com.myapi.web.repository.UserEducationRepository;

@Service
public class UserEducationServiceImpl implements UserEducationService {

	@Autowired
	private UserEducationRepository userEducationRepository;

	@Override
	public List<UserEducation> findAll(Long userId) {
		return userEducationRepository.findByUserId(userId);
	}

	@Override
	public void save(UserEducation theEducation) {
		userEducationRepository.save(theEducation);
	}

	@Override
	public void delete(UserEducation theEducation) {
		userEducationRepository.delete(theEducation);
	}

}

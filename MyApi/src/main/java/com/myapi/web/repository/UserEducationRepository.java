package com.myapi.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.myapi.web.entities.UserEducation;

public interface UserEducationRepository extends CrudRepository<UserEducation, Long> {
	List<UserEducation> findByUserId(long userId);
}

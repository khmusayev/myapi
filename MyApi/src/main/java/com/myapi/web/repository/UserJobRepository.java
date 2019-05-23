package com.myapi.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.myapi.web.entities.UserJob;

public interface UserJobRepository extends CrudRepository<UserJob, Long> {
	List<UserJob> findByUserId(long userId);
}

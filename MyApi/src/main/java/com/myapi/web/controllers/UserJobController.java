package com.myapi.web.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapi.web.entities.UserEducation;
import com.myapi.web.entities.UserJob;
import com.myapi.web.services.UserJobService;
import com.myapi.web.user.crud.impl.UserService;
import com.myapi.web.user.entity.User;

@RestController
@RequestMapping("/users/jobs")
public class UserJobController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserJobService userJobService;

	@GetMapping
	public List<UserJob> getUser(final Principal principal) {

		Optional<User> theUser = userService.findByUsername(principal.getName());

		if (!theUser.isPresent()) {
			throw new RuntimeException("User id not found - " + principal.getName());
		}
		List<UserJob> jobs = userJobService.findAll(theUser.get().getId());
		return jobs;
	}

	@PostMapping
	public UserJob addJobEntry(final Principal principal, @RequestBody final UserJob theJob) {
		Optional<User> theUser = userService.findByUsername(principal.getName());

		if (!theUser.isPresent()) {
			throw new RuntimeException("User id not found - " + principal.getName());
		}
		
		theJob.setId((long) 0);
		theJob.setUser(theUser.get());
		userJobService.save(theJob);
		return theJob;
	}
	
	@PutMapping
	public UserJob updateJobEntry(final Principal principal, @RequestParam("jobid") final int jobId, final @RequestBody UserJob theJob) {
		Optional<User> theUser = userService.findByUsername(principal.getName());

		if (!theUser.isPresent()) {
			throw new RuntimeException("User id not found - " + principal.getName());
		}
		
		List<UserJob> jobs = userJobService.findAll(theUser.get().getId());
		if(jobId >= jobs.size()) {
			throw new RuntimeException("UserJob id not found - " + theUser.get().getId());
		}
		long edittedJobId = jobs.get((int) jobId).getId();
		theJob.setUser(theUser.get());
		theJob.setId(edittedJobId);
		userJobService.save(theJob);
		return theJob;
	}
	
	@DeleteMapping
	public String deleteJobEntry(final Principal principal, @RequestParam("jobid") final int jobId) {
		Optional<User> theUser = userService.findByUsername(principal.getName());

		if (!theUser.isPresent()) {
			throw new RuntimeException("User id not found - " + principal.getName());
		}
		List<UserJob> jobs = userJobService.findAll(theUser.get().getId());
		if(jobId >= jobs.size()) {
			throw new RuntimeException("UserJob id not found - " + theUser.get().getId());
		}
		long deletedJobId = jobs.get((int) jobId).getId();
		userJobService.delete(jobs.get((int) jobId));
		return "Deleted userJob id - " + deletedJobId;
	}

}

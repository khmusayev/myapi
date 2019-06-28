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
	public UserJob addUpdateJobEntry(final Principal principal, @RequestBody final UserJob theJob) {
		Optional<User> theUser = userService.findByUsername(principal.getName());

		if (!theUser.isPresent()) {
			throw new RuntimeException("User id not found - " + principal.getName());
		}
		if(theJob.getId() < 0) {
			theJob.setId((long) 0);
			theJob.setUser(theUser.get());
			userJobService.save(theJob);
		} else {
			 Optional<UserJob> job = userJobService.findById(theJob.getId());
			if(job.isPresent()) {
				if(job.get().getUser().getId() != theUser.get().getId()) {
					throw new RuntimeException("Job does not belong to the user - " + principal.getName());
				} else {
					theJob.setUser(job.get().getUser());
					userJobService.save(theJob);
				}
			}
			else {
				throw new RuntimeException("Job id is bigger than zero and does not exist in the databank");
			}
		}
		
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
	public UserJob deleteJobEntry(final Principal principal, @RequestParam("jobid") final Long jobId) {
		Optional<User> theUser = userService.findByUsername(principal.getName());

		if (!theUser.isPresent()) {
			throw new RuntimeException("User id not found - " + principal.getName());
		}
		
		Optional<UserJob> job = userJobService.findById(jobId);
		if(job.isPresent()) {
			if(job.get().getUser().getId() != theUser.get().getId()) {
				throw new RuntimeException("Job does not belong to the user - " + principal.getName());
			} else {
				userJobService.delete(job.get());
				return job.get();
			}
		}
		return null;
	}

}

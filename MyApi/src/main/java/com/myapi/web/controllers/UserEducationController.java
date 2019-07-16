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
import com.myapi.web.services.UserEducationService;
import com.myapi.web.user.crud.impl.UserService;
import com.myapi.web.user.entity.User;

@RestController
@RequestMapping("/users/studies")
public class UserEducationController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserEducationService userEducationService;

	@GetMapping
	public List<UserEducation> getAllStudies(final Principal principal) {

		Optional<User> theUser = userService.findByUsername(principal.getName());

		if (!theUser.isPresent()) {
			throw new RuntimeException("User id not found - " + principal.getName());
		}
		List<UserEducation> studies = userEducationService.findAll(theUser.get().getId());
		return studies;
	}

	@PostMapping
	public UserEducation addUpdateStudyEntry(final Principal principal, @RequestBody final UserEducation theStudy) {
		Optional<User> theUser = userService.findByUsername(principal.getName());

		if (!theUser.isPresent()) {
			throw new RuntimeException("User id not found - " + principal.getName());
		}
		if(theStudy.getId() < 0) {
			theStudy.setId((long) 0);
			theStudy.setUser(theUser.get());
			userEducationService.save(theStudy);
		} else {
			 Optional<UserEducation> study = userEducationService.findById(theStudy.getId());
			if(study.isPresent()) {
				if(study.get().getUser().getId() != theUser.get().getId()) {
					throw new RuntimeException("Job does not belong to the user - " + principal.getName());
				} else {
					theStudy.setUser(study.get().getUser());
					userEducationService.save(theStudy);
				}
			}
			else {
				throw new RuntimeException("Job id is bigger than zero and does not exist in the databank");
			}
		}
		
		return theStudy;
	}
	
	@PutMapping
	public UserEducation updateStudyEntry(final Principal principal, @RequestParam("eduid") final int eduid, final @RequestBody UserEducation theStudy) {
		Optional<User> theUser = userService.findByUsername(principal.getName());

		if (!theUser.isPresent()) {
			throw new RuntimeException("User id not found - " + principal.getName());
		}
		
		List<UserEducation> studies = userEducationService.findAll(theUser.get().getId());
		if(eduid >= studies.size()) {
			throw new RuntimeException("UserJob id not found - " + eduid);
		}
		long edittedstudyId = studies.get((int) eduid).getId();
		theStudy.setUser(theUser.get());
		theStudy.setId(edittedstudyId);
		userEducationService.save(theStudy);
		return theStudy;
	}
	
	@DeleteMapping
	public UserEducation deleteStudyEntry(final Principal principal, @RequestParam("eduid") final Long eduid) {
		Optional<User> theUser = userService.findByUsername(principal.getName());

		if (!theUser.isPresent()) {
			throw new RuntimeException("User id not found - " + principal.getName());
		}
		
		Optional<UserEducation> study = userEducationService.findById(eduid);
		if(study.isPresent()) {
			if(study.get().getUser().getId() != theUser.get().getId()) {
				throw new RuntimeException("Job does not belong to the user - " + principal.getName());
			} else {
				userEducationService.delete(study.get());
				return study.get();
			}
		}
		return null;
	}

}

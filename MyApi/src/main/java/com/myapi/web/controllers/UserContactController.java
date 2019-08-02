package com.myapi.web.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapi.web.entities.UserContact;
import com.myapi.web.services.UserContactService;
import com.myapi.web.user.crud.impl.UserService;
import com.myapi.web.user.entity.User;

@RestController
@RequestMapping("/users/contact")
public class UserContactController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserContactService userContactService;

	@GetMapping
	public UserContact getContact(Principal principal) {

		Optional<User> theUser = userService.findByUsername(principal.getName());

		if (!theUser.isPresent()) {
			throw new RuntimeException("User id not found - " + principal.getName());
		}
		UserContact contact = userContactService.findByUserId(theUser.get().getId());
		return contact;
	}

	@PostMapping
	public UserContact addUpdateContact(Principal principal, @RequestBody UserContact theContact) {
		Optional<User> theUser = userService.findByUsername(principal.getName());

		if (!theUser.isPresent()) {
			throw new RuntimeException("User id not found - " + theUser.get().getUsername());
		}

		theContact.setId((long) 0);
		UserContact currentContact = userContactService.findByUserId(theUser.get().getId());
		if(currentContact != null) {
			theUser.get().setUserContact(null);
			currentContact.setUser(null);
			userContactService.delete(currentContact);
			System.out.println("Old contact just deleted");
		}
		theUser.get().setUserContact(theContact);	
		userService.save(theUser.get());
		System.out.println("New contact just added");
//		userContactService.save(theContact);
		return theContact;
	}

	@DeleteMapping
	public String deleteContact(Principal principal) {
		Optional<User> theUser = userService.findByUsername(principal.getName());

		if (!theUser.isPresent()) {
			throw new RuntimeException("User id not found - " + principal.getName());
		}
		
		UserContact contact = userContactService.findByUserId(theUser.get().getId());
		if (contact == null) {
			return "No Contact to delete";
		}
		userContactService.delete(contact);
		return "Deleted Contact id - " + contact.getId();
	}

}

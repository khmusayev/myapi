package com.myapi.web.user.controller;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapi.web.auth.api.UserAuthenticationService;
import com.myapi.web.user.entity.User;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
@CrossOrigin(origins = "http://localhost:4444")
final class SecuredUsersController {
  @NonNull
  UserAuthenticationService authentication;

  @CrossOrigin(origins = "http://localhost:4444")
  @GetMapping("/current")
  User getCurrent(@AuthenticationPrincipal final User user) {
    return user;
  }

  @CrossOrigin(origins = "http://localhost:4444")
  @GetMapping("/logout")
  boolean logout(@AuthenticationPrincipal final User user) {
	System.out.println("About to log out");
    return authentication.logout(user);
  }
}

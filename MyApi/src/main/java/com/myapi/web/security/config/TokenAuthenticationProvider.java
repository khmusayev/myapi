package com.myapi.web.security.config;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.myapi.web.auth.api.UserAuthenticationService;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
  @NonNull
  UserAuthenticationService auth;

  @Override
  protected void additionalAuthenticationChecks(final UserDetails d, final UsernamePasswordAuthenticationToken auth) {
    // Nothing to do
	  }

  @Override
  protected UserDetails retrieveUser(final String username, final UsernamePasswordAuthenticationToken authentication) {
    System.out.println("About to check if the token is there");
	final Object token = authentication.getCredentials();
    return Optional
      .ofNullable(token)
      .map(String::valueOf)
      .flatMap(auth::findLoggedInUserByToken)
      .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with authentication token=" + token));
  }
}


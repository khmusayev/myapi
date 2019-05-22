package com.myapi.web.user.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myapi.web.user.crud.impl.UserService;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import static java.util.Objects.requireNonNull;

@Entity
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User implements UserDetails {
	private static final long serialVersionUID = 2396654715019746670L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	Long id;
	String username;
	String password;

	@Transient
	String token;

	@JsonCreator
	User(@JsonProperty("id") final Long id, @JsonProperty("username") final String username,
			@JsonProperty("password") final String password, @JsonProperty("token") final String token) {
		super();
		this.id = requireNonNull(id);
		this.username = requireNonNull(username);
		this.password = requireNonNull(password);
		this.token = token;
	}

	@JsonIgnore
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		Map<String, User> a = UserService.loggedInUsers.entrySet().stream()
				.filter(entry -> entry.getKey().equals(this.token) && entry.getValue().getUsername().equals(this.username))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		if (a.isEmpty())
			return false;
		return true;
	}

}

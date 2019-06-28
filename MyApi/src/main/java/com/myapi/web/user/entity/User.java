package com.myapi.web.user.entity;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myapi.web.entities.UserContact;
import com.myapi.web.entities.UserEducation;
import com.myapi.web.entities.UserJob;
import com.myapi.web.user.crud.impl.UserService;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
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
	String firstName;
	String lastName;

	@Transient
	String token;

	@JsonCreator
	public User(@JsonProperty("id") final Long id, @JsonProperty("username") final String username,
			@JsonProperty("password") final String password, @JsonProperty("firstName") final String firstName, @JsonProperty("lastName") final String lastName,@JsonProperty("token") final String token) {
		super();
		this.id = requireNonNull(id);
		this.username = requireNonNull(username);
		this.password = requireNonNull(password);
		this.firstName = requireNonNull(firstName);
		this.lastName = requireNonNull(lastName);
		this.token = token;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<UserJob> userJobs = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<UserEducation> userEdus = new HashSet<>();
	
	@JsonIgnore
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    UserContact userContact;
	
	public void setUserContact(UserContact contact) {
		if (contact == null) {
            if (getUserContact() != null) {
                getUserContact().setUser(null);
            }
        }
        else {
            contact.setUser(this);
        }
        this.userContact = contact;
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
		return true;
	}

}

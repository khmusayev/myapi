package com.myapi.web.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myapi.web.user.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class UserEducation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable = false, updatable = false)
	private Long id;
	private String startDate;
	private String endDate;
	private String university;
	private String faculty;
	private String degree;
	
	@Column(columnDefinition="text")
	private String description;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	@JsonIgnore
    private User user;

	@Override
	public String toString() {
		return "UserEducation{" +
				"id=" + id +
				", startDate='" + startDate + '\'' +
				", endDate='" + endDate + '\'' +
				", university='" + university + '\'' +
				", faculty='" + faculty + '\'' +
				", degree='" + degree + '\'' +
				'}';
	}
}

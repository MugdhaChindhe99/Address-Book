package com.cg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "register")
public class User {

	@Id
	@Column(name = "user_id")
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userIdGenerator")
	//@SequenceGenerator(name = "userIdGenerator", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	@Column
	@Size(min = 3, max = 20)
	private String username;

	@Column
	@Size(min = 6, max = 30)
	private String emailId;

	@Column
	@Size(min = 6, max = 15)
	private String password;
	
	public User() {
		super();
	}

	public User(int userId, @Size(min = 3, max = 20) String username, @Size(min = 6, max = 30) String emailId,
			@Size(min = 6, max = 15) String password) {
		super();
		this.userId = userId;
		this.username = username;
		this.emailId = emailId;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

package com.alifetvaci.ReadingIsGood.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Customer {

	@Id
	@JsonIgnore
	private String id;

	@NotBlank
	private String username;

	@NotBlank
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;


	public Customer() {
		super();
	}

	public Customer(String id, @NotBlank String username, @NotBlank String email,
			@NotBlank @Size(max = 120) String password) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}

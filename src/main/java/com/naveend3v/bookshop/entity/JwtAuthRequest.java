package com.naveend3v.bookshop.entity;

public class JwtAuthRequest {

	private String username;
	private String password;

	// Non parameterized constructor

	public JwtAuthRequest() {}

	// Parameterized constructor

	public JwtAuthRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// Getter and Setter
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


}

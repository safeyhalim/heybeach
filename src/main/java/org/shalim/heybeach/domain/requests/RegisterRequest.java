package org.shalim.heybeach.domain.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest implements IRequest {

	@JsonProperty(value = "email", required = true)
	private String email;

	@JsonProperty(value = "password", required = true)
	private String password;

	@JsonProperty(value = "repeatedPassword", required = true)
	private String repeatedPassword;

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

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}
}

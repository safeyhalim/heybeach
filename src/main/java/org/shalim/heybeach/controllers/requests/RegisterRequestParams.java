package org.shalim.heybeach.controllers.requests;

public enum RegisterRequestParams {
	EMAIL("email"), PASSWORD("password"), REPEATED_PASSWORD("repeatedPassword");

	private String paramName;

	private RegisterRequestParams(String paramName) {
		this.paramName = paramName;
	}

	public String getParamName() {
		return paramName;
	}
}

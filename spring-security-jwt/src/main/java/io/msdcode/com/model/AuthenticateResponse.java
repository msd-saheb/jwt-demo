package io.msdcode.com.model;

public class AuthenticateResponse {

	private String jwt;

	public AuthenticateResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	
	
}

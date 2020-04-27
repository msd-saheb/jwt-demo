package io.msdcode.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.msdcode.com.model.AuthenticateResponse;
import io.msdcode.com.model.AuthenticationRequest;
import io.msdcode.com.serice.JwtUtil;
import io.msdcode.com.serice.MyUserDetailsService;

@RestController
public class HomeResource {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/hello")
	public String getMessage() {
		return "success";
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> getJwtToken(@RequestBody AuthenticationRequest authenticationRequest )throws Exception{
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), 
				authenticationRequest.getPassword()));	
	}catch (Exception e) {
		System.out.println("username & Password wrong");
	}
		UserDetails userdetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		String jwtToken = jwtUtil.generateToken(userdetails);
		return ResponseEntity.ok(new AuthenticateResponse(jwtToken));
}
}

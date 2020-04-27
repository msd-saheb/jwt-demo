package io.msdcode.com.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.msdcode.com.serice.JwtUtil;
import io.msdcode.com.serice.MyUserDetailsService;

@Configuration
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		String jwt=null;
		String username=null;
		if(header!=null && header.startsWith("Bearer "))
		{
		    jwt=header.substring(7);
		    username=jwtUtil.extractUsername(jwt);
		}
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userdetails = myUserDetailsService.loadUserByUsername(username);
			if(jwtUtil.validateToken(jwt,userdetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
						UsernamePasswordAuthenticationToken(userdetails, null,userdetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}
		
		}
		filterChain.doFilter(request,response);
		
	}

}

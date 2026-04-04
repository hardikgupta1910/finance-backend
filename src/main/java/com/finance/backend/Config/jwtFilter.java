package com.finance.backend.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.*;

import java.io.IOException;

@Component
public class jwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private final jwtService jwtService;
	jwtFilter(jwtService jwtService){
		this.jwtService=jwtService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	
		String header = request.getHeader("Authorization");
		if(header == null || !header.startsWith("Bearer ")){
			filterChain.doFilter(request,response);
			return;
		}
		String token = header.substring("Bearer ".length());
		
		if (!jwtService.validateToken(token)) {
			throw new RuntimeException("Invalid token");
		}
		try {
			if (jwtService.validateToken(token)) {
				Long userId = jwtService.extractUserId(token);
				UsernamePasswordAuthenticationToken auth =
						new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
				
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		filterChain.doFilter(request,response);
	}
	
	
}

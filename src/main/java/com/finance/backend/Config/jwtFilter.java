package com.finance.backend.Config;

import com.finance.backend.Model.User;
import com.finance.backend.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.*;

import java.io.IOException;

@Component
public class jwtFilter extends OncePerRequestFilter {
	
	
	private final jwtService jwtService;
	private final UserService userService;
	@Autowired
	jwtFilter(jwtService jwtService, UserService userService) {
		this.jwtService=jwtService;
		this.userService=userService;
	}
	
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//		String path = request.getRequestURI();
//
//		if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
//			filterChain.doFilter(request, response);
//			return;
//		}
//
//		String header = request.getHeader("Authorization");
//		if(header == null || !header.startsWith("Bearer ")){
//			filterChain.doFilter(request,response);
//			return;
//		}
//		String token = header.substring("Bearer ".length());
//
//		if (!jwtService.validateToken(token)) {
//			throw new RuntimeException("Invalid token");
//		}
//		try {
//			if (jwtService.validateToken(token)) {
//				Long userId = jwtService.extractUserId(token);
//				User user = userService.getUserById(userId);
//				List<GrantedAuthority> authorities =
//						List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
//
//				UsernamePasswordAuthenticationToken auth =
//						new UsernamePasswordAuthenticationToken(userId, null, authorities);
//
//				SecurityContextHolder.getContext().setAuthentication(auth);
//			}
//		}catch (Exception e) {
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			return;
//		}
//		filterChain.doFilter(request,response);
//	}
//
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain)
			throws ServletException, IOException {
		
		String path = request.getRequestURI();
		
		// 🔥 SKIP SWAGGER
		if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String header = request.getHeader("Authorization");
		
		if (header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = header.substring(7);
		
		try {
			if (!jwtService.validateToken(token)) {
				throw new RuntimeException("Invalid token");
			}
			
			Long userId = jwtService.extractUserId(token);
			User user = userService.getUserById(userId);
			
			List<GrantedAuthority> authorities =
					List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
			
			UsernamePasswordAuthenticationToken auth =
					new UsernamePasswordAuthenticationToken(userId, null, authorities);
			
			SecurityContextHolder.getContext().setAuthentication(auth);
			
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		filterChain.doFilter(request, response);
	}
	
}

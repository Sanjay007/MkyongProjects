package com.mkyong.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class RestServiceEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest arg0, HttpServletResponse response,
			AuthenticationException arg2) throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		  response.setHeader("Cache-Control", "no-cache");
		  try {
		    response.getWriter().write(String.format("{\"%s\": \"%s\"}", 401,"Unauthorized Access"));
		  } catch (IOException e) {
		      
		  }
		
	}


}



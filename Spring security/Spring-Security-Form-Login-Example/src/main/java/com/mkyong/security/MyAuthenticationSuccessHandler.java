package com.mkyong.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.mkyong.utils.JwtUtil;



public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler  {

	private Logger log=LoggerFactory.getLogger(MyAuthenticationSuccessHandler.class);
	
	

		@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse res, Authentication arg2) throws IOException,
			ServletException {
		log.info("Header Details Inside Handler Mapping");
		
		log.info("Inside Authentication Handler : You Are Authenticated by ");
		
	
		
		User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();  
		
		System.out.println("Authenticated Details For User :"+authUser.toString());
		
		
		String s=MyTokenUtils.generateToken(authUser);
		
		
		/*//mytokenUtils.generateToken(authUser,"WEB");
		Claims claims = Jwts.claims().setSubject(authUser.getUsername());
        claims.put("userId", authUser.getUsername() + "");
        claims.put("role", authUser.getAuthorities());
		String s=Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "SANJAY")
                .compact();*/
		
		
		  res.getWriter().println("{\"success\": "+s+" }");
	}

}

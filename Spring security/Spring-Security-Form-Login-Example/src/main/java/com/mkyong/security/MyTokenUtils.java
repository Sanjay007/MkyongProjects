package com.mkyong.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;


public class MyTokenUtils {

  private static  final org.slf4j.Logger logger = LoggerFactory.getLogger(MyTokenUtils.class);


  private final String AUDIENCE_UNKNOWN   = "unknown";
  private final String AUDIENCE_WEB       = "web";
  private final String AUDIENCE_MOBILE    = "mobile";
  private final String AUDIENCE_TABLET    = "tablet";

  
  private static String secret="sanjay";


  private static  Long expiration=Long.parseLong("604800");
  
  public static String  generateToken(UserDetails authUser){
	  logger.info("Generating Token for User "+authUser.toString());
	  
	  Map<String, Object> claims = new HashMap<String, Object>();
	    claims.put("sub", authUser.getUsername());
	    claims.put("password", authUser.getPassword());
	    claims.put("role", authUser.getAuthorities().toString());
	    claims.put("created", generateCurrentDate());
	    return generateToken(claims);
	  
  }
  
  private static String generateToken(Map<String, Object> claims) {
	    return Jwts.builder()
	      .setClaims(claims)
	      .setExpiration(generateExpirationDate())
	      .signWith(SignatureAlgorithm.HS384, secret)
	      .compact();
	  }
  
  private  static Date generateCurrentDate() {
	  logger.info("Generating Current Date for Token");
	    return new Date(System.currentTimeMillis());
	  }
  private static Date generateExpirationDate() {
	  logger.info("Generating Exp. Date for Token");
	    return new Date(System.currentTimeMillis() + expiration * 1000);
	  }
}

package com.mkyong.security;

import java.io.IOException;

import javax.management.loading.PrivateClassLoader;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mkyong.security.MyAuthenticationFailureHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
/**
* Authentication filter for REST services
*/
public class RestUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter  {

	
	 private static final String DEFAULT_FILTER_PROCESSES_URL = "/login";
	  private static final String POST = "POST";

	  @Autowired
	  AuthenticationSuccessHandler myAuthenticationSuccessHandler;
	 
	  @Autowired
	  AuthenticationFailureHandler myAuthenticationFailureHandler;
	  
	  
	  private Logger logger=LoggerFactory.getLogger(RestUsernamePasswordAuthenticationFilter.class);
	  
	  public RestUsernamePasswordAuthenticationFilter () {
		  
	    super(DEFAULT_FILTER_PROCESSES_URL);
	    setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
	    setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());
	    logger.debug("Url Processed For Login:");
	    System.out.println("Url Processed For Login inside Constructor :"+DEFAULT_FILTER_PROCESSES_URL);
	  }

	  @Override
	  public Authentication attemptAuthentication(HttpServletRequest request,
	    HttpServletResponse response) throws AuthenticationException,
	    IOException, ServletException {
		  
		  logger.debug("Initiating Login ");
		  if ( !request.getMethod().equals("POST")) {
				throw new AuthenticationServiceException(
						"Authentication method not supported: " + request.getMethod());
			}
		  

			String username = request.getParameter("username");
			String password =  request.getParameter("password");

			if (username == null) {
				username = "";
			}

			if (password == null) {
				password = "";
			}

			username = username.trim();

			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					username, password);

			// Allow subclasses to set the "details" property
			authenticationDetailsSource.buildDetails(request);

			return this.getAuthenticationManager().authenticate(authRequest);
	  }

	  @Override
	  public void doFilter(ServletRequest req, ServletResponse res,
	    FilterChain chain) throws IOException, ServletException {
	    final HttpServletRequest request = (HttpServletRequest) req;
	    final HttpServletResponse response = (HttpServletResponse) res;
	    if(request.getMethod().equals(POST)) {
	    	System.out.println("Url Processed For Login POST:");
	    	// If the incoming request is a POST, then we send it up
	      // to the AbstractAuthenticationProcessingFilter.
	      super.doFilter(request, response, chain);
	      
	    } else {
	    	System.out.println("Url Processed For Login GET:");
	      // If it's a GET, we ignore this request and send it
	      // to the next filter in the chain.  In this case, that
	      // pretty much means the request will hit the /login
	      // controller which will process the request to show the
	      // login page.
	      chain.doFilter(request, response);
	    }
	  }


	
	
	
}

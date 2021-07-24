package com.bijay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.bijay.security.UsersDetails;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	public Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
			UsersDetails userdDetails = (UsersDetails) authentication.getPrincipal();
			List<String> authorities = new ArrayList<String>();
			userdDetails.getAuthorities().iterator().forEachRemaining(c -> authorities.add(c.getAuthority()));
			
			if(authorities.contains("ADMIN")) {
				response.sendRedirect("/admin");
			}else if(authorities.contains("USER")){
				response.sendRedirect("/employee");
			}else {
				response.sendRedirect("/403");
			}
	}
	
}
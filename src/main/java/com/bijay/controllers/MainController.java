package com.bijay.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bijay.models.Users;
import com.bijay.service.AuthService;

@Controller
public class MainController {

	@Autowired BCryptPasswordEncoder passwordenc;
	@Autowired AuthService authservice;
	
	@GetMapping("/403")
	public String get403() {
		if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			return "403";
		}else {
			return "";
		}
	}

	@GetMapping("/login")
	public ModelAndView getLogin() {
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}


	@GetMapping("/signup")
	public ModelAndView getSignup() {
		ModelAndView mav = new ModelAndView("signup");
		return mav;
	}
	
	@PostMapping("/signup")
	public void submittedSignUp(
			HttpServletResponse response,
			@RequestParam("username")String username,
			@RequestParam("email")String email,
			@RequestParam("password")String pwd
			) throws IOException {
		Users user = new Users();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordenc.encode(pwd));
		user.setCredentialsNonExpired(true);
		user.setAccountNonLocked(true);
		user.setAccountNonExpired(true);
		user.setEnabled(false);
		authservice.saveUser(user);
		response.sendRedirect("/login");
	}
	
}

package com.bijay.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bijay.repos.UserRepository;

@Service
public class HRUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email){
		return new UsersDetails(userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("UserDetails not found for email: "+email)));
	}
	
}

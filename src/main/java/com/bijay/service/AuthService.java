package com.bijay.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bijay.models.LeaveTypes;
import com.bijay.models.RoleEnum;
import com.bijay.models.Roles;
import com.bijay.models.Users;
import com.bijay.repos.LeaveTypeRepo;
import com.bijay.repos.RoleRepository;
import com.bijay.repos.UserRepository;

@Service
public class AuthService {
	
	@Autowired UserRepository userRepository;
	@Autowired RoleRepository roleRepository;
	@Autowired LeaveTypeRepo leaveTypeRepo;
	
	public void saveUser(Users user) {
		if(userRepository.findByEmail(user.getEmail()).isEmpty()) {
			user.setRoleSet(roleRepository.findAll().stream().collect(Collectors.toSet()));
			userRepository.save(user);
		}else {
			System.out.println("User Already exists with email: "+user.getEmail());
		}
	}

	public List<Users> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	public void deactivateUserById(UUID id) {
		Users user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
		if(user != null) {
			user.setEnabled(false);
			user.setRoleSet(null);
			userRepository.save(user);
		}
	}
	
	public void activateUserById(UUID id) {
		Users user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
		if(user != null) {
			user.setEnabled(true);
			Roles role = roleRepository.findByRole(RoleEnum.USER).orElseThrow(() -> new RuntimeException("no roles found"));
			user.setRoleSet(Arrays.asList(role).stream().collect(Collectors.toSet()));
			userRepository.save(user);
		}
	}

	public List<LeaveTypes> getAllLeaves() {
		// TODO Auto-generated method stub
		return leaveTypeRepo.findAll();
	}

	public Users findUserById(UUID id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).get();
	}
}

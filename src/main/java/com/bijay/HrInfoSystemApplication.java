package com.bijay;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bijay.models.RoleEnum;
import com.bijay.models.Roles;
import com.bijay.models.Users;
import com.bijay.repos.RoleRepository;
import com.bijay.repos.UserRepository;

@SpringBootApplication
public class HrInfoSystemApplication  implements CommandLineRunner{

	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
	
	public static void main(String[] args) {
		SpringApplication.run(HrInfoSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(userRepo.findAll().isEmpty() && roleRepo.findAll().isEmpty()) {
			Set<Roles> roleset = roleRepo.saveAll(
					Arrays.asList(
							new Roles(RoleEnum.ADMIN),
							new Roles(RoleEnum.USER)
							)
					).stream().collect(Collectors.toSet());
			Users users = userRepo.save(
					new Users(
							"leokingsagarbro@gmail.com",
							passwordEncoder.encode("sagar"),
							"sagar",
							true,
							true,
							true,
							true,
							roleset
							)
					);			
		}
	}

}

package com.bijay.repos;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bijay.models.RoleEnum;
import com.bijay.models.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, UUID>{
	Optional<Roles> findByRole(RoleEnum role);
}

package com.bijay.models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Roles {
	@Id
	@GeneratedValue
	private UUID id;
	private RoleEnum role;
	
	public Roles() {
	}
	
	public Roles(RoleEnum role) {
		super();
		this.role = role;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}
	
	
}

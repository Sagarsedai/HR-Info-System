package com.bijay.models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Teams {
	@Id
	@GeneratedValue
	private UUID id;
	
	private String teamName;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Teams(String teamName) {
		this.teamName = teamName;
	}
	public Teams() {
	}
	
}

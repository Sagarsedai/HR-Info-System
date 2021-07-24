package com.bijay.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LeaveTypes {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String leaveName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLeaveName() {
		return leaveName;
	}

	public void setLeaveName(String leaveName) {
		this.leaveName = leaveName;
	}

	public LeaveTypes(String leaveName) {
		this.leaveName = leaveName;
	}
	public LeaveTypes() {
	}
	
	
}

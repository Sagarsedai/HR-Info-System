package com.bijay.responses;

import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.Transient;
import com.bijay.models.Users;

@Transient
public class PIReturns {
private UUID id;
	
	private String officeContact;
	private String personalContact;
	private String parentName;
	private String permanentAddress;
	private String temporaryAddress;
	private Set<CIReturns> carrerInfos;
	private String healthStatus;
	private Users user;
	
	public PIReturns(UUID id, String officeContact, String personalContact, String parentName, String permanentAddress,
			String temporaryAddress, String healthStatus, Users user) {
		super();
		this.id = id;
		this.officeContact = officeContact;
		this.personalContact = personalContact;
		this.parentName = parentName;
		this.permanentAddress = permanentAddress;
		this.temporaryAddress = temporaryAddress;
		this.healthStatus = healthStatus;
		this.user = user;
	}
	public PIReturns() {
	}
	public Set<CIReturns> getCarrerInfos() {
		return carrerInfos;
	}
	public void setCarrerInfos(Set<CIReturns> carrerInfos) {
		this.carrerInfos = carrerInfos;
	}
	
	
	
	
}

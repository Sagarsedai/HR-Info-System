package com.bijay.models;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class PersonalInfo {

	@Id
	@GeneratedValue
	private UUID id;
	
	private String officeContact;
	private String personalContact;
	private String parentName;
	private String permanentAddress;
	private String temporaryAddress;
	@OneToMany
	private Set<CarrerInfo> carrerInfos;
	private String healthStatus;
	@OneToOne(fetch = FetchType.EAGER)
	private Users user;
	@OneToOne
	private JobInfo jobInfo;
	@OneToMany
	private List<LeaveRequest> leaveRequest;
	
	@OneToMany
	private List<Attendance> attendances;
	
	
	public List<Attendance> getAttendances() {
		return attendances;
	}
	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}
	public List<LeaveRequest> getLeaveRequest() {
		return leaveRequest;
	}
	public void setLeaveRequest(List<LeaveRequest> leaveRequest) {
		this.leaveRequest = leaveRequest;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getOfficeContact() {
		return officeContact;
	}
	public void setOfficeContact(String officeContact) {
		this.officeContact = officeContact;
	}
	public String getPersonalContact() {
		return personalContact;
	}
	public void setPersonalContact(String personalContact) {
		this.personalContact = personalContact;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	public String getTemporaryAddress() {
		return temporaryAddress;
	}
	public void setTemporaryAddress(String temporaryAddress) {
		this.temporaryAddress = temporaryAddress;
	}
	public Set<CarrerInfo> getCarrerInfos() {
		return carrerInfos;
	}
	public void setCarrerInfos(Set<CarrerInfo> carrerInfos) {
		this.carrerInfos = carrerInfos;
	}
	public String getHealthStatus() {
		return healthStatus;
	}
	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
	}

	public JobInfo getJobInfo() {
		return jobInfo;
	}
	public void setJobInfo(JobInfo jobInfo) {
		this.jobInfo = jobInfo;
	}
	public PersonalInfo(UUID id, String officeContact, String personalContact, String parentName,
			String permanentAddress, String temporaryAddress, Set<CarrerInfo> carrerInfos, String healthStatus, JobInfo jobInfo) {
		this.id = id;
		this.officeContact = officeContact;
		this.personalContact = personalContact;
		this.parentName = parentName;
		this.permanentAddress = permanentAddress;
		this.temporaryAddress = temporaryAddress;
		this.carrerInfos = carrerInfos;
		this.healthStatus = healthStatus;
		this.jobInfo = jobInfo;
	}
	public PersonalInfo() {
	}
	
	
}

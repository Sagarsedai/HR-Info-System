package com.bijay.models;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class JobInfo {

	@Id
	@GeneratedValue
	private UUID id;
	
	private String officeName;
	private String branchName;
	private String level;
	private String department;
	private String position;
	
	private Date dateJoined;
	private Date permanentDate;
	private String yearsOfService;
	
	@ManyToMany
	private Set<Teams> teamNames;
	@ManyToMany
	private Set<TransferHistory> transferHistories;
	@OneToOne
	private PersonalInfo personalInfo ;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public Date getDateJoined() {
		return dateJoined;
	}
	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}
	public Date getPermanentDate() {
		return permanentDate;
	}
	public void setPermanentDate(Date permanentDate) {
		this.permanentDate = permanentDate;
	}
	public String getYearsOfService() {
		return yearsOfService;
	}
	public void setYearsOfService(String yearsOfService) {
		this.yearsOfService = yearsOfService;
	}
	public Set<Teams> getTeamNames() {
		return teamNames;
	}
	public void setTeamNames(Set<Teams> teamNames) {
		this.teamNames = teamNames;
	}
	public Set<TransferHistory> getTransferHistories() {
		return transferHistories;
	}
	public void setTransferHistories(Set<TransferHistory> transferHistories) {
		this.transferHistories = transferHistories;
	}
	
	public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}
	public void setPersonalInfo(PersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}
	public JobInfo(String officeName, String branchName, String level, String department, String position,
			Date dateJoined, Date permanentDate, String yearsOfService, Set<Teams> teamNames,
			Set<TransferHistory> transferHistories, PersonalInfo personalInfo) {
		super();
		this.officeName = officeName;
		this.branchName = branchName;
		this.level = level;
		this.department = department;
		this.position = position;
		this.dateJoined = dateJoined;
		this.permanentDate = permanentDate;
		this.yearsOfService = yearsOfService;
		this.teamNames = teamNames;
		this.transferHistories = transferHistories;
		this.personalInfo = personalInfo;
	}
	
	public JobInfo() {
	}
	
	
}

package com.bijay.models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class LeaveRequest {

	@Id
	@GeneratedValue
	private UUID id;
	
	private boolean isApproved;
	private Date appliedDate;
	private Date approvedDate;
	private Date lastDate;
	@OneToOne
	private LeaveTypes leaveTypes;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	public Date getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	public LeaveTypes getLeaveTypes() {
		return leaveTypes;
	}
	public void setLeaveTypes(LeaveTypes leaveTypes) {
		this.leaveTypes = leaveTypes;
	}
	public LeaveRequest(boolean isApproved, Date appliedDate, Date approvedDate, Date lastDate,
			LeaveTypes leaveTypes) {
		this.isApproved = isApproved;
		this.appliedDate = appliedDate;
		this.approvedDate = approvedDate;
		this.lastDate = lastDate;
		this.leaveTypes = leaveTypes;
	}
	public LeaveRequest() {
	}
	@Override
	public String toString() {
		return "LeaveRequest [id=" + id + ", isApproved=" + isApproved + ", appliedDate=" + appliedDate
				+ ", approvedDate=" + approvedDate + ", lastDate=" + lastDate + ", leaveTypes=" + leaveTypes + "]";
	}
	
	
}

package com.bijay.models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Attendance {

	@Id
	@GeneratedValue
	private UUID id;
	
	private Date clockIn;
	private Date clockOut;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public Date getClockIn() {
		return clockIn;
	}
	public void setClockIn(Date clockIn) {
		this.clockIn = clockIn;
	}
	public Date getClockOut() {
		return clockOut;
	}
	public void setClockOut(Date clockOut) {
		this.clockOut = clockOut;
	}
	public Attendance(Date clockIn, Date clockOut) {
		this.clockIn = clockIn;
		this.clockOut = clockOut;
	}
	public Attendance() {
		// TODO Auto-generated constructor stub
	}
	
}

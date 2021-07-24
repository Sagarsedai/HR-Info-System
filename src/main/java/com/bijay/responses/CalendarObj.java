package com.bijay.responses;


public class CalendarObj {

	private String year,day,month;
	private boolean isPresent;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public boolean isPresent() {
		return isPresent;
	}
	public void setPresent(boolean isPresent) {
		this.isPresent = isPresent;
	}
	public CalendarObj(String year, String day, String month, boolean isPresent) {
		super();
		this.year = year;
		this.day = day;
		this.month = month;
		this.isPresent = isPresent;
	}
	public CalendarObj() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CalendarObj [year=" + year + ", day=" + day + ", month=" + month + ", isPresent=" + isPresent + "]";
	}
	
	
	
}

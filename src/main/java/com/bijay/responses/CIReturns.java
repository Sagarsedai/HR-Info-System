package com.bijay.responses;

import java.util.UUID;

import org.springframework.security.core.Transient;

@Transient
public class CIReturns {
	private UUID id;
	private String file;
	private String name;
	private String details;
	private String fileType;
	public CIReturns(UUID id, String file, String name, String details, String fileType) {
		this.id = id;
		this.file = file;
		this.name = name;
		this.details = details;
		this.fileType = fileType;
	}
	public CIReturns() {
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	
	
}

package com.bijay.models;

import java.sql.Blob;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CarrerInfo {

	@Id
	@GeneratedValue
	private UUID id;
	
	private byte[] file;
	private String name;
	private String details;
	private String fileType;
	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
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
	public CarrerInfo(byte[] bs, String name, String details , String fileType) {
		this.file = bs;
		this.name = name;
		this.details = details;
		this.fileType = fileType;
	}
	
	public CarrerInfo() {
	}
	
}

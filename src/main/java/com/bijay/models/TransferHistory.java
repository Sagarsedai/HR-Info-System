package com.bijay.models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TransferHistory {
	@Id
	@GeneratedValue
	private UUID id;
	
	private String info;
	private String promotionName;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getPromotionName() {
		return promotionName;
	}
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}
	public TransferHistory(String info, String promotionName) {
		super();
		this.info = info;
		this.promotionName = promotionName;
	}
	public TransferHistory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

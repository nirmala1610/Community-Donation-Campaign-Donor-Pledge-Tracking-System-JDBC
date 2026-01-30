package com.donate.bean;

public class Campaign {

	private String campaignID;
	private String campaignName;
	private java.sql.Date startDate;
	private java.sql.Date endDate;
	private java.math.BigDecimal targetAmount;
	private String status ;
	public String getCampaignID() {
		return campaignID;
	}
	public void setCampaignID(String campaignID) {
		this.campaignID = campaignID;
	}
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public java.sql.Date getStartDate() {
		return startDate;
	}
	public void setStartDate(java.sql.Date startDate) {
		this.startDate = startDate;
	}
	public java.sql.Date getEndDate() {
		return endDate;
	}
	public void setEndDate(java.sql.Date endDate) {
		this.endDate = endDate;
	}
	public java.math.BigDecimal getTargetAmount() {
		return targetAmount;
	}
	public void setTargetAmount(java.math.BigDecimal targetAmount) {
		this.targetAmount = targetAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}

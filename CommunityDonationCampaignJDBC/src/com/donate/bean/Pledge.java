package com.donate.bean;

public class Pledge {

	private int pledgeID;
	private String donorID ;
	private String campaignID ;
	private java.sql.Date pledgeDate;
	private java.math.BigDecimal pledgeAmount;
	private java.math.BigDecimal amountPaid;
	private String paymentStatus;
	private String writeoffFlag;
	public int getPledgeID() {
		return pledgeID;
	}
	public void setPledgeID(int pledgeID) {
		this.pledgeID = pledgeID;
	}
	public String getDonorID() {
		return donorID;
	}
	public void setDonorID(String donorID) {
		this.donorID = donorID;
	}
	public String getCampaignID() {
		return campaignID;
	}
	public void setCampaignID(String campaignID) {
		this.campaignID = campaignID;
	}
	public java.sql.Date getPledgeDate() {
		return pledgeDate;
	}
	public void setPledgeDate(java.sql.Date pledgeDate) {
		this.pledgeDate = pledgeDate;
	}
	public java.math.BigDecimal getPledgeAmount() {
		return pledgeAmount;
	}
	public void setPledgeAmount(java.math.BigDecimal pledgeAmount) {
		this.pledgeAmount = pledgeAmount;
	}
	public java.math.BigDecimal getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(java.math.BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getWriteoffFlag() {
		return writeoffFlag;
	}
	public void setWriteoffFlag(String writeoffFlag) {
		this.writeoffFlag = writeoffFlag;
	}
	
}

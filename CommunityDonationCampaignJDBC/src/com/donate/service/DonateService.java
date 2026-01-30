package com.donate.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.donate.bean.Campaign;
import com.donate.bean.Donor;
import com.donate.bean.Pledge;
import com.donate.dao.CampaignDAO;
import com.donate.dao.DonorDAO;
import com.donate.dao.PledgeDAO;
import com.donate.util.ActivePledgesExistException;
import com.donate.util.CampaignClosedException;
import com.donate.util.DBUtil;
import com.donate.util.ValidationException;

public class DonateService {

	public Donor viewDonorDetails(String donorID) {
		if(donorID!=null) {
			DonorDAO donerDao = new DonorDAO();
			return donerDao.findDonor(donorID);	
		}
		return null;
	}
	
	public java.util.List<Donor> viewAllDonors(){
		DonorDAO donerDao = new DonorDAO();
		return donerDao.viewAllDoners();
	}
	
	public boolean registerNewDonor(Donor donor) {
		
		if (donor == null 
			    || donor.getDonorID() == null || donor.getDonorID().trim().isEmpty()
			    || donor.getFullName() == null || donor.getFullName().trim().isEmpty()
			    || donor.getMobile() == null || donor.getMobile().trim().isEmpty()) {
			    return false;
	    }
		DonorDAO donerDao = new DonorDAO();
		
		String email = donor.getEmail();
	    if (email != null && !email.trim().isEmpty()) {
	        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	        if (!email.matches(emailRegex)) {
	            return false; 
	        }
	    }

	    Donor existingDonor = donerDao.findDonor(donor.getDonorID());
	    if (existingDonor != null) {
	        return false;
	    }
	    donor.setStatus("ACTIVE");

	    return donerDao.insertDonor(donor);

	}
	
	public Campaign viewCampaignDetails(String campaignID) {
		if(campaignID!=null) {
			CampaignDAO campaignDao = new CampaignDAO();
			return campaignDao.findCampaign(campaignID);	
		}
		return null;
	}
	
	public java.util.List<Campaign> viewAllCampaigns(){
		CampaignDAO campaignDao = new CampaignDAO();
		return campaignDao.viewAllCampaigns();
	}
	
	public boolean createCampaign(Campaign campaign) {
		if (campaign == null 
		        || campaign.getCampaignID() == null || campaign.getCampaignID().isEmpty()
		        || campaign.getCampaignName() == null || campaign.getCampaignName().isEmpty()
		        || campaign.getStartDate() == null 
		        || campaign.getEndDate() == null 
		        || campaign.getTargetAmount().compareTo(BigDecimal.ZERO) < 0) {
		        return false;
		    }
		if(campaign.getStartDate().after(campaign.getEndDate())) {
			return false;
		}
		CampaignDAO campaignDao = new CampaignDAO();
		Campaign uniqueID=campaignDao.findCampaign(campaign.getCampaignID());
		if(uniqueID!=null) {
			return false;
		}
		if(campaign.getStatus()==null || (!campaign.getStatus().equals("PLANNED") 
				&& !campaign.getStatus().equals("ACTIVE"))) {
			campaign.setStatus("PLANNED");
		}
		
		campaignDao.insertCampaign(campaign);
		return true;
	}
	
	public boolean recordPledge(String donorID,String campaignID,java.sql.Date pledgeDate,java.math.BigDecimal pledgeAmount) {
		
		try {
			if(donorID!=null && campaignID!=null 
			   && pledgeDate!=null && pledgeAmount.compareTo(BigDecimal.ZERO)>0) {
				DonorDAO donorDao= new DonorDAO();
				Donor donor = donorDao.findDonor(donorID);
				CampaignDAO campaignDAO = new CampaignDAO();
				Campaign campaign=campaignDAO.findCampaign(campaignID);
				if(donor==null || campaign==null || donor.getStatus().equals("INACTIVE")) {
					return false;
				}
					if(campaign.getStatus().equals("ACTIVE")) {
						try {
							Connection connection=DBUtil.getDBConnection();
							connection.setAutoCommit(false);
							
							PledgeDAO pledgeDAO=new PledgeDAO();
							int pledgeID=pledgeDAO.generatePledgeID();
							
							Pledge pledge=new Pledge();
							pledge.setPledgeID(pledgeID);
							pledge.setDonorID(donorID);
							pledge.setCampaignID(campaignID);
							pledge.setPledgeDate(pledgeDate);
							pledge.setPledgeAmount(pledgeAmount);
							pledge.setAmountPaid(BigDecimal.ZERO);
							pledge.setPaymentStatus("NOT_PAID");
							pledge.setWriteoffFlag("NO");
							
							if(pledgeDAO.insertPledge(pledge)) {
								connection.commit();
								return true;
							}else {
								connection.rollback();
								return false;
							}
						}catch (SQLException e) {
							e.printStackTrace();
						}
					}else{
						throw new CampaignClosedException();	
					}
			    }throw new ValidationException();
			}catch(CampaignClosedException | ValidationException e) {
					System.out.println(e.getMessage());
				}

		return false;
	}
	
	public boolean recordPayment(int pledgeID,java.math.BigDecimal paymentAmount) {
		try {
		if(pledgeID >0 &&  paymentAmount.compareTo(BigDecimal.ZERO)>0 ) {
			PledgeDAO pledgeDAO=new PledgeDAO();
			Pledge pledge= pledgeDAO.findPledge(pledgeID);
			if(pledge==null) {
				return false;
			}
			BigDecimal newAmountPaid = pledge.getAmountPaid().add(paymentAmount);
			int value = pledge.getPledgeAmount().compareTo(newAmountPaid);
			if(value == -1) {
				throw new ValidationException();
			}
			DonorDAO donorDAO=new DonorDAO();
			if(value == 0) {
				donorDAO.updateDonerStatus(pledge.getDonorID(), "FULLY_PAID");
			}
			if(newAmountPaid.compareTo(BigDecimal.ZERO)>0 && value==1) {
				donorDAO.updateDonerStatus(pledge.getDonorID(), "PARTIALLY_PAID");
			}
			if(newAmountPaid.compareTo(BigDecimal.ZERO)==0) {
				donorDAO.updateDonerStatus(pledge.getDonorID(), "NOT_PAID");
			}
				Connection connection = DBUtil.getDBConnection();
				try {
					connection.setAutoCommit(false);
				
				if(pledgeDAO.updatePledgePayment(pledgeID, newAmountPaid, pledge.getPaymentStatus())) {
					connection.commit();	
					return true;
				}else {
					connection.rollback();
					return false;
				}
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        
		}throw new CampaignClosedException();
		}catch(ValidationException | CampaignClosedException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public java.util.List<Pledge> listPledgesByDonor(String donorID){
		PledgeDAO pledgeDAO=new PledgeDAO();
		return pledgeDAO.findPledgesByDonor(donorID);
	}
	
	public java.util.List<Pledge> listPledgesByCampaign(String campaignID){
		PledgeDAO pledgeDAO=new PledgeDAO();
		return pledgeDAO.findPledgesByCampaign(campaignID);
	}
	
	public boolean closeCampaign(String campaignID) {
		try {
			if(!campaignID.isEmpty()) {
				CampaignDAO campaignDAO=new CampaignDAO();
				Campaign campaign = campaignDAO.findCampaign(campaignID);
				if(campaign == null || campaign.getStatus().equals("CLOSED")) {
					return false;
				}
				PledgeDAO pledgeDAO = new PledgeDAO();
				List<Pledge> pledge = pledgeDAO.findActivePledgesForCampaign(campaignID);
				for (Pledge p : pledge) {
			        String status = p.getPaymentStatus();
			        String writeoffFlag = p.getWriteoffFlag();

				  if(("NOT_PAID".equals(status) || "PARTIALLY_PAID".equals(status)) 
			                && "NO".equals(writeoffFlag)) {
					throw new ActivePledgesExistException();
				   }
				}
					campaignDAO.updateCampaignStatus(campaignID, "CLOSED");
					return true;
				
			}else {
				throw new ValidationException();
			}
		}catch(ValidationException | ActivePledgesExistException e) {
			System.out.println(e.getMessage());
		}
		return false;		
	}
	
	public boolean removeDonor(String donorID) {
		try {
			if(!donorID.isEmpty()) {
				PledgeDAO pledgeDAO=new PledgeDAO();
				List<Pledge> pledge= pledgeDAO.findActivePledgesForDonor(donorID);
				if(pledge.isEmpty()) {
					DonorDAO donorDAO=new DonorDAO();
					donorDAO.deleteDoner(donorID);
					return true;
				}else {
					throw new ActivePledgesExistException();
				}
			}else {
				throw new ValidationException();
			}
		}catch (ValidationException | ActivePledgesExistException e) {
			System.out.print(e.getMessage());
		}
		return false;
	}
	
}

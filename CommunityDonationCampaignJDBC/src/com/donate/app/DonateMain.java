package com.donate.app;

import com.donate.bean.Campaign;
import com.donate.bean.Donor;
import com.donate.service.DonateService;

public class DonateMain { 
       public static void main(String[] args) { 

    	    DonateService donateService = new DonateService();
			System.out.println("--- Community Donation & Pledge Tracking Console ---"); 
			// DEMO 1: Register a new donor 
			try { 
				Donor d = new Donor(); 
				d.setDonorID("DNR3001"); 
				d.setFullName("Luffy"); 
				d.setEmail("luffystrawhats@example.org"); 
				d.setMobile("9876599123"); 
				d.setCity("Tokyo"); 
				d.setStatus("ACTIVE"); 
				
				boolean ok = donateService.registerNewDonor(d); 
				System.out.println(ok ? "DONOR REGISTERED" : "DONOR REGISTRATION FAILED"); 
			} catch (Exception e) { 
			System.out.println("System Error: " + e.getMessage()); 
			} 
			// DEMO 2: Create a new campaign 
			try { 
				Campaign c = new Campaign(); 
				c.setCampaignID("CMP2026C"); 
				c.setCampaignName("Relief Fund 2025"); 
				c.setStartDate(new java.sql.Date(System.currentTimeMillis())); 
				c.setEndDate(new java.sql.Date(System.currentTimeMillis() + 30L*24*60*60*1000)); 
				c.setTargetAmount(new java.math.BigDecimal("150000.00")); 
				c.setStatus("ACTIVE"); 
				boolean ok = donateService.createCampaign(c); 
				System.out.println(ok ? "CAMPAIGN CREATED" : 
				"CAMPAIGN CREATION FAILED"); 
			} catch (Exception e) { 
			System.out.println("System Error: " + e.getMessage()); 
			} 
			// DEMO 3: Record a pledge 
			try { 
				java.sql.Date pledgeDate = new java.sql.Date(System.currentTimeMillis()); 
				boolean ok = donateService.recordPledge("DNR3001","CMP2026C", pledgeDate, new java.math.BigDecimal("3000.00")); 
				System.out.println(ok ? "PLEDGE RECORDED" : "PLEDGE FAILED"); 
			} catch (Exception e) { 
			System.out.println("System Error: " + e.getMessage()); 
            } 
     } 
}
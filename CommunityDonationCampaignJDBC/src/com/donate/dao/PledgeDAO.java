package com.donate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.donate.bean.Donor;
import com.donate.bean.Pledge;
import com.donate.util.DBUtil;


public class PledgeDAO {

	public int generatePledgeID() {
		Connection connection = DBUtil.getDBConnection();
		String query = "SELECT pledgeId_seql.NEXTVAL from dual";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int seqNumber = rs.getInt(1);
			return seqNumber;
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public boolean insertPledge(Pledge pledge) {
		Connection connection = DBUtil.getDBConnection();
		String query = "INSERT INTO PLEDGE_TBL VALUES (?,?,?,?,?,?,?,?)";
			try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1,pledge.getPledgeID());
			ps.setString(2, pledge.getDonorID());
			ps.setString(3,pledge.getCampaignID());
			ps.setDate(4,pledge.getPledgeDate());
			ps.setBigDecimal(5, pledge.getPledgeAmount());
			ps.setBigDecimal(6,pledge.getAmountPaid());
			ps.setString(7, pledge.getPaymentStatus());
			ps.setString(8, pledge.getWriteoffFlag());
	
		    ps.execute();
		    return true;			
			}catch(SQLException e){
				e.printStackTrace();
				return false;
		   }
	}
	
	public boolean updatePledgePayment(int pledgeID,java.math.BigDecimal newAmountPaid,String newPaymentStatus) {
		Connection connection = DBUtil.getDBConnection();
		String query = "UPDATE PLEDGE_TBL SET AmountPaid=?,PaymentStatus=? WHERE PledgeID=?";
			try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setBigDecimal(1, newAmountPaid);
			ps.setString(2, newPaymentStatus);
			ps.setInt(3, pledgeID);
			ps.executeUpdate();
			return true;
			}catch(SQLException e){
				e.printStackTrace();
				return false;
		   }
			
	}
	
	public Pledge findPledge(int pledgeID) {
		Connection connection = DBUtil.getDBConnection();
		Pledge pledge=new Pledge();
		String query = "SELECT * FROM PLEDGE_TBL WHERE PledgeID=?";
			try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, pledgeID);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				pledge.setPledgeID(rs.getInt("PledgeID"));
				pledge.setDonorID(rs.getString("DonorID"));
				pledge.setCampaignID(rs.getString("CampaignID"));
				pledge.setPledgeDate(rs.getDate("PledgeDate"));
				pledge.setPledgeAmount(rs.getBigDecimal("PledgeAmount"));
				pledge.setPaymentStatus(rs.getString("PaymentStatus"));
				pledge.setWriteoffFlag(rs.getString("WriteoffFlag"));
				
			}
			}catch(SQLException e){
				e.printStackTrace();
				return null;
		   }
	return pledge;
	}
	
	public java.util.List<Pledge> findPledgesByDonor(String donorID){
		List<Pledge> allPledge = new ArrayList<>();
		
		Connection connection = DBUtil.getDBConnection();
		String query = "SELECT * FROM PLEDGE_TBL WHERE DonorID=?";
			try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, donorID);
			ResultSet rs = ps.executeQuery();
			
		while(rs.next()) {
			Pledge pledge = new Pledge();
			
			pledge.setPledgeID(rs.getInt("PledgeID"));
			pledge.setDonorID(rs.getString("DonorID"));
			pledge.setCampaignID(rs.getString("CampaignID"));
			pledge.setPledgeDate(rs.getDate("PledgeDate"));;
			pledge.setPledgeAmount(rs.getBigDecimal("PledgeAmount"));
			pledge.setAmountPaid(rs.getBigDecimal("AmountPaid"));
			pledge.setPaymentStatus(rs.getString("PaymentStatus"));
			pledge.setWriteoffFlag(rs.getString("WriteoffFlag"));
			
			allPledge.add(pledge);		
		}
			}catch(SQLException e){
				e.printStackTrace();
				return null;
		   }
		return allPledge;
	}
	
	public java.util.List<Pledge> findPledgesByCampaign(String campaignID){
		List<Pledge> allPledge = new ArrayList<>();
		
		Connection connection = DBUtil.getDBConnection();
		String query = "SELECT * FROM PLEDGE_TBL WHERE CampaignID=?";
			try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, campaignID);
			ResultSet rs = ps.executeQuery();
			
		   while(rs.next()) {
			Pledge pledge = new Pledge();
			
			pledge.setPledgeID(rs.getInt("PledgeID"));
			pledge.setDonorID(rs.getString("DonorID"));
			pledge.setCampaignID(rs.getString("CampaignID"));
			pledge.setPledgeDate(rs.getDate("PledgeDate"));;
			pledge.setPledgeAmount(rs.getBigDecimal("PledgeAmount"));
			pledge.setAmountPaid(rs.getBigDecimal("PledgeAmount"));
			pledge.setPaymentStatus(rs.getString("PaymentStatus"));
			pledge.setWriteoffFlag(rs.getString("WriteoffFlag"));
			
			allPledge.add(pledge);		
		}
			}catch(SQLException e){
				e.printStackTrace();
				return null;
		   }
		return allPledge;
	}
	
	public java.util.List<Pledge> findActivePledgesForDonor(String donorID){
		List<Pledge> allPledge = new ArrayList<>();
		
		Connection connection = DBUtil.getDBConnection();
		String query = "SELECT * FROM PLEDGE_TBL WHERE DonorID=? "
				+ "AND (PaymentStatus=? OR PaymentStatus=?)"
				+ "AND WriteoffFlag=?";
		
			try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1,donorID);
			ps.setString(2,"NOT_PAID");
			ps.setString(3,"PARTIALLY_PAID");
			ps.setString(4,"NO");
			ResultSet rs = ps.executeQuery();
		   while(rs.next()) {
			Pledge pledge = new Pledge();
			
			pledge.setPledgeID(rs.getInt("PledgeID"));
			pledge.setDonorID(rs.getString("DonorID"));
			pledge.setCampaignID(rs.getString("CampaignID"));
			pledge.setPledgeDate(rs.getDate("PledgeDate"));;
			pledge.setPledgeAmount(rs.getBigDecimal("PledgeAmount"));
			pledge.setAmountPaid(rs.getBigDecimal("PledgeAmount"));
			pledge.setPaymentStatus(rs.getString("PaymentStatus"));
			pledge.setWriteoffFlag(rs.getString("WriteoffFlag"));
			
			allPledge.add(pledge);		
		}
			}catch(SQLException e){
				e.printStackTrace();
				return null;
		   }
		return allPledge;
	}
	
	public java.util.List<Pledge> findActivePledgesForCampaign(String campaignID){
		List<Pledge> allPledge = new ArrayList<>();
		
		Connection connection = DBUtil.getDBConnection();
		String query = "SELECT * FROM PLEDGE_TBL WHERE CampaignID=? "
				+ "AND (PaymentStatus=? OR PaymentStatus=?)"
				+ "AND WriteoffFlag=?";
		
			try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1,campaignID);
			ps.setString(2,"NOT_PAID");
			ps.setString(3,"PARTIALLY_PAID");
			ps.setString(4,"NO");
			ResultSet rs = ps.executeQuery();
		    while(rs.next()) {
			Pledge pledge = new Pledge();
			
			pledge.setPledgeID(rs.getInt("PledgeID"));
			pledge.setDonorID(rs.getString("DonorID"));
			pledge.setCampaignID(rs.getString("CampaignID"));
			pledge.setPledgeDate(rs.getDate("PledgeDate"));;
			pledge.setPledgeAmount(rs.getBigDecimal("PledgeAmount"));
			pledge.setAmountPaid(rs.getBigDecimal("PledgeAmount"));
			pledge.setPaymentStatus(rs.getString("PaymentStatus"));
			pledge.setWriteoffFlag(rs.getString("WriteoffFlag"));
			
			allPledge.add(pledge);		
		}
			}catch(SQLException e){
				e.printStackTrace();
				return null;
		   }
		return allPledge;
	}
}

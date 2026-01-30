package com.donate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.donate.bean.Campaign;
import com.donate.util.DBUtil;

public class CampaignDAO {

	public Campaign findCampaign(String campaignID) {
		Campaign campaign = new Campaign();
		Connection connection = DBUtil.getDBConnection();
		String query = "SELECT * FROM CAMPAIGN_TBL WHERE CampaignID=?";
			try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1,campaignID);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				campaign.setCampaignID(rs.getString("CampaignID"));
				campaign.setCampaignName(rs.getString("CampaignName"));
				campaign.setStartDate(rs.getDate("StartDate"));
				campaign.setEndDate(rs.getDate("EndDate"));
				campaign.setTargetAmount(rs.getBigDecimal("TargetAmount"));
				campaign.setStatus(rs.getString("Status"));	
			}
			}catch(SQLException e){
				e.printStackTrace();
				return null;
		   }
			
			return null;
	}
	
	public java.util.List<Campaign> viewAllCampaigns(){
        List<Campaign> allCampaign = new ArrayList<>();
		
		Connection connection = DBUtil.getDBConnection();
		String query = "SELECT * FROM CAMPAIGN_TBL";
			try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Campaign campaign = new Campaign();
			campaign.setCampaignID(rs.getString("CampaignID"));
			campaign.setCampaignName(rs.getString("CampaignName"));
			campaign.setStartDate(rs.getDate("StartDate"));
			campaign.setEndDate(rs.getDate("EndDate"));
			campaign.setTargetAmount(rs.getBigDecimal("TargetAmount"));
			campaign.setStatus(rs.getString("Status"));
			
			allCampaign.add(campaign);		
		}
		
			}catch(SQLException e){
				e.printStackTrace();
				return null;
		   }
		
		return allCampaign;
	}
	
	public boolean insertCampaign(Campaign campaign) {
		Connection connection = DBUtil.getDBConnection();
		String query = "INSERT INTO CAMPAIGN_TBL VALUES (?,?,?,?,?,?)";
			try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1,campaign.getCampaignID());
			ps.setString(2, campaign.getCampaignName());
			ps.setDate(3, campaign.getStartDate());
			ps.setDate(4,campaign.getEndDate());
			ps.setBigDecimal(5,campaign.getTargetAmount());
			ps.setString(6,campaign.getStatus());
	
		    ps.executeUpdate();
		    return true;			
			}catch(SQLException e){
				e.printStackTrace();
				return false;
		   }
	}
	
	public boolean updateCampaignStatus(String campaignID,String status) {
		Connection connection = DBUtil.getDBConnection();
		String query = "UPDATE CAMPAIGN_TBL SET Status=? WHERE CampaignID=?";
			try {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, status);
				ps.setString(2, campaignID);
				ps.executeUpdate();
				
				return true;	
			}catch(SQLException e){
				e.printStackTrace();
				return false;
		   }
	}
	
}

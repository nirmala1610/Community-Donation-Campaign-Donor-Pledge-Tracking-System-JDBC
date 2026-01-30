package com.donate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.donate.bean.Donor;
import com.donate.util.DBUtil;

public class DonorDAO {

	public Donor findDonor(String donorID) {
		Donor doner = new Donor();
		Connection connection = DBUtil.getDBConnection();
		String query = "SELECT * FROM DONOR_TBL WHERE DonorID=?";
			try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1,donorID);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				doner.setDonorID(rs.getString("DonorID"));
				doner.setFullName(rs.getString("FullName"));
				doner.setEmail(rs.getString("Email"));
				doner.setMobile(rs.getString("Mobile"));
				doner.setCity(rs.getString("City"));
				doner.setStatus(rs.getString("Status"));	
			}
			}catch(SQLException e){
				e.printStackTrace();
				return null;
		   }
			return null;
	}
	public java.util.List<Donor> viewAllDoners(){
		List<Donor> allDonors = new ArrayList<>();
		
		Connection connection = DBUtil.getDBConnection();
		String query = "SELECT * FROM DONOR_TBL";
			try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Donor doner = new Donor();
			doner.setDonorID(rs.getString("DonorID"));
			doner.setFullName(rs.getString("FullName"));
			doner.setEmail(rs.getString("Email"));
			doner.setMobile(rs.getString("Mobile"));
			doner.setCity(rs.getString("City"));
			doner.setStatus(rs.getString("Status"));
			
			allDonors.add(doner);		
		}
		
			}catch(SQLException e){
				e.printStackTrace();
				return null;
		   }
		
		return allDonors;
	}
	
	public boolean insertDonor(Donor doner) {
		Connection connection = DBUtil.getDBConnection();
		String query = "INSERT INTO DONOR_TBL VALUES (?,?,?,?,?,?)";
			try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1,doner.getDonorID());
			ps.setString(2, doner.getFullName());
			ps.setString(3, doner.getEmail());
			ps.setString(4, doner.getMobile());
			ps.setString(5, doner.getCity());
			ps.setString(6,doner.getStatus());
	
		    ps.execute();
		    return true;			
			}catch(SQLException e){
				e.printStackTrace();
				return false;
		   }		
	}
	
	public boolean updateDonerStatus(String donerID,String status) {
		Connection connection = DBUtil.getDBConnection();
		String query = "UPDATE DONOR_TBL SET Status=? WHERE DonorID=?";
			try {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, status);
				ps.setString(2, donerID);
				ps.executeUpdate();
				
				return true;	
			}catch(SQLException e){
				e.printStackTrace();
				return false;
		   }		
	}
	
	public boolean deleteDoner(String donerID) {
		Connection connection = DBUtil.getDBConnection();
		String query = "DELETE FROM DONOR_TBL WHERE DonorID=?";
			try {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, donerID);
				ps.execute();
				
				return true;	
			}catch(SQLException e){
				e.printStackTrace();
				return false;
		   }
	}
}

package com.donate.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	public static java.sql.Connection getDBConnection(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}	
		
		String url = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
		String user = "donate_user";
		String password = "levi1610";
		
		try {
		Connection connection = DriverManager.getConnection(url,user,password);
		return connection;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}

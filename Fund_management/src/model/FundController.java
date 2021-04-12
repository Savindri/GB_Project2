package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

public class FundController {
	
	/* Database connection */
	private static String url = "127.0.0.1:3306/gadetbadget";
	private static String username = "root";
	private static String password = "";
	
	//Creating Database Connection
	private static Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://" + url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	
	 public String insertFund(int fundid , int cusid , String fund_desc , String fundamt, String requestDate, String status) {
			
			String output = "";
			
			try{ 
				
		     Connection con = connect();
			
			if (con == null) {
				return"Error while connecting to the database for inserting."; 
				} 
			
			  String query = "insert into Fund (FundID,CustomerID,Fund_desc,Fund_amount, Request_date,Fund_status, FundGrant_date)"
				  		+ " values(?,?,?,?,?,?,?)";
			// create a prepared statement
				PreparedStatement preparedStmt = con.prepareStatement(query); 
				
		
			//converting to simple data format	
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			java.util.Date udob = null;
			
			 try {
				 
				udob = sdf.parse(requestDate);						
				long ms =udob.getTime();						
				java.sql.Date sqdob = new java.sql.Date(ms);
			
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setInt(2, cusid);
			preparedStmt.setString(3, fund_desc);
			preparedStmt.setDouble(4, Double.parseDouble(fundamt)); 
			preparedStmt.setDate(5, sqdob);
			preparedStmt.setString(6,status);
			preparedStmt.setInt(7, 0); 
			
			// execute the statement
			preparedStmt.execute(); 
			con.close();
			output = "Fund ID = '"+fundid+"' - Details inserted successfully";
			
			 }catch(Exception e) {
				 			 
			 }
			} catch (Exception e) {
				output = "Error while inserting the item."; 
				System.err.println(e.getMessage()); 
				
			} 
				 return output; 
				
			
		} 
		
	   
	   
		
		//Deleting the fund details
		public static String deleteFund(int fundid) {
			String output = "";
			try {
				  Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for deleting.";
				}
				// create a prepared statement
				String query = "delete from `Fund` where `FundID` = ?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, fundid);
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Data deleted successfully";
			} catch (Exception e) {
				output = "Error while deleting the Data...";
				System.err.println(e.getMessage());
			}
			return output;
		}



	

}

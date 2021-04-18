package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import util.DBConnect;

public class FundController {
	
	
	 public String insertFund( String Fund_announcement , String Fund_duration , String instructions , String amount) {
			
			String output = "";
			Double FundAmt = Double.parseDouble(amount);
		
			
			//checking the null value insertion
			if(Fund_announcement.equals("")) {
				
				 return "You need to enter a Description";
				 
			}else if(Fund_duration.equals("")) {
				
				return "You need to enter Duration";
				
			}else if(instructions.equals("")) {
				
				return "You need to enter instructions";
				
			}else if(FundAmt.equals(null)) {
				
				return "You need to enter an amount";				
			}
			
			
			try{ 
				
		     Connection con = DBConnect.connect();
			
			if (con == null) {
				return"Error while connecting to the database for inserting."; 
				} 
			
			  String query = "insert into fund (FundID,ProjectID,Fund_Announcment,F_Duration,A_Instructions,Fund_amount)"
				  		+ " values(?,?,?,?,?,?)";
			// create a prepared statement
			PreparedStatement preparedStmt = con.prepareStatement(query); 
				
//				
//			// Get the Project ID of the Fund from Project Micro Service
//			Client client = new Client(); //Creating a
//			//Created webresource to access the specified URL
//			WebResource resource = client.resource("http://localhost:8090/TestFund/FundServices/Items");
//			//Capturing the returning value
//			String response = resource.type(MediaType.TEXT_PLAIN_TYPE).get(String.class);
//
//							
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, Fund_duration);
			preparedStmt.setString(3, Fund_announcement);
			preparedStmt.setString(4, (Fund_duration));
			preparedStmt.setString(5, (instructions));
			preparedStmt.setDouble(6, (FundAmt));
			
			
			// execute the statement
			preparedStmt.execute(); 
			
//			//retrieving the fund id from the fund Table
//			String queryslt = "SELECT FundID FROM  fund WHERE ProjectID = ? ORDER BY FundID DESC LIMIT 1"; 
//			//creating the prepared statement to execute the query
//			PreparedStatement preparedStmt2 = con.prepareStatement(queryslt);	
//			preparedStmt2.setString(1, response);
//			
//			// execute the statement
//			ResultSet resultSet = preparedStmt2.executeQuery();
//
//			//retriving the Fundid to a variable
//			if (resultSet.next()) {
//				 FundIDs = resultSet.getInt(1);
//			} else {
//				return "Error whiling loading data..";
//						}
			con.close();
			
			output = " ============= Details reguarding ID :'"+FundAmt+"' inserted successfully =============";
			
			 }catch(Exception e) {
				 e.printStackTrace();				 			 
			 }
			
				 return output; 		
		} 
		
	   
	 public String updateItem(int fundid , String Fund_announcement , String Fund_duration,String instructions)
	 { 
	 	String output = "";
	 	
	 	//Checking for the null values
	 	if(fundid == 0 ) {
	 		
	 		return "Field ProjectID cannot be 0";	
	 		
	 	}else if( Fund_announcement.equals("") ) {
	 		
	 		return "Announcement cannot be empty";
	 		
	 	}else if(Fund_duration.equals("") ) {
	 		
	 		return "Fund_duration cannot be empty";
	 		
	 	}else if(instructions.equals("") ) {
	 		
	 		return "instructions cannot be empty";
	 	}
	 	
	 	 	
	 	try{ 
	 		//Testing database connection
	 		 Connection con = DBConnect.connect();
	 	
	 	if (con == null) {
	 		
	 		return"Error while connecting to the database for updating.";
	 		
	 		} 
	 	//Query to execute
	 	String query = "UPDATE fund SET Fund_Announcment = ? , F_Duration = ? , A_Instructions = ? ,D_modified_date = ? Where FundID = ? ";
	     
	 	// create a prepared statement
	 	PreparedStatement preparedStmt = con.prepareStatement(query); 
	 	
	 	//Getting the local date 
	 	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	 	java.util.Date date =  new Date();
	 	String d1 = dateFormat.format(date);
	 	
	 	// binding values
	 	preparedStmt.setString(1, Fund_announcement);
	 	preparedStmt.setString(2, Fund_duration);
	 	preparedStmt.setString(3,instructions);
	 	preparedStmt.setString(4,d1);	 	
	 	preparedStmt.setInt(5,fundid );
	 	//preparedStmt.setString(4,ProjID );
	 
	 	
	 	// execute the statement
	 	preparedStmt.execute(); 
	 	con.close();
	 	output = "================ Details of Fund ID : '"+fundid+"'  Updated successfully ================";
	 	
	 	}catch (Exception e) { 
	 		output = "Error while updating the Details...Fund ID = '"+fundid+"'";
	 		System.err.println(e.getMessage()); 
	 		
	 	  } return output; 
	 		 	  
	 } 
	 
	 
	 public String readFundDetails()
		{
			String output = "";
			//Create the DB Conenction
			try
			{
				 Connection con = DBConnect.connect();
				 
			if (con == null)
			{
				return "Error while connecting to the database for reading."; }
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr>"
					  + "<th>Fund ID</th>"
					  + "<th>Project ID</th>"
					  + "<th>Fund Request Date</th>" 
					  + "<th> Fund Announcement</th>" 
					  +	"<th> Fund Duration</th>" 
					  +	"<th> Instructions to Applicant</th>" 
					  +	"<th> Fund Amount</th>" 
					  +	"<th>Details Updated Date</th>" ;
					
			//Query to execute
			String query = "select * from fund";
			//Creating the prepared statement
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			//getting the values to a result set
			ResultSet rs = preparedStmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next())
			{
				String FundID = Integer.toString(rs.getInt("FundID"));
				String ProjID = (rs.getString("ProjectID"));
				Date ReqDate = rs.getDate("F_Request_Date");
				String FundAnnounce = rs.getString("Fund_Announcment");
				String Duration = rs.getString("F_Duration");
				String Instructions = rs.getString("A_Instructions");				
				Date ModifyDate = rs.getDate("D_modified_date");
				Double amount = rs.getDouble("Fund_amount");
				
				
				// Add into the html table
				output += "<tr><td>" + FundID + "</td>";
				output += "<td>" + ProjID + "</td>";
				output += "<td>" + ReqDate + "</td>";
				output += "<td>" + FundAnnounce + "</td>";
				output += "<td>" + Duration + "</td>";
				output += "<td>" + Instructions + "</td>";				
				output += "<td>" + amount + "</td>";
				output += "<td>" + ModifyDate + "</td>";
						
			}
			con.close();
			
			// Complete the html table
			output += "</table>";
			}
			catch (Exception e)
			{
				output = "Error while reading the products.";
				System.err.println(e.getMessage());
			}
			return output;
		}
	 
	 
	 public String readFundDetails(int id)
		{
		 
			String output = "";
			//create DB Connection
			try
			{
				 Connection con = DBConnect.connect();
				 
			if (con == null)
			{
				return "Error while connecting to the database for reading."; 
			}
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr>"
					  + "<th>Fund ID</th>"
					  + "<th>Project ID</th>"
					  + "<th>Fund Request Date</th>" 
					  + "<th>Fund Fund Announcement</th>" 
					  +	"<th>Fund Fund Duration</th>" 
					  +	"<th>Fund Instructions to Applicant</th>" 
					  +	"<th>Fund Fund Amount</th>" 
					  +	"<th>Details Updated Date</th>" ;
					
			//Query to execute
			String query = "Select * from fund where FundID = ?";
			//creating the prepared statement
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			
			preparedStmt.setInt(1,id);
			//Retrieving the values to a result set
			ResultSet rs = preparedStmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next())
			{
				String FundID = Integer.toString(rs.getInt("FundID"));
				String ProjID = (rs.getString("ProjectID"));
				Date ReqDate = rs.getDate("F_Request_Date");
				String FundAnnounce = rs.getString("Fund_Announcment");
				String Duration = rs.getString("F_Duration");
				String Instructions = rs.getString("A_Instructions");				
				Date ModifyDate = rs.getDate("D_modified_date");
				Double amount = rs.getDouble("Fund_amount");
				
				
				// Add into the html table
				output += "<tr><td>" + FundID + "</td>";
				output += "<td>" + ProjID + "</td>";
				output += "<td>" + ReqDate + "</td>";
				output += "<td>" + FundAnnounce + "</td>";
				output += "<td>" + Duration + "</td>";
				output += "<td>" + Instructions + "</td>";
				output += "<td>" + ModifyDate + "</td>";
				output += "<td>" + amount + "</td>";
				
			}
			con.close();
			
			// Complete the html table
			output += "</table>";
			}
			catch (Exception e)
			{
				output = "Error while reading the products.";
				System.err.println(e.getMessage());
			}
			return output;
		}
	 
		
		//Deleting the fund details
		public static String deleteFund(String fundID) {
			
			int FundID = Integer.parseInt(fundID);
			
			String output = "";
			
			try {
				 Connection con = DBConnect.connect();
				if (con == null) {
					return "Error while connecting to the database for deleting.";
				}
				// create a prepared statement
				String query = "delete from fund where FundID = ?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, FundID);
				// execute the statement
				preparedStmt.execute();
				
				con.close();
				
				output = "============== Data deleted successfully ===================";
				
			} catch (Exception e) {
				
				output = "Error while deleting the Fund details of id '"+fundID+"'...";
				System.err.println(e.getMessage());
				
			}
			return output;
		}


	public String retval() {
		
		String output = "";
		
		try {
			
			 Connection con = DBConnect.connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "SELECT FundID FROM fund ORDER BY FundID DESC LIMIT 1 ";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			ResultSet rs = preparedStmt.executeQuery(query);
			while (rs.next())
			{
				String FundID = Integer.toString(rs.getInt("FundID"));
				return (FundID);
			}
			preparedStmt.execute();
			con.close();
			
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
		}
		return output;
	}

}

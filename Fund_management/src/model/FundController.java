package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;


import util.DBConnect;

public class FundController {
	
	//======================== Client Fund Requesting Method ============================= //
	
	 public String insertClientFund(String Fund_duration ,String ProjectID ) {
			
			String output = "";
			//int id = Integer.parseInt(ProjectID);
			
			//checking the null value insertion
			if(Fund_duration.isEmpty()) {
				
				return "You need to enter Duration";	
				
			}else if(ProjectID.isEmpty()) {
				
				return "You need to enter ProjectID";		
			}
						
			try{ 
				
		     Connection con = DBConnect.connect();
			
			if (con == null) {
				
				return"Error while connecting to the database for inserting."; 
				
				} 
			
			  String query = "insert into fund (FundID,ProjectID,F_Duration,Fund_amount)"
				  		+ " values(?,?,?,?)";
			// create a prepared statement
			PreparedStatement preparedStmt = con.prepareStatement(query); 
				
				
			// Get the Project ID of the Fund from Project Micro Service
			Client client = new Client(); //Creating a Client Object 
			//Created webresource to access the specified URL
			WebResource resources = client.resource("http://localhost:8090/Project_managements/ProjectService/project/retID/"+ProjectID);					
			//Capturing the returning value
			String response = resources.queryParam("id", ProjectID).accept(MediaType.TEXT_PLAIN).get(String.class);
			//String response = resources.type(MediaType.TEXT_PLAIN).get(String.class);

			//Double amt = Double.parseDouble(response);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setInt(2,Integer.parseInt((ProjectID)));			
			preparedStmt.setString(3,(Fund_duration));			
			preparedStmt.setDouble(4,Double.parseDouble(response));
					
			// execute the statement
			preparedStmt.execute(); 
			
			con.close();
			
			output = " ============= Details inserted successfully =============";
			
			 }catch(Exception e) {
				 e.printStackTrace();	
				 output = " ============= Error While Inserting the Data =============";
			 }
			
				 return output; 		
		} 
		
	//======================== Client Fund Updating  Method ============================= //
	 
	 public String updateClientFundReq(int fundid ,String Fund_duration, String Amount)
	 { 
	 	String output = "";
	 	
	 	//Checking for the null values
	 	if(fundid == 0) {
	 		
	 		return "Field FundID cannot be 0";	
	 		
	 	}else if( Fund_duration.isEmpty()) {
	 		
	 		return "Duration cannot be empty";
	 		
	 	}else if(Amount.isEmpty()) {
	 		
	 		return "Amount cannot be empty";
	 	}
	 	
	 	 	
	 	
	 	try{ 
			 	//Testing database connection
			 	Connection con = DBConnect.connect();
			 	
			 	if (con == null) {
			 		
			 		return"Error while connecting to the database for updating.";
			 		
			 	} 
			 	
			 	//Query to execute
			 	String query = "UPDATE fund SET  F_Duration = ? ,D_modified_date = ? ,Fund_amount = ? Where FundID = ? ";
			     
			 	// create a prepared statement
			 	PreparedStatement preparedStmt = con.prepareStatement(query); 
			 	
			 	//Getting the local date 
			 	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			 	java.util.Date date =  new Date();
			 	String d1 = dateFormat.format(date);
			 	
			 	// binding values
			 	preparedStmt.setString(1, Fund_duration);
			 	preparedStmt.setString(2, d1);
			 	preparedStmt.setString(3,Amount);
			 	preparedStmt.setInt(4,fundid);					
			 	
			 	// execute the statement
			 	preparedStmt.execute(); 
			 	con.close();
			 	output = "================ Fund Details Updated successfully ================";
	 	
	 	}catch (Exception e) { 
	 		
		 		output = "Error while updating the Details...Fund ID = '"+fundid+"'";
		 		System.err.println(e.getMessage()); 
	 		
	 	  } 
	 			return output; 
	 		 	  
	 } 
	 
	
	//============================= Admin Updating the Fund Details ===========================// 
		
	 public String UpdateFund(int fundid , String Fund_announcement , String Fund_duration,String instructions , String Amount)
	 { 
	 	String output = "";
	 	
	 	//Checking for the null values
	 	if(fundid == 0 ) {
	 		
	 		return "Field ProjectID cannot be 0";	
	 		
	 	}else if( Fund_announcement.isEmpty() ) {
	 		
	 		return "Announcement cannot be empty";
	 		
	 	}else if(Fund_duration.isEmpty() ) {
	 		
	 		return "Fund_duration cannot be empty";
	 		
	 	}else if(instructions.isEmpty() ) {
	 		
	 		return "instructions cannot be empty";
	 	}
	 	
	 	 	
	 	try{ 
			 	//Testing database connection
			 	 Connection con = DBConnect.connect();
			 	
			 	if (con == null) {
			 		
			 		return"Error while connecting to the database for updating.";
			 		
			 		} 
			 	//Query to execute
			 	String query = "UPDATE fund SET Fund_Announcment = ? , F_Duration = ? , A_Instructions = ? ,D_modified_date = ? ,Fund_amount = ? Where FundID = ? ";
			     
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
			 	preparedStmt.setString(4,Amount);	
			 	preparedStmt.setInt(5,fundid );
			 	//preparedStmt.setString(4,ProjID );
			 
			 	
			 	// execute the statement
			 	preparedStmt.execute(); 
			 	con.close();
			 	output = "================ Details of Fund ID : '"+fundid+"'  Updated successfully ================";
	 	
	 	}catch (Exception e) { 
		 		output = "Error while updating the Details...Fund ID = '"+fundid+"'";
		 		System.err.println(e.getMessage()); 
	 		
	 	  } 
	 			return output; 
	 		 	  
	 } 
	 
	  // ================================== Reading the all Fund Request Details =========================//
	 
	 public String readFundDetails()
		{
			String output = "";
			//Create the DB Conenction
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
	
	 
		
		//============================= Fund Deleting Method ============================//
	 
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
		
		// ========================== Searching For a particular Fund Details =====================//
		
		public String SearchFund(String id)
		{
			int fID = Integer.parseInt(id);
			
			String output = "";
			//create DB Connection
			try
			{
				 Connection con = DBConnect.connect();
				 
			if (con == null)
			{
				//return "Error while connecting to the database for reading."; 
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr>"
					  + "<th>Fund ID</th>"
					  + "<th>Project ID</th>"
					  + "<th>Request Date</th>" 
					  + "<th>Fund Announcement</th>" 
					  +	"<th>Fund Duration</th>" 
					  +	"<th>Instructions to Applicant</th>" 				 
					  +	"<th>Details Updated Date</th>"
					  +	"<th>Fund Amount</th>" ;
			
			//Query to execute
			String query = "SELECT * FROM `fund` WHERE FundID = '"+id+"' ";
			//creating the prepared statement
			PreparedStatement preparedStmt = con.prepareStatement(query); 
		    //preparedStmt.setInt(1,fID);			
			
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
			
//	

}

package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.mysql.cj.xdevapi.Statement;
import util.DBConnect;

public class FundController {
	
	
	
	
	 public String insertFund(int fundid , int cusid , String fund_desc , String fundamt, String requestDate, String status) {
			
			String output = "";
			
			try{ 
				
		     Connection con = DBConnect.connect();
			
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
		
	   
	 public String updateItem(int fundid , int cusid , String fund_desc , String status)
	 { 
	 	String output = "";

	 	try{ 
	 		 Connection con = DBConnect.connect();
	 	
	 	if (con == null) {
	 		
	 		return"Error while connecting to the database for updating."; 
	 		} 
	 	// create a prepared statement
	 	String query = "UPDATE Fund SET fund_desc = ? , status = ?   FundID FundID=? AND cusid = ?";
	 	
	 	PreparedStatement preparedStmt = con.prepareStatement(query); 
	 	
	 	// binding values
	 	preparedStmt.setString(1, fund_desc);
	 	preparedStmt.setString(2, status);
	 	preparedStmt.setInt(3,fundid );
	 	preparedStmt.setInt(4,cusid );
	 
	 	
	 	// execute the statement
	 	preparedStmt.execute(); 
	 	con.close();
	 	output = "Fund Details Updated successfully....";
	 	
	 	}catch (Exception e) { 
	 		output = "Error while updating the Details...Fund ID = '"+fundid+"'";
	 		System.err.println(e.getMessage()); 
	 		
	 	  } return output; 
	 	
	 	} 
	 
	 
	 public String readFundDetails()
		{
			String output = "";
			try
			{
				 Connection con = DBConnect.connect();
			if (con == null)
			{return "Error while connecting to the database for reading."; }
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr>"
					  + "<th>Fund ID</th>"
					  + "<th>Fund Description</th>" 
					  + "<th>Fund amount</th>" 
					  +	"<th>Fund Request Date</th>" 
					  +	"<th>Fund Request Status</th>" 
					  +	"<th>Fund Grant Date</th>" 
					  + "<th>Update</th><th>Remove</th></tr>";
			
			String query = "select * from product";
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			ResultSet rs = preparedStmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next())
			{
				String FundID = Integer.toString(rs.getInt("FundID"));
				String Funddesc = rs.getString("Fund_desc");
				Double Fundamount = rs.getDouble("Fund_amount");
				Date ReqDate = rs.getDate("Req_date");
				Double ReqStatus = rs.getDouble("Req_status");
				Date GrantDate = rs.getDate("Grant_date");
			
				
				// Add into the html table
				output += "<tr><td>" + FundID + "</td>";
				output += "<td>" + Funddesc + "</td>";
				output += "<td>" + Fundamount + "</td>";
				output += "<td>" + ReqDate + "</td>";
				output += "<td>" + ReqStatus + "</td>";
				output += "<td>" + GrantDate + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
				        + "<td><form method='post' action='#'>"//need to add the redirect jsp
						+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
						+ "<input name='pro_ID' type='hidden' value='" + FundID+ "'>" + "</form></td></tr>";
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
		public static String deleteFund(int fundid) {
			String output = "";
			try {
				 Connection con = DBConnect.connect();
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
				output = "Error while deleting the Fund details of id '"+fundid+"'...";
				System.err.println(e.getMessage());
			}
			return output;
		}



	

}

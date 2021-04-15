package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import util.DBConnect;

public class FundController {
	
	
	
	
	 public String insertFund(String ProjID , String fund_desc , String fundamt) {
			
			String output = "";
			int FundID;
			
			try{ 
				
		     Connection con = DBConnect.connect();
			
			if (con == null) {
				return"Error while connecting to the database for inserting."; 
				} 
			
			  String query = "insert into Fund (FundID,CustomerID,Fund_desc,Fund_amount)"
				  		+ " values(?,?,?,?)";
			// create a prepared statement
				PreparedStatement preparedStmt = con.prepareStatement(query); 
				
				
			// Get the Project ID of the Fund from Project Micro Service
			Client client = new Client();
			WebResource resource = client.resource("http://localhost:8080/Lab05Rest/ItemService/Items");
			String response = resource.queryParam("id", ProjID).accept(MediaType.TEXT_PLAIN).get(String.class);

							
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, response);
			preparedStmt.setString(3, fund_desc);
			preparedStmt.setDouble(4, Double.parseDouble(fundamt)); 			
			
			// execute the statement
			preparedStmt.execute(); 
			
			//retrieving the fund id from the fund Table
			String queryslt = "SELECT FundID FROM  Fund WHERE ProjID = ? ORDER BY FundID DESC LIMIT 1"; 
			PreparedStatement preparedStmt2 = con.prepareStatement(queryslt);	
			preparedStmt2.setString(1, ProjID);
			
			// execute the statement
			ResultSet resultSet = preparedStmt2.executeQuery();

			//retriving the Fundid
			if (resultSet.next()) {
				 FundID = resultSet.getInt(1);
			} else {
				return "Error whiling loading data..";
						}
			con.close();
			
			output = " ============= Fund ID :'"+FundID+"' - Details inserted successfully =============";
			
			 }catch(Exception e) {
				 e.printStackTrace();				 			 
			 }
			
				 return output; 		
		} 
		
	   
	 public String updateItem(int fundid , int cusid , String fund_desc , String status)
	 { 
	 	String output = "";
	 	
	 	try{ 
	 		//Testing database connection
	 		 Connection con = DBConnect.connect();
	 	
	 	if (con == null) {
	 		
	 		return"Error while connecting to the database for updating.";
	 		
	 		} 
	 	//Query to execute
	 	String query = "UPDATE Fund SET fund_desc = ? , status = ?   FundID FundID=? AND cusid = ?";
	     
	 	// create a prepared statement
	 	PreparedStatement preparedStmt = con.prepareStatement(query); 
	 	
	 	// binding values
	 	preparedStmt.setString(1, fund_desc);
	 	preparedStmt.setString(2, status);
	 	preparedStmt.setInt(3,fundid );
	 	preparedStmt.setInt(4,cusid );
	 
	 	
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
					  +	"<th>Fund Grant Date</th>" ;
					
			
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
	 
	 
	 public String readFundDetails(String ID)
		{
		 
		 int id = Integer.parseInt(ID);
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
					  +	"<th>Fund Grant Date</th>" ;
					
			
			String query = "select * from Fund Where FundID = ?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			
			preparedStmt.setInt(1,id);
			
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
				output = "============== Data deleted successfully ===================";
			} catch (Exception e) {
				output = "Error while deleting the Fund details of id '"+fundid+"'...";
				System.err.println(e.getMessage());
			}
			return output;
		}



	

}

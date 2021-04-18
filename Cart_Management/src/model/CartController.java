package model;

import util.DBConnection;
import java.sql.*;

//To implement the server-model 

public class CartController {
	
	int quantity2;
	double unitPrice2;
	
	DBConnection dbObj = new DBConnection();
	
	public String insertToCart(String item, String quantity, String unitPrice){ 
		 String output = ""; 
		 try{ 
			 Connection con = dbObj.connect(); 
			 if (con == null) {
				 return "Error while connecting to the database for inserting."; 
			 } 
			 // create a prepared statement
			 String query = "insert into cart(`cartID`,`item`,`quantity`,`unitPrice`)"
					 			+ " values (?, ?, ?, ?)"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, item); 
			 preparedStmt.setInt(3, Integer.parseInt(quantity));
			 preparedStmt.setDouble(4, Double.parseDouble(unitPrice)); 
			// execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 output = "Items Inserted successfully!"; 
		 } 
		 catch (Exception e) { 
			 output = "Error while inserting the order!"; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	}

}

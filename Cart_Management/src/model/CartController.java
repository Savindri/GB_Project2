package model;

import util.DBConnection;
import java.sql.*;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

//To implement the server-model 
public class CartController {
	
	int quantity2;
	double price;
	
	DBConnection dbObj = new DBConnection();
	
	public String insertToCart(String pro_ID, String quantity){ 
		 String output = ""; 
		 
		 try{ 
			 Connection con = dbObj.connect(); 
			 if (con == null) {
				 return "Error while connecting to the database for inserting."; 
			 } 
			 // create a prepared statement
			 String query = "insert into cart(`cartID`,`pro_ID`,`quantity`,`unitPrice`)"
					 			+ " values (?, ?, ?, ?)"; 
			 
			 	//to get unit price from product table
			 	String query2 = "select price from product where pro_ID = '"+pro_ID+"'";
				PreparedStatement preparedStmt2 = con.prepareStatement(query2); 
				ResultSet rs2 = preparedStmt2.executeQuery(query2);
				while (rs2.next()){
					price = rs2.getDouble("price");
				}
				String unitPrice = Double.toString(price);
				preparedStmt2.execute();
				
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			 // binding values
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, pro_ID);
			 preparedStmt.setInt(3, Integer.parseInt(quantity));
			 preparedStmt.setString(4, unitPrice); 
			// execute the statement
			 preparedStmt.execute(); 
			 
			 con.close(); 
			 output = "Items Inserted successfully!"; 
		 } 
		 catch (Exception e) { 
			 output = "Error while inserting the items!"; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	}
	
	public String readCart(){ 
		 String output = ""; 
		 try{ 
			 Connection con = dbObj.connect(); 
			 if (con == null) {
				 return "Error while connecting to the database for reading!"; 
			 } 
			 // Prepare the html table to be displayed
			 output = "<table border='1'>"
			 			+ "<tr>"
			 			+ "<th>Product ID</th>" 
			 			+ "<th>Quantity</th>"
			 			+ "<th>Unit_Price (Rs.)</th>"
			 			+ "<th>Update</th>"
						+ "<th>Remove</th>"
			 			+ "</tr>"; 
			 
			 String query = "select * from cart"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) { 
				 String cartID = Integer.toString(rs.getInt("cartID"));
				 String pro_ID = Integer.toString(rs.getInt("pro_ID"));
				 String quantity = Integer.toString(rs.getInt("quantity"));
				 String unitPrice = Double.toString(rs.getDouble("unitPrice")); 
				 //  Add a row into the html table
				 output += "<td>" + pro_ID + "</td>";
				 output += "<td>" + quantity + "</td>";
				 output += "<td>" + unitPrice + "</td>";
				 // buttons
				 output += "<td><form method='post' action='updateCart.jsp'>"
				 		 + "<input name='btnUpdate' type='submit' value='Update' class='btn btn-secondary'></td>"
				 		 + "<input name='cartID' type='hidden' value='" + cartID + "'>"
				 		 + "<input name='pro_ID' type='hidden' value='" + pro_ID + "'>"
				 		 + "<input name='quantity' type='hidden' value='" + quantity + "'>"
				 		 + "<input name='unitPrice' type='hidden' value='" + unitPrice + "'>"
				 		 + "</form></td>"
						 + "<td><form method='post' action='Cart.jsp'>"
						 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						 + "<input name='cartID' type='hidden' value='" + cartID +"'>" 
						 + "</form></td></tr>"; 
			 } 
			 con.close(); 
			 // Complete the html table
			 output += "</table>"; 
		 } 
		 catch (Exception e) { 
			 output = "Error while reading cart!"; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	 }
	
	
	public String deleteFromCart(String cartID) { 
		 String output = ""; 
		 try{ 
			 Connection con = dbObj.connect(); 
			 if (con == null) {
				 return "Error while connecting to the database for deleting."; 
			 } 
			 // create a prepared statement
			 String query = "delete from cart where cartID = ?";
					 /*ALTER TABLE order_ ADD  CONSTRAINT fk_cartID FOREIGN KEY(cartID)
					 REFERENCES cart (cartID)
					 ON DELETE CASCADE*/
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(cartID)); 
			 // execute the statement
			 preparedStmt.execute();
			 con.close(); 
			 output = "Items Deleted successfully!"; 
		 } 
		 catch (Exception e) { 
			 output = "Error while deleting the items!"; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	 }
	
	
	public String updateCart(String cartID, String pro_ID, String quantity ) {
    	String output = "";    	
    	try{
    		Connection con = dbObj.connect();
    		if (con == null){
	    		return "Error while connecting to the database for updating!";
	    	}	
			//String querry = "update cart set pro_ID = ? , quantity = ? , unitPrice = ?  where cartID = ?";
					/*ALTER TABLE order_  ADD  CONSTRAINT fk_cartID_update FOREIGN KEY(cartID)
					REFERENCES cart (cartID)
					ON UPDATE CASCADE*/
			
    		String querry = "update cart,order_ set cart.pro_ID = ? , cart.quantity = ? , cart.unitPrice = ?, order_.total = cart.quantity*cart.unitPrice where cart.cartID = ? and cart.cartID = order_.cartID";
			
		    		//to get unit price from product table
				 	String query2 = "select price from product where pro_ID = '"+pro_ID+"'";
					PreparedStatement preparedStmt2 = con.prepareStatement(query2); 
					ResultSet rs2 = preparedStmt2.executeQuery(query2);
					while (rs2.next()){
						price = rs2.getDouble("price");
					}
					String Price = Double.toString(price);
					preparedStmt2.execute();
    		
			//create a prepared statement
			PreparedStatement preparedStmt = con.prepareStatement(querry);				  
			//binding values
			preparedStmt.setInt(1, Integer.parseInt(pro_ID));
			preparedStmt.setString(2, quantity);
			preparedStmt.setDouble(3, Double.parseDouble(Price));
			preparedStmt.setInt(4, Integer.parseInt(cartID));
			//execute the statement
			preparedStmt.execute();
			con.close();
			output = "Cart Updated successfully!";
		}
    	catch(Exception e){
    		output = "Error while updating the cart!"; 
			 System.err.println(e.getMessage()); 
		}  	
    	return output;
    }
}

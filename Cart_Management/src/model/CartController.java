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
			 			+ "<th>Item</th>" 
			 			+ "<th>Quantity</th>"
			 			+ "<th>Unit_Price</th>"
			 			+ "<th>Update</th>"
						+ "<th>Remove</th>"
			 			+ "</tr>"; 
			 
			 String query = "select * from cart"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) { 
				 String cartID = Integer.toString(rs.getInt("cartID")); 
				 String item = rs.getString("item");
				 String quantity = Integer.toString(rs.getInt("quantity"));
				 String unitPrice = Double.toString(rs.getDouble("unitPrice")); 
				 //  Add a row into the html table
				 output += "<td>" + item + "</td>";
				 output += "<td>" + quantity + "</td>";
				 output += "<td>" + unitPrice + "</td>";
				 // buttons
				 output += "<td><form method='post' action='updateCart.jsp'>"
				 		 + "<input name='btnUpdate' type='submit' value='Update' class='btn btn-secondary'></td>"
				 		 + "<input name='cartID' type='hidden' value='" + cartID + "'>"
				 		 + "<input name='item' type='hidden' value='" + item + "'>"
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
			 String query = "delete from cart where cartID=?"; 
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
	
	public String updateCart(String cartID ,String item, String quantity, String unitPrice) {
    	String output = "";    	
    	try{
    		Connection con = dbObj.connect();
    		if (con == null){
	    		return "Error while connecting to the database for updating!";
	    	}	
			//String querry = "update cart set item = ? , quantity = ? , unitPrice = ? where cartID = ?";
    		
    		String querry = "update cart,order_ set cart.item = ? , cart.quantity = ? , cart.unitPrice = ?, order_.total = cart.quantity*cart.unitPrice where cartID = ? and cart.cartID = order_.cartID_f";
			
			//create a prepared statement
			PreparedStatement preparedStmt = con.prepareStatement(querry);				  
			//binding values
			preparedStmt.setString(1,item );
			preparedStmt.setString(2, quantity);
			preparedStmt.setDouble(3, Double.parseDouble(unitPrice));
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

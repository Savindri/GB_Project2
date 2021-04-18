package model;

import util.DBConnection;
import java.sql.*;

public class OrderController {
	
	int quantity;
	double unitPrice;
	int foreignKey;
	
	DBConnection dbObj = new DBConnection();
	
	

	
	
	public String readOrder(){ 
		 String output = ""; 
		 try{ 
			 Connection con = dbObj.connect(); 
			 if (con == null) {
				 return "Error while connecting to the database for reading!"; 
			 } 
			 // Prepare the html table to be displayed
			 output = "<table border='1'>"
			 			+ "<tr>"
			 			+ "<th>cartID</th>"
			 			+ "<th>Date</th>" 
			 			+ "<th>Customer Name</th>"
			 			+ "<th>Address</th>"
			 			+ "<th>Phone</th>"
			 			+ "<th>Email</th>"
			 			+ "<th>Total Amount</th>"
			 			+ "<th>Update</th>"
						+ "<th>Remove</th>"
			 			+ "</tr>"; 
			 
			 String query = "select * from order_";
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) { 
				 String orderID = Integer.toString(rs.getInt("orderID"));
				 String cartID_f = Integer.toString(rs.getInt("cartID_f"));
				 String date = rs.getString("date");
				 String custName = rs.getString("custName");
				 String address = rs.getString("address");
				 String phone = Integer.toString(rs.getInt("phone"));
				 String email = rs.getString("email");
				 String total = rs.getString("total");
				 //  Add a row into the html table
				 output += "<td>" + cartID_f + "</td>";
				 output += "<td>" + date + "</td>";
				 output += "<td>" + custName + "</td>";
				 output += "<td>" + address + "</td>";
				 output += "<td>" + phone + "</td>";
				 output += "<td>" + email + "</td>";
				 output += "<td>" + total + "</td>";
				 // buttons
				 output += "<td><form method='post' action='updateOrder.jsp'>"
				 		 + "<input name='btnUpdate' type='submit' value='Update' class='btn btn-secondary'></td>"
				 		 + "<input name='orderID' type='hidden' value='" + orderID + "'>"
				 		 + "<input name='cartID_f' type='hidden' value='" + cartID_f + "'>"
				 		 + "<input name='date' type='hidden' value='" + date + "'>"
				 		 + "<input name='custName' type='hidden' value='" + custName + "'>"
				 		 + "<input name='address' type='hidden' value='" + address + "'>"
				 		 + "<input name='phone' type='hidden' value='" + phone + "'>"
				 		 + "<input name='email' type='hidden' value='" + email + "'>"
				 		 + "<input name='total' type='hidden' value='" + total + "'>"
				 		 + "</form></td>"
						 + "<td><form method='post' action='Order.jsp'>"
						 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						 + "<input name='orderID' type='hidden' value='" + orderID +"'>" 
						 + "</form></td></tr>"; 
			 } 
			 con.close(); 
			 // Complete the html table
			 output += "</table>"; 
		 } 
		 catch (Exception e) { 
			 output = "Error while reading order!"; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	 }

}

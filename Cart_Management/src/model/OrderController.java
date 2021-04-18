package model;

import util.DBConnection;
import java.sql.*;

public class OrderController {
	
	int quantity;
	double unitPrice;
	int foreignKey;
	
	DBConnection dbObj = new DBConnection();
	
	
	public String insertOrder(String date, String custName, String address, String phone, String email){ 
		 String output = ""; 
		 try{ 
			 Connection con = dbObj.connect(); 
			 if (con == null) {
				 return "Error while connecting to the database for inserting."; 
			 } 
			 // create a prepared statement
			 String query = "insert into order_(`orderID`,`cartID_f`,`date`,`custName`,`address`,`phone`,`email`,`total`)"
					 			+ " values (?, ?, ?, ?, ?, ?, ?, ?)";	
			 	
			 	//calculate total amount
			 	String query2 = "select quantity , unitPrice from cart order by cartID desc limit 1";
				PreparedStatement preparedStmt2 = con.prepareStatement(query2); 
				ResultSet rs = preparedStmt2.executeQuery(query2);
				while (rs.next()){
					 quantity = (rs.getInt("quantity"));
			         unitPrice = (rs.getDouble("unitPrice")); 	
				}
				String total = Double.toString(quantity * unitPrice);
				preparedStmt2.execute();
				
				//to get cartID as foreign key
			 	String query3 = "select cartID from cart order by cartID desc limit 1";
				PreparedStatement preparedStmt3 = con.prepareStatement(query3); 
				ResultSet rs3 = preparedStmt3.executeQuery(query3);
				while (rs3.next()){
					foreignKey = rs3.getInt("cartID");
				}
				String cartID_f = Integer.toString(foreignKey);
				preparedStmt3.execute();
			
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, cartID_f);
			 preparedStmt.setString(3, date); 
			 preparedStmt.setString(4, custName);
			 preparedStmt.setString(5, address);
			 preparedStmt.setInt(6, Integer.parseInt(phone));
			 preparedStmt.setString(7, email);
			 preparedStmt.setString(8, total);
			 // execute the statement
			 preparedStmt.execute();
			 con.close(); 
			 output = "Order Inserted successfully!"; 
		 } 
		 catch (Exception e) { 
			 output = "Error while inserting the order!"; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	}
	
	
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

package model;

import util.DBConnection;
import java.sql.*;

public class Project {
	
	DBConnection obj = new DBConnection();
	
	

	public String insertProject(String projectName, String budget, String completionDate, String productCategory, String sellOrNot, String description) { 
	 
		String output = ""; 
	 
		try{ 
	 
			Connection con = obj.connect(); 
	 
			if (con == null) {
				return "Error while connecting to the database for inserting."; 
			} 
		
		String query = " insert into project_proposals (`proposal_ID`,`projectName`,`budget`,`completionDate`,`productCategory`,`sellOrNot`,`description`)"+ " values (?, ?, ?, ?, ?, ?, ?)"; 
	 
		PreparedStatement preparedStmt = con.prepareStatement(query); 
	
	
		preparedStmt.setInt(1, 0); 
		preparedStmt.setString(2, projectName); 
		preparedStmt.setDouble(3, Double.parseDouble(budget)); 
		preparedStmt.setString(4, completionDate); 
		preparedStmt.setString(5, productCategory); 
		preparedStmt.setString(6, sellOrNot);
		preparedStmt.setString(7, description);
	
		// execute the statement3
		preparedStmt.execute(); 
		con.close(); 
		output = "Project proposal inserted successfully"; 
		} 
		catch (Exception e) { 
	 
			output = "Error while inserting the item."; 
			System.err.println(e.getMessage());
			System.out.print(e);
			
		} 
		
		return output; 
	
	 }
	
	
	public String readProject() {
		
	
		String output = ""; 
	 
		try{ 
	 
			Connection con = obj.connect(); 
	 
			if (con == null) {
				return "Error while connecting to the database for reading.";
			} 
	 
	 
			output = "<table border='1'>"
					+ "<tr>"
					+ "<th>Proposal ID</th>"
					+ "<th>Project Name</th>"
					+"<th>Budget</th>" 
					+"<th>Completion Date</th>" 
					+"<th>Product Category</th>" 
					+"<th>Sell Or Not</th>" 
					+"<th>Description</th>" 
					+"<th>Update</th>"
					+ "<th>Remove</th>"
					+ "</tr>"; 
	 
			String query = "select * from project_proposals"; 
	 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
	
			// iterate through the rows in the result set
			while (rs.next()) {
				
				 String proposal_ID = Integer.toString(rs.getInt("proposal_ID")); 
				 String projectName = rs.getString("projectName"); 
				 String budget = Double.toString(rs.getDouble("budget")); 
				 String completionDate = rs.getString("completionDate"); 
				 String productCategory = rs.getString("productCategory"); 
				 String sellOrNot = rs.getString("sellOrNot");
				 String description = rs.getString("description");
				 
				 // Add into the html table
				 output += "<tr><td>" + proposal_ID + "</td>"; 
				 output += "<td>" + projectName + "</td>"; 
				 output += "<td>" + budget + "</td>"; 
				 output += "<td>" + completionDate + "</td>";
				 output += "<td>" + productCategory + "</td>"; 
				 output += "<td>" + sellOrNot + "</td>"; 
				 output += "<td>" + description + "</td>"; 
				 
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
						 + "<td><form method='post' action='items.jsp'>"
						 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						 + "<input name='proposal_ID' type='hidden' value='" + proposal_ID + "'>" + "</form></td></tr>"; 
			}
			
			con.close(); 
	 
			// Complete the html table
			output += "</table>"; 
	 } 
	 catch (Exception e) 
	 { 
		 output = "Error while reading the items."; 
		 System.err.println(e.getMessage()); 
	 } 
		
	 
		return output; 
	} 
	
	
	
	
	
	public String deleteProject(String proposal_ID) { 
	 
		String output = ""; 
	 
		try{ 
	 
			Connection con = obj.connect(); 
	 
			if (con == null) {
				return "Error while connecting to the database for deleting."; } 
	 
			// create a prepared statement
			String query = "delete from project_proposals where proposal_ID=?"; 
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(proposal_ID)); 
	 
			// execute the statement
			preparedStmt.execute(); 
	 
			con.close(); 
	 
			output = "Project deleted successfully"; 
	 
		} 
		catch (Exception e) 
		{ 
			output = "Error while deleting the item."; 
			System.err.println(e.getMessage()); 
			System.out.print(e);
		} 
	 
		return output; 
	 }
	
	
	public String updateProject(String proposal_ID, String projectName, String budget, String completionDate, String productCategory, String sellOrNot, String description){ 
	 
		String output = ""; 
	 
		try{ 
	 
			Connection con = obj.connect(); 
	 
			if (con == null) {
				return "Error while connecting to the database for updating.";
				} 
	 
			// create a prepared statement
			String query = "UPDATE project_proposals SET projectName=?,budget=?,completionDate=?,productCategory=?,sellOrNot=?,description=? WHERE proposal_ID=?"; 
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values 
			preparedStmt.setString(1, projectName); 	
			preparedStmt.setDouble(2, Double.parseDouble(budget));
			preparedStmt.setString(3, completionDate); 
			preparedStmt.setString(4, productCategory); 
			preparedStmt.setString(5, sellOrNot); 
			preparedStmt.setString(6, description); 
			preparedStmt.setInt(7, Integer.parseInt(proposal_ID)); 
	 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
	 
			output = "Project updated successfully"; 
	 
		} 
		catch (Exception e) 
		{ 
	 
			output = "Error while updating the item."; 
	 
			System.err.println(e.getMessage()); 
			System.out.print(e);
	 
		} 
	
		return output;
	 
	 }

	
	
}



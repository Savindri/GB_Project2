package model;

import java.sql.*;

public class store {
	//A common method to connect to the DB
	private Connection connect()
	{
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			//Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
		}
		catch (Exception e)
		{e.printStackTrace();}
		return con;
	}
	//Insert Method
	public String insertProduct(String pro_ID, String desc, String qty, String price, String category)
	{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for inserting."; }
		
		// create a prepared statement
		String query = " insert into product(`pro_ID`,`desc`,`qty`,`price`,`category`)"
		+ " values (?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		// binding values
		preparedStmt.setString(1, pro_ID);
		preparedStmt.setString(2, desc);
		preparedStmt.setInt(3, 2);
		preparedStmt.setDouble(4, Double.parseDouble(price));
		preparedStmt.setString(5, category);
		
		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Inserted successfully";
		}
		catch (Exception e)
		{
		output = "Error while inserting the products.";
		System.err.println(e.getMessage());
		}
		return output;
	}
	//Read Method
	public String readProduct()
	{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for reading."; }
		
		// Prepare the html table to be displayed
		output = "<table border='1'><tr><th>Product ID</th><th>Description</th>" + "<th>Quantity</th>" + "<th>Price</th>" +	"<th>Category</th>" +"<th>Update</th><th>Remove</th></tr>";
		String query = "select * from product";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		// iterate through the rows in the result set
		while (rs.next())
		{
			String pro_ID = Integer.toString(rs.getInt("po_ID"));
			String desc = rs.getString("desc");
			String qty = rs.getString("qty");
			String price = Double.toString(rs.getDouble("price"));
			String category = rs.getString("category");
			
			// Add into the html table
			output += "<tr><td>" + pro_ID + "</td>";
			output += "<td>" + desc + "</td>";
			output += "<td>" + qty + "</td>";
			output += "<td>" + price + "</td>";
			output += "<td>" + category + "</td>";
			
			// buttons
			output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"+ "<td><form method='post' action='store.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"+ "<input name='pro_ID' type='hidden' value='" + pro_ID+ "'>" + "</form></td></tr>";
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
	
	//update method
	public String updateProduct(String pro_ID, String desc, String qty, String price, String category)
	{
	String output = "";
	try
	{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for updating."; }
		
		// create a prepared statement
		String query = "UPDATE product SET pro_ID=?,desc=?,qty=?,price=?,category=? WHERE pro_ID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		// binding values
		preparedStmt.setString(1, pro_ID);
		preparedStmt.setString(2, desc);
		preparedStmt.setInt(3, 2);
		preparedStmt.setDouble(4, Double.parseDouble(price));
		preparedStmt.setString(5, category);
		
		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Updated successfully";
	}
	catch (Exception e)
	{
		output = "Error while updating the product.";
		System.err.println(e.getMessage());
	}
	return output;
	}
	
	//delete method
	public String deleteProduct(String pro_ID)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{return "Error while connecting to the database for deleting."; }
			
			// create a prepared statement
			String query = "delete from product where pro_ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, pro_ID);
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		}
		catch (Exception e)
		{
			output = "Error while deleting the product.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}

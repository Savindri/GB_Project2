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
}

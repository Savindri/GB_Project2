package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class FundController {
	
	/* Database connection */
	private static String url = "127.0.0.1:3306/gadetbadget";
	private static String username = "root";
	private static String password = "";
	
	//Creating Database Connection
	private static Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://" + url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	

}

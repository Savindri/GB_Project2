package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	public Connection connect() {
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gb_projects", "root", "");
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.print(e);
		}
		
		return con;
	}
	
}

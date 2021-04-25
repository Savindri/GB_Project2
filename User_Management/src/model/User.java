package model;
import java.sql.*;
import java.util.Set;
public class User
{ 
	
            //Create Database Connection
	
            private static Connection connect(){
            Connection con = null;
            try{
               Class.forName("com.mysql.jdbc.Driver");
               con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return con;
}
            //implement insert method to the order
            public String insertUser(String firstName, String lastName, String address, String contactNumber,String email,String gender, String password, String type)
            {
                String output = "";
            try
            {
                Connection con = connect();
            if (con == null)
            {
            	return "Error while connecting to the database for inserting."; }
            
            //create a prepared statement
            String query = " insert into users(`userID`,`userfirstName`,`userlastName`,`useraddress`,`usercontactNumber`,`useremail`,`usergender`,`userpassword`,`usertype`)"
            + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            
            // binding values
            preparedStmt.setInt(1, 0);
            preparedStmt.setString(2, firstName);
            preparedStmt.setString(3, lastName);
            preparedStmt.setString(4, address);
            preparedStmt.setString(5, contactNumber);
            preparedStmt.setString(6, email);
            preparedStmt.setString(7, gender);
            preparedStmt.setString(8, password);
            preparedStmt.setString(9, type);
            
           // execute the statement
           preparedStmt.execute();
           con.close();
           output = "Inserted successfully";
           }
           catch (Exception e)
           {
        	   
           //print error messages if exist
           output = "Error while inserting the item.";
           System.err.println(e.getMessage());
           }
           return output;
           }
           //implement display all users
           public String readUsers(){

                  String output = "";
                  try{

                     Connection con = connect();
                     if (con == null){
                     return "Error while connecting to the database for reading."; 
                     }
                     
            // create table view strucyure
            output = "<table border='1'><tr><th>First Name</th><th>Last Name</th>" +
            "<th>Address</th>" +
            "<th>Contact Number</th>" +
            "<th>Email</th>" +
            "<th>Gender</th>" +
            "<th>Password</th>" +
            "<th>User Type</th>" +
            "<th>Update</th><th>Remove</th></tr>";
            
            String query = "select * from users";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            // iterate through the rows which taking from the table using result set
            while (rs.next()){

            String userID = Integer.toString(rs.getInt("userID"));
            String userfirstName = rs.getString("userfirstName");
            String userlastName = rs.getString("userlastName");
            String useraddress = rs.getString("useraddress");
            String usercontactNumber = rs.getString("usercontactNumber");
            String useremail = rs.getString("useremail");
            String usergender = rs.getString("usergender");
            String userpassword = rs.getString("userpassword");
            String usertype = rs.getString("usertype");

            // fill table
            output += "<tr><td>" + userfirstName + "</td>";
            output += "<td>" + userlastName + "</td>";
            output += "<td>" + useraddress + "</td>";
            output += "<td>" + usercontactNumber + "</td>";
            output += "<td>" + useremail + "</td>";
            output += "<td>" + usergender + "</td>";
            output += "<td>" + userpassword + "</td>";
            output += "<td>" + usertype + "</td>";
            
            // buttons
            output += "<td><input name='btnUpdate' type='button' value='Update'"
		    + "class='btn btn-secondary'></td>"
            + "<td><form method='post' action='users.jsp'>"
            + "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
            + "<input name='userID' type='hidden' value='" + userID
            + "'>" + "</form></td></tr>";
            }
            
            con.close();
            output += "</table>";
      }
      catch (Exception e)
      {
            output = "Error while reading the users.";
            System.err.println(e.getMessage());
      }
                  
      return output;
}
//implement update users in user table          
public String updateUser(String ID, String firstName, String lastName, String address, String contactNumber, String email, String gender, String password ,String type)
{
       String output = "";
       try
       {
    	   
          Connection con = connect();
          if (con == null){
             return "Error while connecting to the database for updating.";
          }

          // create a prepared statement

          String query = "UPDATE users SET userfirstName=? ,userlastName=? ,useraddress=? ,usercontactNumber=? ,useremail=? ,usergender=? ,userpassword=? ,usertype=? WHERE userID=?";
          PreparedStatement preparedStmt = con.prepareStatement(query);

         // binding values

         preparedStmt.setString(1, firstName);
         preparedStmt.setString(2, lastName);
         preparedStmt.setString(3, address);
         preparedStmt.setString(4, contactNumber);
         preparedStmt.setString(5, email);
         preparedStmt.setString(6, gender);
         preparedStmt.setString(7, password);
         preparedStmt.setString(8, type );
         preparedStmt.setInt(9, Integer.parseInt(ID));

        // execute the statement

        preparedStmt.execute();
        con.close();
        output = "Updated successfully";
        }
        catch (Exception e)
        {
           output = "Error while updating the user.";
           System.err.println(e.getMessage());
        }
        return output;
    }
    //implement dlete Users 
    public String deleteUser(String userID)
    {
        String output = "";
    try
    {
        Connection con = connect();
        if (con == null)
{
        	return "Error while connecting to the database for deleting."; }

       // create a prepared statement
       String query = "delete from users where userID=?";
       PreparedStatement preparedStmt = con.prepareStatement(query);

       // binding values
       preparedStmt.setInt(1, Integer.parseInt(userID));

      // execute the statement
      preparedStmt.execute();
      con.close();
          output = "Deleted successfully";
      }
      catch (Exception e)
      {
    	  
            output = "Error while deleting the user.";
            System.err.println(e.getMessage());
      }
      return output;
      }
    
    
    public static boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet)
    {
        boolean isAllowed = false;
        String role = "" , user = "", psw = "" ;
        
        try {
        	Connection con = connect();
    		if (con == null) {
    			
    			
    		}
    		// create a prepared statement
    		String query = "SELECT * FROM users where userfirstName='"+username+"' and userpassword='"+password+"'";
    		PreparedStatement preparedStmt = con.prepareStatement(query);
    		
    	
    	   	preparedStmt.execute();
    	   	//execute the statement
    		  ResultSet rs = preparedStmt.executeQuery(query);
    		 
    		  if(rs.next()) {
    			  user = rs.getString("userfirstName");
    			  psw = rs.getString("userpassword");
    			  role=rs.getString("usertype");
    			 
    		  }
    		  con.close();
    		  
    		  
    	} catch (Exception e) {
    				
    				System.err.println(e.getMessage());
    			}
        
        if(username.equals(user) && password.equals(psw))
        {
             
            //Step 2. Verify user role
            if(rolesSet.contains(role))
            {
           	 
                isAllowed = true;
                System.out.println(username +" "+password );
            }
        }
        return isAllowed;
        
    }
}

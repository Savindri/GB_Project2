package com;
import model.User;

import javax.annotation.security.RolesAllowed;
//For REST Service
           import javax.ws.rs.*;
           import javax.ws.rs.core.MediaType;

           //For JSON
           import com.google.gson.*;

           //For XML
           import org.jsoup.*;
           import org.jsoup.parser.*;
           import org.jsoup.nodes.Document;
           
@Path("/Users")
public class UserDetails{
	
            //create object for the User class
			User userObj = new User();
			
			@RolesAllowed({"admin"})
			@GET
			@Path("/admin")
			@Produces(MediaType.TEXT_PLAIN)
			public boolean readadmin() {
					
				return true;
				
			}
			
			@RolesAllowed({"buyer"})
			@GET
			@Path("/buyer")
			@Produces(MediaType.TEXT_PLAIN)
			public boolean readbuyer() {
				return true;
			}
			
			@RolesAllowed({"Researcher"})
			@GET
			@Path("/Researcher")
			@Produces(MediaType.TEXT_PLAIN)
			public boolean readresearcher() {
				return true;
			}
			
			
			//Reading all Users
			@RolesAllowed({"admin"})
            @GET
            @Path("/")
            @Produces(MediaType.TEXT_HTML)
            
            public String readUsers()
              {
	              return userObj.readUsers();
	          }

            //Add insertuser for the Table
			@RolesAllowed({"admin","buyer","Researcher"})
            @POST
            @Path("/")
            @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
            @Produces(MediaType.TEXT_PLAIN)
            
            public String insertUser(
            		   @FormParam("userfirstName") String userfirstName,
                       @FormParam("userlastName") String userlastName,
                       @FormParam("useraddress") String useraddress,
                       @FormParam("usercontactNumber") String usercontactNumber,
                       @FormParam("useremail") String useremail,
                       @FormParam("usergender") String usergender,
                       @FormParam("userpassword") String userpassword,
                       @FormParam("usertype") String usertype)
                       {
String output = userObj.insertUser(userfirstName,userlastName, useraddress,usercontactNumber,useremail,usergender,userpassword,usertype);
return output;
}

            //Add Update user 
			@RolesAllowed({"admin","buyer","Researcher"})
            @PUT
            @Path("/")
            @Consumes(MediaType.APPLICATION_JSON)
            @Produces(MediaType.TEXT_PLAIN)
            
            public String updateUser(String userData)
            {
            //Convert the input string to a JSON object
	
            JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject();

            //Read the values from the JSON object

            String userID = userObject.get("userID").getAsString();
            String userfirstName = userObject.get("userfirstName").getAsString();
            String userlastName = userObject.get("userlastName").getAsString();
            String useraddress = userObject.get("useraddress").getAsString();
            String usercontactNumber = userObject.get("usercontactNumber").getAsString();
            String useremail = userObject.get("useremail").getAsString();
            String usergender = userObject.get("usergender").getAsString();
            String userpassword = userObject.get("userpassword").getAsString();
            String usertype = userObject.get("usertype").getAsString();
            String output = userObj.updateUser(userID,userfirstName,userlastName,useraddress,usercontactNumber,useremail,usergender,userpassword,usertype);
            
            return output;
            }

            //Delete User for Specific userID
			@RolesAllowed({"admin","buyer","Researcher"})
            @DELETE
            @Path("/")
            @Consumes(MediaType.APPLICATION_XML)
            @Produces(MediaType.TEXT_PLAIN)
            
            public String deleteUser(String userData)
            {
            	
            //Convert the input string to an XML document
	
            Document doc = Jsoup.parse(userData, "", Parser.xmlParser());

            //Read the value from the element <userID>

            String userID = doc.select("userID").text();
            String output = userObj.deleteUser(userID);
            return output;
            }
}
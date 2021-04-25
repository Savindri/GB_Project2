package com;


import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;
import model.FundController;

//For XML
import org.jsoup.*; 

import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;



@Path("/Fund") 
public class FundService {

//Creating a Object From Controller class
FundController fundC = new FundController();

//@RolesAllowed({"admin"})
//	@GET
//	@Path("/admin/ss") 
//	@Produces(MediaType.TEXT_HTML) 	
//	public String read() { 
//		
//			return "Access granted";
//		}


// =================== Client Fund insertion =====================//
@RolesAllowed({"researcher"})
@POST
@Path("/Client")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_PLAIN) 

//Getting the Fund details from the xml to insert
public String AddNewFund(@FormParam("Duration") String Duration,												     
						 @FormParam("ProjectID") String ProjID)
	{      
			//Passing values to the controller class      
			String output = fundC.insertClientFund(Duration,ProjID);
			
			return output; 
	}

//=================== Client Fund Updation =====================//
@RolesAllowed({"researcher"})
@PUT
@Path("/Client")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN) 
//Getting the Fund details from the xml to Update
public String UpdateClintFundDetails(String Funddate) { 
	
			//Convert the input string to a JSON object 
			JsonObject FundObject = new JsonParser().parse(Funddate).getAsJsonObject(); 
			
			//Read the values from the JSON object
			int FundID1 = FundObject.get("FundID").getAsInt();
			String Fund_duration = FundObject.get("Duration").getAsString(); 
			String Amount = FundObject.get("Fund_amount").getAsString();
			
			//Passing values to the controller class       
			String output = fundC.updateClientFundReq(FundID1, Fund_duration,Amount); 
			return output;
	
	
}

//===================Retriving Fund Details  =====================//
    @RolesAllowed({"admin"})
	@GET
	@Path("/admin") 
	@Produces(MediaType.TEXT_HTML) 	
	public String readFunddetails() { 
		
			return fundC.readFundDetails();
		}
    
//=================== Retriving particular Fund Details  =====================//	
    @RolesAllowed({"researcher"})
    @GET
	@Path("/Client/{FundID}") 
	@Produces(MediaType.TEXT_HTML) 	
	public String readFunddetailss(@PathParam("FundID") String ID) { 
				
			return fundC.SearchFund(ID);
			  
			} 
	
//=================== Admin Update Fund Details  =====================//	
    @RolesAllowed({"admin"})
	@PUT
	@Path("/admin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN) 
	//Getting the Fund details from the xml to Update
	public String UpdateFundDetails(String Funddate) { 
		
		//Convert the input string to a JSON object 
		JsonObject FundObject = new JsonParser().parse(Funddate).getAsJsonObject(); 
		
		//Read the values from the JSON object
		int FundID1 = FundObject.get("FundID").getAsInt();
		String Fund_announcement = FundObject.get("Announcement").getAsString();
		String Fund_duration = FundObject.get("Duration").getAsString(); 
		String instructions = FundObject.get("Intructions").getAsString(); 
		String Amount = FundObject.get("Fund_amount").getAsString();
		
		//Passing values to the controller class       
		String output = fundC.UpdateFund(FundID1, Fund_announcement, Fund_duration, instructions,Amount);
		
		return output;
		
		
	}
	
	//=================== Deleting particular Fund  =====================//	
    @RolesAllowed({"researcher"})
	@DELETE
	@Path("/Client")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String DeleteFundDetails(String FundData)
	{
	
		//Convert the input string to an XML document	
		Document doc = Jsoup.parse(FundData, "", Parser.xmlParser());
	
		//Read the value from the element <itemID	
		String FundID = doc.select("FundID").text();
		//Passing the ID to the controller class 
		String output =FundController.deleteFund(FundID);
		
		
		return output;
	}
}

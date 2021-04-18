package com;

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

FundController fundC = new FundController();
	
@GET
@Path("/retID") 
@Produces(MediaType.TEXT_HTML) 	
public String retID() { 
	
	return fundC.retval();
	
	} 


	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 	
	public String readFunddetails() { 
		
		return fundC.readFundDetails();
		} 
	
	@GET
	@Path("/{FundID}") 
	@Produces(MediaType.TEXT_HTML) 	
	public String readFunddetails(@PathParam("FundID") int FundID) { 
		
		 return fundC.readFundDetails(FundID);
		} 
	
	@POST
	@Path("/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN) 
	
	//Getting the Fund details from the xml to insert
	public String AddNewFund(@FormParam("FundAnnounce") String FundAnnounce,
							 @FormParam("Duration") String Duration,
							 @FormParam("instructions") String instructions,
						     @FormParam("FundAmount") String amount) 
		{ 
				String output = fundC.insertFund(FundAnnounce,Duration,instructions,amount);
				
				return output; 
		}
	
	
	@PUT
	@Path("/")
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
		
		       
		String output = fundC.updateItem(FundID1, Fund_announcement, Fund_duration, instructions); 
		return output;
		
		
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String DeleteFundDetails(String FundData)
	{
	
		//Convert the input string to an XML document	
		Document doc = Jsoup.parse(FundData, "", Parser.xmlParser());
	
		//Read the value from the element <itemID	
		String FundID = doc.select("FundID").text();
		
		String output =FundController.deleteFund(FundID);
		
		
		return output;
	}
}

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

public class FundService {

FundController fundC = new FundController();
	
	
	@GET@Path("/") 
	@Produces(MediaType.TEXT_HTML) 	
	public String readFunddetails() { 
		
		return "";
		} 
	
	@POST
	@Path("/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN) 
	
	//Getting the Fund details from the xml to insert
	public String AddNewFund(@FormParam("CustomerId") String a,  
							 @FormParam("ProjectID") String b,
						     @FormParam("") String c,   
							 @FormParam("") String d) { 
		
		return ""; 
		}
	
	
	@PUT
	@Path("/Funds")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN) 
	//Getting the Fund details from the xml to Update
	public String UpdateFundDetails(String FundID) { 
		
		//Convert the input string to a JSON object 
		JsonObject FundObject = new JsonParser().parse(FundID).getAsJsonObject(); 
		
		//Read the values from the JSON object
		String a = FundObject.get("a").getAsString();
		String b = FundObject.get("b").getAsString();
		String c = FundObject.get("c").getAsString(); 
		String d = FundObject.get("d").getAsString();
		       
		return ""; 
		
	}
	
	@DELETE
	@Path("/Fun")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String DeleteFundDetails(String FundID)
	{
	
		//Convert the input string to an XML document
	
		Document doc = Jsoup.parse(FundID, "", Parser.xmlParser());
	
		//Read the value from the element <itemID>
	
		String itemID = doc.select("FundID").text();	
		
		return "";
	}
}
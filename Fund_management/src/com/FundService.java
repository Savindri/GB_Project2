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
	public String AddNewFund(@FormParam("ProjectID") String ProjectID,
							 @FormParam("FundDesc") String FundDesc,
						     @FormParam("FundAmount") String amount) 
		{ 
				String output = fundC.insertFund(ProjectID, FundDesc, amount);
				
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
		String ProjectID = FundObject.get("ProjectID").getAsString();
		String FundDesc = FundObject.get("FundDesc").getAsString(); 
		String Fundstatus = FundObject.get("Fundstatus").getAsString();
		       
		String output = fundC.updateItem(FundID1, ProjectID, FundDesc, Fundstatus); 
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
		
		String output =fundC.deleteFund(FundID);
		
		
		return output;
	}
}

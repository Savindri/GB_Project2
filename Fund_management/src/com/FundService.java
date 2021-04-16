package com;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces; 


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
	@Path("/read/{FundID}") 
	@Produces(MediaType.TEXT_HTML) 	
	public String readFunddetails(int FundID) { 
		
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
	
//	@DELETE
//	@Path("/Delete")
//	@Consumes(MediaType.APPLICATION_XML)
//	@Produces(MediaType.TEXT_PLAIN)
//	public String DeleteFundDetails(String FundID)
//	{
//	
//		//Convert the input string to an XML document
//	
//		Document doc = Jsoup.parse(FundID, "", Parser.xmlParser());
//	
//		//Read the value from the element <itemID>
//	
//		String itemID = doc.select("FundID").text();	
//		
//		return "";
//	}
}

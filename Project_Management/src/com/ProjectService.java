package com;

import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 

import com.google.gson.*;

import model.Project;

import org.jsoup.*;
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;

@Path("/project") 
public class ProjectService {

	Project projectObj = new Project();
	
	//read
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_PLAIN) 
	public String readProject() 
	 { 
	 	
		return projectObj.readProject();
	 } 
	
	
	//insert
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertProject(String projectData){
		
		//Convert the input string to a JSON object
		JsonObject projectObject = new JsonParser().parse(projectData).getAsJsonObject();
		
		//Read the values from the JSON object
		String projectName = projectObject.get("projectName").getAsString(); 
		String budget = projectObject.get("budget").getAsString();
		String completionDate = projectObject.get("completionDate").getAsString();
		String productCategory = projectObject.get("productCategory").getAsString();
		String sellOrNot = projectObject.get("sellOrNot").getAsString();
		String description = projectObject.get("description").getAsString();
		String output = projectObj.insertProject(projectName, budget, completionDate, productCategory, sellOrNot, description);
		
		return output; 
		
	
	}
	
	
	
	//delete
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProject(String projectData){ 
		
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(projectData, "", Parser.xmlParser());		 
		
		 //Read the value from the element <prposal_ID>
		 String proposal_ID = doc.select("proposal_ID").text(); 
		 String output = projectObj.deleteProject(proposal_ID); 
		 return output; 
	}
	
	
	
	//update
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateProject(String projectData) { 
		
		//Convert the input string to a JSON object 
		 JsonObject projectObject = new JsonParser().parse(projectData).getAsJsonObject(); 
		
		 //Read the values from the JSON object
		 String proposal_ID = projectObject.get("proposal_ID").getAsString(); 
		 String projectName = projectObject.get("projectName").getAsString(); 
		 String budget = projectObject.get("budget").getAsString();
		 String completionDate = projectObject.get("completionDate").getAsString();
		 String productCategory = projectObject.get("productCategory").getAsString();
		 String sellOrNot = projectObject.get("sellOrNot").getAsString();
		 String description = projectObject.get("description").getAsString();
		 String output = projectObj.updateProject(proposal_ID, projectName, budget, completionDate, productCategory, sellOrNot, description); 
		 return output; 
	
	}

	
}

package com;

import javax.annotation.security.RolesAllowed;
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
	
	
		//retrieve for researcher filter by the proposal ID
		@RolesAllowed({"Researcher"})
		@GET
		@Path("/researcher/{proposal_ID}") 
		@Produces(MediaType.TEXT_HTML) 
		public String readProjectID(@PathParam("proposal_ID") String ID) 
		{ 
	 	
			return projectObj.readProjectByID(ID);
		}
		
		
		//retrieve all projects for admin
		@RolesAllowed({"admin"})
		@GET
		@Path("/admin") 
		@Produces(MediaType.TEXT_HTML) 
		public String readProjects() 
		{ 
	 	
			return projectObj.readProject();
		}

	
	
	//insert
	@RolesAllowed({"Researcher"})
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
		String userID = projectObject.get("userID").getAsString();
		String output = projectObj.insertProject(projectName, budget, completionDate, productCategory, sellOrNot, description,userID);
		
		return output; 
		
	
	}
	
	
	
	//delete for admin
	@RolesAllowed({"admin"})
	@DELETE
	@Path("/admin")
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
	
	
	
	//delete for researcher
	@RolesAllowed({"Researcher"})
	@DELETE
	@Path("/researcher")
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProject2(String projectData){ 
		
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(projectData, "", Parser.xmlParser());		 
		
		 //Read the value from the element <prposal_ID>
		 String proposal_ID = doc.select("proposal_ID").text(); 
		 String output = projectObj.deleteProject(proposal_ID); 
		 return output; 
	}
	
	
	//update status for admin
	@RolesAllowed({"admin"})
	@PUT
	@Path("/admin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateProjectManager(String projectData) { 
		
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
		 String status = projectObject.get("status").getAsString();
		 String output = projectObj.updateProjectManager(proposal_ID, projectName, budget, completionDate, productCategory, sellOrNot, description,status); 
		 return output; 
	
	}
	
	//update for researcher
	@RolesAllowed({"Researcher"})
	@PUT
	@Path("/researcher")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateProjectManager2(String projectData) { 
		
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
		 String status = projectObject.get("status").getAsString();
		 String output = projectObj.updateProjectManager(proposal_ID, projectName, budget, completionDate, productCategory, sellOrNot, description,status); 
		 return output; 
	
	}
	
	
	@RolesAllowed({"admin","Researcher"})
	@GET
	@Path("/retID/{id}") 	
	@Produces(MediaType.TEXT_PLAIN) 	
	public String returnPrices(@PathParam("id") String ID) { 
		
		return projectObj.retval(ID);
		
		}

	
}

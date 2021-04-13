package com;

import javax.ws.rs.GET; 
import javax.ws.rs.Path; 
import javax.ws.rs.Produces; 
import javax.ws.rs.core.MediaType; 

@Path("/ProjectService") 
public class ProjectService {

	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_PLAIN) 
	public String project() 
	 { 
	 return "Project service is up and running."; 
	 } 
}

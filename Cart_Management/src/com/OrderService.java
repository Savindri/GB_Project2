package com;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON - read the JSON input as a String and parse it using gson library
import com.google.gson.*;

import model.OrderController;

//For XML - use jsoup library to parse XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

//To implement the RESTful API

@Path("/Order") 
public class OrderService {
	
	OrderController orderObj = new OrderController(); 
	
	//to read order
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readOrder() { 
		return orderObj.readOrder(); 
	}
	
	


}

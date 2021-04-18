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
	
	
	//to insert order
	@POST
	@Path("/") 
	//to specify the input type as form data
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//to produce a status message as an output in plain text
	@Produces(MediaType.TEXT_PLAIN) 
	//to specify the form elements as the parameters to the insertOrder() method
	public String insertOrder(@FormParam("date") String date, 
								 @FormParam("custName") String custName, 
								 @FormParam("address") String address, 
								 @FormParam("phone") String phone,
								 @FormParam("email") String email){ 
		String output = orderObj.insertOrder(date, custName, address, phone, email); 
		return output; 
	}	
	
	
	//to update order
	@PUT
	@Path("/")
	//accept the input as JSON
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateOrder(String orderData) { 
		//Convert the input string to a JSON object 
		 JsonObject orderObject = new JsonParser().parse(orderData).getAsJsonObject(); 
		//Read the values from the JSON object
		 String orderID = orderObject.get("orderID").getAsString();
		 String cartID_f = orderObject.get("cartID_f").getAsString();
		 String date = orderObject.get("date").getAsString(); 
		 String custName = orderObject.get("custName").getAsString(); 
		 String address = orderObject.get("address").getAsString(); 
		 String phone = orderObject.get("phone").getAsString();
		 String email = orderObject.get("email").getAsString();
		 //String total = orderObject.get("total").getAsString();
		 String output = orderObj.updateOrder(orderID, cartID_f, date, custName, address, phone, email/*, total*/); 
		 return output; 
	}
	
	
	//to delete order
	@DELETE
	@Path("/")
	//use XML for the input
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	//read the XML dataset as a string
	public String deleteOrder(String orderData){ 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(orderData, "", Parser.xmlParser());		 
		//Read the value from the element <orderID>
		 String orderID = doc.select("orderID").text(); 
		 String output = orderObj.deleteOrder(orderID); 
		 return output; 
	}


}

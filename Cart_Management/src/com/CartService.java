package com;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON - read the JSON input as a String and parse it using gson library
import com.google.gson.*;

import model.CartController;

//For XML - use jsoup library to parse XML
import org.jsoup.*;
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;

//==========================================================To implement the RESTful API==========================================================


@Path("/Cart")
public class CartService {
	
		CartController cartObj = new CartController();
	
	
		//=========================to read cart========================================================
		@GET
		@Path("/") 
		@Produces(MediaType.TEXT_HTML) 
		public String readCart() { 
			return cartObj.readCart(); 
		}
	
	
		//=======================to insert into cart===================================================
		@POST
		@Path("/") 
		//to specify the input type as form data
		@Consumes(MediaType.APPLICATION_JSON)
		//to produce a status message as an output in plain text
		@Produces(MediaType.TEXT_PLAIN)
		public String insertToCart(String cartData){
			//Convert the input string to a JSON object
			JsonObject cartObject = new JsonParser().parse(cartData).getAsJsonObject();
			//Read the values from the JSON object
			String uID = cartObject.get("uID").getAsString();
			String pro_ID = cartObject.get("pro_ID").getAsString();
			String quantity = cartObject.get("quantity").getAsString();
			String output = cartObj.insertToCart(uID, pro_ID, quantity);
			return output; 
		}
	
	
		//============================to update cart====================================
		@PUT
		@Path("/")
		//accept the input as JSON
		@Consumes(MediaType.APPLICATION_JSON)
		//to produce a status message as an output in plain text
		@Produces(MediaType.TEXT_PLAIN) 
		public String updateCart(String cartData) { 
			//Convert the input string to a JSON object 
			 JsonObject cartObject = new JsonParser().parse(cartData).getAsJsonObject(); 
			//Read the values from the JSON object
			 String cartID = cartObject.get("cartID").getAsString();
			 String pro_ID = cartObject.get("pro_ID").getAsString(); 
			 String quantity = cartObject.get("quantity").getAsString();
			 String output = cartObj.updateCart(cartID, pro_ID, quantity); 
			 return output; 
		}
	
	
		//========================to delete items from cart============================
		@DELETE
		@Path("/")
		//use XML for the input
		@Consumes(MediaType.APPLICATION_XML)
		//to produce a status message as an output in plain text
		@Produces(MediaType.TEXT_PLAIN) 
		//read the XML dataset as a string
		public String deletFromeOrder(String cartData){ 
			//Convert the input string to an XML document
			 Document doc = Jsoup.parse(cartData, "", Parser.xmlParser());		 
			//Read the value from the element <orderID>
			 String cartID = doc.select("cartID").text(); 
			 String output = cartObj.deleteFromCart(cartID); 
			 return output; 
		}
}
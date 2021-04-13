package com;

import model.store; 

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 

//For JSON
import com.google.gson.*; 

//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

//read operation
@Path("/Test")
public class storeService {
	store strObj = new store(); 
		
		@GET
		@Path("/") 
		@Produces(MediaType.TEXT_HTML) 
		public String readStore()
		{ 
			return strObj.readProduct(); 
		}
//insert operation
		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String insertProduct(@FormParam("pro_ID") String pro_ID,
									@FormParam("desc") String desc,
									@FormParam("qty") String qty,
									@FormParam("price") String price,
									@FormParam("category") String category)
		{
			String output = strObj.insertProduct(pro_ID, desc, qty, price, category);
			return output;
		}
}

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
		
//update operation
		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String updateProduct(String productData)
		{
			
			//Convert the input string to a JSON object
			JsonObject productObject = new JsonParser().parse(productData).getAsJsonObject();
			
			//Read the values from the JSON object
			String pro_ID = productObject.get("itemID").getAsString();
			String desc = productObject.get("itemCode").getAsString();
			String qty = productObject.get("itemName").getAsString();
			String price = productObject.get("itemPrice").getAsString();
			String category = productObject.get("itemDesc").getAsString();
			
			String output = strObj.updateProduct(pro_ID, desc, qty, price, category);
			
			return output;
		}
		
//delete operation
		@DELETE
		@Path("/")
		@Consumes(MediaType.APPLICATION_XML)
		@Produces(MediaType.TEXT_PLAIN)
		public String deleteProduct(String productData)
		{
			
			//Convert the input string to an XML document
			Document doc = Jsoup.parse(productData, "", Parser.xmlParser());
			
			//Read the value from the element <pro_ID>
			String pro_ID = doc.select("pro_ID").text();
			
			String output = strObj.deleteProduct(pro_ID);
			
			return output;
		}
}

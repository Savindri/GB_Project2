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


@Path("/pro")
public class storeService {
	store strObj = new store(); 
//read operation		
		@GET
		@Path("/product") 
		@Produces(MediaType.TEXT_HTML) 
		public String readStore()
		{ 
			return strObj.readStore(); 
		}
//read operation only one product		
		@GET
		@Path("/{pro_ID}") 
		@Produces(MediaType.TEXT_HTML) 
		public String readOrderByID(@PathParam("pro_ID") String pro_ID)
			{ 
				return strObj.readOrderByID(pro_ID);
			}
		
//insert operation
		@POST
		@Path("/product")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String insertProduct(@FormParam("proCode") String proCode,
									@FormParam("desc") String desc,
									@FormParam("qty") String qty,
									@FormParam("price") String price,
									@FormParam("category") String category)
		{
			String output = strObj.insertProduct(proCode, desc, qty, price, category);
			return output;
		}
		
//update operation
		@PUT
		@Path("/product")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String updateProduct(String productData)
		{
			
			//Convert the input string to a JSON object
			JsonObject productObject = new JsonParser().parse(productData).getAsJsonObject();
			
			//Read the values from the JSON object
			String pro_ID = productObject.get("pro_ID").getAsString();
			String proCode = productObject.get("proCode").getAsString();
			String desc = productObject.get("desc").getAsString();
			String qty = productObject.get("qty").getAsString();
			String price = productObject.get("price").getAsString();
			String category = productObject.get("category").getAsString();
			
			String output = strObj.updateProduct(pro_ID, proCode, desc, qty, price, category);
			
			return output;
		}
		
//delete operation
		@DELETE
		@Path("/product")
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
//=======================read product details related to a specific product ID============================
		@GET
		@Path("price/{pro_ID}") 
		@Produces(MediaType.TEXT_HTML) 	
		public String readProduct(@PathParam("pro_ID") String pro_ID)
		{ 
			
		 //String id = Integer.toString(pro_ID);
			return strObj.readUnitPrice(pro_ID);
		} 


}

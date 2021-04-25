package com;

import java.lang.reflect.Method;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;


import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;

@Provider
public class SecurityFilter implements ContainerRequestFilter {
	
	public static final String AUTHENTICATION_HEADER_KEY = "Authorization";
	public static final String AUTHENTICATION_HEADER_PREFIX = "Basic ";

	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub
		
		//Accessing the URL Header
		List<String> authHeader = requestContext.getHeaders().get(AUTHENTICATION_HEADER_KEY);
		Method method = resourceInfo.getResourceMethod();

		//Checking the annotation is denied all
		if (!method.isAnnotationPresent(PermitAll.class)) {
			
		
			if (method.isAnnotationPresent(DenyAll.class)) {
				
				//The request has not been applied because it lacks valid authentication credentials for the target resource.
				Response unauthoriazedStatus = Response.status(Response.Status.UNAUTHORIZED).entity(" Status 1 - Access denied").build();
						
				
				requestContext.abortWith(unauthoriazedStatus);
			}
			 
			if (authHeader != null && authHeader.size() > 0) {
				
				String authToken = authHeader.get(0);
				authToken = authToken.replaceFirst(AUTHENTICATION_HEADER_PREFIX, "");

				String decodedString = "";
				
				try {
					
					byte[] decodedBytes = Base64.getDecoder().decode(authToken);
					decodedString = new String(decodedBytes, "UTF-8");
				} catch (IOException e) {
					e.printStackTrace();
				}
				final StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");

				final String username = tokenizer.nextToken();
				final String password = tokenizer.nextToken();
				
				if (method.isAnnotationPresent(RolesAllowed.class)) {
					RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
					Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

					// Is user valid?

					ClientConfig clientConfig = new ClientConfig();
					HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);
					clientConfig.register(feature);

					clientConfig.register(JacksonFeature.class);

					Client client = ClientBuilder.newClient(clientConfig);
					WebTarget webTarget = null;

					//Checking the return value is euqal to the user type when access the user management service
					//http://localhost:8080/TestLast/AuthService
					//http://localhost:8080/User_Management/myService
					if (rolesSet.contains("admin")) {
						webTarget = client.target("http://localhost:8080/User_Management/myService")
								.path("auth/auths");

					} else if (rolesSet.contains("buyer")) {
						webTarget = client.target("http://localhost:8080/User_Management/myService").path("Users/buyer");
					} //else if (rolesSet.contains("admin")) {
//						webTarget = client.target("http://localhost:8080/AuthService/AuthService").path("users/admin");
//					} else {
//						webTarget = client.target("http://localhost:8080/AuthService/AuthService").path("users/deny");
//					}

					Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);

					Response response = invocationBuilder.get();

					// disallow access to improper URL
					if (response.getStatus() != 200) {
						Response unauthoriazedStatus = Response.status(Response.Status.UNAUTHORIZED)
								.entity("{\"error\" : \"not authorized 3\"}").build();
						requestContext.abortWith(unauthoriazedStatus);

					}
					return;
				}

			}
		}
		// disallow access to improper URLs
		Response unauthoriazedStatus = Response.status(Response.Status.UNAUTHORIZED)
				.entity("Status 3 - Access denied").build();
		requestContext.abortWith(unauthoriazedStatus);

	}

}
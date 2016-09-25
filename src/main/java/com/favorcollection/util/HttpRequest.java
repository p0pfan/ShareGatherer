package com.favorcollection.util;

import java.util.Map;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class HttpRequest {
	
	public static String doGet(String url, Map<String,Object> params){
		
		ClientConfig clientConfig =  new DefaultClientConfig();
		Client client = Client.create(clientConfig);
		 
	    ClientResponse clientResponse = null;
	    String entity =null;
	    try{
	    	WebResource webResource = client.resource(url);
	    	MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
	    	for(Map.Entry<String, Object> p : params.entrySet()){
	    		queryParams.add(p.getKey(), p.getValue());
	    	}
	    	clientResponse = webResource.queryParams(queryParams).get(ClientResponse.class); 
	    	if (clientResponse.getStatus() != 200) {
	  		  throw new Exception("{\"status\":\"failed\",\"message\":\"HTTP error code :"
	  			+ clientResponse.getStatus()+"\"}");
	  		}
	    	
	    	entity = "{\"status\":\"success\",\"message\":"+ clientResponse.getEntity(String.class)+"}";
	    	System.out.println(entity);
		} catch (Exception e) {
			entity = e.getMessage();
		}finally{
			if(clientResponse != null){
				clientResponse.close();
			}
			if(client != null){
				client.destroy();
			}
		}
	    return entity;
	}
	
	public static String doPost(String url){
		return null;
	}

}

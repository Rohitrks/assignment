package com.mytaxi.utilities;

import org.apache.log4j.Logger;
import org.testng.Assert;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;


public class RestApiActions extends CommonUtilities {
    
	public static String responseData = "";
	public static Response response = null;
	protected static final Logger log = Logger.getLogger(RestApiActions.class);
	
	/**
	 * Used to get HTTP_GET response
	 * @param uri
	 * @return String
	 */
	public static String getGETResponse(String uri) {
		try {

			if(uri!=null){
				log.info("Requested URI : "+uri);
				response = RestAssured.given().contentType("application/json").when().get(uri);
				responseData = response.getBody().asString();
				log.info("\nResponse Body :  \n"+responseData+"\n");
			}else{
				log.error("URI Can not be null");
			}
		} catch (Exception e) {
			Assert.fail("Not getting any Response from Server", e);
		}
		return responseData;
	}
	
	
}

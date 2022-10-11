package com.metadata;

//import static com.jayway.restassured.RestAssured.given;

import java.util.Properties;

import org.json.JSONObject;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

//import com.jayway.restassured.RestAssured;
//import com.jayway.restassured.response.Response;
//import com.jayway.restassured.specification.RequestSpecification;




public class HiPiResponseInstance {
	
	public static Response resp = null;

	static Properties FEProp = new Properties();

	public static String username = null;
	public static String password = null;
	
	
	public static Response getResponse() {
		resp = given().urlEncodingEnabled(false).when().get(
				"https://hipigwapi.zee5.com/api/v1/shorts/home?limit=20&type=forYou&offset=1&cLang=en");
		return resp;
	}
	
	public static Response getResponse(String URL) {
		resp = given().urlEncodingEnabled(false).when().get(URL);
		return resp;
	}
	
	/**
	 * Function to return X-ACCESS TOKEN
	 * 
	 * @param page
	 * @return
	 */
	public static String getXAccessToken() {
		Response resp = null;
		String xAccessToken = "";
		String url = "https://hipigwapi.zee5.com/api/v1/shorts/home?cLang=en&limit=20&offset=1&type=forYou";
		resp = given().urlEncodingEnabled(false).when().get(url);
		//.urlEncodingEnabled(false)
		String respString = resp.getBody().asString();
		respString = respString.replace("\"gwapiPlatformToken\":\"\"", "");
		respString = respString.split("\"gwapiPlatformToken\":\"")[1];
		xAccessToken = respString.split("\"")[0];
		return xAccessToken;
	}
	
	/**
	 * Function to return Bearer token
	 * 
	 * @param email, password
	 * @return bearer token
	 */
	public static String getBearerToken(String email, String password) {
		JSONObject requestParams = new JSONObject();
		requestParams.put("email", email);
		requestParams.put("password", password);
		RequestSpecification req = RestAssured.given();
		req.header("Content-Type", "application/json");
		req.config(io.restassured.RestAssured.config().encoderConfig(io.restassured.config.EncoderConfig
				.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));
		req.body(requestParams.toString());
		//https://userapi.zee5.com/v2/user/loginemail
		Response resp = req.post("https://userapi.zee5.com/v2/user/loginemail");
		String accesstoken = resp.jsonPath().getString("access_token");
		String tokentype = resp.jsonPath().getString("token_type");
		String bearerToken = tokentype + " " + accesstoken;
		
		
		return bearerToken;
	}
	
	
	
	/**
	 * Function to return response for different pages
	 * 
	 * @param page
	 * @return
	 */
	public static Response getResponseForPages(String page) {
		Response respCarousel = null;
		String Uri = "";
		 if (page.equals("profile")) {
			Uri="https://hipigwapi.zee5.com/api/v1/shorts/profile?limit=20&type=forYou&offset=1&cLang=en";
		} else if (page.equals("home")) {
			Uri="https://hipigwapi.zee5.com/api/v1/shorts/home?limit=20&type=forYou&offset=1&cLang=en";
		} else if (page.equals("discover")) {
			Uri = "https://hipigwapi.zee5.com/api/v2/shorts/discover?offset=1&limit=5";
		}
		
		System.out.println(getXAccessToken());
		respCarousel = RestAssured.given().headers("X-ACCESS-TOKEN", getXAccessToken()).when().get(Uri);
		System.out.println("Response status : " + respCarousel.statusCode());
		// System.out.println("Response Body : "+respCarousel.getBody().asString());
		return respCarousel;
	}
	
	//https://hipigwapi.zee5.com/api/v1/shorts/home?limit=20&type=forYou&offset=1&cLang=en
	//https://hipigwapi.zee5.com/api/v2/shorts/discover?offset=1&limit=5
	//https://gwapi.zee5.com/shorturl/create
	
	
	
	

}

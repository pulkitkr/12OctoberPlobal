package com.extent;

//import static com.jayway.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.xml.XmlSuite;
import static io.restassured.RestAssured.*;

import io.restassured.response.Response;

//import com.jayway.restassured.response.Response;

import org.testng.IAlterSuiteListener;

public class ParameterInjector implements IAlterSuiteListener  {
	
	@Override
	public void alter(List<XmlSuite> suites) {
		XmlSuite suite = suites.get(0);
		Map<String, String> params = new HashMap<>();	
		
		params.put("browserType", suite.getParameter("browserType"));
		params.put("userType", suite.getParameter("userType"));
		params.put("NonsubscribedUserName", suite.getParameter("NonsubscribedUserName"));
		params.put("NonsubscribedPassword", suite.getParameter("NonsubscribedPassword"));
		params.put("SubscribedUserName", suite.getParameter("SubscribedUserName"));
		params.put("SubscribedPassword", suite.getParameter("SubscribedPassword"));
		params.put("devicePin", suite.getParameter("devicePin"));
		params.put("runModule", suite.getParameter("runModule"));
		params.put("runMode", suite.getParameter("runMode"));
		
		if(suite.getParameter("url").equals("newpwa")) {
			params.put("url", "https://newpwa.zee5.com/");
		}else if(suite.getParameter("url").equals("prod")) {
			params.put("url", "https://www.zee5.com/");
		}else if(suite.getParameter("url").equals("preprod")) {
			params.put("url", "https://pwa-preprod2.zee5.com/");
		}
		
		Response regionResponse=given().urlEncodingEnabled(false).when().get("https://xtra.zee5.com/country");
		String region=regionResponse.getBody().jsonPath().getString("state_code");
		System.out.println("Region : "+region);
		if(region.equals("KA")) {
			params.put("searchModuleSearchKey", "Kamali");
		}
		if(region.equals("MH")) {
			params.put("searchModuleSearchKey", "Kundali Bhagya");
		}
        suite.setParameters(params);	
	}
}

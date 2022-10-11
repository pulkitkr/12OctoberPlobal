package com.metadata;

//import static com.jayway.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;
import org.testng.Reporter;
import static io.restassured.RestAssured.*;

import com.appsflyerValidation.AppsFlyer;
//import com.jayway.restassured.RestAssured;
//import com.jayway.restassured.response.Response;
//import com.jayway.restassured.specification.RequestSpecification;
import com.propertyfilereader.PropertyFileReader;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Properties;
import java.util.stream.Stream;
import java.text.ParseException;

public class ResponseInstance {

	protected static Response resp = null;

	static Properties FEProp = new Properties();

	public static String username = null;
	public static String password = null;
	public static String AdID = "7e128be0-8f02-4eb4-93e4-e7382eb01d82";
	
	public static Response getResponse() {
		resp = given().urlEncodingEnabled(false).when().get(
				"https://hipigwapi.zee5.com/api/v1/shorts/home?limit=20&type=forYou&offset=1&cLang=en");
		return resp;
	}

	public static Response getResponse(String URL) {
		resp = given().urlEncodingEnabled(false).when().get(URL);
		return resp;
	}

	public static void traysTitle() {
		int numberOfTrays = getResponse().jsonPath().getList("buckets").size();
		for (int i = 1; i < numberOfTrays; i++) {
			System.out.println(getResponse().jsonPath().getString("buckets[" + i + "].title"));
		}
	}

	public static ArrayList<String> traysIndiviualTags(String NameOfTheTray) {
		ArrayList<String> Arraytags = new ArrayList<String>();
		int numberOfTrays = getResponse().jsonPath().getList("buckets").size();
		for (int i = 1; i < numberOfTrays; i++) {
			String TrayName = getResponse().jsonPath().getString("buckets[" + i + "].title");
			int numberOfitmesTrays = getResponse().jsonPath().getList("buckets[" + i + "].items").size();
			if (TrayName.equals(NameOfTheTray)) {
				for (int j = 0; j < numberOfitmesTrays; j++) {
					Arraytags.add(getResponse().jsonPath().getString("buckets[" + i + "].items[" + j + "].title"));
				}
				break;
			}
			if (Arraytags.size() > 0) {
				break;
			}
		}
		return Arraytags;
	}

	public static ArrayList<String> ContentOfViewAll() {
		ArrayList<String> contentFromViewAll = new ArrayList<String>();
		for (int i = 1; i <= 3; i++) {
			String page1 = "https://gwapi.zee5.com/content/collection/0-8-2074?page=" + i
					+ "&limit=20&desc=no&country=IN&languages=en,kn&translation=en&version=6";
			resp = given().urlEncodingEnabled(false).when().get(page1);
			int sizeOfAnItem = resp.jsonPath().getList("buckets[0].items").size();
			if (sizeOfAnItem > 0) {
				for (int j = 0; j < sizeOfAnItem; j++) {
					contentFromViewAll.add(resp.jsonPath().getString("buckets[0].items[" + j + "].title"));
					System.out.println(contentFromViewAll);
				}
			}
		}
		return contentFromViewAll;
	}

	public static ArrayList<String> getCollectionContent(String CollectionName) {

		ArrayList<String> contentList = new ArrayList<String>();
		int sizeOfAnCollection = 0;
		int NumberOfCollection = resp.jsonPath().getList("buckets[0].items").size();
		for (int i = 0; i < NumberOfCollection; i++) {
			if (resp.jsonPath().getString("").equals(CollectionName)) {
				sizeOfAnCollection = resp.jsonPath().getList("buckets[0].items").size();
				for (int j = 0; j < sizeOfAnCollection; j++) {
					contentList.add(resp.jsonPath().getString(""));
				}
			}
			if (sizeOfAnCollection > 0) {
				break;
			}
		}
		return contentList;
	}

//	BASAVARAJ
	public static Response getRECOResponse(String URL, String username, String password) {
		Response respp = null;
		String Uri = URL;
		respp = given()
				.headers("X-ACCESS-TOKEN", getXAccessToken(), "Authorization", getBearerToken(username, password))
				.when().get(Uri);
		System.out.println(respp.getBody());
		return respp;
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
		String url = "https://hipigwapi.zee5.com/";
		resp = given().urlEncodingEnabled(false).when().get(url);
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
	 * Get Recommendation data for the tab
	 * 
	 * @param tab
	 * @return reco response
	 */
	public static Response getRecoDataFromTab(String userType, String tab, String contLang) {
		Response respReco = null;
		if (tab.equalsIgnoreCase("shows")) {
			tab = "tvshows";
		} else if (tab.equalsIgnoreCase("premium")) {
			tab = "premiumcontents";
		} else if (tab.equalsIgnoreCase("home")) {
			tab = "homepage";
		} else if (tab.equalsIgnoreCase("kids")) {
			tab = "3673";
		} else if (tab.equalsIgnoreCase("videos")) {
			tab = "5011";
		} else if (tab.equalsIgnoreCase("movies")) {
			tab = "movies";
		} else if (tab.equalsIgnoreCase("music")) {
			tab = "2707";
		} else if (tab.equalsIgnoreCase("zeeoriginals")) {
			tab = "zeeoriginals";
		} else if (tab.equalsIgnoreCase("news")) {
			tab = "626";
		}
		Response regionResponse = given().urlEncodingEnabled(false).when().get("https://xtra.zee5.com/country");
		String region = regionResponse.getBody().jsonPath().getString("state_code");
		String Uri = "https://gwapi.zee5.com/content/reco?country=IN&translation=en&languages=" + contLang
				+ "&version=6&collection_id=0-8-" + tab + "&region=" + region;
		System.out.println("Hitting api:\n" + Uri);
		String xAccessToken = getXAccessToken();
		if (userType.equalsIgnoreCase("Guest")) {
			// Get Guest Token
			JSONObject requestParams = new JSONObject();
			requestParams.put("aid", "91955485578");
			requestParams.put("apikey", "6BAE650FFC9A3CAA61CE54D");
			requestParams.put("DekeyVal", "Z5G211");
			requestParams.put("UserAgent", "pwaweb");
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type", "application/json");
			request.config(io.restassured.RestAssured.config()
					.encoderConfig(io.restassured.config.EncoderConfig.encoderConfig()
							.appendDefaultContentCharsetToContentTypeIfUndefined(false)));
			request.body(requestParams.toString());
			Response response = request.post("https://whapi-prod-node.zee5.com/fetchGuestToken");
			String guestToken = response.jsonPath().get("guest_user").toString();
			respReco = given().headers("x-access-token", xAccessToken).header("x-z5-guest-token", guestToken).when()
					.get(Uri);
		} else if (userType.equalsIgnoreCase("SubscribedUser")) {
			String email = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("SubscribedUserName");
			String password = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("SubscribedPassword");
			String bearerToken = getBearerToken(email, password);
			respReco = given().headers("x-access-token", xAccessToken).header("authorization", bearerToken).when()
					.get(Uri);
		} else if (userType.equalsIgnoreCase("NonSubscribedUser")) {
			String email = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("NonsubscribedUserName");
			String password = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("NonsubscribedPassword");
			String bearerToken = getBearerToken(email, password);
			respReco = given().headers("x-access-token", xAccessToken).header("authorization", bearerToken).when()
					.get(Uri);
		} else {
		}
		respReco.body().print();
		return respReco;
	}

	/**
	 * Get Recommendation data for the content
	 * 
	 * @param tab
	 * @return reco response
	 */
	public static Response getRecoTraysInDetailsPage(String userType, String contentID) {
		Response respReco = null;
		String Uri = "https://gwapi.zee5.com/content/reco?asset_id=" + contentID
				+ "&country=IN&translation=en&languages=en,kn&version=6&region=KA";
		System.out.println("Hitting content api:\n" + Uri);
		String xAccessToken = getXAccessToken();
		if (userType.equalsIgnoreCase("Guest")) {
			respReco = given().headers("x-access-token", xAccessToken).header("x-z5-guest-token", "12345").when()
					.get(Uri);
		} else if (userType.equalsIgnoreCase("SubscribedUser")) {
			String email = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("SubscribedUserName");
			String password = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("SubscribedPassword");
			String bearerToken = getBearerToken(email, password);
			respReco = given().headers("x-access-token", xAccessToken).header("authorization", bearerToken).when()
					.get(Uri);
		} else if (userType.equalsIgnoreCase("NonSubscribedUser")) {
			String email = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("NonsubscribedUserName");
			String password = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("NonsubscribedPassword");
			String bearerToken = getBearerToken(email, password);
			respReco = given().headers("x-access-token", xAccessToken).header("authorization", bearerToken).when()
					.get(Uri);
		} else {
			System.out.println("Incorrect user type passed to method");
		}
		respReco.body().print();
		return respReco;
	}

	/**
	 * Function to tray list on carousel for different pages
	 * 
	 * @param page
	 * @return
	 */
	public static List<String> traysTitleCarousel(String page) {
		Response responseCarouselTitle = getResponseForPages(page);
		List<String> titleOnCarouselList = new LinkedList<String>();
		int numberOfCarouselSlides = responseCarouselTitle.jsonPath().getList("buckets[0].items").size();
		System.out.println("api size : " + numberOfCarouselSlides);
		for (int i = 0; i < numberOfCarouselSlides; i++) {
			String title = responseCarouselTitle.jsonPath().getString("buckets[0].items[" + i + "].title");
			titleOnCarouselList.add(title);
		}
		return titleOnCarouselList;
	}

	/**
	 * Function to return response for different pages
	 * 
	 * @param page
	 * @return
	 */
	public static Response getResponseForPages(String page) {
		Response respCarousel = null;
		String Uri;
		if (page.equals("news")) {
			page = "626";
		} else if (page.equals("music")) {
			page = "2707";
		} else if (page.equals("home")) {
			page = "homepage";
		} else if (page.equals("live tv")) {
			Uri = "https://catalogapi.zee5.com/v1/channel/genres?translation=en&country=IN";
		}
		Uri = "https://gwapi.zee5.com/content/collection/0-8-" + page
				+ "?page=1&limit=5&item_limit=20&country=IN&translation=en&languages=en,kn&version=6";
		respCarousel = given().urlEncodingEnabled(false).when().get(Uri);
		return respCarousel;
	}

	/**
	 * Method to get content details passing content ID
	 * 
	 */
	public static Response getContentDetails(String contentID, String contentType) {
		Response respContentDetails = null;
		String Uri = "";
		if (contentType.equals("original")) {
			Uri = "https://gwapi.zee5.com/content/tvshow/" + contentID + "?translation=en&country=IN";
		} else if (contentType.contentEquals("Manual")) {
			Uri = "https://gwapi.zee5.com/content/collection/" + contentID + "?translation=en&country=IN";
		} else {
			Uri = "https://gwapi.zee5.com/content/details/" + contentID + "?translation=en&country=IN";
		}
		respContentDetails = given().when().get(Uri);
		// System.out.println("Content Details API Response:
		// "+respContentDetails.getBody().asString());
		return respContentDetails;
	}

	/**
	 * Function to return response for different pages
	 * 
	 * @param page
	 * @return
	 */
	public static Response getResponseForPages(String page, String contLang) {
		Response respCarousel = null;
		String Uri = "";
		if (page.equals("news")) {
			page = "5857";
		} else if (page.equals("music")) {
			page = "2707";
		} else if (page.equals("home")) {
			page = "homepage";
		} else if (page.equals("kids")) {
			page = "3673";
		} else if (page.equals("freemovies")) {
			page = "5011";
		} else if (page.equals("play")) {
			page = "4603";
		} else if (page.equals("zeeoriginals") || page.equals("zee5 originals")) {
			page = "zeeoriginals";
		} else if (page.equals("videos")) {
			page = "videos";
		} else if (page.equals("movies")) {
			page = "movies";
		} else if (page.equals("shows")) {
			page = "tvshows";
		} else if (page.equals("premium")) {
			page = "premiumcontents";
		} else if (page.equals("club")) {
			page = "5851";
		}
		if (page.equals("stories")) {
			Uri = "https://zeetv.zee5.com/wp-json/api/v1/featured-stories";
		} else {
			Uri = "https://gwapi.zee5.com/content/collection/0-8-" + page
					+ "?page=1&limit=5&item_limit=20&country=IN&translation=en&languages=" + contLang + "&version=10";
			System.out.println(Uri);
		}
		respCarousel = given().headers("X-ACCESS-TOKEN", getXAccessToken()).when().get(Uri);
		System.out.println("Response status : " + respCarousel.statusCode());
		// System.out.println("Response Body : "+respCarousel.getBody().asString());
		return respCarousel;
	}

	/**
	 * Function to tray list on carousel for different pages
	 * 
	 * @param page
	 * @return
	 */
	public static List<String> traysTitleCarousel(String page, String contLang) {
		Response responseCarouselTitle = getResponseForPages(page, contLang);
		List<String> titleOnCarouselList = new LinkedList<String>();
		int numberOfCarouselSlides = 0;
		String title = "";
		if (page.equals("stories")) {
			numberOfCarouselSlides = responseCarouselTitle.jsonPath().getList("posts").size();
		} else {
			numberOfCarouselSlides = responseCarouselTitle.jsonPath().getList("buckets[0].items").size();
		}
		System.out.println("api size : " + numberOfCarouselSlides);
		if (numberOfCarouselSlides > 7)
			numberOfCarouselSlides = 7;
		for (int i = 0; i < numberOfCarouselSlides; i++) { // Only 7 tray visible on UI
			if (page.equals("stories")) {
				title = responseCarouselTitle.jsonPath().getString("posts[" + i + "].title");
			} else {
				title = responseCarouselTitle.jsonPath().getString("buckets[0].items[" + i + "].title");
			}
			titleOnCarouselList.add(title);
		}
		return titleOnCarouselList;
	}

	public static Response getResponseForPages2(String page, String contLang, int q) {
		Response respCarousel = null;
		String Uri = "";
		if (page.equals("news")) {
			page = "626";
		} else if (page.equals("music")) {
			page = "2707";
		} else if (page.equals("home")) {
			page = "homepage";
		} else if (page.equals("kids")) {
			page = "3673";
		} else if (page.equals("freemovies")) {
			page = "5011";
		} else if (page.equals("play")) {
			page = "4603";
		}

		if (page.equals("stories")) {
			Uri = "https://zeetv.zee5.com/wp-json/api/v1/featured-stories";
		} else {
			Uri = "https://gwapi.zee5.com/content/collection/0-8-" + page + "?page=" + q
					+ "&limit=5&item_limit=20&country=IN&translation=en&languages=" + contLang + "&version=6";
		}

		respCarousel = given().headers("X-ACCESS-TOKEN", getXAccessToken()).when().get(Uri);
		System.out.println("Response status : " + respCarousel.statusCode());
		return respCarousel;
	}

	public static Response getResponseForUpcomingPage() {
		Response respCarousel = null;

		String Url = "https://gwapi.zee5.com/content/collection/0-8-3367?page=1&limit=10&item_limit=20&translation=en&country=IN&languages=en,kn&version=6&";
		respCarousel = given().urlEncodingEnabled(false).when().get(Url);
		return respCarousel;
	}

	public static Response getResponseForApplicasterPages(String userType, String page) {
		Response respHome = null;
		String language = getLanguage(userType);
		String Uri = "https://gwapi.zee5.com/content/collection/0-8-" + page
				+ "?page=1&limit=10&item_limit=20&translation=en&country=IN&version=6&languages=" + language;
		System.out.println(Uri);

		String xAccessToken = getXAccessTokenWithApiKey();
		if (userType.equalsIgnoreCase("Guest")) {
			respHome = given().headers("x-access-token", xAccessToken).when().get(Uri);
		} else if (userType.equalsIgnoreCase("SubscribedUser")) {
			String email = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("SubscribedUserName");
			String password = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("SubscribedPassword");
			String bearerToken = getBearerToken(email, password);
			respHome = given().headers("x-access-token", xAccessToken).header("authorization", bearerToken).when()
					.get(Uri);
		} else if (userType.equalsIgnoreCase("NonSubscribedUser")) {
			String email = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("NonsubscribedUserName");
			String password = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("NonsubscribedPassword");
			String bearerToken = getBearerToken(email, password);
			respHome = given().headers("x-access-token", xAccessToken).header("authorization", bearerToken).when()
					.get(Uri);
		} else {
			System.out.println("Incorrect user type passed to method");
		}
		return respHome;
	}

	// Getting Content Language API response for the NonSubscribedUser and
	// SubscribedUser ..

	// Getting API response ::

	public static Response getUserinfoforNonSubORSub(String userType) {
		Response resp = null;

		String url = "https://userapi.zee5.com/v1/settings";

		String xAccessToken = getXAccessTokenWithApiKey();
//					if (userType.equalsIgnoreCase("Guest")) {
//						resp = given().headers("x-access-token", xAccessToken).header("x-z5-guest-token", "12345").when()
//								.get(url);
//					} else
		if (userType.equalsIgnoreCase("SubscribedUser")) {
//						String email="zeetest998@test.com";
//						String password ="123456";
			String email = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("SubscribedUserName");
			String password = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("SubscribedPassword");
			String bearerToken = getBearerToken(email, password);
			resp = given().headers("x-access-token", xAccessToken).header("authorization", bearerToken).when().get(url);
		} else if (userType.equalsIgnoreCase("NonSubscribedUser")) {
//						String email="igstesting001@gmail.com";
			// String password ="igs@12345";
			String email = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("NonsubscribedUserName");
			String password = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("NonsubscribedPassword");
			String bearerToken = getBearerToken(email, password);
			resp = given().headers("x-access-token", xAccessToken).header("authorization", bearerToken).when().get(url);
		} else {
			System.out.println("Incorrect user type passed to method");
		}

		return resp;

	}

	// Fetching language from response ::
	public static String getLanguage(String userType) {
		String language = null;
		if (userType.contains("Guest")) {
			language = "en,kn";
		} else {
			Response resplanguage = getUserinfoforNonSubORSub(userType);
			// System.out.println(resplanguage.print());

			// System.out.println(resplanguage.jsonPath().getList("array").size());

			for (int i = 0; i < resplanguage.jsonPath().getList("array").size(); i++) {

				String key = resplanguage.jsonPath().getString("[" + i + "].key");
				// System.out.println(language);
				if (key.contains("content_language")) {
					language = resplanguage.jsonPath().getString("[" + i + "].value");
					System.out.println("UserType Language: " + language);
					break;
				}
			}
		}
		return language;
	}

	public static String getXAccessTokenWithApiKey() {
		Response respToken = null, respForKey = null;
		// get APi-KEY
		String Uri = "https://gwapi.zee5.com/user/getKey?=aaa";
		respForKey = given().urlEncodingEnabled(false).when().get(Uri);
		String rawApiKey = respForKey.getBody().asString();
		String apiKeyInResponse = rawApiKey.substring(0, rawApiKey.indexOf("<br>airtel "));
		String finalApiKey = apiKeyInResponse.replaceAll("<br>rel - API-KEY : ", "");
		String UriForToken = "http://gwapi.zee5.com/user/getToken";
		respToken = given().headers("API-KEY", finalApiKey).when().get(UriForToken);
		String xAccessToken = respToken.jsonPath().getString("X-ACCESS-TOKEN");
		return xAccessToken;
	}

	public static Response getRespofCWTray(String userType) {
		Response respCW = null;
		String language = getLanguage(userType);

		String Uri = "https://gwapi.zee5.com/user/v2/watchhistory?country=IN&translation=en&languages=" + language;

		String xAccessToken = getXAccessTokenWithApiKey();

		if (userType.equalsIgnoreCase("Guest")) {
			respCW = given().headers("x-access-token", xAccessToken).when().get(Uri);
		} else if (userType.equalsIgnoreCase("SubscribedUser")) {
//			String email = "zeetest998@test.com";
//			String password = "123456";
			String email = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("SubscribedUserName");
			String password = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("SubscribedPassword");
			String bearerToken = getBearerToken(email, password);
			respCW = given().headers("x-access-token", xAccessToken).header("authorization", bearerToken).when()
					.get(Uri);
		} else if (userType.equalsIgnoreCase("NonSubscribedUser")) {

//			String email = "igstesting001@gmail.com";
//			String password = "igs@12345";
			String email = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("NonsubscribedUserName");
			String password = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("NonsubscribedPassword");
			String bearerToken = getBearerToken(email, password);
			respCW = given().headers("x-access-token", xAccessToken).header("authorization", bearerToken).when()
					.get(Uri);
		} else {
			System.out.println("Incorrect user type passed to method");
		}
		return respCW;
	}

	public static Response getResponseForApplicasterPagesVersion2(String userType, String page, String pUsername,
			String pPassword) {
		Response respHome = null;
		String language = getLanguageVersion2(userType, pUsername, pPassword);
		String Uri = "https://gwapi.zee5.com/content/collection/0-8-" + page
				+ "?page=1&limit=10&item_limit=20&translation=en&country=IN&version=6&languages=" + language;

		String xAccessToken = getXAccessTokenWithApiKey();
		if (userType.equalsIgnoreCase("Guest")) {
			respHome = given().headers("x-access-token", xAccessToken).when().get(Uri);
		} else if (userType.equalsIgnoreCase("SubscribedUser")) {
			String bearerToken = getBearerToken(pUsername, pPassword);
			respHome = given().headers("x-access-token", xAccessToken).header("authorization", bearerToken).when()
					.get(Uri);
		} else if (userType.equalsIgnoreCase("NonSubscribedUser")) {
			String bearerToken = getBearerToken(pUsername, pPassword);
			respHome = given().headers("x-access-token", xAccessToken).header("authorization", bearerToken).when()
					.get(Uri);
		} else {
			System.out.println("Incorrect user type passed to method");
		}
		return respHome;
	}

	public static String getLanguageVersion2(String userType, String pUsername, String pPassword) {
		String language = null;
		if (userType.contains("Guest")) {
			language = "en,kn";
		} else {
			Response resplanguage = getUserinfoforNonSubORSubversion2(userType, pUsername, pPassword);
			// System.out.println(resplanguage.print());
			// System.out.println(resplanguage.jsonPath().getList("array").size());

			for (int i = 0; i < resplanguage.jsonPath().getList("array").size(); i++) {

				String key = resplanguage.jsonPath().getString("[" + i + "].key");
				// System.out.println(language);
				if (key.contains("content_language")) {
					language = resplanguage.jsonPath().getString("[" + i + "].value");
					System.out.println("UserType Language: " + language);
					break;
				}
			}
		}
		return language;
	}

	public static Response getUserinfoforNonSubORSubversion2(String userType, String pUsername, String pPassword) {
		Response resp = null;
		String url = "https://userapi.zee5.com/v1/settings";
		String xAccessToken = getXAccessTokenWithApiKey();

		if (userType.equalsIgnoreCase("SubscribedUser") | userType.equalsIgnoreCase("NonSubscribedUser")) {
			String bearerToken = getBearerToken(pUsername, pPassword);
			resp = given().headers("x-access-token", xAccessToken).header("authorization", bearerToken).when().get(url);
		} else {
			System.out.println("Incorrect user type passed to method");
		}
		return resp;
	}

	public static boolean BeforeTV(String UserType, String tabName) {
		Response resp = null;
		boolean title = false;
		String xAccessToken = getXAccessTokenWithApiKey();
		if (tabName.equals("Home")) {
			resp = given().headers("x-access-token", xAccessToken).when().get(
					"https://gwapi.zee5.com/content/collection/0-8-homepage?page=1&limit=10&item_limit=20&translation=en&country=IN&version=6&languages=en,kn&");
		} else if (tabName.equals("Show")) {
			resp = given().headers("x-access-token", xAccessToken).when().get(
					"https://gwapi.zee5.com/content/collection/0-8-tvshows?page=1&limit=10&item_limit=20&translation=en&country=IN&version=6&languages=en,kn&");
		}

		for (int i = 0; i < resp.jsonPath().getList("buckets").size(); i++) {
			title = resp.jsonPath().getString("buckets[" + i + "].title").contains("Premiere Episodes | Before");
			if (title) {
				break;
			}
		}
		return title;
	}

	public static Properties getUserSettingsDetails(String pUsername, String pPassword) {
		Properties pro = new Properties();
		if (!pUsername.equals("")) {
			String bearerToken = getBearerToken(pUsername, pPassword);
			resp = given().headers("x-access-token", getXAccessTokenWithApiKey()).header("authorization", bearerToken)
					.when().get("https://userapi.zee5.com/v1/settings");

			String[] comm = resp.asString().replace("{", "").replace("}", "").replace("[", "").replace("]", "")
					.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
			System.out.println(comm.length);
			String value = null;
			for (int i = 0; i < comm.length;) {
				String key = comm[i].replace("\"", "").split("y:")[1];
				try {
					value = comm[i + 1].replace("\"", "").split("e:")[1];
				} catch (Exception e) {
					value = "Empty";
				}
				if (value.contains("age_rating")) {
					key = "Parent Control Setting";
					value = value.replace("\\", "").split(":")[1].replace(", pin", "");
				}
				pro.setProperty(key, value);
				i = i + 2;
			}
		}
		return pro;
	}

	@SuppressWarnings("unused")
	public static void getUserData(String pUsername, String pPassword) {
		String[] userData = { "email", "first_name", "last_name", "birthday", "gender", "registration_country",
				"recurring_enabled" };
		Properties pro = new Properties();
		String xAccessToken = getXAccessTokenWithApiKey();
		String bearerToken = getBearerToken(pUsername, pPassword);
		String url = "https://userapi.zee5.com/v1/user";
		resp = given().headers("x-access-token", xAccessToken).header("authorization", bearerToken).when().get(url);
//		resp.print();
		String commaSplit[] = resp.asString().replace("{", "").replace("}", "").replaceAll("[.,](?=[^\\[]*\\])", "-")
				.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
		for (int i = 0; i < commaSplit.length; i++) {
			if (Stream.of(userData).anyMatch(commaSplit[i]::contains)) {
				String com[] = commaSplit[i].split(":(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
				FEProp.setProperty(com[0].replace("\"", ""), com[1].replace("\"", ""));
			}
		}
		getDOB();
	}

	private static void getDOB() {
		LocalDate dob = LocalDate.parse(FEProp.getProperty("birthday").split("T")[0]);
		LocalDate curDate = LocalDate.now();
		FEProp.setProperty("Age", String.valueOf(Period.between(dob, curDate).getYears()));
	}

	public static String getRegion() {
		Response regionResponse = given().urlEncodingEnabled(false).when().get("https://xtra.zee5.com/country");
		return regionResponse.getBody().jsonPath().getString("state");
	}

	public static String getresponse(String searchText) {
		Response resp = given().urlEncodingEnabled(false).when().get("https://gwapi.zee5.com/content/search_all?q="
				+ searchText
				+ "&limit=10&asset_type=0,6,1&country=IN&languages=hi,en,mr,te,kn,ta,ml,bn,gu,pa,hr,or&translation=en&version=3&");
		return resp.jsonPath().getString("results[0].total");
	}

	public static String getFreeContent(String tabName, String pUsername, String pPassword) {
		PropertyFileReader handler = new PropertyFileReader("properties/MixpanelKeys.properties");
		String language;
		String pageName = handler.getproperty(tabName);
		if (!pUsername.equals("")) {
			Properties prop = getUserSettingsDetails(pUsername, pPassword);
			System.out.println(prop.getProperty("content_language"));
			language = prop.getProperty("content_language");
		} else {
			language = "en,kn";
		}
		String url = "https://gwapi.zee5.com/content/collection/0-8-" + pageName
				+ "?page=1&limit=5&item_limit=20&country=IN&translation=en&languages=" + language + "&version=6";
		String xAccessToken = getXAccessTokenWithApiKey();
		if (!pUsername.equals("")) {
			String bearerToken = getBearerToken(pUsername, pPassword);
			resp = given().headers("x-access-token", xAccessToken).header("authorization", bearerToken).when().get(url);
		} else {
			resp = given().headers("x-access-token", xAccessToken).when().get(url);
		}
		String title = "No Free Contents";
		for (int i = 0; i < 7; i++) {
			if (resp.jsonPath().getString("buckets[0].items[" + i + "].business_type").equals("free")) {
				title = resp.jsonPath().getString("buckets[0].items[" + i + "].title");
				if (!title.contains("HiPi")) {
					System.out.println(title);
					break;
				}
			}
		}

		if (!pUsername.equals("")) {
			getUserData(pUsername, pPassword);
		}
		return title;
	}

	public static Properties getUserOldSettingsDetails(String pUsername, String pPassword) {
		Properties pro = new Properties();
		if (!pUsername.equals("")) {
			String bearerToken = getBearerToken(pUsername, pPassword);
			resp = given().headers("x-access-token", getXAccessTokenWithApiKey()).header("authorization", bearerToken)
					.when().get("https://userapi.zee5.com/v1/settings");

			String[] comm = resp.asString().replace("{", "").replace("}", "").replace("[", "").replace("]", "")
					.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
			String value = null;
			for (int i = 0; i < comm.length;) {
				String key = comm[i].replace("\"", "").split("y:")[1];
				try {
					value = comm[i + 1].replace("\"", "").split("e:")[1];
				} catch (Exception e) {
					value = "Empty";
				}
				if (value.contains("age_rating")) {
					key = "Parent Control Setting";
					value = value.replace("\\", "").split(":")[1].replace(", pin", "");
				}
				pro.setProperty(key, value);
				i = i + 2;
			}
		}
		pro.forEach((key, value) -> System.out.println(key + " : " + value));
		return pro;
	}

	public static void getContentDetails(String ID) {
		resp = given().headers("x-access-token", getXAccessTokenWithApiKey()).when()
				.get("https://gwapi.zee5.com/content/details/" + ID + "?translation=en&country=IN&version=2");
//		resp = given().headers("x-access-token", getXAccessTokenWithApiKey()).when().get("https://gwapi.zee5.com/content/details/0-1-84080?translation=en&country=IN&version=2");
		FEProp.setProperty("Content Duration", resp.jsonPath().getString("duration"));
		FEProp.setProperty("Content ID", resp.jsonPath().getString("id"));
		FEProp.setProperty("Content Name", resp.jsonPath().getString("original_title"));
		FEProp.setProperty("Content Specification", resp.jsonPath().getString("asset_subtype"));
		FEProp.setProperty("Characters", resp.jsonPath().getList("actors").toString().replaceAll(",", "-"));
		FEProp.setProperty("Audio Language", resp.jsonPath().getList("audio_languages").toString());
		FEProp.setProperty("Subtitle Language", resp.jsonPath().getString("subtitle_languages").toString());
		FEProp.setProperty("Content Type", resp.jsonPath().getString("business_type"));
		FEProp.setProperty("Genre", resp.jsonPath().getList("genre.id").toString().replaceAll(",", "-"));
		FEProp.setProperty("Content Original Language",
				resp.jsonPath().getString("languages").replace("[", "").replace("]", ""));
		if (resp.jsonPath().getString("is_drm").equals("1")) {
			FEProp.setProperty("DRM Video", "true");
		} else {
			FEProp.setProperty("DRM Video", "false");
		}
//		resp.print();
//		FEProp.forEach((key, value) -> System.out.println(key + " : " + value));
	}

	public static void Player(String pUsername, String pPassword) {
		String url = "https://gwapi.zee5.com/content/player/0-1-136585";
		String bearerToken = getBearerToken(pUsername, pPassword);
		resp = given().headers("x-access-token", getXAccessTokenWithApiKey()).header("authorization", bearerToken)
				.when().get(url);
		resp.print();
	}

	@SuppressWarnings("unused")
	public static void getWatchList(String pUsername, String pPassword) {
		String url = "https://userapi.zee5.com/v1/watchlist";
		String bearerToken = getBearerToken(pUsername, pPassword);
		System.out.println(getXAccessTokenWithApiKey() + "\n");
		System.out.println(bearerToken);
//		resp = given().headers("x-access-token", getXAccessTokenWithApiKey()).header("authorization", bearerToken).when().get(url);
//		resp.print();
	}
	
	
	
	//----------------------------------------APPSFLYER---------------------------------------------------------
	
	
	public static void fetchExpectedDataforAppsFlyer(String event, String userType, String idNumber, String userValue, String topnavtab, String tabname, String showID, String packID, String musicID) throws IOException, ParseException{
		
		StaticValuesforAppsFlyer(event,idNumber,userValue,userType);
		
		getDeviceDetails();
		
		getUserLocationforAppsFlyer(userType);
		
		if(!userType.equalsIgnoreCase("Guest")){
			getHiPiUserDetailsForAppsFlyer(username, password);	
		}
		
		if(!userType.equalsIgnoreCase("Guest")){
			getHiPiMeDataForAppsflyer(username, password);	
		}
		
		if(!userType.equalsIgnoreCase("Guest")){
			getHiPiProfileDataForAppsflyer(username, password);	
		}
		
		
	}
	
	
	public static Properties StaticValuesforAppsFlyer(String eventName, String idNumber, String userValue, String userType){
		System.out.println("- - - - StaticValuesforAppsFlyer - - - -");
		Properties pro = new Properties();
		
		if(userType.equalsIgnoreCase("Guest")){
			
			AppsFlyer.expectedData.setProperty("Gender", "N/A");
			AppsFlyer.expectedData.setProperty("User Handle", "N/A");
			AppsFlyer.expectedData.setProperty("Age", "N/A");
			AppsFlyer.expectedData.setProperty("Platform Name", "Android");
			AppsFlyer.expectedData.setProperty("Appsflyer Source", "N/A");
			AppsFlyer.expectedData.setProperty("Advertisement ID", AdID);
			AppsFlyer.expectedData.setProperty("Appsflyer Medium", "N/A");
			AppsFlyer.expectedData.setProperty("Appsflyer Campaign", "N/A");
			AppsFlyer.expectedData.setProperty("Platform Section", "HiPi");
			AppsFlyer.expectedData.setProperty("App ID", "com.zee5.hipi");
			AppsFlyer.expectedData.setProperty("Language", "English");
			AppsFlyer.expectedData.setProperty("user_access_type", userValue);
		}else{

		AppsFlyer.expectedData.setProperty("Appsflyer Source", "N/A");
		AppsFlyer.expectedData.setProperty("Advertisement ID", AdID);
		AppsFlyer.expectedData.setProperty("ad_id", AdID);
		AppsFlyer.expectedData.setProperty("Appsflyer Medium", "N/A");
		AppsFlyer.expectedData.setProperty("Appsflyer Campaign", "N/A");
		AppsFlyer.expectedData.setProperty("user_access_type", userValue);
		AppsFlyer.expectedData.setProperty("Language", "English");
		AppsFlyer.expectedData.setProperty("App ID", "com.zee5.hipi");
		AppsFlyer.expectedData.setProperty("App Name", "HiPi");
		AppsFlyer.expectedData.setProperty("Platform Section", "HiPi");
		
		if(eventName.equalsIgnoreCase("clip_recording_ended") || eventName.equalsIgnoreCase("clip_recording_started")){
			AppsFlyer.expectedData.setProperty("Short Type", "Live");
		}else if(eventName.equalsIgnoreCase("clip_upload_result")){
			AppsFlyer.expectedData.setProperty("Short Type", "Upload");
		}
		
		}
		AppsFlyer.expectedData.forEach((key, value) -> System.out.println(key + " : " + value));
		
		return pro;
	}
	
	
	
	public static Properties getDeviceDetails() throws IOException{
		System.out.println("- - - - getDeviceDetails - - - -");
		Properties pro = new Properties();

		//APP VERSION
		String temp;
		String appVersion = null;
		String cmd = "adb shell \"dumpsys package com.zee5.hipi | grep versionName\"";
		Process p = Runtime.getRuntime().exec(cmd);
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while ((temp = br.readLine()) != null) {
		//	System.out.println(temp);	
			appVersion = temp.trim();
		}
		String[] str = appVersion.split("=");
		AppsFlyer.expectedData.setProperty("App Version", str[1]);

		
		//MOBILE BRAND+MODEL
		String MobileBrand = null,MobileModel = null;
		String DeviceType = null;
		String cmd1 = "adb shell getprop ro.product.vendor.brand";
		Process p1 = Runtime.getRuntime().exec(cmd1);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
		while ((temp = br1.readLine()) != null) {
		//	System.out.println(temp);	
			MobileBrand = temp.trim();
		}
		String cmd2 = "adb shell getprop ro.product.vendor.model";
		Process p2 = Runtime.getRuntime().exec(cmd2);
		BufferedReader br2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
		while ((temp = br2.readLine()) != null) {
		//	System.out.println(temp);	
			MobileModel = temp.trim();
		}
		DeviceType = MobileBrand +"-"+ MobileModel;
		AppsFlyer.expectedData.setProperty("Device Type", DeviceType);

		
		//OS VERSION
		float osVersion = 0 ;
		String cmd4 = "adb shell getprop ro.build.version.release";
		Process p4 = Runtime.getRuntime().exec(cmd4);
		BufferedReader br4 = new BufferedReader(new InputStreamReader(p4.getInputStream()));
		while ((temp = br4.readLine()) != null) {
		//	System.out.println(temp);	
			osVersion = Float.parseFloat(temp.trim());
		}
		AppsFlyer.expectedData.setProperty("OS Version", String.valueOf(osVersion));
		
		
		AppsFlyer.expectedData.forEach((key, value) -> System.out.println(key + " : " + value));
		
		return pro;
		
	}
	
	
	
	
	public static Properties getUserLocationforAppsFlyer(String userType) {
		System.out.println("- - - - getUserLocationforAppsFlyer - - - -");
		String[] userData = { "country_code", "country", "state", "state_code" };
		Properties pro = new Properties();
		String url = "https://xtra.zee5.com/country";
		resp = given().urlEncodingEnabled(false).when().get(url);
		resp.print();
		
		if(userType.equalsIgnoreCase("Guest")){
		AppsFlyer.expectedData.setProperty("Registring Country", resp.jsonPath().getString("country_code"));			
		}
		

		AppsFlyer.expectedData.setProperty("Country", resp.jsonPath().getString("country"));
		AppsFlyer.expectedData.setProperty("Country Code", resp.jsonPath().getString("country_code"));
		AppsFlyer.expectedData.setProperty("State", resp.jsonPath().getString("state_code"));
		
		AppsFlyer.expectedData.forEach((key, value) -> System.out.println(key + " : " + value));
		
		return pro;

	}
	
	
	
	public static Properties getHiPiUserDetailsForAppsFlyer(String Username,String Password){
		
//		String Username = "zeenonsubhipi@gmail.com";
//		String Password = "123456";
		Properties pro = new Properties();
		
		String bearerToken = getBearerToken(Username, Password);
//		System.out.println(bearerToken);
		String xToken = getXAccessToken1();
//		System.out.println(xToken);
		resp = given().headers("x-access-token", xToken).header("authorization", bearerToken)
				.when().get("https://userapi.zee5.com/v1/user");

		System.out.println(resp.asString());
		
		AppsFlyer.expectedData.setProperty("af_registration_method","email_password");
		
		AppsFlyer.expectedData.setProperty("UGC Creation Type","Normal");
		
		AppsFlyer.expectedData.setProperty("user_id", resp.jsonPath().getString("id"));
		AppsFlyer.expectedData.setProperty("Gender", resp.jsonPath().getString("gender"));
		AppsFlyer.expectedData.setProperty("Registring Country", resp.jsonPath().getString("registration_country"));
		AppsFlyer.expectedData.setProperty("Platform Name", resp.jsonPath().getString("additional.platform"));
		getDOBforAppsFlyer();
		AppsFlyer.expectedData.forEach((key, value) -> System.out.println(key + " : " + value));
		
		return pro;
		
	}
	
	public static String getXAccessToken1() {
		Response respToken = null, respForKey = null;
		// get APi-KEY
		String Uri = "https://gwapi.zee5.com/user/getKey?=aaa";
		respForKey = RestAssured.given().urlEncodingEnabled(false).when().get(Uri);
		String rawApiKey = respForKey.getBody().asString();
		String apiKeyInResponse = rawApiKey.substring(0, rawApiKey.indexOf("<br>airtel "));
		String finalApiKey = apiKeyInResponse.replaceAll("<br>rel - API-KEY : ", "");
		//System.out.println(finalApiKey);
		String UriForToken = "http://gwapi.zee5.com/user/getToken";
		respToken = RestAssured.given().headers("API-KEY", finalApiKey).when().get(UriForToken);
		String xAccessToken = respToken.jsonPath().getString("X-ACCESS-TOKEN");
		return xAccessToken;
	}
	
	private static String getDOBforAppsFlyer() {
		String birthday=resp.jsonPath().get("birthday").toString();
		LocalDate dob = LocalDate.parse(birthday.split("T")[0]);
		LocalDate curDate = LocalDate.now();
		int ageYears=Period.between(dob, curDate).getYears();
		int ageMonths=Period.between(dob, curDate).getMonths();
		if(ageMonths>=6) {
			++ageYears;
		}
		AppsFlyer.expectedData.setProperty("Age", String.valueOf(ageYears));
		
		return String.valueOf(ageYears);
	}
	
//	public static void main(String[] args){
//		gettoken();
//}
	
	
	
	public static void getHiPiMeDataForAppsflyer(String username,String password){
		
//		String username = "zeenonsubhipi@gmail.com";
//		String password = "123456";		
		
		String url = "http://3.6.36.112:7000/api/v2/shorts/login?userName="+username+"&password="+password+"&type=email";
		
		resp = given().urlEncodingEnabled(false).headers("accept", "application/json").header("guest-token","sdvsvbefb").when().post(url);
		
		String token = "Bearer "+resp.jsonPath().getString("shortsAuthToken");
		
		//System.out.println(token);
		
		String bearerToken = getBearerToken(username, password);
		
		String updatedBearerToken = bearerToken.replace("Bearer ", "");
		
		//System.out.println(updatedBearerToken);
		
		resp = given().headers("access-token", updatedBearerToken).header("authorization", token)
				.when().get("https://hipigwapi.zee5.com/api/v1/shorts/profile/videos?filter=all&limit=10&offset=1");
		
		//resp.print();
		
		AppsFlyer.expectedData.setProperty("Short Actual Duration", resp.jsonPath().getString("responseData[0].videoDuration"));
		
		AppsFlyer.expectedData.forEach((key, value) -> System.out.println(key + " : " + value));
		
	}

	
	
	public static void getHiPiProfileDataForAppsflyer(String username,String password){
		
//		String username = "zeenonsubhipi@gmail.com";
//		String password = "123456";		
		
		String url = "http://3.6.36.112:7000/api/v2/shorts/login?userName="+username+"&password="+password+"&type=email";
		
		resp = given().urlEncodingEnabled(false).headers("accept", "application/json").header("guest-token","sdvsvbefb").when().post(url);
		
		String token = "Bearer "+resp.jsonPath().getString("shortsAuthToken");
		
		//System.out.println(token);
		
		String bearerToken = getBearerToken(username, password);
		
		String updatedBearerToken = bearerToken.replace("Bearer ", "");
		
		//System.out.println(updatedBearerToken);
		
		resp = given().headers("access-token", updatedBearerToken).header("authorization", token)
				.when().get("https://hipigwapi.zee5.com/api/v1/shorts/profile?id=");
		
		//resp.print();
		
		AppsFlyer.expectedData.setProperty("User Handle", resp.jsonPath().getString("responseData.userHandle"));
		
		AppsFlyer.expectedData.forEach((key, value) -> System.out.println(key + " : " + value));
		
	}

	
	
	
}

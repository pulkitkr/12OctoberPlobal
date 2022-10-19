package com.android.PlobalPages;

import org.openqa.selenium.By;

public class PlobalLoginPage {

	// Welcome header
	public static By objWelcomeHeader = By.xpath("//*[@text='NEVER MISS ON YOUR SCENTS!']");

	// Welcome page yes button
	public static By objYesBtn = By.xpath("//*[@text='Yes']");

	// Welcome to family
	public static By objWelcomeToFamilyText = By.xpath("//*[@text='WELCOME TO THE FAMILY!']");

	// Done Button
	public static By objDoneBtn = By.xpath("//*[@text='Done']");

	// HomePage Header logo
	public static By objHomePageLogo = By.xpath("//*[@class='UIAImage' and @width>0 and ./parent::*[./parent::*[@class='UIAStaticText']]]");

	// Search Product
	public static By objSearchProduct = By.xpath("//*[@id='Search products']");

	// Profile Tab
	public static By objProfileTab = By.xpath("//*[@id='27']");
	
	// Login Btn
	public static By objLoginBtn = By.xpath("//*[@id='Login']");

	// Login Page Header
	public static By objLoginPageHeader = By
			.xpath("//*[@resource-id='plobaltestshutterstock.android.staging:id/textview_title']");

	// Email id
	public static By objEmailTextField = By.xpath("//*[@placeholder='Email']");

	// Pasword
	public static By objPasswordTextFied = By.xpath("//*[@placeholder='Password']");

	// Login Button
	public static By objLoginButton = By.xpath("//*[@id='LOGIN']");

	// User created or not
	public static By objusercreated(String user) {
		return By.xpath("//*[@text='" + user + "']");
	}

	// HomePage Tab
	public static By objHomePageTab = By.xpath("(//*[@class='UIACollectionView']/*[@text='24'])[1]");

	// Allow to take pictures popup
	public static By objAllowPopup = By
			.xpath("//*[@resource-id='com.android.permissioncontroller:id/permission_message']");

	// Allow Button
	public static By objAllowBtn = By
			.xpath("//*[@text='Allow' or @text='WHILE USING THE APP' or @text='ALLOW' or @text='While using the app']");

	// collection product
	public static By objCollectionProduct = By.xpath("//*[@id='Coats & Jackets']");

	// Select First product
	public static By objCollectionFirstProduct = By.xpath("//*[@class='UIAImage' and @width>0 and (./preceding-sibling::* | ./following-sibling::*)[@id='ONE/ZERO BY KOOVS Contrast Panel Windproof Tracksuit']]");

	// Search Bar
	public static By objSearchBar = By.xpath("//*[@text='Search products']");

	// Gold ring Product
	public static By objProductName = By.xpath("(//*[contains(@text,'Search result')]/following-sibling::XCUIElementTypeCollectionView/descendant::XCUIElementTypeStaticText)[1]");

	// share button
	public static By objShareImage = By.xpath("//*[@id='shareHomeProdList']");

	// Wish button
	public static By objWishlistImage = By.xpath("//*[@id='FavoritePDP']");

	// Add to cart Button
	public static By objAddToCartBtn = By
			.xpath("//*[@class='UIAButton' and @width>0 and (./preceding-sibling::* | ./following-sibling::*)[@id='ADD TO CART'] and ./parent::*[@class='UIAView']]");

	// Buy Now Button
	public static By objBuyNowBtn = By
			.xpath("//*[@class='UIAButton' and @width>0 and (./preceding-sibling::* | ./following-sibling::*)[@id='BUY NOW'] and ./parent::*[@class='UIAView']]");

	// Toast message
	public static By objToastMsg = By.xpath("//*[@text='Unidentified customer']");
	
	//Ok button
	public static By objUnidentifyOkBtn = By.xpath("//*[@text='Ok']");
	
	// Login page title
	public static By objLoginPageTitle = By.xpath("//*[@id='staging plobal-test-shutterstock' and @class='UIAStaticText']");
	
	// Logout
	public static By objLogOut = By.xpath("//*[@id='Logout']");
	
	// Logout message
	public static By objLogOutMsg = By.xpath("//*[@id='Are you sure you wish to logout?' and @class='UIAStaticText']");
	
	// Logout Ok button
	public static By objLogOutYesBtn = By.xpath("//*[@text='Yes']");
	
	// Bar code product search
	public static By objSerachThroughBarCodeProduct = By.xpath("(//*[@text='Description']/parent::XCUIElementTypeCell/preceding-sibling::XCUIElementTypeCell/descendant::XCUIElementTypeStaticText)[2]");
		
}

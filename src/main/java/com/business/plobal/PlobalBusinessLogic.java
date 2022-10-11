package com.business.plobal;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import java.awt.Robot;

import com.android.PlobalPages.CheckOutPage;
import com.android.PlobalPages.PlobalLoginPage;
import com.android.PlobalPages.ProductPage;
import com.deviceDetails.DeviceDetails;
import com.driverInstance.DriverInstance;

import com.driverInstance.Drivertools;
import com.emailReport.GmailInbox;
import com.excel.Time_ExcelUpdate;
import com.extent.ExtentReporter;
import com.propertyfilereader.PropertyFileReader;

import com.utility.LoggingUtils;
import com.utility.Utilities;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.HideKeyboardStrategy;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

@SuppressWarnings("static-access")
public class PlobalBusinessLogic extends Utilities {

	public Date date;

	Drivertools tools;

	@SuppressWarnings("unused")
	private static final String Expectedresult = null;

	private String productText;
	public static String iosModel;
	public static String iosVersion;
	SoftAssert softAssertion = new SoftAssert();

	public PlobalBusinessLogic(String Application) {
		new DriverInstance(Application);
		init();

	}

	private int timeout;

	/** Retry Count */
	private int retryCount;
	ExtentReporter extent = new ExtentReporter();

	/** The Constant logger. */
	// final static Logger logger = Logger.getLogger("rootLogger");
	static LoggingUtils logger = new LoggingUtils();

	@SuppressWarnings("rawtypes")
	public TouchAction touchAction;

	/** The iOS driver. */
	public IOSDriver<WebElement> iOSDriver;

	@Override
	public int getTimeout() {
		return timeout;
	}

	Robot robo;

	String pUserType = getParameterFromXML("userType");

	/** Array of App */
	static ArrayList<String> AppMyProfile = new ArrayList<String>();
	static HashSet<String> contentsInWatchList = new HashSet<String>();
	static HashSet<String> contentsInReminders = new HashSet<String>();
	static ArrayList<String> AppSubscription = new ArrayList<String>();
	static ArrayList<String> AppTransaction = new ArrayList<String>();

	@Override
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public int getRetryCount() {
		return retryCount;
	}

	@Override
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	GmailInbox gmail = new GmailInbox();

	String SVODEpisode = getParameterFromXML("SVODEpisode");

	String profileID = null;
	String profileText = null;

	String ProfileFirstName = null;
	String ProfileLastName = null;
	String DefinedHandleName = null;

	String NotificationUsername = null;
	String NotificationPassword;

	String fun = null;

	String effectName = null;
	int CommentTitleCount1;

	public void init() {
		if (Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName()
				.equals("iOSWeb")) {
			System.out.println("Platform : iOS Web");
			iosModel = (String) getDriver().getCapabilities().getCapability("device.model");
			System.out.println(iosModel + "--- Model Name ");
			iosVersion = (String) getDriver().getCapabilities().getCapability("device.version");
			System.out.println(iosVersion + "--- Version ");
		} else if (Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName()
				.equals("HIPI_iOS")) {
			iosModel = (String) getDriver().getCapabilities().getCapability("device.model");
			System.out.println(iosModel + "--- Model Name ");
			iosVersion = (String) getDriver().getCapabilities().getCapability("device.version");
			System.out.println(iosVersion + "--- Version ");
		} else if (Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName()
				.equals("Web")) {
			System.out.println("Platform : Web");
		}
		PropertyFileReader handler = new PropertyFileReader("properties/Execution.properties");
		setTimeout(Integer.parseInt(handler.getproperty("TIMEOUT")));
		setRetryCount(Integer.parseInt(handler.getproperty("RETRY_COUNT")));
	}

	public void devicedetails() {
		HeaderChildNode("Device Details");

		iosModel = (String) getDriver().getCapabilities().getCapability("device.model");

		iosVersion = (String) getDriver().getCapabilities().getCapability("device.version");

		extent.extentLogger("", "Model Name :" + iosModel);
		extent.extentLogger("", "Version :" + iosVersion);
	}

	public void tearDown() {
		getDriver().quit();
	}

	public void loginToApp(String invalidEmailId, String invalidPassword, String validEmailId, String validPassword)
			throws Exception {
		extent.HeaderChildNode("Login to App");

		verifyElementPresent(PlobalLoginPage.objHomePageLogo, "ESCAWAY Logo");
		logger.info("Navigated to HomePage");
		extent.extentLogger("Home", "Navigated to HomePage");
		verifyElementPresentAndClick(PlobalLoginPage.objProfileTab, "Profile Tab");
		click(PlobalLoginPage.objLoginBtn, "Login Button");
		click(PlobalLoginPage.objEmailTextField, "Email Text Field");
		type(PlobalLoginPage.objEmailTextField, invalidEmailId, "Email Text Field");
		String wrongUserName = getText(PlobalLoginPage.objEmailTextField);
		click(PlobalLoginPage.objPasswordTextFied, "Password Text Field");
		type(PlobalLoginPage.objPasswordTextFied, invalidPassword, "Password Text Field");
		click(PlobalLoginPage.objLoginButton, "Login Button");

		String toastMsg = getText(PlobalLoginPage.objToastMsg);
		click(PlobalLoginPage.objUnidentifyOkBtn, "OK Button");

		logger.info(toastMsg + " Toast Message is displayed");
		extent.extentLogger("Toast Message", toastMsg + " Toast Message is displayed");

		if (verifyIsElementDisplayed(PlobalLoginPage.objLoginPageTitle, "Login Page")) {

			click(PlobalLoginPage.objEmailTextField, "Email Text Field");
			clearField(PlobalLoginPage.objEmailTextField, "Email Text Field");
			type(PlobalLoginPage.objEmailTextField, validEmailId, "Email Text Field");
			String userName = getText(PlobalLoginPage.objEmailTextField);
			click(PlobalLoginPage.objPasswordTextFied, "Password Text Field");
			clearField(PlobalLoginPage.objPasswordTextFied, "Password Text Field");
			type(PlobalLoginPage.objPasswordTextFied, validPassword, "Password Text Field");
			click(PlobalLoginPage.objLoginButton, "Login Button");
			verifyIsElementDisplayed(PlobalLoginPage.objusercreated("Testmail@mailnesia.com"),
					"User Logged in by " + userName + "");
			click(PlobalLoginPage.objHomePageTab, "Home Page Tab");

			logger.info("Navigated Search Product Page");
			extent.extentLoggerPass("SearchProduct", "Navigated Search Product Page");
		} else {
			logger.info("Failed to Navigate Search Product Page");
			extent.extentLoggerFail("SearchProduct", "Failed to Navigate Search Product Page");
		}
	}

	public void productItem(String size) throws Exception {
		extent.HeaderChildNode("Product");

		Swipe("UP", 2);
		productText = getText(PlobalLoginPage.objCollectionProduct);
		WebElement productElement = getDriver().findElement(PlobalLoginPage.objCollectionProduct);
		verifyElementExist1(productElement, productText);
		verifyElementPresentAndClick(PlobalLoginPage.objCollectionFirstProduct, "Product Image");
		if (getText(ProductPage.objOpenProductText).equals(getText(ProductPage.objSelectedProductText))) {
			softAssertion.assertEquals(getText(ProductPage.objOpenProductText),
					getText(ProductPage.objSelectedProductText));

			logger.info(getText(ProductPage.objSelectedProductText) + " product Text is displayed");
			extent.extentLogger("Product Name",
					getText(ProductPage.objSelectedProductText) + " product Text is displayed");

			verifyIsElementDisplayed(ProductPage.objShareImage, "Share Button");
			verifyIsElementDisplayed(ProductPage.objWishlistImage, "Wish Button");

			if (verifyIsElementDisplayed(ProductPage.objOutofStock, getText(ProductPage.objOutofStock))) {
				click(ProductPage.objSize, "Select Size Dropdown box");
				scrollToElement(size);
				verifyElementPresentAndClick(ProductPage.objDoneBtn, "Done Button");
			}

			verifyIsElementDisplayed(ProductPage.objAddToCartBtn, "Add to Cart Button");
			verifyIsElementDisplayed(ProductPage.objBuyNowBtn, "Buy Now Button");
			if (verifyElementDisplayed(ProductPage.objAddToCartBtn) && verifyElementDisplayed(ProductPage.objBuyNowBtn)
					&& verifyElementDisplayed(ProductPage.objShareImage)
					&& verifyElementDisplayed(ProductPage.objWishlistImage)) {
				click(ProductPage.objAddToCartBtn, "ADD TO CART");
				verifyElementPresent(ProductPage.objProductAddedToCartMsg,
						getText(ProductPage.objProductAddedToCartMsg));
			}
		} else {
			logger.info(getText(ProductPage.objSelectedProductText) + " is different from "
					+ getText(ProductPage.objOpenProductText));
			extent.extentLoggerFail("Product Text", getText(ProductPage.objSelectedProductText) + " is different from "
					+ getText(ProductPage.objOpenProductText));
		}
		softAssertion.assertAll();
	}

	public void addToCart() throws Exception {
		extent.HeaderChildNode("Cart");

		verifyElementPresentAndClick(ProductPage.objCart, "Cart");
		iOSSwipeUp();
		String cartTotalPrice = getText(ProductPage.objCartItemTotalPrice);
		logger.info("Total Price :" + cartTotalPrice + "is displayed");
		extent.extentLogger("Total Price", cartTotalPrice + " is displayed");
		verifyElementPresentAndClick(ProductPage.objPlaceOrderBtn, "Place Order Button");
	}

	public void paymentCheckOut(String address) throws Exception {
		extent.HeaderChildNode("CheckOut");

		verifyIsElementDisplayed(CheckOutPage.objCheckOutHeader, getText(CheckOutPage.objCheckOutHeader));

		switch (address) {
		case "Remove":
			if (verifyIsElementDisplayed(CheckOutPage.objDeliveryAddressPlusBtn)) {
				click(CheckOutPage.objDeliveryAddressPlusBtn, "Plus Button");
				verifyIsElementDisplayed(CheckOutPage.objAddAddress);
				type(CheckOutPage.objFirstName, "Subbu", "First Name");
				click(CheckOutPage.objLastName, "Last Name");
				type(CheckOutPage.objLastName, "Vr", "Last Name");
				click(CheckOutPage.objContactNo, "Contact Number");
				type(CheckOutPage.objContactNo, "7070707070", "Contact Number");
				click(CheckOutPage.objAddress, "Address");
				type(CheckOutPage.objAddress, "Test", "Address");
				Back(1);
				click(CheckOutPage.objCountry, "Country Drop down box");
				scrollToElement("India");
				verifyElementPresentAndClick(ProductPage.objDoneBtn, "Done Button");

				click(CheckOutPage.objState, "State");
				type(CheckOutPage.objState, "Karnataka", "State");
				Back(1);
				click(CheckOutPage.objCity, "City");
				type(CheckOutPage.objCity, "Bangalore", "City");
				Back(1);
				click(CheckOutPage.objZipCode, "Zip Code");
				type(CheckOutPage.objZipCode, "560078", "Zip Code");
				Back(1);
				verifyElementPresentAndClick(ProductPage.objDoneBtn, "Done Button");
				click(CheckOutPage.objSaveBtn, "Save button");
				verifyElementDisplayed(CheckOutPage.objMessage);
				waitForElementDisplayediOS(CheckOutPage.objOkBtn, 20, getText(CheckOutPage.objOkBtn));
				verifyElementPresentAndClick(CheckOutPage.objOkBtn, "OK Button");
			} else {
				verifyElementPresentAndClick(CheckOutPage.objDeliveryAddresseditBtn, "Edit address");
				Swipe("UP", 1);
				verifyElementPresentAndClick(CheckOutPage.objRemoveAdd, "Remove Address Button");
				verifyIsElementDisplayed(CheckOutPage.objRemoveAddressMsg,
						"Are you sure you want to remove this address?");
				verifyElementPresentAndClick(CheckOutPage.objRemoveAddressYesBtn, "Yes Button");
				verifyElementPresent(CheckOutPage.objAddressRemovedSuccessfullyMsg,
						getText(CheckOutPage.objAddressRemovedSuccessfullyMsg));
				verifyElementPresentAndClick(CheckOutPage.objOkBtn, "OK Button");
				waitTime(3000);
				click(CheckOutPage.objDeliveryAddressPlusBtn, "Plus Button");
				verifyIsElementDisplayed(CheckOutPage.objAddAddress);
				type(CheckOutPage.objFirstName, "Subbu", "First Name");
				click(CheckOutPage.objLastName, "Last Name");
				type(CheckOutPage.objLastName, "Vr", "Last Name");
				click(CheckOutPage.objContactNo, "Contact Number");
				type(CheckOutPage.objContactNo, "7070707070", "Contact Number");
				click(CheckOutPage.objAddress, "Address");
				type(CheckOutPage.objAddress, "Test", "Address");
				Back(1);
				click(CheckOutPage.objCountry, "Country Drop down box");
				scrollToElement("India");
				verifyElementPresentAndClick(ProductPage.objDoneBtn, "Done Button");

				click(CheckOutPage.objState, "State");
				type(CheckOutPage.objState, "Karnataka", "State");
				Back(1);
				click(CheckOutPage.objCity, "City");
				type(CheckOutPage.objCity, "Bangalore", "City");
				Back(1);
				click(CheckOutPage.objZipCode, "Zip Code");
				type(CheckOutPage.objZipCode, "560078", "Zip Code");
				Back(1);
				verifyElementPresentAndClick(ProductPage.objDoneBtn, "Done Button");
				click(CheckOutPage.objSaveBtn, "Save button");
				verifyElementDisplayed(CheckOutPage.objMessage);
				verifyElementPresentAndClick(CheckOutPage.objOkBtn, "OK Button");
			}
			break;

		case "Edit":
			if (verifyIsElementDisplayed(CheckOutPage.objDeliveryAddressPlusBtn)) {
				click(CheckOutPage.objDeliveryAddressPlusBtn, "Plus Button");
				verifyIsElementDisplayed(CheckOutPage.objAddAddress);
				type(CheckOutPage.objFirstName, "Subbu", "First Name");
				click(CheckOutPage.objLastName, "Last Name");
				type(CheckOutPage.objLastName, "Vr", "Last Name");
				click(CheckOutPage.objContactNo, "Contact Number");
				type(CheckOutPage.objContactNo, "7070707070", "Contact Number");
				click(CheckOutPage.objAddress, "Address");
				type(CheckOutPage.objAddress, "Test", "Address");
				Back(1);
				click(CheckOutPage.objCountry, "Country Drop down box");
				scrollToElement("India");
				verifyElementPresentAndClick(ProductPage.objDoneBtn, "Done Button");

				click(CheckOutPage.objState, "State");
				type(CheckOutPage.objState, "Karnataka", "State");
				Back(1);
				click(CheckOutPage.objCity, "City");
				type(CheckOutPage.objCity, "Bangalore", "City");
				Back(1);
				click(CheckOutPage.objZipCode, "Zip Code");
				type(CheckOutPage.objZipCode, "560078", "Zip Code");
				Back(1);
				verifyElementPresentAndClick(ProductPage.objDoneBtn, "Done Button");
				click(CheckOutPage.objSaveBtn, "Save button");
				verifyElementDisplayed(CheckOutPage.objMessage);
				verifyElementPresentAndClick(CheckOutPage.objOkBtn, "OK Button");
			} else {
				verifyElementPresentAndClick(CheckOutPage.objDeliveryAddresseditBtn, "Edit address Image");
				Swipe("UP", 1);
				verifyElementPresentAndClick(CheckOutPage.objChooseAddressEditBtn, "Edit address Text");
				clearField(CheckOutPage.objFirstName, "First Name Field");
				click(CheckOutPage.objFirstName, "First Name Field");
				type(CheckOutPage.objFirstName, "PK", "First Name");
				Back(1);
				verifyElementPresentAndClick(CheckOutPage.objCancelBtn, "Cancel Button");
				click(CheckOutPage.objSaveBtn, "Save button");
			}
			break;

		case "Confirm_Address":

			verifyElementPresentAndClick(CheckOutPage.objDeliveryAddresseditBtn, "Edit address");
			verifyElementDisplayed(CheckOutPage.objChooseAddress);
			verifyElementPresentAndClick(CheckOutPage.objConfirmBtn, "Confirm Button");

			break;

		default:
			logger.info("invalid Address!!");
			extent.extentLogger("Address", "invalid Address!!");
			break;
		}

		waitTime(3000);
		click(CheckOutPage.objPaymentPlusBtn, "Add Payment Button");
		verifyIsElementDisplayed(CheckOutPage.objCreditCardHeader, "Credit Card Title");

		click(CheckOutPage.objCreditCardFirstName, "Credit Card First name");
		type(CheckOutPage.objCreditCardFirstName, "Subbu", "Credit Card First name");
		click(CheckOutPage.objCreditCardLastName, "Credit Card Last name");
		type(CheckOutPage.objCreditCardLastName, "Vr", "Credit Card Last name");

		click(CheckOutPage.objCreditCardExpiryDate, "Credit Card Expiry Date");
		type(CheckOutPage.objCreditCardExpiryDate, "1122", "Credit Card Expiry Date");
		click(CheckOutPage.objCreditCardCVV, "Credit Card CVV");
		type(CheckOutPage.objCreditCardCVV, "123", "Credit Card CVV");
		verifyElementPresentAndClick(ProductPage.objDoneBtn, "Done Button");
		verifyElementPresentAndClick(CheckOutPage.objCreditCardAddpaymentBtn, "Add Payment Button");

		if (verifyElementPresent(CheckOutPage.objSorryMsg, getText(CheckOutPage.objEnterFieldMsg))) {
			click(CheckOutPage.objOkBtn, "OK Button");
			click(CheckOutPage.objCreditCardNumber, "Credit Card Number");
			type(CheckOutPage.objCreditCardNumber, "1", "Credit Card Number");
			verifyElementPresentAndClick(ProductPage.objDoneBtn, "Done Button");
		}

		verifyElementPresentAndClick(CheckOutPage.objCreditCardAddpaymentBtn, "Add Payment Button");
		String itemCost = getText(CheckOutPage.objTotalPrice);
		itemCost = itemCost.substring(8);

		Swipe("up", 1);
		String totalAmt = getText(CheckOutPage.objTotalAmt);
		softAssertion.assertEquals(itemCost, totalAmt);
		click(CheckOutPage.objPlaceOrderBtn, "Place Order Button");

		verifyElementPresent(CheckOutPage.objThankYouTitle, "Thank You Page");
		logger.info(getText(CheckOutPage.objThankYouBagText) + " " + getText(CheckOutPage.objOrderId));
		extent.extentLogger("Order id",
				getText(CheckOutPage.objThankYouBagText) + " " + getText(CheckOutPage.objOrderId));
		verifyElementPresentAndClick(CheckOutPage.objContinueShoppingBtn, "Continue Shopping Button");
		waitTime(3000);
		verifyElementPresent(PlobalLoginPage.objHomePageLogo, "ESCAWAY Logo");
		logger.info("Navigated to HomePage");
		extent.extentLogger("Home", "Navigated to HomePage");
		softAssertion.assertAll();
	}

	public void searchProduct(String address) throws Exception {
		extent.HeaderChildNode("Search Product and Place order scenario");
		click(PlobalLoginPage.objSearchProduct, "Search Product Tab");

		Swipe("DOWN", 1);
		int count = 1;
		for (int i = 0; i < count; i++) {
			click(PlobalLoginPage.objSearchProduct, "Search Bar");
			waitTime(2000);
			if (verifyElementPresent(PlobalLoginPage.objSearchProduct, "Search Bar")) {
				count++;
				break;
			}
		}
		waitTime(4000);
		click(PlobalLoginPage.objSearchProduct, "Search Bar");
		type(PlobalLoginPage.objSearchProduct, "gold stone ring", "Search Bar");
		waitTime(5000);
		String prouctName = getText(PlobalLoginPage.objProductName);
		if (verifyIsElementDisplayed(PlobalLoginPage.objProductName, prouctName)) {
			click(PlobalLoginPage.objProductName, prouctName);
		}
		verifyIsElementDisplayed(PlobalLoginPage.objShareImage, "Share Button");
		verifyIsElementDisplayed(PlobalLoginPage.objWishlistImage, "Wish Button");
		verifyIsElementDisplayed(PlobalLoginPage.objAddToCartBtn, "Add to Cart Button");
		verifyIsElementDisplayed(PlobalLoginPage.objBuyNowBtn, "Buy Now Button");
		if (verifyElementDisplayed(PlobalLoginPage.objAddToCartBtn)
				&& verifyElementDisplayed(PlobalLoginPage.objBuyNowBtn)
				&& verifyElementDisplayed(PlobalLoginPage.objShareImage)
				&& verifyElementDisplayed(PlobalLoginPage.objWishlistImage)) {
			click(PlobalLoginPage.objBuyNowBtn, "BUY NOW");
		}
		paymentCheckOut(address);
	}

	public void barCodeProductSearch(String address) throws Exception {
		extent.HeaderChildNode("Search Product and Place order scenario");
		click(PlobalLoginPage.objSearchProduct, "Search Product Tab");

		waitTime(4000);
		waitForElementDisplayediOS(PlobalLoginPage.objSerachThroughBarCodeProduct, 10,
				getText(PlobalLoginPage.objSerachThroughBarCodeProduct));

		verifyIsElementDisplayed(PlobalLoginPage.objShareImage, "Share Button");
		verifyIsElementDisplayed(PlobalLoginPage.objWishlistImage, "Wish Button");
		verifyIsElementDisplayed(PlobalLoginPage.objAddToCartBtn, "Add to Cart Button");
		verifyIsElementDisplayed(PlobalLoginPage.objBuyNowBtn, "Buy Now Button");
		if (verifyElementDisplayed(PlobalLoginPage.objAddToCartBtn)
				&& verifyElementDisplayed(PlobalLoginPage.objBuyNowBtn)
				&& verifyElementDisplayed(PlobalLoginPage.objShareImage)
				&& verifyElementDisplayed(PlobalLoginPage.objWishlistImage)) {
			click(PlobalLoginPage.objBuyNowBtn, "BUY NOW");
		}
		paymentCheckOut(address);
	}

	public void logOut() throws Exception {
		extent.HeaderChildNode("Logout");

		verifyElementPresent(PlobalLoginPage.objHomePageLogo, "ESCAWAY Logo");
		logger.info("Navigated to HomePage");
		extent.extentLogger("Home", "Navigated to HomePage");

		verifyElementPresentAndClick(PlobalLoginPage.objProfileTab, "Profile Tab");
		Swipe("UP", 2);
		click(PlobalLoginPage.objLogOut, "Logout Button");
		verifyElementPresent(PlobalLoginPage.objLogOutMsg, getText(PlobalLoginPage.objLogOutMsg));
		verifyElementPresentAndClick(PlobalLoginPage.objLogOutYesBtn, "OK Button");
	}
}
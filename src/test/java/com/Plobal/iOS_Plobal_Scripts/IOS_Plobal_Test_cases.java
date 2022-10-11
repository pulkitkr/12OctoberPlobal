package com.Plobal.iOS_Plobal_Scripts;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.business.plobal.PlobalBusinessLogic;
import com.extent.ExtentReporter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class IOS_Plobal_Test_cases {

	private PlobalBusinessLogic PlobalBusinessLogic;

	@BeforeTest
	public void init() throws InterruptedException {
		PlobalBusinessLogic = new PlobalBusinessLogic("plobal");
	}

	@Parameters({ "invalidEmailId", "invalidPassword", "validEmailId", "validPassword" })
	@Test(priority = 1)
	public void plobalLogin(String invalidEmailId, String invalidPassword, String validEmailId, String validPassword) throws Exception {
		PlobalBusinessLogic.loginToApp(invalidEmailId, invalidPassword, validEmailId, validPassword);
		ExtentReporter.jiraID = "PP-69";
	}

	@Parameters({ "size" })
	@Test(priority = 2)
	public void plobalSelectItem(String size) throws Exception {
		PlobalBusinessLogic.productItem(size);
		ExtentReporter.jiraID = "PP-71";
	}

	@Test(priority = 3)
	public void addProductToCart() throws Exception {
		PlobalBusinessLogic.addToCart();
		ExtentReporter.jiraID = "PP-72";
	}

	@Parameters({ "address" })
	@Test(priority = 4)
	public void plobalDeliveryAddress(String address) throws Exception {
		PlobalBusinessLogic.paymentCheckOut(address);
		ExtentReporter.jiraID = "PP-73";
	}

	@Parameters({ "deliveryaddress" })
	@Test(priority = 5)
	public void plobalSearchProduct(String deliveryaddress) throws Exception {
		PlobalBusinessLogic.searchProduct(deliveryaddress);
		ExtentReporter.jiraID = "PP-74";
	}

	@Parameters({ "barCodeaddress" })
	@Test(priority = 6)
	public void plobalBarCodeSearchProduct(String barCodeaddress) throws Exception {
		PlobalBusinessLogic.barCodeProductSearch(barCodeaddress);
		ExtentReporter.jiraID = "PP-75";
	}

	@Test(priority = 7)
	public void plobalLogout() throws Exception {
		PlobalBusinessLogic.logOut();
		ExtentReporter.jiraID = "PP-76";
	}

	@AfterTest
	public void plobalAppQuit() throws Exception {
		PlobalBusinessLogic.tearDown();
	}
}

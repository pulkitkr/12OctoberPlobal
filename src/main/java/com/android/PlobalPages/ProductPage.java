package com.android.PlobalPages;

import org.openqa.selenium.By;

public class ProductPage {

	//Selected product Name
	public static By objSelectedProductText = By.xpath("//*[@id='ONE/ZERO BY KOOVS Contrast Panel Windproof Tracksuit' and @class='UIAStaticText']");
	
	//opened product text
	public static By objOpenProductText = By.xpath("//*[@id='ONE/ZERO BY KOOVS Contrast Panel Windproof Tracksuit']");
	
	//Add to cart Button
	public static By objAddToCartBtn = By.xpath("//*[@class='UIAButton' and @width>0 and (./preceding-sibling::* | ./following-sibling::*)[@text='ADD TO CART'] and ./parent::*[@class='UIAView']]");
	
	//Buy Now Button
	public static By objBuyNowBtn = By.xpath("//*[@class='UIAButton' and @width>0 and (./preceding-sibling::* | ./following-sibling::*)[@text='BUY NOW'] and ./parent::*[@class='UIAView']]");
	
	//Payment option Header
	public static By objPaymentOptionHeader = By.xpath("//*[@resource-id='plobaltestshutterstock.android.staging:id/payment_options_title_textview']");
	
	//Payment option select
	public static By objPaymentOptionSelect= By.xpath("//*[@resource-id='plobaltestshutterstock.android.staging:id/payment_list_item_TextView']");
	
	//share button
	public static By objShareImage= By.xpath("//*[@id='shareHomeProdList']");
	
	//Wish button
	public static By objWishlistImage= By.xpath("//*[@id='FavoritePDP']");
	
	//Select Size
	public static By objSize= By.xpath("//*[@id='XS']");
	
	//Out of stock
	public static By objOutofStock= By.xpath("//*[@id='OUT OF STOCK']");
	
	//Size
	public static By objuserSize(String size) {
		return By.xpath("//*[@text='" + size + "']");
	}
	
	//Done
	public static By objDoneBtn = By.xpath("//*[@text='Done']");
	
	//Product added to cart message
	public static By objProductAddedToCartMsg = By.xpath("//*[@text='Item added to cart' or contains(@text,'Updated the quantity of the Item to')]");
	
	//Cart
	public static By objCart = By.xpath("(//*[@accessibilityLabel='ONE/ZERO BY KOOVS Contrast Panel Windproof Tracksuit']/*[@class='UIAButton' and @width>0])[3]");
	
	//Cart item text
	public static By objItemText = By.xpath("//*[@id='ONE/ZERO BY KOOVS Contrast Panel Windproof Tracksuit']");
		
	//Cart header item price
	public static By objCartItemHeaderPrice = By.xpath("//*[@id='ONE/ZERO BY KOOVS Contrast Panel Windproof Tracksuit']");
	
	//Cart Total price
	public static By objCartItemTotalPrice = By.xpath("//*[@text='Total']/following-sibling::XCUIElementTypeStaticText");
	
	//Place Order button
	public static By objPlaceOrderBtn = By.xpath("//*[@id='PLACE ORDER']");
	
	//Total product
	public static By objTotalProduct = By.xpath("//*[@class='UIATextField']");
	
	//- button
	public static By objDeleteBtn = By.xpath("//*[@id='-']");
}
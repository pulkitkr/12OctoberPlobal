package com.android.PlobalPages;

import org.openqa.selenium.By;

public class CheckOutPage {
	
	//ChekOut Header
	public static By objCheckOutHeader = By.xpath("//*[@id='Checkout' and @class='UIAView']");
	
	//Delivery Address + button
	public static By objDeliveryAddressPlusBtn  = By.xpath("//*[@id='+' and (./preceding-sibling::* | ./following-sibling::*)[@accessibilityLabel='newAddress']]");
	
	//Delivery Address edit button
	public static By objDeliveryAddresseditBtn  = By.xpath("//*[@id='edit icon ' and (./preceding-sibling::* | ./following-sibling::*)[@accessibilityLabel='location']]");
	
	//Delivery Address text
	public static By objDeliveryAddressText  = By.xpath("//*[@resource-id='plobaltestshutterstock.android.staging:id/address_shipping_selected_address_title_textview']");
	
	//Add Address Header
	public static By objAddAddressHeader  = By.xpath("//*[@resource-id='plobaltestshutterstock.android.staging:id/add_address_title_textView']");
	
	//Choose Delivery Address
	public static By objChooseAddress  = By.xpath("//*[@id='Choose Address']");
	
	//Confirm Button
	public static By objConfirmBtn  = By.xpath("//*[@id='Confirm']");
	
	//Add Address Header
	public static By objAddAddress = By.xpath("//*[@id='SHIPPING ADDRESS']");
	
	//First Name Field
	public static By objFirstName = By.xpath("//*[@placeholder='First Name*']");
	
	//Last Name Field
	public static By objLastName = By.xpath("//*[@placeholder='Last Name*']");
	
	//Contact number Field
	public static By objContactNo = By.xpath("//*[@placeholder='Contact Number']");
	
	//Address line Field
	public static By objAddress = By.xpath("//*[@placeholder='Address line 1*']");
	
	//Country dropdown
	public static By objCountry = By.xpath("(//*[@class='UIAView']/descendant::XCUIElementTypeImage)[10]");
	
	//State Field
	public static By objState = By.xpath("//*[@class='UIAView' and ./*[@placeholder='State*']]");
	
	//City Field
	public static By objCity = By.xpath("//*[@placeholder='City*']");
	
	//Zip code Field
	public static By objZipCode = By.xpath("//*[@placeholder='Zip / Postal Code*']");
	
	//Invalid Zip code message
	public static By objInvalidZipCodeMsg = By.xpath("(//*[@resource-id='/hierarchy/android.widget.Toast'])[9]");
	
	//Save Button
	public static By objSaveBtn = By.xpath("//*[@id='SAVE ADDRESS']");
	
	//Message
	public static By objMessage = By.xpath("//*[@resource-id='android:id/message']");
	
	//Ok Btn
	public static By objOkBtn = By.xpath("//*[@id='Ok']");
	
	//Payment + button
	public static By objPaymentPlusBtn  = By.xpath("//*[@id='+']");
	
	//Credit Card header
	public static By objCreditCardHeader  = By.xpath("//*[@id='Credit Card']");
	
	//Credit Card Number Field
	public static By objCreditCardNumber  = By.xpath("//*[@placeholder='Credit Card Number*']");
	
	//Credit Card First name Field
	public static By objCreditCardFirstName  = By.xpath("//*[@placeholder='First Name']");
	
	//Credit Card Last name Field
	public static By objCreditCardLastName  = By.xpath("//*[@placeholder='Last Name']");
	
	//Credit Card Month year Field
	public static By objCreditCardExpiryDate  = By.xpath("//*[@placeholder='MM/YY']");
	
	//Invalid card details message
	public static By objInvalidCardDetailsMsg = By.xpath("//*[@resource-id='plobaltestshutterstock.android.staging:id/textinput_error']");
	
	//Credit Card CVV Field
	public static By objCreditCardCVV  = By.xpath("//*[@placeholder='CVV']");
	
	//ADD Payment Button
	public static By objCreditCardAddpaymentBtn = By.xpath("//*[@id='ADD PAYMENT']");
	
	//order total amount
	public static By objTotalAmt = By.xpath("(//*[@id='Total']/parent::XCUIElementTypeCell/descendant::XCUIElementTypeStaticText)[2]");
	
	//Place order Button
	public static By objPlaceOrderBtn  = By.xpath("//*[@id='CONTINUE']");
	
	//Thank you Title
	public static By objThankYouTitle  = By.xpath("//*[@id='Thank You' and @class='UIAView']");
	
	//Thank you Bag
	public static By objThankYouBagText  = By.xpath("//*[@id='THANK YOU']");
	
	//Order Id
	public static By objOrderId  = By.xpath("//*[@id='THANK YOU']/following-sibling::XCUIElementTypeOther/descendant::XCUIElementTypeStaticText");
	
	//Continue Shopping Button
	public static By objContinueShoppingBtn  = By.xpath("//*[@id='CONTINUE SHOPPING']");
	
	//Continue Shopping Button
	public static By objItemCost  = By.xpath("//*[contains(@text,'Items')]/following-sibling::XCUIElementTypeStaticText");
	
	//Remove Address
	public static By objRemoveAdd  = By.xpath("//*[@id='Remove']");
	
	//Remove this address message
	public static By objRemoveAddressMsg = By.xpath("//*[@id='Are you sure you want to remove this address?']");
	
	//Remove this address yes button
	public static By objRemoveAddressYesBtn = By.xpath("//*[@id='Yes']");
	
	//Choose address edit button button
	public static By objChooseAddressEditBtn = By.xpath("//*[@id='Edit']");
	
	//Add New Address
	public static By objAddNewAddress = By.xpath("//*[@id='Add New Address']");
	
	//Address removed successfully message
	public static By objAddressRemovedSuccessfullyMsg = By.xpath("//*[@id='Address removed successfully.']");
	
	//Sorry Msg
	public static By objSorryMsg = By.xpath("//*[@id='Sorry!']");
	
	//Error msg
	public static By objEnterFieldMsg = By.xpath("//*[@id='Sorry!' and @class='UIAStaticText']/following-sibling::XCUIElementTypeStaticText");
	
	//Next Button
	public static By objCancelBtn = By.xpath("//*[@id='Cancel']");
	
	//Total price on header
	public static By objTotalPrice = By.xpath("//*[contains(@text,'TOTAL')]");
}

package org.iomedia.galen.pages;

import java.sql.Timestamp;
import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.framework.Driver.TestDevice;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;

public class STP extends BaseUtil {

	private String driverType;
	
	public STP(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
		driverType = driverFactory.getDriverType().get();
	}
	
	private By STPUserName = By.xpath(".//*[@id='block-custom-block-user-info']");
	private By STPUserNameMobile = By.cssSelector("div.col-demo.black.text-left.bold.ng-binding");
	private By STPUserNameMobile1 = By.xpath("//div[@class='homeScreenText-Content']//h1"); 
	private By Welcome = By.xpath("//div[@class='homeScreenText-Content']");
	private By Invoicetext = By.cssSelector("span.ng-binding");
	private By hamburger = By.xpath(".//button[@ng-click='openSideMenu()']");
	private By myAccountMenuMobile = By.xpath(".//div[contains(@class, 'menuItemLink') and contains(., 'MY ACCOUNT')]");
	private By logoutMobile = By.xpath(".//div[contains(@class, 'menuItemLink') and contains(., 'MY ACCOUNT')]/..//ion-item[contains(text(), 'Log out')]");
	private By classicAMGRMobile = By.xpath(".//div[contains(@class, 'menuItemLink') and (starts-with(., 'CAM') or starts-with(., 'Classic AMGR'))]");
	private By AMGRMobile = By.xpath(".//div[contains(@class, 'menuItemLink') and (starts-with(., 'NAM') or starts-with(., 'AMGR'))]");
	private By signInLink = By.id("sign-in");
	private By mobileSignInLink = By.xpath(".//div[contains(@class, 'menuItemLink') and contains(., 'SIGN IN')]");
	private By emailAddressMobile = By.name("username");
	private By passwordMobile = By.name("password");
	private By signInButtonMobile = By.cssSelector("button[type='submit']");
	private By signInButton = By.id("edit-submit");
	private By emailAddress = By.id("edit-name");
	private By password = By.id("edit-pass");
//	private By myAccount = By.cssSelector("a#my-invoice.dropdown-toggle");
//	private By Logout =  By.xpath("//[@id='my-invoice']/li/a[contains(@href, 'user/logout')]");
	
	private By emailAddressSignUp = By.id("edit-email");
	private By firstNameSignUp = By.id("edit-name-first");
	private By lastNameSignUp = By.id("edit-name-last");
	private By passwordSignUp = By.id("edit-pin");
	private By confirmPasswordSignUp = By.id("edit-confirm-pin");
	private By streetAddressSignUp = By.id("edit-street-addr-1");
	private By citySignUp = By.id("edit-city");
	private By zipCodeSignUp = By.id("edit-zip");
	private By selectStateSignUp = By.xpath("(.//a[contains(text(), 'Please Select State')])[1]");
	private By mobilePhoneSignUp = By.id("edit-phone-cell");
	private By tc = By.id("edit-terms");
	private By checkout = By.id("edit-submit-button");
	
	private By emailAddressSignUpMobile = By.name("email");
	private By firstNameSignupMobile = By.name("name_first");
	private By lastNameSignUpMobile = By.name("name_last");
	private By passwordSignupMobile = By.name("pin");
	private By confirmPasswordSignUpMobile = By.name("confirm_pin");
	private By streetAddressSignUpMobile = By.name("street_addr_1");
	private By citySignUpMobile = By.name("city");
	private By zipCodeSignUpMobile = By.name("zip");
	private By stateSignUpMobile = By.name("state");
	private By mobilePhoneSignupMobile = By.name("phone_cell");
	private By tcMobile = By.xpath(".//input[@type='checkbox']/..//span");
	private By checkoutMobile = By.cssSelector("button[class*='button'][ type='submit']");
	private By payBuyFlow = By.xpath("//a[contains(@class,'btn-pay')]");
	private By selectPlan = By.cssSelector("div[class*='payment-plan-id'] a[class=selectyzeValue]");
	private By paymentPlan = By.xpath("//*[@class='UlSelectize']/li/a[contains(text(),'plan')][1]");
	private By selectCard = By.cssSelector("div[class*='choose-card'] a[class=selectyzeValue]");
	private By addNewCard = By.xpath("//*[@class='UlSelectize']/li/a[text()='Add New Card']");
	private By selectCardType = By.cssSelector("div[class*='ctype'] a[class=selectyzeValue]");
	private By cardExpiry = By.id("edit-exp-date");
	private By cardCVV = By.id("edit-cin");
	private By acceptTerms = By.id("edit-terms");
	private By cardNumber = By.id("edit-cc-num");
	private By submit = By.id("edit-submit");
	private By mob_payBuyFlow = By.xpath("//*[contains(@class,'button-full')]");
	private By mob_planDropdown = By.cssSelector("select[ng-model='paymentPlan.id']");
	private By mob_continuePlan = By.xpath("//*[contains(@ng-click,'continueToCardDetails()')]");
	private By mob_addNewCard = By.cssSelector("select[ng-model='selectedCard']");
	private By mob_cardNum= By.name("cc_num");
	private By mob_ccExpiry = By.name("exp_date");
	private By mob_cvv = By.name("cin");
	private By mob_continuePayment = By.xpath("//*[@name='paymentInfoForm']//button[contains(@class,'button-full')]");
	private By mob_continueBilling = By.xpath("//*[@name='billingInfoForm']//button[contains(@class,'button-full')]");
	private By mob_acceptTerms= By.cssSelector("label[class*='checkbox-terms'] span");
	private By mob_submit = By.xpath("//button[text()='Submit Payment']");
	private By thankyou = By.cssSelector("article[class*='payment-thankyou']");
	private By thankyou_mob = By.cssSelector("div[class*='thanksContent']");
	
	private By firstPendingInvoiceId = By.xpath(".//table[@id='invoices-list-pending-table']//tbody//tr[1]//a[1]");
	private By firstPendingInvoiceAmount = By.xpath(".//table[@id='invoices-list-pending-table']//tbody//tr[1]//td[3]");
	private By firstInvoiceLink_mob = By.cssSelector(".listRow:nth-child(2) a[ng-click*='goToInvoiceSummary']");
	private By firstInvoicePayButton_mob = By.cssSelector("div[ng-if*='pay_button'] a");
	private By firstInvoiceAmount_mob = By.cssSelector("[class*='invoiceTotalBar'] div[class*='text-right']");
	private By backArrow = By.cssSelector("button[ng-show='showBackButton']");
	
	public void clickBackArrow(TestDevice device){
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			click(backArrow, "Back Arrow");
			getElementWhenVisible(firstInvoiceLink_mob);
		}
	}
	
	public void buyGATickets(String paymentMode, String cardType, String cardNum, String ccExpiry, String CVV, TestDevice device) throws Exception{
		click(payBuyFlow, mob_payBuyFlow, "Pay", device);
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			if(!paymentMode.trim().equalsIgnoreCase("Full Payment"))
				selectFirstPaymentPlan_mob();
			clickContinuePlan();
			selectAddNewCard_mob();
		} else {
			if(!paymentMode.trim().equalsIgnoreCase("Full Payment"))
				selectFirstPaymentPlan();
			selectAddNewCard();
			selectCardType(cardType);
		}
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			type(cardCVV, mob_cvv, "Card CVV", CVV, device);
			type(cardExpiry, mob_ccExpiry, "Card Expiry" , ccExpiry, device);
			type(cardNumber, mob_cardNum, "Card Number", cardNum, device);
			clickContinuePayment();
			clickContinueBilling();
		} 
		click(acceptTerms, mob_acceptTerms, "Accept Terms",  device);
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			//Do Nothing
		} else {
			type(cardCVV, mob_cvv, "Card CVV", CVV, device);
			type(cardExpiry, mob_ccExpiry, "Card Expiry" , ccExpiry, device);
			type(cardNumber, mob_cardNum, "Card Number", cardNum, device);
		}
		click(submit, mob_submit, "Submit", device);
	}
	
	public void clickSubmitPayment_mob(){
		click(mob_submit, "Submit Payment");
	}
	public void selectAcceptTerms_mob(){
		click(mob_acceptTerms, "Accept Terms");
	}
	public void clickContinueBilling(){
		click(mob_continueBilling, "Continue Billing");
	}
	
	public void clickContinuePayment(){
		click(mob_continuePayment, "Continue Payment");
	}
	public void enter_CCdetails(String ccNum, String expiry, String cvv) throws Exception{
		type(mob_cardNum, "Mobile Card Number", ccNum);
		type(mob_ccExpiry,"Mobile ExpiryDate", expiry);
		type(mob_cvv, "Mobile CVV", cvv);
	}
	
	public void selectAddNewCard_mob() {
		selectByVisibleText(mob_addNewCard, "Add New Card", "Add New Card");	
		}
	
	public void clickContinuePlan(){
		click(mob_continuePlan, "Continue Plan");
	}
	
	public void selectFirstPaymentPlan_mob(){
//		click(mob_paymentPlan, "Payment plan" );
		selectByIndex(mob_planDropdown, "Plan Dropdown", 1);
	}
	
	public void clickPay_mob(){
		click(mob_payBuyFlow, "Pay GA flow");
	}

	
	public String getSTPUserName(){
		return getText(STPUserName).trim();
	}
	
	public String getSTPUserNameMobile() {
		getElementWhenVisible(this.Welcome);
		return getText(STPUserNameMobile1).trim();
	}
	
	public String getExistingUserNameMobile() {
		getElementWhenVisible(this.Invoicetext);
		return getText(STPUserNameMobile).trim();
	}
	
	public void STPLogout() {
		//http://stp-qa-stg.io-research.com/user/login
		String driverType = driverFactory.getDriverType().get();
		if(driverType.trim().toUpperCase().contains("ANDROID") ||driverType.trim().toUpperCase().contains("IOS")) {
			click(hamburger, "Hamburger");
			sync(500L);
			click(myAccountMenuMobile, "My Account");
			sync(500L);
			click(logoutMobile, "Log out");
		} else {
			String[] values = getDriver().getCurrentUrl().split("/");
			String newValue = "";
			for(int i = 0 ; i < values.length && i < 3; i++) {
				newValue += values[i] + "/";
			}
			newValue += "user/logout";
			getDriver().navigate().to(newValue);
		}
		getElementWhenPresent(By.xpath(".//div[contains(@class, 'menuItemLink') and contains(., 'SIGN IN')] | .//a[@href='/user/login']"));
	}
	
	public void STPClassicAMGR() {
		String driverType = driverFactory.getDriverType().get();
		if(driverType.trim().toUpperCase().contains("ANDROID") ||driverType.trim().toUpperCase().contains("IOS")) {
			click(hamburger, "Hamburger");
			sync(500L);
			click(classicAMGRMobile, "Classic AMGR");
		} else {
			String[] values = getDriver().getCurrentUrl().split("/");
			String newValue = "";
			for(int i = 0 ; i < values.length && i < 3; i++) {
				newValue += values[i] + "/";
			}
			newValue += "classic-amgr?redir=account/settings";
			getDriver().navigate().to(newValue);
		}
	}
	
	public void STPAMGR() {
		String driverType = driverFactory.getDriverType().get();
		if(driverType.trim().toUpperCase().contains("ANDROID") ||driverType.trim().toUpperCase().contains("IOS")) {
			click(hamburger, "Hamburger");
			sync(500L);
			click(AMGRMobile, "AMGR");
		} else {
			String[] values = getDriver().getCurrentUrl().split("/");
			String newValue = "";
			for(int i = 0 ; i < values.length && i < 3; i++) {
				newValue += values[i] + "/";
			}
			newValue += "/amgr?redir=invoice";
			getDriver().navigate().to(newValue);
		}
	}
	
	public boolean waitforStpLoggedInPage(TestDevice device) {
		getElementWhenPresent(By.xpath(".//div[contains(@class, 'menuItemLink') and contains(., 'MY ACCOUNT')]/..//ion-item[contains(text(), 'Log out')] | .//a[@href='/user/logout']"));
		return true;
	}
	
	public void clickSignInLink(TestDevice device) {
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			click(hamburger, "Hamburger");
			sync(500L);
			click(mobileSignInLink, "Sign In");
		} else {
			click(signInLink, "Sign In");
		}
	}
	
	public void clickSignInButton(TestDevice device) {
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			click(signInButtonMobile, "Sign In");
		} else {
			click(signInButton, "Sign In");
		}
	}
	
	public void typeEmailAddress(String emailAddress, TestDevice device) throws Exception{
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			type(emailAddressMobile, "Email Address", emailAddress);
		} else {
			WebElement we = getElementWhenVisible(this.emailAddress);
			if(driverType.trim().toUpperCase().contains("SAFARI")) {
				we.clear();
				we.sendKeys(emailAddress, Keys.TAB);
			} else {
				type(we, "Email address", emailAddress);
				we.sendKeys(Keys.TAB);
			}
		}
	}
	
	public void typePassword(String password, TestDevice device) throws Exception{
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			type(passwordMobile, "Password", password);
		} else {
			WebElement we = getElementWhenVisible(this.password);
			we.clear();
			we.sendKeys(password, Keys.TAB);
		}
	}
	
	public boolean isPasswordFieldVisible(TestDevice device){
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			return checkIfElementPresent(passwordMobile);
		} else {
			return checkIfElementPresent(this.password);
		}
	}
	
	public void login(String emailaddress, String password, TestDevice device) throws Exception{
		if(emailaddress.trim().equalsIgnoreCase("")){
			emailaddress = Dictionary.get("EMAIL_ADDRESS").trim();
			password = Dictionary.get("PASSWORD").trim();
		}
		
		if(emailaddress.trim().equalsIgnoreCase("") || password.trim().equalsIgnoreCase("") || emailaddress.trim().equalsIgnoreCase("EMPTY") || password.trim().equalsIgnoreCase("EMPTY")) {
			throw new SkipException("Please provide valid email address and password");
		}

//		int counter = 3;
//		do {
	    	typeEmailAddress(emailaddress, device);
			typePassword(password, device);
			clickSignInButton(device);
//			counter--;
//		} while(isPasswordFieldVisible(device) && counter > 0);
		
		Assert.assertTrue(waitforStpLoggedInPage(device), "Verify user get logged in");
	}
	
	private void type(By locator, By mobileLocator, String objName, String text, TestDevice device) throws Exception {
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			type(mobileLocator, objName, text, true);
		} else {
			type(locator, objName, text, true);
		}
	}
	
	private void click(By locator, By mobileLocator, String objName, TestDevice device) {
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			click(mobileLocator, objName);
		} else {
			click(locator, objName);
		}
	}
	
	public void createaccount(TestDevice device, String suffix) throws Exception {
		//******************* Fetch Current TimeStamp ************************
		java.util.Date today = new java.util.Date();
		Timestamp now = new java.sql.Timestamp(today.getTime());
		String tempNow[] = now.toString().split("\\.");
		final String sStartTime = tempNow[0].replaceAll(":", "").replaceAll(" ", "").replaceAll("-", "");
		String emailaddress = "test" + sStartTime + suffix + "@example.com";
		String password = "123456";
		
		type(firstNameSignUp, firstNameSignupMobile, "First name", "Test", device);
		type(lastNameSignUp, lastNameSignUpMobile, "Last name", "Test", device);
		type(emailAddressSignUp, emailAddressSignUpMobile, "Email Address", emailaddress, device);
		type(passwordSignUp, passwordSignupMobile, "Password", password, device);
		type(confirmPasswordSignUp, confirmPasswordSignUpMobile, "Confirm Password", password, device);
		type(streetAddressSignUp, streetAddressSignUpMobile, "Street Address", "110th Chantilly Avenue Road", device);
		type(citySignUp, citySignUpMobile, "City", "Atlanta", device);
		type(zipCodeSignUp, zipCodeSignUpMobile, "Zip Code", "12100", device);
		
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			selectByVisibleText(stateSignUpMobile, "Select State", "Illinois");
		} else {
			click(selectStateSignUp, "Select state");
			String xpath = getXpath(selectStateSignUp, "Select State", "", 0);
			xpath += "/..//ul/li/a[text()='Illinois']";
			click(By.xpath(xpath), "Illinois");
		}
		
		type(mobilePhoneSignUp, mobilePhoneSignupMobile, "Mobile Phone", "9599911452", device);
		click(tc, tcMobile, "Terms & Conditions", device);
		click(checkout, checkoutMobile, "Checkout", device);
		
		Assert.assertTrue(waitforStpLoggedInPage(device), "Verify user get logged in");
		Dictionary.put("NEW_EMAIL_ADDRESS", emailaddress);
		Dictionary.put("NEW_PASSWORD", password);
	}
	
	public void clickPay(){
		click(payBuyFlow, "Pay Button");
	}
	
	public void selectFirstPaymentPlan() {
		click(selectPlan, "Select plan");
		click(paymentPlan, "Payment plan");
	}
	
	public void selectAddNewCard(){
		click(selectCard, "Select card");
		click(addNewCard, "Add new card");
	}
	
	public void selectCardType(String cardType){
		click(selectCardType, "Select card type");
		click(By.xpath("//*[@class='UlSelectize']/li/a[text()='"+cardType+"']"), cardType);
	}
	
	public void typeCardNumber(String cardNum) throws Exception{
		type(cardNumber, "CC Number", cardNum);
	}
	
	public void typeCardExpiry(String expiry) throws Exception{
		type(cardExpiry, "CC Expiry Date", expiry);
	}
	
	public void typeCVV(String cvv) throws Exception{
		type(cardCVV, "CVV", cvv);
	}
	
	public void acceptTerms(){
		click(acceptTerms, "Accept TnC");
	}
	
	public void clickSubmit(){
		click(submit, "Submit Pay");
	}
	
	public boolean isConfirmationPageDisplayed(TestDevice device) {
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			getElementWhenVisible(thankyou_mob);
			return true;
		}
		else {
			getElementWhenVisible(thankyou);
			return true;
		}
	}
	
	public void clickFirstInvoiceContinue(){
		click(firstInvoiceLink_mob, "Continue First Invoice", firstInvoicePayButton_mob, 10);
	}
	
	public String getFirstPendingInvoiceId(TestDevice device) {
		String href="";
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			clickFirstInvoiceContinue();
			getElementWhenPresent(firstInvoicePayButton_mob);
			href = driverFactory.getDriver().get().getCurrentUrl(); 
		}
		else{
			href = getAttribute(firstPendingInvoiceId, "href");
		}
		href = href.trim().substring(href.trim().indexOf("/invoice/") + 9);
		if(!href.substring(0/*, href.lastIndexOf("/")*/).trim().contains("/")) {
			href = href.substring(0/*, href.lastIndexOf("/")*/).trim() + "/1";
		}
		return href.substring(0/*, href.lastIndexOf("/")*/);
	}
	
	public String getFirstPendingInvoiceAmtDue(TestDevice device) {
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			return getText(firstInvoiceAmount_mob).replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
		}
		return getText(firstPendingInvoiceAmount).replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
	}
}
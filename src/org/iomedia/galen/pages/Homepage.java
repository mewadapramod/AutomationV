package org.iomedia.galen.pages;

import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.framework.Driver.TestDevice;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.iomedia.galen.common.ManageticketsAAPI;
import org.iomedia.galen.common.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import java.awt.AWTException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Assert;

public class Homepage extends BaseUtil {

	private String driverType;
	public Homepage(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter, Assert Assert, org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<HashMapNew> sTestDetails) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		driverType = driverFactory.getDriverType().get();
	}

	private By languageSelector = By.xpath(".//*[@id='edit-item']/div/div[1]/span");
	private By languageList = By.xpath(".//*[@id='edit-item']/div/div[2]/ul/li");
	private By SignInText = By.xpath(".//*[@id='invoke-signin-modal']");
	private By donthaveAccount = By.xpath(".//*[@id='block-userentrycomponentblock']/div/div/div/div/div[2]/div/div/div/p | .//*[@id='block-userentrycomponentblock']//*[contains(@class,'account-postFormLink')]//p");
	private By mobileSignInLink = By.xpath(".//div[@class='mobile-signin']//*[text()='Sign In'] | //div[contains(@class,'mobile-signin')]//*[text()='Sign In'] | //div[contains(@class,'mobile-signin')]");
	//private By desktopSignInLink = By.xpath(".//div[@class='desktop-signin-dashboard']//a[text()='Sign In'] | //div[contains(@class,'account-account')]/div//form//button");
	private By desktopSignInLink = By.xpath(".//div[@class='desktop-signin-dashboard']//a[text()='Sign In'] | //div[contains(@class,'account-account')]/div//form//button| //a[@id='invoke-signin-modal']");
	private By signInLink1 = By.xpath(".//div[@class='mobile-signin']//a");
	private By forgotpassword = By.xpath(".//a[@href='/forgot']");
	public By emailAddress = By.xpath(".//input[@name='email']");
	private By submitBtn = By.xpath(".//button[@type='submit']");
	private By passwordLinkExpired = By.xpath("//*[contains(text(),'expired')]");
	private By enterPassword = By.xpath(".//input[@id='pwd']");
	private By reEnterpassword = By.xpath(".//input[@id='confirmpwd']");
	private By notificationBanner = By.xpath(".//div[starts-with(@class, 'notification-notificationBanner')]");
	private By cancelLink = By.xpath(".//div[contains(@class, 'anchorContainer')]//p");
	//private By reactComponent = By.cssSelector("div.react-root-account");
	private By passwordLink = By.xpath(".//input[@name='password']");
	private By signUpLink = By.xpath(".//a[@href='/signup']");
	private By signInReactLink = By.xpath(".//a[@href='/']");
	private By errorMessage = By.xpath(".//input[@name='email']/..//span[starts-with(@class, 'theme-error')]");
	private By userMenu = By.xpath("//*[contains(@id,'block-useraccountmenu')]//a");
	private By mobileUserMenu = By.cssSelector("button span[class='mobusericon']");
	private By logout = By.xpath("//*[@id='amgr-user-menu']/li/a[contains(@href, 'user/logout')]");
	private By loginMessage = By.cssSelector("div[class*='notification-notificationBannerText']"); //div[class*='notification-notificationBannerTitle'] ,
	private By error = By.cssSelector("div[class*='notification-notificationBannerText']");
	private By firstName = By.name("first_name");
	private By lastName = By.name("last_name");
	private By termsConditionsCheckBox = By.cssSelector("form div[class*='container-fluid'] div[class*='midFormLink'] > label div");
	private By agree = By.cssSelector("button.default");
	private By rememberCheckbox = By.cssSelector("label[data-react-toolbox='checkbox'] div");
	private By rememberCheckboxText = By.cssSelector("label[data-react-toolbox='checkbox'] span");
	private By backIcon = By.cssSelector("i[class*='modal-back-icon']");
	private By welcome1 = By.xpath(".//a[@href='#amgr-user-menu']");
	private By CopyrightLink = By.cssSelector(".footer p a");
	private By CopyrightText = By.cssSelector(".footer p");
	private By Policy= By.xpath("//div[@class='footer']/div/div/div/ul/li[1]/a");
	private By Policylink = By.xpath("//*[@class='footer']//a[contains(text(),'PRIVACY POLICY')]");
	private By Terms = By.xpath("//*[@class='footer']//a[contains(text(),'TERMS OF USE')] | //*[@class='footer']//li[3]/a");
	private By Termsofuse = By.xpath("//div[@class='container']/div/h1");
	//private By welcome = By.cssSelector(".react-root-dashboard-header > div > div > div:first-child > p:first-child");
	private By componentSubHeading = By.cssSelector("div[class*='componentSubHeading']");
	private By passwordShowHide = By.cssSelector(".passwordShowHide");
	private By privacy_terms_link = By.cssSelector("label[data-react-toolbox='checkbox'] div[class*='forms-anchorContainer'] p a, div[class*='account-midFormLink'] p a");
	private By resetpswdmessage = By.xpath("//div[@class='page__content']/div/div[2]/h2");
	private By Savechange =By.xpath(".//input[@type='submit' and @value='Save change']");
	private By expirelinkmessage = By.xpath("//div[@class='page__content']/div/h2");
	private By menuLinkItems = By.cssSelector("#block-iom-main-navigation-block li");
	private By popUpEventsComingUpMobile = By.xpath("//*[contains(@class,'takeOver')]//button[contains(text(),'No')]");
	private By popUpEventsComingUpWeb = By.xpath("//*[contains(@class,'takeOver')]//button[1]");
	private By showMore = By.xpath("//div[contains(@class,'notification-showMore')]");
	private By notification = By.id("notification-message-container");
	private ManageticketsAAPI aapi = new ManageticketsAAPI(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	private By notificationSection = By.xpath("//section[@role='body']");
	private By notificationMessageXpath = By.xpath("//section[@role=\"body\"]//*[contains(@class,'styles-transferTitle')]");
	private By messageonpage = By.xpath("//div[@class='messageTitle']");
	private By searchBox = By.xpath("//input[@id='searchBox']");
	private By suggestionBox =By.xpath("//div[@id='searchResults']");
	private By suggestionItem = By.xpath("//div[@class='item-name']");
	private By suggestionLitstext= By.xpath("//div[@id='searchResults']//following::div[@class=\"item-name\"]");

	Utils utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	
	public void verifyAllMenuNodesTextswithCMS(){
		List<WebElement> menuNodes = getWebElementsList(menuLinkItems);
		String[] menuTexts = new String[menuNodes.size()];
		for(int i=0; i < menuNodes.size();i++){
			menuTexts[i]=menuNodes.get(i).getText();
			Assert.assertEquals(menuTexts[i], Dictionary.get("CMSMenuNames"+i));
		}
	}


	public String getCurrentURL() {
		return getDriver().getCurrentUrl();
	}

	public String getPolicyinOtherLanguage() {
		return getText(Policy);
	}

	public String getTermsinOtherLanguage() {
		return getText(Terms);
	}

	public String getSignInTextinOtherLanguage() {
		return getText(SignInText);
	}

	public String getCopyRightTextinOtherLanguage() {
		return getText(CopyrightText);
	}

	public String getSignInButtonTextinOtherLang() {
		return getText(submitBtn);
	}

	public String getRememberMeTextinOtherLanguage() {
		return getText(rememberCheckboxText);
	}

	public String DontHaveAccountTextinOtherLanguage() {
		return getText(donthaveAccount);
	}

	public void clickSignInLink1() {
		click(signInLink1, "Sign In");
	}

	public void clickSignInLinkinOtherLang(TestDevice device) {
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			clickSignInLink(null);
		}
	}

	public String getSignInTextInOtherLang(TestDevice device){
		String text ="";
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			text = getText(signInLink1);
		}
		return text;
	}

	public String getForgotPasswordTextinOtherLanguage() {
		String text =  getText(forgotpassword);
		text = text.substring(0, text.length()-1);
		return text;
	}

	public void clickLanguageSelector() {
		click(languageSelector, "Language");
	}

	public void selectLanguageFromDropdowninHomepage(String language) {
		List<WebElement> list = HomepageElementofLanguage();
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getText().contentEquals(language)) {
				click(list.get(i),language);}
		}
	}

	public List<WebElement> HomepageElementofLanguage(){
		List<WebElement> itemlist = getWebElementsList(languageList);
		List<WebElement> listitem = new ArrayList<WebElement>();
		Iterator<WebElement> iter = itemlist.iterator();
		for(int i=0;i<itemlist.size();i++) {
			WebElement item = iter.next();
			listitem.add(item.findElement(By.tagName("a")));
		}
		return listitem;
	}

	public String getLoginMessage(){
		return getText(loginMessage);
	}

	public void clickPrivacyTermsLink() {
		click(privacy_terms_link, "Privacy/Terms link");
	}

	public void clickSendEmail() {
		click(submitBtn,"SEND");
	}

	public void clickSaveChanges() {
		click(Savechange,"SAVE CHANGE",30);
	}

	public String getresetpwdmessage() {
		return getText(resetpswdmessage);
	}

	public String getexpirelinkmessage() {
		return getText(expirelinkmessage);
	}

	public String getNotificationBannerTitleMessage() {
		String message = getText(loginMessage);
		return message;
	}

	public String getNotificationBannerTextMessage() {
		String message = getText(error);
		return message;
	}
	
	public String getErrorPageMessage() {
		String messages = getText(messageonpage);
		return messages;
	}
	
	public String searchFunctionality() {
		String message = getAttribute(searchBox, "placeholder", 2);
		return message;
	 
	}
	public void typeOnSearchBox(String text) throws Exception {
		type(searchBox, "Search Box",text);
	}
	
	public Boolean suggestionpopulated() throws Exception {
		return checkElementPresent(suggestionBox, 5);
	}
	
	public void verifyTextPresentInSuggestion(String searchedText) {
		List<WebElement> listElement = new ArrayList<WebElement>();
		listElement =  getDriver().findElements(suggestionLitstext);
		searchedText = searchedText.toLowerCase();
		for (WebElement webElement : listElement) {
			String elementText = webElement.getText().toLowerCase();
			if(!elementText.contains(searchedText)) {
				Assert.assertTrue(false, "Suggested Elements Does't contains the searched Text. SearchedText:"+searchedText+" Suggested Element:"+elementText);
				break;
			}
		}
		Assert.assertTrue(true, "Suggested Elements contains the searched Text ");
	}
	

	public void enterPassword(String password) {
		WebElement we = getElementWhenVisible(this.enterPassword);
		we.clear();
		we.sendKeys(password);
		WebElement we1 = getElementWhenVisible(this.reEnterpassword);
		we1.clear();
		we1.sendKeys(password);
	}

	public String getPrivacyTermsLinkUrl() {
		return getAttribute(privacy_terms_link, "href");
	}

	public String getCopyrightLink(){
		WebElement we = getElementWhenVisible(this.CopyrightLink);
		return we.getAttribute("href");
	}

	public String getPolicyLink(){
		WebElement we = getElementWhenVisible(this.Policy);
		return we.getAttribute("href");
	}

	public String getTermsLink(){
		WebElement we = getElementWhenVisible(this.Terms);
		return we.getAttribute("href");
	}

	public String getTermsofuse(){
		return getText(Termsofuse);
	}

	public void clickLogout(){
		click(userMenu,"UserMenu");
		click(logout, "Logout",10);
	}

	public void clickSignInLink(TestDevice device){
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			click(mobileSignInLink, "Sign In", submitBtn, 10);
		} else {
			if(checkIfElementPresent(desktopSignInLink))
				click(desktopSignInLink, "Sign In", submitBtn, 1);
		}
	}

	public void clickSignUpLink() {
		try {
			click(signUpLink, "Sign Up", 1);
		} catch(Exception ex) {
			if(checkIfElementPresent(passwordLink)) {
				// Do Nothing
			} else {
				click(signInReactLink, "Sign in");
				click(signUpLink, "Sign Up");
			}
		}
	}

	public void clickSignInReactLink() {

		if(checkIfElementPresent(signInReactLink,0))
			click(signInReactLink, "Sign in",10);

	}

	public void clickForgotPasswordLink(){
		if(checkIfElementPresent(signInReactLink))
			click(signInReactLink, "Sign in");

		click(forgotpassword, "Forgot password");

	}

	public void typeEmailAddress(String emailAddress) throws Exception{
		WebElement we = getElementWhenVisible(this.emailAddress);
		if(driverType.trim().toUpperCase().contains("SAFARI")) {
			we.clear();
			we.sendKeys(emailAddress, Keys.TAB);
			//utils.clearAndSetText(this.emailAddress,emailAddress);
			//type(we,"sss",emailAddress);
		} else {
			type(we, "Email address", emailAddress);
			we.sendKeys(Keys.TAB);
		}
	}

	public void reTypeEmailAddress(String emailAddress) throws Exception{
		WebElement we = getElementWhenVisible(this.emailAddress);
		if(driverType.trim().toUpperCase().contains("IOS")) {
			((JavascriptExecutor) getDriver()).executeScript("$(\"input[name*='email']\").val( '' )");
			//we.clear();
			we.sendKeys(emailAddress, Keys.TAB);
			type(we,"sss","xyz");
		} else {
			type(we, "Email address", emailAddress);
			we.sendKeys(Keys.TAB);
		}
	}

	public String getEmailAddress(){
		WebElement we = getElementWhenVisible(this.emailAddress);
		return we.getAttribute("value");
	}

	public void enterEmailAddress(String emailAddress){
		WebElement we = getElementWhenVisible(this.emailAddress);
		we.clear();
		we.sendKeys(emailAddress, Keys.TAB);
		getElementWhenVisible(componentSubHeading).click();
		int counter = 10;
		while(!isErrorMessageDisplayed() && counter > 0){
			if(driverType.trim().toUpperCase().contains("IOS")) {
				click(backIcon, "Back icon");
				clickSignInLink(null);
			}
			if(!isErrorMessageDisplayed()) {
				String emailAddressValue = getEmailAddress();
				if(emailAddressValue.trim().equalsIgnoreCase("")) {
					WebElement we1 = getElementWhenVisible(this.emailAddress);
					we1.sendKeys(emailAddress, Keys.TAB);
					getElementWhenVisible(componentSubHeading).click();
				}
			}
			counter--;
		}
		Assert.assertTrue(isErrorMessageDisplayed(), "Verify error message is displayed");
	}

	public void enterEmail(String emailaddress) {
		WebElement we = getElementWhenVisible(this.emailAddress);
		we.clear();
		we.sendKeys(emailaddress,Keys.TAB);
	}

	public void typeEmail(String emailAddress) throws AWTException{
		WebElement we = getElementWhenVisible(this.emailAddress);
		we.clear();
		we.sendKeys(emailAddress, Keys.TAB);
		getElementWhenVisible(componentSubHeading).click();

		int counter = 10;
		while(!isSendEnabled() && counter > 0){
			if(driverType.trim().toUpperCase().contains("IOS")) {
				click(backIcon, "Back icon");
				clickSignInLink(null);
			}
			if(!isSendEnabled()) {
				String emailAddressValue = getEmailAddress();
				if(emailAddressValue.trim().equalsIgnoreCase("")) {
					WebElement we1 = getElementWhenVisible(this.emailAddress);
					we1.sendKeys(emailAddress, Keys.TAB);
					getElementWhenVisible(componentSubHeading).click();
				}
			}
			counter--;
		}
		Assert.assertTrue(isSendEnabled(), "Verify send button is enabled");
	}

	public boolean isSendEnabled() {
		String value = getAttribute(submitBtn, "disabled");
		return value == null ? true : value.trim().equalsIgnoreCase("true") ? false : true;
	}

	public void clickSendButton(){
		click(submitBtn, "Send");
	}

	public void clickCopyright(){
		click(CopyrightLink, "Copyright © 2017 Ticketmaster All rights reserved.");
	}

	public void clickPolicy(){
		click(Policylink,"Privacy Policy");
	}

	public void clickTerms(){
		click(Terms,"Terms of Use");
		getElementWhenVisible(Termsofuse, 10);
	}

	public boolean isWelcomePageDisplayed(){
		return checkIfElementPresent(welcome1);
	}

	public boolean isNotificationBannerDisplayed(){
		return checkIfElementPresent(notificationBanner, 8);
	}

	public boolean isHomepageDisplayed(TestDevice device){
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			getElementWhenVisible(mobileSignInLink);
		} else {
			getElementWhenVisible(desktopSignInLink);
		}
		return true;
	}

	public boolean isSignInPageDisplayed(){
		return checkIfElementPresent(submitBtn);
	}

	public boolean isSignUpPageDisplayed(){
		return checkIfElementPresent(submitBtn);
	}

	public boolean isForgotpasswordPageDisplayed(){
		return checkIfElementPresent(submitBtn);
	}
	
	public boolean isPasswordLinkExpired(){
		return checkIfElementPresent(passwordLinkExpired);
	}

	public boolean isErrorMessageDisplayed(){
		return checkIfElementPresent(errorMessage, 1);
	}

	public boolean isDashBoardDisplayed() {
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))) {
			return utils.checkIfElementClickable(mobileUserMenu,10);
		} else return utils.checkIfElementClickable(userMenu,10);
	}

	public void clickCancelLink(){
		click(cancelLink, "Cancel");
	}

	public void clickPasswordLink(){
		click(passwordLink, "Password");
	}

	public void typePassword(String password){
		WebElement we = getElementWhenVisible(this.passwordLink);
		we.clear();
		we.sendKeys(password, Keys.TAB);
		if(!driverType.trim().toUpperCase().contains("IE")) {
			if(checkIfElementPresent(passwordShowHide, 1))
				getElementWhenVisible(passwordShowHide).click();
		}
	}

	public void clickRememberMe() {
		click(rememberCheckbox, "Remember me", 10);
	}

	public void clickSignInButton(){
		click(submitBtn, "Sign In",10);
	}
	private static int loginAttemptCounter=3; 

	public void login(String emailaddress, String password, TestDevice device, boolean interstitial) throws Exception{
		getDriver().manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
		if(!interstitial){
			clickSignInLink(device);
		}
		clickSignInReactLink();
		getDriver().manage().timeouts().implicitlyWait(Long.valueOf(Environment.get("implicitWait")), TimeUnit.MILLISECONDS);
		if(emailaddress.trim().equalsIgnoreCase("")){
			emailaddress = Dictionary.get("EMAIL_ADDRESS").trim();
			password = Dictionary.get("PASSWORD").trim();
		}
		if(emailaddress.trim().equalsIgnoreCase("") || password.trim().equalsIgnoreCase("") ||
				emailaddress.trim().equalsIgnoreCase("EMPTY") || password.trim().equalsIgnoreCase("EMPTY")) {
			throw new SkipException("Please provide valid email address and password");
		}
		typeEmailAddress(emailaddress);
				
	    if (password.equalsIgnoreCase("x9876")) {
	    	        System.out.println("Using Password as default");
	    			typePassword("x9876"); 
	    			clickRememberMe();
	    			clickSignInButton();
	    	   if(isNotificationBannerDisplayed()) {	    		   
	    		   System.out.println("Using Password as x98765");
	    			typePassword("x98765");
	    			clickRememberMe();
	    			clickSignInButton();
	    			 if(isNotificationBannerDisplayed()) {
	    			   System.out.println("Using Password as x9876x");
		    			typePassword("x9876x");
		    			clickRememberMe();
		    			clickSignInButton();
	    			   }
	    		   }	    	   			
		} 
	    if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
	    	typePassword(password);	
			//clickRememberMe();
			clickSignInButton();
	    }
	    
	    else {
		typePassword(password);	
		clickRememberMe();
		clickSignInButton();
	    }
	    
		Hamburger hamburger = new Hamburger(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
//                            if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS")) {
		try {
			((JavascriptExecutor) getDriver()).executeScript("$('#doorbell-button').remove()");
		} catch(Exception ex) {
			//Do Nothing
		}
//            }
		if(Environment.get("splunkLogIntegration").trim().equalsIgnoreCase("true")) {
			String sessionId = getSessionId();
			String searchQuery = sTestDetails.get().get("SEARCH_QUERY");
			if(searchQuery.trim().equalsIgnoreCase("")) {
				searchQuery = sessionId;
			} else {
				if(!sessionId.trim().equalsIgnoreCase("")) {
					searchQuery += "%20OR%20" + sessionId;
				}
			}
			sTestDetails.get().put("SEARCH_QUERY", searchQuery.trim());
		}

		if(getDriver().getCurrentUrl().contains("myevent")||getDriver().getCurrentUrl().contains("invoice")||getDriver().getCurrentUrl().contains("invite"))
		{
			boolean userLoggedIn = hamburger.waitforLoggedInPage(device);
			if(!userLoggedIn) {
				if(loginAttemptCounter > 0) {
					loginAttemptCounter--;
					Assert.assertTrue(true,"Login Not Success, Retrying Again... "+loginAttemptCounter+" Attempts left");
					login(emailaddress, password, device, interstitial);
					return;
				}else {
					loginAttemptCounter = 3;
				}
			}
			Assert.assertTrue(userLoggedIn, "Verify user get logged in");
		}
		else
		{

			boolean userLoggedIn = hamburger.waitforLoggedInPage(device);
			if (!userLoggedIn) {
				if (loginAttemptCounter > 0) {
					loginAttemptCounter--;
					Assert.assertTrue(true,
							"Login Not Success, Retrying Again... " + loginAttemptCounter + " Attempts left");
					login(emailaddress, password, device, interstitial);
					return;
				} else {
					loginAttemptCounter = 3;
				}
			}
			if (checkEventmangerTakeOver() || checkUpgardedTakeover() || checkNammeTakeover())
				try {
					if (driverType.trim().toUpperCase().contains("ANDROID")
							|| driverType.trim().toUpperCase().contains("IOS")) {
						getElementWhenClickable(popUpEventsComingUpWeb, 15);
						click(popUpEventsComingUpWeb, "Events Coming Up", 15);
					} else {
						getElementWhenClickable(popUpEventsComingUpWeb, 15);
						click(popUpEventsComingUpWeb, "Events Coming Up", 15);
					}

				} catch (Exception e) {

				}

			Assert.assertTrue(userLoggedIn, "Verify user get logged in");
		}
		loginAttemptCounter = 3;
	}

	public void stplogin(String emailaddress, String password, TestDevice device) throws Exception{
		if(emailaddress.trim().equalsIgnoreCase("")){
			emailaddress = Dictionary.get("EMAIL_ADDRESS").trim();
			password = Dictionary.get("PASSWORD").trim();
		}

		if(emailaddress.trim().equalsIgnoreCase("") || password.trim().equalsIgnoreCase("") || emailaddress.trim().equalsIgnoreCase("EMPTY") || password.trim().equalsIgnoreCase("EMPTY")) {
			throw new SkipException("Please provide valid email address and password");
		}

		typeEmailAddress(emailaddress);
		typePassword(password);
		clickRememberMe();
		clickSignInButton();

		STP stp = new STP(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
		Assert.assertTrue(stp.waitforStpLoggedInPage(device), "Verify user get logged in");
		if(Environment.get("splunkLogIntegration").trim().equalsIgnoreCase("true")) {
			String sessionId = getSessionId();
			String searchQuery = sTestDetails.get().get("SEARCH_QUERY");
			if(searchQuery.trim().equalsIgnoreCase("")) {
				searchQuery = sessionId;
			} else {
				if(!sessionId.trim().equalsIgnoreCase("")) {
					searchQuery += "%20OR%20" + sessionId;
				}
			}
			sTestDetails.get().put("SEARCH_QUERY", searchQuery.trim());
		}
	}

	private void click(By locator, String objName, By androidAppLocator, By iosAppLocator, long... waitSeconds) {
		int counter = 1;
		while(counter >= 0){
			try{
				WebElement we = getElementWhenClickable(locator, waitSeconds);
				if(we != null){
					javascriptClick(we, objName, androidAppLocator, iosAppLocator);
					Reporter.log("Verify user is able to click on " + objName.toLowerCase(), "User should able to click on " + objName.toLowerCase(), "User clicked on " + objName.toLowerCase() + " successfully", "Pass");
					break;
				}
			} catch(Exception ex){
				if(counter == 0){
					Reporter.log("Verify user is able to click on " + objName.toLowerCase(), "User should able to click on " + objName.toLowerCase(), "Not able to click on " + objName.toLowerCase(), "Fail");
					throw ex;
				}
				sync(500L);
				counter--;
			}
		}
	}

	public void createaccount(TestDevice device, boolean interstitial) throws Exception {
		if(!interstitial){
			clickSignInLink(device);
		}

		clickSignUpLink();
		if(checkIfElementPresent(firstName, 1))
			type(firstName, "First name", "Test", true);
		if(checkIfElementPresent(lastName, 1))
			type(lastName, "Last name", "Test", true);

		//******************* Fetch Current TimeStamp ************************
		java.util.Date today = new java.util.Date();
		Timestamp now = new java.sql.Timestamp(today.getTime());
		String tempNow[] = now.toString().split("\\.");
		final String sStartTime = tempNow[0].replaceAll(":", "").replaceAll(" ", "").replaceAll("-", "");
		String emailaddress = "test" + sStartTime + "@example.com";
		String password = "123456";
		typeEmailAddress(emailaddress);
		typePassword(password);
//                            click(termsConditionsCheckBox, "Terms & Conditions");
		click(submitBtn, "Sign Up");
		if(Environment.get("tncpop").trim().equalsIgnoreCase("false")) {
			//Do Nothing
		} else {
			try {
				this.click(agree, "Agree", null, By.name("AGREE"), 1);
			} catch(Exception ex) {
				//Do Nothing
			}
		}
		Hamburger hamburger = new Hamburger(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		Assert.assertTrue(hamburger.waitforLoggedInPage(device), "Verify user get logged in");
		Dictionary.put("NEW_EMAIL_ADDRESS", emailaddress);
		Dictionary.put("NEW_PASSWORD", password);
		
		
		
//                            if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS")) {
		try {
			((JavascriptExecutor) getDriver()).executeScript("$('#doorbell-button').remove()");
		} catch(Exception ex) {
			//Do Nothing
		}
//            }
		if(Environment.get("splunkLogIntegration").trim().equalsIgnoreCase("true")) {
			String sessionId = getSessionId();
			String searchQuery = sTestDetails.get().get("SEARCH_QUERY");
			if(searchQuery.trim().equalsIgnoreCase("")) {
				searchQuery = sessionId;
			} else {
				if(!sessionId.trim().equalsIgnoreCase("")) {
					searchQuery += "%20OR%20" + sessionId;
				}
			}
			sTestDetails.get().put("SEARCH_QUERY", searchQuery.trim());
		}
	}

	public void createaccount(String emailaddress, TestDevice device, boolean interstitial) throws Exception {
		if(!interstitial){
			clickSignInLink(device);
		}

		clickSignUpLink();
		if(checkIfElementPresent(firstName, 1))
			type(firstName, "First name", "Test", true);
		if(checkIfElementPresent(lastName, 1))
			type(lastName, "Last name", "Test", true);
		String password = "123456";
		typeEmailAddress(emailaddress);
		typePassword(password);
		//click(termsConditionsCheckBox, "Terms & Conditions");
		click(submitBtn, "Sign Up");
		if(Environment.get("tncpop").trim().equalsIgnoreCase("false")) {
			//Do Nothing
		} else {
			try {
				
				//click(agree, "Agree", null, By.name("AGREE"), "Agree", 10);
				click(agree, "Agree",20);
			} catch(Exception ex) {
				//Do Nothing
			}
		}

		Hamburger hamburger = new Hamburger(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		Assert.assertTrue(hamburger.waitforLoggedInPage(device), "Verify user get logged in");
//                      Dictionary.put("NEW_EMAIL_ADDRESS", emailaddress);
		Dictionary.put("NEW_PASSWORD", password);
//                            if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS")) {
		try {
			((JavascriptExecutor) getDriver()).executeScript("$('#doorbell-button').remove()");
		} catch(Exception ex) {
			//Do Nothing
		}
//            }
		if(Environment.get("splunkLogIntegration").trim().equalsIgnoreCase("true")) {
			String sessionId = getSessionId();
			String searchQuery = sTestDetails.get().get("SEARCH_QUERY");
			if(searchQuery.trim().equalsIgnoreCase("")) {
				searchQuery = sessionId;
			} else {
				if(!sessionId.trim().equalsIgnoreCase("")) {
					searchQuery += "%20OR%20" + sessionId;
				}
			}
			sTestDetails.get().put("SEARCH_QUERY", searchQuery.trim());
		}
	}

	public void get(String uri) {
		load(uri);
		WebElement we = getElementWhenPresent(By.xpath(".//input[@name='email'] | .//div[@class='mobile-signin']//*[text()='Sign In'] | .//div[@class='desktop-signin-dashboard']//a[text()='Sign In']"));
		Assert.assertNotNull(we, "Verify page is displayed");
	}
	
	public void verifyInterstitialPage() {
		WebElement we = getElementWhenPresent(By.xpath(".//input[@name='email'] | .//div[@class='mobile-signin']//*[text()='Sign In'] | .//div[@class='desktop-signin-dashboard']//a[text()='Sign In']"));
		Assert.assertNotNull(we, "Verify page is displayed");
	}

	public void redirect(String link) {
		getDriver().navigate().to(link);
	}
	
	public void popUpEventsComingUp() {
		if (utils.checkIfElementClickable(popUpEventsComingUpWeb, 10)) {

			if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
				getElementWhenClickable(popUpEventsComingUpWeb,30);
				click(popUpEventsComingUpWeb, "Events Coming Up",30);
			}else
			{
				getElementWhenClickable(popUpEventsComingUpWeb,30);
				click(popUpEventsComingUpWeb, "Events Coming Up",30);
			}
		}else {

		}
	}
	
	/**
	 * This method will check checkEventmangerTakeOver takeover setting and return true when takeover is on 
	 * @return
	 */
	private boolean checkEventmangerTakeOver() {
		try {
			long value =  (long)((JavascriptExecutor)this.getDriver()).executeScript("return drupalSettings.componentConfigData.takeover.event_takeover.enabled");
			if(value==1)
				return true;
		} catch (WebDriverException var5) {
			var5.getMessage();
		}
		return false;
	}
	/**
	 * This method will check upgraded takeover setting and return true when takeover is on 
	 * @return
	 */
	private boolean checkUpgardedTakeover() {
		try {
			boolean value =  (boolean)((JavascriptExecutor)this.getDriver()).executeScript("return drupalSettings.contentManagerConfigData.enable_takeover");
			return value;
		} catch (WebDriverException var5) {
			var5.getMessage();
		}
		return false;
	}
	
	/**
	 * This method will check namme takeover setting and return true when takeover is on 
	 * @return
	 */
	private boolean checkNammeTakeover() {
		try {
			String value =  (String)((JavascriptExecutor)this.getDriver()).executeScript("return drupalSettings.contentManagerConfigData.takeover_json_url");
			if(!value.equalsIgnoreCase("false"))
				return true;
		} catch (Exception var5) {
			//This feature will available on 4.8.0 release
			var5.getMessage();
		}
		return false;
	}
	
	
	public void enterSiteName() {
		
		
	}


	public void verifyNotificationPresent() throws Exception {
		if(checkElementPresent(showMore, 10)) {
			click(showMore, "Show More");
		}
		Map<String, Object> customerDetail = aapi.getCustomerName();
		System.out.println(customerDetail);
		String expectedNotificationMessage = customerDetail.get("ACTUAL_CUST_NAME") +" has sent you 1 tickets. Sign in or create an account to view";
		String notificationMessage = getText(notification).split("the offer")[0].trim();
		
		Assert.assertEquals(notificationMessage, expectedNotificationMessage,"Notification message present on interstitial page before login");
		System.out.println("Leaving");
	}


	public void verifyNotificationSectionIsDisplayed() throws Exception {
		if(checkElementPresent(notificationSection, 10)) {
			String notificationMessage = getText(notificationMessageXpath);
			Map<String, Object> customerDetail = aapi.getCustomerName();
			String expectedNotificationMessage = customerDetail.get("ACTUAL_CUST_NAME") +" sent you 1 ticket(s).";
			Assert.assertEquals(notificationMessage, expectedNotificationMessage,"Notification message present on interstitial page after login");
			
		}else {
			Assert.assertTrue(false, "Notification not present when login through Transfer ticket interstitial page");
		}
	}


	public void acceptTransferTicket() {
		getDriver().findElement(By.xpath("//button[text()='Accept']")).click();
		sync(5000l);		
	}


}
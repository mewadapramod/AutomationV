package org.iomedia.galen.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver.HashMapNew;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.iomedia.galen.common.Utils;

public class CAM extends BaseUtil {

	public CAM(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<HashMapNew>[] sTestDetails) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	}

	//private By manageByPersonalProfile = By.id("link-personalprofile");
	private By manageByPersonalProfile = By.xpath("//a[@id='link-personalprofile']| //a[text()='Manage My Personal Profile']");
	private By firstName = By.id("iNameFirst");
	private By lastName = By.id("iNameLast");
	private By streetAddress = By.id("iStreetAddr1");
	private By streetAddress2 = By.id("iStreetAddr2");
	private By city = By.id("iCity");
	private By state = By.id("iState");
	private By country = By.id("iCountry");
	private By zipCode = By.id("iZip");
	private By emailAddress = By.id("iEmail");
	private By dayPhone = By.id("iPhoneDay");
	private By eveningPhone = By.id("iPhoneEve");
	private By pin = By.id("iPIN");
	private By saveButton = By.id("btn-save");
	private By eventContinue = By.xpath(".//a[text()='continue']");
//	private By dateandtimepresent = By.xpath("(//td[@class=\"padR10\"][3])/self::*[not(text()='various' or text()=' ') and contains(text(),',')]");
	private By dateandtimepresent = By.xpath("(//td[@class='padR10'][3])/self::*[not(text()='various' or text()=' ' ) and preceding-sibling::td[contains(.,'Tampa Bay vs Game 8')] and contains(text(),',')]");
	private By nextLink = By.id("next");
	private By paginationStop = By.xpath("(//span[@class=\"pagination-stop\"])[1]");
	private By paginationTotal = By.xpath("(//span[@class=\"pagination-total\"])[1]");
	private By no_of_seats = By.cssSelector("select[class*='seats-option']");
	private By find_seats = By.id("find-seat");
	private By addtoCart = By.xpath(".//a[text()='add to cart']");
	private By delivery_method = By.cssSelector("select[class*='delivery-method']");
	private By checkOut = By.cssSelector("a[class*='checkout']");
	private By surveyQuestionsDialogBox = By.xpath(".//div[contains(@id, 'popup-content') and contains(., 'Survey Questions')]");
	private By pay_in_installments = By.id("pay-in-installments");
	private By card_type = By.id("card-type-full");//Credit/Debit Card
	private By card_type_installments = By.id("card-type-installments");
	private By payment_plan = By.cssSelector("input[type='radio']");
	private By choose_plan = By.id("choose-plan-step");
	private By addNewCardButton = By.id("btn-add-card");
	private By first_name = By.id("ccFirstName");
	private By last_name = By.id("ccLastName");
	private By address = By.id("ccAddress");
	private By zipcode = By.id("ccZipCode");
	private By cardtype = By.id("ccCardType");
	private By cardNumber = By.id("ccCardNumber");
	private By expirationdateMM = By.id("ccExpirationDateMM");
	private By expirationdateYYYY = By.id("ccExpirationDateYYYY");
	private By cvv = By.id("ccVcc");
	private By store_new = By.id("store_new");
	private By next_step = By.id("next-step-full");
	private By next_step_installments = By.id("next-step-installments");
	private By t_c = By.id("terms-and-condition");

	private By eventName = By.cssSelector("#cart-table td:nth-child(2) b");
	private By seatDetails = By.cssSelector("#cart-table tr:first-child td:nth-child(3)");
	private By order_number = By.xpath(".//td[@class='bld' and text()='Confirmation Number']/../td[2]");
	private By invoice_number = By.xpath(".//td[@class='bld' and text()='Invoice Number']/../td[2]");
	private By outstanding_balance = By.xpath(".//td[contains(@class,'bld') and text()='Outstanding Balance']/../td[2]"); 
	private By Select_All = By.xpath("//*[@id=\"all-filter\"]");

	private By INET_RETURN= By.id("is-20314-128"); 
	//private By INET_RETURN= By.xpath("//table[@id='datatables']/tbody/tr[2]/td/table/tbody/tr/td[4]");
	private By cart_table = By.id("cart-table");
	private By account_home = By.id("account-home");
	private By side_timer=By.id("side-timer");
	
	private By dialogBox = By.cssSelector(".ui-dialog");


	public void updateProfile(String firstName, String lastName, String streetAddress, String streetAddress2, String city, String state, String country, String zipCode, String emailAddress, String dayPhone, String eveningPhone, String pin) throws Exception {
		//((JavascriptExecutor) getDriver()).executeScript("$('.ui-dialog').remove()");
	
		if(getAttribute(this.firstName, "value").trim().equalsIgnoreCase(""))
			type(this.firstName, "First name", firstName);
		if(getAttribute(this.lastName, "value").trim().equalsIgnoreCase(""))
			type(this.lastName, "Last name", lastName);
		if(getAttribute(this.streetAddress, "value").trim().equalsIgnoreCase(""))
			type(this.streetAddress, "Street address", streetAddress);
		if(getAttribute(this.streetAddress2, "value").trim().equalsIgnoreCase(""))
			type(this.streetAddress2, "Street address 2", streetAddress2);
		if(getAttribute(this.city, "value").trim().equalsIgnoreCase(""))
			type(this.city, "City", city);
		if(getAttribute(this.country, "value").trim().equalsIgnoreCase(""))
			selectByVisibleText(this.country, "Country", country);
		if(getAttribute(this.state, "value").trim().equalsIgnoreCase(""))
			selectByVisibleText(this.state, "State", state);
		if(getAttribute(this.zipCode, "value").trim().equalsIgnoreCase(""))
			type(this.zipCode, "Zip code", zipCode);

		String startString = emailAddress.trim().substring(0, emailAddress.trim().lastIndexOf("@"));
		String endString = emailAddress.trim().substring(emailAddress.trim().lastIndexOf("@") + 1);
		emailAddress = startString + "_new" + "@" + endString;

		type(this.emailAddress, "Email address", emailAddress);

		if(getAttribute(this.dayPhone, "value").trim().equalsIgnoreCase(""))
			type(this.dayPhone, "Day phone", dayPhone);
		if(getAttribute(this.eveningPhone, "value").trim().equalsIgnoreCase(""))
			type(this.eveningPhone, "Evening phone", eveningPhone);

		type(this.pin, "Pin", pin);

		click(saveButton, "Save");
		Dictionary.put("NEW_EMAIL_ADDRESS", emailAddress);
	}

	public void clickManagePersonalProfile() {
	    ((JavascriptExecutor) getDriver()).executeScript("$('.ui-dialog').remove()");
		//((JavascriptExecutor) getDriver()).executeScript("function removeOverlay() {			$('.ui-dialog').remove();			$('.ui-widget-overlay').remove();		}		setInterval(removeOverlay,500);");
		sync(1000l);
		click(manageByPersonalProfile, "Manage personal profile");
	}

	public void clickFirstEventContinue() {
//		((JavascriptExecutor) getDriver()).executeScript("$('.ui-dialog').remove()");
		removeDialogIfPresent();
		System.out.println("CAM EVENT :: "+Environment.get("cam_Event"));
		if(!Environment.get("cam_Event").trim().equalsIgnoreCase("")) {
			Utils utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
			utils.navigateTo("/classic-amgr?redirect_url=buy/details/" + Environment.get("cam_Event").trim().toUpperCase());
		} else {
			getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			int paginationTotal_ = Integer.valueOf(getText(paginationTotal, 1));
			int paginationStop_ = 0;
			doStart: do {
				List<WebElement> text = getDriver().findElements(dateandtimepresent);

				for (WebElement dateAndTime : text) {
					dateAndTime.findElement(By.xpath("./following::a[text()='continue']")).click();
					getDriver().manage().timeouts().implicitlyWait(Long.valueOf(Environment.get("implicitWait")), TimeUnit.MILLISECONDS);
					break doStart;
				}

				clickOnNextButton();
				// click(eventContinue, "Continue");
				paginationStop_ = Integer.valueOf(getText(paginationStop, 1));
			} while (paginationTotal_ > paginationStop_);
		}
	}

	private void clickOnNextButton() {
		click(nextLink, "next >>", 2);
		sync(1000l);
	}

	public void selectNoOfSeats(String value) {
//		((JavascriptExecutor) getDriver()).executeScript("$('.ui-dialog').remove()");
		removeDialogIfPresent();
		selectByVisibleText(no_of_seats, "Seats option", value);
	}

	public void clickFindSeats() {
		sync(1000l);
//		((JavascriptExecutor) getDriver()).executeScript("$('.ui-dialog').remove()");
		removeDialogIfPresent();
		click(find_seats, "Find Seats");
	}

	public void clickAddToCart() {
		sync(1000l);
		printTimeLeft(5);
//		((JavascriptExecutor) getDriver()).executeScript("$('.ui-dialog').remove()");
		removeDialogIfPresent();
		click(addtoCart, "Add to cart");
	}
	
	private void printTimeLeft(int counter) {
		String time = getText(side_timer, 30);
		if (time.isEmpty() || time.length() == 0) {
			sync(500l);
			printTimeLeft(counter - 1);
		}
	}

	public void selectDeliveryMethod() {
		sync(2000L);
		printTimeLeft(5);
//		((JavascriptExecutor) getDriver()).executeScript("$('.ui-dialog').remove()");
		removeDialogIfPresent();
		//		selectByIndex(delivery_method, "Delivery Method", 1);
		sync(1000l);
		selectByValue(delivery_method, "Delivery Method", "FE-");
		sync(1000L);
	}

	public void clickCheckout() {
		sync(500l);
		try {
//			((JavascriptExecutor) getDriver()).executeScript("$('.ui-dialog').remove()");
			removeDialogIfPresent();
			//click(checkOut, "Check out", By.xpath(".//div[contains(@id, 'popup-content') and contains(., 'Survey Questions')] | .//*[@id='pay-in-installments']"), 10);
			click(checkOut, "Check out", 10);
		} catch(Exception ex) {
			selectDeliveryMethod();
			click(checkOut, "Check out", By.xpath(".//div[contains(@id, 'popup-content') and contains(., 'Survey Questions')] | .//*[@id='pay-in-installments']"), 10);
		}
	}

	public void clickPayInInstallmentsTab() {
//		((JavascriptExecutor) getDriver()).executeScript("$('.ui-dialog').remove()");
		removeDialogIfPresent();
		click(pay_in_installments, "Pay in installments tab");
	}

	public void selectSeatsFromSTR(String accountId) throws Exception {
//		((JavascriptExecutor) getDriver()).executeScript("$('.ui-dialog').remove()");
		removeDialogIfPresent();
		getDriver().switchTo().frame("iomedia");
		try{
			getElementWhenVisible(By.cssSelector("#Text_Search"));
			AdminLogin adminLogin = new AdminLogin(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
			String url = driverFactory.getDriver().get().getCurrentUrl();
			String eventId = url.trim().substring(url.trim().lastIndexOf("/") + 1).trim();
			String sectionId = adminLogin.getAvailableSectionId(accountId, eventId);
			//			getElementWhenVisible(By.cssSelector("#Complete_Section_102"));
			((JavascriptExecutor) getDriver()).executeScript("$('#Complete_Section_" + sectionId + "').click();");
			click(By.cssSelector(".seatAvailable"), "Seat Available");
			click(By.id("leftAddToCartBtn"), "Add to cart");
			click(By.xpath(".//div[@id='leftTicketTypeHolder']//div[@class='typeName' and contains(text(), 'Adult')]/..//div[@class='typebtn']"), "Select adult seat");
			click(By.id("checkOutOpenClose"), "Open checkout");
			click(By.cssSelector("#holdSeatsButton h6"), "Continue");
			click(By.cssSelector("#checkoutBtn"), "Checkout");
		} finally {
			getDriver().switchTo().parentFrame();
		}
	}
	
	//needs to choose correct filter then click on first event with date at cam
	public void buyTickets(String paymentMode, String cardType, String cardNum, String ccExpiry, String CVV, String accountId) throws Exception {
		//((JavascriptExecutor) getDriver()).executeScript("$('.ui-dialog').remove()");
		
		checkIfElementPresent(dialogBox, 10);
		removeDialogIfPresent();
		click(Select_All, "Select All");
		//getDriver().findElement(Select_All).click();
		getDriver().findElement(INET_RETURN).click();
		clickFirstEventContinue();
		
		if(checkIfElementPresent(no_of_seats)) {
			selectNoOfSeats("1");
			clickFindSeats();
			clickAddToCart();
		} else {
			selectSeatsFromSTR(accountId);
		}
		selectDeliveryMethod();
		clickCheckout();
		if(checkIfElementPresent(surveyQuestionsDialogBox, 10)) {
			fillSurveyForm();
		}
		System.out.println(getText(side_timer, 30));
		if(!paymentMode.trim().equalsIgnoreCase("Full Payment")) {
			clickPayInInstallmentsTab();
			click(payment_plan, "Payment plan");
			click(choose_plan, "Choose plan");
			selectByVisibleText(card_type_installments, "Card type", "Credit/Debit Card");
		} else
			selectByVisibleText(card_type, "Card type", "Credit/Debit Card");

		if(checkIfElementPresent(By.cssSelector(".bankTable thead")))
			click(addNewCardButton, "Add a new card");

		fillCardDetails(paymentMode, cardType, cardNum, ccExpiry, CVV);

		click(t_c, "Terms & conditions");
		sync(500l);
		if(!paymentMode.trim().equalsIgnoreCase("Full Payment")) {
			click(next_step_installments, "Submit order");
		} else
			click(next_step, "Submit order");

		sync(10000l);
		removeDialogIfPresent();
		System.out.println("URL After Ticket Checkout is :: " + getDriver().getCurrentUrl());
		
		getElementWhenClickable(cart_table, 30);
//		getElementWhenVisible(cart_table);

		String eventName = getText(this.eventName);
		String confirmationNumber = getText(order_number);
		String seatDetails = getText(this.seatDetails);
		String invoiceNumber = "";
		String outstandingBalance = "";

		if(!paymentMode.trim().equalsIgnoreCase("Full Payment")) {
			invoiceNumber = getText(invoice_number);
			outstandingBalance = getText(outstanding_balance).replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
		}

		Dictionary.put("EVENT_NAME", eventName);
		Dictionary.put("CONF_NUMBER", confirmationNumber);
		Dictionary.put("SEAT_DETAILS", seatDetails);
		Dictionary.put("AMT_DUE", outstandingBalance);
		Dictionary.put("INVOICE_NUMBER", invoiceNumber);
		
		click(account_home, "Account home");
	}

	public void fillCardDetails(String paymentMode, String cardType, String cardNum, String ccExpiry, String CVV) throws Exception {
		sync(2000L);
//		((JavascriptExecutor) getDriver()).executeScript("$('.ui-dialog').remove()");
		removeDialogIfPresent();
		selectByVisibleText(cardtype, "Card type", cardType);
		type(cardNumber, "Card number", cardNum);
		String[] vals = ccExpiry.trim().split("/");
		selectByVisibleText(expirationdateMM, "MM", vals[0]);
		selectByVisibleText(expirationdateYYYY, "YYYY", vals[1]);
		type(cvv, "Cvv", CVV);
		type(first_name, "First name", "test");
		type(last_name, "Last name", "test");
		type(address, "Address", "test");
		type(zipcode, "Zip code", "12100");
		click(store_new, "Store my credit/debit card and billing information for future use on Unitas Testing Ticket Management's Account.");
		sync(500l);
		System.out.println(getDriver().getCurrentUrl());
		if(!paymentMode.trim().equalsIgnoreCase("Full Payment")) {
			click(next_step_installments, "Next step");
		} else
			click(next_step, "Next step");
		sync(2500l);
	}

	public void fillSurveyForm() throws Exception {
//		((JavascriptExecutor) getDriver()).executeScript("$('.ui-dialog').remove()");
		removeDialogIfPresent();
		By textBox = By.cssSelector("input[type='text']");
		By radioButton = By.cssSelector("input[type='radio']");
		By checkBox = By.cssSelector("input[type='checkbox']");
		By dropDown = By.cssSelector("select[name*='question']");
		if(!getAttribute(textBox, "value").trim().equalsIgnoreCase("")) {
			click(saveButton, "Save");
		} else {
			List<WebElement> textBoxes =  getWebElementsList(textBox);
			for(int i = 0; i < textBoxes.size(); i++) {
				type(textBoxes.get(i), "Text box", "Test", true);
			}
			List<WebElement> radioButtons =  getWebElementsList(radioButton);
			for(int i = 0; i < radioButtons.size(); i++) {
				click(radioButtons.get(i), "Radio button");
			}
			List<WebElement> dropDowns =  getWebElementsList(dropDown);
			for(int i = 0; i < dropDowns.size(); i++) {
				Select _select = new Select(dropDowns.get(i));
				_select.selectByIndex(1);
			}
			List<WebElement> checkBoxs =  getWebElementsList(checkBox);
			for(int i = 0; i < checkBoxs.size(); i++) {
				click(checkBoxs.get(i), "Checkbox");
			}
			click(saveButton, "Save");
		}
	}
	
	public boolean isAlertPresent() {
		try {
			sync(8000l);
			getDriver().switchTo().alert();
			return true;
			
		}catch(NoAlertPresentException ex) {
			return false;
		}
	}
	
	private void removeDialogIfPresent() {
		checkIfElementPresent(dialogBox, 10);
		((JavascriptExecutor) getDriver()).executeScript("function removeOverlay() {			$('.ui-dialog').remove();			$('.ui-widget-overlay').remove();		}		setInterval(removeOverlay,500);");
		sync(1000l);
	}
}

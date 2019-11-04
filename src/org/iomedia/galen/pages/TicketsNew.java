package org.iomedia.galen.pages;

import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.iomedia.galen.common.ManageticketsAAPI;
import org.json.JSONObject;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.steps.TicketNewSteps;

import static org.testng.Assert.assertTrue;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver.HashMapNew;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.SkipException;

import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;

public class TicketsNew extends BaseUtil {

	private String driverType;
	ManageticketsAAPI aapi;

	public TicketsNew(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<HashMapNew> sTestDetails) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		driverType = driverFactory.getDriverType().get();
	}

	public By popUpSeatDetail = By.xpath(" .//section[contains(@class,'theme-body')]//div[contains(@class,'ticketDetails')] | //div[contains(@class,'transfer-selectRecipient')]//div[contains(@class,'styles-eventInfo')]//div[contains(@class,'styles-eventText')]/h4");
	private By recipientFirstName = By.xpath("//*[contains(@name,'first_name')]");
	private By recipientLastName = By.xpath("//*[contains(@name,'last_name')]");
	private By recipientEmail = By.xpath("//*[contains(@name,'email')]");
	private By recipientMessage = By.xpath("//*[@name='message'] | //*[@name='note']");
	private By sendButton = By.cssSelector("div[class*= formSubmitButtons] button:nth-child(2),div[class*= style-bulkTransferActionBar] button:nth-child(2) ,div[class*='transfer-actionButtons'] button:nth-child(2)");
	private By recipientEmailAddressDisplayed = By.xpath("//*[contains(@class,'sentInfo')]//strong");
	private By recipientEmailAddressDisplayedEDP = By.xpath("//*[contains(@class,'transfer-recipient')]//span");
	private By doneButton = By.xpath("//div[contains(@class,'formSubmitButton')]//button | //div[contains(@class,'transfer-doneBtn')]//button");
	private By sendExpire = By.xpath("//*[contains(@class,'sentInfo')]//u | (//div[contains(@class,'transfer-msg')]/span)[1]");
	private By errorDetailsClaim = By.xpath("//*[contains(@class,'errorDetails')]");
	private By errorDetailsClaimEDP = By.xpath("//div[contains(@class,'style-errorSection')]");
	
	private By dropDownFilter = By.xpath("//div[contains(@class,'dropdown')]//input");
	private By ticketCountText = By.xpath("//div[contains(@class,'totalTicketCounts')]//small");
	private By noofmobiletickets = By.xpath(".//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-slide')] | //div[contains(@class, 'ticket-eventListContainerMobile')]/section/div[contains(@class, 'ticket-event') and not(contains(@class, 'ticket-eventTicketPagger'))]");
	private By mobiletickets = By.xpath(".//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-slide') and @data-index>'-1' and contains(@class, 'slick-active')] | //div[contains(@class, 'ticket-eventListContainerMobile')]/section/div[contains(@class, 'ticket-event') and not(contains(@class, 'ticket-eventTicketPagger'))]");
	private String ticketDetail = "//*[contains(@class,'detailTicket') and contains(text(),'Ticket')]";
	private String ticketDetail2 = "//*[contains(@class,'completed')]//span[contains(text(),'TICKET')]";
	private By ticketPrice = By.xpath("//div[contains(@class, 'seat-list-ticketDetails')]//div[contains(text(),'Adult')]/..//span");
	private String ticketDetailBackHeader = "//h3[contains(@class,'detailsTitle')]";
	private String ticketDetailBarcodeNumber = "//*[contains(text(),'Bar')]/..//span";
	private By optionalMessageDecline = By.xpath("//textarea");
	private By agreeCheckBoxDecline = By.xpath("//input[@type='checkbox']");
	private By declineOffer = By.xpath("//a[contains(text(),'decline')]");
	
	private By allEventsBulkTransfer = By.xpath("//*[contains(@class,'eventInfo')]//h3");
	private By bulkTransferCheckbox = By.xpath("//*[contains(@class,'multiSelectIcon')]");
	private By sendTicket =By.cssSelector("div [class*='sendButton'] button , div[class*='transferButtons'] button:nth-child(2), button[class*='send_recipientContinue']");
	private By selectAllBulkTransfer = By.xpath("//*[contains(text(),'Select')]");
	private By allEventCheckboxBulkTransfer = By.xpath("//*[contains(@class,'eventCheckbox')]//div[contains(@class,'check')]");
	private By allSectionLables = By.xpath("//*[contains(@class,'sectionTitle')]//span[contains(@class,'titleInner')]");
	private By allTicketNames = By.xpath("//*[contains(@class,'sectionSeat')]//span[contains(@class,'titleInner')] | //*[contains(@class,'sectionSeat')]//span[contains(@class,'theme')]");
	private By acceptTransferTicketDashboard = By.xpath("//*[contains(@class,'pendingTransfersLabel')]/..//*[text()='Accept'] | //*[contains(@class,'styles-toastContainer-')]/*[contains(@class,'styles-buttonsBox-')]/button[not(contains(@class,'styles-seeDetails'))]");
	private By declineTransferTicketDashboard = By.xpath("//*[contains(@class,'pendingTransfersLabel')]/..//*[text()='Decline']");
	private By declineTransferPopUp = By.xpath("//*[contains(@class,'formSubmitButtons')]/..//*[text()='Decline']");
	private By allTransferTicketsAvailable = By.xpath("//label[@for!='identifier-1']/../..//div[contains(@class,'check')]");
	private By chooseRecipient = By.xpath("//button[contains(text(),'Choose')]");
	private By addNewRecipient = By.xpath("//div[contains(@class,'chooseRecipientSection')]//span");
	private By addRecFirstName = By.name("addNewRecipient.firstName");
	private By addRecSecondName = By.name("addNewRecipient.lastName");
	private By addRecEmailAddress = By.name("addNewRecipient.email");
	private By addRecNote = By.name("addNewRecipient.noteToRecipient");
	private By addRecSendTickets = By.xpath("//div[contains(@class,'formSubmitButtons')]//button[contains(text(),'Send Tickets')]");
	private By successTitle = By.xpath("//h3[contains(text(),'Success')]");
	private By successMessage = By.xpath("//*[contains(@class,'successMsg')] | //*[contains(@class,'claimMessage')]/span");
	private By recipientNameAdded = By.xpath("//h4[contains(@class,'Name')]");
	private By acceptInvitesPopup = By.cssSelector("[class*='manage-tickets-dialogContainer'] button:last-child");
	private By inviteEventName = By.cssSelector("[class*='manage-tickets-dialogContainer'] h4");
	private By invitesDetailsArrow = By.cssSelector("div [class*='style-iconAction']");
	private By invitesSection = By.cssSelector("[class*='manage-tickets-dialogContainer'] div[class*='style-eventDetails'] span:nth-child(2)");
	private By invitesRow = By.cssSelector("[class*='manage-tickets-dialogContainer'] div[class*='style-eventDetails'] span:nth-child(4)");
	private By invitesSeat = By.cssSelector("[class*='manage-tickets-dialogContainer'] div[class*='style-eventDetails'] span:nth-child(6)");
	//private By inviteEventName = By.cssSelector("[class*='manage-tickets-dialogContainer'] div[class*='detailsHolder'] span");
	private By invitesContinue = By.cssSelector("[class*='manage-tickets-dialogContainer'] button");
	private By invitesSeatDetails = By.cssSelector("[class*='manage-tickets-dialogContainer'] div[class*='style-eventDetails']");
	private By bulkmodel = By.xpath("//section[@role='body' and contains(@class, 'theme-body')]");
	private By eventnamemodel = By.cssSelector("div[class*=eventDetailsInner] h3");
	private By eventtimemodel = By.cssSelector("div[class*=eventDetailsInner] p");
	private By closemodel = By.cssSelector("span[role='button'] i[class*=closeButton]");
	private By cancelbutton = By.cssSelector("div[class*=bulkTransferActionBar] button:nth-child(1)");
	private By selectAllSend = By.cssSelector("label[class*='selectAll'] span");
	private By section1 = By.cssSelector("div[class*='eventSections'] div:nth-child(1) h3 span");
	private By section2 = By.cssSelector("div[class*='eventSections'] div:nth-child(2) h3 span");
	private By section3 = By.cssSelector("div[class*='eventSections'] div:nth-child(3) h3 span");
	private By nextbutton = By.cssSelector("div[class*=bulkTransferActionBar] button:nth-child(2)");
	private By fnamelabel = By.cssSelector("div[class*=addNewRecipientForm] div:nth-child(2) div[data-react-toolbox='input'] label[class*=theme-label]");
	private By lnamelabel = By.cssSelector("div[class*=addNewRecipientForm] div:nth-child(3) div[data-react-toolbox='input'] label[class*=theme-label]");
	private By emaillabel = By.cssSelector("div[class*=addNewRecipientForm] div:nth-child(4) div[data-react-toolbox='input'] label[class*=theme-label]");
	private By notelabel = By.cssSelector("div[class*=addNewRecipientForm] div:nth-child(5) div[data-react-toolbox='input'] label[class*=theme-label]");
	private By fnameinput = By.cssSelector("div[class*=addNewRecipientForm] div:nth-child(2) div[data-react-toolbox='input'] input");
	private By lnameinput = By.cssSelector("div[class*=addNewRecipientForm] div:nth-child(3) div[data-react-toolbox='input'] input");
	private By emailinput = By.cssSelector("div[class*=addNewRecipientForm] div:nth-child(4) div[data-react-toolbox='input'] input");
	private By noteinput = By.cssSelector("div[class*=addNewRecipientForm] div:nth-child(5) div[data-react-toolbox='input'] textarea");
	private By recipientmodel = By.cssSelector("div[data-react-toolbox='dialog'] section h6");
	private By transferCompleteModel = By.cssSelector("div[data-react-toolbox='dialog'] section h6");
	private By thankyouTitle = By.cssSelector("div[class*='style-thankYouTop'] div h3[class*=thankYouTitle]");
    private By inviteDetail = By.cssSelector("div[class*='style-thankYouTop'] div  p:nth-child(4)");
    private By addReceipient = By.cssSelector("div[class*='addNewRecipient'] span");
    private By closeReceipient = By.cssSelector("span[class*='addNewRecipientFormClose']");
	private By viewAll = By.cssSelector("a.viewAllBtn");
	private By bulkTransfer = By.cssSelector("div[class*=\"style-multiSelect\"] button");
	private By bulkCBs = By.xpath("//label[@data-react-toolbox='checkbox']/div");
	private By bulkCBsChecked = By.xpath("//label[@data-react-toolbox='checkbox']/div[contains(@class, 'checked')]");
	private By bulkCBsUnChecked = By.xpath("//label[@data-react-toolbox='checkbox']/div[not(contains(@class, 'checked'))]");
	private By event = By.cssSelector("div[class*='style-eventContent'] , div[class*='style-eventDetails']");
	private By sendbutton = By.cssSelector("div[class*='style-sendButton'] button");
	private By addedRecipient = By.cssSelector("div[class*='style-recipientList'] div[class*='style-recipient']:nth-child(2)");
	private By bulkTransferDoneButton = By.cssSelector("div[class*='style-bulkTransferActionBar'] button[class*='bulkContinue']");
	private By transferredTicketDetails = By.cssSelector("div[class*='style-sectionSeats'] label[data-react-toolbox='checkbox'] span");
	private By gotoEventButton = By.cssSelector(" div[class*='style-buttonBox'] button");
	private By sameSeatParkingDialogue = By.xpath("//div[@data-react-toolbox='dialog']/section/h6[contains(.,'Transfer - Select Tickets')]");
	private By viewAllButton = By.xpath("//div[contains(@class,'style-eventListContainer')]/button[contains(.,'View All')]");
	private By selectAll = By.cssSelector("label[class*='selectAll'] span");
	private By eventNameSelectTicket = By.cssSelector("div[class*='style-eventTile'] div[class*='style-eventDetails'] div[class*='style-eventDetailsInner'] h3");
	private By eventDateTimeSelectTicket = By.cssSelector("div[class*='style-eventTile'] div[class*='style-eventDetails'] div[class*='style-eventDetailsInner'] p");
	private By sameParkingYesButton = By.cssSelector("div[class*='bulkTransferActionBar'] button:nth-child(2)");
	private By plusiconTransfer = By.xpath("//div[contains(@class,'transfer-addNewText')]");
	private By addButtonTransfer = By.xpath("//button[contains(@class,'send_addNewRecipient')]");

	Utils utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);

	String firstName = "First";
	String lastName = "Last";
	String optionalMessage = "Test Automation";

	public String getPopUpSeatDetails() {

		return getText(popUpSeatDetail);
	}

	public String getSection() {
		return getPopUpSeatDetails().split("\\|")[0].trim();
	}

	public void enterRecipientDetails(String email) throws Exception {
		getDriver().manage().timeouts().implicitlyWait(Long.valueOf(10), TimeUnit.MILLISECONDS);
		if(utils.checkIfElementClickable(plusiconTransfer, 1)) {
			click(plusiconTransfer,"Plus Icon",1);
		}
		type(recipientFirstName, "First Name", firstName);
		type(recipientLastName, "Last Name", lastName);
		type(recipientEmail, "Email ", email);
		if(utils.checkIfElementClickable(addButtonTransfer, 1)) {
			click(addButtonTransfer,"Add Button ",1);
		}
		getDriver().manage().timeouts().implicitlyWait(Long.valueOf(Environment.get("implicitWait")), TimeUnit.MILLISECONDS);
		type(recipientMessage, "Optional", optionalMessage);
		Dictionary.put("OptionalMessage", optionalMessage);
	}

	private String getRecipientEmailAddressDisplayed() {
		return getText(recipientEmailAddressDisplayed);
	}
	
	private String getRecipientEmailAddressDisplayedEDP() {
		return getText(recipientEmailAddressDisplayedEDP);
	}

	private void getSendTicketExpire() {
		Dictionary.put("SendExpire", getText(sendExpire));
	}

	public void clickSend() {
		click(sendTicket, "Send");
	}

	public void clickDone() {
		click(doneButton, "Done");
	}

	public void verifyRecipientEmailAddressIsSameAsEntered(String email) {
		Assert.assertEquals(getRecipientEmailAddressDisplayed(), email, "Recipient address is same as enterend in previous screen");
		getSendTicketExpire();
	}
	
	public void verifyRecipientEmailAddressIsSameAsEnteredEDP(String email) {
		sync(1000l);  
		String str=getRecipientEmailAddressDisplayedEDP();
		String[] UIEmail = str.split(", ");
		System.out.println(UIEmail[1]);
		Assert.assertEquals(UIEmail[1], email, "Recipient address is same as enterend in previous screen");
		getSendTicketExpire();
	}

	public void verifyStatusAndExpiry(String status, String expiry) {
		Assert.assertTrue(status.contains(firstName), "First name is displayed in status message");
		Assert.assertTrue(status.contains(lastName), "Last name is displayed in status message");
		Assert.assertEquals(expiry, Dictionary.get("SendExpire"),
				"Expiry details are same on ticket as displayed in confirmation box");
	}
	
	public void verifyStatusAndExpiryEDP(String expiryMessage, String expiryDate) {
		Assert.assertTrue(expiryMessage.contains(firstName), "First name is displayed in status message");
		Assert.assertTrue(expiryMessage.contains(lastName), "Last name is displayed in status message");
		Assert.assertTrue(Dictionary.get("SendExpire").contains(expiryDate),
				"Expiry details are same on ticket as displayed in confirmation box");
	}

	public String getTicketExpiry(String eventId, String Section, String Row, String Seat, String ticketId,
			String xpath) {
		
		try {
			if ((driverType.trim().toUpperCase().contains("ANDROID")|| driverType.trim().toUpperCase().contains("IOS"))) {
				return getText(By.xpath(xpath + "//div[contains(@class, 'mobileStatus')]//p//span"), 1);
			} else
			return getText(By.xpath(xpath + "//div[contains(@class, 'ticket-ticketImage')]//div[contains(@class, 'status')][1]//span"),1);
		} catch (Exception ex) {
			return "No Status";
		}
	}
	
	public String getTicketExpiryEDP(String eventId, String Section, String Row, String Seat, String ticketId,
			String xpath) {
		
		try {
			if ((driverType.trim().toUpperCase().contains("ANDROID")|| driverType.trim().toUpperCase().contains("IOS"))) {
				return getText(By.xpath(xpath + "//div[contains(@class, 'mobileStatus')]//p//span"), 1);
			} else
			return getText(By.xpath(xpath + "/preceding-sibling::p"),1);
		} catch (Exception ex) {
			return "No Status";
		}
	}

	public void getTextOfSelectedParkingSlot(int numberselected) {

		Dictionary.put("ParkingSlot" + numberselected + "",
				getText(By.xpath("(//input[@value='true']/..//span)[" + numberselected + "]")));

	}

	public void verifyParkingSlot(String parkingSlot) {

		Assert.assertTrue(getElementWhenPresent(By.xpath("//*[text()='" + parkingSlot + "']")).isDisplayed(),
				"Parking slot is same as selected");

	}

	public void verifyErrorMessageClaim() {
		Assert.assertTrue(getElementWhenPresent(errorDetailsClaimEDP).isDisplayed(),
				"Error message displayed, unable to claim as expected");
	}

	public void enterBlankSpaceAndVerifySendButtonDisabled(String email) throws Exception {
		if (utils.checkElementPresent(plusiconTransfer, 2)) {
			click(plusiconTransfer,"Add A New Recipient",2);	
		}
		type(recipientFirstName, "First Name", "  ");
		type(recipientLastName, "Last Name", "  ");
		type(recipientEmail, "Email ", email);
		if(utils.checkElementPresent(recipientMessage, 2)) {
		type(recipientMessage, "Optional", optionalMessage);
		}
		Assert.assertEquals(getAttribute(sendButton, "disabled"), "true","verify Continue Button disabled before selecting payment option");
		
	}

	public void clickTickedDetailsAndValidatePriceOnUI(By ticketDetail, By purchasePrice, By iosAppLocator) {
		getElementWhenVisible(ticketDetail);
		click(ticketDetail, "Ticket Details");
		String priceOnUI = getText(purchasePrice);

		Double priceAPI = Double.parseDouble(Dictionary.get("Price")) / 100;
		String currencyApi = Dictionary.get("Currency");

		System.out.println(Double.parseDouble(priceOnUI.replaceAll("[^0-9.]", "")));
		System.out.println(priceAPI);

		System.out.println(priceOnUI.replaceAll("[0-9.]", ""));

		Assert.assertEquals(Double.parseDouble(priceOnUI.replaceAll("[^0-9.]", "")), priceAPI, "Price on UI is Price in API divided by 100");
		
		if(currencyApi.equalsIgnoreCase(Environment.get("currency"))) {
			Assert.assertEquals(priceOnUI.replaceAll("[0-9.]", "").trim(), currencyApi, "Currency on UI is same as in API");
		} else 
			//Assert.assertEquals(priceOnUI.replaceAll("[0-9.]", "").trim(), Environment.get("currency").replaceAll("\\s+",""), "Currency on UI is same as in API");
			Assert.assertEquals(priceOnUI.replaceAll("[0-9.]", "").trim(), currencyApi, "Currency on UI is same as in API");
	}

	public void validatePriceOnUI(By ticketDetail, By purchasePrice, By iosAppLocator) {
		getElementWhenVisible(ticketDetail);
		click(ticketDetail, "Ticket Details", null, iosAppLocator, "Ticket Dtails", false);
		String priceOnUI = getText(purchasePrice);

		Double priceAPI = Double.parseDouble(Dictionary.get("Price")) / 100;
		String currencyApi = Dictionary.get("Currency");

		System.out.println(Double.parseDouble(priceOnUI.replaceAll("[^0-9.]", "")));
		System.out.println(priceAPI);

		System.out.println(priceOnUI.replaceAll("[0-9.]", ""));

		Assert.assertEquals(Double.parseDouble(priceOnUI.replaceAll("[^0-9.]", "")), priceAPI,
				"Price on UI is Price in API divided by 100");
		Assert.assertEquals(priceOnUI.replaceAll("[0-9.]", "").trim(), currencyApi, "Currency on UI is same as in API");
	}

	public void clickMakePaymentAndValidateInvoicePage(By makePayment, By iosAppLocator) {

		getElementWhenVisible(makePayment);
		click(makePayment, "Ticket Details", null, iosAppLocator, "Ticket Dtails", false);

		if (((driverType.trim().toUpperCase().contains("ANDROID")
				|| driverType.trim().toUpperCase().contains("IOS")))) {
			sync(4000L);
		}

		Assert.assertTrue(getDriver().getCurrentUrl().contains("invoice"), "Invoice Page is displayed");

	}

	public void verifyFlipWindow(String x1, String x2) {
		String firstTicketBackHeader = x1 + "/.." + ticketDetailBackHeader;
		String secondTicketBackHeader = x2 + "/.." + ticketDetailBackHeader;

		try {
			click(By.xpath(x1 + ticketDetail), "First Ticket Detail", 5);
		} catch (Exception e) {
			click(By.xpath(x1 + ticketDetail2), "First Ticket detail", 5);
		}

		getElementWhenVisible(By.xpath(firstTicketBackHeader));
		Assert.assertTrue(checkIfElementPresent(By.xpath(firstTicketBackHeader)));

		try {
			click(By.xpath(x2 + ticketDetail), "Second Ticket Detail", 5);
		} catch (Exception e) {
			click(By.xpath(x2 + ticketDetail2), "Second Ticket Detail", 5);
		}

		getElementWhenVisible(By.xpath(secondTicketBackHeader));
		System.out.println(firstTicketBackHeader);

		try {
			WebElement we = getElementWhenClickable(By.xpath(firstTicketBackHeader));
			sync(200l);
			we.click();
			Assert.assertFalse(true, "Previous ticket not closed");
		} catch (Exception ex) {
			Assert.assertTrue(true, "Previous ticket closed");
		}

	}

	public void verifyBarcode(String x1, boolean check) {
		String firstTicketBarcodeNumber = x1 + "/.." + ticketDetailBarcodeNumber;
		try {
			click(By.xpath(x1 + ticketDetail), "First Ticket Detail");

		} catch (Exception e) {
			click(By.xpath(x1 + ticketDetail2), "First Ticket Detail");
		}

		if (check) {
			getElementWhenVisible(By.xpath(firstTicketBarcodeNumber));
			Assert.assertTrue(checkIfElementPresent(By.xpath(firstTicketBarcodeNumber)));
		} else {
			sync(4000L);
			Assert.assertFalse(checkIfElementPresent(By.xpath(firstTicketBarcodeNumber)));
		}
	}
	
	public void verifyBarcodeEDP(boolean check) {
		String barcode = "//*[text()='Barcode']/following-sibling::*/span";
		if(check) {
			getElementWhenVisible(By.xpath(barcode));
			Assert.assertTrue(checkIfElementPresent(By.xpath(barcode)));
		}else {
			Assert.assertFalse(checkIfElementPresent(By.xpath(barcode)));
		}
	}

	public boolean verifyDropDownFilter(int active, int pending, int completed) {

		if (active == 0 && pending == 0 && completed == 0) {
			assertTrue(true, "No tickets, skipping");
			return false;
		} else if (active == 0 && pending == 0 && completed != 0 || active == 0 && pending != 0 && completed == 0
				|| active != 0 && pending == 0 && completed == 0) {
			Assert.assertFalse(checkIfElementPresent(dropDownFilter),
					"Filter Dropdown not displayed when only active or completed or pending tickets are present in an event");
			return false;
		} else {
			getElementWhenClickable(dropDownFilter);
			Assert.assertTrue(checkIfElementPresent(dropDownFilter), "Filter Dropdown is displayed");
			return true;
		}
	}

	public void selectByValueUsingInput(String value) {
		sync(2000L);
		if(checkIfElementPresent(dropDownFilter)) {	
			click(dropDownFilter, "dropdown filter");
			click(getElementWhenClickable(By.xpath("//ul[contains(@class,'values')]//li[text()='" + value + "']")), value);
		}
	}

	public int getTicketCountText() {
		System.out.println(Integer.parseInt(getText(ticketCountText).replaceAll("[^0-9]", "").trim()));
		return Integer.parseInt(getText(ticketCountText).replaceAll("[^0-9]", "").trim());
	}

	public void clickEditCancelTicket(String eventId, String Section, String Row, String Seat, String ticketId, String xpath) {
		click(getElementWhenVisible(By.xpath(xpath + "//div[contains(@class,'completedStatus')]//span[1]"), 3), "manage posting");
	}
	
	public void clickEditCancelTicketEDP(String xpath) {
		click(getElementWhenVisible(By.xpath(xpath + "/following::button"), 3), "manage posting");
	}

	public String scrollToTicketEDP(String eventId, String Section, String Row, String Seat, String ticket_id) {
		Section = Section.replaceAll("%20", " ");
		Row = Row.replaceAll("%20", " ");
		By xpath;
		String sectionLabel = "";
		if (ticket_id != null) {
			sectionLabel = Dictionary.get("FORMATTED_SECTION_LABEL_" + ticket_id);
		} else {
			sectionLabel = "";
		}
		if (sectionLabel.trim().equalsIgnoreCase("[S]")) {
			xpath = By.xpath(".//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
		} else {
			String text1 ="Section "+ Section + ", "+"Row "+ Row + ", "+"Seat " +Seat;	
			xpath = By.xpath(".//div[contains(@class, 'react-root-event')]//div[contains(@class, 'seat-list-section')]//div[contains(@class, 'seat-list-seat')]");
			List<WebElement> tickets1 = getWebElementsList(xpath);
			
			for (int i=0;i<tickets1.size();i++) {
				
				if(tickets1.get(i).getText().contains(text1) && tickets1.get(i).getText().contains("Transfer Pending")) {
				i=i+1;
		
				
				WebElement e= getDriver().findElement(By.xpath(".//div[contains(@class, 'react-root-event')]//div[contains(@class, 'seat-list-section')]//div[contains(@class, 'seat-list-seat')]["+i+"]//*[contains(@class,'seat-list-messageTitle')]"));
				
			//	WebElement e= getDriver().findElement(By.xpath(xpath+"["+i+"]"+"//*[contains(@class,'seat-list-messageTitle')]//*[contains(@class,'seat-list-collapseIcon')]"));
				
				if(e.getText().contains("Transfer Pending")) {
						
					//xpath= By.xpath(xpath+"["+i+"]"+"//*[contains(@class,'seat-list-messageTitle')]//*[contains(@class,'seat-list-collapseIcon')]");
						
					xpath= By.xpath(".//div[contains(@class, 'react-root-event')]//div[contains(@class, 'seat-list-section')]//div[contains(@class, 'seat-list-seat')]["+i+"]");
			
				    e.click();
				}
			    xpath = By.xpath("(" +getXpath(xpath,"Chrome ticket", "", -1)+ ")" + "//div[contains(@class, 'seat-list-messageDetail')]//button");	
			    System.out.println("crrfefd   "+xpath);
			    break;				
			}			
		}
		}
		
		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			getElementWhenVisible(mobiletickets);
			List<WebElement> tickets = getWebElementsList(noofmobiletickets);
			int size = tickets.size() - 2;
			By activeMobileTicket = By.xpath(".//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-active') and @data-index>'-1' and @data-index<'" + size + "'] | //div[contains(@class, 'ticket-eventListContainerMobile')]/section/div[contains(@class, 'ticket-event') and not(contains(@class, 'ticket-eventTicketPagger'))]");
			if (sectionLabel.trim().equalsIgnoreCase("[S]")) {
				xpath = By.xpath("(" + getXpath(activeMobileTicket, "Mobile ticket", "", -1) + ")" + "//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
			} else
				xpath = By.xpath("(" + getXpath(activeMobileTicket, "Mobile ticket", "", -1) + ")" + "//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='" + Section + "'] and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/.. ");
			    
			int i = 0;
			Object[] coords = null;
			while (i < size && !checkIfElementPresent(xpath, 1)) {
				if (i < size - 1) {
					if (driverType.trim().toUpperCase().contains("ANDROID") && i == 0) {
						coords = getCoordinates(By.xpath(".//android.view.View[@resource-id='block-componentblockformanageticket']/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[not(@content-desc='') and @index='" + i + "'] | .//android.view.View[@resource-id='block-componentblockformanageticket']/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[@index='" + i + "']"));
					}
					
					swipe(By.xpath(".//div[contains(@class, 'react-root-event')]//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-slide') and @data-index='" + i + "']"), "Left", coords);
				}
				i++;
			}
		}
		getElementWhenVisible(xpath);
		return getXpath(xpath, "Ticket", "", -1);
	}
	
	
	public String scrollToTicket(String eventId, String Section, String Row, String Seat, String ticket_id) {
		Section = Section.replaceAll("%20", " ");
		Row = Row.replaceAll("%20", " ");
		By xpath;
		String sectionLabel = "";
		if (ticket_id != null) {
			sectionLabel = Dictionary.get("FORMATTED_SECTION_LABEL_" + ticket_id);
		} else {
			sectionLabel = "";
		}
		if (sectionLabel.trim().equalsIgnoreCase("[S]")) {
			xpath = By.xpath(
					".//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='"
							+ Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
		} else
			xpath = By.xpath(".//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='" + Section + "'] and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			getElementWhenVisible(mobiletickets);
			List<WebElement> tickets = getWebElementsList(noofmobiletickets);
			int size = tickets.size() - 2;
			By activeMobileTicket = By.xpath(".//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-active') and @data-index>'-1' and @data-index<'" + size + "'] | //div[contains(@class, 'ticket-eventListContainerMobile')]/section/div[contains(@class, 'ticket-event') and not(contains(@class, 'ticket-eventTicketPagger'))]");
			if (sectionLabel.trim().equalsIgnoreCase("[S]")) {
				xpath = By.xpath("(" + getXpath(activeMobileTicket, "Mobile ticket", "", -1) + ")" + "//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
			} else
				xpath = By.xpath("(" + getXpath(activeMobileTicket, "Mobile ticket", "", -1) + ")" + "//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='" + Section + "'] and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/.. ");
			    
			int i = 0;
			Object[] coords = null;
			while (i < size && !checkIfElementPresent(xpath, 1)) {
				if (i < size - 1) {
					if (driverType.trim().toUpperCase().contains("ANDROID") && i == 0) {
						coords = getCoordinates(By.xpath(".//android.view.View[@resource-id='block-componentblockformanageticket']/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[not(@content-desc='') and @index='" + i + "'] | .//android.view.View[@resource-id='block-componentblockformanageticket']/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[@index='" + i + "']"));
					}
					
					swipe(By.xpath(".//div[contains(@class, 'react-root-event')]//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-slide') and @data-index='" + i + "']"), "Left", coords);
				}
				i++;
			}
		}
		getElementWhenVisible(xpath);
		return getXpath(xpath, "Ticket", "", -1);
	}
	
	public String scrollToTicketAfterReload(String eventId, String Section, String Row, String Seat, String ticket_id) {
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
				&& Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			utils.navigateTo("/myevents");
			ManageTicket manageTicket = new ManageTicket(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
			Assert.assertTrue(manageTicket.isManageTicketsListDisplayed() , "Verify manage tickets list is displayed");
			utils.navigateTo("/myevents#/" + eventId);
		}
		Section = Section.replaceAll("%20", " ");
		Row = Row.replaceAll("%20", " ");
		By xpath;
		By xpath1;
		String sectionLabel = "";
		if (ticket_id != null) {
			sectionLabel = Dictionary.get("FORMATTED_SECTION_LABEL_" + ticket_id);
		} else {
			sectionLabel = "";
		}
		Object String;
		if (sectionLabel.trim().equalsIgnoreCase("[S]")) {
			//xpath = By.xpath(".//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='\" + Row + \"'] and descendant::strong[text()='\" + Seat + \"']]/..");
			xpath = By.xpath(".//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
		} else {
			//xpath = By.xpath(".//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[contains(text(),'" + Section + "')] and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
			xpath = By.xpath(".//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[contains(text(),'" + Section + "')] and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/.. ");
		}
		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			getElementWhenVisible(mobiletickets);
			List<WebElement> tickets = getWebElementsList(noofmobiletickets);
			int size = tickets.size() - 2;
			By activeMobileTicket = By.xpath(".//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-active') and @data-index>'-1' and @data-index<'" + size + "'] | //div[contains(@class, 'ticket-eventListContainerMobile')]/section/div[contains(@class, 'ticket-event') and not(contains(@class, 'ticket-eventTicketPagger'))]");
			if (sectionLabel.trim().equalsIgnoreCase("[S]")) {
				xpath = By.xpath("(" + getXpath(activeMobileTicket, "Mobile ticket", "", -1) + ")" + "//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
			} else
				xpath = By.xpath("("+getXpath(activeMobileTicket, "Mobile ticket", "", -1) + ")" + "//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[contains(text(),'" + Section + "')] and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
			
			int i = 0;
			Object[] coords = null;
			while (i < size && !checkIfElementPresent(xpath, 1)) {
				if (i < size - 1) {
					if (driverType.trim().toUpperCase().contains("ANDROID") && i == 0) {
						coords = getCoordinates(By.xpath(".//android.view.View[@resource-id='block-componentblockformanageticket']/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[not(@content-desc='') and @index='" + i + "'] | .//android.view.View[@resource-id='block-componentblockformanageticket']/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[@index='" + i + "']"));
					}
					swipe(By.xpath(".//div[contains(@class, 'react-root-event')]//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-slide') and @data-index='" + i + "']"), "Left", coords);
				}
				i++;
			}
		}
		//getElementWhenVisible(xpath);
		
		getElementWhenClickable(xpath,2);
		return getXpath(xpath, "Ticket", "", -1);
	}
	
	public String scrollToTicketAfterReloadEDP(String eventId, String Section, String Row, String Seat, String ticket_id) {
		/*if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
				&& Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			utils.navigateTo("/myevents");
			ManageTicket manageTicket = new ManageTicket(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
			Assert.assertTrue(manageTicket.isManageTicketsListDisplayed() , "Verify manage tickets list is displayed");
			utils.navigateTo("/myevents#/" + eventId);
		}*/
		Section = Section.replaceAll("%20", " ");
		Row = Row.replaceAll("%20", " ");
		By xpath;
		By xpath1;
		String sectionLabel = "";
		if (ticket_id != null) {
			sectionLabel = Dictionary.get("FORMATTED_SECTION_LABEL_" + ticket_id);
		} else {
			sectionLabel = "";
		}
		Object String;
		if (sectionLabel.trim().equalsIgnoreCase("[S]")) {
			xpath = By.xpath(".//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");			
		} else {	
			xpath = By.xpath(".//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[contains(text(),'" + Section + "')] and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/.. ");
		}
		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			getElementWhenVisible(mobiletickets);
			List<WebElement> tickets = getWebElementsList(noofmobiletickets);
			int size = tickets.size() - 2;
			By activeMobileTicket = By.xpath(".//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-active') and @data-index>'-1' and @data-index<'" + size + "'] | //div[contains(@class, 'ticket-eventListContainerMobile')]/section/div[contains(@class, 'ticket-event') and not(contains(@class, 'ticket-eventTicketPagger'))]");
			if (sectionLabel.trim().equalsIgnoreCase("[S]")) {
				xpath = By.xpath("(" + getXpath(activeMobileTicket, "Mobile ticket", "", -1) + ")" + "//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
			} else
				xpath = By.xpath("(" + getXpath(activeMobileTicket, "Mobile ticket", "", -1) + ")" + "//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[contains(text(),'" + Section + "')] and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
			
			int i = 0;
			Object[] coords = null;
			while (i < size && !checkIfElementPresent(xpath, 1)) {
				if (i < size - 1) {
					if (driverType.trim().toUpperCase().contains("ANDROID") && i == 0) {
						coords = getCoordinates(By.xpath(".//android.view.View[@resource-id='block-componentblockformanageticket']/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[not(@content-desc='') and @index='" + i + "'] | .//android.view.View[@resource-id='block-componentblockformanageticket']/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[@index='" + i + "']"));
					}
					swipe(By.xpath(".//div[contains(@class, 'react-root-event')]//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-slide') and @data-index='" + i + "']"), "Left", coords);
				}
				i++;
			}
		}
		//getElementWhenVisible(xpath);
		
		getElementWhenClickable(xpath,2);
		return getXpath(xpath, "Ticket", "", -1);
	}
	
	
	
	
	public void getTicketPricePathEDP(String eventId, String Section, String Row, String Seat, String ticket_id) {

		if(driverType.trim().toUpperCase().contains("IOS")) {
			By section = By.xpath("//div[contains(@class,'seat-list-sectionTitle-') and contains(.,'"+Section+"')]");
			if(utils.checkElementPresent(section, 10)) {
				click(section,"Click on Selected Section",10);
			}
			
			By ticket = By.xpath("//div[contains(@class,'seat-list-seatNumber-') and contains(.,'" + Section
					+ "') and contains(.,'" + Row + "') and contains(.,'" + Seat + "')]");
			if(utils.checkElementPresent(ticket, 10)) {
				click(ticket,"Click on Selected Section",10);
			}
		}

		else {
			Section = Section.replaceAll("%20", " ");
			Row = Row.replaceAll("%20", " ");
			By xpath1;
			String sectionLabel = "";
			if (ticket_id != null) {
				sectionLabel = Dictionary.get("FORMATTED_SECTION_LABEL_" + ticket_id);
			} else {
				sectionLabel = "";
			}

			if (sectionLabel.trim().equalsIgnoreCase("[S]")) {
				xpath1 = By.xpath(".//div[contains(@class, 'react-root-event')]//div[contains(@class, 'seat-list-sectionList')]//div[contains(@class, 'seat-list-sectionSeats')]");
			} else {
				String text1 ="";	
				xpath1 = By.xpath("//*[contains(@class,'seat-list-seat-') and (self::*//following-sibling::div[contains(.,'Sec "+Section+"') or contains(.,'Section "+Section+"')][contains(.,'Row "+Row+"')][contains(.,'Seat "+Seat+"')] or following-sibling::span[contains(.,'Sec "+Section+"') or contains(.,'Section "+Section+"')][contains(.,'Row "+Row+"')][contains(.,'Seat "+Seat+"')])]");			
				List<WebElement> tickets1 = getWebElementsList(xpath1);			
				for (int i=0;i<tickets1.size();i++) {	
					System.out.println(tickets1.get(i));
					System.out.println(tickets1.get(i).getText());
					if(tickets1.get(i).getText().contains(text1)) {
						WebElement e= tickets1.get(i).findElement(By.xpath("//*[contains(@class,'seat-list-detailsIcon')]"));
						e.click();	
						break;
					}			
				}			
			}
		}
	}

	
	public String getTicketPriceEDP() {
		return getText(ticketPrice);
	}
	
	public String getClaimByStatusofTicketEDP(String eventId, String Section, String Row, String Seat, String ticket_id) {
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))&& Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			By xpath2;
			String status1= "";
			String text2 ="Section "+ Section + ", "+"Row "+ Row + ", "+"Seat " +Seat;	
			xpath2 = By.xpath(".//div[contains(@class, 'react-root-event')]//div[contains(@class, 'seat-list-sectionSeats')]//div[contains(@class, 'seat-list-seat')]");

			List<WebElement> tickets2 = getWebElementsList(xpath2);

			for (int j=0;j<tickets2.size();j++) {			
				if(tickets2.get(j).getText().contains(text2)) {
					WebElement e= tickets2.get(j).findElement(By.xpath(".//div[contains(@class, 'seat-list-messageTitle')]"));
					status1 = e.getText();	
					System.out.println(status1);
					break;

				}
			}
		}
				
		String status= "";
		Section = Section.replaceAll("%20", " ");
		Row = Row.replaceAll("%20", " ");
		By xpath;
		By xpath1;
		String sectionLabel = "";
		if (ticket_id != null) {
			sectionLabel = Dictionary.get("FORMATTED_SECTION_LABEL_" + ticket_id);
		} else {
			sectionLabel = "";
		}
		
		if (sectionLabel.trim().equalsIgnoreCase("[S]")) {
			xpath1 = By.xpath(".//div[contains(@class, 'react-root-event')]//div[contains(@class, 'seat-list-sectionList')]//div[contains(@class, 'seat-list-sectionSeats')]");
		} else {
	    String text1 ="Section "+ Section + ", "+"Row "+ Row + ", "+"Seat " +Seat;	
			xpath1 = By.xpath(".//div[contains(@class, 'react-root-event')]//div[contains(@class, 'seat-list-sectionSeats')]//div[contains(@class, 'seat-list-seat')]");
			
			List<WebElement> tickets1 = getWebElementsList(xpath1);
			
			for (int i=0;i<tickets1.size();i++) {			
				if(tickets1.get(i).getText().contains(text1)) {
					WebElement element= tickets1.get(i).findElement(By.xpath(".//div[contains(@class, 'seat-list-messageDetail')]"));
					if(!element.getAttribute("class").contains("seat-list-display"))
						element.findElement(By.xpath("./preceding-sibling::div[contains(@class,'seat-list-messageTitle')]")).click();
					status = element.getText();			
					break;
				}			
			}			
		}
		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			getElementWhenVisible(mobiletickets);
			List<WebElement> tickets = getWebElementsList(noofmobiletickets);
			int size = tickets.size() - 2;
			By activeMobileTicket = By.xpath(".//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-active') and @data-index>'-1' and @data-index<'" + size + "'] | //div[contains(@class, 'ticket-eventListContainerMobile')]/section/div[contains(@class, 'ticket-event') and not(contains(@class, 'ticket-eventTicketPagger'))]");
			if (sectionLabel.trim().equalsIgnoreCase("[S]")) {
				xpath = By.xpath("(" + getXpath(activeMobileTicket, "Mobile ticket", "", -1) + ")" + "//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
			} else
				xpath = By.xpath("(" + getXpath(activeMobileTicket, "Mobile ticket", "", -1) + ")" + "//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[contains(text(),'" + Section + "')] and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
			
			int i = 0;
			Object[] coords = null;
			while (i < size && !checkIfElementPresent(xpath, 1)) {
				if (i < size - 1) {
					if (driverType.trim().toUpperCase().contains("ANDROID") && i == 0) {
						coords = getCoordinates(By.xpath(".//android.view.View[@resource-id='block-componentblockformanageticket']/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[not(@content-desc='') and @index='" + i + "'] | .//android.view.View[@resource-id='block-componentblockformanageticket']/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[@index='" + i + "']"));
					}
					swipe(By.xpath(".//div[contains(@class, 'react-root-event')]//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-slide') and @data-index='" + i + "']"), "Left", coords);
				}
				i++;
			}
		}
		//getElementWhenVisible(xpath);
		getElementWhenClickable(xpath1, 2);
		return status;
	}
	
	public String scrollToTicket(String eventId, String Section, String Row, String Seat, String ticket_id, String ticketState) {
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			utils.navigateTo("/tickets");
			ManageTicket manageTicket = new ManageTicket(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
			Assert.assertTrue(manageTicket.isManageTicketsListDisplayed() , "Verify manage tickets list is displayed");
			utils.navigateTo("/tickets#/" + eventId);
		}
		Section = Section.replaceAll("%20", " ");
		Row = Row.replaceAll("%20", " ");
		By xpath;
		String sectionLabel = "";
		if (ticket_id != null) {
			sectionLabel = Dictionary.get("FORMATTED_SECTION_LABEL_" + ticket_id);
		} else {
			sectionLabel = "";
		}
		if (sectionLabel.trim().equalsIgnoreCase("[S]")) {
			xpath = By.xpath(".//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
		} else
			xpath = By.xpath(".//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='" + Section + "'] and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			getElementWhenVisible(mobiletickets);
			if(ticketState != null && !ticketState.trim().equalsIgnoreCase("")) {
				try {
					TicketsNew ticketNew = new TicketsNew(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
					ticketNew.selectByValueUsingInput(ticketState);
				} catch (Exception e) {
					// no filter present
				}
			}
			List<WebElement> tickets = getWebElementsList(noofmobiletickets);
			int size = tickets.size() - 2;
			By activeMobileTicket = By.xpath(".//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-active') and @data-index>'-1' and @data-index<'" + size + "'] | //div[contains(@class, 'ticket-eventListContainerMobile')]/section/div[contains(@class, 'ticket-event') and not(contains(@class, 'ticket-eventTicketPagger'))]");
			if (sectionLabel.trim().equalsIgnoreCase("[S]")) {
				xpath = By.xpath("(" + getXpath(activeMobileTicket, "Mobile ticket", "", -1) + ")" + "//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
			} else
				xpath = By.xpath("(" + getXpath(activeMobileTicket, "Mobile ticket", "", -1) + ")" + "//div[contains(@class, 'ticket-ticketDetails') and descendant::strong[text()='" + Section + "'] and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..");
			
			int i = 0;
			Object[] coords = null;
			while (i < size && !checkIfElementPresent(xpath, 1)) {
				if (i < size - 1) {
					if (driverType.trim().toUpperCase().contains("ANDROID") && i == 0) {
						coords = getCoordinates(By.xpath(".//android.view.View[@resource-id='block-componentblockformanageticket']/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[not(@content-desc='') and @index='" + i + "'] | .//android.view.View[@resource-id='block-componentblockformanageticket']/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[@index='" + i + "']"));
					}
					swipe(By.xpath(".//div[contains(@class, 'react-root-event')]//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-slide') and @data-index='" + i + "']"), "Left", coords);
				}
				i++;
			}
		}
		getElementWhenVisible(xpath);
	 	return getXpath(xpath, "Ticket", "", -1);
	}
	
	

	public String getTicketStatus(String eventId, String Section, String Row, String Seat, String ticketId) {
		System.out.println("Extra Log :: "+Dictionary.get("SendExpire"));
		//String xpath = scrollToTicket(eventId, Section, Row, Seat, ticketId);
		try {
			if ((driverType.trim().toUpperCase().contains("ANDROID")|| driverType.trim().toUpperCase().contains("IOS"))) {
				//return getText(By.xpath(xpath + "//div[contains(@class, 'mobileStatus')]//p"), 1);
				return getText(By.xpath("//div[contains(@class,'seat-list-messageTitle')]"), 1);
			} else
				return getText(By.xpath("//div[contains(@class,'seat-list-messageTitle')] | //*[contains(@class,'seat-list-title-')]"), 1);
				//return getText(By.xpath(xpath+ "//div[contains(@class, 'ticket-ticketImage')]//div[contains(@class, 'ticket-listedOn')][1] | "+ xpath+ "//div[contains(@class, 'ticket-ticketImage')]//div[contains(@class, 'statusInner')]//p[1]"),1);
		} catch (Exception ex) {
			return "No Status";
		}
	}

	public void declinePageDisplayed() {
		Assert.assertTrue(checkIfElementPresent(getElementWhenVisible(optionalMessageDecline)));
	}

	public void declineOffer() throws Exception {
		type(optionalMessageDecline, "Optional Message", optionalMessage);
		click(agreeCheckBoxDecline, "Agree Terms of Use checkbox");
		click(declineOffer, "Decline Offer");
		By manageMoreTickets = By.id("manage-more-tickets");
		getElementWhenVisible(manageMoreTickets);
		Assert.assertTrue(getDriver().getCurrentUrl().contains("confirmation"));
	}
	
	public void clickBulkTransferValidateSendButtonAndSelectAllCbox()
	{
		if ((driverType.trim().toUpperCase().contains("ANDROID")|| driverType.trim().toUpperCase().contains("IOS"))) {
			click(By.xpath("//*[contains(@class,'multiSelectIcon')]//button"), "Bulk Transfer Checkbox");
		}
		else
		{
			WebElement bulkTransfer = getDriver().findElement(bulkTransferCheckbox);
			Actions builder = new Actions(getDriver());
			builder.click(bulkTransfer).build().perform();
		}	
		getElementWhenVisible(sendTicket);
		Assert.assertTrue(getElementWhenVisible(sendTicket).isDisplayed(), "Send Ticket Button is displayed");
		Assert.assertTrue(getElementWhenVisible(selectAllBulkTransfer).isDisplayed(),"Select All checkbox displayed");
	}
	
	public void selectEventsBulkTranfer(int cb)
	{
		List<WebElement> allEventCheckbox = getDriver().findElements(allEventCheckboxBulkTransfer);
		for (int i = 0; i < cb; i++) {
			click(allEventCheckbox.get(i), i+" Event Selected ");
		}
		
		click(getElementWhenClickable(sendTicket), "Send Ticket");
	}

	public void selectEventByName(String name) {
		getDriver().manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
		if (checkIfElementPresent(viewAllButton)) {
			click(viewAllButton, "VIEW ALL");
		}

		getDriver().manage().timeouts().implicitlyWait(Long.valueOf(Environment.get("implicitWait")), TimeUnit.MILLISECONDS);
		
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			click(By.xpath("//div[contains(@class,'style-eventName') and text()='" + name
					+ "'] | //div[contains(@class, 'style-eventName')]/span[contains(text(),'" + name + "')]"),
					"Event");
			// click(By.xpath("//div[contains(@class,
			// 'style-eventName')]/span[contains(text(),'" + name + "')]"), "Event Name");
		} else {
			click(By.xpath("//div[contains(@class,'style-eventName') and text()='" + name
					+ "'] | //div[contains(@class, 'style-eventName')]/span[contains(text(),'" + name + "')]"),
					"Event Name");
			// click(By.xpath("//div[contains(@class,
			// 'style-eventName')]/span[contains(text(),'" + name + "')]"), "Event Name");
		}
	}

	public void clickTransfer() {
		click(getElementWhenClickable(sendTicket), "Send Ticket");

	}

	public void clickTransferTwo() {
		click(getElementWhenClickable(sendTicket), "Send Ticket");
		if(checkIfElementPresent(sendButton,7)){
			click(sendButton,"SEND BUTTON");
		}
	}

	
	public String[] getAllEventsNameBulkTransfer()
	{
		List<WebElement> allEvents =getDriver().findElements(allEventsBulkTransfer);
		String[] allEventNames = new String[allEvents.size()];
		for (int i = 0; i < allEvents.size(); i++) {
			allEventNames[i] = allEvents.get(i).getText();
		}
		return allEventNames;
	}
	
	public String[] getAllSectionLabels()
	{
		//(//*[contains(@class,'sectionTitle')]//span[contains(@class,'titleInner')]/../..)[1]//*[contains(@class,'sectionSeat')]//span[contains(@class,'titleInner')] | (//*[contains(@class,'sectionTitle')]//span[contains(@class,'titleInner')]/../..)[1]//*[contains(@class,'sectionSeat')]//span[contains(@class,'theme')]
		List<WebElement> allSection =getDriver().findElements(allSectionLables);
		String[] allLables = new String[allSection.size()];
		for (int i = 0; i < allSection.size(); i++) {
			scrollingToElementofAPage(allSection.get(i));
			allLables[i] = allSection.get(i).getText();
		}
//		int count = allSection.size();
//		for (int i = 0; i < count; i++) {
//			scrollingToElementofAPage(allSection.get(i));
//			click(allSection.get(i), "Section");
//			getElementWhenVisible(allSectionLables);
//			 allSection =getDriver().findElements(allSectionLables);
//		}
		return allLables;
	}

	public void clickOnAcceptOrDecline(String key) {
		
		if(key.equalsIgnoreCase("Accept"))
			click(acceptTransferTicketDashboard, "Accept Ticket");
		else 
			{
				click(declineTransferTicketDashboard, "Decline Ticket");
				click(declineTransferPopUp, "Decline Confirm");
			}
	}

	public List<String> getTicketNamesAndVerifyTickets(List<String> api) {
		LinkedList<String> ticketNameList = new LinkedList<String>() ;
		//List<WebElement> ticketNames = getDriver().findElements(allTicketNames);
		List<WebElement> allSection =getDriver().findElements(allSectionLables);
		//(//*[contains(@class,'sectionTitle')]//span[contains(@class,'titleInner')]/../..)[1]//*[contains(@class,'sectionSeat')]//span[contains(@class,'titleInner')] | (//*[contains(@class,'sectionTitle')]//span[contains(@class,'titleInner')]/../..)[1]//*[contains(@class,'sectionSeat')]//span[contains(@class,'theme')]
		int count = 0;
		for(int j = 0; j< allSection.size();j++)
		{
			click(allSection.get(j), "Section");
			List<WebElement> ticketsInSection = getDriver().findElements(By.xpath("(//*[contains(@class,'sectionTitle')]//span[contains(@class,'titleInner')]/../..)["+(j+1)+"]//*[contains(@class,'sectionSeat')]//span[contains(@class,'titleInner')] | (//*[contains(@class,'sectionTitle')]//span[contains(@class,'titleInner')]/../..)["+(j+1)+"]//*[contains(@class,'sectionSeat')]//span[contains(@class,'theme')]"));
		for (int i = 0; i < ticketsInSection.size(); i++) {
			ticketNameList.add(ticketsInSection.get(i).getText());
			
			//works fine but need to be tested thoroughly, will test and uncomment later
			//comment below code if its failing for few users
//			if(Dictionary.get(i+"CanTransferFlag").equalsIgnoreCase("true"))
//			{
//				SoftAssert.assertTrue(checkIfElementPresent(By.xpath("(//label[@for!='identifier-1'])["+(i+1)+"]/../..//div[contains(@class,'check')]")));
//			}else SoftAssert.assertFalse(checkIfElementPresent(By.xpath("(//label[@for!='identifier-1'])["+(i+1)+"]/../..//div[contains(@class,'check')]")));
		//	SoftAssert.assertEquals(allSection.get(j).getText()+", "+ticketNameList.get(count), api.get(count));
			Assert.assertEquals(allSection.get(j).getText()+", "+ticketNameList.get(count), api.get(count));
			count++;
		}
		}
	//	SoftAssert.assertAll();
		return ticketNameList;
	}
	
	public void clickChooseRecipient()
	{
		
		click(chooseRecipient, "Choose Recipient");
	}
	
	public void addRecipientAndSend(String email) throws Exception
	{
		click(addNewRecipient, "Add New Recipient");
		type(addRecFirstName, "First Name", firstName);
		type(addRecSecondName, "Last Name", lastName);
		type(addRecEmailAddress, "Email ", email);
		type(addRecNote, "Optional", optionalMessage);
		Dictionary.put("OptionalMessage", optionalMessage);
		click(addRecSendTickets, "Send Ticket");
		Assert.assertTrue(getElementWhenVisible(successMessage).isDisplayed(),"Success Message displayed");
		Assert.assertTrue(getText(recipientNameAdded).contains(firstName));
		Assert.assertTrue(getText(recipientNameAdded).contains(lastName));
	}
	
	public void selectTicket(HashMap<String,String> tik) {
		List<String> ticketName = new ArrayList<>(tik.values());
		List<WebElement> ticketNames = getDriver().findElements(allTicketNames);
		for (int k = 0; k<ticketName.size(); k++)
		{
			for (int i = 0; i < ticketNames.size(); i++) {
				if(getDriver().findElements(allTicketNames).get(i).getText().equalsIgnoreCase(ticketName.get(k)))
				{
					click(getDriver().findElements(allTicketNames).get(i), "Clicked On Ticket");
					break;
				}
				
			}
		}
		
	}
	
	public void selectEventsWithTicketId(int cb)
	{
		List<WebElement> allEventCheckbox = getDriver().findElements(allEventCheckboxBulkTransfer);
		for (int i = 0; i < cb; i++) {
			click(allEventCheckbox.get(i), i+" Event Selected ");
		}
		
		click(getElementWhenClickable(sendTicket), "Send Ticket");
	}

	public void clickOnEvents(List<String> ticketIds) {
		List<WebElement> events = getDriver().findElements(By.xpath("//a[contains(@href,'#/')]"));
		int[] eventIdLocationNumber = new int[ticketIds.size()];
		for(int i = 0; i<ticketIds.size(); i++)
		{
			
			for(int k = 0; k< events.size(); k++)
			{
				if(events.get(k).getAttribute("href").contains(ticketIds.get(i).split("\\.")[0]))
				{
					eventIdLocationNumber[i] = k;
				}
			}
		}
		
		clickBulkTransferValidateSendButtonAndSelectAllCbox();
		
		List<WebElement> allEventCheckbox = getDriver().findElements(allEventCheckboxBulkTransfer);
		for (int i = 0; i < ticketIds.size(); i++) {
			click(allEventCheckbox.get(eventIdLocationNumber[i]), i+" Event Selected ");
		}
		
		click(getElementWhenClickable(sendTicket), "Send Ticket");
		
	}
	
	public void acceptTicketFromDashboard() {
		try
		{
			getElementWhenClickable(acceptTransferTicketDashboard);
		}catch(Exception e)
		{
			throw new SkipException("Invites to accept not found");
		}
		click(acceptTransferTicketDashboard, "Accept Button");
		Assert.assertTrue(getElementWhenVisible(successMessage).isDisplayed());
	}
	
	public void acceptInviteFromPopUp() {
		click(acceptInvitesPopup, "Accept Button");
		Assert.assertTrue(getElementWhenVisible(successTitle).getText().equals("Success!"));
		Assert.assertTrue(getElementWhenVisible(successMessage).getText().contains("You've accepted"));
		clickInvitesContinue();
		Object obj = ((JavascriptExecutor) getDriver()).executeScript("var obj = drupalSettings.componentConfigData.takeover.claim_takeover;return JSON.stringify(obj);");
		JSONObject json = new JSONObject(obj.toString());
		String enabled = json.get("enabled").toString();
		System.out.println("enabled  ::"+enabled);
		if(enabled.equals("1")){
			System.out.println("New enabled  ::"+enabled);
		clickInvitesContinue();
		}
	}
	
	public void verifyInviteEventAndSeatDetails(String eventName, String[] seatDetails){
		Assert.assertEquals(getEventName(), eventName);
		clickInviteDetailsArrow();
		for(String seatDetail: seatDetails){
			Assert.assertEquals(getInvitesSeatDetails(), seatDetail);
		}
	}
	
	public void clickInvitesContinue(){
		click(invitesContinue, "Continue");
	}
	
	public String getEventName(){
		System.out.println("Event Name:: "+getText(inviteEventName));
		return getText(inviteEventName);
	}
	
	public String getInviteTicketSection(){
		return getText(invitesSection);
	}
	
	public String getInviteTicketRow(){
		return getText(invitesRow);
	}
	
	public String getInviteTicketSeat(){
		return getText(invitesSeat);
	}
	
	public void clickInviteDetailsArrow(){
		click(invitesDetailsArrow, "Ticket Details Arrow");
	}
	
	public String getInvitesSeatDetails(){
		System.out.println("Seat Details::  "+getText(invitesSeatDetails));
		return getText(invitesSeatDetails);
	}
	
	public String getInvitesSeatId(){
		return (getInviteTicketSection().substring(8)+"."+getInviteTicketRow().substring(4)+"."+getInviteTicketSeat().substring(5));
	}public void clickViewAll()  {

		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			Hamburger hamburger = new Hamburger(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
			try {
				hamburger.clickMobileHamburger();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			hamburger.clickMyEvents();

		} else {
			click(viewAll, "VIEW ALL");
		}

	}

	public void verifyMSIcon() {
		checkIfElementPresent(bulkTransfer, 5);

	}

	public void clickMSIcon() {
		click(bulkTransfer,"Bulk CB");

	}

	public int verifyCBsCount() {
		return getWebElementsList(bulkCBs).size();
	}

	public int verifyEventCount() {
		return getWebElementsList(event).size();
	}


	public void verifyCBs() {
		Assert.assertEquals(verifyCBsCount()-1,verifyEventCount(), "There is a checkbox against every event for Bulk Transfer");
	}

	public void selectAllCB() {
		Assert.assertEquals(getAttribute(sendbutton, "disabled"), "true");
		click(bulkCBs,"Select All");
		Assert.assertEquals(getWebElementsList(bulkCBsChecked).size(), verifyCBsCount());
		Assert.assertEquals(getAttribute(sendbutton, "disabled"), null);

	}

	public void deselectAll() {
		click(bulkCBs,"Select All");
		Assert.assertEquals(getAttribute(sendbutton, "disabled"), "true");
		Assert.assertEquals(getWebElementsList(bulkCBsUnChecked).size(), verifyCBsCount());
	}




	public void verifyModel(String eventname, String ed, String et) {
		Assert.assertTrue(checkIfElementPresent(bulkmodel, 10));
		Assert.assertTrue(eventname.contains(getText(eventNameSelectTicket)));
		String seats = Dictionary.get("SeatsSectionTransfer");
		if (!seats.isEmpty()) {
			String section = Dictionary.get("SectionNameSectionTransfer");
			By sectionDD = By.xpath("//div[contains(@class,'style-eventDetails')]/div/h3[contains(.,'" + eventname
					+ "')]/ancestor::div[contains(@class,'style-eventContainer')]/div[contains(@class,'style-eventSections')]/div/h3/span[contains(.,'"
					+ section + "')]");
			if (utils.checkIfElementClickable(sectionDD, 10)) {
				click(sectionDD, "Section Drop Down", 5);
			}
			System.out.println(seats);
			By allTicketsLabel = By.xpath("//div[contains(@class,'style-eventDetails')]/div/h3[contains(.,'" + eventname
					+ "')]/ancestor::div[contains(@class,'style-eventContainer')]/div[contains(@class,'style-eventSections')]/div/h3/span[contains(.,'"
					+ section
					+ "')]/following::label[@data-react-toolbox='checkbox']/span[@data-react-toolbox='label' and not(contains(text(),'Select All'))]");
			List<WebElement> tickets = getDriver().findElements(allTicketsLabel);
			for (WebElement ticket : tickets) {
				String ticketDetails = ticket.getText().trim();
				if (!ticketDetails.isEmpty())
					if (seats.contains(ticketDetails)) {
						SoftAssert.assertTrue(true, "Reclaimed Ticket is visible for transfer :" + ticketDetails);
					} else {
						SoftAssert.assertTrue(false, "Reclaimed Ticket not visible for transfer:" + ticketDetails);
					}
			}
		}
		
//		String [] date = ed.split(" ");
		//Assert.assertTrue(getText(eventDateTimeSelectTicket).contains(date[0]),"Day does not match");
		//Assert.assertTrue(getText(eventDateTimeSelectTicket).contains(date[1]),"Month does not match");
		//Assert.assertTrue(getText(eventDateTimeSelectTicket).contains(date[2]),"Date does not match");
		//Assert.assertTrue(getText(eventDateTimeSelectTicket).contains(date[5]),"Year does not match");
	}

	public void verifyModelTwoEvents(String en, String ed, String et) {
		Assert.assertTrue(checkIfElementPresent(bulkmodel, 10));
		if(utils.checkIfElementClickable(sameParkingYesButton,5)) {
			click(sameParkingYesButton, "Transfer same seat(s) and/or parking ticket(s) for all selected events?");
		}
	}



	public String getftn() {
		return getText(eventnamemodel);
	}

	public String getftt() {
		return getText(eventtimemodel);
	}

	public void verifyXButton() {
		click(closemodel, "Close Model");
		Assert.assertFalse(checkIfElementPresent(bulkmodel));
	}

	public void verifyCancelButton() {
		click(cancelbutton, "CANCEL");
		Assert.assertFalse(checkIfElementPresent(bulkmodel));
	}

	/*public Map<String,String> selectAllSend1() {
		String text=null;
		String ticketCount="";
		List<String> seats=null;
		Map<String,String> seatDetails = new HashMap<String,String>();
		click(section1, "First Section");
		if (utils.getElementWhenVisible(selectAllSend)!=null) {
			text=getText(selectAllSend);
			seats=transferSeatDetails();
			click(selectAllSend, "Select All on Send Tickets Model");

		} else {
			click(section1, "First Section");
			click(section2, "Second Section");
			if (utils.getElementWhenVisible(selectAllSend)!=null) {
				text=getText(selectAllSend);
				seats=transferSeatDetails();
				click(selectAllSend, "Select All on Send Tickets Model");
			} else {
				click(section2, "Second Section");
				click(section3, "Third Section");
				if (utils.getElementWhenVisible(selectAllSend)!=null) {
					text=getText(selectAllSend);
					seats=transferSeatDetails();
					click(selectAllSend, "Select All on Send Tickets Model");
				}
			}
		}
		seatDetails.put("seats",String.join(";",seats));
		Pattern p=Pattern.compile("(\\d+)");
		Matcher m=p.matcher(text);
		while (m.find()) {
			ticketCount=m.group();
		}
		click(nextbutton,"Click Next Button");
		seatDetails.put("count",ticketCount);
		return seatDetails;
	}
*/
	public Map<String,String> selectAllSendSection(String eventname, String section, String num) {
		int flag =0;
		selectAll=By.xpath("//div[contains(@class,'style-eventDetails')]/div/h3[contains(.,'"+eventname+"')]/ancestor::div[contains(@class,'style-eventContainer')]/div[contains(@class,'style-eventSections')]/div[contains(@class,'eventSectionItem')]/div[contains(@class,'sectionData')]/label[contains(@class,'selectAll')]/span");
		By sectionDD= By.xpath("//div[contains(@class,'style-eventDetails')]/div/h3[contains(.,'"+eventname+"')]/ancestor::div[contains(@class,'style-eventContainer')]/div[contains(@class,'style-eventSections')]/div/h3/span[contains(.,'"+section+"')]");
		if(utils.checkIfElementClickable(sectionDD,10)){
			click(sectionDD,"Section Drop Down",5);
			selectAll = By.xpath("//div[contains(@class,'style-eventDetails')]/div/h3[contains(.,'"+eventname+"')]/ancestor::div[contains(@class,'style-eventContainer')]/div[contains(@class,'style-eventSections')]/div[contains(@class,'eventSectionItem')]/h3/span[contains(.,'"+section+"')]/ancestor::div[contains(@class,'eventSectionItem')]/div[contains(@class,'style-sectionData')]/label[contains(@class,'selectAll')]/span");
			flag=1;
		}

		List<String> seats=null;
		Map<String,String> seatDetails = new HashMap<String,String>();
		String text=getText(selectAll);
		seats=transferSeatDetails(section,eventname);
		Assert.assertEquals(Integer.parseInt(num),seats.size(),"Number of Transferable tickets: API data does not match with UI for event:"+eventname);
		click(selectAll, "Select All on Send Tickets Model");
		seatDetails.put("seats",String.join(";",seats));
		Pattern p=Pattern.compile("(\\d+)");
		Matcher m=p.matcher(text);
		String ticketCount = "";
		while (m.find()) {
			ticketCount=m.group();
		}
		seatDetails.put("count",ticketCount);
		if(flag==1) {
			click(By.xpath("//div[contains(@class,'style-eventDetails')]/div/h3[contains(.,'" + eventname + "')]/ancestor::div[contains(@class,'style-eventContainer')]/div[contains(@class,'style-eventSections')]/div/h3/span[contains(.,'" + section + "')]"), "Click Section");
		}
		return seatDetails;
	}

	public void selectNext() {
		click(nextbutton,"Click Next Button");
	}


	public void verifyLabels() {
		Assert.assertEquals(getText(fnamelabel),"First Name");
		Assert.assertEquals(getText(lnamelabel),"Last Name");
		Assert.assertEquals(getText(emaillabel),"Email");
		Assert.assertEquals(getText(notelabel),"Note to Recipient (optional)");
	}

	public void verifyRecipientModel() {
		Assert.assertTrue(getText(recipientmodel).equals("TRANSFER - SELECT A RECIPIENT"));

	}

	public void clickAddReceipient() {
		if (utils.getElementWhenVisible(addReceipient)!=null) {
			click(addReceipient, "Add Receipient");
		}
	}


	public void enterParticulars(String fname, String lname, String email, String notes) {
		if (utils.getElementWhenVisible(addReceipient)!=null) {
			click(addReceipient, "Add Receipient");
		}
		if ((driverType.trim().toUpperCase().contains("IOS"))) {
			try {
				type(fnameinput, "First Name", fname, true, By.xpath("(//XCUIElementTypeTextField)[1]"));
				type(lnameinput, "Last Name", lname, true, By.xpath("(//XCUIElementTypeTextField)[2]"));
				type(emailinput, "Email", email, true, By.xpath("(//XCUIElementTypeTextField)[3]"));
				type(noteinput, "NOTES", notes, true, By.xpath("(//XCUIElementTypeTextView)"));
				click(nextbutton, "TRANSFER");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			try {
				click(fnameinput, "FNAME");
				type(fnameinput, "FNAME", fname);
				click(lnameinput, "LNAME");
				type(lnameinput, "LNAME", lname);
				click(emailinput, "EMAIL");
				type(emailinput, "EMAIL", email);
				sendKeys(emailinput, Keys.TAB);
				click(noteinput, "NOTES");
				type(noteinput, "NOTES", notes);
				click(nextbutton, "TRANSFER");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void enterParticularsCancel(String fname, String lname, String email) {
		try {
			type(fnameinput,"FNAME", fname);
			type(lnameinput,"LNAME", lname);
			type(emailinput,"EMAIL", email);
			click(cancelbutton,"CANCEL");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean verifyMandatoryFields() {
		if (!getAttribute(sendButton, "disabled").equalsIgnoreCase("true")) {return false;}
		try {
			utils.clear(fnameinput);
			type(fnameinput, "FNAME", "first");
			if (!getAttribute(sendButton, "disabled").equalsIgnoreCase("true")) {return false;}
			utils.clear(lnameinput);
			type(lnameinput, "LNAME", "LAST");
			if (!getAttribute(sendButton, "disabled").equalsIgnoreCase("true")) {return false;}
			utils.clear(emailinput);
			type(emailinput, "EMAIL", "email@saa");
			if (!getAttribute(sendButton, "disabled").equalsIgnoreCase("true")) {return false;}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void verifyTransferComplete() {
		Assert.assertEquals(getText(transferCompleteModel), "TRANSFER - COMPLETE");
		Assert.assertTrue(getText(thankyouTitle).contains("Success"), "Tickets transferred");
		Assert.assertTrue(getText(inviteDetail).contains("If you’d like to cancel this transfer - you can do so, just as long as"));
		click(bulkTransferDoneButton,"Clicked Done on Bulk Transfer complete dialogue",10);

	}

	/*public void verifyTransferCompleteBackend(String tid) {
		try {
			 //SoftAssert.assertTrue(aapi.verifyTransferSuccessful(tid));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	public void verifyMyEventsPage(String name) {
	    Assert.assertTrue(checkIfElementPresent(By.xpath("//div[contains(@class, 'style-eventName') and text()='"+name+"'] | //div[contains(@class, 'style-eventName')]/span[contains(text(),'"+ name +"')] ")));
    }

	public void verifyCloseReceipient() {
		click(closeReceipient, "close Receipient Model");
		Assert.assertTrue(checkIfElementPresent(addReceipient));
	}

	public void verifyAddedReceipient() {
		SoftAssert.assertTrue(getText(addedRecipient).matches("\\w+\\s+\\w"));

	}

	public boolean verifySpecialChars( String sc) {
		if (!getAttribute(sendButton, "disabled").equalsIgnoreCase("true")) {return false;}
		try {
			type(emailinput, "EMAIL", "email@saa.com");
			type(fnameinput, "FNAME", "first"+sc);
			type(lnameinput, "LNAME", "LAST");
			if (!getAttribute(sendButton, "disabled").equalsIgnoreCase("true")) {return false;}
			type(fnameinput, "FNAME", "FIRST");
			type(lnameinput, "LNAME", "lname"+sc);
			if (!getAttribute(sendButton, "disabled").equalsIgnoreCase("true")) {return false;}
			type(lnameinput, "LNAME", "lname");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public List<String> transferSeatDetails(String section, String eventname) {
		List<String> seats = new ArrayList<>();
		//for ( WebElement we : getWebElementsList(By.xpath("//div[contains(@class,'style-sectionSeats')]/label[@data-react-toolbox='checkbox']/span[contains(.,'"+section+"')]"))){
		for ( WebElement we : getWebElementsList(By.xpath("//div[contains(@class,'style-eventDetails')]/div/h3[contains(.,'"+eventname+"')]/ancestor::div[contains(@class,'style-eventContainer')]/div[contains(@class,'style-eventSections')]/div[contains(@class,'eventSectionItem')]/div[contains(@class,'style-sectionData')]/div[contains(@class,'style-sectionSeats')]/label[@data-react-toolbox='checkbox']/span[contains(.,'"+section+"')]"))) {
			seats.add(getText(we));

		}
		return seats;
	}

	public void verifyEventsPage() {
		click(gotoEventButton,"Go to Event");
	}

}

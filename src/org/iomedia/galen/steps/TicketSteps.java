package org.iomedia.galen.steps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.iomedia.common.BaseUtil;
import org.iomedia.framework.OSValidator;
import org.iomedia.galen.common.AccessToken;
import org.iomedia.galen.common.ManageticketsAAPI;
import org.iomedia.galen.common.ManageticketsAPI;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.common.ManageticketsAPI.Event;
import org.iomedia.galen.pages.ManageTicket;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TicketSteps {
	
	ManageTicket ticket;
	org.iomedia.framework.Assert Assert;
	ManageticketsAPI api;
	BaseUtil base;
	String driverType;
	AccessToken accessToken;
	String transferName="TestAuto";
	private Utils utils;
	org.iomedia.framework.SoftAssert SoftAssert;
	org.iomedia.framework.Reporting Reporter;
	ManageticketsAAPI aapi;
	
	public TicketSteps(ManageTicket ticket, org.iomedia.framework.Assert Assert, ManageticketsAPI api, BaseUtil base, AccessToken accessToken, Utils utils, org.iomedia.framework.SoftAssert SoftAssert, org.iomedia.framework.Reporting Reporter,ManageticketsAAPI aapi) {
		this.ticket = ticket;
		this.Assert = Assert;
		this.api = api;
		this.base = base;
		this.driverType = base.driverFactory.getDriverType().get();
		this.accessToken = accessToken;
		this.utils=utils;
		this.SoftAssert = SoftAssert;
		this.Reporter = Reporter;
		this.aapi = aapi;
	}
	
	public TicketSteps() {}
	
	@When("^Events page is displayed$")
	public void events_page_is_displayed() {
	    
		Assert.assertTrue(ticket.isManageTicketsListDisplayed(), "Verify events list is displayed");
	}
	
	@Then("^Verify events summary for (.+) and (.+)$")
    public void verifyEventsSummary(String emailaddress, String password) throws Exception {
        emailaddress = (String) base.getGDValue(emailaddress);
        password = (String) base.getGDValue(password);
        HashMap<Integer, ManageticketsAPI.Event> events = api.getEventIdWithTktsDetails(emailaddress, password);
        Assert.assertNotNull(events);
        By eventsLocator = By.xpath(".//div[contains(@class, 'eventListContainer')]//a[contains(@class,'style-link')]");
        if(!base.checkIfElementPresent(eventsLocator, 1)) {
	        HashMap<Integer, String> levents = ticket.getListOfEvents();
	        Set<Integer> keys = levents.keySet();
	        Iterator<Integer> iter = keys.iterator();
	        while (iter.hasNext()) {
	            int eventId = iter.next();
	            Event _event = events.get(eventId);
	            String text = "";
	            if(_event != null) {
	                if (_event.getTicketsCount() > 1) {
	                    text = _event.getTicketsCount() + " Total Tickets";
	                } else {
	                    text = _event.getTicketsCount() + " Ticket";
	                }
	                Assert.assertEquals(levents.get(eventId), text, "Verify ticket count for event Id : " + eventId);
	            }
	        }
	        if(!base.Environment.get("event_id").trim().equalsIgnoreCase("")) {
	            String ticketCountValue = levents.get(Integer.valueOf(base.Environment.get("event_id").trim())).trim();
	            base.Dictionary.put("TICKETS_COUNT", ticketCountValue.split(" ")[0]);
	        }
        } else {
        	if(!base.Environment.get("event_id").trim().equalsIgnoreCase("")) {
        		int ticketCountValue = events.get(Integer.valueOf(base.Environment.get("event_id").trim())).getTicketsCount();
	            base.Dictionary.put("TICKETS_COUNT", String.valueOf(ticketCountValue));
	        }
        }
    }
	
	@When("^Tickets page is displayed$")
	public void tickets_page_is_displayed() {
		if(ticket.checkenableEDP()==true) {
			Assert.assertTrue(ticket.isTicketsListDisplayedEDP(null), "Verify tickets listing page is displayed");
		}else {
			Assert.assertTrue(ticket.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
		}
		String currenturl = base.driverFactory.getDriver().get().getCurrentUrl();
		String eventId = currenturl.trim().substring(currenturl.trim().lastIndexOf("/") + 1);
		base.Dictionary.put("EVENT_ID", eventId.trim());
	}
	
	@Then("^Verify tickets count based on status$")
	public void verify_tickets_count_based_on_status() throws Exception {
		int ticketsCount;
		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && base.Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			List<List<String>> actualsections = ticket.getMobileTicketsDetail();
			ticketsCount = actualsections.size();
		} else {
			ticketsCount = ticket.getTotalTicketsCount();
			int sentTicketsCount = ticket.getSentTicketsCount();
			int listedTicketsCount = ticket.getListedTicketsCount();
			int claimedTicketsCount = ticket.getClaimedTicketsCount();
			int donatedTicketsCount = ticket.getDonatedTicketsCount();
			Assert.assertEquals(ticket.getTotalTicketsCountText(), ticketsCount > 1 ? ticketsCount + " Tickets" : ticketsCount + " Tickets", "Verify total tickets count");
			if(sentTicketsCount > 0)
				Assert.assertEquals(ticket.getSentTicketsCountText(), sentTicketsCount + " Sent", "Verify sent tickets count");
			if(listedTicketsCount > 0)
				Assert.assertEquals(ticket.getListedTicketsCountText(), listedTicketsCount + " Listed", "Verify listed tickets count");
			if(claimedTicketsCount > 0)
				Assert.assertEquals(ticket.getClaimedTicketsCountText(), claimedTicketsCount + " Claimed", "Verify claimed tickets count");
			if(donatedTicketsCount > 0)
				Assert.assertEquals(ticket.getDonatedTicketsCountText(), donatedTicketsCount + " Donated", "Verify donated tickets count");
		}
		Assert.assertEquals(String.valueOf(ticketsCount), base.Dictionary.get("TICKETS_COUNT").trim(), "Verify total tickets count on tickets listing page");
	}
	
	@Then("^Verify event (.+) ticket - (.+) is displayed$")
	public void verify_ticket_is_displayed(String eventId, String ticket) {		
		
		if(this.ticket.checkenableEDP()==true){
			eventId = (String) base.getGDValue(eventId);
			ticket = (String) base.getGDValue(ticket);
			String[] val = ticket.trim().split("\n");
			if(val.length > 2) {
				String section = val[0].trim().substring(val[0].trim().lastIndexOf(" ") + 1);
				String row = val[1].trim().substring(val[1].trim().lastIndexOf(" ") + 1);
				String seat = val[2].trim().substring(val[2].trim().lastIndexOf(" ") + 1);
				//Assert.assertTrue(this.ticket.isTicketDisplayedEDP(eventId, section, row, seat, null), "Verify ticket is displayed - " + ticket);
			} else {
				Assert.assertTrue(this.ticket.isTicketsListDisplayedEDP(null), "Verify ticket is displayed - " + ticket);
			}

		}else {
		eventId = (String) base.getGDValue(eventId);
		ticket = (String) base.getGDValue(ticket);
		String[] val = ticket.trim().split("\n");
		if(val.length > 2) {
			String section = val[0].trim().substring(val[0].trim().lastIndexOf(" ") + 1);
			String row = val[1].trim().substring(val[1].trim().lastIndexOf(" ") + 1);
			String seat = val[2].trim().substring(val[2].trim().lastIndexOf(" ") + 1);
			Assert.assertTrue(this.ticket.isTicketDisplayed(eventId, section, row, seat, null), "Verify ticket is displayed - " + ticket);
		} else {
			Assert.assertTrue(this.ticket.isTicketsListDisplayed(null), "Verify ticket is displayed - " + ticket);
		}
		}	
	}
	
	@When("^User clicks on manage tickets using (.+)$")
	public void user_click_on_manage_tickets(String ticketId ) {
		ticketId = (String) base.getGDValue(ticketId);
		String[] tkt = ticketId.split("\\.");
	    ticket.clickManageTickets(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);
	}
	
	@When("^User clicks on Reclaim$")
	public void user_click_on_reclaim() {
		ticket.clickReclaim();
	}
	
	@Then("^Verify success screen$")
	public void verify_success_screen() {
		if(ticket.checkenableEDP()==true) {
			Assert.assertEquals(ticket.getSuccessEDP(), "You’re All Set");
		}else {
		Assert.assertEquals(ticket.getSuccess(), "Success!");
		}
	}
	
	@When("^User click on done$")
	public void user_click_on_done() {
		ticket.clickDone();
	}
	
	@Then("^Verify ticket status - (.+) for ticketId (.+)$")
	public void verify_reclaim_ticket_status(String status, String ticketId) throws Exception {
		ticketId = (String) base.getGDValue(ticketId);
		String[] tkt = ticketId.split("\\.");
		
		if (ticket.checkenableEDP()==true) {
			utils.sync(9000l);
			Assert.assertEquals(ticket.getTicketStatusEDP(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId),status);
		} else {
			Assert.assertEquals(ticket.getTicketStatus(tkt[0], tkt[1], tkt[2], tkt[3], ticketId), status);
		}
	}
	
	@When("^User clicks on Send tickets$")
	public void user_clicks_on_send() {
		if (ticket.checkenableEDP()==true) {
			ticket.clickSendTicketsEDP(null);
		}else {
			ticket.clickSendTickets(null);	
		}
	}
	
	@Given("^User clicked on event (.+)$")
	public void user_clicked_on_event(String eventname) throws Exception {
		eventname = (String) base.getGDValue(eventname);
		
		JSONObject eventJsonObject = aapi.getAllEventsForMember();
		JSONObject embedded = (JSONObject) eventJsonObject.get("_embedded");
		JSONArray events = embedded.getJSONArray("events");
		forLoop: for (Object eventObject : events) {
			JSONObject event = (JSONObject)eventObject;
			if(eventname.equals(event.get("archticsDescription"))){
				eventname = event.getString("name");
				break forLoop;
			}
		}
		ticket.clickEventName(eventname);
	}
	
	@When("^User selects the seat using (.+)$")
	public void user_select_seat(String ticketId) {
		ticketId = (String) base.getGDValue(ticketId);
		String[] tkt = ticketId.split("\\.");
		System.out.println(tkt);
		if (ticket.checkenableEDP()) {
			ticket.selectSeatInPopUpEDP(ticketId, tkt[1].replaceAll("%20", " "), tkt[2], tkt[3]);	
		}else {
			ticket.selectSeatInPopUp(ticketId, tkt[1].replaceAll("%20", " "), tkt[2], tkt[3]);	
		}
	} 
	
	@When("^User save the seatSectionRow using (.+)$")
	public void seatSectionRow(String ticketId) {
		ticketId = (String) base.getGDValue(ticketId);
		String[] tkt = ticketId.split("\\.");
		if (ticket.checkenableEDP()) {
			ticket.seatSectionRowVerificationEDP(ticketId, tkt[1].replaceAll("%20", " "), tkt[2], tkt[3]);	
		}else {
			ticket.seatSectionRowVerification(ticketId, tkt[1].replaceAll("%20", " "), tkt[2], tkt[3]);		
		}
	} 
	
	@When("^User clicks on continue$")
	public void user_click_continue() {
		if (ticket.checkenableEDP()==true) {
            ticket.clickContinueEDP();
		}else {
			ticket.clickContinue();
		}
	}
	
	@When("^User clicks donate charity confirm Button$")
	public void user_click_DonateCharityConfirmButton() {
		if (ticket.checkenableEDP()==true) {
            ticket.donatecharityConfirmButton();
		}else {
			ticket.clickContinue();
		}
	}
	
	@When("^User clicks on Donate Button$")
	public void user_click_Donate() {
		if (ticket.checkenableEDP()==true) {
            ticket.clickDonateEDP();
		}else {
			ticket.clickContinue();
		}
		
	}
	
	@When("^User clicks on Doante Ticktes continue$")
	public void donate_tickets_Continue() {
		if (ticket.checkenableEDP()==true) {
            ticket.clickDonateTicketsPageContiuneEDP();
		}else {
			ticket.clickContinue();
		}
	}
	
	
	@When("^User clicks on Transfer to continue$")
	public void user_click_transfer_continue() {
		if (ticket.checkenableEDP()==true) {
            ticket.clickTransferEDP();
		}else {
			ticket.clickContinue();
		}
	}
	
	@When("^User clicks on Print Button$")
	public void userclickprintButton() {
		if (ticket.checkenableEDP()==true) {
            ticket.clickPrintEDP();
		}else {
			ticket.clickContinue();
		}
	}
	
	@When ("^User clicks on Done Button$")
	public void userclickdoneButton() {
		if (ticket.checkenableEDP()==true) {
            ticket.clickDoneEDP();
		}else {
			ticket.clickContinue();
		}
	}
	
	@Then("^Verify Event Details using (.+)$")
	public void verify_event_details(String ticketId) {
		ticketId = (String) base.getGDValue(ticketId);
		String[] tkt = ticketId.split("\\.");
		if(ticket.checkenableEDP()==true) {
			SoftAssert.assertEquals(ticket.getPopUpEventDetailsEDP(), base.Dictionary.get("eventName"));
			SoftAssert.assertTrue(ticket.getSection().contains(tkt[1].replaceAll("%20", " ")));
		}else {
			SoftAssert.assertEquals(ticket.getPopUpEventDetails(), base.Dictionary.get("eventName"));
			SoftAssert.assertTrue(ticket.getSection().contains(tkt[1].replaceAll("%20", " ")));
		}		
	}
	
	@When("^User enters transfer Name$")
	public void user_enter_transfer_name() throws Exception {
		ticket.typeTransferTag(transferName);
	}
	
	@When("^User click on Transfer button$")
	public void user_click_on_transfer() {
		ticket.clickTransferDone();
	}
	
	@Then("^Verify ticket listing page display$")
	public void verify_ticket_listing_page_display() {
		utils.sync(9000L);
		if (ticket.checkenableEDP()) {
			Assert.assertTrue(ticket.isTicketsListDisplayedEDP(null), "Verify tickets listing page is displayed");
		} else {
			Assert.assertTrue(ticket.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
		}
	}
	
	@Then("^User click on cancel transfer link on event details page$" )
	public void clickCancelTransfer() {
		if(ticket.checkenableEDP()) {
			ticket.clickCancelTransferEDP();
		}else {
			ticket.clickCancelTransfer();
		}
	}
	
	@Then("^Save the state of ticket for (.+)$")
	public void save_state_of_ticket(String ticketId) throws Exception {
		ticketId = (String) base.getGDValue(ticketId);
		System.out.println(ticketId);
		if(ticket.checkenableEDP()) {
			String state = api.waitForTicketStateEDP(ticketId, "pending");
			base.Dictionary.put("State", state);	
		}else {
			String state = api.waitForTicketState(ticketId, "pending");
			base.Dictionary.put("State", state);
		}	
	}
	

	
	@Then("^Save State for ticketId (.+) using (.+) and (.+)$")
	public void save_state_for_ticketId(String ticketId, String emailaddress, String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		ticketId = (String) base.getGDValue(ticketId);
		String state = api.waitForTicketState(emailaddress,password,ticketId, "pending");
		base.Dictionary.put("State", state);
	}
	
	@When("^User Logout and login again using (.+) and (.+)$")
	public void user_logout_login(String emailaddress, String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		ticket.logoutNLogin(emailaddress, password);
	}
	
	@Then("^Save the Status of ticket using (.+)$")
	public void save_status_of_ticket(String ticketId) {
		ticketId = (String) base.getGDValue(ticketId);
		String[] tkt = ticketId.split("\\.");
		String status = ticket.getTicketStatus(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);
		base.Dictionary.put("Status", status);
	}
	
	@Then("^Verify the state of the ticket (.+)$")
	public void verify_state_of_ticket(String state) {
		state = (String) base.getGDValue(state);
		Assert.assertEquals(state, "pending");
	}
	
	@Then("^Verify the status of Send ticket (.+)$")
	public void verify_status_of_send_ticket(String status) {
		status = (String) base.getGDValue(status);
//		Assert.assertTrue(status.trim().contains(transferName));
	}
	
	@Then("^Verify the status of Sell ticket (.+)$")
	public void verify_status_of_sell_ticket(String status) {
		status = (String) base.getGDValue(status);
		SoftAssert.assertTrue(status.contains("Listed"));
	}
	
	
	@When("^User clicks on Sell tickets$")
	public void user_click_sell_ticket() {
		ticket.clickSellTickets(null);
	}
	
	@When("^User clicks on Print tickets$")
	public void user_click_print_ticket() {
		ticket.clickViewTickets();
	}
	
	@Then("^User enters Earning price$")
	public void user_enter_earning_price() throws Exception {
		ticket.typeEarningPrice(new String[]{base.Dictionary.get("EarningPrice")});
	}
	
	@Then("^User selects Seller Credit$")
	public void user_select_seller_credit() {
		ticket.selectSellerCredit();
	}	
	
	@Then("^Verify User edit Seller profile for (.+) and (.+)$")
	public void verify_user_edit_seller_profile(String email, String pass) throws Exception {
		email = (String) base.getGDValue(email);
		pass = (String) base.getGDValue(pass);
	
		if(ticket.isContinueEnabled()) {
			//Do Nothing
		} else {
			ticket.clickEditSellerProfile();
			ticket.inputSellerProfile(email, pass, base.Dictionary.get("FirstName"), base.Dictionary.get("LastName"), base.Dictionary.get("Add1"), base.Dictionary.get("Add2"), base.Dictionary.get("Country"), base.Dictionary.get("City"), base.Dictionary.get("State"), base.Dictionary.get("ZipCode"), base.Dictionary.get("MobileNum"), base.Dictionary.get("PhoneNum"));
			ticket.clickContinue();
			Assert.assertTrue(ticket.getSellerAddress(base.Dictionary.get("FirstName")).contains(base.Dictionary.get("FirstName")), "Seller profile");
			Assert.assertTrue(ticket.getSellerAddress(base.Dictionary.get("FirstName")).contains(base.Dictionary.get("LastName")));
			Assert.assertTrue(ticket.getSellerAddress(base.Dictionary.get("FirstName")).contains(base.Dictionary.get("Add1")));
			Assert.assertTrue(ticket.getSellerAddress(base.Dictionary.get("FirstName")).contains(base.Dictionary.get("Add2")));
			Assert.assertTrue(ticket.getSellerAddress(base.Dictionary.get("FirstName")).contains(base.Dictionary.get("City")));
			Assert.assertTrue(ticket.getSellerAddress(base.Dictionary.get("FirstName")).contains(base.Dictionary.get("ZipCode")));
		}
	}
	
	 @Then("^Verify The Event Name$")
	 public void verify_event_name() {
		 Assert.assertEquals(ticket.getPopUpEventDetails(), base.Dictionary.get("eventName"));
		 base.sync(2000L);
	 }
	 
	 @When("^User clicks on Edit or Cancel ticket for (.+)$")
	 public void user_click_edit_cancel_ticket(String ticketId) {
		 ticketId = (String) base.getGDValue(ticketId);
		 String[] tkt=ticketId.split("\\.");
		 ticket.clickEditCancelTicket(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);
	 }
	 
	 @Then("^User clicks on Edit Posting$")
	 public void user_click_edit_posting() {
		 ticket.clickEditPosting();
	 }
	 
	 @Then("^User select Bank Account$")
	 public void user_select_bank_account() {
		 ticket.selectBankAccount();
	 }
	 
	 @Then("^Verify Earning Price$")
	 public void verify_earning_price() {
		 Assert.assertEquals(ticket.getEarningPrice(), base.Dictionary.get("EarningPrice"));
	 }
	 
	 @Then("^User cancels posting$")
	 public void user_cancel_posting() {
		 ticket.clickCancelPosting();
	 }
	 
	 @When("^user click on confirm button$")
	 public void user_click_confirm() {
		 ticket.clickConfirm();
	 }
	 
	 @Then("^Verify pending action get removed for (.+) using (.+) and (.+)$")
	 public void verify_pending_action_get_removed(String ticketId,String emailaddress, String password) throws Exception  {
			emailaddress = (String) base.getGDValue(emailaddress);
			password = (String) base.getGDValue(password);
			ticketId = (String) base.getGDValue(ticketId);
			String state = api.waitForPendingActionRemoved(emailaddress,password,ticketId);
			Assert.assertNull(state, "Verify pending action got removed");
	 }
	 
	 @When("^Verify user clicks on Scan Barcode for (.+)$")
	 public void verify_user_click_Scan_barcode(String ticketId) {
		 ticketId = (String) base.getGDValue(ticketId);
		 String[] tkt = ticketId.split("\\.");
		
		 if(ticket.checkenableEDP()==true) {
			 ticket.clickScanBarcodeEDP(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);
		 }else {
			 ticket.clickScanBarcode(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);
		 }
		// Assert.assertEquals(ticket.getMobileScanEventDetails(), base.Dictionary.get("eventName"));
		 Assert.assertEquals(ticket.getMobileScanSectionName(), tkt[1].replaceAll("%20", " "));
		 Assert.assertEquals(ticket.getMobileScanRowName(),tkt[2]);
		 Assert.assertEquals(ticket.getMobileScanSeatName(), tkt[3]);
		 //SoftAssert.assertEquals(ticket.getMobileScanGateNumber(), "Enter Gate: " + base.Dictionary.get("entryGate").trim());
	 }
	 
	 @When("^Clicks on Scan Barcode for (.+) and (.+)$")
	 public void verify_user_click_Scan_barcode(String ticketIds, String index) {
		 ticketIds = (String) base.getGDValue(ticketIds);
		 index = (String) base.getGDValue(index);
		 String ticketId = ticketIds.trim().split(",")[Integer.valueOf(index.trim())];
		 String[] tkt=ticketId.split("\\.");
		 if(ticket.checkenableEDP()==true) {
			 ticket.clickScanBarcodeEDP(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);
		 }else {
			 ticket.clickScanBarcode(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);
		 }
		 //		 Assert.assertEquals(ticket.getMobileScanEventDetails(), base.Dictionary.get("eventName"));
		 Assert.assertEquals(ticket.getMobileScanSectionName(), tkt[1].replaceAll("%20", " "));
		 Assert.assertEquals(ticket.getMobileScanRowName(),tkt[2]);
		 Assert.assertEquals(ticket.getMobileScanSeatName(), tkt[3]);
		 //		 Assert.assertEquals(ticket.getMobileScanGateNumber(), "Enter Gate: " + base.Dictionary.get("entryGate").trim());
		 
	 }
	 
	 @Then("^Verify Barcode gets display$")
	 public void verify_barcode_display() {
		 Assert.assertTrue(ticket.isBarcodeDisplayed(), "Verify bar code is displayed");
	 }
	 
	 @Then("^Barcode gets displayed for (.+) and (.+)$")
	 public void verify_barcode_display_for(String ticketIds, String index) {
		 ticketIds = (String) base.getGDValue(ticketIds);
		 index = (String) base.getGDValue(index);
		 String ticketId = ticketIds.trim().split(",")[Integer.valueOf(index.trim())];
		 String[] tkt=ticketId.split("\\.");
		 Assert.assertTrue(ticket.isBarcodeDisplayed(tkt[1].replaceAll("%20", " "), tkt[2], tkt[3]), "Verify bar code is displayed");
	 }
	 
	 @Then("^Secure Barcode gets displayed for (.+) and (.+)$")
	 public void verify_secure_barcode_display_for(String ticketIds, String index) {
		 ticketIds = (String) base.getGDValue(ticketIds);
		 index = (String) base.getGDValue(index);
		 String ticketId = ticketIds.trim().split(",")[Integer.valueOf(index.trim())];
		 String[] tkt=ticketId.split("\\.");
		 Assert.assertTrue(ticket.isSecureBarcodeDisplayed(tkt[1].replaceAll("%20", " "), tkt[2], tkt[3]), "Verify bar code is displayed");
	 }
	 
	 @Then ("User Accept the Education Information POP Up if present")
	 public void acceptEducationPopUp() {
		 ticket.accepctEducationPopUp(); 
	 }
	 
	 @Given("^User saves Barcode Image URL for (.+) and (.+)$")
	 public void user_save_barcode_image_url(String ticketIds, String index) {
		 ticketIds = (String) base.getGDValue(ticketIds);
		 index = (String) base.getGDValue(index);
		 String ticketId = ticketIds.trim().split(",")[Integer.valueOf(index.trim())];
		 String[] tkt=ticketId.split("\\.");
		 String URL = ticket.getBarcodeImageUrl(tkt[1].replaceAll("%20", " "), tkt[2], tkt[3]);
		 base.Dictionary.put("BarcodeURL", URL);
	 }
	 
	 @Given("^User saves Secure Barcode frontend attribute for (.+) and (.+)$")
	 public void user_save_barcode_attribute(String ticketIds, String index) {
		 ticketIds = (String) base.getGDValue(ticketIds);
		 index = (String) base.getGDValue(index);
		 String ticketId = ticketIds.trim().split(",")[Integer.valueOf(index.trim())];
		 String[] tkt=ticketId.split("\\.");
		 utils.sync(9000L);
		 String attribute = ticket.secureBarcodeAttribute(tkt[1].replaceAll("%20", " "), tkt[2], tkt[3]);
		 base.Dictionary.put("SecureBarcodeAttribute", attribute);
	 }
	 
	 
	 @Given("^Save Destination Folder$")
	 public void save_destination_folder() {
		 String destinationFile = System.getProperty("user.dir") + OSValidator.delimiter + "Tickets";
		 if(!new File(destinationFile).exists()) {
			  new File(destinationFile).mkdirs();
		  }
		 base.Dictionary.put("ImagePath", destinationFile);
	 }
	 
	 @When("^Copy image (.+) into destination Folder (.+)$")
	 public void save_barcode_image_into_destination_folder(String BarcodeURL, String ImageFolder) throws Exception {
		String clientName = "";
		BarcodeURL = (String) base.getGDValue(BarcodeURL);
		ImageFolder = (String) base.getGDValue(ImageFolder);
		 
		if(!base.Environment.get("APP_URL").trim().equalsIgnoreCase("")) {
			String appurl = base.Environment.get("APP_URL").trim();
			if(appurl.trim().endsWith("/"))
				appurl = appurl.trim().substring(0, appurl.trim().length() - 1);
			String clientId = appurl.substring(appurl.lastIndexOf("/") + 1).trim().toUpperCase();
			clientName = clientId;
		} else {
			clientName = base.Environment.get("x-client").trim();
		}
		 
		//******************* Fetch Current TimeStamp ************************
		java.util.Date today = new java.util.Date();
		Timestamp now = new java.sql.Timestamp(today.getTime());
		String tempNow[] = now.toString().split("\\.");
		final String sStartTime = tempNow[0].replaceAll(":", "").replaceAll(" ", "").replaceAll("-", "");
	 
		String ImagePath = ticket.getBarcodeImage(ImageFolder, clientName, sStartTime);
		
		base.Dictionary.put("ImageFilePath", ImagePath);
 	}
	 
	 @Given("^User is enabled on mobile$")
	 public void user_enable_on_mobile() {
		 if(!utils.getManageTicketConfiguration("mobile_enabled"))
			 throw new SkipException("Mobile_Enabled is not enabled in CMS");
		 if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS"))
			 throw new SkipException("Skipped");
		 if(base.Environment.get("deviceType").trim().equalsIgnoreCase("tablet")) {
			 throw new SkipException("Skipped");
		 }
	 }
	 
	 @When("^User saves claimLink$")
	 public void save_claimLink(){
		String claimLink = ticket.getClaimLink();
		base.Dictionary.put("ClaimLink", claimLink);
	 }
	 
	 @When("^User clicks on Donate$")
	 public void click_donate(){
		 if(ticket.checkenableEDP()==true) {
			 ticket.clickDonateTicketsEDP(null);
		 }else {
			 ticket.clickDonateTickets(null);
		 }
	 }
	 
	 @When("^User selects charity$")
	 public void select_charity(){
		ticket.selectCharity();
	 }
	 
	 @Then("^User deletes transfer using (.+) and (.+) for ClaimLink (.+)$")
	 public void user_delete_transfer(String emailaddress,String password, String claimLink) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		claimLink = (String) base.getGDValue(claimLink);
		String transferId = claimLink.trim().split("\\?")[1];
		if(!transferId.trim().equalsIgnoreCase(""))
			api.deleteTransfer(transferId);
	 }
	 
	 @When("^User gets Posting ID using (.+) and (.+) for (.+)$")
	 public void user_get_posting_id(String emailaddress, String password, String ticketId) throws Exception {
	    emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		ticketId = (String) base.getGDValue(ticketId);
		Integer[] postings = api.getPostingId(emailaddress,password,new String[]{ticketId});
		if(postings.length > 0) {
			String postingId = postings[0].toString();
			base.Dictionary.put("PostingId", postingId);
		}
	 }
	 
	 @Then("^User deletes postingId using (.+) and (.+) for (.+)$")
	 public void user_delete_posting_id(String emailaddress, String password,String postingId) throws Exception {
		 emailaddress = (String) base.getGDValue(emailaddress);
		 password = (String) base.getGDValue(password);
		 postingId = (String) base.getGDValue(postingId);
		 if(!postingId.trim().equalsIgnoreCase(""))
			 api.deletePosting(emailaddress,password,Integer.parseInt(postingId));
	 }
	 
	 @Then("^Verify User edit Bank Account profile for (.+) and (.+)$")
	 public void user_edit_bank_account(String emailaddress,String password) throws Exception {
		 emailaddress = (String) base.getGDValue(emailaddress);
		 password = (String) base.getGDValue(password);
			
		 if(ticket.getDepositAccountValue().trim().equalsIgnoreCase("Check by mail")) {
			 ticket.clickEditSellerProfile();
			 ticket.inputSellerProfile(emailaddress, password, base.Dictionary.get("FirstName"), base.Dictionary.get("LastName"), base.Dictionary.get("Add1"), base.Dictionary.get("Add2"), base.Dictionary.get("Country"), base.Dictionary.get("City"), base.Dictionary.get("State"), base.Dictionary.get("ZipCode"), base.Dictionary.get("MobileNum"), base.Dictionary.get("PhoneNum"));
			 ticket.clickContinue();
			 Assert.assertTrue(ticket.getSellerAddress(base.Dictionary.get("FirstName")).contains(base.Dictionary.get("FirstName")), "Seller profile");
			 Assert.assertTrue(ticket.getSellerAddress(base.Dictionary.get("FirstName")).contains(base.Dictionary.get("LastName")));
			 Assert.assertTrue(ticket.getSellerAddress(base.Dictionary.get("FirstName")).contains(base.Dictionary.get("Add1")));
			 Assert.assertTrue(ticket.getSellerAddress(base.Dictionary.get("FirstName")).contains(base.Dictionary.get("Add2")));
			 Assert.assertTrue(ticket.getSellerAddress(base.Dictionary.get("FirstName")).contains(base.Dictionary.get("City")));
			 Assert.assertTrue(ticket.getSellerAddress(base.Dictionary.get("FirstName")).contains(base.Dictionary.get("ZipCode")));
		 } else {
			 ticket.clickEditBankDetail();
			 ticket.inputBankDetails(base.Dictionary.get("AccType"), base.Dictionary.get("RoutingNum"), base.Dictionary.get("AccountNum"), base.Dictionary.get("ConfirmAcc"));
			 ticket.clickContinue();
			 SoftAssert.assertEquals(ticket.getBankDetails(base.Dictionary.get("AccountNum").trim().substring(base.Dictionary.get("AccountNum").trim().length() - 4)), utils.toCamelCase(base.Dictionary.get("AccType").trim()) + " account ending in " + base.Dictionary.get("AccountNum").trim().substring(base.Dictionary.get("AccountNum").trim().length() - 4), "Verify bank details got updated");
		 }
	 }
	 
	 @Then("^User click on Claim link for ticketId (.+) and (.+) using (.+) and (.+)$")
	 public void user_click_on_claim_link(String ticketId, String transferId, String emailaddress, String password) throws Exception {
		 emailaddress = (String) base.getGDValue(emailaddress);
		 password = (String) base.getGDValue(password);
		 ticketId = (String) base.getGDValue(ticketId);
		 transferId = (String) base.getGDValue(transferId);
		 String state = null;
		 try{
			 if(ticket.checkenableEDP()) {
				 ticket.clickClaimEDP(); 
				 state = api.getTicketStateEDP(ticketId);
				 System.out.println(state);
				 Assert.assertEquals(state, "accepted");
			 }else {
			     ticket.clickClaim();
			     state = api.waitForTicketState(emailaddress, password, ticketId, "accepted");
			     Assert.assertEquals(state, "accepted");
			 }
			 
		 } catch(Exception ex) {
			 api.deleteTransfer(emailaddress, password,transferId);
			 throw ex;
		 }
	 }
	 
	 @Then("^Verify User claimed the ticket using (.+) and (.+)$")
	 public void verify_user_claim_ticket(String ticketId, String customer_name) throws Exception {
		customer_name = (String) base.getGDValue(customer_name);
		ticketId = (String) base.getGDValue(ticketId);
		if (ticket.checkenableEDP()) {
			SoftAssert.assertEquals("Claimed By " + aapi.getTicketClaimByEmailAddressEDP(ticketId),
					"Claimed By " + customer_name, "Verify claimed text on tickets");
		} else {
			SoftAssert.assertEquals("Claimed By " +
					api.getTicketClaimByEmailAddress(ticketId),
					"Claimed By " + customer_name, "Verify claimed text on tickets");
		}
	}
	 
	 @Then("^Verify printTicket or Render Barcode for email (.+) pass (.+) and ticketID (.+)$")
	 public void verify_print_download_ticket(String email, String pass, String ticketId) throws Exception{
		ticketId= (String) base.getGDValue(ticketId);
		email= (String) base.getGDValue(email);
		pass = (String) base.getGDValue(pass);
		
		if(base.Environment.get("deviceType").trim().equalsIgnoreCase("tablet")) {
			throw new SkipException("Skipped");
		}
		else if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && base.Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			ticket.verifyRenderBarcode(email, pass, ticketId);
		}
		else {
			ticket.verifyPrintTicket(email, pass, ticketId);
		}
	 }

	 @When("^Swipe (.+) with index (.+)$")
	 public void swipe_with_index(String direction, String index) {
		 direction = (String) base.getGDValue(direction);
		 index = (String) base.getGDValue(index);
		 ticket.swipe(direction, String.valueOf(Integer.valueOf(index) - 1));
		 base.Dictionary.put("RenderBarcodeID_Count", String.valueOf(Integer.valueOf(index) - 1));
	 }
	 
	 @Then("^User click on Print Button$")
	 public void user_clicks_print_button() {
		 if (ticket.checkenableEDP()==true) {
			 ticket.clickDownloadTicketsEDP();
		 }else {
			 ticket.clickDownloadTickets();
		 }
	 }
	 
	 
	 @Given("^User get barcode for (.+)$")
	 public void get_barcode(String ticketId) throws Exception {
	    ticketId = (String) base.getGDValue(ticketId);
		String[] details = api.renderTicketDetails(ticketId);
		System.out.println("Old Barcode: "+details[1]);
		base.Dictionary.put("APIBarcodeID",details[1]);		
	 }
	 
	 @Then("^User get new barcode for (.+) and (.+) and (.+)$")
	 public void get_new_barcode(String emailaddress,String password,String ticketId) throws Exception {
	    emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
	    ticketId = (String) base.getGDValue(ticketId);
		String[] details = api.renderTicketDetails(emailaddress, password, ticketId);
		System.out.println("New Barcode: "+details[1]);
		base.Dictionary.put("New_APIBarcodeID",details[1]);	
	 }
	 
	 @Then("^User click on Lost ticket$")
	 public void click_lost_ticket() {
		 ticket.clickLostTickets();
	 }
	 
	 @Then("^User download the ticket for (.+)$")
	 public void user_download_ticket(String ticketId) throws Exception {
		 ticketId = (String) base.getGDValue(ticketId);
		 api.renderFilefromTMSide(new String[]{ticketId});	
	 }
	 
	 @Then("^User verify the barcode from TicketPdf from (.+) for (.+) and (.+)$")
	 public void read_barcode_from_ticket_pdf(String TicketPath, String newBarcodeID, String BarcodeID) throws Exception {
		 TicketPath = (String) base.getGDValue(TicketPath);
		 newBarcodeID = (String) base.getGDValue(newBarcodeID);
		 BarcodeID = (String) base.getGDValue(BarcodeID);

		 String newBarcode = api.BarcodeSeparator(newBarcodeID);
		 String Barcode = api.BarcodeSeparator(BarcodeID);
		 Reporter.log("New barcode", newBarcode, newBarcode, "Done");
		 Reporter.log("Old barcode", Barcode, Barcode, "Done");
		 String data = "";
	 	 try (PDDocument document = PDDocument.load(new File(TicketPath))) {
	 		 document.getClass();
//	         if (document.isEncrypted()) {    	 
	    	     PDFTextStripperByArea stripper = new PDFTextStripperByArea();
	             stripper.setSortByPosition(true);           
	             PDFTextStripper tStripper = new PDFTextStripper();
	             String pdfFileInText = tStripper.getText(document);
	             Reporter.log("PDF text", pdfFileInText, pdfFileInText, "Done");
	             // split by whitespace
	             String lines[] = pdfFileInText.split("\\r?\\n");             
	             for (String line : lines) {
	                 data = data+line+'\n';            
	             }
//	         }
	 	 }
	 	 Reporter.log("Removed whitespaces PDF text", data, data, "Done");
	 	 Assert.assertFalse(data.contains(Barcode), "Old Barcode is not matching");
	 	 Assert.assertTrue(data.contains(newBarcode), "API generated New Barcode  gets matched"); 	 
	 }
	 
	 @Given("^Logout and login using (.+) and (.+)$")
	 public void logout_and_login_using_and(String email, String pass) throws Exception {
		 email = (String) base.getGDValue(email);
		 pass = (String) base.getGDValue(pass);
		 ticket.logoutNLogin(email, pass);
	 }
	 
	 @Then("^Verify that tickets displayed are in sorted order$")
	 public void verify_that_tickets_displayed_are_in_sorted_order() throws Exception {
		 boolean areTicketsInSortedOrder = ticket.verifyTicketsAreInSortedOrder();
		 Assert.assertTrue(areTicketsInSortedOrder, "Verify Tickets Displayed Are In Sorted Order");
	 }
}
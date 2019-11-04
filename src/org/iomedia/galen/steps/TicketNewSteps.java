package org.iomedia.galen.steps;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang.RandomStringUtils;
import org.iomedia.common.BaseUtil;
import org.iomedia.galen.common.AccessToken;
import org.iomedia.galen.common.ManageticketsAAPI;
import org.iomedia.galen.common.ManageticketsAPI;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.common.ManageticketsAAPI.Section;
import org.iomedia.galen.pages.Homepage;
import org.iomedia.galen.pages.Invoice;
import org.iomedia.galen.pages.ManageTicket;
import org.iomedia.galen.pages.SuperAdminPanel;
import org.iomedia.galen.pages.TicketsNew;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.testng.SkipException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TicketNewSteps {

	ManageTicket ticket;
	TicketsNew ticketNew;
	Invoice invoice;
	Homepage homepage;
	org.iomedia.framework.Assert Assert;
	ManageticketsAPI api;
	ManageticketsAAPI aapi;
	BaseUtil base;
	String driverType;
	AccessToken accessToken;
	String transferName = "TestAuto";
	org.iomedia.framework.SoftAssert SoftAssert;
	org.iomedia.framework.Reporting Reporter;
	SuperAdminPanel superAdminPanel;
	HashMap<String,String> ticketDetails;
	Utils utils;
	
	boolean CAN_TRANSFER_1, CAN_RESALE_1, CAN_RENDER_1, CAN_RENDER_FILE_1, CAN_RENDER_BARCODE_1, CAN_RENDER_PASSBOOK_1, CAN_DONATE_CHARITY_1;
	boolean CAN_TRANSFER_2, CAN_RESALE_2, CAN_RENDER_2, CAN_RENDER_FILE_2, CAN_RENDER_BARCODE_2, CAN_RENDER_PASSBOOK_2, CAN_DONATE_CHARITY_2;
	String ftname;
	String fttime;

	public TicketNewSteps(ManageTicket ticket, TicketsNew ticketNew, Invoice invoice, Homepage homepage,SuperAdminPanel superAdminPanel, org.iomedia.framework.Assert Assert, ManageticketsAPI api, ManageticketsAAPI aapi, BaseUtil base, AccessToken accessToken, org.iomedia.framework.SoftAssert SoftAssert, org.iomedia.framework.Reporting Reporter, Utils utils) {
		this.ticket = ticket;
		this.ticketNew = ticketNew;
		this.invoice = invoice;
		this.homepage = homepage;
		this.superAdminPanel = superAdminPanel;
		this.Assert = Assert;
		this.api = api;
		this.aapi = aapi;
		this.base = base;
		this.driverType = base.driverFactory.getDriverType().get();
		this.accessToken = accessToken;
		this.SoftAssert = SoftAssert;
		this.Reporter = Reporter;
		this.utils = utils;
	}

	@Then("^Verify Event Detail using (.+),NEW TICKETS$")
	public void verify_event_details(String ticketId) {
		ticketId = (String) base.getGDValue(ticketId);
		String[] tkt = ticketId.split("\\.");
		System.out.println(ticketId);
		if(ticket.checkenableEDP()==true) {
			//popup remove from edp phase3
		}else {
		SoftAssert.assertTrue(ticketNew.getSection().contains(tkt[1].replaceAll("%20", " ")));
		}
	}

	@Then("^Verify Event Detail for both tickets using (.+) (.+)")
	public void verify_event_details_two_tickets(String ticketId, String ticketId2) {
		ticketId = (String) base.getGDValue(ticketId);
		String[] tkt = ticketId.split("\\.");
		System.out.println(ticketId);
		// Assert.assertEquals(ticket.getPopUpEventDetails(),
		// base.Dictionary.get("eventName"));

		ticketId2 = (String) base.getGDValue(ticketId2);
		String[] tkt2 = ticketId2.split("\\.");

		SoftAssert.assertTrue(ticketNew.getSection().contains(tkt[1].replaceAll("%20", " ")));
		SoftAssert.assertTrue(ticketNew.getSection().contains(tkt2[1].replaceAll("%20", " ")));

		// Assert.assertTrue(ticket.getRow().contains(tkt[2]));
		// Assert.assertTrue(ticket.getSeat().contains(tkt[3]));
	}

	@Then("^Save the state of both ticket for (.+) (.+)$")
	public void save_state_of_ticket(String ticketId, String ticketId2) throws Exception {
		ticketId = (String) base.getGDValue(ticketId);
		String state = api.waitForTicketState(ticketId, "pending");
		base.Dictionary.put("State", state);

		ticketId2 = (String) base.getGDValue(ticketId2);
		String state2 = api.waitForTicketState(ticketId2, "pending");
		base.Dictionary.put("State2", state2);

	}

	@Then("^Verify the state of both the tickets (.+) (.+)$")
	public void verify_state_of_ticket(String state, String state2) {
		state = (String) base.getGDValue(state);
		state2 = (String) base.getGDValue(state2);
		System.out.println(state);
		System.out.println(state2);
		Assert.assertEquals(state, "pending");
		Assert.assertEquals(state2, "pending");
	}

	@Then("Verify ticket flags of both tickets (.+) (.+), (.+) and (.+)")
	public void verify_ticket_flags(String ticketId, String ticketId2, String emailaddress, String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		ticketId = (String) base.getGDValue(ticketId);
		ticketId2 = (String) base.getGDValue(ticketId2);
		SoftAssert.assertEquals(api.getTicketFlags(ticketId, emailaddress, password), new Boolean[] { CAN_TRANSFER_1, CAN_RESALE_1, CAN_RENDER_1, CAN_RENDER_FILE_1, CAN_RENDER_BARCODE_1, CAN_RENDER_PASSBOOK_1, CAN_DONATE_CHARITY_1 }, "Verify the ticket flags for first ticket");
		SoftAssert.assertEquals(api.getTicketFlags(ticketId2, emailaddress, password), new Boolean[] { CAN_TRANSFER_2, CAN_RESALE_2, CAN_RENDER_2, CAN_RENDER_FILE_2, CAN_RENDER_BARCODE_2, CAN_RENDER_PASSBOOK_2, CAN_DONATE_CHARITY_2 }, "Verify the ticket flags for second ticket");
	}

	@When("^User enters recipient details (.+)$")
	public void enterRecipentDetails(String email) throws Exception {
		email = (String) base.getGDValue(email);
		ticketNew.enterRecipientDetails(email);
	}

	@And("^User enter blank space and verify send button remains disabled (.+)$")
	public void enterBlankSpaceAndVerifySendDisabled(String email) throws Exception {
		email = (String) base.getGDValue(email);
		ticketNew.enterBlankSpaceAndVerifySendButtonDisabled(email);
	}

	@When("^User clicks on Send$")
	public void clickSend() {
		ticketNew.clickSend();
	}

	@When("^User clicks on Done$")
	public void clickDone() {
		ticketNew.clickDone();
	}

	@Then("^Confirmation page is displayed with Recipient details (.+)$")
	public void confirmationPage(String email) throws Exception {
		email = (String) base.getGDValue(email);
		if(ticket.checkenableEDP()==true) {
		 ticketNew.verifyRecipientEmailAddressIsSameAsEnteredEDP(email);	
		}else {
		ticketNew.verifyRecipientEmailAddressIsSameAsEntered(email);
		}
	}

	@Then("^Verify the status and expiry of Send ticket after logout and login (.+) (.+) (.+)$")
	public void verify_status_of_send_ticket(String status, String expiry, String ticketId) {
		status = (String) base.getGDValue(status);
		expiry = (String) base.getGDValue(expiry);

		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))) {
			try {
				ticketNew.selectByValueUsingInput("Pending");
			} catch (Exception e) {
				// no filter present
			}
		}

		ticketId = (String) base.getGDValue(ticketId);
		String[] tkt = ticketId.split("\\.");
		
		String statusNew = ticketNew.getTicketStatus(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);
//		String xpath = ticketNew.scrollToTicketEDP(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);
		String xpath = "//button[@type='button'  and text()='Cancel Transfer']";
		String expiryNew = ticketNew.getTicketExpiryEDP(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3],
				ticketId, xpath);
		String expiryDate = expiryNew.split("Expires")[1].split("\\.")[0].trim();
		
		System.out.println(status + "STATUS");
		System.out.println(expiry);

		Assert.assertEquals(statusNew, status, "Status is same");
		Assert.assertEquals(expiryDate, expiry, "Expiry is same");
		// Assert.assertTrue(status.trim().contains(transferName));
	}

	@Then("^Verify the status and expiry of both Send tickets after logout and login (.+) (.+) (.+) (.+) (.+) (.+)$")
	public void verify_status_of_send_ticket_both(String status, String expiry, String ticketId, String status2, String expiry2, String ticketId2) {
		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))) {
			try {
				ticketNew.selectByValueUsingInput("Pending");
			} catch (Exception e) {
				// no filter present
			}
		}
		status = (String) base.getGDValue(status);
		expiry = (String) base.getGDValue(expiry);
		ticketId = (String) base.getGDValue(ticketId);
		status2 = (String) base.getGDValue(status2);
		expiry2 = (String) base.getGDValue(expiry2);

		ticketId2 = (String) base.getGDValue(ticketId2);

		String[] tkt = ticketId.split("\\.");
		String statusNew = ticketNew.getTicketStatus(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);

		String xpath = ticketNew.scrollToTicket(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);
		String expiryNew = ticketNew.getTicketExpiry(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId,
				xpath);

		System.out.println(status + "STATUS");
		System.out.println(expiry);

		Assert.assertEquals(statusNew, status, "Status is same");
		Assert.assertEquals(expiryNew, expiry, "Expiry is same");
		// Assert.assertTrue(status.trim().contains(transferName));

		String[] tkt2 = ticketId2.split("\\.");
		String statusNew2 = ticketNew.getTicketStatus(tkt2[0], tkt2[1].replaceAll("%20", " "), tkt2[2], tkt2[3],
				ticketId2);

		String xpath2 = ticketNew.scrollToTicket(tkt2[0], tkt2[1].replaceAll("%20", " "), tkt2[2], tkt2[3], ticketId2);
		String expiryNew2 = ticketNew.getTicketExpiry(tkt2[0], tkt2[1].replaceAll("%20", " "), tkt2[2], tkt2[3],
				ticketId2, xpath2);

		System.out.println(status2 + "STATUS2");
		System.out.println(expiry2);

		Assert.assertEquals(statusNew2, status2, "Status is same");
		Assert.assertEquals(expiryNew2, expiry2, "Expiry is same");

	}

	@Then("^Verify and Save the Status and Expiry of ticket using (.+)$")
	public void save_status_of_ticket(String ticketId) {
		if (ticket.checkenableEDP()) {
			ticketId = (String) base.getGDValue(ticketId);
			String[] tkt = ticketId.split("\\.");
			String status = ticketNew.getTicketStatus(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);
//			String xpath = ticketNew.scrollToTicketEDP(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);
			String xpath = "//button[@type='button'  and text()='Cancel Transfer']";
			String expiry = ticketNew.getTicketExpiryEDP(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3],
					ticketId, xpath);
			String expiryDate = expiry.split("Expires")[1].split("\\.")[0].trim();
			
			System.out.println("TICKET STATUS" + status);
			
			ticketNew.verifyStatusAndExpiryEDP(expiry, expiryDate);

			base.Dictionary.put("Status", status);
			base.Dictionary.put("Expiry", expiryDate);
			
		} else {

			if ((driverType.trim().toUpperCase().contains("ANDROID")
					|| driverType.trim().toUpperCase().contains("IOS"))) {
				try {
					ticketNew.selectByValueUsingInput("Pending");
				} catch (Exception e) {
					// no filter present
				}
			}
			ticketId = (String) base.getGDValue(ticketId);
			String[] tkt = ticketId.split("\\.");
			String status = ticketNew.getTicketStatus(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);
			String xpath = ticketNew.scrollToTicket(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);
			String expiry = ticketNew.getTicketExpiry(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId,
					xpath);

			System.out.println("TICKET STATUS" + status);

			ticketNew.verifyStatusAndExpiry(status, expiry);
			base.Dictionary.put("Status", status);
			base.Dictionary.put("Expiry", expiry);
		}
	}

	@Then("^Verify and Save the Status and Expiry of both the tickets using (.+) (.+)$")
	public void save_status_of_ticket_both(String ticketId, String ticketId2) {
		ticketId = (String) base.getGDValue(ticketId);
		ticketId2 = (String) base.getGDValue(ticketId2);
		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))) {
			try {
				ticketNew.selectByValueUsingInput("Pending");
			} catch (Exception e) {
				// no filter present
			}
		}

		String[] tkt = ticketId.split("\\.");
		String status = ticketNew.getTicketStatus(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);

		String xpath = ticketNew.scrollToTicket(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);
		String expiry = ticketNew.getTicketExpiry(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId, xpath);

		System.out.println("TICKET STATUS 1" + status);
		System.out.println("TICKET Expiry 1" + expiry);
		ticketNew.verifyStatusAndExpiry(status, expiry);

		base.Dictionary.put("Status", status);
		base.Dictionary.put("Expiry", expiry);

		String[] tkt2 = ticketId2.split("\\.");
		String status2 = ticketNew.getTicketStatus(tkt2[0], tkt2[1].replaceAll("%20", " "), tkt2[2], tkt2[3], ticketId2);

		String xpath2 = ticketNew.scrollToTicket(tkt2[0], tkt2[1].replaceAll("%20", " "), tkt2[2], tkt2[3], ticketId2);
		String expiry2 = ticketNew.getTicketExpiry(tkt2[0], tkt2[1].replaceAll("%20", " "), tkt2[2], tkt2[3], ticketId2,
				xpath2);

		ticketNew.verifyStatusAndExpiry(status2, expiry2);
		System.out.println("TICKET STATUS 2" + status2);
		System.out.println("TICKET Expiry 2" + expiry2);

		base.Dictionary.put("Status2", status2);
		base.Dictionary.put("Expiry2", expiry2);
	}

	@Given("^Get transfer ticket IDs$")
	public void get_transfer_ticket_IDs() throws Exception {
		// String[] Tkt = api.getTransferDetails(emailaddress, password, true, "event",
		// false, false, false);

		String[] Tkt = api.getTransferDetails(true, "event", true, false, false);
		System.out.println("EVent id is " + Tkt[0].split("\\.")[0]);
		System.out.println(Tkt[0]);
		System.out.println(Tkt[1]);
		base.Dictionary.put("TransferTicketID1", Tkt[0]);
		base.Dictionary.put("TransferTicketID2", Tkt[1]);
		base.Dictionary.put("EventId", Tkt[0].split("\\.")[0]);
	}

	@Given("^Get transfer ticket ID with related event (.+)$")
	public void get_transfer_ticket_IDs_related(String multipleParkingTickets) throws Exception {
		multipleParkingTickets = (String) base.getGDValue(multipleParkingTickets);
		String[] Tkt = api.getTransferDetails(true, "event", false, Boolean.valueOf(multipleParkingTickets), true);
		System.out.println("EVent id is " + Tkt[0].split("\\.")[0]);
		System.out.println(Tkt[0]);
		base.Dictionary.put("TransferTicketID1", Tkt[0]);
		base.Dictionary.put("EventId", Tkt[0].split("\\.")[0]);
	}

	@Given("^Get ticket ID without Deferred Delivery$")
	public void get_No_deferred_ticket_IDs() throws Exception {
		String[] Tkt = api.getTicketsWithoutDeferredDelivery(true, "event", false, false, false);
		System.out.println("Event id is " + Tkt[0].split("\\.")[0]);
		System.out.println(Tkt[0]);
		base.Dictionary.put("TransferTicketID1", Tkt[0]);
		base.Dictionary.put("EventId", Tkt[0].split("\\.")[0]);
	}

	@Given("^Get transfer ticket IDs with related events$")
	public void get_transfer_multiple_ticket_IDs_related() throws Exception {
		String[] Tkts = api.getTransferDetails(true, "event", true, true, true);
		System.out.println("EVent id is " + Tkts[0].split("\\.")[0]);
		base.Dictionary.put("TransferTicketID1", Tkts[0]);
		base.Dictionary.put("TransferTicketID2", Tkts[1]);
		base.Dictionary.put("EventId", Tkts[0].split("\\.")[0]);
		base.Dictionary.put("Multiple", "true");
	}

	@Given("^Get transfer ticket ID with parking$")
	public void get_transfer_ticket_IDs_parking() throws Exception {
		String[] Tkt = api.getTransferDetails(true, "parking", false, false, false);
		System.out.println("Event id is " + Tkt[0].split("\\.")[0]);
		System.out.println(Tkt[0]);
		base.Dictionary.put("TransferTicketID", Tkt[0]);
		base.Dictionary.put("EventId", Tkt[0].split("\\.")[0]);
	}
	

	@Given("^Save ticket flags for ticket Ids (.+) (.+) using (.+) and (.+)$")
	public void save_ticket_flags(String tick, String tick2, String emailaddress, String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		tick = (String) base.getGDValue(tick);
		String[] ticket = tick.split("\\.");
		base.Dictionary.put("EventId", ticket[0]);
		Boolean[] flags = api.getTicketFlags(tick, emailaddress, password);

		CAN_TRANSFER_1 = flags[0];
		CAN_RESALE_1 = flags[1];
		CAN_RENDER_1 = flags[2];
		CAN_RENDER_FILE_1 = flags[3];
		CAN_RENDER_BARCODE_1 = flags[4];
		CAN_RENDER_PASSBOOK_1 = flags[5];
		CAN_DONATE_CHARITY_1 = flags[6];

		tick2 = (String) base.getGDValue(tick2);
		Boolean[] flags2 = api.getTicketFlags(tick2, emailaddress, password);

		CAN_TRANSFER_2 = flags2[0];
		CAN_RESALE_2 = flags2[1];
		CAN_RENDER_2 = flags2[2];
		CAN_RENDER_FILE_2 = flags2[3];
		CAN_RENDER_BARCODE_2 = flags2[4];
		CAN_RENDER_PASSBOOK_2 = flags2[5];
		CAN_DONATE_CHARITY_2 = flags2[6];
	}

	@When("^User selects two seat using (.+) (.+)$")
	public void user_select_seat(String ticketId, String ticketId2) {
		ticketId = (String) base.getGDValue(ticketId);
		ticketId2 = (String) base.getGDValue(ticketId2);

		String[] tkt = ticketId.split("\\.");
		ticket.selectSeatInPopUp(ticketId, tkt[1].replaceAll("%20", " "), tkt[2], tkt[3]);
		String[] tkt2 = ticketId2.split("\\.");
		ticket.selectSeatInPopUp(ticketId2, tkt2[1].replaceAll("%20", " "), tkt2[2], tkt2[3]);
	}

	@And("^User selects Bundle Parking and Parking Slot (.+)$")
	public void selectBundleParking(String multiple) {
		multiple = (String) base.getGDValue(multiple);
		ticket.clickBundleParking();
		if (multiple.equalsIgnoreCase("true")) {
			ticket.selectParkingSlots(2);
			ticketNew.getTextOfSelectedParkingSlot(1);
			ticketNew.getTextOfSelectedParkingSlot(2);
		} else {
			ticket.selectParkingSlots(1);
			ticketNew.getTextOfSelectedParkingSlot(1);
		}

	}

	@And("^Verify parking slot selected in Bundles is displayed (.+)$")
	public void parkingSlotVerify(String parkingSlot) {
		parkingSlot = (String) base.getGDValue(parkingSlot);

		SoftAssert.assertTrue(ticketNew.getSection().contains(parkingSlot));

	}

	@And("^Verify both parking slot selected in Bundles is displayed (.+) (.+)$")
	public void parkingSlotBothVerify(String parkingSlot, String parkingSlot2) {
		parkingSlot = (String) base.getGDValue(parkingSlot);
		parkingSlot2 = (String) base.getGDValue(parkingSlot);

		System.out.println(parkingSlot);
		String[] p1 = parkingSlot.split("Seat");
		System.out.println(p1[1]);
		// Assert.assertEquals(ticket.getPopUpEventDetails(),
		// base.Dictionary.get("eventName"));

		String[] p2 = parkingSlot2.split("Seat");

		SoftAssert.assertTrue(ticketNew.getSection().contains(p1[1].trim()));
		SoftAssert.assertTrue(ticketNew.getSection().contains(p2[1].trim()));

		// Assert.assertTrue(ticket.getRow().contains(tkt[2]));
		// Assert.assertTrue(ticket.getSeat().contains(tkt[3]));

		// parkingSlot = (String) base.getGDValue(parkingSlot);
		// parkingSlot2 = (String) base.getGDValue(parkingSlot2);
		// ticketNew.verifyParkingSlot(parkingSlot);
		// ticketNew.verifyParkingSlot(parkingSlot2);

	}

	@Then("^User click on Claim link$")
	public void user_click_on_claim_link() throws Exception {
		if(ticket.checkenableEDP()==true) {
			ticket.clickClaimEDP();	
		}else {
			ticket.clickClaim();
		}
	}

	@Then("^Claim Pop-up is displayed with Error Message$")
	public void errorMessageClaim() throws Exception {
		ticketNew.verifyErrorMessageClaim();
	}

	@Then("^Get ticket with Price and ValueID for (.+) (.+)$")
	public void getTicketWithPriceAndValueId(String email, String pass) throws Exception {
		email = (String) base.getGDValue(email);
		pass = (String) base.getGDValue(pass);
		System.out.println("EMAIL " + email);
		// String[] Tkt = api.getTransferDetails(emailaddress, password, true, "event",
		// false, false, false);
		String[] Tkt = api.getPriceCurrencyAndValue(email, pass, true, "event", false, false, false);

		base.Dictionary.put("TicketID", Tkt[0]);
		base.Dictionary.put("EventId", Tkt[0].split("\\.")[0]);

		System.out.println(Tkt[0]);
		System.out.println(base.Dictionary.get("Currency") + "Dict");
		System.err.println(base.Dictionary.get("Price") + "Price");

	}

	@When("^User clicks on Ticket Details (.+) and verifies values of Purchase Price on UI and API$")
	public void clickTicketDetails(String ticketId) throws Exception {
		ticketId = (String) base.getGDValue(ticketId);
		String[] tkt = ticketId.split("\\.");
		By ticketDetails;
		By purchasePrice;
		String price="";
		By iosAppLocator = null;
        if (ticket.checkenableEDP()) {
			ticketNew.getTicketPricePathEDP(tkt[0], tkt[1], tkt[2], tkt[3], ticketId);
			if (((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")))) {
				//String xpath = ticketNew.scrollToTicketAfterReloadEDP(tkt[0], tkt[1], tkt[2], tkt[3], ticketId);
				iosAppLocator = By.xpath("//XCUIElementTypeStaticText[@value='" + tkt[1] + "']/../../..//XCUIElementTypeStaticText[@value='" + tkt[2] + "']/../../..//XCUIElementTypeStaticText[@value='" + tkt[3] + "']/../../..//XCUIElementTypeStaticText[@value='TICKET DETAILS'] | //XCUIElementTypeStaticText[@value='" + tkt[1] + "']/../../..//XCUIElementTypeStaticText[@value='" + tkt[2] + "']/../../..//XCUIElementTypeStaticText[@value='" + tkt[3] + "']/../../..//XCUIElementTypeStaticText[@value='Ticket Details']");
				ticketDetails = By.xpath("//*[contains(@class,'ticket-carousel-infoIcon')]");
				purchasePrice = By.xpath("(//div[@data-index=\"0\"]//following::*[text()='Ticket Details'])[1]/following-sibling::*//h4[text()='Adult']/following-sibling::span");
				ticketNew.clickTickedDetailsAndValidatePriceOnUI(ticketDetails, purchasePrice, iosAppLocator);
				
			} else {
				
			price = ticketNew.getTicketPriceEDP();					
			System.out.println(base.Dictionary.get("Price"));
			Double priceAPI = Double.parseDouble(base.Dictionary.get("Price")) / 100;
			String currencyApi = base.Dictionary.get("Currency");

			System.out.println(Double.parseDouble(price.replaceAll("[^0-9.]", "")));
			System.out.println(priceAPI);

			System.out.println(price.replaceAll("[0-9.]", ""));

			Assert.assertEquals(Double.parseDouble(price.replaceAll("[^0-9.]", "")), priceAPI, "Price on UI is Price in API divided by 100");
			
			if(currencyApi.equalsIgnoreCase(base.Environment.get("currency"))) {
				Assert.assertEquals(price.replaceAll("[0-9.]", "").trim(), currencyApi, "Currency on UI is same as in API");
			} else 
			    Assert.assertEquals(price.replaceAll("[0-9.]", "").trim(), currencyApi, "Currency on UI is same as in API");
			}
			
		} else {
			
			
			String xpath = ticketNew.scrollToTicketAfterReload(tkt[0], tkt[1], tkt[2], tkt[3], ticketId);
					
			if (((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")))) {
				if(driverType.trim().toUpperCase().contains("IOS")) {
					ticketDetails = By.xpath("(" + xpath + "//*[contains(@class, 'ticket-completedStatus')])[1]");
				} else {
					try {
						base.getElementWhenVisible(By.xpath(xpath + "//*[contains(@class,'detailTicket') and contains(text(),'TICKET')]"));
						ticketDetails = By.xpath(xpath + "//*[contains(@class,'detailTicket') and contains(text(),'TICKET')]");
					}
					catch(Exception E) {
						base.getElementWhenVisible(By.xpath(xpath + "//*[contains(@class,'completed')]//span[contains(text(),'TICKET')]"));
						ticketDetails = By.xpath(xpath + "//*[contains(@class,'completed')]//span[contains(text(),'TICKET')]");
					}
				}
				iosAppLocator = By.xpath("//XCUIElementTypeStaticText[@value='" + tkt[1] + "']/../../..//XCUIElementTypeStaticText[@value='" + tkt[2] + "']/../../..//XCUIElementTypeStaticText[@value='" + tkt[3] + "']/../../..//XCUIElementTypeStaticText[@value='TICKET DETAILS'] | //XCUIElementTypeStaticText[@value='" + tkt[1] + "']/../../..//XCUIElementTypeStaticText[@value='" + tkt[2] + "']/../../..//XCUIElementTypeStaticText[@value='" + tkt[3] + "']/../../..//XCUIElementTypeStaticText[@value='Ticket Details']");
				purchasePrice = By.xpath("(" + xpath + "/..//*[contains(@class,'ticket-back')]//*[text()='Purchase Price']/..//span//span)[1]");
			} else {
				try {
					base.getElementWhenVisible(By.xpath(xpath + "//*[contains(@class,'detailTicket') and contains(text(),'Ticket Details')]"));
					ticketDetails = By.xpath(xpath + "//*[contains(@class,'detailTicket') and contains(text(),'Ticket Details')]");
				}
				catch(Exception E) {
					base.getElementWhenVisible(By.xpath(xpath + "//*[contains(@class,'completed')]//span[contains(text(),'TICKET')]"));
					ticketDetails = By.xpath(xpath + "//*[contains(@class,'completed')]//span[contains(text(),'TICKET')]");
				}
				purchasePrice = By.xpath(xpath + "/..//*[contains(@class,'ticket-back')]//*[text()='Adult']/..//span");
				ticketNew.clickTickedDetailsAndValidatePriceOnUI(ticketDetails, purchasePrice, iosAppLocator);
			}			
		}		
		
	}

	@Given("^Get ticket with Pending Invoice for (.+) (.+)$")
	public void get_pending_invoice_ticket(String emailaddress, String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		String[] tickets = api.getPendingInvoiceTicketId(emailaddress, password, true, "event", false, false, false);
		base.Dictionary.put("TicketID", tickets[0]);
		base.Dictionary.put("EventId", tickets[0].split("\\.")[0]);
		System.out.println(tickets.length);
		System.out.println(tickets[0]);
	}

	@When("^User clicks on Make Payment (.+) , Invoice page is displayed$")
	public void clickMakePayment(String ticketId) throws Exception {
		ticketId = (String) base.getGDValue(ticketId);
		String[] tkt = ticketId.split("\\.");
		
		if (ticket.checkenableEDP()==true) {
			throw new SkipException("Flip Window Functionality Is Not Present In EDP");
		}
		
		String xpath = ticketNew.scrollToTicketAfterReload(tkt[0], tkt[1], tkt[2], tkt[3], ticketId);
		By makePayment;
		By iosAppLocator = null;
		System.out.println(xpath);
		if (((driverType.trim().toUpperCase().contains("ANDROID")
				|| driverType.trim().toUpperCase().contains("IOS")))) {
			makePayment = By.xpath(xpath + "//*[contains(@class, 'ticket-completedStatus')]//a");
			iosAppLocator = By.xpath(
					"//XCUIElementTypeStaticText[@value='" + tkt[1] + "']/../../..//XCUIElementTypeStaticText[@value='"
							+ tkt[2] + "']/../../..//XCUIElementTypeStaticText[@value='" + tkt[3]
							+ "']/../../..//XCUIElementTypeStaticText[@value='MAKE A PAYMENT']");
		} else {
			makePayment = By.xpath(xpath + "//*[contains(@class, 'ticket-completedStatus')]//a");
		}
		ticketNew.clickMakePaymentAndValidateInvoicePage(makePayment, iosAppLocator);
	}

	@When("^Verify Ticket details flip window should be displayed only for one ticket. On click of second ticket, previous one should be closed. (.+) (.+)$")
	public void verifyTicketFlipWindow(String tick1, String tick2) throws Exception {
		if (((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))))
			Assert.assertTrue(true, "SKIPPING THIS CHECK FOR MOBILE");
		tick1 = (String) base.getGDValue(tick1);
		tick2 = (String) base.getGDValue(tick2);
		String[] tkt1 = tick1.split("\\.");

		System.out.println(tick1);
		System.out.println(tick2);

		String xpath = ticketNew.scrollToTicketAfterReload(tkt1[0], tkt1[1], tkt1[2], tkt1[3], tick1);
		String[] tkt2 = tick2.split("\\.");
		String xpath2 = ticketNew.scrollToTicketAfterReload(tkt2[0], tkt2[1], tkt2[2], tkt2[3], tick2);
		ticketNew.verifyFlipWindow(xpath, xpath2);
	}

	@Given("^Verify Sections and Tickets for Bulk Transfer (.+) (.+)$")
	public void getSectionDetails(String email, String pass) throws Exception {
		email = (String) base.getGDValue(email);
		pass = (String) base.getGDValue(pass);
		base.Dictionary.put("EMAIL_ADDRESS", email);
		int countOfEventSelected =0;
		if(base.Dictionary.get("EventCountSelected")!="")
			countOfEventSelected = Integer.parseInt(base.Dictionary.get("EventCountSelected"));

		List<org.iomedia.galen.common.ManageticketsAAPI._Event> event = aapi.getEventDetails(email,pass);
		LinkedHashMap<Integer, List<Section>> abc = aapi.getAllSectionDetailsWithSorting(event,countOfEventSelected);

		String[] eventNamesUI = ticketNew.getAllEventsNameBulkTransfer();

		List<List<Section>> allEventsWithSections = new ArrayList<List<Section>>(abc.values());
		String[] sectionLables = ticketNew.getAllSectionLabels();
		List<String> ticketsName = aapi.getTicketNames(allEventsWithSections);

		ticketNew.getTicketNamesAndVerifyTickets(ticketsName);

		int countMatched = 0;

		for(int i = 0; i <abc.size();i++)
			{
				List<Section> sectionsForEvent = allEventsWithSections.get(i);
			//Uncomment below assertion when sorting bug is fixed on ui
				Assert.assertEquals(eventNamesUI[i],event.get(i).getEventName(),"Verify Event Names");
				if(sectionsForEvent.size()!=1)
					{
					for (int j = 0; j < sectionsForEvent.size(); j++) {
						Assert.assertEquals(sectionsForEvent.get(j).sectionFormmattedLabel(),sectionLables[countMatched].trim(),"Verify Section Names");
						countMatched++;
					}
				} else countMatched++;
			}
	}


	@Given("^Get Ticket Names from Invite (.+) (.+) and (.+)$")
	public void getTicketNamesFromInvite(String email, String pass,String inviteId) throws Exception {
		email = (String) base.getGDValue(email);
		pass = (String) base.getGDValue(pass);
		inviteId = (String) base.getGDValue(inviteId);

		List<Integer> eventIds = aapi.getEventIdsForSpecificInvite(email, pass, inviteId);
		LinkedHashMap<Integer,List<String>> allTicketNames = new LinkedHashMap<Integer,List<String>>();
		for (int i = 0; i < eventIds.size(); i++) {
			allTicketNames.put(eventIds.get(i),aapi.getSpecificInviteTicketName(email,pass,inviteId,eventIds.get(i)));
		}
	}

	@Then("^Click on Bulk Transfer Checkbox and Verify Send Ticket button and Select All checkbox displayed$")
	public void clickBulkTransferAndVerifySendButtonAndSelectAllCbox() throws Exception {
			ticketNew.clickBulkTransferValidateSendButtonAndSelectAllCbox();
	}

	@Then("^Select first two Events for Bulk Transfer and click Send Tickets button$")
	public void selectFirstTwoEvents() throws Exception {
			ticketNew.selectEventsBulkTranfer(2);
			base.Dictionary.put("EventCountSelected", "2");
	}

	@Then("^User selects  Valid Event for Complete Section Bulk Transfer and click Send Tickets button for member (.+) (.+)$")
	public void selectFirstEvent(String mail, String passwd) throws Exception {
		String uname = (String) base.getGDValue(mail);
		String password = (String) base.getGDValue(passwd);
		String eventNameSectionTransfer = base.Dictionary.get("EventNameSectionTransfer");
		if (eventNameSectionTransfer.isEmpty()) {
			HashMap<String, String> availability = aapi.getTransfrableSectionForMemberEvents(uname, password);
			eventNameSectionTransfer = availability.get("eventname");
			base.Dictionary.put("EventNameSectionTransfer", availability.get("eventname"));
			base.Dictionary.put("EventIdSectionTransfer", availability.get("eventid"));
			base.Dictionary.put("SectionNameSectionTransfer", availability.get("section"));
			base.Dictionary.put("TicketNumberSectionTransfer", availability.get("number"));
			base.Dictionary.put("EventDateSectionTransfer", availability.get("eventdate"));
			base.Dictionary.put("EventTimeSectionTransfer", availability.get("eventtime"));
		}
		ticketNew.selectEventByName(eventNameSectionTransfer);
		ticketNew.clickTransfer();
		
	}


	@Then("^User selects  Valid  two Event for Complete Section Bulk Transfer and click Send Tickets button for member (.+) (.+)$")
	public void selectTwoEvent(String mail, String passwd) throws Exception {
		String uname = (String) base.getGDValue(mail);
		String password = (String) base.getGDValue(passwd);
		HashMap<String, HashMap<String,String>> availability = aapi.getTransfrableTwoEventsSectionForMemberEvents(uname, password);
		ticketNew.selectEventByName(availability.get("0").get("eventname"));
		ticketNew.selectEventByName(availability.get("1").get("eventname"));
		ticketNew.clickTransferTwo();
		base.Dictionary.put("EventNameSectionTransfer1", availability.get("0").get("eventname"));
		base.Dictionary.put("EventIdSectionTransfer1", availability.get("0").get("eventid"));
		base.Dictionary.put("SectionNameSectionTransfer1", availability.get("0").get("section"));
		base.Dictionary.put("TicketNumberSectionTransfer1", availability.get("0").get("number"));
		base.Dictionary.put("EventNameSectionTransfer2", availability.get("1").get("eventname"));
		base.Dictionary.put("EventIdSectionTransfer2", availability.get("1").get("eventid"));
		base.Dictionary.put("SectionNameSectionTransfer2", availability.get("1").get("section"));
		base.Dictionary.put("TicketNumberSectionTransfer2", availability.get("1").get("number"));

	}

	@Then("Model to select seats is triggered with correct Event Name and Time$")
	public void verifyModel() {
		ticketNew.verifyModel(base.Dictionary.get("EventNameSectionTransfer"), base.Dictionary.get("EventDateSectionTransfer"),base.Dictionary.get("EventTimeSectionTransfer"));
	}

	@Then("Model to select seats is triggered with correct Event Names and Time for both Events")
	public void verifyModelTwoEvents() {
		ticketNew.verifyModelTwoEvents(base.Dictionary.get("EventNameSectionTransfer1"), base.Dictionary.get("EventDateSectionTransfer1"),base.Dictionary.get("EventTimeSectionTransfer1"));
	}

	@And("Model can be closed by clicking  X button")
	public void verifyXButton() {
		ticketNew.verifyXButton();
	}

	@And("Model can be closed by clicking Cancel button")
	public void verifyCancelButton() {
		ticketNew.verifyCancelButton();
	}

	@Then("^Barcode should be \"(.+)\" on UI (.+)$")
	public void barcodeCheck(String check, String tick1) throws Exception {
		tick1 = (String) base.getGDValue(tick1);
		String[] tkt1 = tick1.split("\\.");
		if (ticket.checkenableEDP()) {
			ticket.selectEventTicketAvailableEDP(tkt1[0], tkt1[1], tkt1[2], tkt1[3]);
			if (check.equalsIgnoreCase("Enabled") || check.equalsIgnoreCase("Enable")) {
				ticketNew.verifyBarcodeEDP(true);
			} else
				ticketNew.verifyBarcodeEDP(false);
		} else {
			String xpath = ticketNew.scrollToTicketAfterReload(tkt1[0], tkt1[1], tkt1[2], tkt1[3], tick1);
			if (check.equalsIgnoreCase("Enabled") || check.equalsIgnoreCase("Enable")) {
				ticketNew.verifyBarcode(xpath, true);
			} else
				ticketNew.verifyBarcode(xpath, false);
		}
	}

	@Given("^Get all events sorted and verify with name, date and time with UI (.+) (.+)$")
	public void getSortedEvents(String emailaddress, String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		//List<_Event> _events = api.getEventDetails(emailaddress, password);
		List<ManageticketsAAPI._Event> _events = aapi.getEventDetails(emailaddress, password);
		Assert.assertNotNull(_events);
		base.Dictionary.put("TotalEvents", "" + _events.size() + "");
		System.out.println(base.Dictionary.get("TotalEvents"));
		String date1, date2, date3;
		String time1, time2;

		List<String> dateTime = ticket.getListOfDateAndTime();
		System.out.println(dateTime);
		List<String> eventName = ticket.getListOfEventNames();
		System.out.println(eventName);

		for (int i = 0; i < _events.size(); i++) {
			if (_events.get(i).getDate() == null || _events.get(i).getDate().toString() == "") {
				date1 = _events.get(i).getDateOverride();
				System.out.println(dateTime.get(i) + "---" + date1);
				Assert.assertTrue(dateTime.get(i).contains(date1));
			} else {
				date1 = new SimpleDateFormat("MMMM dd, yyyy").format(_events.get(i).getDate());
				date2 = new SimpleDateFormat("dd MMMM yyyy").format(_events.get(i).getDate());
				date3 = new SimpleDateFormat("MMM d yyyy").format(_events.get(i).getDate()).trim().toUpperCase();
				System.out.println(dateTime.get(i) + "---" + date1 + "---" + date2 + "---" + date3);
				Assert.assertTrue(dateTime.get(i).contains(date1) || dateTime.get(i).contains(date2) || dateTime.get(i).contains(date3));
			}
			if (_events.get(i).getTime() == null || _events.get(i).getTime().toString() == "") {
				time1 = _events.get(i).getTimeOverride();
				base.Dictionary.put("Time" + i, time1);
				System.out.println(dateTime.get(i) + "---" + time1);
				Assert.assertTrue(dateTime.get(i).contains(time1));
			} else {
				time1 = new SimpleDateFormat("hh:mm aa").format( _events.get(i).getTime());
				time2 = new SimpleDateFormat("hh:mm").format( _events.get(i).getTime());
				base.Dictionary.put("Time" + i, time1);
				System.out.println(dateTime.get(i) + "---" + time1 + "---" + time2);
				Assert.assertTrue(dateTime.get(i).contains(time1) || dateTime.get(i).contains(time2));
			}
			base.Dictionary.put("Date" + i, date1);
			base.Dictionary.put("EventName" + i, _events.get(i).getEventName());
			Assert.assertTrue(_events.get(i).getEventName().equalsIgnoreCase(eventName.get(i)));
		}
	}

	@Given("^Get Event Id with Maximum Tickets$")
	public void getEventIdAndValidateFilter() throws Exception {
		HashMap<Integer, ManageticketsAPI.Event> events = api.getEventIdWithTktsDetails();
		Assert.assertNotNull(events);

		int eventId = api.getEventIdWithMaxTkts(events);

		Assert.assertNotEquals(eventId, -1);

		System.out.println("Event Id With MAximum Tickets "+eventId);

		base.Dictionary.put("EventId", String.valueOf(eventId));

	}

	@Then("^Verify filtering of tickets for (.+) (.+) (.+)$")
	public void verifyFiltering(String emailaddress, String password, String eventIdString) throws Exception {
		if(ticket.checkenableEDP()==true) {
			throw new SkipException("This feature is not implemented in EDP");
		}else {
			emailaddress = (String) base.getGDValue(emailaddress);
			password = (String) base.getGDValue(password);
			eventIdString = (String) base.getGDValue(eventIdString);
			int eventId = Integer.parseInt(eventIdString);

			String access = accessToken.getAccessToken(emailaddress, password);
			String host = base.Environment.get("TM_HOST").trim();
			JSONObject jsonObject = api.get(host + "/api/v1/member/" + base.Dictionary.get("member_id") + "/inventory/search?event_id=" + eventId, access);
			int activeTickets = api.getTicketIdCount(jsonObject, null, "Active");
			int pendingTickets = api.getTicketIdCount(jsonObject, "pending", "Pending");
			int completedTickets = api.getTicketIdCount(jsonObject, "donated", "Completed") + api.getTicketIdCount(jsonObject, "sold", "Completed") + api.getTicketIdCount(jsonObject, "accepted", "Completed");

			int totalTickets = activeTickets + pendingTickets + completedTickets;

			System.out.println(activeTickets);
			System.out.println(pendingTickets);
			System.out.println(completedTickets);

			boolean filterPresent = ticketNew.verifyDropDownFilter(activeTickets, pendingTickets, completedTickets);

			if (filterPresent) {
				int ticketsCount = ticketNew.getTicketCountText();
				Assert.assertEquals(ticketsCount, totalTickets);
				if (activeTickets != 0) {
					ticketNew.selectByValueUsingInput("Active");
					ticketsCount = ticketNew.getTicketCountText();
					Assert.assertEquals(ticketsCount, activeTickets, "Active Tickets are correclty filtered");
				}
				if (completedTickets != 0) {
					ticketNew.selectByValueUsingInput("Completed");
					ticketsCount = ticketNew.getTicketCountText();
					Assert.assertEquals(ticketsCount, completedTickets, "Completed Tickets are correclty filtered");
				}
				if (pendingTickets != 0) {
					ticketNew.selectByValueUsingInput("Pending");
					ticketsCount = ticketNew.getTicketCountText();
					Assert.assertEquals(ticketsCount, pendingTickets, "Pending Tickets are correclty filtered");
				}
			}
			System.out.println(filterPresent);
		}
	}

	@Then("^Get count of Pending and Active Tickets (.+) (.+) (.+)$")
	public void getCountOfPendingAndActive(String emailaddress, String password, String eventIdString) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		eventIdString = (String) base.getGDValue(eventIdString);
		int eventId = Integer.parseInt(eventIdString);

		String access = accessToken.getAccessToken(emailaddress, password);
		String host = base.Environment.get("TM_HOST").trim();
		JSONObject jsonObject = api.get(host + "/api/v1/member/" + base.Dictionary.get("member_id") + "/inventory/search?event_id=" + eventId, access);
		int activeTickets = api.getTicketIdCount(jsonObject, null, "Active");
		int pendingTickets = api.getTicketIdCount(jsonObject, "pending", "Pending");
		int completedTickets = api.getTicketIdCount(jsonObject, "donated", "Completed") + api.getTicketIdCount(jsonObject, "sold", "Completed") + api.getTicketIdCount(jsonObject, "accepted", "Completed");

		System.out.println(activeTickets);
		System.out.println(pendingTickets);

		base.Dictionary.put("ActiveCount", String.valueOf(activeTickets));
		base.Dictionary.put("PendingCount", String.valueOf(pendingTickets));
		base.Dictionary.put("CompletedCount", String.valueOf(completedTickets));
	}

	@Then("^Verify count of Pending ticket reduced by one and count of Active tickets increased by one (.+) (.+) (.+)$")
	public void verifyCountOfFilterUpdated(String activeCount, String pendingCount, String completedCount) throws Exception {
		if(ticket.checkenableEDP()==true) {
			System.out.println("This Feature is not implemented on EDP");
		}else {
			
		activeCount = (String) base.getGDValue(activeCount);
		pendingCount = (String) base.getGDValue(pendingCount);
		completedCount = (String) base.getGDValue(completedCount);
		int ticketsCount = 0;

		boolean filterPresent = ticketNew.verifyDropDownFilter(Integer.parseInt(activeCount)+1, Integer.parseInt(pendingCount)-1, Integer.parseInt(completedCount));

		System.out.println(activeCount);
		System.out.println(pendingCount);

		if (filterPresent) {
			ticketsCount = ticketNew.getTicketCountText();

			ticketNew.selectByValueUsingInput("Active");
			ticketsCount = ticketNew.getTicketCountText();
			System.out.println(ticketsCount);

			Assert.assertEquals(ticketsCount, Integer.parseInt(activeCount) + 1, "Active Tickets counts increased by 1 as expected");

			if (Integer.parseInt(pendingCount) != 1) {

				ticketNew.selectByValueUsingInput("Pending");
				ticketsCount = ticketNew.getTicketCountText();
				System.out.println(ticketsCount);
				Assert.assertEquals(ticketsCount, Integer.parseInt(pendingCount) - 1, "Pending Tickets counts decreased by 1 as expected");
			} else
				Assert.assertEquals(0, Integer.parseInt(pendingCount) - 1, "Pending Tickets counts decreased by 1 as expected");
		}
		else {
			 if(ticket.checkenableEDP()==true) {
				 ticketsCount = ticket.getTotalTicketsCountTextEDP();
				 Assert.assertEquals(ticketsCount, Integer.parseInt(activeCount) + 1,"Active Tickets counts increased by 1 as expected");
				 Assert.assertEquals(0, Integer.parseInt(pendingCount) - 1,"Pending Tickets counts decreased by 1 as expected");
			 }
			 else {
		    ticketsCount = ticketNew.getTicketCountText();
			Assert.assertEquals(ticketsCount, Integer.parseInt(activeCount) + 1,"Active Tickets counts increased by 1 as expected");
			Assert.assertEquals(0, Integer.parseInt(pendingCount) - 1,"Pending Tickets counts decreased by 1 as expected");
			 }
		}
		
		}
	}

	@When("^User clicks on Edit or Cancel ticket, new ticket, for (.+)$")
	public void user_click_edit_cancel_ticket(String ticketId) {
		ticketId = (String) base.getGDValue(ticketId);
		String xpath ="";
		String[] tkt = ticketId.split("\\.");
		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))) {
			if(ticket.checkenableEDP()) {
				throw  new SkipException("This Feature is removed in EDP");
			}
			try {
				ticketNew.selectByValueUsingInput("Pending");
			} catch (Exception e) {
				// no filter present
			}
		}

     if(ticket.checkenableEDP()) {
    	 xpath = ticketNew.scrollToTicketEDP(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId); 
    	 ticketNew.clickEditCancelTicketEDP(xpath);
     }
     else {
		xpath = ticketNew.scrollToTicket(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId);
		ticketNew.clickEditCancelTicket(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], ticketId, xpath);
     }
     
	}

	@When("^User clicks on \"(.+)\" link in Email (.+)$")
	public void loadEmailTicketLin(String linktype, String link) {
		link = (String) base.getGDValue(link);
		base.getDriver().navigate().to(link);
		if (linktype.equalsIgnoreCase("Accept")) {
			Assert.assertTrue(homepage.getLoginMessage().contains("Congratulations"), "Verify 'Congratulations' text is displayed on login screen");
//			homepage.isHomepageDisplayed(null);
			String url = base.getDriver().getCurrentUrl();
			String claimId = url.substring(url.indexOf("?"));
			base.getDriver().get(base.Environment.get("APP_URL")+"/ticket/claim/"+claimId);
		} else if (linktype.equalsIgnoreCase("Decline"))
			ticketNew.declinePageDisplayed();
		else
			throw new SkipException("Enter correct type in feature file");
	}

	@Then("^Decline Offer page is displayed and user enters optional message, agrees Term of User and Declines Offer")
	public void declineOffer() throws Exception {
		ticketNew.declinePageDisplayed();
		ticketNew.declineOffer();
	}

	@Then("^Accept or Decline Offer using AAPI")
	public void acceptDeclineDashboard(String key) throws Exception {
		ticketNew.clickOnAcceptOrDecline(key);
	}

	@Then("^Select two tickets and click on Choose Recipient (.+) (.+)")
	public void selectTickets(String email, String pass) throws Exception {
		email = (String) base.getGDValue(email);
		pass = (String) base.getGDValue(pass);
		base.Dictionary.put("EMAIL_ADDRESS", email);
		ticketNew.getAllSectionLabels();
		ticketNew.selectTicket(ticketDetails);
		ticketNew.clickChooseRecipient();
	}

	@Then("^Add Recipient and Send Ticket (.+)")
	public void addRecipientAndSend(String email) throws Exception {
		email = (String) base.getGDValue(email);
		ticketNew.addRecipientAndSend(email);
	}

	@Then("^Click on Bulk Transfer Checkbox, verify Send and Select All buttons, Select two Events with Transfer Tickets enabled and Click Send Tickets (.+) (.+)$")
	public void selectTwoEventsWithTransferTicket(String email, String pass) throws Exception {
		email = (String) base.getGDValue(email);
		pass = (String) base.getGDValue(pass);
		base.Dictionary.put("EMAIL_ADDRESS", email);
			int countOfEventSelected =0;
			if(base.Dictionary.get("EventCountSelected")!="")
				countOfEventSelected = Integer.parseInt(base.Dictionary.get("EventCountSelected"));
			List<org.iomedia.galen.common.ManageticketsAAPI._Event> event = aapi.getEventDetails(email,pass);
			HashMap<Integer, List<Section>> abc = aapi.getAllSectionDetailsWithSorting(event,countOfEventSelected);
			List<List<Section>> allEventsWithSections = new ArrayList<List<Section>>(abc.values());
			ticketDetails = aapi.getTwoTicketIdsFromDifferentEvent(allEventsWithSections);
			if(ticketDetails.size()==0)
				throw new SkipException("Events with Transfer Enabled tickets not found");
			List<String> ticketIds = new ArrayList<>(ticketDetails.keySet());
			ticketNew.clickOnEvents(ticketIds);
	}

	@Then("^Accept ticket from dashboard and verify success message")
	public void acceptTickets() throws Exception {
		ticketNew.acceptTicketFromDashboard();
	}

	@Then("^Accept ticket from popup and verify success message")
	public void acceptInviteFromPopup() throws Exception {
		ticketNew.acceptInviteFromPopUp();
	}

	@Then("^Print Seat Detail (.+) (.+) (.+) (.+) (.+)")
	public void print(String oldmail, String oldpass, String Newmail, String newpass, String TransferTicketID){
		oldmail = (String) base.getGDValue(oldmail);
		oldpass = (String) base.getGDValue(oldpass);
		newpass = (String) base.getGDValue(newpass);
		Newmail = (String) base.getGDValue(Newmail);
		TransferTicketID = (String) base.getGDValue(TransferTicketID);
		ticketNew.getEventName();
		ticketNew.clickInviteDetailsArrow();
		ticketNew.getInvitesSeatDetails();
		System.out.println("new  ::"+Newmail+"/"+newpass);
		System.out.println("old  ::"+oldmail+"/"+oldpass);
		System.out.println("Ticket  ::"+TransferTicketID);
	}

	@Then("^Verify event (.+) and seat details (.+) on the invites pop up")
	public void click_arrow_and_verify_seat_details(String eventName, String[] seatDetails){
		eventName = (String) base.getGDValue(eventName);
		seatDetails = (String[]) base.getGDValue(seatDetails);
		ticketNew.verifyInviteEventAndSeatDetails(eventName, seatDetails);
	}

	@And("User navigates to myevents page by clicking \"VIEW ALL\" link on dashboard")
	public void click_view_all() {
		//ticketNew.clickViewAll();
		utils.navigateTo("/myevents");
		homepage.popUpEventsComingUp();
		
	}

	@Then("^Verify multiselect icon by default state  at event list page$")
	public void verify_multi_select(){
		ticketNew.verifyMSIcon();
	}

	@And("User clicks multiselect icon")
	public void click_ms(){
		ticketNew.clickMSIcon();
	}

	@Then("Checkboxes are triggered next to each event")
	public void cb_triggered(){
		ticketNew.verifyCBs();
	}

	@And("User is able to select all events via select all Checkbox and send button gets enabled")
	public void selectAllCB(){
		ticketNew.selectAllCB();
	}

	@And("User deselects Select All Button")
    public void deselectAll() {
        ticketNew.deselectAll();
    }

    @And("User clicks Send All Button for section to send all tickets")
    public void selectAllSendSection() {
	    Map<String,String> seats = ticketNew.selectAllSendSection(base.Dictionary.get("EventNameSectionTransfer"),base.Dictionary.get("SectionNameSectionTransfer"),base.Dictionary.get("TicketNumberSectionTransfer"));
		base.Dictionary.put("SeatsSectionTransfer", seats.get("seats"));
		ticketNew.selectNext();
    }

	@And("User clicks Send All Button for two section to send all tickets")
	public void selectAllSendSection2() {
		Map<String,String> seats1 = ticketNew.selectAllSendSection(base.Dictionary.get("EventNameSectionTransfer1"),base.Dictionary.get("SectionNameSectionTransfer1"),base.Dictionary.get("TicketNumberSectionTransfer1"));
		base.Dictionary.put("SeatsSectionTransfer1", seats1.get("seats"));
		Map<String,String> seats2=ticketNew.selectAllSendSection(base.Dictionary.get("EventNameSectionTransfer2"),base.Dictionary.get("SectionNameSectionTransfer2"),base.Dictionary.get("TicketNumberSectionTransfer2"));
		base.Dictionary.put("SeatsSectionTransfer2", seats2.get("seats"));
		ticketNew.selectNext();
	}

    @Then("Select a Recipient model appears with proper labels for FirstName, LastName, Email and notes")
    public void verifyReceipentModel() {
		ticketNew.clickAddReceipient();
	    ticketNew.verifyRecipientModel();
	    ticketNew.verifyLabels();
    }

    @And("Already added Recipient is present")
    public void verifyAddedRecipient() {
	    ticketNew.verifyAddedReceipient();
    }



    @And("Send button does not appear until all mandatory fields are filled")
	public void mandatoryFields() {
		Assert.assertTrue(ticketNew.verifyMandatoryFields());
	}

    @And ("^User types following details for recipient and clicks Send (.+) (.+)$")
    public void enterParticularsandSend(String email, String fname) {
		email = (String) base.getGDValue(email);
		String lname=RandomStringUtils.randomAlphabetic(8);
		String notes=RandomStringUtils.randomAlphabetic(20);
	    ticketNew.enterParticulars(fname, lname, email, notes);
        base.Dictionary.put("RecipientName", fname+" "+lname);
        base.Dictionary.put("RecipientLastName",  lname);
        base.Dictionary.put("Notes",notes);
    }

	@And ("^User types new users's mail id for recipient and clicks Send$")
	public void enterParticularsNewuserandSend() {

		java.util.Date today = new java.util.Date();
		Timestamp now = new java.sql.Timestamp(today.getTime());
		String tempNow[] = now.toString().split("\\.");
		final String sStartTime = tempNow[0].replaceAll(":", "").replaceAll(" ", "").replaceAll("-", "");
		String email = "test" + sStartTime + "@example.com";
		String fname = "NAMAutomation";
		String lname=RandomStringUtils.randomAlphabetic(8);
		String notes=RandomStringUtils.randomAlphabetic(20);
		ticketNew.enterParticulars(fname, lname, email, notes);
		base.Dictionary.put("RecipientName", fname+" "+lname);
		base.Dictionary.put("Notes",notes);
		base.Dictionary.put("NEWEMAIL",email);
	}

	@And ("User types following details for recipient and clicks Cancel: (.+) (.+) (.+)")
	public void enterParticularsandCancel(String fname, String lname, String email) {
		ticketNew.enterParticularsCancel(fname, lname, email);
	}

	@And ("User is redirected to my events page")
	public void verifyMyEventsPage() {
		ticketNew.verifyMyEventsPage(base.Dictionary.get("EventNameSectionTransfer"));
	}

    @Then("Ticket Transfer complete Dialouge appears")
    public void transferComplete() {
		ticketNew.verifyTransferComplete();
	}

	/*@And("Ticket Transfer request is completed at backend")
	public void transferCompleteBackend() {
		try {
			ticketNew.verifyTransferCompleteBackend(aapi.getTransferID());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}*/

	@And ("User is able to close subpop of adding new user via cross icon")
	public void closeAddRecipient() {
		ticketNew.verifyCloseReceipient();
		ticketNew.clickAddReceipient();
	}

    @And("Special char (.) is not allowed in FirstName & LastName Section")
    public void verifySpecialChars( String sc) {
        Assert.assertTrue(ticketNew.verifySpecialChars(sc), "Special Chars are being allowed in FirstName/LastName Field");
    }

    @Then("Transferred tickets are shown as Pending with CANCEL TRANSFER link and proper Firstname and LastName of Transferee")
    public void verifyPendingandCancelLink() {
    	if (ticket.checkenableEDP()) {
    		ticket.verifyPendingandCancelLinkEDP(base.Dictionary.get("RecipientName"),base.Dictionary.get("SeatsSectionTransfer"), base.Dictionary.get("EventIdSectionTransfer"), Integer.parseInt(base.Dictionary.get("TicketNumberSectionTransfer")));
    	}else {
    		ticket.verifyPendingandCancelLink(base.Dictionary.get("RecipientName"),base.Dictionary.get("SeatsSectionTransfer"), base.Dictionary.get("EventIdSectionTransfer"), Integer.parseInt(base.Dictionary.get("TicketNumberSectionTransfer")));
    	}
    }


	@Then("Transferred tickets are shown for two events as Pending with CANCEL TRANSFER link and proper Firstname and LastName of Transferee")
	public void verifyPendingandCancelLinkTwoEvents() {
		ticket.verifyPendingandCancelLink(base.Dictionary.get("RecipientName"),base.Dictionary.get("SeatsSectionTransfer1"), base.Dictionary.get("EventIdSectionTransfer1"), Integer.parseInt(base.Dictionary.get("TicketNumberSectionTransfer1")));
		ticket.verifyPendingandCancelLink(base.Dictionary.get("RecipientName"),base.Dictionary.get("SeatsSectionTransfer2"), base.Dictionary.get("EventIdSectionTransfer2"), Integer.parseInt(base.Dictionary.get("TicketNumberSectionTransfer2")));
	}

    @Then("User navigates to Manage tickets page by clicking event name")
    public void navigateManageTickets() {
		utils.navigateTo("/myevents#/"+base.Dictionary.get("EventIdSectionTransfer"));
		ticket.verifyMyEventsPage();
    }

    @Then("User navigates to Manage tickets pages by clicking event names one by one and Transferred tickets are shown for two events as Pending with CANCEL TRANSFER")
    public void navigateManageTicketsTwoEvents() {
    	if (ticket.checkenableEDP()==true) {
    		utils.navigateTo("/myevents#/"+base.Dictionary.get("EventIdSectionTransfer1"));
    		base.getDriver().navigate().refresh();
    		//ticketNew.selectEventByName(base.Dictionary.get("EventNameSectionTransfer1"));
    		ticket.verifyPendingandCancelLinkEDP(base.Dictionary.get("RecipientName"),base.Dictionary.get("SeatsSectionTransfer1"), base.Dictionary.get("EventIdSectionTransfer1"), Integer.parseInt(base.Dictionary.get("TicketNumberSectionTransfer1")));
    		utils.navigateTo("/myevents#/"+base.Dictionary.get("EventIdSectionTransfer2"));
    		base.getDriver().navigate().refresh();
    		//ticketNew.selectEventByName(base.Dictionary.get("EventNameSectionTransfer2"));
    		ticket.verifyPendingandCancelLinkEDP(base.Dictionary.get("RecipientName"),base.Dictionary.get("SeatsSectionTransfer2"), base.Dictionary.get("EventIdSectionTransfer2"), Integer.parseInt(base.Dictionary.get("TicketNumberSectionTransfer2")));
    	}else {
    		utils.navigateTo("/myevents#/"+base.Dictionary.get("EventIdSectionTransfer1"));
    		base.getDriver().navigate().refresh();
    		//ticketNew.selectEventByName(base.Dictionary.get("EventNameSectionTransfer1"));
    		ticket.verifyPendingandCancelLink(base.Dictionary.get("RecipientName"),base.Dictionary.get("SeatsSectionTransfer1"), base.Dictionary.get("EventIdSectionTransfer1"), Integer.parseInt(base.Dictionary.get("TicketNumberSectionTransfer1")));
    		utils.navigateTo("/myevents#/"+base.Dictionary.get("EventIdSectionTransfer2"));
    		base.getDriver().navigate().refresh();
    		//ticketNew.selectEventByName(base.Dictionary.get("EventNameSectionTransfer2"));
    		ticket.verifyPendingandCancelLink(base.Dictionary.get("RecipientName"),base.Dictionary.get("SeatsSectionTransfer2"), base.Dictionary.get("EventIdSectionTransfer2"), Integer.parseInt(base.Dictionary.get("TicketNumberSectionTransfer2")));
    	}
    }

	@And("User clicks Transfer Button")
	public void transferButton() {
		ticketNew.clickTransfer();
	}

	@And("Transferred tickets are shown as Completed with  proper Firstname and LastName of Transferee and  CANCEL TRANSFER link disappears")
	public void verifyCompleted() {
		ticket.verifyCompleted(base.Dictionary.get("RecipientName"), base.Dictionary.get("SeatsSectionTransfer"), Integer.parseInt(base.Dictionary.get("TicketNumberSectionTransfer")));
	}


	@And("User navigates to Manage tickets pages by clicking event name one by one  and Transferred tickets for two events are shown as Completed and  CANCEL TRANSFER link disappears")
	public void verifyCompletedTwoEvents() {
		utils.navigateTo("/myevents#/"+base.Dictionary.get("EventIdSectionTransfer1"));
		//ticketNew.selectEventByName(base.Dictionary.get("EventNameSectionTransfer1"));
		ticket.verifyCompleted(base.Dictionary.get("RecipientName"),base.Dictionary.get("SeatsSectionTransfer1"),Integer.parseInt(base.Dictionary.get("TicketNumberSectionTransfer1")));
		utils.navigateTo("/myevents#/"+base.Dictionary.get("EventIdSectionTransfer2"));
		//ticketNew.selectEventByName(base.Dictionary.get("EventNameSectionTransfer2"));
		ticket.verifyCompleted(base.Dictionary.get("RecipientName"),base.Dictionary.get("SeatsSectionTransfer2"),Integer.parseInt(base.Dictionary.get("TicketNumberSectionTransfer2")));
	}

	@And("Transferred tickets are shown as Active")
	public void verifyActive() {
		if(ticket.checkenableEDP()==true)
		{
			ticket.verifyActiveEDP(base.Dictionary.get("SeatsSectionTransfer"), base.Dictionary.get("EventIdSectionTransfer"));
		}else {
			ticket.verifyActive(base.Dictionary.get("SeatsSectionTransfer"), base.Dictionary.get("EventIdSectionTransfer"));
		}
	}

	@And("^User is navigated to My Events page by clicking Go to Event Button$")
	public void eventsPage() {
		if(ticket.checkenableEDP()) {
			//Go to event Button is not present in EDP Phase 3
		}else {
			ticketNew.verifyEventsPage();
		}
	}

	@And("Reclaim is successful")
	public void reclaim() {
		if(ticket.checkenableEDP()==true) {
			ticket.reclaimTicketsEDP(base.Dictionary.get("RecipientName"), base.Dictionary.get("SeatsSectionTransfer"),Integer.parseInt(base.Dictionary.get("TicketNumberSectionTransfer")));
		}else {
			ticket.reclaimTickets(base.Dictionary.get("RecipientName"), base.Dictionary.get("SeatsSectionTransfer"),Integer.parseInt(base.Dictionary.get("TicketNumberSectionTransfer")));
		}
	}
	
	
	@Then("^User select one events with donate capabilities and Select one Tickets from the list$")
	public void oneEventsDonatecapblities() throws Exception {
	//	String uname = (String) base.getGDValue("tm73812e3f88@example.com");
		//String password = (String) base.getGDValue("123456");
		//HashMap<String, String> availability = aapi.getTransfrableSection(uname, password);
		//System.out.println(availability);
		String[] Tkt = aapi.getDonateDetails(true, "event", false, false);
		String[] ticket1 = Tkt[0].split("\\.");
		base.load("/tickets#/" + ticket1[0]); 
		ticket.clickDonateTicketsEDP(null);
		ticket.selectSeatInPopUp(Tkt[0], ticket1[1].replaceAll("%20", " "), ticket1[2], ticket1[3]);
		ticket.clickDonate();
		ticket.selectCharity();
		ticket.clickContinue();
		ticket.clickContinue();
		String state = api.waitForTicketState(Tkt[0], "donated");
		ticket.logoutNLogin("", "");
	//	Assert.assertEquals(ticket.getTicketStatusEDP().contains("Donated"),true);
		Assert.assertEquals(state, "donated");
		Assert.assertEquals(api.getTicketFlagsEDP(Tkt[0], "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");	
		Assert.assertEquals(ticket.getPopUpEventDetails(), base.Dictionary.get("eventName"));
	}
	
	
	@Then("^User verify Section Row and Seats In UI$")
    public void user_verify_Section_Row_and_Seats_In_UI() throws Exception {

        
        
      
    }


	

	
	
	
	
	
	
	
	
	
}
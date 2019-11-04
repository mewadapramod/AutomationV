package org.iomedia.galen.tests;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.iomedia.framework.Driver;
import org.iomedia.galen.pages.CMS;
import org.iomedia.galen.pages.Hamburger;
import org.iomedia.galen.pages.Homepage;
import org.iomedia.galen.pages.Invoice;
import org.iomedia.galen.pages.ManageTicket;
import org.iomedia.galen.pages.SuperAdminPanel;
import org.iomedia.galen.pages.TicketsNew;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;

import org.iomedia.galen.common.Utils;
import org.iomedia.galen.common.ManageticketsAAPI;
import org.iomedia.galen.common.ManageticketsAPI;
import org.iomedia.galen.common.ManageticketsAPI.Event;

@CucumberOptions(plugin = "json:target/cucumber-report-feature-composite.json", format = "pretty", features = {"features/userJourneys.feature", "features/ticketsNew.feature"}, glue = {"org.iomedia.galen.steps"}, monochrome = true, strict = true)
public class ManageTickets extends Driver {
	
	
	private Homepage homepage;
	private ManageTicket managetickets;
	private ManageticketsAPI manageticketsapi;
	private ManageticketsAAPI manageticketsaapi;
	private Hamburger hamburger;
	private Utils utils;
	private Invoice invoice;
	private CMS cms;
	private TicketsNew ticketsNew;
	boolean CAN_TRANSFER, CAN_RESALE, CAN_RENDER, CAN_RENDER_FILE, CAN_RENDER_BARCODE, CAN_RENDER_PASSBOOK, CAN_DONATE_CHARITY; 
	String host;
	private String driverType;
	org.iomedia.common.BaseUtil base;
	
	@BeforeMethod(alwaysRun=true)
	public void init(){
		homepage = new Homepage(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		managetickets = new ManageTicket(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		manageticketsapi = new ManageticketsAPI(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		manageticketsaapi = new ManageticketsAAPI(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		hamburger = new Hamburger(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		CAN_TRANSFER = CAN_RESALE = CAN_RENDER = CAN_RENDER_FILE = CAN_RENDER_BARCODE = CAN_RENDER_PASSBOOK = CAN_DONATE_CHARITY = true;
		host= Environment.get("TM_HOST").trim();
		invoice = new Invoice(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		ticketsNew = new TicketsNew(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		driverType = driverFactory.getDriverType().get();
	}
	
	
	@Test(groups={"smoke","regression","ticketsFunctional","ViewFunctional","prod"}, priority=1)
	public void verifyDownloadTickets() throws Exception {
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))
			throw new SkipException("Skipped");
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] Tkt = manageticketsapi.getRenderDetails(true, "event", false);
		String[] ticket = Tkt[0].split("\\.");
		load("/tickets#/" + ticket[0]);
		
		homepage.login("", "", null, true);
		if (managetickets.checkenableEDP()) {
			managetickets.clickDownloadTicketsEDP();
			//managetickets.selectEventSectionAvailable(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
			managetickets.selectSeatInPopUpEDP(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
			managetickets.clickContinueEDP();
			Assert.assertTrue(Dictionary.get("eventName").contains(managetickets.getPopUpEventDetailsforMultiple()),"Event Name are getting dispalyed");
			SoftAssert.assertTrue(managetickets.getSection().contains(ticket[1].replaceAll("%20", " ")));
			managetickets.clickPrintEDP();
			managetickets.clickDoneEDP();
			Assert.assertTrue(managetickets.isTicketsListDisplayedEDP(null), "Verify tickets listing page is displayed");
		}else {
			managetickets.clickDownloadTickets();
			managetickets.selectSeatInPopUp(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
			managetickets.clickContinue();
			Assert.assertEquals(managetickets.getPopUpEventDetailsforMultiple(), Dictionary.get("eventName"));
			SoftAssert.assertTrue(managetickets.getSection().contains(ticket[1].replaceAll("%20", " ")));
			managetickets.clickContinue();
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
		}
		manageticketsapi.renderFile(new String[]{Tkt[0]});
	}
	
	@Test(groups={"smoke","regression","ticketsFunctional","sendFunctional","prod","criticalbusiness"}, priority=2)
	public void verifySendTickets() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] Tkt = manageticketsapi.getTransferDetails(true, "event", false, false, false);
		String[] ticket = Tkt[0].split("\\.");
		load("/tickets#/" + ticket[0]);
		
		homepage.login("", "", null, true);
		String state="";
	    String status ="";
	    String fname="Auto";
		String lname="test";

		if (managetickets.checkenableEDP()) {
			managetickets.clickSendTicketsEDP(null);
			managetickets.selectSeatInPopUpEDP(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);	
			Assert.assertTrue(Dictionary.get("eventName").contains(managetickets.getPopUpEventDetailsEDP()), "Event Details are matching");
			managetickets.clickContinueTransferEDP();		
			SoftAssert.assertTrue(managetickets.getSection().contains(ticket[1].replaceAll("%20", " ")));
			managetickets.typeRecipeintFirstName(fname);
			managetickets.typeRecipeintLastName(lname);
			managetickets.typeRecipientEmailAddress("jitendra.roy@ticketmaster.com");	
			managetickets.typeRecipientMessage("Send Ticket");	
			managetickets.clickContinuepopUpTransferEDP();
			Assert.assertEquals(managetickets.getRecipientDetailEDP(), "Recipient Details", "Recipient Details are getting displayed");
		//	String transferName="TestAuto";
     	//	managetickets.typeTransferTag(transferName);
			managetickets.clickTransferDoneEDP();
			Assert.assertTrue(managetickets.isTicketsListDisplayedEDP(null), "Verify tickets listing page is displayed");
			state = manageticketsapi.waitForTicketState(Tkt[0], "pending");
			managetickets.logoutNLogin("", "");
			status = managetickets.getTicketStatusEDP(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt[0]);	
			Assert.assertTrue(status.trim().contains("Transfer Pending"));
			 
		}else{
			Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
			managetickets.clickSendTickets(null);
			managetickets.selectSeatInPopUp(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);			
			managetickets.clickContinue();			
			SoftAssert.assertTrue(managetickets.getSection().contains(ticket[1].replaceAll("%20", " ")));
//			Assert.assertTrue(managetickets.getRow().contains(ticket[2]));
//			Assert.assertTrue(managetickets.getSeat().contains(ticket[3]));
			
			managetickets.typeRecipeintFirstName(fname);
			managetickets.typeRecipeintLastName(lname);
			managetickets.typeRecipientEmailAddress("jitendra.roy@ticketmaster.com");	
			managetickets.typeRecipientMessage("Send Ticket");
			managetickets.clickContinuepopUpTransferEDP();
			Assert.assertEquals(managetickets.getRecipientDetailEDP(), "Recipient Details", "Recipient Details are getting displayed");
//			String claimLink = managetickets.getClaimLink();
//			String transferName="TestAuto";
//			managetickets.typeTransferTag(transferName);
			managetickets.clickTransferDone();
			
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
			state = manageticketsapi.waitForTicketState(Tkt[0], "pending");
			managetickets.logoutNLogin("", "");
			status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt[0]);			
			Assert.assertTrue(status.trim().contains(fname+" "+lname));			
		} 
		
		Assert.assertEquals(state, "pending");
		Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt[0], "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
	}
	

	@Test(groups = {"regression","ticketsFunctional","sendFunctional"}, priority = 3)
	public void verifyMultipleSendTickets() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] Tkt = manageticketsapi.getTransferDetails(true, "event", true, false, false);
		System.out.println("Tkt:" + Tkt);
		String[] ticket = Tkt[0].split("\\.");
		load("/tickets#/" + ticket[0]);
		homepage.login("", "", null, true);
		String state, state2 = "";
	    String status, status2 = "";
	    String fname="Auto";
		String lname="test";

		if (managetickets.checkenableEDP()) {
			managetickets.clickSendTicketsEDP(null);
			String[] ticket2 = Tkt[1].split("\\.");
			managetickets.selectSeatInPopUpEDP(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
			managetickets.selectSeatInPopUpEDP(Tkt[1], ticket2[1].replaceAll("%20", " "), ticket2[2], ticket2[3]);
			Assert.assertTrue(Dictionary.get("eventName").contains(managetickets.getPopUpEventDetailsEDP()), "Event Details are matching");
			managetickets.clickContinueTransferEDP();		
	
			SoftAssert.assertTrue(managetickets.getSection().contains(ticket[1].replaceAll("%20", " ")));
			SoftAssert.assertTrue(managetickets.getSection().contains(ticket2[1].replaceAll("%20", " ")));
			
			managetickets.typeRecipeintFirstName(fname);
			managetickets.typeRecipeintLastName(lname);
			managetickets.typeRecipientEmailAddress("jitendra.roy@ticketmaster.com");	
			managetickets.typeRecipientMessage("Send Ticket");
				
			managetickets.clickContinuepopUpTransferEDP();
			
			Assert.assertEquals(managetickets.getRecipientDetailEDP(), "Recipient Details", "Recipient Details are getting displayed");

			managetickets.clickTransferDoneEDP();
		
			Assert.assertTrue(managetickets.isTicketsListDisplayedEDP(null), "Verify tickets listing page is displayed");
			state = manageticketsapi.waitForTicketState(Tkt[0], "pending");
			state2 = manageticketsapi.waitForTicketState(Tkt[1], "pending");
			managetickets.logoutNLogin("", "");
			status = managetickets.getTicketStatusEDP(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt[0]);
			status2 = managetickets.getTicketStatusEDP(ticket2[0], ticket2[1].replaceAll("%20", " "), ticket2[2], ticket2[3], Tkt[1]);
			Assert.assertTrue(status.trim().contains("Transfer Pending"));		
			Assert.assertTrue(status2.trim().contains("Transfer Pending"));							
		}
		else {
		Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
		managetickets.clickSendTickets(null);
		String[] ticket2 = Tkt[1].split("\\.");
		managetickets.selectSeatInPopUp(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
		managetickets.selectSeatInPopUp(Tkt[1], ticket2[1].replaceAll("%20", " "), ticket2[2], ticket2[3]);
		managetickets.clickContinue();
		SoftAssert.assertTrue(managetickets.getSection().contains(ticket[1].replaceAll("%20", " ")));
		SoftAssert.assertTrue(managetickets.getSection().contains(ticket2[1].replaceAll("%20", " ")));
		
		managetickets.typeRecipeintFirstName(fname);
		managetickets.typeRecipeintLastName(lname);
		managetickets.typeRecipientEmailAddress("jitendra.roy@ticketmaster.com");	
		managetickets.typeRecipientMessage("Send Ticket");
		managetickets.clickContinuepopUpTransferEDP();
		Assert.assertEquals(managetickets.getRecipientDetailEDP(), "Recipient Details", "Recipient Details are getting displayed");
//		Assert.assertTrue(managetickets.getRow().contains(ticket[2]));
//		Assert.assertTrue(managetickets.getSeat().contains(ticket[3]));
//		Assert.assertTrue(managetickets.getRow().contains(ticket2[2]));
//		Assert.assertEquals(managetickets.getMultipleSeat(), ticket2[3]);
//		String claimLink = managetickets.getClaimLink();
//		String transferName="TestAuto";
//		managetickets.typeTransferTag(transferName);
		managetickets.clickTransferDone();
		
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
			state = manageticketsapi.waitForTicketState(Tkt[0], "pending");
			state2 = manageticketsapi.waitForTicketState(Tkt[1], "pending");
			managetickets.logoutNLogin("", "");
			status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt[0]);
			status2 = managetickets.getTicketStatus(ticket2[0], ticket2[1].replaceAll("%20", " "), ticket2[2], ticket2[3], Tkt[1]);
			Assert.assertTrue(status.trim().contains(fname+" "+lname));			
			Assert.assertTrue(status2.trim().contains(fname+" "+lname));
			
		}
		Assert.assertEquals(state, "pending");
		Assert.assertEquals(state2, "pending");
		Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt[0], "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
		Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt[1], "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the second ticket flags");	
	}

	@Test(groups = {"smoke","regression","ticketsFunctional","donateFunctional"}, priority = 4)
	public void verifyDonateTickets() throws Exception {		
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		load("/tickets#/");
		homepage.login("", "", null, true);
				
		if (managetickets.checkenableEDP()) {
			String[] Tkt = manageticketsaapi.getDonateDetailsEDP(true, "event", false, false);
			String[] ticket = Tkt[0].split("\\.");
			load("/tickets#/" + ticket[0]);
			managetickets.clickDonateTicketsEDP(null);
			//managetickets.selectEventSectionAvailable(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
			managetickets.selectSeatInPopUpEDP(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
			managetickets.clickDonate();
			managetickets.selectCharity();
			managetickets.clickContinue();
			Assert.assertTrue(Dictionary.get("eventName").contains(managetickets.getPopUpEventDetailsEDP()));
			managetickets.donatecharityConfirmButton();
			Assert.assertTrue(managetickets.isTicketsListDisplayedEDP(null), "Verify tickets listing page is displayed");
			String state = manageticketsapi.waitForTicketState(Tkt[0], "donated");
			managetickets.logoutNLogin("", "");
			Assert.assertEquals(managetickets.getTicketStatusEDP(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt[0]).contains("Donated"),true);
			Assert.assertEquals(state, "donated");
			Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt[0], "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
		}else {
			String[] Tkt = manageticketsapi.getDonateDetails(true, "event", false, false);
			String[] ticket = Tkt[0].split("\\.");
			load("/tickets#/" + ticket[0]);
			managetickets.clickDonateTickets(null);	
			managetickets.selectSeatInPopUp(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
			managetickets.clickContinue();
			managetickets.selectCharity();
			managetickets.clickContinue();
			Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
			managetickets.clickContinue();
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
			String state = manageticketsapi.waitForTicketState(Tkt[0], "donated");
			managetickets.logoutNLogin("", "");
			Assert.assertEquals(managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt[0]).contains("Donated"),true);
			Assert.assertEquals(state, "donated");
			Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt[0], "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
		}
	}

	@Test(groups = {"regression","ticketsFunctional","donateFunctional"}, priority = 5)
	public void verifyMultipleDonateTickets() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		load("/tickets#/");
		homepage.login("", "", null, true);

		
		if (managetickets.checkenableEDP()) {
			String[] Tkt = manageticketsaapi.getDonateDetailsEDP(true, "event", false, false);
			String[] ticket = Tkt[0].split("\\.");
			load("/tickets#/" + ticket[0]);
			managetickets.clickDonateTicketsEDP(null);
			String[] ticket2 = Tkt[1].split("\\.");
			//managetickets.selectEventSectionAvailable(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
			managetickets.selectSeatInPopUpEDP(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
			managetickets.selectSeatInPopUpEDP(Tkt[1], ticket2[1].replaceAll("%20", " "), ticket2[2], ticket2[3]);
			managetickets.clickDonate();
			managetickets.selectCharity();
			managetickets.clickContinue();
			Assert.assertTrue(Dictionary.get("eventName").contains(managetickets.getPopUpEventDetailsEDP()));
			managetickets.clickContinue();
			Assert.assertTrue(managetickets.isTicketsListDisplayedEDP(null), "Verify tickets listing page is displayed");
			String state = manageticketsapi.waitForTicketState(Tkt[0], "donated");
			String state2 = manageticketsapi.waitForTicketState(Tkt[1], "donated");
			managetickets.logoutNLogin("", "");
			managetickets.selectEventSectionAvailable(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
			//Assert.assertEquals(managetickets.getTicketStatusEDP(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt[0]).contains("Donated"),true);
			//Assert.assertEquals(managetickets.getTicketStatusEDP(ticket2[0], ticket2[1].replaceAll("%20", " "), ticket2[2], ticket2[3], Tkt[1]).contains("Donated"), true);
			Assert.assertEquals(state, "donated");
			Assert.assertEquals(state2, "donated");
			Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt[0], "", ""), new Boolean[] { false, false, false, false, false, false, false }, "Verify the ticket flags");
			Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt[1], "", ""), new Boolean[] { false, false, false, false, false, false, false }, "Verify the second  ticket flags");

		}else {
			String[] Tkt = manageticketsapi.getDonateDetails(true, "event", false, false);
			String[] ticket = Tkt[0].split("\\.");
			load("/tickets#/" + ticket[0]);
			managetickets.clickDonateTickets(null);
			String[] ticket2 = Tkt[1].split("\\.");
			managetickets.selectSeatInPopUp(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
			managetickets.selectSeatInPopUp(Tkt[1], ticket2[1].replaceAll("%20", " "), ticket2[2], ticket2[3]);
			managetickets.clickContinue();
			managetickets.selectCharity();
			managetickets.clickContinue();
			Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
			managetickets.clickContinue();
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
			String state = manageticketsapi.waitForTicketState(Tkt[0], "donated");
			String state2 = manageticketsapi.waitForTicketState(Tkt[1], "donated");
			managetickets.logoutNLogin("", "");
			Assert.assertEquals(managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt[0]).contains("Donated"),true);
			Assert.assertEquals(managetickets.getTicketStatus(ticket2[0], ticket2[1].replaceAll("%20", " "), ticket2[2], ticket2[3], Tkt[1]).contains("Donated"), true);
			Assert.assertEquals(state, "donated");
			Assert.assertEquals(state2, "donated");
			Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt[0], "", ""), new Boolean[] { false, false, false, false, false, false, false }, "Verify the ticket flags");
			Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt[1], "", ""), new Boolean[] { false, false, false, false, false, false, false }, "Verify the second  ticket flags");
		}
	}

	@Test(groups = {"smoke","regression","ticketsFunctional","sendFunctional","prod","criticalbusiness"}, priority = 6)
	public void verifyReclaimTickets() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		
		String[] Tkt = manageticketsapi.getTransferDetails(true, "event", false, false, false);
		String[] ticket = Tkt[0].split("\\.");
		
		Boolean[] flags = manageticketsapi.getTicketFlags(Tkt[0], "", "");
		CAN_TRANSFER = flags[0];
		CAN_RESALE = flags[1];
		CAN_RENDER = flags[2];
		CAN_RENDER_FILE = flags[3];
		CAN_RENDER_BARCODE = flags[4];
		CAN_RENDER_PASSBOOK = flags[5];
		CAN_DONATE_CHARITY = flags[6];
		
		manageticketsapi.sendTickets(Integer.valueOf(ticket[0]), new String[]{Tkt[0]});
		try {
			load("/tickets#/"+ticket[0]);
			homepage.login("", "", null, true);
			managetickets.clickManageTickets(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt[0]);
			managetickets.clickReclaim();
			Assert.assertEquals(managetickets.getSuccess(), "Success!");
		} catch(Exception ex) {
			String[] transferIds = manageticketsapi.getTransferID(new String[]{Tkt[0]});
			if(transferIds != null && transferIds.length > 0) {
				String transferId = transferIds[0];
				manageticketsapi.deleteTransfer(transferId);
			}
			throw ex;
		}
			
		managetickets.clickDone();
		Assert.assertEquals(managetickets.getTicketStatus(ticket[0], ticket[1], ticket[2], ticket[3], Tkt[0]), "No Status");
		SoftAssert.assertEquals(manageticketsapi.getTicketFlags(Tkt[0], "", ""), new Boolean[] {CAN_TRANSFER, CAN_RESALE, CAN_RENDER, CAN_RENDER_FILE, CAN_RENDER_BARCODE, CAN_RENDER_PASSBOOK, CAN_DONATE_CHARITY}, "Verify the ticket flags");
	}

	@Test(groups = {"smoke","regression","ticketsFunctional","prod"}, priority = 7)
	public void verifySortingOfManageTickets() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		
		HashMap<Integer, ManageticketsAPI.Event> events = manageticketsapi.getEventIdWithTktsDetails();
		Assert.assertNotNull(events);

		int eventId = -1;
		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))
			eventId = manageticketsapi.getEventIdWithMinTktsHavingRenderBarcode(events);
		else
			eventId = manageticketsapi.getEventIdWithMinTkts(events);

		Assert.assertNotEquals(eventId, -1);
		load("/tickets#/" + eventId);
		
		homepage.login("", "", null, true);
		
		if (managetickets.checkenableEDP()==true) {
			
			Assert.assertTrue(managetickets.isTicketsListDisplayedEDP(null), "Verify tickets listing page is displayed");
			List<List<String>> expectedsections = manageticketsapi.getTickets(eventId, events);
			if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
				List<List<String>> actualsections = managetickets.getMobileTicketsDetail();
				Assert.assertEquals(actualsections, expectedsections, "Verify manage tickets are sorted");
			} else {
				List<List<String>> actualsections = managetickets.getTicketsDetailEDP();
				Assert.assertEquals(actualsections, expectedsections, "Verify manage tickets are sorted");
			}

		} else {
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
			List<List<String>> expectedsections = manageticketsapi.getTickets(eventId, events);
			if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
				List<List<String>> actualsections = managetickets.getMobileTicketsDetail();
				Assert.assertEquals(actualsections, expectedsections, "Verify manage tickets are sorted");
			} else {
				List<List<String>> actualsections = managetickets.getTicketsDetail();
				Assert.assertEquals(actualsections, expectedsections, "Verify manage tickets are sorted");
			}
		}
	}

	@Test(groups = {"smoke","regression","ticketsFunctional","prod"}, priority = 8, enabled = false)
	public void verifyEventsSummary() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		HashMap<Integer, ManageticketsAPI.Event> events = manageticketsapi.getEventIdWithTktsDetails();
		Assert.assertNotNull(events);
		load("/tickets");
		
		homepage.login("", "", null, true);
		Assert.assertTrue(managetickets.isManageTicketsListDisplayed(), "Verify manage tickets list is displayed");
		HashMap<Integer, String> levents = managetickets.getListOfEvents();
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
	}

	@Test(groups = {"smoke","regression","ticketsFunctional","prod"}, priority = 9)
	public void verifyTicketsCountBasedOnStatus() throws Exception {
		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))
			throw new SkipException("Skipped");

		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		HashMap<Integer,ManageticketsAPI.Event> events = manageticketsapi.getEventIdWithTktsDetails();
		Assert.assertNotNull(events);

		int eventId = -1;
		eventId = manageticketsapi.getEventIdWithMaxTkts(events);

		Assert.assertNotEquals(eventId, -1);
		load("/tickets#/" + eventId);
		
		homepage.login("", "", null, true);

		if (managetickets.checkenableEDP()) {
			int ticketsCountEDP = managetickets.getTotalTicketsCountEDP();	
			Assert.assertEquals(managetickets.getTotalTicketsCountTextEDP(), ticketsCountEDP > 1 ? ticketsCountEDP : ticketsCountEDP + " Tickets", "Verify total tickets count");	
		} else {
			int ticketsCount = managetickets.getTotalTicketsCount();
			Assert.assertEquals(managetickets.getTotalTicketsCountText(), ticketsCount > 1 ? ticketsCount + " Tickets" : ticketsCount + " Tickets", "Verify total tickets count");
		}
		getDriver().manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		int sentTicketsCount = managetickets.getSentTicketsCount();
		int listedTicketsCount = managetickets.getListedTicketsCount();
		int claimedTicketsCount = managetickets.getClaimedTicketsCount();
		int donatedTicketsCount = managetickets.getDonatedTicketsCount();
		int donateTicketsCountEDP = managetickets.getDonatedTicketsCountEDP();

		if(sentTicketsCount > 0)
			Assert.assertEquals(managetickets.getSentTicketsCountText(), sentTicketsCount + " Sent", "Verify sent tickets count");
		if(listedTicketsCount > 0)
			Assert.assertEquals(managetickets.getListedTicketsCountText(), listedTicketsCount + " Listed", "Verify listed tickets count");
		if(claimedTicketsCount > 0)
			Assert.assertEquals(managetickets.getClaimedTicketsCountText(), claimedTicketsCount + " Claimed", "Verify claimed tickets count");
		if(donatedTicketsCount > 0)
			Assert.assertEquals(managetickets.getDonatedTicketsCountText(), donatedTicketsCount + " Donated", "Verify donated tickets count");
		if(donateTicketsCountEDP > 0)
			Assert.assertEquals(managetickets.getDonatedTicketsCountTextEDP(), donateTicketsCountEDP + " Donated", "Verify donated tickets count");
		
		getDriver().manage().timeouts().implicitlyWait(Long.valueOf(Environment.get("implicitWait")), TimeUnit.MILLISECONDS);
		
	}

	@Test(groups = {"smoke","regression","ticketsFunctional","sendFunctional","prod"}, priority = 10)
	public void sendTicketWithParkingPass() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] tickets = manageticketsapi.getTransferDetails(true, "event", false, false, true);
		String Tkt = tickets[0];
		String[] ticket = Tkt.split("\\.");
		load("/tickets#/" + ticket[0]);
		
		homepage.login("", "", null, true);
		managetickets.clickSendTickets(null);
		managetickets.selectSeatInPopUp(Tkt, ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
		managetickets.clickBundleParking();
		//managetickets.clickContinue();
		managetickets.selectParkingSlots(1);
		managetickets.clickContinue();
		Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
		SoftAssert.assertTrue(managetickets.getSection().contains(ticket[1].replaceAll("%20", " ")));
//		Assert.assertTrue(managetickets.getRow().contains(ticket[2]));
//		Assert.assertTrue(managetickets.getSeat().contains(ticket[3]));
		managetickets.clickContinue();
		String eventclaimLink = "", parkingclaimLink= "";
		try {
			eventclaimLink = managetickets.getEventClaimLink();
			parkingclaimLink = managetickets.getParkingClaimLink();
//			managetickets.clickContinue();
			String transferName="TestAuto";
			managetickets.typeTransferTag(transferName);
			managetickets.clickTransferDone();
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
			String state = manageticketsapi.waitForTicketState(Tkt, "pending");
	
			managetickets.logoutNLogin("", "");
			String status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			Assert.assertTrue(status.trim().contains(transferName));
			Assert.assertEquals(state, "pending");
			
			Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt, "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
			
			String transferId = parkingclaimLink.trim().split("\\?")[1];
			Tkt = manageticketsapi.getTransferTicketIDs(transferId)[0];
			ticket = Tkt.split("\\.");
			utils.navigateTo("/tickets#/" + ticket[0]);
			status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			Assert.assertTrue(status.trim().contains(transferName));
			Assert.assertEquals(manageticketsapi.waitForTicketState(Tkt, "pending"), "pending");
			
			Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt, "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
		} finally {
			if(!eventclaimLink.trim().equalsIgnoreCase("")) {
				String transferId = eventclaimLink.trim().split("\\?")[1];
				manageticketsapi.deleteTransfer(transferId);
			}
			if(!parkingclaimLink.trim().equalsIgnoreCase("")) {
				String transferId = parkingclaimLink.trim().split("\\?")[1];
				manageticketsapi.deleteTransfer(transferId);
			}
		}
	}
	
	@Test(groups={"smoke","regression","ticketsFunctional","sellFunctional","prod","criticalbusiness"}, priority=11)
	public void verifySellTicketsSellerProfile() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		
		String[] tickets = manageticketsapi.getResaleDetails(true, "event", false, false);
		String Tkt = tickets[0];
		String[] ticket=Tkt.split("\\.");
		load("/tickets#/"+ticket[0]);
		
		homepage.login("", "", null, true);
		managetickets.clickSellTickets(null);
		managetickets.selectSeatInPopUp(Tkt, ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
		managetickets.clickContinue();
		managetickets.typeEarningPrice(new String[]{Dictionary.get("EarningPrice")});
		managetickets.clickContinue();
		managetickets.selectSellerCredit();
		if(managetickets.isContinueEnabled()) {
			//Do Nothing
		} else {
			
			managetickets.clickEditSellerProfile();
			managetickets.inputSellerProfile(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Add1"), Dictionary.get("Add2"), Dictionary.get("Country"), Dictionary.get("City"), Dictionary.get("State"), Dictionary.get("ZipCode"), Dictionary.get("MobileNum"), Dictionary.get("PhoneNum"));
			managetickets.clickContinue();
			SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("FirstName")), "Seller profile");
			SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("LastName")));
			SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add1")));
			SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add2")));
			SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("City")));
			SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("ZipCode")));
		}
		
		managetickets.clickContinue();
		Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
		BaseUtil.sync(2000L);
		managetickets.clickContinue();
		try {
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
			String state = manageticketsapi.waitForTicketState(Tkt, "pending");
			managetickets.logoutNLogin("", "");
			String status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			Assert.assertTrue(status.contains("Listed"), status);
			Assert.assertEquals(state, "pending");
			Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt, "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
		} finally {
			Integer[] postings = manageticketsapi.getPostingId(new String[]{Tkt});
			if(postings.length > 0) {
				int postingId = postings[0];
				manageticketsapi.deletePosting(postingId);
			}
		}
	}
	
	@Test(groups={"smoke","regression","ticketsFunctional","sellFunctional","prod"}, priority=12)
	public void verifySellTicketsBankAccount() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		
		String[] tickets = manageticketsapi.getResaleDetails(true, "event", false, false);
		String Tkt = tickets[0];
		String[] ticket=Tkt.split("\\.");
		load("/tickets#/"+ticket[0]);
		
		homepage.login("", "", null, true);
		managetickets.clickSellTickets(null);
		managetickets.selectSeatInPopUp(Tkt, ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
		managetickets.clickContinue();
		managetickets.typeEarningPrice(new String[]{Dictionary.get("EarningPrice")});
		managetickets.clickContinue();
		managetickets.selectBankAccount();
		if(managetickets.isContinueEnabled()) {
			//Do Nothing
		} else {
			if(managetickets.getDepositAccountValue().trim().equalsIgnoreCase("Check by mail")) {
				managetickets.clickEditSellerProfile();
				managetickets.inputSellerProfile(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Add1"), Dictionary.get("Add2"), Dictionary.get("Country"), Dictionary.get("City"), Dictionary.get("State"), Dictionary.get("ZipCode"), Dictionary.get("MobileNum"), Dictionary.get("PhoneNum"));
				managetickets.clickContinue();
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("FirstName")), "Seller Profile");
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("LastName")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add1")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add2")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("City")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("ZipCode")));
			} else {
				managetickets.clickEditBankDetail();
				managetickets.inputBankDetails(Dictionary.get("AccType"), Dictionary.get("RoutingNum"), Dictionary.get("AccountNum"), Dictionary.get("ConfirmAcc"));
				managetickets.clickContinue();
				SoftAssert.assertEquals(managetickets.getBankDetails(Dictionary.get("AccountNum").trim().substring(Dictionary.get("AccountNum").trim().length() - 4)), utils.toCamelCase(Dictionary.get("AccType").trim()) + " account ending in " + Dictionary.get("AccountNum").trim().substring(Dictionary.get("AccountNum").trim().length() - 4), "Verify bank details got updated");
			}
		}	
		
		managetickets.clickContinue();
		Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
		managetickets.clickContinue();
		try {
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
			String state = manageticketsapi.waitForTicketState(Tkt, "pending");
			managetickets.logoutNLogin("", "");
			String status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			Assert.assertTrue(status.contains("Listed"), status);
			Assert.assertEquals(state, "pending");
			Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt, "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
		} finally {
			Integer[] postings = manageticketsapi.getPostingId(new String[]{Tkt});
			if(postings.length > 0) {
				int postingId = postings[0];
				manageticketsapi.deletePosting(postingId);
			}
		}
	}
	
	@Test(groups={"regression","ticketsFunctional","sellFunctional","prod","criticalbusiness"}, priority=13)
	public void verifySellTicketsEditBankAccount() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] tickets = manageticketsapi.getResaleDetails(true, "event", false, false);
		String Tkt = tickets[0];
		String[] ticket=Tkt.split("\\.");
		
//		Boolean[] flags = manageticketsapi.getTicketFlags(Tkt, "", "");
//		CAN_TRANSFER = flags[0];
//		CAN_RESALE = flags[1];
//		CAN_RENDER = flags[2];
//		CAN_RENDER_FILE = flags[3];
//		CAN_RENDER_BARCODE = flags[4];
//		CAN_RENDER_PASSBOOK = flags[5];
//		CAN_DONATE_CHARITY = flags[6];
		
		manageticketsapi.selltickets(new String[]{Tkt});

		try {
			load("/tickets#/"+ticket[0]);
			
			homepage.login("", "", null, true);
			managetickets.clickEditCancelTicket(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			managetickets.clickEditPosting();
			managetickets.typeEarningPrice(new String[]{Dictionary.get("EarningPrice")});
			managetickets.clickContinue();
			managetickets.selectBankAccount();
			
			if(managetickets.getDepositAccountValue().trim().equalsIgnoreCase("Check by mail")) {
				managetickets.clickEditSellerProfile();
				managetickets.inputSellerProfile(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Add1"), Dictionary.get("Add2"), Dictionary.get("Country"), Dictionary.get("City"), Dictionary.get("State"), Dictionary.get("ZipCode"), Dictionary.get("MobileNum"), Dictionary.get("PhoneNum"));
				managetickets.clickContinue();
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("FirstName")), "Seller profile");
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("LastName")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add1")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add2")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("City")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("ZipCode")));
			} else {
				managetickets.clickEditBankDetail();
				managetickets.inputBankDetails(Dictionary.get("AccType"), Dictionary.get("RoutingNum"), Dictionary.get("AccountNum"), Dictionary.get("ConfirmAcc"));
				managetickets.clickContinue();
				SoftAssert.assertEquals(managetickets.getBankDetails(Dictionary.get("AccountNum").trim().substring(Dictionary.get("AccountNum").trim().length() - 4)), utils.toCamelCase(Dictionary.get("AccType").trim()) + " account ending in " + Dictionary.get("AccountNum").trim().substring(Dictionary.get("AccountNum").trim().length() - 4), "Verify bank details got updated");
			}
			
			managetickets.clickContinue();
			Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
			BaseUtil.sync(2000L);
			managetickets.clickContinue();
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
			String state = manageticketsapi.waitForTicketState(Tkt, "pending");
			managetickets.logoutNLogin("", "");
			String status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			Assert.assertTrue(status.contains("Listed"), status);
			Assert.assertEquals(state, "pending");
			managetickets.clickEditCancelTicket(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			managetickets.clickEditPosting();
			Assert.assertEquals(managetickets.getEarningPrice(), Dictionary.get("EarningPrice"));
	//		Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt, "", ""), new Boolean[] {!CAN_TRANSFER, !CAN_RESALE, !CAN_RENDER, !CAN_RENDER_FILE, !CAN_RENDER_BARCODE, !CAN_RENDER_PASSBOOK, !CAN_DONATE_CHARITY}, "Verify the ticket flags");
		} finally {
			Integer[] postings = manageticketsapi.getPostingId(new String[]{Tkt});
			if(postings.length > 0) {
				int postingId = postings[0];
				manageticketsapi.deletePosting(postingId);
			}
		}
	}	
	
	@Test(groups={"regression","ticketsFunctional","sellFunctional","prod","criticalbusiness"}, priority=14)
	public void verifySellTicketsEditSellerCredit() throws Exception{
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		
		String[] Tkts = manageticketsapi.getResaleDetails(true, "event", false, false);
		String Tkt = Tkts[0];
		String[] ticket=Tkt.split("\\.");
		
//		Boolean[] flags = manageticketsapi.getTicketFlags(Tkt, "", "");
//		CAN_TRANSFER = flags[0];
//		CAN_RESALE = flags[1];
//		CAN_RENDER = flags[2];
//		CAN_RENDER_FILE = flags[3];
//		CAN_RENDER_BARCODE = flags[4];
//		CAN_RENDER_PASSBOOK = flags[5];
//		CAN_DONATE_CHARITY = flags[6];
		
		manageticketsapi.selltickets(new String[]{Tkt});
		try {
			load("/tickets#/"+ticket[0]);
			
			homepage.login("", "", null, true);
			managetickets.clickEditCancelTicket(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			managetickets.clickEditPosting();
			managetickets.typeEarningPrice(new String[]{Dictionary.get("EarningPrice")});
			managetickets.clickContinue();
			managetickets.selectSellerCredit();
			managetickets.clickEditSellerProfile();
			managetickets.inputSellerProfile(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Add1"), Dictionary.get("Add2"), Dictionary.get("Country"), Dictionary.get("City"), Dictionary.get("State"), Dictionary.get("ZipCode"), Dictionary.get("MobileNum"), Dictionary.get("PhoneNum"));
			managetickets.clickContinue();
			SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("FirstName")), "Seller profile");
			SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("LastName")));
			SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add1")));
			SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add2")));
			SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("City")));
			SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("ZipCode")));
			managetickets.clickContinue();
			Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
			BaseUtil.sync(2000L);
			managetickets.clickContinue();
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
			String state = manageticketsapi.waitForTicketState(Tkt, "pending");
			managetickets.logoutNLogin("", "");
			String status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			Assert.assertTrue(status.contains("Listed"), status);
			Assert.assertEquals(state, "pending");
			managetickets.clickEditCancelTicket(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			managetickets.clickEditPosting();
			Assert.assertEquals(managetickets.getEarningPrice(), Dictionary.get("EarningPrice"));
	//		Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt, "", ""), new Boolean[] {CAN_TRANSFER, CAN_RESALE, CAN_RENDER, CAN_RENDER_FILE, CAN_RENDER_BARCODE, CAN_RENDER_PASSBOOK, CAN_DONATE_CHARITY}, "Verify the ticket flags");
		} finally {
			Integer[] postings = manageticketsapi.getPostingId(new String[]{Tkt});
			if(postings.length > 0) {
				int postingId = postings[0];
				manageticketsapi.deletePosting(postingId);
			}
		}
	}	
	
	@Test(groups={"smoke","regression","ticketsFunctional","sellFunctional","prod","criticalbusiness"}, priority=15)
	public void verifyCancelPosting() throws Exception{
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		
		String[] Tkts = manageticketsapi.getResaleDetails(true, "event", false, false);
		String Tkt = Tkts[0];
		String[] ticket=Tkt.split("\\.");
		
		Boolean[] flags = manageticketsapi.getTicketFlags(Tkt, "", "");
		CAN_TRANSFER = flags[0];
		CAN_RESALE = flags[1];
		CAN_RENDER = flags[2];
		CAN_RENDER_FILE = flags[3];
		CAN_RENDER_BARCODE = flags[4];
		CAN_RENDER_PASSBOOK = flags[5];
		CAN_DONATE_CHARITY = flags[6];
		
		manageticketsapi.selltickets(new String[]{Tkt});
		
		try {
			load("/tickets#/"+ticket[0]);
			homepage.login("", "", null, true);
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
			managetickets.clickEditCancelTicket(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			managetickets.clickCancelPosting();
			Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
			managetickets.clickConfirm();
			Assert.assertEquals(managetickets.getSuccess(), "Success!");
		} catch(Exception ex) {
			Integer[] postings = manageticketsapi.getPostingId(new String[]{Tkt});
			if(postings.length > 0) {
				int postingId = postings[0];
				manageticketsapi.deletePosting(postingId);
			}
			throw ex;
		}
		
		managetickets.clickDone();
		Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
		String state = manageticketsapi.waitForPendingActionRemoved(Tkt);
		Assert.assertNull(state, "Verify pending action got removed");
		managetickets.logoutNLogin("", "");
		String status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
		Assert.assertEquals(status, "No Status");
		SoftAssert.assertEquals(manageticketsapi.getTicketFlags(Tkt, "", ""), new Boolean[] {CAN_TRANSFER, CAN_RESALE, CAN_RENDER, CAN_RENDER_FILE, CAN_RENDER_BARCODE, CAN_RENDER_PASSBOOK, CAN_DONATE_CHARITY}, "Verify the ticket flags");
	}

	@Test(groups={"smoke","regression","ticketsFunctional","viewFunctional","prod","criticalbusiness"}, priority=16)
	public void downloadBarcode() throws Exception {
		if(!utils.getManageTicketConfiguration("mobile_enabled"))
			  throw new SkipException("Mobile_Enabled is not enabled in CMS");
		if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("Skipped");
		if(Environment.get("deviceType").trim().equalsIgnoreCase("tablet")) {
			throw new SkipException("Skipped");
		}
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] Tkts = manageticketsapi.getBarcodeRenderMobileEnabledTickets(true, "event", false, false, false, false);
		String Tkt = Tkts[0];
		String[] ticket=Tkt.split("\\.");
		load("/tickets#/"+ticket[0]);
		homepage.login("", "", null, true);
		
		if (managetickets.checkenableEDP()) {
			Assert.assertTrue(Dictionary.get("eventName").contains(managetickets.getMobileScanEventDetails().split(",")[0]), "Event Name on UI is Same As Fetched from API");
			managetickets.clickScanBarcodeEDP(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			
			//Assert.assertEquals(managetickets.getMobileScanEventDetails(), Dictionary.get("eventName"));
			Assert.assertEquals(managetickets.getMobileScanSectionName(), ticket[1].replaceAll("%20", " "));
			Assert.assertEquals(managetickets.getMobileScanRowName(),ticket[2]);
			Assert.assertEquals(managetickets.getMobileScanSeatName(), ticket[3]);
			//SoftAssert.assertEquals(managetickets.getMobileScanGateNumber(), "Enter Gate: " + Dictionary.get("entryGate").trim());
			manageticketsapi.renderBarcode(Tkt);
			Assert.assertTrue(managetickets.isBarcodeDisplayed(), "Verify bar code is displayed");
		}
		else {
		
		managetickets.clickScanBarcode(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
		Assert.assertEquals(managetickets.getMobileScanEventDetails(), Dictionary.get("eventName"));
		Assert.assertEquals(managetickets.getMobileScanSectionName(), ticket[1].replaceAll("%20", " "));
		Assert.assertEquals(managetickets.getMobileScanRowName(),ticket[2]);
		Assert.assertEquals(managetickets.getMobileScanSeatName(), ticket[3]);
		SoftAssert.assertEquals(managetickets.getMobileScanGateNumber(), "Enter Gate: " + Dictionary.get("entryGate").trim());
		manageticketsapi.renderBarcode(Tkt);
		Assert.assertTrue(managetickets.isBarcodeDisplayed(), "Verify bar code is displayed");
		}
	}
	
	@Test(groups={"regression","ticketsFunctional","sellFunctional"}, priority=17)
	public void verifySellMultipleTickets() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] Tkts = manageticketsapi.getResaleDetails(true, "event", true, false);
		List<String> Tkt = Arrays.asList(Tkts);
		String[] ticket1=Tkt.get(0).split("\\.");
		String[] ticket2=Tkt.get(1).split("\\.");
		load("/tickets#/"+ticket1[0]);
		homepage.login("", "", null, true);
		managetickets.clickSellTickets(null);
		managetickets.selectSeatInPopUp(Tkt.get(0), ticket1[1].replaceAll("%20", " "), ticket1[2], ticket1[3]);
		managetickets.selectSeatInPopUp(Tkt.get(1), ticket2[1].replaceAll("%20", " "), ticket2[2], ticket2[3]);
		managetickets.clickContinue();
		managetickets.typeEarningPrice(new String[]{Dictionary.get("EarningPrice"), Dictionary.get("EarningPrice2")});
		managetickets.clickContinue();
		managetickets.selectBankAccount();
		if(managetickets.isContinueEnabled()) {
			//Do Nothing
		} else {
			if(managetickets.getDepositAccountValue().trim().equalsIgnoreCase("Check by mail")) {
				managetickets.clickEditSellerProfile();
				managetickets.inputSellerProfile(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Add1"), Dictionary.get("Add2"), Dictionary.get("Country"), Dictionary.get("City"), Dictionary.get("State"), Dictionary.get("ZipCode"), Dictionary.get("MobileNum"), Dictionary.get("PhoneNum"));
				managetickets.clickContinue();
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("FirstName")), "Seller Profile");
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("LastName")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add1")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add2")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("City")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("ZipCode")));
			} else {
				managetickets.clickEditBankDetail();
				managetickets.inputBankDetails(Dictionary.get("AccType"), Dictionary.get("RoutingNum"), Dictionary.get("AccountNum"), Dictionary.get("ConfirmAcc"));
				managetickets.clickContinue();
				SoftAssert.assertEquals(managetickets.getBankDetails(Dictionary.get("AccountNum").trim().substring(Dictionary.get("AccountNum").trim().length() - 4)), utils.toCamelCase(Dictionary.get("AccType").trim()) + " account ending in " + Dictionary.get("AccountNum").trim().substring(Dictionary.get("AccountNum").trim().length() - 4), "Verify bank details got updated");
			}
		}	
		managetickets.clickContinue();
		Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
		managetickets.clickContinue();
		try {
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
			String state1 = manageticketsapi.waitForTicketState(Tkt.get(0), "pending");
			String state2=	manageticketsapi.waitForTicketState(Tkt.get(1), "pending");
			managetickets.logoutNLogin("", "");
			String status1 = managetickets.getTicketStatus(ticket1[0], ticket1[1].replaceAll("%20", " "), ticket1[2], ticket1[3], Tkt.get(0));
			String status2 = managetickets.getTicketStatus(ticket2[0], ticket2[1].replaceAll("%20", " "), ticket2[2], ticket2[3], Tkt.get(1));
			Assert.assertTrue(status1.contains("Listed"));
			Assert.assertEquals(state1, "pending");
			Assert.assertTrue(status2.contains("Listed"));
			Assert.assertEquals(state2, "pending");
			Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt.get(0), "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
			Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt.get(1), "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
		} finally {
			Integer[] postings = manageticketsapi.getPostingId(new String[]{Tkt.get(0)});
			if(postings.length > 0) {
				int postingId = postings[0];
				manageticketsapi.deletePosting(postingId);
			}
			postings = manageticketsapi.getPostingId(new String[]{Tkt.get(1)});
			if(postings.length > 0) {
				int postingId = postings[0];
				manageticketsapi.deletePosting(postingId);
			}
		}
	}
	
	@Test(groups={"regression","ticketsFunctional","sellFunctional"}, priority=18)
	public void verifyEditMultipleSellTickets() throws Exception{
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] Tkts = manageticketsapi.getResaleDetails(true, "event", true, false);
		List<String> Tkt = Arrays.asList(Tkts);
		Tkt = manageticketsapi.selltickets(new String[]{Tkt.get(0), Tkt.get(1)});
		List<String> states = new ArrayList<String>();
		try {
			for(int i = 0; i < Tkt.size(); i++) {
				String[] ticket=Tkt.get(i).split("\\.");
				if(i == 0) {
					load("/tickets#/"+ticket[0]);
					homepage.login("", "", null, true);
				} else {
					utils.navigateTo("/tickets#/"+ticket[0]);
				}
				managetickets.clickEditCancelTicket(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt.get(i));
				managetickets.clickEditPosting();
				managetickets.typeEarningPrice(new String[]{Dictionary.get("EarningPrice")});
				managetickets.clickContinue();
				managetickets.selectSellerCredit();
				managetickets.clickEditSellerProfile();
				managetickets.inputSellerProfile(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Add1"), Dictionary.get("Add2"), Dictionary.get("Country"), Dictionary.get("City"), Dictionary.get("State"), Dictionary.get("ZipCode"), Dictionary.get("MobileNum"), Dictionary.get("PhoneNum"));
				managetickets.clickContinue();
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("FirstName")), "Seller Profile");
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("LastName")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add1")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add2")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("City")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("ZipCode")));
				managetickets.clickContinue();
				Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
				BaseUtil.sync(2000L);
				managetickets.clickContinue();
				Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
				String state = manageticketsapi.waitForTicketState(Tkt.get(i), "pending");
				states.add(state);
			}
			
			String[] state = states.toArray(new String[states.size()]);
			managetickets.logoutNLogin("", "");
			int i = 0;
			String[] ticket=Tkt.get(i).split("\\.");
			String status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt.get(i));
			Assert.assertTrue(status.contains("Listed"), status);
			Assert.assertEquals(state[i], "pending");
			managetickets.clickEditCancelTicket(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt.get(i));
			managetickets.clickEditPosting();
			Assert.assertEquals(managetickets.getEarningPrice(), Dictionary.get("EarningPrice"));
			Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt.get(i), "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
		} finally {
			for(int i = 0; i < Tkt.size(); i++) {
				Integer[] postings = manageticketsapi.getPostingId(new String[]{Tkt.get(i)});
				if(postings.length > 0) {
					int postingId = postings[0];
					manageticketsapi.deletePosting(postingId);
				}
			}
		}
	}
	
	@Test(groups={"regression","ticketsFunctional","sellFunctional"}, priority=19)
	public void verifyCancelMultipleSellTickets() throws Exception{
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] Tkts = manageticketsapi.getResaleDetails(true, "event", true, false);
		List<String> Tkt = Arrays.asList(Tkts);
		Tkt = manageticketsapi.selltickets(new String[]{Tkt.get(0), Tkt.get(1)});
		try {
			for(int i = 0; i < Tkt.size(); i++) {
				String[] ticket1=Tkt.get(i).split("\\.");
				if(i == 0) {
					load("/tickets#/"+ticket1[0]);
					homepage.login("", "", null, true);
				} else
					utils.navigateTo("/tickets#/"+ticket1[0]);
				
				managetickets.clickEditCancelTicket(ticket1[0], ticket1[1].replaceAll("%20", " "), ticket1[2], ticket1[3], Tkt.get(i));
				managetickets.clickCancelPosting();
				Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
				managetickets.clickConfirm();
				Assert.assertEquals(managetickets.getSuccess(), "Success!");
				managetickets.clickDone();
				Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
				String state1 = manageticketsapi.waitForPendingActionRemoved(Tkt.get(i));
				Assert.assertNull(state1, "Verify pending action got removed");
				String status1 = managetickets.getTicketStatus(ticket1[0], ticket1[1].replaceAll("%20", " "), ticket1[2], ticket1[3], Tkt.get(i));
				Assert.assertEquals(status1, "No Status");
			}
		} catch(Exception ex) {
			Integer[] postings = manageticketsapi.getPostingId(new String[]{Tkt.get(0)});
			if(postings.length > 0) {
				int postingId = postings[0];
				manageticketsapi.deletePosting(postingId);
			}
			postings = manageticketsapi.getPostingId(new String[]{Tkt.get(1)});
			if(postings.length > 0) {
				int postingId = postings[0];
				manageticketsapi.deletePosting(postingId);
			}
			throw ex;
		}
	}
	
	@Test(groups={"smoke","regression","ticketsFunctional","viewFunctional","prod"}, priority=20)
	public void downloadPassbook() throws Exception {
		if(!utils.getManageTicketConfiguration("mobile_enabled"))
			  throw new SkipException("Mobile_Enabled is not enabled in CMS");
		if(!driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("Skipped");
		if(Environment.get("deviceType").trim().equalsIgnoreCase("tablet"))
			throw new SkipException("Skipped");
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] Tkts = manageticketsapi.getBarcodeRenderMobileEnabledTickets(true, "event", false, false, false, false);
		String Tkt = Tkts[0];
		String[] ticket=Tkt.split("\\.");
		load("/tickets#/"+ticket[0]);
		homepage.login("", "", null, true);
 		if (managetickets.checkenableEDP()==true) {
 			SoftAssert.assertEquals(managetickets.getMobileScanEventDetails(), Dictionary.get("eventName"));
			managetickets.clickScanBarcodeEDP(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			Assert.assertEquals(managetickets.getMobileScanSectionName(), ticket[1].replaceAll("%20", " "));
			Assert.assertEquals(managetickets.getMobileScanRowName(),ticket[2]);
			Assert.assertEquals(managetickets.getMobileScanSeatName(), ticket[3]);
			SoftAssert.assertEquals(managetickets.getMobileScanGateNumber(), "Enter Gate: " + Dictionary.get("entryGate").trim());
			manageticketsapi.renderPassbook(Tkt);
			Assert.assertTrue(managetickets.isAddToAppletWalletDisplayed(), "Verify add to apple wallet button is displayed");
		}else {
			managetickets.clickScanBarcode(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			Assert.assertEquals(managetickets.getMobileScanEventDetails(), Dictionary.get("eventName"));
			Assert.assertEquals(managetickets.getMobileScanSectionName(), ticket[1].replaceAll("%20", " "));
			Assert.assertEquals(managetickets.getMobileScanRowName(),ticket[2]);
			Assert.assertEquals(managetickets.getMobileScanSeatName(), ticket[3]);
			SoftAssert.assertEquals(managetickets.getMobileScanGateNumber(), "Enter Gate: " + Dictionary.get("entryGate").trim());
			manageticketsapi.renderPassbook(Tkt);
			Assert.assertTrue(managetickets.isAddToAppletWalletDisplayed(), "Verify add to apple wallet button is displayed");
		}
	}

	@Test(groups = {"regression","ticketsFunctional","sendFunctional" }, priority = 21)
	public void sendMultipleTicketsWithMultipleParkingPass() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		
		String[] Tkts = manageticketsapi.getTransferDetails(true, "event", true, true, true);
		List<String> Tkt = Arrays.asList(Tkts);
		String[] ticket = Tkt.get(0).split("\\.");
		load("/tickets#/" + ticket[0]);
		homepage.login("", "", null, true);
		managetickets.clickSendTickets(null);
		String[] ticket2 = Tkt.get(1).split("\\.");
		managetickets.selectSeatInPopUp(Tkt.get(0), ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
		managetickets.selectSeatInPopUp(Tkt.get(1), ticket2[1].replaceAll("%20", " "), ticket2[2], ticket2[3]);
		//managetickets.clickBundleParkingCheckBox();
		 managetickets.clickBundleParking();
		//managetickets.clickContinue();
		managetickets.selectParkingSlots(2);
		managetickets.clickContinue();
		Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
		SoftAssert.assertTrue(managetickets.getSection().contains(ticket[1].replaceAll("%20", " ")));
//		Assert.assertTrue(managetickets.getRow().contains(ticket[2]));
//		Assert.assertTrue(managetickets.getSeat().contains(ticket[3]));
		managetickets.clickContinue();
		String eventclaimLink = "", parkingclaimLink = "";
		try {
			 eventclaimLink = managetickets.getEventClaimLink();
			 parkingclaimLink = managetickets.getParkingClaimLink();
			 //managetickets.clickContinue();
			 String transferName="TestAuto";
		     managetickets.typeTransferTag(transferName);
		     managetickets.clickTransferDone();
			 Assert.assertTrue(managetickets.isTicketsListDisplayed(null) ,"Verify tickets listing page is displayed");
			 String state = manageticketsapi.waitForTicketState(Tkt.get(0), "pending");
			 String state2 = manageticketsapi.waitForTicketState(Tkt.get(1), "pending");
			 managetickets.logoutNLogin("", "");
			 String status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "),ticket[2], ticket[3], Tkt.get(0));
			 String status2 = managetickets.getTicketStatus(ticket2[0], ticket2[1].replaceAll("%20", " "),ticket2[2], ticket2[3], Tkt.get(1));
			 Assert.assertTrue(status.trim().contains(transferName));
			 Assert.assertEquals(state, "pending");
			 Assert.assertTrue(status2.trim().contains(transferName));
			 Assert.assertEquals(state2, "pending");
			 Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt.get(0), "",""), new Boolean[] {false, false, false, false, false, false, false},"Verify the ticket flags");
			 Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt.get(1), "", ""), new Boolean[] {false, false, false, false, false, false, false},"Verify the ticket flags");
			 String transferId = parkingclaimLink.trim().split("\\?")[1];
			 String[] parkingtickedIds = manageticketsapi.getTransferTicketIDs(transferId);
			 String firstTicket = parkingtickedIds[0];
			 String secondTicket = parkingtickedIds[1];
			 ticket = firstTicket.split("\\.");
			 utils.navigateTo("/tickets#/" + ticket[0]);
			 status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], firstTicket);
			 Assert.assertTrue(status.trim().contains(transferName));
			 Assert.assertEquals(manageticketsapi.waitForTicketState(firstTicket, "pending"),"pending");
			 Assert.assertEquals(manageticketsapi.getTicketFlags(firstTicket, "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
			 
			 ticket = secondTicket.split("\\.");
			 utils.navigateTo("/tickets#/" + ticket[0]);
			 status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], secondTicket);
			 Assert.assertTrue(status.trim().contains(transferName));
			 Assert.assertEquals(manageticketsapi.waitForTicketState(secondTicket, "pending"),"pending");
			 Assert.assertEquals(manageticketsapi.getTicketFlags(secondTicket, "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags"); 
		 } finally {
			 if(!eventclaimLink.trim().equalsIgnoreCase("")) {
				 String transferId = eventclaimLink.trim().split("\\?")[1];
				 manageticketsapi.deleteTransfer(transferId);
			 }
			 if(!parkingclaimLink.trim().equalsIgnoreCase("")) {
				 String transferId = parkingclaimLink.trim().split("\\?")[1];
				 manageticketsapi.deleteTransfer(transferId);
			 }
		 }
	}

	@Test(groups = {"regression","ticketsFunctional","sendFunctional"}, priority = 22)
	public void sendTicketWithMultipleParkingPass() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		
		String[] Tkts = manageticketsapi.getTransferDetails(true, "event", false, true, true);
		List<String> Tkt = Arrays.asList(Tkts);
		String[] ticket = Tkt.get(0).split("\\.");
		load("/tickets#/" + ticket[0]);
		
		homepage.login("", "", null, true);
		managetickets.clickSendTickets(null);
		managetickets.selectSeatInPopUp(Tkt.get(0), ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
		managetickets.clickBundleParking();
		managetickets.selectParkingSlots(2);
		 managetickets.clickContinue();
		 Assert.assertEquals(managetickets.getPopUpEventDetails(),
		 Dictionary.get("eventName"));
		 SoftAssert.assertTrue(managetickets.getSection().contains(ticket[1].replaceAll("%20", " ")));
//		 Assert.assertTrue(managetickets.getRow().contains(ticket[2]));
//		 Assert.assertTrue(managetickets.getSeat().contains(ticket[3]));
		 managetickets.clickContinue();
		 String eventclaimLink = "", parkingclaimLink = "";
		 try {
			 eventclaimLink = managetickets.getEventClaimLink();
			 parkingclaimLink = managetickets.getParkingClaimLink();
			 String transferName="TestAuto";
		     managetickets.typeTransferTag(transferName);
		     managetickets.clickTransferDone();
			 Assert.assertTrue(managetickets.isTicketsListDisplayed(null) ,"Verify tickets listing page is displayed");
			 String state = manageticketsapi.waitForTicketState(Tkt.get(0), "pending");
			 managetickets.logoutNLogin("", "");
			 String status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "),ticket[2], ticket[3], Tkt.get(0));
			 Assert.assertTrue(status.trim().contains(transferName));
			 Assert.assertEquals(state, "pending");
			 Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt.get(0), "",""), new Boolean[] {false, false, false, false, false, false, false},"Verify the ticket flags");
	//		 Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt.get(1), "", ""), new Boolean[] {false, false, false, false, false, false, false},"Verify the ticket flags");
			 String transferId = parkingclaimLink.trim().split("\\?")[1];
			 String[] parkingtickedIds = manageticketsapi.getTransferTicketIDs(transferId);
			 String firstTicket = parkingtickedIds[0];
			 String secondTicket = parkingtickedIds[1];
			 ticket = firstTicket.split("\\.");
			 utils.navigateTo("/tickets#/" + ticket[0]);
			 status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], firstTicket);
			 Assert.assertTrue(status.trim().contains(transferName));
			 Assert.assertEquals(manageticketsapi.waitForTicketState(firstTicket, "pending"), "pending");
			 Assert.assertEquals(manageticketsapi.getTicketFlags(firstTicket, "",""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
			 
			 ticket = secondTicket.split("\\.");
			 utils.navigateTo("/tickets#/" + ticket[0]);
			 status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], secondTicket);
			 Assert.assertTrue(status.trim().contains(transferName));
			 Assert.assertEquals(manageticketsapi.waitForTicketState(secondTicket, "pending"), "pending");
			 Assert.assertEquals(manageticketsapi.getTicketFlags(secondTicket, "",""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
		 } finally {
			 if(!eventclaimLink.trim().equalsIgnoreCase("")) {
				 String transferId = eventclaimLink.trim().split("\\?")[1];
				 manageticketsapi.deleteTransfer(transferId);
			 }
			 if(!parkingclaimLink.trim().equalsIgnoreCase("")) {
				 String transferId = parkingclaimLink.trim().split("\\?")[1];
				 manageticketsapi.deleteTransfer(transferId);
			 }
		 }
	}

	@Test(groups = {"smoke","regression","ticketsFunctional","viewFunctional"}, priority = 23)
	public void mobileTicketDetails() throws Exception {
		if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("Skipped");
		if(Environment.get("deviceType").trim().equalsIgnoreCase("tablet")) {
			throw new SkipException("Skipped");
		}
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] renderTkts = manageticketsapi.getRenderDetailsFiles(true, "event", false);
		String[] cannotRenderTkts = manageticketsapi.getCannotRenderDetailsFiles(true, "event", false);
		if(renderTkts == null && cannotRenderTkts == null)
			throw new SkipException("no tickets found");

		load("/tickets");
		homepage.login("", "", null, true);

		if(renderTkts != null) {
			String renderTkt = renderTkts[0];
			String[] ticket = renderTkt.split("\\.");
			utils.navigateTo("/tickets#/" + ticket[0]);

			if (managetickets.checkenableEDP()) {
				managetickets.clickTicketDetailsEDP(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], renderTkt);
				String[] expected = manageticketsapi.renderTicketDetails(renderTkt);
				String[] actual = managetickets.getTicketDetails();
				SoftAssert.assertEquals(actual, expected, "Verify ticket details for rendered ticket");
				// Assert.assertEquals(actual, expected, "Verify ticket details for rendered
				// ticket");
			} else {
				managetickets.clickTicketDetails(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3],
						renderTkt);
				String[] expected = manageticketsapi.renderTicketDetails(renderTkt);
				String[] actual = managetickets.getTicketDetails();
				Assert.assertEquals(actual, expected, "Verify ticket details for rendered ticket");
			}    
		}

		/*if(cannotRenderTkts != null) {
			String cannotrenderTkt = cannotRenderTkts[0];
			String[] ticket = cannotrenderTkt.split("\\.");
			utils.navigateTo("/tickets");
			if (managetickets.checkenableEDP()==true) {
				Assert.assertTrue(managetickets.isManageTicketsListDisplayed() , "Verify manage tickets list is displayed");
				utils.navigateTo("/tickets#/" + ticket[0]);
				managetickets.clickTicketDetailsEDP(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], cannotrenderTkt);
				String[] expected = manageticketsapi.renderTicketDetails(cannotrenderTkt);
				String[] actual = managetickets.getTicketDetails();
				Assert.assertEquals(actual, expected, "Verify ticket details for not rendered ticket");
			}else {

				Assert.assertTrue(managetickets.isManageTicketsListDisplayed() , "Verify manage tickets list is displayed");
				utils.navigateTo("/tickets#/" + ticket[0]);
				managetickets.clickTicketDetails(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], cannotrenderTkt);
				String[] expected = manageticketsapi.renderTicketDetails(cannotrenderTkt);
				String[] actual = managetickets.getTicketDetails();
				Assert.assertEquals(actual, expected, "Verify ticket details for not rendered ticket");
			}
		}*/
	}
	
	@Test(groups={"regression","ticketsFunctional","viewFunctional"}, priority=24)
	public void verifyMultipleDownloadTickets() throws Exception {
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))
			throw new SkipException("Skipped");
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] Tkt = manageticketsapi.getRenderDetails(true, "event", true);
		String[] ticket = Tkt[0].split("\\.");
		String[] ticket2= Tkt[1].split("\\.");
		load("/tickets#/" + ticket[0]);
		homepage.login("", "", null, true);
		System.out.println("EDP Feature is Enabled "+managetickets.checkenableEDP());
		if (managetickets.checkenableEDP()==true) {
			managetickets.clickDownloadTicketsEDP();
			managetickets.selectSeatInPopUpEDP(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
			managetickets.selectSeatInPopUpEDP(Tkt[1], ticket2[1].replaceAll("%20", " "), ticket2[2], ticket2[3]);
			managetickets.clickContinueEDP();
			Assert.assertTrue(Dictionary.get("eventName").contains(managetickets.getPopUpEventDetailsforMultiple()),"Event Name are getting dispalyed");
			SoftAssert.assertTrue(managetickets.getSection().contains(ticket[1].replaceAll("%20", " ")));
			managetickets.clickContinue();
			Assert.assertTrue(managetickets.isTicketsListDisplayedEDP(null), "Verify tickets listing page is displayed");
		}else {
			managetickets.clickDownloadTickets();
			managetickets.selectSeatInPopUp(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
			managetickets.selectSeatInPopUp(Tkt[1], ticket2[1].replaceAll("%20", " "), ticket2[2], ticket2[3]);
			managetickets.clickContinue();
			Assert.assertEquals(managetickets.getPopUpEventDetailsforMultiple(), Dictionary.get("eventName"));
			SoftAssert.assertTrue(managetickets.getSection().contains(ticket[1].replaceAll("%20", " ")));
			managetickets.clickContinue();
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
		}
		manageticketsapi.renderFile(Tkt);
		
	}
	
	@Test(groups={"smoke","regression","ticketsFunctional"}, priority=25, enabled = false)
	public void verifyBuyMoreTickets() throws Exception{
		load("/");
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		homepage.login("", "", null, false);
		Assert.assertTrue(getDriver().getCurrentUrl().contains("/dashboard"));
		load("/tickets");
		managetickets.clickBuyMoreTickets();
		Assert.assertEquals(getDriver().getCurrentUrl(), Dictionary.get("URL"), "Redirecting to correct page");
	}
	
	@Test(groups={"smoke","regression","ticketsFunctional","prod"}, priority=26)
	public void verifyButtonsonTicketPage() throws Exception{
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		HashMap<Integer,ManageticketsAPI.Event> events = manageticketsapi.getEventIdWithTktsDetails();
		Assert.assertNotNull(events);
		int eventId = -1;
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))
			eventId = manageticketsapi.getEventIdWithMaxTktsHavingRenderBarcode(events);
		else 
			eventId = manageticketsapi.getEventIdWithMaxTkts(events);
		Assert.assertNotEquals(eventId, -1);
		
		load("/tickets#/" + eventId);
		homepage.login("", "", null, true);

		if (managetickets.checkenableEDP()==true) {
			Assert.assertTrue(managetickets.isTicketsListDisplayedEDP(null) , "Verify tickets listing page is displayed");	
		}else {
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
		}

		ManageticketsAPI.Event _event = events.get(eventId);

		if((!driverType.trim().toUpperCase().contains("IOS") && !driverType.trim().toUpperCase().contains("ANDROID")) || Environment.get("deviceType").trim().equalsIgnoreCase("tablet")) {
			if(_event.canrenderfiletktcount > 0)
				Assert.assertTrue(managetickets.isViewButtonEnable(), "Verify view button is enabled");
			else
				Assert.assertFalse(managetickets.isViewButtonEnable(), "Verify view button is disabled");
		}

		if(_event.cantransfertktcount > 0) {
			Assert.assertTrue(managetickets.isSendButtonEnable(null), "Verify send button is enabled");
		} else {
			Assert.assertFalse(managetickets.isSendButtonEnable(null), "Verify send button is disabled");
		}

		if(_event.canresaletktcount > 0) {
			Assert.assertTrue(managetickets.isSellButtonEnable(null), "Verify sell button is enabled");
		} else {
			Assert.assertFalse(managetickets.isSellButtonEnable(null), "Verify sell button is disabled");
		}


		if (managetickets.checkenableEDP()==true) {
			if(_event.candonatecharitytktcount > 0) {
				Assert.assertTrue(managetickets.isDonateButtonEnableEDP(null), "Verify donate button is enabled");
			} else {
				Assert.assertFalse(managetickets.isDonateButtonEnableEDP(null), "Verify donate button is disabled");
			}
		}
		else {
			if(_event.candonatecharitytktcount > 0) {
				Assert.assertTrue(managetickets.isDonateButtonEnable(null), "Verify donate button is enabled");
			} else {
				Assert.assertFalse(managetickets.isDonateButtonEnable(null), "Verify donate button is disabled");
			}
		}
	}
	
	@Test(groups = {"smoke","regression","ticketsFunctional","sendFunctional"}, priority = 27)
	public void verifySendParkingTicket() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		
		String[] Tkt = manageticketsapi.getTransferDetails(true, "parking", false, false, false);
		String[] ticket = Tkt[0].split("\\.");
		load("/tickets#/" + ticket[0]);
		homepage.login("", "", null, true);
		
		managetickets.clickSendTickets(null);
		managetickets.selectSeatInPopUp(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
		managetickets.clickContinue();
		Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
		SoftAssert.assertTrue(managetickets.getSection().contains(ticket[1].replaceAll("%20", " ")) || managetickets.getSection().contains(Dictionary.get("FORMATTED_SECTION_NAME_" + Tkt[0])));
//		Assert.assertTrue(managetickets.getRow().contains(ticket[2]));
//		Assert.assertTrue(managetickets.getSeat().contains(ticket[3]));
		managetickets.clickContinue();
		String claimLink = managetickets.getClaimLink();
		String transferName="TestAuto";
		managetickets.typeTransferTag(transferName);
		managetickets.clickTransferDone();
		try{
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
			String state = manageticketsapi.waitForTicketState(Tkt[0], "pending");
			managetickets.logoutNLogin("", "");
			String status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt[0]);
			Assert.assertTrue(status.trim().contains(transferName));
			Assert.assertEquals(state, "pending");
			Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt[0], "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
		} finally {
			String transferId = claimLink.trim().split("\\?")[1];
			manageticketsapi.deleteTransfer(transferId);
		}
	}
	
	@Test(groups={"smoke","regression","ticketsFunctional","sellFunctional"}, priority=28)
	public void verifySellParkingTicket() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] tickets = manageticketsapi.getResaleDetails(true, "parking", false, false);
		String Tkt = tickets[0];
		String[] ticket=Tkt.split("\\.");
		load("/tickets#/"+ticket[0]);
		homepage.login("", "", null, true);
		managetickets.clickSellTickets(null);
		managetickets.selectSeatInPopUp(Tkt, ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
		managetickets.clickContinue();
		managetickets.typeEarningPrice(new String[]{Dictionary.get("EarningPrice")});
		managetickets.clickContinue();
		managetickets.selectBankAccount();
		if(managetickets.isContinueEnabled()) {
			//Do Nothing
		} else {
			if(managetickets.getDepositAccountValue().trim().equalsIgnoreCase("Check by mail")) {
				managetickets.clickEditSellerProfile();
				managetickets.inputSellerProfile(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Add1"), Dictionary.get("Add2"), Dictionary.get("Country"), Dictionary.get("City"), Dictionary.get("State"), Dictionary.get("ZipCode"), Dictionary.get("MobileNum"), Dictionary.get("PhoneNum"));
				managetickets.clickContinue();
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("FirstName")), "Seller Profile");
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("LastName")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add1")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add2")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("City")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("ZipCode")));
			} else {
				managetickets.clickEditBankDetail();
				managetickets.inputBankDetails(Dictionary.get("AccType"), Dictionary.get("RoutingNum"), Dictionary.get("AccountNum"), Dictionary.get("ConfirmAcc"));
				managetickets.clickContinue();
				SoftAssert.assertEquals(managetickets.getBankDetails(Dictionary.get("AccountNum").trim().substring(Dictionary.get("AccountNum").trim().length() - 4)), utils.toCamelCase(Dictionary.get("AccType").trim()) + " account ending in " + Dictionary.get("AccountNum").trim().substring(Dictionary.get("AccountNum").trim().length() - 4), "Verify bank details got updated");
			}
		}	
		
		managetickets.clickContinue();
		Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
		managetickets.clickContinue();
		try {
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
			String state = manageticketsapi.waitForTicketState(Tkt, "pending");
			managetickets.logoutNLogin("", "");
			String status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			Assert.assertTrue(status.contains("Listed"), status);
			Assert.assertEquals(state, "pending");
			Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt, "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
		} finally {
			Integer[] postings = manageticketsapi.getPostingId(new String[]{Tkt});
			if(postings.length > 0) {
				int postingId = postings[0];
				manageticketsapi.deletePosting(postingId);
			}
		}
	}
	
	@Test(groups={"regression","ticketsFunctional","sellFunctional"}, priority=29)
	public void verifyEditParkingTicket() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] tickets = manageticketsapi.getResaleDetails(true, "parking", false, false);
		String Tkt = tickets[0];
		String[] ticket=Tkt.split("\\.");
		
//		Boolean[] flags = manageticketsapi.getTicketFlags(Tkt, "", "");
//		CAN_TRANSFER = flags[0];
//		CAN_RESALE = flags[1];
//		CAN_RENDER = flags[2];
//		CAN_RENDER_FILE = flags[3];
//		CAN_RENDER_BARCODE = flags[4];
//		CAN_RENDER_PASSBOOK = flags[5];
//		CAN_DONATE_CHARITY = flags[6];
		
		manageticketsapi.selltickets(new String[]{Tkt});

		try {
			load("/tickets#/"+ticket[0]);
			homepage.login("", "", null, true);
			managetickets.clickEditCancelTicket(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			managetickets.clickEditPosting();
			managetickets.typeEarningPrice(new String[]{Dictionary.get("EarningPrice")});
			managetickets.clickContinue();
			managetickets.selectBankAccount();
			
			if(managetickets.getDepositAccountValue().trim().equalsIgnoreCase("Check by mail")) {
				managetickets.clickEditSellerProfile();
				managetickets.inputSellerProfile(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Add1"), Dictionary.get("Add2"), Dictionary.get("Country"), Dictionary.get("City"), Dictionary.get("State"), Dictionary.get("ZipCode"), Dictionary.get("MobileNum"), Dictionary.get("PhoneNum"));
				managetickets.clickContinue();
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("FirstName")), "Seller Profile");
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("LastName")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add1")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add2")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("City")));
				SoftAssert.assertTrue(managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("ZipCode")));
			} else {
				managetickets.clickEditBankDetail();
				managetickets.inputBankDetails(Dictionary.get("AccType"), Dictionary.get("RoutingNum"), Dictionary.get("AccountNum"), Dictionary.get("ConfirmAcc"));
				managetickets.clickContinue();
				SoftAssert.assertEquals(managetickets.getBankDetails(Dictionary.get("AccountNum").trim().substring(Dictionary.get("AccountNum").trim().length() - 4)), utils.toCamelCase(Dictionary.get("AccType").trim()) + " account ending in " + Dictionary.get("AccountNum").trim().substring(Dictionary.get("AccountNum").trim().length() - 4), "Verify bank details got updated");
			}
			
			managetickets.clickContinue();
			Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
			BaseUtil.sync(2000L);
			managetickets.clickContinue();
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
			String state = manageticketsapi.waitForTicketState(Tkt, "pending");
			managetickets.logoutNLogin("", "");
			String status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			Assert.assertTrue(status.contains("Listed"), status);
			Assert.assertEquals(state, "pending");
			managetickets.clickEditCancelTicket(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			managetickets.clickEditPosting();
			Assert.assertEquals(managetickets.getEarningPrice(), Dictionary.get("EarningPrice"));
	//		Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt, "", ""), new Boolean[] {!CAN_TRANSFER, !CAN_RESALE, !CAN_RENDER, !CAN_RENDER_FILE, !CAN_RENDER_BARCODE, !CAN_RENDER_PASSBOOK, !CAN_DONATE_CHARITY}, "Verify the ticket flags");
		} finally {
			Integer[] postings = manageticketsapi.getPostingId(new String[]{Tkt});
			if(postings.length > 0) {
				int postingId = postings[0];
				manageticketsapi.deletePosting(postingId);
			}
		}
	}
	
	@Test(groups={"regression","ticketsFunctional","sellFunctional"}, priority=30)
	public void verifyCancelParkingPosting() throws Exception{
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] Tkts = manageticketsapi.getResaleDetails(true, "parking", false, false);
		String Tkt = Tkts[0];
		String[] ticket=Tkt.split("\\.");
		
		Boolean[] flags = manageticketsapi.getTicketFlags(Tkt, "", "");
		CAN_TRANSFER = flags[0];
		CAN_RESALE = flags[1];
		CAN_RENDER = flags[2];
		CAN_RENDER_FILE = flags[3];
		CAN_RENDER_BARCODE = flags[4];
		CAN_RENDER_PASSBOOK = flags[5];
		CAN_DONATE_CHARITY = flags[6];
		
		manageticketsapi.selltickets(new String[]{Tkt});
		
		try {
			load("/tickets#/"+ticket[0]);
			homepage.login("", "", null, true);
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
			managetickets.clickEditCancelTicket(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
			managetickets.clickCancelPosting();
			Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
			managetickets.clickConfirm();
			Assert.assertEquals(managetickets.getSuccess(), "Success!");
		} catch(Exception ex) {
			Integer[] postings = manageticketsapi.getPostingId(new String[]{Tkt});
			if(postings.length > 0) {
				int postingId = postings[0];
				manageticketsapi.deletePosting(postingId);
			}
			throw ex;
		}
		
		managetickets.clickDone();
		Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
		String state = manageticketsapi.waitForPendingActionRemoved(Tkt);
		Assert.assertNull(state, "Verify pending action got removed");
		managetickets.logoutNLogin("", "");
		String status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt);
		Assert.assertEquals(status, "No Status");
		SoftAssert.assertEquals(manageticketsapi.getTicketFlags(Tkt, "", ""), new Boolean[] {CAN_TRANSFER, CAN_RESALE, CAN_RENDER, CAN_RENDER_FILE, CAN_RENDER_BARCODE, CAN_RENDER_PASSBOOK, CAN_DONATE_CHARITY}, "Verify the ticket flags");
	}
	
	@Test(groups = {"smoke","regression","ticketsFunctional","donateFunctional"}, priority = 31)
	public void verifyDonateParkingTickets() throws Exception {
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] Tkt = manageticketsapi.getDonateDetails(true, "parking", false, false);
		String[] ticket = Tkt[0].split("\\.");
		load("/tickets#/" + ticket[0]);
		homepage.login("", "", null, true);
		
		managetickets.clickDonateTickets(null);
		managetickets.selectSeatInPopUp(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
		managetickets.clickContinue();
		managetickets.selectCharity();
		managetickets.clickContinue();
		Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
		managetickets.clickContinue();
		Assert.assertTrue(managetickets.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
		String state = manageticketsapi.waitForTicketState(Tkt[0], "donated");
		managetickets.logoutNLogin("", "");
		Assert.assertEquals(managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt[0]), "Donated");
		Assert.assertEquals(state, "donated");
		Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt[0], "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
	}
	
	@Test(groups = {"regression","ticketsFunctional","sendFunctional","criticalbusiness"}, priority = 32)
	public void verifyClaimTickets() throws Exception {
		String[] Tkt = manageticketsapi.getTransferDetails(true, "event", false, false, false);
		String[] ticket=Tkt[0].split("\\.");
		
		Boolean[] flags = manageticketsapi.getTicketFlags(Tkt[0], "", "");
		CAN_TRANSFER = flags[0];
		CAN_RESALE = flags[1];
		CAN_RENDER = flags[2];
		CAN_RENDER_FILE = flags[3];
		CAN_RENDER_BARCODE = flags[4];
		CAN_RENDER_PASSBOOK = flags[5];
		CAN_DONATE_CHARITY = flags[6];
		
		manageticketsapi.sendTickets(Integer.valueOf(ticket[0]), new String[]{Tkt[0]});
		String[] transferIds = manageticketsapi.getTransferID(new String[]{Tkt[0]});
		load("/ticket/claim?"+transferIds[0]);

		try{
			Assert.assertTrue(homepage.getLoginMessage().contains("Congratulations") || homepage.getLoginMessage().contains("To view your offer"), "Verify 'Claim Offer' text is displayed on login screen");
			homepage.createaccount(null, true);
			managetickets.clickClaim();
			String state = manageticketsapi.waitForTicketState(Tkt[0], "accepted");
			Assert.assertEquals(state, "accepted");
		} catch(Exception ex) {
			manageticketsapi.deleteTransfer(transferIds[0]);
			throw ex;
		}
		
		Assert.assertEquals(managetickets.getTicketStatus(Tkt[0].split("\\.")[0], Tkt[0].split("\\.")[1].replaceAll("%20", " "), Tkt[0].split("\\.")[2],Tkt[0].split("\\.")[3], Tkt[0]), "No Status");
		//For old account
		Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt[0], "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
		//For new account
		SoftAssert.assertEquals(manageticketsapi.getTicketFlags(Tkt[0], Dictionary.get("NEW_EMAIL_ADDRESS").trim(), Dictionary.get("NEW_PASSWORD").trim()), new Boolean[] {CAN_TRANSFER, CAN_RESALE, CAN_RENDER, CAN_RENDER_FILE, CAN_RENDER_BARCODE, CAN_RENDER_PASSBOOK, CAN_DONATE_CHARITY}, "Verify the ticket flags");
		
		//Login into old account
		managetickets.logoutNLogin("", "");
		Map<String, Object> names = manageticketsapi.getCustomerName(Dictionary.get("NEW_EMAIL_ADDRESS").trim(), Dictionary.get("NEW_PASSWORD").trim());
		SoftAssert.assertEquals(managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt[0]), "Claimed By " + names.get("ACTUAL_CUST_NAME"), "Verify claimed text on tickets");
	}
	
	@Test(groups={"regression","ticketsFunctional","sendFunctional"}, priority = 33)
	public void verifyClaimParkingTicket() throws Exception {
		String[] ticketIds = manageticketsapi.getTransferDetails(true, "parking", false, false, false);
		String ticketId = ticketIds[0];
		String[] ticket = ticketId.split("\\.");
		
		Boolean[] flags = manageticketsapi.getTicketFlags(ticketId, "", "");
		CAN_TRANSFER = flags[0];
		CAN_RESALE = flags[1];
		CAN_RENDER = flags[2];
		CAN_RENDER_FILE = flags[3];
		CAN_RENDER_BARCODE = flags[4];
		CAN_RENDER_PASSBOOK = flags[5];
		CAN_DONATE_CHARITY = flags[6];
		
		manageticketsapi.sendTickets(Integer.valueOf(ticket[0]), new String[]{ticketId});
		String[] transferIds = manageticketsapi.getTransferID(new String[]{ticketId});
		load("/ticket/claim?"+transferIds[0]);
		
		
		try {
			Assert.assertTrue(homepage.getLoginMessage().contains("Congratulations") || homepage.getLoginMessage().contains("To view your offer"));
			homepage.createaccount(null, true);
			managetickets.clickClaim();
			String state = manageticketsapi.waitForTicketState(ticketId, "accepted");
			Assert.assertEquals(state, "accepted");
		} catch(Exception ex) {
			manageticketsapi.deleteTransfer(transferIds[0]);
			throw ex;
		}
		
		Assert.assertEquals(managetickets.getTicketStatus(ticketId.split("\\.")[0], ticketId.split("\\.")[1].replaceAll("%20", " "), ticketId.split("\\.")[2],ticketId.split("\\.")[3], ticketId), "No Status");
		//For old account
		Assert.assertEquals(manageticketsapi.getTicketFlags(ticketId, "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
		//For new account
		SoftAssert.assertEquals(manageticketsapi.getTicketFlags(ticketId, Dictionary.get("NEW_EMAIL_ADDRESS").trim(), Dictionary.get("NEW_PASSWORD").trim()), new Boolean[] {CAN_TRANSFER, CAN_RESALE, CAN_RENDER, CAN_RENDER_FILE, CAN_RENDER_BARCODE, CAN_RENDER_PASSBOOK, CAN_DONATE_CHARITY}, "Verify the ticket flags");
		
		//Login into old account
		managetickets.logoutNLogin("", "");
		Map<String, Object> names = manageticketsapi.getCustomerName(Dictionary.get("NEW_EMAIL_ADDRESS").trim(), Dictionary.get("NEW_PASSWORD").trim());
		SoftAssert.assertEquals(managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], ticketId), "Claimed By " + names.get("ACTUAL_CUST_NAME"));
	}

	@Test(groups={"regression","ticketsFunctional","sendFunctional","criticalbusiness"}, priority = 34)
	public void verifyClaimTicketsforExistingUser() throws Exception {
		if(Dictionary.get("NEW_EMAIL_ADDRESS").trim().equalsIgnoreCase("") || Dictionary.get("NEW_EMAIL_ADDRESS").trim().equalsIgnoreCase(Dictionary.get("EMAIL_ADDRESS").trim())) {
			throw new SkipException("No existing account found for claiming ticket");
		}
		String[] Tkt = manageticketsapi.getTransferDetails(true, "event", false, false, false);
		String[] ticket=Tkt[0].split("\\.");
		
		Boolean[] flags = manageticketsapi.getTicketFlags(Tkt[0], "", "");
		CAN_TRANSFER = flags[0];
		CAN_RESALE = flags[1];
		CAN_RENDER = flags[2];
		CAN_RENDER_FILE = flags[3];
		CAN_RENDER_BARCODE = flags[4];
		CAN_RENDER_PASSBOOK = flags[5];
		CAN_DONATE_CHARITY = flags[6];
		
		manageticketsapi.sendTickets(Integer.valueOf(ticket[0]), new String[]{Tkt[0]});
		String[] transferIds = manageticketsapi.getTransferID(new String[]{Tkt[0]});
		load("/ticket/claim?"+transferIds[0]);
		
		try{
			homepage.login(Dictionary.get("NEW_EMAIL_ADDRESS"),Dictionary.get("NEW_PASSWORD"), null, true);
			managetickets.clickClaim();
			String state = manageticketsapi.waitForTicketState(Tkt[0], "accepted");
			Assert.assertEquals(state, "accepted");
		} catch(Exception ex) {
			manageticketsapi.deleteTransfer(transferIds[0]);
			throw ex;
		}
		
		Assert.assertEquals(managetickets.getTicketStatus(Tkt[0].split("\\.")[0], Tkt[0].split("\\.")[1].replaceAll("%20", " "), Tkt[0].split("\\.")[2],Tkt[0].split("\\.")[3], Tkt[0]), "No Status");
		//For old account
		Assert.assertEquals(manageticketsapi.getTicketFlags(Tkt[0], "", ""), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags");
		//For new account
		SoftAssert.assertEquals(manageticketsapi.getTicketFlags(Tkt[0], Dictionary.get("NEW_EMAIL_ADDRESS").trim(), Dictionary.get("NEW_PASSWORD").trim()), new Boolean[] {CAN_TRANSFER, CAN_RESALE, CAN_RENDER, CAN_RENDER_FILE, CAN_RENDER_BARCODE, CAN_RENDER_PASSBOOK, CAN_DONATE_CHARITY}, "Verify the ticket flags");
		
		//Login into old account
		managetickets.logoutNLogin("", "");
		Map<String, Object> names = manageticketsapi.getCustomerName(Dictionary.get("NEW_EMAIL_ADDRESS").trim(), Dictionary.get("NEW_PASSWORD").trim());
		SoftAssert.assertEquals(managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt[0]), "Claimed By " + names.get("ACTUAL_CUST_NAME"), "Verify claimed text on tickets");
	}
	
	@Test(groups={"smoke","regression","ticketsFunctional","prod"}, priority = 35)
	public void verifySearchFunctionalityEventName() throws Exception {
		load("/tickets");
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		homepage.login("", "", null, true);
		if(managetickets.verifySearchableValue("park")){
			managetickets.clickSearch();
			managetickets.typeSearchValue("park");
			BaseUtil.sync(200L);
			Assert.assertTrue(managetickets.verifySearchValues("park"));
		} else 
			throw new SkipException("No Parking Tickets Available");
	}

	// Managetickets API
	@Test(groups={"smoke","regression","ticketsFunctional","ViewFunctional","prod", "manageTicketsApi"}, priority=36)
	public void verifyDownloadTicketsAPI() throws Exception {
		//change CAN_RENDER to CAN_RENDER_FILE in manageticketsapi
		String[] Tkt = manageticketsapi.getRenderDetails(true, "event", false);
		manageticketsapi.renderFile(new String[]{Tkt[0]});
	}
	
	@Test(groups={"smoke","regression","ticketsFunctional","donateFunctional", "manageTicketsApi"}, priority=37)
	public void verifyDonateAPI() throws Exception {
		String[] Tkt = manageticketsapi.getDonateDetails(true, "event", false, false);
		String[] eventid=Tkt[0].split("\\.");
		String event_id=eventid[0];
		Integer[] charityId= manageticketsapi.getCharityId(event_id);
		Integer charity = charityId[0];
		manageticketsapi.donateTickets(Tkt[0],charity.intValue());
	}
	
	@Test(groups={"smoke","regression","ticketsFunctional","sendFunctional","prod","manageTicketsApi"}, priority=38)
	public void verifyReclaimTicketsAPI() throws Exception{
		String[] tickets = manageticketsapi.getTransferDetails(true, "event", false, false, false);
		System.out.println("Tkt: "+tickets[0]);
		String Tkt = tickets[0];
		String[] ticket = tickets[0].split("\\.");
		manageticketsapi.sendTickets(Integer.valueOf(ticket[0]), new String[]{Tkt});
		String[] transferIds = manageticketsapi.getTransferID(new String[]{tickets[0]});
		if(transferIds != null && transferIds.length > 0) {
			String transferId = transferIds[0];
			manageticketsapi.deleteTransfer(transferId);
		}
	}
	
	@Test(groups={"smoke", "regression","ticketsFunctional","sendFunctional", "manageTicketsApi"}, priority=39)
	public void verifyClaimTicketsAPI() throws Exception{
		String[] tickets = manageticketsapi.getTransferDetails(true, "event", false, false, false);
		System.out.println("Tkt: "+tickets[0]);
		String Tkt = tickets[0];
		String[] ticket = tickets[0].split("\\.");
		manageticketsapi.sendTickets(Integer.valueOf(ticket[0]), new String[]{Tkt});
		String[] transferIds = manageticketsapi.getTransferID(new String[]{tickets[0]});
		String invite_id = transferIds[0];
		System.out.println("invite_id: " + invite_id);
		load("/");
		homepage.createaccount(null, false);
		try {
			manageticketsapi.claimTickets(Dictionary.get("NEW_EMAIL_ADDRESS").trim(), Dictionary.get("NEW_PASSWORD").trim(), invite_id, Tkt);
		} finally {
			transferIds = manageticketsapi.getTransferID(new String[]{Tkt});
			if(transferIds.length > 0)
				manageticketsapi.deleteTransfer(transferIds[0]);
		}
	}
	
	@Test(groups={"smoke","regression","ticketsFunctional","sendFunctional","prod", "manageTicketsApi"}, priority=40)
	public void verifySendTicketsAPI() throws Exception{
		String[] tickets = manageticketsapi.getTransferDetails(true, "event", false, false, false);
		String Tkt = tickets[0];
		String[] ticket = tickets[0].split("\\.");
		try {
			manageticketsapi.sendTickets(Integer.valueOf(ticket[0]), new String[]{Tkt});
		} finally {
			String[] transferIds = manageticketsapi.getTransferID(new String[]{Tkt});
			if(transferIds.length > 0)
				manageticketsapi.deleteTransfer(transferIds[0]);
		}
	}
	
	@Test(groups={"smoke","regression","ticketsFunctional","sellFunctional","prod", "manageTicketsApi"}, priority=41)
	public void verifySellTicketsAPI() throws Exception {
		String[] tickets = manageticketsapi.getResaleDetails(true, "event", false, false);
		String Tkt = tickets[0];
		try {
			manageticketsapi.selltickets(new String[]{Tkt});
		} finally {
			Integer[] postings = manageticketsapi.getPostingId(new String[]{Tkt});
			if(postings.length > 0) {
				int postingId = postings[0];
				manageticketsapi.deletePosting(postingId);
			}
		}
	}
		
	@Test(groups={"smoke","regression","ticketsFunctional","prod"}, priority=42)
	public void verifyActionButtons() throws Exception {
		HashMap<Integer,ManageticketsAPI.Event> events = manageticketsapi.getEventIdWithTktsDetails();
		Assert.assertNotNull(events);
        int eventId = -1;
        String tkt[] = null;
        if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			String ticketId[] = manageticketsapi.getBarcodeRenderMobileEnabledTickets(true, "event", false, false, false, false);
			tkt = ticketId[0].split("\\.");
			eventId = Integer.parseInt(ticketId[0].split("\\.")[0]);
        } else
            eventId = manageticketsapi.getEventIdWithMaxTkts(events);
        
        Assert.assertNotEquals(eventId, -1);
       // System.out.println(eventId);
        
		load("/tickets#/" + eventId);
		homepage.login("", "", null, true);
		
		if (managetickets.checkenableEDP()==true) {
			Assert.assertTrue(managetickets.isTicketsListDisplayedEDP(null) , "Verify tickets listing page is displayed");
		}
		else {
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
		}
		
		boolean donate = utils.getManageTicketConfiguration("donate");
		boolean sell = utils.getManageTicketConfiguration("sell");
		boolean send = utils.getManageTicketConfiguration("send");
		boolean view;
		
		if (managetickets.checkenableEDP()==true) {
			//scroll functionality is not present in the EDP
		}else {
			if(tkt != null)
				ticketsNew.scrollToTicket(tkt[0], tkt[1].replaceAll("%20", " "), tkt[2], tkt[3], tkt[0]);
		}
		
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			view = utils.getManageTicketConfiguration("mobile_enabled");
		} else
			view = utils.getManageTicketConfiguration("download");
		
		Assert.assertEquals(managetickets.isSellButtonVisible(null), sell, "Verify sell button is visible");
		Assert.assertEquals(managetickets.isSendButtonVisible(null), send, "Verify send button is visible");
		if (managetickets.checkenableEDP()==true) {
			Assert.assertEquals(managetickets.isDonateButtonVisibleEDP(null), donate, "Verify donate button is visible");
		}else {
			Assert.assertEquals(managetickets.isDonateButtonVisible(null), donate, "Verify donate button is visible");
		}

		if (managetickets.checkenableEDP()==true) {
			//this functionality is allready tested in other test case 
		}else {
			if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
				WebElement ele=	getDriver().findElement(By.xpath("(//span[contains(@class,'viewBarcode')])"));
				JavascriptExecutor obj = (JavascriptExecutor)getDriver();
				obj.executeScript("arguments[0].click();", ele);
				Assert.assertEquals(managetickets.isScanBarcodeVisible(), view, "Verify scan barcode is visible");
			} else
				Assert.assertEquals(managetickets.isViewButtonVisible(), view, "Verify view button is visible");
		}
	}
	
	@Test(groups={"smoke","regression","ticketsFunctional","prod"}, priority=43, enabled = false)
	public void verifyActionButtonsOnDisable() throws Exception {
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))
			throw new SkipException("Skipped");
		load("/user/login");
		SuperAdminPanel superAdminPanel = new SuperAdminPanel(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		superAdminPanel.login("", "");
		utils.navigateTo("/admin/config/site-settings");
		utils.scrollingToBottomofAPage();		
		superAdminPanel.clickDownloadToggleButton(false);
		superAdminPanel.clickSellToggleButton(false);
		superAdminPanel.clickSendToggleButton(false);
		superAdminPanel.clickDonateToggleButton(false);
		superAdminPanel.clickBarcodeToggleButton(false);
		superAdminPanel.save();
		load("/user/logout");
		
		HashMap<Integer,ManageticketsAPI.Event> events = manageticketsapi.getEventIdWithTktsDetails();
		Assert.assertNotNull(events);
		int eventId = -1;
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))
			eventId = manageticketsapi.getEventIdWithMaxTktsHavingRenderBarcode(events);
		else 
			eventId = manageticketsapi.getEventIdWithMaxTkts(events);
		Assert.assertNotEquals(eventId, -1);
		System.out.println(eventId);
		load("/tickets#/" + eventId);
		homepage.login("", "", null, true);
		Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
		
		boolean donate = utils.getManageTicketConfiguration("donate");
		boolean sell = utils.getManageTicketConfiguration("sell");
		boolean send = utils.getManageTicketConfiguration("send");
		boolean view;
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			view = utils.getManageTicketConfiguration("mobile_enabled");
		} else
			view = utils.getManageTicketConfiguration("download");
		
		Assert.assertFalse(sell, "Verify sell button is visible");
		Assert.assertFalse(send, "Verify send button is visible");
		Assert.assertFalse(donate, "Verify donate button is visible");
		
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			Assert.assertFalse(view, "Verify scan barcode is visible");
		} else
			Assert.assertFalse(view, "Verify view button is visible");
		
		Assert.assertEquals(managetickets.isSellButtonVisible(null), sell, "Verify sell button is visible");
		Assert.assertEquals(managetickets.isSendButtonVisible(null), send, "Verify send button is visible");
		Assert.assertEquals(managetickets.isDonateButtonVisible(null), donate, "Verify donate button is visible");
		
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			Assert.assertEquals(managetickets.isScanBarcodeVisible(), view, "Verify scan barcode is visible");
		} else
			Assert.assertEquals(managetickets.isViewButtonVisible(), view, "Verify view button is visible");
		utils.navigateTo("/user/logout");
	//	SuperAdminPanel superAdminPanel = new SuperAdminPanel(driverType, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		utils.navigateTo("/user/login");
		superAdminPanel.login("", "");
		utils.navigateTo("/admin/config/site-settings");
		utils.scrollingToBottomofAPage();		
		superAdminPanel.clickDownloadToggleButton(true);
		superAdminPanel.clickSellToggleButton(true);
		superAdminPanel.clickSendToggleButton(true);
		superAdminPanel.clickDonateToggleButton(true);
		superAdminPanel.clickBarcodeToggleButton(true);
		superAdminPanel.save();
	}
	
	@Test(groups={"smoke","regression","ticketsFunctional","prod"}, priority=44, enabled = false)
	public void verifyActionButtonsTwoenabled() throws Exception {
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))
			throw new SkipException("Skipped");
		load("/user/login");
		SuperAdminPanel superAdminPanel = new SuperAdminPanel(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		superAdminPanel.login("", "");
		utils.navigateTo("/admin/config/site-settings");
		utils.scrollingToBottomofAPage();
		superAdminPanel.clickDownloadToggleButton(true);
		superAdminPanel.clickSellToggleButton(true);
		superAdminPanel.clickSendToggleButton(false);
		superAdminPanel.clickDonateToggleButton(false);
		superAdminPanel.clickBarcodeToggleButton(false);
		superAdminPanel.save();
		load("/user/logout");
		
		HashMap<Integer,ManageticketsAPI.Event> events = manageticketsapi.getEventIdWithTktsDetails();
		Assert.assertNotNull(events);
		int eventId = -1;
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))
			eventId = manageticketsapi.getEventIdWithMaxTktsHavingRenderBarcode(events);
		else 
			eventId = manageticketsapi.getEventIdWithMaxTkts(events);
		Assert.assertNotEquals(eventId, -1);
		System.out.println(eventId);
		load("/tickets#/" + eventId);
		homepage.login("", "", null, true);
		Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
		
		boolean donate = utils.getManageTicketConfiguration("donate");
		boolean sell = utils.getManageTicketConfiguration("sell");
		boolean send = utils.getManageTicketConfiguration("send");
		boolean view;
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			view = utils.getManageTicketConfiguration("mobile_enabled");
		} else
			view = utils.getManageTicketConfiguration("download");
		
		Assert.assertTrue(sell, "Verify sell button is visible");
		Assert.assertFalse(send, "Verify send button is visible");
		Assert.assertFalse(donate, "Verify donate button is visible");
		
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			Assert.assertFalse(view, "Verify scan barcode is visible");
		} else
			Assert.assertTrue(view, "Verify view button is visible");
		
		Assert.assertEquals(managetickets.isSellButtonVisible(null), sell, "Verify sell button is visible");
		Assert.assertEquals(managetickets.isSendButtonVisible(null), send, "Verify send button is visible");
		Assert.assertEquals(managetickets.isDonateButtonVisible(null), donate, "Verify donate button is visible");
		
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			Assert.assertEquals(managetickets.isScanBarcodeVisible(), view, "Verify scan barcode is visible");
		} else
			Assert.assertEquals(managetickets.isViewButtonVisible(), view, "Verify view button is visible");
		utils.navigateTo("/user/logout");
		//	SuperAdminPanel superAdminPanel = new SuperAdminPanel(driverType, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		utils.navigateTo("/user/login");
		superAdminPanel.login("", "");
		utils.navigateTo("/admin/config/site-settings");
		utils.scrollingToBottomofAPage();		
		superAdminPanel.clickDownloadToggleButton(true);
		superAdminPanel.clickSellToggleButton(true);
		superAdminPanel.clickSendToggleButton(true);
		superAdminPanel.clickDonateToggleButton(true);
		superAdminPanel.clickBarcodeToggleButton(true);
		superAdminPanel.save();
	}
	
	@Test(groups={"smoke","ticketsFunctional","regression"}, priority=45)
	public void verifyInvoiceMemberInformationforSellerProfile() throws Exception{
		String accessToken = manageticketsapi.getAccessToken(Dictionary.get("EMAIL_ADDRESS").trim(),Dictionary.get("PASSWORD").trim());
		manageticketsapi.getAccountId(accessToken);
		Object[] obj = utils.get(host + "/api/v1/member/" + Dictionary.get("member_id")+ "/posting/profile", new String[] {"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[] {"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accessToken});
		InputStream is = (InputStream) obj[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		if(!jsonObject.has("first_name"))
			jsonObject = manageticketsapi.get(host + "/api/v1/member/" + Dictionary.get("member_id"), accessToken);
		
		String firstName = jsonObject.getString("first_name");
		String lastName = jsonObject.getString("last_name");
		if(!jsonObject.has("address")) {
			throw new SkipException("Address field not found");
		}
		JSONObject js=(JSONObject) jsonObject.get("address");
		String StreetAddress1=js.getString("line_1");
		String StreetAddress2=js.getString("line_2");
		String city=js.getString("city");
		String zipcode=js.getString("postal_code");
		String State=js.getString("region");
		String Country=js.getString("country");
		String phone2=js.getString("home_phone");
		String phone1=js.getString("mobile_phone");
		  
		obj = utils.get(Environment.get("APP_URL") + "/api/countries?_format=json",new String[] {"User-Agent", "Accept", "Accept-Language"},new String[] {"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", "en-US,en;q=0.5"});
		is = (InputStream) obj[0];
		JSONObject jsonObject1 = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		String Country1 = jsonObject1.getString(Country);
		 
		obj = utils.get(Environment.get("APP_URL") + "/api/regions/"+Country+"?_format=json",new String[] {"User-Agent", "Accept", "Accept-Language"},new String[] {"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", "en-US,en;q=0.5"});
		is = (InputStream) obj[0];
		jsonObject1 = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		String region = jsonObject1.getString(State);
	
		String[] tickets = manageticketsapi.getResaleDetails(true, "event", false, false);
		String Tkt = tickets[0];
		String[] ticket=Tkt.split("\\.");
		load("/tickets#/"+ticket[0]);
		homepage.login("", "", null, true);
		managetickets.clickSellTickets(null);
		managetickets.selectSeatInPopUp(Tkt, ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
		managetickets.clickContinue();
		managetickets.typeEarningPrice(new String[]{Dictionary.get("EarningPrice")});
		managetickets.clickContinue();
		SoftAssert.assertTrue(managetickets.getSellerAddress(firstName).contains(firstName));
		BaseUtil.sync(1000L);
	    managetickets.clickEditSellerProfile();
	    
		Assert.assertEquals(managetickets.SellerfirstName(firstName), firstName ,"First Name is matching");
		Assert.assertEquals(managetickets.SellerLastName(),lastName ,"Last Name is matching");
		Assert.assertEquals(managetickets.sellerZip(),zipcode ,"ZipCode is matching");
		Assert.assertEquals(managetickets.sellerAddress1(), StreetAddress1 ,"Street Address1 is matching");
		Assert.assertEquals(managetickets.sellerAddress2(), StreetAddress2 ,"Street Address2 is matching");
		Assert.assertEquals(managetickets.sellerState(),region ,"State is matching");
		Assert.assertEquals(managetickets.sellerCity(),city ,"City is matching");
		Assert.assertEquals(managetickets.sellerCountry(), Country1 ,"Country is matching");
		Assert.assertEquals(managetickets.sellerMobile(),phone1 ,"Mobile Phone No. is matching");
		Assert.assertEquals(managetickets.sellerHomePhone(),phone2 ,"Home Phone No. is matching");
	}
	
	@Test(groups={"smoke","regression","ticketsUi","prod"}, dataProvider="devices", priority = 1)
	public void verifyManageTicketsPage(TestDevice device) throws Exception{
		load("/tickets");
		homepage.login("", "", device, true);
		hamburger.clickManageTickets();
		
		Assert.assertTrue(managetickets.isManageTicketsListDisplayed() , "Verify manage tickets list is displayed");
		checkLayout(Dictionary.get("SPEC_FILE_NAME").trim(), device.getTags());
	}
	
	@Test(groups={"smoke","regression","ticketsUi","prod"}, dataProvider="devices", priority = 2)
	public void verifyTicketsListingPage(TestDevice device) throws Exception{
		load("/tickets");
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		homepage.login("", "", device, true);
		hamburger.clickManageTickets();
		
		managetickets.clickFirstManageTickets();
		Assert.assertTrue(managetickets.isTicketsListDisplayed(device) , "Verify tickets listing page is displayed");
		checkLayout(Dictionary.get("SPEC_FILE_NAME").trim(), device.getTags());
	}
	
	@Test(groups={"smoke","regression","ticketsUi"}, dataProvider="devices", priority = 3)
	public void verifyDownloadTicket(TestDevice device) throws Exception{
		if(!device.getName().trim().equalsIgnoreCase("mobile")){
			//If User Pass credentails from Jenkins
			utils.credentials_jenkins();
			String[] Tkt= manageticketsapi.getRenderDetails(false, "all", false, false);
			String[] ticket = Tkt[0].split("\\.");
			load("/tickets#/"+ticket[0]);
			homepage.login("", "", device, true);
			managetickets.clickDownloadTickets();
			Assert.assertTrue(managetickets.isDownloadTicketsTextVisible() , "Verify Download Tickets text is displayed on Pop Up");
			checkLayout(Dictionary.get("SPEC_FILE_NAME").trim(), device.getTags());
		} else {
			throw new SkipException("Skipped");
		}
	}
	
	@Test(groups={"smoke","regression","ticketsUi"}, dataProvider="devices", priority = 4)
	public void verifySendTicket(TestDevice device) throws Exception{
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] Tkt= manageticketsapi.getTransferDetails(false, "all", false, false, false, false);
		String[] ticket=Tkt[0].split("\\.");
		load("/tickets#/"+ticket[0]);
		homepage.login("", "", device, true);
		managetickets.clickSendTickets(device);
		Assert.assertTrue(managetickets.isSendTicketsTextVisible() , "Verify SEND/DONATE Tickets text is displayed on Pop Up");
		checkLayout(Dictionary.get("SPEC_FILE_NAME").trim(), device.getTags());
	}
	
	@Test(groups={"smoke","regression","ticketsUi"}, dataProvider="devices", priority = 5)
	public void verifySellTicket(TestDevice device) throws Exception{
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] Tkt= manageticketsapi.getResaleDetails(false, "all", false, false, false);
		String[] ticket=Tkt[0].split("\\.");
		load("/tickets#/"+ticket[0]);
		homepage.login("", "", device, true);
		  
		managetickets.clickSellTickets(device);
		Assert.assertTrue(managetickets.isSellTicketsTextVisible() , "Verify SELL Tickets text is displayed on Pop Up");
		checkLayout(Dictionary.get("SPEC_FILE_NAME").trim(), device.getTags());
	}
	
	@Test(groups={"smoke","regression","ticketsUi"}, dataProvider="devices", priority = 6)
	public void verifyDonateTicket(TestDevice device) throws Exception{
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] Tkt= manageticketsapi.getDonateDetails(false, "all", false, false, false);
		String[] ticket=Tkt[0].split("\\.");
		load("/tickets#/"+ticket[0]);
		homepage.login("", "", device, true);
		
		managetickets.clickDonateTickets(device);
		Assert.assertTrue(managetickets.isDonateTicketsTextVisible() , "Verify DONATE Tickets text is displayed on Pop Up");
		checkLayout(Dictionary.get("SPEC_FILE_NAME").trim(), device.getTags());
	}
	
	@Test(groups={"smoke","regression","ticketsUi","prod"}, dataProvider="devices", priority = 1)
	public void verifyManageTicketsGridPage(TestDevice device) throws Exception{
		load("/tickets");
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		homepage.login("", "", device, true);
//		hamburger.clickManageTickets();
		Assert.assertTrue(managetickets.isManageTicketsListDisplayed() , "Verify manage tickets list is displayed");
		managetickets.clickticketlistview();
		checkLayout(Dictionary.get("SPEC_FILE_NAME").trim(), device.getTags());
	}
	
	@Test(groups={"smoke","regression","ticketsFunctional","sendFunctional","prod", "manageTicketsApi"}, priority=34)
	public void verifyDrupalSendTicketsAPI() throws Exception{
		String emailaddress = Dictionary.get("EMAIL_ADDRESS");
		String password = Dictionary.get("PASSWORD");
		String[] tickets = manageticketsapi.getTransferDetails(true, "event", false, false, false);
		String Tkt = tickets[0];
		String[] ticket = tickets[0].split("\\.");
		System.out.println(Tkt);
		String cookies = invoice.loginThruDrupalApi(emailaddress, password);
		invoice.getTerms(cookies);
		String token = invoice.getCsrfToken(cookies);
		manageticketsapi.sendTicketsDrupal(Integer.valueOf(ticket[0]), Tkt, cookies, token);
	}
		
	@Test(groups={"smoke","regression","ticketsFunctional","donateFunctional", "manageTicketsApi"}, priority=31)
	public void verifyDrupalDonateAPI() throws Exception {
		String emailaddress = Dictionary.get("EMAIL_ADDRESS");
		String password = Dictionary.get("PASSWORD");
		String[] Tkt = manageticketsapi.getDonateDetails(true, "event", false, false);
		String[] eventid=Tkt[0].split("\\.");
		String event_id=eventid[0];
		Integer[] charityId= manageticketsapi.getCharityId(event_id);
		Integer charity = charityId[0];
		String cookies = invoice.loginThruDrupalApi(emailaddress, password);
		invoice.getTerms(cookies);
		String token = invoice.getCsrfToken(cookies);
		manageticketsapi.donateTicketsDrupal(Tkt[0],charity.intValue(),cookies,token);
	}
		
	@Test(groups={"smoke","regression","ticketsFunctional","sellFunctional","prod", "manageTicketsApi"}, priority=31)
	public void verifyDrupalSellTicketsAPI() throws Exception {
		String emailaddress = Dictionary.get("EMAIL_ADDRESS");
		String password = Dictionary.get("PASSWORD");
		String[] tickets = manageticketsapi.getResaleDetails(true, "event", false, false);
		String Tkt = tickets[0];
		String cookies = invoice.loginThruDrupalApi(emailaddress, password);
		invoice.getTerms(cookies);
		String token = invoice.getCsrfToken(cookies);
		try {
			manageticketsapi.sellticketsDrupalAPI(new String[]{Tkt}, cookies, token);
		} finally {
			Integer[] postings = manageticketsapi.getPostingId(new String[]{Tkt});
			if(postings.length > 0) {
				int postingId = postings[0];
				manageticketsapi.deletePosting(postingId);
			}
		}
	}
	
	@Test(groups={"smoke", "regression","ticketsFunctional","sendFunctional", "manageTicketsApi"}, priority=31)
	public void verifyDrupalClaimTicketsAPI() throws Exception {
		String[] tickets = manageticketsapi.getTransferDetails(true, "event", false, false, false);
		System.out.println("Tkt: " + tickets[0]);
		String Tkt = tickets[0];
		String[] ticket = tickets[0].split("\\.");
		manageticketsapi.sendTickets(Integer.valueOf(ticket[0]), new String[]{Tkt});
		String[] transferIds = manageticketsapi.getTransferID(new String[]{tickets[0]});
		String invite_id = transferIds[0];
		System.out.println("invite_id: " + invite_id);
		load("/");
		homepage.createaccount(null, false);
		Set<Cookie> cookies = BaseUtil.getDriver().manage().getCookies();
		Iterator<Cookie> iter = cookies.iterator();
	  	String _cookies = "";
	  	while(iter.hasNext()) {
	  		Cookie cookie = iter.next();
	  		_cookies += cookie.getName() + "=" + cookie.getValue() + ";";
	  	}
		String token = invoice.getCsrfToken(_cookies);
		try {
			manageticketsapi.claimTicketsDrupal(invite_id, Tkt, _cookies, token);
		} finally {
			transferIds = manageticketsapi.getTransferID(new String[]{Tkt});
			if(transferIds.length > 0)
				manageticketsapi.deleteTransfer(transferIds[0]);
		}
	}
	
	@Test(groups={"smoke","regression","ticketsFunctional","viewFunctional","prod"}, priority=32)
	public void verifyTicketDeferredDelivery() throws Exception {
		if(!utils.getManageTicketConfiguration("mobile_enabled"))
			  throw new SkipException("Mobile_Enabled is not enabled in CMS");
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		String[] Tkts = manageticketsapi.getRenderDeferredDelivery(true, "event", false, false);
		String Tkt = Tkts[0];
		String[] ticket=Tkt.split("\\.");
		load("/tickets#/"+ticket[0]);
		homepage.login("", "", null, true);
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			Assert.assertFalse(managetickets.verifyScanBarcodeState(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt));
		}
		else {
			Assert.assertFalse(managetickets.isViewButtonEnable());	
		}
	}
	
	@Test(groups={"smoke","regression","user_journey","criticalbusiness", "prod", "nonstp", "ticketsFunctional", "ViewFunctional"}, priority = 33)
	public void verifyBarcodeReader() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression", "ticketsFunctional","ViewFunctional"}, priority = 34)
	public void verifyReprintTicket() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "criticalbusiness", "prod", "ticketsFunctional", "sendNew" }, priority = 4)
	public void verifyReclaimTicketExistingUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "criticalbusiness", "prod", "ticketsFunctional", "sendNew" }, priority = 5)
	public void verifySendTicketExistingUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "criticalbusiness", "prod", "ticketsFunctional", "sendNew" }, priority = 6)
	public void verifyClaimTicketExistingUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 7)
	public void verifySendMultipleTicketExistingUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 8)
	public void sendTicketWithParkingPassNew() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 9)
	public void sendMultipleTicketsWithMultipleParkingPassNew() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 10)
	public void sendTicketWithMultipleParkingPassNew() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 11)
	public void sendParkingTicket() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 12, dependsOnMethods="verifyClaimTicketNewUser",enabled=false)
	public void verifyClaimTicketAnotherUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 13, dependsOnMethods="verifyClaimTicketNewUser",enabled=false)
	public void verifyClaimParkingTicketNew() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 1,enabled=false)
	public void verifyClaimTicketNewUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	//dependsOnMethods="verifyClaimTicketNewUser"

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 15, dependsOnMethods="verifyClaimTicketNewUser", enabled=false)
    public void verifyUnableToClaimAfterReclaim() throws Throwable {
        runScenario(Dictionary.get("SCENARIO"));
    }

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 16)
	public void verifySendDisabledForBlankSpaces() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "ticketDetails" }, priority = 17)
	public void verifyTicketPrice() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "ticketDetails" }, priority = 18)
	public void verifyMakePaymentInvoice() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional" }, priority = 19)
	public void verifyEventSorting() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional" }, priority = 20)
	public void verifyTicketsFiltering() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "ticketDetails" }, priority = 21)
	public void verifyBarcodeEnableDisable() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 22, enabled = false)
	public void verifyClaimWithEmail() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 23, enabled = false)
	public void verifyDeclineWithEmail() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 24 , enabled= false)
	public void verifyBulkTransferTickets() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 1)
	public void bulkTransferTicket() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 24)
	public void claimTicketDashboard() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 1)
	public void verifyBulkModel() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}


	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 1)
	public void verifyBulkReclaim() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 1)
	public void verifyBulkTwoEvents() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

    @Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 1)
    public void verifyBulkDecline() throws Throwable {
        runScenario(Dictionary.get("SCENARIO"));
    }
    
    @Test(groups = { "smoke", "regression", "ticketsFunctional", "sendNew" }, priority = 1)
    public void verifyDonateTicketsEDP() throws Throwable {
        runScenario(Dictionary.get("SCENARIO"));
    }
    
	@Test(groups = { "smoke", "regression", "criticalbusiness", "prod", "ticketsFunctional", "sendNew" }, priority = 2)
	public void verifySendTicketwithExistingUserEDP() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	} 
	
	@Test(groups = { "smoke", "regression", "criticalbusiness", "prod", "ticketsFunctional", "sendNew" }, priority = 2)
	public void verifySeatsAndTransfer() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	} 
	
	@Test(groups = { "smoke", "regression", "criticalbusiness", "prod", "ticketsFunctional" }, priority = 2)
	public void enabledSecuredBarcodeCMS() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "criticalbusiness", "prod", "ticketsFunctional" }, priority = 2)
	public void verifySecureBarcode() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "criticalbusiness", "prod", "ticketsFunctional" }, priority = 2)
	public void verifySendFunctionalityEDPPhase3() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "criticalbusiness", "prod", "ticketsFunctional" }, priority = 2)
	public void verifyDeclineEDP() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	@Test
	public void verifyTicketsAreSortedInBulkTransfer() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
}
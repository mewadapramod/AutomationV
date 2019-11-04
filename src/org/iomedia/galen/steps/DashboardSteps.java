package org.iomedia.galen.steps;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import cucumber.api.java.en.And;
import org.iomedia.common.BaseUtil;
import org.iomedia.framework.SoftAssert;
import org.iomedia.galen.common.ManageticketsAPI;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.common.ManageticketsAPI.Event;
import org.iomedia.galen.pages.DashboardHeader;
import org.iomedia.galen.pages.DashboardSection;
import org.iomedia.galen.pages.Hamburger;
import org.iomedia.galen.pages.ManageTicket;
import org.iomedia.galen.pages.MobileSectionTabs;
import org.testng.SkipException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DashboardSteps {
	
	DashboardSection section;
	DashboardHeader header;
	org.iomedia.framework.Assert Assert;
	String driverType;
	Hamburger hamburger;
	Utils utils;
	BaseUtil base;
	MobileSectionTabs tabs;
	ManageticketsAPI api;
    ManageTicket managetickets;
	
	public DashboardSteps(DashboardSection section, ManageTicket managetickets, DashboardHeader header, org.iomedia.framework.Assert Assert, Hamburger hamburger, Utils utils, BaseUtil base, MobileSectionTabs tabs, ManageticketsAPI api) {
		this.section = section;
		this.header = header;
		this.Assert = Assert;
		this.hamburger = hamburger;
		this.utils = utils;
		this.base = base;
		this.tabs = tabs;
		this.api = api;
		this.driverType = section.driverFactory.getDriverType().get();
		this.managetickets = managetickets;
	}
	
	@Then("^User logged in successfully$")
	public void user_logged_in_successfully() {
		utils.sync(200l);
		utils.getDriver().navigate().to(base.Environment.get("APP_URL")+"/dashboard");
		//Assert.assertTrue(header.waitForDasboardHeader(), "Verify dashboard header is displayed");
		//Assert.assertTrue(section.waitForDasboardSection(null), "Verify dashboard section is displayed");
	}
	
	@When("^User clicks your account$")
	public void user_clicks_your_account() throws InterruptedException {
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			utils.navigateTo("/classic-amgr?redirect_url=account/settings");
		} else {
			section.clickAccount();
			for(String handle :  section.getDriver().getWindowHandles()){
		    	section.getDriver().switchTo().window(handle);
		    }
		}
	}
	
	@When("^User clicks switch accounts$")
	public void user_clicks_switch_accounts() throws InterruptedException {
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")){
			hamburger.clickMobileHamburger();
			section.clickSwitchAccountMobile();
		}
		else {
			section.clickSwitchAccounts();
		}
	}
	
	@When("^Switch account to (.+)$")
	public void switch_account_to(String accountId) {
		accountId = (String) base.getGDValue(accountId);
		section.selectAccount(accountId);
		section.clickSwitchAccount();
	}
	
	@Then("^User switched to (.+)$")
	public void user_switched_to(String name) {
		name = (String) base.getGDValue(name);
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")){
			Assert.assertTrue(header.waitForDasboardHeader(), "Verify dashboard header is displayed");
			Assert.assertTrue(section.waitForDasboardSection(null), "Verify dashboard section is displayed");
			tabs.clickQuickLinks();
		}
		Assert.assertEquals(header.getSwitchedAccountName(name), name);
		
	}
	
	@Then("^SSO done successfully to NAM with correct (.+) and (.+)$")
	public void sso_done_successfully_to_nam_with_correct_and(String accountName, String accountId) {
		accountId = (String) base.getGDValue(accountId);
		accountName = (String) base.getGDValue(accountName);
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			tabs.clickQuickLinks();
		}
		if(!utils.getDriver().getCurrentUrl().contains("dashboard")) {
			utils.getDriver().navigate().to(utils.Environment.get("APP_URL")+"/dashboard");
		}
		Assert.assertEquals(header.getAccountName(), accountName , "Account Name is matching. SSO is successful");
	    Assert.assertEquals(header.getAccountId(), accountId, "Account ID is matching. SSO is successful");
	}
	
	@Given("^Change password to (.+) for (.+) and (.+)$")
	public void change_password_to_for_and(String newpassword, String emailaddress, String password) throws Exception {
		newpassword = (String) base.getGDValue(newpassword);
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		
		boolean can_edit_password = api.canUserEditPassword();
		if(can_edit_password) {
			if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")){
			//	hamburger.clickMobileHamburger();
				section.clickMobileUserMenu();
				section.clickChangePasswordMobile();
			}
			else {
				section.clickChangePasswordDesktop();
			}
		    section.typeChangePassword(password, newpassword);
			section.clickSavePassword();
			Assert.assertTrue(header.waitForDasboardHeader(), "Verify dashboard header is displayed");
			base.Dictionary.put("NEW_PASSWORD", newpassword);
		}
	}
	
	@Then("^Verify events details on dashboard for (.+) and (.+)$")
	public void verifyEventsSummary(String emailaddress, String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		HashMap<Integer, ManageticketsAPI.Event> events = api.getEventIdWithTktsDetails(emailaddress, password);
		Assert.assertNotNull(events);
		HashMap<Integer, String> levents = section.getListOfEvents();
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
				if(levents.containsKey(eventId)) {
					Assert.assertEquals(levents.get(eventId), text, "Verify ticket count for event Id : " + eventId);
				}
			}
		}
		
		if(!base.Environment.get("event_id").trim().equalsIgnoreCase("")) {
			String ticketCountValue = levents.get(Integer.valueOf(base.Environment.get("event_id").trim())).trim();
			base.Dictionary.put("TICKETS_COUNT", ticketCountValue.split(" ")[0]);
		}
	}

	@And("User is able to see tickets transferred info bar on Dashboard with Decline and Accept Button")
	public void verifyInfobar() {
		section.verifyInfobar();

	}

	@And("User clicks See Details Link")
	public void clickSeeDetails() {
		section.clickSeeDetails();
	}

	@Then("Transferred tickets are seen")
	public void verifyTransferredTickets() {
		if (managetickets.checkenableEDP()==true) {
			section.verifyTransferredTicketsEDP(base.Dictionary.get("TicketNumberSectionTransfer"),base.Dictionary.get("EventNameSectionTransfer"));
		}else {
			section.verifyTransferredTickets(base.Dictionary.get("TicketNumberSectionTransfer"),base.Dictionary.get("EventNameSectionTransfer"));
		}
	}

	@Then("Tickets transferred are seen for two events")
	public void verifyTransferredTicketstwoevents() {
		int num = Integer.parseInt(base.Dictionary.get("TicketNumberSectionTransfer1"))+Integer.parseInt(base.Dictionary.get("TicketNumberSectionTransfer2"));
		base.Dictionary.put("TicketNumberTwoEvents", String.valueOf(num));
		if (managetickets.checkenableEDP()==true) {
			section.verifyTransferredTicketsTwoEventsEDP(num, base.Dictionary.get("EventNameSectionTransfer1"),base.Dictionary.get("EventNameSectionTransfer2"));
		}else {
			section.verifyTransferredTicketsTwoEvents(num, base.Dictionary.get("EventNameSectionTransfer1"),base.Dictionary.get("EventNameSectionTransfer2"));
		}
	}

	@And("User Accepts Tickets Transfer")
	public void acceptTransfer() {
		section.acceptTransfer();
	}

	@And("User Declines Tickets Transfer")
	public void declineTransfer() {
		if (managetickets.checkenableEDP()==true) {
			throw new SkipException("Decline Features is not implemented on EDP Phase3");
		}else {
			section.declineTransfer();	
		}
		
	}

	@Then("Success Message is Seen with Go to Event Button")
	public void successMsg() {
		if (managetickets.checkenableEDP()==true) {
			section.successMsgEDP();
		}else {
			section.successMsg();
		}
	}


	@Then("Decline Message is Seen with Go to Event Button")
	public void declineMsg() {
		section.declineMsg();

	}

	@And("User is navigated to events page by clicking Go to Event Button")
	public void eventPage() {
		if (managetickets.checkenableEDP()==true) {
			//not present in EDP Phase 3
		}else {
		section.verifyEventPage(base.Dictionary.get("EventNameSectionTransfer"));
		}
	}

	@Then("Transfer Tickets info bar disappears")
	public void transferInfoDisappears() {
		section.verifyInfoBarDisappear();
	}

	@And("User declines all pending transfers if any")
	public void cleanPendingTransfers() {
		section.cleanPendingTransfer();
	}
	
	@And("User accept all pending transfers if any")
	public void cleanAcceptTransfers() {
		section.cleanAcceptTransfer();
	}

}

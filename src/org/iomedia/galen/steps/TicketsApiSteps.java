package org.iomedia.galen.steps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iomedia.common.BaseUtil;
import org.iomedia.galen.common.AccessToken;
import org.iomedia.galen.common.ManageticketsAAPI;
import org.iomedia.galen.common.ManageticketsAPI;
import org.iomedia.galen.pages.DashboardSection;
import org.iomedia.galen.pages.ManageTicket;
import org.iomedia.galen.pages.TicketsNew;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.common.ManageticketsAPI.Event;
import org.testng.SkipException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TicketsApiSteps {
	ManageticketsAAPI aapi;
	DashboardSection section;
	ManageticketsAPI api;	
	BaseUtil base;
	ManageTicket managetickets;
	TicketsNew ticket;
	Utils utils;
	AccessToken accessToken;
	org.iomedia.framework.Assert Assert;
	org.iomedia.framework.SoftAssert SoftAssert;
	String driverType;
	
	boolean CAN_TRANSFER, CAN_RESALE, CAN_RENDER, CAN_RENDER_FILE, CAN_RENDER_BARCODE, CAN_RENDER_PASSBOOK, CAN_DONATE_CHARITY;
	
	public TicketsApiSteps(DashboardSection section,Utils utils,ManageticketsAAPI aapi,TicketsNew ticket,ManageticketsAPI api, BaseUtil base, AccessToken accessToken, ManageTicket managetickets, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert) {
		this.aapi=aapi;
		this.section=section;
		this.utils=utils;
		this.api = api;
		this.ticket = ticket;
		this.base = base;
		this.driverType = base.driverFactory.getDriverType().get();
		this.accessToken = accessToken;	
		this.Assert = Assert;
		this.SoftAssert = SoftAssert;
		this.managetickets = managetickets;
		CAN_TRANSFER = CAN_RESALE = CAN_RENDER = CAN_RENDER_FILE = CAN_RENDER_BARCODE = CAN_RENDER_PASSBOOK = CAN_DONATE_CHARITY = true;
	}

	@Given("^Customer details are fetched for (.+) and (.+)$")
	public void customer_details_are_fetched(String emailaddress,String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		Map<String, Object> names = api.getCustomerName(emailaddress, password);
		api.Dictionary.put("CustomerName", names.get("ACTUAL_CUST_NAME").toString().trim());
		api.Dictionary.put("NickName", names.containsKey("NICK_NAME") ? names.get("NICK_NAME").toString().trim() : "");
		api.Dictionary.put("AccountName", names.get("CUST_NAME").toString().trim());
		api.Dictionary.put("StpAccountName", names.get("STP_CUST_NAME").toString().trim());
	}
	
	@Given("^Generate member response with (.+) and (.+)$")
	public void generate_member_response_with_and(String emailaddress, String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		accessToken.getMemberResponse(emailaddress, password);
		if(Integer.parseInt(base.Dictionary.get("associatedCount")) < 2){
			throw new SkipException("No Associated Accounts Available");
		} 	
	}
	
//	@Given("^Get transfer ticket ID for (.+) and (.+)$")
//	public void get_transfer_ticket_ID(String emailaddress, String password) throws Exception {
//		emailaddress = (String) base.getGDValue(emailaddress);
//		password = (String) base.getGDValue(password);
//		String[] Tkt = api.getTransferDetails(emailaddress, password, true, "event", false, false, false);
//		base.Dictionary.put("TransferTicketID", Tkt[0]);
//		base.Dictionary.put("EventId", Tkt[0].split("\\.")[0]);
//	}
	
	@Given("^Get transfer ticket ID for (.+) and (.+)$")
	public void get_transfer_ticket_ID(String emailaddress, String password) throws Exception {
     	String EventID = System.getProperty("EventId") != null && !System.getProperty("EventId").trim().equalsIgnoreCase("") ? System.getProperty("EventId").trim() : "";		
		System.out.println("Event ID  from POM :: "+   EventID);
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		String[] Tkt = aapi.getTransferDetails(emailaddress, password, true, "event", false, false, false);
		base.Dictionary.put("TransferTicketID", Tkt[0]);
		if(EventID==null || EventID=="") {			
		base.Dictionary.put("EventId", Tkt[0].split("\\.")[0]);
		}
		else {
			base.Dictionary.put("EventId", EventID);		
			}
			System.out.println("EventID from System ::  "+ base.Dictionary.get("EventId"));
	}
	
	@Given("^Get donate ticket ID for (.+) and (.+)$")
	public void get_ticketID_for_donate(String emailaddress, String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		String[] Tkt = api.getDonateDetails(emailaddress, password, true, "event", false, false, false);
		base.Dictionary.put("DonateTicketID", Tkt[0]);
		base.Dictionary.put("EventId", Tkt[0].split("\\.")[0]);
	}
	
	/*@Given("^Save ticket flags for ticket Id (.+) using (.+) and (.+)$")
	public void save_ticket_flags(String ticketId, String emailaddress, String password) throws Exception {
	emailaddress = (String) base.getGDValue(emailaddress);
	password = (String) base.getGDValue(password);
	ticketId = (String) base.getGDValue(ticketId);
	String[] ticket = ticketId.split("\\.");
	base.Dictionary.put("Event_Id", ticket[0]);
	base.Dictionary.put("EventId", ticket[0]);
	Boolean[] flags = api.getTicketFlags(ticketId, emailaddress , password);
	CAN_TRANSFER = flags[0];
	CAN_RESALE = flags[1];
	CAN_RENDER = flags[2];
	CAN_RENDER_FILE = flags[3];
	CAN_RENDER_BARCODE = flags[4];
	CAN_RENDER_PASSBOOK = flags[5];
	CAN_DONATE_CHARITY = flags[6];
	}*/
	
	@Given("^Save ticket flags for ticket Id (.+) using (.+) and (.+)$")
	public void save_ticket_flags(String ticketId, String emailaddress, String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		ticketId = (String) base.getGDValue(ticketId);
		String[] ticket = ticketId.split("\\.");
		base.Dictionary.put("Event_Id", ticket[0]);
		base.Dictionary.put("EventId", ticket[0]);
		Boolean[] flags = aapi.getTicketFlags(ticketId, emailaddress , password);
		CAN_TRANSFER = flags[0];
		CAN_RESALE = flags[1];
		CAN_RENDER = flags[2];
		CAN_RENDER_FILE = flags[3];
		CAN_RENDER_BARCODE = flags[4];
		CAN_RENDER_PASSBOOK = flags[5];
		CAN_DONATE_CHARITY = flags[6];
	}
	
	@Then("Verify ticket flags for (.+), (.+) and (.+)")
	public void verify_ticket_flags(String ticketId, String emailaddress, String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		ticketId = (String) base.getGDValue(ticketId);
		SoftAssert.assertEquals(aapi.getTicketFlags(ticketId, emailaddress, password), new Boolean[] {CAN_TRANSFER, CAN_RESALE, CAN_RENDER, CAN_RENDER_FILE, CAN_RENDER_BARCODE, CAN_RENDER_PASSBOOK, CAN_DONATE_CHARITY}, "Verify the ticket flags");
	}
	
	@Then("Verify False ticket flags for (.+), (.+) and (.+)")
	public void verify_ticket_flags_after_action(String ticketId, String emailaddress, String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		ticketId = (String) base.getGDValue(ticketId);
		Assert.assertEquals(api.getTicketFlags(ticketId, emailaddress, password), new Boolean[] {false, false, false, false, false, false, false}, "Verify the ticket flags after user action performed");
	}
	
	@Given("^Send Ticket new account using (.+)$")
	public void send_ticket_new(String ticketId) throws NumberFormatException, Exception {
		ticketId = (String) base.getGDValue(ticketId);
		String[] ticket = ticketId.split("\\.");
	//	api.sendTickets(Integer.valueOf(ticket[0]), new String[]{ticketId});
		aapi.sendTickets(Integer.valueOf(ticket[0]), new String[]{ticketId}, true);
	}
	
	@Given("^Send Ticket using (.+)$")
	public void send_ticket(String ticketId) throws NumberFormatException, Exception {
		ticketId = (String) base.getGDValue(ticketId);
		String[] ticket = ticketId.split("\\.");
	//	api.sendTickets(Integer.valueOf(ticket[0]), new String[]{ticketId});
		aapi.sendTickets(Integer.valueOf(ticket[0]), new String[]{ticketId});
	}
	
	@Given("^User generate TransferId for (.+)$")
	public void user_generate_transferId(String ticketId) throws Exception {
		ticketId = (String) base.getGDValue(ticketId);
		String[] transferIds = aapi.getTransferID(new String[]{ticketId});
		base.Dictionary.put("TransferID", transferIds[0]);
	}
	
	@Given("^Get Resale details of ticket for (.+) and (.+)$")
	public void get_resale_detail(String emailaddress, String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		String[] tickets = api.getResaleDetails(emailaddress, password, true, "event", false, false);
		base.Dictionary.put("ResaleTicketID", tickets[0]);
		base.Dictionary.put("EventId", tickets[0].split("\\.")[0]);
	}
	
	@Given("^User fetch ticketId for selling$")
	public void user_fetch_ticketId() throws Exception {
		String[] tickets = api.getResaleDetails(true, "event", false, false);
		base.Dictionary.put("ResaleTicketID", tickets[0]);
	}
	
	@Given("^Save Event Id for ticket Id (.+)$")
	public void save_event_id(String ticketId) {
		ticketId = (String) base.getGDValue(ticketId);
		String[] ticket=ticketId.split("\\.");
		base.Dictionary.put("Resale_Event_Id", ticket[0]);
	}
	
	@Given("^User sells ticket using API for (.+)$")
	public void user_sell_ticket_api(String ticketId) throws Exception {
		ticketId = (String) base.getGDValue(ticketId);	
		api.selltickets(new String[] {ticketId});
	}
	
	@Given("^User fetches Render details for (.+) and (.+)$")
	public void user_fetch_render_details(String emailaddress,String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		String[] tickets = api.getBarcodeRenderMobileEnabledTickets(emailaddress, password, true, "event", false, false, false, false);
		base.Dictionary.put("RenderTicketID", tickets[0]);	
	}
	
	@When("^fetch Render details for (.+)$")
	public void fetch_render_detail(String EventID) throws Exception {
		EventID = (String) base.getGDValue(EventID);
		String[] tickets = api.getRenderDetails(true, "event", false, false);
		for(int i=0; i<tickets.length;i++) {
			String[] tkt = tickets[i].split("\\.");
			if(tkt[0].equalsIgnoreCase(EventID)) {
				base.Dictionary.put("RenderTicketID", tickets[i]);
				break;
			}
		}
	}
	
	@Given("^Save Render Event Id for (.+)$")
	public void save_render_event_id(String ticketId) {
		ticketId = (String) base.getGDValue(ticketId);
		String[] ticket=ticketId.split("\\.");
		base.Dictionary.put("Render_Event_Id", ticket[0]);
	}
	
	@Then("^User click on render Barcode using (.+) and (.+) for (.+)$")
	public void user_click_render_barcode(String emailaddress,String password, String ticketId) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		ticketId = (String) base.getGDValue(ticketId);
		api.renderBarcode(emailaddress,password,ticketId);
	}
	
	@Then("^Verify all tickets section using (.+) and (.+)$")
	public void fetch_all_events(String emailaddress, String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		
		HashMap<Integer, ManageticketsAPI.Event> events = api.getSingleEventIdWithTktsDetails(emailaddress, password);
		if (events.size()>0) {
			  List<Event> eventsByTktCount = new ArrayList<Event>(events.values());
			  Event event = eventsByTktCount.get(0);
			  String eventid = Integer.toString(event.getId());
			  utils.navigateTo("/myevents#/"+eventid);
			  String accessToken= api.getAccessToken(emailaddress, password);
			  int TicketCount = api.getTicketsCount(event.getId(), accessToken);
			   try {
				   String countText =  managetickets.getTicketsCountTextEDP();
			              if(TicketCount>1) 			   
						  Assert.assertEquals(countText, "My Tickets ("+TicketCount+")", "Total Ticket counts are correctly displayed");			              
						  else
						  Assert.assertEquals(countText, "My Ticket ("+TicketCount+")", "Total Ticket counts are correctly displayed");
			    }  			   
			   catch(Exception e) {				  
				String count = Integer.toString(ticket.getTicketCountText());			
     		  if(TicketCount>1)   			
			  Assert.assertEquals(count+" Tickets", TicketCount+" Tickets", "Total Ticket counts are correctly displayed");
			  else 			
			  Assert.assertEquals(count+" Ticket", TicketCount+" Ticket", "Total Ticket counts are correctly displayed");
     		  }
			   
		}
		else {
			Assert.assertEquals(section.getEventsPlaceholderText(),"There are no events in your account.", "No Events found in the account");
			utils.navigateTo("/tickets");
			Assert.assertEquals(section.getEventsTextinTicketPage(), "You don’t have tickets to any upcoming events.", "No Ticket found for the user");
		}
	}
	
	@When("^User fetch render ticket Ids$")
	public void fetch_eventId_with_max_tickets() throws Exception {
		HashMap<Integer, ManageticketsAPI.Event> events = api.getEventIdWith3RenderTktsDetails();
		int eventId = -1;
		if(base.Dictionary.get("RENDER_TICKET_EVENT_ID").trim().equalsIgnoreCase("")) {
			Assert.assertNotNull(events);
			if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && base.Environment.get("deviceType").trim().equalsIgnoreCase("phone"))
				eventId = api.getEventIdWithMaxTktsHavingRenderBarcode(events);
			else 
				eventId = api.getEventIdWithMaxTkts(events);
			Assert.assertNotEquals(eventId, -1);
		} else {
			eventId = Integer.valueOf(base.Dictionary.get("RENDER_TICKET_EVENT_ID").trim());
		}
		
		base.Dictionary.put("EventID", String.valueOf(eventId));
		
		List<List<String>> ticketIds = api.getTop3TicketIds(eventId, events);
		if(ticketIds.size() < 2)
			throw new SkipException("No event found with more than 1 render tickets");
		
		String tickets = "";
		for(List<String> ticket: ticketIds) {
			String sectionName = ticket.get(0).trim().replace(" ", "%20");
			String rowName = ticket.get(1).trim().replace(" ", "%20");
			String seatNumber = ticket.get(2).trim().replace(" ", "%20");
			String ticketId = String.valueOf(eventId).trim() + "." + sectionName + "." + rowName + "." + seatNumber;
			tickets += ticketId + ",";
		}
		tickets = tickets.trim().substring(0, tickets.trim().length() - 1);
		base.Dictionary.put("RenderBarcodeID", tickets);
		base.Dictionary.put("RenderBarcodeID_Count", String.valueOf(tickets.split(",").length));
	}
}

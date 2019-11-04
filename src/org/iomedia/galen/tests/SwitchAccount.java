package org.iomedia.galen.tests;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.iomedia.framework.Driver;
import org.iomedia.galen.common.AccessToken;
import org.iomedia.galen.common.ManageticketsAAPI;
import org.iomedia.galen.common.ManageticketsAPI;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.pages.AdminLogin;
import org.iomedia.galen.pages.DashboardHeader;
import org.iomedia.galen.pages.DashboardSection;
import org.iomedia.galen.pages.Hamburger;
import org.iomedia.galen.pages.Homepage;
import org.iomedia.galen.pages.Invoice;
import org.iomedia.galen.pages.InvoiceNew;
import org.iomedia.galen.pages.ManageTicket;
import org.iomedia.galen.pages.MobileSectionTabs;
import org.iomedia.galen.pages.TicketsNew;
import org.json.JSONObject;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SwitchAccount extends Driver {

	private AccessToken accessToken;
	private MobileSectionTabs section;
	private AdminLogin adminlogin;
	String host;
	private Homepage homepage;
	private DashboardSection dashboardSection;
	private Hamburger hamburger;
	private DashboardHeader header;
	private Invoice invoice;
	InvoiceNew invoiceNew;
	private Utils utils;
	private ManageTicket managetickets;
	private ManageticketsAPI manageticketsapi;
	private ManageticketsAAPI aapi;
	private String driverType;
	private TicketsNew ticketNew;

	@BeforeMethod(alwaysRun = true)
	public void init() {
		host = Environment.get("TM_HOST").trim();
		homepage = new Homepage(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		accessToken = new AccessToken(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert,
				sTestDetails);
		section = new MobileSectionTabs(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
		adminlogin = new AdminLogin(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		dashboardSection = new DashboardSection(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
		hamburger = new Hamburger(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		header = new DashboardHeader(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert,
				sTestDetails);
		invoice = new Invoice(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		invoiceNew = new InvoiceNew(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		section = new MobileSectionTabs(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
		managetickets = new ManageTicket(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert,
				sTestDetails);
		manageticketsapi = new ManageticketsAPI(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert,
				sTestDetails);
		aapi = new ManageticketsAAPI(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		ticketNew = new TicketsNew(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		driverType = driverFactory.getDriverType().get();
	}

	@Test(groups = { "smoke", "invoicesFunctional", "regression", "prod", "switchAccount" }, priority = 47)
	public void verifyInvoicesSecondarydAccount() throws Exception {
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			throw new SkipException("Skipped");
		}
		if (Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim().equalsIgnoreCase(""))
			throw new SkipException("Skipped");
		accessToken.getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"),
				Dictionary.get("SWITCH_ACC_PASSWORD"));
		if (Integer.parseInt(Dictionary.get("associatedCount")) < 2) {
			throw new SkipException("No Associated Accounts Available");
		}
		load("/");
		homepage.login(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"), Dictionary.get("SWITCH_ACC_PASSWORD"), null, false);
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			hamburger.clickMobileHamburger();
			dashboardSection.clickYourAccountMobile();
		} else {
			dashboardSection.clickYourAccount();
		}
		String accountId = Dictionary.get("accId1").trim();
		dashboardSection.selectAccount(accountId);
		dashboardSection.clickSwitchAccount();
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			section.clickQuickLinks();
		}
		Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("name1")), Dictionary.get("name1"));
		utils.navigateTo("/invoice");
		List<List<String>> sortedList = adminlogin.getSortedInvoiceList(accountId, "");
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		Assert.assertEquals(invoice.getInvoiceList(), sortedList, "Verify invoice list is sorted");

		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			hamburger.clickMobileHamburger();
			dashboardSection.clickYourAccountMobile();
		} else {
			dashboardSection.clickYourAccount();
		}
		dashboardSection.selectAccount(Dictionary.get("accId0").trim());
		dashboardSection.clickSwitchAccount();
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			section.clickQuickLinks();
		}

		if (Dictionary.get("name0").trim().equalsIgnoreCase(Dictionary.get("accId0"))) {
			Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("CUST_NAME")),
					Dictionary.get("CUST_NAME"));
		} else {
			Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("name0")), Dictionary.get("name0"));
		}

		utils.navigateTo("/invoice");
		List<List<String>> sortedList1 = adminlogin.getSortedInvoiceList(Dictionary.get("accId0"), "");
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		Assert.assertEquals(invoice.getInvoiceList(), sortedList1, "Verify invoice list is sorted");
	}

	@Test(groups = { "smoke", "invoicesFunctional", "regression", "prod", "switchAccount" }, priority = 47)
	public void verifyCardInfoSwitchAccount() throws Exception {
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("No need to run on android/ios");
		if (Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim().equalsIgnoreCase(""))
			throw new SkipException("Skipped");
		accessToken.getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"),
				Dictionary.get("SWITCH_ACC_PASSWORD"));
		if (Integer.parseInt(Dictionary.get("associatedCount")) < 2) {
			throw new SkipException("No Associated Accounts Available");
		}

		String accountId = Dictionary.get("accId2").trim();
		String invoicestatus = "UNPAID";
		int invoiceId = adminlogin.getInvoiceListAndId(accountId, invoicestatus);
		if (invoiceId == -1) {
			invoicestatus = "PARTIALLY PAID";
			invoiceId = adminlogin.getInvoiceListAndId(accountId, invoicestatus);
			if (invoiceId == -1) {
				invoicestatus = "PLAN";
				invoiceId = adminlogin.getInvoiceListAndId(accountId, invoicestatus);
			}
		}

		if (invoiceId == -1) {
			throw new SkipException("Didn't found any non-paid invoice");
		}

		adminlogin.getCCQuery(Dictionary.get("accId0"), invoicestatus);
		adminlogin.getCCQueryFieldValues(Dictionary.get(invoicestatus.trim().toUpperCase() + "cc_Query"),
				new String[] { "cc_name_first", "cc_name_last", "cc_postal_code", "cc_address", "cc_exp", "data_mask" },
				invoicestatus);

		if (Dictionary.get(invoicestatus.trim().toUpperCase() + "data_mask").trim().equalsIgnoreCase("")) {
			throw new SkipException("Credit card info not found");
		}

		load("/");
		homepage.login(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"), Dictionary.get("SWITCH_ACC_PASSWORD"), null, false);
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			hamburger.clickMobileHamburger();
			dashboardSection.clickYourAccountMobile();
		} else {
			dashboardSection.clickYourAccount();
		}
		dashboardSection.selectAccount(accountId);
		dashboardSection.clickSwitchAccount();
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			section.clickQuickLinks();
		}
		Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("name2")), Dictionary.get("name2"));
		utils.navigateTo("/invoice");

		Assert.assertTrue(invoice.verifyInvoiceStatus(invoiceId, invoicestatus),
				"Verify " + invoicestatus.trim().toLowerCase() + " invoice is displayed");
		invoice.clickInvoiceLink(invoiceId, null);

		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");

		invoice.clickContinueButton();
		if (invoicestatus.trim().equalsIgnoreCase("UNPAID")
				|| invoicestatus.trim().equalsIgnoreCase("PARTIALLY PAID")) {
			invoice.clickpayInFull();
		}
		invoice.clickContinuePlan();
		Assert.assertEquals(invoice.cardNumber(), invoice.CardlastDigits(invoicestatus),
				"Verify card last digits matches with the result from TM Api");
		Assert.assertEquals(invoice.cardExpiryDate(invoicestatus), invoice.cardExpiry(),
				"Verify card expiry date matches with the result from TM Api");
	}

	@Test(groups = { "smoke", "invoicesFunctional", "regression", "switchAccount" }, priority = 47)
	public void verifyInvoicePaymentSwitchAccount() throws Exception {
		if (Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim().equalsIgnoreCase(""))
			throw new SkipException("Skipped");
		accessToken.getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"),
				Dictionary.get("SWITCH_ACC_PASSWORD"));
		if (Integer.parseInt(Dictionary.get("associatedCount")) < 2) {
			throw new SkipException("No Associated Accounts Available");
		}
		load("/");
		homepage.login(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"), Dictionary.get("SWITCH_ACC_PASSWORD"), null, false);
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			hamburger.clickMobileHamburger();
			dashboardSection.clickYourAccountMobile();
		} else {
			dashboardSection.clickYourAccount();
		}
		dashboardSection.selectAccount(Dictionary.get("accId1").trim());
		dashboardSection.clickSwitchAccount();
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			section.clickQuickLinks();
		}
		Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("name1")), Dictionary.get("name1"));
		utils.navigateTo("/invoice");
		invoice.clickFirstInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		String invDue = invoice.amountDue();
		invoice.clickContinueButton();
		boolean flag = false;
		if (!invoice.isContinuePlanEnabled()) {
			flag = true;
			invoice.clickpayInFull();
		} else
			invoice.waitForPaymentPlanBreakage();
		invoice.clickContinuePlan();
		invoice.clickAddNewCard();
		invoice.typeCardNumber(Dictionary.get("CardNumber"));
		invoice.typeExpiryDate(Dictionary.get("ExpiryDate"), null);
		invoice.typeCardCVV(Dictionary.get("CVV"));
		invoice.clickContinueCard();
		invoice.typeBillingDetails(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Address_1"),
				Dictionary.get("Address_2"), Dictionary.get("Country"), Dictionary.get("State"), Dictionary.get("City"),
				Dictionary.get("ZipCode"));
		invoice.clickContinueBilling();
		String AmtEnter = Dictionary.get("PartialAmt");
		invoice.typePartialAmount(invDue, AmtEnter);
		invoice.selectAcceptTnC();
		String dueAmt;
		double newDueAmt = 0;
		if (!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS")) {
			dueAmt = invoice.getHeaderAmount();
			newDueAmt = Double.parseDouble(dueAmt) - Double.parseDouble(AmtEnter);
		}
		double newInvDue = Double.parseDouble(invDue) - Double.parseDouble(AmtEnter);
		if (flag)
			Assert.assertEquals("Pay In Full", invoice.paymentoption(), "Verify payment option on review page");
		else
			Assert.assertEquals("Pay with Payment Plan", invoice.paymentoption(),
					"Verify payment option on review page");
		invoice.clickConfirmPay();
		Assert.assertEquals(invoice.getSuccessHead(), Dictionary.get("SuccessHeading").trim(),
				"Verify success heading on confirmation page");
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			BaseUtil.navigateBack();
			Assert.assertEquals(
					invoice.getInvoiceBalWhenRefreshed(Integer.valueOf(Dictionary.get("invoiceId")), newInvDue),
					String.format("%.02f", newInvDue), "Verify invoice balance on listing after payment");
		} else {
			Assert.assertEquals(invoice.getHeaderAmountWhenRefreshed(newDueAmt), String.format("%.02f", newDueAmt),
					"Verify invoice balance on header after payment");
			Assert.assertEquals(invoice.getSelectedInvoiceBalWhenRefreshed(newInvDue),
					String.format("%.02f", newInvDue), "Verify invoice balance on listing after payment");
		}

		// invoice.verifyInvoiceStatus(Integer.valueOf(Dictionary.get("invoiceId")),
		// "PLAN");
		adminlogin.beforeMtdInvoiceAPI("", false);
		DecimalFormat df = new DecimalFormat("0.00");
		Assert.assertEquals(df.format(Double.valueOf(newDueAmt)),
				adminlogin.getTotalAmountDue(Dictionary.get("InvoiceListArray")));
		// String invoicestatus = "UNPAID";
		// int invoiceId = adminlogin.getInvoiceListAndId(Dictionary.get("accId2"),
		// invoicestatus);
		// if(invoiceId == -1){
		// invoicestatus = "PARTIALLY PAID";
		// invoiceId = adminlogin.getInvoiceListAndId(Dictionary.get("accId2"),
		// invoicestatus);
		// if(invoiceId == -1){
		// invoicestatus = "PLAN";
		// invoiceId = adminlogin.getInvoiceListAndId(Dictionary.get("accId2"),
		// invoicestatus);
		// }
		// }
		// if(invoiceId == -1){
		// throw new SkipException("Didn't found any non-paid invoice");
		// }
		String cardnum = Dictionary.get("CardNumber");
		adminlogin.getCCQuery(Dictionary.get("accId0"), "");
		Assert.assertTrue(
				Dictionary.get("cc_Query").contains(cardnum.substring(cardnum.length() - 4, cardnum.length())));
	}

	@Test(groups = { "smoke", "ticketsFunctional", "regression", "prod", "switchAccount" }, priority = 46)
	public void verifyTicketsSecondarydAccount() throws Exception {
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			throw new SkipException("Skipped");
		}
		if (Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim().equalsIgnoreCase(""))
			throw new SkipException("Skipped");
		     accessToken.getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"),
				Dictionary.get("SWITCH_ACC_PASSWORD"));
		if (Integer.parseInt(Dictionary.get("associatedCount")) < 2) {
			throw new SkipException("No Associated Accounts Available");
		}
		load("/");
		homepage.login(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"), Dictionary.get("SWITCH_ACC_PASSWORD"), null, false);
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			hamburger.clickMobileHamburger();
			dashboardSection.clickYourAccountMobile();
		} else {
			try {
				dashboardSection.clickYourAccount();
				dashboardSection.getAccountCount();
				}catch(Exception e)
				{
					dashboardSection.clickYourAccount();
				}
		}
		dashboardSection.selectAccount(Dictionary.get("accId1").trim());
		dashboardSection.clickSwitchAccount();
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			section.clickQuickLinks();
		}
		Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("name1")), Dictionary.get("name1"));
		Dictionary.put("member_id", Dictionary.get("memberId1"));
		utils.navigateTo("/tickets");
		Assert.assertTrue(managetickets.isManageTicketsListDisplayed(), "Verify manage tickets list is displayed");
		accessToken.switchToAssociatedMember(Dictionary.get("memberId1"));
		System.out.println(Dictionary.get("memberId1"));
		
		//List<String> eventsList = aapi.getEventNames(Dictionary.get("memberId1"));
		//System.out.println(eventsList);
		List<String> eventsLists = manageticketsapi.getEventNames(Dictionary.get("memberId1"));
		
		//Assert.assertEquals(managetickets.getListOfEventNames(), eventsLists, "Verify event names");
		Assert.assertTrue(managetickets.getListOfEventNames().contains(eventsLists), "Verify event names");
		
		Dictionary.put("member_id", Dictionary.get("memberId1"));
		HashMap<Integer, ManageticketsAPI.Event> events = manageticketsapi.getTicketsDetails();
		System.out.println(events);
		
		Assert.assertNotNull(events);
		List<Integer> e = new ArrayList<Integer>(events.keySet());
		for (int i = 0; i < e.size(); i++) {
			utils.navigateTo("/tickets#/" + e.get(i));
			BaseUtil.getDriver().navigate().refresh();
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
			List<List<String>> expectedsections = manageticketsapi.getTickets(e.get(i), events);
			List<List<String>> actualsections = managetickets.getTicketsDetail();
			Assert.assertEquals(actualsections, expectedsections, "Verify manage tickets are sorted");
		}

		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			hamburger.clickMobileHamburger();
			dashboardSection.clickYourAccountMobile();
		} else {
			dashboardSection.clickYourAccount();
		}
		dashboardSection.selectAccount(Dictionary.get("accId0").trim());
		dashboardSection.clickSwitchAccount();
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			section.clickQuickLinks();
		}
		if (Dictionary.get("name0").trim().equalsIgnoreCase(Dictionary.get("accId0"))) {
			Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("CUST_NAME")),
					Dictionary.get("CUST_NAME"));
		} else {
			Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("name0")), Dictionary.get("name0"));
		}
		Dictionary.put("member_id", Dictionary.get("memberId0"));
		utils.navigateTo("/tickets");
		Assert.assertTrue(managetickets.isManageTicketsListDisplayed(), "Verify manage tickets list is displayed");
		accessToken.switchToAssociatedMember(Dictionary.get("memberId0"));
		List<String> eventsList1 = aapi.getEventNames(Dictionary.get("memberId0"));
		Assert.assertEquals(managetickets.getListOfEventNames(), eventsList1, "Verify event names");
		Dictionary.put("member_id", Dictionary.get("memberId0"));
		//HashMap<Integer, ManageticketsAAPI.Event> events1 = aapi.getTicketsDetails();
		
		HashMap<Integer, ManageticketsAPI.Event> events1 = manageticketsapi.getTicketsDetails();
		Assert.assertNotNull(events1);
		List<Integer> es = new ArrayList<Integer>(events1.keySet());
		for (int i = 0; i < es.size(); i++) {
			utils.navigateTo("/tickets#/" + es.get(i));
			BaseUtil.getDriver().navigate().refresh();
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
			//List<List<String>> expectedsections1 = aapi.getTickets(es.get(i), events1);
			List<List<String>> expectedsections1 = manageticketsapi.getTickets(es.get(i), events1);
			List<List<String>> actualsections1 = managetickets.getTicketsDetail();
			//Assert.assertEquals(actualsections1, expectedsections1, "Verify manage tickets are sorted");
			Assert.assertEquals(actualsections1.contains(expectedsections1), "Verify manage tickets are sorted");
		}
	}
	
	
	
	
	

	@Test(groups = { "smoke", "ticketsFunctional", "regression", "switchAccount" }, priority = 48)
	public void verifySendTicketsSwitchAccount() throws Exception {
		if (Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim().equalsIgnoreCase(""))
			throw new SkipException("Skipped");
		if (Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim()
				.equalsIgnoreCase(Dictionary.get("ASSOCIATED_ACC_EMAIL_ADDRESS").trim()))
			throw new SkipException("Skipped");
		JSONObject obj = accessToken.getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"),
				Dictionary.get("SWITCH_ACC_PASSWORD"));
		System.out.println(obj.toString(2));
		if (Integer.parseInt(Dictionary.get("associatedCount")) < 2) {
			throw new SkipException("No Associated Accounts Available");
		}
		load("/");
		homepage.login(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"), Dictionary.get("SWITCH_ACC_PASSWORD"), null, false);
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			hamburger.clickMobileHamburger();
			dashboardSection.clickYourAccountMobile();
		} else {
			try {
				dashboardSection.clickYourAccount();
				dashboardSection.getAccountCount();
				}catch(Exception e)
				{
					dashboardSection.clickYourAccount();
				}
		}
		dashboardSection.selectAccount(Dictionary.get("accId1").trim());
		dashboardSection.clickSwitchAccount();
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			header.waitForDasboardHeader();
			dashboardSection.waitForDasboardSection(null);
			section.clickQuickLinks();
		}
		Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("name1")), Dictionary.get("name1"));
		Dictionary.put("member_id", Dictionary.get("memberId1"));
		accessToken.switchToAssociatedMember(Dictionary.get("memberId1"));
		String[] Tkt = manageticketsapi.getTransferDetails(true, "event", false, false, false, true, false);
		String[] ticket = Tkt[0].split("\\.");
		utils.navigateTo("/tickets#/" + ticket[0]);
		managetickets.clickSendTickets(null);
		managetickets.selectSeatInPopUp(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
		managetickets.clickContinue();
		SoftAssert.assertTrue(ticketNew.getSection().contains(ticket[1].replaceAll("%20", " ")));
		ticketNew.enterRecipientDetails(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim());
		ticketNew.clickSend();
		ticketNew.verifyRecipientEmailAddressIsSameAsEntered(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim());
		ticketNew.clickDone();
		try {
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
			String state = manageticketsapi.waitForTicketState(Tkt[0], "pending", false, false, true);
			managetickets.logoutNLogin(Dictionary.get("ASSOCIATED_ACC_EMAIL_ADDRESS"), Dictionary.get("ASSOCIATED_ACC_PASSWORD"));
			String status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], Tkt[0]);
			Assert.assertTrue(status.trim().contains("Waiting"));
			Assert.assertEquals(state, "pending");
			SoftAssert.assertEquals(manageticketsapi.getTicketFlags(Tkt[0], Dictionary.get("ASSOCIATED_ACC_EMAIL_ADDRESS"), Dictionary.get("ASSOCIATED_ACC_PASSWORD"), true), new Boolean[] { false, false, false, false, false, false, false }, "Verify the ticket flags");
		} finally {
			String[] transferIds = manageticketsapi.getTransferID(Dictionary.get("ASSOCIATED_ACC_EMAIL_ADDRESS"), Dictionary.get("ASSOCIATED_ACC_PASSWORD"), new String[]{Tkt[0]});
			if(transferIds != null && transferIds.length > 0) {
				String transferId = transferIds[0];
				manageticketsapi.deleteTransfer(transferId, false);
			}
		}
	}

	@Test(groups = { "smoke", "ticketsFunctional", "regression", "switchAccount" }, priority = 49, enabled = false)
	public void verifySellTicketsSwitchAccount() throws Exception {
		if (Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim().equalsIgnoreCase(""))
			throw new SkipException("Skipped");
		if (Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim()
				.equalsIgnoreCase(Dictionary.get("ASSOCIATED_ACC_EMAIL_ADDRESS").trim()))
			throw new SkipException("Skipped");
		JSONObject obj = accessToken.getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"),
				Dictionary.get("SWITCH_ACC_PASSWORD"));
		System.out.println(obj.toString(2));
		if (Integer.parseInt(Dictionary.get("associatedCount")) < 2) {
			throw new SkipException("No Associated Accounts Available");
		}
		load("/");
		homepage.login(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"), Dictionary.get("SWITCH_ACC_PASSWORD"), null, false);
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			hamburger.clickMobileHamburger();
			dashboardSection.clickYourAccountMobile();
		} else {
			dashboardSection.clickYourAccount();
		}
		dashboardSection.selectAccount(Dictionary.get("accId1").trim());
		dashboardSection.clickSwitchAccount();
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			header.waitForDasboardHeader();
			dashboardSection.waitForDasboardSection(null);
			section.clickQuickLinks();
		}
		Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("name1")), Dictionary.get("name1"));
		Dictionary.put("member_id", Dictionary.get("memberId1"));
		accessToken.switchToAssociatedMember(Dictionary.get("memberId1"));

		String[] tickets = manageticketsapi.getResaleDetails(true, "event", false, false, true, false);
		String Tkt = tickets[0];
		String[] ticket = Tkt.split("\\.");
		utils.navigateTo("/tickets#/" + ticket[0]);
		managetickets.clickSellTickets(null);
		managetickets.selectSeatInPopUp(Tkt, ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
		managetickets.clickContinue();
		managetickets.typeEarningPrice(new String[] { Dictionary.get("EarningPrice") });
		managetickets.clickContinue();
		managetickets.selectSellerCredit();
		if (managetickets.isContinueEnabled()) {
			// Do Nothing
		} else {
			managetickets.clickEditSellerProfile();
			managetickets.inputSellerProfile(Dictionary.get("FirstName"), Dictionary.get("LastName"),
					Dictionary.get("Add1"), Dictionary.get("Add2"), Dictionary.get("Country"), Dictionary.get("City"),
					Dictionary.get("State"), Dictionary.get("ZipCode"), Dictionary.get("MobileNum"),
					Dictionary.get("PhoneNum"));
			managetickets.clickContinue();
			SoftAssert.assertTrue(
					managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("FirstName")));
			SoftAssert.assertTrue(
					managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("LastName")));
			SoftAssert.assertTrue(
					managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add1")));
			SoftAssert.assertTrue(
					managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("Add2")));
			SoftAssert.assertTrue(
					managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("City")));
			SoftAssert.assertTrue(
					managetickets.getSellerAddress(Dictionary.get("Add1")).contains(Dictionary.get("ZipCode")));
		}

		managetickets.clickContinue();
		Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
		BaseUtil.sync(2000L);
		managetickets.clickContinue();
		try {
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
			String state = manageticketsapi.waitForTicketState(Tkt, "pending", false, false, true);
			managetickets.logoutNLogin(Dictionary.get("ASSOCIATED_ACC_EMAIL_ADDRESS"),
					Dictionary.get("ASSOCIATED_ACC_PASSWORD"));
			String status = managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2],
					ticket[3], Tkt);
			Assert.assertTrue(status.contains("Listed"));
			Assert.assertEquals(state, "pending");
			SoftAssert.assertEquals(
					manageticketsapi.getTicketFlags(Tkt, Dictionary.get("ASSOCIATED_ACC_EMAIL_ADDRESS"),
							Dictionary.get("ASSOCIATED_ACC_PASSWORD"), true),
					new Boolean[] { false, false, false, false, false, false, false }, "Verify the ticket flags");
		} finally {
			Integer[] postings = manageticketsapi.getPostingId(new String[] { Tkt }, true);
			if (postings.length > 0) {
				int postingId = postings[0];
				manageticketsapi.deletePosting(postingId, false);
			}
		}
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "donateFunctional", "switchAccount" }, priority = 50)
	public void verifyDonateTicketsSwitchAccount() throws Exception {
		if (Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim().equalsIgnoreCase(""))
			throw new SkipException("Skipped");
		if (Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim()
				.equalsIgnoreCase(Dictionary.get("ASSOCIATED_ACC_EMAIL_ADDRESS").trim()))
			throw new SkipException("Skipped");
		JSONObject obj = accessToken.getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"),
				Dictionary.get("SWITCH_ACC_PASSWORD"));
		System.out.println(obj.toString(2));
		if (Integer.parseInt(Dictionary.get("associatedCount")) < 2) {
			throw new SkipException("No Associated Accounts Available");
		}
		load("/");
		homepage.login(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"), Dictionary.get("SWITCH_ACC_PASSWORD"), null, false);
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			hamburger.clickMobileHamburger();
			dashboardSection.clickYourAccountMobile();
		} else {
			try {
				dashboardSection.clickYourAccount();
				dashboardSection.getAccountCount();
				}catch(Exception e)
				{
					dashboardSection.clickYourAccount();
				}
		}
		dashboardSection.selectAccount(Dictionary.get("accId1").trim());
		dashboardSection.clickSwitchAccount();
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			header.waitForDasboardHeader();
			dashboardSection.waitForDasboardSection(null);
			section.clickQuickLinks();
		}
		Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("name1")), Dictionary.get("name1"));
		Dictionary.put("member_id", Dictionary.get("memberId1"));
		accessToken.switchToAssociatedMember(Dictionary.get("memberId1"));
		String[] Tkt = manageticketsapi.getDonateDetails(true, "event", false, false, true, false);
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
		String state = manageticketsapi.waitForTicketState(Tkt[0], "donated", false, false, true);
		Assert.assertEquals(managetickets.getTicketStatus(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2],
				ticket[3], Tkt[0]), "Donated");
		Assert.assertEquals(state, "donated");
		SoftAssert.assertEquals(
				manageticketsapi.getTicketFlags(Tkt[0], Dictionary.get("ASSOCIATED_ACC_EMAIL_ADDRESS"),
						Dictionary.get("ASSOCIATED_ACC_PASSWORD"), true),
				new Boolean[] { false, false, false, false, false, false, false }, "Verify the ticket flags");
	}

	@Test(groups = { "smoke", "regression", "ticketsFunctional", "ViewFunctional", "prod",
			"switchAccount" }, priority = 47)
	public void verifyDownloadTicketsSwitchAccount() throws Exception {
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("Skipped");
		if (Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim().equalsIgnoreCase(""))
			throw new SkipException("Skipped");
		JSONObject obj = accessToken.getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"),
				Dictionary.get("SWITCH_ACC_PASSWORD"));
		System.out.println(obj.toString(2));
		if (Integer.parseInt(Dictionary.get("associatedCount")) < 2) {
			throw new SkipException("No Associated Accounts Available");
		}
		load("/");
		homepage.login(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"), Dictionary.get("SWITCH_ACC_PASSWORD"), null, false);
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			hamburger.clickMobileHamburger();
			dashboardSection.clickYourAccountMobile();
		} else {
			dashboardSection.clickYourAccount();
		}
		dashboardSection.selectAccount(Dictionary.get("accId1").trim());
		dashboardSection.clickSwitchAccount();
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			header.waitForDasboardHeader();
			dashboardSection.waitForDasboardSection(null);
			section.clickQuickLinks();
		}
		Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("name1")), Dictionary.get("name1"));
		Dictionary.put("member_id", Dictionary.get("memberId1"));
		accessToken.switchToAssociatedMember(Dictionary.get("memberId1"));

		String[] Tkt = manageticketsapi.getRenderDetails(true, "event", false, true, false);
		String[] ticket = Tkt[0].split("\\.");
		utils.navigateTo("/tickets#/" + ticket[0]);
		managetickets.clickDownloadTickets();
		managetickets.selectSeatInPopUp(Tkt[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
		managetickets.clickContinue();
		Assert.assertEquals(managetickets.getPopUpEventDetails(), Dictionary.get("eventName"));
		SoftAssert.assertTrue(managetickets.getSection().contains(ticket[1].replaceAll("%20", " ")));
		managetickets.clickContinue();
		Assert.assertTrue(managetickets.isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
		manageticketsapi.renderFile(new String[] { Tkt[0] }, false);
	}

	@Test(groups = { "smoke", "dashboardFunctional", "regression", "prod", "switchAccount" })
	public void verifyCountOfAccounts() throws Exception {
		if (Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim().equalsIgnoreCase(""))
			throw new SkipException("Skipped");
		accessToken.getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"),
				Dictionary.get("SWITCH_ACC_PASSWORD"));
		int count = Integer.parseInt(Dictionary.get("associatedCount"));
		if (count <= 1) {
			throw new SkipException("No related member accounts found");
		}
		load("/");
		homepage.login(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"), Dictionary.get("SWITCH_ACC_PASSWORD"), null, false);
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			hamburger.clickMobileHamburger();
			dashboardSection.clickYourAccountMobile();
		} else {
			try {
			dashboardSection.clickYourAccount();
			dashboardSection.getAccountCount();
			}catch(Exception e)
			{
				dashboardSection.clickYourAccount();
			}
		}
		
		Assert.assertEquals(dashboardSection.getAccountCount(), count);
		for (int i = 0; i < count; i++) {
			Assert.assertEquals(dashboardSection.getNickName(Dictionary.get("accId" + i)), Dictionary.get("name" + i));
			Assert.assertEquals(dashboardSection.getAccountId(Dictionary.get("accId" + i)), Dictionary.get("accId" + i));
		}
	}

	@Test(groups = { "smoke", "dashboardFunctional", "regression", "switchAccount" })
	public void verifyEditNickNames() throws Exception {
		if (Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim().equalsIgnoreCase(""))
			throw new SkipException("Skipped");
		accessToken.getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"),
				Dictionary.get("SWITCH_ACC_PASSWORD"));
		if (Integer.parseInt(Dictionary.get("associatedCount")) < 2) {
			throw new SkipException("No Associated Accounts Available");
		}
		load("/");
		homepage.login(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"), Dictionary.get("SWITCH_ACC_PASSWORD"), null, false);
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			hamburger.clickMobileHamburger();
			dashboardSection.clickYourAccountMobile();
		} else {
			try {
				dashboardSection.clickYourAccount();
				dashboardSection.getAccountCount();
				}catch(Exception e)
				{
					dashboardSection.clickYourAccount();
				}
		}
		String accountId = Dictionary.get("accId1");
		String accountId1 = Dictionary.get("accId2");
		dashboardSection.selectAccount(accountId1);
		dashboardSection.selectAccount(accountId);
		dashboardSection.clickEditName();
		String nickname = Dictionary.get("NickName");
		dashboardSection.typeAccountName(nickname);
		nickname = dashboardSection.getTypedName();
		dashboardSection.clickSaveNickName();
		dashboardSection.clickSwitchAccount();
		BaseUtil.sync(2000L);
		accessToken.getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"),
		Dictionary.get("SWITCH_ACC_PASSWORD"));
		Assert.assertEquals(Dictionary.get("name_" + accountId), nickname);
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			Assert.assertTrue(header.waitForDasboardHeader(), "Verify dashboard header is displayed");
			Assert.assertTrue(dashboardSection.waitForDasboardSection(null), "Verify dashboard section is displayed");
			section.clickQuickLinks();
		}
		BaseUtil.sync(2000L);
		if(!getDriver().getCurrentUrl().contains("dashboard")) {
			getDriver().navigate().to(Environment.get("APP_URL")+"/dashboard");
		}
		Assert.assertEquals(header.getAccountName(), nickname);
		Assert.assertEquals(header.getAccountId(), accountId);
		
	}

	@Test(groups = { "smoke", "dashboardUi", "regression", "prod", "switchAccount" })
	public void verifySwitchAccountNoAssociatedAccounts() throws Exception {
		accessToken.getMemberResponse(Dictionary.get("EMAIL_ADDRESS"), Dictionary.get("PASSWORD"));
		if (Integer.parseInt(Dictionary.get("associatedCount")) > 1) {
			throw new SkipException("Associated accounts are available");
		}
		load("/");
		homepage.login(Dictionary.get("EMAIL_ADDRESS"), Dictionary.get("PASSWORD"), null, false);
		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
				&& Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			hamburger.clickMobileHamburger();
			Assert.assertFalse(dashboardSection.checkSwitchAccountPresentMobile(),
					"Switch Account Option Not Available");
		} else {
			Assert.assertFalse(dashboardSection.checkSwitchAccountPresent(), "Switch Account Option Not Available");
		}
	}

	@Test(groups = { "smoke", "regression", "prod", "switchAccount", "invoiceNew" }, priority = 47)
	public void verifyInvoicesSecondarydAccountNew() throws Exception {
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			throw new SkipException("Skipped");
		}
		if (Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim().equalsIgnoreCase(""))
			throw new SkipException("Skipped");
		accessToken.getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"),
				Dictionary.get("SWITCH_ACC_PASSWORD"));
		if (Integer.parseInt(Dictionary.get("associatedCount")) < 2) {
			throw new SkipException("No Associated Accounts Available");
		}
		load("/");
		homepage.login(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"), Dictionary.get("SWITCH_ACC_PASSWORD"), null, false);
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			hamburger.clickMobileHamburger();
			dashboardSection.clickYourAccountMobile();
		} else {
			try {
				dashboardSection.clickYourAccount();
				dashboardSection.getAccountCount();
				}catch(Exception e)
				{
					dashboardSection.clickYourAccount();
				}
		}
		String accountId = Dictionary.get("accId1").trim();
		dashboardSection.selectAccount(accountId);
		dashboardSection.clickSwitchAccount();
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			section.clickQuickLinks();
		}
		Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("name1")), Dictionary.get("name1"));
		utils.navigateTo("/invoice");

		List<List<String>> sortedListUnpaid = adminlogin.getSortedInvoiceListPaidOrUnpaid(accountId, "", "Unpaid");
		invoiceNew.clickUnpaidTab();
		if(sortedListUnpaid.size() == 0) {
			invoiceNew.verifyNoInvoiceMessageUnpaid();
		} else {
			Assert.assertTrue(invoiceNew.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
			Assert.assertEquals(invoice.getInvoiceList(), sortedListUnpaid, "Verify invoice list is sorted for unpaid invoices");
		}
		
		invoiceNew.clickPaidTab();
		List<List<String>> sortedListPaid = adminlogin.getSortedInvoiceListPaidOrUnpaid(accountId, "", "Paid");
		if(sortedListPaid.size() == 0) {
			invoiceNew.verifyNoInvoiceMessagePaid();
		}
		else{
			Assert.assertTrue(invoiceNew.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
			Assert.assertEquals(invoice.getInvoiceList(), sortedListPaid, "Verify invoice list is sorted for paid invoices");
		}
			
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			hamburger.clickMobileHamburger();
			dashboardSection.clickYourAccountMobile();
		} else {
			try {
				dashboardSection.clickYourAccount();
				dashboardSection.getAccountCount();
				}catch(Exception e)
				{
					dashboardSection.clickYourAccount();
				}
		}
		dashboardSection.selectAccount(Dictionary.get("accId0").trim());
		dashboardSection.clickSwitchAccount();
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			section.clickQuickLinks();
		}

		if (Dictionary.get("name0").trim().equalsIgnoreCase(Dictionary.get("accId0"))) {
			Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("CUST_NAME")),
			Dictionary.get("CUST_NAME"));
		} else {
			Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("name0")), Dictionary.get("name0"));
		}

		utils.navigateTo("/invoice");
		invoiceNew.clickUnpaidTab();
		List<List<String>> sortedListUnpaid1 = adminlogin.getSortedInvoiceListPaidOrUnpaid(Dictionary.get("accId0"), "", "Unpaid");
		if(sortedListUnpaid1.size() == 0) {
			invoiceNew.verifyNoInvoiceMessageUnpaid();
		} else {
			Assert.assertTrue(invoiceNew.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
			Assert.assertEquals(invoice.getInvoiceList(), sortedListUnpaid1, "Verify invoice list is sorted for unpaid invoices");
		}
		
		invoiceNew.clickPaidTab();
		List<List<String>> sortedListPaid1 = adminlogin.getSortedInvoiceListPaidOrUnpaid(Dictionary.get("accId0"), "", "Paid");
		if(sortedListPaid1.size() == 0) {
			invoiceNew.verifyNoInvoiceMessagePaid();
		} else {
			Assert.assertTrue(invoiceNew.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
			Assert.assertEquals(invoice.getInvoiceList(), sortedListPaid1,"Verify invoice list is sorted for paid invoices");
		}
	}

	@Test(groups = { "smoke", "regression", "switchAccount", "invoiceNew" }, priority = 47, enabled=false)
	public void verifyInvoicePaymentSwitchAccountNew() throws Exception {
		if (Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim().equalsIgnoreCase(""))
			throw new SkipException("Skipped");
		accessToken.getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"),
				Dictionary.get("SWITCH_ACC_PASSWORD"));
		if (Integer.parseInt(Dictionary.get("associatedCount")) < 2) {
			throw new SkipException("No Associated Accounts Available");
		}
		load("/");
		homepage.login(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"), Dictionary.get("SWITCH_ACC_PASSWORD"), null, false);
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			hamburger.clickMobileHamburger();
			dashboardSection.clickYourAccountMobile();
		} else {
			dashboardSection.clickYourAccount();
		}
		dashboardSection.selectAccount(Dictionary.get("accId1").trim());
		dashboardSection.clickSwitchAccount();
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			section.clickQuickLinks();
		}
		Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("name1")), Dictionary.get("name1"));
		utils.navigateTo("/invoice");

		String check = invoiceNew.checkInvoicePresent();

		System.out.println(check + "CHECK");

		if (check.equalsIgnoreCase("NOPLAN"))
			invoiceNew.clickFirstNonPlanInvoiceLink();
		else if (check.equalsIgnoreCase("PLAN"))
			invoiceNew.clickFirstPlanInvoiceLink();
		else
			throw new SkipException("No Invoices");

		Assert.assertTrue(invoiceNew.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		// String invDue = invoice.amountDue();
		Double BalanceDue = invoiceNew.getBalanceDue();
		invoice.clickContinueButton();
		String accountBalance = "";

		if (check.equalsIgnoreCase("NOPLAN")) {
			invoiceNew.selectPayOther();
			invoiceNew.amountDuePayTodayDisplayed();
			invoiceNew.clickSelectOrAddPaymentLink();
			invoiceNew.selectPaymentMethodDisplayed();
			invoiceNew.selectFirstExistingCard();
			invoiceNew.cvvDisplayed();
			invoiceNew.enterCVVFirstExistingCard();
			invoiceNew.clickContinuePopUp();
			if (((driverType.trim().toUpperCase().contains("ANDROID")
					|| driverType.trim().toUpperCase().contains("IOS"))
					&& Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			} else
				accountBalance = dashboardSection.getAccountBalance();
			invoiceNew.amountFieldDisplayed();

			String AmtEnter = Dictionary.get("PartialAmt");
			invoiceNew.enterAmount(AmtEnter);

			invoiceNew.clickContinuePaymentSection();

			invoiceNew.reviewSectionDisplayed();
			invoice.selectAcceptTnC();
			invoiceNew.clickConfirmButton();

			Double debitAmount = invoiceNew.validateAmountUpdatedAndGetAmountDebitted(accountBalance);
			System.out.println(debitAmount);

			if (((driverType.trim().toUpperCase().contains("ANDROID")
					|| driverType.trim().toUpperCase().contains("IOS")))) {

			} else {

				System.out.println(BalanceDue);
				System.out.println(BalanceDue - debitAmount);
				double x = BalanceDue - debitAmount;
				System.out.println(x);
				invoice.getInvoiceBalWhenRefreshed(invoiceNew.getInvoiceNumber(), x);

			}
		} else if (check.equalsIgnoreCase("PLAN")) {
			if (((driverType.trim().toUpperCase().contains("ANDROID")
					|| driverType.trim().toUpperCase().contains("IOS"))
					&& Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			} else
				accountBalance = dashboardSection.getAccountBalance();
			invoiceNew.amountFieldDisplayed();
			invoiceNew.clickContinuePaymentSection();
			invoiceNew.reviewSectionDisplayed();
			invoice.selectAcceptTnC();
			invoiceNew.clickConfirmButton();
			invoiceNew.validateConfirmPopUp();
			invoiceNew.enterCVVinConfirmPopUp();
			invoiceNew.clickContinuePopUp();

			Double debitAmount = invoiceNew.validateAmountUpdatedAndGetAmountDebitted(accountBalance);
			System.out.println(debitAmount);

			if (((driverType.trim().toUpperCase().contains("ANDROID")
					|| driverType.trim().toUpperCase().contains("IOS")))) {
			} else {
				System.out.println(BalanceDue);
				System.out.println(BalanceDue - debitAmount);
				double x = BalanceDue - debitAmount;
				System.out.println(x);
				invoice.getInvoiceBalWhenRefreshed(invoiceNew.getInvoiceNumber(), x);
			}
		}
	}

	@Test(groups = { "smoke", "regression", "prod", "switchAccount", "invoiceNew" }, priority = 47)
	public void verifyCardInfoSwitchAccountNew() throws Exception {
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("No need to run on android/ios");
		if (Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS").trim().equalsIgnoreCase(""))
			throw new SkipException("Skipped");
		accessToken.getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"),
				Dictionary.get("SWITCH_ACC_PASSWORD"));
		if (Integer.parseInt(Dictionary.get("associatedCount")) < 2) {
			throw new SkipException("No Associated Accounts Available");
		}

		String accountId = Dictionary.get("accId2").trim();
		String accountId1 = Dictionary.get("accId3").trim();
		String invoicestatus = "UNPAID";
		int invoiceId = adminlogin.getInvoiceListAndId(accountId, invoicestatus);
		if (invoiceId == -1) {
			invoicestatus = "PARTIALLY PAID";
			invoiceId = adminlogin.getInvoiceListAndId(accountId, invoicestatus);
			if (invoiceId == -1) {
				invoicestatus = "PAYMENT PLAN";
				invoiceId = adminlogin.getInvoiceListAndId(accountId, invoicestatus);
			}
		}

		if (invoiceId == -1) {
			throw new SkipException("Didn't found any non-paid invoice");
		}

		adminlogin.getCCQuery(Dictionary.get("accId0"), invoicestatus);
		adminlogin.getCCQueryFieldValues(Dictionary.get(invoicestatus.trim().toUpperCase() + "cc_Query"),
				new String[] { "cc_name_first", "cc_name_last", "cc_postal_code", "cc_address", "cc_exp", "data_mask" },
				invoicestatus);
		if (Dictionary.get(invoicestatus.trim().toUpperCase() + "data_mask").trim().equalsIgnoreCase("")) {
			throw new SkipException("Credit card info not found");
		}
		load("/");
		homepage.login(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"), Dictionary.get("SWITCH_ACC_PASSWORD"), null, false);
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			hamburger.clickMobileHamburger();
			dashboardSection.clickYourAccountMobile();
		} else {
			openSwtichAccount();
		}
		dashboardSection.selectAccount(accountId);
		if(!dashboardSection.isSwitchButtonEnabled()) {
			dashboardSection.selectAccount(accountId1);
			dashboardSection.clickSwitchAccount();
 			openSwtichAccount();
 		    dashboardSection.selectAccount(accountId);   
		}
		
		
		
       // dashboardSection.selectAccount(accountId);
		dashboardSection.clickSwitchAccount();
		utils.sync(5000l);
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			section.clickQuickLinks();
		}
		
		if(!getDriver().getCurrentUrl().contains("dashboard")) {
			getDriver().navigate().to(Environment.get("APP_URL")+"/dashboard");
		utils.sync(5000l);
		Assert.assertEquals(header.getSwitchedAccountName(Dictionary.get("name2")), Dictionary.get("name2"));
	    }
		utils.navigateTo("/invoice");
		Assert.assertTrue(invoice.verifyInvoiceStatus(invoiceId, invoicestatus),"Verify " + invoicestatus.trim().toLowerCase() + " invoice is displayed");
		invoice.clickInvoiceLink(invoiceId, null);

		Assert.assertTrue(invoiceNew.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");

		invoice.clickContinueButton();

		invoiceNew.clickSelectOrAddPaymentLink();
		
		Assert.assertTrue(invoiceNew.verifyAllCardDetails(Dictionary.get(invoicestatus.trim().toUpperCase() + "cc_Query")),"Verify all cards last digits matches with the result from TM Api");
		
	}
	
	private void openSwtichAccount() {
	
		try {
			dashboardSection.clickYourAccount();
			dashboardSection.getAccountCount();
		}catch(Exception e)
		{
			dashboardSection.clickYourAccount();
		}
	}
	
}

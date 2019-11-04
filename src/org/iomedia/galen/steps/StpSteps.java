package org.iomedia.galen.steps;

import org.iomedia.common.BaseUtil;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.pages.DashboardHeader;
import org.iomedia.galen.pages.STP;
import org.testng.SkipException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StpSteps {
	
	BaseUtil base;
	org.iomedia.framework.Assert Assert;
	STP stp;
	Utils utils;
	String driverType;
	DashboardHeader header;
	
	public StpSteps(BaseUtil base, org.iomedia.framework.Assert Assert, STP stp, Utils utils, DashboardHeader header) {
		this.base = base;
		this.Assert = Assert;
		this.stp = stp;
		this.utils = utils;
		this.header = header;
		this.driverType = base.driverFactory.getDriverType().get();
	}
	
	@Then("^User done SSO successfully to STP with correct (.+) or (.+)$")
	public void user_done_sso_successfully_to_stp_with_correct_or(String stpaccountName, String accountName) {
		stpaccountName = (String) base.getGDValue(stpaccountName);
		accountName = (String) base.getGDValue(accountName);
		String stpUserName;
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			stpUserName =  stp.getExistingUserNameMobile();
		} else {			
			stpUserName =  stp.getSTPUserName();
		}
		Assert.assertTrue(stpUserName.equals(stpaccountName) || stpUserName.equals(accountName), "SSO is successfull to STP");
	}
	
	@Then("^User done SSO successfully to STP Welcome page with correct (.+) or (.+)$")
	public void user_done_sso_successfully_to_stp_welcome_page_with_correct_or(String stpaccountName, String accountName) {
		stpaccountName = (String) base.getGDValue(stpaccountName);
		accountName = (String) base.getGDValue(accountName);
		String stpUserName;
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			stpUserName =  stp.getSTPUserNameMobile();
		} else {			
			stpUserName =  stp.getSTPUserName();
		}
		Assert.assertTrue(stpUserName.equals(stpaccountName) || stpUserName.equals(accountName), "SSO is successfull to STP");
	}
	
	@Given("^User logout from STP$")
	public void user_logout_from_stp() {
		stp.STPLogout();
	}
	
	@Given("^User navigates to NAM from STP$")
	public void user_navigates_to_nam_from_stp() {
		stp.STPAMGR();
		header.waitForNAM();
	}
	
	@When("^User navigates to CAM from STP$")
	public void user_navigates_to_cam_from_stp() {
		stp.STPClassicAMGR();
	}
	
	@When("^User login to STP with (.+) and (.+)$")
	public void user_login_to_stp_with_and(String emailaddress, String password) throws Exception {
		if(base.Environment.get("STP_URL").trim().equalsIgnoreCase(""))
			throw new SkipException("STP url not found");
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		base.getDriver().navigate().to(base.Environment.get("STP_URL").trim() + "/user/login");
		stp.login(emailaddress, password, null);
	}
	
	@Given("^User creates stp account(.*)$")
	public void user_creates_stp_account(String suffix) throws Exception {
		if(base.Environment.get("STP_URL").trim().equalsIgnoreCase(""))
			throw new SkipException("STP url not found");
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			base.getDriver().navigate().to(base.Environment.get("STP_URL").trim() + "/create-account?type=buy&value=TMPLAN%3D" + base.Environment.get("stp_GA_Event").trim() + "%26PC1%3D" + base.Environment.get("stp_Price_Code").trim());
		} else
			base.getDriver().navigate().to(base.Environment.get("STP_URL").trim() + "/create-account");
		stp.createaccount(null, suffix);
	}
	
	@Given("^User visits STP homepage$")
	public void user_visits_stp_homepage() {
		if(base.Environment.get("STP_URL").trim().equalsIgnoreCase(""))
			throw new SkipException("STP url not found");
		base.getDriver().navigate().to(base.Environment.get("STP_URL").trim());
	}
	
	@When("^User buys a GA ticket with (.+) using (.+) type card with card number (.+), CVV (.+), Expiry (.+)$")
	public void user_buys_GA_ticket(String paymentMode, String cardType, String cardNum, String CVV, String Expiry) throws Exception {
		if(base.Environment.get("STP_URL").trim().equalsIgnoreCase(""))
			throw new SkipException("STP url not found");
		paymentMode = (String) base.getGDValue(paymentMode);
		cardType = (String) base.getGDValue(cardType);
		cardNum = (String) base.getGDValue(cardNum);
		CVV = (String) base.getGDValue(CVV);
		Expiry = (String) base.getGDValue(Expiry);
		
		String stp_GA_Event = System.getProperty("stp_GA_Event") != null && !System.getProperty("stp_GA_Event").trim().equalsIgnoreCase("") ? System.getProperty("stp_GA_Event").trim() : base.Environment.get("stp_GA_Event").trim();
		String stp_Price_Code = System.getProperty("stp_Price_Code") != null && !System.getProperty("stp_Price_Code").trim().equalsIgnoreCase("") ? System.getProperty("stp_Price_Code").trim() : base.Environment.get("stp_Price_Code").trim();
		
		if(stp_GA_Event.trim().equalsIgnoreCase("") || stp_Price_Code.trim().equalsIgnoreCase("")) {
			throw new SkipException("GA Event name or price code not found for stp");
		}
		base.getDriver().navigate().to(base.Environment.get("STP_URL") + "/buy?TMPLAN=" + stp_GA_Event + "&PC1=" + stp_Price_Code);
		stp.buyGATickets(paymentMode, cardType, cardNum, Expiry, CVV, null);	
	}
	
	@When("^User buys a non GA ticket with (.+) using (.+) type card with card number (.+), CVV (.+), Expiry (.+)$")
	public void user_buys_non_GA_ticket(String paymentMode, String cardType, String cardNum, String CVV, String Expiry) throws Exception {
		if(base.Environment.get("STP_URL").trim().equalsIgnoreCase(""))
			throw new SkipException("STP url not found");
		paymentMode = (String) base.getGDValue(paymentMode);
		cardType = (String) base.getGDValue(cardType);
		cardNum = (String) base.getGDValue(cardNum);
		CVV = (String) base.getGDValue(CVV);
		Expiry = (String) base.getGDValue(Expiry);
		
		String stp_non_GA_Event = System.getProperty("stp_NonGA_Event") != null && !System.getProperty("stp_NonGA_Event").trim().equalsIgnoreCase("") ? System.getProperty("stp_NonGA_Event").trim() : base.Environment.get("stp_NonGA_Event").trim();
		String stp_Price_Code = System.getProperty("stp_Price_Code") != null && !System.getProperty("stp_Price_Code").trim().equalsIgnoreCase("") ? System.getProperty("stp_Price_Code").trim() : base.Environment.get("stp_Price_Code").trim();
		
		if(stp_non_GA_Event.trim().equalsIgnoreCase("") || stp_Price_Code.trim().equalsIgnoreCase("")) {
			throw new SkipException("Non GA Event name or price code not found for stp");
		}
		base.getDriver().navigate().to(base.Environment.get("STP_URL") + "/buy?TMPLAN=" + stp_non_GA_Event + "&PC1=" + stp_Price_Code);
		stp.buyGATickets(paymentMode, cardType, cardNum, Expiry, CVV, null);	
	}
	
	@Then("^Thank you page is displayed$")
	public void thank_you_page_is_displayed() {
		Assert.assertTrue(stp.isConfirmationPageDisplayed(null), "Verify thank you page is displayed");
		base.sync(30000L);
	}
	
	@Given("^Invoice Id is generated$")
	public void invoice_id_is_generated() {
		base.getDriver().navigate().to(base.Environment.get("STP_URL") + "/invoice/list");
		String invoiceId = stp.getFirstPendingInvoiceId(null);
		String amtDue = stp.getFirstPendingInvoiceAmtDue(null);
		stp.clickBackArrow(null);
		base.Dictionary.put("STP_INVOICE_ID", invoiceId);
		base.Dictionary.put("STP_INVOICE_AMT_DUE", amtDue);
	}
}

package org.iomedia.galen.steps;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.iomedia.common.BaseUtil;
import org.iomedia.galen.common.AccessToken;
import org.iomedia.galen.common.EncryptDecrypt;
import org.iomedia.galen.common.ManageticketsAPI;
import org.iomedia.galen.common.RecieveMail;
import org.iomedia.galen.common.Screenshot;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.pages.AdminLogin;
import org.iomedia.galen.pages.CMS;
import org.iomedia.galen.pages.DashboardSection;
import org.iomedia.galen.pages.Invoice;
import org.iomedia.galen.pages.InvoiceNew;
import org.iomedia.galen.pages.ManageTicket;
import org.json.JSONException;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.SkipException;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class InvoiceNewSteps {

	InvoiceNew invoiceNew;
	CMS cms;
	DashboardSection dashboard;
	AdminLogin adminLogin;
	ManageTicket managetickets;
	BaseUtil base;
	Utils utils;
	org.iomedia.framework.Assert Assert;
	AccessToken accessToken;
	String driverType;
	DashboardSection section;
	RecieveMail mail;
	EncryptDecrypt decode = new EncryptDecrypt();
	ManageticketsAPI api;
	Screenshot shot;
	int actualMsgCount;
	String accountBalance;
	Invoice invoice;
	int invoiceId;
	String accountId;
	double BalanceDue;
	int orderNumber;
	int orderExpire;

	public InvoiceNewSteps(CMS cms,Screenshot shot, InvoiceNew invoiceNew, Invoice invoice, DashboardSection dashboard, AdminLogin adminLogin, BaseUtil base, Utils utils, org.iomedia.framework.Assert Assert, DashboardSection section, RecieveMail mail, AccessToken accessToken, ManageticketsAPI api) {
		this.invoiceNew = invoiceNew;
		this.cms = cms;
		this.invoice = invoice;
		this.dashboard = dashboard;
		this.adminLogin = adminLogin;
		this.base = base;
		this.utils = utils;
		this.Assert = Assert;
		this.section = section;
		this.accessToken = accessToken;
		this.managetickets =managetickets;
		driverType = base.driverFactory.getDriverType().get();
		this.mail = mail;
		this.api = api;
		this.shot = shot;
		actualMsgCount = 0;
	}

	@Then("^User is on Invoice page and Invoice list is displayed$")
	public void invoiceListIsDisplayed() throws JSONException, IOException {
		
		invoiceNew.isInvoiceListDisplayed();
		
	}

	@When("^User selects payment plan invoice (.+),(.+)$")
	public void clickFirstPlanInvoice(String email, String password) throws JSONException, Exception {
		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		base.Dictionary.put("AccessToken", accessToken.getAccessToken(email, password));
		accountId = accessToken.getAccountId(base.Dictionary.get("AccessToken"));
		String invoicestatus = "PLAN";
		invoiceId = adminLogin.getInvoiceListAndId(accountId, invoicestatus);
		if(invoiceId == -1)
			throw new SkipException(invoicestatus + " Invoice not found");
		
//		if(invoiceId == -1){
//			invoicestatus = "PARTIALLY PAID";
//			invoiceId = adminlogin.getInvoiceListAndId(accId, invoicestatus);
//			if(invoiceId == -1){
//				invoicestatus = "UNPAID";
//				invoiceId = adminlogin.getInvoiceListAndId(accId, invoicestatus);
//			}
//		}
	
		invoiceNew.loadInvoice(invoiceId);
	}

	@When("^User clicks on Unpaid tab and selects first without plan invoice (.+),(.+)$")
	public void clickFirstNonPlanInvoice(String email, String password) throws Exception {
		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		base.Dictionary.put("AccessToken", accessToken.getAccessToken(email, password));
		accountId = accessToken.getAccountId(base.Dictionary.get("AccessToken"));
		String invoicestatus = "UNPAID";
		invoiceId = adminLogin.getInvoiceListAndIdNew(accountId, invoicestatus);
		if(invoiceId == -1){
			invoicestatus = "PARTIALLY PAID";
			invoiceId = adminLogin.getInvoiceListAndIdNew(accountId, invoicestatus);
		}
		if(invoiceId == -1)
			throw new SkipException(invoicestatus + " Invoice not found");
		invoiceNew.loadInvoice(invoiceId);
	}
	
	@When("^User clicks on Unpaid tab without plan invoice (.+),(.+)$")
	public void clickNonPlanInvoice(String email, String password) throws Exception {
		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		base.Dictionary.put("AccessToken", accessToken.getAccessToken(email, password));
		accountId = accessToken.getAccountId(base.Dictionary.get("AccessToken"));
		String invoicestatus = "UNPAID";
		invoiceId = adminLogin.getInvoiceListAndId(accountId, invoicestatus);
		if(invoiceId == -1){
			invoicestatus = "PARTIALLY PAID";
			invoiceId = adminLogin.getInvoiceListAndId(accountId, invoicestatus);
		}
		if(invoiceId == -1)
			throw new SkipException(invoicestatus + " Invoice not found");
		invoiceNew.loadInvoice(invoiceId);
	}
	
	
	
	@When("^User clicks on Unpaid tab and selects invoice with Upsells (.+),(.+)$")
	public void selectInvoiceWithUpsells(String email, String password) throws Exception {
		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		base.Dictionary.put("AccessToken", accessToken.getAccessToken(email, password));
		accountId = accessToken.getAccountId(base.Dictionary.get("AccessToken"));
		invoiceId = adminLogin.getInvoiceWithUpsells(accountId);
		
		System.out.println(base.Dictionary.get("UpsellResponse"));
		for(int i =0; i<Integer.parseInt(base.Dictionary.get("TotalUpsell"));i++)
		{
			if(base.Dictionary.get("TotalPriceCodes"+i).equalsIgnoreCase("1"))
			{
				System.out.println(base.Dictionary.get("Price"+i));
			}
			else
			{
				for(int j=0;j<Integer.parseInt(base.Dictionary.get("TotalPriceCodes"+i));j++)
				{
					System.out.println(base.Dictionary.get("Price"+i+j));
					System.out.println(base.Dictionary.get("Description"+i+j));
				}
			}
		}
		invoiceNew.loadInvoice(invoiceId);
	}

	@When("^User clicks on Paid tab and selects first invoice (.+),(.+)$")
	public void clickFirstPaidInvoice(String email, String password) throws Exception {
		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		base.Dictionary.put("AccessToken", accessToken.getAccessToken(email, password));
		accountId = accessToken.getAccountId(base.Dictionary.get("AccessToken"));
		String invoicestatus = "PAID";
		invoiceId = adminLogin.getInvoiceListAndId(accountId, invoicestatus);
		if(invoiceId == -1)
			throw new SkipException(invoicestatus + " Invoice not found");
		invoiceNew.loadInvoice(invoiceId);
	}

	@Then("^Paid invoice Summary is displayed and Continue button is not displayed$")
	public void checkContinueButtonNotDisplayed() {
		Assert.assertEquals(invoiceNew.getInvoiceSummaryDueAmt(), "0.00");
		Assert.assertTrue(invoice.checkPaidContinueDisabled());
	}

	@Then("^Invoice Summary is displayed with Due Amount and Continue button$")
	public void summaryDisplayed() {
		invoiceNew.isInvoiceDetailDisplayed(null);

		utils.sync(6000L);
		BalanceDue = invoiceNew.getBalanceDue();
	}

	@And("^MOP has same card as displayed in UI$")
	public void verifyMOPCard() throws Exception {	
		System.out.println(accountId);
		// adminLogin.beforeMtdInvoiceAPI("PLAN",false);
		adminLogin.getPaymentSchedule(accountId, adminLogin.getInvoiceListAndId(accountId, "PLAN"), "PLAN");
		adminLogin.getPaymentScheduleMopValues(base.Dictionary.get("PLAN".trim().toUpperCase() + "paymentPlansSchedule"), "PLAN");
		System.out.println("PLAN ID IS " + base.Dictionary.get("PLAN" + "planId"));
		invoiceNew.verifyMOP("PLAN");
	}

	@And("^Selected card must be there in MOP for (.+), (.+)$")
	public void verifyMOPCards(String email, String password) throws Exception {
		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		base.Dictionary.put("AccessToken", accessToken.getAccessToken(email, password));
		accountId = accessToken.getAccountId(base.Dictionary.get("AccessToken"));
		invoiceId = invoiceNew.getInvoiceNumber();

		System.out.println(accountId);
		// adminLogin.beforeMtdInvoiceAPI("PLAN",false);
		adminLogin.getPlanIdForInvoice(invoiceId, accountId, "PLAN");
		adminLogin.getPaymentSchedule(accountId, invoiceId, "PLAN");
		adminLogin.getPaymentScheduleMopValues(base.Dictionary.get("PLAN".trim().toUpperCase() + "paymentPlansSchedule"), "PLAN");
		System.out.println("PLAN ID IS " + base.Dictionary.get("PLAN" + "planId"));
		invoiceNew.verifyMOP("PLAN");
	}
	
	@Then("^User fetches the existing card payment method$")
	public void user_fetch_existing_card() {
		String card = invoiceNew.getTextpaymentMethod();
		int size=4;
		List<String> parts = new ArrayList<>();
        int length = card.length();
        for (int i = 0; i < length; i += size) {
            parts.add(card.substring(i, Math.min(length, i + size)));
        }
        String [] ar = parts.toArray(new String[0]);	
		base.Dictionary.put("CARD", ar[1]);
	}
	

	@When("^User clicks on Continue button in Summary Section$")
	public void clickContinueButton() {
		invoiceNew.clickSummaryContinue();
	}
	

	@And("^Two cards added are not saved and verified in cc query (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void testCCCardsNotSaved(String email, String password, String gdCardFirstName, String gdCardLastName,
			String gdCardNum, String gdCardExpiry, String gdZip, String gdAddress, String gdCardFirstNameSec,
			String gdCardLastNameSec, String gdCardNumSec, String gdCardExpirySec, String gdZipSec, String gdAddressSec)
			throws Exception {
		boolean cardAdded;

		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		base.Dictionary.put("AccessToken", accessToken.getAccessToken(email, password));

		accountId = accessToken.getAccountId(base.Dictionary.get("AccessToken"));

		invoiceId = invoiceNew.getInvoiceNumber();

		System.out.println(accountId);
		

		gdCardFirstName = (String) base.getGDValue(gdCardFirstName);
		gdCardLastName = (String) base.getGDValue(gdCardLastName);
		gdCardNum = (String) base.getGDValue(gdCardNum);
		gdCardExpiry = (String) base.getGDValue(gdCardExpiry);
		gdZip = (String) base.getGDValue(gdZip);

		gdAddress = (String) base.getGDValue(gdAddress);

		gdCardFirstNameSec = (String) base.getGDValue(gdCardFirstNameSec);
		gdCardLastNameSec = (String) base.getGDValue(gdCardLastNameSec);
		gdCardNumSec = (String) base.getGDValue(gdCardNumSec);
		gdCardExpirySec = (String) base.getGDValue(gdCardExpirySec);
		gdZipSec = (String) base.getGDValue(gdZipSec);

		gdAddressSec = (String) base.getGDValue(gdAddressSec);
		System.out.println(gdCardFirstNameSec);
		System.out.println(gdCardLastNameSec);
		System.out.println(gdCardNumSec);
		System.out.println(gdCardExpirySec);

		adminLogin.getCCQuery(accountId, "PLAN");
		System.out.println("Response" + base.Dictionary.get("PLAN" + "cc_Query"));
		cardAdded = adminLogin.verifyCCAddedInCC(base.Dictionary.get("PLAN" + "cc_Query"), "PLAN", gdCardFirstName,
				gdCardLastName, gdCardNum, gdCardExpiry, gdZip, gdAddress);

		Assert.assertFalse(cardAdded, "First Card not saved and verified in CC Query");
		cardAdded = adminLogin.verifyCCAddedInCC(base.Dictionary.get("PLAN" + "cc_Query"), "PLAN", gdCardFirstNameSec,
				gdCardLastNameSec, gdCardNumSec, gdCardExpirySec, gdZipSec, gdAddressSec);
		Assert.assertFalse(cardAdded, "Second Card not saved and verified in CC Query");

		System.out.println(cardAdded);

	}
	
	@Then("^Payment should be successfull and amount should get updated$")
	public void payment_should_be_successfull_and_amount_should_get_updated() throws Exception {
		invoiceNew.validateAmountUpdatedAndGetAmountDebittedNew();
	//	String AmountDue = invoiceNew.getamountDue();
//		String previousBalance = base.Dictionary.get("DUE_BALANCE");
//		if (((driverType.trim().toUpperCase().contains("ANDROID")
//				|| driverType.trim().toUpperCase().contains("IOS")))) {
//		} else {
//			System.out.println(AmountDue);
//			Double CalculativeAmountDue= Double.valueOf(previousBalance) - Double.valueOf(payamount);
//			Assert.assertTrue(AmountDue.contains(String.valueOf(CalculativeAmountDue)),"Amount are correctly displayed");
//			System.out.println(Double.valueOf(CalculativeAmountDue));
//			invoice.getInvoiceBalWhenRefreshed(invoiceId, Double.valueOf(CalculativeAmountDue));
//		}
//	}
	}

	@And("^Two cards added are saved and verified in cc query (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void testCCCardsSaved(String email, String password, String gdCardFirstName, String gdCardLastName,
			String gdCardNum, String gdCardExpiry, String gdZip, String gdAddress, String gdCardFirstNameSec,
			String gdCardLastNameSec, String gdCardNumSec, String gdCardExpirySec, String gdZipSec, String gdAddressSec)
			throws Exception {
		boolean cardAdded;

		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		base.Dictionary.put("AccessToken", accessToken.getAccessToken(email, password));

		accountId = accessToken.getAccountId(base.Dictionary.get("AccessToken"));

		invoiceId = invoiceNew.getInvoiceNumber();

		System.out.println(accountId);

		gdCardFirstName = (String) base.getGDValue(gdCardFirstName);
		gdCardLastName = (String) base.getGDValue(gdCardLastName);
		gdCardNum = (String) base.getGDValue(gdCardNum);
		gdCardExpiry = (String) base.getGDValue(gdCardExpiry);
		gdZip = (String) base.getGDValue(gdZip);

		gdAddress = (String) base.getGDValue(gdAddress);

		gdCardFirstNameSec = (String) base.getGDValue(gdCardFirstNameSec);
		gdCardLastNameSec = (String) base.getGDValue(gdCardLastNameSec);
		gdCardNumSec = (String) base.getGDValue(gdCardNumSec);
		gdCardExpirySec = (String) base.getGDValue(gdCardExpirySec);
		gdZipSec = (String) base.getGDValue(gdZipSec);

		gdAddressSec = (String) base.getGDValue(gdAddressSec);
		System.out.println(gdCardFirstNameSec);
		System.out.println(gdCardLastNameSec);
		System.out.println(gdCardNumSec);
		System.out.println(gdCardExpirySec);

		adminLogin.getCCQuery(accountId, "PLAN");
		System.out.println("Response" + base.Dictionary.get("PLAN" + "cc_Query"));
		cardAdded = adminLogin.verifyCCAddedInCC(base.Dictionary.get("PLAN" + "cc_Query"), "PLAN", gdCardFirstName,
				gdCardLastName, gdCardNum, gdCardExpiry, gdZip, gdAddress);

		Assert.assertTrue(cardAdded, "First Card saved and verified in CC Query");
		cardAdded = adminLogin.verifyCCAddedInCC(base.Dictionary.get("PLAN" + "cc_Query"), "PLAN", gdCardFirstNameSec,
				gdCardLastNameSec, gdCardNumSec, gdCardExpirySec, gdZipSec, gdAddressSec);
		Assert.assertTrue(cardAdded, "Second Card saved and verified in CC Query");

		System.out.println(cardAdded);

	}

	@And("^Single card added is not saved and verified in cc query (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void testCCCardNotSaved(String email, String password, String gdCardFirstName, String gdCardLastName,
			String gdCardNum, String gdCardExpiry, String gdZip, String gdAddress) throws Exception {
		boolean cardAdded;

		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		base.Dictionary.put("AccessToken", accessToken.getAccessToken(email, password));

		accountId = accessToken.getAccountId(base.Dictionary.get("AccessToken"));

		invoiceId = invoiceNew.getInvoiceNumber();

		System.out.println(accountId);

		gdCardFirstName = (String) base.getGDValue(gdCardFirstName);
		gdCardLastName = (String) base.getGDValue(gdCardLastName);
		gdCardNum = (String) base.getGDValue(gdCardNum);
		gdCardExpiry = (String) base.getGDValue(gdCardExpiry);
		gdZip = (String) base.getGDValue(gdZip);

		gdAddress = (String) base.getGDValue(gdAddress);

		adminLogin.getCCQuery(accountId, "PLAN");
		System.out.println("Response" + base.Dictionary.get("PLAN" + "cc_Query"));
		cardAdded = adminLogin.verifyCCAddedInCC(base.Dictionary.get("PLAN" + "cc_Query"), "PLAN", gdCardFirstName,
				gdCardLastName, gdCardNum, gdCardExpiry, gdZip, gdAddress);

		Assert.assertFalse(cardAdded, "Card not saved and verified in CC Query");
		System.out.println(cardAdded);

	}

	@And("^Single card added is saved and verified in cc query (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void testCCCardSaved(String email, String password, String gdCardFirstName, String gdCardLastName,
			String gdCardNum, String gdCardExpiry, String gdZip, String gdAddress) throws Exception {
		boolean cardAdded;

		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		base.Dictionary.put("AccessToken", accessToken.getAccessToken(email, password));

		accountId = accessToken.getAccountId(base.Dictionary.get("AccessToken"));

		invoiceId = invoiceNew.getInvoiceNumber();

		System.out.println(accountId);

		gdCardFirstName = (String) base.getGDValue(gdCardFirstName);
		gdCardLastName = (String) base.getGDValue(gdCardLastName);
		gdCardNum = (String) base.getGDValue(gdCardNum);
		gdCardExpiry = (String) base.getGDValue(gdCardExpiry);
		gdZip = (String) base.getGDValue(gdZip);

		gdAddress = (String) base.getGDValue(gdAddress);

		adminLogin.getCCQuery(accountId, "PLAN");
		System.out.println("Response" + base.Dictionary.get("PLAN" + "cc_Query"));
		cardAdded = adminLogin.verifyCCAddedInCC(base.Dictionary.get("PLAN" + "cc_Query"), "PLAN", gdCardFirstName,
				gdCardLastName, gdCardNum, gdCardExpiry, gdZip, gdAddress);

		Assert.assertTrue(cardAdded, "Card saved and verified in CC Query");

		System.out.println(cardAdded);

	}

	@And("^User selects Pay Other from Payment Option dropdown$")
	public void selectPayOther() {
//		try {
//		invoiceNew.selectPayOther();
//		}
	//	catch(Exception e) {
			invoiceNew.amountforPaymentNew();
	//	}

	}

	@And("^User selects Payment Plan from Payment Option dropdown$")
	public void selectPaymentPlan() {
		invoiceNew.selectPaymentPlan();

	}

	@And("^User selects Pay In Full from Payment Option dropdown$")
	public void selectPayInFull() {
		invoiceNew.selectPayFull();
	}

	@Then("^Amount Due and Pay Today is displayed$")
	public void amountDuePayTodayDisplayed() {
		invoiceNew.amountDuePayTodayDisplayed();
	}

	@Then("^Amount Due is displayed$")
	public void amountDueDisplayed() {
		invoiceNew.amountDueDisplayed();
	}

	@When("^User clicks on add or select payment method$")
	public void clickAddPaymentMethod() {
		invoiceNew.clickSelectOrAddPaymentLink();

	}

	@Then("^Select Payment Method pop up appears with added cards and Add New Payment button$")
	public void popUpDisplayed() {
		invoiceNew.selectPaymentMethodDisplayed();
	}

	@When("^User selects first added card$")
	public void selectAddedCard() {
		invoiceNew.selectFirstExistingCard();
	}

	@Then("^CVV field is displayed$")
	public void ccvFieldDisplayed() {
		invoiceNew.cvvDisplayed();
	}

	@When("^User selects two added card$")
	public void selectTwoAddedCard() {
		invoiceNew.selectFirstTwoExistingCard();
	}

	@When("^User enters first two cvv, clicks on Continue button$")
	public void enterCVVForTwo() throws Exception {
		invoiceNew.enterCVVFirstTwoExistingCard();
		invoiceNew.clickContinuePopUp();

	}

	@When("^User enters first cvv, clicks on Continue button$")
	public void enterCVV() throws Exception {
		invoiceNew.enterCVVFirstExistingCard();
		invoiceNew.clickContinuePopUp();
	}

	@When("^User enters wrong cvv, clicks on Continue button$")
	public void enterWrongCVV() throws Exception {
		invoiceNew.enterWrongCVVFirstExistingCard();
		invoiceNew.clickContinuePopUp();
	}

	@Then("^Payment card is select and Amount is autopopulated$")
	public void amountDisplayed() {
		if (((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && base.Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			invoiceNew.amountFieldDisplayedNew();
		} else
		//	accountBalance = dashboard.getAccountBalance();
		      invoiceNew.amountFieldDisplayedNew();
		  //invoiceNew.amountFieldDisplayedNew();
	}

	@Then("^Payment card is selected, Amount Due is populated and Continue gets enabled$")
	public void cardSelectedAndContinueEnabled() {
		if (((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
				&& base.Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
		} else
			accountBalance = dashboard.getAccountBalance();
		System.out.println(accountBalance);
		invoiceNew.cardAddedAndContinueEnabled();
	}

	@When("^User clicks on Continue button in Payment Section$")
	public void clickContinuePaymentSection() {
		invoiceNew.clickContinuePaymentSection();
	}

	@And("^Amount is populated for each card$")
	public void amountForEachCard() {
		invoiceNew.amountForAllCard();
	}

	@Then("^Review Your Payment section is displayed with Confirm button$")
	public void reviewSectionDisplayed() {
		invoiceNew.reviewSectionDisplayed();
	}

	@When("^User clicks on Confirm button$")
	public void clickConfirmButton() {
		invoice.selectAcceptTnC();
		invoiceNew.clickConfirmButton();
	}

	@Then("^Confirm Payment pop-up is displayed$")
	public void confirmPaymentPopUp() {
		invoiceNew.validateConfirmPopUp();
	}

	@When("^User enters cvv,clicks on Continue button in Confirm Payment$")
	public void clickContinueConfirmPayment() throws Exception {
		invoiceNew.enterCVVinConfirmPopUp();
		invoiceNew.clickContinuePopUp();
	}
	
	@Then("^Verify Payment is successfull$")
	public void verify_payment_successfull() {
		invoiceNew.verifySuccessMessage();

	}

	
	
	@Then("^Payment of amount (.+) should be successfull and amount should get updated$")
	public void paymentValidation(String amount) {
		String payamount = (String) base.getGDValue(amount);
		invoiceNew.validateAmountUpdatedAndGetAmountDebittedNew();
		String AmountDue = invoiceNew.getamountDue();
		String previousBalance = base.Dictionary.get("DUE_BALANCE");
		if (((driverType.trim().toUpperCase().contains("ANDROID")
				|| driverType.trim().toUpperCase().contains("IOS")))) {
		} else {
			System.out.println(AmountDue);
			Double CalculativeAmountDue= Double.valueOf(previousBalance) - Double.valueOf(payamount);
			Assert.assertTrue(AmountDue.contains(String.valueOf(CalculativeAmountDue)),"Amount are correctly displayed");
			System.out.println(Double.valueOf(CalculativeAmountDue));
			invoice.getInvoiceBalWhenRefreshed(invoiceId, Double.valueOf(CalculativeAmountDue));
		}
	}

	
	//payment with typeform review
	@Then("^Typeform submitted and Payment should be successfull and amount should get updated$")
	public void reviewpaymentValidation() throws AWTException {
		Double debitAmount = invoiceNew.validateAmountUpdatedAndGetAmountDebitted(accountBalance);
		System.out.println(debitAmount);

		if (((driverType.trim().toUpperCase().contains("ANDROID")
				|| driverType.trim().toUpperCase().contains("IOS")))) {
		} else {
			System.out.println(BalanceDue);
			System.out.println(BalanceDue - debitAmount);
			double x = BalanceDue - debitAmount;
			System.out.println(x);
			invoice.getInvoiceBalWhenRefreshed(invoiceId, x);
			
			//verify and submiiting typeform
			try {
				invoice.verify_surveytab_display_payment();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	@Then("^Payment should be successfull and amount should get updated for addons$")
	public void paymentValidationAddOns() {
		
		Double amounttt = Double.parseDouble(base.Dictionary.get("AddOnSCAmount")) + Double.parseDouble(accountBalance.replaceAll("[^0-9.]", ""));
		Double debitAmount = invoiceNew.validateAmountUpdatedAndGetAmountDebitted(""+amounttt+"");
		System.out.println(debitAmount);
		invoiceId = invoiceNew.getUpdatedInvoiceNumber();

	}

	@Then("^Payment should be declined and message must be displayed$")
	public void paymentDeclinedValidation() {
		invoiceNew.transactionDeclined();
	}

	//user entering amount
	@When("^User enters (.+),pay today gets updated, user clicks on Continue button$")
	public void enterAmount(String amount) throws Exception {
		amount = (String) base.getGDValue(amount);
		System.out.println(amount);
		invoiceNew.enterAmount(amount);
		utils.sync(900l);
	}
	
	@When("^User enters CVV number of Card$")
	public void user_enters_cvv_card() throws Exception {
		 String CVV1= base.Dictionary.get("CVV_CARD");
		 String CVV2= base.Dictionary.get("CVV_CARD_AE");
		 try {
		invoiceNew.typeCVV(CVV1);
		invoiceNew.selectTnC();
		invoiceNew.getContinuePaymentSection();
		}
		 catch(Exception e) {
			 invoiceNew.typeCVV(CVV2);
			 invoiceNew.selectTnC();
			 invoiceNew.getContinuePaymentSection(); 
		 }
	}

	@When("^User clicks on Add New Card, adds new card without saving to account (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void addNewCard(String gdCardFirstName, String gdCardLastName, String gdCardNum, String gdCardExpiry,
			String gdZip, String gdAddress) throws Exception {
		String saveCard = "NO";

		gdCardFirstName = (String) base.getGDValue(gdCardFirstName);
		gdCardLastName = (String) base.getGDValue(gdCardLastName);
		gdCardNum = (String) base.getGDValue(gdCardNum);
		gdCardExpiry = (String) base.getGDValue(gdCardExpiry);
		gdZip = (String) base.getGDValue(gdZip);
		;
		gdAddress = (String) base.getGDValue(gdAddress);
		System.out.println(gdCardFirstName);
		System.out.println(gdCardLastName);
		System.out.println(gdCardNum);
		System.out.println(gdCardExpiry);
		//invoiceNew.addNewCard(saveCard, gdCardFirstName, gdCardLastName, gdCardNum, gdCardExpiry, gdZip, gdAddress);
		invoiceNew.clickcancelbutton();

	}

	@When("^User enters cvv for added card, clicks on Continue button$")
	public void enterCVVForAddedCard() throws Exception {
		//invoiceNew.typeCVV();
		invoiceNew.enterCVVForAddedCard();
	//	invoiceNew.clickContinuePopUp();

	}

	@When("^User clicks on Add New Card, adds two new card without saving to account (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void addTwoNewCard(String gdCardFirstName, String gdCardLastName, String gdCardNum, String gdCardExpiry,
			String gdZip, String gdAddress, String gdCardFirstNameSec, String gdCardLastNameSec, String gdCardNumSec,
			String gdCardExpirySec, String gdZipSec, String gdAddressSec) throws Exception {

		String saveCard = "NO";

		gdCardFirstName = (String) base.getGDValue(gdCardFirstName);
		gdCardLastName = (String) base.getGDValue(gdCardLastName);
		gdCardNum = (String) base.getGDValue(gdCardNum);
		gdCardExpiry = (String) base.getGDValue(gdCardExpiry);
		gdZip = (String) base.getGDValue(gdZip);
		;
		gdAddress = (String) base.getGDValue(gdAddress);

		gdCardFirstNameSec = (String) base.getGDValue(gdCardFirstNameSec);
		gdCardLastNameSec = (String) base.getGDValue(gdCardLastNameSec);
		gdCardNumSec = (String) base.getGDValue(gdCardNumSec);
		gdCardExpirySec = (String) base.getGDValue(gdCardExpirySec);
		gdZipSec = (String) base.getGDValue(gdZipSec);
		;
		gdAddressSec = (String) base.getGDValue(gdAddressSec);
		System.out.println(gdCardFirstNameSec);
		System.out.println(gdCardLastNameSec);
		System.out.println(gdCardNumSec);
		System.out.println(gdCardExpirySec);
	//	invoiceNew.addNewCard(saveCard, gdCardFirstName, gdCardLastName, gdCardNum, gdCardExpiry, gdZip, gdAddress);

	//	invoiceNew.addNewCard(saveCard, gdCardFirstNameSec, gdCardLastNameSec, gdCardNumSec, gdCardExpirySec, gdZipSec,
		//		gdAddressSec);

	}

	@When("^User clicks on Add New Card, adds new card and saves to account (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void addNewCardSaveToAccount(String gdCardFirstName, String gdCardLastName, String gdCardNum,
		String gdCardExpiry, String gdZip, String gdAddress) throws Exception {
		String saveCard = "YES";

		gdCardFirstName = (String) base.getGDValue(gdCardFirstName);
		gdCardLastName = (String) base.getGDValue(gdCardLastName);
		gdCardNum = (String) base.getGDValue(gdCardNum);
		gdCardExpiry = (String) base.getGDValue(gdCardExpiry);
		gdZip = (String) base.getGDValue(gdZip);

		gdAddress = (String) base.getGDValue(gdAddress);
		System.out.println(gdCardFirstName);
		System.out.println(gdCardLastName);
		System.out.println(gdCardNum);
		System.out.println(gdCardExpiry);
		invoiceNew.addNewCard(saveCard, gdCardFirstName, gdCardLastName, gdCardNum, gdCardExpiry, gdZip, gdAddress);

	}
	
	@When("^User clicks on Add New Card, adds new card with invalid data (.+), (.+), (.+), (.+)$")
	public void addNewCardWithNegativeValue(String gdCardFirstName, String gdCardLastName, String gdCardNum,String gdCardExpiry) throws Exception {
		String saveCard = "YES";
		gdCardFirstName = (String) base.getGDValue(gdCardFirstName);
		gdCardLastName = (String) base.getGDValue(gdCardLastName);
		gdCardNum = (String) base.getGDValue(gdCardNum);
		gdCardExpiry = (String) base.getGDValue(gdCardExpiry);
		System.out.println(gdCardFirstName);
		System.out.println(gdCardLastName);
		System.out.println(gdCardNum);
		System.out.println(gdCardExpiry);
		invoiceNew.addNewCardNegative(saveCard, gdCardFirstName, gdCardLastName, gdCardNum, gdCardExpiry);	
		
	}
	
	@When("^User clicks on Add New Card, adds new card and saves to account,CMS Validation (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void addNewCardSaveToAccountVerifyCMSChanges(String gdCardFirstName, String gdCardLastName, String gdCardNum,
			String gdCardExpiry, String gdZip, String gdAddress) throws Exception {
		String saveCard = "YES";

		gdCardFirstName = (String) base.getGDValue(gdCardFirstName);
		gdCardLastName = (String) base.getGDValue(gdCardLastName);
		gdCardNum = (String) base.getGDValue(gdCardNum);
		gdCardExpiry = (String) base.getGDValue(gdCardExpiry);
		gdZip = (String) base.getGDValue(gdZip);

		gdAddress = (String) base.getGDValue(gdAddress);
		System.out.println(gdCardFirstName);
		System.out.println(gdCardLastName);
		System.out.println(gdCardNum);
		System.out.println(gdCardExpiry);
		invoiceNew.addNewCardAndVerifyLabelsCMS(saveCard, gdCardFirstName, gdCardLastName, gdCardNum, gdCardExpiry, gdZip, gdAddress,cms.zip,cms.state,cms.city,cms.lastName);
	}

	@When("^Click on Add New Card button, enter invalid card details and validate error messages$")
	public void wrongCardDetails() throws Exception {
		invoiceNew.addWrongNewCard();
	}

	@When("^User clicks on Terms and Condition link$")
	public void clickTnC() throws Exception {
		invoiceNew.clickTnCLink();
	}

	@When("^Terms and Condition are opened in new window$")
	public void tncOpenedInNewWindow() throws Exception {
//		invoiceNew.validateTnC();
		String tcheader = invoice.getTermsnCondition().trim();
		Assert.assertTrue(tcheader.contains("TERMS & CONDITIONS") || tcheader.contains("Terms of Use") ,"Terms and Condition popup gets open");
	}

	@When("^User clicks on Add New Card, adds two new card and saves to account (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void addTwoNewCardSaveToAccount(String gdCardFirstName, String gdCardLastName, String gdCardNum,
			String gdCardExpiry, String gdZip, String gdAddress, String gdCardFirstNameSec, String gdCardLastNameSec,
			String gdCardNumSec, String gdCardExpirySec, String gdZipSec, String gdAddressSec) throws Exception {

		String saveCard = "YES";

		gdCardFirstName = (String) base.getGDValue(gdCardFirstName);
		gdCardLastName = (String) base.getGDValue(gdCardLastName);
		gdCardNum = (String) base.getGDValue(gdCardNum);
		gdCardExpiry = (String) base.getGDValue(gdCardExpiry);
		gdZip = (String) base.getGDValue(gdZip);

		gdAddress = (String) base.getGDValue(gdAddress);

		gdCardFirstNameSec = (String) base.getGDValue(gdCardFirstNameSec);
		gdCardLastNameSec = (String) base.getGDValue(gdCardLastNameSec);
		gdCardNumSec = (String) base.getGDValue(gdCardNumSec);
		gdCardExpirySec = (String) base.getGDValue(gdCardExpirySec);
		gdZipSec = (String) base.getGDValue(gdZipSec);
		gdAddressSec = (String) base.getGDValue(gdAddressSec);
		System.out.println(gdCardFirstNameSec);
		System.out.println(gdCardLastNameSec);
		System.out.println(gdCardNumSec);
		System.out.println(gdCardExpirySec);
		invoiceNew.addNewCard(saveCard, gdCardFirstName, gdCardLastName, gdCardNum, gdCardExpiry, gdZip, gdAddress);
		invoiceNew.addNewCard(saveCard, gdCardFirstNameSec, gdCardLastNameSec, gdCardNumSec, gdCardExpirySec, gdZipSec,
				gdAddressSec);

	}

	@When("^User Selects Optional Invoice (.+), (.+)$")
	public void optionalInvoice(String email, String password) throws Exception {
		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		base.Dictionary.put("AccessToken", accessToken.getAccessToken(email, password));
		accountId = accessToken.getAccountId(base.Dictionary.get("AccessToken"));
		invoiceId = Integer.parseInt(adminLogin.getOptionalInvoiceId(accountId));
		if (invoiceId == -1)
			throw new SkipException("Invoice not found");
		invoiceNew.loadInvoice(invoiceId);
		invoiceNew.optionalLineItemDisplayed();
	}

	@When("^Add/Remove button should be displayed against line item$")
	public void optionalInvoiceDisp(String email, String password) throws Exception {
		invoiceNew.loadInvoice(invoiceId);
		invoiceNew.optionalLineItemDisplayed();
	}

	@And("^Add/Remove button should not be displayed against line item$")
	public void addRemoveButtonNotDisplayed() throws Exception {
		invoiceNew.loadInvoice(invoiceId);
		invoiceNew.addRemoveButtonNotDisplayed();
	}

	@When("^User clicks on Remove button of optional line item$")
	public void clickRemoveButton() {
		invoiceNew.clickRemoveButton();
	}

	@Then("^Optional Item is removed and link to Add it is displayed$")
	public void addLinkDisplayed() {
		invoiceNew.addLinkDisplayed();
	}

	@Then("^Select invoice and validate invoice balance, due date and description matches with TM API response (.+), (.+)$")
	public void testMapping(String email, String password) throws Exception {
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("No need to run on android/ios");

		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		String invoicestatus = "PLAN";
		base.Dictionary.put(invoicestatus + "_EMAIL_ADDRESS", email);
		base.Dictionary.put(invoicestatus + "_PASSWORD", password);
		adminLogin.beforeMtdInvoiceAPI(invoicestatus);
         if (base.Dictionary.get(invoicestatus + "invoiceId").trim().equalsIgnoreCase("")) {
			invoicestatus = "PAID";
			base.Dictionary.put(invoicestatus + "_EMAIL_ADDRESS", email);
			base.Dictionary.put(invoicestatus + "_PASSWORD", password);
			adminLogin.beforeMtdInvoiceAPI(invoicestatus);
			if (base.Dictionary.get(invoicestatus + "invoiceId").trim().equalsIgnoreCase(""))
				throw new SkipException("Invoice of status - " + invoicestatus.trim().toUpperCase() + " not found");
		}

		int invoiceId = Integer.valueOf(base.Dictionary.get(invoicestatus.trim().toUpperCase() + "invoiceId").trim());
		if (!invoicestatus.trim().equalsIgnoreCase("PAID"))
		Assert.assertTrue(invoice.verifyInvoiceStatus(invoiceId, invoicestatus), "Verify " + invoicestatus.trim().toLowerCase() + " invoice is displayed");
		Assert.assertEquals(invoice.getInvoiceDesc(invoiceId), adminLogin.getInvoiceFieldValues(base.Dictionary.get(invoicestatus.trim().toUpperCase() + "InvoiceListArray"), new String[] { "invoice_descriptions", "balances", "due_dates" }, invoiceId, invoicestatus) .get("invoice_descriptions"),
				"Verify invoice description matches with the result from TM Api");
		Assert.assertEquals(invoice.getInvoiceDesc(invoiceId),
				base.Dictionary.get(invoicestatus.trim().toUpperCase() + "invoice_descriptions"),
				"Verify invoice description matches with the result from TM Api");
		Assert.assertEquals(invoice.getInvoiceBal(invoiceId),
				base.Dictionary.get(invoicestatus.trim().toUpperCase() + "balances"),
				"Verify invoice balance matches with the result from TM Api");
		Assert.assertEquals(invoice.getInvoiceDueDt(invoiceId),
				base.Dictionary.get(invoicestatus.trim().toUpperCase() + "due_dates"),
				"Verify invoice due date matches with the result from TM Api");
	}

	@Then("^Select invoice and validate invoice details with TM API response (.+), (.+)$")
	public void verifyInvoiceDetailsMapping(String email, String password) throws Exception {
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("No need to run on android/ios");

		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		String invoicestatus = "PLAN";
		base.Dictionary.put(invoicestatus + "_EMAIL_ADDRESS", email);
		base.Dictionary.put(invoicestatus + "_PASSWORD", password);
		adminLogin.beforeMtdInvoiceAPI(invoicestatus);
		if (base.Dictionary.get(invoicestatus + "invoiceId").trim().equalsIgnoreCase("")) {
			invoicestatus = "PAID";
			base.Dictionary.put(invoicestatus + "_EMAIL_ADDRESS", email);
			base.Dictionary.put(invoicestatus + "_PASSWORD", password);
			adminLogin.beforeMtdInvoiceAPI(invoicestatus);
			if (base.Dictionary.get(invoicestatus + "invoiceId").trim().equalsIgnoreCase(""))
				throw new SkipException("Invoice of status - " + invoicestatus.trim().toUpperCase() + " not found");
		}

		int invoiceId = Integer.valueOf(base.Dictionary.get(invoicestatus.trim().toUpperCase() + "invoiceId").trim());
		if (!invoicestatus.trim().equalsIgnoreCase("PAID"))
			Assert.assertTrue(invoice.verifyInvoiceStatus(invoiceId, invoicestatus), "Verify " + invoicestatus.trim().toLowerCase() + " invoice is displayed");

		invoiceNew.loadInvoice(invoiceId);
		invoiceNew.isInvoiceSelected(invoiceId, null);

		Assert.assertTrue(invoiceNew.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");

		if (!invoicestatus.trim().equalsIgnoreCase("PAID"))
			invoice.isContinueButtonDisplayed();
		else
			base.sync(2000L);
	    invoiceNew.clickInvoiceDetail();
	 	Assert.assertEquals(invoiceNew.getInvoiceQty(), base.Dictionary.get(invoicestatus.trim().toUpperCase() + "num_seat"),
				"Verify invoice number of seats matches with the result from TM Api");
		Assert.assertEquals(invoiceNew.getSection(),
				base.Dictionary.get(invoicestatus.trim().toUpperCase() + "section_name"),
				"Verify invoice section name matches with the result from TM Api");
		Assert.assertEquals(invoiceNew.getRow(), base.Dictionary.get(invoicestatus.trim().toUpperCase() + "row_name"),
				"Verify invoice row name matches with the result from TM Api");
		Assert.assertEquals(invoiceNew.getFirstSeat(), base.Dictionary.get(invoicestatus.trim().toUpperCase() + "seat_num"),
				"Verify invoice seat number matches with the result from TM Api");
		Assert.assertEquals(invoiceNew.getLastSeat(), base.Dictionary.get(invoicestatus.trim().toUpperCase() + "last_seat"),
				"Verify invoice last seat matches with the result from TM Api");
		base.SoftAssert.assertEquals(invoiceNew.getInvoiceName(), base.Dictionary.get(invoicestatus.trim().toUpperCase() + "item_name"), "Verify invoice name matches with the result from TM Api");
		Assert.assertEquals(invoiceNew.getInvoicedAmt(), base.Dictionary.get(invoicestatus.trim().toUpperCase() + "invoiced_amount"), "Verify invoice amount matches with the result from TM Api");
	}

	@Then("^Invoice List is sorted (.+), (.+)$")
	public void invoiceListSorted(String email, String password) throws JSONException, Exception {
		if (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("No need to run on android/ios");

		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		base.Dictionary.put("UNPAID" + "_EMAIL_ADDRESS", email);
		base.Dictionary.put("UNPAID" + "_PASSWORD", password);
		base.Dictionary.put("AccessToken", accessToken.getAccessToken(email, password));

		accountId = accessToken.getAccountId(base.Dictionary.get("AccessToken"));

		List<List<String>> sortedListUnpaid = adminLogin.getSortedInvoiceListPaidOrUnpaid(accountId, "", "Unpaid");

		Assert.assertTrue(invoiceNew.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		Assert.assertEquals(invoice.getInvoiceList(), sortedListUnpaid,
				"Verify invoice list is sorted for unpaid invoices");

		invoiceNew.clickPaidTab();

		List<List<String>> sortedListPaid = adminLogin.getSortedInvoiceListPaidOrUnpaid(accountId, "", "Paid");
		System.out.println(sortedListPaid);
		System.out.println(invoice.getInvoiceList());
		Assert.assertEquals(invoice.getInvoiceList(), sortedListPaid,
				"Verify invoice list is sorted for paid invoices");
	}

	@Then("^Invoice Payment (.+), (.+)$")
	public void sads(String email, String password) throws JSONException, Exception {
		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		base.Dictionary.put("AccessToken", accessToken.getAccessToken(email, password));
		accountId = accessToken.getAccountId(base.Dictionary.get("AccessToken"));
		String invoicestatus = "PLAN";
		int invoiceId = adminLogin.getInvoiceListAndIdNew(accountId, invoicestatus);
		if (invoiceId == -1) {
			invoicestatus = "PARTIALLY PAID";
			invoiceId = adminLogin.getInvoiceListAndIdNew(accountId, invoicestatus);
			if (invoiceId == -1) {
				invoicestatus = "UNPAID";
				invoiceId = adminLogin.getInvoiceListAndIdNew(accountId, invoicestatus);
			}
		}
		if (invoiceId == -1)
			throw new SkipException("Invoice not found");
		
		System.out.println(invoiceId);
		
		String cookies = invoice.loginThruDrupalApi(email, password);
		String versionNumber = invoice.getTerms(cookies);
		String token = invoice.getCsrfToken(cookies);
		invoice.acceptTerms(cookies, token, versionNumber);
		String[] invoice_conf_ids = invoice.getInvoiceConfIds(invoiceId, cookies);
		
		invoiceNew.getCCQuery(cookies);

		token = invoice.getCsrfToken(cookies);
		String planid;
		if (invoicestatus.trim().equalsIgnoreCase("PLAN")) {
			planid = base.Dictionary.get(invoicestatus.trim().toUpperCase() + "planId");
		} else
			planid = invoice.getPaymentsPlanID(invoiceId, cookies);

		adminLogin.getPaymentSchedule(accountId, invoiceId, invoicestatus);
		System.out.println(base.Dictionary.get(invoicestatus.trim().toUpperCase() + "paymentPlansSchedule"));
		adminLogin.getPaymentScheduleMopValues(base.Dictionary.get(invoicestatus.trim().toUpperCase() + "paymentPlansSchedule"), invoicestatus);
		if(base.Dictionary.get(invoicestatus.trim().toUpperCase() + "CardNumber" + 0).isEmpty()) {
			throw new SkipException("Card is Expired for this Invoice");
		}
		token = invoice.getCsrfToken(cookies);
		invoiceNew.paymentsPlanInvoiceAPI(invoiceId, cookies, token, planid, base.Dictionary.get("member_id"), invoicestatus, invoice_conf_ids);
	}

	@Then("^Invoice Pay Other Payment (.+), (.+)$")
	public void payOtherAPI(String email, String password) throws JSONException, Exception {
		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		base.Dictionary.put("AccessToken", accessToken.getAccessToken(email, password));
		accountId = accessToken.getAccountId(base.Dictionary.get("AccessToken"));
		String invoicestatus = "UNPAID";
		int invoiceId = adminLogin.getInvoiceListAndIdNew(accountId, invoicestatus);
		if (invoiceId == -1) {
			invoicestatus = "PARTIALLY PAID";
			invoiceId = adminLogin.getInvoiceListAndIdNew(accountId, invoicestatus);
		}
		if (invoiceId == -1)
			throw new SkipException("Invoice not found");

		System.out.println(invoiceId);
		
		String cookies = invoice.loginThruDrupalApi(email, password);
		String versionNumber = invoice.getTerms(cookies);
		String token = invoice.getCsrfToken(cookies);
		invoice.acceptTerms(cookies, token, versionNumber);
		
		invoiceNew.getCCQuery(cookies,invoicestatus);
		adminLogin.getCCQuery(accountId, invoicestatus);

		adminLogin.getCCQueryFieldValues(base.Dictionary.get(invoicestatus.trim().toUpperCase() + "cc_Query"), new String[] { "cc_type", "cc_exp", "data_mask" }, invoicestatus);
		token = invoice.getCsrfToken(cookies);
		invoiceNew.paymentsInvoiceAPI(invoiceId, cookies, token, base.Dictionary.get("member_id"), invoicestatus);
	}
	
	@Then("^Check if Upsells are available (.+) and (.+)$")
	public void checkForUpsells(String email,String password) throws JSONException, Exception {
		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		base.Dictionary.put("AccessToken", accessToken.getAccessToken(email, password));
		accountId = accessToken.getAccountId(base.Dictionary.get("AccessToken"));
		invoiceId=invoice.getInvoiceId();
		System.out.println(base.Dictionary.get("UpsellResponse"));
	
		if(adminLogin.checkForUpsell(accountId, invoiceId))
		{
			invoiceNew.clickAddOnContinue();
		}
		System.out.println(base.Dictionary.get("UpsellResponse"));
	}
	
	@Then("^Verify Values in Upsell Dropdown from API and select first value$")
	public void verifyDropdownValuesFromAPIandSelectValue() {
		invoiceNew.verifyUpsellDropdownAndSelectAValue();
	}
	
	@Then("^Add first event on Add ons$")
	public void addFirstEvent() {
		invoiceNew.addFirstEvent();
	}
	
	@When("^User selects two value and clicks on Add button in Add ons Tab, Add on value gets updated, timer is verified and clicks on Continue, verify Amount Displayed on Tab (.+) (.+)$")
	public void clickAddButton(String email,String password) throws Exception {
		invoiceNew.selectAndAddValues();
		email = (String) base.getGDValue(email);
		password = (String) base.getGDValue(password);
		String cookies = invoice.loginThruDrupalApi(email, password);
		long time = TimeUnit.SECONDS.toMinutes(invoiceNew.drupal(cookies));
		
		invoiceNew.verifyTimer(String.valueOf(time));
	
		invoiceNew.clickAddOnContinue();
		invoiceNew.verifyAddOnAmountDisplayedOnTab();
		List<LogEntry> entries = base.getDriver().manage().logs().get(LogType.PERFORMANCE).getAll();
		System.out.println(entries.size() + " " + LogType.PERFORMANCE + " log entries found");
		
		for (LogEntry entry : entries) {
			if(entry.getMessage().contains("order_num")) {
			orderNumber = Integer.parseInt(entry.getMessage().substring(entry.getMessage().indexOf("order_num\\\":")+12,entry.getMessage().indexOf("order_num\\\":")+17));
			}
		}
	}
	
	@When("^Verify invoice Listing page using (.+) and (.+)$")
	public void verify_invoice_listing_page(String email, String pass) throws Exception {
		email = (String) base.getGDValue(email);
		pass = (String) base.getGDValue(pass);	    
		accountId = accessToken.getAccountId(base.Dictionary.get("AccessToken"));	    
	    String invoiceId = Integer.toString(adminLogin.getSingleInoviceId(accountId));
	     System.out.println("Invoice ID:   "+invoiceId);
	    if (adminLogin.getSingleInoviceId(accountId)==-1) {
	    	System.out.println("No Invoice Id Found for the user");
	    	utils.navigateTo("/invoice#/");
	    	Assert.assertEquals(invoiceNew.getInvoicePlaceholder(),"There is no invoice to pay at the moment.","No Invoices present for the user");
	    }
	    else {
        	dashboard.clickViewAllInvoice();
		    invoiceNew.isInvoiceListDisplayed();
			Assert.assertTrue(base.getDriver().getCurrentUrl().contains("/invoice"));
			utils.navigateTo("/invoice#/"+invoiceId+"/1");
		
				
			try {
			Assert.assertTrue(invoiceNew.IsInvoiceSummaryDisplayed(), "Invoice Summary Details are getting displayed");
			}
			catch(Exception e) {
				if(invoiceNew.IsInvoicePrintButtonDisplayed())
					Assert.assertTrue(invoiceNew.IsInvoicePrintButtonDisplayed(), "Invoice Print Details are getting displayed");	
						else
							throw new Exception();
					}
			}
			    		
	}
	
	@When("^Amount Due should be updated with Add on amount and service charge$")
	public void amoundDueAddedWithAddonAndServiceCharge() throws JSONException, IOException, Exception {
		invoiceNew.verifyAmountDueUpdatedWithAddOnAmount(adminLogin.getTotalServiceCharge(accountId, invoiceId,orderNumber));
	}
	@And("^Add on event must be displayed in Invoice Summary$")
	public void addOnEventDisplayedInInvoiceSummary() {
		invoiceNew.loadInvoice(invoiceId);
		invoiceNew.addOnEventDisplayedInInvoiceSummary();
	}
	
	@And("^Verify Invoice Summary Section Labels changed from CMS$")
	public void verifyInvoiceLabelChanges() {
		invoiceNew.verifySummaryTab(cms.summaryName, cms.invoiceSummaryMessage, cms.subtotalName, cms.fieldNameAuto, cms.amountDueName);
	}
	
	@And("^Verify Invoice Payment Section Labels changed from CMS$")
	public void verifyInvoiceLabelPaymentChanges() {
		invoiceNew.verifyPaymentTab(cms.paymentName, cms.fieldNameAuto, cms.amountDueName, cms.paytodayName);
	}
	
	@And("^Verify Invoice Review Section Labels changed from CMS$")
	public void verifyInvoiceLabelReviewChanges() {
		invoiceNew.verifyReviewTab(cms.reviewName, cms.subtotalName, cms.fieldNameAuto, cms.amountDueName, cms.paytodayName);
	}
	
	@Then("^User can verify Edit Payment Method present in UI and click on Edit Payment Method$")
	public void EditPaymentMethod() throws Exception {
	   invoiceNew.verifyEditPaymentMethod();
	}
	
	@Then("^User enter CVV and verify field is displayed$")
	public void enterCVVverifyfielddisplayed() throws Exception {
	   invoiceNew.verifyCVVandEnter();
	   invoiceNew.enterCVVFirstExistingCard();
	   invoiceNew.clickInactiveCvv();
	   invoiceNew.clickContinuePopUp();
	}
	
	@Then("^User Click on Cancel button$")
	public void ClickCancebuttonAddPaymentPage() throws Exception {
	    invoiceNew.clickcancelbutton();
	}
	
	@Then("^Click on Updated Invoice button that is displayed$")
	public void click_on_Updated_Invoice_button_that_is_displayed() throws Exception {
	    invoiceNew.clickOnViewUpdatedInvoiceButton();
	}
	
	@When("^User Enters cvv and accept Terms and Conditions$")
	public void user_Enters_cvv_and_accept_Terms_and_Conditions() throws Exception {
		invoiceNew.enterCVVinConfirmPopUp();
		invoiceNew.acceptTermsAndConditions();
	}
}

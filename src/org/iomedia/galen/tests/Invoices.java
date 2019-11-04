package org.iomedia.galen.tests;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.iomedia.framework.Driver;
import org.iomedia.galen.common.AccessToken;
import org.iomedia.galen.common.ManageticketsAPI;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.pages.AdminLogin;
import org.iomedia.galen.pages.DashboardHeader;
import org.iomedia.galen.pages.DashboardSection;
import org.iomedia.galen.pages.Hamburger;
import org.iomedia.galen.pages.Homepage;
import org.iomedia.galen.pages.Invoice;
import org.iomedia.galen.pages.MobileSectionTabs;
import org.json.JSONObject;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;

@CucumberOptions(plugin = "json:target/cucumber-report-feature-composite.json", format = "pretty", features = {"features/invoiceNew.feature", "features/userJourneys.feature"}, glue = {"org.iomedia.galen.steps"}, monochrome = true, strict = true)
public class Invoices extends Driver{
	
	private AdminLogin adminlogin;
	private Homepage homepage; 
	private Invoice invoice;
	private DashboardHeader dashboardHeader;
	private Utils utils;
	private DashboardHeader header;
	private DashboardSection dashboardSection;
	private MobileSectionTabs mobileSecTabs;
	private ManageticketsAPI manageticketsapi;
	String host;
	Hamburger hamburger;
	private String driverType;
	private AccessToken accessTokens;
	
	@BeforeMethod(alwaysRun=true)
	public void init(){
		header = new DashboardHeader(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		dashboardSection = new DashboardSection(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
		homepage = new Homepage(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		invoice = new Invoice(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);;
		utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		manageticketsapi = new ManageticketsAPI(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		hamburger = new Hamburger(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		mobileSecTabs = new MobileSectionTabs(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
		dashboardHeader = new DashboardHeader(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		host = Environment.get("TM_HOST").trim();
		Dictionary.remove("invoiceId");
		driverType = driverFactory.getDriverType().get();
		accessTokens = new AccessToken(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		adminlogin = new AdminLogin(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	}
	
	@Test(groups={"smoke","invoicesFunctional","regression","criticalbusiness"})
	public void verifyPartialPaymentNewCard() throws Exception{
		load("/invoice");
		homepage.login("", "", null, true);
		invoice.clickFirstUnpaidInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		String invDue = invoice.amountDue();
		invoice.clickContinueButton();
		invoice.clickpayInFull();
		invoice.clickContinuePlan();
		invoice.clickAddNewCard();
		invoice.typeCardNumber(Dictionary.get("CardNumber"));
		invoice.typeExpiryDate(Dictionary.get("ExpiryDate"), null);
		invoice.typeCardCVV(Dictionary.get("CVV"));
		invoice.clickContinueCard();
		invoice.typeBillingDetails(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Address_1"), Dictionary.get("Address_2"), Dictionary.get("Country"), Dictionary.get("State"), Dictionary.get("City"), Dictionary.get("ZipCode"));
		invoice.clickContinueBilling();
		invoice.typePartialAmount(invDue, Dictionary.get("PartialAmt"));
		invoice.selectAcceptTnC();
		String dueAmt;
		double newDueAmt = 0;
		if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS")){
			dueAmt=invoice.getHeaderAmount();
			newDueAmt = Double.parseDouble(dueAmt) - Double.parseDouble(Dictionary.get("PartialAmt"));
		}
		double newInvDue=Double.parseDouble(invDue) - Double.parseDouble(Dictionary.get("PartialAmt"));
		Assert.assertEquals("Pay In Full", invoice.paymentoption(), "Verify payment option on review page");
		invoice.clickConfirmPay();	
		Assert.assertEquals(invoice.getSuccessHead(), Dictionary.get("SuccessHeading").trim(), "Verify success heading on confirmation page");
		Assert.assertTrue(invoice.getSuccessText().contains(Dictionary.get("SuccessText").trim()), "Verify success text on confirmation page");
		
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")){
			BaseUtil.navigateBack();
			Assert.assertEquals(invoice.getInvoiceBalWhenRefreshed(Integer.valueOf(Dictionary.get("invoiceId")), newInvDue), String.format("%.02f",newInvDue), "Verify invoice balance on listing after payment");
		} else{
			Assert.assertEquals(invoice.getSelectedInvoiceBalWhenRefreshed(newInvDue), String.format("%.02f",newInvDue), "Verify invoice balance on listing after payment");
			Assert.assertEquals(invoice.getHeaderAmountWhenRefreshed(newDueAmt), String.format("%.02f",newDueAmt), "Verify invoice balance on header after payment");
		}
		
		invoice.verifyInvoiceStatus(Integer.valueOf(Dictionary.get("invoiceId")), "PARTIALLY PAID");
	}
	
	@Test(groups={"smoke","invoicesFunctional","regression","criticalbusiness"})
	public void verifyFullPaymentNewCard() throws Exception {
		load("/invoice");
		homepage.login("", "", null, true);
		invoice.clickFirstUnpaidInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		String invDue = invoice.amountDue();
		invoice.clickContinueButton();
		invoice.clickpayInFull();
		invoice.clickContinuePlan();
		invoice.clickAddNewCard();
		invoice.typeCardNumber(Dictionary.get("CardNumber"));
		invoice.typeExpiryDate(Dictionary.get("ExpiryDate"), null);
		invoice.typeCardCVV(Dictionary.get("CVV"));
		invoice.clickContinueCard();
		invoice.typeBillingDetails(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Address_1"), Dictionary.get("Address_2"), Dictionary.get("Country"), Dictionary.get("State"), Dictionary.get("City"), Dictionary.get("ZipCode"));
		invoice.clickContinueBilling();
		invoice.selectAcceptTnC();
		String dueAmt;
		double newDueAmt = 0;
		if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS")){
			dueAmt=invoice.getHeaderAmount();
			newDueAmt = Double.parseDouble(dueAmt) - Double.parseDouble(invDue);
		}
		System.out.println(newDueAmt);
		Assert.assertEquals("Pay In Full", invoice.paymentoption(), "Verify payment option on review page");
		invoice.clickConfirmPay();	
		Assert.assertEquals(invoice.getSuccessHead(), Dictionary.get("SuccessHeading").trim(), "Verify success heading on confirmation page");
		Assert.assertTrue(invoice.getSuccessText().contains(Dictionary.get("SuccessText").trim()), "Verify success text on confirmation page");
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")){
			BaseUtil.navigateBack();
			Assert.assertEquals(invoice.getInvoiceBalWhenRefreshed(Integer.valueOf(Dictionary.get("invoiceId")), 0.00), "0.00", "Verify invoice balance on listing after payment");
		} else{
			Assert.assertEquals(invoice.getSelectedInvoiceBalWhenRefreshed(0.00), "0.00", "Verify invoice balance on listing after payment");
			Assert.assertEquals(invoice.getHeaderAmountWhenRefreshed(newDueAmt), String.format("%.02f", newDueAmt), "Verify invoice balance on header after payment");
		}
		
		invoice.verifyInvoiceStatus(Integer.valueOf(Dictionary.get("invoiceId")), "PAID");
	}
	
	@Test(groups={"invoicesFunctional","regression","criticalbusiness"})
	public void verifyPartialPaymentExistingCard() throws Exception{
		load("/invoice");
		homepage.login("", "", null, true);
		invoice.clickFirstUnpaidInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		String invDue = invoice.amountDue();
		invoice.clickContinueButton();
		invoice.clickpayInFull();
		invoice.clickContinuePlan();
		invoice.selectFirstCard();
		invoice.typeExistingCardCVV();
		invoice.clickContinueCard();
		invoice.clickContinueBilling();
		invoice.typePartialAmount(invDue, Dictionary.get("PartialAmt"));
		invoice.selectAcceptTnC();
		String dueAmt;
		double newDueAmt = 0;
		if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS")){
			dueAmt=invoice.getHeaderAmount();
			newDueAmt = Double.parseDouble(dueAmt) - Double.parseDouble(Dictionary.get("PartialAmt"));
		}
		double newInvDue=Double.parseDouble(invDue) - Double.parseDouble(Dictionary.get("PartialAmt"));
		Assert.assertEquals("Pay In Full", invoice.paymentoption(), "Verify payment option on review page");
		invoice.clickConfirmPay();	
		Assert.assertEquals(invoice.getSuccessHead(), Dictionary.get("SuccessHeading").trim(), "Verify success heading on confirmation page");
		Assert.assertTrue(invoice.getSuccessText().contains(Dictionary.get("SuccessText").trim()), "Verify success text on confirmation page");
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")){
			BaseUtil.navigateBack();
			Assert.assertEquals(invoice.getInvoiceBalWhenRefreshed(Integer.valueOf(Dictionary.get("invoiceId")), newInvDue), String.format("%.02f",newInvDue), "Verify invoice balance on listing after payment");
		} else{
			Assert.assertEquals(invoice.getSelectedInvoiceBalWhenRefreshed(newInvDue), String.format("%.02f",newInvDue), "Verify invoice balance on listing after payment");
			Assert.assertEquals(invoice.getHeaderAmountWhenRefreshed(newDueAmt), String.format("%.02f",newDueAmt), "Verify invoice balance on header after payment");
		}
		
		invoice.verifyInvoiceStatus(Integer.valueOf(Dictionary.get("invoiceId")), "PARTIALLY PAID");
	}
	
	@Test(groups={"invoicesFunctional","regression","criticalbusiness"})
	public void verifyFullPaymentExistingCard() throws Exception {
		load("/invoice");
		homepage.login("", "", null, true);
		invoice.clickFirstUnpaidInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		String invDue = invoice.amountDue();
		invoice.clickContinueButton();
		invoice.clickpayInFull();
		invoice.clickContinuePlan();
		invoice.selectFirstCard();
		invoice.typeExistingCardCVV();
		invoice.clickContinueCard();
		invoice.clickContinueBilling();
		invoice.selectAcceptTnC();
		String dueAmt;
		double newDueAmt = 0;
		if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS")){
			dueAmt=invoice.getHeaderAmount();
			newDueAmt = Double.parseDouble(dueAmt) - Double.parseDouble(invDue);
		}
		Assert.assertEquals("Pay In Full", invoice.paymentoption(), "Verify payment option on review page");
		invoice.clickConfirmPay();	
		Assert.assertEquals(invoice.getSuccessHead(), Dictionary.get("SuccessHeading").trim(), "Verify success heading on confirmation page");
		Assert.assertTrue(invoice.getSuccessText().contains(Dictionary.get("SuccessText").trim()), "Verify success text on confirmation page");
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")){
			BaseUtil.navigateBack();
			Assert.assertEquals(invoice.getInvoiceBalWhenRefreshed(Integer.valueOf(Dictionary.get("invoiceId")), 0.00), "0.00", "Verify invoice balance on listing after payment");
		} else{
			Assert.assertEquals(invoice.getSelectedInvoiceBalWhenRefreshed(0.00), "0.00", "Verify invoice balance on listing after payment");
			Assert.assertEquals(invoice.getHeaderAmountWhenRefreshed(newDueAmt), String.format("%.02f",newDueAmt), "Verify invoice balance on header after payment");
		}
		
		invoice.verifyInvoiceStatus(Integer.valueOf(Dictionary.get("invoiceId")), "PAID");
	}
	
	@Test(groups={"invoicesFunctional","regression","prod"})
	public void verifyFullPaymentTillReview() throws Exception {
		load("/invoice");
		homepage.login("", "", null, true);
		invoice.clickFirstUnpaidInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		String invDue = invoice.amountDue();
		invoice.clickContinueButton();
		invoice.clickpayInFull();
		invoice.clickContinuePlan();
		invoice.clickAddNewCard();
		invoice.typeCardNumber(Dictionary.get("CardNumber"));
		invoice.typeExpiryDate(Dictionary.get("ExpiryDate"), null);
		invoice.typeCardCVV(Dictionary.get("CVV"));
		invoice.clickContinueCard();
		invoice.typeBillingDetails(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Address_1"), Dictionary.get("Address_2"), Dictionary.get("Country"), Dictionary.get("State"), Dictionary.get("City"), Dictionary.get("ZipCode"));
		invoice.clickContinueBilling();
		invoice.selectAcceptTnC();
		String dueAmt = null;
		if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS")){
			dueAmt = invoice.getHeaderAmount();
		}
		Assert.assertEquals("Pay In Full", invoice.paymentoption(), "Verify payment option on review page");
		DecimalFormat df=new DecimalFormat("0.00");
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			BaseUtil.navigateBack();
			Assert.assertEquals(invoice.getInvoiceBal(Integer.valueOf(Dictionary.get("invoiceId").trim())), df.format(Double.valueOf(invDue)), "Verify invoice balance on listing without payment");
		} else {
			Assert.assertEquals(invoice.getSelectedInvoiceBal(), df.format(Double.valueOf(invDue)), "Verify invoice balance on listing without payment");
			Assert.assertEquals(invoice.getHeaderAmount(), df.format(Double.valueOf(dueAmt)), "Verify invoice balance on header after payment");
		}
	}
	
	@Test(groups={"invoicesFunctional","regression"})
	public void payWithExistingMultiplecards() throws Exception{
		load("/invoice");
		homepage.login("", "", null, true);
		invoice.clickFirstUnpaidInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		String summaryamountDue = invoice.amountDue();
		invoice.clickContinueButton();
		invoice.clickPayWithMultipleCards();
		String selectpaymentoption = invoice.selectpaymentoption();
		invoice.clickContinuePlan();
		invoice.waitforMasterCard();
		invoice.selectFirstCard();
		invoice.typeExistingCardCVV();
		invoice.typePaymentAmount(summaryamountDue);
		invoice.clickContinueCard();
		invoice.clickContinueBilling();
		String reviewfinalamountDue = invoice.finalamountDue();
		String paymentoption = invoice.paymentoption();
		Assert.assertEquals(summaryamountDue, reviewfinalamountDue, "Strings are matching");
		Assert.assertEquals(selectpaymentoption, paymentoption, "Payments options are matching");
		invoice.selectAcceptTnC();
		/** Will uncomment once we have unpaid invoices in plenty **/
//		invoice.clickConfirm();
//		Assert.assertEquals(invoice.getSuccessHead(), Dictionary.get("SuccessHeading").trim(), "Verify success heading on confirmation page");
//		Assert.assertTrue(invoice.getSuccessText().contains(Dictionary.get("SuccessText").trim()), "Verify success text on confirmation page");
//		double balanceDue=Double.parseDouble(summaryamountDue) - Double.parseDouble(reviewfinalamountDue);
//    	String balance_due=Double.toString(balanceDue);
//    	Assert.assertEquals(balance_due, "0.0", "Verify balance due");
	}
	
	@Test(groups={"invoicesFunctional","regression"})
	public void verifyBillingaddForMulitpleCards() throws Exception{
		load("/invoice");
		homepage.login("", "", null, true);
		invoice.clickFirstUnpaidInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		String summaryamountDue = invoice.amountDue();
		System.out.println(summaryamountDue);
		DecimalFormat df = new DecimalFormat("0.00");
		String firstamount= df.format(Double.parseDouble(summaryamountDue) - 1);
		//System.out.println("firstamount"+firstamount);
		invoice.clickContinueButton();
		invoice.clickPayWithMultipleCards();
		String selectpaymentoption = invoice.selectpaymentoption();
		invoice.clickContinuePlan();
		invoice.waitforMasterCard();
		invoice.selectFirstCard();
		String cardnumber=invoice.ExistingCardCVV();
		invoice.typeExistingCardCVV();
		invoice.typePaymentAmount(firstamount);
		invoice.selectSecondCard();
		String secondCardNo=invoice.ExistingSecondCardCVV();
		invoice.typeSecondCardCVV();
		String paymentamount="1";
		invoice.typeSecondPaymentAmount(paymentamount);
		
		invoice.clickContinueCard();
		invoice.cardlastDigit();
		Assert.assertEquals(cardnumber, invoice.cardlastDigit(),"card last four digits are matching");
		invoice.clickRightArrow();
		invoice.cardlastDigit();
		Assert.assertEquals(secondCardNo, invoice.cardlastDigit(),"Second card last four digits are matching");
		
		invoice.clickContinueBilling();
		String reviewfinalamountDue = invoice.finalamountDue();
		String paymentoption = invoice.paymentoption();
		Assert.assertEquals(summaryamountDue, reviewfinalamountDue, "Strings are matching");
		Assert.assertEquals(selectpaymentoption, paymentoption, "Payments options are matching");
		invoice.selectAcceptTnC();
		/** Will uncomment once we have unpaid invoices in plenty **/
//		invoice.clickConfirm();
//		Assert.assertEquals(invoice.getSuccessHead(), Dictionary.get("SuccessHeading").trim(), "Verify success heading on confirmation page");
//		Assert.assertTrue(invoice.getSuccessText().contains(Dictionary.get("SuccessText").trim()), "Verify success text on confirmation page");
//		double balanceDue=Double.parseDouble(summaryamountDue) - Double.parseDouble(reviewfinalamountDue);
//    	String balance_due=Double.toString(balanceDue);
//    	Assert.assertEquals(balance_due, "0.0", "Verify balance due");
	}
	
	@Test(groups={"invoicesFunctional","regression"})
	public void payWithNewMultiplecards() throws Exception{
		load("/invoice");
		homepage.login("", "", null, true);
		invoice.clickFirstUnpaidInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		String invDue = invoice.amountDue();
		invoice.clickContinueButton();
		invoice.clickPayWithMultipleCards();
		String selectpaymentoption=invoice.selectpaymentoption();
		invoice.clickContinuePlan();
		invoice.clickAddNewCard();
		invoice.typeCardNumber(Dictionary.get("CardNumber"));
		invoice.typeExpiryDate(Dictionary.get("ExpiryDate"), null);
		invoice.typeCardCVV(Dictionary.get("CVV"));
		invoice.typeNewPaymentAmount(invDue);
		invoice.clickContinueCard();
		invoice.typeBillingDetails(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Address_1"), Dictionary.get("Address_2"), Dictionary.get("Country"), Dictionary.get("State"), Dictionary.get("City"), Dictionary.get("ZipCode"));
		invoice.clickContinueBilling();
		String reviewfinalamountDue = invoice.finalamountDue();
		String paymentoption = invoice.paymentoption();
		Assert.assertEquals(invDue,reviewfinalamountDue, "Strings are matching");
		Assert.assertEquals(selectpaymentoption, paymentoption, "Payments options are matching");
		String dueAmt = null;
		double newDueAmt = 0;
		if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS")){
			dueAmt=invoice.getHeaderAmount();
			newDueAmt = Double.parseDouble(dueAmt) - Double.parseDouble(invDue);
		}
		invoice.selectAcceptTnC();
        invoice.clickConfirmPay();
		Assert.assertEquals(invoice.getSuccessHead(), Dictionary.get("SuccessHeading").trim(), "Verify success heading on confirmation page");
		Assert.assertTrue(invoice.getSuccessText().contains(Dictionary.get("SuccessText").trim()), "Verify success text on confirmation page");
		
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")){
			BaseUtil.navigateBack();
			Assert.assertEquals(invoice.getInvoiceBalWhenRefreshed(Integer.valueOf(Dictionary.get("invoiceId")), 0.00), "0.00", "Verify invoice balance on listing after payment");
		} else{
			Assert.assertEquals(invoice.getSelectedInvoiceBalWhenRefreshed(0.00), "0.00", "Verify invoice balance on listing after payment");
			Assert.assertEquals(invoice.getHeaderAmountWhenRefreshed(newDueAmt), String.format("%.02f",newDueAmt), "Verify invoice balance on header after payment");
		}
		
		invoice.verifyInvoiceStatus(Integer.valueOf(Dictionary.get("invoiceId")), "PAID");
		
    	double balanceDue = Double.parseDouble(invDue) - Double.parseDouble(reviewfinalamountDue);
    	String balance_due = Double.toString(balanceDue);
    	Assert.assertEquals(balance_due, "0.0", "Verify balance due");	 
	}
	
	@Test(groups={"invoicesFunctional","regression"})
	public void verifyPaidInvoiceTabsDisabled() throws Exception {
		load("/invoice");
		homepage.login("", "", null, true);
		invoice.clickFirstPaidInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		Assert.assertEquals(invoice.getInvoiceSummaryDueAmt(), "0.00");
		Assert.assertTrue(invoice.checkPaidContinueDisabled());	
	}
	
	@Test(groups={"invoicesFunctional","regression","criticalbusiness"})
	public void payWithPaymentPlansExistingCard() throws Exception{
		load("/invoice");
		homepage.login("", "" , null, true);
		invoice.clickFirstUnpaidInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		invoice.clickContinueButton();
		invoice.clickSelectPaymentPlanRadio();
		invoice.selectDefaultPaymentPlan();
		invoice.clickContinuePlan();
		invoice.waitforMasterCard();
		invoice.selectFirstCard();
		invoice.typeExistingCardCVV();
		invoice.clickContinueCard();
		invoice.clickContinueBilling();
		String reviewfinalamountDue=invoice.totalamountDue();
		System.out.println("reviewfinalamountDue:"+reviewfinalamountDue);
		invoice.selectAcceptTnC();
		//invoice.clickConfirm();
//		adminlogin.runInvoiceApis(Dictionary.get("EMAIL_ADDRESS"),Dictionary.get("PASSWORD"),Integer.parseInt(url));
//		String amount_due_today=Dictionary.get("amount_due_today");
//		Assert.assertEquals(amount_due_today,reviewfinalamountDue,"amount_due_today are matching");
	}
	
	@Test(groups={"invoicesFunctional","regression"})
	public void partiallyPayWithPaymentPlansExistingCard() throws Exception{
		load("/invoice");
		homepage.login("", "" , null, true);
		invoice.clickFirstUnpaidInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		String invDue = invoice.amountDue();
		invoice.clickContinueButton();
		invoice.clickSelectPaymentPlanRadio();
		invoice.selectDefaultPaymentPlan();
		invoice.clickContinuePlan();
		invoice.waitforMasterCard();
		invoice.selectFirstCard();
		invoice.typeExistingCardCVV();
		invoice.clickContinueCard();
		invoice.clickContinueBilling();
		String amt="1";
		invoice.typePartialAmount(invDue, amt);
		String reviewfinalamountDue=invoice.totalamountDue();
		System.out.println("reviewfinalamountDue:"+reviewfinalamountDue);
		invoice.selectAcceptTnC();
		//invoice.clickConfirm();
		//Assert.assertTrue(invoice.getSuccessText().contains(Dictionary.get("SuccessText").trim()), "Verify success text on confirmation page");
	}
	
	@Test(groups={"invoicesFunctional","regression"})
	public void verifyInvalidCardCvvTransDecline() throws Exception{
		load("/invoice");
		homepage.login("", "", null, true);
		invoice.clickFirstPlanInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		String invDue = invoice.amountDue();
		invoice.clickContinueButton();
//		invoice.clickpayInFull();
		invoice.clickContinuePlan();
		invoice.clickAddNewCard();
		invoice.typeCardNumber(Dictionary.get("CardNumber"));
		invoice.typeExpiryDate(Dictionary.get("ExpiryDate"), null);
		invoice.typeCardCVV(Dictionary.get("CVV"));
		invoice.clickContinueCard();
		invoice.typeBillingDetails(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Address_1"), Dictionary.get("Address_2"), Dictionary.get("Country"), Dictionary.get("State"), Dictionary.get("City"), Dictionary.get("ZipCode"));
		invoice.clickContinueBilling();
		invoice.typePartialAmount(invDue, Dictionary.get("PartialAmt"));
		invoice.selectAcceptTnC();
		Assert.assertEquals(invoice.paymentoption(), "Pay with Payment Plan", "Verify payment option on review page");
		invoice.clickConfirmPay();	
		Assert.assertEquals(invoice.getSuccessHead(), Dictionary.get("ErrorHeading").trim(), "Verify failure heading on confirmation page");
//		Assert.assertTrue(invoice.getSuccessText().contains(Dictionary.get("ErrorText").trim()), "Verify failure text on confirmation page");
		DecimalFormat df = new DecimalFormat("0.00");
		if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS"))
			Assert.assertEquals(invoice.getSelectedInvoiceBal(), df.format(Double.valueOf(invDue)));
	}
	
	@Test(groups={"invoicesFunctional","regression"})
	public void verifyInvalidCardPastExpiry() throws Exception{
		load("/invoice");
		homepage.login("", "", null, true);
		invoice.clickFirstPlanInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		invoice.clickContinueButton();
//		invoice.clickpayInFull();
		invoice.clickContinuePlan();
		invoice.clickAddNewCard();
		invoice.typeCardNumber(Dictionary.get("CardNumber"));
		invoice.typeExpiryDate(Dictionary.get("ExpiryDate"), null);
		invoice.typeCardCVV(Dictionary.get("CVV"));
		Assert.assertEquals(invoice.getContinueCardDisableAttr(), "true", "verify Continue Button disabled");
	}
	
	@Test(groups={"invoicesFunctional","regression","prod"})
	public void verifyFirstInvoiceSummary() throws Exception{
		load("/");
		homepage.login("", "", null, false);
		Assert.assertTrue(header.waitForDasboardHeader(), "Verify dashboard header is displayed");
		Assert.assertTrue(dashboardSection.waitForDasboardSection(null), "Verify dashboard section is displayed");
		
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			MobileSectionTabs mobilesectiontabs = new MobileSectionTabs(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
			mobilesectiontabs.clickInvoices();
		}
		String InvoiceName = invoice.getInvoiceText();		
		String Dashboardinvoice = invoice.getInvoiceLink();
		String invoice1 = Dashboardinvoice.substring(Dashboardinvoice.indexOf("#")).substring(2);   //getting the invoiceID from Dashboard
		String DueAmount = invoice.getDueAmountonDashboard();
		dashboardSection.clickInvoice();
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		String InvoicePage = getDriver().getCurrentUrl();
		Assert.assertTrue(InvoicePage.trim().endsWith(invoice1), "First Invoice summary is displayed from Invoice Listing");
		Assert.assertTrue(invoice.isInvoiceSelected(Integer.valueOf(invoice1.replaceAll("/.*", "")), null));
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			//Do Nothing
		} else {
			Assert.assertEquals(invoice.getInvoiceId(), Integer.parseInt(invoice1.replaceAll("/.*", "")), "Invoice Ids are matching");
			Assert.assertEquals(InvoiceName,invoice.getInvoicePageText(),"First Invoice Details are matching");
		}
		//	********************************Summary Verify with Balance Due************************************///
		Assert.assertEquals(Environment.get("currency") + invoice.amountDue(), Environment.get("currency") + DueAmount, "DueAmount of First Invoice are matching.");
	}
	
//	@Test(groups={"sanity"})
//	public void verifyEmailPrintFunctionality() throws Exception{
//		load("/");
//		homepage.login("", "", device, false);
//		try{
//		String InvoiceName = invoice.getInvoiceText();			
//		load("/invoice");
//		invoice.clickEmail();
//		invoice.clickPrint();
//		BaseUtil.switchToWindow(1);
//		Assert.assertEquals(getDriver().getCurrentUrl(), Environment.get("APP_URL").trim()+"/invoice/print/"+invoice.getInvoiceId(),"Print Functionality is working fine.");
//		}
//		catch(Exception j){
//			if(invoice.getInvoiceDashboardText().contentEquals("There is no invoice to pay at the moment.")){
//				System.out.println("There is no invoice to pay at the moment.");
//			}
//		}
//	}
	
	@Test(groups={"invoicesFunctional","regression","prod"})
	public void verifyInvoiceReview() throws Exception{
		load("/invoice");
		homepage.login("", "", null, true);
		Assert.assertTrue(invoice.isInvoiceListDisplayed(), "Verify invoice list is displayed");
		invoice.clickFirstPlanInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		Double ticketAmount = invoice.getTotalTicketAmount();		
		String LessPayment = invoice.getLessPaymentCredit();
		invoice.isContinueButtonDisplayed();
		invoice.clickContinueButton();
//		invoice.clickpayInFull();
		invoice.clickContinuePlan();
		invoice.clickAddNewCard();
		invoice.typeCardNumber(Dictionary.get("CardNumber"));
		invoice.typeExpiryDate(Dictionary.get("ExpiryDate"), null);
		invoice.typeCardCVV(Dictionary.get("CVV"));
		invoice.clickContinueCard();
		invoice.typeBillingDetails(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Address_1"), Dictionary.get("Address_2"), Dictionary.get("Country"), Dictionary.get("State"), Dictionary.get("City"), Dictionary.get("ZipCode"));
		//invoice.sendLastName(Dictionary.get("LastName"));
		invoice.clickContinueBilling();
	
		Assert.assertEquals(Double.parseDouble(invoice.totalamountDue()), ticketAmount, "Review Page is showing correct ticket amount");
		Assert.assertEquals(invoice.getReviewPaymentCredit(), LessPayment, "Review Page is showing correct Payment Credit");
	}
	
	@Test(groups={"invoicesFunctional","regression","prod"})
	public void verifyTermsnCondition() throws Exception{
		if(((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			throw new SkipException("Skipped");
		}
		load("/invoice");
		homepage.login("", "", null, true);
		Assert.assertTrue(invoice.isInvoiceListDisplayed(), "Verify invoice list is displayed");
		invoice.clickFirstPlanInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		invoice.clickContinueButton();
//		invoice.clickpayInFull();
		invoice.clickContinuePlan();
		invoice.clickAddNewCard();
		invoice.typeCardNumber(Dictionary.get("CardNumber"));
		invoice.typeExpiryDate(Dictionary.get("ExpiryDate"), null);
		invoice.typeCardCVV(Dictionary.get("CVV"));
		invoice.clickContinueCard();
		invoice.typeBillingDetails(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Address_1"), Dictionary.get("Address_2"), Dictionary.get("Country"), Dictionary.get("State"), Dictionary.get("City"), Dictionary.get("ZipCode"));
		invoice.clickContinueBilling();
		invoice.clickTermsnCond();
		BaseUtil.sync(2000L);
		String tcheader = invoice.getTermsnCondition().trim();
		Assert.assertTrue(tcheader.contains("TERMS & CONDITIONS") || tcheader.contains("Terms of Use") ,"Terms and Condition popup gets open");
	}
	
	@Test(groups={"smoke","invoicesFunctional","regression"})
	public void verifySuccessPaymentwithNewCard() throws Exception{
		load("/invoice");
		homepage.login("", "", null, true);
		Assert.assertTrue(invoice.isInvoiceListDisplayed(), "Verify invoice list is displayed");
		invoice.clickFirstPlanInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		String invDue = invoice.amountDue();
		invoice.clickContinueButton();
//		invoice.clickpayInsFull();
		invoice.clickContinuePlan();
		invoice.clickAddNewCard();			
		String CardType="VISA";
		String Cardno =utils.getCreditCardNumber(CardType);
		invoice.typeCardNumber(Cardno);	
		String CVV = Cardno.substring(Cardno.length()-3, Cardno.length());
		System.out.println(CVV);
		invoice.typeExpiryDate(Dictionary.get("ExpiryDate"), null);
		invoice.typeCardCVV(CVV);
		invoice.clickContinueCard();
		invoice.typeBillingDetails(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Address_1"), Dictionary.get("Address_2"), Dictionary.get("Country"), Dictionary.get("State"), Dictionary.get("City"), Dictionary.get("ZipCode"));
		invoice.clickContinueBilling();		
		invoice.typePartialAmount(invDue, Dictionary.get("EnterAmount"));
		invoice.selectAcceptTnC();			
		invoice.clickConfirmPay();
		Assert.assertEquals(invoice.getSuccessHead(), Dictionary.get("SuccessHeading").trim(), "Verify success heading on confirmation page");
		Assert.assertTrue(invoice.getSuccessText().contains(Dictionary.get("SuccessText").trim()), "Verify success text on confirmation page");
		//Back to dashboard
		invoice.clickContinueButton();
		
		Assert.assertTrue(header.waitForDasboardHeader(), "Verify dashboard header is displayed");
		Assert.assertTrue(dashboardSection.waitForDasboardSection(null), "Verify dashboard section is displayed");
		
		utils.navigateTo("/invoice#/" + Dictionary.get("invoiceId").trim() + "/1");
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		invoice.clickContinueButton();
//		invoice.clickpayInFull();
		invoice.clickContinuePlan();
		
		Assert.assertTrue(invoice.verifyNewCardSaved("Ending in " + Cardno.trim().substring(Cardno.trim().length() - 4) + " | Expiration Date " + Dictionary.get("ExpiryDate").trim(), "Ending in " + Cardno.trim().substring(Cardno.trim().length() - 4) + " | EXP. " + Dictionary.get("ExpiryDate").trim(), "Ending in " + Cardno.trim().substring(Cardno.trim().length() - 4) + " | Exp. Date " + Dictionary.get("ExpiryDate").trim()), "Verify new card got saved");
	}
	
	@Test(groups={"smoke","invoicesFunctional","regression"})
	public void verifyInvoiceMemberInformation() throws Exception{
		String accessToken = manageticketsapi.getAccessToken(Dictionary.get("EMAIL_ADDRESS").trim(),Dictionary.get("PASSWORD").trim());
		manageticketsapi.getAccountId(accessToken);
		JSONObject jsonObject = manageticketsapi.get(host + "/api/v1/member/" + Dictionary.get("member_id"), accessToken);
		String firstName = jsonObject.getString("first_name");
		// String middleName = jsonObject.getString("middle_name");
		String lastName = jsonObject.getString("last_name");
		String zipcode = jsonObject.getString("postal_code");	
//		JSONObject js = (JSONObject) jsonObject.get("country");
//		String Countryname = js.getString("name");
		
		load("/invoice");
		homepage.login("", "", null, true);
		Assert.assertTrue(invoice.isInvoiceListDisplayed(), "Verify invoice list is displayed");
		invoice.clickFirstPlanInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		invoice.clickContinueButton();
//		invoice.clickpayInFull();
		invoice.clickContinuePlan();
		invoice.clickAddNewCard();
		invoice.typeCardNumber(Dictionary.get("CardNumber"));
		invoice.typeExpiryDate(Dictionary.get("ExpiryDate"), null);
		invoice.typeCardCVV(Dictionary.get("CVV"));
		invoice.clickContinueCard();
		Assert.assertEquals(invoice.firstName(), firstName, "First Name is matching");
		Assert.assertEquals(invoice.lastName(), lastName, "Last Name is matching");
		Assert.assertEquals(invoice.zipCode(), zipcode, "ZipCode is matching");
//		Assert.assertEquals(invoice.CountryName(), Countryname, "Country Name is matching");
	}
	
	@Test(groups={"smoke","regression","invoicesUi","prod"}, dataProvider="devices")
	public void verifyInvoiceListingOnInvoicePage(TestDevice device) throws Exception{
		load("/invoice");
		homepage.login("", "", device, true);
		Assert.assertTrue(invoice.isInvoiceListDisplayed(), "Verify invoice list is displayed");
		
		if(!device.getName().trim().equalsIgnoreCase("mini-tablet") && !device.getName().trim().equalsIgnoreCase("mobile")){
			Assert.assertTrue(invoice.isInvoiceDetailDisplayed(device), "Verify invoice detail block is displayed");
		}
		
		checkLayout(Dictionary.get("SPEC_FILE_NAME").trim(), device.getTags());
	}
	
	@Test(groups={"smoke","regression","invoicesUi","prod"}, dataProvider="devices")
	public void verifyInvoiceSummary(TestDevice device) throws Exception{
		load("/invoice");
		homepage.login("", "", device, true);
		Assert.assertTrue(invoice.isInvoiceListDisplayed(), "Verify invoice list is displayed");
		invoice.clickFirstInvoiceLink(device);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(device), "Verify invoice detail block is displayed");
		String url = BaseUtil.getDriver().getCurrentUrl();
		String queryString = url.substring(url.indexOf("#")).substring(2);
		String[] dueAmounts = invoice.getInvoiceDetails(Dictionary.get("EMAIL_ADDRESS").trim(), Dictionary.get("PASSWORD").trim(), queryString);
		
		ManageticketsAPI manageticketsAPI = new ManageticketsAPI(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		Map<String, Object> names = manageticketsAPI.getCustomerName();
		names.put("TOTAL_TICKET_AMOUNT", dueAmounts[0]);
		names.put("PARKING_FEE", dueAmounts[1]);
		names.put("HANDLING_FEE", dueAmounts[2]);
		checkLayout(Dictionary.get("SPEC_FILE_NAME").trim(), device.getTags(), names);
	}
	
	@Test(groups={"smoke","regression","invoicesUi"}, dataProvider="devices")
	public void verifyPaymentOptions(TestDevice device) throws Exception{
		load("/invoice");
		homepage.login("", "", device, true);
		Assert.assertTrue(invoice.isInvoiceListDisplayed(), "Verify invoice list is displayed");
		
		invoice.clickFirstUnpaidInvoiceLink(device);
		
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(device), "Verify invoice detail block is displayed");
		
		invoice.clickContinueButton();
        invoice.clickSelectPaymentPlanRadio();
        invoice.selectDefaultPaymentPlan();
        
        if(Environment.get("env").trim().equalsIgnoreCase("QA"))
        	Assert.assertTrue(invoice.waitForPaymentPlanBreakage(), "Verify payment plan breakage is displayed");
		
        checkLayout(Dictionary.get("SPEC_FILE_NAME").trim(), device.getTags());
	}
	
	@Test(groups={"smoke","regression","invoicesUi"}, dataProvider="devices")
	public void verifyCardInfoPayFull(TestDevice device) throws Exception{
		load("/invoice");
		homepage.login("", "", device, true);
		Assert.assertTrue(invoice.isInvoiceListDisplayed(), "Verify invoice list is displayed");
		invoice.clickFirstPlanInvoiceLink(device);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(device), "Verify invoice detail block is displayed");
		
		invoice.clickContinueButton();
//        invoice.clickpayInFull();
        invoice.clickContinuePlan();
        invoice.waitforMasterCard();
        checkLayout(Dictionary.get("SPEC_FILE_NAME").trim(), device.getTags());
	}
	
	@Test(groups={"smoke","regression","invoicesUi"}, dataProvider="devices")
	public void verifyCardInfoMultiCards(TestDevice device) throws Exception{
		load("/invoice");
		homepage.login("", "", device, true);
		Assert.assertTrue(invoice.isInvoiceListDisplayed(), "Verify invoice list is displayed");
		
		invoice.clickFirstUnpaidInvoiceLink(device);
		
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(device), "Verify invoice detail block is displayed");
		
		invoice.clickContinueButton();
        invoice.clickPayWithMultipleCards();
        invoice.clickContinuePlan();
        invoice.waitforMasterCard();
        
        checkLayout(Dictionary.get("SPEC_FILE_NAME").trim(), device.getTags());
	}  
	
	@Test(groups={"smoke","regression","invoicesUi"}, dataProvider="devices")
	public void verifyBillingDetails(TestDevice device) throws Exception{
		load("/invoice");
		homepage.login("", "", device, true);
		Assert.assertTrue(invoice.isInvoiceListDisplayed(), "Verify invoice list is displayed");
		invoice.clickFirstPlanInvoiceLink(device);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(device), "Verify invoice detail block is displayed");
		
		invoice.clickContinueButton();
//        invoice.clickpayInFull();
        invoice.clickContinuePlan();
        boolean flag = false;
        try{
	        invoice.waitforMasterCard();
        } catch(Exception ex){
        	flag = true;
        }
        
        if(!flag){
			invoice.selectFirstCard();
			invoice.typeExistingCardCVV();
        } else{
        	invoice.clickAddNewCard();
    		invoice.typeCardNumber("4111111111111111");
    		invoice.typeExpiryDate("0520", device);
    		invoice.typeCardCVV("111");
        }
        invoice.clickContinueCard();
        checkLayout(Dictionary.get("SPEC_FILE_NAME").trim(), device.getTags());
	}
	
	@Test(groups={"smoke","regression","invoicesUi"}, dataProvider="devices")
	public void verifyReviewSection(TestDevice device) throws Exception{
		load("/invoice");
		homepage.login("", "", device, true);
		Assert.assertTrue(invoice.isInvoiceListDisplayed(), "Verify invoice list is displayed");
		invoice.clickFirstPlanInvoiceLink(device);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(device), "Verify invoice detail block is displayed");
		
		invoice.clickContinueButton();
//        invoice.clickpayInFull();
        invoice.clickContinuePlan();
        boolean flag = false;
        try{
	        invoice.waitforMasterCard();
        } catch(Exception ex){
        	flag = true;
        }
        
        if(!flag){
			invoice.selectFirstCard();
			invoice.typeExistingCardCVV();
        } else{
        	invoice.clickAddNewCard();
    		invoice.typeCardNumber("4111111111111111");
    		invoice.typeExpiryDate("0520", device);
    		invoice.typeCardCVV("111");
        }
        invoice.clickContinueCard();
        invoice.typeBillingDetails(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Address_1"), Dictionary.get("Address_2"), Dictionary.get("Country"), Dictionary.get("State"), Dictionary.get("City"), Dictionary.get("ZipCode"));
		invoice.clickContinueBilling();
		invoice.getDueAmount();
        checkLayout(Dictionary.get("SPEC_FILE_NAME").trim(), device.getTags());
	}
	
	@Test(groups={"smoke","invoicesFunctional","regression", "criticalbusiness"})
	public void verifyPaymentPlanInvoicePayment() throws Exception { 
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("No need to run on android/ios");
		
		String emailaddress = Dictionary.get("EMAIL_ADDRESS");
		String password = Dictionary.get("PASSWORD");
		String invoicestatus = "PLAN";
		String accessToken = accessTokens.getAccessToken(emailaddress, password);
		String accId= accessTokens.getAccountId(accessToken);
//		getDriver().navigate().to(Environment.get("strUrl").trim());			
//		adminlogin.adminLogin();
		int invoiceId = adminlogin.getInvoiceListAndId(accId, invoicestatus);
		
		if(invoiceId == -1){
			throw new SkipException("Invoice of status - " + invoicestatus.trim().toUpperCase() + " not found");
		}
		
		load("/invoice");
		homepage.login(emailaddress, password , null, true);
		invoice.clickInvoiceLink(invoiceId, null);
		
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		
		String invDue = invoice.amountDue();
		
		invoice.clickContinueButton();
		invoice.clickContinuePlan();
		invoice.clickAddNewCard();
		invoice.typeCardNumber(Dictionary.get("CardNumber"));
		invoice.typeExpiryDate(Dictionary.get("ExpiryDate"), null);
		invoice.typeCardCVV(Dictionary.get("CVV"));
		invoice.clickContinueCard();
		invoice.typeBillingDetails(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Address_1"), Dictionary.get("Address_2"), Dictionary.get("Country"), Dictionary.get("State"), Dictionary.get("City"), Dictionary.get("ZipCode"));
		
		invoice.clickContinueBilling();
		invoice.typePartialAmount(invDue, Dictionary.get("PartialAmt"));
		invoice.selectAcceptTnC();
		String dueAmt;
		double newDueAmt = 0;
		if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS")){
			dueAmt=invoice.getHeaderAmount();
			newDueAmt = Double.parseDouble(dueAmt) - Double.parseDouble(Dictionary.get("PartialAmt"));
		}
		double newInvDue=Double.parseDouble(invDue) - Double.parseDouble(Dictionary.get("PartialAmt"));
		Assert.assertEquals("Pay with Payment Plan", invoice.paymentoption(), "Verify payment option on review page");
		invoice.clickConfirmPay();	
		Assert.assertEquals(invoice.getSuccessHead(), Dictionary.get("SuccessHeading").trim(), "Verify success heading on confirmation page");
//		Assert.assertTrue(invoice.getSuccessText().contains(Dictionary.get("SuccessText").trim()), "Verify success text on confirmation page");
		
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")){
			BaseUtil.navigateBack();
			Assert.assertEquals(invoice.getInvoiceBalWhenRefreshed(Integer.valueOf(Dictionary.get(invoicestatus.trim().toUpperCase() + "invoiceId")), newInvDue), String.format("%.02f",newInvDue), "Verify invoice balance on listing after payment");
		} else{
			Assert.assertEquals(invoice.getSelectedInvoiceBalWhenRefreshed(newInvDue), String.format("%.02f",newInvDue), "Verify invoice balance on listing after payment");
			Assert.assertEquals(invoice.getHeaderAmountWhenRefreshed(newDueAmt), String.format("%.02f",newDueAmt), "Verify invoice balance on header after payment");
		}
	}
	
	@DataProvider(name = "data", parallel = true)
    public Object[][] data() {
		String accountStatus = System.getProperty("accountStatus") != null && !System.getProperty("accountStatus").trim().equalsIgnoreCase("") ? System.getProperty("accountStatus").trim() : Environment.get("accountStatus").trim();
		String[] statuses = accountStatus.trim().split(",");
		
		Object[][] o = new Object[statuses.length][];
    	for(int i = 0; i < o.length; i++){
    		o[i] = new Object[1];
    		o[i][0] = statuses[i];
    	}
    	
    	return o;
	}
	
	@Test(groups={"smoke","invoicesFunctional","regression","prod"}, dataProvider="data")
	public void verifyInvoiceListMapping(String invoicestatus) throws Exception{
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("No need to run on android/ios");
		
		adminlogin.beforeMtdInvoiceAPI(invoicestatus);
		
		if(Dictionary.get(invoicestatus.trim().toUpperCase() + "invoiceId").trim().equalsIgnoreCase(""))
			throw new SkipException("Invoice of status - " + invoicestatus.trim().toUpperCase() + " not found");
		
		int invoiceId = Integer.valueOf(Dictionary.get(invoicestatus.trim().toUpperCase() + "invoiceId").trim());
		
		load("/invoice");
		String emailaddress = Dictionary.get(invoicestatus.trim().toUpperCase() + "_EMAIL_ADDRESS");
        String password = Dictionary.get(invoicestatus.trim().toUpperCase() + "_PASSWORD");
		homepage.login(emailaddress, password, null, true);
		
		Assert.assertTrue(invoice.verifyInvoiceStatus(invoiceId, invoicestatus), "Verify " + invoicestatus.trim().toLowerCase() + " invoice is displayed");
		
		Assert.assertEquals(invoice.getInvoiceDesc(invoiceId), adminlogin.getInvoiceFieldValues(Dictionary.get(invoicestatus.trim().toUpperCase() + "InvoiceListArray"), new String[]{"invoice_descriptions", "balances", "due_dates"} , invoiceId, invoicestatus).get("invoice_descriptions"), "Verify invoice description matches with the result from TM Api");
		Assert.assertEquals(invoice.getInvoiceDesc(invoiceId), Dictionary.get(invoicestatus.trim().toUpperCase() + "invoice_descriptions"), "Verify invoice description matches with the result from TM Api");
	    Assert.assertEquals(invoice.getInvoiceBal(invoiceId), Dictionary.get(invoicestatus.trim().toUpperCase() + "balances"), "Verify invoice balance matches with the result from TM Api");
	    Assert.assertEquals(invoice.getInvoiceDueDt(invoiceId), Dictionary.get(invoicestatus.trim().toUpperCase() + "due_dates"), "Verify invoice due date matches with the result from TM Api");
	}
	
	@Test(groups={"smoke","invoicesFunctional","regression","prod"}, dataProvider="data")
	public void verifyInvoiceDetailsMapping(String invoicestatus) throws Exception{
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("No need to run on android/ios");
		
		adminlogin.beforeMtdInvoiceAPI(invoicestatus);
		
		if(Dictionary.get(invoicestatus.trim().toUpperCase() + "invoiceId").trim().equalsIgnoreCase(""))
			throw new SkipException("Invoice of status - " + invoicestatus.trim().toUpperCase() + " not found");
		
		int invoiceId = Integer.valueOf(Dictionary.get(invoicestatus.trim().toUpperCase() + "invoiceId").trim());
		
		load("/invoice");
		String emailaddress = Dictionary.get(invoicestatus.trim().toUpperCase() + "_EMAIL_ADDRESS");
        String password = Dictionary.get(invoicestatus.trim().toUpperCase() + "_PASSWORD");
		homepage.login(emailaddress, password, null, true);
		
		Assert.assertTrue(invoice.verifyInvoiceStatus(invoiceId, invoicestatus), "Verify " + invoicestatus.trim().toLowerCase() + " invoice is displayed");
		
		if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS")){
			Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		}
		
		utils.navigateTo("/invoice#/" + invoiceId + "/1");
		
		invoice.isInvoiceSelected(invoiceId, null);
		
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		
		if(!invoicestatus.trim().equalsIgnoreCase("PAID"))
			invoice.isContinueButtonDisplayed();
		
		invoice.clickInvoiceSummary();
		
		Assert.assertEquals(invoice.getInvoiceQty(), Dictionary.get(invoicestatus.trim().toUpperCase() + "num_seat"), "Verify invoice number of seats matches with the result from TM Api");
		Assert.assertEquals(invoice.getSection(), Dictionary.get(invoicestatus.trim().toUpperCase() + "section_name"), "Verify invoice section name matches with the result from TM Api");
		Assert.assertEquals(invoice.getRow(), Dictionary.get(invoicestatus.trim().toUpperCase() + "row_name"), "Verify invoice row name matches with the result from TM Api");
		Assert.assertEquals(invoice.getFirstSeat(), Dictionary.get(invoicestatus.trim().toUpperCase() + "seat_num"), "Verify invoice seat number matches with the result from TM Api");
		Assert.assertEquals(invoice.getLastSeat(), Dictionary.get(invoicestatus.trim().toUpperCase() + "last_seat"), "Verify invoice last seat matches with the result from TM Api");
		Assert.assertEquals(invoice.getUnitPrice(), Dictionary.get(invoicestatus.trim().toUpperCase() + "unit_price"), "Verify invoice unit price matches with the result from TM Api");
		SoftAssert.assertEquals(invoice.getInvoiceName(), Dictionary.get(invoicestatus.trim().toUpperCase() + "item_name_long"), "Verify invoice name matches with the result from TM Api");
		Assert.assertEquals(invoice.getInvoicedAmt(), Dictionary.get(invoicestatus.trim().toUpperCase() + "invoiced_amount"), "Verify invoice amount matches with the result from TM Api");
	}
	
	@Test(groups={"smoke","invoicesFunctional","regression","prod"}, dataProvider="data")
	public void verifyCardInfo(String invoicestatus) throws Exception {
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("No need to run on android/ios");
		
		adminlogin.beforeMtdInvoiceAPI(invoicestatus);
		
		if(invoicestatus.trim().equalsIgnoreCase("PAID")){
			throw new SkipException("Skipped");
		}
		
		if(Dictionary.get(invoicestatus.trim().toUpperCase() + "data_mask").trim().equalsIgnoreCase("")) {
			throw new SkipException("Credit card info not found");
		}
		
		if(Dictionary.get(invoicestatus.trim().toUpperCase() + "invoiceId").trim().equalsIgnoreCase("")){
			throw new SkipException("Invoice of status - " + invoicestatus.trim().toUpperCase() + " not found");
		}
		
		int invoiceId = Integer.valueOf(Dictionary.get(invoicestatus.trim().toUpperCase() + "invoiceId").trim());
		
		load("/invoice");
		String emailaddress = Dictionary.get(invoicestatus.trim().toUpperCase() + "_EMAIL_ADDRESS");
        String password = Dictionary.get(invoicestatus.trim().toUpperCase() + "_PASSWORD");
		homepage.login(emailaddress, password, null, true);
		
		Assert.assertTrue(invoice.verifyInvoiceStatus(invoiceId, invoicestatus), "Verify " + invoicestatus.trim().toLowerCase() + " invoice is displayed");
		invoice.clickInvoiceLink(invoiceId, null);
		
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		
		invoice.clickContinueButton();
		if(invoicestatus.trim().equalsIgnoreCase("UNPAID") || invoicestatus.trim().equalsIgnoreCase("PARTIALLY PAID")){
			invoice.clickpayInFull();
		}
        invoice.clickContinuePlan();
        Assert.assertEquals(invoice.cardNumber(), invoice.CardlastDigits(invoicestatus), "Verify card last digits matches with the result from TM Api");
        Assert.assertEquals(invoice.cardExpiryDate(invoicestatus), invoice.cardExpiry(), "Verify card expiry date matches with the result from TM Api");
	}
	
	@Test(groups={"smoke","invoicesFunctional","regression","prod"}, dataProvider="data")
	public void verifyBillingAddress(String invoicestatus) throws Exception {
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("No need to run on android/ios");
		
		adminlogin.beforeMtdInvoiceAPI(invoicestatus);
		
		if(invoicestatus.trim().equalsIgnoreCase("PAID")){
			throw new SkipException("Skipped");
		}
		
		if(Dictionary.get(invoicestatus.trim().toUpperCase() + "data_mask").trim().equalsIgnoreCase("")) {
			throw new SkipException("Credit card info not found");
		}
		
		if(Dictionary.get(invoicestatus.trim().toUpperCase() + "invoiceId").trim().equalsIgnoreCase("")){
			throw new SkipException("Invoice of status - " + invoicestatus.trim().toUpperCase() + " not found");
		}
		
		int invoiceId = Integer.valueOf(Dictionary.get(invoicestatus.trim().toUpperCase() + "invoiceId").trim());
		
		load("/invoice");
		String emailaddress = Dictionary.get(invoicestatus.trim().toUpperCase() + "_EMAIL_ADDRESS");
        String password = Dictionary.get(invoicestatus.trim().toUpperCase() + "_PASSWORD");
		homepage.login(emailaddress, password, null, true);
		
		Assert.assertTrue(invoice.verifyInvoiceStatus(invoiceId, invoicestatus), "Verify " + invoicestatus.trim().toLowerCase() + " invoice is displayed");
		invoice.clickInvoiceLink(invoiceId, null);
		
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		
		invoice.clickContinueButton();
		if(invoicestatus.trim().equalsIgnoreCase("UNPAID") || invoicestatus.trim().equalsIgnoreCase("PARTIALLY PAID")){
			invoice.clickpayInFull();
		}
        invoice.clickContinuePlan();
        invoice.waitforMasterCard();
		invoice.selectFirstCard();
		invoice.typeExistingCardCVV();
        invoice.clickContinueCard();
        Assert.assertEquals(invoice.ccfirstName(invoicestatus), invoice.firstName(), "Verify firstname matches with the result from TM Api");
        Assert.assertEquals(invoice.ccNamelast(invoicestatus), invoice.lastName(), "Verify lastname matches with the result from TM Api");
        Assert.assertEquals(invoice.ccPostalcode(invoicestatus), invoice.zipCode(), "Verify zipcode matches with the result from TM Api");
        Assert.assertEquals(invoice.ccAddress(invoicestatus), invoice.address(), "Verify address matches with the result from TM Api");      
	}
	
	@Test(groups={"smoke","invoicesFunctional","regression"}, dataProvider="data")
	public void verifyPaymentPlanDetailsInAlreadyApplied(String invoicestatus) throws Exception {
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("No need to run on android/ios");
		
		if(!invoicestatus.trim().equalsIgnoreCase("PLAN")){
			throw new SkipException("Skipped");
		}
		
		adminlogin.beforeMtdInvoiceAPI(invoicestatus);
		
		if(Dictionary.get(invoicestatus.trim().toUpperCase() + "invoiceId").trim().equalsIgnoreCase("")){
			throw new SkipException("Invoice of status - " + invoicestatus.trim().toUpperCase() + " not found");
		}
		
		int invoiceId = Integer.valueOf(Dictionary.get(invoicestatus.trim().toUpperCase() + "invoiceId").trim());
		load("/invoice");
		String emailaddress = Dictionary.get(invoicestatus.trim().toUpperCase() + "_EMAIL_ADDRESS");
        String password = Dictionary.get(invoicestatus.trim().toUpperCase() + "_PASSWORD");
		homepage.login(emailaddress, password, null, true);
		
		Assert.assertTrue(invoice.verifyInvoiceStatus(invoiceId, invoicestatus), "Verify " + invoicestatus.trim().toLowerCase() + " invoice is displayed");
		invoice.clickInvoiceLink(invoiceId, null);
		
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.clickContinueButton();
		
		/*Statement to get refreshed when payment plan selected is displayed*/
		invoice.getRefreshedWhenPaymentPlanPopulated(Dictionary.get(invoicestatus.trim().toUpperCase() + "PLAN_LONG_NAME").trim());
		
		Assert.assertFalse(invoice.isPayInFullDisplayed(), "Verify pay in full option is displayed");
		Assert.assertFalse(invoice.isPayWithMultipleCardsDisplayed(), "Verify pay with multiple cards option is displayed");
		Assert.assertTrue(invoice.isPaymentPlanSelected(), "Verify payment plan is selected");
		Assert.assertTrue(invoice.isPaymentPlanOptionDisabled(), "Verify user is not able to change payment plan once already applied");
		
		/*verifyPaymentPlanValue*/
		Assert.assertTrue(invoice.verifyPaymentPlanValue(Dictionary.get(invoicestatus.trim().toUpperCase() + "PLAN_LONG_NAME").trim()), "Verify selected payment plan");
		Assert.assertTrue(invoice.verifyPaymentPlanBreakage(Dictionary.get(invoicestatus.trim().toUpperCase() + "paymentPlansSchedule").trim()));
		
		invoice.clickContinuePlan();
		invoice.clickAddNewCard();
		invoice.typeCardNumber(Dictionary.get("CardNumber"));
		invoice.typeExpiryDate(Dictionary.get("ExpiryDate"), null);
		invoice.typeCardCVV(Dictionary.get("CVV"));
		invoice.clickContinueCard();
		invoice.typeBillingDetails(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Address_1"), Dictionary.get("Address_2"), Dictionary.get("Country"), Dictionary.get("State"), Dictionary.get("City"), Dictionary.get("ZipCode"));
		invoice.clickContinueBilling();
		String reviewfinalamountDue=invoice.finalamountDue();
		System.out.println("reviewfinalamountDue:"+reviewfinalamountDue);
		invoice.selectAcceptTnC();
		
		adminlogin.getPaymentScheduleFieldValues(Dictionary.get(invoicestatus.trim().toUpperCase() + "paymentPlansSchedule"),new String[]{"amount_due_today","payment_period_amount"}, invoicestatus);
		String amount_due_today = Dictionary.get(invoicestatus.trim().toUpperCase() + "amount_due_today");
		Assert.assertEquals(amount_due_today, reviewfinalamountDue, "amount_due_today are matching");
	}
	
	@Test(groups={"smoke","invoicesFunctional","regression","prod"})
	public void verifyTotalAmtDue() throws Exception {
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("No need to run on android/ios");
		
		adminlogin.beforeMtdInvoiceAPI("");
		
		load("/dashboard");
		homepage.login(Dictionary.get("EMAIL_ADDRESS"), Dictionary.get("PASSWORD"), null, true);
		Assert.assertTrue(header.waitForDasboardHeader(), "Verify dashboard header is displayed");
		Assert.assertTrue(dashboardSection.waitForDasboardSection(null), "Verify dashboard section is displayed");
		
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			mobileSecTabs.clickInvoices();
		}
		
		Assert.assertEquals(dashboardHeader.getDueAmount(adminlogin.getTotalAmountDue(Dictionary.get("InvoiceListArray"))), adminlogin.getTotalAmountDue(Dictionary.get("InvoiceListArray")));
		Assert.assertTrue(dashboardHeader.getAccountId().contains(Dictionary.get("AccountId")));
	}

	@Test(groups={"smoke","invoicesFunctional","regression","prod"})
	public void payWithPaymentPlans() throws Exception{
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("No need to run on android/ios");
		
		load("/invoice");
		homepage.login("", "" , null, true);
		invoice.clickFirstUnpaidInvoiceLink(null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		invoice.clickContinueButton();
		invoice.clickSelectPaymentPlanRadio();
		invoice.selectDefaultPaymentPlan();
		invoice.clickContinuePlan();
		invoice.clickAddNewCard();
		invoice.typeCardNumber(Dictionary.get("CardNumber"));
		invoice.typeExpiryDate(Dictionary.get("ExpiryDate"), null);
		invoice.typeCardCVV(Dictionary.get("CVV"));
		invoice.clickContinueCard();
		invoice.typeBillingDetails(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Address_1"), Dictionary.get("Address_2"), Dictionary.get("Country"), Dictionary.get("State"), Dictionary.get("City"), Dictionary.get("ZipCode"));
		invoice.clickContinueBilling();
		String reviewfinalamountDue=invoice.finalamountDue();
		System.out.println("reviewfinalamountDue:"+reviewfinalamountDue);
		invoice.selectAcceptTnC();
		//invoice.clickConfirm();
		adminlogin.runInvoiceApis(Dictionary.get("EMAIL_ADDRESS"),Dictionary.get("PASSWORD"), Integer.valueOf(Dictionary.get("invoiceId").trim()), "");
		String amount_due_today = Dictionary.get("amount_due_today");
		Assert.assertEquals(amount_due_today, reviewfinalamountDue, "amount_due_today are matching");
	}
	
	@Test(groups={"smoke","invoicesFunctional","regression"})
	public void verifyPartiallyPaidInvoicePayment() throws Exception {
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("No need to run on android/ios");
		
		String emailaddress = Dictionary.get("EMAIL_ADDRESS");
		String password = Dictionary.get("PASSWORD");
		String invoicestatus = "PARTIALLY PAID";
		String accessToken = accessTokens.getAccessToken(emailaddress, password);
		String accId= accessTokens.getAccountId(accessToken);
//		getDriver().navigate().to(Environment.get("strUrl").trim());			
//		adminlogin.adminLogin();
		int invoiceId = adminlogin.getInvoiceListAndId(accId, invoicestatus);
		
		if(invoiceId == -1){
			throw new SkipException("Invoice of status - " + invoicestatus.trim().toUpperCase() + " not found");
		}
		
		load("/invoice");
		homepage.login(emailaddress, password , null, true);
		invoice.clickInvoiceLink(invoiceId, null);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.isContinueButtonDisplayed();
		String invDue = invoice.amountDue();
		invoice.clickContinueButton();
		invoice.clickpayInFull();
		invoice.clickContinuePlan();
		invoice.clickAddNewCard();
		invoice.typeCardNumber(Dictionary.get("CardNumber"));
		invoice.typeExpiryDate(Dictionary.get("ExpiryDate"), null);
		invoice.typeCardCVV(Dictionary.get("CVV"));
		invoice.clickContinueCard();
		invoice.typeBillingDetails(Dictionary.get("FirstName"), Dictionary.get("LastName"), Dictionary.get("Address_1"), Dictionary.get("Address_2"), Dictionary.get("Country"), Dictionary.get("State"), Dictionary.get("City"), Dictionary.get("ZipCode"));
		invoice.clickContinueBilling();
		invoice.typePartialAmount(invDue, Dictionary.get("PartialAmt"));
		invoice.selectAcceptTnC();
		String dueAmt;
		double newDueAmt = 0;
		if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS")){
			dueAmt=invoice.getHeaderAmount();
			newDueAmt = Double.parseDouble(dueAmt) - Double.parseDouble(Dictionary.get("PartialAmt"));
		}
		double newInvDue=Double.parseDouble(invDue) - Double.parseDouble(Dictionary.get("PartialAmt"));
		Assert.assertEquals("Pay In Full", invoice.paymentoption(), "Verify payment option on review page");
		invoice.clickConfirmPay();	
		Assert.assertEquals(invoice.getSuccessHead(), Dictionary.get("SuccessHeading").trim(), "Verify success heading on confirmation page");
		Assert.assertTrue(invoice.getSuccessText().contains(Dictionary.get("SuccessText").trim()), "Verify success text on confirmation page");
		
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")){
			BaseUtil.navigateBack();
			Assert.assertEquals(invoice.getInvoiceBalWhenRefreshed(Integer.valueOf(Dictionary.get(invoicestatus.trim().toUpperCase() + "invoiceId")), newInvDue), String.format("%.02f",newInvDue), "Verify invoice balance on listing after payment");
		} else{
			Assert.assertEquals(invoice.getSelectedInvoiceBalWhenRefreshed(newInvDue), String.format("%.02f",newInvDue), "Verify invoice balance on listing after payment");
			Assert.assertEquals(invoice.getHeaderAmountWhenRefreshed(newDueAmt), String.format("%.02f",newDueAmt), "Verify invoice balance on header after payment");
		}
	}
	
	@Test(groups={"invoicesFunctional","regression","prod"})
	public void verifySortingOfInvoices() throws Exception {
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("No need to run on android/ios");
		
		String emailaddress = Dictionary.get("EMAIL_ADDRESS");
		String password = Dictionary.get("PASSWORD");
		String accessToken = accessTokens.getAccessToken(emailaddress, password);
		String accId= accessTokens.getAccountId(accessToken);
//		getDriver().navigate().to(Environment.get("strUrl").trim());			
//		adminlogin.adminLogin();
		load("/invoice");
		List<List<String>> sortedList = adminlogin.getSortedInvoiceList(accId, "");
		homepage.login(emailaddress, password , null, true);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		Assert.assertEquals(invoice.getInvoiceList(), sortedList, "Verify invoice list is sorted");
	}
	
	// Invoice API
	@Test(groups={"smoke","invoicesFunctional","regression"})
	public void invoicePaymentsThruPayInFull() throws Exception {
		String emailaddress = Dictionary.get("EMAIL_ADDRESS");
		String password = Dictionary.get("PASSWORD");
		String accessToken = accessTokens.getAccessToken(emailaddress, password);
		String accId= accessTokens.getAccountId(accessToken);
		String invoicestatus = "UNPAID";
		int invoiceId = adminlogin.getInvoiceListAndId(accId, invoicestatus);
		if(invoiceId == -1){
			invoicestatus = "PARTIALLY PAID";
			invoiceId = adminlogin.getInvoiceListAndId(accId, invoicestatus);
		}
		if(invoiceId == -1)
			throw new SkipException("Invoice not found");
		
		String cookies = invoice.loginThruDrupalApi(emailaddress, password);
		String versionNumber = invoice.getTerms(cookies);
		String token = invoice.getCsrfToken(cookies);
		invoice.acceptTerms(cookies, token, versionNumber);
		
		token = invoice.getCsrfToken(cookies);
		invoice.paymentsInvoiceAPI(invoiceId, cookies, token);
	}
	
	@Test(groups={"smoke","invoicesFunctional","regression"})
	public void invoicePaymentsThruPaymentsPlan() throws Exception {
		String emailaddress = Dictionary.get("EMAIL_ADDRESS");
		String password = Dictionary.get("PASSWORD");
		String accessToken = accessTokens.getAccessToken(emailaddress, password);
		String accId= accessTokens.getAccountId(accessToken);
		String invoicestatus = "PLAN";
		int invoiceId = adminlogin.getInvoiceListAndId(accId, invoicestatus);
		if(invoiceId == -1){
			invoicestatus = "PARTIALLY PAID";
			invoiceId = adminlogin.getInvoiceListAndId(accId, invoicestatus);
			if(invoiceId == -1){
				invoicestatus = "UNPAID";
				invoiceId = adminlogin.getInvoiceListAndId(accId, invoicestatus);
			}
		}
		if(invoiceId == -1)
			throw new SkipException("Invoice not found");
		
		String cookies = invoice.loginThruDrupalApi(emailaddress, password);
		String versionNumber = invoice.getTerms(cookies);
		String token = invoice.getCsrfToken(cookies);
		invoice.acceptTerms(cookies, token, versionNumber);
		
		token = invoice.getCsrfToken(cookies);
		String planid;
		if(invoicestatus.trim().equalsIgnoreCase("PLAN")) {
			planid = Dictionary.get(invoicestatus.trim().toUpperCase() + "planId");
		} else
			planid=invoice.getPaymentsPlanID(invoiceId, cookies);
		invoice.paymentsPlanInvoiceAPI(invoiceId, cookies, token, planid);
	}
	
	@Test(groups={"smoke","regression","user_journey","criticalbusiness", "sso", "prod", "nonstp"})
	public void verifyExistingUserSSOfromNAMtoCAMAndBuyTicket() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingExistingCard() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingSingleExistingCardNoPlan() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingTwoExistingCardNoPlan() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingNewSingleCardWithoutSaveToAccount() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingNewTwoCardWithoutSaveToAccount() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingNewSingleCardWithSaveToAccount() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingNewTwoCardWithSaveToAccount() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingExistingSingleCardPayInFull() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingTwoExistingCardPayInFull() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingNewSingleCardWithoutSaveToAccountPayInFull() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingNewTwoCardWithoutSaveToAccountPayInFull() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingNewSingleCardWithSaveToAccountPayInFull() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingNewTwoCardWithSaveToAccountPayInFull() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingSingleExistingCardPaymentPlan() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingTwoExistingCardPaymentPlan() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingNewSingleCardWithoutSaveToAccountPaymentPlan() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingNewTwoCardWithoutSaveToAccountPaymentPlan() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingNewSingleCardWithSaveToAccountPaymentPlan() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paymentUsingNewTwoCardWithSaveToAccountPaymentPlan() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void optionalItemPayInFull() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void optionalItemPaymentPlan() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression","invoiceNew" })
	public void removeOptionalItemPaymentPlan() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void removeOptionalItemPayInFull() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void wrongCvvPayOther() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void wrongCvvPayInFull() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void wrongCvvPaymentPlan() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void paidInvoice() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression","invoiceNew" })
	public void invalidCardAdd() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void verifyTermsAndCondition() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void testInvoiceListMapping() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void testInvoiceDetailsMapping() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void testInvoiceListSorted() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void invoicePaymentPlanApi() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void invoicePaymentApi() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "invoiceNew" }, enabled = false)
	public void upSellPayOther() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "invoiceNew" }, enabled = false)
	public void upSellPayInFull() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "invoiceNew" }, enabled = false)
	public void upSellPaymentPlan() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void updateCreditCardInfo() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	

	@Test(groups = { "smoke", "regression", "invoiceNew" })
	public void updateCreditCardInfoinvalid() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
}
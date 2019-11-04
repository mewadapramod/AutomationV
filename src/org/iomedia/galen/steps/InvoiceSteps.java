package org.iomedia.galen.steps;

import java.awt.AWTException;
import java.util.ArrayList;

import javax.mail.Folder;
import javax.mail.Store;

import org.iomedia.common.BaseUtil;
import org.iomedia.galen.common.EncryptDecrypt;
import org.iomedia.galen.common.RecieveMail;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.extras.InvoiceConfirmationMapper;
import org.iomedia.galen.extras.InvoiceConfirmationMapper.InvoiceDetail;
import org.iomedia.galen.pages.Invoice;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class InvoiceSteps {
	
	Invoice invoice;
	InvoiceNewSteps new_invoice;
	Utils utils;
	BaseUtil base;
	org.iomedia.framework.Assert Assert;
	RecieveMail mail;
	EncryptDecrypt decode = new EncryptDecrypt();
	
	public InvoiceSteps(Invoice invoice,Utils utils, BaseUtil base, org.iomedia.framework.Assert Assert,InvoiceNewSteps newinvoice,RecieveMail mail) 
	{
		this.invoice = invoice;
		this.new_invoice=newinvoice;
		this.utils = utils;
		this.base = base;
		this.Assert = Assert;
		this.mail = mail;
	}
	
	@Then("^Pending invoice found on NAM with id (.+) and amount due (.+)$")
	public void pending_invoice_found_on_nam_with_id_and_amount_due(String invoiceId, String amtDue) {
		invoiceId = (String) base.getGDValue(invoiceId);
		amtDue = (String) base.getGDValue(amtDue);	
		if(invoiceId.trim().contains("/"))
		invoiceId = invoiceId.trim().substring(0, invoiceId.trim().lastIndexOf("/"));
		invoice.isInvoiceSelected(Integer.valueOf(invoiceId), null);
		base.sync(5000L);
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		invoice.getInvoice(amtDue, Integer.valueOf(invoiceId));
		String invDue = invoice.amountDue();
		Assert.assertEquals(invDue, amtDue, "Verify invoice balance should match with the stp");
	}
	
	@Then("^User clicks on First unpaid invoice$")
	public void click_first_unpaid_invoice() {
		invoice.clickFirstUnpaidInvoiceLink(null);
	}
	
	@Then("^Verify Invoice detail gets displayed$")
	public void verify_invoice_detail() {
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
	}
	
	@When("^Continue button gets display$")
	public void continue_button_gets_display() {
		//invoice.isContinueButtonDisplayed();
		
		String invDue = invoice.amountDue();
		System.out.println(invDue);
		base.Dictionary.put("InvoiceDue", invDue);
		new_invoice.clickContinueButton();
		//invoice.clickContinueButton();
	}
	//method identify survey tab displaying
	@Then("^Verify survey tab gets display$")
	public void verify_surveytab_display() throws InterruptedException, AWTException 
	{
		Assert.assertTrue(invoice.isTypeformPresent(),"Survey tab is not found");
		invoice.submit_Typeform();
	}
	
	@When("^User clicks on pay in full$")
	public void User_clickon_payinfull() {
		invoice.clickpayInFull();
	}
	
	@Then("^User clicks on continue plan$")
	public void clickc_continue_plan() {
		invoice.clickContinuePlan();
	}
	
	@When("^User select First Card$")
	public void userselectfirstcard() {
		invoice.selectFirstCard();
	}
	
	@Then("^User type Existing CardCVV$")
	public void Usertypeexistingcardcvv() throws Exception {
		invoice.typeExistingCardCVV();
	}
	@Then("^User click Continue Card$")
	public void clickcontunuecards() {
		invoice.clickContinueCard();
	}
	
	@Then("^User click Continue Billing$")
	public void clickContinueBilling() {
		invoice.clickContinueBilling();
	}
	
	@When("^User type Partial Amount (.+) for due amount (.+)$")
	public void typePartialAmount(String partialAmount, String dueAmount) throws Exception {
		partialAmount = (String) base.getGDValue(partialAmount);
		dueAmount = (String) base.getGDValue(dueAmount);
		invoice.typePartialAmount(dueAmount, partialAmount);
	}
	
	@Then("^User select Accept TnC$")
	public void accepttnc() {
		invoice.selectAcceptTnC();
	}
	
	@When("^user fetch invoice detail from email using \"([^\"]*)\" \"([^\"]*)\"$")
	public void user_fetch_invoice_detail_from_email_using(String mailBox, String passKey) throws Exception {
		Store store = mail.connect(base.Environment.get(mailBox), decode.decrypt(decode.decrypt(base.Environment.get(passKey))));
		Folder folder = mail.getFolder(store, "Invoice");
		String html = mail.SearchLinkContent(folder, 0, "FETCHINVOICE");
		base.Dictionary.put("Invoice_Mail_Content", html);
	}

	@Then("^user verify event and invoice details$")
	public void verify_event_and_invoice_details() throws Exception {
		InvoiceConfirmationMapper actualInvoiceDetailOnUI  = invoice.getActualInvoiceDetailOnUI();
		InvoiceConfirmationMapper expectedInvoiceDetailOnMail  = new InvoiceConfirmationMapper(base.Dictionary.get("Invoice_Mail_Content"), InvoiceConfirmationMapper.getMessageType("CamInvoice"));
		// Assert.assertEquals(actualInvoiceDetailOnUI.getAccountNumber(), expectedInvoiceDetailOnMail.getAccountNumber(),"Verify Account Number on UI with Emailed Invoice");
		Assert.assertEquals(actualInvoiceDetailOnUI.getDeliveryCharges(), expectedInvoiceDetailOnMail.getDeliveryCharges(),"Verify Delivery charges on UI with Emailed Invoice");
		Assert.assertEquals(actualInvoiceDetailOnUI.getEventName().toUpperCase(), expectedInvoiceDetailOnMail.getEventName().toUpperCase(),"Verify Event Name on UI with Emailed Invoice");
		Assert.assertEquals(actualInvoiceDetailOnUI.getItemTotal(), expectedInvoiceDetailOnMail.getItemTotal(),"Verify Item Total on UI with Emailed Invoice");
		Assert.assertEquals(actualInvoiceDetailOnUI.getOrderNumber(), expectedInvoiceDetailOnMail.getOrderNumber(),"Verify Order Number on UI with Emailed Invoice");
		Assert.assertEquals(actualInvoiceDetailOnUI.getSection(), expectedInvoiceDetailOnMail.getSection(),"Verify Section on UI with Emailed Invoice");
		Assert.assertEquals(actualInvoiceDetailOnUI.getRow(), expectedInvoiceDetailOnMail.getRow(),"Verify Row On UI with Emailed Invoice");
		Assert.assertEquals(actualInvoiceDetailOnUI.getSeat(), expectedInvoiceDetailOnMail.getSeat(),"Verify Seat On UI with Emailed Invoice");
		Assert.assertEquals(actualInvoiceDetailOnUI.getPaidToday(), expectedInvoiceDetailOnMail.getPaidToday(),"Verify Less Payment On UI with Emailed Invoice");
		ArrayList<InvoiceDetail> actualInvoiceDetail = actualInvoiceDetailOnUI.getInvoiceDetail();
		ArrayList<InvoiceDetail> expectedInvoiceDetail = expectedInvoiceDetailOnMail.getInvoiceDetail();
		invoice.clickViewPaymentSchedule();
		for (int i = 0; i < actualInvoiceDetail.size(); i++) {
			Assert.assertEquals(actualInvoiceDetail.get(i).getDueAmount(), expectedInvoiceDetail.get(i).getDueAmount(),"Verify Due Amount for " + ( i + 1 ) + "st/nd/rd/th Schedule Payment on UI with Emailed Invoice");
			Assert.assertEquals(actualInvoiceDetail.get(i).getDueDate(), expectedInvoiceDetail.get(i).getDueDate(),"Verify Due Date for " + (i + 1) + "st/nd/rd/th Schedule Payment on UI with Emailed Invoice");
		}
		invoice.clickCloseButton();

	}
	
	@When("^user clicks on send email$")
	public void user_clicks_on_send_email() throws Exception {
		invoice.clickOnSendEmailLink();
	}

	@When("^waits for the email to drop in inbox using \"([^\"]*)\" \"([^\"]*)\"$")
	public void waits_for_the_email_to_drop_in_inbox(String mailBox, String passKey) throws Exception {
		Store store = mail.connect(base.Environment.get(mailBox), decode.decrypt(decode.decrypt(base.Environment.get(passKey))));
		Folder folder = mail.getFolder(store, "Events");
		int actualMsgCount = mail.getMessages(folder).length;
		String html = mail.SearchLinkContent(folder, actualMsgCount, "EVENTS");
		base.Dictionary.put("Invoice_Mail_Details", html);
	}

	@Then("^user verify email content with that of UI and it contains (.+) and (.+)$")
	public void user_verify_email_content_with_that_of_UI_and_it_contains_GD_CustomerName_and_GD_NickName(String accountName, String accountId)
			throws Exception {
		accountName = (String) base.getGDValue(accountName);
		accountId = (String) base.getGDValue(accountId);
		InvoiceConfirmationMapper actualInvoiceDetailOnUI  = invoice.getActualInvoiceDetailOnUI();
		InvoiceConfirmationMapper expectedInvoiceDetailOnMail  = new InvoiceConfirmationMapper(base.Dictionary.get("Invoice_Mail_Details"), InvoiceConfirmationMapper.getMessageType("InvoiceDetails"));
		base.SoftAssert.assertEquals(accountName, expectedInvoiceDetailOnMail.getName(),"Verify Account Name on UI with Emailed Invoice");
		base.SoftAssert.assertEquals(accountId, String.valueOf(expectedInvoiceDetailOnMail.getAccountNumber()),"Verify Account ID on UI with Emailed Invoice");
		base.SoftAssert.assertEquals(actualInvoiceDetailOnUI.getDeliveryCharges(), expectedInvoiceDetailOnMail.getDeliveryCharges(),"Verify Delivery charges on UI with Emailed Invoice");
		base.SoftAssert.assertEquals(actualInvoiceDetailOnUI.getEventName().toUpperCase(), expectedInvoiceDetailOnMail.getEventName().toUpperCase(),"Verify Event Name on UI with Emailed Invoice");
		base.SoftAssert.assertEquals(actualInvoiceDetailOnUI.getItemTotal(), expectedInvoiceDetailOnMail.getItemTotal(),"Verify Item Total on UI with Emailed Invoice");
		base.SoftAssert.assertEquals(actualInvoiceDetailOnUI.getOrderNumber(), expectedInvoiceDetailOnMail.getOrderNumber(),"Verify Order Number on UI with Emailed Invoice");
		base.SoftAssert.assertEquals(actualInvoiceDetailOnUI.getSection(), expectedInvoiceDetailOnMail.getSection(),"Verify Section on UI with Emailed Invoice");
		base.SoftAssert.assertEquals(actualInvoiceDetailOnUI.getRow(), expectedInvoiceDetailOnMail.getRow(),"Verify Row On UI with Emailed Invoice");
		base.SoftAssert.assertEquals(actualInvoiceDetailOnUI.getSeat(), expectedInvoiceDetailOnMail.getSeat(),"Verify Seat On UI with Emailed Invoice");
		base.SoftAssert.assertEquals(actualInvoiceDetailOnUI.getPaidToday(), expectedInvoiceDetailOnMail.getPaidToday(),"Verify Less Payment On UI with Emailed Invoice");
		ArrayList<InvoiceDetail> actualInvoiceDetail = actualInvoiceDetailOnUI.getInvoiceDetail();
		ArrayList<InvoiceDetail> expectedInvoiceDetail = expectedInvoiceDetailOnMail.getInvoiceDetail();
		invoice.clickViewPaymentSchedule();
		for (int i = 0; i < actualInvoiceDetail.size(); i++) {
			base.SoftAssert.assertEquals(actualInvoiceDetail.get(i).getDueAmount(), expectedInvoiceDetail.get(i).getDueAmount(),"Verify Due Amount for " + ( i + 1 ) + "st/nd/rd/th Schedule Payment on UI with Emailed Invoice");
			base.SoftAssert.assertEquals(actualInvoiceDetail.get(i).getDueDate(), expectedInvoiceDetail.get(i).getDueDate(),"Verify Due Date for " + (i + 1) + "st/nd/rd/th Schedule Payment on UI with Emailed Invoice");
		}
		invoice.clickCloseButton();
	}

	@When("^user clicks on make a payment$")
	public void user_clicks_on_make_a_payment() throws Exception {
		invoice.clickMakeaPayment();
	}

	@When("^fill the payment details and clicks on PayNow$")
	public void fill_the_payment_details_and_clicks_on_PayNow() throws Exception {
		new_invoice.selectPayOther();
		new_invoice.ccvFieldDisplayed();
		new_invoice.user_enters_cvv_card();
		new_invoice.amountDisplayed();
		new_invoice.enterAmount("0.1");
		new_invoice.clickContinuePaymentSection();
	}

	@Then("^payment successfull message is displayed and email is send with updated invoice$")
	public void payment_successfull_message_is_displayed_and_email_is_send_with_updated_invoice() throws Exception {
		invoice.clickOnViewUpdateInvoice();
	}

}

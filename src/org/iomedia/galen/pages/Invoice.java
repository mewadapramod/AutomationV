package org.iomedia.galen.pages;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.framework.Driver.TestDevice;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.iomedia.galen.common.ManageticketsAPI;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.extras.InvoiceConfirmationMapper;
import org.iomedia.galen.extras.InvoiceConfirmationMapper.InvoiceDetail;
import org.iomedia.galen.extras.SessionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;

import bsh.ParseException;
import io.appium.java_client.AppiumDriver;

public class Invoice extends BaseUtil {
	
	private String driverType;
	
	public Invoice(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<HashMapNew> sTestDetails) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		driverType = driverFactory.getDriverType().get();
		
		
	}

	private By firstInvoiceLink = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a/..");
	private By firstUnpaidInvoiceLink = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[not(descendant::span[contains(@class, 'invoice-paid')])]/..");
	private By firstPaidInvoiceLink = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[(descendant::span[contains(@class, 'invoice-paid') and (text()='PAID' or text()='Paid')])]/..");
	private By firstPlanInvoiceLink = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[(descendant::span[contains(@class, 'invoice-paid') and (text()='PLAN' or text()='Plan')])]/..");
	private By selectedInvoice = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li[contains(@class, 'invoice-activeItem')]");
	private By invoiceList = By.xpath(".//div[contains(@class, 'invoiceListing')]//li[starts-with(@class, 'list-item')]");
	private By invoiceDetail = By.cssSelector("div.accordionSummary");
//	private By payInFull = By.xpath(".//label[@value='payInFull']//span");
//	private By payWithMultipleCards = By.xpath(".//label[@value='payWithMultipleCards']//span");
//	private By selectPaymentPlanRadio = By.xpath(".//label[@value='selectPaymentPlan']//div");
//	private By selectedPaymentPlan = By.xpath(".//label[@value='selectPaymentPlan']//span//input[@role='input']");
	private By payInFull = By.xpath(".//input[@type='radio' and @value='payInFull']/..//span | .//label[@value='payInFull']//span");
	private By payWithMultipleCards = By.xpath(".//input[@type='radio' and @value='payWithMultipleCards']/..//span | .//label[@value='payWithMultipleCards']//span");
	private By selectPaymentPlanRadio = By.xpath(".//input[@type='radio' and @value='selectPaymentPlan']/..//div | .//label[@value='selectPaymentPlan']//div");
	private By selectedPaymentPlan = By.xpath(".//input[@type='radio' and @value='selectPaymentPlan']/..//span//input[@role='input'] | .//label[@value='selectPaymentPlan']//span//input[@role='input']");

	private By selectPaymentPlanValue = By.xpath(".//input[@role='input' and @value='Select a Payment Plan']");
//	private By paymentPlansDropdown = By.xpath(".//label[@value='selectPaymentPlan']//span//ul");
    private By paymentPlansDropdown = By.xpath(".//input[@type='radio' and @value='selectPaymentPlan']/..//span//ul | .//label[@value='selectPaymentPlan']//span//ul");

	private By cardinfo=By.xpath(".//div[contains(@class, 'accordionCard')]//h2");
	private By continueButton = By.cssSelector("button[class*='invoice-buttonBlock-2A66BN']");
	private By paymentPlanBreakage = By.cssSelector("table[class*='paymentBreakageTable'] tbody");
	private By paymentPlanBreakageDates = By.cssSelector("table[class*='paymentBreakageTable'] tbody tr td:nth-child(2)");
	private By paymentPlanBreakageAmount = By.cssSelector("table[class*='paymentBreakageTable'] tbody tr td:last-child");
	private By radioChecked = By.cssSelector("div[class*='radioChecked']");
	private By firstCard= By.cssSelector(".accordionCard label:nth-child(1)");
	private By cvvDetails= By.xpath(".//input[@name='cvc']");
	private By paymentAmount= By.xpath(".//input[@name='payment']");
	private By newpaymentAmount= By.xpath(".//input[@name='newPaymentAmount']");
	private By secondpaymentAmount= By.xpath(".//*[contains(@class,'invoice-invoiceCard')]/div/div[2]/div[1]/div/input");
	private By cardlastfourdigitsnumber= By.cssSelector("div[class*='slick-active'] > span[class*='invoice-cardNumberTitle']");
	private By secondCard= By.xpath("//*[contains(@class,'invoiceCard')]//label[2]/div");
	private By secondCVV= By.xpath("//*[contains(@class,'invoice-invoiceCard')]/div/div[2]/div[2]/div/input");
	private By rightarrow= By.xpath(".//*[contains(@class,'invoice-sliderButtonConatiner')]/span[contains(., 'right')]/i");
//	private By enterAmount= By.xpath("//input[@name='userEnteredAmount' and @type='tel']");
	private By amount_Due=By.xpath("(.//div[contains(@class, 'invoice-amountTableContainer')])[1]//div[3]/div[2]/span | (//div[contains(@class,'amountDue')]//span)[1]");
	private By continuePlan = By.xpath(".//*[contains(@class, 'accordionPayment')]//button[text()='CONTINUE']");
	private By continueCard = By.xpath(".//*[contains(@class, 'accordionCard')]//button[text()='CONTINUE']");
	private By continueBilling = By.xpath("//*[contains(@class,'invoiceBilling')]//button[text()='CONTINUE']");
	public By progressOverlay = By.cssSelector("div[class*='invoice-progressOverlay']");
	private By addNewCard = By.cssSelector("button[class*='invoice-addCardButton']");
	private By inputCardNumber= By.xpath(".//input[@name='newCardNumber']");
	private By inputCardExpiry= By.xpath(".//input[@name='newExpiryDate']");
	private By inputCardCvv= By.xpath(".//input[@name='newCvc']");
	private By firstName = By.cssSelector("div[class*='invoiceBilling'] div[class*='invoice-singleCardAddress'] input[name='firstName']");
	private By lastName = By.cssSelector("div[class*='invoiceBilling'] div[class*='invoice-singleCardAddress'] input[name='lastName']");
	private By streetAdd1 = By.cssSelector("div[class*='invoiceBilling'] div[class*='invoice-singleCardAddress'] input[name='streetAddress']");
	private By streetAdd2 = By.cssSelector("div[class*='invoiceBilling'] div[class*='invoice-singleCardAddress'] input[name='streetAddress2']");
	private By zipCode = By.cssSelector("div[class*='invoiceBilling'] div[class*='invoice-singleCardAddress'] input[name='zipcode']");
	private By billingContinue= By.xpath(".//*[contains(@class, 'invoice-invoiceBilling')]//button[text()='CONTINUE']");
	private By city= By.cssSelector("div[class*='invoiceBilling'] div[class*='invoice-singleCardAddress'] input[name='city']");
	private By country= By.cssSelector("div[class*='invoiceBilling'] div[class*='invoice-singleCardAddress'] input[name='countries']");
//	private String countryName= "//*[contains(@class,'invoiceBilling')]//div[contains(@class, 'invoice-singleCardAddress')]//input[@name='countries']/../..//ul/li";
	private By state = By.cssSelector("div[class*='invoiceBilling'] div[class*='invoice-singleCardAddress'] input[name='states']");
//	private String stateName= "//*[contains(@class,'invoiceBilling')]//div[contains(@class, 'invoice-singleCardAddress')]//input[@name='states']/../..//ul/li";
	private By partialAmt= By.xpath("//input[@name='userEnteredAmount']");
	private By tnCCheck = By.cssSelector("[class*='reviewTableCheckbox'] label div");
	private By confirmPay = By.xpath("//button[text()='CONFIRM']");
	private By paymentSuccessHead= By.cssSelector("[class*='reviewTitle']");
	private By paymentSuccessText= By.cssSelector("[class*='reviewDescription']");
	private By headerAmount = By.cssSelector(".react-root-dashboard-header div div div:last-child span");
//	private By invoiceListing= By.xpath("//*[contains(@class,'invoiceListing')]/ul/*");
	private By backDashboard= By.xpath("//button[text()='BACK TO DASHBOARD']");
	
	private By paymentOption= By.xpath("(.//div[contains(@class,'invoice-reviewTableRows')])[2]/div[2]/..//div[2]");
	
	private By finalamountdue=By.cssSelector("div[class*='reviewTableFinalAmount'] div span:first-child");
	private By invoiceQty = By.xpath("//table[contains(@class,'invoice-perticulars')]/tbody/tr[1]/td[1]");
	private By invoiceName = By.xpath("//table[contains(@class,'invoice-perticulars')]/tbody/tr[1]/td[2]/div[1]");
	private By sectionRowSeat = By.xpath("//table[contains(@class,'invoice-perticulars')]/tbody/tr[1]/td[2]/div[2]");
	private By invoicedAmt = By.xpath("//table[contains(@class,'invoice-perticulars')]/tbody/tr[1]/td[3]/div[1]/span");
	private By unitPrice = By.xpath("//table[contains(@class,'invoice-perticulars')]/tbody/tr[1]/td[3]/div[2]/span");
	private By cardDetails1 = By.xpath("//*[contains(@class,'invoice-bankCardLabel')]/span[2]");
	private By cardDetails2= By.xpath("//*[contains(@class,'invoice-invoiceCard')]/div/label[2]/span/div/span[2]");
	private By invoiceSummaryDue= By.xpath("//table[contains(@class,'invoice-amountTable')]//tr[last()]/td[2]/span");
	
	private By total_ticket_amount= By.xpath("(.//div[contains(@class,'invoice-reviewTableRows')])[1]/div[2]/..//span");
	private By less_credits = By.xpath("(.//div[contains(@class,'invoice-reviewTableRows')])[3]/div[2]/..//span");
	
	private By invoiceSummaryDiv = By.cssSelector(".accordionSummary div[class*='invoice-invoiceSummary']");
	private By InvoiceLinkText = By.xpath("//div[@class='react-root-invoice-dashboard']//div//div//div[1]//div//div//p");
	private By InvoiceText = By.xpath("//section[@id='block-invoicedashboardblock']//div//div[2]//div//div//ul/li[1]//a//div//em");
	private By InvoiceLink = By.xpath(".//*[@id='block-invoicedashboardblock']//div//div[2]//div//div//ul//li[1]//a");
	private By InvoicePageText = By.xpath("//div[contains(@class,'invoice-invoiceListing')]//ul//li//a//div//em");
	private By DashboardAmount_Due = By.xpath("//div[@class='react-root-invoice-dashboard']//div//div//ul/li[contains(@class,'odd')]//a//div//ul//li[1]//span");
//	private By Email = By.xpath("//button[contains(@class,'invoice-emailButton')]");
//	private By Print = By.xpath("//button[contains(@class,'invoice-printButton')]");
//	private By Close = By.xpath("//button[contains(text(),'invoice-closeButton')]");
//	private By TicketAmount	= By.xpath("//table[contains(@class,'invoice-amountTable')]//tr//td[contains(text(),'Total Ticket Amount')]/..//span");
	private By LessPayment	= By.cssSelector("div[class*='invoice-amountTableContainer'] table.table tbody tr:nth-last-child(2) span");
	private By PayError = By.xpath("//p[contains(@class,'invoice-reviewTitle')]");
	private By Terms = By.xpath("//*[contains(@class,'reviewTableCheckbox')]//label/../p/a");
	private By TermsPopup = By.xpath("//div[@class='modalHeader']//h3");
	private By TermsheaderNewWindow = By.cssSelector("div[class*='termsHeader'] h1");
//	private By Lastname_Bill = By.xpath("//input[@name='lastName']");
	
	private By savedCardList = By.cssSelector("span[class*='invoice-bankCardLabelText']");
	private By surveyBlockSelected = By.cssSelector(".accordionSurvey:not([class*='invoice-hide'])");
	private By surveyBlock = By.cssSelector(".accordionSurvey");
   private By surveyText = By.cssSelector(".accordionSurvey h2 span:nth-child(2)");
	//private By surveyText = By.className("invoice-headerlabelText-3zD39m");
	//public By surveyText=By.xpath("//*[@id=\"wrap-container\"]/div/div[2]/div/div/div/div/div/div/div[2]/div[2]/form/div[2]/h2/span[2]");
	Utils utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	ManageticketsAPI manageticketsAPI = new ManageticketsAPI(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	
// Typeform buttons	
	//public By Iframe_xpath=By.className("invoice_typeform");
	public By Iframe_xpath=By.xpath("//iframe[@class='invoice_typeform'] | //iframe[@data-qa='iframe']");
	
	public By Yes_xpath=By.xpath("//div[contains(@id,'yes')] | (//div[text()='Yes'])[2]");
	public By submit_xpath= By.xpath("(.//div[text()='Submit'])");
	
	public By ok = By.cssSelector("#typeform li div[class*='button'] span:first-child");
	//By submit = By.cssSelector("#typeform #unfixed-footer div[class*='submit']");
	public By textBox = By.cssSelector("#typeform li input[type='text']");
	public By radioButton = By.cssSelector("#typeform li input[type='radio']");
	public By checkBox = By.cssSelector("#typeform li input[type='checkbox']");
	public By dropDown = By.cssSelector("#typeform li select[name*='question']");
	public By choiceButton = By.xpath(".//*[@id='typeform']//li//input[@value='Yes']/..//div[@class='bd']");
	
	private By orderNumber = By.xpath("//span[contains(@class,'invoice-invoiceTitle')]");
	private By ticketDetail = By.xpath("(//div[contains(@class,'invoice-perticular')]//div[contains(@class,'invoice-secRowSeat')])[1]");
	private By eventName = By.xpath("(//div[contains(@class,'invoice-eventName')])[1]");
	private By viewInvoiceDetail = By.xpath("//div[contains(@class,'invoice-viewInvoiceDetails') and //div[@id=\"perticulars\" and @style=\"max-height: 0px;\"]]");
	private By viewPaymentSchedule = By.xpath("(//span[contains(@class,'invoice-desktopScheduleLink')])[1]");
	private By itemTotal = By.xpath("(//div[@class='invoice-perticular-iB7YG0 false']//td[@colspan='1' and contains(@class,'invoice-secRowSeat')])[1]");
	private By invoicePlanStatus = By.xpath("//div[contains(@class,'invoice-planStatus')]");
	private By deliveryCharges = By.xpath("(//div[@id='perticulars']//div[@class='perticular'])[2]//span");
	private By closeButton = By.xpath("//div[contains(@class,'invoice-paymentScheduleButton')]/button | //div[contains(@class,'invoice-fixedBottom')]/button");
	private By amountTable = By.xpath("//div[contains(@class,'invoice-amountTable')]");
	private By emailButton = By.xpath("//div[contains(@class,'invoice-printEmailBtn')]/span[2]");
	private By makeAPayment = By.xpath("//div[contains(@class,'invoice-v2InvoiceSubmit')]/button");
	private By viewUpdateInvoice = By.xpath("//div[contains(@class,'invoice-paymentScheduleButton')]/button");
	private By emailMessage = By.xpath("//div[contains(@class,'invoice-successFailureStatusHead')]/p//p");
	
	
	public void submit_Typeform() throws AWTException
	{
		System.out.println("user entered into question frame");
		
		boolean flag= false;
		WebDriverWait wait= new WebDriverWait(getDriver(), 60);
		JavascriptExecutor obj = (JavascriptExecutor)getDriver();
		
		try
		{
			WebElement typeform_iframe= getDriver().findElement(Iframe_xpath);
			getDriver().switchTo().frame(typeform_iframe);

			if(utils.checkIfElementPresent(Yes_xpath, 30))
			{
				WebElement yes_button= getDriver().findElement(Yes_xpath);
				wait.until(ExpectedConditions.elementToBeClickable(yes_button));
				obj.executeScript("arguments[0].click();", yes_button);
				flag=true;
				
				if(flag==true)
				{
					WebElement submit_button=getDriver().findElement(submit_xpath);
					wait.until(ExpectedConditions.elementToBeClickable(submit_button));	
					click(submit_button, "Submit button");
					//getDriver().findElement(submit_xpath).click();
					getDriver().switchTo().defaultContent();
				}
				
			}
			
			if(utils.checkIfElementPresent(dropDown, 30))
			{
				WebElement dropDown_button= getDriver().findElement(dropDown);
				wait.until(ExpectedConditions.elementToBeClickable(dropDown_button));
				obj.executeScript("arguments[0].click();", dropDown_button);
				flag=true;
				
				if(flag==true)
				{
					WebElement submit_button=getDriver().findElement(submit_xpath);
					wait.until(ExpectedConditions.elementToBeClickable(submit_button));	
					click(submit_button, "Submit button");
					//getDriver().findElement(submit_xpath).click();
					getDriver().switchTo().defaultContent();
				}
			}
			
			if(utils.checkIfElementPresent(checkBox, 30))
			{
				WebElement checkBox_button= getDriver().findElement(checkBox);
				wait.until(ExpectedConditions.elementToBeClickable(checkBox_button));
				//obj.executeScript("arguments[0].click();", checkBox_button);
				obj.executeScript("document.querySelector(checkBox_button).checked=false;");
				flag=true;
				
				if(flag==true)
				{
					WebElement submit_button=getDriver().findElement(submit_xpath);
					wait.until(ExpectedConditions.elementToBeClickable(submit_button));	
					click(submit_button, "Submit button");
					//getDriver().findElement(submit_xpath).click();
					getDriver().switchTo().defaultContent();
				}
			}
			
			if(utils.checkIfElementPresent(radioButton, 30))
			{
				WebElement radioButton_button= getDriver().findElement(radioButton);
				wait.until(ExpectedConditions.elementToBeClickable(radioButton_button));
				obj.executeScript("arguments[0].click();", radioButton_button);
				flag=true;
				
				if(flag==true)
				{
					WebElement submit_button=getDriver().findElement(submit_xpath);
					wait.until(ExpectedConditions.elementToBeClickable(submit_button));	
					click(submit_button, "Submit button");
					//getDriver().findElement(submit_xpath).click();
					getDriver().switchTo().defaultContent();
				}
			}
			
			if(utils.checkIfElementPresent(ok, 30))
			{
				WebElement ok_button= getDriver().findElement(ok);
				wait.until(ExpectedConditions.elementToBeClickable(ok_button));
				obj.executeScript("arguments[0].click();", ok_button);
				flag=true;
				
				if(flag==true)
				{
					WebElement submit_button=getDriver().findElement(submit_xpath);
					wait.until(ExpectedConditions.elementToBeClickable(submit_button));	
					click(submit_button, "Submit button");
					//getDriver().findElement(submit_xpath).click();
					getDriver().switchTo().defaultContent();
				}
			}
			
			if(utils.checkIfElementPresent(textBox, 30))
			{
				WebElement ok_textBox= getDriver().findElement(textBox);
				wait.until(ExpectedConditions.elementToBeClickable(ok_textBox));
				obj.executeScript("document.querySelector(ok_textBox).value='ashish';");
				flag=true;
				
				if(flag==true)
				{
					WebElement submit_button=getDriver().findElement(submit_xpath);
					wait.until(ExpectedConditions.elementToBeClickable(submit_button));	
					click(submit_button, "Submit button");
					getDriver().switchTo().defaultContent();
					
				}
			}

		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		finally 
		{
			System.out.println("user is getting logout from nam");
			getDriver().switchTo().defaultContent();
	
		}
	}
	
	
	
	
	public boolean verifyNewCardSaved(String text1, String text2, String text3) {
		getElementWhenVisible(savedCardList);
		List<WebElement> savedcards = getWebElementsList(savedCardList);
		for(int i = 0 ;i < savedcards.size(); i++) {
			if(savedcards.get(i).getText().trim().equalsIgnoreCase(text1.trim()) || savedcards.get(i).getText().trim().equalsIgnoreCase(text2.trim()) || savedcards.get(i).getText().trim().equalsIgnoreCase(text3.trim())) {
				return true;
			}
		}
		return false;
	}
	
	public String getInvoiceSummaryDueAmt(){
		return getText(invoiceSummaryDue).replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
	}
	
	public String getContinueCardDisableAttr(){
		return getAttribute(continueCard, "disabled");
	}
	
	public boolean checkPaidContinueDisabled(){
		if (checkIfElementPresent(continueButton)){
			return false;
		} else {
			 return true;
		}
	}
	
	public String getHeaderAmount(){
		if(driverType.trim().toUpperCase().contains("IE")) {
			scrollingToTopofAPage();
		}
		return getText(headerAmount).replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
	}
	
	public void clickBackToDashBoard(){
		click(backDashboard, "BackToDashboard Button");
	}
	
	public void clickRightArrow(){
		click(rightarrow, "click right arrow");
	}
	
	public String getDueAmount(){
		return getAttribute(partialAmt, "value").replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
	}
	
	public String getSuccessText(){
		return getText(paymentSuccessText);
	}
	
	public void clickConfirmPay(){
		getElementWhenVisible(confirmPay).click();
//		click(confirmPay, "Confirm Pay");
	}
	
	public void selectAcceptTnC(){
		if(checkIfElementPresent(tnCCheck, 1))
			click(tnCCheck, "Accept TnC");
	}
	
	public void typePartialAmount(String dueAmount, String Amount) throws Exception{
//		WebElement we = getElementWhenClickable(partialAmt, 2);
		Double amount = Double.valueOf(dueAmount.trim());
		if(amount > Double.valueOf(Amount)) {
//			we.clear();
			type(partialAmt, "Enter Partial Amt", Environment.get("currency") + Amount, false, By.xpath("//XCUIElementTypeTextField[1]"));
		} else {
			Dictionary.put("PartialAmt", String.valueOf(amount));
		}
	}
	
	public void clickContinueBilling(){
		getElementWhenRefreshed(billingContinue, "disabled", "null");
		click(continueBilling, "Billing Continue");
	}
	
	public void typeCardCVV(String CVV) throws Exception{
		if(driverType.trim().toUpperCase().contains("SAFARI")) {
			throw new SkipException("Skipped");
		}
		type(inputCardCvv, "New Card CVV", CVV, true, By.xpath("//XCUIElementTypeSecureTextField[1]"));
	}
	
	public void typeExpiryDate(String ExpiryDate, TestDevice device) throws Exception{
		if(driverType.trim().toUpperCase().contains("SAFARI")) {
			throw new SkipException("Skipped");
		}
		type(inputCardExpiry,"New Card Expiry", ExpiryDate, true, By.xpath("//XCUIElementTypeTextField[2]"));
		Dictionary.put("ExpiryDate", getAttribute(inputCardExpiry, "value"));
	}
	
	public void typeCardNumber(String CardNumber) throws Exception {
		if(driverType.trim().toUpperCase().contains("SAFARI")) {
			throw new SkipException("Skipped");
		}
		type(inputCardNumber, "New Card Number", CardNumber, true, By.xpath("//XCUIElementTypeTextField[1]"));
	}
	
	public boolean isInvoiceListDisplayed(){
//		sync(5000L);
		boolean status = false;
		int counter = 3;
		do{
			status = checkIfElementPresent(invoiceList);
			if(!status) {
				getDriver().navigate().refresh();
				sync(5000L);
			}
			counter--;
		} while(counter > 0 && status == false);
		getElementWhenVisible(invoiceList, 5);
		return getWebElementsList(invoiceList).size() > 0;
	}
	
	public boolean isInvoiceDetailDisplayed(TestDevice device){
//		sync(5000L);
		boolean status = false;
		int counter = 3;
		do{
			status = checkIfElementPresent(invoiceDetail);
			if(!status) {
				getDriver().navigate().refresh();
				sync(5000L);
			}
			counter--;
		} while(counter > 0 && status == false);
		getElementWhenVisible(invoiceDetail, 5);
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			//Do Nothing
		} else {
			if(checkIfElementPresent(selectedInvoice)) {
				sync(5000l);
				String selectedInvoiceXpath = getXpath(selectedInvoice, "Selected invoice", "", -1);
				WebElement selectedInvoiceBalance = getSingleChildObject(selectedInvoiceXpath, By.xpath("//div[contains(@class,'invoice-balancesList')]/div/span"), "", "Invoice balance");		
				String selInvoiceBalance = selectedInvoiceBalance.getText();
				try{
					getElementWhenRefreshed(amount_Due, "innerHTML", selInvoiceBalance);
				} catch(Exception ex) {
					getDriver().navigate().refresh();
					sync(5000L);
					getElementWhenVisible(invoiceDetail, 40);
					getElementWhenRefreshed(amount_Due, "innerHTML", selInvoiceBalance);
				}
			}
		}
		return true;
	}
	
	// verify survey and then submit under payment
	public void verify_surveytab_display_payment() throws InterruptedException, AWTException 
	{
		System.out.println("Typeform submission displaying....");
		//Assert.assertTrue(isSurveyDisplayPayment().trim().contains("Questions"),"Survey tab is not found");
		submit_Typeform();
		System.out.println(".....user submitted typeform");
		
	}
	// survey displayed under review tab
	public By surveyText_payment=By.xpath("");
	public String isSurveyDisplayPayment()
	{
		System.out.println("invoice class : survey value"+ getText(surveyText_payment));
		return getText(surveyText);
	}
	
	public String isSurveyDisplay() {
		System.out.println("invoice class : survey value"+ getText(surveyText));
		return getText(surveyText);
	}
	
	public boolean isTypeformPresent() {
		boolean isTypeformPresent = getDriver().findElement(Iframe_xpath).isDisplayed();
		System.out.println("Typeform Is Present On Page "+isTypeformPresent);
		return isTypeformPresent;
	}
	
	public void clickFirstInvoiceLink(TestDevice device) {
		isInvoiceListDisplayed();
		WebElement selectedInvoiceId = getSingleChildObject(getXpath(firstInvoiceLink, "Selected invoice", "", -1), By.tagName("a"), "", "Invoice id");
		String[] href = getAttribute(selectedInvoiceId, "href").split("\\#/");
		Dictionary.put("invoiceId", href[1].replaceAll("/.*", ""));
		
		int invoiceId = Integer.valueOf(Dictionary.get("invoiceId").trim());
		
		utils.navigateTo("/invoice#/" + invoiceId + "/1");
		isInvoiceSelected(invoiceId, device);
		sync(5000L);
	}
	
	public void clickFirstUnpaidInvoiceLink(TestDevice device) {
		isInvoiceListDisplayed();
		WebElement selectedInvoiceId ;
		try {
			selectedInvoiceId = getSingleChildObject(getXpath(firstUnpaidInvoiceLink, "Selected invoice", "", -1), By.tagName("a"), "", "Invoice id");
		}
		catch (TimeoutException e){
			throw new SkipException("No Unpaid Invoice Available");
		}
		String[] href = getAttribute(selectedInvoiceId, "href").split("\\#/");
		Dictionary.put("invoiceId", href[1].replaceAll("/.*", ""));
		
		int invoiceId = Integer.valueOf(Dictionary.get("invoiceId").trim());
		
		utils.navigateTo("/invoice#/" + invoiceId + "/1");
		isInvoiceSelected(invoiceId, device);
		sync(5000L);
	}

	public void clickFirstPaidInvoiceLink(TestDevice device) {
		isInvoiceListDisplayed();
		WebElement selectedInvoiceId;
		try {
		selectedInvoiceId = getSingleChildObject(getXpath(firstPaidInvoiceLink, "Selected invoice", "", -1), By.tagName("a"), "", "Invoice id");
		}
		catch (TimeoutException e){
			throw new SkipException("No Paid Invoice Available");
		}
		String[] href = getAttribute(selectedInvoiceId, "href").split("\\#/");
		Dictionary.put("invoiceId", href[1].replaceAll("/.*", ""));
		
		int invoiceId = Integer.valueOf(Dictionary.get("invoiceId").trim());
		
		utils.navigateTo("/invoice#/" + invoiceId + "/1");
		isInvoiceSelected(invoiceId, device);
		sync(5000L);
	}
	
	public void clickFirstPlanInvoiceLink(TestDevice device) {
		isInvoiceListDisplayed();
		WebElement selectedInvoiceId;
		try {
		selectedInvoiceId = getSingleChildObject(getXpath(firstPlanInvoiceLink, "Selected invoice", "", -1), By.tagName("a"), "", "Invoice id");
		}	catch (TimeoutException e){
				throw new SkipException("No Plan Invoice Available");
		}
		String[] href = getAttribute(selectedInvoiceId, "href").split("\\#/");
		Dictionary.put("invoiceId", href[1].replaceAll("/.*", ""));
		
		int invoiceId = Integer.valueOf(Dictionary.get("invoiceId").trim());
		
		utils.navigateTo("/invoice#/" + invoiceId + "/1");
		isInvoiceSelected(invoiceId, device);
		sync(5000L);
	}
	
	public void clickInvoiceLink(int invoiceId, TestDevice device){
		isInvoiceListDisplayed();
		utils.navigateTo("/invoice#/" + invoiceId + "/1");
		isInvoiceSelected(invoiceId, device);
		sync(5000L);
	}
	
	public boolean isInvoiceSelected(int invoiceId, TestDevice device){
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			//Do Nothing
		} else {
			isInvoiceListDisplayed();
//			By invoice = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[contains(@href, '" + invoiceId + "')]/..");
//			utils.getInvoiceWhenSelected(invoice, "class", "invoice-activeItem");
		}
		return true;
	}
	
	public boolean isPayInFullDisplayed() {
		return checkIfElementPresent(payInFull, 1);
	}
	
	public boolean isPayWithMultipleCardsDisplayed() {
		return checkIfElementPresent(payWithMultipleCards, 1);
	}
	
	public boolean isPaymentPlanSelected(){
		String classValue = getAttribute(selectPaymentPlanRadio, "class");
		return classValue.trim().contains("radioChecked");
	}
	
	public boolean isPaymentPlanOptionDisabled(){
		return getAttribute(selectedPaymentPlan, "disabled").trim().equalsIgnoreCase("true");
	}
	
	public boolean verifyPaymentPlanValue(String expectedPPV){
		return getAttribute(selectedPaymentPlan, "value").trim().equals(expectedPPV.trim());
	}
	
	public void getRefreshedWhenPaymentPlanPopulated(String expectedPPV) {
		getElementWhenRefreshed(selectedPaymentPlan, "value", expectedPPV);
	}
	
	public void clickContinueButton(){
		sync(2000L);
		click(continueButton, "Continue");
		if(checkIfElementPresent(surveyBlock, 1)) {
			if(checkIfElementPresent(surveyBlockSelected)) {
				driverFactory.getDriver().get().switchTo().frame(0);
				try {
					utils.fillSurveyForm();
				} finally {
					driverFactory.getDriver().get().switchTo().parentFrame();
				}
			}
		}
	}
	
	public void clickInvoiceSummary(){
		sync(2000L);
		click(invoiceSummaryDiv, "Invoice summary");
	}
	
	public void isContinueButtonDisplayed(){
		sync(2000L);
		getElementWhenClickable(continueButton);
	}
	
	public void clickpayInFull(){
		waitforPayInFull();
		click(payInFull, "Pay In Full");
	}
	
	public void clickPayWithMultipleCards(){
		waitforPayInFull();
		click(payWithMultipleCards, "Pay with payment cards");
	}
	
	public void clickSelectPaymentPlanRadio(){
		waitforPayInFull();
		click(selectPaymentPlanRadio, "Select payment plan radio", radioChecked, 1);
		getElementWhenVisible(radioChecked);
	}
	
//	public void selectPaymentPlan(String planName) {
//		String paymentplans = getXpath(paymentPlansDropdown, "Payment plan dropdown", "", 0);
//		String xpath = "";
//		if(paymentplans.trim().contains("|")) {
//			String[] xpaths = paymentplans.trim().split("\\|");
//			int i = 0;
//			for(; i < xpaths.length - 1; i++) {
//				xpath += xpaths[i].trim() + "//li[text()='" + planName + "']" + " | ";
//			}
//			xpath += xpaths[i].trim() + "//li[text()='" + planName + "']";
//		}
////		Actions action = new Actions(getDriver());
////		action.click(getElementWhenVisible(selectPaymentPlanValue)).perform();
////		action.click(getElementWhenVisible(By.xpath(xpath))).perform();
//		click(selectPaymentPlanValue, "Select payment plan", By.xpath(xpath), 1);
//		javascriptClick(getElementWhenVisible(By.xpath(xpath)), planName);
//	}
	
	public void selectDefaultPaymentPlan() {
		String paymentplans = getXpath(paymentPlansDropdown, "Payment plan dropdown", "", 0);
		String xpath = "";
		if(paymentplans.trim().contains("|")) {
			String[] xpaths = paymentplans.trim().split("\\|");
			int i = 0;
			for(; i < xpaths.length - 1; i++) {
				xpath += "(" + xpaths[i].trim() + "//li)[2]" + " | ";
			}
			xpath += "(" + xpaths[i].trim() + "//li)[2]";
		}
//		Actions action = new Actions(getDriver());
//		action.click(getElementWhenVisible(selectPaymentPlanValue)).perform();
//		action.click(getElementWhenVisible(By.xpath(xpath))).perform();
		try {
			click(selectPaymentPlanValue, "Select payment plan", By.xpath(xpath), 1);
		} catch(Exception ex) {
			//Do Nothing
		}
		javascriptClick(getElementWhenVisible(By.xpath(xpath)), "First plan name");
	}
	
	public boolean waitforPayInFull(){
		getElementWhenVisible(payInFull);
		return true;
	}
	
	public boolean waitforMasterCard(){
		getElementWhenVisible(firstCard, 40);
		return true;
	}
	
	public boolean waitforCardInfo(){
		getElementWhenVisible(addNewCard);
		return true;
	}
	
	public void clickCardInfo(){
		click(cardinfo, "Card Info");
	}
	
	public void selectFirstCard(){
		click(firstCard, "Card Info");
	}
	
	public void selectSecondCard(){
		click(secondCard, "Card Info");
	}
	
	public void clickContinuePlan(){
		getElementWhenRefreshed(continuePlan, "disabled", "null");
		click(continuePlan, "Continue Plan", continueCard, 1);
	}
	
	public boolean isContinuePlanEnabled() {
		return getAttribute(continuePlan, "disabled") == null ? true : false;
	}
	
	public void clickContinueCard() {
		getElementWhenRefreshed(continueCard, "disabled", "null");
		click(continueCard, "Continue Card");
	}
	
	public boolean waitForPaymentPlanBreakage(){
		getElementWhenVisible(paymentPlanBreakage, 40);
		return true;
	}
	
	public String amountDue(){
		String amount_due=getElementWhenVisible(amount_Due).getText();
		return amount_due.replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
	}
	
	public String finalamountDue(){
		String finalamount_due=getElementWhenVisible(finalamountdue).getText();
		return finalamount_due.replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
	}
	
	public String totalamountDue(){
		String totalamount_due=getElementWhenVisible(total_ticket_amount).getText();
		return totalamount_due.replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
	}
	
	public String paymentoption(){
		String payment_option=getElementWhenVisible(paymentOption).getText();
		//System.out.println("payment_option: "+payment_option);
		return payment_option;
	}
	
	public String selectpaymentoption(){
		String selectpayment_option=getElementWhenVisible(payWithMultipleCards).getText();
		System.out.println("select payment_option: "+selectpayment_option);
		return selectpayment_option;
	}
	
	public String cardlastDigit(){
		String cardlastdigitsnumber = getText(cardlastfourdigitsnumber);
		System.out.println(cardlastdigitsnumber);
		String[] cardnumber = cardlastdigitsnumber.split(" ");
		String str = cardnumber[3];
		System.out.println("cardlastfourdigitsnumber: " + str);
		return str;
	}
	
	public String getInvoiceText(){
		return getText(InvoiceText,10);
	}
	
	public String getInvoiceDashboardText(){
		return getText(InvoiceLinkText,10);
	}
	
	public String getInvoiceLink(){
		WebElement we = getElementWhenVisible(this.InvoiceLink);
		return we.getAttribute("href");
	}
	
	public String getInvoicePageText(){
		return getText(InvoicePageText, 10);
	}
	
	public String getDueAmountonDashboard(){
        return getText(DashboardAmount_Due,10).trim().replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
	}
	
	public void switchWindowContextHandles() {
		Set<String> contextView = ((AppiumDriver<?>) getDriver()).getContextHandles();
		ArrayList<String> s = new ArrayList<String>(contextView);
		((AppiumDriver<?>) getDriver()).context(s.get(contextView.size() - 1));
		sync(5000L);
	}
	
	public String getTermsnCondition(){
		if(checkIfElementPresent(TermsPopup))
			return getText(TermsPopup);
		else {
			if(((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
				switchWindowContextHandles();
			} else
				switchToWindow(1);
			return getText(TermsheaderNewWindow);
		}
	}
	
//	public void sendLastName(String textToType ) throws Exception{
//	  WebElement we = getElementWhenVisible(this.Lastname_Bill);
//			we.clear();
//			sendKeys(we, textToType);
//		}
//	
//	public void clickEmail(){	
//		getElementWhenVisible(Email, 40);
//		click(Email,"EMAIL");
//		
//		click(Close,"CLOSE");
//	}
//	
//	public void clickPrint(){
//		click(Print,"PRINT");
//	}
	
	public void clickTermsnCond(){
		if(!checkIfElementPresent(tnCCheck, 1))
			throw new SkipException("Skipped");
		click(Terms, "Terms & Conditions", null, By.xpath(".//XCUIElementTypeLink[@name='Terms & Conditions']"), "Terms & Conditions", false);
	}
	
	public Double getTotalTicketAmount(){ 
		List<WebElement> amounts = getWebElementsList(By.xpath("//table[contains(@class,'invoice-amountTable')]//tr"));
		double total=0.00;
		for(int i = 0; i < amounts.size() - 2; i++) {
			String amt = getText(By.xpath("//table[contains(@class,'invoice-amountTable')]//tr[" + (i + 1) + "]/td[2]/span")).replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
			total += Double.parseDouble(amt);
		}
	  return total;
	}
	
	public String getLessPaymentCredit(){ 
		  return getText(LessPayment,10);
	}
	
	public String getReviewPaymentCredit(){ 
		return getText(less_credits,10);
	}
	
	public String getPaymentErrorMessage(){
		return getText(PayError,10);
	}

	public void typeBillingDetails(String FirstName,String LastName,String Add1, String Add2, String Country, String State,String City,String Zipcode) throws Exception{
		type(firstName, "FirstName", FirstName, false, By.xpath("//XCUIElementTypeTextField[1]"));
		type(lastName, "LastName", LastName, false, By.xpath("//XCUIElementTypeTextField[2]"));
		type(streetAdd1,"AddressLine1", Add1, false, By.xpath("//XCUIElementTypeTextField[3]"));
		type(streetAdd2,"AddressLine2",Add2, false, By.xpath("//XCUIElementTypeTextField[4]"));
		if(driverType.trim().toUpperCase().contains("IOS")) {
			type(country, "Country", "United States", false, By.xpath("//XCUIElementTypeTextField[5]"));
			type(state, "State", "", false, By.xpath("//XCUIElementTypeTextField[6]"));
		}
		type(city,"City",City, false, By.xpath("//XCUIElementTypeTextField[7]"));
		type(zipCode,"ZipCode",Zipcode, false, By.xpath("//XCUIElementTypeTextField[8]"));
		
//		Actions action = new Actions(getDriver());
//		getElementWhenVisible(country).click();
//		action.click(getElementWhenVisible(By.xpath(countryName+"[text()='"+Country+"']"))).perform();
//		getElementWhenVisible(state).click();
//		action.click(getElementWhenVisible(By.xpath(stateName+"[text()='"+State+"']"))).perform();
	}

	public void typePaymentAmount(String Amt) throws Exception{
		type(paymentAmount, "payment amount", Amt, true);
	}
	
	public void typeSecondPaymentAmount(String Amt) throws Exception{
		type(secondpaymentAmount, "payment amounts", Amt, true);
	}
	
	public void typeNewPaymentAmount(String Amt) throws Exception{
		type(newpaymentAmount, "payment amount", Amt, true);
	}
	
	public String getSuccessHead(){
		return getText(paymentSuccessHead);
	}

	public void clickAddNewCard(){
		click(addNewCard, "Add New Card", inputCardNumber, 1);
	}
			
	public int getInvoiceId(){
		String selectedInvoiceXpath = getXpath(selectedInvoice, "Selected invoice", "", -1);
		 if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS") || driverType.trim().toUpperCase().contains("SAFARI"))) {
			 	getElementWhenPresent(By.xpath(selectedInvoiceXpath));
			 	String[] href = getDriver().findElement(By.xpath(selectedInvoiceXpath+"//a")).getAttribute("href").split("\\#/");
			 	return Integer.parseInt(href[1].replaceAll("/.*", ""));
		 }
		 else {
			 WebElement selectedInvoiceId = getSingleChildObject(selectedInvoiceXpath, By.tagName("a"), "", "Invoice id");
			 String[] href = getAttribute(selectedInvoiceId, "href").split("\\#/");
			 Dictionary.put("invoiceId", href[1].replaceAll("/.*", ""));
			 return Integer.parseInt(href[1].replaceAll("/.*", ""));
		 }
	}
	
	public String getSelectedInvoiceDesc(){
		String selectedInvoiceXpath = getXpath(selectedInvoice, "Selected invoice", "", -1);
		WebElement selectedInvoiceDesc = getSingleChildObject(selectedInvoiceXpath, By.tagName("em"), "", "Invoice description");
		return selectedInvoiceDesc.getText();
	}
	
	public String getInvoiceDesc(int invoiceId){
		By invoice = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[contains(@href, '" + invoiceId + "')]/..");
		String selectedInvoiceXpath = getXpath(invoice, "Invoice", "", -1);
		WebElement selectedInvoiceDesc = getSingleChildObject(selectedInvoiceXpath, By.tagName("em"), "", "Invoice description");
		return selectedInvoiceDesc.getText();
	}
	
	public String getInvoiceQty(){
		return getElementWhenVisible(invoiceQty).getText();
	}

	public String getInvoiceName(){
		return getElementWhenVisible(invoiceName).getText();
	}

	public String getSection(){
		String sec= getElementWhenVisible(sectionRowSeat).getText();
		String str1= sec.trim().split("\\|")[0].trim().substring(sec.trim().split("\\|")[0].trim().indexOf(" ") + 1).trim();
		return str1;
	}

	public String getRow(){
		String sec= getElementWhenVisible(sectionRowSeat).getText();
		String str1= sec.trim().split("\\|")[1].trim().substring(sec.trim().split("\\|")[1].trim().indexOf(" ") + 1).trim();
		return str1;
	}

	public String getFirstSeat(){
		String sec= getElementWhenVisible(sectionRowSeat).getText();
		String str1= sec.trim().split("\\|")[2].trim().substring(sec.trim().split("\\|")[2].trim().indexOf(" ") + 1).trim();
		if(Integer.parseInt(getInvoiceQty())>1){
			return str1.split("-")[0].trim();
		} else
			return str1;
	}
   
	public String getLastSeat(){
		if(Integer.parseInt(getInvoiceQty())>1){
			String sec= getElementWhenVisible(sectionRowSeat).getText();
			String str1= sec.trim().split("\\|")[2].trim().substring(sec.trim().split("\\|")[2].trim().indexOf(" ") + 1).trim();
			return str1.split("-")[1].trim();
		}
		else {
			return getFirstSeat();
		}
	}
	   
	public String getUnitPrice(){
		String unitP=getText(unitPrice).replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
		return unitP;
	}
	   
	public String CardlastDigits(String invoicestatus){
		String CardlastDigits = Dictionary.get(invoicestatus.trim().toUpperCase() + "data_mask");
		String[] str=CardlastDigits.split("x");
		return str[1];
	}
	   
	public String cardNumber(){
		String sec= getElementWhenVisible(cardDetails1).getText();
		String[] str1= sec.split(" ");
		return str1[2].replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
	}
   
	public String cardExpiry(){
		String str= getElementWhenVisible(cardDetails1).getText().trim();
		str = str.substring(str.lastIndexOf(" ") + 1).trim();
		String str3 = str.replace("/", "");
		return str3;
	}
   
	public String cardExpiryDate(String invoicestatus){
		String CardlastDigits = Dictionary.get(invoicestatus.trim().toUpperCase() + "cc_exp");
		return CardlastDigits;
	}
	   
	public String ccfirstName(String invoicestatus){
		String cc_name_first = Dictionary.get(invoicestatus.trim().toUpperCase() + "cc_name_first").trim();
		return cc_name_first;  
	}
	   
	public String firstName(){
		String str= getAttribute(firstName, "value");
		return str;
	}
	   
	public String ccNamelast(String invoicestatus){
		String cc_name_last = Dictionary.get(invoicestatus.trim().toUpperCase() + "cc_name_last");
		return cc_name_last;   
	}
	   
	public String lastName(){
		String str= getAttribute(lastName,"value");
		return str;
	}
	   
	public String ccPostalcode(String invoicestatus){
		String cc_postal_code = Dictionary.get(invoicestatus.trim().toUpperCase() + "cc_postal_code");
		return cc_postal_code;   
	}
	   
	public String zipCode(){
		String str= getAttribute(zipCode,"value");
		return str;
	}
	
	public String CountryName(){
		String str= getAttribute(country,"value");
		return str; 
	}
	   
	public String ccAddress(String invoicestatus){
		String cc_address = Dictionary.get(invoicestatus.trim().toUpperCase() + "cc_address");
		return cc_address; 
	}
   
	public String address(){
		String str = getAttribute(streetAdd1,"value");
		return str;
	}
	   
	public String getInvoicedAmt(){
	   String invAmt = getText(invoicedAmt).replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
	   return invAmt;
	}

   public String getSelectedInvoiceBal(){
	   String selectedInvoiceXpath = getXpath(selectedInvoice, "Selected invoice", "", -1);
	   WebElement selectedInvoiceBalance = getSingleChildObject(selectedInvoiceXpath, By.xpath("//ul/li[1]/span"), "", "Invoice balance");
	   return selectedInvoiceBalance.getText().replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
   }
   
   public String getInvoiceBal(int invoiceId){
	   By invoice = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[contains(@href, '" + invoiceId + "')]/..");
	   String selectedInvoiceXpath = getXpath(invoice, "Invoice", "", -1);
	   WebElement selectedInvoiceBalance = getSingleChildObject(selectedInvoiceXpath, By.xpath("//div//div[2]/div[1]/span"), "", "Invoice balance");
	   return selectedInvoiceBalance.getText().replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
   }
   
   public String ExistingCardCVV(){
	   String sec = getElementWhenVisible(cardDetails1).getText();
	   String[] str1 = sec.split(" ");
	   String str2 = str1[2].substring(1);
	   System.out.println(str2);
	   return str2;
   }

   public void typeExistingCardCVV() throws Exception{
	   if(driverType.trim().toUpperCase().contains("SAFARI")) {
		   throw new SkipException("Skipped");
	   }
	   String sec = getElementWhenVisible(cardDetails1).getText();
	   String[] str1 = sec.split(" ");
	   String str2 = str1[2].substring(1);
	   type(cvvDetails, "Existing Card CVV", str2, true, By.xpath("//XCUIElementTypeSecureTextField[1]"));
   }
   
   public void typeSecondCardCVV() throws Exception{
	   if(driverType.trim().toUpperCase().contains("SAFARI")) {
		   throw new SkipException("Skipped");
	   }
	   String sec = getElementWhenVisible(cardDetails2).getText();
	   String[] str1 = sec.split(" ");
	   String str2 = str1[2].substring(1);
	   System.out.println("cvv: "+str2 );
	   type(secondCVV, "Existing Card CVV", str2, true, By.xpath("//XCUIElementTypeSecureTextField[1]"));
   }
   
   public String ExistingSecondCardCVV(){
	   String sec = getElementWhenVisible(cardDetails2).getText();
	   String[] str1 = sec.split(" ");
	   String str2 = str1[2].substring(1);
	   System.out.println(str2);
	   return str2;
   }

   public String getSelectedInvoiceDueDt() throws ParseException{
	   String selectedInvoiceXpath = getXpath(selectedInvoice, "Selected invoice", "", -1);
	   WebElement selectedInvoiceBalance = getSingleChildObject(selectedInvoiceXpath, By.xpath("//ul/li[2]/span"), "", "Invoice due date");
	   @SuppressWarnings("deprecation")
	   Date date = new Date(selectedInvoiceBalance.getText());
	   SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
	   String strDate = sm.format(date);
	   return strDate;
   }

   public String getHeaderAmountWhenRefreshed(double Amount){
	    NumberFormat in=NumberFormat.getCurrencyInstance(Locale.US);
		double payment = Amount;
		String newamt = in.format(payment).replace("$", Environment.get("currency"));
	    getElementWhenRefreshed(headerAmount, "innerHTML", newamt);
	    return getText(headerAmount).replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim(); 
   }
   
   public String getSelectedInvoiceBalWhenRefreshed(double expectedBalance){
	   String selectedInvoiceXpath = getXpath(selectedInvoice, "Selected invoice", "", -1);
	   getSingleChildObject(selectedInvoiceXpath, By.xpath("//ul/li[1]/span"), "", "Invoice balance");
	   NumberFormat in=NumberFormat.getCurrencyInstance(Locale.US);
	   double payment = expectedBalance;
	   String newamt = in.format(payment).replace("$", Environment.get("currency"));
	   WebElement selectedInvoiceBalance = getElementWhenRefreshed(By.xpath(selectedInvoiceXpath + "//ul/li[1]/span"), "innerHTML", newamt);
	   return selectedInvoiceBalance.getText().replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
   }
   
   public String getInvoiceBalWhenRefreshed(int invoiceId, double expectedBalance){
	   By invoice = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[contains(@href, '" + invoiceId + "')]/..");
	   String selectedInvoiceXpath = getXpath(invoice, "Invoice", "", -1);
	   getSingleChildObject(selectedInvoiceXpath, By.xpath("//div[contains(@class,'invoice-balancesList')]/div/span"), "", "Invoice balance");
	   NumberFormat in=NumberFormat.getCurrencyInstance(Locale.US);
	   double payment = expectedBalance;
	   String newamt = in.format(payment).replace("$", Environment.get("currency"));
	   WebElement selectedInvoiceBalance = getElementWhenRefreshed(By.xpath(selectedInvoiceXpath + "//div[contains(@class,'invoice-balancesList')]/div/span"), "innerHTML", newamt);
	   return selectedInvoiceBalance.getText().replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
   }
   
   public void getInvoice(String invoiceBal, int invoiceId) {
	   NumberFormat in=NumberFormat.getCurrencyInstance(Locale.US);
	   double payment = Double.valueOf(invoiceBal);
	   String newamt = in.format(payment).replace("$", Environment.get("currency"));
	   try{
			getElementWhenRefreshed(amount_Due, "innerHTML", newamt);
		} catch(Exception ex) {
			utils.navigateTo("/invoice#/" + invoiceId + "/1");
			sync(5000L);
			getElementWhenVisible(invoiceDetail, 40);
			getElementWhenRefreshed(amount_Due, "innerHTML", newamt);
		}
   }
   
   public String getInvoiceDueDt(int invoiceId){
	   By invoice = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[contains(@href, '" + invoiceId + "')]/..");
	   String selectedInvoiceXpath = getXpath(invoice, "Invoice", "", -1);
	   WebElement selectedInvoiceBalance = getSingleChildObject(selectedInvoiceXpath, By.xpath("//div[contains(@class,'invoice-dueDateContainer')]//span"), "", "Invoice due date");
	   @SuppressWarnings("deprecation")
	   Date date = new Date(selectedInvoiceBalance.getText());
	   SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
	   String strDate = sm.format(date);
	   return strDate;
   }
   
   public boolean verifyInvoiceStatus(int invoiceId, String invoicestatus){
	     isInvoiceListDisplayed();
	   By invoice = null;
	   switch(invoicestatus.trim().toUpperCase()){
	    case "PLAN":
		 //invoice = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[contains(@href, '" + invoiceId + "') and (descendant::span[contains(@class, 'invoice-paid') and (text()='Payment Plan' or text()='Paid')])]/..");
	   		invoice = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li/a[contains(@href,'" + invoiceId + "') and (descendant::span[contains(@class,'invoice-paid')  and (descendant::span[contains(text(),'Payment Plan')])])]");
	 		break;
	   	case "PAYMENT PLAN":
	   		//invoice = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[contains(@href, '" + invoiceId + "') and (descendant::span[contains(@class, 'invoice-paid') and (text()='Payment Plan' or text()='Paid')])]/..");
	   		invoice = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li/a[contains(@href,'" + invoiceId + "') and (descendant::span[contains(@class,'invoice-paid')  and (descendant::span[contains(text(),'Payment Plan')])])]");
	 		break;
	 	case "UNPAID":
	 		invoice = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[contains(@href, '" + invoiceId + "') and ((descendant::span[contains(@class, 'invoice-paid')]))]/..");
			break;
	 	case "PARTIALLY PAID":
	 		invoice = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[contains(@href, '" + invoiceId + "') and ((descendant::span[contains(@class, 'invoice-paid')]))]/..");
	 		break;
	 	default:
	 		invoice = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[contains(@href, '" + invoiceId + "') and (descendant::span[contains(@class,'invoice-paid')  and (descendant::span[contains(text(),'Payment Plan')] | .//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[contains(@href, '" + invoiceId + "') and (descendant::span[contains(@class, 'invoice-paid') and (text()='Payment Plan' or text()='Plan')])]/.. | .//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[contains(@href, '" + invoiceId + "') and (descendant::span[contains(@class, 'invoice-paid') and descendant::span[text()='PAYMENT PLAN' or text()='Plan']])]/..");
	   }
	  
	   return checkIfElementPresent(invoice);
   }
   
   public boolean verifyPaymentPlanBreakage(String paymentPlansSchedule) throws JSONException, java.text.ParseException{
	   waitForPaymentPlanBreakage();
	   List<WebElement> dates = getWebElementsList(paymentPlanBreakageDates);
	   List<WebElement> amount = getWebElementsList(paymentPlanBreakageAmount);
	   JSONObject jsonObject=new JSONObject(paymentPlansSchedule);
	   JSONObject command1=(JSONObject) jsonObject.get("command1");
	   JSONArray payments=(JSONArray) command1.get("payments");
	   
	   boolean success = false;
	   for(int i = 0; i < payments.length(); i++) {
		   JSONObject payment = payments.getJSONObject(i);
		   NumberFormat in = NumberFormat.getCurrencyInstance(Locale.US);
		   String owedAmt = in.format(Double.valueOf(payment.getString("owed_amount"))).replace("$", Environment.get("currency"));
		   SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		   Date dueDate = sm.parse(payment.getString("payment_due_date"));
		   SimpleDateFormat dt1 = new SimpleDateFormat("MMMMM dd, yyyy");
		   String strDate = dt1.format(dueDate);
		   if(strDate.trim().equalsIgnoreCase(dates.get(i).getText().trim()) && owedAmt.trim().equalsIgnoreCase(amount.get(i).getText().trim())) {
			   success = true;
		   } else{
			   success = false;
			   break;
		   }
	   }
	   
	   if(success)
		   return true;
	   else
		   return false;
   }
   
   public List<List<String>> getInvoiceList() {
	   List<WebElement> invs = getWebElementsList(firstInvoiceLink);
	   List<List<String>> invsList = new ArrayList<List<String>>();
	   for(int i = 0; i < invs.size(); i++) {
		   String xpath = getXpath(firstInvoiceLink, "Invoice", "", -1);
		   WebElement invoiceDesc = getSingleChildObject("(" + xpath + ")[" + (i+1) + "]", By.tagName("em"), "", "Invoice description");
		   WebElement invoiceBal = getSingleChildObject("(" + xpath + ")[" + (i+1) + "]", By.xpath("//div/div[2]//span"), "", "Invoice balance");
//		   WebElement invoiceDueDate = getSingleChildObject("(" + xpath + ")[" + (i+1) + "]", By.xpath("//ul/li[2]/span"), "", "Invoice due date");
		   List<String> _invoice = new ArrayList<String>();
		   _invoice.add(invoiceDesc.getText());
		   _invoice.add(invoiceBal.getText());
//		   _invoice.add(invoiceDueDate.getText());
		   invsList.add(_invoice);
	   }
	   return invsList;
   }
   
   public String getCsrfToken(String cookies) throws Exception {
		Object[] obj = utils.get(Environment.get("APP_URL") + "/rest/session/token", new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1", cookies});
		InputStream is = (InputStream) obj[0];
		Scanner scan = new Scanner(is);
		String token = new String();
		while (scan.hasNext())
			token += scan.nextLine();
		scan.close();
		return token;
   }
   
   public void paymentsInvoiceAPI(int invoiceid , String cookies, String token) throws Exception {
	   String payload = "{\"acct_id\":\"" + Dictionary.get("member_id").trim() + "\",\"invoice_conf_id\":\"1\",\"credit_card\":[{\"amount\":\"0.1\",\"pmt_type\":\"" + Dictionary.get("pmttype") + "\",\"cc_num\":\"" + Dictionary.get("CardNumber") + "\",\"cin\":\"" + Dictionary.get("CVV") + "\",\"exp_date\":\"" + Dictionary.get("ExpiryDate") + "\",\"first_name\":\"Test\",\"last_name\":\"Test\",\"Avs_address\":\"Address\",\"Avs_postal_code\":\"12345\"}]}";
	   InputStream is = utils.post(Environment.get("APP_URL") + "/api/invoice/"+ invoiceid +"/payment?_format=json", payload, new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie", "content-type", "x-csrf-token"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1", cookies, "application/json", token});
	   JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
	   String message = jsonObject.getJSONObject("data").getString("errorHeading");
	   Assert.assertEquals(message, Dictionary.get("SuccessHeading").trim(), "Verify payment successful");
   }
   
   public String loginThruDrupalApi(String emailaddress, String password) throws Exception {
	   utils.post(Environment.get("APP_URL") + "/user/login?_format=json", "{\"name\":\"" + emailaddress + "\",\"pass\":\"" + password + "\",\"remember_me\":0}", new String[]{"accept", "accept-encoding", "accept-language", "content-type", "user-agent"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "application/json", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"});
	   String sessionCookie = Dictionary.get("RESPONSE_COOKIES").trim();
	   return sessionCookie;
   }
   
   public String getTerms(String cookies) throws Exception {
	   Object[] obj = utils.get(Environment.get("APP_URL") + "/api/text/terms?_format=json", new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1", cookies});
	   InputStream is = (InputStream) obj[0];
	   JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
	   return jsonObject != null ? (jsonObject.has("version") ? jsonObject.getString("version") : "") : "";
   }
   
   public void acceptTerms(String cookies, String token, String versionNumber) throws Exception {
	   if(!versionNumber.trim().equalsIgnoreCase("")) {
		   utils.post(Environment.get("APP_URL") + "/api/accept/terms?_format=json", "{\"response\" : true, \"version\" : \"" + versionNumber + "\"}", new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie", "content-type", "x-csrf-token"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1", cookies, "application/json", token});
	   }
   } 
   
   public String[] getInvoiceConfIds(int invoice_id, String cookies) throws Exception {
	   Object[] obj = utils.get(Environment.get("APP_URL") + "/api/invoice/" + invoice_id + "/1?_format=json", new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1", cookies});
	   InputStream is = (InputStream) obj[0];
	   JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
	   JSONArray data = jsonObject.getJSONArray("data");
	   List<String> invoice_conf_id = new ArrayList<String>();
	   for(int i = 0 ; i < data.length(); i++) {
		   JSONObject d = data.getJSONObject(i);
		   String invoice_item_seq_id = d.getString("invoice_item_seq_id");
		   invoice_conf_id.add(invoice_item_seq_id);
	   }
	   return invoice_conf_id.toArray(new String[invoice_conf_id.size()]);
   }
   
   public void paymentsPlanInvoiceAPI(int invoiceid , String cookies, String token, String planid) throws Exception {
	   String payload = "{\"acct_id\":\"" + Dictionary.get("member_id").trim() + "\",\"invoice_conf_id\":\"1\",\"credit_card\":[{\"amount\":\"0.2\",\"pmt_type\":\"" + Dictionary.get("pmttype") + "\",\"cc_num\":\"" + Dictionary.get("CardNumber") + "\",\"cin\":\"" + Dictionary.get("CVV") + "\",\"exp_date\":\"" + Dictionary.get("ExpiryDate") + "\",\"first_name\":\"Test\",\"last_name\":\"Test\",\"Avs_address\":\"Address\",\"Avs_postal_code\":\"12345\"}],\"payment_plan_id\":"+ planid+ "}";
	   InputStream is = utils.post(Environment.get("APP_URL") + "/api/invoice/"+ invoiceid +"/payment?_format=json", payload, new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie", "content-type", "x-csrf-token"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1", cookies, "application/json", token});
	   JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
	   String message = jsonObject.getJSONObject("data").getString("errorHeading");
	   Assert.assertEquals(message, Dictionary.get("SuccessHeading").trim(), "Verify payment successful");
   }
   
   public String getPaymentsPlanID(int invoiceid, String cookies) throws Exception {
	   Object[] obj=  utils.get(Environment.get("APP_URL") + "/api/invoice/plans/"+invoiceid+"?_format=json", new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1", cookies});
	   InputStream is = (InputStream) obj[0];
	   JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
	   String planids= (String) jsonObject.get("payment_plan_ids");
	   System.out.println("planid:" + planids);
	   String[] planID= planids.toString().split(",");
	   String plan_id=planID[1];
	   return plan_id;
   }
   
   public String[] getInvoiceDetails(String emailaddress, String password, String queryString) throws Exception {
	   InputStream is;
	   Set<Cookie> cookies = getDriver().manage().getCookies();
	   Iterator<Cookie> iter = cookies.iterator();
	   String _cookies = "";
	   if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS") || driverType.trim().toUpperCase().contains("SAFARI"))) {
		   while(iter.hasNext()) {
			   Cookie cookie = iter.next();
			   if(driverType.trim().toUpperCase().contains("SAFARI"))
				   _cookies += cookie.getName() + "=" + cookie.getValue() + ";";
			   else
				   _cookies += URLEncoder.encode(cookie.getName(), "UTF-8") + "=" + URLEncoder.encode(cookie.getValue(), "UTF-8") + ";";
		   }
		   is = utils.post(Environment.get("APP_URL") + "/user/login?_format=json", "{\"name\":\"" + emailaddress + "\",\"pass\":\"" + password + "\",\"remember_me\":0}", new String[]{"accept", "accept-encoding", "accept-language", "content-type", "user-agent"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "application/json", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"});
		   String __cookies = Dictionary.get("RESPONSE_HEADERS").substring(Dictionary.get("RESPONSE_HEADERS").indexOf("Set-Cookie")).split("\\\\n")[0].replace("Set-Cookie : ", "");
		   String sessionCookie = utils.getSessionCookie(__cookies);
		   _cookies += sessionCookie;
	   } else {
		   while(iter.hasNext()) {
			   Cookie cookie = iter.next();
			   _cookies += cookie.getName() + "=" + cookie.getValue() + ";";
		   }
	   }
	   if(!_cookies.trim().equalsIgnoreCase("")) {
		   int counter = 3;
		   JSONObject jsonObject;
		   do{
			   Object[] obj =  utils.get(Environment.get("APP_URL") + "/api/invoice/" + queryString + "?_format=json", new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1", _cookies});
			   is = (InputStream) obj[0];
			   jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
			   counter --;
		   } while(counter > 0 && jsonObject.optJSONArray("data") == null);
		  
		  JSONArray data = jsonObject.getJSONArray("data");
		  
		  JSONObject label = jsonObject.getJSONObject("label");
		  String park_api_event = "", hand_api_event = "";
		  int en_park = 0, en_hand = 0;
		  boolean flag = false;
		  if(label.has("park")) {
			  JSONObject park = label.getJSONObject("park");
			  en_park = park.getInt("en_park");
			  park_api_event = park.getString("park_api_event");
			  flag = true;
		  }
		  if(label.has("hand")) {
			  JSONObject hand = label.getJSONObject("hand");
			  en_hand = hand.getInt("en_hand");
			  hand_api_event = hand.getString("hand_api_event");
			  flag = true;
		  }
		  if(!flag) {
			  JSONArray summary_fields = label.getJSONArray("summary_fields");
			  for(int i = 0; i < summary_fields.length(); i++) {
				  JSONObject summary_field = summary_fields.getJSONObject(i);
				  if(summary_field.getString("field_name").trim().toLowerCase().contains("parking")) {
					  park_api_event = summary_field.getString("field_event_name").trim();
					  if(!park_api_event.trim().equalsIgnoreCase(""))
						  en_park = 1;
				  }
				  if(summary_field.getString("field_name").trim().toLowerCase().contains("handling")) {
					  hand_api_event = summary_field.getString("field_event_name").trim();
					  if(!hand_api_event.trim().equalsIgnoreCase(""))
						  en_hand = 1;
				  }
			  }
		  }
		  
		  float totalticketamount = 0.0f;
		  float parkingfee = 0.0f;
		  float handlingfee = 0.0f;
		  
		  for(int i = 0; i < data.length(); i++) {
			  float current_due_amt = Float.valueOf(data.getJSONObject(i).getString("invoiced_amount"));
			  if(en_park == 1 && data.getJSONObject(i).has("event_name") && park_api_event.trim().toLowerCase().contains(data.getJSONObject(i).getString("event_name").trim().toLowerCase())) {
				  parkingfee += current_due_amt;
			  }
			  else if(en_hand == 1 && data.getJSONObject(i).has("event_name") && hand_api_event.trim().toLowerCase().contains(data.getJSONObject(i).getString("event_name").trim().toLowerCase())) {
				  handlingfee += current_due_amt;
			  } else {
				  totalticketamount += current_due_amt;
			  }
		  }
		  NumberFormat in = NumberFormat.getCurrencyInstance(Locale.US);
		  return new String[]{in.format(totalticketamount).replace("$", Environment.get("currency")), in.format(parkingfee).replace("$", Environment.get("currency")), in.format(handlingfee).replace("$", Environment.get("currency"))};
	   }
	   
	   return null;
   }
   public InvoiceConfirmationMapper getActualInvoiceDetailOnUI() {
//		private By viewPaymentSchedule = By.xpath("(//span[contains(@class,'invoice-desktopScheduleLink')])[1]");
		if(checkElementPresent(viewInvoiceDetail, 10))
			click(viewInvoiceDetail, "View Invoice Detail");
		InvoiceConfirmationMapper actualInvoiceDetailOnUI = new InvoiceConfirmationMapper(
				InvoiceConfirmationMapper.getMessageType("CamInvoice"));
		actualInvoiceDetailOnUI.setOrderNumber(Integer.parseInt(getText(orderNumber, 10).split(" ")[1].trim()));
		String ticket = getText(ticketDetail);
		System.out.println("Ticket :: " + ticket);
		actualInvoiceDetailOnUI.setTicketDetail(ticket);
		String[] ticketDetails = ticket.split("\\|");
		actualInvoiceDetailOnUI.setSection(
				ticketDetails[0].replaceAll(SessionContext.get().nbsp, "").replace("Sec", "Section").trim());
		actualInvoiceDetailOnUI.setRow(ticketDetails[1].replaceAll(SessionContext.get().nbsp, "").trim());
		actualInvoiceDetailOnUI.setSeat(ticketDetails[2].replaceAll(SessionContext.get().nbsp, "").trim());
		actualInvoiceDetailOnUI.setEventName(getText(eventName).trim());
		String itemTotal_ = getText(itemTotal).split("\\$")[1];
		actualInvoiceDetailOnUI.setItemTotal(itemTotal_);
		actualInvoiceDetailOnUI.setDeliveryCharges(getText(deliveryCharges).split("\\$")[1].trim());

		clickViewPaymentSchedule();

		ArrayList<InvoiceDetail> invoiceDetail = new ArrayList<InvoiceConfirmationMapper.InvoiceDetail>();
		List<WebElement> invoicePlan = getDriver().findElements(invoicePlanStatus);
		for (WebElement plan : invoicePlan) {
			String date = plan.findElement(By.xpath("./div[contains(@class,'Date')]")).getText();
			String amount = plan.findElement(By.xpath("./div[contains(@class,'Amount')]")).getText().split("\\$")[1];
			invoiceDetail.add(actualInvoiceDetailOnUI.new InvoiceDetail(amount, "", date));
		}
		actualInvoiceDetailOnUI.setInvoiceDetail(invoiceDetail);

		clickCloseButton();

		String lessPayment = getElementWhenVisible(amountTable).findElement(By.xpath("./div[2]//span")).getText();
		actualInvoiceDetailOnUI.setPaidToday(lessPayment.split("\\$")[1]);

		return actualInvoiceDetailOnUI;
	}

	public void clickOnSendEmailLink() {
		click(emailButton, "Email Link");
		clickCloseButton();
	}

	public void clickMakeaPayment() {
		click(makeAPayment, "Make a Payment");

	}

	public void clickOnViewUpdateInvoice() {
		Assert.assertTrue(getText(emailMessage,10).contains("receive a confirmation email"),"Verify updated verify message present");
		click(viewUpdateInvoice, "View Update Invoice");
	}
	
	public void clickViewPaymentSchedule() {
		click(viewPaymentSchedule, "View Payment Schedule");
	}
	
	public void clickCloseButton() {
		click(closeButton, "Close Button");
	}

}

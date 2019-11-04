package org.iomedia.galen.pages;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import java.util.concurrent.TimeUnit;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.framework.Driver.TestDevice;
import org.iomedia.galen.common.ManageticketsAPI;
import org.iomedia.galen.common.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;

import com.amazonaws.services.inspector.model.AssetAttributes;

import io.appium.java_client.AppiumDriver;


public class InvoiceNew extends BaseUtil {
	private String driverType;

	public InvoiceNew(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter,
			org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert,
			ThreadLocal<HashMapNew> sTestDetails) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		driverType = driverFactory.getDriverType().get();
	}

	private By firstCardDetailNew = By.xpath(
			"//div[contains(@class,'theme-dropdown')]//div//span[contains(@class,'invoice-activeCardLabel')]//span");
	private By successWindowNew = By.xpath("//div[contains(@class,'invoice-successFailureStatus')]");
	private By successMessageNew = By.xpath(
			"//div[contains(@class,'invoice-successFailureStatus')]//div[contains(@class,'invoice-successFailureStatusHead')]//h4/span");
	private By UpdatedInvoiceButton = By.xpath("//div[contains(@class,'invoice-successFailureStatus')]//div//button");

	private By firstUnpaidInvoiceLink = By.xpath(
			".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[not(descendant::span[contains(@class, 'invoice-paid')])]/..//a");

	private By paidTab = By.xpath("//span[contains(text(),'Paid')]");
	private By unPaidTab = By.xpath("//span[contains(text(),'Unpaid')]");
	private By firstPlanInvoiceLink = By
			.xpath("(//li[contains(@class,'list-item')]//span[contains(text(),'Plan')]/../../..)[1]");
	private By invoiceMsgBox = By.xpath("//*[contains(@class,'invoice-msgBox')]");
	private By invoiceList = By
			.xpath(".//div[contains(@class, 'invoiceListing')]//li[starts-with(@class, 'list-item')]");
	private By paymentMethod = By.xpath(".//div[contains(@class, 'invoice-paymentMethodBox')]//p");

	private By InvoiceDetailsNew = By.xpath(".//div[contains(@class,'invoice-viewInvoiceDetails')]");
	private By addOnContinue = By.xpath("//*[contains(@class,'invoiceAdd')]//button[@type='button']");
	private By headerAmount = By.cssSelector(".react-root-dashboard-header div div div div:last-child span");
	// private By headerAmount = By.cssSelector(".react-root-dashboard-header div
	// div div:last-child span");
	private By continueButton = By.cssSelector("button[class*='invoice-buttonBlock']");
	private By activeInvoiceBalanceDue = By
			.xpath("//li[contains(@class,'activeItem')]//*[contains(text(),'Balance Due')]/..//span");

	private By activeInvoiceAmountDue = By.xpath("//*[contains(@class,'invoice-invoiceSummary')]//*[contains(@class,'invoice-amountDue')]//span");
	
	private By amountDueSummarySection = By.xpath("(//div[contains(@class,'amountDue')]//span)[1]");
	private By amountDueSummarySectionNew = By.xpath("//div[contains(@class,'amountDue')]//span");

	private By amountDuePaymentSection = By
			.xpath("//div[contains(@class,'amountTablePaymentOption')]//div[contains(@class,'amountDue')]//span");

	private By amountDuePaymentSectionNew = By
			.xpath("//div[contains(@class,'invoice-paymentRight')]//div[contains(@class,'amountDue')]//span[2]/span");
	private By selectOrAddPaymentMethod = By
			.xpath("//p[contains(@class,'invoice-paymentText')] | //span[contains(@class,'editAddCard')]");
	private By selectOrAddPaymentMethodIOS = By
			.xpath("//p[contains(@class,'invoice-paymentText')] | //span[contains(@class,'editAddCardOnMobile')]");
	private By selectPaymentMethodPopUpTitle = By.xpath("//h6[contains(@class,'title')]");
	private By addNewPaymentButtonSelectPaymentPopUp = By
			.xpath("//div[contains(@class,'invoice-editAddCardDialog')]//div//div");

	private By continueButtonPopUp = By
			.xpath("//div[contains(@class,'formSubmitButton')]//button[text()='CONTINUE' or text()='Continue']");

	private By firstCardImage = By.xpath("((//div[contains(@class,'cardDetail')])[1]//p//span)[1]");
	private By secondCardImage = By.xpath("((//div[contains(@class,'cardDetail')])[4]//p//span)[1]");

	private By allCardImageConfirmPop = By
			.xpath("(//div[contains(@class,'cardDetail')])//p//span[contains(@class,'i')]");
	private By allCardNumberConfirmPop = By.xpath("(//div[contains(@class,'cardDetail')])//p//span[2]");
	private By allCardNameConfirmPop = By
			.xpath("(//div[contains(@class,'cardDetail')]//strong[contains(text(),'NA')])/..//span[1]");
	private By allCardExpConfirmPop = By
			.xpath("(//div[contains(@class,'cardDetail')]//strong[contains(text(),'EX')])/..//span[1]");

	private By allCardImagePaymentSection = By
			.xpath("//div[contains(@class,'cardIn')]//p//span[contains(@class,'mage')]");
//	private By allCardImagePaymentSectionNew = By.xpath("\"//div[contains(@class,'theme-dropdown')]//ul[contains(@class,'theme-values')]//li//div//span[contains(@class,'invoice-activeCardLabel')]//span");
	private By allCardNamePaymentSection = By
			.xpath("//div[contains(@class,'cardIn')]//strong[contains(text(),'NA')]/..//span");
	private By allCardExpPaymentSection = By
			.xpath("(//div[contains(@class,'cardInfo')]//strong[contains(text(),'EX')])/..//span[1]");

	private By allCardNamePaymentSectionNew = By.xpath(
			"//div[contains(@class,'theme-dropdown')]//ul[contains(@class,'theme-values')]//li//div//span[contains(@class,'invoice-activeCardLabel')]//span");
	// div[contains(@class,'theme-dropdown')]//ul[contains(@class,'theme-values')]//li//div//span[contains(class,'invoice-activeCardLabel')]
//	private By allCardExpPaymentSectionNew = By.xpath("(//div[contains(@class,'theme-field')]//strong[contains(text(),'EX')])/..//span[1]");

	private By allCardNumberPaymentSection = By.xpath("//div[contains(@class,'cardInfo')]//p//span[2]");
	private By paymentMethodsDropDown = By.xpath("//*[contains(@class,'invoice-cardList-')]//*[contains(@class,'theme-templateValue-')]");
	private By allCardNumberPaymentSectionNew = By.xpath(
			"//div[contains(@class,'invoice-cardList')]//ul//li//span[contains(@class,'invoice-activeCardLabel')]//span");

	private By firstCardNumberSelectPaymentPopUp = By.xpath("((//div[contains(@class,'cardDetail')])[1]//p//span)[2]");
	private By firstCard = By.xpath("//div[contains(@class,'cardDetail')]");
	private By secondCard = By.xpath("//div[contains(@class,'invoice-item')][2]//div[contains(@class,'cardDetail')]");
	private By firstCardNo = By.xpath("//div[contains(@class,'cardDetail')]//div[1]//sapn");
	private By firstCardName = By.xpath("//div[contains(@class,'cardDetail')]//div[3]//span[1]");
	private By firstCardExp = By.xpath("//div[contains(@class,'cardDetail')]//div[3]//span[2]");
	private By secondCardNo = By
			.xpath("//div[contains(@class,'invoice-item')][2]//div[contains(@class,'cardDetail')]//div[1]//sapn");
	private By secondCardName = By
			.xpath("//div[contains(@class,'invoice-item')][2]//div[contains(@class,'cardDetail')]//div[3]//span[1]");
	private By secondCardExp = By
			.xpath("//div[contains(@class,'invoice-item')][2]//div[contains(@class,'cardDetail')]//div[3]//span[2]");
	private By firstCardNameSelectPaymentPopUp = By
			.xpath("((//div[contains(@class,'cardDetail')])[1]//strong)[1]/..//span");
	private By firstCardExpiresSelectPaymentPopUp = By
			.xpath("((//div[contains(@class,'cardDetail')])[1]//strong)[2]/..//span");
	private By EmailbuttonText = By.cssSelector("button[class*='invoice-emailButton']");
	private By secondCardNumberSelectPaymentPopUp = By.xpath("((//div[contains(@class,'cardDetail')])[4]//p//span)[2]");
	private By secondCardNameSelectPaymentPopUp = By
			.xpath("((//div[contains(@class,'cardDetail')])[4]//strong)[1]/..//span");
	private By secondCardExpiresSelectPaymentPopUp = By
			.xpath("((//div[contains(@class,'cardDetail')])[4]//strong)[2]/..//span");
	private By secondCardCVVSelectPaymentPopUp = By.xpath("(//*[@type='password'])[2]");

	private By firstCardCVVSelectPaymentPopUp = By.xpath("(//*[@type='password'])[1]");
	private By allCVVSelectPaymentPopUp = By.xpath("//*[@type='password']");
	private By allCVVSelectPaymentPopUpIOS = By.xpath("//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeOther[11]/XCUIElementTypeSecureTextField");

	private By paymentOptionDropdown = By.xpath("//div[contains(@class,'paymentPlanDropDown')]//input");
	private By completed = By
			.xpath("//div[contains(@class,'accordionReview')]//span[contains(@class,'selectedValue')]");
	private By payOther = By.xpath("//ul[contains(@class,'value')]//li[contains(text(),'ther')]");
	private By payFull = By.xpath("//ul[contains(@class,'value')]//li[contains(text(),'ull')]");
	private By amountInputPaymentSection = By.xpath("//input[contains(@name,'payment.cardDetail')]");
	private By amountInputPaymentSectionNew = By.xpath("//input[contains(@name,'payment.paymentMethod0.amount')]");
	private By firstAmountInputPaymentSection = By.xpath(
			"(//input[contains(@name,'payment.paymentMethod')])[1] | //input[contains(@name,'payment.paymentMethod')]");
	private By amountDueInvoicePaymentSection = By
			.xpath("//div[contains(@class,'amountTablePaymentOption')]//div[contains(@class,'amountDue')]//span");
	private By payTodayAmountPaymentSection = By
			.xpath("//div[contains(@class,'PaymentOption')]//div[contains(@class,'payTodayAmount')]//span");

	private By payTodayAmountPaymentSectionNew = By
			.xpath("//div[contains(@class,'invoice-paymentRight')]//div[contains(@class,'payToday')]//span[2]/span");
	private By continuePlan = By
			.xpath(".//*[contains(@class, 'invoice-payNowButton')]//button[text()='Pay Now' or text()='Continue']");

	private By payTodayAmountReviewSection = By
			.xpath("//div[contains(@class,'invoice-reviewTableContainer')]//div[contains(@class,'payToday')]//span");

	private By cardCVVConfirmPaymentPopUp = By.xpath("(//*[@type='password'])[1]");
	private By amountToChargeConfirmPaymentPopUp = By
			.xpath("//div[contains(@class,'row invoice-selectPaymentTex')]//span");

	private By cardNameFirst = By
			.xpath("//div[contains(@class,'invoice-add-card')]//form//div[contains(@class,'theme-errored')]//input");
	private By cardNameLast = By
			.xpath("//div[contains(@class,'invoice-add-card')]//form//div[contains(@class,'theme-input')]//input");
	private By cardNumber = By.xpath(
			"//div[contains(@class,'invoice-add-card')]//form//div[contains(@class,'row')][2]//div[contains(@class,'theme-errored')]//input");
	private By Postalcode = By.xpath(
			"//div[contains(@class,'invoice-add-card')]//form//div[contains(@class,'row')][3]//div[contains(@class,'invoice-customLPadding')]//input");
	private By cardExpiry = By.xpath(
			"//div[contains(@class,'invoice-add-card')]//form//div[contains(@class,'row')][3]//div[contains(@class,'invoice-expirationDateDropdown')]//input");
	private By cardNameFirstError = By
			.xpath("//input[contains(@name,'cardDetail.cc_name_first')]/..//span[contains(@class,'error')]");
	private By cardNameLastError = By
			.xpath("//input[contains(@name,'cardDetail.cc_name_last')]/..//span[contains(@class,'error')]");
	private By cardNumberError = By
			.xpath("//input[contains(@name,'cardDetail.card')]/..//span[contains(@class,'error')]");
	private By cardExpiryError = By.xpath("//input[contains(@name,'exp')]/..//span[contains(@class,'error')]");
	private By address = By.xpath("//input[contains(@name,'cardDetail.cc_address')]");
	private By zipCode = By.xpath("//input[contains(@name,'cardDetail.cc_postal')]");
	private By zipCodeLabel = By.xpath("//input[contains(@name,'cardDetail.cc_postal')]/..//label");
	private By cityLabel = By.xpath("//input[contains(@name,'cardDetail.city')]/..//label");
	private By stateLabel = By.xpath("//input[contains(@name,'cardDetail.state')]/..//label");
	private By lastNameLabel = By.xpath("//input[contains(@name,'billingLastName')]/..//label");
	private By saveButton = By.xpath("//button[text()='SAVE'] | //button[text()='Save']");
	private By Qty = By.xpath(
			"//div[contains(@class,'invoice-perticular')]//table/tbody/tr/td/div[contains(@class,'invoice-quantity')]");
	private By termsHeader = By.xpath("//div[contains(@class,'termsHeader')]");
	// private By termsLink =
	// By.xpath("//div[@data-react-toolbox='check']/../..//a");
	private By tncCheckBox = By.xpath("//label[contains(@class, 'invoice_termsCondition')]");
	private By termsLink = By.xpath("//div[@data-react-toolbox='check']/../../p/a");
	private By confirmPay = By.xpath("//button[text()='CONFIRM'] | //button[text()='Confirm']");
	private By savedCardList = By.cssSelector("span[class*='invoice-bankCardLabelText']");

	private By activeCardImage = By.xpath("//div[contains(@class,'cardActive')]//span[contains(@class,'Image')]");
	private By activeCardNumber = By
			.xpath("//div[contains(@class,'cardActive')]//span[contains(@class,'Image')]/..//span[2]");
	private By activeCardCvv = By.xpath("//div[contains(@class,'cardActive')]//input");
	private By continueAddNewCard = By.xpath("//button[contains(@class,'invoice_addCardContinue')]");

	private By saveToAccount = By.xpath("(//div[@data-react-toolbox='check'])[1]");
	private By billingaddress = By.xpath("(//div[@data-react-toolbox='check'])[2]");
	private By paymentPlanOption = By.xpath(
			"(//div[contains(@class,'paymentPlanDropDown') ]//li[not(contains(text(),'Pay In Full') or contains(text(),'Pay Other'))])[2]");
	private By removeButton = By.xpath("(//button[text()='Remove' or text()='REMOVE'])");
	private By addButton = By.xpath("(//button[text()='Add' or text()='ADD'])[1]");
	private By reviewTitleDeclineMessage = By.xpath("//*[contains(@class,'reviewTitleError')]");

	private By sectionRowSeat = By.xpath("(//div[contains(@class,'secRowSeat')])[1]");
	private By invoiceName = By.xpath("//div[contains(@class,'eventName')]");
	private By invoicedAmt = By.cssSelector("td[class*='invoice-secRowSeat'][class*='right']");
	private By invoiceDetail = By.cssSelector("div.accordionSummary");
	private By selectedInvoice = By.xpath(
			".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li[contains(@class, 'invoice-activeItem')]");

	private By oopsErrorMessage = By.xpath("//p[contains(@class,'reviewTitleError') and contains(text(),'Oops')]");
	private By ErrorMessage = By.xpath(
			"//div[contains(@class,'invoice-successFailureStatus')]//div[contains(@class,'invoice-successFailureStatusHead')]//h4");
	private By reloadButton = By.xpath("//button[contains(text(),'Reload')]");

	private By summaryTitle = By.xpath("//*[contains(@class,'Summary')]//span[contains(@class,'header')]");
	private By paymentTitle = By.xpath("//*[contains(@class,'Payment')]//span[contains(@class,'header')]");
	private By reviewTitle = By.xpath("//*[contains(@class,'Review')]//span[contains(@class,'header')]");
	private By amountDueTitle = By.xpath("//div[contains(@class,'amountDue')]//div[1]");
	private By subtotalTitle = By.xpath("(//div[contains(@class,'amountTable-')]//div//div)[1]");
	private By fieldTitle = By.xpath("(//div[contains(@class,'amountTable-')]//div[2]//div)[1]");
	private By summaryTextSummary = By.xpath("//*[contains(@class,'renderHtmlWrapper')]");

	private By fieldTitlePayment = By.xpath("(//*[contains(@class,'amountTablePaymentOption')]//div//div)[3]");
	private By amountDueTitlePayment = By
			.xpath("//div[contains(@class,'payment')]//*[contains(@class,'amountDue')]//div[1]");
	private By payTodayPaymentTitle = By
			.xpath("//div[contains(@class,'payment')]//*[contains(@class,'payToday')]//div[1]");

	private By subtotalTitleReview = By.xpath(
			"(//div[contains(@class,'reviewTableContainer')]//div[contains(@class,'amount')]//div[1]//div//div)[1]");
	private By fieldTitlePaymentReview = By.xpath(
			"(//div[contains(@class,'reviewTableContainer')]//div[contains(@class,'amount')]//div[1]//div//div)[3]");
	private By amountDueTitlePaymentReview = By.xpath(
			"(//div[contains(@class,'reviewTableContainer')]//div[contains(@class,'amount')]//div[1]//div//div)[5]");
	private By payTodayPaymentTitleReview = By.xpath(
			"(//div[contains(@class,'reviewTableContainer')]//div[contains(@class,'amount')]//div[1]//div//div)[7]");
	private By SummaryLabelText = By.cssSelector("h2[class*='invoice-accordionTitle']");
	private By SummaryHeaderText = By.xpath("//span[contains(@class,'invoice-headerlabelText')]");
	private By PrintbuttonText = By.cssSelector("button[class*='invoice-printButton']");

	private By InvoicePlaceholder = By.cssSelector("div[class*=invoice-placeholder] div div div p");

	private By viewUpdatedInvoice = By.xpath("//button[contains(@class,'invoice_paymentSucessDialog')]");
	
	private By acceptTermsAndCondition = By.xpath("//p[contains(text(),'I Accept')]/preceding-sibling::*//input[@type='checkbox']/following-sibling::div");
	// ---------------------------------------Edit Payment
	// method----------------------
	public By editpaymentmethoddesktop = By.xpath(
			"//button[contains(@class,'invoice_paymentEditPayment')]//span[contains(@class,'invoice-isDesktop') and text()='Edit Payment Method']");
	public By editpaymentmethodmobile = By.xpath(
			"//button[contains(@class,'invoice_paymentEditPayment')]//span[contains(@class,'invoice-isMobile') and text()='Edit']");
	public By selectpaymentpopup = By.xpath("//div[contains(@class,'theme-dialog')]/section/h6");
	public By addnewcardbutton = By.xpath("//div[contains(@class,'invoice-paymentType')]");
	public By activeinvoice = By.xpath("//div[contains(@class,'invoice-cardInfo')]//p/span[2]");
	public By activecvv = By.xpath("//input[@type='password' and @value='']");
	public By cancelbutton = By.xpath("//button[contains(@class,'addCardCancel')]");

	// ---------------------------------------Upsells----------------------

	private By eventName = By.xpath("//div[contains(@class, 'nameOnly')]");
	private By eventDropDown = By.xpath("//div[contains(@class, 'eventNameDrop')]//div[contains(@class,'field')]");
	private By eventDropDownValues = By.xpath("//ul[contains(@class,'values')]//li//div[contains(@class,'eventName')]");
	private By eventSelectedAmount = By.xpath(
			"//div[contains(@class,'templateValue')]//div[contains(@class,'eventName')]//span[contains(@class,'mount')]//span");
	private By addUpsellButton = By.xpath("//button[contains(@class,'add')]");
	private By addOnAmountDue = By.xpath("//div[contains(@class,'addOnsTable')]//div[contains(@class,'amount')]//span");
	private By allAddOnRemoveButton = By.xpath("//button[text()='Remove']");
	// private By addOnContinue =
	// By.xpath("//div[contains(@class,'invoiceAddOns')]//button[text()='CONTINUE']");
	private By addOnAmountOnAddOnTab = By.xpath("//*[contains(@class,'AddOn')]//*[contains(@class,'selectedValue')]");
	private By addOnMore = By.xpath("//div[contains(@class,'invoiceAddOns')]//button[text()='More']");
	private By activeDropDownValues = By.xpath(
			"//div[contains(@class,'active')]//ul[contains(@class,'values')]//li//div[contains(@class,'eventName')]");
	private By priceCodeDisplayed = By
			.xpath("//div[contains(@class,'eventDescription')]//h3//div[contains(@class,'eventNameAndAmount')]");
	private By invoiceTitle = By
			.xpath("//div[contains(@class,'invoiceDetails')]//div[contains(@class,'invoiceSectionsTitle')]");
	private By timeLeftAddOn = By
			.xpath("//*[contains(@class,'accordionAddOns')]//*[contains(@class,'timeLeft')]//span");

	String addOnRemoveButton = "(//button[text()='Remove'])";
	String selectedAddOns = "(//*[contains(@class,'selectedAddOns')])";

	// --------------------------------------------------------------------

	String cvv;
	String cardNumFirst;
	String cardNamFirst;
	String cardTypFirst;
	String cardExpFirst;
	String cvvSecond;
	String cardNumSecond;
	String cardNamSecond;
	String cardTypSecond;
	String cardExpSecond;
	double amount = 0;
	List<WebElement> amountToCharge;
	String payToday;
	List<WebElement> allCardNum;
	String[] allCvvValue;
	List<WebElement> allCvvField;
	List<WebElement> allImage;
	List<WebElement> allCardNam;
	List<WebElement> allCardExp;
	List<WebElement> allValuesUpsellDropdown;
	String[] allCardType;
	String[] cardName;
	String[] cardExpire;
	String[] cardNum;
	String[] cardImage;
	double addOnAmount = 0;
	String summaryDueAmount;

	Utils utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	ManageticketsAPI manageticketsAPI = new ManageticketsAPI(driverFactory, Dictionary, Environment, Reporter, Assert,
			SoftAssert, sTestDetails);

	public int getUpdatedInvoiceNumber() {
		String invTitle = getText(invoiceTitle);
		String invoiceNumber = getText(
				By.xpath("//em[text()='" + invTitle + "']/..//span[contains(@class,'invoiceNumber')]"));
		return Integer.parseInt(invoiceNumber.substring(invoiceNumber.indexOf("#") + 1));
	}

	public void selectTnC() {
		click(tncCheckBox, "TnC");
	}

	public String getamountDue() {
		return getText(amountDueSummarySectionNew);
	}

	public boolean checkSuccessWindow() {
		boolean val = false;
		val = checkIfElementPresent(successWindowNew);
		return val;
	}

	public boolean checkUpdatedInvoiceButton() {
		boolean val = false;
		val = checkIfElementPresent(UpdatedInvoiceButton);
		return val;
	}

	public void clickUpdatedInvoice() {
		click(UpdatedInvoiceButton, "Updated Invoice");
	}

	public String getSuccessMessage() {
		return getText(successMessageNew);
	}

	public boolean IsInvoiceButtonDisplayed() {
		boolean val = false;
		val = checkIfElementPresent(PrintbuttonText);
		val = checkIfElementPresent(EmailbuttonText);
		return val;
	}

	public boolean IsInvoiceSummaryDisplayed() {
		boolean val = false;
		val = checkElementPresent(SummaryHeaderText, 20);
		return val;
	}

	public boolean IsInvoicePrintButtonDisplayed() {
		boolean val = false;
		val = checkElementPresent(PrintbuttonText, 30);
		return val;
	}

	public void clickInvoiceDetail() {
		click(InvoiceDetailsNew, "View Invoice Details");
	}

	public String getInvoicePlaceholder() {
		return getText(InvoicePlaceholder);
	}

	public String getTextpaymentMethod() {
		return getText(paymentMethod);
	}

	public boolean verifyNewCardSaved(String text1, String text2, String text3) {
		getElementWhenVisible(savedCardList);
		List<WebElement> savedcards = getWebElementsList(savedCardList);
		for (int i = 0; i < savedcards.size(); i++) {
			if (savedcards.get(i).getText().trim().equalsIgnoreCase(text1.trim())
					|| savedcards.get(i).getText().trim().equalsIgnoreCase(text2.trim())
					|| savedcards.get(i).getText().trim().equalsIgnoreCase(text3.trim())) {
				return true;
			}
		}
		return false;
	}

	// Updated

	public String getInvoiceSummaryDueAmt() {
		getElementWhenInVisible(continueButton);
		return getText(amountDueSummarySection).replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
	}

	public String getSummaryTextHeader() {
		return getText(SummaryLabelText);
	}

	public String getHeaderAmount() {
		if (driverType.trim().toUpperCase().contains("IE")) {
			scrollingToTopofAPage();
		}
		return getText(headerAmount).replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
	}

	public void loadInvoice(int invoice) {

		utils.navigateTo("/invoice#/" + invoice + "/1");
	}

	public void clickFirstNonPlanInvoiceLink() {
		click(firstUnpaidInvoiceLink, "First Without Plan Invoice");
	}

	public void clickFirstPlanInvoiceLink() {
		click(firstPlanInvoiceLink, "First Plan Invoice");
	}

	public String checkInvoicePresent() {
		boolean flag = false;
		flag = checkIfElementPresent(firstUnpaidInvoiceLink);

		if (flag == true) {

			return "NOPLAN";
		} else
			flag = checkIfElementPresent(firstPlanInvoiceLink);

		if (flag == true)
			return "PLAN";

		return "";
	}

	public String getSelectedInvoiceBal() {
		String selectedInvoiceXpath = getXpath(selectedInvoice, "Selected invoice", "", -1);
		WebElement selectedInvoiceBalance = getSingleChildObject(selectedInvoiceXpath, By.xpath("//ul/li[1]/span"), "",
				"Invoice balance");
		String selInvoiceBalance = selectedInvoiceBalance.getText()
				.replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
		return selInvoiceBalance;
	}

	public boolean isInvoiceDetailDisplayed(TestDevice device) {
		// sync(5000L);
		boolean status = false;
		int counter = 3;
		do {
			status = checkIfElementPresent(invoiceDetail);
			if (!status) {
				getDriver().navigate().refresh();
				sync(5000L);
			}
			counter--;
		} while (counter > 0 && status == false);
		getElementWhenVisible(invoiceDetail, 5);
		if ((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet")
				|| device.getName().trim().equalsIgnoreCase("mobile")))
				|| driverType.trim().toUpperCase().contains("ANDROID")
				|| driverType.trim().toUpperCase().contains("IOS")) {
			// Do Nothing
		} else {
			if (checkIfElementPresent(selectedInvoice)) {
				String selectedInvoiceXpath = getXpath(selectedInvoice, "Selected invoice", "", -1);
				// WebElement selectedInvoiceBalance =
				// getSingleChildObject(selectedInvoiceXpath,By.xpath("//ul/li[1]/span"), "",
				// "Invoice balance");
				WebElement selectedInvoiceBalance = getSingleChildObject(selectedInvoiceXpath,
						By.xpath("//div//div[2]//span"), "", "Invoice balance");

				String selInvoiceBalance = selectedInvoiceBalance.getText();
				System.out.println(selInvoiceBalance);
				try {
					getElementWhenRefreshed(amountDueSummarySectionNew, "innerHTML", selInvoiceBalance);
				} catch (Exception ex) {
					getDriver().navigate().refresh();
					sync(5000L);
					getElementWhenVisible(invoiceDetail, 40);
					getElementWhenRefreshed(amountDueSummarySectionNew, "innerHTML", selInvoiceBalance);
				}
			}
		}
		return true;
	}

	public double getBalanceDue() {
		System.out.println(Double.parseDouble(getText(activeInvoiceAmountDue).replaceAll("[^0-9.]", "")));
		Dictionary.put("DUE_BALANCE",
				String.valueOf(Double.parseDouble(getText(activeInvoiceAmountDue).replaceAll("[^0-9.]", ""))));
		return Double.parseDouble(getText(activeInvoiceAmountDue).replaceAll("[^0-9.]", ""));
	}

	public void clickTnCLink() throws Exception {
		scrollingToElementofAPage(termsLink);
		if (((driverType.trim().toUpperCase().contains("ANDROID")
				|| driverType.trim().toUpperCase().contains("IOS")))) {
			getDriver().navigate().to(Environment.get("APP_URL") + "/terms/");
		} else {
			click(termsLink, "Terms & Conditions", null,
					By.xpath(".//XCUIElementTypeStaticText[contains(@name,'Conditions')]"), "Terms & Conditions",
					false);
		}
	}

	public void switchWindowContextHandles() {
		Set<String> contextView = ((AppiumDriver<?>) getDriver()).getContextHandles();
		ArrayList<String> s = new ArrayList<String>(contextView);
		((AppiumDriver<?>) getDriver()).context(s.get(contextView.size() - 1));
		sync(5000L);
	}

	public void validateTnC() {
		if (((driverType.trim().toUpperCase().contains("ANDROID")
				|| driverType.trim().toUpperCase().contains("IOS")))) {
			switchWindowContextHandles();
		} else
			switchToWindow(1);

		Assert.assertTrue(getDriver().getCurrentUrl().contains("terms"), "Validated URL contains terms");
		Assert.assertTrue(getElementWhenPresent(termsHeader).isDisplayed(), "Terms of User header is displayed");
	}

	public int getInvoiceNumber() {
		String currentUrl = getDriver().getCurrentUrl();
		String invoiceNumber = currentUrl.substring(currentUrl.indexOf("#") + 2);
		invoiceNumber = invoiceNumber.substring(0, invoiceNumber.indexOf("/"));
		System.out.println(Integer.parseInt(invoiceNumber));
		return Integer.parseInt(invoiceNumber);
	}

	public void verifyMOP(String invoiceStatus) {
		// Assert.assertEquals(Integer.parseInt(Dictionary.get("TotalCards")),
		// cardExpire.length);
		for (int i = 0; i < Integer.parseInt(Dictionary.get("TotalCards")); i++) {
			System.out.println("Iterator" + i);
			System.out.println(cardNum[i].substring(cardNum[i].length() - 4));
			System.out.println(Dictionary.get(invoiceStatus + "CardNumber" + i));
			// Assert.assertEquals("x" + cardNum[i].substring(cardNum[i].length() - 4),
			// Dictionary.get(invoiceStatus + "CardNumber" + i));
			// Assert.assertEquals(cardExpire[i].replaceAll("[^0-9]", ""),
			// Dictionary.get(invoiceStatus + "CardExpiry" + i));
			// String testCardType = cardImage[i];
			// String testCardType2 ;

//			if (testCardType.contains("discover")) {
//				testCardType = "DI";
//			} else if (testCardType.contains("master")) {
//				testCardType = "MC";
//			} else if (testCardType.contains("visa")) {
//				testCardType = "VI";
//			} else if (testCardType.contains("amex")) {
//				testCardType = "AE";
//			}
			// System.out.println(Dictionary.get(invoiceStatus + "CardType" + i) +
			// Dictionary.get(invoiceStatus + "CardNumber" + i) +
			// Dictionary.get(invoiceStatus + "CardExpiry" + i));
			// Assert.assertEquals(testCardType, Dictionary.get(invoiceStatus + "CardType" +
			// i));
		}
	}

	public void clickUnpaidTab() {
		getElementWhenPresent(unPaidTab);
		click(unPaidTab, "Unpaid Tab");
	}

	public void verifyNoInvoiceMessageUnpaid() {
		getElementWhenPresent(invoiceMsgBox);
		Assert.assertTrue(getText(invoiceMsgBox).contains("no invoices"));
	}

	public void verifyNoInvoiceMessagePaid() {
		getElementWhenPresent(invoiceMsgBox);
		Assert.assertTrue(getText(invoiceMsgBox).contains("no paid"));
	}

	public void clickPaidTab() {
		getElementWhenPresent(paidTab);
		click(paidTab, "Paid Tab", 30);
		Assert.assertTrue(getAttribute(paidTab, "class").contains("active"), "Paid Tab is active");

	}

	public void clickRemoveButton() {
		getElementWhenPresent(removeButton);

		click(removeButton, "Remove Button");
	}

	public void addLinkDisplayed() {
		Assert.assertTrue(getElementWhenPresent(addButton).isDisplayed());
	}

	public void clickSummaryContinue() {
		sync(2000L);
		summaryDueAmount = getInvoiceSummaryDueAmt();
		getElementWhenClickable(continueButton);
		click(continueButton, "Summary Continue");
	}

	public void cvvDisplayed() {
		getElementWhenClickable(firstCardCVVSelectPaymentPopUp);
		allCvvField = getDriver().findElements(allCVVSelectPaymentPopUp);
		for (int i = 0; i < allCvvField.size(); i++) {
			Assert.assertTrue(allCvvField.get(i).isDisplayed(), "CVV field is displayed");
		}
	}

	public void amountDuePayTodayDisplayed() {
		Assert.assertTrue(getElementWhenVisible(amountDuePaymentSectionNew).isDisplayed(), "Due Amount displayed");
		Assert.assertTrue(getElementWhenPresent(payTodayAmountPaymentSectionNew).isDisplayed(), "Pay Today displayed");
	}

	public void amountDueDisplayed() {
		Assert.assertTrue(getElementWhenVisible(amountDuePaymentSectionNew).isDisplayed(), "Due Amount displayed");
	}

	public void selectPaymentMethodDisplayed() {
		// Assert.assertTrue(getElementWhenVisible(selectPaymentMethodPopUpTitle).isDisplayed(),
		// "Select Payment Method Title displayed");
		Assert.assertTrue(getElementWhenVisible(addNewPaymentButtonSelectPaymentPopUp).isDisplayed(),
				"Add New Payment button displayed");
		Assert.assertTrue(getElementWhenVisible(firstCard).isDisplayed(), "First added card displayed");
	}

	public void clickContinue() {
		click(continueButton, "Continue Summary");
	}

	public void clickSelectOrAddPaymentLink() {
		if (((driverType.trim().toUpperCase().contains("ANDROID")
				|| driverType.trim().toUpperCase().contains("IOS")))) {
			click(selectOrAddPaymentMethodIOS, "Click Here to Select or Add Payment Method");
		} else
			click(selectOrAddPaymentMethod, "Click Here to Select or Add Payment Method");
	}

	public void selectFirstExistingCard() {
		click(firstCard, "First Card");
		cardNumFirst = getText(firstCardNo);
		cardNamFirst = getText(firstCardName);
		cardExpFirst = getText(firstCardExp);
	}

	public void enterCVVFirstExistingCard() throws Exception {
		cardNumFirst = getText(firstCardNumberSelectPaymentPopUp);

		if (getAttribute(firstCardImage, "class").contains("discover")) {
			cardTypFirst = "discover";
		} else if (getAttribute(firstCardImage, "class").contains("master")) {
			cardTypFirst = "master";
		} else if (getAttribute(firstCardImage, "class").contains("visa")) {
			cardTypFirst = "visa";
		} else if (getAttribute(firstCardImage, "class").contains("amex")) {
			cardTypFirst = "amex";
		}

		if (cardTypFirst == "amex") {
			cvv = cardNumFirst.substring(cardNumFirst.length() - 4);
			System.out.println(cvv);
		} else {
			cvv = cardNumFirst.substring(cardNumFirst.length() - 3);
			System.out.println(cvv);
		}
		type(firstCardCVVSelectPaymentPopUp, "CVV Number", cvv);
	}

	public void enterWrongCVVFirstExistingCard() throws Exception {
		cardNumFirst = getText(firstCardNumberSelectPaymentPopUp);

		if (getAttribute(firstCardImage, "class").contains("discover")) {
			cardTypFirst = "discover";
		} else if (getAttribute(firstCardImage, "class").contains("master")) {
			cardTypFirst = "master";
		} else if (getAttribute(firstCardImage, "class").contains("visa")) {
			cardTypFirst = "visa";
		} else if (getAttribute(firstCardImage, "class").contains("amex")) {
			cardTypFirst = "amex";
		}

		if (cardTypFirst == "amex") {
			cvv = "9876";
			System.out.println(cvv);
		} else {
			cvv = "987";
			System.out.println(cvv);
		}
		type(firstCardCVVSelectPaymentPopUp, "CVV Number", cvv);
	}

	public void transactionDeclined() {
		Assert.assertTrue(getElementWhenPresent(reviewTitleDeclineMessage).isDisplayed(),
				"Transaction Declined message displayed");

	}

	public void selectFirstTwoExistingCard() {
		click(firstCard, "First Card");
		cardNumFirst = getText(firstCardNo);
		cardNamFirst = getText(firstCardName);
		cardExpFirst = getText(firstCardExp);

		click(secondCard, "Second Card");

		cardNumSecond = getText(secondCardNo);
		cardNamSecond = getText(secondCardName);
		cardExpSecond = getText(secondCardExp);

	}

	public void enterCVVFirstTwoExistingCard() throws Exception {
		cardNumFirst = getText(firstCardNumberSelectPaymentPopUp);
		cardNumSecond = getText(secondCardNumberSelectPaymentPopUp);

		if (getAttribute(firstCardImage, "class").contains("discover")) {
			cardTypFirst = "discover";
		} else if (getAttribute(firstCardImage, "class").contains("master")) {
			cardTypFirst = "master";
		} else if (getAttribute(firstCardImage, "class").contains("visa")) {
			cardTypFirst = "visa";
		} else if (getAttribute(firstCardImage, "class").contains("amex")) {
			cardTypFirst = "amex";
		}

		if (cardTypFirst == "amex") {
			cvv = cardNumFirst.substring(cardNumFirst.length() - 4);
			System.out.println(cvv);
		} else {
			cvv = cardNumFirst.substring(cardNumFirst.length() - 3);
			System.out.println(cvv);
		}
		if (getAttribute(secondCardImage, "class").contains("discover")) {
			cardTypSecond = "discover";
		} else if (getAttribute(secondCardImage, "class").contains("master")) {
			cardTypSecond = "master";
		} else if (getAttribute(secondCardImage, "class").contains("visa")) {
			cardTypSecond = "visa";
		} else if (getAttribute(secondCardImage, "class").contains("amex")) {
			cardTypSecond = "amex";
		}

		if (cardTypSecond == "amex") {
			cvvSecond = cardNumSecond.substring(cardNumSecond.length() - 4);
			System.out.println(cvvSecond);
		} else {
			cvvSecond = cardNumSecond.substring(cardNumSecond.length() - 3);
			System.out.println(cvvSecond);
		}

		type(firstCardCVVSelectPaymentPopUp, "CVV Number", cvv, true, By.xpath("//XCUIElementTypeSecureTextField[1]"));
		type(secondCardCVVSelectPaymentPopUp, "CVV Number", cvvSecond, true,
				By.xpath("(//XCUIElementTypeSecureTextField)[2]"));
	}

	public void enterCVVinConfirmPopUp() throws Exception {
		amountFieldDisplayedNew();
		allCvvField = getDriver().findElements(allCVVSelectPaymentPopUp);

		System.out.println("CVV IS AS :: "+Dictionary.get("CVV_CARD"));
		
		type(allCvvField.get(0), "CVV Number", Dictionary.get("CVV_CARD"), true,
					By.xpath("(//XCUIElementTypeSecureTextField)[" + 1 + "]"));

	}
	
	public void acceptTermsAndConditions() {
		click(acceptTermsAndCondition,"Accept Terms And Conditions");
	}

	public void clickContinuePopUp() {
		click(continueButtonPopUp, "Continue Button", 10);
	}

	public void clickConfirmButton() {
		click(confirmPay, "Confirm Button", 10);
	}

	public void amountforPaymentNew() {
		click(paymentMethodsDropDown, "Payment Methods Dropdown");
		WebElement firstCard = getDriver().findElement(allCardNumberPaymentSectionNew);
		click(firstCard, "");

		String cardText = getText(firstCardDetailNew);
		// String cardText =
		// getText(By.xpath("//div[contains(@class,'theme-dropdown')]//ul[contains(@class,'theme-values')]//li//div//span[contains(@class,'invoice-activeCardLabel')]//span"));
		String cardNo = cardText.substring(5, cardText.indexOf(","));
		// String cardExp = cardText.substring(cardText.lastIndexOf(".") +
		// 1,cardText.length());

		Dictionary.put("CVV_CARD", cardNo.substring(0, cardNo.length() - 1));
		Dictionary.put("CVV_CARD_AE", cardNo);

		if (((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
				&& Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			payToday = getText(payTodayAmountPaymentSectionNew);

		} else {
			amountToCharge = getDriver().findElements(amountInputPaymentSectionNew);
			for (int i = 0; i < amountToCharge.size(); i++) {
				System.out.println(
						Double.parseDouble(amountToCharge.get(i).getAttribute("value").replaceAll("[^0-9.]", "")));
				amount = amount
						+ Double.parseDouble(amountToCharge.get(i).getAttribute("value").replaceAll("[^0-9.]", ""));
				System.out.println("AMOUNT " + amount + "  ");
			}

			payToday = getText(payTodayAmountPaymentSectionNew);
			Assert.assertEquals(amount, Double.parseDouble(payToday.replaceAll("[^0-9.]", "")),
					"Pay Today in Payment Section and Amount displayed are equal");
		}

	}

	public void amountFieldDisplayedNew() {
		Assert.assertTrue(checkIfElementPresent(amountInputPaymentSectionNew), "Amount field is displayed");
		click(By.xpath(
				"//div[contains(@class,'invoice-cardList')]//div[contains(@class,'theme-dropdown')]//div[contains(@class,'invoice-cardInfo')]//span"),
				"");
		if (allCardExp == null || allCardType == null || allCardNum == null || allCardNam == null)
			allCardNum = getDriver().findElements(allCardNumberPaymentSectionNew);

		// allImage = getDriver().findElements(allCardImagePaymentSection);
		allCardNam = getDriver().findElements(allCardNamePaymentSectionNew);
		// allCardExp = getDriver().findElements(allCardExpPaymentSection);
		allCardType = new String[allCardNum.size()];
		// allCvvValue = new String[allCardNum.size()];
		cardName = new String[allCardNum.size()];
		cardExpire = new String[allCardNum.size()];
		cardNum = new String[allCardNum.size()];
		// cardImage = new String[allCardNum.size()];

		for (int i = 0; i < allCardNum.size(); i++) {
			// ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView(true);",
			// allCardNum.get(i));
			cardName[i] = getText(allCardNum.get(i));
			cardNum[i] = cardName[i].substring(5, cardName[i].indexOf(","));
			cardExpire[i] = cardName[i].substring(cardName[i].lastIndexOf(".") + 1, cardName[i].length());

//			if (allCardType[i] == "amex") {
//				allCvvValue[i] = getText(allCardNum.get(i)).substring(getText(allCardNum.get(i)).length() - 4);
//				// cvv = cardNum.substring(cardNum.length() - 4);
//				System.out.println(allCvvValue[i]);
//			} else {
//				allCvvValue[i] = getText(allCardNum.get(i)).substring(getText(allCardNum.get(i)).length() - 3);
//				// cvv = cardNum.substring(cardNum.length() - 3);
//				System.out.println(allCvvValue[i]);
//			}
			if (i == allCardNum.size() - 1) {
				click(allCardNum.get(i), "");
				Dictionary.put("CVV_CARD", cardNum[i].substring(1, cardNum[i].length()));
				Dictionary.put("CVV_CARD_AE", cardNum[i]);
			}
		}

		amount = 0;
		if (((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
				&& Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			payToday = getText(payTodayAmountPaymentSectionNew);

		} else {
			amountToCharge = getDriver().findElements(amountInputPaymentSectionNew);
			// System.out.println("hgvhg "+amountToCharge.size());
			// System.out.println("hgvhcvfdf g "+amountToCharge);
			// getAttribute(amountPaymentSection, "value");

			for (int i = 0; i < amountToCharge.size(); i++) {
				// System.out.println("hgffvhg "+i);
				// System.out.println(amount);
				// System.out.println("fbvvbb "+amountToCharge.get(i).getAttribute("value"));
				System.out.println(
						Double.parseDouble(amountToCharge.get(i).getAttribute("value").replaceAll("[^0-9.]", "")));
				amount = amount
						+ Double.parseDouble(amountToCharge.get(i).getAttribute("value").replaceAll("[^0-9.]", ""));
				System.out.println("AMOUNT " + amount + "  ");
			}

			payToday = getText(payTodayAmountPaymentSectionNew);
			SoftAssert.assertEquals(amount, Double.parseDouble(payToday.replaceAll("[^0-9.]", "")),
					"Pay Today in Payment Section and Amount displayed are equal");
		}

		// Assert.assertTrue(getElementWhenClickable(continuePlan).isDisplayed(),
		// "Continue button in Payment Option displayed");

	}

	public void amountFieldDisplayed() {
		Assert.assertTrue(getElementWhenClickable(amountInputPaymentSection).isDisplayed(),
				"Amount field is displayed");
		if (allCardExp == null || allCardType == null || allCardNum == null || allCardNam == null)
			allCardNum = getDriver().findElements(allCardNumberPaymentSection);

		// div[contains(@class,'cardInfo')]//span[contains(@class,'invoice-activeCard')]//span
		// div[contains(@class,'cardInfo')]//svg

		allImage = getDriver().findElements(allCardImagePaymentSection);
		allCardNam = getDriver().findElements(allCardNamePaymentSection);
		allCardExp = getDriver().findElements(allCardExpPaymentSection);
		allCardType = new String[allCardNum.size()];
		allCvvValue = new String[allCardNum.size()];
		cardName = new String[allCardNum.size()];
		cardExpire = new String[allCardNum.size()];
		cardNum = new String[allCardNum.size()];
		cardImage = new String[allCardNum.size()];

		for (int i = 0; i < allCardNum.size(); i++) {
			System.out.println("loop if");
			if (getAttribute(allImage.get(i), "class").contains("discover")) {
				allCardType[i] = "discover";
			} else if (getAttribute(allImage.get(i), "class").contains("master")) {
				allCardType[i] = "master";
			} else if (getAttribute(allImage.get(i), "class").contains("visa")) {
				allCardType[i] = "visa";
			} else if (getAttribute(allImage.get(i), "class").contains("amex")) {
				allCardType[i] = "amex";
			}
			cardImage[i] = getAttribute(allImage.get(i), "class");
			cardNum[i] = getText(allCardNum.get(i));
			cardName[i] = getText(allCardNam.get(i));
			cardExpire[i] = getText(allCardExp.get(i));
			if (allCardType[i] == "amex") {
				allCvvValue[i] = getText(allCardNum.get(i)).substring(getText(allCardNum.get(i)).length() - 4);
				// cvv = cardNum.substring(cardNum.length() - 4);
				System.out.println(allCvvValue[i]);
			} else {
				allCvvValue[i] = getText(allCardNum.get(i)).substring(getText(allCardNum.get(i)).length() - 3);
				// cvv = cardNum.substring(cardNum.length() - 3);
				System.out.println(allCvvValue[i]);
			}
		}
		if (((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
				&& Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			payToday = getText(payTodayAmountPaymentSection);

		} else {
			amountToCharge = getDriver().findElements(amountInputPaymentSection);
			System.out.println("hgvhg  " + amountToCharge.size());
			System.out.println("hgvhcvfdf  g  " + amountToCharge);
			// getAttribute(amountPaymentSection, "value");

			for (int i = 0; i < amountToCharge.size(); i++) {
				System.out.println("hgffvhg  " + i);
				System.out.println(amount);
				System.out.println("fbvvbb   " + amountToCharge.get(i).getAttribute("value"));
				System.out.println(
						Double.parseDouble(amountToCharge.get(i).getAttribute("value").replaceAll("[^0-9.]", "")));
				amount = amount
						+ Double.parseDouble(amountToCharge.get(i).getAttribute("value").replaceAll("[^0-9.]", ""));
				System.out.println("AMOUNT " + amount + "  ");
			}

			payToday = getText(payTodayAmountPaymentSection);
			Assert.assertEquals(amount, Double.parseDouble(payToday.replaceAll("[^0-9.]", "")),
					"Pay Today in Payment Section and Amount displayed are equal");
		}

		Assert.assertTrue(getElementWhenClickable(continuePlan).isDisplayed(),
				"Continue button in Payment Option displayed");

	}

	public void cardAddedAndContinueEnabled() {
		System.out.println("gefore");
		System.out.println("be before if");
		System.out.println(allCardExp);
		System.out.println(allCardType);
		System.out.println(allCardNum);
		System.out.println(allCardNam);
		if (allCardExp == null || allCardType == null || allCardNum == null || allCardNam == null)
			allCardNum = getDriver().findElements(allCardNumberPaymentSection);
		allImage = getDriver().findElements(allCardImagePaymentSection);
		allCardNam = getDriver().findElements(allCardNamePaymentSection);
		allCardExp = getDriver().findElements(allCardExpPaymentSection);
		allCardType = new String[allCardNum.size()];
		allCvvValue = new String[allCardNum.size()];
		cardName = new String[allCardNum.size()];
		cardExpire = new String[allCardNum.size()];
		cardNum = new String[allCardNum.size()];
		cardImage = new String[allCardNum.size()];
		// allCardExp =getDriver().findElements(allCardExp);
		System.out.println("in if");
		for (int i = 0; i < allCardNum.size(); i++) {
			System.out.println("loop if");

			cardImage[i] = getAttribute(allImage.get(i), "class");
			if (getAttribute(allImage.get(i), "class").contains("discover")) {
				allCardType[i] = "discover";
			} else if (getAttribute(allImage.get(i), "class").contains("master")) {
				allCardType[i] = "master";
			} else if (getAttribute(allImage.get(i), "class").contains("visa")) {
				allCardType[i] = "visa";
			} else if (getAttribute(allImage.get(i), "class").contains("amex")) {
				allCardType[i] = "amex";
			}
			cardNum[i] = getText(allCardNum.get(i));
			cardName[i] = getText(allCardNam.get(i));
			cardExpire[i] = getText(allCardExp.get(i));
			if (allCardType[i] == "amex") {
				allCvvValue[i] = getText(allCardNum.get(i)).substring(getText(allCardNum.get(i)).length() - 4);
				// cvv = cardNum.substring(cardNum.length() - 4);
				System.out.println(allCvvValue[i]);
			} else {
				allCvvValue[i] = getText(allCardNum.get(i)).substring(getText(allCardNum.get(i)).length() - 3);

				// cvv = cardNum.substring(cardNum.length() - 3);
				System.out.println(allCvvValue[i]);
			}

		}

		amount = Double.parseDouble(getText(amountDuePaymentSectionNew).replaceAll("[^0-9.]", ""));
		payToday = getText(amountDuePaymentSectionNew);
		System.out.println("after if" + amount);

		Assert.assertTrue(getElementWhenClickable(continuePlan).isDisplayed(),
				"Continue button in Payment Option displayed");

		// Assert.assertTrue(getAttribute(cardImagePaymentSection,
		// "class").contains(cardTyp),
		// "Card Type is same as selected in Payment Method");
		// Assert.assertEquals(getText(cardNumberPaymentSection), cardNum,
		// "Card Number is same as selected in Payment Method");
		// Assert.assertEquals(getText(cardNamePaymentSection), cardNam,
		// "Card Name is same as selected in Payment Method");

	}

	public void amountToPay(String amount) {
		try {
			type(amountInputPaymentSection, "Amount Field", amount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getContinuePaymentSection() {
		getElementWhenVisible(continuePlan);
	}

	public void clickContinuePaymentSection() {
		click(continuePlan, "Pay Now");
	}

	public void reviewSectionDisplayed() {
		Assert.assertTrue(getElementWhenVisible(confirmPay).isDisplayed(), "Confirm button is displayed");
		Assert.assertEquals(getText(payTodayAmountReviewSection), payToday,
				"Pay Today Amount in Payment Section and Pay Today Amount in Review Section are equal");
	}

	public void validateConfirmPopUp() {

		Assert.assertTrue(getElementWhenVisible(cardCVVConfirmPaymentPopUp).isDisplayed(),
				"Confirm pop up is displayed");
		Assert.assertTrue(getElementWhenPresent(continueButtonPopUp).isDisplayed(), "Continue button is displayed");

		for (int i = 0; i < allCardNum.size(); i++) {
			System.out.println("loop if");
			System.out.println(getAttribute(getDriver().findElements(allCardImageConfirmPop).get(i), "class") + "     "
					+ allCardType[i]);
			Assert.assertTrue(getAttribute(getDriver().findElements(allCardImageConfirmPop).get(i), "class")
					.contains(allCardType[i]), "Card Type is same as in Payment Method");
			Assert.assertEquals(getText(getDriver().findElements(allCardNameConfirmPop).get(i)), cardName[i],
					"Card Name is same as in Payment Method");
			Assert.assertEquals(getText(getDriver().findElements(allCardExpConfirmPop).get(i)), cardExpire[i],
					"Card Expiry is same as in Payment Method");

		}

		if (((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
				&& Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {

		} else {
			Assert.assertEquals(
					Double.parseDouble(getText(amountToChargeConfirmPaymentPopUp).replaceAll("[^0-9.]", "")), amount,
					"Amount to charge is same as selected in Payment Option section");
		}
	}

	public boolean isInvoiceSelected(int invoiceId, TestDevice device) {
		if ((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet")
				|| device.getName().trim().equalsIgnoreCase("mobile")))
				|| driverType.trim().toUpperCase().contains("ANDROID")
				|| driverType.trim().toUpperCase().contains("IOS")) {
			// Do Nothing
		} else {
			isInvoiceListDisplayed();
			// By invoice =
			// By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class,
			// 'react-listing')]//li//a[contains(@href, '" + invoiceId + "')]/..");
			// utils.getInvoiceWhenSelected(invoice, "class", "invoice-activeItem");
		}
		return true;
	}

	public boolean isInvoiceListDisplayed() {
		boolean status = false;
		int counter = 3;
		do {
			status = checkIfElementPresent(invoiceList);
			if (!status) {
				getDriver().navigate().refresh();
				sync(5000L);
			}
			counter--;
		} while (counter > 0 && status == false);
		getElementWhenVisible(invoiceList, 5);
		return getWebElementsList(invoiceList).size() > 0;
	}

	public void selectPayOther() {
		click(paymentOptionDropdown, "Payment Option Dropdown");
		javascriptClick(getElementWhenVisible(payOther), "Pay Other");
	}

	public void selectPaymentPlan() {
		click(paymentOptionDropdown, "Payment Option Dropdown");
		getElementWhenClickable(paymentPlanOption);
		javascriptClick(getElementWhenVisible(paymentPlanOption), "Pay Plan");
		sync(4000L);
	}

	public void selectPayFull() {
		click(paymentOptionDropdown, "Payment Option Dropdown");
		javascriptClick(getElementWhenVisible(payFull), "Pay In Full");
	}

	public void optionalLineItemDisplayed() {
		Assert.assertTrue(getElementWhenPresent(removeButton).isDisplayed(), "Verify remove button is displayed");
	}

	public void addRemoveButtonNotDisplayed() {
		Assert.assertFalse(checkIfElementPresent(removeButton), "Verify remove button is not displayed");
		Assert.assertFalse(checkIfElementPresent(addButton), "Verify add button is not displayed");
	}

	public double validateAmountUpdatedAndGetAmountDebitted(String previousAccountBalance) {
		if (((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
				&& Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			try {
				Assert.assertTrue(getElementWhenPresent(completed).isDisplayed(),
						"Completed Box displayed. Payment Successfull");
			} catch (Exception e) {
				Assert.assertTrue(getElementWhenPresent(oopsErrorMessage).isDisplayed(),
						"Oops Message displayed, reloading");
				click(getElementWhenClickable(reloadButton), "Reload Button");
			}
		} else {
			try {
				Assert.assertTrue(checkSuccessWindow(), "Successful Messsage Window is getting displayed");
				Assert.assertTrue(checkUpdatedInvoiceButton(), "Button should present");
				Assert.assertEquals(getSuccessMessage(), "Thank you for your payment!", "Payment Successful");
				clickUpdatedInvoice();

			} catch (Exception e) {
				Assert.assertTrue(getElementWhenPresent(oopsErrorMessage).isDisplayed(),
						"Oops Message displayed, reloading");
				click(getElementWhenClickable(reloadButton), "Reload Button");
			}

			System.out.println(Double.parseDouble(previousAccountBalance.replaceAll("[^0-9.]", "")) + " "
					+ (Double.parseDouble(previousAccountBalance.replaceAll("[^0-9.]", "")) - amount) + " " + amount);
			System.out.println(getHeaderAmountWhenRefreshed(
					Double.parseDouble(previousAccountBalance.replaceAll("[^0-9.]", "")) - amount));

		}
		return amount;
	}

	public void validateAmountUpdatedAndGetAmountDebittedNew() {
		if (((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
				&& Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			try {
				Assert.assertTrue(getElementWhenPresent(completed).isDisplayed(),
						"Completed Box displayed. Payment Successfull");
			} catch (Exception e) {
				Assert.assertTrue(getElementWhenPresent(oopsErrorMessage).isDisplayed(),
						"Oops Message displayed, reloading");
				click(getElementWhenClickable(reloadButton), "Reload Button");
			}
		} else {
			try {
				sync(5000L);

				Assert.assertTrue(checkSuccessWindow(), "Successful Messsage Window is getting displayed");
				Assert.assertTrue(checkUpdatedInvoiceButton(), "Button should present");
				Assert.assertEquals(getSuccessMessage(), "Thank you for your payment!", "Payment Successful");
				clickUpdatedInvoice();

			} catch (Exception e) {
				Assert.assertTrue(getElementWhenPresent(ErrorMessage).isDisplayed(), "Something went Wrong!");
				// click(getElementWhenClickable(reloadButton), "Reload Button");
			}

			// System.out.println(Double.parseDouble(payamount.replaceAll("[^0-9.]", "")) +
			// " "+ (Double.parseDouble(payToday.replaceAll("[^0-9.]", "")) - amount) + " "
			// + amount);
			// System.out.println(getHeaderAmountWhenRefreshed(Double.parseDouble(previousAccountBalance.replaceAll("[^0-9.]",
			// "")) - amount));

		}

	}

	public void verifySuccessMessage() {
		try {
			sync(5000L);

			Assert.assertTrue(checkSuccessWindow(), "Successful Messsage Window is getting displayed");
			Assert.assertTrue(checkUpdatedInvoiceButton(), "Button should present");
			Assert.assertEquals(getSuccessMessage(), "Thank you for your payment!", "Payment Successful");
			// clickUpdatedInvoice();

		} catch (Exception e) {
			Assert.assertTrue(getElementWhenPresent(ErrorMessage).isDisplayed(), "Something went Wrong!");
			// click(getElementWhenClickable(reloadButton), "Reload Button");
		}
	}

	public String getHeaderAmountWhenRefreshed(double Amount) {
		NumberFormat in = NumberFormat.getCurrencyInstance(Locale.US);
		double payment = Amount;
		String newamt = in.format(payment).replace("$", Environment.get("currency"));
		getElementWhenRefreshed(headerAmount, "innerHTML", newamt);
		return getText(headerAmount).replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
	}

	// fetching amount
	public void enterAmount(String amountGiven) {
		getElementWhenClickable(firstAmountInputPaymentSection);
		String payToday = getText(payTodayAmountPaymentSectionNew);
		String amountDue = getText(amountDuePaymentSectionNew);

		Double _payToday = Double.parseDouble(payToday.replaceAll("[^0-9.]", ""));
		Double _amountDue = Double.parseDouble(amountDue.replaceAll("[^0-9.]", ""));
		Double _amountGiven = Double.parseDouble(amountGiven.replaceAll("[^0-9.]", ""));
		if (_payToday > _amountGiven) {
			amountGiven = String.valueOf(_payToday);
		} else if (_amountDue < _amountGiven) {
			amountGiven = String.valueOf(_amountDue);
		} else {
			amountGiven = String.valueOf(_amountGiven);
		}
		// }
		amountToCharge = getDriver().findElements(amountInputPaymentSectionNew);

		try {	
			
			if (((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
					&& Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
				By iosAmount = By.xpath("//XCUIElementTypeOther[@name='main']/XCUIElementTypeOther[11]/XCUIElementTypeTextField");
				clear(amountToCharge.get(0));
				type(amountToCharge.get(0), "Enter Partial Amt", Environment.get("currency") + amountGiven, false, iosAmount);
			}else {
				clear(amountToCharge.get(0));
				getDriver().findElement((amountInputPaymentSectionNew)).sendKeys(amountGiven);
				sync(2000l);
			    String textamounttype=	getAttribute(amountInputPaymentSectionNew, "value", 2);
			    String acutal = textamounttype.trim().toString().replaceAll("\\s", "");
			    String expected = Environment.get("currency").trim().toString()+amountGiven.trim().toString();
			    Assert.assertEquals(acutal, expected, "Validate user is able to enter text - "+ amountGiven +" into amount editbox "); 
			}		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		amountToCharge = getDriver().findElements(amountInputPaymentSectionNew);

		amount = 0;
		for (int i = 0; i < amountToCharge.size(); i++) {
			amount = amount + Double.parseDouble(amountToCharge.get(i).getAttribute("value").replaceAll("[^0-9.]", ""));
			System.out.println("AMOUNTT " + amount);
		}
		this.payToday = getText(payTodayAmountPaymentSectionNew);
	}

	public void amountForAllCard() {
		getElementWhenClickable(firstAmountInputPaymentSection);

		amountToCharge = getDriver().findElements(amountInputPaymentSection);

		amount = 0;
		for (int i = 0; i < amountToCharge.size(); i++) {
			amount = amount + Double.parseDouble(amountToCharge.get(i).getAttribute("value").replaceAll("[^0-9.]", ""));
			System.out.println("AMOUNTT" + amount);
		}

		Assert.assertEquals(Double.parseDouble(getText(amountDuePaymentSectionNew).replaceAll("[^0-9.]", "")), amount,
				"Total amount divided in cards is same as Amount Due");
	}

	public void addNewCard(String saveCard, String gdCardFirstName, String gdCardLastName, String gdCardNum,
			String gdCardExpiry, String gdZipCode, String gdAddress) throws Exception {
		click(addNewPaymentButtonSelectPaymentPopUp, "Add New Payment");
		getElementWhenClickable(cardNameFirst);
		clear(getDriver().findElement(cardNameFirst));

		type(cardNameFirst, "First Name", gdCardFirstName, true, By.xpath("(//XCUIElementTypeTextField)[2]"));
		clear(getDriver().findElement(cardNameLast));
		type(cardNameLast, "Last Name", gdCardLastName, true, By.xpath("(//XCUIElementTypeTextField)[3]"));
		clear(getDriver().findElement(cardNumber));
		type(cardNumber, "Card Number", gdCardNum, true, By.xpath("(//XCUIElementTypeSecureTextField)[1]"));
		// clear(getDriver().findElement(cardExpiry));
		// type(cardExpiry, "Card Expiry", gdCardExpiry, true,
		// By.xpath("(//XCUIElementTypeTextField)[4]"));
		// type(, "postal code", "122001", true);

		if (saveCard.equalsIgnoreCase("YES")) {
			click(saveToAccount, "Save To Account");
			System.out.println("card saved to account");

		} else
			System.out.println("Card NOT saved to Account");

		click(continueAddNewCard, "Continue");
		getElementWhenClickable(Postalcode);

		type(Postalcode, "Zip Code", gdZipCode, true, By.xpath("(//XCUIElementTypeTextField)[8]"));
		// type(address, "Address ", gdAddress, true,
		// By.xpath("(//XCUIElementTypeTextField)[4]"));
		// (saveButton, "Save");
		click(cancelbutton, "Cancel");
		if (saveCard.equalsIgnoreCase("YES")) {
			sync(4000L);
		}
	}

	public void addNewCardNegative(String saveCard, String gdCardFirstName, String gdCardLastName, String gdCardNum,
			String gdCardExpiry) throws Exception {
		click(addnewcardbutton, "Add New Payment");
		getElementWhenClickable(cardNameFirst);
		clear(getDriver().findElement(cardNameFirst));

		type(cardNameFirst, "First Name", gdCardFirstName, true, By.xpath("(//XCUIElementTypeTextField)[2]"));
		clear(getDriver().findElement(cardNameLast));
		type(cardNameLast, "Last Name", gdCardLastName, true, By.xpath("(//XCUIElementTypeTextField)[3]"));
		clear(getDriver().findElement(cardNumber));
		type(cardNumber, "Card Number", gdCardNum, true, By.xpath("(//XCUIElementTypeSecureTextField)[1]"));
		clear(getDriver().findElement(cardExpiry));
		type(cardExpiry, "Card Expiry", gdCardExpiry, true, By.xpath("(//XCUIElementTypeTextField)[4]"));

		click(billingaddress, "Billing Address", 2);
		Assert.assertTrue(getElementWhenPresent(cardNameFirstError).isDisplayed(),
				"Error message displayed for invalid card first name");
		Assert.assertTrue(getElementWhenPresent(cardNameLastError).isDisplayed(),
				"Error message displayed for invalid card last name");
		Assert.assertTrue(getElementWhenPresent(cardNumberError).isDisplayed(),
				"Error message displayed for invalid card number");
		Assert.assertTrue(getElementWhenPresent(cardExpiryError).isDisplayed(),
				"Error message displayed for invalid expiry");

		Assert.assertEquals(getAttribute(continueAddNewCard, "disabled"), "true", "verify Continue Button disabled");

	}

	public void addWrongNewCard() throws Exception {
		click(addNewPaymentButtonSelectPaymentPopUp, "Add New Payment");
		getElementWhenClickable(cardNameFirst);
		clear(getDriver().findElement(cardNameFirst));

		type(cardNameFirst, "First Name", "Invalid 123", true, By.xpath("(//XCUIElementTypeTextField)[2]"));
		clear(getDriver().findElement(cardNameLast));
		type(cardNameLast, "Last Name", "Invalid 456", true, By.xpath("(//XCUIElementTypeTextField)[3]"));
		clear(getDriver().findElement(cardNumber));
		type(cardNumber, "Card Number", "1234567890098765", true, By.xpath("(//XCUIElementTypeSecureTextField)[1]"));
		clear(getDriver().findElement(cardExpiry));
		type(cardExpiry, "Card Expiry", "1111", true, By.xpath("(//XCUIElementTypeTextField)[4]"));

		click(saveToAccount, "Save To Account");

		Assert.assertTrue(getElementWhenPresent(cardNameFirstError).isDisplayed(),
				"Error message displayed for invalid card first name");
		Assert.assertTrue(getElementWhenPresent(cardNameLastError).isDisplayed(),
				"Error message displayed for invalid card last name");
		Assert.assertTrue(getElementWhenPresent(cardNumberError).isDisplayed(),
				"Error message displayed for invalid card number");
		Assert.assertTrue(getElementWhenPresent(cardExpiryError).isDisplayed(),
				"Error message displayed for invalid expiry");

		Assert.assertEquals(getAttribute(continueAddNewCard, "disabled"), "true", "verify Continue Button disabled");

	}

	public void enterCVVForAddedCard() throws Exception {
		List<WebElement> cardImage = getDriver().findElements(activeCardImage);
		List<WebElement> cardNumber = getDriver().findElements(activeCardNumber);
		List<WebElement> cardCvv = getDriver().findElements(activeCardCvv);
		String[] activeCardType = new String[cardNumber.size()];
		String[] activeCardCvv = new String[cardNumber.size()];

		for (int i = 0; i < cardImage.size(); i++) {
			System.out.println("loop if");
			if (getAttribute(cardImage.get(i), "class").contains("discover")) {
				activeCardType[i] = "discover";
			} else if (getAttribute(cardImage.get(i), "class").contains("master")) {
				activeCardType[i] = "master";
			} else if (getAttribute(cardImage.get(i), "class").contains("visa")) {
				activeCardType[i] = "visa";
			} else if (getAttribute(cardImage.get(i), "class").contains("amex")) {
				activeCardType[i] = "amex";
			}

			if (activeCardType[i] == "amex") {
				activeCardCvv[i] = getText(cardNumber.get(i)).substring(getText(cardNumber.get(i)).length() - 4);
				// cvv = cardNum.substring(cardNum.length() - 4);
				System.out.println(activeCardCvv[i]);
			} else {
				activeCardCvv[i] = getText(cardNumber.get(i)).substring(getText(cardNumber.get(i)).length() - 3);

				// cvv = cardNum.substring(cardNum.length() - 3);
				System.out.println(activeCardCvv[i]);
			}

			type(cardCvv.get(i), "CVV Number", activeCardCvv[i], true,
					By.xpath("(//XCUIElementTypeSecureTextField)[" + (i + 1) + "]"));
		}

	}

	public String getInvoiceQty() {
		return getText(Qty).replaceAll("[^0-9]", "");
	}

	public String getSection() {
		String sec = getElementWhenVisible(sectionRowSeat).getText();
		String str1 = sec.trim().split("\\|")[0].trim().substring(sec.trim().split("\\|")[0].trim().indexOf(" ") + 1)
				.trim();
		System.out.println(sec);
		System.out.println(str1);
		return str1;
	}

	public String getRow() {
		String sec = getElementWhenVisible(sectionRowSeat).getText();
		String str1 = sec.trim().split("\\|")[1].trim().substring(sec.trim().split("\\|")[1].trim().indexOf(" ") + 1)
				.trim();
		System.out.println(sec);
		System.out.println(str1);
		return str1;
	}

	public String getFirstSeat() {
		String sec = getElementWhenVisible(sectionRowSeat).getText();
		String str1 = sec.trim().split("\\|")[2].trim().substring(sec.trim().split("\\|")[2].trim().indexOf(" ") + 1)
				.trim();
		if (Integer.parseInt(getInvoiceQty()) > 1) {
			System.out.println(sec);
			System.out.println(str1);
			return str1.split("-")[0].trim();
		} else
			System.out.println(sec);
		System.out.println(str1);
		return str1;
	}

	public String getLastSeat() {
		if (Integer.parseInt(getInvoiceQty()) > 1) {
			String sec = getElementWhenVisible(sectionRowSeat).getText();
			String str1 = sec.trim().split("\\|")[2].trim()
					.substring(sec.trim().split("\\|")[2].trim().indexOf(" ") + 1).trim();
			System.out.println(sec);
			System.out.println(str1.split("-")[1].trim());
			return str1.split("-")[1].trim();
		} else {

			return getFirstSeat();
		}
	}

	public String getInvoiceName() {
		return getElementWhenVisible(invoiceName).getText();
	}

	public String getInvoicedAmt() {
		String invAmt = getText(invoicedAmt).replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
		System.out.println(invAmt);
		return invAmt;
	}

	public void getCCQuery(String cookies) throws Exception {
		Object[] obj = utils.get(Environment.get("APP_URL") + "/api/v1/members/creditcards?_format=json",
				new String[] { "accept", "accept-encoding", "accept-language", "user-agent", "cookie", "content-type",
						"content-encoding" },
				new String[] { "application/json, text/plain, */*", " ", "en-GB,en-US;q=0.9,en;q=0.8",
						"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36",
						cookies, "application/json", "gzip" });
		InputStream is = (InputStream) obj[0];
		JSONArray jsonArray = utils.convertToJSONArray(new BufferedReader(new InputStreamReader(is, "UTF-8")));

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject creditcard = jsonArray.getJSONObject(i);
			String dataMask = creditcard.getString("data_mask");
			Dictionary.put(dataMask + "_firstname", creditcard.getString("cc_name_first"));
			Dictionary.put(dataMask + "_lastname", creditcard.getString("cc_name_last"));
			Dictionary.put(dataMask + "_internal_pid_id", creditcard.getString("internal_pid_id"));
			Dictionary.put(dataMask + "_address", creditcard.getString("cc_address"));
			Dictionary.put(dataMask + "_postal_code", creditcard.getString("cc_postal_code"));
			Dictionary.put(dataMask + "_cc_card_uid", creditcard.getString("cc_card_uid"));

		}
	}

	public void getCCQuery(String cookies, String invoicestatus) throws Exception {
		Object[] obj = utils.get(Environment.get("APP_URL") + "/api/v1/members/creditcards?_format=json",
				new String[] { "accept", "accept-encoding", "accept-language", "user-agent", "cookie", "content-type",
						"content-encoding" },
				new String[] { "application/json, text/plain, */*", " ", "en-GB,en-US;q=0.9,en;q=0.8",
						"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36",
						cookies, "application/json", "gzip" });

		InputStream is = (InputStream) obj[0];
		JSONArray jsonArray = utils.convertToJSONArray(new BufferedReader(new InputStreamReader(is, "UTF-8")));

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject creditcard = jsonArray.getJSONObject(i);
			String dataMask = creditcard.getString("data_mask");
			Dictionary.put(invoicestatus + "datamask", dataMask);
			Dictionary.put(dataMask + "_firstname", creditcard.getString("cc_name_first"));
			Dictionary.put(dataMask + "_lastname", creditcard.getString("cc_name_last"));
			Dictionary.put(dataMask + "_internal_pid_id", creditcard.getString("internal_pid_id"));
			Dictionary.put(dataMask + "_address", creditcard.getString("cc_address"));
			Dictionary.put(dataMask + "_postal_code", creditcard.getString("cc_postal_code"));
			Dictionary.put(dataMask + "_cc_card_uid", creditcard.getString("cc_card_uid"));

		}
	}

	public void paymentsPlanInvoiceAPI(int invoiceid, String cookies, String token, String planid, String accountId,
			String invoicestatus, String[] invoice_conf_ids) throws Exception {
		String pre = "{\"acct_id\":\"" + accountId + "\",\"invoice_conf_id\":[\"1\", \"2\"],\"credit_card\":";
		String after = ",\"payment_plan_id\":\"" + planid + "\"}";

		Double _amountGiven = 0.1;
		Double _payToday = Double.parseDouble(Dictionary.get("amount_due_today"));
		Double _amountDue = Double.parseDouble(Dictionary.get(invoicestatus.trim().toUpperCase() + "balances"));
		String amountGiven;

		if (_payToday > _amountGiven) {
			amountGiven = String.valueOf(_payToday);
		} else if (_amountDue < _amountGiven) {
			amountGiven = String.valueOf(_amountDue);
		} else {
			amountGiven = String.valueOf(_amountGiven);
		}

		JSONArray ja = new JSONArray();
		int i = 0;
		JSONObject jo = new JSONObject();
		String cin;
		// System.out.println("sfsd " +Dictionary.get(invoicestatus + "CardNumber" +
		// i));
		if (Dictionary.get(invoicestatus + "CardType" + i).equalsIgnoreCase("AE")) {
			cin = Dictionary.get(invoicestatus + "CardNumber" + i)
					.substring(Dictionary.get(invoicestatus + "CardNumber" + i).length() - 4);
		} else
			cin = Dictionary.get(invoicestatus + "CardNumber" + i)
					.substring(Dictionary.get(invoicestatus + "CardNumber" + i).length() - 3);
		System.out.println(cin);
		jo.put("amount", amountGiven);
		jo.put("cin", cin);
		String datamask = Dictionary.get(invoicestatus + "CardNumber" + i);
		jo.put("first_name", Dictionary.get(datamask + "_firstname"));
		jo.put("last_name", Dictionary.get(datamask + "_lastname"));
		jo.put("Avs_address", Dictionary.get(datamask + "_address"));
		jo.put("Avs_postal_code", Dictionary.get(datamask + "_postal_code"));
		jo.put("pmt_type", Dictionary.get(invoicestatus + "CardType" + i));
		jo.put("data_mask", datamask);
		jo.put("exp_date", Dictionary.get(invoicestatus + "CardExpiry" + i));
		jo.put("internal_pid_id", Dictionary.get(datamask + "_internal_pid_id"));
		jo.put("cc_card_uid", Dictionary.get(datamask + "_cc_card_uid"));
		jo.put("display_on_inet", "Y");
		ja.put(jo);
		System.out.println("AND THE ARRAY IS" + ja);
		String payload = pre + ja.toString() + after;
		AdminLogin adminLogin = new AdminLogin(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert,
				sTestDetails);
		payload = adminLogin.update(new JSONObject(payload), "invoice_conf_id", invoice_conf_ids).toString();
		System.out.println("AND THE PAY LOAD IS " + payload);
		InputStream is = utils.post(Environment.get("APP_URL") + "/api/invoice/" + invoiceid + "/payment?_format=json",
				payload,
				new String[] { "accept", "accept-encoding", "accept-language", "user-agent", "cookie", "content-type",
						"x-csrf-token" },
				new String[] { "application/json, text/plain, */*", " ", "en-GB,en-US;q=0.8,en;q=0.6",
						"Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1",
						cookies, "application/json", token });
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		System.out.println(jsonObject.getJSONObject("data"));
		String message = jsonObject.getJSONObject("data").getString("errorHeading");
		System.out.println(message);
		Assert.assertTrue(message.contains("Thank"), "Verify payment successful");
	}

	public void paymentsInvoiceAPI(int invoiceid, String cookies, String token, String accountId, String invoicestatus)
			throws Exception {
		String cin;

		String datamask = Dictionary.get(invoicestatus + "datamask");
		if (Dictionary.get(invoicestatus + "cc_type").equalsIgnoreCase("AE")) {
			cin = datamask.substring(datamask.length() - 4);
		} else
			cin = datamask.substring(datamask.length() - 3);

		System.out.println(cin);

		String payload = "{\"acct_id\":\"" + accountId
				+ "\",\"invoice_conf_id\":[\"1\"],\"credit_card\":[{\"amount\":\"0.1\",\"cin\":\"" + cin
				+ "\",\"first_name\":\"" + Dictionary.get(datamask + "_firstname") + "\",\"last_name\":\""
				+ Dictionary.get(datamask + "_lastname") + "\",\"Avs_address\":\""
				+ Dictionary.get(datamask + "_address") + "\",\"Avs_postal_code\":\""
				+ Dictionary.get(datamask + "_postal_code") + "\",\"pmt_type\":\""
				+ Dictionary.get(invoicestatus + "cc_type") + "\",\"data_mask\":\"" + datamask + "\",\"exp_date\":\""
				+ Dictionary.get(invoicestatus + "cc_exp") + "\",\"internal_pid_id\":\""
				+ Dictionary.get(datamask + "_internal_pid_id") + "\",\"cc_card_uid\":\""
				+ Dictionary.get(datamask + "_cc_card_uid") + "\",\"display_on_inet\":\"Y\"}]}";

		InputStream is = utils.post(Environment.get("APP_URL") + "/api/invoice/" + invoiceid + "/payment?_format=json",
				payload,
				new String[] { "accept", "accept-encoding", "accept-language", "user-agent", "cookie", "content-type",
						"x-csrf-token" },
				new String[] { "application/json, text/plain, */*", " ", "en-GB,en-US;q=0.8,en;q=0.6",
						"Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1",
						cookies, "application/json", token });
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		System.out.println(jsonObject.getJSONObject("data"));
		String message = jsonObject.getJSONObject("data").getString("errorHeading");
		System.out.println(message);
		Assert.assertTrue(message.contains("Thank you"), "Verify payment successful");
	}

	public boolean verifyAllCardDetails(String ccResponse) {

		allCardNum = getDriver().findElements(allCardNumberConfirmPop);
		allCardExp = getDriver().findElements(allCardExpConfirmPop);
		cardExpire = new String[allCardNum.size()];
		cardNum = new String[allCardNum.size()];
		boolean pass = false;
		int count = 0;
		for (int i = 0; i < allCardNum.size(); i++) {
			cardNum[i] = getText(allCardNum.get(i)).substring(getText(allCardNum.get(i)).length() - 4);
			cardExpire[i] = getText(allCardExp.get(i)).replaceAll("/", "");

			if (ccResponse.contains(cardNum[i]) && ccResponse.contains(cardExpire[i])) {
				count++;
				System.out.println(count);
			}

		}
		System.out.println("FINAL COUNT" + count);
		System.out.println(allCardNum.size());
		if (count == allCardNum.size())
			pass = true;
		return pass;
	}

	public void clickAddOnContinue() {
		getElementWhenClickable(addOnContinue);
		click(addOnContinue, "Continue Button in Add On Section");
	}

	public void verifyUpsellDropdownAndSelectAValue() {
		int count = 0;
		if (checkIfElementPresent(addOnMore)) {
			click(addOnMore, "More");
		}

		for (int j = 0; j < Integer.parseInt(Dictionary.get("TotalUpsell")); j++) {
			if (Dictionary.get("TotalPriceCodes" + j).equalsIgnoreCase("1")) {

				NumberFormat in = NumberFormat.getCurrencyInstance(Locale.US);
				double payment = Double.valueOf(Dictionary.get("Price" + j));
				String newamt = in.format(payment).replace("$", Environment.get("currency"));
				Assert.assertEquals(getText(getDriver().findElements(priceCodeDisplayed).get(j)),
						Dictionary.get("UpsellEventName" + j) + newamt);

			} else {
				click(getDriver().findElements(eventDropDown).get(count), "Event Dropdown");
				count++;
				allValuesUpsellDropdown = getDriver().findElements(activeDropDownValues);
				System.out.println(allValuesUpsellDropdown.size());
				System.out.println(Integer.parseInt(Dictionary.get("TotalPriceCodes" + j)));
				Assert.assertTrue(
						allValuesUpsellDropdown.size() == Integer.parseInt(Dictionary.get("TotalPriceCodes" + j)));
				NumberFormat in = NumberFormat.getCurrencyInstance(Locale.US);
				for (int i = 0; i < allValuesUpsellDropdown.size(); i++) {
					double payment = Double.valueOf(Dictionary.get("Price" + j + i));
					String newamt = in.format(payment).replace("$", Environment.get("currency"));
					Assert.assertEquals(getText(allValuesUpsellDropdown.get(i)),
							Dictionary.get("Description" + j + i) + newamt);
				}
				click(allValuesUpsellDropdown.get(0), "Select First Value");

			}
		}
	}

	public void selectAndAddValues() {
		int count = 0;
		addOnAmount = 0;
		for (int i = 0; i < 2; i++) {
			if (Dictionary.get("TotalPriceCodes" + i).equalsIgnoreCase("1")) {
				click(getDriver().findElements(addUpsellButton).get(i), "Add Button " + i);
				getElementWhenPresent(By.xpath(addOnRemoveButton + "[" + (i + 1) + "]"));
				addOnAmount = addOnAmount + Double.valueOf(Dictionary.get("Price" + i));
			} else {
				click(getDriver().findElements(eventDropDown).get(0), "Event Dropdown " + i);
				allValuesUpsellDropdown = getDriver().findElements(activeDropDownValues);
				click(allValuesUpsellDropdown.get(count), "First Value in Dropdown");
				count++;
				click(getDriver().findElements(addUpsellButton).get(i), "Add Button");
				getElementWhenPresent(By.xpath(addOnRemoveButton + "[" + (i + 1) + "]"));
				addOnAmount = addOnAmount + Double.valueOf(Dictionary.get("Price" + i + count));
			}
		}
		Assert.assertEquals(Double.parseDouble(getText(addOnAmountDue).replaceAll("[^0-9.]", "")), addOnAmount);
		System.out.println();
	}

	public void verifyAddOnAmountDisplayedOnTab() {
		Assert.assertEquals(Double.parseDouble(getText(addOnAmountOnAddOnTab).replaceAll("[^0-9.]", "")), addOnAmount);
	}

	public void verifyAmountDueUpdatedWithAddOnAmount(Double scAmount) {
		System.out.println(scAmount);
		System.out.println(Double.parseDouble(getText(amountDueInvoicePaymentSection).replaceAll("[^0-9.]", "")));
		System.out.println(addOnAmount);
		System.out.println(Double.parseDouble(summaryDueAmount.replaceAll("[^0-9.]", "")));
		Double addOnScAmount = addOnAmount + scAmount;
		Dictionary.put("AddOnSCAmount", "" + addOnScAmount + "");
		Assert.assertEquals(Double.parseDouble(getText(amountDueInvoicePaymentSection).replaceAll("[^0-9.]", "")),
				addOnAmount + scAmount + Double.parseDouble(summaryDueAmount.replaceAll("[^0-9.]", "")));
	}

	public void addOnEventDisplayedInInvoiceSummary() {
		Assert.assertTrue(checkIfElementPresent(
				getElementWhenPresent(By.xpath("(//div[text()='" + Dictionary.get("UpsellEventName0") + "'])[1]"))));
	}

	public void addFirstEvent() {
		if (Dictionary.get("TotalPriceCodes" + "0").equalsIgnoreCase("1")) {
			click(getDriver().findElements(addUpsellButton).get(0), "Add Button");
		} else {
			click(eventDropDown, "Event Dropdown");
			click(getDriver().findElements(eventDropDownValues).get(0), "Select " + (1) + " Value");
			click(getDriver().findElements(addUpsellButton).get(0), "Add Button");
		}
	}

	public int drupal(String cookies) throws Exception {

		Object[] obj = utils.get(Environment.get("APP_URL") + "/api/invoice/add-ons/5256?_format=json",
				new String[] { "accept", "accept-encoding", "accept-language", "user-agent", "cookie" },
				new String[] { "application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6",
						"Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1",
						cookies });
		InputStream is = (InputStream) obj[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		System.out.println(jsonObject);
		int orderExpire = Integer
				.parseInt(jsonObject.toString().substring(jsonObject.toString().indexOf("order_expire\":") + 14,
						jsonObject.toString().indexOf("order_expire\":") + 17));

		return orderExpire;
	}

	public void verifyTimer(String timer) throws ParseException {
		Date d = new SimpleDateFormat("mm:ss").parse(getText(timeLeftAddOn));
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(d);
		calendar1.add(Calendar.DATE, 1);

		Date d2 = new SimpleDateFormat("mm").parse(timer);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(d2);
		calendar2.add(Calendar.DATE, 1);
		System.out.println(calendar2.getTime());

		Date d3 = new SimpleDateFormat("mm").parse(String.valueOf(Integer.parseInt(timer) - 1));
		Calendar calendar3 = Calendar.getInstance();
		calendar3.setTime(d3);
		calendar3.add(Calendar.DATE, 1);
		System.out.println(calendar3.getTime());

		Date x = calendar1.getTime();

		Assert.assertTrue(x.after(calendar3.getTime()) && x.before(calendar2.getTime()),
				"Timer on UI verified from drupal");
	}

	public void verifySummaryTab(String summaryText, String summaryInSummary, String subtotal, String field,
			String amountDue) {
		Assert.assertTrue(getText(summaryTitle).contains(summaryText));
		Assert.assertTrue(getText(summaryTextSummary).contains(summaryInSummary));
		System.out.println(getText(subtotalTitle));
		System.out.println(subtotal);
		Assert.assertTrue(getText(subtotalTitle).contains(subtotal.toUpperCase()));
		Assert.assertTrue(getText(fieldTitle).contains(field.toUpperCase()));
		Assert.assertTrue(getText(amountDueTitle).contains(amountDue.toUpperCase()));
	}

	public void verifyPaymentTab(String paymentTitleText, String fieldText, String amountDue, String payToday) {
		Assert.assertTrue(getText(paymentTitle).contains(paymentTitleText));
		Assert.assertTrue(getText(fieldTitlePayment).contains(fieldText.toUpperCase()));
		Assert.assertTrue(getText(amountDueTitlePayment).contains(amountDue.toUpperCase()));
		Assert.assertTrue(getText(payTodayPaymentTitle).contains(payToday.toUpperCase()));
	}

	public void addNewCardAndVerifyLabelsCMS(String saveCard, String gdCardFirstName, String gdCardLastName,
			String gdCardNum, String gdCardExpiry, String gdZipCode, String gdAddress, String zipLabelText,
			String stateLabelText, String cityLabelText, String lastNameLabelText) throws Exception {
		click(addNewPaymentButtonSelectPaymentPopUp, "Add New Payment");
		getElementWhenClickable(cardNameFirst);
		clear(getDriver().findElement(cardNameFirst));

		type(cardNameFirst, "First Name", gdCardFirstName, true, By.xpath("(//XCUIElementTypeTextField)[2]"));
		clear(getDriver().findElement(cardNameLast));
		type(cardNameLast, "Last Name", gdCardLastName, true, By.xpath("(//XCUIElementTypeTextField)[3]"));
		clear(getDriver().findElement(cardNumber));
		type(cardNumber, "Card Number", gdCardNum, true, By.xpath("(//XCUIElementTypeSecureTextField)[1]"));
		clear(getDriver().findElement(cardExpiry));
		type(cardExpiry, "Card Expiry", gdCardExpiry, true, By.xpath("(//XCUIElementTypeTextField)[4]"));

		if (saveCard.equalsIgnoreCase("YES")) {
			click(saveToAccount, "Save To Account");
			System.out.println("card saved to account");

		} else
			System.out.println("Card NOT saved to Account");

		click(continueAddNewCard, "Continue");
		getElementWhenClickable(zipCode);

		Assert.assertTrue(getText(zipCodeLabel).contains(zipLabelText));
		Assert.assertTrue(getText(cityLabel).contains(cityLabelText));
		Assert.assertTrue(getText(stateLabel).contains(stateLabelText));
		Assert.assertTrue(getText(lastNameLabel).contains(lastNameLabelText));

		type(zipCode, "Zip Code", gdZipCode, true, By.xpath("(//XCUIElementTypeTextField)[8]"));
		type(address, "Address ", gdAddress, true, By.xpath("(//XCUIElementTypeTextField)[4]"));
		click(saveButton, "Save");
		if (saveCard.equalsIgnoreCase("YES")) {
			sync(4000L);
		}
	}

	public void verifyReviewTab(String reviewTitleText, String subtotalText, String fieldText, String amountDue,
			String payToday) {
		Assert.assertTrue(getText(reviewTitle).contains(reviewTitleText));
		Assert.assertTrue(getText(subtotalTitleReview).contains(subtotalText.toUpperCase()));
		Assert.assertTrue(getText(fieldTitlePaymentReview).contains(fieldText.toUpperCase()));
		Assert.assertTrue(getText(amountDueTitlePaymentReview).contains(amountDue.toUpperCase()));
		Assert.assertTrue(getText(payTodayPaymentTitleReview).contains(payToday.toUpperCase()));
	}

	public List<String> verifyEditPaymentMethod() {

		List<WebElement> invoiceactive = getDriver()
				.findElements(By.xpath("//div[contains(@class,'invoice-cardInfo')]//p/span[2]"));
		List<String> allinvoiceactive = new ArrayList<>();
		for (int i = 0; i < invoiceactive.size(); i++) {
			allinvoiceactive.add(invoiceactive.get(i).getText());
		}

		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")
				|| driverType.trim().toUpperCase().contains("SAFARI"))) {
			if (!checkIfElementPresent(editpaymentmethodmobile, 5))
				throw new SkipException("Skipped");
			Assert.assertTrue(getText(editpaymentmethodmobile).contains("Edit"));
			click(editpaymentmethodmobile, "Edit Payment method");
			Assert.assertTrue(getText(selectpaymentpopup).contains("SELECT PAYMENT METHOD"));
		} else {
			if (!checkIfElementPresent(editpaymentmethoddesktop, 5))
				throw new SkipException("Skipped");
			Assert.assertTrue(getText(editpaymentmethoddesktop).contains("Edit Payment Method"));
			click(editpaymentmethoddesktop, "Edit Payment Method");
			Assert.assertTrue(getText(selectpaymentpopup).contains("SELECT PAYMENT METHOD"));
		}

		return allinvoiceactive;
	}

	public void verifyCVVandEnter() {
		getElementWhenClickable(allCVVSelectPaymentPopUp);
		allCvvField = getDriver().findElements(allCVVSelectPaymentPopUp);
		for (int i = 0; i < allCvvField.size(); i++) {
			Assert.assertTrue(allCvvField.get(i).isDisplayed(), "CVV field is displayed");
		}

	}

	public void clickInactiveCvv() {
		if (checkIfElementPresent(activecvv, 2)) {
			click(activecvv, "Inactive CVV", 2);
		} else {
			// nothing
		}
	}

	public void clickcancelbutton() {
		click(cancelbutton, "Cancel Button", 2);
	}

	public void typeCVV(String cvv1) throws Exception {
		type(allCVVSelectPaymentPopUp, "CVV", cvv1, false, allCVVSelectPaymentPopUpIOS);
	}

	public void clickOnViewUpdatedInvoiceButton() {
		click(viewUpdatedInvoice, "View Updated Invoice");

	}
}
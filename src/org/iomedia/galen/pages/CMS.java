package org.iomedia.galen.pages;




import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.iomedia.galen.common.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CMS extends BaseUtil {
	private Utils utils;
	private SuperAdminPanel adminPanel;
	public CMS(Utils base, WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<HashMapNew>[] sTestDetails ,SuperAdminPanel adminPanel) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		utils=base;
		this.adminPanel = adminPanel;
	}
	
	private By buildtext= By.xpath("//span[@class='build-text']");
	private By invoices = By.xpath("//p[text()='Invoices']");
	private By setting = By.xpath("//p[text()='Settings']");
	private By ticketmanagement = By.xpath("//p[text()='Ticket Management']");
	private By donation = By.xpath("//p[text()='Donation']");
	private By quickdonation = By.xpath("//p[text()='Quick Donation']");
	private By quickdonationenabled = By.cssSelector("#edit-box-donation-enable[checked='checked']");
	private By quickdonationenablecb = By.cssSelector("#edit-box-donation-enable + span");
	private By ticketoptions = By.xpath("//p[text()='Ticket Options']");
	private By bulktoggle = By.cssSelector("label[title='Enable this to show bulk transfer option'] span[class='toggle']");
	private By editsubmit = By.cssSelector("#edit-submit");
	private By addpage =By.xpath("//a[@title='Add a new page']");
	private By addnewmenu =By.xpath("//a[@title='Add a new menu']");
	private By viewmenus =By.xpath("//p[contains(text(),'View Menus')]");
	private By videopform = By.xpath("//form[@class='add-content-videotakeover']");
	private By formchekbox = By.xpath("(//span[@class='checkbox-material'])[2]");
	private By closeX = By.xpath("//span[contains(@class,'ui-icon-closethick')]");
	private By homepage =By.xpath("Home Pages");
	private By selectbutton = By.xpath("(//li[@class='content-type-options']/a//div[text()='Select'])[1]");
	private By ticketsalesverification = By.xpath("//div[@class='panel-title' and text()='Ticket Sales']");
	private By pagemanager= By.xpath("//p[text()='Page Manager']");
	private By menumanager = By.xpath("//p[text()='Menu Manager']");
	private By transactionalpages = By.xpath("//b[text()='Transactional Pages']/following-sibling::span");
	private By ticketsalespage =By.xpath("//div[@id='edit-ticket-sales']");
	private By formpresent =By.xpath("//div[@id='edit-ticket-sales']/div");
	private By ticketsalestext =By.xpath("//p[@id='edit-ticket-sales-wrapper--description']");
	private By signInComponent = By.xpath("//p[text()='Sign In Component']");
	private By addNewInvoice = By.xpath("//p[text()='Add New Invoice']");
	private By viewInvoices = By.xpath("//*[@class='view-invoices']");
	private By settingGear = By.xpath("//a[contains(@href,'invoice/list')]//i");
	private By invoiceTitle = By.name("title");
	private By summaryTab = By.xpath("//label[contains(@for,'summary-tab')]/..//input");
	private By paymentTab = By.xpath("//label[contains(@for,'payment-tab')]/..//input");
	private By reviewTab = By.xpath("//label[contains(@for,'review-tab')]/..//input");
	private By donationTab = By.xpath("//label[contains(@for,'donation-tab')]/..//input");
	private By questionTab = By.xpath("//label[contains(@for,'question-tab')]/..//input");
	private By addOnTab = By.xpath("//label[contains(@for,'addon-tab')]/..//input");
	private By subtotalLabel = By.xpath("(//div[contains(@class,'common')]//label[contains(@for,'tab-labels')]/..//input)[1] | //input[@id='edit-box1-tab-labels-total-tkt-lbl']");
	private By amountDueLabel = By.xpath("(//div[contains(@class,'common')]//label[contains(@for,'tab-labels')]/..//input)[2] | //input[@id='edit-box1-tab-labels-amount-due']");
	private By payTodayLabel = By.xpath("(//div[contains(@class,'common')]//label[contains(@for,'tab-labels')]/..//input)[3] | //input[@id='edit-box1-tab-labels-next-payment-due']");
	
	private By invoiceSummarySection = By.xpath("//a//span[text()='INVOICE SUMMARY']");
	private By paymentSection = By.xpath("//a//span[text()='PAYMENT']");
	private By reviewSection = By.xpath("//a//span[text()='REVIEW']");
	
	private By fieldName = By.xpath("//label[contains(@for,'field-name')]/..//input");
	
	private By lastNameLabel = By.xpath("//input[contains(@id,'payment-surname-lbl')]");
	private By stateLabel = By.xpath("//input[contains(@id,'state-lbl')]");
	private By cityLabel = By.xpath("//input[contains(@id,'city-lbl')]");
	private By zipCode = By.xpath("//input[contains(@id,'zipcode')]");
	
	private By interstitialGear = By.xpath("//td[contains(text(),'Interstitial')]/..//a");
	private By homePageGear = By.xpath("//td[contains(text(),'Home')]/..//a");
	private By changePasswordGear = By.xpath("//td[contains(text(),'Change')]/..//a");
	private By resetPasswordGear = By.xpath("//td[contains(text(),'Reset')]/..//a");
	private By claimTicketGear = By.xpath("//td[contains(text(),'Claim')]/..//a");
	private By bannerclose =By.xpath("//button[@type='button' and @class='close']");
	
	private By toggle = By.xpath("(//input[@class='iom-checkbox-ajax form-checkbox iom-processed'])[1]/..//span");
	private By submit = By.xpath("//button[text()='Submit']| //button[@type='submit' and text()='Save']");
	
	private By createAccountInput = By.xpath("//input[@id='edit-settings-userentry-signup-title-label']");
	private By signUpInput = By.xpath("//input[@id='edit-settings-userentry-signup-submit-btn-label']");
	private By firstInput = By.xpath("//input[@id='edit-settings-userentry-signup-first-name-label']");
	private By lastInput = By.xpath("//input[@id='edit-settings-userentry-signup-last-name-label']");
	private By signUpEmail = By.xpath("//input[@id='edit-settings-userentry-signup-email-label']");
	private By signUpPassword = By.xpath("//input[@id='edit-settings-userentry-signup-password-label']");
	private By signUpRemember = By.xpath("//input[@id='edit-settings-userentry-signup-remember-me-label']");
	
	private By signInTab = By.xpath("//a[@aria-controls='signin']");
	private By signInHeader = By.xpath("//input[@id='edit-settings-userentry-signin-title-label']");
	private By signInButtonText = By.xpath("//input[@id='edit-settings-userentry-signin-submit-btn-label']");
	private By signInEmail = By.xpath("//input[@id='edit-settings-userentry-signin-email-label']");
	private By signInPassword = By.xpath("//input[@id='edit-settings-userentry-signin-password-label']");
	private By signInRemember = By.xpath("//input[@id='edit-settings-userentry-signin-remember-me-label']");
	
	private By forgotPassword = By.xpath("//a[@aria-controls='forgotpassword']");
	private By forgotPasswordHeader = By.xpath("//input[@id='edit-settings-userentry-forgotpassword-title-label']");
	private By forgotPasswordButtonText = By.xpath("//input[@id='edit-settings-userentry-forgotpassword-submit-btn-label']");
	private By forgotPasswordDescription = By.xpath("//input[@id='edit-settings-userentry-forgotpassword-description-label']");
	private By forgotPasswordEmail = By.xpath("//input[@id='edit-settings-userentry-forgotpassword-email-label']");
	
	private By interstitialSaveCMS = By.xpath("//button[@type='submit']");
	
	private By changePasswordTitle = By.xpath("//input[@id='edit-settings-userentry-resetpassword-title-label']");
	private By changePasswordSubmitText = By.xpath("//input[@id='edit-settings-userentry-resetpassword-submit-btn-label']");
	private By changePasswordCurrentPassword = By.xpath("//input[@id='edit-settings-userentry-resetpassword-temp-pass-label']");
	private By changePasswordNewPassword = By.xpath("//input[@id='edit-settings-userentry-resetpassword-password-label']");
	private By changePasswordConfirmPassword = By.xpath("//input[@id='edit-settings-userentry-resetpassword-confirm-pass-label']");
	private By invokeAccount = By.xpath("//a[@class='invokeAccount']");
	
	private By signInTitleUI = By.xpath("//div[contains(@class,'componentTitle')]");
	private By signInEmailUI = By.xpath("//input[contains(@type,'email')]/..//label");
	private By signInPasswordUI = By.xpath("//input[contains(@type,'password')]/..//label");
	private By signInSignInButtonUI = By.xpath("//button[contains(@type,'submit')]");
	private By signInRememberUI = By.xpath("//input[contains(@name,'remember_me')]/..//span");
	
	private By signUpLink = By.xpath("//a[@href='/signup']");
	private By forgotPasswordLink = By.xpath("//a[@href='/forgot']");
	private By backToSignInLink = By.xpath("//a[@href='/']");
	
	private By firstNameSignUpUI = By.xpath("//input[contains(@name,'first_name')]/..//label");
	private By lastNameSignUpUI = By.xpath("//input[contains(@name,'last_name')]/..//label");
	private By componentSubHeading = By.xpath("//div[contains(@class,'componentSubHeading')]");
	
	// CMS - settings 
	public By cmssetting = By.xpath("//*[@id=\"block-editornavigation\"]/ul/li[6]/a/p");
	public By dashboardconfig = By.xpath("//*[@id=\"editor-navigation--6-1\"]/li[1]/a/p");
	
	//cms- manage ticket dash board header elements...cleaning this method2
	public By welcomelabel=By.xpath("//*[@id=\"edit-manage-dashboard-manage-ticket-dashboard-header-welcome-label-label\"]");
	public By accountidlabel=By.xpath("//*[@id=\"edit-manage-dashboard-manage-ticket-dashboard-header-account-id-label-label\"]");
	public By clientnamelabel=By.xpath("//*[@id=\"edit-manage-dashboard-manage-ticket-dashboard-header-team-name-label\"]");
	public By manageticketlabel= By.xpath("//*[@id=\"edit-manage-dashboard-manage-ticket-dashboard-header-manage-ticket-label-label\"]");
	public By totalticketslabel= By.xpath("//*[@id=\"edit-manage-dashboard-manage-ticket-dashboard-header-ticket-total-label-label\"]");
	public By accountbalancelabel=By.xpath("//*[@id=\"edit-manage-dashboard-manage-ticket-dashboard-header-account-balance-label-label\"]");
	public By outstandinginvoiceslabel=By.xpath("//*[@id=\"edit-manage-dashboard-manage-ticket-dashboard-header-outstanding-invoice-label-label\"]");
	
	public By dashboardconfigsave=By.id("edit-submit");
	
	//cms- manage ticket 
	public By ticketlabel=By.xpath("//*[@id=\"edit-manage-dashboard-manage-tickets-ticket-label-label\"]");

	//cms - manage invoices
	public By invoicelabel= By.xpath("//*[@id=\"edit-manage-dashboard-manage-invoices-invoice-label-label\"]");
	
	//cms - quicklinkslabel
	public By quicklinkslabels=By.xpath("//*[@id=\"edit-manage-dashboard-manage-quick-links-quick-link-label-label\"]");
	
	//frontend dashboard locators
	public By f_welcome=By.xpath("//*[@id=\"block-manage-ticket-dashboard-header-block\"]/div/div/div[1]/div/div[1]/p[1]");
	public By f_accountid=By.xpath("//*[@id=\"block-manage-ticket-dashboard-header-block\"]/div/div/div[1]/div/div[1]/p[2]");
	public By f_clientname=By.xpath("//*[@id=\"block-manage-ticket-dashboard-header-block\"]/div/div/div[1]/div/div[1]/h3/span");
	
	public By f_accountbalance=By.xpath("//*[@id=\"block-manage-ticket-dashboard-header-block\"]/div/div/div[1]/div/div[3]/p[1]");
	public By f_outstandinginvoices=By.xpath("//*[@id=\"block-manage-ticket-dashboard-header-block\"]/div/div/div[1]/div/div[3]/p[2]/a");
	public By f_ticket=By.xpath("//*[@id=\"block-manage-ticket-dashboard-block\"]/div/div[1]/div/div[1]/h3");
	public By f_manageinvoice= By.xpath("//*[@id=\"block-invoicedashboardblock\"]/div/div[1]/div/div[1]/h3");
	public By f_quicklinks=By.xpath("//*[@id=\"block-views-block-promo-tile-block-3\"]/h2");
	public By f_logedin=By.xpath("//*[@id=\"block-userentrycomponentblock\"]/div/div/div/div/form/div[4]");
	
	private By changePasswordCurrentVerify = By.xpath("//input[@name='temp_pass']/..//label");
	private By changePasswordNewVerify = By.xpath("//input[@name='password']/..//label");
	private By changePasswordConfirmVerify = By.xpath("//input[@name='confirm_pass']/..//label");
	private By changePasswordSubmitVerify = By.xpath("//button[@type='submit']");
	
	public By typeformworkspaceid = By.cssSelector("input[name*='typeform_workspace_id'][id*='edit-typeform-workspace-id']");
	public By saveconfigurationid = By.id("edit-submit");
	public By enabletypeformcheckbox = By.cssSelector("span[class*='check']");
	private By invoice_active = By.cssSelector("div[class*='togglebutton'] label[class*='toggle-color']");
	public By checkbox = By.cssSelector("input[checked*='checked']");
	
	//edp implemetation
	public By edptoggle = By.xpath("//label[@title='Enable this to list View EDP UI']//span[@class='toggle']");
	
	//secure barcode Implementation
	public By secureBarcode = By.xpath("//label[@title='Enable this to show Secure Barcode (Rotating Barcode)']//span[@class='toggle']");
	
	
	// Email Reporting 
	private By emailreporting = By.xpath("//p[contains(text(),'Email Reporting')]");
	private By refressButton = By.xpath("//button[text()='REFRESH']");
	private By downloadReportButton = By.xpath("//button[text()='DOWNLOAD REPORT']");
	private By BOUNCEDEMAIL =By.xpath("//div[@class='graphTextWrapper']//p[contains(text(),'BOUNCED E-MAIL')]");
	private By MARKEDASSPAM = By.xpath("//p[contains(text(),'MARKED AS SPAM')]");
	private By INVALIDEMAIL = By.xpath("//p[contains(text(),'INVALID EMAIL')]");
	private By BOUNCEDEMAILFooter =By.xpath("//label[@id='0' and text()='BOUNCED E-MAIL']");
	private By MARKEDASSPAMFooter = By.xpath("//label[@id='1' and text()='MARKED AS SPAM']");
	private By INVALIDEMAILFooter = By.xpath("//label[@id='2' and text()='INVALID E-MAIL']");
	private By SearchEmail = By.xpath("//input[@placeholder='Search Email']");
	
	//Transantctional page 
	private By dashboard =By.xpath("//div[contains(@id,'menu_Dashboard') and parent::div[contains(@class,'visibleContainer')]] | //nav[@id='block-iom-main-navigation-block']//a[contains(text(),'Dashboard')]");
	private By myevents =By.xpath("//div[contains(@id,'menu_MyEvents') and parent::div[contains(@class,'visibleContainer')]] | //nav[@id='block-iom-main-navigation-block']//a[contains(text(),'My Events')]");
	private By invoice =By.xpath("//div[contains(@id,'menu_Invoice') and parent::div[contains(@class,'visibleContainer')]] | //nav[@id='block-iom-main-navigation-block']//a[contains(text(),'Invoice')]");
	private By buy = By.xpath("//div[contains(@id,'menu_Buy') and parent::div[contains(@class,'visibleContainer')]] | //nav[@id='block-iom-main-navigation-block']//a[contains(text(),'Buy')]");
	private By upgrade =By.xpath("//div[contains(@id,'menu_Upgrade') and parent::div[contains(@class,'visibleContainer')]] | //nav[@id='block-iom-main-navigation-block']//a[contains(text(),'Upgrade')]");
	private By quickdonate =By.xpath("//div[contains(@id,'menu_QuickDonate') and parent::div[contains(@class,'visibleContainer')]] | //nav[@id='block-iom-main-navigation-block']//a[contains(text(),'Quick Donate')]");
	private By menunametextbox =By.xpath("//input[@name='menuName']");
	private By publiccheckBox =By.xpath("//label[1]//div[1]");
	private By loggedIncheckBox =By.xpath("//label[2]//div[1]");
	private By saveButton =By.xpath("//button[contains(@class,'menu-manager-saveBtn')]");
	private By activePublicMenuDropdown =By.xpath("(//input[@class='select-dropdown'])[1]");
	private By activeLoggedInDropdown =By.xpath("(//input[@class='select-dropdown'])[2]");
	
	private By savebutton=By.xpath("//button[@id='edit-submit']");
	private By menulistingtext = By.xpath("//p[@class='menu-listing']");
	private By savestatustext = By.xpath("//p[text()='Your settings have been saved']");
	
	//NAMMe Locators 
	private By marketingExperienceAddPage = By.xpath("//p[contains(text(),'Add Page')]");
	private By closebuttonvideo = By.xpath("//span[@class='ui-button-icon ui-icon ui-icon-closethick']");
	private By contentPage(String contentPageType) {
		return By.xpath("//ul[@class='content-type-block']/li//div[text()='"+contentPageType+"']");
	}
	private By layout(String layoutType) {
		return By.xpath("//div[contains(@class,'item-ticket-sales radio')]//div[@class='layout-title'][contains(text(),'"+layoutType+"')]");
	}
	private By startEditing = By.xpath("//label[@class='control-label option active']//div[@class='preview-start-editing'][contains(text(),'Start Editing')]");
	private By pageName = By.xpath("//input[@id='pageName']");
	private By pageTitle = By.xpath("//input[@id='pageTitle']");
	private By backbutton = By.xpath("//div[contains(@class,'cancelButton')]//button//div//div//span[contains(text(),'Back')]");
	private By savebuttonPagesetting = By.xpath("//span[contains(text(),'Save')]");
	private By accesslevel(String accesslevel) {
		return By.xpath("//input[@type='checkbox' and not(@id) and following-sibling::*[.='"+accesslevel+"']]");
	}
	
	
	private By draglocation =By.xpath("//div[@class='rst__placeholder']");
	private By draglocation1 =By.xpath("//div[contains(@class,'virtualScrollOverride')]");
	public String cmsuser="//*[@id=\"block-iom-admin-account-menu\"]/div/div/div[2]/i";
	public String cmslogout="//*[@id=\"amgr-user-menu\"]/li[2]/a";
	public String flogin="//*[@id=\"invoke-signin-modal\"]";
	
	//typeform locators
	public String superadminsetup = "//*[@id=\"block-editornavigation\"]/ul/li[8]/a/p";
	public String cmstypeform= "//*[@id=\"editor-navigation--8-1\"]/li[3]/a/p";
	public String work_space="cfER3z";

	public String cmsinvoice ="//*[@id=\"block-editornavigation\"]/ul/li[5]/a/p";
	public String viewinvoice="//*[@id=\"editor-navigation--5-1\"]/li[1]/a/p";
	public String invoicesetting="//*[@id=\"search_default\"]/tbody/tr/td[7]/span/a/i";
	public String questions="//*[@id=\"add-invoices\"]/div[3]/div/ul/li[7]/a";
	public String selecttypeform="openPopup";
	public String type_save="typeform-accepted";
	public String typeform_submit="edit-submit";
	
	public String cmstoggle="//*[@id=\"search_default\"]/tbody/tr/td[6]/div/label/span";
	String firsttypeform="//*[@id=\"typeform-list\"]/div/div/div[2]/div[2]/div[1]/div[2]/a";
	
	
	public String fname="email";
	public String fpwd="password";

	public String f_manageticket="";
	public String f_totalticket="";
	
	//declare cms variables dashboard config page 
	public String welcome = "welcome_custom"; 
	public String accountid = "accountid_custom";
	public String clientname = "clientname_custom";
	public String manageticket ="manageticket_custom"; 
	public String totaltickets = "tickets_custom";
	public String accountbalance = "accountbalance_custom";
	public String outstandinginvoices="outstandinginvoices_custom";
	
	public String ticket="ticket_cistom";
	public String manageinvoice="manageinvoice_custom";
	public String quicklinks="quicklinks_custom";
	
	boolean allowMultipleSubmission=true;
	String showAfter="Invoice Summary";
	String payment="Payment";
	
	public String invoiceName = "Invoice Test";
	public String invoiceSummaryMessage = "Test Invoice Summary";
	public String invoiceReviewMessage = "Test Review";
	public String summaryName = "AutoSummary";
	public String paymentName = "AutoPayment";
	public String reviewName = "AutoReview";
	public String donationName = "AutoName";
	public String addOnName = "AutoAddOn";
	public String questionName = "AutoQuestions";
	public String subtotalName = "AutoSubtotal";
	public String amountDueName = "AutoAmountDue";
	public String paytodayName = "AutoPayToday";
	public String fieldNameAuto = "AutoFieldName";
	public String lastName = "AutoLast";
	public String state = "AutoState";
	public String city = "AutoCity";
	public String zip = "AutoZip";
	public String createHeaderText = "AutoCreate";
	public String signUpText = "AutoSignUp";
	public String firstNameSignUpText = "AutoFirstSignUp";
	public String lastNameSignUpText = "AutoLastSignUp";
	public String emailSignUp = "AutoEmailSignUp";
	public String passwordSignUp = "AutoPasswordSignUp";
	public String rememberSignUp = "AutoRememberSignUp";
	
	public String signInText = "AutoSignInText";
	public String signInButton = "AutoSignInButton";
	public String signInEmailText = "AutoEmailSignIn";
	public String signInPasswordText = "AutoPasswordSignIn";
	public String signInRememberText = "AutoRememberSignIn";
	
	public String forgotPasswordHeaderText = "AutoForgotText";
	public String forgotPasswordButton = "AutoForgotButton";
	public String forgotPasswordDescriptionText = "AutoForgotDescription";
	public String forgotPasswordEmailText = "AutoForgotEmail";
	
	public String signInTextHome = "AutoSignInTextHome";
	public String signInButtonHome = "AutoSignInButtonHome";
	public String signInEmailTextHome = "AutoEmailSignInHome";
	public String signInPasswordTextHome = "AutoPasswordSignInHome";
	public String signInRememberTextHome = "AutoRememberSignInHome";
	
	public String forgotPasswordHeaderTextHome = "AutoForgotTextHome";
	public String forgotPasswordButtonHome = "AutoForgotButtonHome";
	public String forgotPasswordDescriptionTextHome = "AutoForgotDescriptionHome";
	public String forgotPasswordEmailTextHome = "AutoForgotEmailHome";
	
	public String createHeaderTextHome = "AutoCreateHome";
	public String signUpTextHome = "AutoSignUpHome";
	public String firstNameSignUpTextHome = "AutoFirstSignUpHome";
	public String lastNameSignUpTextHome = "AutoLastSignUpHome";
	public String emailSignUpHome = "AutoEmailSignUpHome";
	public String passwordSignUpHome = "AutoPasswordSignUpHome";
	public String rememberSignUpHome = "AutoRememberSignUpHome";
	
	public String createHeaderTextClaim = "AutoCreateClaim";
	public String signUpTextClaim = "AutoSignUpClaim";
	public String firstNameSignUpTextClaim = "firstNameSignUpTextClaim";
	public String lastNameSignUpTextClaim = "AutoLastSignUpClaim";
	public String emailSignUpClaim = "AutoEmailSignUpClaim";
	public String passwordSignUpClaim = "AutoPasswordSignUpClaim";
	public String rememberSignUpClaim = "AutoRememberSignUpClaim";
	
	public String signInTextClaim = "AutoSignInTextClaim";
	public String signInButtonClaim = "AutoSignInButtonClaim";
	public String signInEmailTextClaim = "AutoEmailSignInClaim";
	public String signInPasswordTextClaim = "AutoPasswordSignInClaim";
	public String signInRememberTextClaim = "AutoRememberSignInClaim";
	
	public String forgotPasswordHeaderTextClaim = "AutoForgotTextClaim";
	public String forgotPasswordButtonClaim = "AutoForgotButtonClaim";
	public String forgotPasswordDescriptionTextClaim = "AutoForgotDescriptionClaim";
	public String forgotPasswordEmailTextClaim = "AutoForgotEmailClaim";
	
	public String changePasswordTitleText = "AutoChangePasswordTitle";
	public String changePasswordSubmitButtonText = "AutoChangePasswordSubmit";
	public String changePasswordCurrentPasswordText = "AutoChangePasswordCurrent";
	public String changePasswordNewPasswordText = "AutoChangePasswordNew";
	public String changePasswordConfirmPasswordText = "AutoChangePasswordConfirm";
	
	
	//typeform selected under review tab
		public void typeform_selection_payment() throws InterruptedException
		{
			WebDriverWait wait= new WebDriverWait(getDriver(), 20);
			JavascriptExecutor ex = (JavascriptExecutor)getDriver();
			WebElement active_toggle=getDriver().findElement(By.xpath(cmstoggle));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(cmstoggle)));
			
			if(!checkIfElementPresent(invoice_active, 5))
			{
				active_toggle.click();
			
			}
			
			utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
			utils.navigateTo("/admin/invoice/1/edit?destination=admin/invoice/list");	
			ex.executeScript("history.go(0)");
			
			WebElement questiontab=getDriver().findElement(By.xpath(questions));
			questiontab.click();
		
		
			By save = By.id("typeform-accepted");
			By dropDown = By.cssSelector("div[class*='typeform'] input[class*='select-dropdown']");
			
			if(checkIfElementPresent(selecttext, 3))
			{
				try {
							
				By questionsTab = By.xpath(".//span[text()='QUESTIONS']");
				By embedTypeform = By.xpath(".//*[@id='openPopup']/span[text()='EMBED TYPEFORM'] | .//*[@id='openPopup']/span[text()='SELECT TYPEFORM']");
				//
				ex.executeScript("arguments[0].click();", getDriver().findElement(questionsTab));
				
				if(checkIfElementPresent(embedTypeform))
				
				{
					WebElement we = getElementWhenVisible(embedTypeform);
					click(we, "Select Typeform");
					By typeform_automation = By.cssSelector("a[title='Automation']");
					WebElement wtypeform_automation = getElementWhenVisible(typeform_automation);
					scrollingToElementofAPage(wtypeform_automation); //scrolling not working
					wtypeform_automation.click();

					ex.executeScript("arguments[0].click();", getDriver().findElement(save));
				
				}
				
				WebElement wdropDown = getElementWhenRefreshed(dropDown, "disabled", "null", 2);
				
				//controlling position of typeform for client
				if(wdropDown.getAttribute("value").trim().equalsIgnoreCase(showAfter.trim())) {

					ex.executeScript("arguments[0].click();", wdropDown);
					By locator = By.xpath(".//div[contains(@class, 'typeform')]//ul//li[2]//span[1]");
					wait.until(ExpectedConditions.elementToBeClickable(locator));
					WebElement pay=getDriver().findElement(locator);
				
					ex.executeScript("arguments[0].click();", pay);
					
				
				}
				
				By allow_multiple_submissions = By.cssSelector("label[for*='typeform-allow-multiple-submission'] input");
				WebElement wallow_multiple_submissions = getDriver().findElement(allow_multiple_submissions);
				
				if(allowMultipleSubmission && wallow_multiple_submissions.getAttribute("checked") == null)
				{
					ex.executeScript("arguments[0].click();", wallow_multiple_submissions);
				} 
				else if(!allowMultipleSubmission && wallow_multiple_submissions.getAttribute("checked") != null) 
				{
					ex.executeScript("arguments[0].click();", wallow_multiple_submissions);
				}
				
				getDriver().findElement(dashboardconfigsave).click();
				ex.executeScript("arguments[0].click();", getDriver().findElement(dashboardconfigsave));
				
				By alert = By.cssSelector("div[role='alert']");
				getElementWhenVisible(alert, 5);
				}
				
				finally {
					
					//getDriver().navigate().to("https://stg1-am.ticketmaster.com/namautomation/user/logout");
					getDriver().navigate().to(Environment.get("APP_URL")+"/user/logout");
					getElementWhenPresent(By.xpath(".//input[@name='email'] | .//div[@class='mobile-signin']//*[text()='Sign In'] | .//div[@class='desktop-signin-dashboard']//a[text()='Sign In']"));
				}
			}
			else
				{
				System.out.println("typeform already selected ... Now changing its poition");
				WebElement wdropDown = getElementWhenRefreshed(dropDown, "disabled", "null", 2);

				if(wdropDown.getAttribute("value").trim().equalsIgnoreCase(showAfter.trim())) {
					ex.executeScript("arguments[0].click();", wdropDown);
					By locator = By.xpath(".//div[contains(@class, 'typeform')]//ul//li[2]//span[1]");
					WebElement pay=getDriver().findElement(locator);
					
					wait.until(ExpectedConditions.elementToBeClickable(locator));
					
					ex.executeScript("arguments[0].click();", pay);
				
					//getDriver().findElement(dashboardconfigsave).click();
					ex.executeScript("arguments[0].click();", getDriver().findElement(dashboardconfigsave));
			
					
				}
				
				//getDriver().navigate().to("https://stg1-am.ticketmaster.com/namautomation/user/logout");
				
				//utils.navigateTo("/user/logout");
				getDriver().navigate().to(Environment.get("APP_URL")+"/user/logout");
				getElementWhenPresent(By.xpath(".//input[@name='email'] | .//div[@class='mobile-signin']//*[text()='Sign In'] | .//div[@class='desktop-signin-dashboard']//a[text()='Sign In']"));
				}
		}
		
	
	
	
		public void verifiesCustomiseDashboard()
		{
	//remove takeover
			((JavascriptExecutor) getDriver()).executeScript("$('.theme-dialog-3SJ5Op').remove()");
			
			Assert.assertTrue(getText(f_welcome).contains(welcome));
			Assert.assertTrue(getText(f_accountid).contains(accountid));
			Assert.assertTrue(getText(f_accountbalance).contains(accountbalance));
			Assert.assertTrue(getText(f_outstandinginvoices).contains(outstandinginvoices));
			Assert.assertTrue(getText(f_ticket).contains(ticket));
			Assert.assertTrue(getText(f_quicklinks).contains(quicklinks));
			Assert.assertTrue(getText(f_manageinvoice).contains(manageinvoice));
				
		}
		
	
	public void ViewDashboardconfig()
	{
	click(cmssetting, "cms setting", 3);
	click(dashboardconfig, "cms Dashboard config", 3);
	}
	
	public void enterManageTicketDashboard()
	{
		getElementWhenClickable(welcomelabel, 3).clear();
		getElementWhenClickable(welcomelabel, 3).sendKeys(welcome);
		
		getElementWhenClickable(accountidlabel, 3).clear();
		getElementWhenClickable(accountidlabel, 3).sendKeys(accountid);
		
		getElementWhenClickable(clientnamelabel, 3).clear();
		getElementWhenClickable(clientnamelabel, 3).sendKeys(clientname);
		
		getElementWhenClickable(manageticketlabel, 3).clear();
		getElementWhenClickable(manageticketlabel, 3).sendKeys(manageticket);
		
		getElementWhenClickable(totalticketslabel, 3).clear();
		getElementWhenClickable(totalticketslabel, 3).sendKeys(totaltickets);

		getElementWhenClickable(accountbalancelabel, 3).clear();
		getElementWhenClickable(accountbalancelabel, 3).sendKeys(accountbalance);
		
		getElementWhenClickable(outstandinginvoiceslabel, 3).clear();
		getElementWhenClickable(outstandinginvoiceslabel, 3).sendKeys(outstandinginvoices);
	}
	
	
	public void EnterTicketLabel()
	{

		getElementWhenClickable(ticketlabel, 3).clear();
		getElementWhenClickable(ticketlabel, 3).sendKeys(ticket);
	}
	
	public void EnterInvoiceLabel()
	{

		getElementWhenClickable(invoicelabel, 3).clear();
		getElementWhenClickable(invoicelabel, 3).sendKeys(manageinvoice);
	}
	
	public void EnterQuickLinkLabel()
	{

		getElementWhenClickable(quicklinkslabels, 3).clear();
		getElementWhenClickable(quicklinkslabels, 3).sendKeys(quicklinks);
	}
	
	public void DashboardConfigSaveButton()
	{
		getElementWhenClickable(dashboardconfigsave, 3).click();
		
	}
	
	//enable afl under superadmin purchase flow 
	public By aflcheckbox = By.cssSelector("input[checked*='checked'][ id*='edit-ga-enable']");
	public By checkbox_afl =By.xpath("//*[@id=\"edit-box2--content\"]/div/div/div/label/span/span");
	
	public void enableafl()
	{
		
	System.out.println("enabling alf method");
	getDriver().manage().window().maximize();
	try
	{
		if(checkIfElementPresent(aflcheckbox, 2)==false)
		{
			System.out.println("enabling alf");
			
			click(checkbox_afl, "enable afl", 2);
			
			click(saveconfigurationid, "Save Configuration",2);	
			
		}
		
	    } 
	
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	
	finally
	{
		click(saveconfigurationid, "Save Configuration",2);	
	}
	
	}
	
//enable contact details toggle
	public String afltoggle="//*[@class='style-stepCounter-34j-9P']/label/span";
	public By inactiveafl=By.xpath("//*[@class='style-stepCounter-34j-9P'][1]/label[1]/span[@class='theme-off-38XAl5']");
	public By aflsave=By.xpath("/html/body/div[2]/div/div/section/div[3]/div/div/div/div[3]/button[2]");
	
	public void enablecontactdetailsafl()
	{
		try {
			
			
			WebDriverWait wait= new WebDriverWait(getDriver(), 20);
			JavascriptExecutor ex = (JavascriptExecutor)getDriver();
			
			WebElement afl_toggle=getDriver().findElement(By.xpath(afltoggle));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(afltoggle)));
			
			if(checkIfElementPresent(inactiveafl, 5))
			{
				
				afl_toggle.click();
				System.out.println("user has  clicked afl toggled");
				
			}	
			
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			click(aflsave, "save", 5);
		}

	}
	
	
	
	public By dsnid= By.name("tm_dsn");	
	public String link = null;
	String dsn=null;
	
	//created afl link as per the dsn
	public void dsnverify()
	{
	
		System.out.println("user navigated at tmapi settings");
		try
		{

			dsn=getDriver().findElement(dsnid).getAttribute("value");
			
			if(dsn.contains("genesis"))
			{

				link = "/buy?TMPLAN=ETS1001&PC1=A_A&PC2=A_A";
				
			}
			else if(dsn.equals("iomedia") || dsn.equals("iomedia_preprod"))
			{
				link="/buy?TMPLAN=15IOFULL&PC1=A_A&PC2=A_A";
			}
			else
			{
				link="/buy?TMPLAN=EIOMED05&PC1=A_A&PC2=A_A";
			}
			
			System.out.println("dsn is= " + dsn + "link is = " + link);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public By promotilename=By.name("promo_tile_name");
	
	//user click on external link
	public void promotilename()
	{
		try {
			
			type(promotilename, "promotile name", "AFL BUY",3);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//enable paypal
	
	public By configpaymentx=By.xpath("//*[text()='Configure Payment']");
	public By paypalcheckx=By.xpath("//*[@class='theme-tabs-2DN8dj style-tabsMenu-1GP0IL']/section/div/div/div[2]/div[6]/label/div");
	public By savepaypal= By.xpath("//*[@class='style-formSubmitButtons-3beXbM']/button[2]");
	public By saveaflsettings=By.xpath("//*[text()='Save']");
	public By savesettings=By.xpath("//*[@class='style-vvCmsContainer-puViOg']/div[3]//*[text()='Save']");
			
	public void enablepaypal()
	{
	
	
	String uncheckpayal="theme-check-X_gKEn theme-check-29aPop";
	
	try {
	click(configpaymentx, "'Configure Payment", 2);
	String py=getDriver().findElement(paypalcheckx).getAttribute("class"); 
	
	System.out.println(py);

	if(py.equals(uncheckpayal))
	{
	click(paypalcheckx, "Paypal", 2);
	click(savepaypal, "save", 2);
	}
	enablepayotheratafl();
	
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

	finally
	{
		click(savesettings, "save", 2);
	}
	}

	//enable ach mop
	public By aflcheckx=By.xpath("//*[@class='theme-tab-3a3cHV theme-active-2lbr9f']/div/div/div[2]/div[8]/label/div");
	public By savexother=By.xpath("//*[text()='Save']");
	public void enableach()
	{
	
	
	String uncheckach="theme-check-X_gKEn theme-check-29aPop";
	
	try {
	click(configpaymentx, "'Configure Payment", 2);
	
	//exception is occuring at line 689
	String ach=getDriver().findElement(aflcheckx).getAttribute("class"); 
	
	System.out.println(ach);

	if(ach.equals(uncheckach))
	{
	click(aflcheckx, "ACH", 2);
	//click(savepaypal, "save", 2);
	}
	enablepayotheratafl();
	click(savesettings, "save", 2);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

	finally
	{
		click(savexother, "save", 2);
	}
	}
	
	//enable payother method
	public By payother=By.xpath("//*[@class='theme-tab-3a3cHV theme-active-2lbr9f']/div/div/div/div[2]/div[2]/label/div");

	
	public void enablepayotheratafl()
	{
		System.out.println("enable payotehr");
		String paypaluncheck="";
		
		String checkedpayother="theme-check-X_gKEn theme-check-29aPop theme-checked-1dEweO theme-checked-3ToUtK";
		
		try {

		paypaluncheck=getDriver().findElement(payother).getAttribute("class").toString();
		
		System.out.println(paypaluncheck);

		if(paypaluncheck.equals(checkedpayother))
		{
		click(payother, "Payother", 2);
		
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			
	}
	
	
	//configire paypal
	public By pyapiendpointn=By.name("payments_endpoint");
	public By pyusernamen=By.name("payments_username");
	public By pypwdn=By.name("payments_password");
	public String pyapiendpoint="";
	public String pyusername="";
	public String pypwd="";
	
	public void configpaypal()
	{
		System.out.println("configuring paypal values");
		
		try
		{

			
			//dsn=getDriver().findElement(dsnid).getAttribute("value").toString();
			System.out.println(dsn);
			//preprod
		

			//qa
			if(dsn.equals("unitas"))
			{
				if(getDriver().findElement(pyapiendpointn).getAttribute("value").toString().equals(""))
				{
					pyapiendpoint="https://api.payment.qa3.us-east-1.nonprod-tmaws.io";
					type(pyapiendpointn, "payments_endpoint", pyapiendpoint, 2);
					
				}
				
				if(getDriver().findElement(pyusernamen).getAttribute("value").toString().equals(""))
				{
					pyusername="new.account.manager";
					type(pyusernamen, "payments_username", pyusername, 2);
				}
				
				if(getDriver().findElement(pypwdn).getAttribute("value").toString().equals(""))
				{
					pypwd="m00lahm@fi@";
					type(pypwdn, "payments_password", pypwd, 2);
				}
			}
			//stg or iomedia_preprod or iomedia
			else
			{
				if(getDriver().findElement(pyapiendpointn).getAttribute("value").toString().equals(""))
				{
					pyapiendpoint="https://api.payment.preprod1.us-east-1.prod-tmaws.io";
					type(pyapiendpointn, "payments_endpoint", pyapiendpoint, 2);
					
				}
				
				if(getDriver().findElement(pyusernamen).getAttribute("value").toString().equals(""))
				{
					pyusername="new.account.manager";
					type(pyusernamen, "payments_username", pyusername, 2);
				}
				
				if(getDriver().findElement(pypwdn).getAttribute("value").toString().equals(""))
				{
					pypwd="m00lahm@fi@_pr3pr0d";
					type(pypwdn, "payments_password",pypwd , 2);
				}
			}
			
			click(saveaflsettings, "save", 2);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	//User click on on add button at external link
	
	public By promotiltle=By.name("manage_tickets_text");
	
	public void promotiltle()
	{
	
		try {
			type(promotiltle, "promotile title", "AFL BUY",3);	
			
			}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	//User drag and drop afl buy ext link at menu tree
	public By titlelink=By.name("title_link");
	
	public void aflurl()
	{
		
		try {

			type(titlelink, "promotile url", link,3);
			click(saveconfigurationid, "Save Configuration",3);
			
		
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	//user click on buy promotile afl link
	public By promotileafllink=By.xpath("//*[@class='views-row clearfix row-1']//*[contains(text(),'AFL BUY')]");
	
	public void clickbuyaflpromotile()
	{
		click(promotileafllink, "Buy AFL promotile link",3);
	}

	
	//fe user input detials of other user
	public By contactdetailpath=By.xpath("//*[@class='contact-details-noContactDetailsSavedButton-2HIWnL']");
	
	public By fullnamepath=By.xpath("//*[@id='targetToScroll']/div[2]/div[2]//*[@name='full_name']");
	public By fullnamepath2=By.xpath("//*[@class='theme-inputElement-gZJC0F theme-filled-1qPLxq'][@name='full_name']");

	
	public By dobxpath=By.xpath("//*[@id='targetToScroll']/div[2]/div[2]//*[@name='birth_date']");
	public By dobxpath2=By.xpath("//*[@id='targetToScroll']//*[@name='birth_date'][1]");
	
	public By emailaddress=By.xpath("//*[@id='targetToScroll']/div[2]/div[2]//*[@name='email']");
	public By emailaddress2=By.xpath("//*[@id='targetToScroll']//*[@name='email'][1]");
	
	public By companyxpath=By.xpath("//*[@id='targetToScroll']/div[2]/div[2]//*[@name='company_name']");
	
	public By savepath=By.xpath("//*[text()='Save & Close']");

	public By edit=By.xpath("//*[@class='contact-details-editText-3zctqs']");
	//window.AMGR.CONFIG.BLOCK_CONFIGS.CHECKOUT_PAGE_CONFIG.steps[3].content.checkoutSteps[0].content.fields
	public By pwdx=By.id("password");
	public By pwdcontinuex=By.xpath("//*[contains(text(),'Continue')]");
	
	String create_email()
	{
		Random rand = new Random();
		int n = rand.nextInt(1000);
		return "test"+n+"@mailinator.com";
	}
	
	//user enter contact details values

	public void contactdetails()
	{
		WebDriverWait wait=new WebDriverWait(getDriver(),10);
		
		sync(500L);
		
		getDriver().manage().window().maximize();
		try {
			
			
			WebElement contactdetail=getDriver().findElement(contactdetailpath);
					
			if(checkIfElementPresent(contactdetailpath, 3))
			{
				wait.until(ExpectedConditions.elementToBeClickable(contactdetail));
				click(contactdetail, "contact details tab");
				
				for(int i=1; i<=2;i++)
				{
				if(i==1)
				{
					if(checkIfElementPresent(edit, 2))
					{
						click(edit, "edit button");
						
						System.out.println(getDriver().findElement(fullnamepath2).getAttribute("value").toString());
						
						if(checkIfElementPresent(fullnamepath2, 2))
						{
							if(getDriver().findElement(fullnamepath2).getAttribute("value").toString().equals(""))
							{
								click(fullnamepath2, "fullname");
								type(fullnamepath2, "Full Name", "test user");
							}

							//JavascriptExecutor js = (JavascriptExecutor) getDriver();  
							//js.executeScript("document.getElementById(fullnamepath2).value='test user';");

						}
						
						if(checkIfElementPresent(dobxpath2, 2))
						{
							if(getDriver().findElement(dobxpath2).getAttribute("value").toString().equals(""))
							{
								click(dobxpath2, "DOB");
								type(dobxpath2, "DOB", "02/02/1991");	
							}
	
						}
						
						if(checkIfElementPresent(emailaddress2, 2))
						{
							
							if(getDriver().findElement(emailaddress2).getAttribute("value").toString().equals(""))
							{
								click(emailaddress2, "email");
								type(emailaddress2, "email", create_email());	
							}

						}
						
					}
					
				}
				
				else
				{
					if(checkIfElementPresent(fullnamepath, 2) )
					{
						
						if(getDriver().findElement(fullnamepath).getAttribute("value").toString().equals(""))
						{
							click(fullnamepath, "fullname");
							type(fullnamepath, "Full Name", "test user");	
						}
	
					}
					
					if(checkIfElementPresent(dobxpath, 2))
					{
						if(getDriver().findElement(dobxpath).getAttribute("value").toString().equals(""))
						{
							click(dobxpath, "DOB");
							type(dobxpath, "DOB", "02/02/1991");	
						}
		
					}
					
					if(checkIfElementPresent(emailaddress, 2))
					{

						if(getDriver().findElement(emailaddress).getAttribute("value").toString().equals(""))
						{
							click(emailaddress, "email");
							type(emailaddress, "email", create_email());	
						}
	
					}
			}
			}
			}		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			
			try 
			{
				wait.until(ExpectedConditions.elementToBeClickable(savepath));
				click(savepath, "save and close buttonbutton");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			
		}
		
	}
	
	public By continuepath=By.xpath("//*[@class='theme-body-3yg_yD']//*[contains(text(),'Continue')]");
	public By conbutton=By.xpath("//*[contains(text(),'Continue')]");
	//user enter password
	public void clickcontinue(String password)
	{
		WebDriverWait wait=new WebDriverWait(getDriver(),10);
		try {
			
			
			
			if(checkIfElementPresent(pwdx, 1))
			{
				type(pwdx, "password", password);
			
			}
			
			if(checkIfElementPresent(continuepath, 1))
			{
			click(continuepath, "Continue");
			
			}
			Thread.sleep(2000);
			
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(checkIfElementPresent(conbutton, 1))
			{
				wait.until(ExpectedConditions.elementToBeClickable(conbutton));
				click(pwdcontinuex, "click continue");
			
			}
		}
		
	}
	
	//select delivery and shipping address 
	public By deliveryandshippingx=By.xpath("//*[text()='Select Delivery Method']");
//	public By shippvalue=By.xpath("//*[@class='theme-values-1ju4QP']/li[text()='Mobile (Free)']");
	public By shippvalue=By.xpath("//*[@class='theme-values-1ju4QP']/li[2]");
	
	public void delivershipping()
	
	{
		WebDriverWait wait=new WebDriverWait(getDriver(),10);
		
		try {
			
			
		/*click(shippvalue, "select free method");
		selectByValue(shippvalue, "select free method", "Mobile (Free)");
		click(continuepath, "Continue button",5);*/
			sync(500L);
			WebElement el1=getDriver().findElement(deliveryandshippingx);
			WebElement el2=getDriver().findElement(shippvalue);
			dropdown(el1,el2);
			
			
			wait.until(ExpectedConditions.elementToBeClickable(pwdcontinuex));
			click(pwdcontinuex, "continue");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	//select payment option
	public void dropdown(WebElement e1,WebElement e2)
	{
	
		try {
		Actions builder = new Actions(getDriver());
		Action seriesofaction=builder
				.moveToElement(e1)
				.click()
				.moveToElement(e2)
				.click()
				.build();
		seriesofaction.perform();
		
		}
		catch (Exception el) 
		{
			el.printStackTrace();
							
		}
	
	}

	
	//select payment option payother
	public By selectpaymentopx= By.xpath("//*[@class='theme-inputElement-gZJC0F'][@placeholder='Select Payment Option']");
	public By payotherx=By.xpath("//*[@class='theme-dropdown-10vqUT style-paymentPlanDropDown-1jFASb style-paymentPlanDropdownNotOnEdge-3S_UjY']//*[text()='Pay other']");
	
	
	public void selectpaymentoption()
	{
		try {
		sync(500L);
		WebElement el1= getDriver().findElement(selectpaymentopx);
		WebElement el2=getDriver().findElement(payotherx);
		dropdown(el1,el2);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		
		
	}
	

	
	//select payment method
	public By addlinkpath=By.xpath("//*[text()='Edit / Add Payment Method']");
	public By addmethodx=By.xpath("//*[text()='Add a New Credit or Debit Card']");
	public By firstname=By.name("cardDetail.cc_name_first");
	public By lname=By.name("cardDetail.cc_name_last");
	public By cardnum=By.name("cardDetail.cardNumber");
	
	public By monthx=By.name("cardDetail.date.month");
	public By monthv=By.xpath("//*[text()='02']");
	
	public By yearx=By.xpath("//*[@class='theme-inputElement-gZJC0F'][@name='cardDetail.date.year']");
	public By yearv=By.xpath("//*[text()='21']");
	
	public By postal=By.xpath("//*[@name='cardDetail.cardAddress.cc_postal_code']");
	public By billcheck= By.name("cardDetail.useBillingAddress");
	public By savex=By.xpath("//*[@type='submit']");
	public By savecardx=By.name("cardDetail.saveToAccount");

	public By accept=By.xpath("//*[@class='theme-field-1fDqBF style-termsCondition-3JfKDv']/div[@class='theme-check-X_gKEn theme-check-29aPop']");
	public By amount=By.xpath("//*[@type='tel']");
	public By cvcx=By.xpath("//*[@name='cvc']");
	public By submitx=By.xpath("//*[contains(text(),'Submit Order')]");
	public By Addx=By.xpath("//*[text()='Add Payment Method']");
	public By method1=By.xpath("//*[@class='theme-field-TB55pk']//*[text()='Select Payment Method']");
	public By method2=By.xpath("//*[@class='theme-values-1ju4QP']/li[2]//*[@class='style-activeCardLabelEllipsis-2gPdKI']");
	public By svgx=By.xpath("//*[local-name() = 'svg']");
	public By method4=By.xpath("//*[@class='theme-field-TB55pk']//*[@class='style-activeCardLabelEllipsis-2gPdKI']");
	
	String last_three_digits(String digits)
	{
		//****** *0005, Ravi Amx one, 
		int len=digits.indexOf(",");
		String cvvr=null;
		System.out.println(digits);
		
		String cname= getDriver().findElement(svgx).getAttribute("class");
		
		if (cname.contains("AE"))
		{
			cvvr=digits.substring(len-4, len);
			System.out.println(cvvr);
		}
		else 
		{
			cvvr=digits.substring(len-3, len);
			System.out.println(cvvr);
		}
		
		return cvvr; 
			 	
	}
	
//add paypal as mop
	public By paypalonlyx= By.xpath("//*[@class='theme-field-TB55pk']//*[@class='style-cardInfo-1KMMf_ checkout-paypal']/img[@alt='PaypalImg']");
	public By paypalmultix=By.xpath("//*[@alt='PaypalImg']");
	public By Addpaypalx=By.xpath("//*[text()='Add PayPal']");
	public By paypalbuttonx=By.xpath("//*[@data-funding-source='paypal']");
	public By paypalemailn= By.name("login_email");
	public By paypalpwdn=By.name("");
	public By pay=By.xpath("//*[@class='theme-values-1ju4QP']/li[2]/div");    
	
	public void addpaypalmop()
	{
	
	System.out.println("user is selecting/adding payment method");
	JavascriptExecutor js = (JavascriptExecutor)getDriver();
	String acterror="";
	String exptmsg="We are currently processing your order. To confirm the details and status of your order, please contact";
	
	try
	{
	//user has one mop as paypal:  rkumar@io-media.com/ravi1234
	if( checkElementPresent(multiplecx, 3)==false && checkIfElementPresent(paypalmultix, 2))
	{
		type(amount, "amount", "$1",2);
		click(accept, "accept",2);
		click(submitx, "Submit",2);
		
		  if(checkIfElementPresent(errorpass, 2))
			{
		    
				System.out.println("Required Error message displaying on payment = " +acterror );
				if(acterror.contains(exptmsg))
				{
					System.out.println("Required Error message displaying on payment = " +acterror );
				}
			}
	}
	
	// in case of user has having multi mop along with paypal: ashish.tanwar@ticketmaster.com
	else if (checkElementPresent(multiplecx, 3)==true && checkElementPresent(paypalmultix, 3)==true)
	{
		
		WebElement m1=getDriver().findElement(multiplecx);
		WebElement m2=getDriver().findElement(paypalmultix);
		dropdown(m1,m2);
      
		type(amount, "amount", "$1");
		click(accept, "accept",3);
	    click(submitx, "Submit",3);
		
	    
	    if(checkIfElementPresent(errorpass, 2))
		{
	    
			System.out.println("Required Error message displaying on payment = " +acterror );
			if(acterror.contains(exptmsg))
			{
				System.out.println("Required Error message displaying on payment = " +acterror );
			}
		}
	
	}
	
	
	//input condition to verify payment message
    }

catch(Exception e)
{
		e.printStackTrace();
}	

}

	//function to create random nine digit number 
	
	String create_rnum()
	{
		Random rand = new Random();
		int n = rand.nextInt(1000);
		return "192639" + n;
	}
	
	
	// adding ach as mop
	
	public By achmultix=By.xpath("//*[@class='theme-values-1ju4QP']/li[2]/div[@class='style-cardInfo-1KMMf_ checkout-account']/span");
	public By addNewCheckingAccount = By.xpath("//*[@class='style-cardForm-euYTrx ']/div/div[3]//*[text()='Add New Checking Account']");
	public By achfname=By.name("firstName");
	public By achlname=By.name("lastName");
	public By achregion=By.xpath("//*[text()='United States']");
	public By selctregion=By.xpath("//*[@placeholder='Select Region of your Account']");
	public By bankn=By.name("institutionName");
	public By routingn=By.name("routingNumber");
	public By accnum=By.name("accountNumber");
	public By achusebilling= By.xpath("//*[text()='Use billing address on this account']");
	public By pcn=By.name("Avs_postal_code");
	public By achsave=By.xpath("//*[text()='Save']");
	public By achonlyx=By.xpath("//*[@class='theme-dropdown-10vqUT style-selectCardDropDown-3qyox_']/div[2]/div/div[@class='style-cardInfo-1KMMf_ checkout-account']");
	public By selectaddx=By.xpath("//*[text()='Edit / Add Payment Method']");
	 public By achx=By.xpath("//*[@class='style-cardInfo-1KMMf_ checkout-account']//*[@id='BANK']");
	 
	 public void addachmop()
	    {
	    
	    System.out.println("user is selecting/adding ach payment method");
	    JavascriptExecutor js = (JavascriptExecutor)getDriver();
	    WebDriverWait w= new WebDriverWait(getDriver(),10);
	    
	    
	    try
	    {
	    
	    //pass: in case of user has having multi mop along with paypal: ashish.tanwar@ticketmaster.com
	    if (checkElementPresent(multiplecx, 3)==true && checkElementPresent(achx, 3)==true)
	    {
	        
	        WebElement m1=getDriver().findElement(method1);
	        WebElement m2=getDriver().findElement(achx);
	        dropdown(m1,m2);
	        w.until(ExpectedConditions.elementToBeClickable(amount));
	        type(amount, "amount", "$1");
	        
	        click(accept, "accept",3);
	        click(submitx, "Submit",3);
	        
	    
	    }
	    
	    //in case user has multi mop but not,  userthree@mailinator.com/123456, userfour@mailinator.com/123456, userfive@mailinator.com/123456
	    else if(checkElementPresent(multiplecx, 3)==true && checkElementPresent(achx, 3)==false )
	    {
	        
	        
	        click(selectaddx, "Edit / add paymant");
	        WebElement addachbutton=getDriver().findElement(addNewCheckingAccount);
	        w.until(ExpectedConditions.elementToBeClickable(addachbutton));
	        click(addNewCheckingAccount, "Add ach mop",3);
	        
	        type(achfname, "fname", "ashish");
	        
	        type(achlname, "lname", "tanwar");
	        
	        WebElement webElement = getDriver().findElement(selctregion);
	        webElement.sendKeys(Keys.TAB);
	        webElement.sendKeys(Keys.ENTER);
	        
	        type(bankn, "bank name", "Amx");
	        //need to take given below dyna values
	        type(routingn, "routing number", create_rnum());
	        type(accnum, "accountnum", create_rnum());
	        type(pcn, "postal code", "123456", 1);
	        click(achsave, "save",3);
	        
	        // need to check why terms and condition opens after selecting cards
	        Thread.sleep(2000);
	        
	        w.until(ExpectedConditions.elementToBeClickable(amount));
	            type(amount, "amount", "$1");
	    
	            click(accept, "accept",3);
	            click(submitx, "Submit",3);
	        
	    }
	    
	    //pass: in case user has not any mop: achuser@gmail.com
	    else if(checkIfElementPresent(Addx, 3)==true)
	    {
	            click(Addx, "Add Payment Method");
	                WebElement addachbutton=getDriver().findElement(addNewCheckingAccount);
	                w.until(ExpectedConditions.elementToBeClickable(addachbutton));
	                
	                click(addNewCheckingAccount, "Add ach mop",3);
	                
	                type(achfname, "fname", "ashish");
	                
	                type(achlname, "lname", "tanwar");
	                
	                WebElement webElement = getDriver().findElement(selctregion);
	                webElement.sendKeys(Keys.TAB);
	                webElement.sendKeys(Keys.ENTER);
	                
	                type(bankn, "bank name", "Amx");
	            
	                type(routingn, "routing number", create_rnum());
	                type(accnum, "accountnum", create_rnum());
	                
	                click(achusebilling, "click use billing address",1);
	                type(pcn, "postal code", "123456", 1);
	                click(achsave, "save",3);
	                                
	                w.until(ExpectedConditions.elementToBeClickable(amount));
	                type(amount, "amount", "$1");
	                    click(accept, "accept",3);
	                    click(submitx, "Submit",3);
	    }
	    
	    else
	    {
	        //pass: user has one mop as ach:  manu@mailinator.com/123456
	        if(checkIfElementPresent(achx, 3)==true)
	        {
	            w.until(ExpectedConditions.elementToBeClickable(amount));
	            type(amount, "amount", "$1",2);
	            
	            click(accept, "accept",2);
	            click(submitx, "Submit",2);
	        }
	    }
	    
	    
	   }
	catch(Exception e)
	{
	        e.printStackTrace();
	}    
	    
	    }
	
	//add any card as mop
	public By multiplecx=By.xpath("//*[@class='style-activeCardLabelEllipsis-2gPdKI style-placeHolder-bcALoC'][text()='Select Payment Method']");
	public By method3= By.xpath("//*[@class='style-activeCardLabelEllipsis-2gPdKI']"); 
	
	public void addpaymentmethod()
	{
		System.out.println("user is selecting/adding payment method");
		JavascriptExecutor js = (JavascriptExecutor)getDriver();
		
		String cardnumber=null;
		String cvv=null;
		
		// in case of new user i.e. not having any card
if(checkIfElementPresent(Addx, 3))
	{
	
	try
	{
	
		click(Addx, "Add Payment Method");
		click(addmethodx, "Add new card");
		sync(500L);
		type(firstname, "input firstname", "ashish", 1);
		type(lname, "last name", "tanwar", 1);
		type(cardnum, "card number", "4111111111111111", 1);
		
		WebElement mmx=getDriver().findElement(monthx);
		WebElement mmv=getDriver().findElement(monthv);
		WebElement yyx=getDriver().findElement(yearx);
		WebElement yyv=getDriver().findElement(yearv);
		
		dropdown(mmx,mmv);
		dropdown(yyx,yyv);
		WebElement bill1 =getDriver().findElement(billcheck);
		
		js.executeScript("arguments[0].click();",bill1 );
		
		type(postal, "postalcode", "123456", 1);
		
		WebElement savec =getDriver().findElement(savecardx);
		js.executeScript("arguments[0].click();", savec);
		
		
		click(savex, "save",2);
		
		
		WebElement amountx=getDriver().findElement(amount);
		//clear(amountx);
		type(amount, "amount", "$1");
		
		cardnumber=getDriver().findElement(method3).getText();
		cvv=last_three_digits(cardnumber);
		
		type(cvcx, "cvc", cvv);
		
		click(accept, "accept",3);
		click(submitx, "Submit",3);
	
	}
	catch(Exception exp)
	{
		exp.printStackTrace();
	}
			
	
	}
		
//in case of existing user having multiple cards
	else if (checkElementPresent(multiplecx, 3))
	{
		try {
			
			
			WebElement m1=getDriver().findElement(method1);
			WebElement m2=getDriver().findElement(method2);
			dropdown(m1,m2);
      
		type(amount, "amount", "$1");
		
		cardnumber=getDriver().findElement(method3).getText();
		cvv=last_three_digits(cardnumber);
		System.out.println(cvv);
		
       type(cvcx, "cvc", cvv);
		click(accept, "accept",3);
	      click(submitx, "Submit",3);
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
	}

//in case user has single cards
	else
	{

		try {
			
      
		type(amount, "amount", "$1");
		
		cardnumber=getDriver().findElement(method3).getText();
		cvv=last_three_digits(cardnumber);
		
       type(cvcx, "cvc", cvv);
		click(accept, "accept",3);
	      click(submitx, "Submit",3);
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	}
	
	//payment validation
	public By amountpaid_x=By.xpath("//*[text()='Amount Paid']");
	public By amountpaid=By.xpath("//*[@class='confirmation-style-darkText-20xd-K confirmationAmountPaidValue']");
	public By errorpass=By.xpath("//*[@class='style-message-vInLxY']/h5");
	
	public void paymentverify()
	{
		String exp_amount="Amount Paid";
		
		
	try 
	{
		String amount_label=getDriver().findElement(amountpaid_x).getText();
		
		if(checkIfElementPresent(amountpaid_x, 2))
		{
		
		Assert.assertEquals(amount_label, exp_amount);
		String ap= getDriver().findElement(amountpaid).getText();
		System.out.println(ap);
		}
		else
		{
			System.out.println(" Checkout confirmation page not displayed");
		}
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	}
	
	
	//enable typeform via cms and configured its work space if not already
	
	public void verifiestypeformadminsetup()
	{	
			try {
				if(checkIfElementPresent(checkbox, 2)==false)
				{
					click(enabletypeformcheckbox, "enable typeform", 2);
				
					click(saveconfigurationid, "Save Configuration",2);	
				}
				
				if(getDriver().findElement(typeformworkspaceid).getAttribute("typeform_workspace_id")=="" || getDriver().findElement(typeformworkspaceid).getAttribute("typeform_workspace_id")==null)
				{
					getDriver().findElement(typeformworkspaceid).clear();	
				type(typeformworkspaceid, "workspaceid", "cfER3z", 2);
				click(saveconfigurationid, "Save Configuration",2);	
				}
			
			    } 
			
			catch (Exception e) 
			{
				e.printStackTrace();
								
			}
			
			finally
			{
				click(saveconfigurationid, "Save Configuration",2);	
			}
		}
		
	
	public By selecttext=By.xpath(".//*[@id='openPopup']/span[text()='SELECT TYPEFORM']");

	public void typeform_selection() throws InterruptedException
	{
		WebDriverWait wait= new WebDriverWait(getDriver(), 20);
		JavascriptExecutor ex = (JavascriptExecutor)getDriver();
		WebElement active_toggle=getDriver().findElement(By.xpath(cmstoggle));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(cmstoggle)));
		boolean status = false;
		int counter = 3;
		WebElement typeform_summary;
		utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		By submit = By.id("edit-submit");
		
		
		if(!checkIfElementPresent(invoice_active, 5))
		{
			active_toggle.click();
		
		}
		
		
		utils.navigateTo("/admin/invoice/1/edit?destination=admin/invoice/list");	
		//ex.executeScript("history.go(0)");
		
		WebElement questiontab=getDriver().findElement(By.xpath(questions));
		questiontab.click();
		
		if(checkIfElementPresent(selecttext, 3))
		{
			try {
						
			By questionsTab = By.xpath(".//span[text()='QUESTIONS']");
			By embedTypeform = By.xpath(".//*[@id='openPopup']/span[text()='EMBED TYPEFORM'] | .//*[@id='openPopup']/span[text()='SELECT TYPEFORM']");
			//
			ex.executeScript("arguments[0].click();", getDriver().findElement(questionsTab));
			
			if(checkIfElementPresent(embedTypeform))
			
			{
				WebElement we = getElementWhenVisible(embedTypeform);
				click(we, "Select Typeform");
				
				By typeform_automation = By.cssSelector("a[title='Automation']");
			
				//refreshing typeform	
				do {
					status = checkIfElementPresent(typeform_automation);
					if (!status) {
						getDriver().navigate().refresh();
						sync(500L);
						questiontab.click();
						click(we, "Select Typeform");
						
					}
					counter--;
				} while (counter > 0 && status == false);
				
				
				WebElement wtypeform_automation = getElementWhenVisible(typeform_automation);
				
				scrollingToElementofAPage(wtypeform_automation); //scrolling not working
				wtypeform_automation.click();
				By save = By.id("typeform-accepted");
				ex.executeScript("arguments[0].click();", getDriver().findElement(save));
			
			}
			
			By dropDown = By.cssSelector("div[class*='typeform'] input[class*='select-dropdown']");
			WebElement wdropDown = getElementWhenRefreshed(dropDown, "disabled", "null", 2);
			
			//controlling summary  position of typeform for client 
			if(!wdropDown.getAttribute("value").trim().equalsIgnoreCase(showAfter.trim())) {
				ex.executeScript("arguments[0].click();", wdropDown);
				By locator = By.xpath(".//div[contains(@class, 'typeform')]//ul//li//span[text()='" + showAfter.trim() + "']");
				//ex.executeScript("arguments[0].click();", getDriver().findElement(locator));
				wait.until(ExpectedConditions.elementToBeClickable(locator));
				typeform_summary=getDriver().findElement(locator);
				typeform_summary.click();	
			}
			
			By allow_multiple_submissions = By.cssSelector("label[for*='typeform-allow-multiple-submission'] input");
			WebElement wallow_multiple_submissions = getDriver().findElement(allow_multiple_submissions);
			
			if(allowMultipleSubmission && wallow_multiple_submissions.getAttribute("checked") == null) 
			{
				ex.executeScript("arguments[0].click();", wallow_multiple_submissions);
			} 
			
			else if(!allowMultipleSubmission && wallow_multiple_submissions.getAttribute("checked") != null) {
				ex.executeScript("arguments[0].click();", wallow_multiple_submissions);
			}
			
			ex.executeScript("arguments[0].click();", getDriver().findElement(dashboardconfigsave));
			ex.executeScript("arguments[0].click();", getDriver().findElement(submit));
			
			By alert = By.cssSelector("div[role='alert']");
			getElementWhenVisible(alert, 5);
			}
			
		catch(Exception e)	
			{
			e.printStackTrace();
			}
			
			finally 
			{	
		 	System.out.println("user is getting logs out");
		 	
		 
		 //	getDriver().navigate().to("https://stg1-am.ticketmaster.com/namautomation/user/logout");
			
		 	getDriver().navigate().to(Environment.get("APP_URL")+"/user/logout");
		 	
			getElementWhenPresent(By.xpath(".//input[@name='email'] | .//div[@class='mobile-signin']//*[text()='Sign In'] | .//div[@class='desktop-signin-dashboard']//a[text()='Sign In']"));
			}
		}
		else
			{
			System.out.println("typeform already selected");

			boolean isSelectTypeformDisabled = getElementWhenVisible(By.id(selecttypeform)).getAttribute("class").contains("removesign-button-text");
			if(isSelectTypeformDisabled) {
				WebElement removesign = getElementWhenVisible(By.xpath("//button[@id=\"openPopup\"]/following-sibling::*[contains(@class,'removesign')]"), 2);
				removesign.click();
			}
			
			// click and select type form
			click(getDriver().findElement(By.id(selecttypeform)), "Select Typeform");
			WebElement typeform = getElementWhenClickable(By.xpath("//a[@title='Automation' and @class='typeform_anchor']"), 30);
			typeform.click();
			// Save the Select Typeform
			click(getDriver().findElement(By.xpath("//*[@id=\"typeform-accepted\"]")), "Submit Typeform (Save)");
			
			
			By dropDown = By.cssSelector("div[class*='typeform'] input[class*='select-dropdown']");
			WebElement wdropDown = getElementWhenRefreshed(dropDown, "disabled", "null", 20);
			
			if(!wdropDown.getAttribute("value").trim().equalsIgnoreCase(showAfter.trim())) 
			{
				ex.executeScript("arguments[0].click();", wdropDown);
				By locator = By.xpath(".//div[contains(@class, 'typeform')]//ul//li//span[text()='" + showAfter.trim() + "']");
				//ex.executeScript("arguments[0].click();", getDriver().findElement(locator));
				wait.until(ExpectedConditions.elementToBeClickable(locator));
				typeform_summary=getDriver().findElement(locator);
				typeform_summary.click();
				//ex.executeScript("arguments[0].click();", getDriver().findElement(dashboardconfigsave));
				
				
			}
			ex.executeScript("arguments[0].click();", getDriver().findElement(submit));
			getDriver().navigate().to(Environment.get("APP_URL")+"/user/logout");
			 //	getDriver().navigate().to("https://stg1-am.ticketmaster.com/namautomation/user/logout");
			//utils.navigateTo("/user/logout");
			getElementWhenPresent(By.xpath(".//input[@name='email'] | .//div[@class='mobile-signin']//*[text()='Sign In'] | .//div[@class='desktop-signin-dashboard']//a[text()='Sign In']"));
			
			}
	}
	
	public void clickInvoices() {
		click(invoices,"Invoices");
	}
	
	public void clickAddNewInvoice() {
		click(addNewInvoice,"Add New Invoice");
		((JavascriptExecutor) getDriver()).executeScript("$('.close').trigger('click')");
		getDriver().findElement(invoiceTitle).clear();
		getDriver().findElement(invoiceTitle).sendKeys(invoiceName);
	}
	
	public void clickViewInvoicesClickGear() {
		click(addNewInvoice,"Add New Invoice");
		click(viewInvoices, "View Invoice");
		click(settingGear,"Setting Gear");
	}
	
	public void enterInvoiceTitleAndAllLabels() throws Exception {
		((JavascriptExecutor) getDriver()).executeScript("$('.close').trigger('click')");
		type(summaryTab, "Summary Name", summaryName);
		type(paymentTab, "Payment Name", paymentName);
		type(reviewTab, "Review Name", reviewName);
		type(donationTab, "Donation Name", donationName);
		type(addOnTab, "Add On Name", addOnName);
		type(questionTab, "Question Name", questionName);
		type(subtotalLabel, "Subtotal Name", subtotalName);
		type(amountDueLabel, "Amount Due Name", amountDueName);
		type(payTodayLabel, "Pay Today Name", paytodayName);
	}
	
	public void switchToParentFrame()
	{
		getDriver().switchTo().parentFrame();
	}
	
	public void enterInvoiceSummary() throws Exception {
		click(invoiceSummarySection, "Invoice Summary");
		
		try{
		WebElement ele = getDriver().findElement(By.xpath("//iframe[@aria-describedby=\"cke_74\"]"));
		getDriver().switchTo().frame(ele);
		//click(By.xpath("//body"), "Body");
	//	type(invoiceSummaryMessage, "Summary Message", "Test");
		getDriver().findElement(By.xpath("(//*[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders'])[1]")).clear();
		getDriver().findElement(By.xpath("(//*[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders'])[1]")).sendKeys(invoiceSummaryMessage);
		switchToParentFrame();
		}
		catch(Exception E)
		{
			switchToParentFrame();
			getDriver().findElement(By.xpath("(//textarea)[1]")).clear();
			type(By.xpath("(//textarea)[1]"), "Invoice Summary Message", invoiceSummaryMessage);
		}
		type(fieldName, "Field Name", fieldNameAuto);
	}
	
	public void enterPaymentDetails() throws Exception {
		click(paymentSection, "Payment Tab");
		type(lastNameLabel, "Last Name", lastName);
		type(stateLabel, "Sur Name", state);
		type(cityLabel, "City Label", city);
		type(zipCode, "Zip Code", zip);
	}
	
	public void enterReviewDetailsAndClickSubmit() throws Exception {
		click(reviewSection,"Review Tab");
		WebElement ele = getDriver().findElement(By.xpath("//iframe[@aria-describedby=\"cke_186\"]"));
		try{getDriver().switchTo().frame(ele);
		getDriver().findElement(By.xpath("(//*[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders'])[1]")).clear();
		getDriver().findElement(By.xpath("(//*[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders'])[1]")).sendKeys(invoiceReviewMessage);
		switchToParentFrame();}
		catch(Exception E)
		{
			sync(1000l);
			switchToParentFrame();
			getDriver().findElement(By.xpath("(//textarea)[4]")).clear();
			type(By.xpath("(//textarea)[4]"), "Review Message", invoiceReviewMessage);
		}
		click(submit, "Submit");
		//click(toggle, "Disable Default Invoice");
		System.out.println();
	}
	
	public void clickSettingAndSignInComponent() {
		click(setting, "Setting");
		click(signInComponent,"Sign In Componenet");
	}
	
	public void enterCreateAccountInterstitialDetails() throws Exception {
		click(interstitialGear, "Interstitial Setting");
		((JavascriptExecutor) getDriver()).executeScript("$('.close').trigger('click')");
		type(createAccountInput, "create account", createHeaderText);
		type(signUpInput, "Sign Up Text", signUpText);
		type(firstInput, "First name", firstNameSignUpText);
		getDriver().findElement(lastInput).clear();
		getDriver().findElement(lastInput).sendKeys(lastNameSignUpText);
		//type(lastInput, "Last name", lastNameSignUpText);
		type(signUpEmail, "Sign Up Email", emailSignUp);
		type(signUpPassword, "Sign Up Password", passwordSignUp);
		type(signUpRemember, "Sign Up Remember", rememberSignUp);
	}
	
	public void enterSignInInterstitialDetails() throws Exception {
		click(signInTab, "Sign In");
		((JavascriptExecutor) getDriver()).executeScript("$('.close').trigger('click')");
		getDriver().findElement(signInHeader).clear();
		getDriver().findElement(signInHeader).sendKeys(signInText);
		//type(signInHeader, "Sign In Text", signInText);
		type(signInButtonText, "Sign In Button Text", signInButton);
		type(signInEmail, "Sign In Email Text", signInEmailText);
		type(signInPassword, "Sign In Password Text", signInPasswordText);
		type(signInRemember, "Sign In Remember Me Text", signInRememberText);
	}
	
	public void enterForgetPasswordInterstitialDetailsAndSubmit() throws Exception {
		click(forgotPassword, "Forget Password");
		type(forgotPasswordHeader, "Forget Password Text", forgotPasswordHeaderText);
		type(forgotPasswordButtonText, "Forget Password Button Text", forgotPasswordButton);
		type(forgotPasswordDescription, "Forget Password Description", forgotPasswordDescriptionText);
		type(forgotPasswordEmail, "Forget Password Email", forgotPasswordEmailText);
		click(interstitialSaveCMS, "Submit");
	}
	
	public void enterCreateAccountHomePageDetails() throws Exception {
		click(homePageGear, "HomePage Setting");
		type(createAccountInput, "create account", createHeaderTextHome);
		type(signUpInput, "Sign Up Text", signUpTextHome);
		type(firstInput, "First name", firstNameSignUpTextHome);
		getDriver().findElement(lastInput).clear();
		getDriver().findElement(lastInput).sendKeys(lastNameSignUpTextHome);
		//type(lastInput, "Last name", lastNameSignUpTextHome);
		type(signUpEmail, "Sign Up Email", emailSignUpHome);
		type(signUpPassword, "Sign Up Password", passwordSignUpHome);
		type(signUpRemember, "Sign Up Remember", rememberSignUpHome);
	}
	
	public void enterSignInHomePageDetails() throws Exception {
		click(signInTab, "Sign In");
		((JavascriptExecutor) getDriver()).executeScript("$('.close').trigger('click')");
		getDriver().findElement(signInHeader).clear();
		getDriver().findElement(signInHeader).sendKeys(signInTextHome);
		//type(signInHeader, "Sign In Text", signInTextHome);
		type(signInButtonText, "Sign In Button Text", signInButtonHome);
		type(signInEmail, "Sign In Email Text", signInEmailTextHome);
		type(signInPassword, "Sign In Password Text", signInPasswordTextHome);
		type(signInRemember, "Sign In Remember Me Text", signInRememberTextHome);
	}
	
	public void enterForgetPasswordHomePageDetailsAndSubmit() throws Exception {
		click(forgotPassword, "Forget Password");
		type(forgotPasswordHeader, "Forget Password Text", forgotPasswordHeaderTextHome);
		type(forgotPasswordButtonText, "Forget Password Button Text", forgotPasswordButtonHome);
		type(forgotPasswordDescription, "Forget Password Description", forgotPasswordDescriptionTextHome);
		type(forgotPasswordEmail, "Forget Password Email", forgotPasswordEmailTextHome);
		click(interstitialSaveCMS, "Submit");
	}
	
	public void enterCreateAccountClaimDetails() throws Exception {
		if(checkElementPresent(bannerclose, 10)) {
			System.out.println("Bannerclose");
			click(bannerclose, "Banner Close",2);
		}
		click(claimTicketGear, "Claim Setting");
		type(createAccountInput, "create account", createHeaderTextClaim);
		type(signUpInput, "Sign Up Text", signUpTextClaim);
//		type(firstInput, "First name", firstNameSignUpTextClaim);
		
		 JavascriptExecutor js = (JavascriptExecutor)getDriver();
		 js.executeScript("arguments[0].value='firstNameSignUpTextClaim';",getDriver().findElement(firstInput));
		
		
		getDriver().findElement(lastInput).clear();
		getDriver().findElement(lastInput).sendKeys(lastNameSignUpTextClaim);
		//type(lastInput, "Last name", lastNameSignUpTextClaim);
		type(signUpEmail, "Sign Up Email", emailSignUpClaim);
		type(signUpPassword, "Sign Up Password", passwordSignUpClaim);
		type(signUpRemember, "Sign Up Remember", rememberSignUpClaim);
	}
	
	public void enterSignInClaimDetails() throws Exception {
		click(signInTab, "Sign In");
		((JavascriptExecutor) getDriver()).executeScript("$('.close').trigger('click')");
		getDriver().findElement(signInHeader).clear();
		getDriver().findElement(signInHeader).sendKeys(signInTextClaim);
		//type(signInHeader, "Sign In Text", signInTextClaim);
		type(signInButtonText, "Sign In Button Text", signInButtonClaim);
		type(signInEmail, "Sign In Email Text", signInEmailTextClaim);
		type(signInPassword, "Sign In Password Text", signInPasswordTextClaim);
		type(signInRemember, "Sign In Remember Me Text", signInRememberTextClaim);
	}
	
	public void enterForgetPasswordClaimDetailsAndSubmit() throws Exception {
		click(forgotPassword, "Forget Password");
		type(forgotPasswordHeader, "Forget Password Text", forgotPasswordHeaderTextClaim);
		type(forgotPasswordButtonText, "Forget Password Button Text", forgotPasswordButtonClaim);
		type(forgotPasswordDescription, "Forget Password Description", forgotPasswordDescriptionTextClaim);
		type(forgotPasswordEmail, "Forget Password Email", forgotPasswordEmailTextClaim);
		click(interstitialSaveCMS, "Submit");
	}
	
	public void enterChangePasswordDetailsAndSubmit() throws Exception {
		click(changePasswordGear, "Change Password");
		type(changePasswordTitle, "Change Password Text", changePasswordTitleText);
		type(changePasswordSubmitText, "Change Password Submit Text", changePasswordSubmitButtonText);
		type(changePasswordCurrentPassword, "Change Password Current Password", changePasswordCurrentPasswordText);
		type(changePasswordNewPassword, "Change Password New Password", changePasswordNewPasswordText);
		type(changePasswordConfirmPassword, "Change Password Confirm Password", changePasswordConfirmPasswordText);
		click(interstitialSaveCMS, "Submit");
	}
	
	public void verifyHomePageSignInLabel() throws Exception {
		click(invokeAccount, "Invoke Account");
		Assert.assertTrue(getText(signInTitleUI).contains(signInTextHome));
		Assert.assertTrue(getText(signInEmailUI).contains(signInEmailTextHome));
		Assert.assertTrue(getText(signInPasswordUI).contains(signInPasswordTextHome));
		Assert.assertTrue(getText(signInSignInButtonUI).contains(signInButtonHome));
		Assert.assertTrue(getText(signInRememberUI).contains(signInRememberTextHome));
	}
	
	public void verifyInterstitialSignInLabel() throws Exception {
		Assert.assertTrue(getText(signInTitleUI).contains(signInText));
		Assert.assertTrue(getText(signInEmailUI).contains(signInEmailText));
		Assert.assertTrue(getText(signInPasswordUI).contains(signInPasswordText));
		Assert.assertTrue(getText(signInSignInButtonUI).contains(signInButton));
		Assert.assertTrue(getText(signInRememberUI).contains(signInRememberText));
	}
	
	public void verifyClaimSignInLabel() throws Exception {
		Assert.assertTrue(getText(signInTitleUI).contains(signInTextClaim));
		Assert.assertTrue(getText(signInEmailUI).contains(signInEmailTextClaim));
		Assert.assertTrue(getText(signInPasswordUI).contains(signInPasswordTextClaim));
		Assert.assertTrue(getText(signInSignInButtonUI).contains(signInButtonClaim));
		Assert.assertTrue(getText(signInRememberUI).contains(signInRememberTextClaim));
	}
	
	public void verifyHomePageSignUpLabel() throws Exception {
		click(signUpLink, "Click Here");
		Assert.assertTrue(getText(signInTitleUI).contains(createHeaderTextHome));
		Assert.assertTrue(getText(signInEmailUI).contains(emailSignUpHome));
		Assert.assertTrue(getText(signInPasswordUI).contains(passwordSignUpHome));
		Assert.assertTrue(getText(signInSignInButtonUI).contains(signUpTextHome));
		Assert.assertTrue(getText(firstNameSignUpUI).contains(firstNameSignUpTextHome));
		System.out.println(getText(lastNameSignUpUI));
		System.out.println(lastNameSignUpTextHome);
		Assert.assertTrue(getText(lastNameSignUpUI).contains(lastNameSignUpTextHome));
		click(backToSignInLink, "Click Here");
	}
	
	public void verifyInterstitialSignUpLabel() throws Exception {
		click(signUpLink, "Click Here");
		Assert.assertTrue(getText(signInTitleUI).contains(createHeaderText));
		Assert.assertTrue(getText(signInEmailUI).contains(emailSignUp));
		Assert.assertTrue(getText(signInPasswordUI).contains(passwordSignUp));
		Assert.assertTrue(getText(signInSignInButtonUI).contains(signUpText));
		Assert.assertTrue(getText(firstNameSignUpUI).contains(firstNameSignUpText));
		Assert.assertTrue(getText(lastNameSignUpUI).contains(lastNameSignUpText));
		click(backToSignInLink, "Click Here");
	}
	
	public void verifyClaimSignUpLabel() throws Exception {
		click(signUpLink, "Click Here");
		Assert.assertTrue(getText(signInTitleUI).contains(createHeaderTextClaim));
		Assert.assertTrue(getText(signInEmailUI).contains(emailSignUpClaim));
		Assert.assertTrue(getText(signInPasswordUI).contains(passwordSignUpClaim));
		Assert.assertTrue(getText(signInSignInButtonUI).contains(signUpTextClaim));
		Assert.assertTrue(getText(firstNameSignUpUI).contains(firstNameSignUpTextClaim));
		Assert.assertTrue(getText(lastNameSignUpUI).contains(lastNameSignUpTextClaim));
		click(backToSignInLink, "Click Here");
	}
	
	public void verifyHomePageForgotPasswordLabel() throws Exception {
		click(invokeAccount, "Invoke Account");
		click(forgotPasswordLink, "Forgot Password");
		Assert.assertTrue(getText(signInTitleUI).contains(forgotPasswordHeaderTextHome));
		Assert.assertTrue(getText(signInEmailUI).contains(forgotPasswordEmailTextHome));
		Assert.assertTrue(getText(signInSignInButtonUI).contains(forgotPasswordButtonHome));
		Assert.assertTrue(getText(componentSubHeading).contains(forgotPasswordDescriptionTextHome));
	}
	
	public void verifyInterstitialForgotPasswordLabel() throws Exception {
		click(forgotPasswordLink, "Forgot Password");
		Assert.assertTrue(getText(signInTitleUI).contains(forgotPasswordHeaderText));
		Assert.assertTrue(getText(signInEmailUI).contains(forgotPasswordEmailText));
		Assert.assertTrue(getText(signInSignInButtonUI).contains(forgotPasswordButton));
		Assert.assertTrue(getText(componentSubHeading).contains(forgotPasswordDescriptionText));
	}
	
	public void verifyClaimForgotPasswordLabel() throws Exception {
		click(forgotPasswordLink, "Forgot Password");
		Assert.assertTrue(getText(signInTitleUI).contains(forgotPasswordHeaderTextClaim));
		Assert.assertTrue(getText(signInEmailUI).contains(forgotPasswordEmailTextClaim));
		Assert.assertTrue(getText(signInSignInButtonUI).contains(forgotPasswordButtonClaim));
		Assert.assertTrue(getText(componentSubHeading).contains(forgotPasswordDescriptionTextClaim));
	}
	
	public void verifyChangePasswordLabels() throws Exception {
		Assert.assertTrue(getText(signInTitleUI).contains(changePasswordTitleText));
		Assert.assertTrue(getText(changePasswordCurrentVerify).contains(changePasswordCurrentPasswordText));
		Assert.assertTrue(getText(changePasswordNewVerify).contains(changePasswordNewPasswordText));
		Assert.assertTrue(getText(changePasswordConfirmVerify).contains(changePasswordConfirmPasswordText));
		Assert.assertTrue(getText(changePasswordSubmitVerify).contains(changePasswordSubmitButtonText));
	}

	public void enableBulk() {
		click(ticketmanagement, "TICKET MANAGEMENT");
		click(ticketoptions, "TICKET OPTIONS");
		click(bulktoggle,"BULK TOGGLE");
		click(editsubmit,"SUBMIT changes");
	}

	public boolean checkBulkEnabled() {
		try {
			long value =  (long)((JavascriptExecutor)this.getDriver()).executeScript("return drupalSettings.componentConfigData.siteconfig.manage_ticket_configuration.bulk_transfer_enabled");
			if(value==1)
				return true;
		} catch (WebDriverException var5) {
			var5.getMessage();
		}
		return false;
	}

	public boolean checkenableSecureBarcode() {
		try {
			long value =  (long)((JavascriptExecutor)this.getDriver()).executeScript("return window.drupalSettings.componentConfigData.siteconfig.manage_ticket_configuration.secure_barcode_enabled");
			if(value==1)
				return true;
		} catch (WebDriverException var5) {
			var5.getMessage();
		}
		return false;
	}
	
	public boolean checkQDEnabled() {
		try {
			long value =  (long)((JavascriptExecutor)this.getDriver()).executeScript("return drupalSettings.componentConfigData.siteconfig.manage_ticket_configuration.donate");
			if(value==1)
				return true;
		} catch (WebDriverException var5) {
			var5.getMessage();
		}
		return false;
	}
	
	public boolean checkenableEDP() {
		try {
			long value =  (long)((JavascriptExecutor)this.getDriver()).executeScript("return window.drupalSettings.componentConfigData.siteconfig.manage_ticket_configuration.eventslistView");
			if(value==1)
				return true;
		} catch (WebDriverException var5) {
			var5.getMessage();
		}
		return false;
	}
	
	public void enableQD() {
		click(donation, "DONATION");
		click(quickdonation, "Quick Donation");
		if(!checkIfElementPresent(quickdonationenabled,5))
			click(quickdonationenablecb,"Quick Donation CB");
		click(editsubmit,"SUBMIT changes");
	}
	
	public void clickAddPageButton() {
		click(pagemanager,"Page Manager",5);
		click(addpage, "Add Page",5);
		Assert.assertTrue(getDriver().getCurrentUrl().contains("page-manager"));
	}
	
	public void clickAddNewMenuButton() {
		click(menumanager,"Menu Manager",5);
		click(addnewmenu, "Add New Menu",5);
		Assert.assertTrue(getDriver().getCurrentUrl().contains("menu-manager"));
	}
	
	public void clickAddpageButtonUnderMarketingExperience() {
		click(marketingExperienceAddPage, "Add Page",5);
		Assert.assertTrue(getDriver().getCurrentUrl().contains("page-manager"));
	}
	
	public void clickViewMenuButton() {
		click(menumanager,"Menu Manager",5);
		click(viewmenus, "View Menu",5);
		Assert.assertTrue(getDriver().getCurrentUrl().contains("menu-listing"));
	}
	
	public void selectHomePageButton() {
		click(selectbutton,"Select",5);
		Assert.assertTrue(getText(ticketsalesverification).contains("Ticket Sales"));
		Assert.assertTrue(getDriver().getCurrentUrl().contains("hybrid_home_page"));
	}

	public void verifyTicketsSalespage() {
		Assert.assertTrue(getText(ticketsalestext).contains("Ticket Sales layouts are designed around ticket on-sales including new season tickets"));
		List<WebElement> element = getDriver().findElements(formpresent);
		for(int i=1; i<=element.size();i++) {
	    	  By form = By.xpath("//div[@id='edit-ticket-sales']/div["+i+"]/label//div[2]");	
	    	  Assert.assertTrue((getText(form, 0)!=""),getText(form, 0));
	      }
	}  
	
	
	public void enableEDP() {
		click(ticketmanagement, "TICKET MANAGEMENT");
		click(ticketoptions, "TICKET OPTIONS");
		click(edptoggle,"EDP TOGGLE");
		click(editsubmit,"SUBMIT changes");
	}
	
	public void enableSecureBarcode() {
		click(ticketmanagement, "TICKET MANAGEMENT");
		click(ticketoptions, "TICKET OPTIONS");
		click(secureBarcode,"Secure Barcode TOGGLE");
		click(editsubmit,"SUBMIT changes");
	}
	
	public String versionUI() {
	  return	getText(buildtext, 2);
	}
	
	public String versionAPI() {
		try {
			String value =  (String)((JavascriptExecutor)this.getDriver()).executeScript("return window.drupalSettings.componentConfigData.siteconfig.version");
				return value;
		} catch (WebDriverException var5) {
			var5.getMessage();
		}
		return "";
	}
	
	public void clickonEmailReporting() {
		click(emailreporting, "Email Reporting");
	}
	
	public void verifyEmailReportingPage() {
		utils.checkIfElementClickable(refressButton,10);
		Assert.assertTrue(getText(refressButton).contains("REFRESH"), "REFRESH Button Present");
		utils.checkIfElementClickable(downloadReportButton,10);
		Assert.assertTrue(getText(downloadReportButton).contains("DOWNLOAD REPORT"), "DOWNLOAD REPORT Button Present");
		utils.checkIfElementClickable(BOUNCEDEMAIL,10);
		Assert.assertTrue(getText(BOUNCEDEMAIL).contains("BOUNCED E-MAIL"), "BOUNCED E-MAIL Section Present");
		utils.checkIfElementClickable(MARKEDASSPAM,10);
		Assert.assertTrue(getText(MARKEDASSPAM).contains("MARKED AS SPAM"), "MARKED AS SPAM Section Present");
		utils.checkIfElementClickable(INVALIDEMAIL,10);
		Assert.assertTrue(getText(INVALIDEMAIL).contains("INVALID EMAIL"), "INVALID EMAIL Section Present");
		utils.checkIfElementClickable(BOUNCEDEMAILFooter,10);
		Assert.assertTrue(getText(BOUNCEDEMAILFooter).contains("BOUNCED E-MAIL"), "BOUNCED E-MAIL Section Present in footer");
		utils.checkIfElementClickable(MARKEDASSPAMFooter,10);
		Assert.assertTrue(getText(MARKEDASSPAMFooter).contains("MARKED AS SPAM"), "MARKED AS SPAM Section Present in footer");
		utils.checkIfElementClickable(INVALIDEMAILFooter,10);
		Assert.assertTrue(getText(INVALIDEMAILFooter).contains("INVALID E-MAIL"), "INVALID E-MAIL Section Present in footer");
		utils.checkIfElementClickable(SearchEmail,10);	
	}	
	
	
	public void clickTransactionalPages() {
		utils.sync(2000l);
		click(transactionalpages,"Transactional Pages",5);	
			
	}
	
	public void verifytransactionalpageselements() {
		utils.checkIfElementClickable(dashboard,10);
		Assert.assertTrue(getText(dashboard).contains("Dashboard"), "Dashboard is Present on Transactional Pages");
	    Dictionary.put("CMS_DashboardText", getText(dashboard, 2));
	    
		utils.checkIfElementClickable(myevents,10);
		Assert.assertTrue(getText(myevents).contains("My Events"), "My Events is Present on Transactional Pages");
		Dictionary.put("CMS_MyEvents", getText(myevents, 2));
		
		utils.checkIfElementClickable(invoice,10);
		Assert.assertTrue(getText(invoice).contains("Invoice"), "Invoice is Present on Transactional Pages");
		Dictionary.put("CMSMy_Invoice", getText(invoice, 2));
		
		utils.checkIfElementClickable(buy,10);
		Assert.assertTrue(getText(buy).contains("Buy"), "Buy is Present on Transactional Pages");
		Dictionary.put("CMS_Buy", getText(buy, 2));
		
		utils.checkIfElementClickable(upgrade,10);
		Assert.assertTrue(getText(upgrade).contains("Upgrade"), "Upgrade is Present on Transactional Pages");
		Dictionary.put("CMS_Upgrade", getText(upgrade, 2));
		
		utils.checkIfElementClickable(quickdonate,10);
		Assert.assertTrue(getText(quickdonate).contains("Quick Donate"), "Quick Donate is Present on Transactional Pages");	
		Dictionary.put("CMS_QuickDonate", getText(quickdonate, 2));
	}
	
	public void dragmenuelement() throws Exception {
		type(menunametextbox,"Menu Text Box", "TestAutomation",3);
		click(publiccheckBox,"Public Check Box",2);
		click(loggedIncheckBox, "Logged In Check Box",2);
		adminPanel.dragAndDrop(getElementWhenClickable(dashboard,3), getElementWhenClickable(draglocation,3));
		sync(1000l);
		adminPanel.dragAndDrop(getElementWhenClickable(myevents,3), getElementWhenClickable(draglocation1,3));
		sync(1000l);
		adminPanel.dragAndDrop(getElementWhenClickable(invoice,3), getElementWhenClickable(draglocation1,3));
		sync(1000l);
		adminPanel.dragAndDrop(getElementWhenClickable(buy,3), getElementWhenClickable(draglocation1,3));
		sync(1000l);
		adminPanel.dragAndDrop(getElementWhenClickable(upgrade,3), getElementWhenClickable(draglocation1,3));
		sync(1000l);
		adminPanel.dragAndDrop(getElementWhenClickable(quickdonate,3), getElementWhenClickable(draglocation1,3));
		sync(2000l);
		if(getElementWhenVisible(saveButton, 5).isEnabled()) {
			sync(1000l);
			click(saveButton,"Save Button",3);
			sync(3000l);
		}
	}
	
	public void slectactivemenu(String selectActiveMenu) {
		By selectactivemenu_xpath =By.xpath("//ul[contains(@id,'select-options') and contains(@style,'display: block;')]//span[text()='"+selectActiveMenu+"']");
		click(activePublicMenuDropdown, "Active Public Menu",2);
		if(getElementWhenVisible(selectactivemenu_xpath, 5).isEnabled()) {
			click(selectactivemenu_xpath,selectActiveMenu,3);
		}
		click(menulistingtext,"Menu listing Text");
		click(activeLoggedInDropdown, "Active Public Menu",2);
		if(getElementWhenVisible(selectactivemenu_xpath, 5).isEnabled()) {
			click(selectactivemenu_xpath,selectActiveMenu,3);
		}
		click(savebutton, "Save Button",2);	
		Assert.assertEquals(getText(savestatustext, 2), "Your settings have been saved" , "Setting Save Successfully");
	}
	
	
	public void verifyTransactionalPage(String pageType) {
		sync(1000l);
		Assert.assertEquals(getText(dashboard, 2), Dictionary.get("CMS_DashboardText"),"CMS transactional pages Dashboard is displayed on NAM forntend "+pageType+" LoggedIn");
		Assert.assertEquals(getText(myevents, 2), Dictionary.get("CMS_MyEvents"),"CMS transactional pages My Events is displayed on NAM forntend "+pageType+" LoggedIn");
		Assert.assertEquals(getText(invoice, 2), Dictionary.get("CMSMy_Invoice"),"CMS transactional pages Invoice is displayed on NAM forntend "+pageType+" LoggedIn");
		Assert.assertEquals(getText(buy, 2), Dictionary.get("CMS_Buy"),"CMS transactional pages Buy is displayed on NAM forntend "+pageType+" LoggedIn");
		Assert.assertEquals(getText(upgrade, 2), Dictionary.get("CMS_Upgrade"),"CMS transactional pages Upgrade is displayed on NAM forntend "+pageType+" LoggedIn");
		Assert.assertEquals(getText(quickdonate, 2), Dictionary.get("CMS_QuickDonate"),"CMS transactional pages Quick Donate is displayed on NAM forntend "+pageType+" LoggedIn");
	}
	
	public void selectPageType(String pageType) {
		if(checkElementPresent(closebuttonvideo, 3)) {
			click(closebuttonvideo,"Close Button",2);
		}
		click(contentPage(pageType), "Content Type > " + pageType, 2);	
		Assert.assertTrue(getDriver().getCurrentUrl().contains("home_page"));	
	}
	
	public void selectLayout(String layoutType) {
		click(layout(layoutType), "Layout Type > " + layoutType ,2);
		click(startEditing,"Start Editing",2);
		Assert.assertTrue(getDriver().getCurrentUrl().contains("node"));
	}
	
	public void fillPageSettingsData(String accessLevelInput) throws Exception {
		// accessLevelInput = public,ahsdjhasgd
		if (checkElementPresent(pageName, 2)) {
			type(pageName, "Page Name Text Box", "TestAutomation", 2);
		}
		
		if (checkElementPresent(pageTitle, 2)) {
			type(pageTitle, "Page Title Text Box", "TestAutomation", 2);
		}
		
		if (accessLevelInput.contains("Public")) {
			if (!getElementWhenClickable(accesslevel("Public")).isSelected()) {
				click(accesslevel("Public"), "Acess Level Selected" + "Public", 2);
			}
		} else {
			if (getElementWhenClickable(accesslevel("Public")).isSelected()) {
				click(accesslevel("Public"), "Acess Level DeSelected" + "Public", 2);
			}
		}
		if (accessLevelInput.contains("Logged In")) {
			if (!getElementWhenClickable(accesslevel("Logged In")).isSelected()) {
				click(accesslevel("Logged In"), "Acess Level Selected" + "Logged In", 2);
			}
		} else {
			if (getElementWhenClickable(accesslevel("Logged In")).isSelected()) {
				click(accesslevel("Logged In"), "Acess Level DeSelected" + "Logged In", 2);
			}
		}
	}

	
}


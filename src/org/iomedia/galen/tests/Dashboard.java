package org.iomedia.galen.tests;
import java.util.List;
import java.util.Set;
import java.util.Map;
import org.iomedia.framework.Driver;
import org.iomedia.galen.common.ManageticketsAPI;
import org.iomedia.galen.pages.DashboardHeader;
import org.iomedia.galen.pages.DashboardSection;
import org.iomedia.galen.pages.Hamburger;
import org.iomedia.galen.common.*;
import org.iomedia.galen.pages.Homepage;
import org.iomedia.galen.pages.Invoice;
import org.iomedia.galen.pages.InvoiceNew;
import org.iomedia.galen.pages.ManageTicket;
import org.iomedia.galen.pages.MobileSectionTabs;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Dashboard extends Driver {
	
	Utils utils;
	DashboardHeader header;
	DashboardSection dashboardSection;
	Homepage homepage;
	MobileSectionTabs section;
	Hamburger hamburger;
	ManageticketsAPI api;
	AccessToken accessToken;
	Invoice invoice;
	InvoiceNew invoiceNew;
	String emailaddress; 
	String password; 
	String driverType;
	
	@BeforeMethod(alwaysRun=true)
	public void init(){
		header = new DashboardHeader(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		dashboardSection = new DashboardSection(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
		homepage = new Homepage(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		section = new MobileSectionTabs(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
		api = new ManageticketsAPI(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert,sTestDetails);
		hamburger = new Hamburger(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		invoice = new Invoice(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		invoiceNew = new InvoiceNew(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		accessToken = new AccessToken(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert,sTestDetails);
		emailaddress = Dictionary.get("EMAIL_ADDRESS").trim();
		password = Dictionary.get("PASSWORD").trim();
		driverType = driverFactory.getDriverType().get();
	}
	

	@Test(groups={"smoke","dashboardUi","regression","prod"}, dataProvider="devices", priority = 1)
	public void verifyDashboardPage(TestDevice device) throws Exception{
		load("/dashboard");
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		homepage.login("", "", device, true);
		Assert.assertTrue(header.waitForDasboardHeader(), "Verify dashboard header is displayed");
		Assert.assertTrue(dashboardSection.waitForDasboardSection(device), "Verify dashboard section is displayed");
		if(device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile")) {
			section.clickQuickLinks();
		}
		ManageticketsAPI manageticketsAPI = new ManageticketsAPI(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		Map<String, Object> names = manageticketsAPI.getCustomerName();
		checkLayout(Dictionary.get("SPEC_FILE_NAME").trim(), device.getTags(), names);
		
	}
	
	@Test(groups={"smoke","ticketsUi", "regression","prod"}, dataProvider="devices", priority = 2)
	public void verifyDashboardPageOnClickingTicketsSectionOnDevices(TestDevice device) throws Exception{
		if(device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile")) {
			load("/dashboard");
			//If User Pass credentails from Jenkins
			utils.credentials_jenkins();
			homepage.login("", "", device, true);
			Assert.assertTrue(header.waitForDasboardHeader(), "Verify dashboard header is displayed");
			Assert.assertTrue(dashboardSection.waitForDasboardSection(device), "Verify dashboard section is displayed");
			section.clickTickets();
			checkLayout(Dictionary.get("SPEC_FILE_NAME"), device.getTags());
		} else {
			throw new SkipException("Skipped");
		}
	}
	
	@Test(groups={"smoke","invoicesUi", "regression","prod"}, dataProvider="devices", priority = 3)
	public void verifyDashboardPageOnClickingInvoicesSectionOnDevices(TestDevice device) throws Exception{
		if(device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile")) {
			load("/dashboard");
			//If User Pass credentails from Jenkins
			utils.credentials_jenkins();
			homepage.login("", "", device, true);
			Assert.assertTrue(header.waitForDasboardHeader(), "Verify dashboard header is displayed");
			Assert.assertTrue(dashboardSection.waitForDasboardSection(device), "Verify dashboard section is displayed");
			section.clickInvoices();
			checkLayout(Dictionary.get("SPEC_FILE_NAME"), device.getTags());
		} else {
			throw new SkipException("Skipped");
		}
	}
	
	@Test(groups={"dashboardFunctional","regression","prod"})
	public void verifyDashboardViewAllTickets() throws Exception {
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("Skipped");
		load("/dashboard");
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		homepage.login("", "", null, true);
		Assert.assertTrue(header.waitForDasboardHeader(), "Verify dashboard header is displayed");
		Assert.assertTrue(dashboardSection.waitForDasboardSection(null), "Verify dashboard section is displayed");
		dashboardSection.clickViewAllTickets();
		Assert.assertTrue(getDriver().getCurrentUrl().contains("/tickets") || getDriver().getCurrentUrl().contains("/myevents"));
	}
	
	@Test(groups={"dashboardFunctional","regression","prod"})
	public void verifyDashboardViewAllInvoices() throws Exception {
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))
			throw new SkipException("Skipped");
		load("/dashboard");
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		homepage.login("", "", null, true);
		Assert.assertTrue(header.waitForDasboardHeader(), "Verify dashboard header is displayed");
		Assert.assertTrue(dashboardSection.waitForDasboardSection(null), "Verify dashboard section is displayed");
		dashboardSection.clickViewAllInvoice();
		Assert.assertTrue(getDriver().getCurrentUrl().contains("/invoice"));
	}
	
	@Test(groups={"smoke","dashboardFunctional","regression","prod"})
	public void verifyNavLinksRedirection() throws Exception {
		int code = 0;
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS") || driverType.trim().toUpperCase().contains("SAFARI"))
			throw new SkipException("Skipped");
		load("/dashboard");
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		homepage.login("", "", null, true);		
		List<WebElement> links=dashboardSection.getAllLinks();
		System.out.println(links.size());
		for(int i=0; i < links.size() - 1 ;i++){
			String NavLinkName=links.get(i).getText();
			System.out.println("Status Check of Header Link:  "+NavLinkName);	
			Set<Cookie> cookies = getDriver().manage().getCookies();			 
			dashboardSection.clickLinks(links.get(i));
			if(!getDriver().getCurrentUrl().toString().contains(Environment.get("APP_URL").trim()) && !getDriver().getCurrentUrl().toString().contains(Environment.get("TM_HOST").trim())){
				code= dashboardSection.checkStatuscodeExternal(getDriver().getCurrentUrl());
				if(driverType.trim().toUpperCase().contains("SAFARI")) {
					((JavascriptExecutor) BaseUtil.getDriver()).executeScript("history.go(-1)");
				} else {
					getDriver().navigate().back();
				}
			} else {
				code=dashboardSection.checkStatuscode(getDriver().getCurrentUrl(), cookies);
			}
			System.out.println("Status code of " +NavLinkName+ " : "  + code);
		    Assert.assertEquals(code, 200, "URL is navigating to correct page");				 		
			links=dashboardSection.getAllLinks();	
		}	
	}
	
	@Test(groups={"smoke","regression","miscFunctional","prod"})
	public void verifyChangePassword() throws Exception {
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS") ||
				driverType.trim().toUpperCase().contains("SAFARI"))
			throw new SkipException("Skipped");
		
		load("/dashboard");
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		homepage.login("", "", null, true);
		Assert.assertTrue(getDriver().getCurrentUrl().contains("/dashboard"));
		boolean can_edit_password = api.canUserEditPassword();
		Set<Cookie> cookies = getDriver().manage().getCookies();
	    String text = dashboardSection.getChangePasswordText();
		if(!can_edit_password) {
			Assert.assertNotEquals(text.trim().toLowerCase(), "change password", "Verify change password option is not visible");
			utils.navigateTo("/user/change-password");
			int code = dashboardSection.checkStatuscode(Environment.get("APP_URL") + "/user/change-password", cookies);
			Assert.assertEquals(code, 404, "Access Denied for changing the password");
		} 
		else {
		    Assert.assertEquals(text.trim().toLowerCase(), "change password", "Verify change password option is visible");	
			dashboardSection.clickChangePassword();
			dashboardSection.typeChangePassword(Dictionary.get("PASSWORD").trim(), Dictionary.get("PASSWORD").trim());
			dashboardSection.clickSavePassword();
			BaseUtil.sync(10000L);
			Assert.assertTrue(hamburger.waitforLoggedInPage(null), "Verify dashboard header is displayed");
		}
	}
	
	@Test(groups={"smoke","dashboardFunctional","regression","prod"})
	public void verifyEventInvoicefromDashboard() throws Exception {	
		load("/dashboard");
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		homepage.login("", "", null, true);
		Assert.assertTrue(header.waitForDasboardHeader(), "Verify dashboard header is displayed");
		Assert.assertTrue(dashboardSection.waitForDasboardSection(null), "Verify dashboard section is displayed");
		Assert.assertTrue(getDriver().getCurrentUrl().contains("/dashboard"));
		
		/**------------------------------- Check Events-------------------------------- **/
		String ticketname = dashboardSection.clickDashboardEvent();
		ManageTicket managetickets = new ManageTicket(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
		Assert.assertTrue(getDriver().getCurrentUrl().contains("/tickets"));	
		Assert.assertEquals(dashboardSection.getTicketName(), ticketname, "Event Link is landing to correct page");
		if(driverType.trim().toUpperCase().contains("SAFARI")) {
			((JavascriptExecutor) BaseUtil.getDriver()).executeScript("history.go(-1)");
		} else {
			getDriver().navigate().back();
		}
		Assert.assertTrue(header.waitForDasboardHeader(), "Verify dashboard header is displayed");
		Assert.assertTrue(dashboardSection.waitForDasboardSection(null), "Verify dashboard section is displayed");
		
		/**------------------------------- Check Invoices-------------------------------- **/
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			MobileSectionTabs mobilesectiontabs = new MobileSectionTabs(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
			mobilesectiontabs.clickInvoices();
		}
		
		String name = dashboardSection.getInvoiceText();
		String InvoiceLink = dashboardSection.getInvoiceLink();
		dashboardSection.clickInvoice();
		Assert.assertTrue(invoice.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		Assert.assertTrue(getDriver().getCurrentUrl().trim().contains(InvoiceLink.trim()), "Invoice Link is landing to correct page");
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			//Do Nothing
		} else
			Assert.assertEquals(name, invoice.getInvoicePageText(), "Invoice is opened");		
	}
	
	@Test(groups={"smoke","dashboardFunctional","regression","prod", "invoiceNew"})
	public void verifyEventInvoicefromDashboardNew() throws Exception {	
		load("/dashboard");
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		homepage.login("", "", null, true);
		Assert.assertTrue(header.waitForDasboardHeader(), "Verify dashboard header is displayed");
		Assert.assertTrue(dashboardSection.waitForDasboardSection(null), "Verify dashboard section is displayed");
		Assert.assertTrue(getDriver().getCurrentUrl().contains("/dashboard"));
		
		/**------------------------------- Check Events-------------------------------- **/
		String ticketname = dashboardSection.clickDashboardEvent();
		ManageTicket managetickets = new ManageTicket(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		if (managetickets.checkenableEDP()==true) {
			Assert.assertTrue(managetickets.isTicketsListDisplayedEDP(null) , "Verify tickets listing page is displayed");
		}else {
			Assert.assertTrue(managetickets.isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
		}
		Assert.assertTrue(getDriver().getCurrentUrl().contains("/tickets") || getDriver().getCurrentUrl().contains("/myevents"));	
		Assert.assertTrue(ticketname.contains(header.getEventName()), "Event Link is landing to correct page");
		if(driverType.trim().toUpperCase().contains("SAFARI")) {
			((JavascriptExecutor) BaseUtil.getDriver()).executeScript("history.go(-1)");
		} else {
			getDriver().navigate().back();
		}
		Assert.assertTrue(header.waitForDasboardHeader(), "Verify dashboard header is displayed");
		Assert.assertTrue(dashboardSection.waitForDasboardSection(null), "Verify dashboard section is displayed");
		
		/**------------------------------- Check Invoices-------------------------------- **/
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			MobileSectionTabs mobilesectiontabs = new MobileSectionTabs(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
			mobilesectiontabs.clickInvoices();
		}
		
		String name = dashboardSection.getInvoiceText();
		String InvoiceLink = dashboardSection.getInvoiceLink();
		dashboardSection.clickInvoice();
		utils.sync(9000L);
		Assert.assertTrue(invoiceNew.isInvoiceDetailDisplayed(null), "Verify invoice detail block is displayed");
		Assert.assertTrue(getDriver().getCurrentUrl().trim().contains(InvoiceLink.trim()), "Invoice Link is landing to correct page");
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			//Do Nothing
		} else
			Assert.assertEquals(name, invoice.getInvoicePageText(), "Invoice is opened");		
	}

	@Test(groups={"smoke","dashboardUi","regression","prod"}, dataProvider="devices")
	public void verifyChangePasswordDesign(TestDevice device) throws Exception {
		load("/dashboard");
		//If User Pass credentails from Jenkins
		utils.credentials_jenkins();
		homepage.login("", "", device, true);
		boolean can_edit_password = api.canUserEditPassword();
		if(!can_edit_password)
			throw new SkipException("Change password not allowed");
		utils.navigateTo("/user/change-password");
		Assert.assertTrue(dashboardSection.isChangePasswordPageDisplayed(), "Verify change password page is displayed");
		checkLayout(Dictionary.get("SPEC_FILE_NAME").trim(), device.getTags());
	}
}
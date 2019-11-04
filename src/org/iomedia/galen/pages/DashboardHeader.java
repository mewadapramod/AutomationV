package org.iomedia.galen.pages;

import java.text.NumberFormat;
import java.util.Locale;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver.HashMapNew;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;

public class DashboardHeader extends BaseUtil {

	public DashboardHeader(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<HashMapNew> sTestDetails) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	}
	
	private By dashboardheader = By.cssSelector(".sub-header section");
	private By header = By.cssSelector(".sub-header section, div[class*='events-subHeader']");
	private By accountName = By.cssSelector(".react-root-dashboard-header > div > div > div:first-child > div > h3 > span , .react-root-dashboard-header > div > div:first-child > div:first-child >div:first-child > h3 > span");
	private By accountId = By.cssSelector(".react-root-dashboard-header > div > div > div:first-child > p:last-child , .react-root-dashboard-header > div > div:first-child > div:first-child >div:first-child > p:last-child");
	private By totalDue = By.cssSelector(".react-root-dashboard-header > div > div > div:last-child > h3 > span , .react-root-dashboard-header > div > div:first-child > div:first-child >div:last-child > h3 > span");
	private By AMGRAccntName = By.cssSelector("div.text18px.alt_fontface");
	private By AMGRAccntID = By.cssSelector("span#currentAccount");
	private By AMGRChangePswd = By.cssSelector("a#link-updatepassword");
	private By AMGRcurrentpswd = By.cssSelector("input#iCurrentPIN");
	private By AMGRnewpswd = By.cssSelector("input#iPIN");
	private By reenter_AMGRnewpswd = By.cssSelector("input#iPIN2");
	private By AMGRsave = By.cssSelector("a#btn-save");
	private By AMGRLogout = By.cssSelector("a#menu-logout");
	private By EventName = By.cssSelector("div [class*=ticket-subHeader] h3 , div [class*=event-card-eventText] h4");
//	private By client_logo = By.cssSelector(".navbar-brand-logo");
	
	public boolean waitForNAM(){
		WebElement we = getElementWhenPresent(header);
		Assert.assertNotNull(we, "Verify dashboard is displayed");
		if(Environment.get("splunkLogIntegration").trim().equalsIgnoreCase("true")) {
			Cookie QuantumMetricSessionID = getDriver().manage().getCookieNamed("QuantumMetricSessionID");
	    	Cookie QuantumMetricUserID = getDriver().manage().getCookieNamed("QuantumMetricUserID");
	    	String searchQuery = "";
	    	if(QuantumMetricSessionID != null) {
	    		searchQuery += QuantumMetricSessionID.getValue() + "%20OR%20" + QuantumMetricUserID.getValue();
	    	}
	    	sTestDetails.get().put("SEARCH_QUERY", searchQuery.trim());
	    	String sessionId = getSessionId();
	    	searchQuery = sTestDetails.get().get("SEARCH_QUERY");
	    	if(searchQuery.trim().equalsIgnoreCase("")) {
	    		searchQuery = sessionId;
	    	} else {
		    	if(!sessionId.trim().equalsIgnoreCase("")) {
		    		searchQuery += "%20OR%20" + sessionId;
		    	}
	    	}
	    	sTestDetails.get().put("SEARCH_QUERY", searchQuery.trim());
		}
		return true;
	}
	
	public boolean waitForDasboardHeader(){
		if(!getDriver().getCurrentUrl().contains("dashboard"))
			getDriver().navigate().to(Environment.get("APP_URL")+"/dashboard");
		getElementWhenVisible(dashboardheader, 10);
		return true;
	}
	
	public String getEventName() {
		return getText(EventName);
	}
	
	public String getAccountName(){
		return getText(accountName).trim();
	}
	
	public void clickUpdatePassword(){
		click(AMGRChangePswd,"Change My Password");
	}
	
	public void clickSavePassword(){
		click(AMGRsave,"Save");
	}
	
	public void clickAMGRLogout(){
		click(AMGRLogout, "Logout",10);
		try {
			getElementWhenPresent(By.xpath(".//input[@name='email'] | .//div[@class='mobile-signin']//*[text()='Sign In'] | .//div[@class='desktop-signin-dashboard']//a[text()='Sign In']"), 10);
		} catch(Exception ex) {
			// Do Nothing
		}
	}
	
	public void typeCurrentPassword(String password){
		WebElement we = getElementWhenVisible(this.AMGRcurrentpswd);
		we.clear();
		we.sendKeys(password, Keys.TAB);
	}
	
	public void typeNewPassword(String password){
		WebElement we = getElementWhenVisible(this.AMGRnewpswd);
		we.clear();
		we.sendKeys(password, Keys.TAB);
		
		we = getElementWhenVisible(this.reenter_AMGRnewpswd);
		we.clear();
		we.sendKeys(password, Keys.TAB);
	}
	
	public String getAMGRaccntName(){
		String text = getText(AMGRAccntName).replaceAll("Hello, ", "");
		return text;	
	}
	
	public String getAMGRaccntID(){
		String id = getText(AMGRAccntID).replaceAll("Account ", "");
		return id;
	}
	
	public String getSwitchedAccountName(String newText){
		sync(900l);
		return getElementWhenRefreshed(accountName, "innerHTML", newText).getText();
	}
	
	public String getAccountId(){
		String[] account= getText(accountId).trim().split(" ");
		return account[account.length-1];
	}
	
	
	public String getDueAmount(String newValue){
		NumberFormat in=NumberFormat.getCurrencyInstance(Locale.US);
		double payment = Double.valueOf(newValue);
		String newamt = in.format(payment).replace("$", Environment.get("currency"));
		return getElementWhenRefreshed(totalDue,"innerHTML", newamt).getText().replaceAll("[$A-Za-z" + Environment.get("currency") + ",]", "").trim();
	}
}
package org.iomedia.galen.pages;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.framework.Driver.TestDevice;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

public class Hamburger extends BaseUtil {
	
	private String driverType;
	
	public Hamburger(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<HashMapNew> sTestDetails) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		driverType = driverFactory.getDriverType().get();
	}

//	private By invoiceLink = By.cssSelector("li.expanded a[href*='/invoice']");
//	private By dashboardLink = By.cssSelector("li.expanded a[href*='/dashboard']");
	private By signoutLink = By.cssSelector("div[class='navbar-left'] a[href*='/user/logout'], #amgr-user-menu-mobile a[href*='/user/logout']");
	private By mobileHamburger = By.xpath(".//button[starts-with(@class, 'navbar-toggle')]//i[1]");
	private By userFullName = By.cssSelector(".user-fullname a");
//	private By ManageTickets = By.cssSelector("li.expanded a[href*='/tickets']");
	private By myevents = By.cssSelector("#block-iommainnavmobileblock > ul > li:nth-child(2) > a");
	private By errorNotificationMessage = By.xpath("//*[contains(@class,'notification-notificationBanner-')]/*[contains(@class,'notification-notificationConent-')]");
	
	org.iomedia.galen.common.Utils utils = new org.iomedia.galen.common.Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);  
	
	public void clickInvoice(){
		utils.navigateTo("/invoice");
	}
	
	public void clickDashboard(){
		utils.navigateTo("/dashboard");
	}
		
	public void clickMobileHamburger() throws InterruptedException{
		click(mobileHamburger, "Mobile hamburger");
		sync(500L);
	}
	
	@SuppressWarnings("unchecked")
	public boolean waitforLoggedInPage(TestDevice device) {
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS"))) {
			getDriver().manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
			try {
				getElementWhenPresent(errorNotificationMessage, 3);
				getDriver().manage().timeouts().implicitlyWait(Long.valueOf(Environment.get("implicitWait")), TimeUnit.MILLISECONDS);
				getDriver().navigate().refresh();
				sync(5000l);
				return false;
			} catch (Exception e) {
				// Do nothing as User Logged In Successful
			}
			getElementWhenVisible(By.cssSelector(".navbar-brand-logo:last-child"), 40);
			utils.getElementWhenPresent(signoutLink, 30);
			getDriver().manage().timeouts().implicitlyWait(Long.valueOf(Environment.get("implicitWait")), TimeUnit.MILLISECONDS);
		}
		else {
			getDriver().manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
			try {
				getElementWhenPresent(errorNotificationMessage, 3);
				getDriver().manage().timeouts().implicitlyWait(Long.valueOf(Environment.get("implicitWait")), TimeUnit.MILLISECONDS);
				return false;
			} catch (Exception e) {
				// Do nothing as User Logged In Successful
			}
			getElementWhenPresent(userFullName, 30);
			getDriver().manage().timeouts().implicitlyWait(Long.valueOf(Environment.get("implicitWait")), TimeUnit.MILLISECONDS);
		}
		
		return true;
	}
	
	public void clickManageTickets(){
		utils.navigateTo("/tickets");
	}

	public void clickMyEvents() { click(myevents, "Click My events mobile");}
}

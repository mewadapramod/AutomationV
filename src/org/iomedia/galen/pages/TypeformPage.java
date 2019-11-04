package org.iomedia.galen.pages;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver.HashMapNew;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;

public class TypeformPage extends BaseUtil {
	
	WebDriver driver;
	
	public TypeformPage(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<HashMapNew> sTestDetails) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		driver = driverFactory.getDriver().get();
	}
	public boolean enableTypeForm() {
		String url = Environment.get("APP_URL").trim();
		String userName = System.getProperty("adminUserName") != null && !System.getProperty("adminUserName").trim().equalsIgnoreCase("") ? System.getProperty("adminUserName").trim() : Environment.get("adminUserName").trim();
		String password = System.getProperty("adminPassword") != null && !System.getProperty("adminPassword").trim().equalsIgnoreCase("") ? System.getProperty("adminPassword").trim() : Environment.get("adminPassword").trim();
		String tm_oauth_url = Environment.get("TM_OAUTH_URL").trim();
    	if(userName.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
    		if(tm_oauth_url.contains("app.ticketmaster.com")){
    			String clientId = url.substring(url.lastIndexOf("/") + 1);
    			if(clientId.trim().endsWith("/")) {
    				clientId = clientId.substring(0, clientId.trim().length() - 1);
    			}
    			userName = "admin";
    			password = clientId + "1234";
    		} else {
    			return false;
    		}
    	}
    	
		driver.get(url + "/user/login");
		driver.findElement(By.id("edit-name")).sendKeys(userName);
		driver.findElement(By.id("edit-pass")).sendKeys(password);
		JavascriptExecutor ex = (JavascriptExecutor)driver;
		ex.executeScript("arguments[0].click();", driver.findElement(By.id("edit-submit")));
			
		if(!checkIfElementPresent(By.id("toolbar-bar"), 10))	{
			String oldUsername = userName;
			String oldPassword = password;
			if(oldUsername.trim().equalsIgnoreCase("admin") && oldPassword.trim().equalsIgnoreCase("123456")) {
				return false;
			} else {
				userName = "admin";
				password = "123456";
				driver.findElement(By.id("edit-name")).clear();
				driver.findElement(By.id("edit-name")).sendKeys(userName);
				driver.findElement(By.id("edit-pass")).clear();
				driver.findElement(By.id("edit-pass")).sendKeys(password);
				ex.executeScript("arguments[0].click();", driver.findElement(By.id("edit-submit")));
				if(!checkIfElementPresent(By.id("toolbar-bar"), 10)) {
					if(oldUsername.trim().equalsIgnoreCase("admin") && oldPassword.trim().equalsIgnoreCase("Jl7%q1*K")) {
						return false;
					} else {
						userName = "admin";
						password = "Jl7%q1*K";
						driver.findElement(By.id("edit-name")).clear();
						driver.findElement(By.id("edit-name")).sendKeys(userName);
						driver.findElement(By.id("edit-pass")).clear();
						driver.findElement(By.id("edit-pass")).sendKeys(password);
						ex.executeScript("arguments[0].click();", driver.findElement(By.id("edit-submit")));
					}
				}
			}
		}
		
		/*Enable typeform*/
		try {
			driver.navigate().to(url + "/admin/config/typeform");
			By enableTypeformCheckbox = By.name("enable_typeform");
			By typeformWorkspaceId = By.id("edit-typeform-workspace-id");
			String typeform_workspace_id = System.getProperty("typeform_workspace_id") != null && !System.getProperty("typeform_workspace_id").trim().equalsIgnoreCase("") ? System.getProperty("typeform_workspace_id").trim() : Environment.get("typeform_workspace_id").trim();
			
			WebElement wenableTypeformCheckbox = driver.findElement(enableTypeformCheckbox);
			WebElement wtypeformWorkspaceId = driver.findElement(typeformWorkspaceId);
			if(wenableTypeformCheckbox.getAttribute("checked") == null) {
				ex.executeScript("arguments[0].click();", wenableTypeformCheckbox);
			}
			wtypeformWorkspaceId.clear();
			wtypeformWorkspaceId.sendKeys(typeform_workspace_id);
			ex.executeScript("arguments[0].click();", driver.findElement(By.id("edit-submit")));
			getElementWhenVisible(By.cssSelector("div[class*='messages--status']"), 5);
			System.out.println("Typeform enabled");
		} catch(Exception ex1) {
			throw ex1;
		} finally {
			driver.navigate().to(url + "/user/logout");
			getElementWhenPresent(By.xpath(".//input[@name='email'] | .//div[@class='mobile-signin']//*[text()='Sign In'] | .//div[@class='desktop-signin-dashboard']//a[text()='Sign In']"));
		}
		
		selectTypeform(driver, url, "automation_editor_manager", "nam_editor_manager", "Invoice Summary", true);
		System.out.println("Typeform selected");
		return true;
	}
	
	public void selectTypeform(WebDriver driver, String url, String userName, String password, String showAfter, boolean allowMultipleSubmission) {
		
		driver.navigate().to(url + "/user/login");
		driver.findElement(By.id("edit-name")).sendKeys(userName);
		driver.findElement(By.id("edit-pass")).sendKeys(password);
		
		JavascriptExecutor ex = (JavascriptExecutor)driver;
		ex.executeScript("arguments[0].click();", driver.findElement(By.id("edit-submit")));
		try {
			driver.navigate().to(url + "/admin/invoice/list");
			By settingsIcon = By.xpath(".//tr[descendant::input[@checked='']]//span[@class='edit']//i");
			WebElement wsettingsIcon = getElementWhenVisible(settingsIcon);
			ex.executeScript("arguments[0].click();", wsettingsIcon);
			
			By questionsTab = By.xpath(".//span[text()='QUESTIONS']");
			By embedTypeform = By.xpath(".//*[@id='openPopup']/span[text()='EMBED TYPEFORM'] | .//*[@id='openPopup']/span[text()='SELECT TYPEFORM']");
			//
			ex.executeScript("arguments[0].click();", driver.findElement(questionsTab));
			
			if(checkIfElementPresent(embedTypeform)) {
				WebElement we = getElementWhenVisible(embedTypeform);
				click(we, "Select Typeform");
				By typeform_automation = By.cssSelector("a[title='Typeform Automation']");
				WebElement wtypeform_automation = getElementWhenVisible(typeform_automation);
				scrollingToElementofAPage(wtypeform_automation);
				wtypeform_automation.click();
				By save = By.id("typeform-accepted");
				ex.executeScript("arguments[0].click();", driver.findElement(save));
			}
			
			By dropDown = By.cssSelector("div[class*='typeform'] input[class*='select-dropdown']");
			WebElement wdropDown = getElementWhenRefreshed(dropDown, "disabled", "null", 2);
			if(!wdropDown.getAttribute("value").trim().equalsIgnoreCase(showAfter.trim())) {
				ex.executeScript("arguments[0].click();", wdropDown);
				By locator = By.xpath(".//div[contains(@class, 'typeform')]//ul//li//span[text()='" + showAfter.trim() + "']");
				ex.executeScript("arguments[0].click();", driver.findElement(locator));
			}
			
			By allow_multiple_submissions = By.cssSelector("label[for*='typeform-allow-multiple-submission'] input");
			WebElement wallow_multiple_submissions = driver.findElement(allow_multiple_submissions);
			if(allowMultipleSubmission && wallow_multiple_submissions.getAttribute("checked") == null) {
				ex.executeScript("arguments[0].click();", wallow_multiple_submissions);
			} else if(!allowMultipleSubmission && wallow_multiple_submissions.getAttribute("checked") != null) {
				ex.executeScript("arguments[0].click();", wallow_multiple_submissions);
			}
			
			By submit = By.id("edit-submit");
			ex.executeScript("arguments[0].click();", driver.findElement(submit));
			
			By alert = By.cssSelector("div[role='alert']");
			getElementWhenVisible(alert, 5);
		} finally {
			driver.navigate().to(url + "/user/logout");
			getElementWhenPresent(By.xpath(".//input[@name='email'] | .//div[@class='mobile-signin']//*[text()='Sign In'] | .//div[@class='desktop-signin-dashboard']//a[text()='Sign In']"));
		}
	}
}

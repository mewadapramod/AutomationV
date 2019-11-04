package org.iomedia.galen.steps;

import org.iomedia.common.BaseUtil;
import org.iomedia.galen.pages.SuperAdminPanel;
import org.openqa.selenium.By;
import org.testng.SkipException;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class AdminPanelSteps {

	BaseUtil base;
	SuperAdminPanel superAdminPanel;
	org.iomedia.framework.Assert Assert;

	public AdminPanelSteps(BaseUtil base, SuperAdminPanel superAdminPanel, org.iomedia.framework.Assert Assert) {
		this.base = base;
		this.superAdminPanel = superAdminPanel;
		this.Assert = Assert;
	}

	@Given("^User navigates to Admin Login and Logs in using Admin Credentials$")
	public void adminLogin() throws Exception {
		base.load("/user/login");
		String url = base.Environment.get("APP_URL").trim();
		String userName = "admin";
		String password = "123456";
		String tm_oauth_url = base.Environment.get("TM_OAUTH_URL").trim();
		System.out.println(userName);
		System.out.println(password);

		if (userName.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
			if (tm_oauth_url.contains("app.ticketmaster.com")) {
				String clientId = url.substring(url.lastIndexOf("/") + 1);
				if (clientId.trim().endsWith("/")) {
					clientId = clientId.substring(0, clientId.trim().length() - 1);
				}
				userName = "admin";
				password = clientId + "1234";
			} else {
				return;
			}
		}
		base.getDriver().get(url + "/user/login");
		base.type(By.id("edit-name"), "Admin Username", userName);
		base.type(By.id("edit-pass"), "Admin Password", password);
		base.click(By.id("edit-submit"), "Admin Sign In");
		String oldUsername = userName;
		String oldPassword = password;
		
		if (((base.driverFactory.getDriverType().get().toString().trim().toUpperCase().contains("ANDROID") || base.driverFactory.getDriverType().get().toString().trim().toUpperCase().contains("IOS")))) {
			if(!base.checkIfElementPresent(By.xpath("//a[@title='Admin menu']"), 10)) {
				if(oldUsername.trim().equalsIgnoreCase("admin") && oldPassword.trim().equalsIgnoreCase("123456")) {
					userName = "admin";
					password = "Jl7%q1*K";
					base.type(By.id("edit-name"), "Admin Username", userName);
					base.type(By.id("edit-pass"), "Admin Password", password);
					base.click(By.id("edit-submit"), "Admin Sign In");
					if(!base.checkIfElementPresent(By.xpath("//a[@title='Admin menu']"), 10)) {
						throw new SkipException("Provide valid admin credentials");
					}
				} else {
					userName = "admin";
					password = "123456";
					base.type(By.id("edit-name"), "Admin Username", userName);
					base.type(By.id("edit-pass"), "Admin Password", password);
					base.click(By.id("edit-submit"), "Admin Sign In");
					if (!base.checkIfElementPresent(By.xpath("//a[@title='Admin menu']"), 10)) {
						if(oldUsername.trim().equalsIgnoreCase("admin") && oldPassword.trim().equalsIgnoreCase("Jl7%q1*K")) {
							throw new SkipException("Provide valid admin credentials");
						} else {
							userName = "admin";
							password = "Jl7%q1*K";
							base.type(By.id("edit-name"), "Admin Username", userName);
							base.type(By.id("edit-pass"), "Admin Password", password);
							base.click(By.id("edit-submit"), "Admin Sign In");
							if (!base.checkIfElementPresent(By.xpath("//a[@title='Admin menu']"), 10)) {
								throw new SkipException("Provide valid admin credentials");
							}
						}
					}
				}
			}
		} else {
			if (!base.checkIfElementPresent(By.id("toolbar-bar"), 10)) {
				if(oldUsername.trim().equalsIgnoreCase("admin") && oldPassword.trim().equalsIgnoreCase("123456")) {
					userName = "admin";
					password = "Jl7%q1*K";
					base.type(By.id("edit-name"), "Admin Username", userName);
					base.type(By.id("edit-pass"), "Admin Password", password);
					base.click(By.id("edit-submit"), "Admin Sign In");
					if(!base.checkIfElementPresent(By.id("toolbar-bar"), 10)) {
						throw new SkipException("Provide valid admin credentials");
					}
				} else {
					userName = "admin";
					password = "123456";
					base.type(By.id("edit-name"), "Admin Username", userName);
					base.clear(base.getElementWhenClickable(By.id("edit-name")));
					base.type(By.id("edit-pass"), "Admin Password", password);
					base.clear(base.getElementWhenClickable(By.id("edit-pass")));
					base.click(By.id("edit-submit"), "Admin Sign In");
					if (!base.checkIfElementPresent(By.id("toolbar-bar"), 10)) {
						if(oldUsername.trim().equalsIgnoreCase("admin") && oldPassword.trim().equalsIgnoreCase("Jl7%q1*K")) {
							throw new SkipException("Provide valid admin credentials");
						} else {
							userName = "admin";
							password = "Jl7%q1*K";
							base.type(By.id("edit-name"), "Admin Username", userName);
							base.type(By.id("edit-pass"), "Admin Password", password);
							base.click(By.id("edit-submit"), "Admin Sign In");
							if (!base.checkIfElementPresent(By.id("toolbar-bar"), 10)) {
								throw new SkipException("Provide valid admin credentials");
							}
						}
					}
				}
			}
		}
	}

	@And("^Barcode check and save setting \"(.+)\"$")
	public void barcode(String check) throws Exception {
		System.out.println(check);
		if (check.equalsIgnoreCase("Enabled") || check.equalsIgnoreCase("Enable")) {
			if (base.getAttribute(base.getElementWhenClickable(By.xpath("//input[contains(@name,'barcode_number')]")), "checked") != null) {
				// do nothing
			} else {
				base.click(base.getElementWhenClickable(By.xpath("//input[contains(@name,'barcode_number')]")), "Barcode checkbox");
				base.click(base.getElementWhenClickable(By.xpath(".//*[@id='edit-submit']")), "Save");
				if (((base.driverFactory.getDriverType().get().toString().trim().toUpperCase().contains("ANDROID") || base.driverFactory.getDriverType().get().toString().trim().toUpperCase() .contains("IOS")))) {
					base.sync(4000L);
				}
			}
		} else if (check.equalsIgnoreCase("Disabled") || check.equalsIgnoreCase("Disable")) {
			if (base.getAttribute(base.getElementWhenClickable(By.xpath("//input[contains(@name,'barcode_number')]")), "checked") != null) {
				base.click(base.getElementWhenClickable(By.xpath("//input[contains(@name,'barcode_number')]")), "Barcode checkbox");
				base.click(base.getElementWhenClickable(By.xpath(".//*[@id='edit-submit']")), "Save");
				if (((base.driverFactory.getDriverType().get().toString().trim().toUpperCase().contains("ANDROID") || base.driverFactory.getDriverType().get().toString().trim().toUpperCase().contains("IOS")))) {
					base.sync(4000L);
				}
			} else {
				// Do Nothing
			}
		} else
			throw new SkipException("Provide correct value i.e Enable or Disable");
		System.out.println(check);
	}
	
	@Then("^Verify barcode state after upgrade (.+)$")
	public void verify_barcode_state_after_upgrade(String check) throws Exception {
		check = (String) base.getGDValue(check);
		String state = base.getAttribute(By.id("edit-manage-ticket-configuration-barcode-number-enabled"), "checked");
		Assert.assertEquals(state == null ? "false" : "true", check, "Verify barcode number state after upgrade");
	}
	
}

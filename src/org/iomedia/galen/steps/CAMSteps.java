package org.iomedia.galen.steps;

import org.iomedia.common.BaseUtil;
import org.iomedia.galen.pages.CAM;
import org.iomedia.galen.pages.DashboardHeader;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CAMSteps {
	
	DashboardHeader header;
	org.iomedia.framework.Assert Assert;
	BaseUtil base;
	CAM cam;

	public CAMSteps(DashboardHeader header, org.iomedia.framework.Assert Assert, BaseUtil base, CAM cam) {
		this.header = header;
		this.Assert = Assert;
		this.base = base;
		this.cam = cam;
	}
	
	@Then("^Classic AMGR is displayed with correct (.+) and (.+)$")
	public void classic_amgr_is_displayed_with_correct_and(String accountName, String accountId)  {
		
		if(cam.isAlertPresent()) {
			base.checkAlert("decline");
			base.getDriver().navigate().refresh();
		}else {
			//nothing
		}
		accountName = (String) base.getGDValue(accountName);
		accountId = (String) base.getGDValue(accountId);
		Assert.assertEquals(header.getAMGRaccntName(), accountName , "Account Name is matching. SSO is successful");
		if(base.Dictionary.get("name_"+accountId).trim().equalsIgnoreCase(""))
			Assert.assertEquals(header.getAMGRaccntID(), accountId, "Account ID is matching. SSO is successful");
		else
			Assert.assertEquals(header.getAMGRaccntID(), base.Dictionary.get("name_"+accountId).trim(), "Account ID is matching. SSO is successful");
//	    Assert.assertTrue(header.getDriver().getCurrentUrl().contains(header.Environment.get("x-client").trim()));
	}
	
	@Given("^User navigates to (.+) from CAM$")
	public void user_navigates_to_from_cam(String uri) {
		base.getDriver().navigate().to(base.Environment.get("APP_URL").trim() + uri);
	}
	
	@Given("^User logout from CAM$")
	public void user_logout_from_cam() {
		header.clickAMGRLogout();
	}
	
	@When("^update (.+) with (.+)$")
	public void update_with(String currentpassword, String newpassword) {
		currentpassword = (String) base.getGDValue(currentpassword);
		newpassword = (String) base.getGDValue(newpassword);
		header.clickUpdatePassword();
	    header.typeCurrentPassword(currentpassword);
	    header.typeNewPassword(newpassword);
	    header.clickSavePassword();
	}
	
	@When("^manage profile with (.+) and (.+)$")
	public void update_profile_with_and(String emailaddress, String pin) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		pin = (String) base.getGDValue(pin);
		cam.clickManagePersonalProfile();
		cam.updateProfile("Test", "Test", "110th CC Avenue", "Hellington", "Atlanta", "Illinois", "United States", "12100", emailaddress, "7032225555", "7032221111", pin);
	}		
	
	@When("^User buys a ticket with (.+) using (.+) type card with card number (.+), CVV (.+), Expiry (.+), AccountId (.+)$")
	public void user_buys_ticket(String paymentMode, String cardType, String cardNum, String CVV, String Expiry, String accountId) throws Exception {
		paymentMode = (String) base.getGDValue(paymentMode);
		cardType = (String) base.getGDValue(cardType);
		cardNum = (String) base.getGDValue(cardNum);
		CVV = (String) base.getGDValue(CVV);
		Expiry = (String) base.getGDValue(Expiry);
		accountId = (String) base.getGDValue(accountId);
		cam.buyTickets(paymentMode, cardType, cardNum, Expiry, CVV, accountId.trim());	
	}

	
}
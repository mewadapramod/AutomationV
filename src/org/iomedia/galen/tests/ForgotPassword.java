package org.iomedia.galen.tests;

import org.iomedia.framework.Driver;
import org.iomedia.galen.pages.Homepage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ForgotPassword extends Driver {
	
	private Homepage homepage; 
	
	@BeforeMethod(alwaysRun=true)
	public void init(){
		homepage = new Homepage(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	}
	
	@Test(groups={"smoke","miscUi","regression","prod"}, dataProvider="devices")
	public void verifyForgotPasswordReactComponent(TestDevice device) throws Exception{
		load("/");
		homepage.clickSignInLink(device);
		homepage.clickForgotPasswordLink();
		Assert.assertTrue(homepage.isForgotpasswordPageDisplayed(), "Verify forgot password page is displayed");
		checkLayout(Dictionary.get("SPEC_FILE_NAME"), device.getTags());
	}
	
	@Test(groups={"smoke","miscUi","regression"}, dataProvider="devices")
	public void verifyForgotPasswordInvalidEmailAddressReactComponent(TestDevice device) throws Exception{
		load("/");
		homepage.clickSignInLink(device);
		homepage.clickForgotPasswordLink();
		Assert.assertTrue(homepage.isForgotpasswordPageDisplayed(), "Verify forgot password page is displayed");
		homepage.enterEmailAddress("12345678");
		checkLayout(Dictionary.get("SPEC_FILE_NAME"), device.getTags());
	}
	
	@Test(groups={"smoke","miscUi","regression"}, dataProvider="devices", enabled = false)
	public void verifyForgotPasswordValidEmailAddressReactComponent(TestDevice device) throws Exception{
		load("/");
		homepage.clickSignInLink(device);
		homepage.clickForgotPasswordLink();
//		Assert.assertTrue(homepage.isForgotpasswordPageDisplayed(), "Verify forgot password page is displayed");
		homepage.typeEmail(Dictionary.get("EMAIL_ADDRESS").trim());
		homepage.clickSendButton();
		Assert.assertTrue(homepage.isNotificationBannerDisplayed(), "Verify notification banner is displayed");
		checkLayout(Dictionary.get("SPEC_FILE_NAME"), device.getTags());
	}
	
	@Test(groups={"smoke","miscUi","regression"}, dataProvider="devices")
	public void verifyForgotPasswordReactComponentInterstitial(TestDevice device) throws Exception{
		load("/dashboard");
		homepage.clickForgotPasswordLink();
		Assert.assertTrue(homepage.isForgotpasswordPageDisplayed(), "Verify forgot password page is displayed");
		checkLayout(Dictionary.get("SPEC_FILE_NAME"), device.getTags());
	}
	
	@Test(groups={"smoke","miscUi","regression"}, dataProvider="devices")
	public void verifyForgotPasswordInvalidEmailAddressReactComponentInterstitial(TestDevice device) throws Exception{
		load("/dashboard");
		homepage.clickForgotPasswordLink();
		Assert.assertTrue(homepage.isForgotpasswordPageDisplayed(), "Verify forgot password page is displayed");
		homepage.enterEmailAddress("12345678");
		checkLayout(Dictionary.get("SPEC_FILE_NAME"), device.getTags());
	}
	
	@Test(groups={"smoke","miscUi","regression"}, dataProvider="devices", enabled = false)
	public void verifyForgotPasswordValidEmailAddressReactComponentInterstitial(TestDevice device) throws Exception{
		load("/dashboard");
		homepage.clickForgotPasswordLink();
//		Assert.assertTrue(homepage.isForgotpasswordPageDisplayed(), "Verify forgot password page is displayed");
		homepage.typeEmail(Dictionary.get("EMAIL_ADDRESS").trim());
		homepage.clickSendButton();
		Assert.assertTrue(homepage.isNotificationBannerDisplayed(), "Verify notification banner is displayed");
		checkLayout(Dictionary.get("SPEC_FILE_NAME"), device.getTags());
	}
}

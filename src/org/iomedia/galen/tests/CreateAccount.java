package org.iomedia.galen.tests;

import org.iomedia.framework.Driver;
import org.iomedia.galen.pages.Homepage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CreateAccount extends Driver {
	
	private Homepage homepage;
	
	@BeforeMethod(alwaysRun=true)
	public void init(){
		homepage = new Homepage(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	}
	
	@Test(groups={"smoke","miscUi","regression","prod"}, dataProvider="devices", priority = 1)
	public void verifyCreateAccountReactComponent(TestDevice device) throws Exception{
		load("/");
		homepage.clickSignInLink(device);
		Assert.assertTrue(homepage.isSignInPageDisplayed(), "Verify sign in page is displayed");
		
		homepage.clickSignUpLink();
		Assert.assertTrue(homepage.isSignUpPageDisplayed(), "Verify sign up page is displayed");
		
		checkLayout(Dictionary.get("SPEC_FILE_NAME"), device.getTags());
	}
	
	@Test(groups={"smoke","miscUi","regression"}, dataProvider="devices", priority = 2)
	public void verifyCreateAccountReactComponentInterstitial(TestDevice device) throws Exception{
		load("/dashboard");
		Assert.assertTrue(homepage.isSignInPageDisplayed(), "Verify sign in page is displayed");
		
		homepage.clickSignUpLink();
		Assert.assertTrue(homepage.isSignUpPageDisplayed(), "Verify sign up page is displayed");
		
		checkLayout(Dictionary.get("SPEC_FILE_NAME"), device.getTags());
	}

	
	@Test(groups={"smoke","miscFunctional","regression","criticalbusiness"}, priority = 3)
	public void verifyCreateNewUser() throws Exception{
		load("/dashboard");
		homepage.createaccount(null, true);
	}
}

package org.iomedia.galen.tests;

import org.iomedia.framework.Driver;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.pages.Hamburger;
import org.iomedia.galen.pages.Homepage;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SignIn extends Driver {
	
	private Homepage homepage; 
	private String driverType;
	
	@BeforeMethod(alwaysRun=true)
	public void init(){
		homepage = new Homepage(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		driverType = driverFactory.getDriverType().get();
	}
	
	@Test(groups={"smoke","regression","miscUi","prod"}, dataProvider="devices", priority = 1)
	public void verifySignInReactComponent(TestDevice device) throws Exception{
		load("/");
		homepage.clickSignInLink(device);
		homepage.clickSignInReactLink();
		Assert.assertTrue(homepage.isSignInPageDisplayed(), "Verify sign in page is displayed");
		checkLayout(Dictionary.get("SPEC_FILE_NAME"), device.getTags());
	}
	
	@Test(groups={"smoke","regression","miscUi"}, dataProvider="devices", priority = 2)
	public void verifySignInReactComponentInterstitial(TestDevice device) throws Exception{
		load("/dashboard");
		homepage.clickSignInReactLink();
		Assert.assertTrue(homepage.isSignInPageDisplayed(), "Verify sign in page is displayed");
		checkLayout(Dictionary.get("SPEC_FILE_NAME"), device.getTags());
	}
	
	@Test(groups={"smoke","regression","miscFunctional"}, priority = 3)
	public void verifyRememberMe() throws Exception{
		load("/");
		homepage.login("", "", null, false);
		Utils utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		utils.navigateTo("/user/logout");
		homepage.clickSignInLink(null);
		Assert.assertEquals(homepage.getEmailAddress(), Dictionary.get("EMAIL_ADDRESS").trim().toLowerCase(), "Verify email address retains in the field after logout");
		homepage.typePassword(Dictionary.get("PASSWORD").trim());
		homepage.clickRememberMe();
		homepage.clickSignInButton();
		Hamburger hamburger = new Hamburger(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		Assert.assertTrue(hamburger.waitforLoggedInPage(null), "Verify user get logged in");
	}
	
	@Test(groups={"smoke","regression","miscFunctional","prod"}, priority = 4)
	public void verifyFooterLinkforPublicuser() throws Exception {
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			throw new SkipException("Skipped");
		}
		load("/");	
		String CopyRight = homepage.getCopyrightLink();
		homepage.clickCopyright();		
		BaseUtil.switchToWindow(1);		
		String URLRedirection = BaseUtil.getDriver().getCurrentUrl().substring(0,4)+'s'+BaseUtil.getDriver().getCurrentUrl().substring(4, BaseUtil.getDriver().getCurrentUrl().length());
		Assert.assertTrue(CopyRight.trim().contains(URLRedirection.trim()) || URLRedirection.trim().contains(CopyRight.trim()) || URLRedirection.trim().contains("ticketmaster.com"), "CopyRight Link is redirecting to correct Page for Public User");
		
		BaseUtil.switchToWindow(0);
		
		homepage.clickPolicy();
		BaseUtil.switchToWindow(2);	
		String URLRedirect= BaseUtil.getDriver().getCurrentUrl();
		Assert.assertTrue(URLRedirect.trim().contains("privacy"), "Privacy Policy Link is redirecting to correct page for Public user"); 

		BaseUtil.switchToWindow(0);
		
		homepage.clickTerms();
		Assert.assertTrue(BaseUtil.getDriver().getCurrentUrl().trim().contains("/terms"), "Terms Link is redirecting to correct page for Public user");
	}
	
	@Test(groups={"smoke","regression","miscFunctional","prod"}, priority = 4)
	public void verifyFooterLinkforLoggedInuser() throws Exception {
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			throw new SkipException("Skipped");
		}
		load("/");	
		String emailaddress = Dictionary.get("EMAIL_ADDRESS").trim();	
		String password = Dictionary.get("PASSWORD").trim();	
		homepage.login(emailaddress,password, null, false);
		Assert.assertTrue(homepage.isWelcomePageDisplayed(),"Existing User Logged into the account successfully");
		
		String CopyRight =homepage.getCopyrightLink();
		homepage.clickCopyright();		
		BaseUtil.switchToWindow(1);		
		String URLRedirection=BaseUtil.getDriver().getCurrentUrl().substring(0,4)+'s'+BaseUtil.getDriver().getCurrentUrl().substring(4, BaseUtil.getDriver().getCurrentUrl().length());
		Assert.assertTrue(CopyRight.trim().contains(URLRedirection.trim()) || URLRedirection.trim().contains(CopyRight.trim()) || URLRedirection.trim().contains("ticketmaster.com"), "CopyRight Link is redirecting to correct Page for LoggedIn User");
		
		BaseUtil.switchToWindow(0);
		
		homepage.clickPolicy();
		BaseUtil.switchToWindow(2);	
		String URLRedirect= BaseUtil.getDriver().getCurrentUrl();
		Assert.assertTrue(URLRedirect.trim().contains("privacy"), "Privacy Policy Link is redirecting to correct page for LoggedIn user"); 

		BaseUtil.switchToWindow(0);
		
		homepage.clickTerms();
		URLRedirect= BaseUtil.getDriver().getCurrentUrl();
		Assert.assertTrue(URLRedirect.trim().contains("/terms"), "Terms Link is redirecting to correct page for LoggedIn user");
	}
}

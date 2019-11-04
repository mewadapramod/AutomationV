package org.iomedia.galen.tests;

import org.iomedia.framework.Driver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;

import org.iomedia.galen.common.AccessToken;
import org.iomedia.galen.pages.*;

@CucumberOptions(plugin = "json:target/cucumber-report-feature-composite.json", format = "pretty", features = "features/prodsanity.feature", glue = {"org.iomedia.galen.steps"}, monochrome = true, strict = true)
public class Homescreen extends Driver{
	
	private Homepage homepage; 
	private AccessToken accessToken;
	private DashboardHeader header;
	private MobileSectionTabs tabs;
	
	@BeforeMethod(alwaysRun=true)
	public void init(){
		homepage = new Homepage(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		accessToken = new AccessToken(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		header = new DashboardHeader(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		tabs = new MobileSectionTabs(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
	}

	@Test(groups={"smoke", "miscUi","regression","prod"}, dataProvider="devices", priority = 1, enabled = false)
	public void verifyHomePage(TestDevice device) throws Exception{
		load("/");
		Assert.assertTrue(homepage.isHomepageDisplayed(device), "Verify homepage is displayed");
		checkLayout(Dictionary.get("SPEC_FILE_NAME"), device.getTags());
	}
	
	@Test(groups={"smoke","miscFunctional","regression"}, dataProvider="devices", priority = 2, invocationCount=1)
	public void verifyUserLogin(TestDevice device) throws Exception{
		load("/");
		homepage.clickSignInLink(device);
		homepage.login("", "", device, false);
		if(device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile")) {
			tabs.clickQuickLinks();
		}
		Assert.assertEquals(accessToken.getAccountId(accessToken.getAccessToken(Dictionary.get("EMAIL_ADDRESS"), Dictionary.get("PASSWORD"))), header.getAccountId());	
	}
}
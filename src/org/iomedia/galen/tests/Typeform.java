package org.iomedia.galen.tests;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.pages.AdminLogin;
import org.iomedia.galen.pages.TypeformPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import cucumber.api.CucumberOptions;

@CucumberOptions(plugin = "json:target/cucumber-report-feature-composite.json", format = "pretty", features = {"features/typeform.feature"}, glue = {"org.iomedia.galen.steps"}, monochrome = true, strict = true)	
public class Typeform extends Driver {
	
	//boolean enable_typeform = false;
	TypeformPage typeformpage;
	private Utils utils;
	
	private AdminLogin adminLogin;
	String username;
	String password;
	BaseUtil base;
	String uri;
	
	@BeforeMethod(alwaysRun=true)
	public void init() throws Exception {
	
		typeformpage = new TypeformPage(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		adminLogin= new AdminLogin(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		
		if(Dictionary.get("cmsUserName").trim().equalsIgnoreCase(""))
		{
		adminLogin.adminLogin();
		utils.navigateTo("/admin/people");
		adminLogin.createAdduserCMS();
		//adminLogin.filterSiteadmin();
		//adminLogin.changePasswordOfSiteAdminUser();
		utils.navigateTo("/user/logout");
		//Dictionary.put("cmsAdminUserName", "siteadmin");
		//Dictionary.put("cmsAdminPassword", "123456");
		Dictionary.put("cmsUserName", "automationsuport4");
		Dictionary.put("cmsPassword", "123456");
			username = "automationsuport4";
			password = "123456";
		}
	}
	
	@Test(groups={"smoke","regression","typeform"}, priority = 1)
	public void verifytypeform() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","typeform"}, priority = 2)
	public void verifytypeformpayment() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","typeform"}, priority = 3)
	public void enableaflpurchaseflow() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","typeform"}, priority = 4)
	public void aflpaypalpayment() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups={"smoke","regression","typeform"}, priority = 5)
	public void aflachpayment() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

}
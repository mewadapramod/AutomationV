package org.iomedia.galen.tests;

import org.iomedia.framework.Driver;
import org.testng.annotations.Test;
import cucumber.api.CucumberOptions;



@CucumberOptions(plugin = "json:target/cucumber-report-feature-composite.json", format = "pretty", features = {"features/prodsanity.feature", "features/userJourneys.feature"}, glue = {"org.iomedia.galen.steps"}, monochrome = true, strict = true)
public class ProdSanity extends Driver {
	

	@Test(groups={"smoke","regression","sso", "prod"}, priority = 2)
	public void prodSanityVerificationPart1() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod"}, priority = 1)
	public void prodSanityVerification() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression", "prod"}, priority = 3)
	public void verifyPrivacyLink() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression", "prod", "cmsFunctional"}, priority = 4)
	public void verifyCMSLogin() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","criticalbusiness", "takescreenshot"}, priority = 5)
	public void takeScreenshotbeforeDeployment() throws Throwable {
	   runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","criticalbusiness", "takescreenshot"}, priority = 6)
	public void compareScreenshotafterDeployment() throws Throwable {
	   runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "barcode"}, priority = 7)
	public void verifyBarcodeNumberStateAfterUpgrade() throws Throwable {
	   runScenario(Dictionary.get("SCENARIO"));
	}
}

package org.iomedia.galen.tests;

import org.iomedia.framework.Driver;
import org.testng.annotations.Test;
import cucumber.api.CucumberOptions;

@CucumberOptions(plugin = "json:target/cucumber-report-feature-composite.json", format = "pretty", features = {"features/FrontEnd_Internationalization.feature"}, glue = {"org.iomedia.galen.steps"}, monochrome = true, strict = true)	
public class Internationalization extends Driver{
		
	@Test(groups={"smoke","regression","prod", "Internationalization"}, priority = 1)
	public void verifyInternationalizationOnFrontEnd() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups={"smoke","regression","prod", "Internationalization"}, priority = 2)
	public void verifyFunctionalityafterSwitichingOnfromBackend() throws Throwable{
		runScenario(Dictionary.get("SCENARIO"));
	}
		
	@Test(groups={"smoke","regression","prod", "Internationalization"}, priority = 3)
	public void verifyLoginPageinOtherLang() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "Internationalization"}, priority = 4)
	public void verifyDashboardPageinOtherLang() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
}

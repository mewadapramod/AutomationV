package org.iomedia.galen.tests;

import org.iomedia.framework.Driver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;

@CucumberOptions(plugin = "json:target/cucumber-report-feature-composite.json", format = "pretty", features = {"features/VVOverview.feature"}, glue = {"org.iomedia.galen.steps"}, monochrome = true, strict = true)
public class VirtualVenue extends Driver {
	
	org.iomedia.common.BaseUtil base;
	
	@BeforeMethod(alwaysRun=true)
	public void init(){
		driverType = driverFactory.getDriverType().get();
	}
	
	@Test(groups = { "smoke", "regression", "sanity", "vvapi" }, priority = 2)
	public void verifySourceSeatInfo() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "sanity", "vvapi" }, priority = 1)
	public void verifyTargetEventInfo() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "sanity", "vvapi" }, priority = 1)
	public void verifyVenueAvailability() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}



}


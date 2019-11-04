package org.iomedia.galen.tests;

import org.iomedia.framework.Driver;
import org.testng.annotations.Test;
import cucumber.api.CucumberOptions;

@CucumberOptions(plugin = "json:target/cucumber-report-feature-composite.json", format = "pretty", features = {"features/userJourneys.feature", "features/menuManager.feature"}, glue = {"org.iomedia.galen.steps"}, monochrome = true, strict = true)	
public class UserJourneys_2 extends Driver{
		
	@Test(groups={"smoke","regression","user_journey","criticalbusiness", "sso", "noncam"}, priority = 1)
	public void verifySTPCreateAccountBuyTicketSSOtoNAM() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","criticalbusiness", "sso", "nonstp"}, priority = 2)
	public void verifyNewUserSSOfromNAMtoCAMAndBuyTicket() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","criticalbusiness", "sso", "noncam"}, priority = 4)
	public void verifyBuyTicketsInSTPTransferInNAM() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","criticalbusiness", "sso", "nonstp"}, priority = 5)
	public void verifyBuyTicketsInCAMTransferInNAM() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","criticalbusiness", "sso", "nonstp"}, priority = 6)
	public void verifyBuyTicketsInCAMPrintScanInNAM() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","criticalbusiness", "sso", "nonstp"}, priority = 7)
	public void verifyBuyTicketsInCAMDonateInNAM() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","criticalbusiness", "sso", "noncam"}, priority = 8)
	public void verifyBuyTicketsInSTPDonateInNAM() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","criticalbusiness", "sso", "nonstp"}, priority = 9)
	public void verifyBuyTicketsInCAMSellInNAM() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","criticalbusiness", "sso", "noncam"}, priority = 10)
	public void verifyBuyTicketsInSTPPrintScanInNAM() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}	
	
	@Test(groups={"smoke","regression","user_journey","criticalbusiness", "sso", "noncam"}, priority = 10)
	public void verifygeneric404Error() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
}
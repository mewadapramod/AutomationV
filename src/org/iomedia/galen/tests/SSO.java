package org.iomedia.galen.tests;

import org.iomedia.framework.Driver;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.PickleEventWrapper;

@CucumberOptions(plugin = "json:target/cucumber-report-feature-composite.json", format = "pretty", features = {"features/sso.feature", "features/prodsanity.feature"}, glue = {"org.iomedia.galen.steps"}, monochrome = true, strict = true)
public class SSO extends Driver{

	@Test(groups={"smoke","regression","sso", "prod"}, dataProvider="scenarios", priority = 0)
	public void runSSOScenarios(PickleEventWrapper pickleEvent, CucumberFeatureWrapper cucumberFeature) throws Throwable {
		testNGCucumberRunner.runScenario(pickleEvent.getPickleEvent());
	}
	
	@Test(groups={"smoke","regression","sso", "prod"}, priority = 1)
	public void verifySSOtoClassicAMGRforExisitingUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod"}, priority = 2)
	public void verifySSOtoClassicAMGRforNewUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod","criticalbusiness"}, priority = 3)
	public void verifySSOtoClassicAMGRforMultipleUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod"}, priority = 7)
	public void verifyClassicAMGREdition() throws Throwable{
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod" , "switchAccount"}, priority = 6)
	public void verifySSOonSwitchingAccount() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod"}, priority = 4)
	public void verifySSOfromNAMtoSTPforExistingUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod"},priority = 5)
	public void verifySSOfromNAMtoSTPforNewUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod","criticalbusiness"}, priority = 8)
	public void verifySSOfromNAMtoSTPforMultipleUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod"}, priority = 9)
	public void verifySSOfromNAMtoCAMtoSTPforExistingUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod"}, priority = 10)
	public void verifySSOfromNAMtoCAMtoSTPforNewUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod"}, priority = 11)
	public void verifySSOfromNAMtoCAMtoSTPforMulitpleUsers() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod"}, priority = 12)
	public void verifySSOfromNAMtoSTPtoCAMforExistingUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod"}, priority = 13)
	public void verifySSOfromNAMtoSTPtoCAMforNewUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod"}, priority = 14)
	public void verifySSOfromNAMtoSTPtoCAMforMultipleUsers() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod"}, priority = 15)
	public void verifySSOfromSTPtoNAMtoCAMandEmailIDChangeInCAM() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod", "criticalbusiness"}, priority = 17)
	public void verifyTicketBuyFromSTPandRedirectToNAM() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod", "criticalbusiness"}, priority = 18)
	public void verifySSOfromNAMtoCAMandEmailIdChangeInCAM() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod"}, priority = 19)
	public void verifySSOfromSTPToNAMtoCAMforNewuserHavingEmailIDcontaining1() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod","criticalbusiness"}, priority = 20)
	public void verifySSOFromSTPtoNAMwithAccountId() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod","criticalbusiness"}, priority = 21)
	public void verifySSOFromSTPtoNAMtoCAMforMulitpleUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod","criticalbusiness"}, priority = 22)
	public void verifySSOFromSTPtoNAMtoCAMforNewUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod"}, priority = 23)
	public void verifySSOFromSTPToNAMForNewUserHavingEmailIdcontaining1() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod","criticalbusiness"}, priority = 24)
	public void verifySSOFromSTPtoNAMForMultipleUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod", "criticalbusiness"}, priority = 25)
	public void verifySSOFromSTPtoNAMForNewUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod","criticalbusiness"}, priority = 26)
	public void verifySSOFromSTPtoNAMForExistingUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","sso", "prod"}, priority = 27)
	public void verifySSORedirectionFromPromotilestoSTP() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
}
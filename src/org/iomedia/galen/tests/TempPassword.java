package org.iomedia.galen.tests;

import org.iomedia.framework.Driver;
import org.testng.annotations.Test;
import cucumber.api.CucumberOptions;
	
@CucumberOptions(plugin = "json:target/cucumber-report-feature-composite.json", format = "pretty", features = "features/userJourneys.feature", glue = {"org.iomedia.galen.steps"}, monochrome = true, strict = true)	
public class TempPassword extends Driver {

	@Test(groups={"smoke","regression","user_journey","criticalbusiness","resetpassword"}, priority = 1)
	public void verifyResetPassword() throws Throwable {
	   runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","criticalbusiness","resetpassword"}, priority = 2, dependsOnMethods="verifyResetPassword")
	public void verifyUserLoginafterResetPasswordRequest() throws Throwable {
	   runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","criticalbusiness","resetpassword"}, priority = 3 ,enabled=false)
	public void verifyResetPasswordExpireafterChangingPassword() throws Throwable {
	   runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","criticalbusiness","resetpassword"}, priority = 4, enabled=false)
	public void claimTicketafterResetingPassword() throws Throwable {
	   runScenario(Dictionary.get("SCENARIO"));
	}
}

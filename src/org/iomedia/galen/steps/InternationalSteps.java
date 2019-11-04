package org.iomedia.galen.steps;

import org.iomedia.common.BaseUtil;
import org.iomedia.galen.common.Screenshot;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.pages.Homepage;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class InternationalSteps {
	BaseUtil base;
	Screenshot shot;
	Utils utils;
	Homepage page;
	org.iomedia.framework.Assert Assert;
	
	public InternationalSteps(BaseUtil base, Screenshot shot, Utils utils, Homepage page, org.iomedia.framework.Assert Assert) {
		this.base = base;
		this.shot = shot;
		this.utils = utils;
		this.page = page;
		this.Assert = Assert;
	}
	
	@When("^Change the language of the site to (.+) from Dropdown$")
	public void change_language_of_site_from_dropdown(String language) {
		language = (String) base.getGDValue(language);
		page.clickLanguageSelector();
		page.selectLanguageFromDropdowninHomepage(language);		
	}
	
	@Then("^Verify Login Component Texts$")
	public void verify_login_component_texts() throws Exception {
		//Assert.assertEquals(utils.ReadFromPOFile(base.Dictionary.get("SIGN_IN")), page.getSignInTextInOtherLang(null), "Translation gets matched ");
		page.clickSignInLinkinOtherLang(null);
//		Assert.assertEquals(utils.ReadFromPOFile(base.Dictionary.get("SignInUp")), "Se Connecter / S'inscrire", "Translation gets matched ");
		Assert.assertEquals(page.getSignInTextinOtherLanguage(), "Se Connecter / S'inscrire", "Translation gets matched ");
		page.clickSignInLink(null);
//		Assert.assertEquals(utils.ReadFromPOFile(base.Dictionary.get("SIGN_IN")), page.getSignInTextinOtherLanguage(), "Translation gets matched ");
		Assert.assertEquals(page.getRememberMeTextinOtherLanguage(),"Souviens-toi de moi", "Translation gets matched ");
		Assert.assertEquals(page.getForgotPasswordTextinOtherLanguage(), "Mot de passe oublié", "Translation gets matched ");
		Assert.assertEquals(utils.ReadFromPOFile(base.Dictionary.get("SIGN_IN")).toUpperCase(), page.getSignInButtonTextinOtherLang(), "Translation gets matched ");
		Assert.assertEquals(page.DontHaveAccountTextinOtherLanguage(), "Vous n'avez pas de compte? Cliquez ici", "Translation gets matched ");
	}
	
	@Then("^Verify Footer Text in Each Page$")
	public void verify_footer_text() throws Exception {
		Assert.assertEquals(page.getCopyRightTextinOtherLanguage(),"© 1999-2019 Ticketmaster. Tous droits réservés.", "Translation gets matched ");
		Assert.assertEquals(page.getPolicyinOtherLanguage(),"Politique de confidentialité", "Translation gets matched ");
		Assert.assertEquals(page.getTermsinOtherLanguage(),"Conditions d'utilisation", "Translation gets matched ");
//		Assert.assertEquals(utils.ReadFromPOFile(base.Dictionary.get("PRIVACY_POLICY")), page.getPolicyinOtherLanguage(), "Translation gets matched ");
//		Assert.assertEquals(utils.ReadFromPOFile(base.Dictionary.get("TERMS")), page.getTermsinOtherLanguage(), "Translation gets matched ");	
	}
	
	@Then("^Verify URL gets appended with (.+)$")
	public void verify_url_appended_with(String uri) {
		uri = (String) base.getGDValue(uri);
		Assert.assertTrue(page.getCurrentURL().contains(base.Environment.get("APP_URL").trim() + uri), "Redirected to Correct URL");
	}
	
	@Then("^Verify Dashboard Page Texts$")
	public void verify_dashboard_page_texts() {
	}
}
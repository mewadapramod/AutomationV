package org.iomedia.galen.tests;

import org.iomedia.framework.Driver;
import org.iomedia.galen.pages.AddNewPage;
import org.iomedia.galen.pages.AdminLogin;
import org.iomedia.galen.pages.ContentPageEdit;
import org.iomedia.galen.pages.HomePageEdit;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.pages.Homepage;
import org.iomedia.galen.pages.SuperAdminPanel;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;

@CucumberOptions(plugin = "json:target/cucumber-report-feature-composite.json", format = "pretty", features = {"features/userJourneys.feature", "features/menuManager.feature", "features/prodsanity.feature", "features/Internationalization.feature", "features/cms.feature"}, glue = {"org.iomedia.galen.steps"}, monochrome = true, strict = true)
public class CMS extends Driver {
	
	private Homepage homepage; 
	private SuperAdminPanel superAdminPanel;
	private AddNewPage addNewPage;
	private ContentPageEdit contentPageEdit;
	private HomePageEdit homePageEdit;
	private Utils utils;
	private AdminLogin adminLogin;
	
	String username;
	String password;
	

	@BeforeMethod(alwaysRun=true)
	public void init() throws Exception	{
		homepage = new Homepage(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		superAdminPanel = new SuperAdminPanel(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		addNewPage = new AddNewPage(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
		contentPageEdit = new ContentPageEdit(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		homePageEdit = new HomePageEdit(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
		utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		
		if(Dictionary.get("cmsUserName").trim().equalsIgnoreCase(""))
		{
		adminLogin = new AdminLogin(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		adminLogin.adminLogin();
		utils.navigateTo("/admin/people");
	    adminLogin.createAdduserCMS();
		utils.navigateTo("/user/logout");
		Dictionary.put("cmsUserName", "automationsuport4");
		Dictionary.put("cmsPassword", "123456");
			username = "automationsuport4";
			password = "123456";
		}
	}
	
	@Test(groups={"smoke","regression","cmsFunctional"})
	public void verifyAddNewContentPage() throws Exception{
		load("/user/login");
		superAdminPanel.login(username,password);
		superAdminPanel.clickAddPageLeftMenu();
		addNewPage.clickSelectContentPage();
		addNewPage.selectContentPageLayout();
		((JavascriptExecutor) getDriver()).executeScript("$('#doorbell-button').remove();");
		addNewPage.clickNext();
		Assert.assertTrue(contentPageEdit.verifyPageTypeContent());
		contentPageEdit.clickPageSettings();
		contentPageEdit.typePageName(Dictionary.get("PageName"));
		contentPageEdit.typePageTitle(Dictionary.get("PageTitle"));
		contentPageEdit.clickSaveSettings();
		superAdminPanel.clickPageManager();
		superAdminPanel.clickViewPagemenuItem();
		Assert.assertTrue(superAdminPanel.verifyPageAvailableViewPages(Dictionary.get("PageName")));	
	}
	
	@Test(groups={"smoke","regression","cmsFunctional"})
	public void verifyAddNewHomePage() throws Exception{
		load("/user/login");
		superAdminPanel.login(username,password);
		superAdminPanel.clickAddPageLeftMenu();
		addNewPage.clickSelectHomePage();
		addNewPage.selectHomePageLayout();
		((JavascriptExecutor) getDriver()).executeScript("$('#doorbell-button').remove();");
		addNewPage.clickNext();
		Assert.assertTrue(homePageEdit.verifyPageTypeHome());
		homePageEdit.clickPageSettings();
		homePageEdit.typePageName(Dictionary.get("PageName"));
		homePageEdit.typePageTitle(Dictionary.get("PageTitle"));
		homePageEdit.clickSaveSettings();
		superAdminPanel.clickPageManager();
		superAdminPanel.clickViewPagemenuItem();
		Assert.assertTrue(superAdminPanel.verifyPageAvailableViewPages(Dictionary.get("PageName")));	
	}
	
	@Test(groups={"smoke","regression","cmsFunctional"})
	public void verifyEditContentPageTitleAndText() throws Exception{
		load("/user/login");
		superAdminPanel.login(username,password);
		superAdminPanel.clickPageInPageManager("Content Page");
		contentPageEdit.editTitle("This Title");
		contentPageEdit.clickSaveEditor();
		contentPageEdit.editContent("Content Text");
		contentPageEdit.clickSaveEditor();
		Assert.assertEquals(contentPageEdit.getPageTitle(), "This Title");
		Assert.assertEquals(contentPageEdit.getPageTitle(), "Content Text");
	}

	@Test(groups={"smoke","regression","cmsFunctional"})
	public void verifyFirstpromotilesBuyTicketsForPublicUsers() throws InterruptedException{
		load("/user/login");
		superAdminPanel.login(username,password);
		superAdminPanel.viewPromoTiles();
		Assert.assertEquals(superAdminPanel.getpromotilesTitle(), "Promo Tiles", "Promotiles title is displayed");
		superAdminPanel.edit();
		Assert.assertEquals("Public",superAdminPanel.getLoggedInTxt(),"Public header text is displayed");
		superAdminPanel.clickPromotilesecondEdit();
		Assert.assertEquals("Public", superAdminPanel.selectPublicPromoTileGroup(),"Public promotile group is selected");
		superAdminPanel.typeHeaderText(Dictionary.get("HeaderText"));
		superAdminPanel.typepromoTitle(Dictionary.get("PromoTitle"));
		superAdminPanel.typeurl(Dictionary.get("URL"));
		superAdminPanel.colorPicker(Dictionary.get("backgroundColor"));
		String URL = superAdminPanel.getTitleLink();
		superAdminPanel.save();
		superAdminPanel.logout();
		utils.navigateTo("/");
		Assert.assertEquals(superAdminPanel.getpromotilesheadertext(), Dictionary.get("HeaderText")," Promotiles header texts are matching for public users");
		Assert.assertEquals(Dictionary.get("URL"), URL,"Redirecting URLs are matching for first promotiles");
		utils.navigateTo(URL);
		Assert.assertEquals(true, superAdminPanel.checkRedirection(),"First Promotile Redirected to login page for Public User");
	}
	
	@Test(groups={"smoke","regression","cmsFunctional"})
	public void verifyFirstpromotilesBuyTicketsForLoggedInUsers() throws Exception{
		load("/user/login");
		superAdminPanel.login(username,password);
		superAdminPanel.viewPromoTiles();
		Assert.assertEquals(superAdminPanel.getpromotilesTitle(), "Promo Tiles", "Promotiles title is displayed");
		superAdminPanel.loggedInEdit();
		Assert.assertEquals("Logged In",superAdminPanel.getLoggedInTxt(),"Logged In header text is displayed");
		superAdminPanel.clickPromotilesecondEdit();
		superAdminPanel.typeHeaderText(Dictionary.get("HeaderText"));
		superAdminPanel.typepromoTitle(Dictionary.get("PromoTitle"));
		Assert.assertEquals("Logged In",superAdminPanel.selectPromoTileGroup(),"Logged In promotile group is selected");
		superAdminPanel.typeurl(Dictionary.get("URL"));
		superAdminPanel.colorPicker(Dictionary.get("backgroundColor"));
		String URL= superAdminPanel.getTitleLink();
		superAdminPanel.save();
		superAdminPanel.logout();
		Assert.assertEquals(Dictionary.get("URL"), URL,"Redirecting URLs are matching for first promotiles for Logged In users");
		utils.navigateTo("/");
		homepage.login("", "", null, false);
		Assert.assertEquals(superAdminPanel.getpromotilesheadertext(), Dictionary.get("HeaderText"),"Promotiles header texts are matching for Logged In users");
		superAdminPanel.clickFirstpromotile();
		Assert.assertEquals(true, superAdminPanel.FirstTileLoggedIn(),"First Tiles is redirecting to the correct URL for Logged In users after click");
		utils.navigateTo(URL);
        Assert.assertEquals(true, superAdminPanel.FirstTileLoggedIn(),"URL is redirecting to the correct URL for Logged In users");
	}
	
	@Test(groups={"smoke","regression","cmsFunctional"})
	public void verifySecondpromotilesForLoggedInUsers() throws Exception{
		load("/user/login");
		superAdminPanel.login(username,password);
		superAdminPanel.viewPromoTiles();
		Assert.assertEquals(superAdminPanel.getpromotilesTitle(), "Promo Tiles", "Promotiles title is displayed");
		superAdminPanel.loggedInEdit();
		Assert.assertEquals("Logged In",superAdminPanel.getLoggedInTxt(),"Logged In header text is displayed");
		superAdminPanel.clickSecondPromotileEdit();
		superAdminPanel.typeHeaderText(Dictionary.get("HeaderText"));
		superAdminPanel.typepromoTitle(Dictionary.get("PromoTitle"));
		superAdminPanel.typeurl(Dictionary.get("URL"));
		superAdminPanel.colorPicker(Dictionary.get("backgroundColor"));
		String URL= superAdminPanel.getTitleLink();
		Assert.assertEquals("Logged In",superAdminPanel.selectPromoTileGroup(),"Logged In promotile group is selected");
		superAdminPanel.save();
		superAdminPanel.logout();
		utils.navigateTo("/");
		homepage.login("", "", null, false);
        Assert.assertEquals(superAdminPanel.getloggedInPromoTitleText(), Dictionary.get("HeaderText"),"header texts are matching for second promotiles");
        Assert.assertEquals(Dictionary.get("URL"), URL,"Redirecting URLs are matching for second promotiles");
        superAdminPanel.secondPromotile();
		Assert.assertEquals(true, superAdminPanel.FirstTileLoggedIn(),"Second Tiles is redirecting to the correct URL for Logged In users after click");
		utils.navigateTo(URL);
        Assert.assertEquals(true, superAdminPanel.FirstTileLoggedIn(),"URL is redirecting to the correct URL for Logged In users");
	}
	
	@Test(groups={"smoke","regression","cmsFunctional"})
	public void verifySecondpromotilesForPublicUsers() throws InterruptedException{
		load("/user/login");
		superAdminPanel.login(username,password);
		superAdminPanel.viewPromoTiles();
		Assert.assertEquals(superAdminPanel.getpromotilesTitle(), "Promo Tiles", "Promotiles title is displayed");
		superAdminPanel.edit();
		Assert.assertEquals("Public",superAdminPanel.getLoggedInTxt(),"Public header text is displayed");
		superAdminPanel.clickSecondPromotileEdit();
		superAdminPanel.typeHeaderText(Dictionary.get("HeaderText"));
		superAdminPanel.typepromoTitle(Dictionary.get("PromoTitle"));
		superAdminPanel.typeurl(Dictionary.get("URL"));
		superAdminPanel.colorPicker(Dictionary.get("backgroundColor"));
		String URL= superAdminPanel.getTitleLink();
		Assert.assertEquals("Public",superAdminPanel.selectPublicPromoTileGroup(),"Public promotile group is selected");
		superAdminPanel.save();
		superAdminPanel.logout();
		utils.navigateTo("/");
		Assert.assertEquals(superAdminPanel.getPublicSecondPromoTitle(), Dictionary.get("HeaderText"),"header texts are matching for second promotiles");
		Assert.assertEquals(Dictionary.get("URL"), URL,"Redirecting URLs are matching for second promotiles");
		utils.navigateTo(URL);
		Assert.assertEquals(true, superAdminPanel.checkRedirection(),"Second Promotile Redirected to login page for Public User");
	}
	
	@Test(groups={"smoke","regression","cmsFunctional"})
	public void verifyThirdpromotilesBuyTicketsForPublicUsers() throws InterruptedException{
		load("/user/login");
		superAdminPanel.login(username,password);
		superAdminPanel.viewPromoTiles();
		Assert.assertEquals(superAdminPanel.getpromotilesTitle(), "Promo Tiles", "Promotiles title is displayed");
		superAdminPanel.edit();
		Assert.assertEquals("Public",superAdminPanel.getLoggedInTxt(),"Public header text is displayed");
		superAdminPanel.clickContactUsPromotileEdit();
		superAdminPanel.typeHeaderText(Dictionary.get("HeaderText"));
		superAdminPanel.typepromoTitle(Dictionary.get("PromoTitle"));
		superAdminPanel.typeurl(Dictionary.get("URL"));
		superAdminPanel.colorPicker(Dictionary.get("backgroundColor"));
		String URL= superAdminPanel.getTitleLink();
		Assert.assertEquals("Public",superAdminPanel.selectPublicPromoTileGroup(),"Public promotile group is selected");
		superAdminPanel.save();
		superAdminPanel.logout();
		utils.navigateTo("/");
		Assert.assertEquals(superAdminPanel.getThirdPromotileText(), Dictionary.get("HeaderText") ,"header texts are matching for public users");
		Assert.assertEquals(Dictionary.get("URL"), URL,"Redirecting URLs are matching for third promotiles");
		utils.navigateTo(URL);
		Assert.assertEquals(true, superAdminPanel.checkRedirection(),"Third Promotile Redirected to login page for Public User");
	}
	
	@Test(groups={"smoke","regression","cmsFunctional"})
	public void verifyThirdPromotilesLoggedInUsers() throws Exception{
		load("/user/login");
		superAdminPanel.login(username,password);
		superAdminPanel.viewPromoTiles();
		Assert.assertEquals(superAdminPanel.getpromotilesTitle(), "Promo Tiles", "Promotiles title is displayed");
		superAdminPanel.loggedInEdit();
		Assert.assertEquals("Logged In",superAdminPanel.getLoggedInTxt(),"Logged In header text is displayed");
		superAdminPanel.clickContactUsPromotileEdit();
		superAdminPanel.typeHeaderText(Dictionary.get("HeaderText"));
		superAdminPanel.typepromoTitle(Dictionary.get("PromoTitle"));
		superAdminPanel.typeurl(Dictionary.get("URL"));
		superAdminPanel.colorPicker(Dictionary.get("backgroundColor"));
		String URL= superAdminPanel.getTitleLink();
		Assert.assertEquals("Logged In",superAdminPanel.selectPromoTileGroup(),"Logged In promotile group is selected");
		superAdminPanel.save();
		superAdminPanel.logout();
		utils.navigateTo("/");
		homepage.login("", "", null, false);
        Assert.assertEquals(superAdminPanel.getloggedIncontactUsHeaderText(), Dictionary.get("HeaderText"),"header texts are matching for Contact Us promotiles");
        Assert.assertEquals(Dictionary.get("URL"), URL,"Redirecting URLs are matching for third promotiles");
	    superAdminPanel.thirdPromotile();
		Assert.assertEquals(true, superAdminPanel.FirstTileLoggedIn(),"Third Tiles is redirecting to the correct URL for Logged In users after click");
		utils.navigateTo(URL);
	    Assert.assertEquals(true, superAdminPanel.FirstTileLoggedIn(),"URL is redirecting to the correct URL for Logged In users");
	}
	
	@Test(groups={"smoke","regression","cmsFunctional"})
	public void verifyFourthPromotilesForPublicUsers() throws InterruptedException{
		load("/user/login");
		superAdminPanel.login(username,password);
		superAdminPanel.viewPromoTiles();
		Assert.assertEquals(superAdminPanel.getpromotilesTitle(), "Promo Tiles", "Promotiles title is displayed");
		superAdminPanel.edit();
		Assert.assertEquals("Public",superAdminPanel.getLoggedInTxt(),"Public header text is displayed");
		superAdminPanel.clickFourthPromotileEdit();
		superAdminPanel.typeHeaderText(Dictionary.get("HeaderText"));
		superAdminPanel.typepromoTitle(Dictionary.get("PromoTitle"));
		superAdminPanel.typeurl(Dictionary.get("URL"));
		superAdminPanel.colorPicker(Dictionary.get("backgroundColor"));
		String URL= superAdminPanel.getTitleLink();
		Assert.assertEquals("Public",superAdminPanel.selectPublicPromoTileGroup(),"Public promotile group is selected");
		superAdminPanel.save();
		superAdminPanel.logout();
		utils.navigateTo("/");
		Assert.assertEquals(superAdminPanel.getpublicContactus(), Dictionary.get("HeaderText"),"header texts are matching for public Contact Us");
		Assert.assertEquals(Dictionary.get("URL"), URL,"Redirecting URLs are matching for fourth promotiles");
		utils.navigateTo(URL);
		Assert.assertEquals(true, superAdminPanel.checkRedirection(),"Fourth Promotile Redirected to login page for Public User");
	}
	
	@Test(groups={"smoke","regression","cmsFunctional"})
	public void verifyFourthPromotilesForLoggedInUsers() throws Exception {
		load("/user/login");
		superAdminPanel.login(username,password);
		superAdminPanel.viewPromoTiles();
		Assert.assertEquals(superAdminPanel.getpromotilesTitle(), "Promo Tiles", "Promotiles title is displayed");
		superAdminPanel.loggedInEdit();
		Assert.assertEquals("Logged In",superAdminPanel.getLoggedInTxt(),"Logged In header text is displayed");
		superAdminPanel.clickFourthPromotileEdit();
		superAdminPanel.typeHeaderText(Dictionary.get("HeaderText"));
		superAdminPanel.typepromoTitle(Dictionary.get("PromoTitle"));
		superAdminPanel.typeurl(Dictionary.get("URL"));
		superAdminPanel.colorPicker(Dictionary.get("backgroundColor"));
		String URL= superAdminPanel.getTitleLink();
		Assert.assertEquals("Logged In",superAdminPanel.selectPromoTileGroup(),"Logged In promotile group is selected");
		superAdminPanel.save();
		superAdminPanel.logout();
		utils.navigateTo("/");
		homepage.login("", "", null, false);
        Assert.assertEquals(superAdminPanel.getInvoiceHeaderTextForLoggedInUsers(), Dictionary.get("HeaderText"),"header texts are matching for Invoice promotiles");
        Assert.assertEquals(Dictionary.get("URL"), URL,"Redirecting URLs are matching for fourth promotiles");
	    superAdminPanel.fourthPromotile();
		Assert.assertEquals(true, superAdminPanel.FirstTileLoggedIn(),"Fourth Tiles is redirecting to the correct URL for Logged In users after click");
		utils.navigateTo(URL);
	    Assert.assertEquals(true, superAdminPanel.FirstTileLoggedIn(),"URL is redirecting to the correct URL for Logged In users");
	}
	
	@Test(groups={"smoke","regression","cmsFunctional"})
	public void verifyAddPromotilesForLoggedInUsers() throws Exception {
		load("/user/login");
		superAdminPanel.login(username,password);
		superAdminPanel.viewPromoTiles();
		Assert.assertEquals(superAdminPanel.getpromotilesTitle(), "Promo Tiles", "Promotiles title is displayed");
		superAdminPanel.loggedInEdit();
		Assert.assertEquals(superAdminPanel.getLoggedInTxt(), "Logged In","Logged In header text is displayed");
		superAdminPanel.clickAddPromotiles();
		superAdminPanel.selectPromoTileLogo();
		superAdminPanel.selectNext();
		superAdminPanel.typePromoTileName(Dictionary.get("PromoTileName"));
		superAdminPanel.selectActiveStatus();
		superAdminPanel.typePromoTileTitle(Dictionary.get("PromoTileTitle"));
		superAdminPanel.typeBackgroudColour(Dictionary.get("Color"));
		superAdminPanel.typeTitleUrl(Dictionary.get("URL"));
		superAdminPanel.openlink();
		superAdminPanel.save();
		superAdminPanel.viewPromoTiles();
		superAdminPanel.loggedInEdit();
		superAdminPanel.reorder(Dictionary.get("PromoTileName"));
		superAdminPanel.logout();
		utils.navigateTo("/");
		homepage.login("", "", null, false);
		String[] loggedInPromotiles = superAdminPanel.get2N3LoggedInPromoTitleText();
		if(loggedInPromotiles[0].equals(Dictionary.get("PromoTileName")))
			Assert.assertEquals(loggedInPromotiles[0], Dictionary.get("PromoTileName"), "PromoTileNames are matching for new add promotiles");
		else if(loggedInPromotiles[1].equals(Dictionary.get("PromoTileName")))
			Assert.assertEquals(loggedInPromotiles[1], Dictionary.get("PromoTileName"), "PromoTileNames are matching for new add promotiles");
		else
			Assert.assertEquals(loggedInPromotiles[0], Dictionary.get("PromoTileName"), "PromoTileNames are matching for new add promotiles");
       // Assert.assertEquals(Dictionary.get("URL"), URL,"Redirecting URLs are matching for fourth promotiles");
	}
	
	@Test(groups={"smoke","regression","cmsFunctional"})
	public void verifyAddPromotilesForPublicUsers() throws Exception {
		load("/user/login");
		superAdminPanel.login(username,password);
		superAdminPanel.viewPromoTiles();
		Assert.assertEquals(superAdminPanel.getpromotilesTitle(), "Promo Tiles", "Promotiles title is displayed");
		superAdminPanel.edit();
		Assert.assertEquals(superAdminPanel.getLoggedInTxt(), "Public", "Public header text is displayed");
		superAdminPanel.clickAddPromotiles();
		superAdminPanel.selectPromoTileLogo();
		superAdminPanel.selectNext();
		superAdminPanel.typePromoTileName(Dictionary.get("PromoTileName"));
		superAdminPanel.selectActiveStatus();
		superAdminPanel.typePromoTileTitle(Dictionary.get("PromoTileTitle"));
		superAdminPanel.typeBackgroudColour(Dictionary.get("Color"));
		superAdminPanel.typeTitleUrl(Dictionary.get("URL"));
		superAdminPanel.openlink();
		superAdminPanel.save();
		superAdminPanel.viewPromoTiles();
		superAdminPanel.edit();
		superAdminPanel.reorder(Dictionary.get("PromoTileName"));
		superAdminPanel.logout();
		utils.navigateTo("/");
		String[] publicPromotiles = superAdminPanel.get2N3PublicPromoTitleText();
		if(publicPromotiles[0].equals(Dictionary.get("PromoTileName")))
			Assert.assertEquals(publicPromotiles[0], Dictionary.get("PromoTileName"), "PromoTileNames are matching for new add promotiles");
		else if(publicPromotiles[1].equals(Dictionary.get("PromoTileName")))
			Assert.assertEquals(publicPromotiles[1], Dictionary.get("PromoTileName"), "PromoTileNames are matching for new add promotiles");
		else
			Assert.assertEquals(publicPromotiles[0], Dictionary.get("PromoTileName"), "PromoTileNames are matching for new add promotiles");
	}
	
	@Test(groups={"smoke","regression","cmsFunctional"})
	public void verifyHomepagePageSettings() throws InterruptedException {
		load("/user/login");
		superAdminPanel.login(username,password);
		superAdminPanel.getType();
		superAdminPanel.loggedInEdit();
		superAdminPanel.clickPageSettings();
		superAdminPanel.typePageName(Dictionary.get("PageName"));
		superAdminPanel.typePageTitle(Dictionary.get("PageTitle"));
		superAdminPanel.clickPageUrl();
		superAdminPanel.typePageUrl(Dictionary.get("PageUrl"));
		superAdminPanel.uploadBackgroundImage();
		superAdminPanel.save();
	}
	
	@Test(groups={"smoke","regression","cmsFunctional"})
	public void verifyCKEditor() throws InterruptedException {
		load("/user/login");
		superAdminPanel.login(username,password);
		superAdminPanel.loggedInEdit();
		superAdminPanel.getCKEditorText();
		superAdminPanel.getCKEditorText2();
		superAdminPanel.typeCKEditorText(Dictionary.get("CKEditorTitle"));
		superAdminPanel.typeCKEditorText2(Dictionary.get("CKEditorText"));
		superAdminPanel.logout();
		utils.navigateTo("/");
		Assert.assertEquals(Dictionary.get("CKEditorTitle"), superAdminPanel.getCKEditorTitle(),"CKEditor Titles are matching");
		Assert.assertEquals(Dictionary.get("CKEditorText"), superAdminPanel.getCKEditorBody(),"CKEditor Body text are matching");
	}
	
	@Test(groups={"smoke","regression","user_journey","cmsFunctional", "menuManager"}, priority = 11)
	public void verifyCreatingPublicMenuDisplayForEndUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}	
	
	@Test(groups={"smoke","regression","user_journey","cmsFunctional", "menuManager"}, priority = 12)
	public void verifyCreatingLoggedInMenuDisplayForEndUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","cmsFunctional", "menuManager"}, priority = 13)
	public void verifyActivePublicMenuDisplayForEndUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","cmsFunctional", "menuManager"}, priority = 14)
	public void verifyActiveLoggedInMenuDisplayForEndUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","cmsFunctional", "menuManager"}, priority = 15)
	public void verifyCreatingHybridMenuDisplayForEndUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","cmsFunctional", "menuManager"}, priority = 15)
	public void verifyPublicMenuChangeLoggedIN() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","cmsFunctional", "menuManager"}, priority = 15)
	public void verifyLoggedInMenuChangeHybrid() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","user_journey","cmsFunctional", "menuManager"}, priority = 15)
	public void verifyChangingMenuInPageSettingShowNewMenu() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression", "cmsFunctional", "Internationalization"})
	public void verifyLanguageInstallation() throws Throwable{
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression", "cmsFunctional", "Internationalization"}, dependsOnMethods={"verifyLanguageInstallation"})
	public void deleteLanguage() throws Throwable{
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression", "cmsFunctional"})
	public void addInvoiceInCms() throws Throwable{
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression", "cmsFunctional"})
	public void signInComponent() throws Throwable{
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression", "cmsFunctional"})
	public void verifycmsdashboardconfiguration() throws Throwable{
		runScenario(Dictionary.get("SCENARIO"));
	}
	public void editInvoiceInCms() throws Throwable{
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression", "cmsFunctional"})
	public void verifyTicketSalesPage() throws Throwable{
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression", "cmsFunctional"})
	public void verifyCMSVersion() throws Throwable{
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression", "cmsFunctional"})
	public void VerifyEmailReporting() throws Throwable{
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression", "cmsFunctional"})
	public void Verifytransactionalpagesmenu() throws Throwable{
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression", "cmsFunctional"})
	public void verifyHomepagecreation() throws Throwable{
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	
}

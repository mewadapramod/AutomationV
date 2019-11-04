package org.iomedia.galen.pages;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.galen.common.Utils;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;

public class SuperAdminPanel extends BaseUtil{

	public SuperAdminPanel(WebDriverFactory driverFactory, HashMapNew Dictionary,HashMapNew Environment, Reporting Reporter,org.iomedia.framework.Assert Assert,org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<HashMapNew> sTestDetails) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);	
	}
	
//	private By userId = By.xpath("//*[@id='edit-name']");
//	private By pass= By.xpath("//*[@id='edit-pass']");
	private By leftMenuToggle= By.xpath("//*[@class='leftMenuToggle']");
	private By pageTable = By.xpath("//table/tbody/*");
	private By addNewPage = By.xpath("//*[contains(@class,'btn-primary') and text()='Add New']");
	private By addPageMenuItem = By.xpath("//*[contains(@class,'add-page')]");
//	private By viewpageMenuItem = By.xpath("//*[contains(@class,'view-page')]");

	private By pageManager = By.cssSelector("li[class*='page-manager'] > a");
	private By promoTiles = By.cssSelector("li[class*='promo-tiles'] > a");
	private By viewPromoMenuItem = By.cssSelector("a.view-promo-tiles");
	//private By edit = By.xpath(".//*[contains(@class,'table-hover')]//tr[1]/td[4]/a/i[text()='settings']");
	private By edit = By.xpath("//td[contains(text(),'Public')]/../td//a/i[1]");
	private By loggedInEdit = By.xpath("(//td[contains(text(),'Logged In')]/../td//a/i[1])[2]| //td[contains(text(),'Logged In')]/../td//a/i[1]");
	private By loggedInText = By.xpath(".//*[@id='select-group-0']/a[1]");
	private By pageSetting = By.xpath(".//*[contains(@class,'block-page-setting-button')]");
	private By username = By.xpath(".//*[@id='edit-name']");
	private By password = By.xpath(".//*[@id='edit-pass']");
	private By login = By.xpath(".//*[@id='edit-submit']");
	private By promotileEdit = By.xpath("(.//*[contains(@id,'draggableviews-table-promo-tile-page')]//tr[contains(@class, 'active_promotile')])[1]//i[text()='settings']");
	private By promotileEditSecond = By.xpath("(.//*[contains(@id,'draggableviews-table-promo-tile-page')]//tr[contains(@class, 'active_promotile')])[2]//i[text()='settings']");
	private By invoicePromotileEdit = By.xpath("(.//*[contains(@id,'draggableviews-table-promo-tile-page')]//tr[contains(@class, 'active_promotile')])[4]//i[text()='settings']");
	private By contactUsPromotileEdit = By.xpath("(.//*[contains(@id,'draggableviews-table-promo-tile-page')]//tr[contains(@class, 'active_promotile')])[3]//i[text()='settings']");
//	private By headetText = By.xpath(".//*[@id='edit-promo-tile-name']");
	//private By headetText = By.xpath(".//*[@id='edit-page-name-0-value']");
	private By headetText = By.xpath(".//*[@id='edit-promo-tile-name']");
	//private By promoTitle = By.xpath(".//*[@id='edit-title-0-value']");
	private By promoTitle = By.xpath(".//*[@id='edit-manage-tickets-text']");
	private By promoTitleGroup1 = By.cssSelector("input.select-dropdown");
	private By promoTitleGroup2 = By.cssSelector("#edit-promo-tile-group");
	
	//private By url = By.xpath(".//*[@id='edit-field-button-promotile-0-uri']");
	private By url = By.xpath(".//*[@id='edit-title-link']");
	private By linktext = By.xpath(".//*[@id='edit-title-link']");

//	private By publicGroup = By.xpath(".//*[@id='edit-field-group-promotile']/option[2]");
//	private By loginGroup = By.xpath(".//*[@id='edit-field-group-promotile']/option[3]");
	private By promotilesTitle = By.xpath(".//span[contains(@class,'js-quickedit-page-title')]");
	private By preview = By.xpath(".//*[@id='edit-preview']");
	private By icon = By.xpath(".//*[@id='edit-field-promotile-icon-0-value']/div[3]/label/span[1]");
	private By promotilesheadertext = By.xpath(".//*[contains(@class,'views-row clearfix')]/div[1]//span");
	private By publicSecondPromoTitle = By.xpath(".//*[contains(@class,'views-row clearfix')]/div[2]//span");
	private By publicThirdPromoTitle = By.xpath(".//*[contains(@class,'views-row clearfix')]/div[3]//span");
	private By publicContactus = By.xpath(".//*[contains(@class,'views-row clearfix')]/div[4]//span");
	private By save = By.xpath("//button[@id='edit-submit']");
	private By loggedInPromoTitleText = By.xpath(".//*[contains(@class,'views-row clearfix')]/div[2]/a/div[2]/span");
	private By loggedInHeaderText = By.xpath(".//*[contains(@class,'views-row clearfix')]/div[1]/a/div[2]/span");
	private By loggedInPayInvoice = By.xpath(".//*[contains(@class,'views-row clearfix')]/div[3]/a/div[2]/span");
	private By loggedInContactUs = By.xpath(".//*[contains(@class,'views-row clearfix')]/div[4]/a/div[2]/span");
	private By colorPicker = By.xpath(".//*[@id='edit-background-color']");
	private By backgroundColorR = By.xpath(".//*[@class='colorpicker']/div[6]/input");
	private By backgroundColorG = By.xpath(".//*[@class='colorpicker']/div[7]/input");
	private By backgroundColorB = By.xpath(".//*[@class='colorpicker']/div[8]/input");
	private By firstPromotileforLoggedInUsers = By.xpath(".//*[contains(@id,'draggableviews-table-promo-tile-page')]/tbody/tr[1]/td[2]");
	private By loggedInSecondPromotiles = By.xpath(".//*[contains(@id,'draggableviews-table-promo-tile-page')]/tbody/tr[2]/td[2]");
	private By loggedInInvoicePromotiles = By.xpath(".//*[contains(@id,'draggableviews-table-promo-tile-page')]/tbody/tr[4]/td[2]");
	private By loggedInInvoiceHeaderText = By.xpath(".//*[contains(@class,'views-row clearfix')]/div[4]/a/div[2]/span");
	private By loggedInContactUSText = By.xpath(".//*[contains(@id,'draggableviews-table-promo-tile-page')]/tbody/tr[3]/td[2]");
	private By loggedIncontactUsText = By.xpath(".//*[contains(@class,'views-row clearfix')]/div[3]/a/div[2]/span");
	private By Link = By.xpath(".//*[@id='edit-title-link']");
	private By SignIn=By.xpath("//button[@type='submit']");
	private By First = By.xpath(".//*[@id='contentWrapper']/div[1]/h1/strong");
	private By Group = By.xpath(".//*[@id='draggableviews-table-promo-tile-page-2']/caption/ul/li/span");
	private By saveOrder = By.id("edit-save-order");
	
	private By downloadTogglelabel = By.xpath(".//label[@for='edit-manage-ticket-configuration-download']");
	private By sellTogglelabel = By.xpath(".//label[@for='edit-manage-ticket-configuration-sell']");
	private By sendTogglelabel = By.xpath(".//label[@for='edit-manage-ticket-configuration-send']");
	private By donateTogglelabel = By.xpath(".//label[@for='edit-manage-ticket-configuration-donate']");
	private By BarcodeTogglelabel = By.xpath(".//label[@for='edit-manage-ticket-configuration-mobile-enabled']");
	private By addPromotiles = By.cssSelector(".btn.btn-primary");
	private By promoTileName = By.cssSelector("#edit-promo-tile-name");
	private By promoTileGroup = By.cssSelector("#edit-promo-tile-group");
	private By promoTileTitle = By.cssSelector("#edit-manage-tickets-text");
	private By backgroudColour = By.cssSelector("#edit-background-color");
	private By titleUrl = By.cssSelector("#edit-title-link");
	private By openlink = By.cssSelector(".check");
	private By promoTileLogo = By.xpath(".//*[@id='edit-view-modes']/div[1]/label/div/div[1]");
	private By next = By.cssSelector("#edit-submit");
	private By activeStatus =By.xpath(".//*[contains(@for,'edit-active-status')]");
	private By promotitlename = By.xpath("");
	private By pageName = By.cssSelector("#edit-page-name");
	private By pageTitle = By.cssSelector("#edit-title");
	private By pageUrl = By.cssSelector("#edit-url-alias");
	private By pageUrlSettings = By.cssSelector(".settings_icon");
	private By uploadBackgroundImage = By.cssSelector("#edit-upload-background-image-upload");
	private By type = By.cssSelector(".views-field.views-field-type");
	private By ckEditorText = By.xpath(".//*[contains(@class,'group-')]/div[1]");
	private By ckEditorText2 = By.xpath(".//*[contains(@class,'group-')]/div[2]");
	private By CKEditorTitle = By.xpath(".//*[contains(@class,'group-left')]/div[1]/h3");
	private By CKEditorBody = By.xpath(".//*[contains(@class,'group-left')]/div[2]/p");
	private By addLanguagePageHeaderText = By.xpath(".//*[contains(@class,'js-quickedit-page-title')]");
	private By clickonLanguageDropdown = By.cssSelector("input.select-dropdown");
	private By selectLanguage = By.xpath(".//*[@id='iom-lang-config-form']/div[2]/div[2]/div/div/div/div/ul/li");
	private By clickOnAddLanguageButton = By.name("op");
	private By languageTable = By.id("edit-languages");
	private By languageSwitcher = By.xpath(".//*[contains(@class,'selected-language')]");
	private By deleteButton = By.id("edit-submit");
	private By enterEmailTemplateTitle = By.id("edit-title");
	private By enterEmailTemplateType = By.xpath(".//*[@id='email-templates-add-form']/div[2]/div[1]/div[2]/div/div/div/input");
	private By enterEmailTemplateSubject = By.id("edit-subject");
	private By saveEmailTemplate = By.id("edit-submit");
	private By enterInvoiceTemplateTitle = By.id("edit-title");
	private By saveInvoiceTemplate = By.id("edit-submit");
//	private By selectPaymentEmailTypeOnInvoice = By.id("edit-box4-success-email-payment-success-email-template");
//	private By selectInvoiceEmailTypeOnInvoice = By.id("edit-box4-invoice-email-invoice-email-template");
	private By selectEmailTemplate = By.xpath(".//*[@id='edit-box4-success-email']/div/div[2]/div/div/div/input");
	private By EmailTemplateDropdown = By.xpath(".//*[@id='edit-box4-success-email']/div/div[2]/div/div/div/ul/li");
	private By selectInvoiceEmailTemplate = By.xpath(".//*[@id='edit-box4-invoice-email']/div/div[2]/div/div/div/input");
	private By InvoiceEmailTemplateDropdown = By.xpath(".//*[@id='edit-box4-invoice-email']/div/div[2]/div/div/div/ul/li");
	private By clickInvoiceEmailsTabs = By.xpath("//*[contains(@class,'tabbable')]/ul/li[4]/a");
	private By selectTemplateType= By.xpath(".//*[@id='email-templates-add-form']/div[2]/div[1]/div[2]/div/div/div/ul/li");
	private By langCount = By.xpath(".//*[@id='edit-languages']/tbody/tr");
	
	private By menuManager= By.xpath("//*[@class='menu-manager hassub']");
	private By viewMenu= By.xpath("//*[@class='mm-view-menu']");
	private By addMenu= By.id("edit-add-new");
	private By createMenuTexts = By.cssSelector(".iom-form-header");
	
	private By menuName = By.name("menuName");
	private By publicAccesslevel = By.xpath(".//label[@data-react-toolbox='checkbox' and descendant::span[text()='Public']]");
	private By loggedInAccesslevel = By.xpath(".//label[@data-react-toolbox='checkbox' and descendant::span[text()='Logged In']]");
	private By internalPages = By.cssSelector("div[class*='menu-manager-pageHolder'] div[class*='menu-manager-pageHolder']:nth-child(1)  div[class*='menu-manager-listHeader']");
	private By transactionalPages = By.cssSelector("div[class*='menu-manager-pageHolder'] div[class*='menu-manager-pageHolder']:nth-child(2)  div[class*='menu-manager-listHeader']");
	private By externalPages = By.cssSelector("div[class*='menu-manager-pageHolder'] div[class*='menu-manager-pageHolder']:nth-child(3)  div[class*='menu-manager-listHeader']");
	private By pagesSearch = By.cssSelector("div[class*='menu-manager-pageHolder'] div[class*='menu-manager-pageContainer'][class*='visibleContainer'] input[name='pageSearch']");
	
//	private By dropLocation = By.cssSelector(".rst__placeholder");
//	private By dropLocation = By.cssSelector(".rst__tree > div");
	private By droppedNodes = By.cssSelector(".rst__node");
	private By saveMenu = By.cssSelector("[class*='menu-manager-saveBtn']");
	private By editPageMenu = By.id("edit-page-menu");
	private By externalPageAddNewButton = By.cssSelector("div[class*='menu-manager-addmoreBtn'] button");
	private By externalPageTitleMenu = By.xpath("//*[contains(@class,'menu-manager-externalContainer')]//input[@name='title']");
	private By externalPageLinkMenu = By.xpath("//*[contains(@class,'menu-manager-externalContainer')]//input[@name='path']");
	private By externalPageAddButton = By.xpath("//*[contains(@class,'menu-manager-externalContainer')]//button");
	private By pageSegmentation = By.xpath("//*[contains(@class,'page-segmentation')]//a");
	private By checkPublicAccessPage = By.id("edit-access-level-anonymous");
	private By checkAuthenticateAccessPage = By.id("edit-access-level-authenticated");
	private By menuLinkItems = By.cssSelector("#block-iom-main-navigation-block li");
	private By activePublicMenu = By.cssSelector("div[class*='public-menu'] input");
	private By activeLoggedInMenu = By.cssSelector("div[class*='logged-in-menu'] input");
	private By deleteMenuPopup = By.cssSelector(".editor-delete-dialog button[value='Delete']");
	private By menuTable = By.cssSelector("table tbody");
	private By leftMenuHamburger = By.xpath("//*[contains(@class,'leftMenuToggle')][2]/i");
	private By editIconMenuLink = By.xpath("//*[@class='rst__node'][1]//i[text()='edit']");
	private By active_status = By.cssSelector("input:checked[type='checkbox']");
	
	
	Utils utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	
	public void clickLeftHamburger()	{
		click(leftMenuHamburger, "left Menu Hamburger");
	}
	
	public void selectActivePublicMenu(String menuName){
		click(activePublicMenu, "Public menu");
		By menu = By.xpath(".//div[contains(@class,'public-menu')]//ul//li[descendant::span[text()='" + menuName + "']]");
		click(menu, menuName);
	}
	
	public void selectActiveLoggedInMenu(String menuName){
		click(activeLoggedInMenu, "Logged in menu");
		By menu = By.xpath(".//div[contains(@class,'logged-in-menu')]//ul//li[descendant::span[text()='" + menuName + "']]");
		click(menu, menuName);
	}
	
	public void clickSaveActiveMenu(){
		click(save, "Save Active Menu");
		//getElementWhenVisible(By.cssSelector("div[class*='alert-success'][role='alert']"));
		try{
			WebElement we = getElementWhenVisible(By.cssSelector("a[class='close']"), 1);
			we.click();
		} catch(Exception ex) {
			//Do Nothing
		}
	}
	
	public void clickPageSegmentation(){
		click(getElementWhenClickable(pageSegmentation, 2), "Page Segmentation");
	}
	
	public String getPageUrl(){
		return getAttribute(pageUrl, "value");
	}
	
	public void selectPageAccess(String accessType){
		clickPageSegmentation();
		WebElement checkLoggedIn = getDriver().findElement(checkAuthenticateAccessPage);
		WebElement checkPublic = getDriver().findElement(checkPublicAccessPage);
		if(accessType.equalsIgnoreCase("Public")){
			if(checkLoggedIn.isSelected())
				getDriver().findElement(By.xpath("//label[@for='edit-access-level-authenticated']/span")).click();
			if(!checkPublic.isSelected())
				getDriver().findElement(By.xpath("//label[@for='edit-access-level-anonymous']/span")).click();
		}
		else {
			if(checkPublic.isSelected())
				getDriver().findElement(By.xpath("//label[@for='edit-access-level-anonymous']/span")).click();
			if(!checkLoggedIn.isSelected())
				getDriver().findElement(By.xpath("//label[@for='edit-access-level-authenticated']/span")).click();
		}
	}
	
	public void clickAddNew() {
		sync(2000L);
		click(externalPageAddNewButton, "Add new", externalPageTitleMenu, 1);
	}
	
	public void typeMenuExternalPageTitle(String pageTitle) throws Exception{
		clickAddNew();
		click(externalPageTitleMenu, "External page title menu");
		sync(2000L);
		type(externalPageTitleMenu, "Menu Manager External Page Title", pageTitle);
	}
	
	public void typeMenuExternalPageLink(String linkAddress) throws Exception{
		type(externalPageLinkMenu, "Menu Manager External Page Link", linkAddress);
	}
	
	public void clickAddButtonExternalMenu(){
		click(externalPageAddButton, "Add Button External pages");
		sync(2000L);
	}
	
	public void clickDownloadToggleButton(boolean on) {
		boolean view = utils.getManageTicketConfiguration("download");
		if(view != on) {
			String xpath = getLocatorValue(downloadTogglelabel) + "//span";
			click(By.xpath(xpath), "Download toggle button");
		}
	}
	
	public void clickSellToggleButton(boolean on) {
		boolean sell = utils.getManageTicketConfiguration("sell");
		if(sell != on) {
			String xpath = getLocatorValue(sellTogglelabel) + "//span";
			click(By.xpath(xpath), "sell toggle button");
		}
	}
	
	public void clickSendToggleButton(boolean on) {
		boolean send = utils.getManageTicketConfiguration("send");
		if(send != on) {
			String xpath = getLocatorValue(sendTogglelabel) + "//span";
			click(By.xpath(xpath), "send toggle button");
		}
	}
	
	public void clickDonateToggleButton(boolean on) {
		boolean donate = utils.getManageTicketConfiguration("donate");
		if(donate != on) {
			String xpath = getLocatorValue(donateTogglelabel) + "//span";
			click(By.xpath(xpath), "donate toggle button");
		}
	}
	
	public void clickBarcodeToggleButton(boolean on) {
		boolean view = utils.getManageTicketConfiguration("mobile_enabled");
		if(view != on) {
			String xpath = getLocatorValue(BarcodeTogglelabel) + "//span";
			click(By.xpath(xpath), "Barcode toggle button");
		}
	}
	
	public void typeUsername(String username){
		WebElement we = getElementWhenVisible(this.username);
		we.clear();
		we.sendKeys(username, Keys.TAB);
	}
	
	public void typePassword(String password){
		WebElement we = getElementWhenVisible(this.password);
		we.clear();
		we.sendKeys(password, Keys.TAB);
	}
	
	public void clickLogInButton(){
		click(login, "LogIn");
	}
	
	public void clickAddPromotiles(){
		click(addPromotiles, "addPromotiles");
	}
	// cms some times redirected /user/2..userName, password
	public void login(String username, String password) throws InterruptedException{
		if(username.trim().equalsIgnoreCase("")){
			username = Dictionary.get("adminUsernName").trim();
			password = Dictionary.get("adminPassword").trim();
		}
		typeUsername(username);
		typePassword(password);
		clickLogInButton(); 
		
		//Assert.assertTrue(waitforLoggedInPage(), "Verify user get logged in");
	}
	
	public boolean waitforLoggedInPage(){
		getElementWhenVisible(promoTiles);
		return true;
	}
	
	public void clickPromotilesecondEdit(){
		click(promotileEdit, "Promotile Edit");
	}
	
	public boolean verifyPageAvailableViewPages(String PageName){
		boolean pageAvailable = false;
		List<WebElement> Pages= getWebElementsList(pageTable);
		for (int i=1;i<=Pages.size();i++){
			if(getDriver().findElement(By.xpath("//table/tbody/tr["+i+"]/td[1]")).getText().equalsIgnoreCase(PageName)){
				pageAvailable = true;
				break;
				
			}
			else {
				pageAvailable = false;
				
			}
			
		}
		return pageAvailable;
	}

	public void clickSecondPromotileEdit(){
		click(promotileEditSecond, "click 2nd Promotile Edit");
	}
	
	public void clickFourthPromotileEdit(){
		click(invoicePromotileEdit, "click Invoice Promotile Edit");
	}

	public void clickPageSettingsPageManager(String pageName){
		List<WebElement> Pages= getWebElementsList(pageTable);
		for (int i=1;i<=Pages.size();i++){
			if(getDriver().findElement(By.xpath("//table/tbody/tr["+i+"]/td[1]")).getText().equalsIgnoreCase(pageName)){
				click(getDriver().findElement(By.xpath("//table/tbody/tr["+i+"]/td[7]/a[1]")),"Page Settings");
				break;
			}
		}
	}
	
	public void clickPageCopyPageManager(String pageName){
		List<WebElement> Pages= getWebElementsList(pageTable);
		for (int i=1;i<=Pages.size();i++){
			if(getDriver().findElement(By.xpath("//table/tbody/tr["+i+"]/td[1]/a")).getText().equalsIgnoreCase(pageName)){
				click(getDriver().findElement(By.xpath("//table/tbody/tr["+i+"]/td[7]/a[2]")),"Page Settings");
				break;
			}
		}
	}
	
	public void clickPageInPageManager(String pageName){
		List<WebElement> Pages= getWebElementsList(pageTable);
		for (int i=1;i<=Pages.size();i++){
			if(getDriver().findElement(By.xpath("//table/tbody/tr["+i+"]/td[2]")).getText().equalsIgnoreCase(pageName)){
				click(getDriver().findElement(By.xpath("//table/tbody/tr["+i+"]/td[2]/..//i[@class='material-icons' and text()='settings']")),"Page Settings");
				break;
			}
		}
	}

	public void clickMenuToggle(){
		click(leftMenuToggle, "Toggle Button");
	}
	
	public void clickAddNewPage(){
		click(addNewPage, "Add New Page");
	}
	
	public void clickAddPageLeftMenu(){
		click(addPageMenuItem, "Add Page Left Menu");
	}
	
	public void clickViewPagemenuItem(){
		utils.navigateTo("/admin/page-manager/view-pages");
	}
	
	public void clickPageManager(){
		click(pageManager, "Page Manager");
	}
	
	public void clickPromoTiles(){
		click(promoTiles, "Promo Tiles");
	}
	
	public void viewPromoTiles(){
		utils.navigateTo("/admin/group-view");
	}
	
	public boolean viewPromotiles(){
		return checkIfElementPresent(viewPromoMenuItem);
	}
	
	public void edit(){
		click(edit, "Edit");
	}
	
	public void typeHeaderText(String headetText){
		WebElement we = getElementWhenVisible(this.headetText);
		we.clear();
		we.sendKeys(headetText, Keys.TAB);
	}
	
	public String getpromotilesTitle(){
		return getText(promotilesTitle);
	}
	
	public boolean FirstTileLoggedIn(){
		return checkIfElementPresent(First);
	}
	
	public String PromotileGroup(){
		return getText(Group);
	}
	
	public String getLoggedInTxt(){
		//System.out.println(getText(loggedInPromoTitleText));
		return getText(loggedInText);
	}
	
	public void typepromoTitle(String promoTitle){
		WebElement we = getElementWhenVisible(this.promoTitle);
		we.clear();
		we.sendKeys(promoTitle, Keys.TAB);
	}
	
	public String selectPromoTileGroup(){
		//System.out.println(getText(promoTitleGroup));
		if(checkIfElementPresent(promoTitleGroup1, 1))
			return getAttribute(promoTitleGroup1, "value");
		else {
			Select _select = new Select(getElementWhenVisible(promoTitleGroup2));
			return _select.getFirstSelectedOption().getText();
		}
	}
	
	public String selectPublicPromoTileGroup(){
		//System.out.println(getText(promoTitleGroup));
		if(checkIfElementPresent(promoTitleGroup1, 1))
			return getAttribute(promoTitleGroup1, "value");
		else {
			Select _select = new Select(getElementWhenVisible(promoTitleGroup2));
			return _select.getFirstSelectedOption().getText();
		}
	}
	
	public void typeurl(String url){
		WebElement we = getElementWhenVisible(this.url);
		we.clear();
		we.sendKeys(url, Keys.TAB);
	}
	
	public void typelinktext(String linktext){
		WebElement we = getElementWhenVisible(this.linktext);
		we.clear();
		we.sendKeys(linktext, Keys.TAB);
	}
	
	public void icon(){
		click(icon, "ICON");
	}
	
	public void preview(){
		click(preview, "Preview");
	}
	
	public void logout(){
		getDriver().navigate().to(Environment.get("APP_URL")+"/user/logout");
		//utils.navigateTo("admin/page-manager/view-pages");
		//utils.navigateTo("/user/logout");
	}
	
	public String getpromotilesheadertext(){
		//System.out.println(getText(promotilesheadertext));
		return getText(promotilesheadertext);
	}
	
	public String getpromotilesTitletext(){
		//System.out.println(getText(promotilesheadertext));
		return getText(promotilesheadertext);
	}

//	public String getloggedIntext(){
//		System.out.println(getText(loggedIn));
//		return getText(loggedIn);
//	}

	public void  save(){
		click(save,"Save");
	}
	
	public String gettypeText(){
		return getText(firstPromotileforLoggedInUsers);
	}
	
	public String gettypeText2(){
		return getText(loggedInSecondPromotiles);
	}
	
	public String getTextFourthrow(){
		return getText(loggedInInvoicePromotiles);
	}
	
	public String getloggedInPromoTitleText(){
		//System.out.println(getText(loggedInPromoTitleText));
		return getText(loggedInPromoTitleText);
	}
	
	public String[] get2N3LoggedInPromoTitleText(){
		return new String[]{getText(loggedInPromoTitleText), getText(loggedInPayInvoice)};
	}
	
	public String getInvoiceHeaderTextForLoggedInUsers(){
		return getText(loggedInInvoiceHeaderText);
	}
	
	public String getloggedInHeaderText(){
//		System.out.println(getText(loggedInHeaderText));
		return getText(loggedInHeaderText);
	}
	
	public String getloggedInPayInvoice(){
//		System.out.println(getText(loggedInPayInvoice));
		return getText(loggedInPayInvoice);
	}
	
	public String loggedInContactUsText(){
		return getText(loggedInContactUSText);
	}
	
	public String getloggedInContactUs(){
//		System.out.println(getText(loggedInContactUs));
		return getText(loggedInContactUs);
	}
	
	public String getPublicSecondPromoTitle(){
//		System.out.println(getText(publicSecondPromoTitle));
		return getText(publicSecondPromoTitle);
	}
	
	public String[] get2N3PublicPromoTitleText(){
		return new String[]{getText(publicSecondPromoTitle), getText(publicThirdPromoTitle)};
	}
	
	public void clickEditMenuIcon(String menuName){
		click(getElementWhenClickable(By.xpath("//table//*[contains(text(),'"+menuName+"')]/../td[5]/a[1]"), 2), "Edit Menu Icon");
	}
	
	public String getThirdPromotileText(){
//		System.out.println(getText(publicThirdPromoTitle));
		return getText(publicThirdPromoTitle);
	}
	
	public String getpublicContactus(){
		//System.out.println(getText(publicContactus));
		return getText(publicContactus);
	}
	
	public void colorPicker(String backgroundColor){
		WebElement we = getElementWhenVisible(this.colorPicker);
		we.clear();
		we.sendKeys(backgroundColor, Keys.TAB);
	}
	
	public void typebackgroundColorR(String backgroundColorR){
		WebElement we = getElementWhenVisible(this.backgroundColorR);
		we.clear();
		we.sendKeys(backgroundColorR, Keys.TAB);
	}
	
	public void typebackgroundColorG(String backgroundColorG){
		WebElement we = getElementWhenVisible(this.backgroundColorG);
		we.clear();
		we.sendKeys(backgroundColorG, Keys.TAB);
	}
	
	public void typebackgroundColorB(String backgroundColorB){
		WebElement we = getElementWhenVisible(this.backgroundColorB);
		we.clear();
		we.sendKeys(backgroundColorB, Keys.TAB);
	}
	
	public void loggedInEdit(){
		click(loggedInEdit, "loggedInEdit");
	}
	
	public void clickContactUsPromotileEdit(){
		click(contactUsPromotileEdit,"contactUs Promotile Edit");
	}
	
	public String getloggedIncontactUsHeaderText(){
		return getText(loggedIncontactUsText);
	}
	
	public boolean checkRedirection(){
		return checkIfElementPresent(SignIn);
	}
	
	public String getTitleLink(){
		WebElement we = getElementWhenVisible(this.Link);
		return we.getAttribute("value");
	}
	
	public void clickFirstpromotile(){
		String xpath = getXpath(promotilesheadertext, "Promo tile", "", -1) + "/../..";
		String target = getAttribute(By.xpath(xpath), "target");
		click(promotilesheadertext,"Buy Tickets");
		if(target != null && target.trim().equalsIgnoreCase("_blank")) {
			switchToWindow(1);
		}
	}
	
	public void secondPromotile(){
		String xpath = getXpath(loggedInPromoTitleText, "Promo tile", "", -1) + "/../..";
		String target = getAttribute(By.xpath(xpath), "target");
		click(loggedInPromoTitleText,"Buy Invoices");
		if(target != null && target.trim().equalsIgnoreCase("_blank")) {
			switchToWindow(1);
		}
	}

	public void thirdPromotile(){
		String xpath = getXpath(loggedInPayInvoice, "Promo tile", "", -1) + "/../..";
		String target = getAttribute(By.xpath(xpath), "target");
		click(loggedInPayInvoice,"Manage Tickets");
		if(target != null && target.trim().equalsIgnoreCase("_blank")) {
			switchToWindow(1);
		}
	}
	
	public void fourthPromotile(){
		String xpath = getXpath(loggedInContactUs, "Promo tile", "", -1) + "/../..";
		String target = getAttribute(By.xpath(xpath), "target");
		click(loggedInContactUs,"Contact Us");
		if(target != null && target.trim().equalsIgnoreCase("_blank")) {
			switchToWindow(1);
		}
	}
	
	public void typePromoTileName(String promoTileName){
		WebElement we = getElementWhenVisible(this.promoTileName);
		we.clear();
		we.sendKeys(promoTileName, Keys.TAB);
	}
	
	public void typePromoTileGroup(String promoTileGroup){
		WebElement we = getElementWhenVisible(this.promoTileGroup);
		we.clear();
		we.sendKeys(promoTileGroup, Keys.TAB);
	}
	
	public void typePromoTileTitle(String promoTileTitle){
		WebElement we = getElementWhenVisible(this.promoTileTitle);
		we.clear();
		we.sendKeys(promoTileTitle, Keys.TAB);
	}
	
	public void typeBackgroudColour(String backgroudColour){
		WebElement we = getElementWhenVisible(this.backgroudColour);
		we.clear();
		we.sendKeys(backgroudColour, Keys.TAB);
	}
	
	public void typeTitleUrl(String titleUrl){
		WebElement we = getElementWhenVisible(this.titleUrl);
		we.clear();
		we.sendKeys(titleUrl, Keys.TAB);
	}
	
	public void openlink(){
		click(openlink, "openlink");
	}
	
	public void selectPromoTileLogo(){
		click(promoTileLogo, "promoTileLogo");
	}
	
	public void selectNext(){
		click(next, "next");
	}
	
	public void selectActiveStatus(){
		click(activeStatus, "activeStatus");
	}
	
	public String getNewPromotilesName(){
		return getText(promotitlename);
	}
	
	public void typePageName(String pageName){
		WebElement we = getElementWhenVisible(this.pageName);
		we.clear();
		we.sendKeys(pageName, Keys.TAB);
	}
	
	public void typePageTitle(String pageTitle){
		WebElement we = getElementWhenVisible(this.pageTitle);
		we.clear();
		we.sendKeys(pageTitle, Keys.TAB);
	}
	
	public void clickPageUrl(){
		click(pageUrlSettings,"pageUrlSettings");
	}
	
	public void typePageUrl(String pageUrl){
		WebElement we = getElementWhenVisible(this.pageUrl);
		we.clear();
		we.sendKeys(pageUrl, Keys.TAB);
	}
	
	public void uploadBackgroundImage(){
		click(uploadBackgroundImage, "uploadBackgroundImage");
	}
	
	public void clickPageSettings(){
		if(checkIfElementPresent(pageSetting, 5))
		click(pageSetting,"pageSetting");
		else throw new SkipException("NAM ME is enabled on this site");
	}
	
	public String getType(){
		System.out.println(getText(type));
		return getText(type);
	}
	
	public void clickCKEditorText(){
		click(ckEditorText,"click CK Editor");
	}
	
	public void typeCKEditorText(String ckEditorText){
		WebElement we = getElementWhenClickable(this.ckEditorText);
		sync(2000L);
		we.click();
		sync(2000L);
		we =  getElementWhenClickable(this.ckEditorText);
		sync(2000L);
		we.clear();
		we =  getElementWhenClickable(this.ckEditorText);
		we.sendKeys(ckEditorText, Keys.TAB);
		sync(2000L);
	}
	
	public void typeCKEditorText2(String ckEditorText2){
		WebElement we = getElementWhenVisible(this.ckEditorText2);
		sync(2000L);
		javascriptClick(we, "Click");
		sync(2000L);
		we =getElementWhenClickable(this.ckEditorText2, 10);
		//we = getElementWhenVisible(this.ckEditorText);
		sync(2000L);
		we.clear();
		we.sendKeys(ckEditorText2, Keys.TAB);
		sync(2000L);
	}
	
	public String getCKEditorText(){
		System.out.println(getText(ckEditorText));
		return getText(ckEditorText);
	
	}
	
	public String getCKEditorText2(){
		System.out.println(getText(ckEditorText2));
		return getText(ckEditorText2);
	}
	
	public String getCKEditorTitle(){
		System.out.println(getText(CKEditorTitle));
		return getText(CKEditorTitle);
	}
	
	public String getCKEditorBody(){
		System.out.println(getText(CKEditorBody));
		return getText(CKEditorBody);
	}
	
	public void clickMenuManagerLeftMenu(){
		click(menuManager, "Menu Manager Left Menu");
	}
	
	public void clickViewMenu(){
		click(viewMenu, "View Menu");
	}
	
	public void clickaddMenu(){
		click(addMenu, "Add Menu");
		Assert.assertEquals(getText(createMenuTexts), "Create Menu", "Verify create menu dialog is displayed");
	}
	
	public void typeMenuName(String text) throws Exception {
		getElementWhenVisible(By.cssSelector("div[class*='menu-manager-pageHolder'] div[class*='menu-manager-pageContainer'][class*='visibleContainer'] div[class*='menu-manager-draggable']"));
		type(menuName, "Menu name", text);
	}
	
	public void clickPublicAccessLevel() {
		click(publicAccesslevel, "Public access level");
	}
	
	public void clickLoggedInAccessLevel() {
		click(loggedInAccesslevel, "Logged in access level");
	}
	
	public void clickInternalPagesHeader() {
		click(internalPages, "Internal pages");
	}
	
	public void clickTransactionalPagesHeader() {
		click(transactionalPages, "Transactional pages");
	}
	
	public void clickExternalPagesHeader() {
		click(externalPages, "External pages");
	}
	
	public void typePagesSearch(String text) throws Exception {
		type(pagesSearch, "Page search", text);
	}
	
	public void dragAndDropInternalPages(String searchtext) throws Exception {
		if(!searchtext.trim().equalsIgnoreCase(""))
			typePagesSearch(searchtext);
		String drag = "div[class*='menu-manager-pageHolder'] div[class*='menu-manager-pageContainer'][class*='visibleContainer'] div[class*='menu-manager-draggable']:nth-child(2), div[class*='menu-manager-pageHolder'] div[class*='menu-manager-pageContainer'][class*='visibleContainer'] div[class*='menu-manager-externalLinkEdit']:nth-child(2) div[class*='menu-manager-draggable']:nth-child(1)";
		if(checkIfElementPresent(droppedNodes, 1)) {
			String drop = ".rst__node:nth-last-child(1)";
			dragNdrop(drag, drop);
		} else{
			String drop = ".rst__placeholder";
			dragNdrop(drag, drop);
		}
	}
	
	public void getAllMenuNodesNames()	{
		List<WebElement> menuNodes = getWebElementsList(droppedNodes);
		String[] menuTexts = new String[menuNodes.size()];
		for(int i=0; i < menuNodes.size();i++){
			menuTexts[i]=menuNodes.get(i).findElement(By.tagName("label")).getText();
			Dictionary.put("CMSMenuNames"+i, menuTexts[i]);
		}
	}
	
	public void clickHoldnDrop(By drag, By drop1, By drop2){
		WebElement dragElement = getElementWhenClickable(drag);
		WebElement drop1Element = getElementWhenClickable(drop1);
		WebElement drop2Element = getElementWhenClickable(drop2);
		Actions action = new Actions(driverFactory.getDriver().get());
		action.clickAndHold(dragElement);
		action.moveToElement(drop1Element).build().perform();
		action.moveToElement(drop2Element).build().perform();
		action.release().perform();	
	}
	
	public void saveOrder() {
		click(saveOrder, "Save order");
	}
	
	public void reorder(String promotileName) throws IOException {
		WebElement promoTile = getElementWhenVisible(By.xpath(".//tr[descendant::td[@headers='view-title-table-column' and contains(.,'" + promotileName + "')]]//a/span"));
		WebElement firstPromotile = getElementWhenVisible(By.xpath("(.//tr[descendant::td[@headers='view-title-table-column']])[1]//a/span"));
		dragNdrop(promoTile, firstPromotile);
		saveOrder();
	}
	
	public void dragNdrop(WebElement drag, WebElement drop) throws IOException {
		Actions act=new Actions(driverFactory.getDriver().get());
		act.dragAndDrop(drag, drop).perform();
		Reporter.log("Drag and Drop", "Done", "Done", "Pass");
	}
	
	public void dragNdrop(String drag, String drop) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("js/drag_and_drop_helper.propagate-dropTarget.js"));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");
		while((line = reader.readLine()) != null) {
		   stringBuilder.append(line);
		   stringBuilder.append(ls);
		}
		reader.close();
		JavascriptExecutor js = (JavascriptExecutor) driverFactory.getDriver().get();
		js.executeScript(stringBuilder.toString() + "$(\"" + drag + "\").simulateDragDrop({ dropTarget:\"" + drop + "\" });");
		Reporter.log("Drag and Drop", "Done", "Done", "Pass");
	}
	
	public void dragNdrop(By dragLocator, By dropLocator) {
		Actions act=new Actions(driverFactory.getDriver().get());
		WebElement drag = getElementWhenVisible(dragLocator);
		WebElement drop = getElementWhenVisible(dropLocator);
		act.dragAndDrop(drag, drop).build().perform();
		Reporter.log("Drag and Drop", "Done", "Done", "Pass");
	}
	
	public void clicksaveMenu() {
		click(saveMenu, "Save Menu");
	}
	
	public void clickPageSettingsIcon(String pageType) {
		By pageSettingsIcon = By.xpath(".//tr[descendant::td[@headers='view-node-access-level-table-column' and text()='"+pageType.trim()+"        ']][1]//i[text()='settings']");
		click(getElementWhenClickable(pageSettingsIcon), pageType + " page settings icon");
	}
	
	public boolean checkPageTypeAvailable(String pageType)	{
		return checkIfElementPresent(By.xpath(".//tr[descendant::td[@headers='view-node-access-level-table-column' and text()='"+pageType+"        ']][1]//i[text()='settings']"), 5);
	}
	
	public void selectPageMenu(String text) {
		Assert.assertTrue(getText(editPageMenu).trim().contains(text.trim()), "Verify menu list contains newly created menu - " + text);
		selectByVisibleText(editPageMenu, "Edit page menu", text);
	}
	
	public String getMenuDropdownItemsText() {
		return getText(editPageMenu).trim();
	}
	
	public void verifyAllMenuNodesTextswithCMS(){
		List<WebElement> menuNodes = getWebElementsList(menuLinkItems);
		String[] menuTexts = new String[menuNodes.size()];
		for(int i=0; i < menuNodes.size();i++){
			menuTexts[i]=menuNodes.get(i).getText();
			Assert.assertEquals(menuTexts[i], Dictionary.get("CMSMenuNames"+i),"All Menu Nodes are available as per CMS");
		}
	}
	
	public void clickDeleteMenuIcon(String menuName) {
		By menuDeleteIcon = By.xpath(".//tr[descendant::td[@headers='view-title-table-column' and text()='"+menuName+"        ']][1]//i[text()='delete']");
		click(getElementWhenClickable(menuDeleteIcon), menuName + " menu delete icon");
	}
	
	public void clickMenuSettingsIcon(String menuName) {
		By menuSettingsIcon = By.xpath(".//tr[descendant::td[@headers='view-title-table-column' and text()='"+menuName+"        ']][1]//i[text()='settings']");
		click(getElementWhenClickable(menuSettingsIcon), menuName + " menu settings icon");
		getElementWhenVisible(By.cssSelector("div[class*='menu-manager-pageHolder'] div[class*='menu-manager-pageContainer'][class*='visibleContainer'] div[class*='menu-manager-draggable']"));
	}
	
	public void clickDeletePopUp() {
		getElementWhenVisible(deleteMenuPopup);
	}
	
	public String getMenuTableAllText() {
		return getText(menuTable);
	}
	
	public void clickEditMenuLinkName() {
		click(editIconMenuLink, "Menu Link Edit Icon");
	}
	
	public void editMenuLinkName(String linkName) throws Exception {
		clickEditMenuLinkName();
		click(externalPageTitleMenu, "External page title menu");
		sync(2000L);
		type(externalPageTitleMenu, "Menu Manager External Page Title", linkName);
		clickAddButtonExternalMenu();
	}
	
	public void viewLanguageConfigurationPage() throws Exception{
		utils.navigateTo("/admin/config/site-language");
	}
	
	public String verifyAddLanguagePageHeaderText(){
		return getText(addLanguagePageHeaderText);
	}
	
	public void selectLanguageFromDropdown(String language) {
		click(clickonLanguageDropdown,"Click on Language Dropdown");
		List<WebElement> list = ElementofLanguagetab();
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getText().contentEquals(language)) {
				click(list.get(i),language);}
		}
		click(clickOnAddLanguageButton, "Click on Add language Button");
	}
	
	public List<WebElement> ListinLanguagetable(){
		List<WebElement> itemlist = getWebElementsList(langCount);
		 List<WebElement> listitem = new ArrayList<WebElement>();
		Iterator<WebElement> iter = itemlist.iterator();
		for(int i=0;i<itemlist.size();i++) {
			WebElement item = iter.next();
			listitem.add(item.findElement(By.tagName("td")));
		}
		return listitem;
	}
	
	public List<WebElement> ElementofLanguagetab(){
		List<WebElement> itemlist = getWebElementsList(selectLanguage);
		 List<WebElement> listitem = new ArrayList<WebElement>();
		Iterator<WebElement> iter = itemlist.iterator();
		for(int i=0;i<itemlist.size();i++) {
			WebElement item = iter.next();
			listitem.add(item.findElement(By.tagName("span")));
		}
		return listitem;
	}
	
	public String getLanguagTableText(){	
		System.out.println(getText(languageTable));
		return getText(languageTable);
	}
	
	
	public void deleteLanguage(String language) {
		By delete = By.xpath(".//*[@id='edit-languages']//tbody//tr/td[contains(text(),'" + language + "')]/..//td[2]//i");
		click(delete, "Delete language");
		click(deleteButton, "Delete");
	}
	
	
	public void clicklanguageSwitcher(String language) throws Exception{
		click(languageSwitcher, "Click on Language Switcher");
		By languageselector = By.xpath(".//*[@class='language-list']/ul/li[2]/a");
		//By languageselector = By.xpath(("//div[@class=‘language-selector’]//div[2]//ul//li//a[text=‘" + language + "’]"));
		click(languageselector, language);
	
	}
	
	public void viewAddEmailTemplate() throws Exception{
		utils.navigateTo("/admin/email-templates/add");
	}
	
	public void selectEmailTemplateType(String emailtemplatetype) throws Exception{
		click(enterEmailTemplateType,"Template Type");
		List<WebElement> list = ElementofEmailType();
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getText().contentEquals(emailtemplatetype)) {
				click(list.get(i),emailtemplatetype);
				break;
				}
		}
	}
	
	public void selectEmailTemplate(String emailtemplatetype) throws Exception{
		click(selectEmailTemplate," Email Template Type");
		List<WebElement> list = ElementofEmailTemplate();
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getText().contentEquals(emailtemplatetype)) {
				click(list.get(i),emailtemplatetype);
				break;
				}
		}
	}
	
	public void selectInvoiceTemplate(String invoicetemplatetype) throws Exception{
		click(selectInvoiceEmailTemplate," Invoice Template Type");
		List<WebElement> list = ElementofInvoiceTemplate();
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getText().contentEquals(invoicetemplatetype)) {
				click(list.get(i),invoicetemplatetype);
				break;
				}
		}
	}
	
	public List<WebElement> ElementofInvoiceTemplate(){
		List<WebElement> itemlist = getWebElementsList(InvoiceEmailTemplateDropdown);
		 List<WebElement> listitem = new ArrayList<WebElement>();
		Iterator<WebElement> iter = itemlist.iterator();
		for(int i=0;i<itemlist.size();i++) {
			WebElement item = iter.next();
			listitem.add(item.findElement(By.tagName("span")));
		}
		return listitem;
	}
	
	public List<WebElement> ElementofEmailTemplate(){
		List<WebElement> itemlist = getWebElementsList(EmailTemplateDropdown);
		 List<WebElement> listitem = new ArrayList<WebElement>();
		Iterator<WebElement> iter = itemlist.iterator();
		for(int i=0;i<itemlist.size();i++) {
			WebElement item = iter.next();
			listitem.add(item.findElement(By.tagName("span")));
		}
		return listitem;
	}
	
	
	public List<WebElement> ElementofEmailType(){
		List<WebElement> itemlist = getWebElementsList(selectTemplateType);
		 List<WebElement> listitem = new ArrayList<WebElement>();
		Iterator<WebElement> iter = itemlist.iterator();
		for(int i=0;i<itemlist.size();i++) {
			WebElement item = iter.next();
			listitem.add(item.findElement(By.tagName("span")));
		}
		return listitem;
	}
	
	
	public void enterEmailTemplateFields(String emailtemplatetype, String emailtemplatetitle, String emailtemplatesubject) throws Exception{
		selectEmailTemplateType(emailtemplatetype);
		type(enterEmailTemplateTitle,"Enter Email Template Title", emailtemplatetitle);
		type(enterEmailTemplateSubject, "Enter Email Template Subject" , emailtemplatesubject);
		click(saveEmailTemplate, "Save Email Template");
	}
	
	public void editInvoiceTemplate() throws Exception{
		utils.navigateTo("/admin/invoice/add");
	}
	
	public void selectEmailTemplateTypeOnInvoiceTemplate( String paymentEmailType, String invoiceEmailType) throws Exception{
		selectEmailTemplate(paymentEmailType);
		selectInvoiceTemplate(invoiceEmailType);
	}
	
	public void enterinvoiceTemplateFields(String invoicetemplatetitle) throws Exception{
		type(enterInvoiceTemplateTitle,"Enter Invoice Template Title", invoicetemplatetitle);
	}
	
	public void selectemailtemplateandsave(String paymentEmailType , String invoiceEmailType) throws Exception{
		click(clickInvoiceEmailsTabs, "Click Invoice Emails tab on Invoice Template");
		selectEmailTemplateTypeOnInvoiceTemplate(paymentEmailType,invoiceEmailType);
		click(saveInvoiceTemplate, "Save Invoice Template");
	}
	
	public void clickActiveButton(){
		click(active_status,"click active button");
	}
	
	public String getActiveButton() {
		WebElement we = getElementWhenVisible(this.active_status);
		return we.getAttribute("checked");
	}
	
	public void dragAndDrop(WebElement source, WebElement destination) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("js/DragAndDrop.js"));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");
		while((line = reader.readLine()) != null) {
		   stringBuilder.append(line);
		   stringBuilder.append(ls);
		}
		reader.close();
		JavascriptExecutor jse = (JavascriptExecutor)getDriver();
		String dragAndDropScript = stringBuilder.toString()
                +" var startEle = arguments[0]; "
                + " var endEle = arguments[1];"
                + " var wait = 150; "
                + " window.dragMock.dragStart(startEle).delay(wait).dragEnter(endEle).delay(wait).dragOver(endEle).delay(wait).drop(endEle);";
		System.out.println(dragAndDropScript);
        jse.executeScript(dragAndDropScript, source, destination);			
	}
	
}
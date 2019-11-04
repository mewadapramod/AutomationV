package org.iomedia.galen.pages;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.iomedia.galen.common.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;



public class ContentPageEdit extends BaseUtil{

	public ContentPageEdit(WebDriverFactory driverFactory, HashMapNew Dictionary,HashMapNew Environment, Reporting Reporter,org.iomedia.framework.Assert Assert,org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<HashMapNew> sTestDetails) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	}
	
	private By pageTitle = By.xpath("//div[starts-with(@class, 'field field--name-field-main-title')]");
	private By pageContent = By.xpath("//div[starts-with(@class, 'field field--name-field-content-subtitle')]");
	private By pageSetting= By.xpath("//button[contains(@class,'page-setting-button')]");
	private By pageTitleSettings = By.xpath("//*[@id='edit-title']");
	private By pageNameSettings = By.xpath("//*[@id='edit-page-name']");
	private By saveSettings = By.xpath("//*[@id='edit-submit']");
	private By body = By.xpath("//body");
	private By editorSave= By.xpath("//*[@id='quickedit-entity-toolbar']//button[1]");
	private Utils utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	
	public void clickSaveSettings(){
		click(saveSettings,"Save Page Settings");
	}
	
	public void typePageTitle(String NewTitle) throws Exception{
		getElementWhenVisible(pageTitleSettings, 2).clear();
		type(pageTitleSettings, "Page Title in Page Setings", NewTitle);
	}

	public void typePageName(String Name) throws Exception {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		Dictionary.put("PageName", Name + " - " + timeStamp);
		type(pageNameSettings, "Page Name in Page Setings", Name + " - " + timeStamp);
	}
	
	public WebElement waitTillEditable() {
		int counter = 100;
		WebElement wpageTitle = getElementWhenClickable(pageTitle);
		WebElement wpageContent = getElementWhenClickable(pageContent);
		Actions action = new Actions(getDriver());
	
		while(counter >= 0){
			try{
				if(wpageTitle != null){
					action.moveToElement(wpageTitle).moveToElement(wpageContent).perform();
					utils.getElementWhenEditable(pageTitle, "class", "quickedit-editable", 1);
					break;
				}
			} catch(Exception ex){
				if(counter == 0){
					throw ex;
				}
				sync(500L);
				counter--;
			}
		}
		return wpageTitle;
	}
	
	public void editTitle(String Title) throws Exception{
		WebElement we = waitTillEditable();
		we.click();
		getElementWhenRefreshed(pageTitle, "contenteditable", "true");
		String xpath = getXpath(pageTitle, "Page title", "div", -1) + "//h3";
		type(By.xpath(xpath), "Page Title", Title);
	}
	
	public void editContent(String Content) throws Exception{
		click(pageContent, "Page Title");
		click(pageContent, "Page Title");
		getDriver().findElement(pageContent).clear();
		type(pageContent, "Page Title", Content);
	}
	
	public void clickPageSettings(){
		click(pageSetting, "Page Setting");
	}
	
	public boolean verifyPageTypeContent(){
		getElementWhenVisible(pageSetting);
		if(getAttribute(body, "class").contains("content-page")){
			return true;
		}
		else 
			return false;
	}
	
	public void clickSaveEditor(){
		click(editorSave, "Save CK Editor1");
	}
	
	public String getPageTitle(){
		return getText(pageTitle, 3);
	}
	
	
	public String getPageContent(){
		return getText(pageContent, 3);
	}
}
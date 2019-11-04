package org.iomedia.galen.pages;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.openqa.selenium.By;

public class HomePageEdit extends BaseUtil {

	public HomePageEdit(WebDriverFactory driverFactory, HashMapNew Dictionary,HashMapNew Environment, Reporting Reporter,org.iomedia.framework.Assert Assert,org.iomedia.framework.SoftAssert SoftAssert) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
	}

	private By pageSetting= By.xpath("//button[contains(@class,'page-setting-button')]");
	private By pageTitleSettings = By.xpath("//*[@id='edit-title']");
	private By pageNameSettings = By.xpath("//*[@id='edit-page-name']");
	private By saveSettings = By.xpath("//*[@id='edit-submit']");
	private By body = By.xpath("//body");
	
	public void clickSaveSettings(){
		click(saveSettings,"Save Page Settings");
	}
	
	public void typePageTitle(String NewTitle) throws Exception{
		getElementWhenVisible(pageTitleSettings, 2).clear();
		type(pageTitleSettings, "Page Title in Page Setings", NewTitle);
	}
	
	public void typePageName(String Name) throws Exception{
		type(pageNameSettings, "Page Name in Page Setings", Name);
	}
	
	public void clickPageSettings(){
		click(pageSetting, "Page Setting");
	}
	
	public boolean verifyPageTypeHome(){
		getElementWhenVisible(pageSetting);
		if(getAttribute(body, "class").contains("home-page")){
			return true;
		}
		else 
			return false;
	}
}
package org.iomedia.galen.pages;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.openqa.selenium.By;
import org.testng.SkipException;

public class AddNewPage extends BaseUtil{

	public AddNewPage(WebDriverFactory driverFactory, HashMapNew Dictionary,HashMapNew Environment, Reporting Reporter,org.iomedia.framework.Assert Assert,org.iomedia.framework.SoftAssert SoftAssert) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
	}
	
	private By homePage = By.xpath("//label[contains(@for,'home-page')]");
	private By contentPage = By.xpath("//label[contains(@for,'content-page')]");
	private By homePageLayout = By.xpath("//label[contains(@for,'view-modes-home')]/div[1]/img");
	private By contentLayout = By.xpath("//label[contains(@for,'view-modes-content')]/div[1]/img");
	private By next= By.xpath(".//*[@id='edit-submit']");
	
	public void clickSelectHomePage(){
		if(checkIfElementPresent(homePage, 5))
		click(homePage, "Home Page");
		else throw new SkipException("NAM ME is enabled on this site");
	}
	
	public void clickSelectContentPage(){
		if(checkIfElementPresent(contentPage, 5))
		click(contentPage, "Content Page");
		else throw new SkipException("NAM ME is enabled on this site");
	}
	
	public void selectHomePageLayout(){
		click(homePageLayout, "HomePage Layout");
	}
	
	public void selectContentPageLayout(){
		click(contentLayout, "Content Page Layout");
	}
	
	
	public void clickNext(){
		click(next, "Next Button") ;
	}
}

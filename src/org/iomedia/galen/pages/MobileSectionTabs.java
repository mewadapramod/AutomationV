package org.iomedia.galen.pages;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.openqa.selenium.By;

public class MobileSectionTabs extends BaseUtil {

	public MobileSectionTabs(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
	}
	
	private By quickLinks = By.xpath(".//div[@section='promotitles']/a");
	private By tickets = By.xpath(".//div[@section='tickets']/a");
	private By invoices = By.xpath(".//div[@section='invoices']/a");
	
	public void clickQuickLinks(){
		click(quickLinks, "QUICK LINKS");
	}
	
	public void clickTickets(){
		click(tickets, "TICKETS");
	}
	
	public void clickInvoices(){
		click(invoices, "INVOICES");
	}
}

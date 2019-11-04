package org.iomedia.galen.pages;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.iomedia.galen.common.ManageticketsAAPI;
import org.iomedia.galen.common.ManageticketsAPI;
import org.iomedia.galen.common.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import io.appium.java_client.ios.IOSDriver;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Resale extends BaseUtil {

    org.iomedia.framework.Assert Assert;
    ManageticketsAPI api;
    ManageticketsAAPI aapi;
    BaseUtil base;
    org.iomedia.framework.SoftAssert SoftAssert;
    org.iomedia.framework.Reporting Reporter;
    Utils util;
    DashboardSection dashboardSection;
    private String driverType;

    public Resale(WebDriverFactory driverFactory, Driver.HashMapNew Dictionary, Driver.HashMapNew Environment, Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<Driver.HashMapNew> sTestDetails, BaseUtil base, ManageticketsAAPI aapi, Utils util, DashboardSection dashboardSection) {
        super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
        this.base = base;
        this.aapi = aapi;
        this.util = util;
        this.Assert = Assert;
        this.dashboardSection = dashboardSection;
        driverType = driverFactory.getDriverType().get();
    }

    private By selectseats = By.xpath("//span[contains(.,'Select seats')]");
    private By eventName = By.cssSelector("div[class*='eventDetailsInner'] h3");
    private By seatdetails = By.cssSelector("span[class*='style-seats']");
    private By ticketpriceheader = By.cssSelector("div[class*='ticketPriceHeader']");
    private By decrementicon = By.cssSelector("i[class*='decrement0']");
    private By incrementicon = By.cssSelector("i[class*='increament0']");
    private By priceinput1 = By.cssSelector("input[name='listingPrice0']");
    private By priceinput2 = By.cssSelector("input[name='listingPrice1']");
    private By priceinput3 = By.cssSelector("input[name='listingPrice2']");
    private By payoutdetailheader = By.cssSelector("div[class*='payoutTabelBlock'] h3");
    private By ticketlistedforheader = By.xpath("//div[contains(@class,'payoutTabelBlock')]/div/div[contains(@class,'payoutTabelRow')][1]/div[1]");
    private By ticketlistedforamount = By.xpath("//div[contains(@class,'payoutTabelBlock')]/div/div[contains(@class,'payoutTabelRow')][1]/div[2]");
    private By servicefeeheader = By.xpath("//div[contains(@class,'payoutTabelBlock')]/div/div[contains(@class,'payoutTabelRow')][1]/div[3]");
    private By payoutperticketheader = By.xpath("//div[contains(@class,'payoutTabelBlock')]/div/div[contains(@class,'payoutTabelRow')][2]/div[1]");
    private By payoutperticketamount = By.xpath("//div[contains(@class,'payoutTabelBlock')]/div/div[contains(@class,'payoutTabelRow')][2]/div[2]");
    private By youwillgetpaidheader = By.xpath("//div[contains(@class,'payoutTabelBlock')]/div/div[contains(@class,'payoutTabelRow')][3]/div[1]");
    //private By youwillgetpaidamount = By.xpath("//div[contains(@class,'payoutTabelBlock')]/div/div[contains(@class,'payoutTabelRow')][3]/div[2]");
    private By seatexpand1 = By.cssSelector("div[class*='style-TicketListContainer'] div[class*='style-ticketList']:nth-child(1) div[class*='style-ticketListHeader'] svg");
    private By seatexpand2 = By.cssSelector("div[class*='style-TicketListContainer'] div[class*='style-ticketList']:nth-child(2) div[class*='style-ticketListHeader'] svg");
    private By seatexpand3 = By.cssSelector("div[class*='style-TicketListContainer'] div[class*='style-ticketList']:nth-child(3) div[class*='style-ticketListHeader'] svg");
    private By minvalueamt = By.xpath("span[class*='minValue']");
    private By maxvalueamt = By.xpath("span[class*='maxValue']");
    private By cancelbutton = By.xpath("//button[contains(.,'Cancel')]");
    private By continuebutton = By.xpath("//button[contains(.,'Continue')]");
    private By totalpayoutamountreviewpage = By.cssSelector("div[class*='totalPayoutPrice'] span[class*='totalPayoutAmount']");
    private By totalpayoutamountddreviewpage = By.cssSelector("div[class*='totalPayoutPrice'] span[class*='totalPayoutAmount']");
    private By youwillgetpaidamount = By.xpath("//div[contains(@class,'payoutTabelRow')][3]/div[2]");
    private By payoutmethodheaderreviewpage = By.xpath("//div[contains(@class,'reviewBlock')][1]/h3");
    private By updateaccountinfobuttonreviewpage = By.xpath("//button[contains(.,'Update Account Info')]");
    private By payoutmethoddropdownreviewpage = By.cssSelector("[name='payoutMethod']");
    private By checkingaccountlireviewpage = By.cssSelector("div[class*='payoutMethod'] ul :nth-child(1)");
    private By sellercreditlireviewpage = By.cssSelector("div[class*='payoutMethod'] ul :nth-child(2)");
    private By payeeinfoheaderreviewpage = By.xpath("//div[contains(@class,'reviewBlock')][2]/h3");
    private By payeeinfoaddressreviewpage = By.xpath("//div[contains(@class,'reviewBlock')][2]/div[contains(@class,'reviewBlockLeft')]/p");
    private By updatepayeeinfolinkreviewpage = By.xpath("//div[contains(@class,'reviewBlock')][2]/div[contains(@class,'reviewBlockLeft')]/span[contains(.,'Update Payee Info')]");
    private By seat1reviewpage = By.cssSelector("div[class*='style-TicketListContainer'] div[class*='style-ticketList']:nth-child(1) div[class*='style-ticketListHeader'] span");
    private By seat2reviewpage = By.cssSelector("div[class*='style-TicketListContainer'] div[class*='style-ticketList']:nth-child(2) div[class*='style-ticketListHeader'] span");
    private By seat3reviewpage = By.cssSelector("div[class*='style-TicketListContainer'] div[class*='style-ticketList']:nth-child(3) div[class*='style-ticketListHeader'] span");
    private By welldonetextConfpage = By.xpath("//div[contains(@class,'resale_success')]/div/span[contains(.,'Well Done!')]");
    private By ticketlistingtextconfpage = By.xpath("//div[contains(@class,'resale_success')]/div[contains(@class,'listingMessage')]/div");
    private By ticketpaidtextconfpage = By.xpath("//div[contains(@class,'resale_success')]/div[contains(@class,'paidMessage')]/div");
    private By showalllistingbtnconfpage = By.cssSelector("div[class*='showAllListing'] svg");
    private By showalllistingtxtconfpage = By.cssSelector("div[class*='showAllListing']");
    private By totalpayoutamtconfpage = By.cssSelector("span[class*='totalPayoutAmount']");
    private By payoutmethodtextconfpage = By.xpath("//div[contains(@class,'infoMessages')]/span[contains(.,'Payout Method')]");
    private By payeeinfotextconfpage = By.xpath("//div[contains(@class,'payeeMessage')]");
    private By donebtnconfpage = By.cssSelector("button[class*='resaleDone']");
    private By tickeidreviewpage = By.cssSelector("div[class*='style-TicketListContainer'] div[class*='style-ticketList']:nth-child(1) div[class*='style-ticketListHeader'] svg");
    private By errormessage = By.xpath("//input[@type='text']/following-sibling::span[2]");
    private By priceguidetext = By.xpath("div[ class*='priceGuidePlaceholder']");
    private By inactiveicon = By.cssSelector("i[class*='mdInactive']");
    private By submitlistingbutton = By.cssSelector("button[ class*='resaleSubmitListing']");
    private By paymentmethod = By.xpath("//input[@name='payoutMethod']");
    private By sellercredit = By.xpath("//li[text()='Seller Credit']");
    private By donebutton = By.xpath("//button[text()='Done']");
    private By cancelposting = By.xpath("//button[contains(@class,'sell_cancelButton sell-inverseButto')]");
    private By editposting = By.xpath("//button[contains(@class,'sell_editButton sell-inverseButto')]");
    private By gototicketsbutton = By.xpath("//button[contains(@class,'sell_cancelSuccessButton')]");
    private By confirmbutton = By.xpath("//button[contains(@class,'sell_cancelConfirmButton')]");
    private By cancelpostingmessage = By.xpath("//h5[text()='Please confirm that you wish to cancel your ticket posting.']");
    private By successmessage = By.xpath("//h5[text()='Success!']");
    private By successposting = By.xpath("//div[contains(@class,'resale_success')]/div[1]/span");
    private By inprogressstate = By.xpath("(//p[text()='In Progress'])[1]");
    private By editmanagetext =By.xpath("(//span[text()='EDIT/MANAGE'])[1]");
    private By seatdetailsconfpage = By.cssSelector("div[class*='ticketListHeader'] span");
    private By ticketCount = By.cssSelector("div[class*='ticket-totalTicketCounts'] span small");
    private By tooltipbutton =By.xpath("//button[contains(@class,'tooltipButton')]");


    TicketsNew ticketsNew = new TicketsNew(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);



    //div[contains(@class,'styles-ticketsBlock')]/strong[contains(.,'Section 238 | Row H')]/following-sibling::ul/li/div/strong[contains(.,'11')]


    public void validResaleEvent(String mail, String passwd) {
        String uname = (String) base.getGDValue(mail);
        String password = (String) base.getGDValue(passwd);
        HashMap<String, String> availability = null;
        availability = aapi.getResaleableEventForMember(uname, password);
        base.Dictionary.put("EventNameResale", availability.get("eventname"));
        base.Dictionary.put("EventIdResale", availability.get("eventid"));
        base.Dictionary.put("SectionNameResale", availability.get("section"));
        base.Dictionary.put("TicketNumberResale", availability.get("number"));
        base.Dictionary.put("TicketIDResale", availability.get("seats"));
        ticketsNew.selectEventByName(availability.get("eventname"));

    }


    public void selectSeats() {
        String[] seats = base.Dictionary.get("TicketIDResale").split(";");

        for (int i = 0; i < 2; i++) {
            By seatcb = By.cssSelector("li[ class*='" + seats[i] + "'] div label div");
            //util.checkIfElementClickable(seatcb, 2);
            click(seatcb,"SEAT CB");
            //click(selectseats,"CLICK HEADER");

        }
        click(continuebutton, "CONTINUE");
    }

    public void verifySetPrice() {
        Assert.assertTrue(getText(eventName).contains(base.Dictionary.get("EventNameResale")));
        Assert.assertTrue(util.checkIfElementClickable(eventName, 0));
        Assert.assertTrue(util.checkIfElementClickable(ticketpriceheader, 0));
        Assert.assertTrue(util.checkIfElementClickable(incrementicon, 0));
        Assert.assertTrue(util.checkIfElementClickable(decrementicon, 0));
        Assert.assertTrue(util.checkIfElementClickable(priceinput1, 0));
        Assert.assertTrue(util.checkIfElementClickable(payoutdetailheader, 0));
        Assert.assertTrue(util.checkIfElementClickable(ticketlistedforheader, 0));
        Assert.assertTrue(util.checkIfElementClickable(ticketlistedforamount, 0));
        Assert.assertTrue(util.checkIfElementClickable(servicefeeheader, 0));
        Assert.assertTrue(util.checkIfElementClickable(payoutperticketheader, 0));
        Assert.assertTrue(util.checkIfElementClickable(payoutperticketamount, 0));
        Assert.assertTrue(util.checkIfElementClickable(youwillgetpaidheader, 0));
        Assert.assertTrue(util.checkIfElementClickable(youwillgetpaidamount, 0));
        Assert.assertTrue(util.checkIfElementClickable(payoutdetailheader, 0));
    }


    public void setPrice(String price) {
        base.Dictionary.put("TicketPriceResale", price);
            try {
                util.sendBackSpace(priceinput1,6);
                type(priceinput1, "PRICE", price,true);
                sendKeys(priceinput1,Keys.SPACE);
                sendKeys(priceinput1,Keys.DELETE);
                if (checkIfElementPresent(seatexpand2)) {
                    click(seatexpand2, "EXPAND 2");
                    util.sendBackSpace(priceinput2,6);
                    type(priceinput2, "PRICE", price, true);
                    sendKeys(priceinput2,Keys.SPACE);
                    sendKeys(priceinput1,Keys.DELETE);
                }

                if (checkIfElementPresent(seatexpand3)) {
                    click(seatexpand3, "EXPAND 3");
                    util.sendBackSpace(priceinput3,6);
                    type(priceinput3, "PRICE", price, true);
                    sendKeys(priceinput3,Keys.SPACE);
                    sendKeys(priceinput1,Keys.DELETE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        click(continuebutton, "CONTINUE",5);
        }

    public void verifyReviewPage() {
        //Assert.assertTrue(util.checkIfElementClickable(checkingaccountlireviewpage, 0));
        Assert.assertTrue(util.checkIfElementClickable(payeeinfoaddressreviewpage, 5));
        Assert.assertTrue(util.checkIfElementClickable(payeeinfoheaderreviewpage, 0));
        Assert.assertTrue(util.checkIfElementClickable(payoutmethoddropdownreviewpage, 0));
        //Assert.assertTrue(util.checkIfElementClickable(sellercreditlireviewpage, 0));
        Assert.assertTrue(util.checkIfElementClickable(payoutmethodheaderreviewpage, 0));
        //Assert.assertTrue(util.checkIfElementClickable(totalpayoutamountreviewpage, 0));
        Assert.assertTrue(util.checkIfElementClickable(updateaccountinfobuttonreviewpage, 0));
        Assert.assertTrue(util.checkIfElementClickable(updatepayeeinfolinkreviewpage, 0));
        //Assert.assertTrue(util.checkIfElementClickable(updatepayeeinfolinkreviewpage, 0));
        Assert.assertTrue(getText(eventName).contains(base.Dictionary.get("EventNameResale")));


        String[] seats = base.Dictionary.get("TicketIDResale").split(";");
        List<String> section = new ArrayList<String>();
        List<String> row = new ArrayList<String>();
        List<String> seat = new ArrayList<String>();


        for (int i = 0; i < 2; i++) {
            String[] seatdetails1 = seats[i].split("\\.");
            section.add(seatdetails1[1]);
            row.add(seatdetails1[2]);
            seat.add(seatdetails1[3]);
        }


        if (section.get(0).equals(section.get(1)) && row.get(0).equals(row.get(1)) && (Integer.parseInt(seat.get(0)) == (Integer.parseInt(seat.get(1)) - 1))) {
                    //if (Integer.parseInt(seat.get(1)) == Integer.parseInt(seat.get(2)) - 1) {
                        Assert.assertTrue(getText(seat1reviewpage).contains("Sec " + section.get(0) + ", Row " + row.get(0) + ", Seat " + seat.get(0) + " - " + seat.get(1)));
                        Dictionary.put("ResaleSeatHeader", "Sec " + section.get(0) + ", Row " + row.get(0) + ", Seat " + seat.get(0) + " - " + seat.get(1));

                   // } else {
                    //    Assert.assertTrue(getText(seat1reviewpage).contains("Sec " + section.get(0) + ", Row " + row.get(0) + ", Seat " + seat.get(0))); // + " - " + seat.get(1)));
                   //     Assert.assertTrue(getText(seat2reviewpage).contains("Sec " + section.get(0) + ", Row " + row.get(0) + ", Seat " + seat.get(1)));
                  //  }
                } else {
                    Assert.assertTrue(getText(seat1reviewpage).contains("Sec " + section.get(0) + ", Row " + row.get(0) + ", Seat " + seat.get(0)));
                    Assert.assertTrue(getText(seat2reviewpage).contains("Sec " + section.get(1) + ", Row " + row.get(1) + ", Seat " + seat.get(1)));
                    Dictionary.put("ResaleSeatHeader", "Sec " + section.get(0) + ", Row " + row.get(0) + ", Seat " + seat.get(0));
                    //Assert.assertTrue(getText(seat3reviewpage).contains("Sec " + section.get(2) + ", Row " + row.get(2) + ", Seat " + seat.get(2)));
                }
            //}
        double payout = aapi.getResalePolicyForMember(seats, Integer.parseInt(base.Dictionary.get("TicketPriceResale"))*2);
        Dictionary.put("ResalePayout",String.format("$%.2f",payout));
        String yougetpaid;
        if(util.checkIfElementClickable(youwillgetpaidamount,0)) {
            yougetpaid =getText(youwillgetpaidamount);
        } else {
            yougetpaid =getText(totalpayoutamountreviewpage);
        }
        Assert.assertTrue(yougetpaid.equals(String.format("$%.2f",payout)));
    }

    public void verifyConfirmationPage() {
        click(submitlistingbutton,"SUBMIT LISTING",5);
        //Assert.assertTrue(util.checkIfElementClickable(checkingaccountlireviewpage, 0));
        Assert.assertTrue(util.checkIfElementClickable(welldonetextConfpage, 10));
        Assert.assertTrue(getText(ticketlistingtextconfpage).contains("Your ticket listing will be live soon"));
        Assert.assertTrue(getText(ticketlistingtextconfpage).contains("We’ll email you to confirm when your listing is live. Once your tickets sell, we’ll send you an additional email with everything you need to know. You can edit or delete your ticket listing at anytime once it’s live"));
        Assert.assertTrue(getText(ticketpaidtextconfpage).contains("Time to get paid"));
        Assert.assertTrue(getText(ticketpaidtextconfpage).contains("Once your sold tickets are processed, we'll send the payout amount to your checking account ending in number 3123, Payments will be processed within 5 - 7 business days"));
        Assert.assertTrue(util.checkIfElementClickable(showalllistingbtnconfpage, 0));
        Assert.assertTrue(getText(showalllistingtxtconfpage).contains("Show all listing details"));
        click(showalllistingbtnconfpage,"SHOW ALL",5);
        String yougetpaid = getText(youwillgetpaidamount,10);
        Assert.assertTrue(yougetpaid.contains(Dictionary.get("ResalePayout")));
        Assert.assertTrue(getText(seatdetailsconfpage).contains(Dictionary.get("ResaleSeatHeader")));
        Assert.assertTrue(getText(eventName).contains(base.Dictionary.get("EventNameResale")));
    }



    public void minimumPriceErrorMessage(String price) throws Exception {

    	base.Dictionary.put("TicketPriceResale", price);
    	try {
    		util.sendBackSpace(priceinput1,6);
    		type(priceinput1, "PRICE", price,true,10);
    		sendKeys(priceinput1,Keys.SPACE);
    		sendKeys(priceinput1,Keys.DELETE);

    		if (checkIfElementPresent(seatexpand2)) {
    			util.sendBackSpace(priceinput1,6);
    			type(priceinput2, "PRICE", price, true);
    			sendKeys(priceinput2,Keys.SPACE);
    			sendKeys(priceinput2,Keys.DELETE);
    		}

    		if (checkIfElementPresent(seatexpand3)) {
    			util.sendBackSpace(priceinput3,6);
    			type(priceinput3, "PRICE", price, true);
    			sendKeys(priceinput3,Keys.SPACE);
    			sendKeys(priceinput3,Keys.DELETE);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    	click(tooltipbutton,"Tool Tip",5);
    	String errormessagetext =util.getText(errormessage, 5);
    	Assert.assertTrue(util.checkIfElementClickable(inactiveicon, 0));
    	Assert.assertTrue(errormessagetext.equals("Ticket price must be at least $5.00"));
    	Assert.assertFalse(getDriver().findElement(continuebutton).isEnabled());

    }
		

    public void maximumPriceErrorMessage(String price) {

    	base.Dictionary.put("TicketPriceResale", price);
    	try {
    		util.sendBackSpace(priceinput1,10);
    		type(priceinput1, "PRICE", price,true);
    		sendKeys(priceinput1,Keys.SPACE);
    		sendKeys(priceinput1,Keys.DELETE);
    		
    		if (checkIfElementPresent(seatexpand2)) {
    			util.sendBackSpace(priceinput1,6);
    			type(priceinput2, "PRICE", price, true);
    			sendKeys(priceinput2,Keys.SPACE);
    			sendKeys(priceinput2,Keys.DELETE);
    		}

    		if (checkIfElementPresent(seatexpand3)) {
    			util.sendBackSpace(priceinput3,6);
    			type(priceinput3, "PRICE", price, true);
    			sendKeys(priceinput3,Keys.SPACE);
    			sendKeys(priceinput3,Keys.DELETE);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	click(incrementicon,"increment button");
    	String errormessagetext =util.getText(errormessage, 5);
    	Assert.assertTrue(util.checkIfElementClickable(inactiveicon, 0));
    	Assert.assertTrue(errormessagetext.equals("Ticket price must be at less then or equal to $10,000.00"));
    	Assert.assertFalse(getDriver().findElement(continuebutton).isEnabled());
    }
	

	public void clickonCancelResalePage() {
		click(cancelbutton,"Cancel button");
		Assert.assertEquals(getDriver().getCurrentUrl().contains("myevents"), true);
	}
	
	public void selectpayoutmethod(String pvalue) {
		try {
			if (checkIfElementPresent(paymentmethod, 2)) {
				click(paymentmethod,"Payout Method");
				click(sellercredit,"Seller Credit");
			}

		}catch(Exception e) {
			click(sellercredit,"Seller Credit");
			e.printStackTrace();	
		}
		click(submitlistingbutton,"Submit Listing");
		Assert.assertTrue(getText(successposting).contains("Well Done!"));
		click(donebutton,"Done Button"); 
		Assert.assertEquals(getDriver().getCurrentUrl().contains("myevents"), true);
		
	}
		
	
	public void clickEditManage() {	
		String text = getText(ticketCount);
		Pattern p=Pattern.compile("(\\d+)");
		Matcher m=p.matcher(text);
		String ticketCount = "";
		while (m.find()) {
			ticketCount=m.group();
		}	int i;
		for(int j=0;j<=10;j++) {

			if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
				for (i=0; i<=Integer.parseInt(ticketCount)-1;i++) {
					
					util.swipe((IOSDriver) (getDriver()), 200, 200, 600, 400, Duration.ofSeconds(1, 1));
					
					if(!util.getElementWhenInVisible(editmanagetext))
					//if(util.checkIfElementClickable(editmanagetext,1))
					{break;}
					
				}
				if(i<Integer.parseInt(ticketCount)-1)break;
				getDriver().navigate().refresh();
			}else {

				if(util.checkIfElementClickable(editmanagetext, 1))
				{break;}
				getDriver().navigate().refresh();
				sync(30000l);
			}

		}

		String[] seats = base.Dictionary.get("TicketIDResale").split(";");
		for(int k =0; k<1; k++) {
			By seatcb = By.cssSelector("span[class*='sellManage_"+seats[k]+"']");
			click(seatcb,"Edit/Manage");
		}
	}
	
	public void cancelposting() {
		click(cancelposting,"Cancel Posting",10);
		Assert.assertTrue(getText(cancelpostingmessage).equals("Please confirm that you wish to cancel your ticket posting."));
		
	}
	public void editposting() {
		click(editposting,"Edit Posting",10);
		Assert.assertTrue(util.checkIfElementClickable(eventName,10));
	}
	public void confirmbutton() {
		click(confirmbutton,"Confirm Button",30);
		Assert.assertEquals(getText(successmessage, 20), "Success!");
	}
	
	public void clickGoToTickets() {
		click(gototicketsbutton,"Go To Tickest");
		Assert.assertEquals(getDriver().getCurrentUrl().contains("myevents"), true);
	}
	
	public void editpostingstatus() {
		Assert.assertEquals(getDriver().getCurrentUrl().contains("myevents"), true);
	}

	public void verifyMyEventPage() {
        click(donebtnconfpage,"DONE");
        dashboardSection.verifyMyEventPage(Dictionary.get("EventNameResale"));
    }
}

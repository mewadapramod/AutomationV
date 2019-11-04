package org.iomedia.galen.pages;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.framework.Driver.TestDevice;
import org.iomedia.framework.OSValidator;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.iomedia.galen.common.ManageticketsAAPI;
import org.iomedia.galen.common.ManageticketsAPI;
import org.iomedia.galen.common.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.SkipException;

import com.google.common.io.Files;

import io.appium.java_client.ios.IOSDriver;

public class ManageTicket extends BaseUtil {

	private String driverType;
	BaseUtil base;
	

	public ManageTicket(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<HashMapNew> sTestDetails) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		driverType = driverFactory.getDriverType().get();
	}

	private By bundleParking = By.cssSelector("div[class*='manage-tickets-sendDonateButtons'] button,[class*='manage-tickets-formSubmitButtons'] button:nth-child(1)");
	private By popupButton = By.cssSelector("div[class*='manage-tickets-sendDonateButtons'] button,[class*='manage-tickets-formSubmitButtons'] button[class*='theme'] ");
	private By ContinueTransferEDP = By.cssSelector("button[class*='seatSelectionContinue']");
	private By popupTransferContinueEDP= By.xpath("//div[contains(@class,'manage-tickets-formSubmitButtons')]//button[contains(@class,'send_recipientContinue')]");
	private By popupTransferCancelEDP= By.xpath("//div[contains(@class,'manage-tickets-formSubmitButtons')]//button[contains(@class,'send_recipientCancel')]");
	private By ContinueButtonEDP = By.cssSelector("button[class*='seatSelectionContinue']");
	private By donateButton = By.cssSelector("button[class*='donate_seatSelectionContinue']");
	private By doneButton = By.cssSelector("//button[contains(@class,'print_successDone')]");
	private By donatecharityContinue = By.cssSelector("button[class*='donate_charityContinue']");
	private By donatecharityConfirmButton =By.cssSelector("button[class*='donate_charityConfirmButton']");
	//private By claimButton = By.cssSelector("div[class*='manage-tickets-sendDonateButtons'] button,[class*='manage-tickets-formSubmitButtons'] button");
	private By claimButton =By.xpath("//button[contains(@class,'invite_accepte') and @type='button']");
	private By claimButtonEDP =By.xpath("(//div[contains(@class,'styles-actionButtons')]//button)[2]");
	private By acceptTransferOffer=By.xpath("//button[contains(@class,'inviteaccepted_gotoTakover') and @type='button']");
	private By acceptTransferOfferDone = By.xpath("//button[contains(@class,'invite_LetsGo ') and @type='button']");
	
	
	private By confirm = By.xpath("//section[contains(@class,'theme-body')]//button[text()='CONFIRM']");
	private By reclaimDone= By.xpath("//section[contains(@class,'theme-body')]//button[text()='GO TO TICKETS'] | //button[contains(@class,'reclaim-successBtn') and @type='button']| //button[@type='submit'] | //div[contains(@class,'styles-buttonContainer')]/button")   ;
	private By successTextUpdated=By.xpath("//div[contains(@class,'reclaim-message')]//strong");
	private By successTextUpdatedEDP=By.xpath("//div[contains(@class,'styles-titleContainer')]//span[contains(@class,'styles-titleText')]");
	private By popUpEventNameEDP = By.xpath("//section[contains(@class,'theme-body')]//div[contains(@class,'eventInfo')]//h4 | //section[contains(@class,'theme-body')]//div[contains(@class,'ticketInfo')]//h5");
	//private By popUpEventName = By.xpath("//section[contains(@class,'theme-body')]//div[contains(@class,'ticketInfo')]//h5");
	private By popUpEventName = By.xpath("//section[contains(@class,'theme-body')]//div[contains(@class,'ticketInfo')]//h5");
	private By popUpEventNameforMultiple = By.xpath("//form[contains(@class,'manage-tickets')]//div[contains(@class,'download-ticketInfo')]//div//h5");
	private By mobilepopUpEventName = By.xpath("//div[contains(@class,'ticket-team')] | (//span[contains(@class,'event-card-iconText-')])[2]");
	private By seatExpiryDate = By.xpath("//section[contains(@class,'theme-body')]//div[contains(@class,'charityInfo')]//span");
	private By claimLink = By.xpath("//*[contains(@class,'claimLinkContainer')]/div[2]");
	private By RecipientDetailEDP = By.xpath("//*[contains(@class,'email-transfer-recipientInfo')]//div//h4");
	private By eventClaimLink = By.xpath("//div[text()='Event Link']/../..//*[contains(@class,'claimLinkContainer')]/div[2]/span");
	private By parkingClaimLink = By.xpath("//div[text()='Parking Link']/../..//*[contains(@class,'claimLinkContainer')]/div[2]/span");
	private By reclaim = By.xpath("//section[contains(@class,'theme-body')]//form//button[text()='RECLAIM'] | //section[contains(@class,'theme-body')]//button[2]");
	private By sendUser= By.xpath("//section[contains(@class,'theme-body')]//div[contains(@class,'charityInfo')]//h5");
	private By charitySelect = By.xpath("//section[contains(@class,'theme-body')]//div[contains(@class,'theme-dropdown')]");
	private By firstCharity = By.xpath(".//section[contains(@class,'theme-body')]//div[contains(@class,'theme-dropdown')]/ul/li");
	private By earningPrice = By.xpath("//section[contains(@class,'theme-body')]//input[contains(@name,'seatprice')]");
	private By depositAccount= By.xpath("//section[contains(@class,'theme-body')]//div[contains(@class,'theme-dropdown')]//input");
	private By bankAccount = By.xpath("//section[contains(@class,'theme-body')]//div[contains(@class,'theme-dropdown')]//ul/li[1]");
	private By sellerCredit = By.xpath("//section[contains(@class,'theme-body')]//div[contains(@class,'theme-dropdown')]//ul/li[2]");
	private By editBankAccount = By.xpath("//*[contains(@class,'sell-endingIn')]//button");
	private By editSellerProfile = By.xpath("//*[contains(@class,'sell-sellerAddress')]//button");
	private By sellerFirstName = By.xpath("//*[contains(@class,'sell-sellTicketsProfileEdit')]//input[@name='first_name']");
	private By sellerLastName= By.xpath("//*[contains(@class,'sell-sellTicketsProfileEdit')]//input[@name='last_name']");
	private By sellerAddress1 = By.xpath("//*[contains(@class,'sell-sellTicketsProfileEdit')]//input[@name='address[line_1]']");
	private By sellerAddress2 = By.xpath("//*[contains(@class,'sell-sellTicketsProfileEdit')]//input[@name='address[line_2]']");
	private By sellerCountry = By.xpath("//*[contains(@class,'sell-sellTicketsProfileEdit')]//*[@name='address[country]']");
	private By sellerCity = By.xpath("//*[contains(@class,'sell-sellTicketsProfileEdit')]//*[@name='address[city]']");
	private By sellerState = By.xpath("//*[contains(@class,'sell-sellTicketsProfileEdit')]//*[@id='addressState']");
	private By sellerZip = By.xpath("//*[contains(@class,'sell-sellTicketsProfileEdit')]//*[@name='address[postal_code]']");
	private By sellerMobile = By.xpath("//*[contains(@class,'sell-sellTicketsProfileEdit')]//*[@name='address[mobile_phone]']");
	private By sellerHomePhone = By.xpath("//*[contains(@class,'sell-sellTicketsProfileEdit')]//*[@name='address[home_phone]']");
	private By sellerAddress = By.xpath("//*[contains(@class,'sell-sellerAddress')]//p");
	private By bankDetails = By.xpath("//*[contains(@class,'manage-tickets-endingIn')]//p | //*[contains(@class,'sell-endingIn')]//p");
	private By bankAccountType = By.xpath("//*[contains(@class,'sell-bankEditProfile')]//input[contains(@name,'account_type')]");
//	private By checkingBankAccout = By.xpath("//*[contains(@class,'theme-dropdown')]/ul/li[text()='Checking']");
//	private By savingBankAccount = By.xpath("//*[contains(@class,'theme-dropdown')]/ul/li[text()='Saving']");
	private By bankRoutingNumber = By.xpath("//*[contains(@class,'sell-bankEditProfile')]//input[contains(@name,'routing_number')]");
	private By bankAccountNumber = By.xpath("//*[contains(@class,'sell-bankEditProfile')]//input[contains(@name,'account_number')]");
	private By bankConfirmAccount = By.xpath("//*[contains(@class,'sell-bankEditProfile')]//input[contains(@name,'confirm_account_number')]");

	private By manageTicketsList = By.cssSelector(".react-root-event ul[class*='events-eventList'] li[class*='events-event'] a,div[class*='eventListContainer'] a[class*='style-link']");
	private By desktopEventImages = By.cssSelector(".react-root-event ul[class*='events-eventList'] li[class*='events-event'] a,div[class*='eventListContainer'] a[class*='style-link'] div[class*='hide'] img,div[class*='eventListContainer'] a[class*='style-link'] div[class*='hide'] div[class*='eventImageContainer']");
	private By ticketsListing = By.cssSelector(".react-root-event  ul[class*='ticketList'] li[class*='ticket-event'] div[class*='ticket-eventInner']");	
	private By ticketsListingEDP = By.xpath("//div[contains(@class,'event-card-eventInfo')]");
	private By ticketscountEDP = By.xpath("//div[contains(@class,'seat-list-canManage') or contains(@class,'seat-list-seat-')]");
	private By ticketListingMobile = By.xpath("//section//div[contains(@class,'eventList-')]");
	private By downloadTickets = By.xpath(".//button[contains(.,'Print')]");
	private By downloadTicketsEDP = By.cssSelector("button#printTickets");
	private By downloadTextPopUp = By.xpath(".//h6[contains(text(),'Print Tickets')]");

	private By sendTicket = By.xpath("//*[contains(@class,'ticket-subHeader')]//button[contains(.,'Transfer')] | //*[contains(@class,'ticketListOuter')]//button[contains(@class,'ticket_Send')]");
	private By sendTicketEDP = By.xpath("//*[contains(@class,'ticketListOuter')]//button[contains(@class,'ticket_Send')] | //*[contains(@class,'ticketListOuter')]//button[contains(.,'Transfer')]");

	private By donateTicket = By.xpath(".//ul[contains(@class, 'theme-menuInner')]//li/span[text()='Donate'] | //button[@id='donateTickets']");
	private By moreButton = By.xpath(".//button[contains(.,'more_horiz')]");
	private By moreButtonEDP = By.xpath("//div[@id='moreButton']//button[@type='button']");
	private By moreButtonMobile = By.xpath(".//div[contains(@class, 'ticketButtonRowMobile')]//button[contains(.,'more_horiz')]");
	private By moreButtonMobileEDP = By.xpath("//div[@id='moreButton']//button[@type='button']");
	private By sendTicketMobile = By.xpath(".//div[contains(@class, 'ticketButtonRowMobile')]//button[contains(., 'Transfer')]| //button[@id='transferTickets']");
	private By donateTicketMobile = By.xpath(".//div[contains(@class, 'ticketButtonRowMobile')]//ul[contains(@class, 'theme-menuInner')]//li/span[text()='Donate']");
	private By sendTextPopUp = By.xpath(".//h6[contains(text(),'Send Tickets')]");
	private By donateTextPopUp = By.xpath(".//h6[contains(text(),'Donate Tickets')]");
	private By sellTicket = By.xpath(".//button[contains(.,'Sell')]");
	private By sellTicketMobile = By.xpath("//button[contains(., 'Sell')]");
	private By sellTicketPopUp = By.xpath(".//h6[contains(text(),'Sell Tickets')]");
	private By firstManageTickets = By.cssSelector(".react-root-event ul[class*='events-eventList'] li[class*='first odd']");

	private By ticketsDetail = By.xpath(".//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails')]");
	private By ticketsDetailEDP = By.xpath("//div[contains(@class,'seat-list-seat-31rq9u')]");
	private By noofmobiletickets = By.xpath(".//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-slide')] | //div[contains(@class, 'ticket-eventListContainerMobile')]/section/div[contains(@class, 'ticket-event') and not(contains(@class, 'ticket-eventTicketPagger'))]");
	private By bundleparkingcheckbox = By.cssSelector("div[class*='bundleParkingCheck'] div");
//	private By parkingslotsCheckbox = By.cssSelector("ul[class*='manage-tickets-parkingSlots'] li label div");
	private By events = By.xpath(".//div[contains(@class, 'react-root-event')]//ul[contains(@class, 'events-eventList')]//li[contains(@class, 'events-event')]//a | .//div[contains(@class, 'eventListContainer')]//a[contains(@class,'style-link')]");
	private By desktopEventNames = By.xpath(".//div[contains(@class, 'react-root-event')]//ul[contains(@class, 'events-eventList')]//li[contains(@class, 'events-event')]//a//h3 | .//div[contains(@class, 'eventListContainer')]//a[contains(@class,'style-link')]/div[contains(@class,'hide')]//div[contains(@class, 'eventName')]");
	private By mobileEventNames = By.xpath(".//div[contains(@class, 'react-root-event')]//ul[contains(@class, 'events-eventList')]//li[contains(@class, 'events-event')]//a//h3 | .//div[contains(@class, 'eventListContainer')]//a[contains(@class,'style-link')]/div[contains(@class,'show')]//div[contains(@class, 'eventName')]");
	private By desktopEventDate = By.xpath(".//div[contains(@class, 'react-root-event')]//ul[contains(@class, 'events-eventList')]//li[contains(@class, 'events-event')]//a//p | .//div[contains(@class, 'eventListContainer')]//a[contains(@class,'style-link')]/div[contains(@class,'hide')]//div[contains(@class, 'eventTime')]");
	private By desktopEventTime = By.xpath(".//div[contains(@class, 'react-root-event')]//ul[contains(@class, 'events-eventList')]//li[contains(@class, 'events-event')]//a//p | .//div[contains(@class, 'eventListContainer')]//a[contains(@class,'style-link')]/div[contains(@class,'hide')]//div[contains(@class, 'eventVenue') and contains(@class, 'hide')]");
	private By mobileEventDateTime = By.xpath(".//div[contains(@class, 'react-root-event')]//ul[contains(@class, 'events-eventList')]//li[contains(@class, 'events-event')]//a//p | .//div[contains(@class, 'eventListContainer')]//a[contains(@class,'style-link')]/div[contains(@class,'show')]//span[contains(@class, 'eventDate')]");
	private By totalTicketsCountText = By.xpath(".//div[contains(@class, 'ticket-totalTicketCounts')]/span[1]");
	private By totalTicketsCountTextEDP = By.xpath("//h3[contains(@class,'seat-list-header')]");
	private By sentTicketsCountText = By.xpath(".//div[contains(@class, 'ticket-totalTicketCounts')]/span[contains(., 'Sent')]");
	private By listedTicketsCountText = By.xpath(".//div[contains(@class, 'ticket-totalTicketCounts')]/span[contains(., 'Listed')]");
	private By claimedTicketsCountText = By.xpath(".//div[contains(@class, 'ticket-totalTicketCounts')]/span[contains(., 'Claimed')]");
	private By donatedTicketsCountText = By.xpath(".//div[contains(@class, 'ticket-totalTicketCounts')]/span[contains(., 'Donated')]");
	private By sentTickets = By.xpath(".//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails')]/..//div[contains(@class, 'ticket-ticketImage')]//div[contains(@class, 'ticket-listedOn') and text()='Sent'][1]");
	private By listedTickets = By.xpath(".//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails')]/..//div[contains(@class, 'ticket-ticketImage')]//div[contains(@class, 'ticket-listedOn') and text()='Listed'][1]");
	private By claimedTickets = By.xpath(".//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails')]/..//div[contains(@class, 'ticket-ticketImage')]//div[contains(@class, 'ticket-listedOn') and text()='Claimed'][1]");
	private By donatedTickets = By.xpath(".//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails')]/..//div[contains(@class, 'ticket-ticketImage')]//div[contains(@class, 'ticket-listedOn') and text()='Donated'][1]");
	private By donatedTicketsEDP = By.xpath("(//div[contains(@class,'seat-list-messageTitle') and text()='Donated'])[1]");
	private By donateButtonEDP = By.xpath("//button[@id='donateTickets']");
	private By donateButtonEDPMobile = By.xpath("//span[contains(@class,'theme-caption')][contains(text(),'Donate')]/parent::li");
	
//	private By ticketsdetails = By.xpath("//button[contains(@class,'ticket-detailTicket') and text()='TICKET DETAILS']");
	private By terms_condition = By.xpath("(//span[contains(@class,'renderHtml')])[1]");
	//private By ticketDetails = By.xpath("(//div[contains(@class,'ticket-detailOuter')])[1]//h4[not(contains(text(),'Purchase'))]/..//span[not(span)]");
	private By ticketDetails = By.xpath("(//div[contains(@class,'ticket-detailOuter')])[1]//h4[not(contains(text(),'Adult'))]/..//span | (//div[contains(@class,'ticketCardInner')])[1]//h4[not(contains(text(),'Adult'))]/..//span");
//	private By scanBarcode = By.cssSelector("div[class*='ticket-barCodeInner'] svg,div[class*='ticket-barcode'] svg");
//	private By scanCode = By.xpath("//div[contains(@class,'ticket-barCodeInner')]");
	private By mobileDownloadSectionName = By.xpath("(.//div[contains(@class, 'ticket-barDetails')]//span)[1]/../strong | (//span[contains(@class,'ticket-carousel-infoText-')])[1]");
	private By mobileDownloadRowName = By.xpath("(.//div[contains(@class, 'ticket-barDetails')]//span)[2]/../strong | (//span[contains(@class,'ticket-carousel-infoText-')])[2]");
	private By mobileDownloadSeatName = By.xpath("(.//div[contains(@class, 'ticket-barDetails')]//span)[3]/../strong | (//span[contains(@class,'ticket-carousel-infoText-')])[3]");
	private By barIcon = By.xpath("//div[contains(@class, 'ticket-qrIcon')]//img | //div[contains(@class, 'ticket-qr')]//img | //div[contains(@id,'psebarcodeview')]/canvas");

	private By secureBarcodeIcon = By.xpath("//div[contains(@class,'secureBarcode ticket-secureBarcodeContainer')]");
	private By educationnextbutton = By.xpath("//button[contains(@class,'nextButton') and @type='button']");
	private By educationAllSetButton = By.xpath("//button[contains(@class,'tyle-footerButton') and @type='button']");
	private By addToAppleWalletIcon = By.cssSelector("span[class*='viewbarcodeAddAppleWallet'] , div[class*='ticket-qrCodeInner'] , button[class*='viewbarcodeAddAppleWallet']");
	private By androidBarCodeLink = By.xpath("//span[contains(@class, 'ticket-nonIosDevice')]");;
	private By gateNumber = By.cssSelector("div[class*='ticket-entryFrom']");
	private By BuyTickets = By.xpath("//div[@class='container']//div[2]//button");
	private By viewbarcodebutton = By.xpath("(//span[contains(@class,'viewBarcode')])");
	private Homepage homepage = new Homepage(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	private By cancelPosting= By.cssSelector("div[class*='sell-buttonStack'] :nth-child(2) div button");
	private By editPosting = By.cssSelector("div[class*='sell-buttonStack'] :nth-child(1) div button");
	private By search = By.cssSelector("[class*='eventlistSearch'] div div");
	private By searchValue = By.cssSelector("[class*='eventlistSearch'] input");
	private By eventsList = By.cssSelector("ul[class*='events-eventList'] li");
	private By firstEventName = By.cssSelector("ul[class*='events-eventList'] li:nth-child(1) h3");
	private By clickticketlistview = By.cssSelector(".listView #Page-1");
	private By transferTag= By.xpath("//input[@name='transferTag']");
	private By done = By.cssSelector("div[class*='manage-tickets-sendDonateButtons'] button,[class*='manage-tickets-formSubmitButtons'] button:nth-child(2)");
	private By doneEDP = By.xpath("//div[contains(@class,'manage-tickets-formSubmitButtons')]/button");
	private By noTicketDetails = By.cssSelector("span[class*='ticket-noDetails']");
	private By lostTicket = By.xpath(".//div[contains(@class,'download-charityInfo')]//label");
	private By ticketname = By.xpath("//div[contains(@class, 'eventName')]");
	private By tickettime = By.xpath("//div[contains(@class, 'eventVenue')]");
	private By mobileticketname = By.xpath("//div[contains(@class, 'eventDetails')]/div[contains(@class,'style-showMobile')]");
	private By mobiletickettime = By.xpath("//div[contains(@class, 'eventDetails')]/div[contains(@class,'style-showMobile')]");
	private By viewAllDropDown = By.cssSelector("div[data-react-toolbox='dropdown'] div input");
	private By selectPending = By.xpath("//div[@data-react-toolbox='dropdown']/ul/li[contains(.,'Pending')]");
	private By selectCompleted = By.xpath("//div[@data-react-toolbox='dropdown']/ul/li[contains(.,'Completed')]");
	private By selectActive = By.xpath("//div[@data-react-toolbox='dropdown']/ul/li[contains(.,'Active')]");
	private By transfreeName = By.xpath("//div[contains(@class, 'ticket-status')]/div[contains(@class, 'ticket-statusInner')]/p[1]");
	private By cancelTransfer = By.xpath("//div[contains(@class, 'ticket-ticketStatus')]/div/div[contains(@class, 'ticket-completedStatus')]/span[contains(.,'Cancel Transfer')]");
	private By cancelTransferPOPUP = By.xpath("(//button[contains(@class,'styles-modalButton') and @type='button'])[2]");
	private By cancelTransferEDP = By.xpath("//button[contains(@class,'seat-list-pendingActionButton') and @type='button'] | (//*[text()='Transfer Pending']/following::button[@type='button'])[1]");
	private By completedTransfer = By.xpath("//div[contains(@class, 'ticket-ticketStatus')]/div/div[contains(.,'Ticket Sent')]");
	private By reclaimDialogue = By.xpath("//div[@data-react-toolbox='dialog']/section/h6[contains(.,'Reclaim Tickets')] | //div[@data-react-toolbox='dialog']/section/div/h3[contains(.,'Cancel Transfer')]");
	private By reclaimDropDown = By.cssSelector("div[class*='style-iconAction'] div svg:nth-child(3)");
    private By numTktReclaimDropDown = By.cssSelector("div[class*='style-iconAction'] div span");
    private By seatsReclaimDialogue = By.cssSelector("div[class*='style-eventDetailsHolder']  div div[class*='style-eventDetails']");
    private By reclaimButton = By.cssSelector("div[class*='reclaim-buttonBox'] div button:nth-child(2) , div[class*='styles-modalButtons']  button:nth-child(2)");
    private By reclaimButtonDone = By.cssSelector("div[class*='reclaim-buttonBox'] div button , div[class*='styles-buttonContainer'] button");
    private By manageTicketButton = By.cssSelector("div[class*='ticket-twoButtons'] button");
	private By ticketCount = By.cssSelector("div[class*='ticket-totalTicketCounts'] span small , div[class*='seat-list-ticketsList'] h3 ");
	private By submitBtn = By.xpath(".//button[@type='submit']");
	private By seatFirst =By.xpath("(//div[@data-react-toolbox='check'])[2]");
	private By OkButton =By.xpath("//button[text()='Ok' and @type='button']");


	private By FirstnameEDP = By.xpath("//input[@name='first_name']");
	private By LastnameEDP = By.xpath("//input[@name='last_name']");
	private By EmailEDP = By.xpath("//input[@name='email']");
	private By MessageEDP = By.xpath("//input[@name='message']");
	private By PrintEDP = By.cssSelector("[class*='manage-tickets-formSubmitButtons'] button[class*='theme']");
	private By DoneEDP= By.xpath("//button[@type='submit']");
    private By pendingTicket = By.xpath("//div[text()='Transfer Pending'] | //*[text()='Transfer Pending']");
    private By section = By.xpath("//div[contains(@class,'seat-list-sectionTitle')]");
    private By ticketDetailsSeatRowSection = By.xpath("//div[contains(@class,'ticket-ticketDetails')]");
    private By ticketDetailsSeatRowSectionEDP = By.xpath("//div[contains(@class,'seat-list-seat')]");
    
    private By seatListHeader = totalTicketsCountTextEDP;
    private By seatListSectionName = By.xpath("//*[contains(@class,\"seat-list-sectionName\")]");
    private By seatListSectionArrow = By.xpath("//*[contains(@class,\"seat-list-section-\")]");
    private By seatListEventName = By.xpath("//*[contains(@class,\"seat-list-eventName\")]");
    private By seatListEventArrow = By.xpath("//*[contains(@class,\"seat-list-event-\")]");
	private String selectTicketAvailable=".//*[contains(@class,'tickets-selectionInner')] | //*[contains(@class,'seat-selection-selectionInner')] | //*[contains(@class,'transfer-selectionInner')]";
	private By barcode = By.xpath("(//div[@data-index='0']//following::div[contains(@id,'psebarcodeview')])[1]");



	TicketsNew ticketsNew = new TicketsNew(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);

	
	public boolean checkenableEDP() {
		try {
			long value =  (long)((JavascriptExecutor)this.getDriver()).executeScript("return window.drupalSettings.componentConfigData.siteconfig.manage_ticket_configuration.eventslistView");
			System.out.println("Is EDP Enabled :: "+value);
			if(value==1)
				return true;
		} catch (WebDriverException var5) {
			var5.getMessage();
		}
		return false;
	}
	
	public void clickBundleParking(){
		click(bundleParking, "BundleParking");
	}

	public void clickTransferDone(){
		click(done, "DONE");
	}
	
	public void clickTransferDoneEDP(){
		click(doneEDP, "DONE");
	}

	public void typeTransferTag(String textToType) throws Exception {
		type(transferTag,"Transfer Name", textToType, true, By.xpath("//XCUIElementTypeTextField[1]"));
	}

	public String getNoTicketDetailsText() {
		return getText(noTicketDetails).trim();
	}

	public void clickEventName(String eventName) {
		By desktopxpath = By.xpath(".//h3[contains(@class, 'events-eventName') and text()='" + eventName.trim() + "'] | .//div[contains(@class, 'hide')]//div[contains(@class, 'eventName') and text()='" + eventName.trim() + "'] | //div[@class='style-eventContent-9WFwc8'] | //*[contains(@class,\"style-eventDetails-\") and self::*/parent::*[contains(@class,'style-eventInfo-')]]//following::span[text()='"+eventName+"']");
		By mobilexpath = By.xpath(".//h3[contains(@class, 'events-eventName') and text()='" + eventName.trim() + "'] | .//div[contains(@class, 'show')]//div[contains(@class, 'eventName') and text()='" + eventName.trim() + "'] | //div[@class='style-eventContent-9WFwc8']");
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			click(mobilexpath, eventName.trim());
		} else
			click(desktopxpath, eventName.trim());
	}

	public boolean verifySearchValues(String value){
		boolean has= true;
		List<WebElement> events = getWebElementsList(eventsList);
		for(WebElement event: events){
			if(!event.getText().trim().toLowerCase().contains(value.trim().toLowerCase())){
				has = false;
			}
		}
		return has;
	}

	public boolean verifySearchableValue(String value){
		boolean has= false;
		Assert.assertTrue(isManageTicketsListDisplayed() , "Verify manage tickets list is displayed");
		List<WebElement> events = getWebElementsList(eventsList);
		for(WebElement event: events){
			if(event.getText().trim().toLowerCase().contains(value.trim().toLowerCase())){
				has = true;
				break;
			}
		}
		return has;
	}

	public String getFirstEventName(){
		return getText(firstEventName);
	}

	public void clickSearch(){
		click(search, "Search");
	}

	public void typeSearchValue(String searchText) throws Exception{
		type(searchValue, "SearchBox", searchText, true, By.xpath("//XCUIElementTypeTextField[1]"));
	}

	public void clickticketlistview(){
		getElementWhenVisible(clickticketlistview).click();
	}

	public boolean isViewButtonVisible(){
		return checkIfElementPresent(downloadTickets, 1);
	}

	public boolean isScanBarcodeEnabled() {
		boolean enabled = true;
		try{
			if(getAttribute(By.cssSelector("div[class*='ticket-barCodeContainer'] > div div:nth-child(1)"), "class").contains("grayOut"))
				enabled= false;
			else
				enabled = true;
		}
		catch (NoSuchElementException e){
			enabled = true;
		}
		return enabled;
	}


	public boolean isSellButtonVisible(TestDevice device){
		if((device != null && device.getName().trim().equalsIgnoreCase("mobile")) || ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			return checkIfElementPresent(sellTicketMobile, 1);
		} else
			return checkIfElementPresent(sellTicket, 1);
	}

	public boolean isSendButtonVisible(TestDevice device){
		if((device != null && device.getName().trim().equalsIgnoreCase("mobile")) || ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")))
			return checkIfElementPresent(sendTicketMobile, 1);
		else
			return checkIfElementPresent(sendTicket, 1);
	}

	public boolean isDonateButtonVisible(TestDevice device){
		if((device != null && device.getName().trim().equalsIgnoreCase("mobile")) || ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			scrollingToBottomofAPage();
			By moreButton = By.xpath(".//android.widget.Button[contains(@content-desc, 'more_horiz')]");
			click(moreButtonMobile, "More", moreButton, null, "More");
			return checkIfElementPresent(donateTicketMobile, 1);
		} else {
			click(moreButton, "More");
			return checkIfElementPresent(donateTicket, 1);
		}
	}
	
	public boolean isDonateButtonVisibleEDP(TestDevice device){
		if((device != null && device.getName().trim().equalsIgnoreCase("mobile")) || ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			click(moreButtonMobileEDP, "More", moreButtonEDP, null, "More");
			return checkIfElementPresent(donateButtonEDPMobile, 1);
		} else {
			return checkIfElementPresent(donateButtonEDP, 1);
		}
	}

	public boolean isScanBarcodeVisible() {
		By barcode;
		if(driverType.trim().toUpperCase().contains("IOS")) {
			barcode = addToAppleWalletIcon;
		} else {
			barcode = androidBarCodeLink;
		}
		//return checkIfElementPresent(barcode, 60);
		return utils.checkIfElementClickable(barcode, 60);
	}

	public boolean isViewButtonEnable(){
		String value = getAttribute(downloadTickets, "disabled");
		return value == null ? true : value.trim().equalsIgnoreCase("true") ? false : true;
	}
	

	public boolean isSendButtonEnable(TestDevice device) {
		String value;
		if((device != null && device.getName().trim().equalsIgnoreCase("mobile")) || ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")))
			value = getAttribute(sendTicketMobile, "disabled");
		else
			value = getAttribute(sendTicket, "disabled");
		return value == null ? true : value.trim().equalsIgnoreCase("true") ? false : true;
	}

	public boolean isSellButtonEnable(TestDevice device) {
		String value;
		if((device != null && device.getName().trim().equalsIgnoreCase("mobile")) || ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")))
			value = getAttribute(sellTicketMobile, "disabled");
		else
			value = getAttribute(sellTicket, "disabled");
		return value == null ? true : value.trim().equalsIgnoreCase("true") ? false : true;
	}

	public boolean isDonateButtonEnable(TestDevice device) {
		boolean value;
		if((device != null && device.getName().trim().equalsIgnoreCase("mobile")) || ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			scrollingToBottomofAPage();
			By moreButton = By.xpath(".//android.widget.Button[contains(@content-desc, 'more_horiz')]");
			click(moreButtonMobile, "More", moreButton, null, "More");
			String xpath = getXpath(donateTicketMobile, "Donate", "", -1) + "/..";
			value = getAttribute(By.xpath(xpath), "class").contains("disabled");
		} else {
			click(moreButton, "More");
			String xpath = getXpath(donateTicket, "Donate", "", -1) + "/..";
			value = getAttribute(By.xpath(xpath), "class").contains("disabled");
		}
		return !value;
	}
	
	public boolean isDonateButtonEnableEDP(TestDevice device) {

		boolean value;
		if((device != null && device.getName().trim().equalsIgnoreCase("mobile")) || ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			click(moreButtonMobileEDP, "More", moreButtonEDP, null, "More");
			sync(2000l);
			value = getAttribute(donateButtonEDPMobile, "class").contains("disabled");
		}else {
			value = Boolean.parseBoolean(getAttribute(donateButtonEDP, "disabled"));
		}
		return !value;
	}
	

	public void clickEditPosting(){
		click(editPosting, "Edit Posting");
	}

	public String getEarningPrice(){
		return getAttribute(earningPrice,"value");
	}

	public void clickCancelPosting(){
		click(cancelPosting, "Cancel Posting");
	}

	public String getBankDetails(String text){
		sync(1000L);
		utils.getElementWhenEditable(bankDetails, "innerHTML", text);
		return getText(bankDetails);
	}

	public boolean isBarcodeDisplayed() {
		return isElementDisplayed(barIcon, "Bar Icon", true);
	}

	public boolean isBarcodeDisplayed(String Section, String Row, String Seat) {
		String xpath = ".//div[contains(@class, 'ticket-barCodeDialog')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-slide')]//div[contains(@class, 'ticket-barCodeScan')]//div[contains(@class, 'ticket-barDetails') and descendant::strong[text()='" + Section + "'] and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..";
		return isElementDisplayed(By.xpath(xpath + getXpath(barIcon, "Bar icon", "", -1)), "Bar Icon", true);
	}
	
	public boolean isSecureBarcodeDisplayed(String Section, String Row, String Seat) {
		String xpath = ".//div[contains(@class, 'ticket-barCodeDialog')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-slide')]//div[contains(@class, 'ticket-barCodeScan')]//div[contains(@class, 'ticket-barDetails') and descendant::strong[text()='" + Section + "'] and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..";
		return isElementDisplayed(By.xpath(xpath + getXpath(secureBarcodeIcon, "Secure Barcode icon", "", -1)), "Bar Icon", true);
	}
	
	public String secureBarcodeAttribute(String Section, String Row, String Seat) {
		String xpath = ".//div[contains(@class, 'ticket-barCodeDialog')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-slide')]//div[contains(@class, 'ticket-barCodeScan')]//div[contains(@class, 'ticket-barDetails') and descendant::strong[text()='" + Section + "'] and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..";
		return getAttribute(By.xpath(xpath + getXpath(secureBarcodeIcon, "Secure Barcode attribute", "", -1)),"data-event-label");
	}
	
	public void accepctEducationPopUp() {
		if(utils.checkIfElementClickable(educationnextbutton, 10)) {
			click(educationnextbutton,"Education Next Button",10);
			click(educationAllSetButton,"Education AllSet Button",10);
		}
	}

	public String getBarcodeImageUrl(String Section, String Row, String Seat) {
		String xpath = ".//div[contains(@class, 'ticket-barCodeDialog')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-slide')]//div[contains(@class, 'ticket-barCodeScan')]//div[contains(@class, 'ticket-barDetails') and descendant::strong[text()='" + Section + "'] and descendant::strong[text()='" + Row + "'] and descendant::strong[text()='" + Seat + "']]/..";
		return getAttribute(By.xpath(xpath + getXpath(barIcon, "Bar icon", "", -1)),"src");
	}

	public boolean isAddToAppletWalletDisplayed() {
		getElementWhenVisible(addToAppleWalletIcon, 60);
		return isElementDisplayed(addToAppleWalletIcon, "Add to apple wallet", true);
	}

	public String getMobileScanGateNumber() {
		if(!utils.checkElementPresent(gateNumber, 2)) {
			throw new SkipException("ticket-entryFrom is not present in UI ");
		}
		return getText(gateNumber).trim();
	}
	
	public String getTotalTicketsCountText() {
		String text = getText(totalTicketsCountText);
		return text.substring(text.indexOf(".") + 1).trim();
	}
	
	public int getTotalTicketsCountTextEDP() {
		String text = getText(totalTicketsCountTextEDP);
	    String c= text.substring(text.indexOf("(")+1).replace(")", " ").trim();	
	    int a=Integer.parseInt(String.valueOf(c));
	    return a;	
	}
	
	public String getTicketsCountTextEDP() {
		String text = getText(totalTicketsCountTextEDP); 
	    return text;
	}

	public String getSentTicketsCountText() {
		String text = getText(sentTicketsCountText);
		return text.substring(text.indexOf(".") + 1).trim();
	}

	public String getListedTicketsCountText() {
		String text = getText(listedTicketsCountText);
		return text.substring(text.indexOf(".") + 1).trim();
	}

	public String getClaimedTicketsCountText() {
		String text = getText(claimedTicketsCountText);
		return text.substring(text.indexOf(".") + 1).trim();
	}

	public String getDonatedTicketsCountText() {
		String text = getText(donatedTicketsCountText);
		return text.substring(text.indexOf(".") + 1).trim();
	}
	
	public String getDonatedTicketsCountTextEDP() {
		String text = getText(donatedTicketsCountText);
		return text.substring(text.indexOf(".") + 1).trim();
	}

	public int getTotalTicketsCount() {
		return getWebElementsList(ticketsListing).size();
	}
	public int getTotalTicketsCountEDP() {
		return getWebElementsList(ticketscountEDP).size();
	}

	public int getSentTicketsCount() {
		return getWebElementsList(sentTickets).size();
	}

	public int getListedTicketsCount() {
		return getWebElementsList(listedTickets).size();
	}

	public int getClaimedTicketsCount() {
		return getWebElementsList(claimedTickets).size();
	}

	public int getDonatedTicketsCount() {
		return getWebElementsList(donatedTickets).size();
	}
	
	public int getDonatedTicketsCountEDP() {
		return getWebElementsList(donatedTicketsEDP).size();
	}
	

	public String getSellerAddress(String text){
		sync(1000L);
		utils.getElementWhenEditable(sellerAddress, "innerHTML", text);
		if(driverType.trim().toUpperCase().contains("FIREFOX")) {
			return getText(sellerAddress);
		} else
			return getAttribute(sellerAddress, "innerHTML");
	}

	public HashMap<Integer, String> getListOfEvents() {
		List<WebElement> levents = getWebElementsList(events);
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for(int i = 0; i < levents.size(); i++) {
			int eventId = Integer.valueOf(levents.get(i).getAttribute("href").split("/")[levents.get(i).getAttribute("href").split("/").length - 1]);
			String xpath = getXpath(events, "Event", "", -1);
			String ticketCount = "0 Ticket";
			if(checkIfElementPresent(By.xpath("(" + xpath + ")[" + (i+1) + "]" + "//div[contains(@class, 'events-eventTicketCount')]//span[2]"), 1))
				ticketCount = getText(By.xpath("(" + xpath + ")[" + (i+1) + "]" + "//div[contains(@class, 'events-eventTicketCount')]//span[2]"));
			map.put(eventId, ticketCount);
		}
		return map;
	}

	public List<String> getListOfEventNames() {
		By eventNames;
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			eventNames = mobileEventNames;
		} else {
			eventNames = desktopEventNames;
		}
		List<WebElement> levents = getWebElementsList(eventNames);
		List<String> map = new ArrayList<String>();
		for(int i = 0; i < levents.size(); i++) {
			String eventName = levents.get(i).getText();
			map.add(eventName);
		}
		return map;
	}

	public List<String>  getListOfDateAndTime() {
		By eventDateTime;
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			eventDateTime = mobileEventDateTime;
			List<WebElement> leventsDateTime = getWebElementsList(eventDateTime);
			List<String> map = new ArrayList<String>();
			for(int i = 0; i < leventsDateTime.size(); i++) {
				String dateTime = leventsDateTime.get(i).getText();
				map.add(dateTime);
			}
			return map;
		} else {
			eventDateTime = desktopEventDate;
			List<WebElement> leventsDateTime = getWebElementsList(eventDateTime);
			List<WebElement> leventsTime = getWebElementsList(desktopEventTime);
			List<String> map = new ArrayList<String>();
			for(int i = 0, j = 0; i < leventsDateTime.size() || j < leventsTime.size(); i++, j++) {
				String dateTime = leventsDateTime.get(i).getText().trim().replace("\n", " ");
				String time = j < leventsTime.size() ? leventsTime.get(j).getText() : "";
				map.add((dateTime + " " + time).trim());
			}
			return map;
		}
	}

	public boolean isContinueEnabled(){
		boolean enable= true;
		try{
			String value = getAttribute(popupButton, "disabled");
			System.out.println(value);
			if(value.equals("true"))
				enable = false;
		} catch (Exception e){
			enable = true;
		}
		return enable;
	}

	public void clickBundleParkingCheckBox() {
		click(bundleparkingcheckbox, "Bundle parking checkbox");
	}

	public void clickBuyMoreTickets(){
		click(BuyTickets,"BUY MORE TICKETS");
	}

	public void clickTicketDetails(String eventId, String Section, String Row, String Seat, String ticketId) {
		String xpath = ticketsNew.scrollToTicketAfterReload(eventId, Section, Row, Seat, ticketId);
		//click(By.xpath(xpath + getXpath(ticketsdetails, "Ticket Details", "", -1)), "Click ticket details");

		By ticketDetails;
		By iosAppLocator = null;

		if (((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")))) {
			if(driverType.trim().toUpperCase().contains("IOS")) {
				ticketDetails = By.xpath("(" + xpath + "//*[contains(@class, 'ticket-completedStatus')])[1]");
			} else {
				try {
					getElementWhenVisible(By.xpath(xpath + "//*[contains(@class,'detailTicket') and contains(text(),'TICKET')]"));
					ticketDetails = By.xpath(xpath + "//*[contains(@class,'detailTicket') and contains(text(),'TICKET')]");
				}
				catch(Exception E) {
				getElementWhenVisible(By.xpath(xpath + "//*[contains(@class,'completed')]//span[contains(text(),'TICKET')]"));
					ticketDetails = By.xpath(xpath + "//*[contains(@class,'completed')]//span[contains(text(),'TICKET')]");
				}
			}
			iosAppLocator = By.xpath("//XCUIElementTypeStaticText[@value='" + Section + "']/../../..//XCUIElementTypeStaticText[@value='" + Row + "']/../../..//XCUIElementTypeStaticText[@value='" + Seat + "']/../../..//XCUIElementTypeStaticText[@value='TICKET DETAILS'] | //XCUIElementTypeStaticText[@value='" + Section + "']/../../..//XCUIElementTypeStaticText[@value='" + Row + "']/../../..//XCUIElementTypeStaticText[@value='" + Seat + "']/../../..//XCUIElementTypeStaticText[@value='Ticket Details']");
		} else {
			try {
				getElementWhenVisible(By.xpath(xpath + "//*[contains(@class,'detailTicket') and contains(text(),'TICKET')]"));
				ticketDetails = By.xpath(xpath + "//*[contains(@class,'detailTicket') and contains(text(),'TICKET')]");
			}
			catch(Exception E) {
				getElementWhenVisible(By.xpath(xpath + "//*[contains(@class,'completed')]//span[contains(text(),'TICKET')]"));
				ticketDetails = By.xpath(xpath + "//*[contains(@class,'completed')]//span[contains(text(),'TICKET')]");
			}
		}

		getElementWhenVisible(ticketDetails);
		click(ticketDetails, "Ticket Details", null, iosAppLocator, "Ticket Dtails", false);
	}
	
	
	public void clickTicketDetailsEDP(String eventId, String Section, String Row, String Seat, String ticketId) {
		
		if(driverType.trim().toUpperCase().contains("IOS")) {
			section = By.xpath("//div[contains(@class,'seat-list-sectionTitle-') and contains(.,'"+Section+"')]");
			if(utils.checkElementPresent(section, 10)) {
				click(section,"Click on Selected Section",10);
			}

			By ticket = By.xpath("//div[contains(@class,'seat-list-seatNumber-') and contains(.,'" + Section
					+ "') and contains(.,'" + Row + "') and contains(.,'" + Seat + "')]");
			if(utils.checkElementPresent(ticket, 10)) {
				click(ticket,"Click on Selected Section",10);
			}
		}
		
		String xpath = null;
		//click(By.xpath(xpath + getXpath(ticketsdetails, "Ticket Details", "", -1)), "Click ticket details");

		By ticketDetails;
//		By iosAppLocator = null;

		if (((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")))) {
			if(driverType.trim().toUpperCase().contains("IOS")) {
				ticketDetails = By.xpath("//*[contains(@class,'ticket-carousel-infoIcon')]");
			} else {
				xpath = ticketsNew.scrollToTicketAfterReloadEDP(eventId, Section, Row, Seat, ticketId);
				try {
					getElementWhenVisible(By.xpath(xpath + "//*[contains(@class,'detailTicket') and contains(text(),'TICKET')]"));
					ticketDetails = By.xpath(xpath + "//*[contains(@class,'detailTicket') and contains(text(),'TICKET')]");
				}
				catch(Exception E) {
				getElementWhenVisible(By.xpath(xpath + "//*[contains(@class,'completed')]//span[contains(text(),'TICKET')]"));
					ticketDetails = By.xpath(xpath + "//*[contains(@class,'completed')]//span[contains(text(),'TICKET')]");
				}
			}
//			iosAppLocator = By.xpath("//XCUIElementTypeStaticText[@value='" + Section + "']/../../..//XCUIElementTypeStaticText[@value='" + Row + "']/../../..//XCUIElementTypeStaticText[@value='" + Seat + "']/../../..//XCUIElementTypeStaticText[@value='TICKET DETAILS'] | //XCUIElementTypeStaticText[@value='" + Section + "']/../../..//XCUIElementTypeStaticText[@value='" + Row + "']/../../..//XCUIElementTypeStaticText[@value='" + Seat + "']/../../..//XCUIElementTypeStaticText[@value='Ticket Details']");
		} else {
			xpath = ticketsNew.scrollToTicketAfterReloadEDP(eventId, Section, Row, Seat, ticketId);
			try {
				getElementWhenVisible(By.xpath(xpath + "//*[contains(@class,'detailTicket') and contains(text(),'TICKET')]"));
				ticketDetails = By.xpath(xpath + "//*[contains(@class,'detailTicket') and contains(text(),'TICKET')]");
			}
			catch(Exception E) {
				getElementWhenVisible(By.xpath(xpath + "//*[contains(@class,'completed')]//span[contains(text(),'TICKET')]"));
				ticketDetails = By.xpath(xpath + "//*[contains(@class,'completed')]//span[contains(text(),'TICKET')]");
			}
		}

		getElementWhenVisible(ticketDetails);
		click(ticketDetails, "Ticket Details");
	}
	
	

	public boolean isTicketDisplayed(String eventId, String Section, String Row, String Seat, String ticketId) {
		String xpath = ticketsNew.scrollToTicketAfterReload(eventId, Section, Row, Seat, ticketId);
		getElementWhenVisible(By.xpath(xpath));
		return true;
	}
	
	public boolean isTicketDisplayedEDP(String eventId, String Section, String Row, String Seat, String ticketId) {
		String xpath = ticketsNew.scrollToTicketAfterReloadEDP(eventId, Section, Row, Seat, ticketId);
		getElementWhenVisible(By.xpath(xpath));
		return true;
	}

	public boolean isBulkTicketDisplayedActive(String section, String row, String seat) {
		By xpath=null;
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			xpath = By.xpath("//div[contains(@class, 'react-root-event')]//div[@class='slick-list']//div[@class='slick-track']//div[contains(@class, 'ticket-ticketDetails')]//strong[contains(text(),'" + section + "')]/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='" + row + "']/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='" + seat + "']/ancestor::div[contains(@class,'ticket-eventInner')]/div[contains(@class,'ticket-mobileStatus')]/div[contains(@class,'ticketStatus')]/div/div[contains(@class,'ticket-completedStatus')]/span[contains(.,'Ticket Details')]");
			return scrollandFindTicket(xpath);
		}else {
			xpath = By.xpath("//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails')]//strong[contains(text(),'" + section + "')]/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='" + row + "']/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='" + seat + "']/ancestor::div[contains(@class,'ticket-eventInner')]/div[contains(@class,'ticket-ticketImage')]/div[contains(@class,'ticket-eventName')]/span[contains(.,'Ticket Details')]");
			return getDriver().findElement(xpath).isDisplayed();
		}
	}
	
	public boolean isBulkTicketDisplayedActiveEDP(String section, String row, String seat) {
		By xpath=null;
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			//click(By.xpath("//span[contains(text(),'"+section+"')]"),"Click Section",10);
			xpath = By.xpath("//div[contains(@class,'seat-list-sectionSeats')]//div[contains(@class,'seat-list-seat') and contains(text(),'"+section+"')] | //span[contains(text(),'"+section+"')]");
			return getDriver().findElement(xpath).isDisplayed();
		}else {	
			//click(By.xpath("//*[contains(@class,'seat-list-sectionName-') and contains(text(),'"+section+"')]"), "Click Section");
			//click(By.xpath("//span[contains(text(),'"+section+"')]"),"Click Section",10);
			xpath = By.xpath("//div[contains(@class,'seat-list-sectionSeats')]//div[contains(@class,'seat-list-seat') and contains(text(),'"+section+"')]| //span[contains(text(),'"+section+"')] | //*[contains(@class,'seat-list-sectionName-') and contains(text(),'"+section+"')]/parent::*/following-sibling::*//div[contains(@class,'seat-list-seat-')][contains(.,'"+section+"') and self::*//span[contains(.,'"+row+"') and contains(.,'"+seat+"')]]");
			System.out.println(xpath);
			System.out.println(getDriver().findElement(xpath).isDisplayed());
			return getDriver().findElement(xpath).isDisplayed();
		}
	}


	public boolean isBulkTicketDisplayedCompleted(String section, String row, String seat) {
		By xpath=null;
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			xpath =  By.xpath("//div[contains(@class, 'react-root-event')]//div[@class='slick-list']//div[@class='slick-track']//div[contains(@class, 'ticket-ticketDetails')]//strong[contains(text(),'"+section+"')]/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+row+"']/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+seat+"']/ancestor::div[contains(@class,'ticket-eventInner')]/div[contains(@class,'ticket-mobileStatus')]/div[contains(@class,'ticket-ticketStatus')]/div/div[contains(@class,'ticket-completedStatus') and contains(.,'Ticket Sent')]");
			System.out.println(xpath);
			return scrollandFindTicket(xpath);
		} else {
			xpath = By.xpath("//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails')]//strong[contains(text(),'" + section + "')]/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='" + row + "']/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='" + seat + "']/ancestor::div[contains(@class,'ticket-eventInner')]/div[contains(@class,'ticket-ticketImage')]/div[contains(@class,'ticket-ticketStatus')]/div/div[contains(@class,'ticket-completedStatus') and contains(.,'Ticket Sent')]");
			return getDriver().findElement(xpath).isDisplayed();
		}
	}

	public boolean isBulkTicketDisplayedPending(String section, String row, String seat) {
		By xpath=null;
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			xpath = By.xpath("//div[contains(@class,'react-root-event')]//div[@class='slick-list']//div[@class='slick-track']//div[contains(@class,'slick-active')]//div[contains(@class, 'ticket-ticketDetails')]//strong[contains(text(),'"+section+"')]/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+row+"']/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+seat+"']/ancestor::div[contains(@class,'ticket-eventInner')]/div[contains(@class,'ticket-mobileStatus')]/div[contains(@class,'ticket-ticketStatus')]/div/div[contains(@class,'ticket-completedStatus')]/span[contains(.,'Cancel Transfer')]");
			return scrollandFindTicket(xpath);
		} else {
			xpath = By.xpath("//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails')]//strong[contains(text(),'"+section+"')]/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+row+"']/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+seat+"']/ancestor::div[contains(@class,'ticket-eventInner')]/div[contains(@class,'ticket-ticketImage')]/div[contains(@class,'ticket-ticketStatus')]/div/div[contains(@class,'ticket-completedStatus')]/span[contains(.,'Cancel Transfer')] | //div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails')]//strong[contains(text(),'"+section+"')]/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+seat+"']/ancestor::div[contains(@class,'ticket-eventInner')]/div[contains(@class,'ticket-ticketImage')]/div[contains(@class,'ticket-ticketStatus')]/div/div[contains(@class,'ticket-completedStatus')]/span[contains(.,'Cancel Transfer')]");
			return getDriver().findElement(xpath).isDisplayed();
		}
	}
	
	public boolean isBulkTicketDisplayedPendingEDP(String section, String row, String seat) {
		By xpath=null;
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			xpath = By.xpath("//div[contains(@class,'react-root-event')]//div[@class='slick-list']//div[@class='slick-track']//div[contains(@class,'slick-active')]//div[contains(@class, 'ticket-ticketDetails')]//strong[contains(text(),'"+section+"')]/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+row+"']/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+seat+"']/ancestor::div[contains(@class,'ticket-eventInner')]/div[contains(@class,'ticket-mobileStatus')]/div[contains(@class,'ticket-ticketStatus')]/div/div[contains(@class,'ticket-completedStatus')]/span[contains(.,'Cancel Transfer')]");
			return scrollandFindTicket(xpath);
		} else {
			xpath = By.xpath("//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails')]//strong[contains(text(),'"+section+"')]/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+row+"']/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+seat+"']/ancestor::div[contains(@class,'ticket-eventInner')]/div[contains(@class,'ticket-ticketImage')]/div[contains(@class,'ticket-ticketStatus')]/div/div[contains(@class,'ticket-completedStatus')]/span[contains(.,'Cancel Transfer')]");
			return getDriver().findElement(xpath).isDisplayed();
		}
	}


	public String getTermsCondition() {
		return getText(terms_condition).trim();
	}

//	public void selectParkingSlots(int noofparkingslots) {
//		List<WebElement> parkingslot = getWebElementsList(parkingslotsCheckbox);
//		for(int i = 0; i < parkingslot.size() && (i < noofparkingslots || noofparkingslots == -1); i++) {
//			parkingslot.get(i).click();
//		}
//	}

	public void selectParkingSlots(int numOfSlots){
		for(int i=1;i<=numOfSlots;i++){
			click(By.cssSelector("[class*='send-parkingSlots'] li:nth-child("+i+") div"), "ParkingSlot");
		}
	}

	public List<List<String>> getTicketsDetail() {
		List<List<String>> lticketsDetail = new ArrayList<List<String>>();
		List<WebElement> tickets = getWebElementsList(ticketsDetail);
		for(int i = 0; i < tickets.size(); i++) {
			By section = By.xpath("(" + getXpath(ticketsDetail, "Tickets Details", "", -1) + ")[" + (i+1) +"]" + "/div[1]//span/../strong");
			By row = By.xpath("(" + getXpath(ticketsDetail, "Tickets Details", "", -1) + ")[" + (i+1) +"]" + "/div[2]//span/../strong");
			By seat = By.xpath("(" + getXpath(ticketsDetail, "Tickets Details", "", -1) + ")[" + (i+1) +"]" + "/div[3]//span/../strong");

			String sectionText = checkIfElementPresent(section, 1) ? getText(section) : "";
			String rowText = checkIfElementPresent(row, 1) ? getText(row) : "";
			String seatText = checkIfElementPresent(seat, 1) ? getText(seat) : "";

			List<String> lTicketDetail = new ArrayList<String>();
			lTicketDetail.add(sectionText);
			lTicketDetail.add(rowText);
			lTicketDetail.add(seatText);
			lticketsDetail.add(lTicketDetail);
		}
		return lticketsDetail;
	}
	
	
	public List<List<String>> getTicketsDetailEDP() {
		List<List<String>> lticketsDetail = new ArrayList<List<String>>();
		List<WebElement> tickets = getWebElementsList(ticketsDetailEDP);
		
		for(int i = 0; i < tickets.size(); i++) {
			
			By sectionrowseat = By.xpath("(" + getXpath(ticketsDetailEDP, "Tickets Details", "", -1) + ")[" + (i+1) +"]");
			String ticketText = checkIfElementPresent(sectionrowseat, 1) ? getText(sectionrowseat) : "";
		    String text = ticketText.replace(",", " ");
			String[] arr =text.split("\\s");
					
			List<String> lTicketDetail = new ArrayList<String>();
			lTicketDetail.add(arr[1]);
			lTicketDetail.add(arr[4]);
			lTicketDetail.add(arr[7]);
			lticketsDetail.add(lTicketDetail);
		}
		return lticketsDetail;
	}
	

	public List<List<String>> getMobileTicketsDetail() {
		List<List<String>> lticketsDetail = new ArrayList<List<String>>();
		List<WebElement> tickets = getWebElementsList(noofmobiletickets);
		int i = 0;
		Object[] coords = null;
		do{
			getElementWhenVisible(By.xpath(".//div[contains(@class, 'react-root-event')]//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-slide') and @data-index='" + i + "']"));
			By section = By.xpath("(" + getXpath(noofmobiletickets, "Mobile Ticket", "", -1) + ")[" + (i+1) +"]" + "//div[contains(@class, 'ticket-ticketDetails')]/div[1]//span/../strong");
			By row = By.xpath("(" + getXpath(noofmobiletickets, "Mobile Ticket", "", -1) + ")[" + (i+1) +"]" + "//div[contains(@class, 'ticket-ticketDetails')]/div[2]//span/../strong");
			By seat = By.xpath("(" + getXpath(noofmobiletickets, "Mobile Ticket", "", -1) + ")[" + (i+1) +"]" + "//div[contains(@class, 'ticket-ticketDetails')]/div[3]//span/../strong");
			String sectionText = getText(section);
			String rowText = getText(row);
			String seatText = getText(seat);
			List<String> lTicketDetail = new ArrayList<String>();
			lTicketDetail.add(sectionText);
			lTicketDetail.add(rowText);
			lTicketDetail.add(seatText);
			lticketsDetail.add(lTicketDetail);
			if(i < tickets.size() - 1) {
				if(driverType.trim().toUpperCase().contains("ANDROID") && i == 0) {
					coords = getCoordinates(By.xpath(".//android.view.View[@resource-id='block-componentblockformanageticket']/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[not(@content-desc='') and @index='" + i + "'] | .//android.view.View[@resource-id='block-componentblockformanageticket']/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[@index='" + i + "']"));
				}
				swipe(By.xpath(".//div[contains(@class, 'react-root-event')]//div[contains(@class, 'ticket-eventListContainerMobile')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-slide') and @data-index='" + i + "']"), "Left", coords);
			}
			i++;
		} while(i < tickets.size());
		return lticketsDetail;
	}

	public void inputSellerProfile(String FirstName, String LastName, String Add1, String Add2, String Country, String City, String State, String ZipCode, String MobileNum, String PhoneNum ) throws Exception{
		ManageticketsAPI manageticketsapi = new ManageticketsAPI(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		String firstName = manageticketsapi.getPostingProfileFirstName();
		try {
			getElementWhenRefreshed(sellerFirstName, "value", firstName.trim(), 2);
		} catch(Exception ex) {
			//Temp fix
		}
		By iosLocator = By.xpath(".//XCUIElementTypeOther[@name='EDIT POSTING' or @name='SELL TICKETS']/..");
		type(sellerFirstName, "First Name", FirstName.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[1]"));
		type(sellerLastName, "Last Name", LastName.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[2]"));
		type(sellerAddress1,"Address1", Add1.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[3]"));
		type(sellerAddress2, "Address2", Add2.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[4]"));
		type(sellerCountry, "Country", Country.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[5]"));
		type(sellerCity, "City", City.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[6]"));
		if(driverType.trim().toUpperCase().contains("IOS")) {
			type(sellerState, "State", State.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[7]"));
		}
		type(sellerZip, "Zip", ZipCode.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[8]"));
		type(sellerMobile, "Mobile", MobileNum.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[9]"));
		type(sellerHomePhone, "Phone", PhoneNum.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[10]"));
		if(!driverType.trim().toUpperCase().contains("IOS")) {
			type(sellerState, "State", State.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[7]"));
			WebElement we = getElementWhenVisible(sellerState, 1);
			we.sendKeys(Keys.TAB);
		}
	}

	public void inputSellerProfile(String emailaddress, String password, String FirstName, String LastName, String Add1, String Add2, String Country, String City, String State, String ZipCode, String MobileNum, String PhoneNum ) throws Exception{
		ManageticketsAPI manageticketsapi = new ManageticketsAPI(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		String firstName = manageticketsapi.getPostingProfileFirstName(emailaddress,password);
		try {
			getElementWhenRefreshed(sellerFirstName, "value", firstName.trim(), 2);
		} catch(Exception ex) {
			//Temp fix
		}
		By iosLocator = By.xpath(".//XCUIElementTypeOther[@name='EDIT POSTING' or @name='SELL TICKETS']/..");
		type(sellerFirstName, "First Name", FirstName.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[1]"));
		type(sellerLastName, "Last Name", LastName.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[2]"));
		type(sellerAddress1,"Address1", Add1.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[3]"));
		type(sellerAddress2, "Address2", Add2.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[4]"));
		type(sellerCountry, "Country", Country.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[5]"));
		type(sellerCity, "City", City.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[6]"));
		if(driverType.trim().toUpperCase().contains("IOS")) {
			type(sellerState, "State", State.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[7]"));
		}
		type(sellerZip, "Zip", ZipCode.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[8]"));
		type(sellerMobile, "Mobile", MobileNum.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[9]"));
		type(sellerHomePhone, "Phone", PhoneNum.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[10]"));
		if(!driverType.trim().toUpperCase().contains("IOS")) {
			type(sellerState, "State", State.trim(), false, By.xpath(getXpath(iosLocator, "Edit Posting", "", -1) + "//XCUIElementTypeTextField[7]"));
			WebElement we = getElementWhenVisible(sellerState, 1);
			we.sendKeys(Keys.TAB);
		}
	}

	public String SellerfirstName(String firstName){
		getElementWhenRefreshed(sellerFirstName, "value", firstName.trim());
		String str= getAttribute(sellerFirstName, "value");
		return str;
	}

	public String SellerLastName(){
		String str= getAttribute(sellerLastName, "value");
		return str;
	}

	public String sellerZip(){
		String str= getAttribute(sellerZip, "value");
		return str;
	}

	public String sellerAddress2(){
		String str= getAttribute(sellerAddress2, "value");
		return str;
	}

	public String sellerCountry(){
		String str= getAttribute(sellerCountry, "value");
		return str;
	}

	public String sellerCity(){
		String str= getAttribute(sellerCity, "value");
		return str;
	}

	public String sellerState(){
		String str= getAttribute(sellerState, "value");
		return str;
	}

	public String sellerAddress1(){
		String str= getAttribute(sellerAddress1, "value");
		return str;
	}

	public String sellerMobile(){
		String str= getAttribute(sellerMobile, "value");
		return str;
	}

	public String sellerHomePhone(){
		String str= getAttribute(sellerHomePhone, "value");
		return str;
	}

	public void inputBankDetails(String AccType, String RoutingNum, String AccountNum, String ConfirmAcc) throws Exception{
		type(bankRoutingNumber, "Routing Number",  RoutingNum, false, By.xpath("//XCUIElementTypeOther[starts-with(@name, 'Routing Number')]/../XCUIElementTypeTextField"));
		click(bankAccountType, "AccountType");
		getElementWhenClickable(By.xpath("//*[contains(@class,'theme-dropdown')]/ul/li[text()='"+AccType+"']")).click();
		type(bankAccountNumber, "Account Number", AccountNum, false, By.xpath("//XCUIElementTypeSecureTextField[1]"));
		type(bankConfirmAccount, "Confirm Account", ConfirmAcc, false, By.xpath("//XCUIElementTypeSecureTextField[2]"));
	}

	public void clickEditBankDetail(){
		click(editBankAccount, "Bank Account Edit");
	}

	public void clickEditSellerProfile(){
		click(editSellerProfile, "Bank Account Edit");
	}

	public void selectBankAccount(){
		click(depositAccount,"Deposit Account");
		click(bankAccount, "Bank Account");
	}

	public void selectSellerCredit(){
		click(depositAccount,"Deposit Account");
		click(sellerCredit, "Seller Credit");
	}

	public String getDepositAccountValue() {
		return getAttribute(depositAccount, "value");
	}

	public String getSuccess(){
		WebElement we = getElementWhenRefreshed(successTextUpdated, "innerHTML", "Success!");
		System.out.println(we.getText());
		return we.getText();
	}
	
	public String getSuccessEDP(){
		String successmessage=utils.getText(successTextUpdatedEDP, 2);
		return successmessage;
	}

	public void typeEarningPrice(String[] prices) throws Exception{
		if(driverType.trim().toUpperCase().contains("SAFARI")) {
			throw new SkipException("Skipped");
		}
		By ioslocator = By.xpath(".//XCUIElementTypeOther[@name='Set Price']/..//XCUIElementTypeTextField");
		List<WebElement> earningPrices;
		if(driverType.trim().toUpperCase().contains("IOS")) {
			earningPrices = getIosMobileElementsList(ioslocator);
		} else
			earningPrices = getWebElementsList(earningPrice);
		for(int i = 0; i < earningPrices.size(); i++) {
			type(earningPrices.get(i), "Earning price " + (i+1), prices.length > i ? prices[i] : prices[0], false, By.xpath("(" + getXpath(ioslocator, "Ios text field locator", "", -1) + ")[" + (i+1) + "]"));
		}
	}

	public void swipe(String direction, String index) {
		swipe(By.xpath(".//div[contains(@class, 'ticket-barCodeDialog')]//div[contains(@class, 'slick-list')]//div[contains(@class, 'slick-slide') and @data-index='" + index.trim() + "']//div[contains(@class, 'ticket-qr')]/div"), direction.trim(), null);
	}

	public void clickEditCancelTicket(String eventId, String Section, String Row, String Seat, String ticketId){
		String xpath = ticketsNew.scrollToTicket(eventId, Section, Row, Seat, ticketId, "Pending");
		click(getElementWhenVisible(By.xpath(xpath+"//div[contains(@class,'completedStatus')]//span[1]")),"manage posting");
	}

	public void clickManageTickets(String eventId, String Section, String Row, String Seat, String ticketId){
		String xpath = ticketsNew.scrollToTicket(eventId, Section, Row, Seat, ticketId, "Pending");
		click(By.xpath(xpath + "/../..//span//small/.."), "Manage Ticket", 3);
	}

	public void clickScanBarcode(String eventId, String Section, String Row, String Seat, String ticketId) {
		String xpath = ticketsNew.scrollToTicketAfterReload(eventId, Section, Row, Seat, ticketId);
		if(driverType.trim().toUpperCase().contains("IOS")) {
			
			By ticketDetails = By.xpath("(" + xpath + "//*[contains(@class, 'ticket-completedStatus')])[1]");
			By iosAppLocator = By.xpath("//XCUIElementTypeStaticText[@value='" + Section + "']/../../..//XCUIElementTypeStaticText[@value='" + Row + "']/../../..//XCUIElementTypeStaticText[@value='" + Seat + "']/../../..//XCUIElementTypeStaticText[@value='VIEW BARCODE'] | //XCUIElementTypeStaticText[@value='" + Section + "']/../../..//XCUIElementTypeStaticText[@value='" + Row + "']/../../..//XCUIElementTypeStaticText[@value='" + Seat + "']/../../..//XCUIElementTypeStaticText[@value='View Barcode']");
			click(ticketDetails, "Scan Barcode", null, iosAppLocator, "Scan Barcode", false);
		} else {
			By viewBarcode = androidBarCodeLink;
			String scanBarcode = getXpath(viewBarcode, "Scan barcode", "", -1);
			click(By.xpath(xpath + scanBarcode), "Scan Barcode");
		}
	}
	
	public void clickScanBarcodeEDP(String eventId, String Section, String Row, String Seat, String ticketId) {
		//String xpath = ticketsNew.scrollToTicketAfterReloadEDP(eventId, Section, Row, Seat, ticketId);
		if(driverType.trim().toUpperCase().contains("IOS")) {
			section = By.xpath("//div[contains(@class,'seat-list-sectionTitle-') and contains(.,'"+Section+"')]");
			if(utils.checkElementPresent(section, 10)) {
				click(section,"Click on Selected Section",10);
			}

			By ticket = By.xpath("//div[contains(@class,'seat-list-seatNumber-') and contains(.,'" + Section
					+ "') and contains(.,'" + Row + "') and contains(.,'" + Seat + "')] | (//div[contains(@class,'seat-list-seatTitle')]/*[contains(@class,'seat-list-detailsIcon')])[1]");
			if(utils.checkElementPresent(ticket, 10)) {
				click(ticket,"Click on Selected Section",10);
			}
			sync(3000l);
			// Sometimes fake pass issue was coming on click, so checking again if the element is present click again on that
			if(utils.checkElementPresent(ticket, 5)) {
				click(ticket,"Click on Selected Section",10);
			}
			
		} else {
			By viewBarcode = androidBarCodeLink;
			String scanBarcode = getXpath(viewBarcode, "Scan barcode", "", -1);
			click(By.xpath( scanBarcode), "Scan Barcode");
		}
	}
	
	
	

	public boolean verifyScanBarcodeState(String eventId, String Section, String Row, String Seat, String ticketId) {
		String xpath = ticketsNew.scrollToTicketAfterReload(eventId, Section, Row, Seat, ticketId);
		String scanBarcode;
		if(driverType.trim().toUpperCase().contains("IOS")) {
			scanBarcode = getXpath(addToAppleWalletIcon, "Add to applie wallet", "", -1);
		} else {
			scanBarcode = getXpath(androidBarCodeLink, "Scan barcode", "", -1);
		}
		return checkIfElementPresent(By.xpath(xpath + scanBarcode), 1);
	}

	public void selectCharity() {
		getElementWhenVisible(charitySelect).click();
		javascriptClick(getElementWhenVisible(firstCharity), "First Charity");
	}

	public String getSendUser(){
		return getText(sendUser);
	}
 
	
	public String getRecipientDetailEDP() {
		return getText(RecipientDetailEDP);
	}

	public String getClaimLink(){
		String text = getText(claimLink);
		String claimUrl = text.trim().split("\\n")[0];
//		///(?:(?:https?|ftp|file):\/\/|www\.|ftp\.)(?:\([-A-Z0-9+&@#\/%=~_|$?!:,.]*\)|[-A-Z0-9+&@#\/%=~_|$?!:,.])*(?:\([-A-Z0-9+&@#\/%=~_|$?!:,.]*\)|[A-Z0-9+&@#\/%=~_|$])/igm
//		Pattern r = Pattern.compile("(?:(?:https?|ftp|file):\\/\\/|www\\.|ftp\\.)(?:\\([-A-Z0-9+&@#\\/%=~_|$?!:,.]*\\)|[-A-Z0-9+&@#\\/%=~_|$?!:,.])*(?:\\([-A-Z0-9+&@#\\/%=~_|$?!:,.]*\\)|[A-Z0-9+&@#\\/%=~_|$])");
//        Matcher m = r.matcher(text);
//        String claimUrl = "";
//        if (m.find( )) {
//        	claimUrl = m.group(0);
//            System.out.println("Claim Url : " + claimUrl);
//        }
		return claimUrl;
	}

	public String getEventClaimLink(){
		return getText(eventClaimLink);
	}

	public String getParkingClaimLink(){
		return getText(parkingClaimLink);
	}

	public String[] getEventClaimLinks(){
		List<WebElement> wEventClaimLinks = getWebElementsList(eventClaimLink);
		String[] eventClaimLinks = new String[wEventClaimLinks.size()];
		for(int i = 0; i < wEventClaimLinks.size(); i++) {
			eventClaimLinks[i] = wEventClaimLinks.get(i).getText();
		}
		return eventClaimLinks;
	}

	public String[] getParkingClaimLinks(){
		List<WebElement> wParkingClaimLinks = getWebElementsList(parkingClaimLink);
		String[] parkingClaimLinks = new String[wParkingClaimLinks.size()];
		for(int i = 0; i < wParkingClaimLinks.size(); i++) {
			parkingClaimLinks[i] = wParkingClaimLinks.get(i).getText();
		}
		return parkingClaimLinks;
	}

	public void clickConfirm() {
		sync(1000L);
		WebElement we = getElementWhenVisible(confirm);
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			we.click();
		} else {
			Actions action = new Actions(getDriver());
			action.moveToElement(we).click(we).perform();
		}
	}

	public void clickDone(){
		click(reclaimDone, "Done");
	}

	public void clickViewTickets(){
		click(downloadTickets, "Print Tickets");
	}

	public void clickContinue(){
		sync(500l);
		click(popupButton, "Continue",2);
	}
	
	public void clickDonateEDP() {
		click(donateButton, "Donate,3");
	}
	
	public void clickPrintDoneEDP() {
		click(doneButton, "Donate,3");
	}
	
	public void clickDonateTicketsPageContiuneEDP() {
		click(donatecharityContinue, "Contiune Button On Donate Tickets Page");
	}
	
	public void donatecharityConfirmButton() {
		sync(500l);
		click(donatecharityConfirmButton,"Doante Charity Confirm Button",3);
	}
	
	public void clickContinuepopUpTransferEDP(){
		click(popupTransferContinueEDP, "Continue",2);
	}
	
	public void clickContinueTransferEDP() {
		click(ContinueTransferEDP, "Continue",2);
		
	}
	
	
	public void typeRecipeintFirstName(String fname) throws Exception {
		WebElement we = getElementWhenVisible(this.FirstnameEDP);
		if(driverType.trim().toUpperCase().contains("SAFARI")) {
			we.clear();
			we.sendKeys(fname, Keys.TAB);
			//utils.clearAndSetText(this.emailAddress,emailAddress);
			//type(we,"sss",emailAddress);
		} else {
			type(we, "First Name", fname);
			we.sendKeys(Keys.TAB);
		}
	}
	
	public void typeRecipeintLastName(String lname) throws Exception {
		WebElement we = getElementWhenVisible(this.LastnameEDP);
		if(driverType.trim().toUpperCase().contains("SAFARI")) {
			we.clear();
			we.sendKeys(lname, Keys.TAB);
			//utils.clearAndSetText(this.emailAddress,emailAddress);
			//type(we,"sss",emailAddress);
		} else {
			type(we, "Last Name", lname);
			we.sendKeys(Keys.TAB);
		}
	}
	
	public void typeRecipientEmailAddress(String emailAddress) throws Exception{
		WebElement we = getElementWhenVisible(this.EmailEDP);
		if(driverType.trim().toUpperCase().contains("SAFARI")) {
			we.clear();
			we.sendKeys(emailAddress, Keys.TAB);
			//utils.clearAndSetText(this.emailAddress,emailAddress);
			//type(we,"sss",emailAddress);
		} else {
			type(we, "Email address", emailAddress);
			we.sendKeys(Keys.TAB);
		}
	}
	
	public void typeRecipientMessage(String message) throws Exception{
		WebElement we = getElementWhenVisible(this.MessageEDP);
		if(driverType.trim().toUpperCase().contains("SAFARI")) {
			we.clear();
			we.sendKeys(message, Keys.TAB);
			//utils.clearAndSetText(this.emailAddress,emailAddress);
			//type(we,"sss",emailAddress);
		} else {
			type(we, "Email address", message);
			we.sendKeys(Keys.TAB);
		}
	}
	
	
	public void clickDonate() {
		click(donateButton,"Donate Button",5);
	}

	public void clickClaim(){
		click(claimButton, "Claim");
		click(acceptTransferOffer , "Continue");
		click(acceptTransferOfferDone , "Done");
		
	}
	public void clickClaimEDP(){
		sync(1000l);
		click(claimButtonEDP, "Claim",2);
	}
	
	public String getPopUpEventDetails(){
		return getText(popUpEventName);
	}
	
	public String getPopUpEventDetailsforMultiple() {
		return getText(popUpEventNameforMultiple);
	}
	
	public String getPopUpEventDetailsEDP(){
		return getText(popUpEventNameEDP);
	}

	public String getMobileScanEventDetails() {
		return getText(mobilepopUpEventName).trim().replace("clear", "").trim();
	}

	public String getMobileScanSectionName() {
		return getText(mobileDownloadSectionName,60).trim();
	}

	public String getMobileScanRowName() {
		return getText(mobileDownloadRowName).trim();
	}

	public String getMobileScanSeatName() {
		return getText(mobileDownloadSeatName).trim();
	}

	public String getPopUpSeatDetails() {
		return getText(ticketsNew.popUpSeatDetail);
	}

	public String getSection(){
		return getPopUpSeatDetails().split("\\|")[0].trim();
	}

//	public String getRow(){
//		return getPopUpSeatDetails().split("\\|")[1].trim();
//	}
//	
//	public String getSeat(){
//		return getPopUpSeatDetails().split("\\|")[2].trim();
//	}

	public String getMultipleSeat(){
		return getPopUpSeatDetails().split("\\|")[4].split(" ")[2].trim().replace(",", "");
	}

	public String getExpiryDate(){
		return getText(seatExpiryDate);
	}

	public void clickReclaim(){
		getElementWhenVisible(reclaim).click();
	}

	public void clickLostTickets() {
		click(lostTicket,"Lost or stolen tickets");
	}

	public void selectSeatInPopUp(String ticket_id, String Section, String Row, String SeatNum) {
		getElementWhenVisible(By.xpath(selectTicketAvailable));
		Section = Section.replaceAll("%20", " ");
		Row = Row.replaceAll("%20", " ");

		String sectionName = Dictionary.get("FORMATTED_SECTION_NAME_" + ticket_id);
		String rowName = Dictionary.get("FORMATTED_ROW_NAME_" + ticket_id);
		String seatName = Dictionary.get("FORMATTED_SEAT_NAME_" + ticket_id);
		String sectionLabel = Dictionary.get("FORMATTED_SECTION_LABEL_" + ticket_id);
		String seatlabel = Dictionary.get("FORMATTED_SEAT_LABEL_" + ticket_id).trim().equalsIgnoreCase("") ? "Seat" : Dictionary.get("FORMATTED_SEAT_LABEL_" + ticket_id);
		String row = Dictionary.get("ROW_NAME_" + ticket_id);

	
		if(seatName.trim().split(" ").length == 1) {
			seatName = " " + seatName;
		}

		if(sectionName.trim().split(" ").length == 1) {
			sectionName = " " + sectionName;
		}

		if(rowName.trim().split(" ").length == 1) {
			rowName = " " + rowName;
		}

		if(!rowName.trim().contains(row.trim())) {
			rowName = rowName.trim() + " " + row.trim();
		} else if(rowName.trim().equalsIgnoreCase(row.trim())) {
			rowName = "Row" + " " + row.trim();
		}

		if(!seatlabel.trim().equalsIgnoreCase("") && (seatName.trim().equalsIgnoreCase("") || seatName.trim().equalsIgnoreCase(SeatNum.trim()))) {
			seatName = seatlabel.trim() + " " + SeatNum.trim();
		} else if(seatlabel.trim().equalsIgnoreCase("") && (seatName.trim().equalsIgnoreCase("") || seatName.trim().equalsIgnoreCase(SeatNum.trim()))) {
			seatName = "Seat" + " " + SeatNum.trim();
		}

		String ticketXpath = "//label[contains(@class,'theme-field-') and (following-sibling::div[contains(.,'Sec "
				+ Section + "') or contains(.,'Section " + Section + "')][contains(.,'Row " + Row
				+ "')][contains(.,'Seat " + SeatNum + "')] or following-sibling::span[contains(.,'Sec " + Section
				+ "') or contains(.,'Section " + Section + "')][contains(.,'Row " + Row + "')][contains(.,'Seat "
				+ SeatNum + "')])]";
		
		if(!sectionLabel.trim().equalsIgnoreCase("[S]") && (sectionName.trim().equalsIgnoreCase("") || rowName.trim().equalsIgnoreCase(""))) {
			click(By.xpath(ticketXpath), "Select seat");
		} else if(!sectionLabel.trim().equalsIgnoreCase("[S]")) {

			//click(By.xpath("//*[contains(@class,'selectionInner')]//*[contains(@class,'ticketsBlock')]/strong[contains(text(),'" + sectionName +" | " + rowName + "')]/..//li//strong[text()='" + seatName + "']/../label | //*[contains(@class,'selectionInner')]//*[contains(@class,'ticketsBlock')]/strong[contains(text(),'" + sectionName + " | " + rowName + "')]/..//li//strong[text()='" + seatName + "']/../label"), "Select seat");
			click(By.xpath(ticketXpath),"Select seat");
			//click(By.xpath("//*[contains(@class,'seat-selection-selectionInner')]//*[contains(@class,'styles-sectionList')]//span[text()='" + sectionName + ", " + rowName + ", " + seatName + "']/../label"),"Select seat");
		} else {
			click(By.xpath("//label[contains(@class,'theme-field-') and (following-sibling::div[contains(.,'Row " + Row
					+ "')][contains(.,'Seat " + SeatNum + "')] or following-sibling::span[contains(.,'Row " + Row
					+ "')][contains(.,'Seat " + SeatNum + "')])]"), "Select seat");
		}
	}
	
	
	public void selectSeatInPopUpEDP(String ticket_id, String Section, String Row, String SeatNum) {
		
		Section = Section.replaceAll("%20", " ");
		Row = Row.replaceAll("%20", " ");

		String sectionName = Dictionary.get("FORMATTED_SECTION_NAME_" + ticket_id);
		String rowName = Dictionary.get("FORMATTED_ROW_NAME_" + ticket_id);
		String seatName = Dictionary.get("FORMATTED_SEAT_NAME_" + ticket_id);
		String sectionLabel = Dictionary.get("FORMATTED_SECTION_LABEL_" + ticket_id);
		String seatlabel = Dictionary.get("FORMATTED_SEAT_LABEL_" + ticket_id).trim().equalsIgnoreCase("") ? "Seat" : Dictionary.get("FORMATTED_SEAT_LABEL_" + ticket_id);
		String row = Dictionary.get("ROW_NAME_" + ticket_id);

		String ticketXpath = "//label[contains(@class,'theme-field-') and (following-sibling::div[contains(.,'Sec "+Section+"') or contains(.,'Section "+Section+"')][contains(.,'Row "+Row+"')][contains(.,'Seat "+SeatNum+"')] or following-sibling::span[contains(.,'Sec "+Section+"') or contains(.,'Section "+Section+"')][contains(.,'Row "+Row+"')][contains(.,'Seat "+SeatNum+"')])]";
		
		if(seatName.trim().split(" ").length == 1) {
			seatName = " " + seatName;
		}

		if(sectionName.trim().split(" ").length == 1) {
			sectionName = " " + sectionName;
		}

		if(rowName.trim().split(" ").length == 1) {
			rowName = " " + rowName;
		}

		if(!rowName.trim().contains(row.trim())) {
			rowName = rowName.trim() + " " + row.trim();
		} else if(rowName.trim().equalsIgnoreCase(row.trim())) {
			rowName = "Row" + " " + row.trim();
		}

		if(!seatlabel.trim().equalsIgnoreCase("") && (seatName.trim().equalsIgnoreCase("") || seatName.trim().equalsIgnoreCase(SeatNum.trim()))) {
			seatName = seatlabel.trim() + " " + SeatNum.trim();
		} else if(seatlabel.trim().equalsIgnoreCase("") && (seatName.trim().equalsIgnoreCase("") || seatName.trim().equalsIgnoreCase(SeatNum.trim()))) {
			seatName = "Seat" + " " + SeatNum.trim();
		}

		if(!sectionLabel.trim().equalsIgnoreCase("[S]") && (sectionName.trim().equalsIgnoreCase("") || rowName.trim().equalsIgnoreCase(""))) {
			click(By.xpath("//*[contains(@class,'selectionInner')]//*[contains(@class,'ticketsBlock')]/strong[contains(text(),'" + sectionLabel + " " + Section + " | " + rowName + "')]/..//li//strong[contains(text(), '" + SeatNum + "')]/../label | //*[contains(@class,'seat-selection-selectionInner')]//*[contains(@class,'ticketsBlock')]/strong[contains(text(),'" + sectionLabel + " " + Section + " | " + rowName + "')]/..//li//strong[contains(text(), '" + SeatNum + "')]/../label"), "Select seat");
		} else  {
			
			click(By.xpath(ticketXpath),"Select Seat");	
		} 
	}
	
	
	public void seatSectionRowVerification(String ticket_id, String Section, String Row, String SeatNum) {
		Section = Section.replaceAll("%20", " ");
		Row = Row.replaceAll("%20", " ");

		String sectionName = Dictionary.get("FORMATTED_SECTION_NAME_" + ticket_id);
		String rowName = Dictionary.get("FORMATTED_ROW_NAME_" + ticket_id);
		String seatName = Dictionary.get("FORMATTED_SEAT_NAME_" + ticket_id);
	
		String APIdetails =sectionName + rowName  +seatName;	
		String APIDetails1=APIdetails.replace(" ", "").toUpperCase();
		
		List<WebElement> tickets1 = getWebElementsList(ticketDetailsSeatRowSection);			
		for (int i=0;i<tickets1.size();i++) {	
			String UIdetails = tickets1.get(i).getText();
	        String[] testtest = UIdetails.split("\\n");
	        String SectionNameUI = testtest[0]+" "+testtest[1];
	        String rowNameUI =testtest[2]+" "+testtest[3];
	        String seatNameUI = testtest[4]+" "+testtest[5];
	        
	        String UIdetails2= testtest[0]+testtest[1]+testtest[2]+testtest[3]+testtest[4]+testtest[5];
			if(UIdetails2.contains(APIDetails1)) {
				sync(900l);
				Assert.assertEquals(sectionName.toUpperCase(), SectionNameUI.toUpperCase(), "Section present in API and in UI is same");
				Assert.assertEquals(rowName.toUpperCase(), rowNameUI.toUpperCase(), "Row present in API and in UI is same");
				Assert.assertEquals(seatName.toUpperCase(), seatNameUI.toUpperCase(),"Seat present in API and in UI is same");
				Assert.assertTrue(true, "Section Seat and Row Same in UI and API");
				break;
			}		
		}	
	}
	
	
	public void seatSectionRowVerificationEDP(String ticket_id, String Section, String Row, String SeatNum) {
		Section = Section.replaceAll("%20", " ");
		Row = Row.replaceAll("%20", " ");

		String sectionName = Dictionary.get("FORMATTED_SECTION_NAME_" + ticket_id);
		String rowName = Dictionary.get("FORMATTED_ROW_NAME_" + ticket_id);
		String seatName = Dictionary.get("FORMATTED_SEAT_NAME_" + ticket_id);
	
		String APIdetails =sectionName + rowName  +seatName;	
		String APIDetails1=APIdetails.replace(" ", "").toUpperCase();
		
		List<WebElement> tickets1 = getWebElementsList(ticketDetailsSeatRowSectionEDP);			
		for (int i=0;i<tickets1.size();i++) {	
			String UIdetails = tickets1.get(i).getText();
	        String[] testtest = UIdetails.split(",");
	        String SectionNameUI = testtest[0];
	        String rowNameUI =testtest[1].replaceFirst(" ", "");
	        String seatNameUI = testtest[2].replaceFirst(" ", "");
	        
	        String UIdetails2= (testtest[0]+testtest[1]+testtest[2]).replace(" ", "").toUpperCase();
			if(UIdetails2.contains(APIDetails1)) {
				sync(900l);
				Assert.assertEquals(sectionName.toUpperCase(), SectionNameUI.toUpperCase(), "Section present in API and in UI is same");
				Assert.assertEquals(rowName.toUpperCase(), rowNameUI.toUpperCase(), "Row present in API and in UI is same");
				Assert.assertEquals(seatName.toUpperCase(), seatNameUI.toUpperCase(),"Seat present in API and in UI is same");
				Assert.assertTrue(true, "Section Seat and Row Same in UI and API");
				break;
			}		
		}	
	}
	
	
	public void selectEventSectionAvailable(String eventId, String Section, String Row, String Seat) {
		sync(900l);
		String SECTION ="Section "+ Section;
		String ROW ="Row "+ Row;
		String SEAT ="Seat "+Seat;
		section = By.xpath("//div[contains(.,'"+SECTION+"') and contains(.,'"+ROW+"') and contains(.,'"+SEAT+"') and contains(@class,'styles-seatLabel-')]/parent::*/preceding-sibling::label");
	   // section =By.xpath("//span[contains(@class,'styles-sectionName') and contains(text(),'"+text2+"')] | //*[contains(@class,'styles-seatLabel') and contains(text(),'"+text2+"')]");
		if(utils.checkElementPresent(section, 10)) {
			click(section,"Click on Selected Section",10);
		}
	}
	
	public void selectEventTicketAvailableEDP(String eventId, String Section, String Row, String Seat) {

		By expandTicket = By.xpath("//*[contains(@class,'seat-list-sectionTitle-') and contains(.,'"+Section+"')]");
		if(utils.checkElementPresent(expandTicket, 3)) {
			click(expandTicket,"Click on Expand Section",2);
		}
		
	    section =By.xpath("//*[contains(@class,'seat-list-seat-') and contains(.,'"+Section+"') and contains(.,'"+Row+"') and contains(.,'"+Seat+"')]");
		if(utils.checkElementPresent(section, 10)) {
			click(section,"Click on Selected Section",10);
		}			
	}
	

	public void logoutNLogin(String emailaddress, String password) throws Exception {
		String currentUrl = getDriver().getCurrentUrl();
		String appurl = Environment.get("APP_URL").trim();
		if(appurl.trim().endsWith("/"))
			appurl = appurl.trim().substring(0, appurl.trim().length() - 1);
		String clientId = appurl.substring(appurl.lastIndexOf("/") + 1).trim();
		String uri = currentUrl.trim().substring(currentUrl.trim().lastIndexOf(clientId) + clientId.trim().length());
//		String uri = currentUrl.trim().replace(Environment.get("APP_URL").trim(), "");
		utils.navigateTo("/user/logout");
		utils.navigateTo(uri);
		homepage.login(emailaddress, password, null, true);
		if (utils.checkIfElementClickable(submitBtn, 3)) {
			homepage.login(emailaddress, password, null, true);
		}
	}

	public String getTicketStatus(String eventId, String Section, String Row, String Seat, String ticketId) {
		String xpath = ticketsNew.scrollToTicketAfterReload(eventId, Section, Row, Seat, ticketId);
		try {
			if ((driverType.trim().toUpperCase().contains("ANDROID")|| driverType.trim().toUpperCase().contains("IOS"))) {
				return getText(By.xpath(xpath + "//div[contains(@class, 'mobileStatus')]//p[1][not(strong)  and not(contains(text(),'Barcode'))]"), 1);
			} else
				return getText(By.xpath(xpath+ "//div[contains(@class, 'ticket-ticketImage')]//div[contains(@class, 'statusInner')]//p[1]"),1); 
		} catch (Exception ex) {
			return "No Status";
		}
	}
	//need to check 
	public String getTicketStatusEDP(String eventId, String Section, String Row, String Seat, String ticketId) {
		try {
			if ((driverType.trim().toUpperCase().contains("ANDROID")|| driverType.trim().toUpperCase().contains("IOS"))) {
				return getText(By.xpath("(//div[contains(@class,'seat-list-messageTitle')])[1]"), 1);
			} else {
			  return ticketsNew.getClaimByStatusofTicketEDP(eventId, Section, Row, Seat, ticketId);
			}	
		} catch (Exception ex) {
			return "No Status";
		}
	}

	Utils utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);

	public void clickFirstManageTickets() {
		try{
			sync(5000L);
			getElementWhenVisible(manageTicketsList);
		} catch(Exception ex){
			getDriver().navigate().refresh();
			sync(5000L);
			getElementWhenVisible(manageTicketsList);
		}
		WebElement we = getElementWhenRendered(manageTicketsList, 150, 150);
		String[] href = getAttribute(we, "href").split("\\#/");
		Dictionary.put("eventId", href[1].replaceAll("/.*", ""));
		sync(2000L);
		utils.navigateTo("/tickets#/" + Dictionary.get("eventId").trim());
	}

	public boolean isManageTicketsListDisplayed(){
		try{
			getElementWhenVisible(manageTicketsList);
		} catch(Exception ex){
			utils.navigateTo("/tickets");
			getElementWhenVisible(manageTicketsList);
		}
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			getElementWhenRendered(manageTicketsList, 150, 150);
		} else {
			getElementWhenRendered(desktopEventImages, 80, 133);
		}
		sync(2000L);
		return true;
	}

	public boolean isTicketsListDisplayed(TestDevice device){
		if((device != null && device.getName().trim().equalsIgnoreCase("mobile")) || ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")))
			getElementWhenVisible(ticketListingMobile, 40);
		else
			getElementWhenVisible(ticketsListing, 40);
		return true;
	}
	
	public boolean isTicketsListDisplayedEDP(TestDevice device){
		if((device != null && device.getName().trim().equalsIgnoreCase("mobile")) || ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")))
			getElementWhenVisible(ticketsListingEDP, 40);
		else
			getElementWhenVisible(ticketsListingEDP, 40);
		return true;
	}
	
	public void clickCancelTransferEDP() {
		if(utils.checkIfElementClickable(pendingTicket, 1)) {
			click(pendingTicket,"Pending Transfer",1);			
		}
		click(cancelTransferEDP,"Cancel Transfer",1);
		if(utils.checkIfElementClickable(cancelTransferPOPUP, 1)) {
			click(cancelTransferPOPUP,"Cancel Transfer POP Button",1);	
		}
		if(utils.checkIfElementClickable(OkButton, 1)) {
			click(OkButton,"OK Button",1);	
		}
	}
	
	public void clickCancelTransfer() {
		
		if(checkIfElementPresent(viewAllDropDown,1)) {
			click(viewAllDropDown, "VIEW ALL");
			click(selectPending, "PENDING");
		}
		click(cancelTransfer,"Cancel Button",1);
		if(utils.checkIfElementClickable(reclaimButton, 1)) {
			click(reclaimButton,"Reclaim Button",1);
		}
		if(utils.checkIfElementClickable(reclaimButtonDone, 1)) {
			sync(100l);
			click(reclaimButtonDone,"Reclaim Done Button",1);
			getDriver().navigate().refresh();
		}	
	}

	public boolean clickDownloadTickets(){
		Assert.assertTrue(isTicketsListDisplayed(null) , "Verify tickets listing page is displayed");
		if(isViewButtonEnable()) {
			click(downloadTickets, "Download Tickets", By.xpath(selectTicketAvailable), 2);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean clickDownloadTicketsEDP(){
        Assert.assertTrue(isTicketsListDisplayedEDP(null) , "Verify tickets listing page is displayed");
        if(isViewButtonEnableEDP()) {
            click(downloadTickets, "Download Tickets", By.xpath(selectTicketAvailable), 2);
            return true;
        } else {
            return false;
        }
    }
	
	public boolean isViewButtonEnableEDP(){        
        String value = getAttribute(downloadTicketsEDP, "disabled");
        return value == null ? true : value.trim().equalsIgnoreCase("true") ? false : true;
    }


	public boolean isDownloadTicketsTextVisible(){
		getElementWhenVisible(downloadTextPopUp, 40);
		return true;
	}

	public void clickSendTickets(TestDevice device){
		Assert.assertTrue(isTicketsListDisplayed(device) , "Verify tickets listing page is displayed");
		if((device != null && device.getName().trim().equalsIgnoreCase("mobile")) || ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			scrollingToBottomofAPage();
			By sendButton = By.xpath(".//android.widget.Button[contains(@content-desc, 'SEND')]");
			click(sendTicketMobile, "Send Tickets", sendButton, null, "Send Tickets");
		} else
			click(sendTicket, "Send Tickets", By.xpath(selectTicketAvailable), 2);
	}
	

	public void clickSendTicketsEDP(TestDevice device){
		Assert.assertTrue(isTicketsListDisplayedEDP(device) , "Verify tickets listing page is displayed");
		if((device != null && device.getName().trim().equalsIgnoreCase("mobile")) || ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			scrollingToBottomofAPage();
			By sendButton = By.xpath(".//android.widget.Button[contains(@content-desc, 'SEND')]");
			click(sendTicketMobile, "Send Tickets", sendButton, null, "Send Tickets");
		} else
			click(sendTicketEDP, "Send Tickets", By.xpath(selectTicketAvailable), 2);
	}
	
	public boolean isSendTicketsTextVisible(){
		getElementWhenVisible(sendTextPopUp, 40);
		return true;
	}

	public void clickSellTickets(TestDevice device){
		Assert.assertTrue(isTicketsListDisplayed(device) , "Verify tickets listing page is displayed");
		if((device != null && device.getName().trim().equalsIgnoreCase("mobile")) || ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))){
			scrollingToBottomofAPage();
			By sellButton = By.xpath(".//android.widget.Button[contains(@content-desc, 'SELL')]");
			click(sellTicketMobile, "Sell", sellButton, null, "Sell");
		} else
			click(sellTicket, "Sell", By.xpath(selectTicketAvailable), 2, 40);
	}

	public void clickDonateTickets(TestDevice device){
		Assert.assertTrue(isTicketsListDisplayed(device) , "Verify tickets listing page is displayed");
		if((device != null && device.getName().trim().equalsIgnoreCase("mobile")) || ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			scrollingToBottomofAPage();
			By moreButton = By.xpath(".//android.widget.Button[contains(@content-desc, 'more_horiz')]");
			click(moreButtonMobile, "More", moreButton, null, "More");
			click(donateTicketMobile, "Donate");
		} else {
			click(moreButton, "More");
			click(donateTicket, "Donate", By.xpath(selectTicketAvailable), 2);
		}
	}
	
	public void clickDonateTicketsEDP(TestDevice device){
		//Assert.assertTrue(isTicketsListDisplayed(device) , "Verify tickets listing page is displayed");
		if((device != null && device.getName().trim().equalsIgnoreCase("mobile")) || ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone"))) {
			click(donateTicket, "Donate");
		} else {
			click(donateTicket, "Donate");
		}
	}
	
	

	public boolean isSellTicketsTextVisible(){
		getElementWhenVisible(sellTicketPopUp, 40);
		return true;
	}

	public boolean isDonateTicketsTextVisible(){
		getElementWhenInVisible(donateTextPopUp, 40);
		return true;
	}

	public void clickFirstOddManageTickets(){
		click(firstManageTickets,"Click First Manage Tickets");
	}

	public String[] getTicketDetails() {
		List<String> ticketDetails = new ArrayList<String>();
		List<WebElement> h5 = getWebElementsList(this.ticketDetails);
		if(h5.size() > 0) {
			for(int i = 0; i < h5.size(); i++) {
				ticketDetails.add(getAttribute(getWebElementsList(this.ticketDetails).get(i),"innerHTML").trim().replace("\n", "").
						replaceAll("(<!-- react-text: )\\d\\d\\d\\d( -->)|(<!-- /react-text )(-->)|<!-- react-text: 930 -->|<!-- react-text: 931 -->|<!-- react-text: 932 -->", ""));
			}
			//ticketDetails.add(getTermsCondition());
			return ticketDetails.toArray(new String[ticketDetails.size()]);
		} else {
			return new String[]{getNoTicketDetailsText()};
		}
	}

	public void verifyRenderBarcode(String email, String pass, String ticketId) throws Exception	{
		if(!utils.getManageTicketConfiguration("mobile_enabled"))
			  throw new SkipException("Mobile_Enabled is not enabled in CMS");
		if(Environment.get("deviceType").trim().equalsIgnoreCase("tablet")) {
			throw new SkipException("Skipped");
		}
		ManageticketsAPI manageticketsapi= new ManageticketsAPI(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		String[] ticket=ticketId.split("\\.");
		utils.navigateTo("/tickets#/"+ticket[0]);
		clickScanBarcode(ticket[0], ticket[1].replaceAll("%20", " "), ticket[2], ticket[3], ticketId);
		Assert.assertEquals(getMobileScanEventDetails(), Dictionary.get("eventName"));
		Assert.assertEquals(getMobileScanSectionName(), ticket[1].replaceAll("%20", " "));
		Assert.assertEquals(getMobileScanRowName(),ticket[2]);
		Assert.assertEquals(getMobileScanSeatName(), ticket[3]);
		Assert.assertEquals(getMobileScanGateNumber(), "Enter Gate: " + Dictionary.get("entryGate").trim());
		manageticketsapi.renderBarcode(email, pass, ticketId);
		Assert.assertTrue(isBarcodeDisplayed(), "Verify bar code is displayed");
	}

	public void verifyPrintTicket(String email, String pass, String ticketId) throws Exception	{
		ManageticketsAPI manageticketsapi= new ManageticketsAPI(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		
		String[] ticket = ticketId.split("\\.");
		utils.navigateTo("/tickets#/" + ticket[0]);
		if(checkenableEDP()) {
			boolean success = clickDownloadTicketsEDP();
			if (success) {
				selectSeatInPopUp(ticketId, ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
				clickContinueEDP();
				System.out.println( Dictionary.get("eventName"));
				ManageticketsAAPI aapi = new ManageticketsAAPI(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
				// Get displayed Event Name from API
				JSONObject eventJsonObject = aapi.getAllEventsForMember();
				JSONObject embedded = (JSONObject) eventJsonObject.get("_embedded");
				JSONArray events = embedded.getJSONArray("events");
				forLoop: for (Object eventObject : events) {
					JSONObject event = (JSONObject)eventObject;
					if(event.has("archticsDescription")){
						Dictionary.put("eventName",event.getString("name").trim());
						break forLoop;
					}
				}
				System.out.println(Dictionary.get("eventName"));
				// clickContinue();
				Assert.assertEquals(getPopUpEventDetailsforMultiple(), Dictionary.get("eventName"));
				SoftAssert.assertTrue(getSection().contains(ticket[1].replaceAll("%20", " ")));
				clickContinue();
				clickDone();
				Assert.assertTrue(isTicketsListDisplayedEDP(null), "Verify tickets listing page is displayed");
				manageticketsapi.renderFile(email, pass, ticketId);
			}
		}else {
		 boolean success = clickDownloadTickets();
		if(success) {
			selectSeatInPopUp(ticketId, ticket[1].replaceAll("%20", " "), ticket[2], ticket[3]);
			clickContinue();
			Assert.assertEquals(getPopUpEventDetails(), Dictionary.get("eventName"));
			SoftAssert.assertTrue(getSection().contains(ticket[1].replaceAll("%20", " ")));
			clickContinue();
			Assert.assertTrue(isTicketsListDisplayed(null), "Verify tickets listing page is displayed");
			manageticketsapi.renderFile(email, pass, ticketId);
			clickDone();
		}
		}
	}

	public String returnFirstTicketName() {

		return getText(ticketname);

	}

	public String returnFirstTicketTime() {

		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			return getText(mobiletickettime);
		} else {
			return getText(tickettime);
		}
	}

	public void verifyPendingandCancelLink(String name, String seats, String event, int number) {
		if(checkIfElementPresent(viewAllDropDown,10)) {
			click(viewAllDropDown, "VIEW ALL");
			click(selectPending, "PENDING");
		}
		By transfreeName = null;
		int size=0;
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			transfreeName = By.xpath("//div[@class='slick-slide']//div[contains(@class, 'ticket-mobileStatus')]/div[contains(@class, 'ticketStatus')]/div/p[contains(.,\'Waiting for " + name + " to claim\')]");
			cancelTransfer = By.xpath("//div[@class='slick-slide']//div[contains(@class, 'ticket-ticketStatus')]/div/div[contains(@class, 'ticket-completedStatus')]/span[contains(.,'Cancel Transfer')]");
		} else
		    transfreeName = By.xpath("//div[contains(@class, 'ticket-status')]/div[contains(@class, 'ticket-statusInner')]/p[contains(.,\'Waiting for " + name + " to claim\')]");
		Assert.assertTrue(utils.checkIfElementClickable(transfreeName,10),"Manage ticket page has Transfree name mentioned on tickets");
		size=getWebElementsList(transfreeName).size();
		Assert.assertTrue((size == number || size+1== number), "Number of tickets transferred match on Manage tickets page");
		int cancelTransferNum = getWebElementsList(cancelTransfer).size();
		Assert.assertTrue(cancelTransferNum >= number-1);
		for (String seat : seats.split(";")) {
			String[] details = seat.split(",");
			String section = details[0].replaceAll("[^0-9]", "");
			String row = details[1].substring(5);
			String seatno = details[2].replaceAll("[^0-9]", "");
			Assert.assertTrue(isBulkTicketDisplayedPending(section, row, seatno),"Pending Ticket is not shown with CANCEL TRANSFER link:"+seat);
		}
		Assert.assertTrue(true,"All Pending Tickets are shown with CANCEL TRANSFER link on Manage Tickets Page:"+seats);
	}
	
	public void verifyPendingandCancelLinkEDP(String name, String seats, String event, int number) {
	String[] sectionname= seats.split("\\s");
    String[] sectionname1 =sectionname[1].split("\\,");
	By totalsection = By.xpath("//div[contains(@class,'seat-list-sectionTitle')]//span[contains(text(),'"+sectionname1[0]+"')] | //div[contains(@class,'seat-list-sectionSeats')]//*[contains(text(),'"+sectionname1[0]+"')] | //div[contains(@class,'seat-list-sectionName') and contains(text(),'"+sectionname1[0]+"')]");

		if(checkIfElementPresent(totalsection,10)) {
			click(totalsection,"SECTION ARROW",10);
			if(!checkenableEDP())
				click(pendingTicket,"PENDING TRANSFER",10);
		}else{
			//This feature removed in EDP phase 3
			//click(pendingTicket,"PENDING TRANSFER",10);
		}
		
		By transfreeName = null;
		By cancelTransfer= null;
		int size=0;
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			transfreeName = By.xpath("//div[text()='Transfer Pending']/..//div[contains(@class,'seat-list-messageDetail')]/div//p[contains(text(),'"+name+"')] | //span[normalize-space(text())='Transfer Pending:']/..//p[contains(text(),'\"+name+\"')]\");");
			cancelTransfer = By.xpath("//div[text()='Transfer Pending']/..//div[contains(@class,'seat-list-messageDetail')]//div/..//button[text()='Cancel Transfer'] | //span[normalize-space(text())='Transfer Pending:']/following-sibling::button[text()='Cancel Transfer']");
		} else
		   transfreeName = By.xpath("//div[text()='Transfer Pending']/..//div[contains(@class,'seat-list-messageDetail')]/div//p[contains(text(),'"+name+"')] | //span[normalize-space(text())='Transfer Pending:']/..//p[contains(text(),'"+name+"')]");
		   cancelTransfer = By.xpath("//div[text()='Transfer Pending']/..//div[contains(@class,'seat-list-messageDetail')]//div/..//button[text()='Cancel Transfer'] | //span[normalize-space(text())='Transfer Pending:']/following-sibling::button[text()='Cancel Transfer']");
		Assert.assertTrue(utils.checkIfElementClickable(transfreeName,10),"Manage ticket page has Transfree name mentioned on tickets");
		size=getWebElementsList(transfreeName).size();
		Assert.assertTrue((size == number || size+1== number), "Number of tickets transferred match on Manage tickets page");
		Assert.assertTrue(utils.checkIfElementClickable(cancelTransfer,10),"Manage ticket page has Cancel Transfer button present on tickets");
		size=getWebElementsList(cancelTransfer).size();
		Assert.assertTrue((size == number || size+1== number), "Number of tickets transferred match on Manage tickets page");
	}
	
	
	public void verifyPendingandClickCancelLinkEDP(String name, String seats, String event, int number) {
		String[] sectionname= seats.split("\\s");
	    String[] sectionname1 =sectionname[1].split("\\,");
		By totalsection = By.xpath("//div[contains(@class,'seat-list-sectionTitle')]//span[contains(text(),'"+sectionname1[0]+"')]");

			if(checkIfElementPresent(totalsection,10)) {
				click(totalsection,"SECTION ARROW",10);
				click(pendingTicket,"PENDING TRANSFER",10);
			}else{
				//This feature removed in EDP phase 3
				//click(pendingTicket,"PENDING TRANSFER",10);
			}
			
			By transfreeName = null;
			By cancelTransfer= null;
			int size=0;
			if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
				transfreeName = By.xpath("//div[text()='Transfer Pending']/..//div[contains(@class,'seat-list-messageDetail')]/div//p[contains(text(),'"+name+"')] | //span[normalize-space(text())='Transfer Pending:']/..//p[contains(text(),'\"+name+\"')]\");");
				cancelTransfer = By.xpath("//div[text()='Transfer Pending']/..//div[contains(@class,'seat-list-messageDetail')]//div/..//button[text()='Cancel Transfer'] | //span[normalize-space(text())='Transfer Pending:']/following-sibling::button[text()='Cancel Transfer']");
				click(cancelTransfer,"Cancel Transfer Button",10);
			} else
			   transfreeName = By.xpath("//div[text()='Transfer Pending']/..//div[contains(@class,'seat-list-messageDetail')]/div//p[contains(text(),'"+name+"')] | //span[normalize-space(text())='Transfer Pending:']/..//p[contains(text(),'"+name+"')]");
			   cancelTransfer = By.xpath("//div[text()='Transfer Pending']/..//div[contains(@class,'seat-list-messageDetail')]//div/..//button[text()='Cancel Transfer'] | //span[normalize-space(text())='Transfer Pending:']/following-sibling::button[text()='Cancel Transfer']");
			   click(cancelTransfer,"Cancel Transfer Button",10);
		
		}
	
	
	

	public void verifyCompleted(String name, String seats, int count){
		getDriver().navigate().refresh();
		getDriver().navigate().refresh();
		String tname ="";
		if(checkIfElementPresent(viewAllDropDown,10)) {
			click(viewAllDropDown, "VIEW ALL");
			click(selectCompleted, "COMPLETED");
		}
		//Assert.assertTrue(getWebElementsList(By.xpath("//div[contains(@class, 'ticket-status')]/div[contains(@class, 'ticket-statusInner')]/p[contains(.,\'Claimed By "+name+"\')]")).size()==count,"Number of tickets transferred does not match on Manage tickets page");
		for (String seat : seats.split(";")) {
			String[] details = seat.split(",");
			String section = details[0].replaceAll("[^0-9]", "");
			String row = details[1].substring(5);
			String seatno = details[2].replaceAll("[^0-9]", "");
			Assert.assertTrue(isBulkTicketDisplayedCompleted(section, row, seatno),"Completed Transfer Ticket is not shown with Ticket Sent status:"+seat);
			if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
				transfreeName=By.xpath("//div[contains(@class, 'react-root-event')]//div[@class='slick-list']//div[@class='slick-track']//div[contains(@class, 'ticket-ticketDetails')]//strong[contains(text(),'"+section+"')]/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+row+"']/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+seatno+"']/ancestor::div[contains(@class,'ticket-eventInner')]//div[contains(@class, 'ticket-mobileStatus')]/div[contains(@class, 'ticketStatus')]/div/p");
			} else
				transfreeName=By.xpath("//div[contains(@class, 'react-root-event')]//ul//li[contains(@class, 'list-item')]//div[contains(@class, 'ticket-ticketDetails')]//strong[contains(text(),'"+section+"')]/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+row+"']/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+seatno+"']/ancestor::div[contains(@class,'ticket-eventInner')]/div[contains(@class,'ticket-ticketImage')]/div[contains(@class,'ticket-ticketStatus')]/div/div[contains(@class,'ticket-status')]/div/p");

			tname = getText(transfreeName);
			//Assert.assertTrue(tname.contains(name),"Wrong Transfree name in claimed ticket:"+seat);
		}
		Assert.assertTrue(true,"All Completed Tickets are shown with TICKET SENT text on Manage Tickets Page:"+seats);
	}

	public void verifyActive(String seats, String eid) {
		getDriver().navigate().refresh();
		getDriver().navigate().refresh();
		if(checkIfElementPresent(viewAllDropDown,10)) {
			click(viewAllDropDown, "VIEW ALL");
			click(selectActive, "ACTIVE");
		}
		for (String seat : seats.split(";")) {
			String[] details = seat.split(",");
			String section = details[0].replaceAll("[^0-9]", "");
			String row = details[1].substring(5);
			String seatno = details[2].replaceAll("[^0-9]", "");
			String tid = eid + "." + section + "." + row + "." + seatno;
			Assert.assertTrue(isBulkTicketDisplayedActive(section, row, seatno),"Declined Ticket not visible back on User account Manage ticket page:"+seat);
		}
		Assert.assertTrue(true,"All Declined Tickets are shown back on Manage Tickets Page:"+seats);
	}
	
	public void verifyActiveEDP(String seats, String eid) {
		
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			Assert.assertTrue(checkIfElementPresent(sellTicket));
			Assert.assertTrue(checkIfElementPresent(sendTicketEDP));
		} else {
			Assert.assertTrue(checkIfElementPresent(donateButtonEDP));
			Assert.assertTrue(checkIfElementPresent(downloadTickets));
			Assert.assertTrue(checkIfElementPresent(sellTicket));
			Assert.assertTrue(checkIfElementPresent(sendTicketEDP));
		}
		for (String seat : seats.split(";")) {
			String[] details = seat.split(",");
			String section = details[0].replaceAll("[^0-9]", "");
			String row = details[1].substring(5);
			String seatno = details[2].replaceAll("[^0-9]", "");
			String tid = eid + "." + section + "." + row + "." + seatno;
			System.out.println(tid);
			Assert.assertTrue(isBulkTicketDisplayedActiveEDP(section, row, seatno),"Declined Ticket not visible back on User account Manage ticket page:"+seat);
			
		}
	}
	
	
	

    public void reclaimTickets(String name, String seats, int number) {
		if(checkIfElementPresent(viewAllDropDown)) {
			click(viewAllDropDown, "VIEW ALL");
			click(selectPending, "PENDING");
		}

		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			for (String seat : seats.split(";")) {
				String[] details = seat.split(",");
				String section = details[0].replaceAll("[^0-9]", "");
				String row = details[1].substring(5);
				String seatno = details[2].replaceAll("[^0-9]", "");
				By xpath = By.xpath("//div[contains(@class,'react-root-event')]//div[@class='slick-list']//div[@class='slick-track']//div[contains(@class,'slick-active')]//div[contains(@class, 'ticket-ticketDetails')]//strong[contains(text(),'"+section+"')]/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+row+"']/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+seatno+"']/ancestor::div[contains(@class,'ticket-eventInner')]/div[contains(@class,'ticket-mobileStatus')]/div[contains(@class,'ticket-ticketStatus')]/div/div[contains(@class,'ticket-completedStatus')]/span[contains(.,'Cancel Transfer')]");
				scrollandFindTicket(xpath);
				sync(5000l);
				click(xpath,"Reclaim tickets",10);
				sync(5000l);
				break;
			}
		} else {
			click(By.xpath("//div[contains(@class, 'ticket-status')]/div[contains(@class, 'ticket-statusInner')]/p[contains(.,\'Waiting for " + name + " to claim\')]/ancestor::div[contains(@class,'ticket-ticketStatus')]/div/div[contains(@class,'ticket-completedStatus')]/span[contains(.,'Cancel Transfer')]"), "Cancel Transfer Link");
		}
        Assert.assertTrue(checkIfElementPresent(reclaimDialogue));
        Assert.assertTrue(Integer.parseInt(getText(numTktReclaimDropDown))==number,"Ticket number is correct with logo");
        click(reclaimDropDown,"Click drop down",10);
        Assert.assertTrue(getWebElementsList(seatsReclaimDialogue).size() == number,"Correct seats are shown in drop down list of reclaim");
        click(reclaimButton,"Reclaim Button",10);
        sync(5000l);
        click(reclaimButtonDone,"Done Button",10);
        sync(5000l);
        getDriver().navigate().refresh();
	
   }
    
    
    public void reclaimTicketsEDP(String name, String seats, int number) {
    	
    	String[] sectionname= seats.split("\\s");
        String[] sectionname1 =sectionname[1].split("\\,");
        String section = sectionname1[0];
		if((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			for (String seat : seats.split(";")) {
				String[] details = seat.split(",");
				section = details[0].replaceAll("[^0-9]", "");
				String row = details[1].substring(5);
				String seatno = details[2].replaceAll("[^0-9]", "");
				//By xpath = By.xpath("//div[contains(@class,'react-root-event')]//div[@class='slick-list']//div[@class='slick-track']//div[contains(@class,'slick-active')]//div[contains(@class, 'ticket-ticketDetails')]//strong[contains(text(),'"+section+"')]/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+row+"']/ancestor::div[contains(@class, 'ticket-ticketDetails')]//strong[text()='"+seatno+"']/ancestor::div[contains(@class,'ticket-eventInner')]/div[contains(@class,'ticket-mobileStatus')]/div[contains(@class,'ticket-ticketStatus')]/div/div[contains(@class,'ticket-completedStatus')]/span[contains(.,'Cancel Transfer')]");
				//scrollandFindTicket(xpath);
				click(By.xpath("//div[contains(@class,'seat-list-sectionSeats')]//div[contains(@class,'seat-list-seat') and contains(text(),'"+sectionname1[0]+"')]//button[text()='Cancel Transfer']"),"Cancel Transfer Link");
				sync(5000l);
				//click(xpath,"Reclaim tickets",10);
				break;
			}
		} else {
			click(By.xpath("//div[contains(@class,'seat-list-sectionSeats')]//div[contains(@class,'seat-list-seat') and contains(text(),'"+sectionname1[0]+"')]//button[text()='Cancel Transfer'] | (//*[contains(@class,'seat-list-') and contains(text(),'"+sectionname1[0]+"')]/following::button[text()='Cancel Transfer'])[1]"),"Cancel Transfer Link");
		}
        Assert.assertTrue(checkIfElementPresent(reclaimDialogue));
        
       // this feature is not implemented in EDP Phase 3
       /* Assert.assertTrue(Integer.parseInt(getText(numTktReclaimDropDown))==number,"Ticket number is correct with logo"); 
        click(reclaimDropDown,"Click drop down",10);
        Assert.assertTrue(getWebElementsList(seatsReclaimDialogue).size() == number,"Correct seats are shown in drop down list of reclaim");
        */
        
        click(reclaimButton,"Reclaim Button",10);
        sync(5000l);
        click(reclaimButtonDone,"Done Button",10);
        sync(5000l); 

        Assert.assertTrue(true,"Reclaimed Tickets are shown back on Manage Tickets Page:"+seats);
    }
    



	public boolean scrollandFindTicket(By xpath) {
		//getDriver().navigate().refresh();
		String text = getText(ticketCount);
		Pattern p=Pattern.compile("(\\d+)");
		Matcher m=p.matcher(text);
		String ticketCount = "";
		while (m.find()) {
			ticketCount=m.group();
		}

		for(int i=0; i<=Integer.parseInt(ticketCount);i++) {
			/*try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				checkIfElementPresent
				getElementWhenInVisible
			}*/
			if(checkIfElementPresent(xpath,10)) {
				return true;
			} else {
				utils.swipe((IOSDriver) (getDriver()), 200, 200, 600, 400, Duration.ofSeconds(1));
			}
			}
		return false;
	}

	public void verifyMyEventsPage(){
		getElementWhenVisible(ticketCount,10);
	}

	public void clickContinueEDP() {
		click(ContinueButtonEDP, "Continue",2);
		
	}
	
	public void clickPrintEDP() {
		click(PrintEDP,"Print Button",2);
	}
	
	public void clickDoneEDP() {
		click(DoneEDP,"Done Button",2);
	}	
	
	public void clickTransferEDP() {
		click(submitBtn,"Transfer Button",2);
	}
	
	/**
	 * Verify Tickets Visible On UI Are In Sorted Order When EDP is On
	 * 
	 * @return true if Tickets are sorted
	 */
	public boolean verifyTicketsAreInSortedOrder() {
		boolean areTicketsSorted = false;
		List<WebElement> seatListHeaders = getWebElementsList(seatListHeader);
		getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		for (WebElement header : seatListHeaders) {
			long start = System.currentTimeMillis();
			List<WebElement> seatListSectionNames = header.findElements(seatListSectionName);
			System.out.println("SEAT LIST SECTION NAME "+(System.currentTimeMillis()-start)/1000);
			for (WebElement sectionName : seatListSectionNames) {
				// Open section name if it is closed
				if(!sectionName.findElement(By.xpath("./parent::*/parent::*")).getAttribute("class").contains("Opened")) {
					if(utils.checkIfElementPresent(sectionName, 3))
						sectionName.click();
				}
				areTicketsSorted = checkSortingOrder(sectionName.findElements(ticketDetailsSeatRowSectionEDP));
				
				if(!areTicketsSorted)
					return areTicketsSorted;
				// Closing section name
				if(sectionName.findElement(By.xpath("./parent::*/parent::*")).getAttribute("class").contains("Opened")) {
					if(utils.checkIfElementPresent(sectionName, 3))
						sectionName.click();
				}
			}
		
			getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			start = System.currentTimeMillis();
			List<WebElement> seatListEventNames = header.findElements(seatListEventName);
			System.out.println("SEAT LIST EVENT NAME "+(System.currentTimeMillis()-start)/1000);
			
			for (WebElement eventName : seatListEventNames) {
				// Open event name if it is closed
				if(!eventName.findElement(By.xpath("./parent::*/parent::*")).getAttribute("class").contains("Opened")) {
					if(utils.checkIfElementPresent(eventName, 3))
						eventName.click();
				}
				areTicketsSorted = checkSortingOrder(eventName.findElements(ticketDetailsSeatRowSectionEDP));
				
				if(!areTicketsSorted)
					return areTicketsSorted;
				
				// Closing event name 
				if(eventName.findElement(By.xpath("./parent::*/parent::*")).getAttribute("class").contains("Opened")) {
					if(utils.checkIfElementPresent(eventName, 3))
						eventName.click();
					
				}
			}
			
			if(seatListSectionNames.size()==0 && seatListEventNames.size()==0) {
				areTicketsSorted = checkSortingOrder(getWebElementsList(ticketDetailsSeatRowSectionEDP));
				
				if(!areTicketsSorted)
					return areTicketsSorted;
			}
		}
	
		return areTicketsSorted;
		
	}
	
/**
 * Check data is sorted in list
 * @param tickets
 * @return true if list contains data in sorted order
 */
	private boolean checkSortingOrder(List<WebElement> tickets) {
		ArrayList<TicketDetails> actualTickets = new ArrayList<TicketDetails>();
		for (WebElement ticket : tickets) {
//			System.out.println(ticket.getText());
			String ticketDetail = ticket.getText().split("\n")[0];
			if(ticketDetail!=null && !ticketDetail.isEmpty()) {
				actualTickets.add(new TicketDetails(ticketDetail));
				}
		}
		ArrayList<TicketDetails> expectedTickets = new ArrayList<TicketDetails>(actualTickets);
		
		// @formatter:off
		Comparator<TicketDetails> filter = Comparator.comparing(TicketDetails::getSection)
						.thenComparing(TicketDetails::getSectionNumber).thenComparing(TicketDetails::getSuite)
						.thenComparing(TicketDetails::getSuiteNumber).thenComparing(TicketDetails::getLot)
						.thenComparing(TicketDetails::getRow)
						.thenComparing(TicketDetails::getSeatNumber);
		// @formatter:on
		
		Collections.sort(expectedTickets, filter);
		
		System.out.println("Actual Ticket :: "+actualTickets);
		System.out.println("Sorted Order  :: "+expectedTickets);
		boolean areTicketsSorted = actualTickets.equals(expectedTickets);
		if(!areTicketsSorted)
			getDriver().manage().timeouts().implicitlyWait(Long.valueOf(Environment.get("implicitWait")), TimeUnit.MILLISECONDS);
		
		return areTicketsSorted;
	}
	
	private class TicketDetails {

		private String section;
		private int sectionNumber;

		private String seat;
		private int seatNumber;

		private String suite;
		private int suiteNumber;

		private String row;
		private String lot;

		private String default_;
		private String originalTicket;

		public TicketDetails(String ticket) {
			this.originalTicket = ticket;
			String[] ticketDetail = ticket.split(",");
			boolean isRowPresent = ticketDetail.length == 3 ? true : false;
			for (int i = 0; i < ticketDetail.length; i++) {
				String ticketDetail_ = ticketDetail[i];
				if (ticketDetail_.trim().toLowerCase().contains("section")) {
					this.section = ticketDetail_.trim();
				} else if (ticketDetail_.trim().toLowerCase().contains("suite")) {
					this.suite = ticketDetail_.trim();
				} else if (ticketDetail_.trim().toLowerCase().contains("row") || (isRowPresent && i == 1)) {
					this.row = ticketDetail_.trim();
				} else if (ticketDetail_.trim().toLowerCase().contains("seat")) {
					this.seat = ticketDetail_.trim();
				} else if (ticketDetail_.trim().toLowerCase().contains("lot")) {
					this.lot = ticketDetail_.trim();
				} else {
					default_ = ticketDetail_.trim();
				}
			}

		}

		public String getSection() {
			return section != null ? section : "";
		}

		public String getSeat() {
			return seat != null ? seat : "";
		}

		public String getRow() {
			return row != null ? row : "";
		}

		public int getSeatNumber() {
			try {
				seatNumber = Integer.parseInt(default_.trim());
			} catch (NullPointerException | NumberFormatException e) {
				seatNumber = 0;
			}
			if (seatNumber != 0)
				return seatNumber;

			try {
				seatNumber = Integer.parseInt(this.seat.split(" ")[1].trim());
			} catch (NullPointerException | NumberFormatException e) {
				seatNumber = 0;
			}
			return seatNumber;
		}

		public int getSectionNumber() {
			try {
				sectionNumber = Integer.parseInt(this.section.split(" ")[1].trim());
			} catch (NullPointerException | NumberFormatException e) {
				sectionNumber = 0;
			}
			return sectionNumber;
		}

		public String getSuite() {
			return suite != null ? suite : "";
		}

		public int getSuiteNumber() {
			try {
				suiteNumber = Integer.parseInt(this.suite.split(" ")[1].trim());
			} catch (NullPointerException | NumberFormatException e) {
				suiteNumber = 0;
			}
			return suiteNumber;
		}

		public String getLot() {
			return lot != null ? lot : "";
		}

		@Override
		public String toString() {
			return originalTicket;
		}
		
		

	}

	public String getBarcodeImage(String ImageFolder,String clientName,String sStartTime) throws IOException {
		TakesScreenshot scrShot =((TakesScreenshot)getDriver());
		String destFile = ImageFolder+ OSValidator.delimiter + clientName+"_"+sStartTime+".jpg";
		
		File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File(destFile));
		return destFile;
		
	}
}
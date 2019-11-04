package org.iomedia.galen.pages;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.openqa.selenium.By;
import java.util.HashMap;
import java.util.List;
import org.iomedia.galen.common.ManageticketsAAPI;
import org.iomedia.galen.common.Utils;
import org.openqa.selenium.WebElement;

public class Donation extends BaseUtil {

        private String driverType;
        HashMap<String,String> dictionary = new HashMap<String, String>();

        public Donation (WebDriverFactory driverFactory, Driver.HashMapNew Dictionary, Driver.HashMapNew Environment, Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<Driver.HashMapNew> sTestDetails) {
            super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
            driverType = driverFactory.getDriverType().get();
        }

        private By donationTitle = By.cssSelector("div[class*='style-donationTop'] h1[class*='style-title']");
        private By donationText = By.cssSelector("div[class*='style-donationTop'] span p");
        private By donationstep1 = By.cssSelector("div[class*='style-donationTop'] span ol li:nth-child(1) ");
        private By donationstep2 = By.cssSelector("div[class*='style-donationTop'] span ol li:nth-child(2)");
        private By donationstep3 = By.cssSelector("div[class*='style-donationTop'] span ol li:nth-child(3)");
        private By selectFund = By.cssSelector("input[name='selectedFund']");
        private By fundsElements = By.cssSelector("div[data-react-toolbox='dropdown'] ul li");
        private By selectFund1 = By.cssSelector("div[data-react-toolbox='dropdown'] ul li:nth-child(1)");
        private By selectFund2 = By.cssSelector("div[data-react-toolbox='dropdown'] ul li:nth-child(2)");
        private By selectFund3 = By.cssSelector("div[data-react-toolbox='dropdown'] ul li:nth-child(3)");
        private By amt25Btn = By.xpath("//button[contains(@class,'suggestedAmount25')]");
        private By amt50Btn = By.xpath("//button[contains(@class,'suggestedAmount50')]");
        private By amt100Btn = By.xpath("//button[contains(@class,'suggestedAmount100')]");
        private By amtOtherBtn = By.xpath("//button[contains(.,'Other')]");
        private By anonymousCB = By.xpath("//input[@name='isAnonymous']/following-sibling::div/following-sibling::span[contains(.,'I wish to make this donation anonymously.')]");
        private By cardFname = By.cssSelector("input[name='cardDetail.firstName']");
        private By cardLname = By.cssSelector("input[name='cardDetail.lastName']");
        private By cardNumber = By.cssSelector("input[name='cardDetail.cardNumber']");
        private By cardExpDate = By.cssSelector("input[name='cardDetail.expDate']");
        private By cardCvv = By.cssSelector("input[name='cardDetail.cvv']");
        private By billingFname = By.cssSelector("input[name='billing.firstName']");
        private By billingLname = By.cssSelector("input[name='billing.lastName']");
        private By billingAdd1 = By.cssSelector("input[name='billing.streetAddress1']");
        private By billingAdd2 = By.cssSelector("input[name='billing.streetAddress2']");
        private By billingCity = By.cssSelector("input[name='billing.city']");
        private By billingCountry = By.cssSelector("input[name='billing.country']");
        private By billingState = By.cssSelector("input[name='billing.state']");
        private By billingPostal = By.cssSelector("input[name='billing.postalCode']");
        private By listItem = By.cssSelector("div[data-react-toolbox='autocomplete'] ul li");
        private By cancelButton = By.cssSelector("button[class*='quick-donation-cancelBtn']");
        private By DonateButton = By.cssSelector("button[class*='quick-donation-continueBtn']");
        private By thankyouMsg = By.cssSelector("div[class*=style-donationTop] h1[class*='style-title']");
        private By thankYouMsgDetails = By.cssSelector("div[class*=style-donationTop] h1[class*='style-title'] + span");
        private By fundNameThanksPage = By.cssSelector("div[class*=paymentMethod] div h3");
        private By donationFromThanksPage = By.xpath("//div[contains(@class,'style-paymentMethod')]/div/div/span[contains(.,'From')]/following-sibling::h3");
        private By donationAmtThanksPage = By.xpath("//div[contains(@class,'style-paymentMethod')]/div/div/span[contains(.,'Donation')]/following-sibling::h3");
        private By anonymousTextThankyouPage = By.xpath("//div[contains(.,'Your donation was made anonymously.') and @class='text-center']");
        private By dateThankyouPage = By.xpath("//div/span[contains(.,'Date')]/following-sibling::h3");
        private By paymentDetailsText = By.xpath("//div[contains(@class,'paymentMethod')]/div/div/h3[contains(.,'Payment Details:')]/parent::div/following-sibling::div[2]");
        private By homeButtonThanksPage = By.xpath("//button[contains(@class,'quick-donations-homeBtn') and contains(.,'Home')]");
        private By otherAmt = By.cssSelector("#otherAmountInput");
        private By mimimumAmtMsg = By.xpath("//p[contains(.,'Minimum suggested  amount is $1')]");
        private By invalidFnameMsg = By.xpath("//input[@name='cardDetail.firstName']/following-sibling::span[contains(.,'Invalid')]");
        private By invalidLnameMsg = By.xpath("//input[@name='cardDetail.lastName']/following-sibling::span[contains(.,'Invalid')]");
        private By invalidCardExp = By.xpath("//input[@name='cardDetail.expDate']/following-sibling::div[contains(.,'Invalid expiry date')]");
        HashMap funds = new HashMap<String, String>();


        Utils utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
        ManageticketsAAPI ticket = new ManageticketsAAPI(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);

        public void verifydonationButtons() {
          Assert.assertTrue(utils.checkIfElementClickable(amt25Btn,1),"$25 Button Present");
          Assert.assertTrue(utils.checkIfElementClickable(amt50Btn,1),"$50 Button Present");
          Assert.assertTrue(utils.checkIfElementClickable(amt100Btn,1),"$100 Button Present");
          Assert.assertTrue(utils.checkIfElementClickable(amtOtherBtn,1),"Other Button Present");
          Assert.assertTrue(utils.checkIfElementClickable(anonymousCB,1),"Anonymous CB");
        }

        public void verifyDonationTitleandText() {
          Assert.assertTrue(getText(donationTitle,10).contains("Make a Donation"));
          Assert.assertTrue(getText(donationText).contains("Give today and make a difference. Financial donations of any size help."));
          Assert.assertTrue(getText(donationstep1).contains("Select a fund"));
          Assert.assertTrue(getText(donationstep2).contains("Enter or select an amount to donate"));
          Assert.assertTrue(getText(donationstep3).contains("Enter your payment and billing address"));
        }

        public void verifyFunds() {
            funds = ticket.getQDFunds();
            click(selectFund, "CLICK SELECT FUND DROPDOWN", 5);
            sync(9000L);
            List<WebElement> fundNames = getWebElementsList(fundsElements);
            for(WebElement fund : fundNames) Assert.assertTrue(funds.containsKey(getText(fund)),"Listed fund is present in AAPI");
        }

        public void enterFundAndAmount(String num, String amt, boolean anonymous) {
          dictionary.put("QDFundNumber",num);
          dictionary.put("QDAmt",amt);
          dictionary.put("QDAnonymous",String.valueOf(anonymous));
          click(selectFund, "CLICK SELECT FUND DROPDOWN", 5);
          switch(num) {
                  case "1" : dictionary.put("QDFundName",getText(selectFund1));
                  click(selectFund1,"SELECT FIRST FUND",5);
                      break;
                  case "2" : dictionary.put("QDFundName",getText(selectFund2));
                  click(selectFund2,"SELECT SECOND FUND",5);
                      break;
                  case "3" : dictionary.put("QDFundName",getText(selectFund3));
                  click(selectFund3,"SELECT THIRD FUND",5);
          }

          switch(amt) {
                  case "25" : click(amt25Btn,"SELECT 25$ Button");
                  break;
                  case "50" : click(amt50Btn,"SELECT 50$ Button");
                  break;
                  case "100" : click(amt100Btn,"SELECT 100$ Button");
                  break;
                  default : click(amtOtherBtn,"SELECT OTHER AMT. BUTTON");
                      try {
                          type(otherAmt,"OTHER AMOUNT",amt);
                      } catch (Exception e) {
                          e.printStackTrace();
                      }

          }

           if(anonymous) click(anonymousCB,"ANONYMOUS CHECKBOX");


        }

        public void enterDetails(String fname, String lname, String ccnumber, String cardexpiry, String zip, String add1, String add2, String cvv, String city, String country, String state) {

            dictionary.put("QDFname",fname);
            dictionary.put("QDLname",lname);
            try {
                type(cardFname,"First Name",fname);
                type(cardLname,"Last Name",lname);
                type(cardNumber,"Card Number",ccnumber);
                type(cardExpDate,"Exp Date",cardexpiry, true);
                type(cardCvv, "CVV", cvv);
                type(billingFname,"Billing Fname",fname);
                type(billingLname,"Billing Lname",lname);
                type(billingAdd1,"Address 1",add1);
                type(billingAdd2,"Address 2",add2);
                type(billingCity,"CITY",city);
                utils.clear(billingCountry);
                type(billingCountry,"COUNTRY",country);
                type(billingState,"STATE",state);
                type(billingPostal,"POSTAL",zip);
                click(billingCity,"TAB");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void clickDonate() {
            click(DonateButton,"DONATE");
        }

        public void verifyThanksPage() {
            utils.checkIfElementClickable(homeButtonThanksPage,10);
            Assert.assertTrue(getText(thankyouMsg).contains("Thank you for your generosity"));
            Assert.assertTrue(getText(thankYouMsgDetails).contains("Your donation to "+dictionary.get("QDFundName")+" is extremely important. You will receive an email with a confirmation of your donation. Your donation details are also provided to you below"));
            Assert.assertTrue(getText(thankyouMsg).contains("Thank you for your generosity"));
            Assert.assertTrue(getText(fundNameThanksPage).contains(dictionary.get("QDFundName")));
            Assert.assertTrue(getText(donationAmtThanksPage).contains(dictionary.get("QDAmt")));
            Assert.assertTrue(getText(donationFromThanksPage).contains(dictionary.get("QDFname")));
            Assert.assertTrue(getText(donationFromThanksPage).contains(dictionary.get("QDLname")));
            Assert.assertTrue(getText(paymentDetailsText).contains(dictionary.get("QDFname")));
            Assert.assertTrue(getText(paymentDetailsText).contains(dictionary.get("QDLname")));
            if(dictionary.get("QDAnonymous").equals("true")) Assert.assertTrue(utils.checkIfElementClickable(anonymousTextThankyouPage,1));
            Assert.assertTrue(utils.checkIfElementClickable(homeButtonThanksPage,1));
        }

        public void verifyHomeButton() {
            click(homeButtonThanksPage,"HOME BUTTON THANKS PAGE");
        }

        public void verifyMiminumAmtMsg() {
            String minAmt = (String) funds.get(dictionary.get("QDFundName"));
            if (!minAmt.equals("0")) {
            By xpath = By.xpath("//p[contains(.,'Minimum suggested  amount is $"+minAmt+"')]");
            Assert.assertTrue(utils.checkIfElementPresent(xpath,0));
            }
        }

        public void invalidPayment() {
            Assert.assertTrue(utils.checkIfElementClickable(invalidFnameMsg,0));
            Assert.assertTrue(utils.checkIfElementClickable(invalidLnameMsg,0));
            Assert.assertTrue(utils.checkIfElementClickable(invalidCardExp,0));
        }

        public void donateButtonDisabled() {
            Assert.assertFalse(utils.checkIfElementClickable(DonateButton,1));
        }

        public void clickCancel() {
            click(cancelButton,"CANCEL");
        }
        
    }

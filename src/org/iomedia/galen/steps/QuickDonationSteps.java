package org.iomedia.galen.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.iomedia.common.BaseUtil;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.pages.CMS;
import org.iomedia.galen.pages.Donation;
import org.iomedia.galen.pages.Homepage;


public class QuickDonationSteps {
    CMS cms;
    BaseUtil base;
    Utils utils;
    org.iomedia.framework.Assert Assert;
    Homepage homepage;
    Donation donation;


    public QuickDonationSteps(Donation donation, Homepage homepage, BaseUtil base) {
        this.donation = donation;
        this.homepage = homepage;
        this.base = base;
    }


    @Then("^Quick Donation page title is displayed with Donation Text and Steps to Donate$")
    public void qd_title() {
        donation.verifyDonationTitleandText();
    }

    @And("Quick Donations funds are listed as per AAPI response")
    public void verifyFunds() {
        donation.verifyFunds();
    }

    @And("^User selects fund number, amount and anonymous flag as: (.+), (.+) & (.+) respectively$")
    public void selectfundAndamount(String num, String amount, boolean anonymous) {
        donation.enterFundAndAmount(num, amount, anonymous);
    }

    @And("^User enters following payment details & address info (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
    public void enter_payment_details(String fname, String lname, String ccnumber, String cardexpiry, String zip, String add1, String add2, String cvv, String city, String country, String state) {
        fname = (String) base.getGDValue(fname);
        lname = (String) base.getGDValue(lname);
        ccnumber = (String) base.getGDValue(ccnumber);
        cardexpiry = (String) base.getGDValue(cardexpiry);
        zip = (String) base.getGDValue(zip);
        add1 = (String) base.getGDValue(add1);
        add2 = (String) base.getGDValue(add2);
        cvv = (String) base.getGDValue(cvv);
        city = (String) base.getGDValue(city);
        country = (String) base.getGDValue(country);
        state = (String) base.getGDValue(state);

        donation.enterDetails(fname, lname, ccnumber, cardexpiry, zip, add1, add2, cvv, city, country, state);
    }

    @And("^User clicks Donate Button$")
    public void clickdonate() {
        donation.clickDonate();
    }

    @Then("^Thank you page is displayed with correct Donation details and homepage button$")
    public void verifyThanksPage() {
        donation.verifyThanksPage();
    }

    @And("User clicks Home Button on Quick Donation Thanks Page")
    public void clickHomeButton() {
        donation.verifyHomeButton();
    }

    @Then("Miminum Suggested Amount warning Msg is displayed")
    public void minimumSuggestedAmt() {donation.verifyMiminumAmtMsg();}

    @Then("Invalid FirstName, Invalid LastName & Invalid Card Expiry Messages are displayed")
    public void invalidPaymentDetails() {donation.invalidPayment();}

    @And("Donate Button is disabled")
    public void donateDisable() {donation.donateButtonDisabled();}

    @When("User clicks Cancel Button")
    public void clickCancel() {donation.clickCancel();}
   }

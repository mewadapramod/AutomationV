package org.iomedia.galen.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.iomedia.galen.pages.DashboardSection;
import org.iomedia.galen.pages.Resale;

public class ResaleSteps {

    Resale resale;
    DashboardSection dashboardSection;
    public ResaleSteps(Resale resale){
        this.resale = resale;
    }

    @And("^User selects Valid Event with resale tickets for member (.+) and (.+)$")
    public void valid_resale_ticket_event(String uname, String passwd) {
        resale.validResaleEvent(uname,passwd);

    }

    @And("^User selects two seats for resale and hit Continue button$")
    public void select_seats_resale() {
        resale.selectSeats();
    }

    @Then("^Set Price Page appears with all valid fields$")
    public void verify_set_price() {
        resale.verifySetPrice();
    }

    @And("^User set the price as (.+) per ticket and hit Continue button$")
    public void set_price(String price) {
        resale.setPrice(price);

    }

    @Then("^Review Page appears with all valid fields and correct payout amount as per pricing policy API$")
    public void review_page_verify() {
        resale.verifyReviewPage();
    }

    @And("^Confirmation page appears with all valid fields when User clicks Submit listing button$")
    public void verify_confirmation_page() {
        resale.verifyConfirmationPage();
    }
    
    @Then("^User verify miniumum price (.+) error message$")
    public void verifyMinimumPriceErrorMessage(String price) throws Exception {
        resale.minimumPriceErrorMessage(price);
    }
    
    @Then("^User verify maximum price (.+) error message$")
    public void user_verify_maximum_price_error_message(String price) throws Exception {
        resale.maximumPriceErrorMessage(price);
    }
    
    @Then("^User clicks on cancel button and verify page should redirect to event detail page$")
    public void user_clicks_on_cancel_button_and_verify_page_should_redirect_to_event_detail_page() throws Exception {
        resale.clickonCancelResalePage();
        
    }
    
    @Then("^User Select payout menthod (.+) and clicks on Submit Listing$")
    public void user_Select_payout_menthod_and_clicks_on_Submit_Listing(String pvalue) throws Exception {
       resale.selectpayoutmethod(pvalue);
    }
    
    @Then("^User Click on Edit/Manage and hit on cancel posting$")
    public void user_Click_on_Edit_Manage_and_hit_on_cancel_posting() throws Exception {
       resale.clickEditManage();
       resale.cancelposting();
       resale.confirmbutton();
       resale.clickGoToTickets();
    }
    
    @Then("^User Clcik on Edit/Manage and hit on edit posting$")
    public void ClcikEditManagePosting() throws Exception {
    	resale.clickEditManage();
    	resale.editposting();
    }
    
    @Then("^User Verify edit posting is completed successfully$")
    public void user_Verify_edit_posting_is_completed_successfully() throws Exception {
       resale.editpostingstatus();
    }

    @And("^User is navigated to events page by clicking Done Button$")
    public void verify_events_page() {
        resale.verifyMyEventPage();

    }
}

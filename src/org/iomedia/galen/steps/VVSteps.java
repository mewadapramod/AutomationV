package org.iomedia.galen.steps;

import org.iomedia.common.BaseUtil;
import org.iomedia.galen.common.ManageticketsAPI;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.common.VirtualVenueAPI;
import org.iomedia.galen.pages.DashboardHeader;
import org.iomedia.galen.pages.VVOverviewPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class VVSteps {

	BaseUtil base;
	org.iomedia.framework.Assert Assert;
	VVOverviewPage vvOverview;
	Utils utils;
	String driverType;
	DashboardHeader header;
	String accessToken;
	ManageticketsAPI api;
	VirtualVenueAPI vvAPI;

	public VVSteps(BaseUtil base, org.iomedia.framework.Assert Assert, VVOverviewPage vvOverview, Utils utils,
			DashboardHeader header, ManageticketsAPI api, VirtualVenueAPI vvAPI) {
		this.base = base;
		this.Assert = Assert;
		this.vvOverview = vvOverview;
		this.utils = utils;
		this.header = header;
		this.driverType = base.driverFactory.getDriverType().get();
		this.vvAPI = vvAPI;
	}

	@When("^User select source seat$")
	public void user_select_source_seat() {
		vvOverview.clickSelectSeat();
		vvOverview.sourceSeatData();
	}

	@And("^User selects Next button$")
	public void user_select_next_button() {
		vvOverview.clickNextButton();
	}

	@And("^User select Plan$")
	public void user_select_plan() {
		vvOverview.clickSelectPlan();
	}

	@And("^User select Event$")
	public void user_select_event() {
		vvOverview.clickSelectPlan();
		vvOverview.readWindowObjectData();
	}

	@And("^User selects Plan Next button$")
	public void user_select_plan_next_button() {
		vvOverview.clickPlanNextButton();
		vvOverview.readWindowObjectData();
	}

	@And("^User selects Compare button$")
	public void user_select_compare_button() {
		vvOverview.clickCompareButton();
	}

	@Then("^Verify target event info for Buy Flow$")
	public void verify_initialize_for_buyflow() throws Exception {
		vvAPI.getInitailizeApiData();
		Assert.assertEquals(base.Dictionary.get("targetEvent"), base.Dictionary.get("apieventName"));
	}

	@Then("^Verify source seat info for Upgrade Flow$")
	public void verify_initialize_for_upgradeflow() throws Exception {
		vvAPI.getInitailizeApiData();
		Assert.assertEquals(base.Dictionary.get("sourceSeatPrice"), base.Dictionary.get("apiPurchasePrice"));
		Assert.assertEquals(base.Dictionary.get("totalPrice"), base.Dictionary.get("apifullPrice"));
		// Assert.assertEquals(base.Dictionary.get("totalDonation"), base.Dictionary.get("apiseatDonationAmount"));
		Assert.assertEquals(base.Dictionary.get("numOfSeat"), base.Dictionary.get("apinumSeats"));
		Assert.assertEquals(base.Dictionary.get("SectionSeatInfo"), base.Dictionary.get("seatInfo"));
		Assert.assertEquals(base.Dictionary.get("purchasePrice"), base.Dictionary.get("totalPurchasePrice"));

	}

	@Then("^Verify availability in list view$")
	public void verify_availability_for_buyflow() throws Exception {
		vvAPI.getVenueAvailabilityApiData();
		vvOverview.clickShowListButton();
		vvOverview.getVenueAvailability();
		Assert.assertTrue(vvOverview.verifyVenueAvailabilty(vvAPI.getVenueAvailabilityApiData(),vvOverview.getVenueAvailability()));
	}

}

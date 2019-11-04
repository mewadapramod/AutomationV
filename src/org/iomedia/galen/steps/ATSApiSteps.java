package org.iomedia.galen.steps;

import java.util.HashMap;

import org.iomedia.common.BaseUtil;
import org.iomedia.galen.framework.CreateAccounts;
import org.json.JSONException;

import cucumber.api.java.en.Given;

public class ATSApiSteps {
	
	BaseUtil base;
	CreateAccounts create;
	
	public ATSApiSteps(BaseUtil base, CreateAccounts create) {
		this.base = base;
		this.create = create;
	}
	
	@Given("^User creates account using ats$")
	public void user_creates_account_using_ats() throws JSONException, Exception {
		base.Environment.put("number_of_accounts", "1");
		base.Environment.put("seats.paid", "1");
		base.Environment.put("seats.unpaid", "0");
		base.Environment.put("bundling", "false");
		String Datasheet = System.getProperty("calendar") != null && !System.getProperty("calendar").trim().equalsIgnoreCase("") ? System.getProperty("calendar").trim() : base.Environment.get("calendar").trim();
		HashMap<String, String[]> creds = create.create(null, true, Datasheet, false, false);
		base.Dictionary.put("NEW_EMAIL_ADDRESS", creds.get("New User")[0]);
		base.Dictionary.put("NEW_PASSWORD", creds.get("New User")[1]);
	}
	
	@Given("^User creates account using ats with events$")
	public void user_creates_account_using_ats_with_events() throws JSONException, Exception {
		base.Environment.put("number_of_seats_per_block", "2");
		base.Environment.put("number_of_accounts", "1");
//		base.Environment.put("seats.paid", "1");
		base.Environment.put("seats.unpaid", "0");
    	base.Environment.put("bundling", "false");
    	String Datasheet = System.getProperty("calendar") != null && !System.getProperty("calendar").trim().equalsIgnoreCase("") ? System.getProperty("calendar").trim() : base.Environment.get("calendar").trim();
		HashMap<String, String[]> creds = create.create(null, true, Datasheet, true, false);
		base.Dictionary.put("NEW_EMAIL_ADDRESS", creds.get("New User")[0]);
		base.Dictionary.put("NEW_PASSWORD", creds.get("New User")[1]);
	}
}

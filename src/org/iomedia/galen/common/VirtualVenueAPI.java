package org.iomedia.galen.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.iomedia.framework.Driver.HashMapNew;
import org.json.JSONArray;
import org.json.JSONObject;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;

public class VirtualVenueAPI extends AccessToken {

	String host, emailaddress, password, accessToken, hostAAPI, TMPSCorrelationId, clientId, aapiClientId, hostAAPIAuth;
	String path = System.getProperty("user.dir");
	private String initialize = path + "/APIRequest/initialize.json";
	private String initializeBuy = path + "/APIRequest/initializeBuy.json";
	private String venueAvailability = path + "/APIRequest/venueAvailability.json";

	public VirtualVenueAPI(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment,
			Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert,
			ThreadLocal<HashMapNew> sTestDetails) {

		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);

		Dictionary = Dictionary == null || Dictionary.size() == 0
				? (driverFactory.getDictionary() == null ? null : driverFactory.getDictionary().get()) : Dictionary;

		Environment = Environment == null || Environment.size() == 0
				? (driverFactory.getEnvironment() == null ? null : driverFactory.getEnvironment().get()) : Environment;
		host = Environment.get("TM_HOST").trim();
		hostAAPI = Environment.get("AAPI_HOST").trim();
		hostAAPIAuth = Environment.get("AAPI_AUTH").trim();
		TMPSCorrelationId = Environment.get("TMPSCorrelationId").trim();
		clientId = Environment.get("CLIENT_ID").trim();
		aapiClientId = Environment.get("AAPI_CLIENT_ID").trim();
		emailaddress = Dictionary.get("EMAIL_ADDRESS").trim();
		password = Dictionary.get("PASSWORD").trim();
		accessToken = "";
	}

	public String updateQuery(String query, String fieldName, int value) {
		String[] splitQuery = query.split("\"update_" + fieldName);
		String[] splitQuery1 = query.split("update_" + fieldName + "\"");
		query = splitQuery[0] + value + splitQuery1[1];
		return splitQuery[0] + value + splitQuery1[1];
	}

	public String updateQuery(String query, String fieldName, String value) {
		String[] splitQuery = query.split("update_" + fieldName);
		return splitQuery[0] + value + splitQuery[1];
	}

	public JSONObject parseJsonFile(String filename) throws Exception {
		String jsonText = readAll(new FileReader(filename));
		JSONObject obj = new JSONObject(jsonText);
		return obj;
	}

	public void getInitailizeApiData() throws Exception {
		JSONObject updatepurl;
		JSONObject obj;
		if (Dictionary.get("applicationType").equalsIgnoreCase("Buy")) {
			updatepurl = parseJsonFile(initializeBuy);
		} else {
			updatepurl = parseJsonFile(initialize);
		}
		String newQuery = updatepurl.get("command").toString();
		newQuery = updateQuery(newQuery, "venueId", Integer.parseInt(Dictionary.get("venueId")));
		newQuery = updateQuery(newQuery, "language", Dictionary.get("language"));
		newQuery = updateQuery(newQuery, "baseUrl", Dictionary.get("namApiUrl"));
		if (Dictionary.get("applicationType").equalsIgnoreCase("Buy")) {
			newQuery = updateQuery(newQuery, "targetEvent", Dictionary.get("targetEvent"));
		}
		String updatedRequest = "{\"command\":" + newQuery + "}";
		JSONObject response = post(updatedRequest, "initialize");
		if (Dictionary.get("applicationType").equalsIgnoreCase("Buy")) {
			obj = response.getJSONObject("data").getJSONObject("targetEvent");
			Dictionary.put("apieventName", obj.get("eventName").toString());
		} else {
			obj = response.getJSONObject("data").getJSONArray("sourceSeats").getJSONObject(0);
			Dictionary.put("apiPurchasePrice", obj.get("purchasePrice").toString());
			Dictionary.put("apinumSeats", obj.get("numSeats").toString());
			Dictionary.put("apifullPrice", obj.get("fullPrice").toString());
			Dictionary.put("apirowName", obj.get("rowName").toString().trim());
			Dictionary.put("apifirstSeat", obj.get("firstSeat").toString());
			Dictionary.put("apiseatDonationAmount", obj.get("seatDonationAmount").toString());
			Dictionary.put("totalPurchasePrice", obj.get("blockPurchasePrice").toString());
			Dictionary.put("seatInfo",
					obj.get("sectionLabel").toString() + " " + obj.get("sectionName").toString() + " "
							+ obj.get("rowLabel").toString() + " " + obj.get("rowName").toString() + " "
							+ obj.get("seatLabel").toString() + " " + obj.get("firstSeat").toString() + "-"
							+ obj.get("lastSeat").toString());
			obj = response.getJSONObject("data").getJSONObject("targetEvent");
			Dictionary.put("apieventName", obj.get("eventName").toString());
		}
	}

	public JSONObject post(String payload, String apiName) throws Exception {
		InputStream is;
		String url = Dictionary.get("apiUrl").trim() + Dictionary.get("apiVersion") + "/" + apiName + "/"
				+ Dictionary.get("applicationType").trim();
		System.out.println("URL :"+url);
		if (Dictionary.get("applicationType").equalsIgnoreCase("Buy")) {
			is = utils.post(url, payload,
					new String[] { "Content-Type", "tmcorrelationid", "xtransid", "Whitelist", "User-Agent" },
					new String[] { "application/json", "dddcbf6e-be28-4930-a3c7-a9ac8d992d28",
							Dictionary.get("xTransId").trim(), "8d0b5ef3b9vv",
							"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36" });
		} else {
			is = utils.post(url, payload,
					new String[] { "Content-Type", "tmcorrelationid", "xtransid", "datatoken", "Whitelist",
							"User-Agent" },
					new String[] { "application/json", "dddcbf6e-be28-4930-a3c7-a9ac8d992d28",
							Dictionary.get("xTransId").trim(), Dictionary.get("dataToken").trim(), "8d0b5ef3b9vv",
							"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36" });
		}
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		return jsonObject;
	}

	public HashMap<String, List<String>> getVenueAvailabilityApiData() throws Exception {
		JSONArray obj;
		String headerForIndex;
		JSONObject updatepurl = parseJsonFile(venueAvailability);
		
		HashMap<String, List<String>> hm = new HashMap<String, List<String>>();

		String newQuery = updatepurl.get("command").toString();
		newQuery = updateQuery(newQuery, "venueId", Integer.parseInt(Dictionary.get("venueId")));
		newQuery = updateQuery(newQuery, "language", Dictionary.get("language"));
		//newQuery = updateQuery(newQuery, "srcSysName", Dictionary.get("env"));
		newQuery = updateQuery(newQuery, "targetEvent", "15IOFULL");

		String updatedRequest = "{\"command\":" + newQuery + "}";
		JSONObject response = post(updatedRequest, "venueAvailability");
		obj = response.getJSONObject("data").getJSONObject("section").getJSONArray("data");
		headerForIndex = response.getJSONObject("data").getJSONObject("section").getString("header");
		String[] priceTypeindex = headerForIndex.split("~");
		int sectionIndex = Arrays.asList(priceTypeindex).indexOf("sectionName");
        int availabilityIndex = Arrays.asList(priceTypeindex).indexOf("totalAvailableSeat");
        int priceCodeIndex = Arrays.asList(priceTypeindex).indexOf("priceCode");
        int isWheelChairEnabledIndex = Arrays.asList(priceTypeindex).indexOf("isWheelChairEnabled");
		for(int i=0; i<obj.length(); i++) {
			String sectionName = obj.get(i).toString().split("~")[sectionIndex];
			String totalAvailableSeat = obj.get(i).toString().split("~")[availabilityIndex];
			String priceCode = obj.get(i).toString().split("~")[priceCodeIndex];
			String isWheelChairEnabled = obj.get(i).toString().split("~")[isWheelChairEnabledIndex];
			List<String> list = new ArrayList<String>();
			list.add(totalAvailableSeat);
			list.add(priceCode);
			list.add(isWheelChairEnabled);
			hm.put(sectionName, list);
		}
		return hm;
		
	}
	
}

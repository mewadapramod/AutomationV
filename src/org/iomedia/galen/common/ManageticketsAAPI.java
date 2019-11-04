package org.iomedia.galen.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.jayway.jsonpath.JsonPath;
import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.galen.pages.AdminLogin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Cookie;
import org.testng.SkipException;
import org.iomedia.framework.OSValidator;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;

import static java.util.stream.Collectors.toMap;

public class ManageticketsAAPI extends AccessToken{

  String host, emailaddress, password, accessToken, hostAAPI, TMPSCorrelationId,clientId,aapiClientId, hostAAPIAuth;
  String path = System.getProperty("user.dir");
 // private String sentTicketRequest = path + "/APIRequest/sendTicket.json";
  private String sentTicketRequestAAPI = path + "/APIRequest/sendTicketAAPI.json";
  private String postTicketsRequest= path + "/APIRequest/PostTickets.json";
  private AdminLogin adminLogin;
  private String driverType;

  boolean can_transfer,can_resale,can_render,can_render_file,can_render_barcode, can_render_passbook,can_donate_charity, has_pending_invoice, is_mobile_enabled,is_deferred_delivery;
  
  public ManageticketsAAPI(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<HashMapNew> sTestDetails) {
	  super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	  Dictionary = Dictionary == null || Dictionary.size() == 0 ? (driverFactory.getDictionary() == null ? null : driverFactory.getDictionary().get()) : Dictionary;
	  Environment = Environment == null || Environment.size() == 0 ? (driverFactory.getEnvironment() == null ? null : driverFactory.getEnvironment().get()) : Environment;
	  host= Environment.get("TM_HOST").trim();
	  hostAAPI = Environment.get("AAPI_HOST").trim();
	  hostAAPIAuth = Environment.get("AAPI_AUTH").trim();
	  TMPSCorrelationId = Environment.get("TMPSCorrelationId").trim();
	  clientId = Environment.get("CLIENT_ID").trim();
	  aapiClientId = Environment.get("AAPI_CLIENT_ID").trim();
	  emailaddress = Dictionary.get("EMAIL_ADDRESS").trim();
	  password = Dictionary.get("PASSWORD").trim();
	  accessToken = "";
	  driverType = driverFactory.getDriverType().get();
	  adminLogin = new AdminLogin(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
  }

  Utils utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);

  public JSONObject get(String url, String accesstoken) throws Exception {
	  InputStream is = utils.waitForTMManageTicketsResponse(url, new String[] {"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[] {"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accesstoken}, false);
	  JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
	  return jsonObject;
  }

  public JSONObject get(String url, String accesstoken, boolean skipException) throws Exception {
	  InputStream is = utils.waitForTMManageTicketsResponse(url, new String[] {"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[] {"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accesstoken}, skipException);
	  JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
	  return jsonObject;
  }

  public void delete(String url, String accesstoken) throws Exception {
	  utils.del(url, new String[] {"Content-Type", "Accept", "Accept-Language", "X-Client", "apiKey", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[] {"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accesstoken});
  }

  public JSONObject post(String url, String accesstoken, String payload) throws Exception {
	  InputStream is;
	  if(accesstoken == null) {
		  is = utils.post(url, payload, new String[] {"Content-Type", "Accept", "Accept-Language", "X-Client", "apiKey", "X-OS-Name", "X-OS-Version"}, new String[] {"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim()});
	  } else {
		  is = utils.post(url, payload, new String[] {"Content-Type", "Accept", "Accept-Language", "X-Client", "apiKey", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[] {"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accesstoken});
	  }
	  JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
	  return jsonObject;
  }

  public JSONObject post(String url, String accesstoken, String payload, boolean skipException) throws Exception {
	  InputStream is;
	  if(accesstoken == null) {
		  Object[] obj = utils.post(url, payload, new String[] {"Content-Type", "Accept", "Accept-Language", "X-Client", "apiKey", "X-OS-Name", "X-OS-Version"}, new String[] {"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim()}, skipException);
		  is = (InputStream) obj[0];
	  } else {
		  Object[] obj = utils.post(url, payload, new String[] {"Content-Type", "Accept", "Accept-Language", "X-Client", "apiKey", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[] {"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accesstoken}, skipException);
		  is = (InputStream) obj[0];
	  }
	  JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
	  return jsonObject;
  }

  public JSONObject patch(String url, String accesstoken, String payload) throws Exception {
	  InputStream is = utils.patch(url, payload, new String[] {"Content-Type", "Accept", "Accept-Language", "X-Client", "apiKey", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[] {"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accesstoken});
	  JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
	  return jsonObject;
  }

  public void deleteTransfer(String transferId, boolean... needAccessToken) throws Exception {
	  assert needAccessToken.length <= 1;
	  boolean needtoken = needAccessToken.length > 0 ? needAccessToken[0] : true;
	  if(needtoken) {
		  accessToken = getAccessToken(emailaddress, password);
		  getAccountId(accessToken);
	  } else {
		  accessToken = Dictionary.get("AccessToken").trim();
		  if(accessToken.equalsIgnoreCase("")) {
			  accessToken = getAccessToken(emailaddress, password);
			  getAccountId(accessToken);
		  }
	  }

//	  String accessToken = getAccessToken(emailaddress, password);
//	  getAccountId(accessToken);
	  try {
		  utils.del(hostAAPI + "/members/" + Dictionary.get("member_id") + "/transfers/" + transferId,  new String[] {"Accept","Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id", "apiKey"}, new String[] {"application/json","application/json","Bearer "+ Dictionary.get("AccessToken"), Environment.get("DSN"),aapiClientId, TMPSCorrelationId, Environment.get("x-api-key").trim() });
		  System.out.println("Deleted : " + transferId);
	  } catch(Exception ex) {
		  sTestDetails.get().remove("RAW_RESPONSE");
	  }
  }

  public void deleteTransfer(String email, String pass,String transferId, boolean... needAccessToken) throws Exception {
	  emailaddress = email;
	  password = pass;
	  assert needAccessToken.length <= 1;
	  boolean needtoken = needAccessToken.length > 0 ? needAccessToken[0] : true;
	  if(needtoken) {
		  accessToken = getAccessToken(emailaddress, password);
		  getAccountId(accessToken);
	  } else {
		  accessToken = Dictionary.get("AccessToken").trim();
		  if(accessToken.equalsIgnoreCase("")) {
			  accessToken = getAccessToken(emailaddress, password);
			  getAccountId(accessToken);
		  }
	  }

//	  String accessToken = getAccessToken(emailaddress, password);
//	  getAccountId(accessToken);
	  try {
		  delete(host + "/members/" + Dictionary.get("member_id") + "/transfers/" + transferId, accessToken);
		  System.out.println("Deleted : " + transferId);
	  } catch(Exception ex) {
		  sTestDetails.get().remove("RAW_RESPONSE");
	  }
  }

  public void deletePosting(int postingId, boolean... needAccessToken) throws Exception {
	  assert needAccessToken.length <= 1;
	  boolean needtoken = needAccessToken.length > 0 ? needAccessToken[0] : true;
	  if(needtoken) {
		  if(emailaddress.equals("")){
			  emailaddress=Dictionary.get("ASSOCIATED_ACC_EMAIL_ADDRESS");
			  password= Dictionary.get("ASSOCIATED_ACC_PASSWORD");
		  }
		  accessToken = getAccessToken(emailaddress, password);
		  getAccountId(accessToken);
	  } else {
		  accessToken = Dictionary.get("AccessToken").trim();
		  if(accessToken.equalsIgnoreCase("")) {
			  accessToken = getAccessToken(emailaddress, password);
			  getAccountId(accessToken);
		  }
	  }
	  try {
		  delete(host + "/api/v1/member/" + Dictionary.get("member_id") + "/posting/" + postingId, accessToken);
		  System.out.println("Deleted : " + postingId);
	  } catch(Exception ex) {
		  sTestDetails.get().remove("RAW_RESPONSE");
	  }
  }

  public void deletePosting(String email,String pass,int postingId, boolean... needAccessToken) throws Exception {
	  emailaddress = email;
	  password = pass;
	  assert needAccessToken.length <= 1;
	  boolean needtoken = needAccessToken.length > 0 ? needAccessToken[0] : true;
	  if(needtoken) {
		  if(emailaddress.equals("")){
			  emailaddress=Dictionary.get("ASSOCIATED_ACC_EMAIL_ADDRESS");
			  password= Dictionary.get("ASSOCIATED_ACC_PASSWORD");
		  }
		  accessToken = getAccessToken(emailaddress, password);
		  getAccountId(accessToken);
	  } else {
		  accessToken = Dictionary.get("AccessToken").trim();
		  if(accessToken.equalsIgnoreCase("")) {
			  accessToken = getAccessToken(emailaddress, password);
			  getAccountId(accessToken);
		  }
	  }
	  try {
		  delete(host + "/api/v1/member/" + Dictionary.get("member_id") + "/posting/" + postingId, accessToken);
		  System.out.println("Deleted : " + postingId);
	  } catch(Exception ex) {
		  sTestDetails.get().remove("RAW_RESPONSE");
	  }
  }

//  public JSONObject sendTickets(int eventId, String[] ticketId) throws Exception {
//	  JSONObject obj = adminLogin.update(adminLogin.parseJsonFile(sentTicketRequest), "event_id", eventId);
//	  String payload = adminLogin.updateJSONArrays(obj, new String[]{"ticket_ids"}, Arrays.asList(Arrays.asList(ticketId))).toString();
//	  JSONObject result = post(host + "/api/v1/member/" + Dictionary.get("member_id") + "/transfer", accessToken, payload);
//	  String state = waitForTicketState(ticketId[0], "pending");
//	  Assert.assertEquals(state, "pending");
//	  return result;
//  }

  public List<String> selltickets(String[] ticketId) throws JSONException, IOException, Exception {
	  String accessToken = getAccessToken(emailaddress, password);
	  getAccountId(accessToken);
	  String queryString = "";
	  int i = 0;
	  for(; i < ticketId.length - 1; i++) {
		  queryString += "ticket_id[]=" + ticketId[i] + "&";
	  }
	  queryString += "ticket_id[]=" + ticketId[i];
	  JSONObject posting_policy = get(host + "/api/v1/member/"+Dictionary.get("member_id")+ "/posting/policy?" + queryString, accessToken);
	  JSONArray posting_group_policies = posting_policy.getJSONArray("posting_group_policies");
	  List<String> ticketBlocks = new ArrayList<String>();
	  for(i = 0; i < posting_group_policies.length(); i++) {
		  JSONObject posting_group_policy = posting_group_policies.getJSONObject(i);
		  JSONArray ticket_ids = posting_group_policy.getJSONArray("ticket_ids");
		  List<JSONObject> tickets = new ArrayList<JSONObject>();
		  JSONObject row = null;
		  for(int j = 0; j < ticket_ids.length(); j++) {
			  String ticket_id = ticket_ids.getString(j);
			  JSONObject ticket = new JSONObject("{\"ticket_id\":\"" + ticket_id + "\"}");
			  tickets.add(ticket);
			  if(j == 0) {
				  ticketBlocks.add(ticket_id);
				  int eventId = Integer.parseInt(ticketId[i].split("\\.")[0]);
				  JSONObject obj= adminLogin.update(adminLogin.parseJsonFile(postTicketsRequest), "event_id", eventId);
				  JSONObject section = adminLogin.update(obj, "section_name", ticketId[i].split("\\.")[1]);
				  row = adminLogin.update(section, "row_name", ticketId[i].split("\\.")[2]);
			  }
		  }
		  String payload = adminLogin.update(row, "tickets", tickets).toString();
		  get(host + "/api/v1/member/"+Dictionary.get("member_id")+ "/posting/payoutpreference", accessToken);
		  post(host + "/api/v1/member/" + Dictionary.get("member_id") + "/posting", accessToken, payload);
		  String state = waitForTicketState(ticketId[i], "pending");
		  Assert.assertEquals(state, "pending");
	  }
	  return ticketBlocks;
  }
  public int getAsciiCode(String text) {
	  int ascii_code = 0;
	  for(int i = 0; i < text.length(); i++) {
		  int ascii = (int)text.charAt(i);
		  ascii_code += ascii;
	  }
	  return ascii_code;
  }
  public void deleteAllTransfers(boolean... extras) throws Exception {
	  deleteAllTransfers(5, extras);
  }
  private void deleteAllTransfers(int counter, boolean... extras) throws Exception {
	  System.out.println("Trying to delete all transfer with Counter "+counter);
	  assert extras.length <= 1;
	  boolean needaccesstoken = extras.length > 0 ? extras[0] : true;
	  if(needaccesstoken) {
		  accessToken = getAccessToken(emailaddress, password);
		  getAccountId(accessToken);
	  } else {
		  accessToken = Dictionary.get("AccessToken").trim();
		  if(accessToken.equalsIgnoreCase("")) {
			  accessToken = getAccessToken(emailaddress, password);
			  getAccountId(accessToken);
		  }
	  }


	  if(Dictionary.get("AccessToken").equalsIgnoreCase(""))
	   {
		   String accessToken = getAccessToken(emailaddress, password);
		   Dictionary.put("AccessToken", accessToken);
	   }
		Object[] ab=utils.get(hostAAPI+"/members/"+Dictionary.get("member_id")+"/transfers", new String[] {"Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id", "apiKey"}, new String[] {"application/json","Bearer "+ Dictionary.get("AccessToken"), Environment.get("DSN"),aapiClientId, TMPSCorrelationId, Environment.get("x-api-key").trim() });
		InputStream is = (InputStream) ab[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		System.out.println(jsonObject.toString(2));
		if(jsonObject.has("statusCode")) {
			int statusCode = jsonObject.getInt("statusCode");
			if((statusCode == 401 || statusCode == 500) && counter>0) {
				Assert.assertTrue(true, "Not able to delete all transfer trying with counter " + (counter-1));
				deleteAllTransfers(counter - 1, false);
				return;
			}
		}
		Dictionary.put("AllTransfers", jsonObject.toString(2));

	  //JSONObject jsonObject = get(hostAAPI+"/members/"+Dictionary.get("member_id")+"/transfers", accessToken);
		JSONObject embedded = (JSONObject) jsonObject.get("_embedded");
	  JSONArray transfers =(JSONArray) embedded.get("transfers");
	  System.out.println(jsonObject);
	  System.out.println(transfers);
	  for(int i = 0; i < transfers.length(); i++) {
		  JSONObject transfer = transfers.getJSONObject(i);
		  String transfer_id = transfer.getString("id");
		  System.out.println(transfer_id);
		  deleteTransfer(transfer_id, needaccesstoken);
	  }
  }

  public void acceptDeclineTransfer(String key) throws Exception {

	  if(Dictionary.get("AccessToken").equalsIgnoreCase(""))
	   {
		   String accessToken = getAccessToken(emailaddress, password);
		   Dictionary.put("AccessToken", accessToken);
	   }

		Object[] ab=utils.get(hostAAPI+"/members/"+Dictionary.get("member_id")+"/transfers", new String[] {"Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id", "apiKey"}, new String[] {"application/json","Bearer "+ Dictionary.get("AccessToken"), Environment.get("DSN"),aapiClientId, TMPSCorrelationId, Environment.get("x-api-key").trim() });
		InputStream is = (InputStream) ab[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		System.out.println(jsonObject.toString(2));
		Dictionary.put("AllTransfers", jsonObject.toString(2));

	  //JSONObject jsonObject = get(hostAAPI+"/members/"+Dictionary.get("member_id")+"/transfers", accessToken);
		JSONObject embedded = (JSONObject) jsonObject.get("_embedded");
	  JSONArray transfers =(JSONArray) embedded.get("transfers");
	  System.out.println(jsonObject);
	  System.out.println(transfers);
	  for(int i = 0; i < transfers.length(); i++) {
		  JSONObject transfer = transfers.getJSONObject(i);
		  String transfer_id = transfer.getString("id");
		  System.out.println(transfer_id);
	//	  deleteTransfer(transfer_id, needaccesstoken);
	  }
  }

  public void deleteAllPending(boolean... extras) throws Exception {
	  deleteAllTransfers(extras);
	  deleteAllPostings(extras);
  }

  public void deleteAllPostings(boolean... extras) throws Exception {
	  assert extras.length <= 1;
	  boolean needaccesstoken = extras.length > 0 ? extras[0] : true;
	  if(needaccesstoken) {
		  accessToken = getAccessToken(emailaddress, password);
		  getAccountId(accessToken);
	  } else {
		  accessToken = Dictionary.get("AccessToken").trim();
		  if(accessToken.equalsIgnoreCase("")) {
			  accessToken = getAccessToken(emailaddress, password);
			  getAccountId(accessToken);
		  }
	  }
	  try {
		  JSONObject jsonObject = get(host+"/api/v1/member/"+Dictionary.get("member_id")+"/postings", accessToken);
		  JSONArray postings =(JSONArray) jsonObject.get("postings");

		  for(int i = 0; i < postings.length(); i++) {
			  JSONObject posting = postings.getJSONObject(i);
			  int posting_id = posting.getInt("posting_id");
			  deletePosting(posting_id, needaccesstoken);
		  }
	  } catch(Exception ex) {
		  //Do Nothing
	  }
  }



  public String[] getBarcodeRenderMobileEnabledTickets(boolean currentvalideventsonly, String eventType, boolean multiple, boolean multipleRelevantTicketIds, boolean haveRelatedEventId, boolean... extras) throws Exception {
	  assert extras.length <= 2;
	  boolean delete = extras.length > 0 ? extras[0] : true;
	  boolean needaccesstoken = extras.length > 1 ? extras[1] : true;
	  if(delete)
		  deleteAllPending(needaccesstoken);
	  if(needaccesstoken) {
		  accessToken = getAccessToken(emailaddress, password);
		  getAccountId(accessToken);
	  } else {
		  accessToken = Dictionary.get("AccessToken").trim();
		  if(accessToken.equalsIgnoreCase("")) {
			  accessToken = getAccessToken(emailaddress, password);
			  getAccountId(accessToken);
		  }
	  }
	  ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), haveRelatedEventId, currentvalideventsonly, eventType);
	  System.out.println("event_id:" + event_id);
	  String[] render_ticket_ids = null;
	  for(int m=0 ; m<event_id.size(); m++) {
		  String[] ticketIds = getTicketIds(Integer.valueOf(event_id.get(m)), accessToken, "CAN_RENDER_BARCODE", multiple);
		  if(ticketIds != null) {
			  String eventName = Dictionary.get("eventName").trim();
			  Dictionary.put("eventName", eventName);
			  render_ticket_ids = ticketIds;
			  break;
		  }
	  }

	  if(render_ticket_ids == null)
		  throw new SkipException("ticket to be used for transfer not found");
	  else
		  Assert.assertNotNull(render_ticket_ids, "Verify ticket to be used for transfer found");

	  return render_ticket_ids;
  }

  public String[] getTicketsWithoutDeferredDelivery(boolean currentvalideventsonly, String eventType, boolean multiple, boolean multipleRelevantTicketIds, boolean haveRelatedEventId, boolean... extras) throws Exception {
		assert extras.length <= 2;
		boolean delete = extras.length > 0 ? extras[0] : true;
		boolean needaccesstoken = extras.length > 1 ? extras[1] : true;
		if (delete)
			deleteAllPending(needaccesstoken);
		if (needaccesstoken) {
			accessToken = getAccessToken(emailaddress, password);
			getAccountId(accessToken);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.equalsIgnoreCase("")) {
				accessToken = getAccessToken(emailaddress, password);
				getAccountId(accessToken);
			}
		}
		ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), haveRelatedEventId, currentvalideventsonly, eventType);
		System.out.println("event_id:" + event_id);
		String[] no_deferred_ids = null;
		for (int m = 0; m < event_id.size(); m++) {
			String[] ticketIds = getTicketIds(Integer.valueOf(event_id.get(m)), accessToken, "NO_DEFERRED_DELIVERY", multiple);
			if (ticketIds != null) {
				String eventName = Dictionary.get("eventName").trim();
				Dictionary.put("eventName", eventName);
				no_deferred_ids = ticketIds;
				break;
			}
		}

		if (no_deferred_ids == null)
			throw new SkipException("ticket to be used for transfer not found");
		else
			Assert.assertNotNull(no_deferred_ids, "Verify ticket to be used for transfer found");

		return no_deferred_ids;
	}


  public String[] getPriceCurrencyAndValue(String email, String pass, boolean currentvalideventsonly,
			String eventType, boolean multiple, boolean multipleRelevantTicketIds, boolean haveRelatedEventId,
			boolean... extras) throws Exception {
		emailaddress = email;
		password = pass;
		if (!utils.getManageTicketConfiguration("send"))
			throw new SkipException("Send is not enabled in CMS");
		assert extras.length <= 2;
		boolean delete = extras.length > 0 ? extras[0] : true;
		boolean needaccesstoken = extras.length > 1 ? extras[1] : true;
		if (delete)
			deleteAllPending(needaccesstoken);
		if (needaccesstoken) {
			accessToken = getAccessToken(email, pass);
			getAccountId(accessToken);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.equalsIgnoreCase("")) {
				accessToken = getAccessToken(email, pass);
				getAccountId(accessToken);
			}
		}
		ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), haveRelatedEventId,
				currentvalideventsonly, eventType);
		System.out.println("event_id:" + event_id);
		String[] transfer_ticket_ids = null;
		for (int m = 0; m < event_id.size(); m++) {
			String[] ticketIds = getTicketIds(Integer.valueOf(event_id.get(m)), accessToken, "PRICE", multiple);
			if (ticketIds != null) {
				String eventName = Dictionary.get("eventName").trim();

				Dictionary.put("eventName", eventName);
				transfer_ticket_ids = ticketIds;
				break;
			}
		}

		if (transfer_ticket_ids == null)
			throw new SkipException("ticket to be used for transfer not found");
		else
			Assert.assertNotNull(transfer_ticket_ids, "Verify ticket to be used for transfer found");

		return transfer_ticket_ids;
	}

	public String[] getPendingInvoiceTicketId(String email, String pass, boolean currentvalideventsonly,
			String eventType, boolean multiple, boolean multipleRelevantTicketIds, boolean haveRelatedEventId,
			boolean... extras) throws Exception {
		emailaddress = email;
		password = pass;
		if (!utils.getManageTicketConfiguration("send"))
			throw new SkipException("Send is not enabled in CMS");
		assert extras.length <= 2;
		boolean delete = extras.length > 0 ? extras[0] : true;
		boolean needaccesstoken = extras.length > 1 ? extras[1] : true;
		if (delete)
			deleteAllPending(needaccesstoken);
		if (needaccesstoken) {
			accessToken = getAccessToken(email, pass);
			getAccountId(accessToken);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.equalsIgnoreCase("")) {
				accessToken = getAccessToken(email, pass);
				getAccountId(accessToken);
			}
		}
		ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), haveRelatedEventId, currentvalideventsonly, eventType);
		System.out.println("event_id:" + event_id);
		String[] transfer_ticket_ids = null;
		for (int m = 0; m < event_id.size(); m++) {
			String[] ticketIds = getTicketIds(Integer.valueOf(event_id.get(m)), accessToken, "HAS_PENDING_INVOICE", multiple);
			if (ticketIds != null) {
				String eventName = Dictionary.get("eventName").trim();

				Dictionary.put("eventName", eventName);
				transfer_ticket_ids = ticketIds;
				break;
			}
		}

		if (transfer_ticket_ids == null)
			throw new SkipException("ticket to be used for transfer not found");
		else
			Assert.assertNotNull(transfer_ticket_ids, "Verify ticket to be used for transfer found");

		return transfer_ticket_ids;
	}

	public String getPostingProfileFirstName() throws Exception {
		String accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		Object[] obj = utils.get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/posting/profile", new String[]{"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[]{"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accessToken});
		InputStream is = (InputStream) obj[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		if (!jsonObject.has("first_name"))
			jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id"), accessToken);
		String firstName = jsonObject.getString("first_name");
		return firstName;
	}

	public String[] getResaleDetails(boolean currentvalideventsonly, String eventType, boolean multiple, boolean haveRelatedEventId, boolean... extras) throws Exception {
		if (!utils.getManageTicketConfiguration("sell"))
			throw new SkipException("Sell is not enabled in CMS");
		assert extras.length <= 2;
		boolean delete = extras.length > 0 ? extras[0] : true;
		boolean needaccesstoken = extras.length > 1 ? extras[1] : true;
		if (delete)
			deleteAllPending(needaccesstoken);
		if (needaccesstoken) {
			accessToken = getAccessToken(emailaddress, password);
			getAccountId(accessToken);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.equalsIgnoreCase("")) {
				accessToken = getAccessToken(emailaddress, password);
				getAccountId(accessToken);
			}
		}
		ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), haveRelatedEventId, currentvalideventsonly, eventType);
		String[] resale_ticket_ids = null;
		for (int m = 0; m < event_id.size(); m++) {
			String[] ticketIds = getTicketIds(Integer.valueOf(event_id.get(m)), accessToken, "CAN_RESALE", multiple);
			if (ticketIds != null) {
				resale_ticket_ids = ticketIds;
				break;
			}
		}
		if (resale_ticket_ids == null)
			throw new SkipException("ticket to be used for sell not found");
		else
			Assert.assertNotNull(resale_ticket_ids, "Verify ticket to be used for sell found");
		return resale_ticket_ids;
	}

	public String[] getResaleDetails(String email, String pass, boolean currentvalideventsonly, String eventType, boolean multiple, boolean haveRelatedEventId, boolean... extras) throws Exception {
		emailaddress = email;
		password = pass;
		if (!utils.getManageTicketConfiguration("sell"))
			throw new SkipException("Sell is not enabled in CMS");
		assert extras.length <= 2;
		boolean delete = extras.length > 0 ? extras[0] : true;
		boolean needaccesstoken = extras.length > 1 ? extras[1] : true;
		if (delete)
			deleteAllPending(needaccesstoken);
		if (needaccesstoken) {
			accessToken = getAccessToken(emailaddress, password);
			getAccountId(accessToken);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.equalsIgnoreCase("")) {
				accessToken = getAccessToken(emailaddress, password);
				getAccountId(accessToken);
			}
		}
		ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), haveRelatedEventId, currentvalideventsonly, eventType);
		String[] resale_ticket_ids = null;
		for (int m = 0; m < event_id.size(); m++) {
			String[] ticketIds = getTicketIds(Integer.valueOf(event_id.get(m)), accessToken, "CAN_RESALE", multiple);
			if (ticketIds != null) {
				resale_ticket_ids = ticketIds;
				break;
			}
		}
		if (resale_ticket_ids == null)
			throw new SkipException("ticket to be used for sell not found");
		else
			Assert.assertNotNull(resale_ticket_ids, "Verify ticket to be used for sell found");

		return resale_ticket_ids;
	}

	public String getSoldTicketDetails(boolean currentvalideventsonly, String eventType, boolean haveRelatedEventId) throws Exception {
		if (!utils.getManageTicketConfiguration("sell"))
			throw new SkipException("Sell is not enabled in CMS");
		String ticketId = null;
		String accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), haveRelatedEventId, currentvalideventsonly, eventType);
		for (int m = 0; m < event_id.size(); m++) {
			String ticket_id = getTicketIdPendingAction(Integer.valueOf(event_id.get(m)), accessToken, "posting", "pending");
			if (ticket_id != null) {
				ticketId = ticket_id;
				break;
			}
		}
		if (ticketId == null)
			throw new SkipException("ticket already sold not found");
		else
			Assert.assertNotNull(ticketId, "Verify ticket already sold found");

		return ticketId;
	}

	public String[] getDonateDetails(boolean currentvalideventsonly, String eventType, boolean multiple, boolean haveRelatedEventId, boolean... extras) throws Exception {
		if (!utils.getManageTicketConfiguration("donate"))
			throw new SkipException("Donate is not enabled in CMS");
		assert extras.length <= 2;
		boolean delete = extras.length > 0 ? extras[0] : true;
		boolean needaccesstoken = extras.length > 1 ? extras[1] : true;
		if (delete)
			deleteAllPending(needaccesstoken);
		if (needaccesstoken) {
			accessToken = getAccessToken(emailaddress, password);
			getAccountId(accessToken);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.equalsIgnoreCase("")) {
				accessToken = getAccessToken(emailaddress, password);
				getAccountId(accessToken);
			}
		}
		ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), haveRelatedEventId, currentvalideventsonly, eventType);
		// System.out.println("event_id:"+event_id);
		String[] donate_ticket_ids = null;

		for (int m = 0; m < event_id.size(); m++) {
			String[] ticketIds = getTicketIds(Integer.valueOf(event_id.get(m)), accessToken, "CAN_DONATE_CHARITY", multiple);
			//System.out.println("ticketId:"+ticketId);
			if (ticketIds != null) {
				if (!isCharityAvailable(Integer.valueOf(event_id.get(m)), accessToken)) {
					continue;
				}
				donate_ticket_ids = ticketIds;
				// System.out.println("donate_ticket_id:"+donate_ticket_id);
				break;
			}
		}
		if (donate_ticket_ids == null)
			throw new SkipException("ticket to be used for donate not found");
		else
			Assert.assertNotNull(donate_ticket_ids, "Verify ticket to be used for donate found");

		return donate_ticket_ids;
	}

	public String[] getDonateDetails(String email, String pass, boolean currentvalideventsonly, String eventType, boolean multiple, boolean haveRelatedEventId, boolean... extras) throws Exception {
		emailaddress = email;
		password = pass;
		if (!utils.getManageTicketConfiguration("donate"))
			throw new SkipException("Donate is not enabled in CMS");
		assert extras.length <= 2;
		boolean delete = extras.length > 0 ? extras[0] : true;
		boolean needaccesstoken = extras.length > 1 ? extras[1] : true;
		if (delete)
			deleteAllPending(needaccesstoken);
		if (needaccesstoken) {
			accessToken = getAccessToken(emailaddress, password);
			getAccountId(accessToken);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.equalsIgnoreCase("")) {
				accessToken = getAccessToken(emailaddress, password);
				getAccountId(accessToken);
			}
		}
		ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), haveRelatedEventId, currentvalideventsonly, eventType);
		String[] donate_ticket_ids = null;

		for (int m = 0; m < event_id.size(); m++) {
			String[] ticketIds = getTicketIds(Integer.valueOf(event_id.get(m)), accessToken, "CAN_DONATE_CHARITY", multiple);
			if (ticketIds != null) {
				if (!isCharityAvailable(Integer.valueOf(event_id.get(m)), accessToken)) {
					continue;
				}
				donate_ticket_ids = ticketIds;
				break;
			}
		}
		if (donate_ticket_ids == null)
			throw new SkipException("ticket to be used for donate not found");
		else
			Assert.assertNotNull(donate_ticket_ids, "Verify ticket to be used for donate found");
		return donate_ticket_ids;
	}

	public boolean isCharityAvailable(int eventId, String accessToken) throws JSONException, IOException {
		try {
			get(host + "/api/v1/members/" + Dictionary.get("member_id") + "/inventory/donations/policy?event_id=" + eventId, accessToken);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public String[] getRenderDetails(boolean currentvalideventsonly, String eventType, boolean multiple, boolean... extras) throws Exception {
		if (!utils.getManageTicketConfiguration("download"))
			throw new SkipException("Download is not enabled in CMS");

		assert extras.length <= 2;
		boolean delete = extras.length > 0 ? extras[0] : true;
		boolean needaccesstoken = extras.length > 1 ? extras[1] : true;
		if (delete)
			deleteAllPending(needaccesstoken);
		if (needaccesstoken) {
			accessToken = getAccessToken(emailaddress, password);
			getAccountId(accessToken);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.equalsIgnoreCase("")) {
				accessToken = getAccessToken(emailaddress, password);
				getAccountId(accessToken);
			}
		}

//	  assert deletePending.length <= 1;
//	  boolean delete = deletePending.length > 0 ? deletePending[0] : true;
//	  if(delete)
//		  deleteAllPending();
//	  String accessToken = getAccessToken(emailaddress, password);
//	  getAccountId(accessToken);
		ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), false, currentvalideventsonly, eventType);
		System.out.println("all event_id:" + event_id);
		String[] render_ticket_ids = null;

		for (int m = 0; m < event_id.size(); m++) {
			String[] ticketIds = getTicketIds(Integer.valueOf(event_id.get(m)), accessToken, "CAN_RENDER_FILE", multiple);
			System.out.println("ticketId:" + ticketIds);
			if (ticketIds != null) {
				render_ticket_ids = ticketIds;
				break;
			}
		}
		if (render_ticket_ids == null)
			throw new SkipException("ticket to be used for download not found");
		else
			Assert.assertNotNull(render_ticket_ids, "Verify ticket to be used for download found");

		return render_ticket_ids;
	}

	public String[] getRenderDetails(String email, String pass, boolean currentvalideventsonly, String eventType, boolean multiple, boolean... extras) throws Exception {
		emailaddress = email;
		password = pass;

		if (!utils.getManageTicketConfiguration("download"))
			throw new SkipException("Download is not enabled in CMS");

		assert extras.length <= 2;
		boolean delete = extras.length > 0 ? extras[0] : true;
		boolean needaccesstoken = extras.length > 1 ? extras[1] : true;
		if (delete)
			deleteAllPending(needaccesstoken);
		if (needaccesstoken) {
			accessToken = getAccessToken(emailaddress, password);
			getAccountId(accessToken);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.equalsIgnoreCase("")) {
				accessToken = getAccessToken(emailaddress, password);
				getAccountId(accessToken);
			}
		}

//	  assert deletePending.length <= 1;
//	  boolean delete = deletePending.length > 0 ? deletePending[0] : true;
//	  if(delete)
//		  deleteAllPending();
//	  String accessToken = getAccessToken(emailaddress, password);
//	  getAccountId(accessToken);
		ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), false, currentvalideventsonly, eventType);
		System.out.println("all event_id:" + event_id);
		String[] render_ticket_ids = null;

		for (int m = 0; m < event_id.size(); m++) {
			String[] ticketIds = getTicketIds(Integer.valueOf(event_id.get(m)), accessToken, "CAN_RENDER_FILE", multiple);
			System.out.println("ticketId:" + ticketIds);
			if (ticketIds != null) {
				render_ticket_ids = ticketIds;
				break;
			}
		}
		if (render_ticket_ids == null)
			throw new SkipException("ticket to be used for download not found");
		else
			Assert.assertNotNull(render_ticket_ids, "Verify ticket to be used for download found");

		return render_ticket_ids;
	}

	public String[] getRenderDetailsFiles(boolean currentvalideventsonly, String eventType, boolean multiple) throws Exception {
		deleteAllPending();
		String accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), false, currentvalideventsonly, eventType);
		System.out.println("all event_id:" + event_id);
		String[] render_ticket_ids = null;

		for (int m = 0; m < event_id.size(); m++) {
			String[] renderticketIds = getTicketIds(Integer.valueOf(event_id.get(m)), accessToken, "CAN_RENDER_FILES", multiple);
			if (renderticketIds != null) {
				render_ticket_ids = renderticketIds;
				break;
			}
		}

		return render_ticket_ids;
	}

	public String[] getCannotRenderDetailsFiles(boolean currentvalideventsonly, String eventType, boolean multiple) throws Exception {
		ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), false, currentvalideventsonly, eventType);
		System.out.println("all event_id:" + event_id);
		String[] render_ticket_ids = null;

		for (int m = 0; m < event_id.size(); m++) {
			String[] cannotrenderticketIds = getTicketIds(Integer.valueOf(event_id.get(m)), accessToken, "CANNOT_RENDER_FILES", multiple);
			if (cannotrenderticketIds != null) {
				render_ticket_ids = cannotrenderticketIds;
				break;
			}
		}
		return render_ticket_ids;
	}

	public String waitForTicketState(String ticketId, String expectedState, boolean... extras) throws Exception {
		String state = null;
		int counter = 100;
		do {
			state = getTicketState(ticketId, extras);
			sync(100L);
			counter--;
		} while (counter > 0 && (state == null || !state.trim().equalsIgnoreCase(expectedState.trim())));
		Assert.assertNotNull(state, "Verify " + ticketId + " state");
		return state;
	}

	public String waitForTicketState(String email, String pass, String ticketId, String expectedState, boolean... extras) throws Exception {
		emailaddress = email;
		password = pass;
		String state = null;
		int counter = 100;
		do {
			state = getTicketState(emailaddress, password, ticketId, extras);
			sync(100L);
			counter--;
		} while (counter > 0 && (state == null || !state.trim().equalsIgnoreCase(expectedState.trim())));
		Assert.assertNotNull(state, "Verify " + ticketId + " state");
		return state;
	}

	public String waitForPendingActionRemoved(String ticketId) throws Exception {
		String state = "pending";
		int counter = 100;
		do {
			state = getTicketState(ticketId);
			sync(100L);
			counter--;
		} while (counter > 0 && state != null);
		Assert.assertNull(state, "Verify " + ticketId + " state");
		return state;
	}

	public String waitForPendingActionRemoved(String email, String pass, String ticketId) throws Exception {
		emailaddress = email;
		password = pass;
		String state = "pending";
		int counter = 100;
		do {
			state = getTicketState(emailaddress, password, ticketId);
			sync(100L);
			counter--;
		} while (counter > 0 && state != null);
		Assert.assertNull(state, "Verify " + ticketId + " state");
		return state;
	}

	public String getTicketState(String ticketId, boolean... extras) throws Exception {
		String state = "";
		String[] ticket_id = ticketId.split("\\.");
		int eventId = Integer.valueOf(ticket_id[0].trim());
		String section_name = ticket_id[1].replaceAll("%20", " ").trim();
		String row_name = ticket_id[2].replaceAll("%20", " ").trim();
		assert extras.length <= 3;
		boolean needaccesstoken = extras.length > 0 ? extras[0] : true;
		boolean needmemberid = extras.length > 1 ? extras[1] : true;
		boolean switchUser = extras.length > 2 ? extras[2] : false;

		if (needaccesstoken) {
			accessToken = getAccessToken(emailaddress, password);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.trim().equalsIgnoreCase("")) {
				accessToken = getAccessToken(emailaddress, password);
			}
		}
		if (needmemberid) {
			getAccountId(accessToken);
		} else {
			if (Dictionary.get("member_id").trim().equalsIgnoreCase("")) {
				getAccountId(accessToken);
			}
		}

		if (switchUser) {
			getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"), Dictionary.get("SWITCH_ACC_PASSWORD"));
			Dictionary.put("member_id", Dictionary.get("memberId1"));
			switchToAssociatedMember(Dictionary.get("memberId1"));
			accessToken = Dictionary.get("AccessToken").trim();
		}

		JSONObject jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/inventory/search?event_id=" + eventId, accessToken);
		JSONArray inventory_items = (JSONArray) jsonObject.get("inventory_items");
		for (int i = 0; i < inventory_items.length(); i++) {
			JSONObject data = (JSONObject) inventory_items.get(i);
			String eventname = data.getJSONObject("event").getString("name");
			Dictionary.put("eventName", eventname);
			JSONArray sections = (JSONArray) data.get("sections");
			for (int j = 0; j < sections.length(); j++) {
				JSONObject row = (JSONObject) sections.get(j);
				if (!row.getString("section_name").trim().equalsIgnoreCase(section_name))
					continue;
				JSONArray rows = (JSONArray) row.get("rows");
				for (int k = 0; k < rows.length(); k++) {
					JSONObject ticket = (JSONObject) rows.get(k);
					if (!ticket.getString("row_name").trim().equalsIgnoreCase(row_name))
						continue;
					JSONArray tickets = (JSONArray) ticket.get("tickets");
					for (int l = 0; l < tickets.length(); l++) {
						JSONObject jticket = (JSONObject) tickets.get(l);
						String ticketids = jticket.getString("ticket_id");
						Dictionary.put("State_ticket_id", ticketids);
						if (ticketids.toString().equals(ticketId)) {
							if (jticket.has("pending_action")) {
								JSONObject pending_action = (JSONObject) jticket.get("pending_action");
								if (pending_action.has("state")) {
									state = pending_action.getString("state");
									return state;
								} else {
									return null;
								}
							} else {
								return null;
							}
						}
					}
				}
			}
		}
		return null;
	}

	public String getTicketState(String email, String pass, String ticketId, boolean... extras) throws Exception {
		emailaddress = email;
		password = pass;
		String state = "";
		String[] ticket_id = ticketId.split("\\.");
		int eventId = Integer.valueOf(ticket_id[0].trim());
		String section_name = ticket_id[1].replaceAll("%20", " ").trim();
		String row_name = ticket_id[2].replaceAll("%20", " ").trim();
		assert extras.length <= 3;
		boolean needaccesstoken = extras.length > 0 ? extras[0] : true;
		boolean needmemberid = extras.length > 1 ? extras[1] : true;
		boolean switchUser = extras.length > 2 ? extras[2] : false;

		if (needaccesstoken) {
			accessToken = getAccessToken(emailaddress, password);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.trim().equalsIgnoreCase("")) {
				accessToken = getAccessToken(emailaddress, password);
			}
		}
		if (needmemberid) {
			getAccountId(accessToken);
		} else {
			if (Dictionary.get("member_id").trim().equalsIgnoreCase("")) {
				getAccountId(accessToken);
			}
		}

		if (switchUser) {
			getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"), Dictionary.get("SWITCH_ACC_PASSWORD"));
			Dictionary.put("member_id", Dictionary.get("memberId1"));
			switchToAssociatedMember(Dictionary.get("memberId1"));
			accessToken = Dictionary.get("AccessToken").trim();
		}

		JSONObject jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/inventory/search?event_id=" + eventId, accessToken);
		JSONArray inventory_items = (JSONArray) jsonObject.get("inventory_items");
		for (int i = 0; i < inventory_items.length(); i++) {
			JSONObject data = (JSONObject) inventory_items.get(i);
			String eventname = data.getJSONObject("event").getString("name");
			Dictionary.put("eventName", eventname);
			JSONArray sections = (JSONArray) data.get("sections");
			for (int j = 0; j < sections.length(); j++) {
				JSONObject row = (JSONObject) sections.get(j);
				if (!row.getString("section_name").trim().equalsIgnoreCase(section_name))
					continue;
				JSONArray rows = (JSONArray) row.get("rows");
				for (int k = 0; k < rows.length(); k++) {
					JSONObject ticket = (JSONObject) rows.get(k);
					if (!ticket.getString("row_name").trim().equalsIgnoreCase(row_name))
						continue;
					JSONArray tickets = (JSONArray) ticket.get("tickets");
					for (int l = 0; l < tickets.length(); l++) {
						JSONObject jticket = (JSONObject) tickets.get(l);
						String ticketids = jticket.getString("ticket_id");
						Dictionary.put("State_ticket_id", ticketids);
						if (ticketids.toString().equals(ticketId)) {
							if (jticket.has("pending_action")) {
								JSONObject pending_action = (JSONObject) jticket.get("pending_action");
								if (pending_action.has("state")) {
									state = pending_action.getString("state");
									return state;
								} else {
									return null;
								}
							} else {
								return null;
							}
						}
					}
				}
			}
		}
		return null;
	}

	public boolean isCustomerExist(String emailaddress, String password) throws Exception {
		JSONObject jsonObject = post(host + "/api/v1/member", null, "{\"first_name\":\"Test\",\"last_name\":\"Test\",\"email\":\"" + emailaddress + "\",\"password\":\"" + password + "\",\"remember_me\":0}", true);
		if (jsonObject.has("errors")) {
			return false;
		} else {
			return true;
		}
	}

	public Map<String, Object> getCustomerName() throws Exception {
		String accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		JSONObject jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id"), accessToken);
		Map<String, Object> variables = new HashMap<String, Object>();
		String member_id = jsonObject.getString("member_id");
		String[] account_id = member_id.split("\\.");
		String accountId = account_id[1];

		if (jsonObject.getString("member_type").trim().equalsIgnoreCase("individual")) {
			String firstName = jsonObject.getString("first_name");
			String middleName = "";//jsonObject.getString("middle_name");
			String stpmiddleName = jsonObject.getString("middle_name");
			String lastName = jsonObject.getString("last_name");

			firstName = firstName.trim().equalsIgnoreCase("") ? "" : firstName.trim();
			middleName = middleName.trim().equalsIgnoreCase("") ? "" : " " + middleName.trim();
			stpmiddleName = stpmiddleName.trim().equalsIgnoreCase("") ? "" : " " + stpmiddleName.trim();
			lastName = lastName.trim().equalsIgnoreCase("") ? "" : " " + lastName.trim();

			String fnFirstChar = firstName.trim().equalsIgnoreCase("") ? "" : String.valueOf(firstName.charAt(0)).toUpperCase();
			String lnFirstChar = lastName.trim().equalsIgnoreCase("") ? "" : String.valueOf(lastName.charAt(0)).toUpperCase();
			String lastCharacter = lastName.trim().equalsIgnoreCase("") ? "" : " " + String.valueOf(lastName.charAt(0)).toUpperCase();

			String custName = (firstName + middleName + lastName).trim();
			JSONArray jsonArray = jsonObject.getJSONArray("member_related_accounts");
			String nickName = jsonArray.getJSONObject(0).getString("name").trim();

			variables.put("ACTUAL_CUST_NAME", custName);
			if (!accountId.trim().equalsIgnoreCase(nickName)) {
				custName = nickName;
			}
			String stpcustName = (firstName + stpmiddleName + lastName).trim();
			String custShortenedName = (fnFirstChar + lnFirstChar + firstName + lastCharacter + ".").trim();
			variables.put("CUST_NAME", custName);
			variables.put("STP_CUST_NAME", stpcustName);
			variables.put("CUST_SHORTENED_NAME", custShortenedName);
			variables.put("NICK_NAME", nickName);
		} else {
			String companyName = jsonObject.getString("company_name");
			variables.put("CUST_NAME", companyName);
			variables.put("ACTUAL_CUST_NAME", companyName);

			String firstName = jsonObject.getString("first_name");
			String stpmiddleName = jsonObject.getString("middle_name");
			String lastName = jsonObject.getString("last_name");

			firstName = firstName.trim().equalsIgnoreCase("") ? "" : firstName.trim();
			stpmiddleName = stpmiddleName.trim().equalsIgnoreCase("") ? "" : " " + stpmiddleName.trim();
			lastName = lastName.trim().equalsIgnoreCase("") ? "" : " " + lastName.trim();

			JSONArray jsonArray = jsonObject.getJSONArray("member_related_accounts");
			String nickName = jsonArray.getJSONObject(0).getString("name").trim();

			String stpcustName = (firstName + stpmiddleName + lastName).trim();

			variables.put("STP_CUST_NAME", stpcustName);
			variables.put("CUST_SHORTENED_NAME", companyName);
			variables.put("NICK_NAME", nickName);
		}

		JSONArray jsonArray = jsonObject.getJSONArray("member_related_accounts");
		Dictionary.put("associatedCount", String.valueOf(jsonArray.length()));
		for (int i = 0; i < jsonArray.length(); i++) {
			String memberId = jsonArray.getJSONObject(i).getString("member_id");
			String accountId1 = memberId.split("\\.")[1].trim();
			Dictionary.put("memberId" + i, memberId);
			Dictionary.put("accId" + i, accountId1);
			Dictionary.put("name" + i, jsonArray.getJSONObject(i).getString("name").trim());
			Dictionary.put("name_" + accountId1, jsonArray.getJSONObject(i).getString("name").trim());
		}
		return variables;
	}

	public Map<String, Object> getCustomerName(String email, String pass) throws Exception {
		String accessToken = getAccessToken(email, pass);
		getAccountId(accessToken);
		JSONObject jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id"), accessToken);
		Map<String, Object> variables = new HashMap<String, Object>();
		String member_id = jsonObject.getString("member_id");
		String[] account_id = member_id.split("\\.");
		String accountId = account_id[1];

		if (jsonObject.getString("member_type").trim().equalsIgnoreCase("individual")) {
			String firstName = jsonObject.getString("first_name");
			String middleName = "";//jsonObject.getString("middle_name");
			String stpmiddleName = jsonObject.getString("middle_name");
			String lastName = jsonObject.getString("last_name");

			firstName = firstName.trim().equalsIgnoreCase("") ? "" : firstName.trim();
			middleName = middleName.trim().equalsIgnoreCase("") ? "" : " " + middleName.trim();
			stpmiddleName = stpmiddleName.trim().equalsIgnoreCase("") ? "" : " " + stpmiddleName.trim();
			lastName = lastName.trim().equalsIgnoreCase("") ? "" : " " + lastName.trim();

			String fnFirstChar = firstName.trim().equalsIgnoreCase("") ? "" : String.valueOf(firstName.charAt(0)).toUpperCase();
			String lnFirstChar = lastName.trim().equalsIgnoreCase("") ? "" : String.valueOf(lastName.charAt(0)).toUpperCase();
			String lastCharacter = lastName.trim().equalsIgnoreCase("") ? "" : " " + String.valueOf(lastName.charAt(0)).toUpperCase();

			String custName = (firstName + middleName + lastName).trim();
			JSONArray jsonArray = jsonObject.getJSONArray("member_related_accounts");
			String nickName = jsonArray.getJSONObject(0).getString("name").trim();

			variables.put("ACTUAL_CUST_NAME", custName);
			if (!accountId.trim().equalsIgnoreCase(nickName)) {
				custName = nickName;
			}
			String stpcustName = (firstName + lastName).trim();
			String custShortenedName = (fnFirstChar + lnFirstChar + firstName + lastCharacter + ".").trim();
			variables.put("CUST_NAME", custName);
			variables.put("STP_CUST_NAME", stpcustName);
			variables.put("CUST_SHORTENED_NAME", custShortenedName);
			variables.put("NICK_NAME", nickName);
		} else {
			String companyName = jsonObject.getString("company_name");
			variables.put("CUST_NAME", companyName);
			variables.put("ACTUAL_CUST_NAME", companyName);

			String firstName = jsonObject.getString("first_name");
			String stpmiddleName = jsonObject.getString("middle_name");
			String lastName = jsonObject.getString("last_name");

			firstName = firstName.trim().equalsIgnoreCase("") ? "" : firstName.trim();
			stpmiddleName = stpmiddleName.trim().equalsIgnoreCase("") ? "" : " " + stpmiddleName.trim();
			lastName = lastName.trim().equalsIgnoreCase("") ? "" : " " + lastName.trim();

			JSONArray jsonArray = jsonObject.getJSONArray("member_related_accounts");
			String nickName = jsonArray.getJSONObject(0).getString("name").trim();

			String stpcustName = (firstName + stpmiddleName + lastName).trim();

			variables.put("STP_CUST_NAME", stpcustName);
			variables.put("CUST_SHORTENED_NAME", companyName);
			variables.put("NICK_NAME", nickName);
		}

		JSONArray jsonArray = jsonObject.getJSONArray("member_related_accounts");
		Dictionary.put("associatedCount", String.valueOf(jsonArray.length()));
		for (int i = 0; i < jsonArray.length(); i++) {
			String memberId = jsonArray.getJSONObject(i).getString("member_id");
			String accountId1 = memberId.split("\\.")[1].trim();
			Dictionary.put("memberId" + i, memberId);
			Dictionary.put("accId" + i, accountId1);
			Dictionary.put("name" + i, jsonArray.getJSONObject(i).getString("name").trim());
			Dictionary.put("name_" + accountId1, jsonArray.getJSONObject(i).getString("name").trim());
		}
		return variables;
	}

	public boolean canUserEditPassword() throws Exception {
		String accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		JSONObject jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id"), accessToken);
		boolean can_edit_password = jsonObject.getBoolean("can_edit_password");
		return can_edit_password;
	}

	public boolean canUserEditPassword(String email, String pass) throws Exception {
		String accessToken = getAccessToken(email, pass);
		getAccountId(accessToken);
		JSONObject jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id"), accessToken);
		boolean can_edit_password = jsonObject.getBoolean("can_edit_password");
		return can_edit_password;
	}

	public String getClaimTicketID() throws Exception {
		String ticket_id = "";
		String accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		JSONObject jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/transfers", accessToken);
		JSONArray transfers = (JSONArray) jsonObject.get("transfers");
		JSONObject data = (JSONObject) transfers.get(0);
		JSONArray sections = (JSONArray) data.get("sections");
		JSONObject row = (JSONObject) sections.get(0);
		JSONArray rows = (JSONArray) row.get("rows");
		JSONObject ticket = (JSONObject) rows.get(0);
		JSONArray tickets = (JSONArray) ticket.get("tickets");
		JSONObject tic = (JSONObject) tickets.get(0);
		ticket_id = tic.getString("ticket_id");
		return ticket_id;
	}

	public String getTransferID() throws Exception {
		String accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		JSONObject jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/transfers", accessToken);
		JSONArray transfers = (JSONArray) jsonObject.get("transfers");
		JSONObject data = (JSONObject) transfers.get(0);
		String transfer_id = data.getString("transfer_id");
		return transfer_id;
	}

	public String[] getTransferID(String[] ticketId) throws Exception {
		String accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		JSONObject jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/transfers", accessToken);
		JSONArray transfers = (JSONArray) jsonObject.get("transfers");
		List<String> transferIds = new ArrayList<String>();

		for (int m = 0; m < ticketId.length; m++) {
			boolean success = false;
			for (int i = 0; i < transfers.length(); i++) {
				JSONObject transfer = transfers.getJSONObject(i);
				String eventId = ticketId[m].split("\\.")[0];
				if (transfer.getJSONObject("event").getInt("event_id") == Integer.valueOf(eventId)) {
					JSONArray sections = (JSONArray) transfer.get("sections");
					for (int j = 0; j < sections.length(); j++) {
						JSONObject row = (JSONObject) sections.get(j);
						JSONArray rows = (JSONArray) row.get("rows");
						for (int k = 0; k < rows.length(); k++) {
							JSONObject ticket = (JSONObject) rows.get(k);
							JSONArray tickets = (JSONArray) ticket.get("tickets");
							for (int l = 0; l < tickets.length(); l++) {
								JSONObject jticket = (JSONObject) tickets.get(l);
								String ticket_id = jticket.getString("ticket_id");
								if (ticket_id.toString().equals(ticketId[m])) {
									transferIds.add(transfer.getString("transfer_id"));
									success = true;
									break;
								}
							}
							if (success)
								break;
						}
						if (success)
							break;
					}
				}
				if (success)
					break;
			}
		}

		return transferIds.toArray(new String[transferIds.size()]);
	}

	public String[] getTransferID(String email, String pass, String[] ticketId) throws Exception {
		String accessToken = getAccessToken(email, pass);
		getAccountId(accessToken);
		Dictionary.put("AccessToken", accessToken);
		JSONObject jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/transfers", accessToken);
		JSONArray transfers = (JSONArray) jsonObject.get("transfers");
		List<String> transferIds = new ArrayList<String>();

		for (int m = 0; m < ticketId.length; m++) {
			boolean success = false;
			for (int i = 0; i < transfers.length(); i++) {
				JSONObject transfer = transfers.getJSONObject(i);
				String eventId = ticketId[m].split("\\.")[0];
				if (transfer.getJSONObject("event").getInt("event_id") == Integer.valueOf(eventId)) {
					JSONArray sections = (JSONArray) transfer.get("sections");
					for (int j = 0; j < sections.length(); j++) {
						JSONObject row = (JSONObject) sections.get(j);
						JSONArray rows = (JSONArray) row.get("rows");
						for (int k = 0; k < rows.length(); k++) {
							JSONObject ticket = (JSONObject) rows.get(k);
							JSONArray tickets = (JSONArray) ticket.get("tickets");
							for (int l = 0; l < tickets.length(); l++) {
								JSONObject jticket = (JSONObject) tickets.get(l);
								String ticket_id = jticket.getString("ticket_id");
								if (ticket_id.toString().equals(ticketId[m])) {
									transferIds.add(transfer.getString("transfer_id"));
									success = true;
									break;
								}
							}
							if (success)
								break;
						}
						if (success)
							break;
					}
				}
				if (success)
					break;
			}
		}

		return transferIds.toArray(new String[transferIds.size()]);
	}

	public Integer[] getPostingId(String[] ticketId, boolean... needAccessToken) throws Exception {
		assert needAccessToken.length <= 1;
		boolean needtoken = needAccessToken.length > 0 ? needAccessToken[0] : true;
		if (needtoken) {
			if (emailaddress.equals("")) {
				emailaddress = Dictionary.get("ASSOCIATED_ACC_EMAIL_ADDRESS");
				password = Dictionary.get("ASSOCIATED_ACC_PASSWORD");
			}
			accessToken = getAccessToken(emailaddress, password);
			getAccountId(accessToken);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.equalsIgnoreCase("")) {
				accessToken = getAccessToken(emailaddress, password);
				getAccountId(accessToken);
			}
		}
//	  accessToken = getAccessToken(emailaddress, password);
//	  getAccountId(accessToken);
		JSONObject jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/postings", accessToken);
		JSONArray postings = (JSONArray) jsonObject.get("postings");

		List<Integer> postingIds = new ArrayList<Integer>();

		for (int m = 0; m < ticketId.length; m++) {
			boolean success = false;
			for (int i = 0; i < postings.length(); i++) {
				JSONObject posting = postings.getJSONObject(i);
				String eventId = ticketId[m].split("\\.")[0];
				if (posting.getJSONObject("event").getInt("event_id") == Integer.valueOf(eventId)) {
					JSONArray sections = (JSONArray) posting.get("sections");
					for (int j = 0; j < sections.length(); j++) {
						JSONObject row = (JSONObject) sections.get(j);
						JSONArray rows = (JSONArray) row.get("rows");
						for (int k = 0; k < rows.length(); k++) {
							JSONObject ticket = (JSONObject) rows.get(k);
							JSONArray tickets = (JSONArray) ticket.get("tickets");
							for (int l = 0; l < tickets.length(); l++) {
								JSONObject jticket = (JSONObject) tickets.get(l);
								String ticket_id = jticket.getString("ticket_id");
								if (ticket_id.toString().equals(ticketId[m])) {
									postingIds.add(posting.getInt("posting_id"));
									success = true;
									break;
								}
							}
							if (success)
								break;
						}
						if (success)
							break;
					}
				}
				if (success)
					break;
			}
		}

		return postingIds.toArray(new Integer[postingIds.size()]);
	}

	public Integer[] getPostingId(String email, String pass, String[] ticketId, boolean... needAccessToken) throws Exception {
		emailaddress = email;
		password = pass;
		assert needAccessToken.length <= 1;
		boolean needtoken = needAccessToken.length > 0 ? needAccessToken[0] : true;
		if (needtoken) {
			if (emailaddress.equals("")) {
				emailaddress = Dictionary.get("ASSOCIATED_ACC_EMAIL_ADDRESS");
				password = Dictionary.get("ASSOCIATED_ACC_PASSWORD");
			}
			accessToken = getAccessToken(emailaddress, password);
			getAccountId(accessToken);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.equalsIgnoreCase("")) {
				accessToken = getAccessToken(emailaddress, password);
				getAccountId(accessToken);
			}
		}
//	  accessToken = getAccessToken(emailaddress, password);
//	  getAccountId(accessToken);
		JSONObject jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/postings", accessToken);
		JSONArray postings = (JSONArray) jsonObject.get("postings");

		List<Integer> postingIds = new ArrayList<Integer>();

		for (int m = 0; m < ticketId.length; m++) {
			boolean success = false;
			for (int i = 0; i < postings.length(); i++) {
				JSONObject posting = postings.getJSONObject(i);
				String eventId = ticketId[m].split("\\.")[0];
				if (posting.getJSONObject("event").getInt("event_id") == Integer.valueOf(eventId)) {
					JSONArray sections = (JSONArray) posting.get("sections");
					for (int j = 0; j < sections.length(); j++) {
						JSONObject row = (JSONObject) sections.get(j);
						JSONArray rows = (JSONArray) row.get("rows");
						for (int k = 0; k < rows.length(); k++) {
							JSONObject ticket = (JSONObject) rows.get(k);
							JSONArray tickets = (JSONArray) ticket.get("tickets");
							for (int l = 0; l < tickets.length(); l++) {
								JSONObject jticket = (JSONObject) tickets.get(l);
								String ticket_id = jticket.getString("ticket_id");
								if (ticket_id.toString().equals(ticketId[m])) {
									postingIds.add(posting.getInt("posting_id"));
									success = true;
									break;
								}
							}
							if (success)
								break;
						}
						if (success)
							break;
					}
				}
				if (success)
					break;
			}
		}

		return postingIds.toArray(new Integer[postingIds.size()]);
	}

	public String[] getTransferID(int[] eventId, boolean... extras) throws Exception {
		assert extras.length <= 1;
		boolean needaccesstoken = extras.length > 0 ? extras[0] : true;
		if (needaccesstoken) {
			accessToken = getAccessToken(emailaddress, password);
			getAccountId(accessToken);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.equalsIgnoreCase("")) {
				accessToken = getAccessToken(emailaddress, password);
				getAccountId(accessToken);
			}
		}
		JSONObject jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/transfers", accessToken);
		System.out.println("transferids  " + jsonObject);
		JSONArray transfers = (JSONArray) jsonObject.get("transfers");
		List<String> transferIds = new ArrayList<String>();

		for (int m = 0; m < eventId.length; m++) {
			for (int i = 0; i < transfers.length(); i++) {
				JSONObject transfer = transfers.getJSONObject(i);
				if (transfer.getJSONObject("event").getInt("event_id") == eventId[m]) {
					transferIds.add(transfer.getString("transfer_id"));
					break;
				}
			}
		}

		return transferIds.toArray(new String[transferIds.size()]);
	}

	public String[] getTransferTicketIDs(String transferId) throws Exception {
		String accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		JSONObject jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/transfers", accessToken);
		JSONArray transfers = (JSONArray) jsonObject.get("transfers");
		List<String> ticket_ids = new ArrayList<String>();
		for (int i = 0; i < transfers.length(); i++) {
			JSONObject trans = (JSONObject) transfers.get(i);
			String transfer_id = trans.getString("transfer_id");
			if (transfer_id.equals(transferId)) {
				JSONArray sections = (JSONArray) trans.get("sections");
				for (int j = 0; j < sections.length(); j++) {
					JSONObject row = (JSONObject) sections.get(j);
					JSONArray rows = (JSONArray) row.get("rows");
					for (int k = 0; k < rows.length(); k++) {
						JSONObject ticket = (JSONObject) rows.get(k);
						JSONArray tickets = (JSONArray) ticket.get("tickets");
						for (int l = 0; l < tickets.length(); l++) {
							JSONObject tic = (JSONObject) tickets.get(l);
							String ticket_id = tic.getString("ticket_id");
							ticket_ids.add(ticket_id);
						}
					}
				}
			}
		}
		return ticket_ids.toArray(new String[ticket_ids.size()]);
	}

	public int getTicketsCount(int eventId, String accessToken) throws Exception {
		int tkt_count = 0;
		JSONObject jsonObject = getSummaryForSpecificEvent(String.valueOf(eventId));
		JSONObject js = jsonObject.getJSONObject("totals");
		tkt_count = js.getInt("tickets");
		return tkt_count;
	}

	public Event getSections(int eventId, String eventName, String accessToken) throws Exception {
		JSONObject jsonObject, jsonObjectSection;

		try {
			jsonObjectSection = getAllSectionsForAnyEvent("" + eventId + "");
		} catch (Exception ex) {
			return null;
		}
		JSONObject embedded = (JSONObject) jsonObjectSection.get("_embedded");
		JSONArray sections = embedded.getJSONArray("sections");


		List<Section> lsections = new ArrayList<Section>();
		int ticketcount = 0;
		int cantransferticketcount = 0;
		int canresaleticketcount = 0;
		int canrenderticketcount = 0;
		int canrenderfileticketcount = 0;
		int canrenderbarcodeticketcount = 0;
		int canrenderpassbookticketcount = 0;
		int candonatecharityticketcount = 0;
		int haspendinginvoiceticketcount = 0;
		int ismobileenabledticketcount = 0;
		for (int j = 0; j < sections.length(); j++) {

			JSONObject section = (JSONObject) sections.get(j);
			String section_label = section.getString("label").trim();
			String section_name = section.getString("name").trim();
			String sectionId = "" + section.getInt("id") + "";
			String sectionFormattedLabel = section.getString("formattedLabel").trim();

			try {
				jsonObject = getSectionDetailsForEvent("" + eventId + "", sectionId);
				System.out.println(jsonObject);
			} catch (Exception ex) {
				return null;
			}

			JSONArray rows = (JSONArray) jsonObject.get("rows");
			int persectionticketcount = 0;
			LinkedList<Row> lrows = new LinkedList<Row>();

			for (int k = 0; k < rows.length(); k++) {
				JSONObject ticket = (JSONObject) rows.get(k);
				String row_name = ticket.getString("name").trim();
				String rowFormattedLabel = ticket.getString("formattedLabel").trim();
				JSONArray tickets = (JSONArray) ticket.get("seats");
				persectionticketcount += tickets.length();
				String row_label = ticket.has("label") ? ticket.getString("label") : "";
				List<Ticket> ltickets = new ArrayList<Ticket>();
				for (int l = 0; l < tickets.length(); l++) {
					JSONObject seat = (JSONObject) tickets.get(l);
					JSONObject actions = (JSONObject) seat.get("actions");

					String seat_label = seat.has("label") ? seat.getString("label") : "";
					String formattedLabel = seat.getString("formattedLabel");
					String ticketId = seat.getString("id");
					can_transfer = actions.has("canTransfer") ? actions.getBoolean("canTransfer") : false;
					can_resale = actions.has("canResale") ? actions.getBoolean("canResale") : false;
					can_render = actions.has("can_render") ? actions.getBoolean("can_render") : false;
					can_render_file = actions.has("canRenderFile") ? actions.getBoolean("canRenderFile") : false;
					can_render_barcode = actions.has("canRenderBarcode") ? actions.getBoolean("canRenderBarcode") : false;
					can_render_passbook = actions.has("canRenderPassbook") ? actions.getBoolean("canRenderPassbook") : false;
					can_donate_charity = actions.has("canDonateCharity") ? actions.getBoolean("canDonateCharity") : false;
					has_pending_invoice = actions.has("has_pending_invoice") ? actions.getBoolean("has_pending_invoice") : false;
					is_mobile_enabled = actions.has("is_mobile_enabled") ? actions.getBoolean("is_mobile_enabled") : false;
					is_deferred_delivery = actions.has("is_deferred_delivery") ? actions.getBoolean("is_deferred_delivery") : false;
					Ticket _ticket = new Ticket("" + seat.getInt("number") + "", seat_label, can_transfer, can_resale, can_render, can_render_file, can_render_barcode, can_render_passbook, can_donate_charity, has_pending_invoice, is_mobile_enabled, formattedLabel, ticketId);
					ltickets.add(_ticket);
					if (can_transfer)
						cantransferticketcount++;
					if (can_resale)
						canresaleticketcount++;
					if (!is_deferred_delivery) {
						if (can_render)
							canrenderticketcount++;
						if (can_render_file)
							canrenderfileticketcount++;
						if (can_render_barcode)
							canrenderbarcodeticketcount++;
						if (can_render_passbook)
							canrenderpassbookticketcount++;
					}
					if (can_donate_charity)
						candonatecharityticketcount++;
					if (has_pending_invoice)
						haspendinginvoiceticketcount++;
					if (is_mobile_enabled)
						ismobileenabledticketcount++;

				}
				Row crow = new Row(row_name, row_label, ltickets, rowFormattedLabel);
				lrows.add(crow);

			}
			Section sect = new Section(section_name, section_label, persectionticketcount, lrows, sectionFormattedLabel);
			lsections.add(sect);
			ticketcount += persectionticketcount;
		}

		Event event = new Event(eventId, eventName, ticketcount, cantransferticketcount, canresaleticketcount, canrenderticketcount, canrenderfileticketcount, canrenderbarcodeticketcount, canrenderpassbookticketcount, candonatecharityticketcount, haspendinginvoiceticketcount, ismobileenabledticketcount, lsections);
		return event;
	}

	public Boolean[] getTicketFlags(String ticketId, String email, String pass, boolean... needAccessToken) throws Exception {
		if (email.trim().equalsIgnoreCase("") && pass.trim().equalsIgnoreCase("")) {
			email = emailaddress;
			pass = password;
		}
		assert needAccessToken.length <= 1;
		boolean needToken = needAccessToken.length > 0 ? needAccessToken[0] : true;
		if (needToken) {
			accessToken = getAccessToken(email, pass);
			getAccountId(accessToken);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.equalsIgnoreCase("")) {
				accessToken = getAccessToken(email, pass);
				getAccountId(accessToken);
			}
		}
		String[] ticket_id = ticketId.split("\\.");
		int eventId = Integer.valueOf(ticket_id[0].trim());
		String section_name = ticket_id[1].replaceAll("%20", " ").trim();
		JSONObject jsonObject, jsonObjectSection;
		try {
			jsonObjectSection = getAllSectionsForAnyEvent("" + eventId + "");
		} catch (Exception ex) {
			return null;
		}
		JSONObject embedded = (JSONObject) jsonObjectSection.get("_embedded");
		JSONArray sections = embedded.getJSONArray("sections");
		String sectionId = "";
		for (int j = 0; j < sections.length(); j++) {
			JSONObject section = (JSONObject) sections.get(j);
			if (section_name.equalsIgnoreCase(section.getString("name"))) {
				sectionId = "" + section.getInt("id") + "";
				try {
					jsonObject = getSectionDetailsForEvent("" + eventId + "", sectionId);
				} catch (Exception ex) {
					return null;
				}
				JSONArray rows = (JSONArray) jsonObject.get("rows");
				for (int k = 0; k < rows.length(); k++) {
					JSONObject ticket = (JSONObject) rows.get(k);
					JSONArray tickets = (JSONArray) ticket.get("seats");
					for (int l = 0; l < tickets.length(); l++) {
						JSONObject seat = (JSONObject) tickets.get(l);
						if (!seat.getString("id").equalsIgnoreCase(ticketId)) {
							continue;
						} else {
							JSONObject actions = (JSONObject) seat.get("actions");
							return new Boolean[]{actions.has("canTransfer") && actions.getBoolean("canTransfer"), actions.has("canResale") && actions.getBoolean("canResale"), actions.has("canRender") && actions.getBoolean("canRender"), actions.has("canRenderFile") && actions.getBoolean("canRenderFile"), actions.has("canRenderBarcode") && actions.getBoolean("canRenderBarcode"), actions.has("canRenderPassbook") && actions.getBoolean("canRenderPassbook"), actions.has("canDonateCharity") && actions.getBoolean("canDonateCharity")};
						}
					}
				}
			}
		}
		return null;
	}

	public String getTicketIdPendingAction(int eventId, String accessToken, String type, String state) {
		String ticketId = null;

		JSONObject jsonObject;
		try {
			jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/inventory/search?event_id=" + eventId, accessToken);
		} catch (Exception ex) {
			return null;
		}

		JSONArray inventory_items = (JSONArray) jsonObject.get("inventory_items");
		for (int i = 0; i < inventory_items.length(); i++) {
			JSONObject data = (JSONObject) inventory_items.get(i);
			String eventname = data.getJSONObject("event").getString("name");
			Dictionary.put("eventName", eventname);
			JSONArray sections = (JSONArray) data.get("sections");
			for (int j = 0; j < sections.length(); j++) {
				JSONObject row = (JSONObject) sections.get(j);
				JSONArray rows = (JSONArray) row.get("rows");
				for (int k = 0; k < rows.length(); k++) {
					JSONObject ticket = (JSONObject) rows.get(k);
					JSONArray tickets = (JSONArray) ticket.get("tickets");
					for (int l = 0; l < tickets.length(); l++) {
						JSONObject jticket = (JSONObject) tickets.get(l);
						if (jticket.has("pending_action")) {
							JSONObject pendingAction = jticket.getJSONObject("pending_action");
							if ((pendingAction.get("type").equals(type)) && (pendingAction.get("state").equals(state))) {
								ticketId = jticket.getString("ticket_id");
								return ticketId;
							}
						}
					}
				}
			}
		}
		return ticketId;
	}

	public void getTicketIdCount(String eventId) throws Exception {

		JSONObject jsonObject = getSummaryForSpecificEvent(eventId);
		JSONObject totals = jsonObject.getJSONObject("totals");
		JSONObject transfer = totals.getJSONObject("transfers");
		JSONObject resale = totals.getJSONObject("resales");

		int activeTicket;
		int pendingTicket;
		int completedTicket;
		activeTicket = totals.getInt("tickets") - totals.getInt("donatedToCharity") - transfer.getInt("pending") - transfer.getInt("completed") - resale.getInt("posted") - resale.getInt("completed");
		pendingTicket = transfer.getInt("pending") + resale.getInt("posted");
		completedTicket = totals.getInt("donatedToCharity") + transfer.getInt("completed") + resale.getInt("completed");

		Dictionary.put("ActiveTicketCount", "" + activeTicket + "");
		Dictionary.put("PendingTicketCount", "" + pendingTicket + "");
		Dictionary.put("CompletedTicketCount", "" + completedTicket + "");

	}

	public String[] getTicketIds(int eventId, String accessToken, String flag, boolean multiple) throws Exception {
		JSONObject jsonObject, jsonObjectSection;

		try {
			//jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/inventory/search?event_id=" + eventId, accessToken);
			jsonObjectSection = getAllSectionsForAnyEvent("" + eventId + "");
		} catch (Exception ex) {
			return null;
		}

		JSONObject embedded = (JSONObject) jsonObjectSection.get("_embedded");
		JSONArray sections = embedded.getJSONArray("sections");

		for (int j = 0; j < sections.length(); j++) {
			JSONObject section = (JSONObject) sections.get(j);
			String formatted_section_name = section.getString("formattedLabel").trim();
			String section_label = section.getString("label").trim();

			String sectionId = "" + section.getInt("id") + "";

			try {
				//jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/inventory/search?event_id=" + eventId, accessToken);
				jsonObject = getSectionDetailsForEvent("" + eventId + "", sectionId);
				System.out.println(jsonObject);
			} catch (Exception ex) {
				return null;
			}

			JSONArray rows = (JSONArray) jsonObject.get("rows");
			List<String> ticketIds = new ArrayList<String>();
			boolean status = false;

			for (int k = 0; k < rows.length(); k++) {
				JSONObject ticket = (JSONObject) rows.get(k);
				String formatted_row_name = ticket.getString("formattedLabel").trim();
				String row_name = ticket.getString("name").trim();
				JSONArray tickets = (JSONArray) ticket.get("seats");

				for (int l = 0; l < tickets.length(); l++) {


					JSONObject seat = (JSONObject) tickets.get(l);

					JSONObject actions = (JSONObject) seat.get("actions");


					switch (flag.trim().toUpperCase()) {
						case "CAN_TRANSFER":
							if (actions.has("canTransfer") && actions.getBoolean("canTransfer"))
								status = true;
							break;
						case "PRICE":
							if (actions.getBoolean("canTransfer") && seat.has("priceType")) {
								JSONObject jo = seat.getJSONObject("priceType").getJSONObject("price");
								Dictionary.put("Currency", jo.get("currency").toString());
								Dictionary.put("Price", jo.get("amount").toString());
								status = true;
								if (jo.get("amount").toString().equalsIgnoreCase("0") || jo.get("amount").toString().equalsIgnoreCase("") || jo.get("amount").toString().equalsIgnoreCase("null") || jo.get("amount") == null) {
									System.out.println("PRICE IS ZERO...SKIPPING TO NEXT EVENT");
									status = false;
									continue;
								}
							}
							break;
						case "CAN_RESALE":
							if (actions.has("canResale") && actions.getBoolean("canResale"))
								status = true;
							break;
						case "CAN_RENDER":
							if (actions.has("can_render") && actions.getBoolean("can_render") && actions.has("can_render_file") && actions.getBoolean("can_render_file"))
								status = true;
							break;
						case "CAN_RENDER_FILE":
							if (actions.has("canRenderFile") && actions.getBoolean("canRenderFile"))
								status = true;
							break;
						case "CAN_RENDER_BARCODE":
							if (actions.has("canRenderBarcode") && actions.getBoolean("canRenderBarcode") && !actions.getBoolean("is_deferred_delivery") && actions.has("is_mobile_enabled") && actions.getBoolean("is_mobile_enabled"))
								status = true;
							break;
						case "CAN_RENDER_PASSBOOK":
							if (actions.has("canRenderPassbook") && actions.getBoolean("canRenderPassbook"))
								status = true;
							break;
						case "CAN_DONATE_CHARITY":
							if (actions.has("canDonateCharity") && actions.getBoolean("canDonateCharity"))
								status = true;
							break;
						case "HAS_PENDING_INVOICE":
							if (actions.has("has_pending_invoice") && actions.getBoolean("has_pending_invoice"))
								status = true;
							break;
						case "IS_MOBILE_ENABLED":
							if (actions.has("is_mobile_enabled") && actions.getBoolean("is_mobile_enabled"))
								status = true;
							break;
						case "CANNOT_RENDER_FILES":
							if (actions.has("canRenderFile") && !actions.getBoolean("canRenderFile") && !actions.getBoolean("has_pending_invoice") && (!actions.has("pending_action") || (actions.has("pending_action") && actions.getJSONObject("pending_action").getString("type").trim().equalsIgnoreCase("charity"))))
								status = true;
							break;
						case "DEFERRED_DELIVERY":
							if (actions.has("is_deferred_delivery") && actions.getBoolean("is_deferred_delivery") && actions.has("can_render_file") && actions.getBoolean("can_render_file"))
								status = true;
							break;
						case "NO_DEFERRED_DELIVERY":
							if (actions.has("is_deferred_delivery") && !actions.getBoolean("is_deferred_delivery") && actions.has("can_render_file") && actions.getBoolean("can_render_file"))
								status = true;
							break;
					}

					if (status) {
						ticketIds.add(seat.getString("id"));
						Dictionary.put("FORMATTED_SECTION_LABEL_" + seat.getString("id"), section_label);
						Dictionary.put("FORMATTED_SECTION_NAME_" + seat.getString("id"), formatted_section_name);
						Dictionary.put("FORMATTED_ROW_NAME_" + seat.getString("id"), formatted_row_name);
						Dictionary.put("FORMATTED_SEAT_NAME_" + seat.getString("id"), seat.getString("formattedLabel"));
						Dictionary.put("FORMATTED_SEAT_LABEL_" + seat.getString("id"), seat.has("label") ? seat.getString("label") : "");
						Dictionary.put("ROW_NAME_" + seat.getString("id"), row_name);

						if (!multiple) {
							Dictionary.put("entryGate", seat.has("gatePortalLabel") ? seat.getString("gatePortalLabel").trim() : "");
							return ticketIds.toArray(new String[ticketIds.size()]);
						}
						status = false;
					}
				}
			}
			if (ticketIds.size() > 1)
				return ticketIds.toArray(new String[ticketIds.size()]);
			else
				status = false;
		}
		return null;
	}

	public String[] getRelevantEventTicketIds(int eventId, String accessToken, String flag, boolean multiple) throws Exception {
		JSONObject jsonObject, jsonObjectSection;

		try {
			//jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/inventory/search?event_id=" + eventId, accessToken);
			jsonObjectSection = getAllSectionsForAnyEvent("" + eventId + "");
		} catch (Exception ex) {
			return null;
		}

		JSONObject embedded = (JSONObject) jsonObjectSection.get("_embedded");
		JSONArray sections = embedded.getJSONArray("sections");

		for (int j = 0; j < sections.length(); j++) {
			JSONObject section = (JSONObject) sections.get(j);
			String sectionId = "" + section.getInt("id") + "";

			try {
				//jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/inventory/search?event_id=" + eventId, accessToken);
				jsonObject = getSectionDetailsForEvent("" + eventId + "", sectionId);
				System.out.println(jsonObject);
			} catch (Exception ex) {
				return null;
			}

			JSONArray rows = (JSONArray) jsonObject.get("rows");
			List<String> ticketIds = new ArrayList<String>();
			boolean status = false;

			for (int k = 0; k < rows.length(); k++) {
				JSONObject ticket = (JSONObject) rows.get(k);
				JSONArray tickets = (JSONArray) ticket.get("seats");

				for (int l = 0; l < tickets.length(); l++) {

					JSONObject seat = (JSONObject) tickets.get(l);

					JSONObject actions = (JSONObject) seat.get("actions");

					switch (flag.trim().toUpperCase()) {
						case "CAN_TRANSFER":
							if (actions.has("canTransfer") && actions.getBoolean("canTransfer"))
								status = true;
							break;
						case "CAN_RESALE":
							if (actions.has("canResale") && actions.getBoolean("canResale"))
								status = true;
							break;
						case "CAN_RENDER":
							if (actions.has("can_render") && actions.getBoolean("can_render") && actions.has("can_render_file") && actions.getBoolean("can_render_file"))
								status = true;
							break;
						case "CAN_RENDER_FILE":
							if (actions.has("canRenderFile") && actions.getBoolean("canRenderFile"))
								status = true;
							break;
						case "CAN_RENDER_BARCODE":
							if (actions.has("canRenderBarcode") && actions.getBoolean("canRenderBarcode") && !actions.getBoolean("is_deferred_delivery") && actions.has("is_mobile_enabled") && actions.getBoolean("is_mobile_enabled"))
								status = true;
							break;
						case "CAN_RENDER_PASSBOOK":
							if (actions.has("canRenderPassbook") && actions.getBoolean("canRenderPassbook"))
								status = true;
							break;
						case "CAN_DONATE_CHARITY":
							if (actions.has("canDonateCharity") && actions.getBoolean("canDonateCharity"))
								status = true;
							break;
						case "HAS_PENDING_INVOICE":
							if (actions.has("has_pending_invoice") && actions.getBoolean("has_pending_invoice"))
								status = true;
							break;
						case "IS_MOBILE_ENABLED":
							if (actions.has("is_mobile_enabled") && actions.getBoolean("is_mobile_enabled"))
								status = true;
							break;
						case "CANNOT_RENDER_FILES":
							if (actions.has("canRenderFile") && !actions.getBoolean("canRenderFile") && !actions.getBoolean("has_pending_invoice") && (!actions.has("pending_action") || (actions.has("pending_action") && actions.getJSONObject("pending_action").getString("type").trim().equalsIgnoreCase("charity"))))
								status = true;
							break;
						case "DEFERRED_DELIVERY":
							if (actions.has("is_deferred_delivery") && actions.getBoolean("is_deferred_delivery") && actions.has("can_render_file") && actions.getBoolean("can_render_file"))
								status = true;
							break;
					}
					if (status) {
						ticketIds.add(seat.getString("id"));
						if (!multiple) {
							return ticketIds.toArray(new String[ticketIds.size()]);
						} else {
							if (ticketIds.size() > 1)
								return ticketIds.toArray(new String[ticketIds.size()]);
						}
						status = false;
					}
				}
			}
		}
		return null;
	}

	public List<List<String>> getTickets(int eventId, HashMap<Integer, Event> events) throws Exception {
		boolean mobile = (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone");
		List<List<String>> result = new ArrayList<List<String>>();
		List<List<String>> cannotRenderBarcodeResult = new ArrayList<List<String>>();
		List<Section> sections = events.get(eventId).getSection();
		for (int i = 0; i < sections.size(); i++) {
			List<Row> rows = sections.get(i).getRow();
			for (int j = 0; j < rows.size(); j++) {
				List<Ticket> tickets = rows.get(j).getTickets();
				for (int k = 0; k < tickets.size(); k++) {
					List<String> section = new ArrayList<String>();
					if (sections.get(i).getSectionLabel().trim().equalsIgnoreCase("[S]")) {
						section.add("");
					} else
						section.add(String.valueOf(sections.get(i).getSectionNumber()));
					if (rows.get(j).getRowLabel().trim().equalsIgnoreCase("[S]")) {
						section.add("");
					} else
						section.add(String.valueOf(rows.get(j).getRowName()));
					if (tickets.get(k).getSeatLabel().trim().equalsIgnoreCase("[S]")) {
						section.add("");
					} else
						section.add(String.valueOf(tickets.get(k).getSeatNumber()));
					if ((mobile && tickets.get(k).canRenderBarcodeFlag()) || !mobile)
						result.add(section);
					else
						cannotRenderBarcodeResult.add(section);
				}
			}
		}

		if (mobile) {
			if (cannotRenderBarcodeResult.size() > 0)
				result.addAll(cannotRenderBarcodeResult);
		}

		return result;
	}

	public List<List<String>> getTop3TicketIds(int eventId, HashMap<Integer, Event> events) throws Exception {
		boolean mobile = (driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone");
		List<List<String>> result = new ArrayList<List<String>>();
		List<Section> sections = events.get(eventId).getSection();
		for (int i = 0; i < sections.size() && result.size() < 3; i++) {
			List<Row> rows = sections.get(i).getRow();
			for (int j = 0; j < rows.size() && result.size() < 3; j++) {
				List<Ticket> tickets = rows.get(j).getTickets();
				for (int k = 0; k < tickets.size() && result.size() < 3; k++) {
					List<String> section = new ArrayList<String>();
					section.add(String.valueOf(sections.get(i).getSectionNumber()));
					section.add(String.valueOf(rows.get(j).getRowName()));
					section.add(String.valueOf(tickets.get(k).getSeatNumber()));
					if ((mobile && tickets.get(k).canRenderBarcodeFlag()))
						result.add(section);
				}
			}
		}

		return result;
	}

	public HashMap<Integer, Event> getEventIdWithTktsDetails() throws Exception {
		String accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		JSONObject jsonObject = getAllEventsForMember();
		JSONObject embedded = (JSONObject) jsonObject.get("_embedded");
		JSONArray events = embedded.getJSONArray("events");
		HashMap<Integer, Event> eventsDetail = new HashMap<Integer, Event>();
		if (events != null) {
			for (int i = 0; i < events.length(); i++) {
				int eventId = events.getJSONObject(i).getInt("id");
				String eventName = events.getJSONObject(i).getString("name");
				System.out.println("event Id :: " + eventId);
				try {
					Event _event = getSections(eventId, eventName, accessToken);
					System.out.println(_event);
					eventsDetail.put(eventId, _event);
				} catch (Exception ex) {
					ex.printStackTrace();
					continue;
				}
			}
		}
		return eventsDetail;
	}

	public HashMap<Integer, Event> getEventIdWith3RenderTktsDetails() throws Exception {
		String accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		JSONObject jsonObject = getAllEventsForMember();
		JSONObject embedded = (JSONObject) jsonObject.get("_embedded");
		JSONArray events = embedded.getJSONArray("events");
		HashMap<Integer, Event> eventsDetail = new HashMap<Integer, Event>();
		if (events != null) {
			for (int i = 0; i < events.length(); i++) {
				int eventId = events.getJSONObject(i).getInt("id");
				String eventName = events.getJSONObject(i).getString("name");
				System.out.println("event Id :: " + eventId);
				try {
					Event _event = getSections(eventId, eventName, accessToken);
					eventsDetail.put(eventId, _event);
					if (_event.canrenderbarcodetktcount >= 3) {
						Dictionary.put("RENDER_TICKET_EVENT_ID", String.valueOf(eventId));
						break;
					}
				} catch (Exception ex) {
					continue;
				}
			}
		}
		return eventsDetail;
	}

	public HashMap<Integer, Event> getEventIdWithTktsDetails(String email, String pass) throws Exception {
		String accessToken = getAccessToken(email, pass);
		getAccountId(accessToken);
		JSONObject jsonObject = getAllEventsForMember();
		JSONObject embedded = (JSONObject) jsonObject.get("_embedded");
		JSONArray events = embedded.getJSONArray("events");
		HashMap<Integer, Event> eventsDetail = new HashMap<Integer, Event>();
		if (events != null) {
			for (int i = 0; i < events.length(); i++) {
				int eventId = events.getJSONObject(i).getInt("id");
				String eventName = events.getJSONObject(i).getString("name");
				System.out.println("event Id :: " + eventId);
				try {
					Event _event = getSections(eventId, eventName, accessToken);
					eventsDetail.put(eventId, _event);
				} catch (Exception ex) {
					continue;
				}
			}
		}
		return eventsDetail;
	}

	public HashMap<Integer, Event> getTicketsDetails() throws Exception {
		String aToken = Dictionary.get("AccessToken");
		String mem_id = Dictionary.get("member_id");
		JSONObject jsonObject = getAllEventsForMember(mem_id);
		JSONObject embedded = (JSONObject) jsonObject.get("_embedded");
		JSONArray events = embedded.getJSONArray("events");
		HashMap<Integer, Event> eventsDetail = new HashMap<Integer, Event>();
		if (events != null) {
			for (int i = 0; i < events.length(); i++) {
				int eventId = events.getJSONObject(i).getInt("id");
				String eventName = events.getJSONObject(i).getString("name");
				System.out.println("event Id :: " + eventId);
				try {
					Event _event = getSections(eventId, eventName, aToken);
					eventsDetail.put(eventId, _event);
				} catch (Exception ex) {
					continue;
				}
			}
		}
		return eventsDetail;
	}

	public LinkedHashMap<Integer, Event> getTicketsDetails(List<_Event> events, int count) throws Exception {
		LinkedHashMap<Integer, Event> eventsDetail = new LinkedHashMap<Integer, Event>();
		if (events != null) {
			if (count == 0) {
				for (int i = 0; i < events.size(); i++) {
					int eventId = events.get(i).eventId;
					String eventName = events.get(i).event_name;
					System.out.println("event Id :: " + eventId);
					try {
						Event _event = getSections(eventId, eventName, accessToken);
						eventsDetail.put(eventId, _event);
					} catch (Exception ex) {
						continue;
					}
				}
			} else {
				for (int i = 0; i < count; i++) {
					int eventId = events.get(i).eventId;
					String eventName = events.get(i).event_name;
					System.out.println("event Id :: " + eventId);
					try {
						Event _event = getSections(eventId, eventName, accessToken);
						eventsDetail.put(eventId, _event);
					} catch (Exception ex) {
						continue;
					}
				}
			}
		}
		return eventsDetail;
	}

	public List<String> getEventNames(String AcctId) throws Exception {
		List<_Event> _events = getEventDetails(AcctId);
		List<String> eventNames = new ArrayList<>();
		_events.forEach((xx) -> eventNames.add(xx.getEventName()));
		return eventNames;
	}

	public LinkedHashMap<Integer, List<Section>> getAllSectionDetailsWithSorting(List<_Event> events, int numberOfEventsSelected) throws Exception {
		//numberOfEventsSelected should be 0 for getting section details for all the events

		LinkedHashMap<Integer, List<Section>> eventsDetail = new LinkedHashMap<Integer, List<Section>>();
		LinkedHashMap<Integer, Event> ticketDetails = getTicketsDetails(events, numberOfEventsSelected);
		List<Event> e1 = new ArrayList<Event>(ticketDetails.values());
		int count = 0;

		for (int i = 0; i < ticketDetails.size(); i++) {
			int eventId = events.get(i).eventId;

			for (int j = 0; j < e1.size(); j++) {
				if (e1.get(j).id == eventId) {
					eventsDetail.put(eventId, e1.get(j).sections);
					Dictionary.put(count + "TransferCount", "" + e1.get(j).cantransfertktcount + "");
					count++;
					break;
				}
			}
		}
		return eventsDetail;
	}

	public List<_Event> getEventDetails(String email, String pass) throws Exception {
		String accessToken = getAccessToken(email, pass);
		getAccountId(accessToken);
		JSONObject jsonObject = getAllEventsForMember();
		JSONObject embedded = (JSONObject) jsonObject.get("_embedded");
		JSONArray events = embedded.getJSONArray("events");
		System.out.println(jsonObject);
		List<_Event> _events = new ArrayList<_Event>();
		if (events != null) {
			for (int i = 0; i < events.length(); i++) {
				JSONObject event = events.getJSONObject(i);
				String type = event.getString("type");
				if (type.equals("event") || type.equals("parking")) {
					String event_name = event.getString("name");
					int eventId = event.getInt("id");
					JSONObject eventDate = event.getJSONObject("date");
					boolean has_date_override = eventDate.getBoolean("hasDateOverride");
					boolean has_time_override = eventDate.getBoolean("hasTimeOverride");
					String date_override_text = "";
					String time_override_text = "";
					if (has_date_override) {
						date_override_text = eventDate.getString("dateOverrideText");
					}
					if (has_time_override) {
						time_override_text = eventDate.getString("timeOverrideText");
					}
					Date datetime = null;
					Date date = null;
					Date time = null;
					if (eventDate.has("datetime")) {
						SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
						datetime = sm.parse(eventDate.getString("datetime").trim());
					}
					if (!has_date_override) {
						SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
						date = sm.parse(eventDate.getString("date"));
					}
					if (!has_time_override) {
						SimpleDateFormat sm = new SimpleDateFormat("HH:mm:ss");
						time = sm.parse(eventDate.getString("time"));
					}
					_Event _event = new _Event(event_name, datetime, date, time, has_date_override, has_time_override, time_override_text, date_override_text, eventId);
					_events.add(_event);
				}
			}
		}
		Collections.sort(_events);
		return _events;
	}

	public List<_Event> getEventDetails(String member_id) throws Exception {
		String accessToken = Dictionary.get("AccessToken");
		getAccountId(accessToken);
		JSONObject jsonObject = getAllEventsForMember(member_id);
		JSONObject embedded = (JSONObject) jsonObject.get("_embedded");
		JSONArray events = embedded.getJSONArray("events");
		System.out.println(jsonObject);
		List<_Event> _events = new ArrayList<_Event>();
		if (events != null) {
			for (int i = 0; i < events.length(); i++) {
				JSONObject event = events.getJSONObject(i);
				String event_name = event.getString("name");
				int eventId = event.getInt("id");
				JSONObject eventDate = event.getJSONObject("date");
				boolean has_date_override = eventDate.getBoolean("hasDateOverride");
				boolean has_time_override = eventDate.getBoolean("hasTimeOverride");
				String date_override_text = "";
				String time_override_text = "";
				if (has_date_override) {
					date_override_text = eventDate.getString("dateOverrideText");
				}
				if (has_time_override) {
					time_override_text = eventDate.getString("timeOverrideText");
				}
				Date datetime = null;
				Date date = null;
				Date time = null;
				if (eventDate.has("datetime")) {
					SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
					datetime = sm.parse(eventDate.getString("datetime").trim());
				}
				if (!has_date_override) {
					SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
					date = sm.parse(eventDate.getString("date"));
				}
				if (!has_time_override) {
					SimpleDateFormat sm = new SimpleDateFormat("HH:mm:ss");
					time = sm.parse(eventDate.getString("time"));
				}
				_Event _event = new _Event(event_name, datetime, date, time, has_date_override, has_time_override, time_override_text, date_override_text, eventId);
				_events.add(_event);
			}
		}
		Collections.sort(_events);
		for (int i = 0; i < _events.size(); i++) {
			System.out.println(_events.get(i).getEventName() + " " + _events.get(i).getDate() + " " + _events.get(i).getDateOverride() + " " + _events.get(i).getTime() + " " + _events.get(i).getTimeOverride());
		}
		return _events;
	}

	public void deleteFile(File element) {
		if (element.isDirectory()) {
			for (File sub : element.listFiles()) {
				deleteFile(sub);
			}
		}
		element.delete();
	}

	public void renderFile(String[] tickets, boolean... needaccesstoken) throws Exception {
		assert needaccesstoken.length <= 1;
		boolean needtoken = needaccesstoken.length > 0 ? needaccesstoken[0] : true;
		if (needtoken) {
			accessToken = getAccessToken(emailaddress, password);
			getAccountId(accessToken);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.equalsIgnoreCase("")) {
				accessToken = getAccessToken(emailaddress, password);
				getAccountId(accessToken);
			}
		}
		String member_id = Dictionary.get("member_id");
		String tmstr = "";
		String drupalstr = "";
		for (int i = 0; i < tickets.length; i++) {
			tmstr += "&ticket_id=" + tickets[i];
			drupalstr += "ticket_id%5B%5D=" + tickets[i] + "&";
		}
		Object[] obj = utils.get(host + "/api/v1/render/file?member_id=" + member_id + tmstr, new String[]{"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[]{"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accessToken});
		InputStream is = (InputStream) obj[0];
		long size = getDownloadFileSize(is, "pdf");
		Assert.assertTrue(size > 0, "Verify file is downloaded successfully from TM side");
		Set<Cookie> cookies = getDriver().manage().getCookies();
		Iterator<Cookie> iter = cookies.iterator();
		String _cookies = "";
		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("tablet")) {
			while (iter.hasNext()) {
				Cookie cookie = iter.next();
				_cookies += URLEncoder.encode(cookie.getName(), "UTF-8") + "=" + URLEncoder.encode(cookie.getValue(), "UTF-8") + ";";
			}
			is = utils.post(Environment.get("APP_URL") + "/user/login?_format=json", "{\"name\":\"" + emailaddress + "\",\"pass\":\"" + password + "\",\"remember_me\":0}", new String[]{"accept", "accept-encoding", "accept-language", "content-type", "user-agent"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "application/json", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"});
			String __cookies = Dictionary.get("RESPONSE_HEADERS").substring(Dictionary.get("RESPONSE_HEADERS").indexOf("Set-Cookie")).split("\\\\n")[0].replace("Set-Cookie : ", "");
			String sessionCookie = utils.getSessionCookie(__cookies);
			_cookies += sessionCookie;
		} else {
			while (iter.hasNext()) {
				Cookie cookie = iter.next();
				_cookies += cookie.getName() + "=" + cookie.getValue() + ";";
			}
		}
		if (!_cookies.trim().equalsIgnoreCase("")) {
			obj = utils.get(Environment.get("APP_URL") + "/api/render-ticket/file?" + drupalstr + "_format=json", new String[]{"cookie", "accept", "accept-encoding", "accept-language", "user-agent"}, new String[]{_cookies, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36"});
			is = (InputStream) obj[0];
			size = getDownloadFileSize(is, "pdf");
			Assert.assertTrue(size > 0, "Verify file is downloaded successfully from app side");
		}
	}

	public void renderFilefromTMSide(String[] tickets, boolean... needaccesstoken) throws Exception {
		assert needaccesstoken.length <= 1;
		boolean needtoken = needaccesstoken.length > 0 ? needaccesstoken[0] : true;
		if (needtoken) {
			accessToken = getAccessToken(emailaddress, password);
			getAccountId(accessToken);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.equalsIgnoreCase("")) {
				accessToken = getAccessToken(emailaddress, password);
				getAccountId(accessToken);
			}
		}
		String member_id = Dictionary.get("member_id");
		String tmstr = "";
		for (int i = 0; i < tickets.length; i++) {
			tmstr += "&ticket_id=" + tickets[i];
		}
		Object[] obj = utils.get(host + "/api/v1/render/file?member_id=" + member_id + tmstr, new String[]{"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[]{"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accessToken});
		InputStream is = (InputStream) obj[0];
		long size = getDownloadFileSize(is, "pdf");
		Assert.assertTrue(size > 0, "Verify file is downloaded successfully from TM side");
	}

	public void renderBarcode(String ticket) throws Exception {
		String accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		String member_id = Dictionary.get("member_id");
		utils.get(host + "/api/v1/render/ticket?member_id=" + member_id + "&ticket_id=" + ticket, new String[]{"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[]{"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accessToken});

		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			Object[] obj = utils.get(host + "/api/v1/render/barcode?member_id=" + member_id + "&ticket_id=" + ticket, new String[]{"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[]{"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accessToken});
			InputStream is = (InputStream) obj[0];
			long size = getDownloadFileSize(is, "png");
			Assert.assertTrue(size > 0, "Verify barcode file is downloaded successfully from TM side");
			Set<Cookie> cookies = getDriver().manage().getCookies();
			Iterator<Cookie> iter = cookies.iterator();
			String _cookies = "";
			while (iter.hasNext()) {
				Cookie cookie = iter.next();
				_cookies += URLEncoder.encode(cookie.getName(), "UTF-8") + "=" + URLEncoder.encode(cookie.getValue(), "UTF-8") + ";";
			}
			is = utils.post(Environment.get("APP_URL") + "/user/login?_format=json", "{\"name\":\"" + emailaddress + "\",\"pass\":\"" + password + "\",\"remember_me\":0}", new String[]{"accept", "accept-encoding", "accept-language", "content-type", "user-agent"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "application/json", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"});
			String __cookies = Dictionary.get("RESPONSE_HEADERS").substring(Dictionary.get("RESPONSE_HEADERS").indexOf("Set-Cookie")).split("\\\\n")[0].replace("Set-Cookie : ", "");
			String sessionCookie = utils.getSessionCookie(__cookies);
			_cookies += sessionCookie;
			obj = utils.get(Environment.get("APP_URL") + "/api/render-ticket/" + ticket + "/barcode?_format=json", new String[]{"cookie", "accept", "accept-encoding", "accept-language", "user-agent"}, new String[]{_cookies, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36"});
			is = (InputStream) obj[0];
			size = getDownloadFileSize(is, "png");
			Assert.assertTrue(size > 0, "Verify barcode file is downloaded successfully from app side");
		}
	}

	public void renderBarcode(String email, String pass, String ticket) throws Exception {
		emailaddress = email;
		password = pass;
		String accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		String member_id = Dictionary.get("member_id");
		utils.get(host + "/api/v1/render/ticket?member_id=" + member_id + "&ticket_id=" + ticket, new String[]{"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[]{"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accessToken});

		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			Object[] obj = utils.get(host + "/api/v1/render/barcode?member_id=" + member_id + "&ticket_id=" + ticket, new String[]{"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[]{"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accessToken});
			InputStream is = (InputStream) obj[0];
			long size = getDownloadFileSize(is, "png");
			Assert.assertTrue(size > 0, "Verify barcode file is downloaded successfully from TM side");
			Set<Cookie> cookies = getDriver().manage().getCookies();
			Iterator<Cookie> iter = cookies.iterator();
			String _cookies = "";
			while (iter.hasNext()) {
				Cookie cookie = iter.next();
				_cookies += URLEncoder.encode(cookie.getName(), "UTF-8") + "=" + URLEncoder.encode(cookie.getValue(), "UTF-8") + ";";
			}
			is = utils.post(Environment.get("APP_URL") + "/user/login?_format=json", "{\"name\":\"" + emailaddress + "\",\"pass\":\"" + password + "\",\"remember_me\":0}", new String[]{"accept", "accept-encoding", "accept-language", "content-type", "user-agent"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "application/json", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"});
			String sessionCookie = Dictionary.get("RESPONSE_HEADERS").substring(Dictionary.get("RESPONSE_HEADERS").indexOf("Set-Cookie")).split("\\\\n")[0].replace("Set-Cookie : ", "").split(";")[0];
			_cookies += sessionCookie;
			obj = utils.get(Environment.get("APP_URL") + "/api/render-ticket/" + ticket + "/barcode?_format=json", new String[]{"cookie", "accept", "accept-encoding", "accept-language", "user-agent"}, new String[]{_cookies, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36"});
			is = (InputStream) obj[0];
			size = getDownloadFileSize(is, "png");
			Assert.assertTrue(size > 0, "Verify barcode file is downloaded successfully from app side");
		}
	}

	public void renderFile(String email, String pass, String ticket) throws Exception {
		accessToken = getAccessToken(email, pass);
		getAccountId(accessToken);

		String member_id = Dictionary.get("member_id");
		String tmstr = "";
		String drupalstr = "";

		tmstr += "&ticket_id=" + ticket;
		drupalstr += "ticket_id%5B%5D=" + ticket + "&";

		Object[] obj = utils.get(host + "/api/v1/render/file?member_id=" + member_id + tmstr, new String[]{"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[]{"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accessToken});
		InputStream is = (InputStream) obj[0];
		long size = getDownloadFileSize(is, "pdf");
		Assert.assertTrue(size > 0, "Verify file is downloaded successfully from TM side");
		Set<Cookie> cookies = getDriver().manage().getCookies();
		Iterator<Cookie> iter = cookies.iterator();
		String _cookies = "";
		if ((driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) && Environment.get("deviceType").trim().equalsIgnoreCase("tablet")) {
			while (iter.hasNext()) {
				Cookie cookie = iter.next();
				_cookies += URLEncoder.encode(cookie.getName(), "UTF-8") + "=" + URLEncoder.encode(cookie.getValue(), "UTF-8") + ";";
			}
			is = utils.post(Environment.get("APP_URL") + "/user/login?_format=json", "{\"name\":\"" + emailaddress + "\",\"pass\":\"" + password + "\",\"remember_me\":0}", new String[]{"accept", "accept-encoding", "accept-language", "content-type", "user-agent"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "application/json", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"});
			String __cookies = Dictionary.get("RESPONSE_HEADERS").substring(Dictionary.get("RESPONSE_HEADERS").indexOf("Set-Cookie")).split("\\\\n")[0].replace("Set-Cookie : ", "");
			String sessionCookie = __cookies.substring(__cookies.indexOf("SESS")).split(";")[0];
			_cookies += sessionCookie;
		} else {
			while (iter.hasNext()) {
				Cookie cookie = iter.next();
				_cookies += cookie.getName() + "=" + cookie.getValue() + ";";
			}
		}
		if (!_cookies.trim().equalsIgnoreCase("")) {
			obj = utils.get(Environment.get("APP_URL") + "/api/render-ticket/file?" + drupalstr + "_format=json", new String[]{"cookie", "accept", "accept-encoding", "accept-language", "user-agent"}, new String[]{_cookies, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36"});
			is = (InputStream) obj[0];
			size = getDownloadFileSize(is, "pdf");
			Assert.assertTrue(size > 0, "Verify file is downloaded successfully from app side");
		}
	}

	public void renderPassbook(String ticket) throws Exception {
		String accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		String member_id = Dictionary.get("member_id");
		utils.get(host + "/api/v1/render/ticket?member_id=" + member_id + "&ticket_id=" + ticket, new String[]{"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[]{"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accessToken});

		if (driverType.trim().toUpperCase().contains("IOS") && Environment.get("deviceType").trim().equalsIgnoreCase("phone")) {
			Object[] obj = utils.get(host + "/api/v1/render/passbook?member_id=" + member_id + "&ticket_id=" + ticket, new String[]{"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[]{"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accessToken});
			InputStream is = (InputStream) obj[0];
			long size = getDownloadFileSize(is, "pkpass");
			Assert.assertTrue(size > 0, "Verify passbook file is downloaded successfully from TM side");
			Set<Cookie> cookies = getDriver().manage().getCookies();
			Iterator<Cookie> iter = cookies.iterator();
			String _cookies = "";
			while (iter.hasNext()) {
				Cookie cookie = iter.next();
				_cookies += URLEncoder.encode(cookie.getName(), "UTF-8") + "=" + URLEncoder.encode(cookie.getValue(), "UTF-8") + ";";
			}
			is = utils.post(Environment.get("APP_URL") + "/user/login?_format=json", "{\"name\":\"" + emailaddress + "\",\"pass\":\"" + password + "\",\"remember_me\":0}", new String[]{"accept", "accept-encoding", "accept-language", "content-type", "user-agent"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "application/json", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"});
			String __cookies = Dictionary.get("RESPONSE_HEADERS").substring(Dictionary.get("RESPONSE_HEADERS").indexOf("Set-Cookie")).split("\\\\n")[0].replace("Set-Cookie : ", "");
			String sessionCookie = utils.getSessionCookie(__cookies);
			_cookies += sessionCookie;
			obj = utils.get(Environment.get("APP_URL") + "/api/render-ticket/" + ticket + "/passbook?_format=json", new String[]{"cookie", "accept", "accept-encoding", "accept-language", "user-agent"}, new String[]{_cookies, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36"});
			is = (InputStream) obj[0];
			size = getDownloadFileSize(is, "pkpass");
			Assert.assertTrue(size > 0, "Verify passbook file is downloaded successfully from app side");
		}
	}

	long getDownloadFileSize(InputStream is, String fileType) {
		OutputStream outputStream = null;

		String TicketsFolder = System.getProperty("user.dir") + OSValidator.delimiter + "Tickets" + OSValidator.delimiter;
		if (!new File(TicketsFolder).exists()) {
			new File(TicketsFolder).mkdirs();
		}

		//******************* Fetch Current TimeStamp ************************
		java.util.Date today = new java.util.Date();
		Timestamp now = new java.sql.Timestamp(today.getTime());
		String tempNow[] = now.toString().split("\\.");
		final String sStartTime = tempNow[0].replaceAll(":", "").replaceAll(" ", "").replaceAll("-", "");

		try {
			outputStream = new FileOutputStream(new File(TicketsFolder + driverType.trim().toUpperCase() + "_Ticket_" + sStartTime + "." + fileType));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = is.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

			System.out.println("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Dictionary.put("TicketPath", TicketsFolder + driverType.trim().toUpperCase() + "_Ticket_" + sStartTime + "." + fileType);
		return new File(TicketsFolder + driverType.trim().toUpperCase() + "_Ticket_" + sStartTime + "." + fileType).length();
	}

	public String[] renderTicketDetails(String ticket) throws Exception {
		String accessToken = getAccessToken(emailaddress, password);
		boolean hasBarcode = false;
		getAccountId(accessToken);
		String member_id = Dictionary.get("member_id");
		JSONObject jsonObject = get(host + "/api/v1/render/ticket?member_id=" + member_id + "&ticket_id=" + ticket, accessToken, true);
		if (jsonObject.has("ticket_render_data_line_items")) {
			String barcode = "";
			JSONArray ticket_render_data_line_items = jsonObject.getJSONArray("ticket_render_data_line_items");
			JSONObject json = ticket_render_data_line_items.getJSONObject(0);
			JSONObject event = jsonObject.getJSONObject("event");
			String venueName = jsonObject.has("venue_address") ? jsonObject.getString("venue_address").trim().replace("\n", " ") : event.getJSONObject("venue").getString("name").trim();

			if (!json.has("barcode")) {

			} else {
				hasBarcode = true;
				if (driverType.trim().toUpperCase().contains("ANDROID"))
					barcode = json.getString("barcode").trim() + "j";
				else if (driverType.trim().toUpperCase().contains("IOS"))
					barcode = json.getString("barcode").trim() + "k";
				else
					barcode = json.getString("barcode").trim() + "d";

				barcode = barcode.trim().equalsIgnoreCase("") ? "-" : barcode;
			}
			String seatNumber = json.getString("seat").trim();
			String rowName = json.getString("row_name").trim();
			String sectionName = json.getString("section_name").trim();

			String accountnumber = json.getString("account_number_id").trim();
			String seatLocator;
			if (json.has("entry_gate") && json.has("entry_gate_label")) {
				seatLocator = json.getString("entry_gate_label").trim() + " " + json.getString("entry_gate").trim();
			} else {
				seatLocator = "Entry Gate:";
			}
			String terms_conditions = jsonObject.getString("terms_and_conditions").trim();
			String phone = jsonObject.has("venue_phone") ? jsonObject.getString("venue_phone").trim() : "-";

			venueName = venueName.trim().equalsIgnoreCase("") ? "-" : venueName;

			seatNumber = seatNumber.trim().equalsIgnoreCase("") ? "-" : seatNumber;
			accountnumber = accountnumber.trim().equalsIgnoreCase("") ? "-" : accountnumber;
			seatLocator = seatLocator.trim().equalsIgnoreCase("") ? "-" : seatLocator;
			terms_conditions = terms_conditions.trim().equalsIgnoreCase("") ? "-" : terms_conditions;

			String sectionRowSeat = "";

			if (!seatNumber.equals("") && !rowName.equals("") && !sectionName.equals("")) {
				sectionRowSeat = "SECTION " + sectionName + " | ROW " + rowName + " | SEAT " + seatNumber;
			} else if (seatNumber.equals("") && !rowName.equals("") && !sectionName.equals("")) {
				sectionRowSeat = "SECTION " + sectionName + " | ROW " + rowName;
			} else if (!seatNumber.equals("") && rowName.equals("") && !sectionName.equals("")) {
				sectionRowSeat = "SECTION " + sectionName + " | SEAT " + seatNumber;
			} else if (!seatNumber.equals("") && !rowName.equals("") && sectionName.equals("")) {
				sectionRowSeat = "ROW " + rowName + " | SEAT " + seatNumber;
			}

			if (hasBarcode)
				return new String[]{accountnumber, barcode, sectionRowSeat, seatLocator, venueName, phone, terms_conditions};
			else
				return new String[]{accountnumber, sectionRowSeat, seatLocator, venueName, phone, terms_conditions};
		} else {
			return new String[]{"No details found"};
		}
	}

	public String[] renderTicketDetails(String email, String pass, String ticket) throws Exception {
		emailaddress = email;
		password = pass;
		String accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		String member_id = Dictionary.get("member_id");
		JSONObject jsonObject = get(host + "/api/v1/render/ticket?member_id=" + member_id + "&ticket_id=" + ticket, accessToken, true);
		if (jsonObject.has("ticket_render_data_line_items")) {
			String barcode = "";
			JSONArray ticket_render_data_line_items = jsonObject.getJSONArray("ticket_render_data_line_items");
			JSONObject json = ticket_render_data_line_items.getJSONObject(0);
			JSONObject event = jsonObject.getJSONObject("event");
			String venueName = jsonObject.has("venue_address") ? jsonObject.getString("venue_address").trim().replace("\n", " ") : event.getJSONObject("venue").getString("name").trim();
			if (driverType.trim().toUpperCase().contains("ANDROID"))
				barcode = json.getString("barcode").trim() + "j";
			else if (driverType.trim().toUpperCase().contains("IOS"))
				barcode = json.getString("barcode").trim() + "k";
			else
				barcode = json.getString("barcode").trim() + "d";
			String seatNumber = json.getString("seat").trim();
			String accountnumber = json.getString("account_number_id").trim();
			String seatLocator;
			if (json.has("entry_gate") && json.has("entry_gate_label")) {
				seatLocator = json.getString("entry_gate_label").trim() + " " + json.getString("entry_gate").trim();
			} else {
				seatLocator = "Entry Gate:";
			}
			String terms_conditions = jsonObject.getString("terms_and_conditions").trim();
			String phone = jsonObject.has("venue_phone") ? jsonObject.getString("venue_phone").trim() : "-";

			venueName = venueName.trim().equalsIgnoreCase("") ? "-" : venueName;
			barcode = barcode.trim().equalsIgnoreCase("") ? "-" : barcode;
			seatNumber = seatNumber.trim().equalsIgnoreCase("") ? "-" : seatNumber;
			accountnumber = accountnumber.trim().equalsIgnoreCase("") ? "-" : accountnumber;
			seatLocator = seatLocator.trim().equalsIgnoreCase("") ? "-" : seatLocator;
			terms_conditions = terms_conditions.trim().equalsIgnoreCase("") ? "-" : terms_conditions;
			return new String[]{accountnumber, barcode, venueName, phone, seatNumber, seatLocator, terms_conditions};
		} else {
			return new String[]{"No details found"};
		}
	}

	private int[] convertToInt(List<String> data) {
		int[] alist = new int[data.size()];
		for (int i = 0; i < data.size(); i++) {
			alist[i] = Integer.valueOf(data.get(i));
		}
		return alist;
	}

	public Integer[] getCharityId(String eventId) throws Exception {
		accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		List<Integer> charity_id = new ArrayList<Integer>();
		JSONObject jsonObject = get(host + "/api/v1/members/" + Dictionary.get("member_id") + "/inventory/donations/policy?event_id=" + eventId, accessToken);
		JSONArray charities = (JSONArray) jsonObject.get("charities");
		for (int i = 0; i < charities.length(); i++) {
			JSONObject charityIds = (JSONObject) charities.getJSONObject(i);
			charity_id.add(charityIds.getInt("charity_id"));
		}
		return charity_id.toArray(new Integer[charity_id.size()]);
	}

	public void donateTickets(String ticketsId, int charityId) throws Exception {
		String payload = "{" + "\"charity_id\":" + charityId + ",\"ticket_ids\":[\"" + ticketsId + "\"]}";
		utils.post(host + "/api/v1/members/" + Dictionary.get("member_id") + "/inventory/donations", payload, new String[]{"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[]{"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accessToken});
		String state = waitForTicketState(ticketsId, "donated");
		Assert.assertEquals(state, "donated");
	}

	public void claimTickets(String email, String pass, String invite_id, String ticketsId) throws Exception {
		if (email.trim().equalsIgnoreCase("") && pass.trim().equalsIgnoreCase("")) {
			email = emailaddress;
			pass = password;
		}
		String accesstoken = getAccessToken(email, pass);
		getAccountId(accesstoken);
		JSONObject jsonobject = patch(host + "/api/v1/transfer/invite/" + invite_id, accesstoken, "{\"state\": \"accepted\"}");
		System.out.println(jsonobject);
		String state = waitForTicketState(ticketsId, "accepted");
		Assert.assertEquals(state, "accepted");
	}

	public void claimTicketsDrupal(String invite_id, String ticketsId, String cookies, String token) throws Exception {
		utils.patch(Environment.get("APP_URL").trim() + "/api/ticket/claim/accept/" + invite_id + "?_format=json", "{\"state\": \"accepted\"}", new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie", "content-type", "x-csrf-token"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1", cookies, "application/json", token});
		String state = waitForTicketState(ticketsId, "accepted");
		Assert.assertEquals(state, "accepted");
	}

	public void sendTicketsDrupal(int eventId, String ticketId, String cookies, String token) throws Exception {
		String payload = "[{\"event\":{\"event_id\":\"" + eventId + "\"},\"is_display_price\":true,\"ticket_ids\":[\"" + ticketId + "\"]}]";
		utils.post(Environment.get("APP_URL").trim() + "/api/ticket/transfer?_format=json", payload, new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie", "content-type", "x-csrf-token"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1", cookies, "application/json", token});
		String state = waitForTicketState(ticketId, "pending");
		Assert.assertEquals(state, "pending");
	}

	public void donateTicketsDrupal(String ticketsId, int charityId, String cookies, String token) throws Exception {
		String payload = "{" + "\"charity_id\":\"" + charityId + "\",\"ticket_ids\":[\"" + ticketsId + "\"]}";
		utils.post(Environment.get("APP_URL").trim() + "/api/members/donations?_format=json", payload, new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie", "content-type", "x-csrf-token"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1", cookies, "application/json", token});
		String state = waitForTicketState(ticketsId, "donated");
		Assert.assertEquals(state, "donated");
	}

	public void sellticketsDrupalAPI(String[] ticketId, String cookies, String token) throws JSONException, IOException, Exception {
		for (int i = 0; i < ticketId.length; i++) {
			Object[] resalePolicy = utils.get(Environment.get("APP_URL").trim() + "/api/resale-policy?ticket_id%5B%5D=" + ticketId[i] + "&_format=json", new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie", "content-type"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1", cookies, "application/json"});
			InputStream is = (InputStream) resalePolicy[0];
			JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
			String payout_method = jsonObject.getJSONArray("allowed_payout_methods").getString(0);
			int eventId = Integer.parseInt(ticketId[i].split("\\.")[0]);
			JSONObject obj = adminLogin.update(adminLogin.parseJsonFile(postTicketsRequest), "event_id", eventId);
			obj = adminLogin.update(obj, "payout_method", payout_method);
			JSONObject section = adminLogin.update(obj, "section_name", ticketId[i].split("\\.")[1]);
			JSONObject row = adminLogin.update(section, "row_name", ticketId[i].split("\\.")[2]);
			List<JSONObject> tickets = new ArrayList<JSONObject>();
			JSONObject ticket = new JSONObject("{\"ticket_id\":\"" + ticketId[i] + "\"}");
			tickets.add(ticket);
			String payload = adminLogin.update(row, "tickets", tickets).toString();
			utils.post(Environment.get("APP_URL").trim() + "/api/ticket-resale?_format=json", payload, new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie", "content-type", "x-csrf-token"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1", cookies, "application/json", token});
		}
		String state = waitForTicketState(ticketId[0], "pending");
		Assert.assertEquals(state, "pending");
	}

	public String[] getRenderDeferredDelivery(boolean currentvalideventsonly, String eventType, boolean multiple, boolean... extras) throws Exception {
		if (!utils.getManageTicketConfiguration("download"))
			throw new SkipException("Download is not enabled in CMS");

		assert extras.length <= 2;
		boolean delete = extras.length > 0 ? extras[0] : true;
		boolean needaccesstoken = extras.length > 1 ? extras[1] : true;
		if (delete)
			deleteAllPending(needaccesstoken);
		if (needaccesstoken) {
			accessToken = getAccessToken(emailaddress, password);
			getAccountId(accessToken);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.equalsIgnoreCase("")) {
				accessToken = getAccessToken(emailaddress, password);
				getAccountId(accessToken);
			}
		}

//	  assert deletePending.length <= 1;
//	  boolean delete = deletePending.length > 0 ? deletePending[0] : true;
//	  if(delete)
//		  deleteAllPending();
//	  String accessToken = getAccessToken(emailaddress, password);
//	  getAccountId(accessToken);
		ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), false, currentvalideventsonly, eventType);
		System.out.println("all event_id:" + event_id);
		String[] render_ticket_ids = null;

		for (int m = 0; m < event_id.size(); m++) {
			String[] ticketIds = getTicketIds(Integer.valueOf(event_id.get(m)), accessToken, "DEFERRED_DELIVERY", multiple);
			System.out.println("ticketId:" + ticketIds);
			if (ticketIds != null) {
				render_ticket_ids = ticketIds;
				break;
			}
		}
		if (render_ticket_ids == null)
			throw new SkipException("ticket to be used to verify deferred delivery not found");
		else
			Assert.assertNotNull(render_ticket_ids, "Verify ticket to be used for download found");

		return render_ticket_ids;
	}

	public String getPostingProfileFirstName(String email, String pass) throws Exception {
		emailaddress = email;
		password = pass;
		String accessToken = getAccessToken(emailaddress, password);
		getAccountId(accessToken);
		Object[] obj = utils.get(host + "/api/v1/member/" + Dictionary.get("member_id") + "/posting/profile", new String[]{"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[]{"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accessToken});
		InputStream is = (InputStream) obj[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		if (!jsonObject.has("first_name"))
			jsonObject = get(host + "/api/v1/member/" + Dictionary.get("member_id"), accessToken);
		String firstName = jsonObject.getString("first_name");
		return firstName;
	}

	public String BarcodeSeparator(String Barcode) {
		Barcode = Barcode.substring(0, Barcode.length() - 1);
		StringBuilder sb = new StringBuilder(Barcode.length());
		char[] barcode = Barcode.toCharArray();

		for (int i = 0; i < Barcode.length(); i++) {
			if (i != 0 & i % 4 == 0)
				sb.append(" ").append(barcode[i]);
			else
				sb.append(barcode[i]);
		}
		return sb.toString();
	}


	// AAPI

	public JSONObject getAllEventsForMember() throws Exception {
		return getAllEventsForMember(4);
	}

	private JSONObject getAllEventsForMember(int counter) throws Exception {
		System.out.println("Getting all events with counter > " + counter);
		if (Dictionary.get("AccessToken").equalsIgnoreCase("")) {
			String accessToken = getAccessToken(emailaddress, password);
			Dictionary.put("AccessToken", accessToken);
		}
		Object[] ab = utils.get(hostAAPI + "/members/" + Dictionary.get("member_id") + "/events", new String[]{"Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id", "apiKey"}, new String[]{"application/json", "Bearer " + Dictionary.get("AccessToken"), Environment.get("DSN"), aapiClientId, TMPSCorrelationId, Environment.get("x-api-key").trim()});
		InputStream is = (InputStream) ab[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		System.out.println(jsonObject.toString(2));
		if(jsonObject.has("statusCode")) {
			int statusCode = jsonObject.getInt("statusCode");
			if ((statusCode == 500 || statusCode == 401) && counter > 0) {
				return getAllEventsForMember(counter -1);
			}
		}
		Dictionary.put("MemberEvent", jsonObject.toString(2));
		return jsonObject;
	}

	public JSONObject getAllEventsForMember(String memberId) throws Exception {
		if (Dictionary.get("AccessToken").equalsIgnoreCase("")) {
			String accessToken = getAccessToken(emailaddress, password);
			Dictionary.put("AccessToken", accessToken);
		}
		Object[] ab = utils.get(hostAAPI + "/members/" + memberId + "/events", new String[]{"Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id", "apiKey"}, new String[]{"application/json", "Bearer " + Dictionary.get("AccessToken"), Environment.get("DSN"), aapiClientId, TMPSCorrelationId, Environment.get("x-api-key").trim()});
		InputStream is = (InputStream) ab[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		System.out.println(jsonObject.toString(2));
		Dictionary.put("MemberEvent", jsonObject.toString(2));
		return jsonObject;
	}


	public JSONObject getSummaryForSpecificEvent(String eventId) throws Exception {
		if (Dictionary.get("AccessToken").equalsIgnoreCase("")) {
			String accessToken = getAccessToken(emailaddress, password);
			Dictionary.put("AccessToken", accessToken);
		}
		Object[] ab = utils.get(hostAAPI + "/members/" + Dictionary.get("member_id") + "/events/" + eventId + "/summary", new String[]{"Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id", "apiKey"}, new String[]{"application/json", "Bearer " + Dictionary.get("AccessToken"), Environment.get("DSN"), aapiClientId, TMPSCorrelationId, Environment.get("x-api-key").trim()});
		InputStream is = (InputStream) ab[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		System.out.println(jsonObject.toString(2));
		Dictionary.put("Summary" + eventId, jsonObject.toString(2));
		return jsonObject;
	}

	
	public JSONObject getAllSectionsForAnyEvent(String eventId) throws Exception {
		System.out.println("Getting AllSections For Event >> "+eventId);
		System.out.println("X-API-Key :: "+Environment.get("x-api-key").trim());
		if (Dictionary.get("AccessToken").equalsIgnoreCase("")) {
			String accessToken = getAccessToken(emailaddress, password);
			Dictionary.put("AccessToken", accessToken);
		}
		Object[] ab = utils.get(hostAAPI + "/members/" + Dictionary.get("member_id") + "/events/" + eventId + "/sections", new String[]{"Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id", "apiKey"}, new String[]{"application/json", "Bearer " + Dictionary.get("AccessToken"), Environment.get("DSN"), aapiClientId, TMPSCorrelationId, Environment.get("x-api-key").trim()});
		InputStream is = (InputStream) ab[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		System.out.println(jsonObject.toString(2));
		if (jsonObject.has("statusCode")) {
			int statusCode = jsonObject.getInt("statusCode");
			if (statusCode == 500 || statusCode == 401) {
				Assert.assertTrue(true, "Not able to fetch data from sections. fetching data again with counter " + 3);
				return getAllSectionsForAnyEvent(eventId, 3);
			}
		}
		Dictionary.put("Sections" + eventId, jsonObject.toString(2));
		return jsonObject;
	}
	
	public JSONObject getAllSectionsForAnyEvent(String eventId, int counter) throws Exception {
		System.out.println("Fetching all Sections with counters > "+counter);
		if (Dictionary.get("AccessToken").equalsIgnoreCase("")) {
			String accessToken = getAccessToken(emailaddress, password);
			Dictionary.put("AccessToken", accessToken);
		}
		Object[] ab = utils.get(hostAAPI + "/members/" + Dictionary.get("member_id") + "/events/" + eventId + "/sections", new String[]{"Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id", "apiKey"}, new String[]{"application/json", "Bearer " + Dictionary.get("AccessToken"), Environment.get("DSN"), aapiClientId, TMPSCorrelationId, Environment.get("x-api-key").trim()});
		InputStream is = (InputStream) ab[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		System.out.println(jsonObject.toString(2));
		if (jsonObject.has("statusCode")) {
			int statusCode = jsonObject.getInt("statusCode");
			if ((statusCode == 500 || statusCode == 401) && counter > 0) {
				Assert.assertTrue(true,
						"Not able to fetch data from sections. fetching data again with counter " + (counter - 1));
				return getAllSectionsForAnyEvent(eventId, counter - 1);
			}
		}
		Dictionary.put("Sections" + eventId, jsonObject.toString(2));
		return jsonObject;
	}

	public JSONObject getSectionDetailsForEvent(String eventId, String sectionId) throws Exception {
		return getSectionDetailsForEvent(eventId, sectionId, 3);
	}
	
	public JSONObject getSectionDetailsForEvent(String eventId, String sectionId, int counter) throws Exception {
		System.out.println("Fetching all Sections with counters > "+counter);
		if (Dictionary.get("AccessToken").equalsIgnoreCase("")) {
			String accessToken = getAccessToken(emailaddress, password);
			Dictionary.put("AccessToken", accessToken);
		}
		Object[] ab = utils.get(hostAAPI + "/members/" + Dictionary.get("member_id") + "/events/" + eventId + "/sections/" + sectionId, new String[]{"Accept", "Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id","apiKey"}, new String[]{"application/json", "application/json", "Bearer " + Dictionary.get("AccessToken"), Environment.get("DSN"), aapiClientId, TMPSCorrelationId,Environment.get("x-api-key").trim()});
		InputStream is = (InputStream) ab[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		System.out.println(jsonObject.toString(2));
		if (jsonObject.has("statusCode")) {
			int statusCode = jsonObject.getInt("statusCode");
			if ((statusCode == 500 || statusCode == 401) && counter > 0) {
				Assert.assertTrue(true,
						"Not able to fetch data from sections. fetching data again with counter " + (counter - 1));
				return getSectionDetailsForEvent(eventId, sectionId, counter - 1);
			}
		}
		Dictionary.put("SpecificSection" + eventId + sectionId, jsonObject.toString(2)); 
		return jsonObject;
	}

	public JSONObject getTermsOfUse(String eventId) throws Exception {
		if (Dictionary.get("AccessToken").equalsIgnoreCase("")) {
			String accessToken = getAccessToken(emailaddress, password);
			Dictionary.put("AccessToken", accessToken);
		}
		Object[] ab = utils.get(hostAAPI + "/events/" + eventId + "/termsofuse", new String[]{"Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id", "apiKey"}, new String[]{"application/json", "Bearer " + Dictionary.get("AccessToken"), "unitas", clientId, TMPSCorrelationId, Environment.get("x-api-key").trim()});
		InputStream is = (InputStream) ab[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		System.out.println(jsonObject.toString(2));
		Dictionary.put("TOU" + eventId, jsonObject.toString(2));
		return jsonObject;
	}

	public JSONObject getVenue(String id) throws Exception {
		if (Dictionary.get("AccessToken").equalsIgnoreCase("")) {
			String accessToken = getAccessToken(emailaddress, password);
			Dictionary.put("AccessToken", accessToken);
		}
		Object[] ab = utils.get(hostAAPI + "/venues/" + id, new String[]{"Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id", "apiKey"}, new String[]{"application/json", "Bearer " + Dictionary.get("AccessToken"), "unitas", clientId, TMPSCorrelationId, Environment.get("x-api-key").trim()});
		InputStream is = (InputStream) ab[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		System.out.println(jsonObject.toString(2));
		Dictionary.put("Venue" + id, jsonObject.toString(2));
		return jsonObject;
	}

	public JSONObject getAllInvites(String email, String pass) throws Exception {

		String aT = getAccessToken(email, pass);
		Dictionary.put("AT", aT);
		Object[] ab = utils.get(hostAAPI + "/members/" + Dictionary.get("member_id") + "/invites", new String[]{"Accept", "Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id", "apiKey"}, new String[]{"application/json", "application/json", "Bearer " + aT, Environment.get("DSN"), aapiClientId, TMPSCorrelationId, Environment.get("x-api-key").trim()});
		InputStream is = (InputStream) ab[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		System.out.println(jsonObject.toString(2));
		Dictionary.put("AllInvites", jsonObject.toString(2));
		return jsonObject;
	}

	public List<String> getSpecificInviteTicketName(String email, String pass, String inviteId, int eventId) throws Exception {
		List<String> ticketNames = new ArrayList<>();

		String aT = getAccessToken(email, pass);
		Dictionary.put("AT", aT);
		Object[] ab = utils.get(hostAAPI + "/members/" + Dictionary.get("member_id") + "/invites/" + inviteId + "/events/" + eventId, new String[]{"Accept", "Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id", "apiKey"}, new String[]{"application/json", "application/json", "Bearer " + aT, Environment.get("DSN"), aapiClientId, TMPSCorrelationId, Environment.get("x-api-key").trim()});
		InputStream is = (InputStream) ab[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		JSONObject embedded = (JSONObject) jsonObject.get("_embedded");
		Dictionary.put("AllInvites", embedded.toString(2));


		JSONArray sections = embedded.getJSONArray("sections");

		for (int j = 0; j < sections.length(); j++) {
			JSONObject section = (JSONObject) sections.get(j);
			String sectionFormattedLabel = section.getString("formattedLabel");


			JSONArray rows = (JSONArray) section.get("rows");

			for (int k = 0; k < rows.length(); k++) {

				JSONObject ticket = (JSONObject) rows.get(k);
				String rowFormattedLabel = ticket.getString("formattedLabel");
				JSONArray tickets = (JSONArray) ticket.get("seats");

				for (int l = 0; l < tickets.length(); l++) {

					JSONObject seat = (JSONObject) tickets.get(l);
					String seatFormattedLabel = seat.getString("formattedLabel");

					if (rowFormattedLabel.equalsIgnoreCase("")) {
						ticketNames.add(sectionFormattedLabel + ", " + seatFormattedLabel);
					} else if (sectionFormattedLabel.equalsIgnoreCase("")) {
						ticketNames.add(rowFormattedLabel + ", " + seatFormattedLabel);
					} else
						ticketNames.add(sectionFormattedLabel + ", " + rowFormattedLabel + ", " + seatFormattedLabel);
				}
			}
		}


		return ticketNames;
	}

	public JSONObject getSpecificInviteDetails(String email, String pass, String inviteId) throws Exception {

		String aT = getAccessToken(email, pass);
		Dictionary.put("AT", aT);
		Object[] ab = utils.get(hostAAPI + "/members/" + Dictionary.get("member_id") + "/invites/" + inviteId, new String[]{"Accept", "Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id", "apiKey"}, new String[]{"application/json", "application/json", "Bearer " + aT, Environment.get("DSN"), aapiClientId, TMPSCorrelationId, Environment.get("x-api-key").trim()});
		InputStream is = (InputStream) ab[0];
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		System.out.println(jsonObject.toString(2));
		Dictionary.put("AllInvites", jsonObject.toString(2));
		return jsonObject;
	}

	public LinkedList<Integer> getEventIdsForSpecificInvite(String email, String pass, String inviteId) throws Exception {
		LinkedList<Integer> events = new LinkedList<Integer>();
		JSONObject jo = getSpecificInviteDetails(email, pass, inviteId);
		JSONArray event = jo.getJSONArray("events");
		for (int j = 0; j < event.length(); j++) {
			JSONObject evnt = event.getJSONObject(j);
			events.add(evnt.getInt("id"));
		}
		return events;
	}

	public void acceptDeclineAllInvites(String email, String pass, String key) throws Exception {
		//to be tested and fixed
		JSONObject jo = getAllInvites(email, pass);

		JSONObject embedded = (JSONObject) jo.get("_embedded");
		JSONArray invites = (JSONArray) embedded.get("invites");

		for (int i = 0; i < invites.length(); i++) {
			JSONObject invite = invites.getJSONObject(i);
			String inviteId = invite.getString("id");
			System.out.println(inviteId);
			utils.patch(hostAAPI + "/members/" + Dictionary.get("member_id") + "/invites/" + inviteId, "{\"status\":\"" + key + "\"}", new String[]{"Accept", "Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id", "apiKey"}, new String[]{"application/json", "application/json", "Bearer " + Dictionary.get("AT"), Environment.get("DSN"), aapiClientId, TMPSCorrelationId, Environment.get("x-api-key").trim()});
			//  utils.patch(Environment.get("APP_URL").trim() + "/api/v2/member/invites/accept/" + inviteId + "?_format=json", "{\"state\": \"accepted\"}", new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie", "content-type", "x-csrf-token"}, new String[]{"application/json, text/plain, */*", "gzip, deflate, br", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36", cookies, "application/json", token});
		}

	}

	public ArrayList<String> getAllEvents(String accesstoken, String member_id, boolean haveRelatedEventId, boolean currentvalideventsonly, String eventType) throws Exception {
		JSONObject jsonObject = getAllEventsForMember();
		ArrayList<String> eventIds = new ArrayList<String>();

		JSONObject embedded = (JSONObject) jsonObject.get("_embedded");
		JSONArray events = embedded.getJSONArray("events");
		if (events != null) {
			for (int i = 0; i < events.length(); i++) {
				JSONObject event = events.getJSONObject(i);
				if (eventType.trim().equalsIgnoreCase("all")) {
					//Do Nothing
				} else {
					if (!event.getString("type").trim().equalsIgnoreCase(eventType.trim()))
						continue;
				}
				if (currentvalideventsonly) {
					JSONObject eventDate = event.getJSONObject("date");
					if (eventDate.getBoolean("hasDateOverride")) {
						if (eventDate.getString("dateOverrideText").trim().equalsIgnoreCase(""))
							continue;
					} else {
						if (!eventDate.has("date")) {
							continue;
						}
						SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
						Date event_date = sm.parse(eventDate.getString("date").trim());
						Date today = sm.parse(sm.format(new Date()));
						if (event_date.compareTo(today) == -1)
							continue;
					}
				}
				if (haveRelatedEventId) {
					if (event.has("relatedEventIds") && event.getJSONArray("relatedEventIds").length() > 0) {
						JSONArray related_event_ids = event.getJSONArray("relatedEventIds");
						int j = 0;
						Dictionary.put(event.get("id").toString(), "");
						for (; j < related_event_ids.length() - 1; j++) {
							Dictionary.put(event.get("id").toString(), Dictionary.get(event.get("id").toString()) + related_event_ids.getString(j) + ",");
						}
						Dictionary.put(event.get("id").toString(), Dictionary.get(event.get("id").toString()) + related_event_ids.getString(j));
					} else {
						continue;
					}
				}

				eventIds.add(event.get("id").toString());
			}
		}

		return eventIds;
	}

	public class _Event implements Comparable<_Event> {
		String event_name;
		public Date datetime;
		public Date date;
		public Date time;
		boolean has_date_override;
		boolean has_time_override;
		String time_override_text;
		String date_override_text;
		int eventId;

		_Event(String event_name, Date datetime, Date date, Date time, boolean has_date_override,
			   boolean has_time_override, String time_override_text, String date_override_text, int eventId) {
			this.event_name = event_name;
			this.datetime = datetime;
			this.date = date;
			this.time = time;
			this.has_date_override = has_date_override;
			this.has_time_override = has_time_override;
			this.time_override_text = time_override_text;
			this.date_override_text = date_override_text;
			this.eventId = eventId;
		}

		@Override
		public int compareTo(_Event o) {
			if (this.date != null && this.time != null) {
				if (o.date != null && o.time != null) {
					return this.datetime.compareTo(o.datetime);
				} else if (o.date != null && o.time == null) {
					return this.date.compareTo(o.date);
				} else
					return -1;
			} else if (this.date != null && this.time == null) {
				if (o.date != null) {
					return this.date.compareTo(o.date);
				} else {
					return -1;
				}
			} else if (this.date == null && this.time != null) {
				if (o.date != null) {
					return 1;
				} else if (o.date == null && o.time != null) {
					return this.time.compareTo(o.time);
				} else {
					return -1;
				}
			} else {
				if (o.date != null || o.time != null) {
					return 1;
				} else {
					return this.event_name.compareTo(o.event_name);
				}
			}
		}

		public String getEventName() {
			return event_name;
		}

		public Date getDate() {
			return date;
		}

		public Date getTime() {
			return time;
		}

		public String getDateOverride() {
			return date_override_text;
		}

		public String getTimeOverride() {
			return time_override_text;
		}

		public int getEventId() {
			return eventId;
		}
	}

	public class Event implements Comparable<Event> {
		int id;
		String event_name;
		int tktsCount;
		public int cantransfertktcount;
		public int canresaletktcount;
		public int canrendertktcount;
		public int canrenderfiletktcount;
		public int canrenderbarcodetktcount;
		public int canrenderpassbooktktcount;
		public int candonatecharitytktcount;
		public int haspendinginvoicetktcount;
		public int ismobileenabledtktcount;
		List<Section> sections;

		Event(int id, String event_name, int tktsCount, int cantransfertktcount, int canresaletktcount, int canrendertktcount, int canrenderfiletktcount, int canrenderbarcodetktcount, int canrenderpassbooktktcount, int candonatecharitytktcount, int haspendinginvoicetktcount, int ismobileenabledtktcount, List<Section> sections) {
			this.id = id;
			this.event_name = event_name;
			this.tktsCount = tktsCount;
			this.cantransfertktcount = cantransfertktcount;
			this.canresaletktcount = canresaletktcount;
			this.canrendertktcount = canrendertktcount;
			this.canrenderfiletktcount = canrenderfiletktcount;
			this.canrenderbarcodetktcount = canrenderbarcodetktcount;
			this.canrenderpassbooktktcount = canrenderpassbooktktcount;
			this.candonatecharitytktcount = candonatecharitytktcount;
			this.haspendinginvoicetktcount = haspendinginvoicetktcount;
			this.ismobileenabledtktcount = ismobileenabledtktcount;
			this.sections = sections;
		}

		@Override
		public int compareTo(Event o) {
			switch (Dictionary.get("apply_sorting_on").trim().toUpperCase()) {
				case "CAN_TRANSFER":
					return Integer.valueOf(cantransfertktcount).compareTo(Integer.valueOf(o.cantransfertktcount));
				case "CAN_RESALE":
					return Integer.valueOf(canresaletktcount).compareTo(Integer.valueOf(o.canresaletktcount));
				case "CAN_RENDER":
					return Integer.valueOf(canrendertktcount).compareTo(Integer.valueOf(o.canrendertktcount));
				case "CAN_RENDER_FILE":
					return Integer.valueOf(canrenderfiletktcount).compareTo(Integer.valueOf(o.canrenderfiletktcount));
				case "CAN_RENDER_BARCODE":
					return Integer.valueOf(canrenderbarcodetktcount).compareTo(Integer.valueOf(o.canrenderbarcodetktcount));
				case "CAN_RENDER_PASSBOOK":
					return Integer.valueOf(canrenderpassbooktktcount).compareTo(Integer.valueOf(o.canrenderpassbooktktcount));
				case "CAN_DONATE_CHARITY":
					return Integer.valueOf(candonatecharitytktcount).compareTo(Integer.valueOf(o.candonatecharitytktcount));
				case "HAS_PENDING_INVOICE":
					return Integer.valueOf(haspendinginvoicetktcount).compareTo(Integer.valueOf(o.haspendinginvoicetktcount));
				case "IS_MOBILE_ENABLED":
					return Integer.valueOf(ismobileenabledtktcount).compareTo(Integer.valueOf(o.ismobileenabledtktcount));
					
				default:
					return Integer.valueOf(tktsCount).compareTo(Integer.valueOf(o.tktsCount));
			}
		}

		public int getTicketsCount() {
			return tktsCount;
		}

		public String getEventName() {
			return event_name;
		}

		public int getId() {
			return id;
		}

		public List<Section> getSection() {
			return sections;
		}
	}

	public class Section implements Comparable<Section> {
		String section_number;
		String section_label;
		int ticketsCount;
		List<Row> rows;
		String sectionFormattedLabel;

		Section(String section_number, String section_label, int ticketsCount, List<Row> rows, String sectionFormattedLabel) {
			this.section_number = section_number;
			this.section_label = section_label;
			this.ticketsCount = ticketsCount;
			this.rows = rows;
			this.sectionFormattedLabel = sectionFormattedLabel;
		}

		@Override
		public int compareTo(Section o) {
			return Integer.valueOf(getAsciiCode(section_number)).compareTo(Integer.valueOf(getAsciiCode(o.section_number)));
		}

		public String getSectionNumber() {
			return section_number;
		}

		public String getSectionLabel() {
			return section_label;
		}

		public int getTicketsCount() {
			return ticketsCount;
		}

		public List<Row> getRow() {
			return rows;
		}

		public String sectionFormmattedLabel() {
			return sectionFormattedLabel;
		}
	}

	public class Row implements Comparable<Row> {
		String row_name;
		String row_label;
		List<Ticket> tickets;
		String rowFormattedLabel;

		Row(String row_name, String row_label, List<Ticket> tickets, String rowFormattedLabel) {
			this.row_name = row_name;
			this.row_label = row_label;
			this.tickets = tickets;
			this.rowFormattedLabel = rowFormattedLabel;
		}

		@Override
		public int compareTo(Row o) {
			return Integer.valueOf(getAsciiCode(row_name)).compareTo(Integer.valueOf(getAsciiCode(o.row_name)));
		}

		public String getRowName() {
			return row_name;
		}

		public String getRowLabel() {
			return row_label;
		}

		public List<Ticket> getTickets() {
			return tickets;
		}

		public String getRowFormattedLabel() {
			return rowFormattedLabel;
		}
	}

	public class Ticket implements Comparable<Ticket> {
		String seat;
		String seat_label;
		String formattedLabel;
		String ticketId;
		boolean can_transfer;
		boolean can_resale;
		boolean can_render;
		boolean can_render_file;
		boolean can_render_barcode;
		boolean can_render_passbook;
		boolean can_donate_charity;
		boolean has_pending_invoice;
		boolean is_mobile_enabled;

		Ticket(String seat, String seat_label, boolean can_transfer, boolean can_resale, boolean can_render, boolean can_render_file, boolean can_render_barcode, boolean can_render_passbook, boolean can_donate_charity, boolean has_pending_invoice, boolean is_mobile_enabled, String formattedLabel, String ticketId) {
			this.seat = seat;
			this.seat_label = seat_label;
			this.can_transfer = can_transfer;
			this.can_resale = can_resale;
			this.can_render = can_render;
			this.can_render_file = can_render_file;
			this.can_render_barcode = can_render_barcode;
			this.can_render_passbook = can_render_passbook;
			this.can_donate_charity = can_donate_charity;
			this.has_pending_invoice = has_pending_invoice;
			this.is_mobile_enabled = is_mobile_enabled;
			this.formattedLabel = formattedLabel;
			this.ticketId = ticketId;
		}

		@Override
		public int compareTo(Ticket o) {
			return Integer.valueOf(getAsciiCode(seat)).compareTo(Integer.valueOf(getAsciiCode(o.seat)));
		}

		public String getSeatNumber() {
			return seat;
		}

		public String getTicketFormmatedLabel() {
			return formattedLabel;
		}

		public String getSeatLabel() {
			return seat_label;
		}

		public boolean canTransferFlag() {
			return can_transfer;
		}

		public boolean canResaleFlag() {
			return can_resale;
		}

		public boolean canRenderFlag() {
			return can_render;
		}

		public boolean canRenderFileFlag() {
			return can_render_file;
		}

		public boolean canRenderBarcodeFlag() {
			return can_render_barcode;
		}

		public boolean canRenderPassbookFlag() {
			return can_render_passbook;
		}

		public boolean canDonateCharity() {
			return can_render_passbook;
		}

		public boolean hasPendingInvoice() {
			return has_pending_invoice;
		}

		public boolean isMobileEnabled() {
			return is_mobile_enabled;
		}
	}

	public int getEventIdWithMaxTkts(HashMap<Integer, Event> events) throws Exception {
		Dictionary.put("apply_sorting_on", "");
		List<Event> eventsByTktCount = new ArrayList<Event>(events.values());
		Collections.sort(eventsByTktCount, Collections.reverseOrder());
		Event event = eventsByTktCount.get(0);
//	  Dictionary.put("Event", event.toString());
	  return event.getId();
 }
 
 public int getEventIdWithMinTkts(HashMap<Integer, Event> events) throws Exception {
	  Dictionary.put("apply_sorting_on", "");
	  List<Event> eventsByTktCount = new ArrayList<Event>(events.values());
	  Collections.sort(eventsByTktCount);
	  Event event = eventsByTktCount.get(0);
	  return event.getId();
 }
 
 public int getEventIdWithMaxTktsHavingRenderBarcode(HashMap<Integer, Event> events) throws Exception {
	  Dictionary.put("apply_sorting_on", "CAN_RENDER_BARCODE");
	  List<Event> eventsByTktCountHavingRenderBarcode = new ArrayList<Event>(events.values());
	  Collections.sort(eventsByTktCountHavingRenderBarcode, Collections.reverseOrder());
	  Event event = eventsByTktCountHavingRenderBarcode.get(0);
	  return event.getId();
 }
 
 public int getEventIdWithMinTktsHavingRenderBarcode(HashMap<Integer, Event> events) throws Exception {

	  Dictionary.put("apply_sorting_on", "CAN_RENDER_BARCODE");
	  List<Event> eventsByTktCountHavingRenderBarcode = new ArrayList<Event>(events.values());
	  Collections.sort(eventsByTktCountHavingRenderBarcode);
	  Event event = eventsByTktCountHavingRenderBarcode.get(0);
	  return event.getId();
 }
// public JSONObject sendTickets(int eventId, String[] ticketId, boolean...newAccount) throws Exception {
//	 String email;
//	  if(Dictionary.get("AccessToken").equalsIgnoreCase(""))
//	   {
//		   String accessToken = getAccessToken(emailaddress, password);
//		   Dictionary.put("AccessToken", accessToken);
//	   }
//	  assert newAccount.length <= 1;
//	  boolean createAccount = newAccount.length > 0 ? newAccount[0] : true;
//	  if(createAccount) {
//		  java.util.Date today = new java.util.Date();
//		  Timestamp now = new java.sql.Timestamp(today.getTime());
//		  String tempNow[] = now.toString().split("\\.");
//		  final String sStartTime = tempNow[0].replaceAll(":", "").replaceAll(" ", "").replaceAll("-", "");
//		  email = "test" + sStartTime + "@example.com";
//		  Dictionary.put("NEW_EMAIL_ADDRESS", email);
//	  }
//	  else {
//		  email = Dictionary.get("EMAIL_ADDRESS");
//	  }
//	  JSONObject obj = adminLogin.parseJsonFile(sentTicketRequestAAPI);
//	  adminLogin.updateJSONArrays(obj.getJSONArray("invites").getJSONObject(0), new String[]{"seatIds"}, Arrays.asList(Arrays.asList(ticketId)));
//	  String payload = adminLogin.update(obj, "email", email).toString();
//	  InputStream is=utils.post( hostAAPI + "/members/" + Dictionary.get("member_id") + "/transfers", payload , new String[] {"Accept","Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id"}, new String[] {"application/json","application/json","Bearer "+ Dictionary.get("AccessToken"), Environment.get("DSN"),aapiClientId, TMPSCorrelationId });
//		JSONObject result = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
//	  System.out.println(result);
//	  Dictionary.put("SendTickets", result.toString());
//	  //TODO - update waitForTicketState after getting AAPI checks for Pending Action
////	  String state = waitForTicketState(ticketId[0], "pending");
////	  Assert.assertEquals(state, "pending");
//	  return result;
// }
 
 public JSONObject sendTickets(int eventId, String[] ticketId, boolean...newAccount) throws Exception {
	 String email;
	  if(Dictionary.get("AccessToken").equalsIgnoreCase(""))
	   {
		   String accessToken = getAccessToken(Dictionary.get("EMAIL_ADDRESS"), Dictionary.get("PASSWORD"));
		   Dictionary.put("AccessToken", accessToken);
	   }
	  assert newAccount.length <= 1;
	  boolean createAccount = newAccount.length > 0 ? newAccount[0] : false;
	  if(createAccount) {
		  java.util.Date today = new java.util.Date();
		  Timestamp now = new java.sql.Timestamp(today.getTime());
		  String tempNow[] = now.toString().split("\\.");
		  final String sStartTime = tempNow[0].replaceAll(":", "").replaceAll(" ", "").replaceAll("-", "");
		  email = "test" + sStartTime + "@example.com";
		  Dictionary.put("EMAIL_ADDRESS", email);
	  }
	  else {
		  email = Dictionary.get("EMAIL_ADDRESS");
	  }
	  JSONObject obj = adminLogin.parseJsonFile(sentTicketRequestAAPI);
	  adminLogin.updateJSONArrays(obj.getJSONArray("invites").getJSONObject(0), new String[]{"seatIds"}, Arrays.asList(Arrays.asList(ticketId)));
	  String payload = adminLogin.update(obj, "email", email).toString();
	  InputStream is=utils.post( hostAAPI + "/members/" + Dictionary.get("member_id") + "/transfers", payload , new String[] {"Accept","Content-Type", "Authorization", "DSN", "ClientId", "TMPS-Correlation-Id", "apiKey"}, new String[] {"application/json","application/json","Bearer "+ Dictionary.get("AccessToken"), Environment.get("DSN"),aapiClientId, TMPSCorrelationId, Environment.get("x-api-key").trim() });
		JSONObject result = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
	  System.out.println(result);
	  Dictionary.put("SendTickets", result.toString());
	  //TODO - update waitForTicketState after getting AAPI checks for Pending Action
//	  String state = waitForTicketState(ticketId[0], "pending");
//	  Assert.assertEquals(state, "pending");
	  return result;
 }

 public String[] getTransferDetails(boolean currentvalideventsonly, String eventType, boolean multiple, boolean multipleRelevantTicketIds, boolean haveRelatedEventId, boolean... extras) throws Exception {
	  if(!utils.getManageTicketConfiguration("send"))
		  throw new SkipException("Send is not enabled in CMS");
	  assert extras.length <= 2;
	  boolean delete = extras.length > 0 ? extras[0] : true;
	  boolean needaccesstoken = extras.length > 1 ? extras[1] : true;
	  if(delete)
		  deleteAllPending(needaccesstoken);
	  if(needaccesstoken) {
		  accessToken = getAccessToken(emailaddress, password);
		  getAccountId(accessToken);
	  } else {
		  accessToken = Dictionary.get("AccessToken").trim();
		  if(accessToken.equalsIgnoreCase("")) {
			  accessToken = getAccessToken(emailaddress, password);
			  getAccountId(accessToken);
		  }
	  }
	  ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), haveRelatedEventId, currentvalideventsonly, eventType);
	  System.out.println("event_id:" + event_id);
	  String[] transfer_ticket_ids = null;
	  for(int m=0 ; m<event_id.size(); m++) {
		  String[] ticketIds = getTicketIds(Integer.valueOf(event_id.get(m)), accessToken, "CAN_TRANSFER", multiple);
		  System.out.println("ticketIds:" + ticketIds);
		  if(ticketIds != null) {
			  String eventName = Dictionary.get("eventName").trim();


			  if(haveRelatedEventId) {
				  String[] related_events_id = Dictionary.get(event_id.get(m)).split(",");
				  String[] transferIds = getTransferID(convertToInt(Arrays.asList(related_events_id)), needaccesstoken);
				  System.out.println("transferIds: " + transferIds.toString());
				  for(int i = 0; i < transferIds.length; i++) {
					  deleteTransfer(transferIds[i], needaccesstoken);
				  }
				  if(needaccesstoken) {
					  accessToken = getAccessToken(emailaddress, password);
				  }
				  boolean success = false;
				  int counter = 0;
				  for(int j = 0; j < related_events_id.length; j++) {
					  String[] relatedeventticketId = getRelevantEventTicketIds(Integer.valueOf(related_events_id[j]), accessToken, "CAN_TRANSFER", multipleRelevantTicketIds);
					  if(relatedeventticketId != null) {
						  counter += relatedeventticketId.length;
					  }
					  if(multipleRelevantTicketIds && counter > 1) {
						  success = true;
						  break;
					  } else if(!multipleRelevantTicketIds && counter > 0) {
						  success = true;
						  break;
					  }
				  }

				  if(!success)
					  continue;
			  }
			  Dictionary.put("eventName", eventName);
			  transfer_ticket_ids = ticketIds;
			  break;
		  }
	  }

	  if(transfer_ticket_ids == null)
		  throw new SkipException("ticket to be used for transfer not found");
	  else
		  Assert.assertNotNull(transfer_ticket_ids, "Verify ticket to be used for transfer found");

	  return transfer_ticket_ids;
 }

 public String[] getTransferDetails(String email, String pass, boolean currentvalideventsonly, String eventType, boolean multiple, boolean multipleRelevantTicketIds, boolean haveRelatedEventId, boolean... extras) throws Exception {
	  emailaddress= email;
	  password= pass;
	  if(!utils.getManageTicketConfiguration("send"))
		  throw new SkipException("Send is not enabled in CMS");
	  assert extras.length <= 2;
	  boolean delete = extras.length > 0 ? extras[0] : true;
	  boolean needaccesstoken = extras.length > 1 ? extras[1] : true;
	  if(delete)
		  deleteAllPending(needaccesstoken);
	  if(needaccesstoken) {
		  accessToken = getAccessToken(email, pass);
		  getAccountId(accessToken);
	  } else {
		  accessToken = Dictionary.get("AccessToken").trim();
		  if(accessToken.equalsIgnoreCase("")) {
			  accessToken = getAccessToken(email, pass);
			  getAccountId(accessToken);
		  }
	  }
	  ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), haveRelatedEventId, currentvalideventsonly, eventType);
	  System.out.println("event_id:" + event_id);
	  String[] transfer_ticket_ids = null;
	  for(int m=0 ; m<event_id.size(); m++) {
		  String[] ticketIds = getTicketIds(Integer.valueOf(event_id.get(m)), accessToken, "CAN_TRANSFER", multiple);
		  if(ticketIds != null) {
			  String eventName = Dictionary.get("eventName").trim();
			  if(haveRelatedEventId) {
				  String[] related_events_id = Dictionary.get(event_id.get(m)).split(",");
				  String[] transferIds = getTransferID(convertToInt(Arrays.asList(related_events_id)), needaccesstoken);
				  System.out.println("transferIds: " + transferIds.toString());
				  for(int i = 0; i < transferIds.length; i++) {
					  deleteTransfer(transferIds[i], needaccesstoken);
				  }
				  if(needaccesstoken) {
					  accessToken = getAccessToken(email, pass);
				  }
				  boolean success = false;
				  int counter = 0;
				  for(int j = 0; j < related_events_id.length; j++) {
					  String[] relatedeventticketId = getRelevantEventTicketIds(Integer.valueOf(related_events_id[j]), accessToken, "CAN_TRANSFER", multipleRelevantTicketIds);
					  if(relatedeventticketId != null) {
						  counter += relatedeventticketId.length;
					  }
					  if(multipleRelevantTicketIds && counter > 1) {
						  success = true;
						  break;
					  } else if(!multipleRelevantTicketIds && counter > 0) {
						  success = true;
						  break;
					  }
				  }

				  if(!success)
					  continue;
			  }
			  Dictionary.put("eventName", eventName);
			  transfer_ticket_ids = ticketIds;
			  break;
		  }
	  }

	  if(transfer_ticket_ids == null)
		  throw new SkipException("ticket to be used for transfer not found");
	  else
		  Assert.assertNotNull(transfer_ticket_ids, "Verify ticket to be used for transfer found");

	  return transfer_ticket_ids;
 }



 public List<String> getTicketNames(List<List<Section>> allEventsWithSections) {
	 LinkedList<String> ticketNames = new LinkedList<String>();
	 int count = 0;
	 for (int i = 0; i < allEventsWithSections.size(); i++) {

		 List<Section> section = allEventsWithSections.get(i);
		 for (int j = 0; j < section.size(); j++) {
			 List<Row> row = section.get(j).getRow();
			 String sectionFormattedLabel = section.get(j).sectionFormattedLabel;
				for (int k = 0; k < row.size(); k++) {
						List<Ticket> ticketDetails = row.get(k).tickets;
						String rowFormattedLabel = row.get(k).rowFormattedLabel;
						for (int l = 0; l < ticketDetails.size(); l++) {

							Dictionary.put(count+"CanTransferFlag", ""+ticketDetails.get(l).can_transfer+"");
							count++;

							if (rowFormattedLabel.equalsIgnoreCase("")) {
								ticketNames.add(sectionFormattedLabel+", "+ticketDetails.get(l).formattedLabel);
							} else if(sectionFormattedLabel.equalsIgnoreCase("")) {
								ticketNames.add(rowFormattedLabel+", "+ticketDetails.get(l).formattedLabel);
							} else
								ticketNames.add(sectionFormattedLabel+", "+rowFormattedLabel+", "+ticketDetails.get(l).formattedLabel);
						}
				}
			}
	 	}
	 Dictionary.put("TotalTicketsAAPI", ""+ticketNames.size()+"");
	 return ticketNames;
}

 public  HashMap<String,String> getTwoTicketIdsFromDifferentEvent(List<List<Section>> allEventsWithSections) {
	 HashMap<String,String> ticketIds = new HashMap<>();
	 for (int i = 0; i < allEventsWithSections.size(); i++) {
		 int count = 0;
		 if(ticketIds.size()==2)
			 break;
		 List<Section> section = allEventsWithSections.get(i);
		 for (int j = 0; j < section.size(); j++) {
			 if(count==1)
					break;
			 List<Row> row = section.get(j).getRow();
			 String sectionFormattedLabel = section.get(j).sectionFormattedLabel;
				for (int k = 0; k < row.size(); k++) {
					if(count==1)
						break;
						List<Ticket> ticketDetails = row.get(k).tickets;
						String rowFormattedLabel = row.get(k).rowFormattedLabel;
						for (int l = 0; l < ticketDetails.size(); l++) {
							if(count==1)
							break;
							if(ticketDetails.get(l).can_transfer)
								{
								if (rowFormattedLabel.equalsIgnoreCase("")) {
									ticketIds.put(ticketDetails.get(l).ticketId,sectionFormattedLabel+", "+ticketDetails.get(l).formattedLabel);
									count++;
								} else {
									ticketIds.put(ticketDetails.get(l).ticketId,sectionFormattedLabel+", "+rowFormattedLabel+", "+ticketDetails.get(l).formattedLabel);
									count++;
								}
								}
						}
				}
			}
	 	}
	 return ticketIds;
}

	public HashMap<String, Integer> getTransfrableTicketsForMemberEvents(String uname, String passwd) throws Exception {
		HashMap<String, Integer> eventDetail = new HashMap<String, Integer>();
		List<_Event> eventDetailsMember = null;

		try {
			eventDetailsMember = getEventDetails(uname, passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (ManageticketsAAPI._Event eventId : eventDetailsMember) {
			JSONArray sections = null;
			List<Boolean> canTransfer = new ArrayList<Boolean>();

			sections = getAllSectionsForAnyEvent(String.valueOf(eventId.getEventId())).getJSONObject("_embedded").getJSONArray("sections");

			for (int i = 0; i < sections.length(); i++) {

				String sectionDetails = String.valueOf(getSectionDetailsForEvent(String.valueOf(eventId.getEventId()), (String.valueOf(sections.getJSONObject(i).get("id")))));
				canTransfer.addAll(JsonPath.read(sectionDetails, "$.rows[*].seats[*].actions.canTransfer"));
			}

			canTransfer.removeAll(Collections.singleton(false));
			//canTransfer.removeIf(s -> Boolean.parseBoolean("false"));

			eventDetail.put(eventId.getEventName(), canTransfer.size());
		}

		HashMap<String, Integer> sorted = eventDetail
				.entrySet()
				.stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(
						toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

		return sorted;

	}


	public HashMap<String, String> getTransfrableSectionForMemberEvents(String uname, String passwd) throws Exception {
		HashMap<String, String> eventDetail = new HashMap<String, String>();
		List<_Event> eventDetailsMember = null;

		try {
			eventDetailsMember = getEventDetails(uname, passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (ManageticketsAAPI._Event eventId : eventDetailsMember) {
			JSONArray sections = null;
			List<Boolean> canTransfer = new ArrayList<Boolean>();

			sections = getAllSectionsForAnyEvent(String.valueOf(eventId.getEventId())).getJSONObject("_embedded").getJSONArray("sections");

			for (int i = 0; i < sections.length(); i++) {

				String sectionDetails = String.valueOf(getSectionDetailsForEvent(String.valueOf(eventId.getEventId()), (String.valueOf(sections.getJSONObject(i).get("id")))));
				canTransfer.addAll(JsonPath.read(sectionDetails, "$.rows[*].seats[*].actions.canTransfer"));  
				canTransfer.removeAll(Collections.singleton(false));
				if(canTransfer.size()>=3){
					eventDetail.put("number", String.valueOf(canTransfer.size()));
					eventDetail.put("section", String.valueOf(sections.getJSONObject(i).get("name")));
					eventDetail.put("eventname", eventId.getEventName());
					eventDetail.put("eventdate",String.valueOf(eventId.getDate()));
					eventDetail.put("eventtime",String.valueOf(eventId.getTime()));
					eventDetail.put("eventid", String.valueOf(eventId.getEventId()));
					return eventDetail;
				} else {
					canTransfer.clear();
				}
			}
		}

		throw  new SkipException("Not able to get Transferable event/tickets for logged in user. Please try with another user");
	}

	public HashMap<String, HashMap<String, String>> getTransfrableTwoEventsSectionForMemberEvents(String uname, String passwd) throws Exception {
		HashMap<String, HashMap<String,String>> eventsDetail = new HashMap<String, HashMap<String,String>>();
		List<_Event> eventDetailsMember = null;
		int count=0;
		try {
			eventDetailsMember = getEventDetails(uname, passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (ManageticketsAAPI._Event eventId : eventDetailsMember) {
			HashMap<String, String> eventDetail = new HashMap<String, String>();
			if(count==2) break;
			JSONArray sections = null;
			List<Boolean> canTransfer = new ArrayList<Boolean>();

			sections = getAllSectionsForAnyEvent(String.valueOf(eventId.getEventId())).getJSONObject("_embedded").getJSONArray("sections");

			for (int i = 0; i < sections.length(); i++) {

				String sectionDetails = String.valueOf(getSectionDetailsForEvent(String.valueOf(eventId.getEventId()), (String.valueOf(sections.getJSONObject(i).get("id")))));
				canTransfer.addAll(JsonPath.read(sectionDetails, "$.rows[*].seats[*].actions.canTransfer"));
				canTransfer.removeAll(Collections.singleton(false));
				if(canTransfer.size()>=3){
					eventDetail.put("number", String.valueOf(canTransfer.size()));
					eventDetail.put("section", String.valueOf(sections.getJSONObject(i).get("name")));
					eventDetail.put("eventname", eventId.getEventName());
					eventDetail.put("eventid", String.valueOf(eventId.getEventId()));
					eventsDetail.put(String.valueOf(count), eventDetail);
					count++;
					break;
				}
			}
		}
		if (eventsDetail.size()<2){throw  new SkipException("Not able to get Transferable event/tickets for logged in user. Please try with another user");}
		return eventsDetail;
	}

	/*public boolean verifyTransferSuccessful(String tid) throws Exception {
		String accessToken = getAccessToken(String username, String password);
		getAccountId(accessToken);
		JSONObject jsonObject = get(host+"/members/"+Dictionary.get("member_id")+"/transfers/"+tid, accessToken);
		return true;
	}*/

	public HashMap<String, String> getQDFunds() {
		HashMap Funds = new HashMap<String,String>();
		try {
			String accessToken = postOauthTokenAAPI();
			Object[] ab=utils.get(hostAAPIAuth+"/donations/funds", new String[] {"Content-Type", "Token", "DSN", "ClientId", "Accept-Language"}, new String[] {"application/json", accessToken, "genesis", "genesis", "en-us" });
			InputStream is = (InputStream) ab[0];
			JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
			JSONObject embedded = (JSONObject) jsonObject.get("_embedded");
			JSONArray funds =(JSONArray) embedded.get("funds");
			for(int i = 0; i < funds.length(); i++) {
			JSONObject fund = funds.getJSONObject(i);
			String name = fund.getString("name");
			String minAmt = String.valueOf(fund.getInt("minAmount")/100);
			Funds.put(name,minAmt);
		}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Funds;
	}


	/*public HashMap<String, String> aaaa (String uname, String passwd) throws Exception {
		HashMap<String, String> eventDetail = new HashMap<String, String>();
		List<_Event> eventDetailsMember = null;

		try {
			eventDetailsMember = getEventDetails(uname, passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (ManageticketsAAPI._Event eventId : eventDetailsMember) {
			JSONArray sections = null;
			List<Boolean> canTransfer = new ArrayList<Boolean>();

			getSectionDetailsForEvent();

			sections = getAllSectionsForAnyEvent(String.valueOf(eventId.getEventId())).getJSONObject("_embedded").getJSONArray("sections");

			for (int i = 0; i < sections.length(); i++) {

				String sectionDetails = String.valueOf(getSectionDetailsForEvent(String.valueOf(eventId.getEventId()), (String.valueOf(sections.getJSONObject(i).get("id")))));
				canTransfer.addAll(JsonPath.read(sectionDetails, "$.rows[*].seats[*].actions.canResale"));
				canTransfer.removeAll(Collections.singleton(false));
				if(canTransfer.size()>=3){
					eventDetail.put("number", String.valueOf(canTransfer.size()));
					eventDetail.put("section", String.valueOf(sections.getJSONObject(i).get("name")));
					eventDetail.put("eventname", eventId.getEventName());
					eventDetail.put("eventid", String.valueOf(eventId.getEventId()));
					return eventDetail;
				} else {
					canTransfer.clear();
				}
			}
		}
		throw  new SkipException("Not able to get Transferable event/tickets for logged in user. Please try with another user");
	}*/


	public HashMap<String, String> getResaleableEventForMember(String uname, String passwd) {
		HashMap<String, String> eventDetail = new HashMap<String, String>();
		String accessToken = null;
		String url = null;
		if (Dictionary.get("AccessToken").equalsIgnoreCase("")) {
			try {
				accessToken = getAccessToken(emailaddress, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Dictionary.put("AccessToken", accessToken);
		}
		List<_Event> eventDetailsMember = null;
		try {
			eventDetailsMember = getEventDetails(uname, passwd);
		} catch(Exception e) {
			e.printStackTrace();
		}
		List<String> canresale = new ArrayList<String>();

		for (ManageticketsAAPI._Event eventId : eventDetailsMember) {
			if (eventId.getEventId()<1045) {continue;}
			JSONObject jsonObject;
			url = host + "/api/v1/member/" + Dictionary.get("member_id") + "/inventory/search?event_id=" + eventId.getEventId();
			try {
				jsonObject = get(url, accessToken);
				String json = String.valueOf(jsonObject);
				canresale.addAll(JsonPath.read(json, "$.inventory_items[*].sections[*].rows[*].tickets[?(@.can_resale == true)].ticket_id"));
				//canresale.removeAll(Collections.singleton(false));
				canresale.toString();
				if (canresale.size() >= 2) {
					eventDetail.put("number", String.valueOf(canresale.size()));
					eventDetail.put("eventname", eventId.getEventName());
					eventDetail.put("eventid", String.valueOf(eventId.getEventId()));
					eventDetail.put("seats", String.join(";",canresale));
					return eventDetail;
				} else {
					canresale.clear();
				}
			} catch (Exception e){
			e.printStackTrace();
			}
		}
		throw  new SkipException("Not able to get Resaleable event/tickets for logged in user. Please try with another user");
	}

	public  double getResalePolicyForMember(String [] tickets, int price) {
		HashMap<String, String> eventDetail = new HashMap<String, String>();
		String accessToken = null;
		String url = null;
		ArrayList<Integer> addend = new ArrayList<>();
		ArrayList<Double> multiplier = new ArrayList<>();
		double multipliervalue = 0.0f;
		int addendvalue=0;
		if (Dictionary.get("AccessToken").equalsIgnoreCase("")) {
			try {
				accessToken = getAccessToken(emailaddress, password);accessToken = getAccessToken(emailaddress, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Dictionary.put("AccessToken", accessToken);
		} else { accessToken = Dictionary.get("AccessToken"); }

		StringBuilder param = new StringBuilder();

		for( int i=0; i<2;i++) {
			param.append("&ticket_id[]="+tickets[i]);
		}

			JSONObject jsonObject;
			url = host + "/api/v1/member/" + Dictionary.get("member_id") + "/posting/policy?"+param;
			try {
				jsonObject = get(url, accessToken);
				String json = String.valueOf(jsonObject);
				addend.addAll(JsonPath.read(json, "$.posting_group_policies[*].posting_policy.price_formula.addend"));
				multiplier.addAll(JsonPath.read(json, "$.posting_group_policies[*].posting_policy.price_formula.multiplier"));
				for (Double s : multiplier) { multipliervalue = multipliervalue + s; }
				for (Integer s : addend) { addendvalue = addendvalue + s; }
				multipliervalue = multipliervalue/multiplier.size();
				addendvalue = addendvalue/addend.size();
			} catch (Exception e){
				e.printStackTrace();
			}

		return (price - addendvalue)/multipliervalue;
	}
	
	
	
	 public String[] getDonateDetailsEDP(boolean currentvalideventsonly, String eventType,  boolean multiple, boolean haveRelatedEventId, boolean... extras) throws Exception {
		  if(!utils.getManageTicketConfiguration("donate"))
			  throw new SkipException("Donate is not enabled in CMS");
		  assert extras.length <= 2;
		  boolean delete = extras.length > 0 ? extras[0] : true;
		  boolean needaccesstoken = extras.length > 1 ? extras[1] : true;
		  if(delete)
			  deleteAllPending(needaccesstoken);
		  if(needaccesstoken) {
			  accessToken = getAccessToken(emailaddress, password);
			  getAccountId(accessToken);
		  } else {
			  accessToken = Dictionary.get("AccessToken").trim();
			  if(accessToken.equalsIgnoreCase("")) {
				  accessToken = getAccessToken(emailaddress, password);
				  getAccountId(accessToken);
			  }
		  }
		  ArrayList<String> event_id = getAllEvents(accessToken, Dictionary.get("member_id"), haveRelatedEventId, currentvalideventsonly, eventType);
		 // System.out.println("event_id:"+event_id);
		  
		  String[] donate_ticket_ids = null;
	     
		  for(int m=0; m<event_id.size(); m++) {
			  String[] ticketIds = getTicketIds(Integer.valueOf(event_id.get(m)), accessToken, "CAN_DONATE_CHARITY", multiple);
			  //System.out.println("ticketId:"+ticketId);
			  if(ticketIds != null) {
				  if(!isCharityAvailable(Integer.valueOf(event_id.get(m)), accessToken)){
					  continue;
				  }
	              donate_ticket_ids = ticketIds;
	             // System.out.println("donate_ticket_id:"+donate_ticket_id);
	              break;
			  }     
		  }
		  if(donate_ticket_ids == null)
			  throw new SkipException("ticket to be used for donate not found");
		  else
			  Assert.assertNotNull(donate_ticket_ids, "Verify ticket to be used for donate found");
		  
		  return donate_ticket_ids; 
	  }

	public String getTicketClaimByEmailAddressEDP(String ticketId, boolean... extras) throws Exception {
		String recipientEmail = "";
		String[] ticket_id = ticketId.split("\\.");
		int eventId = Integer.valueOf(ticket_id[0].trim());
		assert extras.length <= 3;
		boolean needaccesstoken = extras.length > 0 ? extras[0] : true;
		boolean needmemberid = extras.length > 1 ? extras[1] : true;
		boolean switchUser = extras.length > 2 ? extras[2] : false;
		String email = emailaddress.isEmpty() ? Dictionary.get("NEW_EMAIL_ADDRESS") : emailaddress;
		String pass = password.isEmpty() ? Dictionary.get("NEW_PASSWORD") : password;

		if (needaccesstoken) {
			accessToken = getAccessToken(email, pass);
		} else {
			accessToken = Dictionary.get("AccessToken").trim();
			if (accessToken.trim().equalsIgnoreCase("")) {
				accessToken = getAccessToken(email, pass);
			}
		}
		if (needmemberid) {
			System.out.println(getAccountId(accessToken));
			getAccountId(accessToken);
		} else {
			if (Dictionary.get("member_id").trim().equalsIgnoreCase("")) {
				getAccountId(accessToken);
			}
		}

		if (switchUser) {
			getMemberResponse(Dictionary.get("SWITCH_ACC_EMAIL_ADDRESS"), Dictionary.get("SWITCH_ACC_PASSWORD"));
			Dictionary.put("member_id", Dictionary.get("memberId1"));
			switchToAssociatedMember(Dictionary.get("memberId1"));
			accessToken = Dictionary.get("AccessToken").trim();
		}
		JSONObject eventJsonObject = getAllSectionsForAnyEvent(String.valueOf(eventId));
		JSONObject embedded = (JSONObject) eventJsonObject.get("_embedded");
		JSONArray eventSections = embedded.getJSONArray("sections");
		String sectionID = "" + ((JSONObject) eventSections.get(0)).getInt("id") + "";

		JSONObject jsonObject = getSectionDetailsForEvent(String.valueOf(eventId), sectionID);
		JSONArray rows = (JSONArray) jsonObject.get("rows");
		for (Object rowObject : rows) {
			JSONObject row = (JSONObject) rowObject;
			JSONArray seats = (JSONArray) row.get("seats");
			for (Object seatObject : seats) {
				JSONObject seat = (JSONObject) seatObject;
				String ticketId_ = seat.getString("id").trim();
				if (ticketId_.equals(ticketId)) {
					if (seat.has("latestActivity")) {
						JSONObject latestActivity = (JSONObject) seat.get("latestActivity");
						if (latestActivity.has("recipient")) {
							JSONObject recipient = (JSONObject) latestActivity.get("recipient");
							recipientEmail = recipient.getString("email");
							return recipientEmail;
						} else {
							return null;
						}
					}
				}
			}
		}
		return null;
	}

}



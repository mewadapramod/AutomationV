package org.iomedia.galen.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AccessToken extends BaseUtil{
	
	public AccessToken(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<HashMapNew> sTestDetails) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	}
	
	Utils utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	String host= Environment.get("TM_HOST").trim();
	
	public String getAccessToken(String emailaddress, String password) throws Exception {
		String access_token = null;
		int responseCode = 0;
		int counter = 3;
		
		do{
			Object[] obj = postOauthToken(emailaddress, password);
			access_token = (String) obj[0];
			responseCode = (int) obj[1];
			sync(100L);
			counter--;
		} while(counter > 0 && (access_token == null));
		Assert.assertNotNull(access_token, "Server response code : " + String.valueOf(responseCode));
		getAccountId(access_token);
		return access_token;
	}
	
	public Object[] postOauthToken(String emailaddress, String password) throws IOException, JSONException{
		if(Environment.get("TM_OAUTH_URL").trim().endsWith("/")) {
			Environment.put("TM_OAUTH_URL", Environment.get("TM_OAUTH_URL").trim().substring(0, Environment.get("TM_OAUTH_URL").trim().length() - 1));
		}
		URL url = new URL(Environment.get("TM_OAUTH_URL").trim());
	    Map<String,Object> params = new LinkedHashMap<>();
	    params.put("client_id", Environment.get("CLIENT_ID").trim());
	    params.put("client_secret", Environment.get("CLIENT_SECRET").trim());
	    params.put("grant_type", "password");
	    params.put("username", emailaddress);
	    params.put("password", password);
	    
	    StringBuilder postData = new StringBuilder();
	    for (Map.Entry<String,Object> param : params.entrySet()) {
	        if (postData.length() != 0) postData.append('&');
	        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	        postData.append('=');
	        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	    }
	    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

	    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	    conn.setDoOutput(true);
	    conn.getOutputStream().write(postDataBytes);
	    
	    int responseCode = conn.getResponseCode();
	    if(responseCode == 201 || responseCode == 200 || responseCode == 204){
		    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		    String output = "";
	        Boolean keepGoing = true;
	        while (keepGoing) {
	            String currentLine = br.readLine();
	            if (currentLine == null) {
	                keepGoing = false;
	            } else {
	                output += currentLine;
	            }
	        }
		    JSONObject jsonObject=new JSONObject(output);
		    String access_token=(String) jsonObject.get("access_token");
		    return new Object[]{access_token, responseCode};
	    } else {

	    	return new Object[]{null, responseCode};
	    }
	}
	
	public String getAccountId(String access_token) throws Exception{
	    String Access_tokens=access_token;
	    if(Environment.get("TM_OAUTH_URL").trim().endsWith("/")) {
			Environment.put("TM_OAUTH_URL", Environment.get("TM_OAUTH_URL").trim().substring(0, Environment.get("TM_OAUTH_URL").trim().length() - 1));
		}
		URL url = new URL(Environment.get("TM_OAUTH_URL").trim() + "/" + Access_tokens);

	    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Content-Type", "application/json");
	    
	    if (conn.getResponseCode() != 200) {
	    	throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
		}

	    Scanner scan = new Scanner(url.openStream());
		String response = new String();
		while (scan.hasNext())
		response += scan.nextLine();
		scan.close();
			
		JSONObject jsonObject=new JSONObject(response);
		String umember_token=(String) jsonObject.get("umember_token");
		//System.out.println("umember_token :" + umember_token);
		Dictionary.put("member_id", umember_token);
		Dictionary.put("AccessToken", access_token);
		//System.out.println("memberid: "+member_id );
		
		String[] account_id=umember_token.split("\\.");
		
		Dictionary.put("AccountId", account_id[1]);
		if(getTermsRequired(Access_tokens)) {
			acceptTerms(getTermsVersion(Access_tokens), Access_tokens);
		}
		System.out.println(account_id[1]);
		return account_id[1];
	}
	
	public JSONObject getMemberResponse(String emailAddress, String Password) throws Exception {
		String aToken= getAccessToken(emailAddress, Password);
		Dictionary.put("AccessToken", aToken);
		System.out.println(aToken);
		getAccountId(aToken);
		InputStream is = utils.waitForTMManageTicketsResponse(host + "/api/v1/member/" + Dictionary.get("member_id"), new String[] {"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[] {"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), aToken}, false);
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		String firstName = jsonObject.getString("first_name");
		String lastName = jsonObject.getString("last_name");
		firstName = firstName.trim().equalsIgnoreCase("") ? "" : firstName.trim();
		lastName = lastName.trim().equalsIgnoreCase("") ? "" : " " + lastName.trim();
		String custName = (firstName + "" + lastName).trim();
		Dictionary.put("CUST_NAME", custName);
		JSONArray jsonArray = jsonObject.getJSONArray("member_related_accounts");
//		Dictionary.put("associatedMembersJson", jsonArray.toString());
		Dictionary.put("associatedCount",String.valueOf(jsonArray.length()));
		for(int i=0;i<jsonArray.length();i++) {
			String memberId = jsonArray.getJSONObject(i).getString("member_id");
			String accountId = memberId.split("\\.")[1].trim();
			Dictionary.put("memberId"+i, memberId);
			Dictionary.put("accId"+i, accountId);
			Dictionary.put("name"+i, jsonArray.getJSONObject(i).getString("name").trim());
			Dictionary.put("name_"+accountId, jsonArray.getJSONObject(i).getString("name").trim());
		}
		return jsonObject;
	}
	
	public boolean getTermsRequired(String accessToken) throws Exception	{
		JSONObject getTerms=get(host + "/api/v1/member/" + Dictionary.get("member_id"), accessToken);
		return getTerms.getBoolean("is_terms_of_use_acceptance_required");
	}
	
	public String getTermsVersion(String accessToken) throws Exception	{
		JSONObject getTermsVersion=get(host + "/api/v1/text/termsofuse?include_text=1",accessToken);
		return getTermsVersion.getString("version");
	}
	
	public void acceptTerms(String version, String accessToken) throws Exception{
		utils.post(host + "/api/v1/member/" + Dictionary.get("member_id") +"/accept", "{\"type\": \"terms_of_use\",\"value\": \""+version+"\"}", new String[] {"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[] {"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(),accessToken});
	}
	
	public void switchToAssociatedMember(String memberId) throws Exception{
		InputStream ab=utils.waitForTMManageTicketsResponse(host + "/api/v1/member/" + memberId, new String[] {"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[] {"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), Dictionary.get("AccessToken")}, false);
		JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(ab, "UTF-8")));
		System.out.println(jsonObject.toString(2));
	}
	
	public JSONObject get(String url, String accesstoken) throws Exception {
		  InputStream is = utils.waitForTMManageTicketsResponse(url, new String[] {"Content-Type", "Accept", "Accept-Language", "X-Client", "X-Api-Key", "X-OS-Name", "X-OS-Version", "X-Auth-Token"}, new String[] {"application/json", Environment.get("amgrVersion").trim().replace(" ", "+"), Environment.get("acceptLanguage").trim(), Environment.get("x-client").trim(), Environment.get("x-api-key").trim(), Environment.get("x-os-name").trim(), Environment.get("x-os-version").trim(), accesstoken}, false);
		  JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		  return jsonObject;
	}


	public String postOauthTokenAAPI() throws IOException, JSONException {
		String url = Environment.get("AAPI_AUTH").trim()+"/userauth";
		String token = null;
		try {
			InputStream is = utils.post(url, "{\"username\":\"inet01\",\"password\":\"xinet01\"}", new String[]{"Content-Type", "DSN", "ClientId", "Accept-Language"}, new String[]{"application/json", "genesis", "genesis", "en-us"});
			JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
			token = jsonObject.getString("token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
	
}
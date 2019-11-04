package org.iomedia.galen.framework;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.iomedia.framework.Driver.HashMapNew;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.GetElementEnabled;

public class Util {
	HashMapNew Environment;
	
	public Util(HashMapNew Environment) {
		this.Environment = Environment;
	}
	
	public String getRelatedEnv() {
		String APP_URL = Environment.get("APP_URL").trim();
		String clientId = APP_URL.substring(APP_URL.lastIndexOf("/") + 1);
		if(clientId.trim().endsWith("/")) {
			clientId = clientId.substring(0, clientId.trim().length() - 1);
		}
		String env = "";
		switch(clientId.trim().toUpperCase()) {
			case "IOMEDIAQAUNITAS" :
			case "TAG7" :
				env = "UNITAS";
				break;
			case "IOMEDIA3" :
				env = "DEMO";
				break;
			case "IOMEDIAQACMS" :
				env = "UNITAS-CMS";
				break;
			default :
				String relatedEnv = System.getProperty("relatedEnv") != null && !System.getProperty("relatedEnv").trim().equalsIgnoreCase("") ? System.getProperty("relatedEnv").trim().toUpperCase() : clientId.trim().toUpperCase();
				env = relatedEnv;
		}
		
		return env;
    }
	
	public JSONObject convertToJSON(Reader jsonStream) throws JSONException, IOException{
		String jsonText = readAll(jsonStream);
		if(!jsonText.trim().equalsIgnoreCase("") && (jsonText.trim().startsWith("{") || jsonText.trim().startsWith("["))) {
			JSONObject json = new JSONObject(jsonText);  	    
			return json;
		} else
			return null;
	}
	
	public String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	}
	
	public String post(String url, String payload, String[] key, String[] value) throws Exception{
        final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            @Override
            public void checkClientTrusted( final X509Certificate[] chain, final String authType ) {
            }
            @Override
            public void checkServerTrusted( final X509Certificate[] chain, final String authType ) {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        } };
        
        final SSLContext sslContext = SSLContext.getInstance( "SSL" );
        sslContext.init( null, trustAllCerts, new java.security.SecureRandom() );
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();            
        
        URLConnection urlCon = new URL(url).openConnection();            
        urlCon.setRequestProperty("Method", "POST");
        
        if(key != null && value != null)
        	urlCon = setHeaders(urlCon, key, value);
        
        urlCon.setDoOutput(true);	            	            
        // Send request
        DataOutputStream wr = new DataOutputStream(urlCon.getOutputStream());
    	wr.writeBytes(payload);
        wr.flush();
        wr.close();
        
        int responseCode = 0;
        
        if(url.trim().toLowerCase().startsWith("https://")){
        	( (HttpsURLConnection) urlCon ).setSSLSocketFactory( sslSocketFactory );
        	responseCode = ((HttpsURLConnection)urlCon).getResponseCode();
        }
        else{
        	responseCode = ((HttpURLConnection)urlCon).getResponseCode();
        }
        
        if(responseCode == 201 || responseCode == 200 || responseCode == 204){
        	//Do Nothing
        }
        else{
        	throw new Exception("Server response code : " + responseCode);
        }
		return getResponseHeaders(urlCon);
	}
	
	public String getResponseHeaders(URLConnection urlCon) {
		Map<String, List<String>> responseheaders = urlCon.getHeaderFields();
        Set<String> keys = responseheaders.keySet();
        Iterator<String> iter = keys.iterator();
        String cookies = "";
        while(iter.hasNext()){
        	String keyName = iter.next();
        	if ("Set-Cookie".equalsIgnoreCase(keyName)) {
        		List<String> headerFieldValue = responseheaders.get(keyName);
        		for (String headerValue : headerFieldValue) {
        			String[] fields = headerValue.split(";\\s*");
        			String cookieValue = fields[0];
        			cookies += cookieValue + ";";
        		}
        	}
        }
        return cookies;
	}
	
	public URLConnection setHeaders(URLConnection urlCon, String[] key, String[] value){
		for(int i = 0 ; i < key.length; i++){
			if(!Environment.get("USER_AGENT").trim().equalsIgnoreCase("")) {
				if(key[i].trim().equalsIgnoreCase("user-agent")) {
					value[i] = value[i].trim() + " " + Environment.get("USER_AGENT").trim(); 
				}
			}
			urlCon.setRequestProperty(key[i].trim(), value[i].trim());
		}
		
		return urlCon;
	}
	
	public InputStream get(String url, String[] key, String[] value) throws Exception{
		InputStream is = null;
        final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
			
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				
			}
			
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				
			}
		}};
        
        final SSLContext sslContext = SSLContext.getInstance( "SSL" );
        sslContext.init( null, trustAllCerts, new java.security.SecureRandom() );
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();            
        
        URLConnection urlCon = new URL(url).openConnection();
        urlCon.setRequestProperty("Method", "GET");
        
        if(key != null && value != null){
        	urlCon = setHeaders(urlCon, key, value);
        }
        
        int responseCode = 0;
        
        if(url.trim().toLowerCase().startsWith("https://")){
        	( (HttpsURLConnection) urlCon ).setSSLSocketFactory( sslSocketFactory );
        	responseCode = ((HttpsURLConnection)urlCon).getResponseCode();
        }
        else{
        	responseCode = ((HttpURLConnection)urlCon).getResponseCode();
        }
        
        if(responseCode == 200 || responseCode == 204) {
        	is = urlCon.getInputStream();  
        }
        else {
        	if(url.trim().toLowerCase().startsWith("https://"))
        		is = ((HttpsURLConnection)urlCon).getErrorStream();
    		else
    			is = ((HttpURLConnection)urlCon).getErrorStream();
    		throw new Exception("Server response code : " + responseCode);
        }
		return is;
	}
	
	public boolean isProd() {
    	String tm_host = Environment.get("APP_URL").trim();
    	if(tm_host.contains("am.ticketmaster.com") || tm_host.contains("support.nam.prd214.preprodpci3.us-east-1.tktm.io")) {
    		return true;
    	} else {
    		return false;
    	}
    }
	
	public String getAppUrl() {
    	String tm_host = Environment.get("TM_HOST").trim();
    	if(tm_host.contains("qa1.acctmgr.us-east-1.nonprod-tmaws.io")) {
    		return "https://qa.nam.prd214.nonprod3.us-east-1.tktm.io";
    	} else if(tm_host.contains("stg1.acctmgr.us-east-1.ppub-tmaws.io") || tm_host.contains("staging-oss.ticketmaster.com")) {
    		return "https://stg1-am.ticketmaster.com";
    	} else if(tm_host.contains("oss.ticketmaster.com") || tm_host.contains("app.ticketmaster.com")) {
    		return "https://am.ticketmaster.com";
    	}
		return null;
    }
	
	public void sync(Long sTime) {
		try {
			Thread.sleep(sTime);
		} catch (InterruptedException e) {			
			//Do Nothing
		}
	}
	
	public InputStream waitForTMGetResponse(String url, String[] key, String[] value) throws JSONException, Exception {
		Object[] response = null;
		int responseCode = 0;
		int counter = 3;
		do{
			response = get(url, key, value, true);
			responseCode = (int)response[1];
			sync(100L);
			counter--;
		} while(counter > 0 && (responseCode != 200 && responseCode != 204));
		if(responseCode != 200 && responseCode != 204) {
			throw new Exception("Server response code : " + responseCode);
		}
		return (InputStream) response[0];
	}
	
	public InputStream waitForTMGetResponse(String url, String[] key, String[] value, int counter) throws JSONException, Exception {
		Object[] response = null;
		int responseCode = 0;
		do{
			System.out.println("Site is coming up");
			response = get(url, key, value, true);
			responseCode = (int)response[1];
			sync(1000L);
			counter--;
		} while(counter > 0 && (responseCode != 200 && responseCode != 204));
		if(responseCode != 200 && responseCode != 204) {
			throw new Exception("Server response code : " + responseCode);
		}
		return (InputStream) response[0];
	}
	
	public String waitForTMPostResponse(String url, String payload, String[] key, String[] value) throws JSONException, Exception {
		Object[] response = null;
		int responseCode = 0;
		int counter = 3;
		do{
			response = post(url, payload, key, value, true);
			responseCode = (int)response[1];
			sync(100L);
			counter--;
		} while(counter > 0 && (responseCode != 200 && responseCode != 204 && responseCode != 201));
		if(responseCode != 200 && responseCode != 204 && responseCode != 201) {
			throw new Exception("Server response code : " + responseCode);
		}
		return getResponseHeaders((URLConnection)response[2]);
	}
	
	public Object[] get(String url, String[] key, String[] value, boolean skipException) throws Exception{
		InputStream is = null;
        final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
			
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				
			}
			
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				
			}
		}};
        
        final SSLContext sslContext = SSLContext.getInstance( "SSL" );
        sslContext.init( null, trustAllCerts, new java.security.SecureRandom() );
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();            
        
        URLConnection urlCon = new URL(url).openConnection();
        urlCon.setRequestProperty("Method", "GET");
        
        if(key != null && value != null){
        	urlCon = setHeaders(urlCon, key, value);
        }
        
        int responseCode = 0;
        
        if(url.trim().toLowerCase().startsWith("https://")){
        	( (HttpsURLConnection) urlCon ).setSSLSocketFactory( sslSocketFactory );
        	responseCode = ((HttpsURLConnection)urlCon).getResponseCode();
        }
        else{
        	responseCode = ((HttpURLConnection)urlCon).getResponseCode();
        }
        
        if(responseCode == 200 || responseCode == 204) {
        	is = urlCon.getInputStream();  
        }
        else {
        	if(url.trim().toLowerCase().startsWith("https://"))
        		is = ((HttpsURLConnection)urlCon).getErrorStream();
    		else
    			is = ((HttpURLConnection)urlCon).getErrorStream();
        	if(!skipException)
        		throw new Exception("Server response code : " + responseCode);
        }
     
		return new Object[]{is, responseCode};
	}
	
	public Object[] post(String url, String payload, String[] key, String[] value, boolean skipException) throws Exception{
		InputStream is = null;
        final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            @Override
            public void checkClientTrusted( final X509Certificate[] chain, final String authType ) {
            }
            @Override
            public void checkServerTrusted( final X509Certificate[] chain, final String authType ) {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        } };
        
        final SSLContext sslContext = SSLContext.getInstance( "SSL" );
        sslContext.init( null, trustAllCerts, new java.security.SecureRandom() );
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();            
        
        URLConnection urlCon = new URL(url).openConnection();            
        urlCon.setRequestProperty("Method", "POST");
        
        if(key != null && value != null)
        	urlCon = setHeaders(urlCon, key, value);
        
        urlCon.setDoOutput(true);	            	            
        // Send request
        DataOutputStream wr = new DataOutputStream(urlCon.getOutputStream());
    	wr.writeBytes(payload);
        wr.flush();
        wr.close();
        
        int responseCode = 0;
        
        if(url.trim().toLowerCase().startsWith("https://")){
        	( (HttpsURLConnection) urlCon ).setSSLSocketFactory( sslSocketFactory );
        	responseCode = ((HttpsURLConnection)urlCon).getResponseCode();
        }
        else{
        	responseCode = ((HttpURLConnection)urlCon).getResponseCode();
        }
        
        if(responseCode == 201 || responseCode == 200 || responseCode == 204){
        	is = urlCon.getInputStream();
        }
        else{
        	if(url.trim().toLowerCase().startsWith("https://"))
        		is = ((HttpsURLConnection)urlCon).getErrorStream();
    		else
    			is = ((HttpURLConnection)urlCon).getErrorStream();
        	if(!skipException)
        		throw new Exception("Server response code : " + responseCode);
        }
		return new Object[]{is, responseCode, urlCon};
	}
	

}

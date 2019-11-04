 package org.iomedia.galen.framework;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.iomedia.framework.Driver;
import org.iomedia.framework.OSValidator;
import org.iomedia.framework.WebDriverFactory;
import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Sessions extends Driver {
	HashMapNew Environment;
	WebDriverFactory driverFactory;
	String datasheet;
	
	public Sessions(WebDriverFactory driverFactory, HashMapNew Environment, String datasheet) {
		this.Environment = Environment;
		this.driverFactory = driverFactory;
		this.datasheet = datasheet;
	}
	
	public void setSessionLimit(String session, String surveyValue, boolean enableParking, boolean enablehandling, boolean postBuild) throws Exception {
		String url = Environment.get("APP_URL").trim();
		String userName = System.getProperty("adminUserName") != null && !System.getProperty("adminUserName").trim().equalsIgnoreCase("") ? System.getProperty("adminUserName").trim() : Environment.get("adminUserName").trim();
		String password = System.getProperty("adminPassword") != null && !System.getProperty("adminPassword").trim().equalsIgnoreCase("") ? System.getProperty("adminPassword").trim() : Environment.get("adminPassword").trim();
		String tm_oauth_url = Environment.get("TM_OAUTH_URL").trim();
    	if(userName.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
    		if(tm_oauth_url.contains("app.ticketmaster.com")){
    			String clientId = url.substring(url.lastIndexOf("/") + 1);
    			if(clientId.trim().endsWith("/")) {
    				clientId = clientId.substring(0, clientId.trim().length() - 1);
    			}
    			userName = "admin";
    			//password = clientId + "1234";
    			password = "123456";
    		} else {
    			return;
    		}
    	}
		
    	super.driverFactory = this.driverFactory;
    	super.driverType = driverFactory.getDriverType().get();
    	super.sTestDetails = driverFactory.getTestDetails();
    	String videoGifIntegration = Environment.get("videoGifIntegration").trim();
    	Environment.put("videoGifIntegration", "false");
    	super.Environment = Environment;
		WebDriver driver = createDriver(new Object[]{});
		try {
			if(!postBuild) {
				checkEnv(url + "/user/login", 200);
				System.out.println("Site is throwing 200");
				int counter = 50;
				do {
					driver.get(url + "/user/login");
					BaseUtil.sync(500L);
					System.out.println("Site is loading");
					try {
						System.out.println(BaseUtil.getText(By.cssSelector(".adminFooter p"), 10));
						BaseUtil.getElementWhenVisible(By.id("edit-name"), 10);
						break;
					} catch(Exception ex) {
						counter--;
					}
				} while(counter > 0);
				if(counter <= 0) {
					System.out.println("Site is not coming up");
					throw new Exception("Site is not coming up");
				}
			} else {
				driver.get(url + "/user/login");
			}
			sendKeys(By.id("edit-name"), userName, "CMSLogin");
			sendKeys(By.id("edit-pass"), password, "CMSLogin");
			JavascriptExecutor ex = (JavascriptExecutor)driver;
			ex.executeScript("arguments[0].click();", driver.findElement(By.id("edit-submit")));
			
			if(!BaseUtil.checkIfElementPresent(By.id("toolbar-bar"), 10))	{
				String oldUsername = userName;
				String oldPassword = password;
				if(oldUsername.trim().equalsIgnoreCase("admin") && oldPassword.trim().equalsIgnoreCase("123456")) {
					userName = "admin";
					//password = "Jl7%q1*K";
					password = "123456";

					
					clear(By.id("edit-name"), "CMSLogin");
					sendKeys(By.id("edit-name"), userName, "CMSLogin");
					clear(By.id("edit-pass"), "CMSLogin");
					sendKeys(By.id("edit-pass"), password, "CMSLogin");
					ex.executeScript("arguments[0].click();", driver.findElement(By.id("edit-submit")));
					if(!BaseUtil.checkIfElementPresent(By.id("toolbar-bar"), 10)) {
						return;
					} else {
						setXMLNodeValue(Environment.get("appCredentialsPath").trim(), Environment.get("env").trim(), Environment.get("version").trim(), "adminUserName", userName);
						setXMLNodeValue(Environment.get("appCredentialsPath").trim(), Environment.get("env").trim(), Environment.get("version").trim(), "adminPassword", password);
					}
				} else {
					userName = "admin";
					password = "123456";
					clear(By.id("edit-name"), "CMSLogin");
					sendKeys(By.id("edit-name"), userName, "CMSLogin");
					clear(By.id("edit-pass"), "CMSLogin");
					sendKeys(By.id("edit-pass"), password, "CMSLogin");
					ex.executeScript("arguments[0].click();", driver.findElement(By.id("edit-submit")));
					if(!BaseUtil.checkIfElementPresent(By.id("toolbar-bar"), 10)) {
						if(oldUsername.trim().equalsIgnoreCase("admin") && oldPassword.trim().equalsIgnoreCase("Jl7%q1*K")) {
							return;
						} else {
							userName = "admin";
							//password = "Jl7%q1*K";
							password = "123456";
							clear(By.id("edit-name"), "CMSLogin");
							sendKeys(By.id("edit-name"), userName, "CMSLogin");
							clear(By.id("edit-pass"), "CMSLogin");
							sendKeys(By.id("edit-pass"), password, "CMSLogin");
							ex.executeScript("arguments[0].click();", driver.findElement(By.id("edit-submit")));
							if(!BaseUtil.checkIfElementPresent(By.id("toolbar-bar"), 10)) {
								return;
							}
						}
					}
					setXMLNodeValue(Environment.get("appCredentialsPath").trim(), Environment.get("env").trim(), Environment.get("version").trim(), "adminUserName", userName);
					setXMLNodeValue(Environment.get("appCredentialsPath").trim(), Environment.get("env").trim(), Environment.get("version").trim(), "adminPassword", password);
				}
			}
			
			Util util= new Util(Environment);
			if(util.isProd() && datasheet.trim().equalsIgnoreCase("PRE_DEPLOYMENT")) {
				/*Get barcode number checkbox value*/
				driver.navigate().to(url + "/admin/config/site-settings");
				WebElement barcode_number_checkbox = driver.findElement(By.id("edit-manage-ticket-configuration-barcode-number-enabled"));
				String value = barcode_number_checkbox.getAttribute("checked");
				setXMLNodeValue(Environment.get("appCredentialsPath").trim(), Environment.get("env").trim(), Environment.get("version").trim(), "barcode_number_set", value == null ? "false" : "true");
				System.out.println("Barcode number state saved");
				return;
			}
			
			/* Set session limit*/
			driver.navigate().to(url + "/admin/config/people/session-limit");
			WebElement sessionInput = driver.findElement(By.id("edit-session-limit-max"));
			String oldValue = sessionInput.getAttribute("value");
			System.out.println(oldValue);
			
//			if(oldValue.trim().equalsIgnoreCase(session)) {
//				return;
//			}
			
			sessionInput.clear();
			sessionInput.sendKeys(session);
			ex.executeScript("arguments[0].click();", driver.findElement(By.id("edit-submit")));
			setXMLNodeValue(Environment.get("appCredentialsPath").trim(), Environment.get("env").trim(), Environment.get("version").trim(), "sessions", oldValue);
			System.out.println("Session limit set");
			
			/*Create editor manager role*/
			if(!postBuild) {
				driver.navigate().to(url + "/admin/people/create");
				WebElement emailAddress = driver.findElement(By.id("edit-mail--2"));
				emailAddress.sendKeys("iomediaautomation@ticketmaster.com");
				WebElement username = driver.findElement(By.id("edit-name"));
				WebElement newpassword = driver.findElement(By.id("edit-pass-pass1"));
				WebElement confirmpassword = driver.findElement(By.id("edit-pass-pass2"));
				username.sendKeys("automation_editor_manager");
				newpassword.sendKeys("nam_editor_manager");
				confirmpassword.sendKeys("nam_editor_manager");
				WebElement editorManagerRole = driver.findElement(By.cssSelector("label[for*='edit-roles-editor-manager']"));
				WebElement tmSupportRole = driver.findElement(By.cssSelector("label[for*='edit-roles-support-admin1']"));
				ex.executeScript("arguments[0].click();", tmSupportRole);
				ex.executeScript("arguments[0].click();", editorManagerRole);
				ex.executeScript("arguments[0].click();", driver.findElement(By.id("edit-submit")));
				BaseUtil.getElementWhenVisible(By.cssSelector("div[class*='messages--status'] , div[class*='messages--error']"), 5);
				System.out.println("CMS user created");
			}
			
			if(util.isProd() && (datasheet.trim().equalsIgnoreCase("PROD_SANITY") || datasheet.trim().equalsIgnoreCase("PRE_DEPLOYMENT"))) {
				driver.navigate().to(url + "/user/logout");
				BaseUtil.getElementWhenPresent(By.xpath(".//input[@name='email'] | .//div[@class='mobile-signin']//*[text()='Sign In'] | .//div[@class='desktop-signin-dashboard']//a[text()='Sign In']"));
				String appVersion = getAppVersionNew(driver, url);
				System.out.println("APP_VERSION :: " + appVersion);
				setXMLNodeValue(Environment.get("appCredentialsPath").trim(), Environment.get("env").trim(), Environment.get("version").trim(), "appVersion", appVersion);
				return;
			}
			
			driver.navigate().to(url + "/admin/invoice/list");
			By selectedInvoiceTemplate = By.xpath(".//table[@id='search' or @id='search_default']//tbody//tr//td//input[@checked='']/../../..//td//i | .//table[@id='search' or @id='search_default']//tbody//tr//td//input/../../..//td//i");
			if(BaseUtil.checkIfElementPresent(selectedInvoiceTemplate)) {
				WebElement settings = driver.findElement(selectedInvoiceTemplate);
				settings.click();
			
				By lAllInvoices = By.xpath(".//label[text()='All Invoices']/../input");
				if(BaseUtil.checkIfElementPresent(lAllInvoices, 1)) {
					WebElement allinvoices = driver.findElement(lAllInvoices);
					allinvoices.click();
				}
				
				try {
					boolean parking = false, handling = false;
					/*Enable parking and handling*/
					if(BaseUtil.checkIfElementPresent(By.xpath(".//strong[text()='INVOICE LABELS']"), 1)) {
						WebElement invoiceLabels = driver.findElement(By.xpath(".//strong[text()='INVOICE LABELS']"));
						ex.executeScript("arguments[0].click();", invoiceLabels);
						WebElement enable_parking = driver.findElement(By.cssSelector("input[type='checkbox'][name*='park']"));
						if(enableParking && enable_parking.getAttribute("checked") == null) {
							ex.executeScript("arguments[0].click();", enable_parking);
							parking = true;
						} else if(!enableParking && enable_parking.getAttribute("checked") != null && enable_parking.getAttribute("checked").trim().equalsIgnoreCase("true")) {
							ex.executeScript("arguments[0].click();", enable_parking);
							parking = false;
						}
						WebElement enable_handling = driver.findElement(By.cssSelector("input[type='checkbox'][name*='hand']"));
						if(enablehandling && enable_handling.getAttribute("checked") == null) {
							ex.executeScript("arguments[0].click();", enable_handling);
							handling = true;
						} else if(!enablehandling && enable_handling.getAttribute("checked") != null && enable_handling.getAttribute("checked").trim().equalsIgnoreCase("true")) {
							ex.executeScript("arguments[0].click();", enable_handling);
							handling = false;
						}
					} else {
						WebElement invoiceSummary = driver.findElement(By.xpath(".//strong[text()='INVOICE SUMMARY']"));
						ex.executeScript("arguments[0].click();", invoiceSummary);
						By locatorParking = By.cssSelector("input[value*='parking' i]");
						if(enableParking && !BaseUtil.checkIfElementPresent(locatorParking, 1)) {
							click(By.cssSelector("input[value*='Add Another']"), By.cssSelector("#multi-replace div[class*='field-name'] input[value='']"), 10);
							WebElement parkingField = BaseUtil.getElementWhenVisible(By.cssSelector("#multi-replace div[class*='field-name'] input[value='']"));
							parkingField.sendKeys("Parking Fees");
							parking = true;
						} else if(!enableParking && BaseUtil.checkIfElementPresent(locatorParking, 1)) {
							click(By.xpath(".//input[contains(translate(@value,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'parking')]/../..//following-sibling::div[2]/input"));
							BaseUtil.sync(2000L);
							parking = false;
						}
						By locatorHandling = By.cssSelector("input[value*='handling' i]");
						if(enablehandling && !BaseUtil.checkIfElementPresent(locatorHandling, 1)) {
							click(By.cssSelector("input[value*='Add Another']"), By.cssSelector("#multi-replace div[class*='field-name'] input[value='']"), 10);
							BaseUtil.getElementWhenVisible(locatorParking, 5);
							WebElement handlingField = BaseUtil.getElementWhenVisible(By.cssSelector("#multi-replace div[class*='field-name'] input[value='']"));
							handlingField.sendKeys("Handling Fees");
							handling = true;
						} else if(!enablehandling && BaseUtil.checkIfElementPresent(locatorHandling, 1)) {
							click(By.xpath(".//input[contains(translate(@value,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'handling')]/../..//following-sibling::div[2]/input"));
							BaseUtil.sync(2000L);
							handling = false;
						}
					}
					
					setXMLNodeValue(Environment.get("appCredentialsPath").trim(), Environment.get("env").trim(), Environment.get("version").trim(), "enableParking", String.valueOf(!parking));
					setXMLNodeValue(Environment.get("appCredentialsPath").trim(), Environment.get("env").trim(), Environment.get("version").trim(), "enableHandling", String.valueOf(!handling));
					System.out.println("Parking and handling labels set");
				} catch(Exception ex1) {
					System.out.println("Not able to add parking or handling labels");
					ex1.printStackTrace();
				}
				
				/*Set survey setting*/
				if(BaseUtil.checkIfElementPresent(By.xpath(".//strong[text()='SURVEY' or text()='QUESTIONS']"), 10)) {
					WebElement surveyTab = driver.findElement(By.xpath(".//strong[text()='SURVEY' or text()='QUESTIONS']"));
					ex.executeScript("arguments[0].click();", surveyTab);
					By dropdown = By.cssSelector("div[class*='survey_position'] select");
					WebElement showSurvey = driver.findElement(dropdown);
					if(showSurvey.getAttribute("disabled") == null) {
						Select select = new Select(showSurvey);
						String selectedValue = select.getFirstSelectedOption().getText();
						if(surveyValue.trim().equalsIgnoreCase(""))
							select.selectByValue("accordionReview");
						else
							select.selectByVisibleText(surveyValue.trim());
						setXMLNodeValue(Environment.get("appCredentialsPath").trim(), Environment.get("env").trim(), Environment.get("version").trim(), "showSurvey", selectedValue);
						System.out.println("Survey setting set");
					}
				}
				
				ex.executeScript("arguments[0].click();", driver.findElement(By.id("edit-submit")));
			}
			driver.navigate().to(url + "/user/logout");
			BaseUtil.getElementWhenPresent(By.xpath(".//input[@name='email'] | .//div[@class='mobile-signin']//*[text()='Sign In'] | .//div[@class='desktop-signin-dashboard']//a[text()='Sign In']"));
			String appVersion= getAppVersionNew(driver, url);
			System.out.println("APP_VERSION :: " + appVersion);
			setXMLNodeValue(Environment.get("appCredentialsPath").trim(), Environment.get("env").trim(), Environment.get("version").trim(), "appVersion", appVersion);
		} catch(Exception ex) {
			throw ex;
		}
		finally {
			driver.quit();
			Environment.put("videoGifIntegration", videoGifIntegration);
	    	super.Environment = Environment;
		}
	}
	
	public String getAppVersionNew(WebDriver driver, String url) {
		driver.navigate().to(url + "/");
		WebElement e= driver.findElement(By.xpath("//body[contains(@class,'page-node-type-hybrid-home-page') or contains(@class,'page-node-type-home-page')] | //body[contains(@class,'path-frontpage')]"));
		String version =e.getAttribute("data-version");
		return version;		
	}
	
	public String getAppVersion(WebDriver driver, String url, String userName, String password, boolean postBuild) {
		if(postBuild)
			return Environment.get("appVersion").trim();
		driver.navigate().to(url + "/user/login");
		clear(By.id("edit-name"), "CMSLogin");
		sendKeys(By.id("edit-name"), userName, "CMSLogin");
		clear(By.id("edit-pass"), "CMSLogin");
		sendKeys(By.id("edit-pass"), password, "CMSLogin");
		JavascriptExecutor ex = (JavascriptExecutor)driver;
		ex.executeScript("arguments[0].click();", driver.findElement(By.id("edit-submit")));
		driver.navigate().to(url + "/admin/group-view");
		if(BaseUtil.checkIfElementPresent(By.xpath("//*[contains(@class,'thick')]"), 10))
		{
			click(By.xpath("//*[contains(@class,'thick')]"), 10);
		}
		By promotilesTitle = By.xpath(".//span[contains(@class,'js-quickedit-page-title')]");
		BaseUtil.getElementWhenVisible(promotilesTitle);
		try {
			return BaseUtil.getText(By.cssSelector(".build-text"), 1);
		} catch(Exception excep) {
			try {
				driver.findElement(By.cssSelector("aside[class*='collapsed']")).click();
				return BaseUtil.getText(By.cssSelector(".build-text"), 1);
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		return "";
	}
	
	public void setXMLNodeValue(String path, String env, String version, String tag, String value){
	    String RootPath = System.getProperty("user.dir");
	    try
	    {
	      String xmlPath = RootPath + path;
	      File fXmlFile = new File(xmlPath);
	      
	      if(!fXmlFile.exists())
	    	  return;
	      
	      DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
	      DocumentBuilder docBuilder = dbFac.newDocumentBuilder();
	      Document xmldoc = docBuilder.parse(fXmlFile);
	      
	      Node ENV = xmldoc.getElementsByTagName("ENV").item(0);
	      
	      XPathFactory xPathfac = XPathFactory.newInstance();
	      XPath xpath = xPathfac.newXPath();

	      XPathExpression expr = xpath.compile("//" + env.trim().toUpperCase());
	      Object obj = expr.evaluate(xmldoc, XPathConstants.NODESET);
	      if(obj != null) {
	    	  Node node = ((NodeList)obj).item(0);
	    	  if(node == null) {
		    	  Element envNode = xmldoc.createElement(env.trim().toUpperCase());
		    	  envNode.appendChild(xmldoc.createElement(version.trim().toUpperCase()));
		    	  ENV.appendChild(envNode);
	    	  }
	      }
	      
	      expr = xpath.compile("//" + env.trim().toUpperCase() + "/" + version.trim().toUpperCase() + "/COMMON");
	      obj = expr.evaluate(xmldoc, XPathConstants.NODESET);
	      if(obj != null) {
	    	  Node node = ((NodeList)obj).item(0);
	    	  if(node != null) {
	    		  NodeList nl = node.getChildNodes();
	    		  boolean flag = false;
	    		  for (int child = 0; child < nl.getLength(); child++) {
	    			  String nodeName = nl.item(child).getNodeName().trim();
	    			  if(nodeName.trim().equalsIgnoreCase(tag.trim())) {
	    				  nl.item(child).setTextContent(value);
	    				  flag = true;
	    				  break;
	    			  }
	    		  }
	    		  if(!flag) {
	    			  Element _node = xmldoc.createElement(tag.trim());
					  _node.appendChild(xmldoc.createTextNode(value));
					  node.appendChild(_node);
	    		  }
	    	  } else {
	    		  expr = xpath.compile("//" + env.trim().toUpperCase() + "/" + version.trim().toUpperCase()); 
	    		  obj = expr.evaluate(xmldoc, XPathConstants.NODESET);
	    		  node = ((NodeList)obj).item(0);
	    		  
	    		  Element commonNode = xmldoc.createElement("COMMON");
	    		  Element _node = xmldoc.createElement(tag.trim());
				  _node.appendChild(xmldoc.createTextNode(value));
				  commonNode.appendChild(_node);
		    	  node.appendChild(commonNode);
	    	  }
	      }
	      
	      //write the content into xml file
	      TransformerFactory transformerFactory = TransformerFactory.newInstance();
	      Transformer transformer = transformerFactory.newTransformer();
	      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	      
	      DOMSource source = new DOMSource(xmldoc);
	      System.out.println("Path : " + xmlPath);
	      StreamResult result = new StreamResult(new FileOutputStream(xmlPath));
	      transformer.transform(source, result);
	      System.out.println("Done");
	    }
	    catch (Exception excep){
	    	excep.printStackTrace();
	    }
	}
	
	public void click(By locator, long... waitSeconds){
		int counter = 2;
		WebElement we = BaseUtil.getElementWhenClickable(locator, waitSeconds);
		while(counter >= 0){
			try{
				if(we != null){
					javascriptClick(we);
					break;
				}
			} catch(Exception ex){
				if(counter == 0){
					throw ex;
				}
				BaseUtil.sync(500L);
				counter--;
			}
		}
	}
	
	public void click(By locator, By expectedLocator, long expectedLocatorWaitSeconds, long... waitSeconds){
		int counter = 2;
		WebElement we = BaseUtil.getElementWhenClickable(locator, waitSeconds);
		while(counter >= 0){
			try{
				if(we != null){
					javascriptClick(we);
					BaseUtil.getElementWhenVisible(expectedLocator, expectedLocatorWaitSeconds);
					break;
				}
			} catch(Exception ex){
				if(counter == 0){
					throw ex;
				}
				BaseUtil.sync(500L);
				counter--;
			}
		}
	}
	
	public boolean javascriptClick(WebElement webElement){   	 		
        //Click on the WebElement    		
        int intCount = 1;        
        while (intCount<=4){
        	try {
        		BaseUtil.scrollingToBottomofAPage();
    			webElement.click();
        		break;
	        }catch (Exception e){
	        	BaseUtil.sync(500L);
	        	if(intCount==4){
	    	    	throw e;
	        	}
    	    }  	    
    	    intCount++;
        }	        
        return true;    	       
    }
	
	public void checkEnv(String url, int counter) throws JSONException, Exception {
		Util util = new Util(Environment);
		util.waitForTMGetResponse(url, new String[]{"accept", "accept-encoding", "accept-language", "user-agent"}, new String[]{"application/json, text/plain, */*", "utf-8", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"}, counter);
	}
	
	private void sendKeys(By locator, String value, String filename) {
		try {
			BaseUtil.getElementWhenVisible(locator).sendKeys(value);
		} catch(Exception ex) {
			takescreenshot(filename);
			throw ex;
		}
	}
	
	private void clear(By locator, String filename) {
		try {
			BaseUtil.getElementWhenVisible(locator).clear();
		} catch(Exception ex) {
			takescreenshot(filename);
			throw ex;
		}
	}
	
	private void takescreenshot(String filename) {
		String clientName = "";
		if(!Environment.get("APP_URL").trim().equalsIgnoreCase("")) {
			String appurl = Environment.get("APP_URL").trim();
			if(appurl.trim().endsWith("/"))
				appurl = appurl.trim().substring(0, appurl.trim().length() - 1);
			String clientId = appurl.substring(appurl.lastIndexOf("/") + 1).trim().toUpperCase();
			clientName = clientId;
		} else {
			clientName = Environment.get("x-client").trim();
		}
		clientName = clientName.trim().toLowerCase();
		File folder1 = ((TakesScreenshot)driverFactory.getDriver().get()).getScreenshotAs(OutputType.FILE);
		try {
			java.util.Date today = new java.util.Date();
			Timestamp now = new java.sql.Timestamp(today.getTime());
			String tempNow[] = now.toString().split("\\.");
			final String sStartTime = tempNow[0].replaceAll(":", ".").replaceAll(" ", "T");
			FileUtils.copyFile(folder1, new File(getFilepath(clientName, false) + OSValidator.delimiter + filename + "_" + sStartTime + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getFilepath(String clientName, boolean deleteExistingDirectory) throws Exception {
		if(deleteExistingDirectory)
			deleteDirectoryIfExists(clientName);
		File file = siteFoldercreation(createFolder(), clientName);
		return file.getAbsolutePath();	
	}
	
	private void deleteDirectoryIfExists(String clientName) throws Exception {
        String path1=  System.getProperty("user.dir") + OSValidator.delimiter + "CompareScreenshots" + OSValidator.delimiter + clientName.trim();
        File path = new File(path1);
        if (path.exists()) {
            System.out.println("delete the directory");
            FileUtils.deleteDirectory(path);
        }
    }
	
	private File createFolder() throws Exception {
		File file = new File("CompareScreenshots");
		if(!file.exists()) {
	        if (file.mkdir()) {
	            System.out.println("Directory is created!");
	        } else {           
	            System.out.println("Failed to create directory!");
	        }
		}
		return file;
	}
	
	private File siteFoldercreation(File parent, String clientName) {
	    File subFile = new File(parent, clientName);
	    subFile.mkdir();
	    if (!subFile.exists() || !subFile.isDirectory()) {
	    	System.out.println("Failed to create Subdirectory");
	    }
	    return subFile;
	}
}
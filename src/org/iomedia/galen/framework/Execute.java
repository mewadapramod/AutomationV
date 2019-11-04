package org.iomedia.galen.framework;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import org.iomedia.common.EncryptDecrypt;
import org.iomedia.framework.DBActivities;
import org.iomedia.framework.Driver;
import org.iomedia.framework.Infra;
import org.iomedia.framework.OSValidator;
import org.iomedia.framework.WebDriverFactory;
import org.json.JSONException;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.CharMatcher;

public class Execute extends Driver {
	
	String[] getAppCredentials(String env, String version, String driverType, String datasheet) {
		String relatedEnvDataPath = Environment.get("appCredentialsPath").trim();
    	relatedEnvDataPath = relatedEnvDataPath.substring(0, relatedEnvDataPath.lastIndexOf("_")) + "_" + Environment.get("env").trim().toUpperCase() + ".xml";
		HashMapNew temp = GetXMLNodeValue(Environment.get("appCredentialsPath").trim(), "//" + env.trim().toUpperCase() + "/" + version.trim().toUpperCase() + "/" + driverType.trim().toUpperCase() , 0);
		if(temp.isEmpty()) {
			temp = GetXMLNodeValue(relatedEnvDataPath, "//" + env.trim().toUpperCase() + "/" + version.trim().toUpperCase() + "/" + driverType.trim().toUpperCase() , 0);
		}
		String emailaddress = null, password = null;
		Util util= new Util(Environment);
		if(util.isProd() && (datasheet.trim().equalsIgnoreCase("PROD_SANITY") || datasheet.trim().equalsIgnoreCase("PRE_DEPLOYMENT"))) {
			emailaddress = Environment.get("EMAIL_ADDRESS").trim().equalsIgnoreCase("") ? null : Environment.get("EMAIL_ADDRESS").trim();
			password = Environment.get("PASSWORD").trim().equalsIgnoreCase("") ? null : Environment.get("PASSWORD").trim();
		}
		if(temp != null && emailaddress == null && password == null) {
			String appCredentialsEmailAddressKey = System.getProperty("appCredentialsEmailAddressKey") != null && !System.getProperty("appCredentialsEmailAddressKey").trim().equalsIgnoreCase("") ? System.getProperty("appCredentialsEmailAddressKey").trim() : Environment.get("appCredentialsEmailAddressKey").trim();
			String appCredentialsPasswordKey = System.getProperty("appCredentialsPasswordKey") != null && !System.getProperty("appCredentialsPasswordKey").trim().equalsIgnoreCase("") ? System.getProperty("appCredentialsPasswordKey").trim() : Environment.get("appCredentialsPasswordKey").trim();
			if(appCredentialsEmailAddressKey.contains("@")) {
				emailaddress = appCredentialsEmailAddressKey;
				password = appCredentialsPasswordKey;
			} else {
				emailaddress = temp.get(appCredentialsEmailAddressKey).trim();
				password = temp.get(appCredentialsPasswordKey).trim();
			}
		}
		if(util.isProd() && (datasheet.trim().equalsIgnoreCase("PROD_SANITY") || datasheet.trim().equalsIgnoreCase("PRE_DEPLOYMENT")) && emailaddress == null && password == null) {
			emailaddress = "accountmanagersupport@ticketmaster.com";
			password = "x9876";
		}
		if(emailaddress == null || password == null)
			return null;
		else
			return new String[]{emailaddress, password};
	}
	
	String[][] getDeviceConfiguration() {
		String deviceConf = System.getProperty("deviceConf") != null && !System.getProperty("deviceConf").trim().equalsIgnoreCase("") ? System.getProperty("deviceConf").trim() : "";
		boolean runLocally = System.getProperty("runLocally") != null && !System.getProperty("runLocally").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("runLocally").trim().toLowerCase()) : Boolean.valueOf(Environment.get("runLocally").trim().toLowerCase());
		HashMapNew temp;
		
		if(deviceConf.trim().equalsIgnoreCase(""))
			temp = GetXMLNodeValue(OSValidator.delimiter + "src" + OSValidator.delimiter + "Configuration.xml", "//selenium", 0);
		else
			temp = GetXMLNodeValueFromString(deviceConf, "//selenium", 0);
		
		List<List<String>> conf = new ArrayList<List<String>>();
		Set<String> keys = temp.keySet();
		Iterator<String> iter = keys.iterator();
		while(iter.hasNext()){
			String key = iter.next();
			if(!key.trim().equalsIgnoreCase("common") && !key.trim().equalsIgnoreCase("#text")) {
				HashMapNew data;
				if(deviceConf.trim().equalsIgnoreCase(""))
					data = GetXMLNodeValue(OSValidator.delimiter + "src" + OSValidator.delimiter + "Configuration.xml", "//" + key, 0);
				else
					data = GetXMLNodeValueFromString(deviceConf, "//" + key, 0);
				
				List<String> device = new ArrayList<String>();
				if(key.trim().toLowerCase().contains("android") || key.trim().toLowerCase().contains("ios")){
					String platformVersion = data.get("platformVersion").trim();
					String deviceName = data.get("deviceName").trim();
					String deviceOrientation = data.get("deviceOrientation").trim();
					String deviceType = data.get("deviceType").trim();
					if(runLocally){
						device.add(toCamelCase(key.substring(0, key.length() - 1)));
						device.add(key.trim().toUpperCase());
					} else {
						device.add(toCamelCase(key.substring(0, key.length() - 1)) + "-" + deviceName + "-" + platformVersion + "-" + toCamelCase(deviceType) + "-" + toCamelCase(deviceOrientation));
						device.add(key.trim().toUpperCase());
					}
				} else {
					String browserVersion = data.get("browserVersion").trim();
					String platformName = data.get("platformName").trim();
					if(runLocally){
						device.add(toCamelCase(key.substring(0, key.length() - 1)));
						device.add(key.trim().toUpperCase());
					} else {
						device.add(toCamelCase(key.substring(0, key.length() - 1)) + "-" + platformName + "-" + browserVersion);
						device.add(key.trim().toUpperCase());
					}
				}
				
				conf.add(device);
			}
		}
		
		String[][] array = new String[conf.size()][];

		int i = 0;
		for (List<String> nestedList : conf) {
		    array[i++] = nestedList.toArray(new String[nestedList.size()]);
		}
		
		return array;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws JSONException, Exception{    
		Execute exec = new Execute();
		
		String relatedEnv = null;
		String envConfig = System.getProperty("envConfig") != null && !System.getProperty("envConfig").trim().equalsIgnoreCase("") ? System.getProperty("envConfig").trim() : "";
		if(!envConfig.trim().equalsIgnoreCase("")) {
	 		relatedEnv = new Util(exec.Environment).getRelatedEnv();
			if(relatedEnv != null && !relatedEnv.trim().equalsIgnoreCase("")) {
				exec.Environment.put("env", relatedEnv.trim().toUpperCase());
			}
		}
		
		String User = System.getProperty("BUILD_USER_ID") != null && !System.getProperty("BUILD_USER_ID").trim().equalsIgnoreCase("") ? System.getProperty("BUILD_USER_ID").trim() : System.getProperty("user.name");
		String RootPath = System.getProperty("user.dir");
		String Datasheet = System.getProperty("calendar") != null && !System.getProperty("calendar").trim().equalsIgnoreCase("") ? System.getProperty("calendar").trim() : exec.Environment.get("calendar").trim();
    	String ExecutionFolderPath = RootPath + OSValidator.delimiter + "Execution";
    	
    	HashMapNew temp;
	    String environment = "";
	    if(!envConfig.trim().equalsIgnoreCase("")) {
	    	temp = exec.GetXMLNodeValueFromString(envConfig, "//CONFIG", 0);
	    	if(!temp.get("APP_URL").trim().equalsIgnoreCase("")) {
	    		String appurl = temp.get("APP_URL").trim();
	    		if(appurl.trim().endsWith("/"))
	    			appurl = appurl.trim().substring(0, appurl.trim().length() - 1);
	    		String clientId = appurl.substring(appurl.lastIndexOf("/") + 1).trim().toUpperCase();
	    		environment = clientId;
	    	}
	    } else {
	    	environment = exec.Environment.get("env").trim().toUpperCase(); 
	    	
	    }	  
	    
	    String CurrentExecutionFolder = ExecutionFolderPath + OSValidator.delimiter + Datasheet + OSValidator.delimiter + environment + OSValidator.delimiter + User;
    	String CurrentExecutionDatasheet = CurrentExecutionFolder + OSValidator.delimiter + Datasheet + ".xls";
    	String DatasheetsPath = RootPath + exec.Environment.get("dataSheets").trim();
    	
    	exec.Environment.put("CURRENTEXECUTIONFOLDER", CurrentExecutionFolder);
    	exec.Environment.put("CURRENTEXECUTIONDATASHEET", CurrentExecutionDatasheet);
    	
    	new File(CurrentExecutionFolder).mkdirs();
    	
    	Boolean replaceCalendarFile = System.getProperty("replaceCalendarFile") != null && !System.getProperty("replaceCalendarFile").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("replaceCalendarFile").trim()) : Boolean.valueOf(exec.Environment.get("replaceCalendarFile").trim());
    	
    	if (!new File(CurrentExecutionDatasheet).exists() || (replaceCalendarFile)) {
	        exec.gblFunctions.fCopyXLS(DatasheetsPath + Datasheet + ".xls", CurrentExecutionDatasheet);
	    }
    	
    	boolean runLocally = System.getProperty("runLocally") != null && !System.getProperty("runLocally").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("runLocally").trim().toLowerCase()) : Boolean.valueOf(exec.Environment.get("runLocally").trim().toLowerCase());
    	String groups = System.getProperty("groups") != null && !System.getProperty("groups").trim().equalsIgnoreCase("") ? System.getProperty("groups").trim() : exec.Environment.get("groups").trim();
    	String[] negGroups = null, posGroups = null;
    	if(!groups.trim().equalsIgnoreCase("")) {
    		String[] actualGroups = groups.trim().split(",");
	    	List<String> lnegGroups = new ArrayList<String>();
	    	List<String> lposGroups = new ArrayList<String>();
			for(int i = 0; i < actualGroups.length; i++) {
				if(actualGroups[i].trim().startsWith("!")) {
					String group = actualGroups[i].trim().substring(1);
					lnegGroups.add(group.trim());
				} else {
					lposGroups.add(actualGroups[i].trim());
				}
			}
			negGroups = lnegGroups.toArray(new String[lnegGroups.size()]);
			posGroups = lposGroups.toArray(new String[lposGroups.size()]);
    	}
    	
    	String createAccount = System.getProperty("createAccount") != null && !System.getProperty("createAccount").trim().equalsIgnoreCase("") ? System.getProperty("createAccount").trim() : exec.Environment.get("createAccount").trim();
    	boolean buyEvents = System.getProperty("buyEvents") != null && !System.getProperty("buyEvents").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("buyEvents").trim()) : Boolean.valueOf(exec.Environment.get("buyEvents").trim());
	    
	    String number_of_accounts = System.getProperty("number_of_accounts") != null && !System.getProperty("number_of_accounts").trim().equalsIgnoreCase("") ? System.getProperty("number_of_accounts").trim() : exec.Environment.get("number_of_accounts").trim();
	    if(!number_of_accounts.trim().equalsIgnoreCase("")) {
	    	CreateAccounts create = new CreateAccounts(exec.Environment);
		    create.create(null, createAccount.trim().equalsIgnoreCase("createonly"), Datasheet, buyEvents, createAccount.trim().equalsIgnoreCase("addEventsOnlyForNewAccounts"));
	    	return;
	    }
	    
	    String parallel = System.getProperty("parallel") != null && !System.getProperty("parallel").trim().equalsIgnoreCase("") ? System.getProperty("parallel").trim() : "";
	    
	    String _driverType = "CHROME1";
    	WebDriverFactory _driverFactory = new WebDriverFactory();
    	_driverFactory.setDriverType(new ThreadLocal<String>(){@Override public String initialValue() {
			return _driverType;
		};});
    	exec.RecordSetMap = retreiveDataFromExcel(exec, _driverFactory, CurrentExecutionDatasheet);
    	List<String> datasheets = new ArrayList<>(Arrays.asList(Datasheet));
	    if(exec.RecordSetMap != null) {
	    	int iRSCount = 0;
	    	do {
	            RecordSet res1 = (RecordSet)exec.RecordSetMap.get(Integer.valueOf(iRSCount + 1));
	            String sTestName = res1.get_sTestName();
	            String sActionName = res1.get_sActionName();
	            if(sTestName.trim().equalsIgnoreCase("datasheet")) {
	            	datasheets.add(sActionName.trim().toUpperCase());
	            }
			    iRSCount++;
	    	} while(iRSCount < exec.RecordSetMap.size());
	    }
	    
	    Util util= new Util(exec.Environment);
	    List<String> suiteFiles = new ArrayList<>();
	    
	    for(int j = 0; j < datasheets.size(); j++) {
	    	Datasheet = datasheets.get(j);
	    	CurrentExecutionFolder = ExecutionFolderPath + OSValidator.delimiter + Datasheet + OSValidator.delimiter + environment + OSValidator.delimiter + User;
	    	CurrentExecutionDatasheet = CurrentExecutionFolder + OSValidator.delimiter + Datasheet + ".xls";
	    	DatasheetsPath = RootPath + exec.Environment.get("dataSheets").trim();
	    	
	    	exec.Environment.put("CURRENTEXECUTIONFOLDER", CurrentExecutionFolder);
	    	exec.Environment.put("CURRENTEXECUTIONDATASHEET", CurrentExecutionDatasheet);
	    	new File(CurrentExecutionFolder).mkdirs();
	    	if (!new File(CurrentExecutionDatasheet).exists() || (replaceCalendarFile)) {
		        exec.gblFunctions.fCopyXLS(DatasheetsPath + Datasheet + ".xls", CurrentExecutionDatasheet);
		    }
	    	XmlSuite suite = new XmlSuite();
		    suite.setName(environment.trim().toUpperCase() + "_" + Datasheet.trim().toUpperCase());
		    
		    if(!parallel.trim().equalsIgnoreCase("")) {
		    	suite.setParallel(parallel);
		    } else {
			    if(runLocally || (util.isProd() && (Datasheet.trim().equalsIgnoreCase("PROD_SANITY") || Datasheet.trim().equalsIgnoreCase("PRE_DEPLOYMENT"))))
			    	suite.setParallel("classes");
			    else
			    	suite.setParallel("tests");
		    }
		    
		    //Create configuration array
		    String[][] configuration = exec.getDeviceConfiguration();
		    HashMap<String, String[]> appCredentials = new HashMap<String, String[]>();
		    
		    List<String> testNames = new ArrayList<String>();
		    int counter = 0;
		    for(int i = 0; i < configuration.length; i++) {
		    	String testName = configuration[i][0];
		    	String driverType = configuration[i][1];
		    	WebDriverFactory driverFactory = new WebDriverFactory();
		    	driverFactory.setDriverType(new ThreadLocal<String>(){@Override public String initialValue() {
					return driverType;
				};});
		    	clearX(exec, driverFactory);
		    	exec.RecordSetMap = retreiveDataFromExcel(exec, driverFactory, CurrentExecutionDatasheet);
		    	if(exec.RecordSetMap == null)
		    		continue;
		    	
		    	if((!runLocally && !(util.isProd() && (Datasheet.trim().equalsIgnoreCase("PROD_SANITY") || Datasheet.trim().equalsIgnoreCase("PRE_DEPLOYMENT")))) || parallel.trim().equalsIgnoreCase("tests"))
		    		counter++;
		    	
		    	XmlTest test = new XmlTest(suite);
		    	test.setName(testName);
		    	testNames.add(testName);
		    	if(!createAccount.trim().equalsIgnoreCase("") && j == 0) {
			    	String[] credentials = exec.getAppCredentials(exec.Environment.get("env"), exec.Environment.get("version"), driverType, Datasheet);
		    		appCredentials.put(driverType.trim().toUpperCase(), credentials);
		    	}
		    	
		    	if((!runLocally && !(util.isProd() && (Datasheet.trim().equalsIgnoreCase("PROD_SANITY") || Datasheet.trim().equalsIgnoreCase("PRE_DEPLOYMENT")))) || parallel.trim().equalsIgnoreCase("tests"))
		    		test.setGroupByInstances(true);
		    	Map<String, String> parameters = new HashMap<String, String>(); 
			    parameters.put("browser", driverType);
			    parameters.put("calendar", Datasheet);
			    test.setParameters(parameters);
			    
			    HashMapNew methodsList = new HashMapNew();
		    	
		    	int iRSCount = 0;
		    	do {
		            RecordSet res1 = (RecordSet)exec.RecordSetMap.get(Integer.valueOf(iRSCount + 1));
		            String sActionName = res1.get_sActionName();
		            String className = getClassName(exec, sActionName, posGroups, negGroups);
		            if(className != null && !className.trim().equalsIgnoreCase("")){
		            	methodsList.put(className, methodsList.get(className) + sActionName + ",");
		            }
				    iRSCount++;
		    	} while(iRSCount < exec.RecordSetMap.size());
		    	
		    	Set<String> keys = methodsList.keySet();
		    	Iterator<String> iterator = keys.iterator();
		    	
		    	List<XmlClass> allclasses = new ArrayList<XmlClass>(); 
		    	
		    	while(iterator.hasNext()){
		    		String key = iterator.next();
		    		XmlClass testClass = new XmlClass();
				    testClass.setName(key);
				    Object[] obj = getMethodsCountInClass(exec, key);
				    int methodscount = (int)obj[1];
				    String[] mtdList = methodsList.get(key).split(",");
				    if(methodscount == mtdList.length){
				    	//Do Nothing
				    }
				    else {
				    	Set<Method> allmethods = (Set<Method>) obj[0];
				    	Object[] _obj = constructIncludes(allmethods, mtdList);
				    	List<XmlInclude> methodsToRun = (List<XmlInclude>) _obj[0];
				    	allmethods = (Set<Method>) _obj[1];
				    	testClass.setIncludedMethods(methodsToRun);
				    	testClass.setExcludedMethods(constructExcludes(allmethods));
				    }
				    allclasses.add(testClass);
		    	}
		    	if((runLocally || (util.isProd() && (Datasheet.trim().equalsIgnoreCase("PROD_SANITY") || Datasheet.trim().equalsIgnoreCase("PRE_DEPLOYMENT")))) || parallel.trim().equalsIgnoreCase("classes"))
		    		counter = counter < allclasses.size() ? allclasses.size() : counter;
		    	test.setXmlClasses(Arrays.asList(allclasses.toArray(new XmlClass[allclasses.size()])));
		    }
		  
		    if(j == 0) {
			    if(appCredentials.size() > 0) {
				    CreateAccounts create = new CreateAccounts(exec.Environment);
				    appCredentials = create.create(appCredentials, createAccount.trim().equalsIgnoreCase("createonly"), Datasheet, buyEvents, createAccount.trim().equalsIgnoreCase("addEventsOnlyForNewAccounts"));
				    boolean saveToAppCredentials = System.getProperty("saveToAppCredentials") != null && !System.getProperty("saveToAppCredentials").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("saveToAppCredentials").trim()) : Boolean.valueOf(exec.Environment.get("saveToAppCredentials").trim());
				    if(saveToAppCredentials) {
				    	String relatedEnvDataPath = exec.Environment.get("appCredentialsPath").trim();
				    	relatedEnvDataPath = relatedEnvDataPath.substring(0, relatedEnvDataPath.lastIndexOf("_")) + "_" + exec.Environment.get("env").trim().toUpperCase() + ".xml";
				    	System.out.println("ENV_PATH :: " + relatedEnvDataPath);
				    	saveAppCredentialsXmlFile(relatedEnvDataPath, exec.Environment.get("appCredentialsPath").trim(), appCredentials, exec.Environment.get("env"), exec.Environment.get("version"));
				    }
			    } else {
			    	String relatedEnvDataPath = exec.Environment.get("appCredentialsPath").trim();
			    	relatedEnvDataPath = relatedEnvDataPath.substring(0, relatedEnvDataPath.lastIndexOf("_")) + "_" + exec.Environment.get("env").trim().toUpperCase() + ".xml";
			    	System.out.println("ENV_PATH :: " + relatedEnvDataPath);

			    	saveAppCredentialsXmlFile(relatedEnvDataPath, exec.Environment.get("appCredentialsPath").trim(), appCredentials, exec.Environment.get("env"), exec.Environment.get("version"));
			    }
		    }
		    
		    if(runLocally || (util.isProd() && (Datasheet.trim().equalsIgnoreCase("PROD_SANITY") || Datasheet.trim().equalsIgnoreCase("PRE_DEPLOYMENT")))) {
		    	if(testNames.size() == 1 && (testNames.get(0).trim().toLowerCase().contains("ios") || testNames.get(0).trim().toLowerCase().contains("android"))) {
		    		if(!exec.Environment.get("sauceLabsConcurrentSession").trim().equalsIgnoreCase("")) {
				    	int sauceLabConcurrentSession = Integer.valueOf(exec.Environment.get("sauceLabsConcurrentSession").trim());
				    	if(counter < sauceLabConcurrentSession) {
				    		suite.setThreadCount(counter);
				    	} else {
				    		suite.setThreadCount(sauceLabConcurrentSession);
				    	}
			    	} else {
			    		suite.setThreadCount(counter);
			    	}
		    	} else {
		    		if(!exec.Environment.get("localConcurrentSession").trim().equalsIgnoreCase("")) {
		    			int localConcurrentSession = Integer.valueOf(exec.Environment.get("localConcurrentSession").trim());
				    	if(counter < localConcurrentSession){
				    		suite.setThreadCount(counter);
				    	} else {
				    		suite.setThreadCount(localConcurrentSession);
				    	}
		    		} else {
		    			suite.setThreadCount(counter);
		    		}
		    	}
		    } else {
		    	if(!exec.Environment.get("sauceLabsConcurrentSession").trim().equalsIgnoreCase("")) {
			    	int sauceLabConcurrentSession = Integer.valueOf(exec.Environment.get("sauceLabsConcurrentSession").trim());
			    	if(counter < sauceLabConcurrentSession){
			    		suite.setThreadCount(counter);
			    	} else {
			    		suite.setThreadCount(sauceLabConcurrentSession);
			    	}
		    	} else {
		    		suite.setThreadCount(counter);
		    	}
		    }
		    
		    suite.addListener("org.iomedia.galen.common.DataProviderAnnotationTransformerListener");
	        System.out.println(suite.toXml());
	        
	        List<XmlSuite> suites = new ArrayList<XmlSuite>();
		    suites.add(suite);
		    
		    String testngFileName = environment.trim().toUpperCase() + "-" + Datasheet.trim().toUpperCase() + "-testng-customsuite.xml";
		    
		    FileOutputStream fout = new FileOutputStream(RootPath + OSValidator.delimiter + "testng" + OSValidator.delimiter + testngFileName, false);
	    	new PrintStream(fout).println(suite.toXml());
	    	fout.close();
	    	
	    	suiteFiles.add("testng" + OSValidator.delimiter + testngFileName);
	    }
	    
	    //Set unlimited sessions for AMGR
	    boolean setSession = System.getProperty("setSession") != null && !System.getProperty("setSession").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("setSession").trim()) : Boolean.valueOf(exec.Environment.get("setSession").trim());
	    if(setSession || (util.isProd() && Datasheet.trim().equalsIgnoreCase("PRE_DEPLOYMENT"))) {
		    String sessions = System.getProperty("sessions") != null && !System.getProperty("sessions").trim().equalsIgnoreCase("") ? System.getProperty("sessions").trim() : (util.isProd() && (Datasheet.trim().equalsIgnoreCase("PROD_SANITY") || Datasheet.trim().equalsIgnoreCase("PRE_DEPLOYMENT"))) ? "1" : "0";
		    WebDriverFactory driverFactory = new WebDriverFactory();
		    driverFactory.setDriverType(new ThreadLocal<String>(){@Override public String initialValue() {
				return "CHROME1";
			};});
		    driverFactory.setTestDetails(new ThreadLocal<HashMapNew>(){@Override public HashMapNew initialValue() {
				return new HashMapNew();
			};});
		    Sessions ses= new Sessions(driverFactory, exec.Environment, Datasheet);
		    ses.setSessionLimit(sessions, "", true, true, false);
	    }
	    
	    XmlSuite suite = new XmlSuite();
	    suite.setName(environment.trim().toUpperCase());
	    suite.setSuiteFiles(suiteFiles);
	    System.out.println(suite.toXml());
	    List<XmlSuite> suites = new ArrayList<XmlSuite>();
	    suites.add(suite);
	    
	    boolean writeToFileOnly = System.getProperty("writeToFile") != null && !System.getProperty("writeToFile").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("writeToFile").trim()) : false;
	    boolean generateNewTestNgFile = System.getProperty("generateNewTestNgFile") != null && !System.getProperty("generateNewTestNgFile").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("generateNewTestNgFile").trim()) : Boolean.valueOf(exec.Environment.get("generateNewTestNgFile").trim());
	    
	    String testngFileName;
	    if(generateNewTestNgFile) {
	    	testngFileName = environment.trim().toUpperCase() + "-testng-customsuite.xml";
	    } else {
	    	testngFileName = "testng-customsuite.xml";
	    }
	    
	    FileOutputStream fout = new FileOutputStream(RootPath + OSValidator.delimiter + "testng" + OSValidator.delimiter + testngFileName, false);
    	new PrintStream(fout).println(suite.toXml());
    	fout.close();
    	
    	if(!writeToFileOnly){
		    TestNG tng = new TestNG();
		    tng.setXmlSuites(suites);
		    tng.run();
	    }
	}
	
	public static Object[] constructIncludes (Set<Method> allmethods, String... methodNames) {
        List<XmlInclude> includes = new ArrayList<XmlInclude> ();
        for (String eachMethod : methodNames) {
        	allmethods.removeIf(obj -> obj.getName().trim().equalsIgnoreCase(eachMethod.trim()));
            includes.add (new XmlInclude (eachMethod));
        }
        return new Object[] {includes, allmethods};
    }
	
	public static List<String> constructExcludes (Set<Method> excludedmethods) {
        List<String> excludes = new ArrayList<String> ();
        Iterator<Method> iter = excludedmethods.iterator();
        while(iter.hasNext()) {
        	Method mtd = iter.next();
        	excludes.add(mtd.getName().trim());
        }
        return excludes;
    }
	
	public static void clearX(Execute exec, WebDriverFactory driverFactory){
		Infra objInfra = new Infra(driverFactory, null, exec.Environment, null);
		String clearX = System.getProperty("clearX") != null && !System.getProperty("clearX").trim().equalsIgnoreCase("") ? System.getProperty("clearX").trim() : exec.Environment.get("clearX").trim();
	    if (!clearX.equals("")) {
	    	objInfra.fClearSkip(clearX);
	    }
	}
	
	public static HashMap<Integer, RecordSet> retreiveDataFromExcel(Execute exec, WebDriverFactory driverFactory, String CurrentExecutionDatasheet){
		DBActivities objDB = new DBActivities(driverFactory, null, exec.Environment);
    	ArrayList<String> columnNames = objDB.fGetColumnName(CurrentExecutionDatasheet, "MAIN");
    
	    int skipColumnNo = columnNames.indexOf("SKIP_" + driverFactory.getDriverType().get().toUpperCase().replace(" ", ""));
	    int headerColumnNo = columnNames.indexOf("HEADER");
	    int testNameColumnNo = columnNames.indexOf("TEST_NAME");
	    int actionNameColumnNo = columnNames.indexOf("ACTION");
	    
	    if(skipColumnNo == -1)
	    	return null;
    
	    List<List<String>> calendarFileData = null;
	    calendarFileData = objDB.fRetrieveDataExcel(CurrentExecutionDatasheet, "MAIN", new int[]{skipColumnNo, headerColumnNo}, new String[]{"", ""});
	    
	    if(calendarFileData == null) {
	    	return null;
	    }
    
	    if(calendarFileData.size() == 0) {
	    	return null;
	    }
    
	    exec.RecordSetMap = new HashMap<Integer, RecordSet>();
	    int iRSCount = 0;
	    do {
	    	exec.RecordSetMap.put(Integer.valueOf(iRSCount + 1), exec.new RecordSet(calendarFileData.get(iRSCount).get(actionNameColumnNo), Integer.valueOf(calendarFileData.get(iRSCount).get(0)) - 1, calendarFileData.get(iRSCount).get(testNameColumnNo), Integer.valueOf(calendarFileData.get(iRSCount).get(0)) + 1));
	    	iRSCount++;
	    } while (iRSCount < calendarFileData.size());
	    
	    return exec.RecordSetMap;
	}
	
	public static String getClassName(Execute exec, String methodName, String[] posGroups, String[] negGroups) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        String className = "";
        
        Reflections reflections = new Reflections(new ConfigurationBuilder() .setUrls(ClasspathHelper.forPackage(exec.Environment.get("agilePackage").trim())).setScanners(new MethodAnnotationsScanner()));
        Set<Method> resources = reflections.getMethodsAnnotatedWith(org.testng.annotations.Test.class);
        Iterator<Method> iter = resources.iterator();
        
        while(iter.hasNext()) {
        	Method method = iter.next();
        	if(method.getName().equalsIgnoreCase(methodName)) {
        		className = method.getDeclaringClass().getName();
        		if(negGroups == null && posGroups == null)
        			return className;
        		Test _test = (Test) method.getDeclaredAnnotations()[0];
        		String[] _groups = _test.groups();
        		String concatGroups = "";
        		for(int i = 0; i < _groups.length; i++) {
        			concatGroups += _groups[i].trim() + " ";
        		}
        		concatGroups = concatGroups.trim().toLowerCase();
        		if(negGroups != null && negGroups.length > 0) {
        			for(int i = 0; i < negGroups.length; i++) {
        				if(concatGroups.contains(negGroups[i].trim().toLowerCase())) {
        					return null;
        				}
        			}
        		}
        		if(posGroups != null && posGroups.length > 0) {
        			for(int i = 0; i < posGroups.length; i++) {
        				if(concatGroups.contains(posGroups[i].trim().toLowerCase())) {
        					return className;
        				}
        			}
        		} else
        			return className;
        	}
        }
        return null;
	}
	
	public static Object[] getMethodsCountInClass(Execute exec, String className) {
		Reflections reflections = new Reflections(new ConfigurationBuilder() .setUrls(ClasspathHelper.forPackage(exec.Environment.get("agilePackage").trim())).setScanners(new MethodAnnotationsScanner()).filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(className))));
        Set<Method> resources = reflections.getMethodsAnnotatedWith(org.testng.annotations.Test.class);
		return new Object[]{resources, resources.size()};
	}
	
	static String toCamelCase(String s){
 	   String[] parts = s.split(" ");
 	   String camelCaseString = "";
 	   for (String part : parts){
 	      camelCaseString = camelCaseString + toProperCase(part) + " ";
 	   }
 	   return camelCaseString.trim();
 	}
	    
	static String toProperCase(String s) {
	    return s.substring(0, 1).toUpperCase() +
	               s.substring(1).toLowerCase();
	}
	
	private static void saveAppCredentialsXmlFile(String relatedEnvDataPath, String path, HashMap<String, String[]> appCredentials, String env, String version) {
	    String RootPath = System.getProperty("user.dir");
	    try {
	      String envConfig = System.getProperty("envConfig") != null && !System.getProperty("envConfig").trim().equalsIgnoreCase("") ? System.getProperty("envConfig").trim() : "";
	      String xmlPath = RootPath + path;
	      if(!envConfig.trim().equalsIgnoreCase("")) {
	    	  xmlPath = RootPath + relatedEnvDataPath;
	      }
	      if(!new File(xmlPath).exists()) {
	    	  xmlPath = RootPath + relatedEnvDataPath;
	      }
	      
	      File fXmlFile = new File(xmlPath);
	      
	      if(!fXmlFile.exists()) {
	    	  fXmlFile.createNewFile();
	      }
	      
	      DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
	      DocumentBuilder docBuilder = dbFac.newDocumentBuilder();
	      Document xmldoc;
	      
	      try{
	    	  xmldoc = docBuilder.parse(fXmlFile);
	      } catch(Exception ex) {
	    	  xmldoc = docBuilder.newDocument();
	      }
	      
	      Node ENV = xmldoc.getElementsByTagName("ENV").item(0);
	      
	      if(ENV == null) {
	    	  Element _env = xmldoc.createElement("ENV");
	    	  ENV = xmldoc.appendChild(_env);
	      }
	      
	      XPathFactory xPathfac = XPathFactory.newInstance();
	      XPath xpath = xPathfac.newXPath();

	      //"//" + env.trim().toUpperCase() + "/" + version.trim().toUpperCase() + "/" + driverType.trim().toUpperCase()
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
	      
	      List<String> keys = new ArrayList<>(appCredentials.keySet());
	      for(int i = 0; i < keys.size(); i++) {
	    	  String driverType = keys.get(i);
	    	  String[] creds = appCredentials.get(driverType);
	    	  String emailAddress = creds[0];
	    	  String pass = creds[1];
	    	  expr = xpath.compile("//" + env.trim().toUpperCase() + "/" + version.trim().toUpperCase() + "/" + driverType.trim().toUpperCase());
		      obj = expr.evaluate(xmldoc, XPathConstants.NODESET);
		      if(obj != null) {
		    	  Node node = ((NodeList)obj).item(0);
		    	  if(node != null) {
		    		  NodeList nl = node.getChildNodes();
		    		  for (int child = 0; child < nl.getLength(); child++) {
		    			  String nodeName = nl.item(child).getNodeName().trim();
		    			  if(nodeName.trim().toUpperCase().contains("EMAIL_ADDRESS") && !nodeName.trim().toUpperCase().contains("SWITCH_ACC_EMAIL_ADDRESS") && !nodeName.trim().toUpperCase().contains("ASSOCIATED_ACC_EMAIL_ADDRESS")) {
		    				  nl.item(child).setTextContent(emailAddress);
		    			  } else if(nodeName.trim().toUpperCase().contains("PASSWORD") && !nodeName.trim().toUpperCase().contains("SWITCH_ACC_PASSWORD") && !nodeName.trim().toUpperCase().contains("ASSOCIATED_ACC_PASSWORD")) {
		    				  nl.item(child).setTextContent(pass);
		    			  }
		    		  }
		    	  } else {
		    		  expr = xpath.compile("//" + env.trim().toUpperCase() + "/" + version.trim().toUpperCase()); 
		    		  obj = expr.evaluate(xmldoc, XPathConstants.NODESET);
		    		  node = ((NodeList)obj).item(0);
		    		  
		    		  Element driverTypeNode = xmldoc.createElement(driverType.trim().toUpperCase());
		    		  String[] keyNodes = new String[]{"EMAIL_ADDRESS", "PASSWORD", "PLAN_EMAIL_ADDRESS", "PLAN_PASSWORD", "PAID_EMAIL_ADDRESS", "PAID_PASSWORD", "PARTIALLY_PAID_EMAIL_ADDRESS", "PARTIALLY_PAID_PASSWORD", "EXISTING_CARD_EMAIL_ADDRESS", "EXISTING_CARD_PASSWORD", "SWITCH_ACC_EMAIL_ADDRESS", "SWITCH_ACC_PASSWORD", "ASSOCIATED_ACC_EMAIL_ADDRESS", "ASSOCIATED_ACC_PASSWORD"};
		    		  for(int j = 0; j < keyNodes.length; j++) {
		    			  if(keyNodes[j].trim().toUpperCase().contains("EMAIL_ADDRESS")) {
		    				  Element emailAddressNode = xmldoc.createElement(keyNodes[j]);
		    				  emailAddressNode.appendChild(xmldoc.createTextNode(emailAddress));
		    				  driverTypeNode.appendChild(emailAddressNode);
		    			  } else if(keyNodes[j].trim().toUpperCase().contains("PASSWORD")) {
		    				  Element passwordNode = xmldoc.createElement(keyNodes[j]);
		    				  passwordNode.appendChild(xmldoc.createTextNode(pass));
		    				  driverTypeNode.appendChild(passwordNode);
		    			  }
		    		  }
			    	  node.appendChild(driverTypeNode);
		    	  }
		      }
	      }
	      
	      //write the content into xml file
	      TransformerFactory transformerFactory = TransformerFactory.newInstance();
	      Transformer transformer = transformerFactory.newTransformer();
	      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	      
	      DOMSource source = new DOMSource(xmldoc);
	      String resultXmlPath = RootPath + path;
	      System.out.println("App credentials path : " + resultXmlPath);
	      StreamResult result = new StreamResult(new FileOutputStream(resultXmlPath));
	      transformer.transform(source, result);
	    } catch (Exception excep) {
	    	excep.printStackTrace();
	    }
	}
	
	
	
	public HashMapNew GetXMLNodeValueFromString(String str, String parentNode, int index){
		str = CharMatcher.is('\'').trimFrom(str);
		if(!str.trim().toUpperCase().contains("<CONFIG>") && !str.trim().toLowerCase().contains("<selenium>")) {
			str = EncryptDecrypt.getEnvConfig(str.trim());
			System.setProperty("envConfig", str);
		}
 		HashMapNew dict = new HashMapNew();
 	    try
 	    {
 	      DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
 	      DocumentBuilder docBuilder = dbFac.newDocumentBuilder();
 	      InputStream stream = new ByteArrayInputStream(str.getBytes("UTF-8"));
 	      Document xmldoc = docBuilder.parse(stream);
 	      
 	      XPathFactory xPathfac = XPathFactory.newInstance();
 	      XPath xpath = xPathfac.newXPath();

 	      XPathExpression expr = xpath.compile(parentNode);
 	      Object obj = expr.evaluate(xmldoc, XPathConstants.NODESET);
 	      if(obj != null){
 	    	  Node node = ((NodeList)obj).item(index);
 	    	  if(node != null){
 			      NodeList nl = node.getChildNodes();
 			      for (int child = 0; child < nl.getLength(); child++) {
 			    	  dict.put(nl.item(child).getNodeName().trim(), nl.item(child).getTextContent().trim());
 			      }
 	    	  }
 	      }
 	    }
 	    catch (Exception excep){
 	    	excep.printStackTrace();
 	    }
 	    
 	    return dict;
 	}
}

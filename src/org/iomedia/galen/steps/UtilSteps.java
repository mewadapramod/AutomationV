package org.iomedia.galen.steps;

import java.io.IOException;
import java.net.MalformedURLException;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.OSValidator;
import org.iomedia.galen.common.Screenshot;
import org.iomedia.galen.common.Utils;
import org.testng.SkipException;

import cucumber.api.java.en.Given;

public class UtilSteps {
	BaseUtil base;
	Screenshot shot;
	Utils utils;
	
	public UtilSteps(BaseUtil base,Screenshot shot,Utils utils) {
		this.base = base;
		this.shot = shot;
		this.utils = utils;
	}
	
	@Given("^Save (.+) into (.+)$")
	public void save_into(String value, String key) {
		value = (String) base.getGDValue(value);
		base.Dictionary.put(key, value);	
	}
	
	@Given("^Get path of Latest POFile$")
	public void save_latest_pofile() throws Exception {
		if(base.Environment.get("POFile_URL").trim().equalsIgnoreCase(""))
			throw new SkipException("PO File URL not found");
		base.getDriver().navigate().to(base.Environment.get("POFile_URL").trim());	
		shot.createPOFileFolder();
		String Filepath = shot.getPOFilepath(true);
		base.Dictionary.put("POFilePath", Filepath);
	}
	
	@Given("^Place latest POFile into (.+)$")
	public void save_latest_pofile_into(String Filepath) throws MalformedURLException, IOException {
		if(base.Environment.get("POFile_URL").trim().equalsIgnoreCase(""))
			throw new SkipException("PO File URL not found");
		Filepath = (String) base.getGDValue(Filepath);
		String URL = base.Environment.get("POFile_URL").trim();
		base.Dictionary.put("Absolute_POFilePath", Filepath+ OSValidator.delimiter + "fr-ca.po");
		utils.saveFileFromUrl(Filepath+ OSValidator.delimiter + "fr-ca.po", URL);		
	}
	
	@Given("^User credentials passed from Jenkins$")
	public void credentials_pass_from_jenkins() {
	utils.credentials_jenkins();	
	}
	
	@Given("^Place JSON File in English and other language into (.+)$")
	public void save_JSON_File_into(String Filepath) throws Exception, IOException {
		if(base.Environment.get("FrenchJSONURL").trim().equalsIgnoreCase("") || base.Environment.get("EnglishJSONURL").trim().equalsIgnoreCase("") )
			throw new SkipException("JSON URL not found");
		Filepath = (String) base.getGDValue(Filepath);
		
		String FrenchURL = base.Environment.get("FrenchJSONURL").trim();
		base.Dictionary.put("FrenchJSONFilePath", Filepath+ OSValidator.delimiter + "fr-ca.json");
		utils.saveFileFromUrl(Filepath+ OSValidator.delimiter + "fr-ca.json", FrenchURL);	
		
		String EnglishURL = base.Environment.get("EnglishJSONURL").trim();
		base.Dictionary.put("EnglishJSONFilePath", Filepath+ OSValidator.delimiter + "en.json");
		utils.saveFileFromUrl(Filepath+ OSValidator.delimiter + "en.json", EnglishURL);	
	}
}

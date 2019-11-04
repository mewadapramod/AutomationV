package org.iomedia.galen.pages;

import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.iomedia.galen.common.VirtualVenueAPI;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import java.util.HashMap;
import java.util.List;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Assert;

public class VVOverviewPage extends BaseUtil {

	VirtualVenueAPI vAPI;

	public VVOverviewPage(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment,
			Reporting Reporter, Assert Assert, org.iomedia.framework.SoftAssert SoftAssert,
			ThreadLocal<HashMapNew> sTestDetails, VirtualVenueAPI vAPI) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		this.vAPI = vAPI;
	}

	private By selectSeat = By.xpath("//button[contains(@class,'eventSelectionButton')]");
	private By NextButton = By.xpath("//button[contains(@class,'gotoNextButton')]");
	private By selectPlan = By.xpath("//button[contains(@class,'eventSelectionButton')]");
	private By planNextButton = By.xpath("//button[contains(@class,'nextButton_step2')]");
	private By clickCompare = By.xpath("//*[@id='topHeaderContainer']//span[text()='Compare Seats']");
	private By sourceSeatPrice = By.xpath("(//*[starts-with(@class,'style-eventPricing')]//span)[1]");
	private By noOfSeat = By.xpath("(//*[starts-with(@class,'style-eventPricing')])[1]");
	private By totalPrice = By.xpath("(//*[starts-with(@class,'style-eventPricing')]//span)[1]");
	private By totalPurchasePrice = By.xpath("(//*[starts-with(@class,'style-eventCost')]//span)[1]");
	private By totalDonation = By.xpath("(//*[starts-with(@class,'style-totalDonation')]//span)[1]");
	private By rowSeatNumber = By.xpath("(//*[starts-with(@class,'style-blockLabel')]//p[1])[1]");
	private By rightrail = By.id("right-rail-list");
	private By sectionName = By.xpath("//*[@id='right-rail-list']//div[starts-with(@id,'Section')]");
	private By availability = By.xpath(
			"//*[@id='right-rail-list']//div[starts-with(@id,'Section')]//span[starts-with(@class,'common-ticketText')]");
	private By listShowButton = By.xpath("//*[starts-with(@class,'overview-listShowButton')]");

	public void sourceSeatData() {
		String srcSeatPrice = getText(sourceSeatPrice);
		Dictionary.put("sourceSeatPrice", srcSeatPrice.replaceAll("[$,]", "").split("\\.")[0]);

		if (checkIfElementPresent(totalPrice)) {
			String tPrice = getText(totalPrice);
			Dictionary.put("totalPrice", tPrice.replaceAll("[$,]", "").split("\\.")[0]);
		}

		if (checkIfElementPresent(totalDonation)) {
			String tDonation = getText(totalDonation);
			Dictionary.put("totalDonation", tDonation.replaceAll("[$,]", "").split("\\.")[0]);
		}

		String numOfSeat = getText(noOfSeat);
		Dictionary.put("numOfSeat", numOfSeat.split("x")[1].split(" ")[1]);
		String rsNumber = getText(rowSeatNumber);
		Dictionary.put("SectionSeatInfo", rsNumber.replaceAll("\\s", " "));
		String purchasePrice = getText(totalPurchasePrice);
		Dictionary.put("purchasePrice", purchasePrice.replaceAll("[$,]", "").split("\\.")[0]);
	}

	public void clickSelectSeat() {
		click(selectSeat, "Select Seats");
	}

	public void clickNextButton() {
		click(NextButton, "Next");
	}

	public void clickSelectPlan() {
		click(selectPlan, "Select Plan");
		sync(100L);
	}

	public void clickPlanNextButton() {
		click(planNextButton, "Plan Next Button");
		sync(10000L);
	}

	public String javaScriptExecutor(String str) {
		String value = null;
		try {
			if (str.contains("total_events")) {
				value = (String) ((JavascriptExecutor) this.getDriver()).executeScript(str).toString();
			} else {
				value = (String) ((JavascriptExecutor) this.getDriver()).executeScript(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public void readWindowObjectData() {
		String apiUrl = javaScriptExecutor("return window.virtualvenue.apiUrl");
		String apiVersion = javaScriptExecutor("return window.virtualvenue.apiVersion");
		Dictionary.put("apiVersion", apiVersion);
		String applicationType = javaScriptExecutor("return window.virtualvenue.applicationType");
		Dictionary.put("applicationType", applicationType);
		String dataToken = javaScriptExecutor("return window.virtualvenue.dataToken");
		String language = javaScriptExecutor("return window.virtualvenue.language");
		String namApiUrl = javaScriptExecutor("return window.virtualvenue.namApiUrl");
		String venueId = javaScriptExecutor("return window.virtualvenue.venueId");
		String xTransId = javaScriptExecutor("return window.virtualvenue.xTransId");
		if (Dictionary.get("applicationType").equalsIgnoreCase("Buy")) {
			String targetEvent = javaScriptExecutor("return window.virtualvenue.eligibleEvent.event_name");
			Dictionary.put("targetEvent", targetEvent);
			String event_name_inet = javaScriptExecutor("return window.virtualvenue.eligibleEvent.event_name_inet");
			Dictionary.put("event_name_inet", event_name_inet);
			String total_events = javaScriptExecutor("return window.virtualvenue.eligibleEvent.total_events");
			Dictionary.put("total_events", total_events);
			String region = javaScriptExecutor("return window.virtualvenue.eligibleEvent.region");
			Dictionary.put("region", region);
		}
		Dictionary.put("apiUrl", apiUrl);
		Dictionary.put("dataToken", dataToken);
		Dictionary.put("language", language);
		Dictionary.put("namApiUrl", namApiUrl);
		Dictionary.put("venueId", venueId);
		Dictionary.put("xTransId", xTransId);
	}

	public void clickCompareButton() {
		sync(10000L);
		click(clickCompare, "Select Compare");
	}

	public void clickShowListButton() {
		sync(10000L);
		click(listShowButton, "Click show list button");
		sync(10000L);
	}

	public HashMap<String, String> getVenueAvailability() {
		HashMap<String, String> sn = null;
		Boolean isScrollable = true;
		int i = 1000;
		int scrollHeight = 0;
		while (isScrollable) {
			WebElement container = this.getDriver().findElement(rightrail);
			int scrollHeight1 = Integer.parseInt(container.getAttribute("scrollHeight"));
			if (scrollHeight == scrollHeight1) {
				break;
			}
			javaScriptExecutor("document.getElementById('right-rail-list').scrollTop= \"" + i + "\"");
			sn = getTextOfAllElements(sectionName, availability);
			isScrollable = scrollHeight != scrollHeight1;
			i = i + 1000;
			scrollHeight = scrollHeight1;
		}
		return sn;
	}

	public HashMap<String, String> getTextOfAllElements(By keylocator, By valuelocator) {
		HashMap<String, String> webElementsTextMap = new HashMap<String, String>();
		List<WebElement> webElementsList = getWebElementsList(keylocator);
		List<WebElement> webElementsList2 = getWebElementsList(valuelocator);
		if (webElementsList != null && webElementsList2 != null) {
			for (int i = 0; i < webElementsList.size(); i++) {
				webElementsTextMap.put(webElementsList.get(i).getAttribute("id").split("_")[1],
						webElementsList2.get(i).getText());
			}
		}
		return webElementsTextMap;
	}

	public boolean verifyVenueAvailabilty(HashMap<String, List<String>> apiMapData, HashMap<String, String> iuMapData) {
		try {
			for (HashMap.Entry<String, List<String>> me : apiMapData.entrySet()) {
				String key = me.getKey();
				List<String> valueList = me.getValue();
				if (iuMapData.get(key).equals(me.getValue().get(0))) {
					if (!iuMapData.get(key).equals(valueList.get(0))) {
						Assert.fail("Api Node Data : " + iuMapData.get(key) + "  \n  " + "UI Node Data : "
								+ valueList.get(0));
						return false;
					}
				}
			}
		} catch (NullPointerException np) {
			np.getMessage();
			return false;
		}
		return true;
	}

}

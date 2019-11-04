package org.iomedia.galen.loadtest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Invoice {
	static String url = "https://amgr.io-media.com/iomediaqaunitas";
	static String emailaddress = "jawed.yusufi@io-media.com";
	static String pass = "123456";
	static String cardNumber = "4764493202530858";
	static String expiryDate = "0520";
	static String cvv = "858";
	
	static WebDriver driver;
	static By emailAddress = By.xpath(".//input[@name='email']");
	static By password = By.xpath(".//input[@name='password']");
	static By submitBtn = By.xpath(".//button[@type='submit']");
	static By userFullName = By.cssSelector(".user-fullname a");
	static By firstUnpaidInvoiceLink = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[not(descendant::span[contains(@class, 'invoice-paid')])]/..//a");
	static By invoiceDetail = By.cssSelector("div.accordionSummary");
	static By selectedInvoice = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li[contains(@class, 'invoice-activeItem')]" + "//ul/li[1]/span");
	static By amount_Due=By.xpath("((.//div[contains(@class, 'invoice-amountTableContainer')]//tbody//tr)[last()]//td)[2]/span");
	static By continueButton = By.cssSelector("button[class*='invoice-buttonBlock']");
	static By payInFull = By.xpath(".//input[@type='radio' and @value='payInFull']/..//span");
	static By continuePlan = By.xpath(".//*[contains(@class, 'accordionPayment')]//button[text()='CONTINUE']");
	static By addNewCard = By.xpath(".//button[contains(@class,'invoice-addCardButton')]");
	static By inputCardNumber= By.xpath(".//input[@name='newCardNumber']");
	static By inputCardExpiry= By.xpath(".//input[@name='newExpiryDate']");
	static By inputCardCvv= By.xpath(".//input[@name='newCvc']");
	static By firstName = By.xpath(".//*[contains(@class,'invoiceBilling')]//div[contains(@class, 'invoice-singleCardAddress') and @data-index='0']//input[@name='firstName']");
	static By lastName = By.xpath(".//*[contains(@class,'invoiceBilling')]//div[contains(@class, 'invoice-singleCardAddress') and @data-index='0']//input[@name='lastName']");
	static By streetAdd1 = By.xpath(".//*[contains(@class,'invoiceBilling')]//div[contains(@class, 'invoice-singleCardAddress') and @data-index='0']//input[@name='streetAddress']");
	static By streetAdd2 = By.xpath(".//*[contains(@class,'invoiceBilling')]//div[contains(@class, 'invoice-singleCardAddress') and @data-index='0']//input[@name='streetAddress2']");
	static By zipCode = By.xpath(".//*[contains(@class,'invoiceBilling')]//div[contains(@class, 'invoice-singleCardAddress') and @data-index='0']//input[@name='zipcode']");
	static By billingContinue= By.xpath(".//*[contains(@class, 'invoice-invoiceBilling')]//button[text()='CONTINUE']");
	static By city= By.xpath("//*[contains(@class,'invoiceBilling')]//div[contains(@class, 'invoice-singleCardAddress') and @data-index='0']//input[@name='city']");
	static By continueCard = By.xpath(".//*[contains(@class, 'accordionCard')]//button[text()='CONTINUE']");
	static By continueBilling = By.xpath("//*[contains(@class,'invoiceBilling')]//button[text()='CONTINUE']");
	static By partialAmt= By.xpath("//input[@name='userEnteredAmount']");
	static By tnCCheck = By.xpath("//*[contains(@class,'reviewTableCheckbox')]//label/div");
	static By confirmPay = By.xpath("//button[text()='CONFIRM']");
	static By paymentSuccessHead= By.xpath("//*[contains(@class,'reviewTitle')]");
	
	public static void main(String[] args) {
		try {
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt("26000"), TimeUnit.MILLISECONDS);
			load("/invoice");
			login(emailaddress, pass);
			clickFirstUnpaidInvoiceLink();
			isInvoiceDetailDisplayed();
			isContinueButtonDisplayed();
			clickContinueButton();
			clickpayInFull();
			clickContinuePlan();
			click(addNewCard);
			getElementWhenVisible(inputCardNumber).sendKeys(cardNumber);
			getElementWhenVisible(inputCardExpiry).sendKeys(expiryDate);
			getElementWhenVisible(inputCardCvv).sendKeys(cvv);
			
			clickContinueCard();
			typeBillingDetails("Test", "Test", "Address_1", "Address_2", "United States", "Alabama", "City", "12345");
			clickContinueBilling();
			
			WebElement partialAMt = getElementWhenClickable(partialAmt);
			partialAMt.clear();
			partialAMt.sendKeys("1.00");
			click(tnCCheck);
			click(confirmPay);
			
			String confirmation = getElementWhenVisible(paymentSuccessHead).getText();
			System.out.println(confirmation.trim().equalsIgnoreCase("Thank you for your payment!"));
		} finally {
			if(driver != null) {
				driver.quit();
			}
		}
	}
	
	static void load(String uri){
		driver.get(url + uri);
	}
	
	static void login(String emailaddress, String pass){
		WebElement email = getElementWhenVisible(emailAddress);
		email.clear();
		email.sendKeys(emailaddress);
		
		WebElement wpass = getElementWhenVisible(password);
		wpass.clear();
		wpass.sendKeys(pass);
		
		WebElement submit = getElementWhenVisible(submitBtn);
		submit.click();
		
		getElementWhenVisible(userFullName);
	}
	
	static WebElement getElementWhenVisible(By locater, long... waitSeconds){
		assert waitSeconds.length <= 1;
		long seconds = waitSeconds.length > 0 ? waitSeconds[0] : 26;
		WebElement element = null;
		
		int counter = 0;
		do{
			long time = 20;
			if(seconds <= 20)
				time = seconds;
			WebDriverWait wait  = new WebDriverWait(driver, time);
			try{
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(locater));
				break;
			}
			catch(Exception ex){
				boolean flag = false;
				if(flag && seconds <= 20){
					try{
						element = wait.until(ExpectedConditions.visibilityOfElementLocated(locater));
						break;
					} catch(Exception ex1){
						throw ex1;
					}
				}
				else{
					if(counter >= seconds || seconds <= 20)
						throw ex;
				}
			}
			finally{
				counter += time;
			}
		}while(true);
		
		return element;
	}
	
	static void clickFirstUnpaidInvoiceLink(){
		WebElement selectedInvoiceId = getElementWhenVisible(firstUnpaidInvoiceLink);
		int invoiceId = Integer.valueOf(selectedInvoiceId.getAttribute("href").split("\\#/")[1].replaceAll("/.*", ""));
		
		load("/invoice#/" + invoiceId + "/1");
		isInvoiceSelected(invoiceId);
		sync(5000L);
	}
	
	static boolean isInvoiceSelected(int invoiceId){
//		By invoice = By.xpath(".//div[@class='react-root-invoice']//ul[contains(@class, 'react-listing')]//li//a[contains(@href, '" + invoiceId + "')]/..");
//		getInvoiceWhenSelected(invoice, "class", "invoice-activeItem");
		return true;
	}
	
	static WebElement getInvoiceWhenSelected(final By locater, final String attribute, final String text, long... waitSeconds){
		assert waitSeconds.length <= 1;
		long seconds = waitSeconds.length > 0 ? waitSeconds[0] : 26;
		WebElement we = null;
		int counter = 0;
		do{
			long time = 20;
			if(seconds <= 20)
				time = seconds;
			WebDriverWait wait  = new WebDriverWait(driver, time);
			try{
				Boolean val = wait.until(ExpectedConditions.refreshed(new ExpectedCondition<Boolean>(){
					@Override
					public Boolean apply(WebDriver driver) {
						String value = driver.findElement(locater).getAttribute(attribute);
						if(attribute.trim().equalsIgnoreCase("disabled"))
							return value == null ? true : value.trim().contains(text);
						else
							return value == null ? false : value.trim().contains(text);
					}
					
				}));
				if(val){
					we = driver.findElement(locater);
					break;
				}
			}
			catch(Exception ex){
				boolean flag = false;
				if(flag && seconds <= 20){
					try{
						Boolean val = wait.until(ExpectedConditions.refreshed(new ExpectedCondition<Boolean>(){
							@Override
							public Boolean apply(WebDriver driver) {
								String value = driver.findElement(locater).getAttribute(attribute);
								if(attribute.trim().equalsIgnoreCase("disabled"))
									return value == null ? true : value.trim().contains(text);
								else
									return value == null ? false : value.trim().contains(text);
							}
							
						}));
						if(val){
							we = driver.findElement(locater);
							break;
						}
					} catch(Exception ex1){
						throw ex1;
					}
				}
				else{
					if(counter >= seconds || seconds <= 20)
						throw ex;
				}
			}
			finally{
				counter += time;
			}
		}while(true);
		
		return we;
	}
	
	static void sync(Long sTime) {
		try {
			Thread.sleep(sTime);
		} catch (InterruptedException e) {			
		}
	}
	
	static boolean isInvoiceDetailDisplayed(){
		sync(5000L);
		getElementWhenVisible(invoiceDetail, 40);
		WebElement selectedInvoiceBalance = getElementWhenVisible(selectedInvoice);
		String selInvoiceBalance = selectedInvoiceBalance.getText();
		try{
			getElementWhenRefreshed(amount_Due, "innerHTML", selInvoiceBalance);
		} catch(Exception ex) {
			driver.navigate().refresh();
			sync(5000L);
			getElementWhenVisible(invoiceDetail, 40);
			getElementWhenRefreshed(amount_Due, "innerHTML", selInvoiceBalance);
		}
		return true;
	}
	
	static WebElement getElementWhenRefreshed(final By locater, final String attribute, final String text, long... waitSeconds){
		assert waitSeconds.length <= 1;
		long seconds = waitSeconds.length > 0 ? waitSeconds[0] : 26;
		WebElement we = null;
		int counter = 0;
		do{
			long time = 20;
			if(seconds <= 20)
				time = seconds;
			WebDriverWait wait  = new WebDriverWait(driver, time);
			try{
				Boolean val = wait.until(ExpectedConditions.refreshed(new ExpectedCondition<Boolean>(){
					@Override
					public Boolean apply(WebDriver driver) {
						String value = driver.findElement(locater).getAttribute(attribute);
						if(attribute.trim().equalsIgnoreCase("disabled"))
							return value == null ? true : value.trim().equalsIgnoreCase(text);
						else
							return value == null ? false : value.trim().equalsIgnoreCase(text);
					}
					
				}));
				if(val){
					we = driver.findElement(locater);
					break;
				}
			}
			catch(Exception ex){
				boolean flag = false;
				if(flag && seconds <= 20){
					try{
						Boolean val = wait.until(ExpectedConditions.refreshed(new ExpectedCondition<Boolean>(){
							@Override
							public Boolean apply(WebDriver driver) {
								String value = driver.findElement(locater).getAttribute(attribute);
								if(attribute.trim().equalsIgnoreCase("disabled"))
									return value == null ? true : value.trim().equalsIgnoreCase(text);
								else
									return value == null ? false : value.trim().equalsIgnoreCase(text);
							}
							
						}));
						if(val){
							we = driver.findElement(locater);
							break;
						}
					} catch(Exception ex1){
						throw ex1;
					}
				}
				else{
					if(counter >= seconds || seconds <= 20)
						throw ex;
				}
			}
			finally{
				counter += time;
			}
		}while(true);
		
		return we;
	}
	
	/**
	 * Get element when rendered
	 * 
	 * @param locater
	 * @param objName
	 * @param text
	 * @param waitSeconds
	 * @return
	 */
	static WebElement getElementWhenRendered(final By locater, final int height, final int width, long... waitSeconds){
		assert waitSeconds.length <= 1;
		long seconds = waitSeconds.length > 0 ? waitSeconds[0] : 26;
		WebElement we = null;
		int counter = 0;
		do{
			long time = 20;
			if(seconds <= 20)
				time = seconds;
			WebDriverWait wait  = new WebDriverWait(driver, time);
			try{
				Boolean val = wait.until(ExpectedConditions.refreshed(new ExpectedCondition<Boolean>(){
					@Override
					public Boolean apply(WebDriver driver) {
						int h = driver.findElement(locater).getSize().getHeight();
						int w = driver.findElement(locater).getSize().getWidth();
						return h >= height && w >= width;
					}
					
				}));
				if(val){
					we = driver.findElement(locater);
					break;
				}
			}
			catch(Exception ex){
				boolean flag = false;
				if(flag && seconds <= 20){
					try{
						Boolean val = wait.until(ExpectedConditions.refreshed(new ExpectedCondition<Boolean>(){
							@Override
							public Boolean apply(WebDriver driver) {
								int h = driver.findElement(locater).getSize().getHeight();
								int w = driver.findElement(locater).getSize().getWidth();
								return h >= height && w >= width;
							}
							
						}));
						if(val){
							we = driver.findElement(locater);
							break;
						}
					} catch(Exception ex1){
						throw ex1;
					}
				}
				else{
					if(counter >= seconds || seconds <= 20)
						throw ex;
				}
			}
			finally{
				counter += time;
			}
		}while(true);
		
		return we;
	}
	
	static void isContinueButtonDisplayed(){
		sync(2000L);
		getElementWhenClickable(continueButton);
	}
	
	static WebElement getElementWhenClickable(By locator, long...waitSeconds){
		assert waitSeconds.length <= 1;
		long seconds = waitSeconds.length > 0 ? waitSeconds[0] : 26;

		WebElement element = null;
		
		int counter = 0;
		do{
			long time = 20;
			if(seconds <= 20)
				time = seconds;
			WebDriverWait wait  = new WebDriverWait(driver, time);
			try{
				element = wait.until(ExpectedConditions.elementToBeClickable(locator));
				break;
			}
			catch(Exception ex){
				boolean flag = false;
				if(flag && seconds <= 20){
					try{
						element = wait.until(ExpectedConditions.elementToBeClickable(locator));
						break;
					} catch(Exception ex1){
						throw ex1;
					}
				}
				else{
					if(counter >= seconds || seconds <= 20)
						throw ex;
				}
			}
			finally{
				counter += time;
			}
		}while(true);
		
		return element;
	}
	
	static void clickContinueButton(){
		sync(2000L);
		click(continueButton);
	}
	
	static void click(By locator, long... waitSeconds){
		int counter = 20;
		WebElement we = getElementWhenClickable(locator, waitSeconds);
		while(counter >= 0){
			try{
				if(we != null){
					we.click();
					break;
				}
			} catch(Exception ex){
				if(counter == 0){
					throw ex;
				}
				sync(500L);
				counter--;
			}
		}
	}
	
	static void clickpayInFull(){
		getElementWhenVisible(payInFull);
		click(payInFull);
	}
	
	static void clickContinuePlan(){
		getElementWhenRefreshed(continuePlan, "disabled", "null");
		click(continuePlan);
	}
	
	static void clickContinueCard(){
		getElementWhenRefreshed(continueCard, "disabled", "null");
		click(continueCard);
	}
	
	static void typeBillingDetails(String FirstName,String LastName,String Add1, String Add2, String Country, String State,String City,String Zipcode){
		getElementWhenVisible(firstName).sendKeys(FirstName);
		getElementWhenVisible(lastName).sendKeys(LastName);
		getElementWhenVisible(streetAdd1).sendKeys(Add1);
		getElementWhenVisible(streetAdd2).sendKeys(Add2);
		getElementWhenVisible(city).sendKeys(City);
		getElementWhenVisible(zipCode).sendKeys(Zipcode);
	}
	
	static void clickContinueBilling(){
		getElementWhenRefreshed(billingContinue, "disabled", "null");
		click(continueBilling);
	}
}

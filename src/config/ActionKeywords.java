package config;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import executionEngine.DriverScript;
import utility.Log;

public class ActionKeywords {
	
	public static WebDriver driver;
	public static int wait = 3000;
	private static WebDriverWait iwait;
	
	//public java.util.List<getProduct> itemsResult  = new  ArrayList<getProduct>();
	
	//public java.util.List<WebElement> getAllItems;
	private static void waitElement(WebElement element)
	{
		iwait = new WebDriverWait(driver, 10);
		iwait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public static void openBrowser(String object, String browser){
		
		try{				
			if(browser.equals("Firefox")){
				driver=new FirefoxDriver();
				Log.info("Firefox browser started");				
				}
			else if(browser.equals("IE")){
				//Dummy Code, Implement you own code
				driver=new InternetExplorerDriver();
				Log.info("IE browser started");
				}
			else if(browser.equals("Chrome")){
				//Dummy Code, Implement you own code
				driver=new ChromeDriver();
				Log.info("Chrome browser started");
				}
			else {
				driver=new FirefoxDriver();
				Log.info("Firefox browser started");
			}
 
			int implicitWaitTime=(10);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		}
		catch (Exception e){
			Log.info("Not able to open the Browser --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void navigate(String object, String data){
		try{
			Log.info("Navigating to URL");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(Constants.URL);
		}catch(Exception e){
			Log.info("Not able to navigate --- " + e.getMessage());
			DriverScript.bResult = false;
			}
		}
 
	public static void click(String object, String data){
		try{
			Log.info("Clicking on "+ object);
			//Thread.sleep(wait);
			waitElement(driver.findElement(RespositoryParser.getObject(object)));
			WebElement element = driver.findElement(RespositoryParser.getObject(object));
			element.click();
		 }catch(Exception e){
 			Log.error("Not able to click " + object +  " --- " + e.getMessage());
 			DriverScript.bResult = false;
         	}
		}
 
	public static void input(String object, String data){
		try{
			Log.info("Entering the text in " + object);
			waitElement(driver.findElement(RespositoryParser.getObject(object)));
			WebElement element = driver.findElement(RespositoryParser.getObject(object));
			element.clear();
			element.sendKeys(data);
		 }catch(Exception e){
			 Log.error("Not able to Enter " + object +  "--- " + e.getMessage());
			 DriverScript.bResult = false;
		 	}
		}
	
	public static void select(String object, String data){
		
		try {
			Log.info("Choose a value in dropdown " + object);
			waitElement(driver.findElement(RespositoryParser.getObject(object)));
			WebElement element = driver.findElement(RespositoryParser.getObject(object));
			Select select = new Select(element);
			select.selectByVisibleText(data);
		} catch (Exception e) {
			// TODO: handle exception
			Log.error("Not able to choose " + object + " --- " + e.getMessage());
			DriverScript.bResult = false;
		}
		
	}
	
	public static void assertText(String object, String expected){
		
		try {
			
			Log.info("Assert text in field " + object);
			waitElement(driver.findElement(RespositoryParser.getObject(object)));
			WebElement element = driver.findElement(RespositoryParser.getObject(object));
			String actual = element.getText();
			Assert.assertEquals(expected, actual);
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.error("Actual text is not like Expected text " + object + " --- " + e.getMessage());
			DriverScript.bResult = false;
		}
		
	}
	
	public static void verifyText(String object, String expected){
		
		try {
			Log.info("Verify text in field " + object);
			waitElement(driver.findElement(RespositoryParser.getObject(object)));
			WebElement element = driver.findElement(RespositoryParser.getObject(object));
			String actual = element.getText();
			if(expected.equals(actual)){
				
				Log.info("Verify text in field " + object + "successfully");
				
			}
			else {
				Log.info("Verify text in field " + object + "fail");
				DriverScript.bSkip = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.error("Not able to get text " + object + " --- " + e.getMessage());
			DriverScript.bResult = false;
		}
		
	}
 
	public static void waitFor(String object, String data) throws Exception{
		try{
			Log.info("Wait for 5 seconds");
			Thread.sleep(wait);
			
		 }catch(Exception e){
			 Log.error("Not able to Wait --- " + e.getMessage());
			 DriverScript.bResult = false;
         	}
		}
 
	public static void closeBrowser(String object, String data){
		try{
			Log.info("Closing the browser");
			driver.quit();
		 }catch(Exception e){
			 Log.error("Not able to Close the Browser --- " + e.getMessage());
			 DriverScript.bResult = false;
         	}
	}
	
	public static void scrollHeightToPixel(String object, String data)
    {
        try
        {
            Log.info("scroll to pixel" + data);
            String scrollBy = "window.scrollBy(0," + data +")";
            ((JavascriptExecutor) driver).executeScript(scrollBy);
        }
        catch (Exception ex)
        {
            Log.error("Not to be able to scroll to this " + data + "---" + ex);
            DriverScript.bResult = false;
        }
    }
    public static void scrollToElement(String object, String data)
    {
        try
        {
            Log.info("scroll to object" + data);
            waitElement(driver.findElement(RespositoryParser.getObject(object)));
            WebElement element = driver.findElement(RespositoryParser.getObject(data));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        }
        catch (Exception ex)
        {
            Log.error("Cannot find this " + data + "---" + ex);
            
        }
        
    }
    public static void scrollToBottom(String object, String data)
    {
    	((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
	
	/*
	public void inputItems(String object, String data){
		
		try {
			Log.info("Input Items");
			Thread.sleep(wait);
			initOrder();
			for (int i = 0; i < DriverScript.itemsResult.size(); i++) {
				
			
			WebElement purchaseUoM = get_PurchaseUoM(DriverScript.itemsResult.get(i).name);
			purchaseUoM.sendKeys(DriverScript.itemsResult.get(i).purchaseUoM);
			WebElement qty =  get_RequestQty(DriverScript.itemsResult.get(i).name);
			qty.sendKeys(DriverScript.itemsResult.get(i).requestQty);
			System.out.println("Input " + DriverScript.itemsResult.get(i).name + DriverScript.itemsResult.get(i).requestQty + DriverScript.itemsResult.get(i).purchaseUoM);
			Log.info("Input " + DriverScript.itemsResult.get(i).name + DriverScript.itemsResult.get(i).requestQty + DriverScript.itemsResult.get(i).purchaseUoM);
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.error("Canot input item --- " + e.getMessage());
			DriverScript.bResult = false;
		}
		
	}
	
	public void initOrder(){
		
		try {
			getAllItems = driver.findElements(RespositoryParser.getObject("listItems"));
			//java.util.List<WebElement> items = getAllItems;
			 for (WebElement webElement : getAllItems) {
				 
				WebElement productname = webElement.findElement(RespositoryParser.getObject("listProductName"));
				String text = productname.getText();
				
				WebElement requestQty_tmp = webElement.findElement(RespositoryParser.getObject("listQuantity"));
				WebElement purchaseUoM_tmp =  webElement.findElement(RespositoryParser.getObject("listUoM"));
				
				getProduct x = new getProduct();
				x.requestQty = requestQty_tmp;
				x.purchaseUoM = purchaseUoM_tmp;
				x.name = text;
				itemsResult.add(x);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public WebElement get_RequestQty(String strProductName)
	{
		for (int i = 0; i < itemsResult.size(); i++) {
			if (itemsResult.get(i).name.equals(strProductName)) {
				return  itemsResult.get(i).requestQty;
				//WebElement b = itemsResult.get(i).purchaseUoM;
				}
			
		}
		return null;
		
	}
	
	public WebElement get_PurchaseUoM(String strProductName)
	{
		for (int i = 0; i < itemsResult.size(); i++) {
			if (itemsResult.get(i).name.equals(strProductName)) {
				return  itemsResult.get(i).purchaseUoM;
				//WebElement b = itemsResult.get(i).purchaseUoM;
				}
			
		}
		return null;
		
	}
	
	public class getProduct
	{
		public WebElement requestQty;
		public WebElement purchaseUoM;
		public String name;
	}
	*/
}

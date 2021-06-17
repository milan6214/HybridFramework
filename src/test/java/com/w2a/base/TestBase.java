package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import com.w2a.utilities.TestUtil;

public class TestBase {

	/*
	 * webdriver properties Logs Extent report Mail DB Excel
	 * 
	 */

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;

	@BeforeSuite
	public void setUp() {

		if (driver == null) {

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config properties loaded!!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR properties loaded!!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (config.getProperty("browser").equals("firefox")) {

				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\geckodriver.exe");

				driver = new FirefoxDriver();
				log.debug("firefox launched!!!");

			} else if (config.getProperty("browser").equals("chrome")) {

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");

				driver = new ChromeDriver();
				log.debug("chrome launched!!!");
			} else if (config.getProperty("browser").equals("ie")) {

				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");

				driver = new InternetExplorerDriver();

			}
			driver.manage().window().maximize();
			driver.get(config.getProperty("testsiteurl"));
			log.debug("Navigate to  :   " + config.getProperty("testsiteurl"));
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);
		}

	}
	
	//This code for customizing the reading the locator.
	
	
	public void click(String locator) {

		if (locator.endsWith("_CSS")) {
		
		driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		}
		
		if (locator.endsWith("_XPATH")) {
			
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
			}
		
		if (locator.endsWith("_ID")) {
			
			driver.findElement(By.id(OR.getProperty(locator))).click();
			}
		
		test.log(LogStatus.INFO, "  clicking on : " + locator);
	}
	
	//This code for reading and writting the locator and its value
	
	public void type(String locator, String value) {
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		}
		
		if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		}
		
		if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		
		test.log(LogStatus.INFO, "Typing on : " + locator + "  entered as value : " + value);
	}
	
	//drop down select class
	
	static WebElement dropdown;
	
	public void select(String locator, String value)
	{
		
		if (locator.endsWith("_CSS")) {
			dropdown=driver.findElement(By.cssSelector(OR.getProperty(locator)));
		}
		
		if (locator.endsWith("_XPATH")) {
			dropdown=driver.findElement(By.xpath(OR.getProperty(locator)));
		}
		
		if (locator.endsWith("_ID")) {
			dropdown=driver.findElement(By.id(OR.getProperty(locator)));
		}
		Select select=new Select(dropdown);
		select.selectByVisibleText(value);
		test.log(LogStatus.INFO, "Selecting from dropdown : " + locator + "  value as : " + value);
	}

	public boolean isElementPresent(By by) {

		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}
	
	
	//This for soft assertion code.
	
	public static void verifyEquals(String expected, String actual) throws IOException
	{
		
		try {
			
			Assert.assertEquals(actual, expected);
		}catch(Throwable t) {
			
			
			TestUtil.captureScreenshot();
			
			//Report NG
			Reporter.log("<br>"+"Verification failure  :  "+t.getMessage()+"<br>");
			
			Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");//This line for creating thumb image.
			Reporter.log("<br>");
			
			
			//ExtentReport
			test.log(LogStatus.FAIL, " Verification failed with exception : "+t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
			
		}
	}

	@AfterSuite
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}
		log.debug("All Testcases execution successfully completed.");

	}
}

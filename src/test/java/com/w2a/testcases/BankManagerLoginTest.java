package com.w2a.testcases;

import static org.testng.Assert.fail;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class BankManagerLoginTest extends TestBase{

	
	@Test
	public void bankManagerLoginTest() throws InterruptedException, IOException {
		
		
		
		verifyEquals("abc", "abc");//Soft assertion failure
		//Thread.sleep(3000);
		log.debug("Inside loginAsBankManagerTest");
		
		click("bmlBtn_CSS");
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))),"Login not successful");
		log.debug("loginAsBankManagerTest successfully executed");//This for application log which are manually.
		
		//Assert.fail("failing test case");  //hard assertion failure
		
		
	}
}
 
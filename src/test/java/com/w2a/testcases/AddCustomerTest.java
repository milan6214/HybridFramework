package com.w2a.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class AddCustomerTest extends TestBase{

	
	/*@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void addCustomerTest(String firstName, String lastName, String postCode, String alerttext) throws InterruptedException{
		
		log.debug("Inside AddCustomerTest");
		click("addCustBtn_CSS");	
		type("firstname_CSS",firstName);				//This whole code for read the data from excel sheet along with parameters.
		type("lastname_CSS",lastName);
		type("postcode_CSS",postCode);
		click("addbtn_CSS");*/
	
	
	
	
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void addCustomerTest(Hashtable<String,String> data) throws InterruptedException{
		
		
		if(!data.get("runmode").equals("Y"))
		{
			
			throw new SkipException("Skipping the test cases as runmode for data set is No");
		}
		log.debug("Inside AddCustomerTest");
		click("addCustBtn_CSS");	
		type("firstname_CSS",data.get("firstname"));
		type("lastname_CSS",data .get("lastname"));
		type("postcode_CSS",data.get("postcode"));
		click("addbtn_CSS");
	
		//Thread.sleep(3000);
		Alert alert=wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
		alert.accept();
		
		//Assert.fail("Customer not added successfully");
		
		//Thread.sleep(3000);
		log.debug("AddCustomerTest successfully executed");
	
	}
	
	
	
}







package com.w2a.listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class CustomListeners extends TestBase implements ITestListener {

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult result) {
		
		System.setProperty("org.uncommons.reportng.escape-output","false");//This line for generating image file in reportng
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//This below code for extent report
		
		test.log(LogStatus.FAIL, result.getName().toUpperCase()+" Failed with exception : "+result.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		
		
		Reporter.log("Click to see Screenshot");
		
		//Reporter.log("Login successfully executed");//this log for reportng
		
		//This code for generating the customize report of reportng and also add in testng emailable report.
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+">Screenshot</a>");//This line for creating screenshot link
		Reporter.log("<br>");//This line for next line
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");//This line for creating thumb image.
		rep.endTest(test);
		rep.flush();
	}

	public void onTestSkipped(ITestResult result) {
		
		
		
		
		test.log(LogStatus.SKIP, result.getName().toUpperCase()+"Skipped the test as the run mode is No ");
		rep.endTest(test);
		rep.flush();
	}

	public void onTestStart(ITestResult result) {
		test= rep.startTest(result.getName().toUpperCase());
		
		
		
		
	}

	public void onTestSuccess(ITestResult result) {

		
		test.log(LogStatus.PASS, result.getName().toUpperCase()+" PASS");
		rep.endTest(test);
		rep.flush();
	}

}

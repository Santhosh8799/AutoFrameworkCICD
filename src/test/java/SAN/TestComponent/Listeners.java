package SAN.TestComponent;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import SAN.Resource.ExtentReportNG;

public class Listeners extends BaseTest implements ITestListener{

	
	ExtentReports extent=ExtentReportNG.getReports();
	ThreadLocal<ExtentTest> thread=new ThreadLocal<ExtentTest>();//Thread Safe -During parallel Execution Failed test Status was reporting to another Test
	                         //to overcome this scenario in Test Results Report we use Thread local class 
	
	ExtentTest test;
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		test=extent.createTest(result.getMethod().getMethodName());
		thread.set(test);//unique thread id(ErrorValidationTest)->test-During Parallel Execution 
		                 //SubmitOrder test will have one unique Id ->test will use exact id in extentreport accurately 
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
	    thread.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		thread.get().fail(result.getThrowable());
		//Initally driver will be null so we are writing the below code to exactly find 
		//driver on which page and capture screenshot
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
					.get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String path=null;
		 try {
			path=getScreenshot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 thread.get().addScreenCaptureFromPath(path,result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		extent.flush();
	}

	
	

}

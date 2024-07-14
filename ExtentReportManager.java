package utility;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import TestData.BaseClass;

public class ExtentReportManager implements ITestListener
{
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	public void onStart(ITestContext testContext) {
		
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());		// time stamp creation
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\Reports\\" + repName);		// specify location of the report
		
		sparkReporter.config().setDocumentTitle("CAS Automation Report"); 		// Title of report
		sparkReporter.config().setReportName("Cognizant Websites Testing"); 	// name of the report
		sparkReporter.config().setTheme(Theme.DARK);		//setting the background theme of the WebPage
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);			//attaching all the system info. in the html doc created
		extent.setSystemInfo("Application", "BeCognizant, OneCognizant");
		extent.setSystemInfo("Module", "World Clock");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environemnt", "QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os");		//getting the os parameter info. from the xml file
		extent.setSystemInfo("Operating System", os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser");		//getting the browser parameter info. from the xml file
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();		//getting all the groups included in the xml file
		if(!includedGroups.isEmpty()) {
		extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}

	
	public void onTestSuccess(ITestResult result) {
	
		test = extent.createTest(result.getTestClass().getName() + " - " + result.getMethod().getMethodName());		//to create the test and display the class and method name into the report
		test.assignCategory(result.getMethod().getGroups()); 		// to display groups of the test method in report
		test.log(Status.PASS, result.getName()+" got successfully executed");		//to mark pass and print the name of the running test method
		
		try {
			String imgPath = new BaseClass().captureScreen(result.getName());		//getting the path of the catured image
			test.addScreenCaptureFromPath(imgPath);			//adding that image into the html file
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

	
	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName() + " - " + result.getMethod().getMethodName());		//to create the test and display the class and method name into the report
		test.assignCategory(result.getMethod().getGroups());			// to display groups of the test method in report
		
		test.log(Status.FAIL,result.getName()+" got failed");		//to mark fail and print the name of the running test method
		test.log(Status.INFO, result.getThrowable().getMessage());		//to provide the information about the failling the test method
		
		try {
			String imgPath = new BaseClass().captureScreen(result.getName());		//getting the path of the catured image
			test.addScreenCaptureFromPath(imgPath);			//adding that image into the html file
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	
	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName() + " - " + result.getMethod().getMethodName());		//to create the test and display the class and method name into the report
		test.assignCategory(result.getMethod().getGroups());		// to display groups of the test method in report
		test.log(Status.SKIP, result.getName()+" got skipped");		//to mark skip and print the name of the running test method
		test.log(Status.INFO, result.getThrowable().getMessage());		//to provide the information about the skiping the test method
	}

	public void onFinish(ITestContext testContext) {
		
		extent.flush();				//this will be writting the information into the test report
		
		String pathOfExtentReport = System.getProperty("user.dir")+"\\Reports\\"+repName;		//determing the path of the extent report
		File extentReport = new File(pathOfExtentReport);			//saving the report into the provided path
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());		//interact directly with the file and launch it
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
		

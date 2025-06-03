package utilities;

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

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener

{
	public ExtentSparkReporter sparkReporter;  // UI of the report
	public ExtentReports extent; //populate common info on the report(example tester name, module name, browser name, operating system)
	public ExtentTest test; // creating test case entries in the report and update status of the test methods (Pass or fail and screenshots)
	
	String repName;
	
	public void onStart(ITestContext testContext) {
		
		/* SimpleDateFormat  df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		 Date dt = new Date();
		 String currentdatetimestamp = df.format(dt) 
		 //Combined above 3 statements to singe statement */ 
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // time stamp
		repName= "Test-Report-"+ timeStamp + ".html";
		
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); //specify the location of the report
		
		sparkReporter.config().setDocumentTitle("opencart Automation Report"); // Title of report
		sparkReporter.config().setReportName("opencart Function Testing"); // name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent= new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name")); // current user of the system who is executing as environmental variable
		extent.setSystemInfo("Environment", "QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System",os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser",browser);
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty())  // if the include groups is empty for example if we run master.xml file where groups information not availble
		{
			extent.setSystemInfo("Groups", includedGroups.toString());
		}		
	}
	
	public void onTestSucess(ITestResult result) 
	{
		test = extent.createTest(result.getTestClass().getName()); //create a new entry in the report
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.PASS,result.getName()+" got successfully executed"); // update status pass/fail/skipped
		
	}
	
	public void onTestFailure(ITestResult result) 
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		
		test.log(Status.FAIL,result.getName()+" got failed"); 
		test.log(Status.INFO,result.getThrowable().getMessage()); 
		
		try 
		{
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
			
		}catch(IOException e1)
		{
			e1.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result) 
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		
		test.log(Status.SKIP,result.getName()+" got skipped"); 
		test.log(Status.INFO,result.getThrowable().getMessage()); 
	}
	
	public void onFinish(ITestContext testContext) 
	{
		extent.flush();
		
		//Reports to open automatically instead of manually opening
		String pathofExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathofExtentReport);
		
		try 
		{
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		/*
		  try 
		  {	
			  URL url = new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
			  
			  //Create the email message
			  ImageHtmlEmail email = new ImageHtmlEmail();
			  email.setDataSourceResolver(new DataSourceUrlResolver(url));
			  email.setHostName("smtp.googlemail.com");
			  email.setAuthenticator(new DefaultAuthenticator("devika.hebbalu@tesco.com","password"));
			  email.setSSLOnConnect(true);
			  email.setFrom("devika.hebbalu@tesco.com"); //Sender
			  email.setSubject("Test Results");
			  email.setMsg("Please find Attached Report......");
			  email.addTo("devika.hebbalu@tesco.com"); //Receiver
			  email.attach(url, "extent report", "please check report.....");
			  email.send();  // send the email
			}
			catch(Exception e) 
			{
				e.printStackTrace();
		    }
		 */
	}
	
}


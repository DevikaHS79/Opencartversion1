package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
//import org.apache.logging.log4j.core.Logger;  //Log4j
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.Date;
import java.util.Properties;


public class BaseClass {
	
public static WebDriver driver ;
public Logger logger;  //Log4j
public Properties p;


	@BeforeClass(groups={"Sanity","Regression","Master"})
	@Parameters({"os","browser"})  //need to import from Testng Annotations
	
	public void setup(String os, String br) throws IOException
	{
		FileReader file = new FileReader("./src\\test\\resources\\config.properties") ; //path
		p=new Properties();
		p.load(file);  // load the properties file
		
		logger=LogManager.getLogger(this.getClass());  //LOG4J2
		
		// Added new steps to run the exceution locally or remotely using grid environment
		if (p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();
			//capabilities.setPlatform(Platform.WIN11); this data comes from xml file 
			//capabilities.setBrowserName("chrome");
			
			//os
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN11);
			}
			else if (os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No matching os");
				return;
			}
			
			//browser
			switch(br.toLowerCase())
			{
			case "chrome" : capabilities.setBrowserName("chrome"); break;
			case "edge" : capabilities.setBrowserName("MicrosoftEdge"); break;
			case "firefox" : capabilities.setBrowserName("firefox"); break;
			default: System.out.println("No matching browser");return;
			}
			
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
		}
		
		if (p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome" : driver = new ChromeDriver();break;
			case "edge"   : driver = new EdgeDriver();break;
			case "firefox" : driver = new FirefoxDriver();break;
			default : System.out.println("Invalid browser name..");return ;
			}
		}
		
		//Before adding the the grid environment running in local or remote
		/*switch(br.toLowerCase())
		{
		case "chrome" : driver = new ChromeDriver();break;
		case "edge"   : driver = new EdgeDriver();break;
		case "firefox" : driver = new FirefoxDriver();break;
		default : System.out.println("Invalid browser name..");return ;
		}*/
		
		//driver = new ChromeDriver(); instead of this we should use the above parameters
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		//driver.get("https://tutorialsninja.com/demo/");
		driver.get(p.getProperty("appURL2"));  //Reading URL from properties file (keys)
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups={"Sanity","Regression","Master"})
	public void closeBrowser()
	{
		driver.quit();
	}
	
	//User defined methods
	@SuppressWarnings("deprecation")
	public String randomeString()
	{
		String generatedstring = RandomStringUtils.randomAlphabetic(5);   //     randomAlphanumeric(5);     //
		return generatedstring;
	}
	
	@SuppressWarnings("deprecation")
	public String randomeNumber()
	{
		String generatednumber = RandomStringUtils.randomNumeric(10) ;
		return generatednumber;
	}
	
	public String randomeAlphaNumeric()
	{
		@SuppressWarnings("deprecation")
		String generatedstring = RandomStringUtils.randomAlphabetic(3) ;
		@SuppressWarnings("deprecation")
		String generatednumber = RandomStringUtils.randomNumeric(10) ;
		return (generatedstring+"@"+generatednumber);
	}
	
	public String captureScreen(String tname) throws IOException {
		
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourcefile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile = new File(targetFilePath);
		
		sourcefile.renameTo(targetFile);
		return targetFilePath;
	}

}


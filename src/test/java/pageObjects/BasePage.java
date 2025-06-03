package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

//This will be invoked by every page object constructor (for re-usability)
public class BasePage {
	
	WebDriver driver ;
	
	public BasePage(WebDriver driver) 
	{
		
		this.driver=driver;
		//Pre-defined class in Selenium Web Driver and call initElements methods . this will make sure driver is applicable on every elements or locators internally
		PageFactory.initElements(driver,this);
	}

}

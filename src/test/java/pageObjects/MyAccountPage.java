package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {
	WebDriver driver;
	
	//Constructor
	public MyAccountPage(WebDriver driver)
	{
		super(driver);
	}
	
	//Locators
	@FindBy(xpath="//h2[normalize-space()='My Account']")  // Account page heading
	WebElement msgHeading;
	
	//@FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']")
	
	@FindBy(xpath="//div[@class='list-group']//a[text()='Logout']")
	WebElement linkLogout;
	
	//Actions
	//Normally we dont do validation in Object class so we creation action where it verify the page exist and return
	public boolean isMyAccountPageExist()
	{
		try
		{
			return (msgHeading.isDisplayed());
		}
		catch(Exception e) 
		{
			return false;
		}
		
	}
	
	public void clickLogout()
	{
		linkLogout.click();
	}
}

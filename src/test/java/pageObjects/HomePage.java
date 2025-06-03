package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{
	
	//constructor
	WebDriver driver ;
	
	public HomePage(WebDriver driver)
	{
		//this.driver = driver ;
		//Pre-defined class in Selenium Web Driver and call initElements methods . this will make sure driver is applicable on every elements or locators internally
		//PageFactory.initElements(driver,this);
		super(driver);
	}
	
	//Locators
	@FindBy(xpath="//span[normalize-space()='My Account']")
	WebElement linkMyaccount;
	
	@FindBy(xpath="//a[normalize-space()='Register']")
	WebElement linkRegister;
	
	@FindBy(xpath="//a[normalize-space()='Login']")  // Login link is added later
	WebElement linkLogin;
	
	
	//Actions
	public void clickMyAccount()
	{
		linkMyaccount.click();
	}
	
	public void clickMyRegister()
	{
		linkRegister.click();
	}
	
	public void clickLogin()
	{
		linkLogin.click();
	}

}

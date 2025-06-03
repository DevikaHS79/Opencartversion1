package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	
	@Test(groups={"Sanity","Master"})
	public void verify_login()
	{
		logger.info("******Starting TC_002_LoginTest******");
		
		try
		{
			//Home page
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			Thread.sleep(2000);
			
			//Login page
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(p.getProperty("email"));
			lp.setPassword(p.getProperty("password"));
			lp.clickLogin();
			Thread.sleep(2000);
			
			//MyAccountPage
			MyAccountPage myacctpg = new MyAccountPage(driver);
			boolean targetpage = myacctpg.isMyAccountPageExist();
			Assert.assertTrue(targetpage);
			//Assert.assertEquals(targetpage,true,"Login faile" );   // we need description we can add one mor parameter
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("******Finished TC_002_LoginTest******");
	}

}

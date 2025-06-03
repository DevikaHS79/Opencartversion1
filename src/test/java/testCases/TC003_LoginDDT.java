package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;


/* 	Data is valid - login success - tets pass - logout
 	Data is valid -- login failed - test fail
 	
 	Data is invalid - login success - test fail - logout
 	Data is invalid -- login failed - test pass
  */

public class TC003_LoginDDT extends BaseClass{
	
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="Datdriven")  // data provider method name as parameter and also getting dataprovider from different class so need to add one more parameter  "dataProviderClass"
	public void verify_loginDDT(String email, String pwd, String exp) throws InterruptedException
	{
		logger.info("**************Starting TC003_LoginDDT***********************");
		
		try
		{
			//Home page
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			Thread.sleep(2000);
			
			//LoginPage
			LoginPage lp= new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();
			Thread.sleep(2000);
			//MyAccountPage
			MyAccountPage myacctpg = new MyAccountPage(driver);
			Boolean targetpage = myacctpg.isMyAccountPageExist();
			
			/* 	Data is valid - login success - test pass - logout
		 	Data is valid -- login failed - test fail
		 	
		 	Data is invalid - login success - test fail - logout
		 	Data is invalid -- login failed - test pass
		  */
			if(exp.equalsIgnoreCase("Valid"))
			{ if(targetpage==true)
				{
					myacctpg.clickLogout();
					Assert.assertTrue(true);
				}
				else
				{
					Assert.assertTrue(false);
				}
			 }
			//else{}
			if(exp.equalsIgnoreCase("Invalid"))
			{
				if(targetpage==true)
				{
					myacctpg.clickLogout();
					Assert.assertTrue(false);
				}
				else
				{
					Assert.assertTrue(false);
				}
			}
		}catch(Exception e)
		{
			Assert.fail();
		}
		Thread.sleep(2000);
	
		logger.info("**************Finished TC003_LoginDDT**************");
	}
	
}

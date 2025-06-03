package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccontRegistrationTest extends BaseClass {

	@Test(groups={"Regression","Master"})
	public void verify_account_registration()
	{
		logger.info("***** Starting TC001_AccontRegistrationTest ******");
		try
		{
		//we need driver as we use constructor in Home page objects model
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on MyAccount Link..");
		hp.clickMyRegister();
		logger.info("Clicked on Register Link..");
		
		AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
		
		logger.info("Providing customer details..");
		regpage.setFirstName(randomeString());   //regpage.setFirstName(randomeString().toUpperCase());
		regpage.setLastName(randomeString());
		regpage.setEmail(randomeString()+"@gmail.com"); //abnc123@gamil.com  // randomly generated EmailId //abnc@gamil.com
		regpage.setTelephone(randomeNumber());
		//Capture the value in a variable so that same pwd is passed in both the places or else it will generated 2 different password
		String pwd = randomeAlphaNumeric();
		regpage.setPassword(pwd);  //Manish@123
		regpage.setCPassword(pwd);
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		logger.info("Validating expected message..");
		//return the message so we need to assign it to variable
		String confmsg = regpage.getConfirmationMsg();
		Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		} catch(Exception e)
		{
			logger.error("Test Failed..");
			logger.debug("Debug logs...");
			Assert.fail();
		}
		logger.info("***** Finished TC001_AccontRegistrationTest ******");
	}
}

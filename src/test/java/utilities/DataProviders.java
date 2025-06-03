package utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders 
{
	
	//DataProviders
	
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path = ".\\testData\\Opencart_LoginData.xlsx"; //taking xl file from test data
		
		ExcelUtility xlutil = new ExcelUtility(path);  // creating an object for xlUtility
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1",1);
		
		String logindata[][] = new String[totalrows][totalcols]; // created for two dimension array which can store data
		
		for(int i=1;i<=totalrows;i++)   //1 //read the data from xl storing in two dimensional array
		{
			for(int j=0;j<totalcols;j++)  //0 i is rows j is col
			{
				logindata[i-1][j]= xlutil.getCellData("Sheet1", i, j);  //1,0  array index starts from zero so we are writing rownum as i=1
			}
		}
		
		return logindata; // returning two dimension array
		
		//Data Provider2
		
		//Data Provider3
		
		//Data Provider4
		
	}

}

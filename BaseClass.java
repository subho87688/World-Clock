package TestData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import com.google.common.io.Files;

public class BaseClass {

	
	public static WebDriver driver;
	public Properties p;
	WebDriverWait wait;
	
	
	@BeforeClass(groups = {"sanity","regression"})
	@Parameters({"browser"})
	public void setUp(String br) throws Exception
	{
		 //loading properties file
		 FileReader file=new FileReader(".//src//test//resources//Config.properties");
		 p=new Properties();
		 p.load(file);
		 
		
		//launching browser based on condition
		if(br.equals("chrome"))
		{
			driver=new ChromeDriver();
		}
		else if(br.equals("edge"))
		{
			driver = new EdgeDriver();
		}
		else
		{
			System.out.println("No matching browser..");
			return;
		}
		
		//Explicit wait
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		
		//Implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        
        //opening the web application		https://be.cognizant.com
        driver.get(p.getProperty("appUrl"));
        
        Thread.sleep(20000);		// LOGIN PURPOSE.
        
	}
	
	
	
	@AfterClass(groups = {"sanity","regression"})
	public void tearDown()
	{
		driver.quit();	//close all the session
	}
	
	//to capture screen shot
	public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				
		File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\ScreenShot\\" + tname + "_" + timeStamp + ".png";
		File targetFile=new File(targetFilePath);
		
		Files.copy(sourceFile, targetFile);
			
		return targetFilePath;

	}
	
	//to capture random alphabets from a-zA-Z
	public String getRandomString()
	{
		return RandomStringUtils.randomAlphabetic(1);
	}
	
}

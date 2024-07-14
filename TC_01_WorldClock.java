package TestData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import PageData.BeCognizantPage;
import PageData.GooglePages;

public class TC_01_WorldClock extends BaseClass{
	
	
	@Test(priority=1, groups = {"regression"})
	public void profileExtraction() throws Exception
	{
		BeCognizantPage be = new BeCognizantPage(driver);
		
		//Thread.sleep(20000);
		
		//wait until the profile section gets loaded completely.
		wait.until(ExpectedConditions.visibilityOf(be.settingsIcon));
		
		//clicking on the my profile section
		be.clickProfile();	
		
		//Extract User details
        String userName = be.getUserName();
        String userMailId = be.getMailId();
        
        System.out.println("*) User name :- " + userName + "\n*) User Mail Id :- " + userMailId);
        
        //click the profile to disappear the profile section
        be.clickProfile();
        
	}
	
	
	@Test(priority=2, groups = {"sanity","regression"})
	public void clockIsDisplayed()
	{
		BeCognizantPage be = new BeCognizantPage(driver);
		
		//scroll down 
        be.scrollToEnd();
        
        //World Clock displayed or not
        String wrldClck = be.getClockName();
        String exptOut   = "World Clock";
        
        Assert.assertEquals(wrldClck, exptOut, "Clock is Not Displayed....\n");
	}
	
	
	@Test(priority = 3, dependsOnMethods = {"clockIsDisplayed"},groups = {"sanity","regression"})
	public void dateAndWeekIsDisplayed()
	{
		BeCognizantPage be = new BeCognizantPage(driver);
		
		//List of Week and Date  week,dd/mm/yyyy
        List<WebElement> dateWeek = be.dateWeek;
        List<String> place = new ArrayList();
        place.add("A. Banglore");
        place.add("B. London");
        place.add("C. New York");
        
        System.out.println("\nDay and Dates of the 3 Cities: ");
        
        //validating wheather the date and day is visible or not
        if(dateWeek.size()==3)
        {
        	for(int i=0; i<3; i++)
        	{
        		Assert.assertNotNull(dateWeek.get(i));
        		System.out.println(place.get(i)+ " : " + dateWeek.get(i).getText());
        	}
        }else {
        	Assert.fail();
        }
       
	}
	
	
	@Test(priority = 4, dependsOnMethods = {"clockIsDisplayed"},groups = {"sanity","regression"})
	public void timeValidation()
	{
		BeCognizantPage be = new BeCognizantPage(driver);
		
		//List of time
        List<WebElement> time = be.time;

        //List of AM/PM
        List<WebElement> am_pm = be.am_pm;
        
        //Banglore Time Validation
        System.out.println("\n==============BANGLORE==============");
        Date now = new Date();                  //capturing today's date
        DateFormat dtf = new SimpleDateFormat("hh:mm a");       //displaying this date on IST timezone
        String expt_time_bnglr = dtf.format(now);
        String actual_time_bnglr = time.get(0).getText() + " " + am_pm.get(0).getText();
        if (actual_time_bnglr.length()<expt_time_bnglr.length())
            actual_time_bnglr = "0"+actual_time_bnglr;
        System.out.println("Expected: " + expt_time_bnglr + " || Actual: " + actual_time_bnglr);
        Assert.assertEquals(actual_time_bnglr, expt_time_bnglr, "Bangalore Time is Invalid...");	//validating the actual and expected time of bangalore is same or not
        
        
        //take the current window ID
        String parentWinId = driver.getWindowHandle();
        
        
        //create object of Google Page to get all the elements
        GooglePages gp = new GooglePages(driver);
        
        
        //--------LONDON TIME VALIDATION---------
        System.out.println("\n==============LONDON==============");
        //Open new Tab to find the london time.
        driver.switchTo().newWindow(WindowType.TAB);		//open the new window TAB 
        driver.get(p.getProperty("googleUrl"));				//opening the google page 
        gp.setSearchValue(p.getProperty("SearchLondon"));			//search the time of london
        gp.clickSearch();
        String expt_time_lndn = gp.getLondonTime();			//get the london time 
        driver.close();
        driver.switchTo().window(parentWinId);
        String actual_time_lndn = time.get(1).getText() + " " + am_pm.get(1).getText().toLowerCase();
        expt_time_lndn = expt_time_lndn.substring(0,expt_time_lndn.length()-3) + " " + expt_time_lndn.substring(expt_time_lndn.length()-2);
        System.out.println("Expected: " + expt_time_lndn + " || Actual: " + actual_time_lndn);
        Assert.assertEquals(actual_time_lndn, expt_time_lndn,"London Time is Invalid....");		//validating the actual and expected time of london is same or not
		
        
        //--------NEW YORK TIME VALIDATION---------
        System.out.println("\n==============NEW YORK==============");
        //Open new Tab to find the london time.....
        driver.switchTo().newWindow(WindowType.TAB);		//open the new window TAB
        driver.get(p.getProperty("googleUrl"));				//open the google page
        gp.setSearchValue(p.getProperty("newYorkSearch"));		//search the time of New York
        gp.clickSearch();
        String expt_time_newYork = gp.getNewYorkTime();			//get the london time 
        driver.close();
        driver.switchTo().window(parentWinId);
        String actual_time_newYork = time.get(2).getText() + " " + am_pm.get(2).getText().toLowerCase();
        expt_time_newYork = expt_time_newYork.substring(0,expt_time_newYork.length()-3) + " " + expt_time_newYork.substring(expt_time_newYork.length()-2);
        System.out.println("Expected: " + expt_time_newYork + " || Actual: " + actual_time_newYork);
        Assert.assertEquals(actual_time_newYork, expt_time_newYork,"New York Time is Invalid...");		//validating the actual and expected time of new york is same or not
        
        
        //Various Zones
        String kolkata_zone = p.getProperty("Kolkata");
        String london_zone = p.getProperty("London");
        String newYork_zone = p.getProperty("NewYork");


        //Difference validation between Kolkata and London
        LocalDateTime dt = LocalDateTime.now();			
        ZonedDateTime kolkata = dt.atZone(ZoneId.of(kolkata_zone));			//define Kolkata time zone
        ZonedDateTime london = dt.atZone(ZoneId.of(london_zone));			//define london time zone
        long exptd_london_diff = Duration.between(kolkata, london).toMinutes();			//comparing the diffrence between these two time zone in minutes
        System.out.println("\nExpected Difference between Kolkata & London time zone is : " + exptd_london_diff);		
        String diff1 = be.getlondonTimeDiff();		//extract the time difference from london tab
        
        int lndn_hrs = Math.abs(Integer.parseInt(diff1.substring(0,diff1.indexOf("h"))));		//extract hours from the text
        int lndn_mint = Integer.parseInt(diff1.substring(diff1.indexOf("h")+2,diff1.indexOf("m")));		//extract minutes from the text
        int actual_london_diff = lndn_hrs*60 + lndn_mint;			//calculating the total minutes.
        System.out.println("Actual Difference between Kolkata & London time zone is : " + actual_london_diff);
        Assert.assertEquals(actual_london_diff, exptd_london_diff, "Time Zone difference for London is not equal...");		//validating the the actual and expected time difference in bangalore        
        
        
        
        //Difference validation between kolkata and New York
        ZonedDateTime newYork = dt.atZone(ZoneId.of(newYork_zone));			//define new york time zone
        long exptd_newYork_diff = Duration.between(kolkata, newYork).toMinutes();		//comparing the diffrence between these two time zone in minutes
        System.out.println("\nExpected Difference between Kolkata & New-York time zone is : " + exptd_newYork_diff);
        String diff2 = be.getNewYorkTimeDiff();			//extract the time difference from new york tabs
        
        int newYork_hrs = Math.abs(Integer.parseInt(diff2.substring(0,diff2.indexOf("h"))));		//extract hours from the text
        int newYork_mint = Integer.parseInt(diff2.substring(diff2.indexOf("h")+2,diff2.indexOf("m")));		//extract minutes from the text
        int actual_newYork_diff = newYork_hrs*60 + newYork_mint;			//calculating the total minutes.
        System.out.println("Actual Difference between Kolkata & New-York time zone is : " + actual_newYork_diff);
        Assert.assertEquals(actual_newYork_diff, exptd_newYork_diff, "Time Zone difference for New-York is not equal...");		//validating the the actual and expected time difference in new york
        
	}

}

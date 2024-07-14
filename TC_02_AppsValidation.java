package TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import PageData.BeCognizantPage;
import PageData.OneCognizantPage;

public class TC_02_AppsValidation extends BaseClass{

	@Test(groups = {"sanity","regression"})
	public void hotAppSectionValidation() throws InterruptedException
	{
		//BeCognizantPage object making
		BeCognizantPage be = new BeCognizantPage(driver);
		
		//OneCognizantPage Object making
		OneCognizantPage oc = new OneCognizantPage(driver);
		
		//Clicking on OneCognizant button
        be.clickBeCognizant();
        
        
        //Catching Windows
        Set<String> win = driver.getWindowHandles();
        List<String> winLis = new ArrayList(win);
        String curntWindow = driver.getWindowHandle();
        String newWindow="";
        if(curntWindow.equals(winLis.get(0)))
        {
            newWindow = winLis.get(1);
        }else {
            newWindow = winLis.get(0);
        }
        
        //switch window
        driver.switchTo().window(newWindow);
        
        
        //click viewAll
        oc.clickViewAll();
        
        //collecting all alphabets element and validating displayed & clickable or not
        List<WebElement> alphabets = oc.aToz;
        System.out.println("\nElement || Displayed");
        for (WebElement e: alphabets) 
        {
            System.out.println(e.getText() + "  :  " + e.isDisplayed());
            Assert.assertEquals(e.isDisplayed(), true,e.getText() + "is not Displayed...");
        }
        System.out.println();

        
        //detail collection of any alphabet webelement
        String getAlphabet = getRandomString();
        
        System.out.println("* Selected alphabet is: "+getAlphabet);
        
        //Extracting Apps from the given alphabet
          for (WebElement e: alphabets)
	      {
	          if(e.getText().equalsIgnoreCase(getAlphabet))		//checks for the alphabet from the list
	          {
	              if(oc.isElementVisible(e))			//cheks wheather the alphabet is displayed or not
	              {
	              	  if (oc.getClass(e).equals("charAZBtn"))		//checks wheather the alphabet is having other applications or not.
	                  {
	                      e.click();
	                      Thread.sleep(2000);
	                      List<WebElement> alphaElements = oc.alphaEles;
	                      System.out.println("# All applications starting with " + e.getText() +" alphabet :- ");
	                      int i=1;
	                      for (WebElement f: alphaElements) {
	                          System.out.println(i + ") " + oc.getAppName(f));
	                          i++;
	                      }
	                      break;
	                  }else {
	                      System.out.println("No apps present with these alphabet name.");
	                      break;
	                  }
	              }else {
	              	Assert.fail("Element is Not Displayed");
	              }
	          }
	
	      }
        
	}
}
	
	


package PageData;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;


public class BeCognizantPage extends BasePage{
	
	public BeCognizantPage(WebDriver driver)
	{
		super(driver);
	}
	
	
	//Profile button
//	@FindAll(
//			{@FindBy(xpath="//div[@class='_8ZYZKvxC8bvw1xgQGSkvvA==']"),
//				@FindBy(xpath="//div[@id='mectrl_headerPicture']")
//			})
//	public WebElement profile;
	@FindBy(xpath="//div[@class='_8ZYZKvxC8bvw1xgQGSkvvA==']")
	public WebElement profile;
//	@FindBy(xpath="//div[@id='mectrl_headerPicture']")
//	public WebElement profile;
	
	
	//username
	@FindBy(id="mectrl_currentAccount_primary")	
	public WebElement name;
	
	//user mail address
	@FindBy(id="mectrl_currentAccount_secondary")
	public WebElement mail;
	
	//view All link
	@FindBy(xpath="//span[@class='fontSizeMedium']")
	public WebElement supportElement;
	
	//world clock text
	@FindBy(xpath="(//div[@data-automation-id='captionElement']/span)[3]")
	public WebElement clock;
	
	//list of all date and week visible 
	@FindBys(@FindBy(xpath="(//div[@class='n_b_816e1fa6'])//div[2]"))
	public List<WebElement> dateWeek;
	
	//list of all 3 time visible (Banglore, London, New York) 
	@FindBys(@FindBy(xpath="(//div[@class='m_b_816e1fa6'])//span[1]"))
	public List<WebElement> time;
	
	//list of all 3 time status visible (Banglore, London, New York) 
	@FindBys(@FindBy(xpath="(//div[@class='m_b_816e1fa6'])//span[2]"))
	public List<WebElement> am_pm;
	
	//time difference in London section
	@FindBy(xpath="(//div[contains(@class,'i_b_816e1fa6 bodyText-')])[2]")
	public WebElement london_time_difference;
	
	//time difference in New York section
	@FindBy(xpath="(//div[contains(@class,'i_b_816e1fa6 bodyText-')])[3]")
	public WebElement newYork_time_difference;
	
	//beCognizant button
	@FindBy(xpath="(//div[@data-automation-id='button-card'])[6]")
	public WebElement beCognizant;
	
	//Setting Icon
	@FindBy(xpath="//span[@class='ms-Icon--Settings RGSb/1c4ex9CM0as0oaLQA==']")
	public WebElement settingsIcon;
	
	
	
	
	//All actions for the above Elements......
	
	public void clickProfile() throws Exception
	{
		//Thread.sleep(10000);
		//profile.click();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()",profile);
//		Actions ac = new Actions(driver);
//		ac.doubleClick(profile).perform();
	}
	
	
	public String getUserName()
	{
		return name.getText();
	}
	
	public String getMailId()
	{
		return mail.getText();
	}
	
	public void scrollToEnd()
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true);",supportElement );
	}
	
	public String getClockName()
	{
		return clock.getText();
	}
	
	public String getlondonTimeDiff()
	{
		return london_time_difference.getText();
	}
	
	public String getNewYorkTimeDiff()
	{
		return newYork_time_difference.getText();
	}
	
	public void clickBeCognizant()
	{
		beCognizant.click();
	}
	
	public boolean isUserDetailsDisplayed()
	{
		return mail.isDisplayed();
	}
	
	
	
	
	
}

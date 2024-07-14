package PageData;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GooglePages extends BasePage{
	
	
	public GooglePages(WebDriver driver)
	{
		super(driver);
	}
	
	
	@FindBy(id="APjFqb")
	public WebElement searchBar;
	
	@FindBy(xpath="//div[@class='lJ9FBc']/descendant::input[1]")
	public WebElement enter;
	
	@FindBy(xpath="//div[@class='gsrt vk_bk FzvWSb YwPhnf']")
	public WebElement lndn_time;
	
	@FindBy(xpath="//div[@class='gsrt vk_bk FzvWSb YwPhnf']")
	public WebElement newYork_time;
	
	
	//all actions
	
	public void setSearchValue(String val)
	{
		searchBar.sendKeys(val);
	}
	
	public void clickSearch()
	{
		enter.click();
	}
	
	public String getLondonTime()
	{
		return lndn_time.getText();
	}
	
	public String getNewYorkTime()
	{
		return newYork_time.getText();
	}
}

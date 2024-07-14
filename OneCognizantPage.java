package PageData;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OneCognizantPage extends BasePage{

	
	public OneCognizantPage(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath="//div[@class='viewAllHotAppsBtn']")
	public WebElement viewAll;
	
	@FindBy(xpath="(//div[@class='a2zCharHolderDiv']/div//div)[position()<27]")
	public List<WebElement> aToz;
	
	@FindBy(xpath="//div[@class='row appStoreCharacRow']//div[@class='appStoreAppName']")
	public List<WebElement> alphaEles;
	
	
	
	
	
	
	
	
	
	
	public void clickViewAll()
	{
		//viewAll.click();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()", viewAll);
	}
	
	public String getClass(WebElement ele)
	{
		return ele.getAttribute("class");
	}
	
	public String getAppName(WebElement ele)
	{
		return ele.getText();
	}
	
	public boolean isElementVisible(WebElement ele)
	{
		return ele.isDisplayed();
	}
}

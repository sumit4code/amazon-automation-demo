package io.automation.amazon.page;

import static io.automation.amazon.Constant.HOME_PAGE_URL;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * {@code HomePage} represents Amazon home page which provides search box to search item.
 * This class provides few method l
 */
public class HomePage extends AbstractPage {

  private final By searchBox = By.id("twotabsearchtextbox");
  public HomePage(WebDriver webDriver, Properties properties) {
    super(webDriver, properties);
  }

  public HomePage open(){
    WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.of(30, ChronoUnit.SECONDS));
    webDriver.switchTo().parentFrame();
    this.webDriver.get(this.properties.getProperty(HOME_PAGE_URL));
    webDriverWait.until(webDriver1 -> ExpectedConditions.titleContains("Online Shopping site in India: Shop Online for Mobiles").apply(webDriver1));
    return this;
  }

  public HomePage verifyTitle(){
    String driverTitle = this.webDriver.getTitle();
    assertThat(driverTitle, containsString("Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in"));
    return this;
  }

  public HomePage verifySearchOption(){
    this.waitForElementToBeAppeared(searchBox, 10, ChronoUnit.SECONDS);
    WebElement element = this.webDriver.findElement(searchBox);
    assertThat(element, notNullValue());
    return this;
  }

  public HomePage searchItem(String itemName){
    this.waitForElementToBeAppeared(searchBox, 30, ChronoUnit.SECONDS);
    WebElement element = this.webDriver.findElement(searchBox);
    element.click();
    element.sendKeys(itemName);
    element.submit();
    return this;
  }




}

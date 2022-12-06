package io.automation.amazon;

import io.automation.amazon.exception.AutomationException;
import io.automation.amazon.page.CartPage;
import io.automation.amazon.page.HomePage;
import io.automation.amazon.page.SearchedResultPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BestSellerAutomationTest {

  private WebDriver webDriver;
  private Properties properties;
  private HomePage homePage;
  private SearchedResultPage searchedResultPage;

  @BeforeTest
  public void setUp() {

    this.properties = loadProperties();
    WebDriverManager.chromedriver().setup();
    webDriver = new ChromeDriver();
    webDriver.manage().window().maximize();
    webDriver.manage().timeouts().implicitlyWait(Duration.of(25, ChronoUnit.SECONDS));
  }

  @Test(priority = 0)
  public void navigateToHomePageTest() {
    homePage = new HomePage(webDriver, properties);
    homePage.open().verifyTitle().verifySearchOption();
  }

  @Test(priority = 1)
  public void searchAnItemTest(){
    homePage.searchItem("headphone");
    searchedResultPage = new SearchedResultPage(webDriver, properties);
  }

  @Test(priority = 2)
  public void verifySearchedResultTest(){
    searchedResultPage.verifySearchResult("headphone");
  }

  @Test(priority = 3)
  public void addSearchedProductMarkedWithBestSeller(){
    searchedResultPage.openProductTaggedWithBestSeller()
        .addToCartOneByOne();
  }


  @Test(priority = 4)
  public void verifyCart(){
    CartPage cartPage = new CartPage(webDriver, properties).navigateToCart();
    cartPage.assertCartItem(searchedResultPage.getProductsByBestSeller());
  }

  private Properties loadProperties() {
    try {
      InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("./application.properties");
      Properties properties = new Properties();
      properties.load(resourceAsStream);
      return properties;
    } catch (IOException e) {
      throw new AutomationException("unable to load application.properties", e);
    }
  }

  @AfterTest
  public void tearDown() {
    this.webDriver.close();
  }
}

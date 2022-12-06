package io.automation.amazon.page;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import io.automation.amazon.domain.Product;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Slf4j
public class SearchedResultPage extends AbstractPage {

  private final By searchedDataSummery = By.xpath("//*[@id=\"search\"]/span/div/h1/div/div[1]/div/div");
  private List<WebElement> elements;
  private List<Product> productsByBestSeller;

  private final By productListMarkedWithBestSeller = By.xpath("//span[.='Best seller' and @class='a-badge-text']/parent::span/../../../../../../../../../..//h2/a/span");

  public SearchedResultPage(WebDriver webDriver, Properties properties) {
    super(webDriver, properties);
  }

  public SearchedResultPage verifySearchResult(String searchTerm){
    this.waitForElementToBeAppeared(searchedDataSummery, 30, ChronoUnit.SECONDS);
    WebElement element = webDriver.findElement(searchedDataSummery);
    String searchedReportText = element.getText();
    assertThat(searchedReportText, containsString(searchTerm));
    return this;
  }

  public SearchedResultPage openProductTaggedWithBestSeller(){
    this.waitForElementToBeAppeared(productListMarkedWithBestSeller, 5, ChronoUnit.SECONDS);
    elements = this.webDriver.findElements(productListMarkedWithBestSeller);
    elements.forEach(WebElement::click);
    return this;
  }

  public SearchedResultPage addToCartOneByOne() {
    productsByBestSeller = new ArrayList<>();
    Set<String> windowHandles = webDriver.getWindowHandles();
    String currentWindow = webDriver.getWindowHandle();
    windowHandles.stream().filter(s -> !s.equalsIgnoreCase(currentWindow)).forEach(s -> {
      webDriver.switchTo().window(s);
      ProductPage productPage = new ProductPage(webDriver, properties);
      productPage.metaInformation().addToCart();
      productsByBestSeller.add(productPage.getProduct());
      webDriver.close();
    });
    log.info("Best seller products which has been added in cart are : {}", productsByBestSeller);
    webDriver.switchTo().window(currentWindow);
    webDriver.navigate().refresh();
    return this;
  }

  public List<Product> getProductsByBestSeller() {
    return productsByBestSeller;
  }
}

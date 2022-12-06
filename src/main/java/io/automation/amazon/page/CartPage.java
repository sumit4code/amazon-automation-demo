package io.automation.amazon.page;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.containsString;

import io.automation.amazon.domain.Product;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Slf4j
public class CartPage extends AbstractPage {

  private By cartLink = By.xpath("//*[@id=\"nav-cart\"]");
  private final By cartItem = By.className("sc-list-item-content");
  public CartPage(WebDriver webDriver, Properties properties) {
    super(webDriver, properties);
  }

  public CartPage navigateToCart(){
    this.waitForElementToBeAppeared(cartLink, 25, ChronoUnit.SECONDS);
    this.webDriver.findElement(cartLink).click();
    By cartPageConfirmation = By.xpath("//*[@id=\"sc-active-cart\"]/div/div[1]/div/h1");
    this.waitForElementToBeAppeared(cartPageConfirmation, 25, ChronoUnit.SECONDS);
    String shoppingCartText = this.webDriver.findElement(cartPageConfirmation).getText();
    assertThat(shoppingCartText, is("Shopping Cart"));
    return this;
  }

  public CartPage assertCartItem(List<Product> productList){
    Set<String> titleSet = productList.stream().map(Product::getTitle).collect(Collectors.toSet());
    List<WebElement> elements = this.webDriver.findElements(cartItem);
   // assertThat(productList.size(), is(elements.size()));
    AtomicInteger atomicInteger = new AtomicInteger(0);
    elements.forEach(webElement -> {
      String truncatedText = webElement.findElement(By.className("a-truncate-cut")).getText().replace("...", "");
      // verifying each item added in cart has name matched from list with bestseller
      assertThat(titleSet, everyItem(containsString(truncatedText)));
      //checking each item has added in cart has bestseller tag
      assertThat(webElement.findElement(By.className("sc-best-seller-badge-redesign")).isDisplayed(), is(true));
      atomicInteger.addAndGet(Integer.parseInt(webElement.findElement(By.className("a-dropdown-prompt")).getText()));
      log.info(truncatedText);
    });
    //asserting cart item count with product list from search page
    assertThat(atomicInteger.get(), is(productList.size()));
    return this;
  }
}

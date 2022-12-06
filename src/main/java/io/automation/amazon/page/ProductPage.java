package io.automation.amazon.page;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import io.automation.amazon.domain.Product;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage extends AbstractPage {
  private Product product;
  private final By confirmationWindow = By.xpath("//*[@id=\"attachDisplayAddBaseAlert\"]/span");
  public ProductPage(WebDriver webDriver, Properties properties) {
    super(webDriver, properties);
  }

  public ProductPage metaInformation(){
    String productTitle = this.webDriver.findElement(By.id("titleSection")).getText();
    String model = this.webDriver.findElement(By.xpath("//*[@id=\"productOverview_feature_div\"]/div/table/tbody/tr[2]/td[2]/span")).getText();
    String brand = this.webDriver.findElement(By.xpath("//*[@id=\"productOverview_feature_div\"]/div/table/tbody/tr[1]/td[2]/span")).getText();
    String color = this.webDriver.findElement(By.xpath("//*[@id=\"productOverview_feature_div\"]/div/table/tbody/tr[3]/td[2]/span")).getText();
    this.product = Product.builder().brand(brand).title(productTitle).color(color).model(model).build();
    return this;
  }

  public ProductPage addToCart(){
    webDriver.findElement(By.id("add-to-cart-button")).click();
    this.waitForElementToBeAppeared(confirmationWindow, 50, ChronoUnit.SECONDS);
    WebElement element = this.webDriver.findElement(confirmationWindow);
    assertThat(element.getText(), is("Added to Cart"));
    return this;
  }

  public Product getProduct(){
    return this.product;
  }


}

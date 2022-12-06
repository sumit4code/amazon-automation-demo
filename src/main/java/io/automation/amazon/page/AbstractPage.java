package io.automation.amazon.page;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class AbstractPage {
  protected final WebDriver webDriver;
  protected final Properties properties;

  public AbstractPage(WebDriver webDriver, Properties properties) {
    this.webDriver = webDriver;
    this.properties = properties;
  }

  protected void waitForElementToBeAppeared(By element, long amount, TemporalUnit temporalUnit) {
    WebDriverWait wait = new WebDriverWait(webDriver, Duration.of(amount, temporalUnit));
    wait.until(ExpectedConditions.visibilityOfElementLocated(element));
  }
}

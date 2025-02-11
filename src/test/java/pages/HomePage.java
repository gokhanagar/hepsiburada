package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.verifyElementDisplayed;
import static utility.BrowserUtils.waitForDOMStability;
import static utility.BrowserUtils.waitForElementInteractable;
import static utility.BrowserUtils.waitForPageToLoad;

public class HomePage {

    private final By selectedPopularProductText = By.xpath("(//h3[@data-test-id='Recommendation-title'])[1]");
    private final By searchBar = By.cssSelector("input[type='search']");
    private final By cookieButton = By.cssSelector("button[id='onetrust-accept-btn-handler']");

    public HomePage assertHomePage() {
        try {
            // Sayfa yüklenene kadar bekle
            waitForPageToLoad(20);
            
            // DOM stabilitesi için bekle
            waitForDOMStability(5);
            
            // Explicit wait ile element görünür olana kadar bekle
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOfElementLocated(selectedPopularProductText));
            
            // Element viewport'ta görünür olana kadar scroll
            WebElement element = driver.findElement(selectedPopularProductText);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            
            // Element interaktif olana kadar bekle
            waitForElementInteractable(selectedPopularProductText);
            
            verifyElementDisplayed(selectedPopularProductText);
            return this;
        } catch (Exception e) {
            throw new RuntimeException("Failed to assert home page: " + e.getMessage());
        }
    }

    // Örnek parametre : "balata"

    public HomePage searchForProduct(String keyword) {
        try {
            waitForPageToLoad(10);
            waitForDOMStability(10);

            verifyElementDisplayed(searchBar);

            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(searchBar))
                    .click()
                    .pause(Duration.ofSeconds(1))
                    .sendKeys(keyword)
                    .pause(Duration.ofSeconds(1))
                    .sendKeys(Keys.ENTER)
                    .perform();

            waitForDOMStability(2);

            return this;
        } catch (Exception e) {
            throw new RuntimeException("Search operation failed: " + e.getMessage());
        }
    }


}



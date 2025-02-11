package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.*;

public class HomePage {

    private final By selectedPopularProductText = By.xpath("(//h3[@data-test-id='Recommendation-title'])[1]");
    private final By searchBar = By.cssSelector("input[type='search']");
    private final By cookieButton = By.cssSelector("button[id='onetrust-accept-btn-handler']");

    public HomePage assertHomePage() {

            //driver.findElement(cookieButton).click();
            System.out.println("Waiting for page to load...");
            waitForPageToLoad(20);
            
            System.out.println("Checking for security page...");
            if (driver.getCurrentUrl().contains("security")) {
                System.out.println("Security page detected, retrying...");
                driver.navigate().to("https://www.hepsiburada.com");
                waitForPageToLoad(20);
            }
            
            System.out.println("Waiting for DOM stability...");
            waitForDOMStability(5);
            
            System.out.println("Checking for popular product text...");
            if (!isElementPresent(selectedPopularProductText)) {
                System.out.println("Popular product text not found, refreshing page...");
                driver.navigate().refresh();
                waitForPageToLoad(20);
                waitForDOMStability(5);
            }
            
            System.out.println("Verifying element display...");
            verifyElementDisplayed(selectedPopularProductText);
            
            return this;

    }

    // Örnek parametre : "balata"

    public HomePage searchForProduct(String keyword) {

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

    }

}



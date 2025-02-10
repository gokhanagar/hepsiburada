package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.*;

public class HomePage {

    private final By selectedPopularProductText = By.xpath("(//h3[@data-test-id='Recommendation-title'])[1]");
    private final By searchBar = By.cssSelector("input[type='search']");
    private final By cookieButton = By.cssSelector("button[id='onetrust-accept-btn-handler']");

    public HomePage assertHomePage() {
        try {
            clickAndWaitForReload(cookieButton);
            waitForDOMStability(2);

            verifyElementDisplayed(selectedPopularProductText);
            return this;
        } catch (Exception e) {
            throw new RuntimeException("Failed to assert home page: " + e.getMessage());
        }
    }

    public HomePage searchForProduct() {
        try {

            waitForPageToLoad(10);
            waitForDOMStability(10);
            
            verifyElementDisplayed(searchBar);


            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(searchBar))
                   .click()
                   .pause(Duration.ofSeconds(1))
                   .sendKeys("balata")
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



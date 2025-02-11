package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.verifyElementDisplayed;
import static utility.BrowserUtils.waitForDOMStability;
import static utility.BrowserUtils.waitForPageToLoad;

public class HomePage {

    private final By selectedPopularProductText = By.xpath("(//h3[@data-test-id='Recommendation-title'])[1]");
    private final By searchBar = By.cssSelector("input[type='search']");
    private final By cookieButton = By.cssSelector("button[id='onetrust-accept-btn-handler']");

    // Belki duration dışarıdan parametre olarak verilebilir.
    public HomePage assertHomePage() {
        try {
            waitForDOMStability(2);
            /*
            // Cookie butonu hala görünüyorsa tıkla
            if (isElementDisplayed(cookieButton)) {
                try {
                    clickAndWaitForReload(cookieButton);
                } catch (Exception e) {
                    System.out.println("Cookie button click failed, continuing...");
                }
            }

             */

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



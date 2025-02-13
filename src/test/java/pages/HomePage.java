package pages;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import enums.Links;
import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.verifyElementDisplayed;
import static utility.BrowserUtils.waitForDOMStability;

public class HomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    private final By selectedPopularProductText = By.xpath("(//h3[@data-test-id='Recommendation-title'])[1]");
    private final By searchBar = By.cssSelector("input[type='search']");
    private final By cookieButton = By.cssSelector("button[id='onetrust-accept-btn-handler']");

    public By getSelectedPopularProductText() {return selectedPopularProductText;}

    public HomePage acceptCookie(){
        try {
            logger.info("Attempting to accept cookies...");
            driver.findElement(cookieButton).click();
            waitForDOMStability(10);
            logger.info("Successfully accepted cookies");
        } catch (Exception e) {
            logger.error("Failed to accept cookies: {}", e.getMessage());
        }
        return this;
    }


    public HomePage checkAndHandleSecurityRedirect() {

            if (driver.getCurrentUrl().contains("security")) {
                driver.get(Links.BASEURL.getLink());}

            waitForDOMStability(20);
            return this;
    }

    public HomePage searchForProduct(String keyword) {

            verifyElementDisplayed(searchBar);

            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(searchBar))
                    .click()
                    .pause(Duration.ofSeconds(1))
                    .sendKeys(keyword)
                    .pause(Duration.ofSeconds(1))
                    .sendKeys(Keys.ENTER)
                    .perform();

            waitForDOMStability(20);
            return this;
    }

}
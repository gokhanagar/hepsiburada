package pages.HomePage;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.BasePage;
import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.isElementDisplayed;
import static utility.BrowserUtils.waitForDOMStability;
import static utility.BrowserUtils.waitForVisibility;
import utility.ConfigReader;

public class HomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    private final By selectedPopularProductText = By.xpath("(//div[@data-test-id='Recommendation']//h3[@data-test-id='Recommendation-title'])[1]");
    private final By cookieButton = By.cssSelector("button[id='onetrust-accept-btn-handler']");

    public By getSelectedPopularProductText() {return selectedPopularProductText;}




    public HomePage acceptCookie() {
        try {
            logger.info("Waiting for cookie button...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(cookieButton)).click();
            logger.info("Successfully accepted cookies");
        } catch (TimeoutException e) {
            logger.warn("Cookie button not found or not clickable, continuing...");
        } catch (Exception e) {
            logger.error("Failed to accept cookies: {}", e.getMessage());
        }
        return this;
    }

    public HomePage checkAndHandleSecurityRedirect() {
        try {
            if (driver.getCurrentUrl().contains("security")) {
                String baseUrl = ConfigReader.get("base.url");
                driver.get(baseUrl);
            }

            waitForDOMStability(20);
            return this;
        } catch (Exception e) {
            logger.error("Error handling security redirect: {}", e.getMessage());
            throw e;
        }
    }




}
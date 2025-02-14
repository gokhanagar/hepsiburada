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

    private final By mainHeader = By.cssSelector("div[data-test-id='main-header']");
    private final By cookieButton = By.id("onetrust-accept-btn-handler");
    private final By searchBox = By.cssSelector("input[type='text']");

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
            
            waitForVisibility(mainHeader, 30);
            
            return this;
        } catch (Exception e) {
            logger.error("Error handling security redirect: {}", e.getMessage());
            throw e;
        }
    }

    public boolean isHomePageDisplayed() {
        try {

            return isElementDisplayed(mainHeader);
        } catch (Exception e) {
            logger.error("Error verifying home page: {}", e.getMessage());
            return false;
        }
    }


}
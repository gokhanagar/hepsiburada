package pages.HomePage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import pages.BasePage;
import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.waitForDOMStability;
import utility.ConfigReader;

public class HomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    private final By selectedPopularProductText = By.xpath("(//div[@data-test-id='Recommendation']//h3[@data-test-id='Recommendation-title'])[1]");
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
                String baseUrl = ConfigReader.get("base.url");
                driver.get(baseUrl);}

            waitForDOMStability(30);
            return this;
    }


}
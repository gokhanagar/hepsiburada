package pages.HomePage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import pages.BasePage;
import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.click;
import static utility.BrowserUtils.waitForDOMStability;
import utility.ConfigReader;

public class HomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    private final By selectedPopularProductText = By.xpath("(//h3[@data-test-id='Recommendation-title'])[1]");
    private final By cookieButton = By.cssSelector("button[id='onetrust-accept-btn-handler']");

    public By getSelectedPopularProductText() {return selectedPopularProductText;}

    public HomePage acceptCookie() {
        try {
            click(cookieButton);
            waitForDOMStability(10);
        } catch (Exception e) {
            logger.error("Error in cookie handling: {}", e.getMessage());
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
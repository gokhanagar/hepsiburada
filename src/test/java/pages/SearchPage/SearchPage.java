package pages.SearchPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;


import java.time.Duration;

import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.waitForDOMStability;

public class SearchPage {
    private static final Logger logger = LogManager.getLogger(SearchPage.class);
    private final By searchBar = By.cssSelector("input[type='search']");

    public void searchForProduct(String keyword) {

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(searchBar))
                .click()
                .pause(Duration.ofSeconds(1))
                .sendKeys(keyword)
                .pause(Duration.ofSeconds(1))
                .sendKeys(Keys.ENTER)
                .perform();

        waitForDOMStability(20);

    }

}
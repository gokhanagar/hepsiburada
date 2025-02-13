package stepDefs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utility.Driver;

public class Hooks {
    private static final Logger logger = LogManager.getLogger(Hooks.class);
    public static WebDriver driver;
    
    @Before
    public void setUp() {
        logger.info("Starting new test scenario");
        driver = Driver.getDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                logger.warn("Scenario failed, taking screenshot");
                final byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
            }
        } catch (Exception e) {
            logger.error("Error in teardown: {}", e.getMessage());
        } finally {
            logger.info("Closing driver");
            Driver.closeDriver();
            driver = null;
        }
    }
}
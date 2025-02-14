package utility;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static stepDefs.Hooks.driver;

public class BrowserUtils {

    private static final Logger logger = LogManager.getLogger(BrowserUtils.class);
    private static final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    public static WebElement waitForVisibility(By locator, int timeout) {
        try {
            logger.debug("Waiting for visibility of element: {}", locator);
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not visible: {} - {}", locator, e.getMessage());
            throw e;
        }
    }

    public static void clickWithJS(By locator) {
        try {
            logger.debug("Attempting to click element with JavaScript: {}", locator);
            WebElement element = waitForClickability(locator, 10);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            js.executeScript("arguments[0].click();", element);
            logger.debug("Successfully clicked element with JavaScript: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to click element with JavaScript: {} - {}", locator, e.getMessage());
            throw e;
        }
    }

    public static void click(By locator) {
        try {
            WebElement element = waitForClickability(locator, 10);
            element.click();
            logger.debug("Successfully clicked element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to click element: {} - {}", locator, e.getMessage());
            throw e;
        }
    }


    public static String getElementText(By locator) {
        try {
            String text = waitForVisibility(locator, 10).getText();
            logger.debug("Got text from element: {} - Text: {}", locator, text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get element text: {} - {}", locator, e.getMessage());
            throw new RuntimeException("Failed to get element text: " + e.getMessage());
        }
    }

    public static void verifyElementDisplayed(By locator) {
        try {
            logger.debug("Verifying element is displayed: {}", locator);
            Assert.assertTrue(driver.findElement(locator).isDisplayed());
            logger.debug("Element is displayed: {}", locator);
        } catch (Exception e) {
            logger.error("Element not displayed: {} - {}", locator, e.getMessage());
            throw e;
        }
    }

    public static boolean isElementDisplayed(By locator) {
        try {
            boolean isDisplayed = driver.findElement(locator).isDisplayed();
            logger.debug("Element display status: {} - {}", locator, isDisplayed);
            return isDisplayed;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            logger.debug("Element not found or stale: {}", locator);
            return false;
        }
    }

    public static void clickAndSwitchToNewTab(List<WebElement> elements, int nextNumber) {
        try {
            logger.debug("Attempting to click and switch to new tab. Element index: {}", nextNumber);
            String originalWindow = driver.getWindowHandle();
            int originalWindowCount = driver.getWindowHandles().size();

            WebElement element = elements.get(nextNumber);


            String productName = "";
            try {
                productName = element.getText();
                if (productName.isEmpty()) {
                    productName = element.getAttribute("title");
                }
            } catch (Exception e) {
                productName = "Unknown Product";
                logger.warn("Could not get product name: {}", e.getMessage());
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            waitForDOMStability(2);

            try {
                element.click();
                logger.info("Clicked on product: {}", productName);
            } catch (Exception e) {
                logger.warn("Normal click failed for product: {}, trying JavaScript click", productName);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            }

            try {
                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.numberOfWindowsToBe(originalWindowCount + 1));

                Set<String> allWindows = driver.getWindowHandles();
                for (String window : allWindows) {
                    if (!originalWindow.contentEquals(window)) {
                        driver.switchTo().window(window);
                        logger.debug("Successfully switched to new window for product: {}", productName);
                        return;
                    }
                }
            } catch (Exception e) {
                logger.error("Failed to switch window for product: {}, Error: {}", productName, e.getMessage());
                throw e;
            }

        } catch (Exception e) {
            logger.error("Failed to click and switch to new tab: {}", e.getMessage());
            throw e;
        }
    }

    public static void waitForDOMStability(int timeoutSeconds) {
        try {
            logger.debug("Waiting for DOM stability for {} seconds", timeoutSeconds);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(driver -> {
                try {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    return (Boolean) js.executeScript(
                            "return new Promise(resolve => {" +
                                    "  let mutations = 0;" +
                                    "  const observer = new MutationObserver(() => mutations++);" +
                                    "  observer.observe(document, { " +
                                    "    childList: true," +
                                    "    subtree: true," +
                                    "    attributes: true" +
                                    "  });" +
                                    "  setTimeout(() => {" +
                                    "    observer.disconnect();" +
                                    "    resolve(mutations < 5);" +
                                    "  }, 500);" +
                                    "});"
                    );
                } catch (Exception e) {
                    logger.error("Error in DOM stability check: {}", e.getMessage());
                    return false;
                }
            });
        } catch (Exception e) {
            logger.error("Failed waiting for DOM stability: {}", e.getMessage());
        }
    }


    public static void sendKeysToElement(By locator, String text) {
        try {
            logger.debug("Sending keys to element: {} - Text: {}", locator, text);
            WebElement element = waitForVisibility(locator, 10);
            element.clear();
            element.sendKeys(text);
            logger.debug("Successfully sent keys to element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to send keys to element: {} - {}", locator, e.getMessage());
            throw new RuntimeException("Failed to send keys to element: " + e.getMessage());
        }
    }


    public static WebElement waitForClickability(By locator, int timeout) {
        try {
            logger.debug("Waiting for element to be clickable: {}", locator);
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            logger.error("Element not clickable: {} - {}", locator, e.getMessage());
            throw e;
        }
    }



}
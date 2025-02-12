package utility;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static stepDefs.Hooks.driver;

public class BrowserUtils {

    // Temel wait mekanizması
    private static final WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));

    public static WebElement waitForVisibility(By locator, int timeout) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickability(By locator, int timeout) {
        return new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void clickWithJS(By locator) {
        try {
            WebElement element = waitForVisibility(locator, 10);
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click element with JavaScript: " + e.getMessage());
        }
    }

    public static void click(By locator){
        WebElement element = waitForVisibility(locator, 10);
        element.click();
    }

    public static void sendKeysToElement(By locator, String text) {
        try {
            WebElement element = waitForVisibility(locator, 10);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send keys to element: " + e.getMessage());
        }
    }


    public static String getElementText(By locator) {
        try {
            return waitForVisibility(locator, 10).getText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get element text: " + e.getMessage());
        }
    }

    public static void verifyElementDisplayed(By locator) {
        Assert.assertTrue( driver.findElement(locator).isDisplayed());
    }
    public static boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }



    public static void verifyTitle(String expectedTitle){
        Assert.assertEquals(expectedTitle,driver.getTitle());
    }

    public static void verifyTitleContains( String expectedInTitle){
        Assert.assertTrue(driver.getTitle().contains(expectedInTitle));
    }


    public static void clickRadioButton(List<WebElement> radioButtons, String attributeValue){
        for (WebElement each : radioButtons) {
            if(each.getAttribute("value").equalsIgnoreCase(attributeValue)){
                each.click();
            }
        }
    }


    public static void verifyURLContains(String expectedInURL){
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains(expectedInURL));
    }

    public static void clickAndSwitchToNewTab(List<WebElement> elements, int nextNumber) {
        String originalWindow = driver.getWindowHandle();
        int originalWindowCount = driver.getWindowHandles().size();
        elements.get(nextNumber).click();

        wait.until(ExpectedConditions.numberOfWindowsToBe(originalWindowCount + 1));

        Set<String> allWindows = driver.getWindowHandles();

        for (String window : allWindows) {
            if (!originalWindow.contentEquals(window)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }

    public static List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            elemTexts.add(el.getText());
        }
        return elemTexts;
    }

    public static List<String> getElementsText(By locator) {

        List<WebElement> elems = Driver.getDriver().findElements(locator);
        List<String> elemTexts = new ArrayList<>();

        for (WebElement el : elems) {
            elemTexts.add(el.getText());
        }
        return elemTexts;
    }



    public static void doubleClick(WebElement element) {
        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
    }


    public static void clickAndWaitForReload(By locator) {
        try {
            WebElement element = waitForVisibility(locator, 10);
            
            // Tıklama işlemi
            element.click();
            
            // Sayfanın yüklenmesini bekle
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));
            
        } catch (Exception e) {
            // JavaScript ile tıklamayı dene
            try {
                WebElement element = driver.findElement(locator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                
                // Sayfanın yüklenmesini bekle
                new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
                        
            } catch (Exception ex) {
                throw new RuntimeException("Failed to click and wait for reload: " + ex.getMessage());
            }
        }
    }

    public static void waitForElementInteractable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Element görünür ve tıklanabilir olana kadar bekle
        wait.until(ExpectedConditions.and(
            ExpectedConditions.visibilityOfElementLocated(locator),
            ExpectedConditions.elementToBeClickable(locator)
        ));
        
        // Element viewport içinde mi kontrol et
        wait.until(driver -> (Boolean) ((JavascriptExecutor) driver)
                .executeScript("var rect = arguments[0].getBoundingClientRect(); " +
                        "return (rect.top >= 0 && rect.left >= 0 && " +
                        "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                        "rect.right <= (window.innerWidth || document.documentElement.clientWidth));", driver.findElement(locator)));
    }

    public static void waitForDOMStability(int timeoutSeconds) {
        try {
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
                    return false;
                }
            });
        } catch (Exception e) {

        }
    }

}

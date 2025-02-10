package utility;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    public static void hover(By locator) {
        try {
            WebElement element = waitForVisibility(locator, 10);
            Actions actions = new Actions(Driver.getDriver());
            actions.moveToElement(element).perform();
        } catch (Exception e) {
            throw new RuntimeException("Failed to hover over element: " + e.getMessage());
        }
    }

    public static void scrollToElement(By locator) {
        try {
            WebElement element = waitForVisibility(locator, 10);
            ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            throw new RuntimeException("Failed to scroll to element: " + e.getMessage());
        }
    }

    public static boolean isElementDisplayed(By locator) {
        try {
            return waitForVisibility(locator, 5).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static void waitForElementToBeClickableAndClick(By locator) {
        try {
            WebElement element = waitForClickability(locator, 10);
            element.click();
        } catch (Exception e) {
            throw new RuntimeException("Element not clickable: " + e.getMessage());
        }
    }

    public static String getElementText(By locator) {
        try {
            return waitForVisibility(locator, 10).getText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get element text: " + e.getMessage());
        }
    }

    // Sayfa yüklenmesini bekleyen metodlar
    public static void waitForPageToLoad(int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            throw new RuntimeException("Page load timeout: " + e.getMessage());
        }
    }

    // Yardımcı metodlar
    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void verifyElementDisplayed(By locator) {
        try {
            Assert.assertTrue("Element not visible: " + locator, 
                    isElementDisplayed(locator));
        } catch (Exception e) {
            Assert.fail("Element not found: " + locator);
        }
    }

    public static void verifyElementNotDisplayed(By locator) {
        try {
            Assert.assertFalse("Element should not be visible: " + locator, 
                    isElementDisplayed(locator));
        } catch (Exception e) {
            // Element zaten görünmüyorsa başarılı
        }
    }

    public static void switchWindowAndVerify(String expectedInURL, String expectedInTitle){


        Set<String> allWindowHandles = Driver.getDriver().getWindowHandles();

        for (String each : allWindowHandles) {

            Driver.getDriver().switchTo().window(each);
            System.out.println("Current URL: " + Driver.getDriver().getCurrentUrl());

            if (Driver.getDriver().getCurrentUrl().contains(expectedInURL )){
                break;
            }
        }


        String actualTitle = Driver.getDriver().getTitle();
        Assert.assertTrue(actualTitle.contains(expectedInTitle));
    }

    public static void verifyTitle(String expectedTitle){
        Assert.assertEquals(expectedTitle,Driver.getDriver().getTitle());
    }

    public static void verifyTitleContains( String expectedInTitle){
        Assert.assertTrue(Driver.getDriver().getTitle().contains(expectedInTitle));
    }

    public static void waitForTitleContains(String title){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains(title));
    }

    public static List<String> dropdownOptions_as_STRING(WebElement dropdownElement){

        Select month = new Select(dropdownElement);
        List<WebElement> actualMonth_as_WEBELEMENT = month.getOptions();

        List<String> actualMonth_as_STRING = new ArrayList<>();

        for (WebElement each : actualMonth_as_WEBELEMENT) {
            actualMonth_as_STRING.add(each.getText());
        }
        return actualMonth_as_STRING;
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

    public static void clickAndSwitchToNewTab(List<WebElement> elements, int n) {
        String originalWindow = driver.getWindowHandle();
        int originalWindowCount = driver.getWindowHandles().size();
        elements.get(n).click();

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

    public static void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", 
            element
        );
        
        wait.until(driver -> {
            Rectangle rect = element.getRect();
            return (Boolean) ((JavascriptExecutor) driver).executeScript(
                "var rect = arguments[0];" +
                "return (rect.top >= 0 && rect.left >= 0 && " +
                "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                "rect.right <= (window.innerWidth || document.documentElement.clientWidth));",
                rect
            );
        });
    }

    public static void doubleClick(WebElement element) {
        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
    }

    public static void highlight(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
        waitFor(1);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].removeAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }

    public static void selectCheckBox(WebElement element, boolean check) {
        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            if (element.isSelected()) {
                element.click();
            }
        }
    }


    public static void executeJScommand(WebElement element, String command) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        jse.executeScript(command, element);

    }

       public static void executeJScommand(String command) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        jse.executeScript(command);

    }


    public static void waitForPresenceOfElement(By by, long time) {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(time)).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static void waitForURLContains(String expectedURL){

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains(expectedURL));

    }

    private static final Wait<WebDriver> fluentWait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(10))
            .pollingEvery(Duration.ofMillis(500))
            .ignoring(StaleElementReferenceException.class);

    public static void waitForJQueryLoad() {
        fluentWait.until(driver -> {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js.executeScript("return jQuery.active == 0");
        });
    }

    public static void waitForPageReadyState() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public static WebElement waitForElementToBeRefreshed(WebElement element, By locator) {
        return fluentWait.until(driver -> driver.findElement(locator));
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

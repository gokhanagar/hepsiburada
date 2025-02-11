package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.verifyElementDisplayed;
import static utility.BrowserUtils.waitForDOMStability;
import static utility.BrowserUtils.waitForPageToLoad;

public class HomePage {

    private final By selectedPopularProductText = By.xpath("(//h3[@data-test-id='Recommendation-title'])[1]");
    private final By searchBar = By.cssSelector("input[type='search']");
    private final By cookieButton = By.cssSelector("button[id='onetrust-accept-btn-handler']");
    private final By mainContent = By.cssSelector("main"); // Ana içerik container'ı

    public HomePage assertHomePage() {
        try {
            System.out.println("Waiting for page to load...");
            waitForPageToLoad(20);
            
            System.out.println("Waiting for DOM stability...");
            waitForDOMStability(5);
            
            // Ana içeriğin yüklenmesini bekle
            System.out.println("Waiting for main content...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfElementLocated(mainContent));
            
            // JavaScript ile sayfanın hazır olduğunu kontrol et
            System.out.println("Checking page readiness...");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
            
            // Sayfayı en üste scroll yap
            System.out.println("Scrolling to top...");
            js.executeScript("window.scrollTo(0, 0)");
            Thread.sleep(1000); // Scroll sonrası kısa bekleme
            
            // Element var mı kontrol et
            System.out.println("Checking for popular product text...");
            if (!isElementPresent(selectedPopularProductText)) {
                System.out.println("Popular product text not found, refreshing page...");
                driver.navigate().refresh();
                waitForPageToLoad(20);
                waitForDOMStability(5);
            }
            
            System.out.println("Verifying element display...");
            verifyElementDisplayed(selectedPopularProductText);
            
            return this;
        } catch (Exception e) {
            System.out.println("Error in assertHomePage: " + e.getMessage());
            // Hata durumunda screenshot al
            takeScreenshot("homepage_error");
            throw new RuntimeException("Failed to assert home page: " + e.getMessage());
        }
    }

    // Yardımcı metod
    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void takeScreenshot(String name) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String screenshot = (String) js.executeScript(
                "return document.documentElement.outerHTML"
            );
            System.out.println("Page Source at error:");
            System.out.println(screenshot);
        } catch (Exception e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }
    }

    // Örnek parametre : "balata"

    public HomePage searchForProduct(String keyword) {
        try {
            waitForPageToLoad(10);
            waitForDOMStability(10);

            verifyElementDisplayed(searchBar);

            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(searchBar))
                    .click()
                    .pause(Duration.ofSeconds(1))
                    .sendKeys(keyword)
                    .pause(Duration.ofSeconds(1))
                    .sendKeys(Keys.ENTER)
                    .perform();

            waitForDOMStability(2);

            return this;
        } catch (Exception e) {
            throw new RuntimeException("Search operation failed: " + e.getMessage());
        }
    }

}



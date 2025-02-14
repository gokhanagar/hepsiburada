package stepDefs.HomePageSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;
import utility.BrowserUtils;
import utility.ConfigReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.*;
import utility.ConfigReader;

public class HomePageSteps extends BasePage {

    @Given("user navigate to hepsiburada.com")
    public void userNavigateToHepsiburadaCom() {
        driver.get(ConfigReader.get("base.url"));
        waitForPageToLoad();
        waitForDOMStability(10);
    }

    @Then("user verify home page is displayed")
    public void userVerifyHomePageIsDisplayed() {

        verifyElementDisplayed(homePage().getSelectedPopularProductText());
    }

    @And("user accepts cookies")
    public void userAcceptsCookies() {
        //homePage().acceptCookie();
        //homePage().checkAndHandleSecurityRedirect();
    }
}

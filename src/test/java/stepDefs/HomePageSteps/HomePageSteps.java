package stepDefs.HomePageSteps;

import org.junit.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.BasePage;
import static stepDefs.Hooks.driver;
import utility.ConfigReader;

public class HomePageSteps extends BasePage {

    @Given("user navigate to hepsiburada.com")
    public void userNavigateToHepsiburadaCom() {driver.get(ConfigReader.get("base.url"));}

    @Then("user verify home page is displayed")
    public void userVerifyHomePageIsDisplayed() {
        homePage()
            .checkAndHandleSecurityRedirect();
        
        Assert.assertTrue("Home page is not displayed", homePage().isHomePageDisplayed());
    }

    @And("user accepts cookies")
    public void userAcceptsCookies() {
        homePage().acceptCookie();
    }
}

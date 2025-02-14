package stepDefs.HomePageSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.BasePage;
import utility.ConfigReader;

import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.verifyElementDisplayed;

public class HomePageSteps extends BasePage {

    @Given("user navigate to hepsiburada.com")
    public void userNavigateToHepsiburadaCom() {driver.get(ConfigReader.get("base.url"));}

    @Then("user verify home page is displayed")
    public void userVerifyHomePageIsDisplayed() {
        verifyElementDisplayed(homePage().getSelectedPopularProductText());
    }

    @And("user accepts cookies")
    public void userAcceptsCookies() {
        homePage()
                .checkAndHandleSecurityRedirect()
                .acceptCookie();
    }
}

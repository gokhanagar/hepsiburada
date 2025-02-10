package stepDefs.UI_StepDefs;

import enums.LINKS;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import pages.BasePage;

import static stepDefs.Hooks.driver;

public class ProductReview extends BasePage {
    @Given("user navigate to hepsiburada.com")
    public void userNavigateToHepsiburadaCom() {
        driver.get(LINKS.BASEURL.getLink());
    }

    @When("user search for a product which is popular and has comments")
    public void userSearchForAProductWhichIsPopularAndHasComments() throws InterruptedException {
        homePage().assertHomePage().searchForProduct();

    }

    @And("select a random product from the search results and navigate to the product details page")
    public void selectARandomProductFromTheSearchResultsAndNavigateToTheProductDetailsPage() {
        searchResultsPage().searchResultTextAssertion().selectARandomProduct();
    }

    @And("wait for the page to load completely")
    public void waitForThePageToLoadCompletely() {
    }

    @And("switch to the Değerlendirmeler tab")
    public void switchToTheDeğerlendirmelerTab() {
    }

    @And("sort the reviews by En Yeni Değerlendirme")
    public void sortTheReviewsByEnYeniDeğerlendirme() {
    }

    @When("select a review and click on thumbsUp or thumbsDown")
    public void selectAReviewAndClickOnThumbsUpOrThumbsDown() {
    }

    @Then("the Thank You message should be displayed")
    public void theThankYouMessageShouldBeDisplayed() {
    }
}

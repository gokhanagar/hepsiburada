package stepDefs.UI_StepDefs;

import org.junit.Assume;

import enums.LINKS;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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
    public void selectARandomProductFromTheSearchResultsAndNavigateToTheProductDetailsPage() throws InterruptedException {
        searchResultsPage().searchResultTextAssertion().selectARandomProduct();
    }

    @And("switch to the Degerlendirmeler tab")
    public void switchToTheDegerlendirmelerTab() {
        productDetailPage().switchToDegerlendirmelerTab();
    }

    @And("sort the reviews by En Yeni Degerlendirme")
    public void sortTheReviewsByEnYeniDegerlendirme() throws InterruptedException {
        if (!productDetailPage().hasReviews()) return;
        System.out.println("bu asamaya gecti");
        productReviewsPage().orderCommentsFromBestToWorst();

    }

    @When("select a review and click on thumbsUp or thumbsDown")
    public void selectAReviewAndClickOnThumbsUpOrThumbsDown() {
        if (!productDetailPage().hasReviews()) return;
        productReviewsPage().clickThumpsUpForReview();
    }

    @Then("the Thank You message should be displayed")
    public void theThankYouMessageShouldBeDisplayed() throws InterruptedException {
        if (!productDetailPage().hasReviews()) return;
        productReviewsPage().assertionResultForReview();
        Thread.sleep(2000);
    }
}

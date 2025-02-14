package stepDefs.ProductReviewsSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.BasePage;

import static utility.BrowserUtils.getElementText;

public class ProductReviewSteps extends BasePage {

    @And("sort the reviews by The Best Reviews")
    public void sortTheReviewsByTheBestReviews() {
        if (!productDetailPage().hasReviews()) return;
        productReviewsPage().sortTheReviewsByTheBestReviews();
    }

    @When("select a review and click on thumbsUp or thumbsDown")
    public void selectAReviewAndClickOnThumbsUpOrThumbsDown() {
        if (!productDetailPage().hasReviews()) return;
        productReviewsPage().clickThumbsUpForReview();
    }

    @Then("{string} message should be displayed")
    public void messageShouldBeDisplayed(String expectedMessage) {
        if (!productDetailPage().hasReviews()) return;
        String actualResult = getElementText(productReviewsPage().getThankYouText());
        Assert.assertEquals(expectedMessage, actualResult);
    }

}
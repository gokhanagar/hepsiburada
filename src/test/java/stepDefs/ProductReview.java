package stepDefs;

import org.junit.Assert;

import enums.Links;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.BasePage;
import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.getElementText;
import static utility.BrowserUtils.verifyElementDisplayed;

@Severity(SeverityLevel.NORMAL)
public class ProductReview extends BasePage {

    @Given("user navigate to hepsiburada.com")
    public void userNavigateToHepsiburadaCom() {
        driver.get(Links.BASEURL.getLink());
    }


    @When("user search for {string} which is popular and has comments")
    public void userSearchForWhichIsPopularAndHasComments(String keyword) {
        verifyElementDisplayed(homePage().getSelectedPopularProductText());
        homePage()
                .checkAndHandleSecurityRedirect()
                .acceptCookie()
                .searchForProduct(keyword);

    }

    @And("select a random {string} from the search results and navigate to the product details page")
    public void selectARandomWhichFromTheSearchResultsAndNavigateToTheProductDetailsPage(String expectedMessage){
        String actualMessage = getElementText(searchResultsPage().searchResultText());
        Assert.assertEquals(expectedMessage, actualMessage);

        searchResultsPage().selectARandomProduct();
    }

    @And("switch to the Reviews tab")
    public void switchTotheReviewstab() {
        productDetailPage().switchToDegerlendirmelerTab();
    }

    @And("sort the reviews by The Best Reviews")
    public void sortTheReviewsByTheBestReviews() throws InterruptedException {
        if (!productDetailPage().hasReviews()) return;
        productReviewsPage().orderCommentsFromBestToWorst();
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

    @And("get main product price to compare")
    public void getMainProductPriceToCompare() {
        productDetailPage().getpriceText();
    }

    @And("switch to the other sellers tab")
    public void switchToTheOtherSellersTab() {
        productDetailPage().switchToOtherSellersTab();
    }

    @And("add the cheapest product to the cart")
    public void addTheCheapestProductToTheCart() {
        if (!productDetailPage().hasOtherSellers()) return;
        otherSellersPage().addLowestPriceToCart();

    }

    @Then("verify product is added to cart successfully")
    public void verifyProductIsAddedToCartSuccessfully() {
        if (!productDetailPage().hasOtherSellers()) return;
        Assert.assertTrue("Product should be added to cart",
                productCartPage().verifyProductAddedToCart());
    }

}

package stepDefs.SearchResultsSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import pages.BasePage;

import static utility.BrowserUtils.getElementText;

public class SearchResultsSteps extends BasePage {

    @Then("user verify {string} is displayed in search result page")
    public void user_verify_is_displayed_in_search_result_page(String expectedMessage) {
        String actualMessage = getElementText(searchResultsPage().searchResultText());
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @And("select a random product from the search results and navigate to the product details page")
    public void selectARandomProductFromTheSearchResultsAndNavigateToTheProductDetailsPage(){

        searchResultsPage().selectARandomProduct();
    }

}
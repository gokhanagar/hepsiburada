package stepDefs.SearchSteps;

import io.cucumber.java.en.When;
import pages.BasePage;

public class SearchSteps extends BasePage {

    @When("user search for {string} which is popular and has comments")
    public void userSearchForWhichIsPopularAndHasComments(String keyword) {
        searchPage().searchForProduct(keyword);
    }


}
package pages;

import utility.Driver;
import org.openqa.selenium.support.PageFactory;

import static stepDefs.Hooks.driver;

public abstract class BasePage {

    private HomePage homePage;
    private SearchResultsPage searchResultsPage;

    public HomePage homePage() {
        if (homePage == null){
            homePage = new HomePage();
        }
        return homePage;
    }

    public SearchResultsPage searchResultsPage(){
        if (searchResultsPage == null){
            searchResultsPage = new SearchResultsPage();
        }
        return searchResultsPage;
    }







}
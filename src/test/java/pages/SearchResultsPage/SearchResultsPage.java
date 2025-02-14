package pages.SearchResultsPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


import java.util.List;
import java.util.Random;


import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.*;

public class SearchResultsPage {
    private static final Logger logger = LogManager.getLogger(SearchResultsPage.class);
    private final By searchResultText = By.cssSelector("h1[data-test-id='header-h1']");
    private final By listOfProducts = By.xpath("//ul[@id='1']//li[@class='productListContent-zAP0Y5msy8OHn5z7T_K_']");

    public By searchResultText() {return searchResultText;}

    public void selectARandomProduct(){

        List<WebElement> products = driver.findElements(listOfProducts);
        Random random = new Random();

        int nextNumber = random.nextInt(products.size());
        clickAndSwitchToNewTab(products, nextNumber);
    }

}
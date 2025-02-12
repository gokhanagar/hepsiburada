package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.Random;


import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.*;

public class SearchResultsPage {

    private final By searchResultText = By.cssSelector("h1[data-test-id='header-h1']");
    private final By listOfProducts = By.xpath("//ul[@id='1']//li[@class='productListContent-zAP0Y5msy8OHn5z7T_K_']");

    public By searchResultText() {return searchResultText;}

    public void selectARandomProduct(){

        List<WebElement> products = driver.findElements(listOfProducts);
        Random random = new Random();

        int nextNumber = random.nextInt(products.size());
        System.out.println("rastgele numara " + nextNumber);
        clickAndSwitchToNewTab(products, nextNumber);
    }
}
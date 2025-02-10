package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.getElementText;
import static utility.BrowserUtils.waitForDOMStability;

public class SearchResultsPage {

    private final By searchResultText = By.cssSelector("h1[data-test-id='header-h1']");
    private final By listOfProducts = By.xpath("//ul[@id='1']//li[@class='productListContent-zAP0Y5msy8OHn5z7T_K_']");




    public SearchResultsPage searchResultTextAssertion(){
        Assert.assertEquals("iphone", getElementText(searchResultText));
        return this;
    }


    public SearchResultsPage selectARandomProduct(){

        List<WebElement> products = driver.findElements(listOfProducts);
        Random random = new Random();

        int n = random.nextInt(products.size());
        System.out.println("rastgele sayi " + n);
        products.get(n).click();
        waitForDOMStability(2);
        
        return this;
    }


















}

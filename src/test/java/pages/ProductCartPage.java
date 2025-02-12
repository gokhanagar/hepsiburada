package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.getElementText;


public class ProductCartPage extends BasePage{

    private final By myCart = By.xpath("//div[@class='basket_headerTop_F06D4']");
    private final By productPriceOnTheCart = By.xpath("//div[@class='product_price_container_2zE3C']");


    public WebElement getMyCart(){
        return driver.findElement(myCart);
    }

    public String getProductPriceOnTheCart(){
        return getElementText(productPriceOnTheCart);
    }

    public int getProductPriceOnTheCartNumber(){
        return otherSellersPage().convertPriceToInteger(getProductPriceOnTheCart());
    }



}
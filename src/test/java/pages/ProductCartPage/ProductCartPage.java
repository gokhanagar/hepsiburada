package pages.ProductCartPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.BasePage;

import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.getElementText;


public class ProductCartPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(ProductCartPage.class);
    private final By myCart = By.xpath("//div[@class='basket_headerTop_F06D4']");
    private final By productPriceOnTheCart = By.xpath("//div[@class='product_price_container_2zE3C']");


    public WebElement getMyCart(){
        try {
            return driver.findElement(myCart);
        } catch (Exception e) {
            logger.error("Error getting my cart element: {}", e.getMessage());
            throw e;
        }
    }

    public String getProductPriceOnTheCart(){
        try {
            String price = getElementText(productPriceOnTheCart);
            logger.info("Product price on cart: {}", price);
            return price;
        } catch (Exception e) {
            logger.error("Error getting product price on cart: {}", e.getMessage());
            throw e;
        }
    }

    public int getProductPriceOnTheCartNumber(){
        try {
            int price = otherSellersPage().convertPriceToInteger(getProductPriceOnTheCart());
            logger.info("Converted cart price to number: {}", price);
            return price;
        } catch (Exception e) {
            logger.error("Error converting cart price to number: {}", e.getMessage());
            throw e;
        }
    }


}
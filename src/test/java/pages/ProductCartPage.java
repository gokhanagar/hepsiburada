package pages;

import org.openqa.selenium.By;

public class ProductCartPage {

    private final By productAddedToCartText = By.xpath("//*[text()=' Ürün sepetinizde']");

    public By getProductAddedToCartText(){
        return productAddedToCartText;
    }
}

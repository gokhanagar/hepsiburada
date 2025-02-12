package pages;

import org.openqa.selenium.By;

import static utility.BrowserUtils.isElementDisplayed;
import static utility.BrowserUtils.waitForDOMStability;

public class ProductCartPage {

    private final By addedToCartPopup = By.xpath("//div[@class='hb-toast-text']");
    private final By addedToCartModal = By.cssSelector("[data-test-class='modal_overflow']");
    private final By productInBasketText = By.xpath("//span[contains(text(), 'Ürün sepetinizde')]");

    public boolean verifyProductAddedToCart() {
        try {
            waitForDOMStability(2);

            // İlk durum: Popup kontrolü
            if (isElementDisplayed(addedToCartPopup)) {
                System.out.println("Product added to cart - Popup message displayed");
                return true;
            }

            // İkinci durum: Modal kontrolü
            if (isElementDisplayed(addedToCartModal)) {
                if (isElementDisplayed(productInBasketText)) {
                    System.out.println("Product added to cart - Modal message displayed");
                    return true;
                }
            }

            System.out.println("No confirmation message found for adding to cart");
            return false;

        } catch (Exception e) {
            System.out.println("Error verifying product added to cart: " + e.getMessage());
            return false;
        }
    }
}

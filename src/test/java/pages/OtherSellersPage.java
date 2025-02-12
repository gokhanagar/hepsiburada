package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.*;

public class OtherSellersPage extends BasePage{

    private final By closeButton = By.cssSelector("div[data-test-id='drawer-close']");
    private final By otherSellersPriceList = By.xpath("//div[@class='hb-AxmME dxBY snleuw7079v XrCxGG290pJRpsP9fhlZ']//div[@data-test-id='price-current-price']");
    private final By otherSellersProductAddToCartButton = By.xpath("//button[@class='sf-Axjyr Wdnsr svjvfdagm5y v5EsDEc7HGg2J9hUA2ag']");


    private List<Integer> getAllSellerPrices() {
        List<WebElement> priceElements = driver.findElements(otherSellersPriceList);
        List<Integer> prices = new ArrayList<>();

        for (WebElement element : priceElements) {
            String priceText = element.getText();
            int price = convertPriceToInteger(priceText);
            prices.add(price);
        }

        return prices;
    }

    private void closeModalAndAddToCart() {
        clickWithJS(closeButton);
        waitForDOMStability(10);
        clickWithJS(productDetailPage().productAddToCartButton());

    }

    private void clickSellerButton(int targetPrice, List<Integer> prices, List<WebElement> buttons) {
        for (int i = 0; i < prices.size(); i++) {
            if (prices.get(i) == targetPrice) {
                System.out.println("Clicking add to cart button for price: " + targetPrice);
                buttons.get(i).click();
                waitForDOMStability(2);
                return;
            }
        }
    }

    public void addLowestPriceToCart() {
        try {
            // Ana ürün fiyatını al ve dönüştür
            String mainPrice = productDetailPage().getMainProductPrice();
            System.out.println("Main product price in OtherSellersPage: " + mainPrice);
            int mainProductPrice = convertPriceToInteger(mainPrice);
            
            // Diğer satıcıların fiyatlarını al
            List<Integer> otherPrices = getAllSellerPrices();
            System.out.println("Other seller prices: " + otherPrices);
            
            // En düşük fiyatı bul
            int minOtherPrice = otherPrices.isEmpty() ? Integer.MAX_VALUE : Collections.min(otherPrices);
            System.out.println("Minimum other seller price: " + minOtherPrice);
            System.out.println("Main product price: " + mainProductPrice);
            
            // Fiyat karşılaştırması
            if (mainProductPrice <= minOtherPrice) {
                System.out.println("Main product has the best price, adding to cart...");
                // Modal'ı kapat
                if (isElementDisplayed(closeButton)) {
                    clickWithJS(closeButton);
                    waitForDOMStability(2);
                }
                // Ana ürünü sepete ekle
                By addToCartButton = productDetailPage().productAddToCartButton();
                clickWithJS(addToCartButton);
            } else {
                System.out.println("Found better price from other seller, adding to cart...");
                List<WebElement> buttons = driver.findElements(otherSellersProductAddToCartButton);
                clickSellerButton(minOtherPrice, otherPrices, buttons);
            }
            
        } catch (Exception e) {
            System.out.println("Error in addLowestPriceToCart: " + e.getMessage());
            throw e;
        }
    }

    private int convertPriceToInteger(String priceText) {
        try {
            System.out.println("Converting price text: " + priceText);
            if (priceText == null || priceText.trim().isEmpty()) {
                throw new IllegalArgumentException("Price text is null or empty");
            }

            // 1. TL veya diğer harfleri temizle
            String cleanedPrice = priceText.replaceAll("[^0-9.,]", "");

            // 2. Binlik ayırıcı olan noktayı kaldır, ondalık için virgülü noktaya çevir
            String normalizedPrice = cleanedPrice.replace(".", "").replace(",", ".");

            // 3. String'i Double'a çevir
            double price = Double.parseDouble(normalizedPrice);

            // 4. En yakın tam sayıya yuvarla
            return (int) Math.round(price);



        } catch (Exception e) {
            System.out.println("Error converting price: " + e.getMessage());
            throw e;
        }
    }

}
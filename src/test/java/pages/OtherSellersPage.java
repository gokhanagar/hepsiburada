package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.clickWithJS;
import static utility.BrowserUtils.isElementDisplayed;
import static utility.BrowserUtils.waitForDOMStability;

public class OtherSellersPage extends BasePage{

    private final By closeButton = By.cssSelector("div[data-test-id='drawer-close']");

    
    // Diğer satıcılar listesi container'ı/
    private final By merchantsContainer = By.xpath("//a[contains(@data-test-id, 'merchant-name')]");
    
    // Fiyat listesi - container içinde
    private final By otherSellersPriceList = By.xpath(
        "//div[@data-test-id='price-current-price']"
    );
    
    // Sepete ekle butonları - container içinde
    private final By otherSellersProductAddToCartButton = By.xpath(
        "//button[text()='Sepete ekle']");




    private List<Integer> getAllSellerPrices() {
        try {
            waitForDOMStability(2);
            
            if (!isElementDisplayed(merchantsContainer)) {
                System.out.println("Merchants container not found");
                return new ArrayList<>();
            }
            
            List<WebElement> priceElements = driver.findElements(otherSellersPriceList);
            List<Integer> prices = new ArrayList<>();

            System.out.println("Found " + priceElements.size() + " price elements");
            
            // İlk fiyatı atla (ana ürün fiyatı)
            for (int i = 1; i < priceElements.size(); i++) {
                try {
                    String priceText = priceElements.get(i).getText();
                    System.out.println("Found price at index " + i + ": " + priceText);
                    if (!priceText.trim().isEmpty()) {
                        int price = convertPriceToInteger(priceText);
                        prices.add(price);
                    }
                } catch (Exception e) {
                    System.out.println("Error processing price element: " + e.getMessage());
                }
            }

            System.out.println("Processed prices (excluding main product): " + prices);
            return prices;
        } catch (Exception e) {
            System.out.println("Error getting seller prices: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void addMainProductToCart() {
        try {
            // Eğer kapatma butonu varsa kapat
            if (isElementDisplayed(closeButton)) {
                System.out.println("Closing other sellers view...");
                clickWithJS(closeButton);
                waitForDOMStability(5);
            } else {
                System.out.println("No close button found, proceeding with main product");
            }
            
            // Ana ürünü sepete ekle
            System.out.println("Adding main product to cart...");
            clickWithJS(productDetailPage().productAddToCartButton());
            waitForDOMStability(2);
            
        } catch (Exception e) {
            System.out.println("Error adding main product to cart: " + e.getMessage());
            throw e;
        }
    }

    private void clickSellerButton(int targetPrice, List<Integer> prices, List<WebElement> buttons) {
        try {
            System.out.println("Looking for button with price: " + targetPrice);
            
            // Modal'ın tamamen yüklenmesini bekle
            waitForDOMStability(3);
            
            // Butonları yeniden al (modal yüklendikten sonra)
            List<WebElement> currentButtons = driver.findElements(otherSellersProductAddToCartButton);
            System.out.println("Total prices: " + prices.size());
            System.out.println("Total buttons found: " + currentButtons.size());
            
            // Fiyat ve buton eşleştirmesi
            for (int i = 0; i < prices.size(); i++) {
                System.out.println("Checking price at index " + i + ": " + prices.get(i));
                if (prices.get(i) == targetPrice) {
                    System.out.println("Found matching price at index: " + i);
                    
                    // Buton index'ini kontrol et
                    if (i < currentButtons.size()) {
                        WebElement button = currentButtons.get(i);
                        if (button.isDisplayed()) {
                            waitForDOMStability(1);
                            button.click();
                            return;
                        }
                    }
                }
            }
            System.out.println("No matching button found for price: " + targetPrice);
        } catch (Exception e) {
            System.out.println("Error clicking seller button: " + e.getMessage());
            throw e;
        }
    }



    public void addLowestPriceToCart() {
        try {
            // Ana ürün fiyatını al
            String mainPrice = productDetailPage().getMainProductPrice();
            System.out.println("Main product price : " + mainPrice);
            int mainProductPrice = convertPriceToInteger(mainPrice);
            
            // Modal'ın yüklenmesini bekle
            waitForDOMStability(3);
            
            // Diğer satıcıların fiyatlarını al
            List<Integer> otherPrices = getAllSellerPrices();
            System.out.println("Other seller prices: " + otherPrices);
            
            if (otherPrices.isEmpty()) {
                System.out.println("No other seller prices found, adding main product to cart");
                clickWithJS(productDetailPage().productAddToCartButton());
                return;
            }
            
            int minOtherPrice = Collections.min(otherPrices);
            System.out.println("Minimum other seller price: " + minOtherPrice);
            System.out.println("Main product price: " + mainProductPrice);
            
            if (mainProductPrice <= minOtherPrice) {
                System.out.println("Main product has the best price, adding to cart...");
                addMainProductToCart();
            } else {
                System.out.println("Found better price from other seller, adding to cart...");
                // Butonları direkt olarak clickSellerButton'a gönderme
                clickSellerButton(minOtherPrice, otherPrices, new ArrayList<>());
            }
        } catch (Exception e) {
            System.out.println("Error in addLowestPriceToCart: " + e.getMessage());
            throw e;
        }
    }

    public int convertPriceToInteger(String priceText) {
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
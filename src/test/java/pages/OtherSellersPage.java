package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.clickWithJS;
import static utility.BrowserUtils.isElementDisplayed;
import static utility.BrowserUtils.waitForDOMStability;

public class OtherSellersPage extends BasePage{
    private static final Logger logger = LogManager.getLogger(OtherSellersPage.class);

    private final By closeButton = By.cssSelector("div[data-test-id='drawer-close']");
    private final By otherSellersContainer = By.xpath("//a[contains(@data-test-id, 'merchant-name')]");
    private final By otherSellersPriceList = By.xpath("//div[@data-test-id='price-current-price']");
    private final By otherSellersProductAddToCartButton = By.xpath("//button[text()='Sepete ekle']");


    private List<Integer> getAllSellerPrices() {
        try {
            waitForDOMStability(2);
            
            if (!isElementDisplayed(otherSellersContainer)) {
                logger.warn("other sellers container not found");
                return new ArrayList<>();
            }
            
            List<WebElement> priceElements = driver.findElements(otherSellersPriceList);
            List<Integer> prices = new ArrayList<>();

            logger.info("Found {} price elements", priceElements.size());
            
            for (int i = 1; i < priceElements.size(); i++) {
                try {
                    String priceText = priceElements.get(i).getText();
                    logger.debug("Found price at index {}: {}", i, priceText);
                    if (!priceText.trim().isEmpty()) {
                        int price = convertPriceToInteger(priceText);
                        prices.add(price);
                    }
                } catch (Exception e) {
                    logger.error("Error processing price element: {}", e.getMessage());
                }
            }

            logger.info("Processed prices (excluding main product): {}", prices);
            return prices;
        } catch (Exception e) {
            logger.error("Error getting seller prices: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private void addMainProductToCart() {
        try {
            if (isElementDisplayed(closeButton)) {
                logger.info("Closing other sellers view...");
                clickWithJS(closeButton);
                waitForDOMStability(5);
            } else {
                logger.info("No close button found, proceeding with main product");
            }
            
            logger.info("Adding main product to cart...");
            clickWithJS(productDetailPage().productAddToCartButton());
            waitForDOMStability(2);
            
        } catch (Exception e) {
            logger.error("Error adding main product to cart: {}", e.getMessage());
            throw e;
        }
    }

    private void clickSellerButton(int targetPrice, List<Integer> prices, List<WebElement> buttons) {
        try {
            logger.info("Looking for button with price: {}", targetPrice);
            
            waitForDOMStability(3);
            
            List<WebElement> currentButtons = driver.findElements(otherSellersProductAddToCartButton);
            logger.info("Total prices: {}", prices.size());
            logger.info("Total buttons found: {}", currentButtons.size());
            
            for (int i = 0; i < prices.size(); i++) {
                logger.debug("Checking price at index {}: {}", i, prices.get(i));
                if (prices.get(i) == targetPrice) {
                    logger.info("Found matching price at index: {}", i);
                    
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
            logger.info("No matching button found for price: {}", targetPrice);
        } catch (Exception e) {
            logger.error("Error clicking seller button: {}", e.getMessage());
            throw e;
        }
    }



    public void addLowestPriceToCart() {
        try {
            String mainPrice = productDetailPage().getMainProductPrice();
            int mainProductPrice = convertPriceToInteger(mainPrice);
            
            waitForDOMStability(3);
            
            List<Integer> otherPrices = getAllSellerPrices();
            logger.info("Other seller prices: {}", otherPrices);
            
            if (otherPrices.isEmpty()) {
                logger.info("No other seller prices found, adding main product to cart");
                clickWithJS(productDetailPage().productAddToCartButton());
                return;
            }
            
            int minOtherPrice = Collections.min(otherPrices);
            logger.info("Minimum other seller price: {}", minOtherPrice);
            logger.info("Main product price: {}", mainProductPrice);
            
            if (mainProductPrice <= minOtherPrice) {
                logger.info("Main product has the best price, adding to cart...");
                addMainProductToCart();
            } else {
                logger.info("Found better price from other seller, adding to cart...");
                clickSellerButton(minOtherPrice, otherPrices, new ArrayList<>());
            }
        } catch (Exception e) {
            logger.error("Error in addLowestPriceToCart: {}", e.getMessage());
            throw e;
        }
    }

    public int convertPriceToInteger(String priceText) {
        try {
            logger.info("Converting price text: {}", priceText);
            if (priceText == null || priceText.trim().isEmpty()) {
                throw new IllegalArgumentException("Price text is null or empty");
            }

            String cleanedPrice = priceText.replaceAll("[^0-9.,]", "");
            String normalPrice = cleanedPrice.replace(".", "").replace(",", ".");
            double price = Double.parseDouble(normalPrice);
            return (int) Math.round(price);

        } catch (Exception e) {
            logger.error("Error converting price: {}", e.getMessage());
            throw e;
        }
    }

}
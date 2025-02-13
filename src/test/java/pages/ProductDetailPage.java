package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static utility.BrowserUtils.clickWithJS;
import static utility.BrowserUtils.getElementText;
import static utility.BrowserUtils.isElementDisplayed;
import static utility.BrowserUtils.waitForDOMStability;

public class ProductDetailPage extends BasePage{
    private static final Logger logger = LogManager.getLogger(ProductDetailPage.class);
    private static boolean hasReview = false;
    private static boolean hasOtherSellers = false;
    private static String mainProductPrice;
    private final By noReviewText = By.xpath("//div[@data-test-id='no-review']//span[@data-test-id='no-review-text']");
    private final By reviewButton = By.xpath("//div[@data-test-id='has-review']/a");
    private final By otherSellersText = By.xpath("//div[@class='vzN3xHW3ClslJV_iYDc1']//span");
    private final By otherSellersSeeAllButton = By.xpath("//div[@class='vzN3xHW3ClslJV_iYDc1']//button[@class='M6iJLUpgHKlEPzGcOggE']");
    private final By defaultPriceText = By.xpath("(//div[@data-test-id='default-price']/div)[1]");
    private final By specialPriceText = By.xpath("//div[@data-test-id='checkout-price']//div[@class='bWwoI8vknB6COlRVbpRj']");
    private final By productAddToCartButton = By.cssSelector("button[data-test-id='addToCart']");
    private final By shoppingCartButton = By.xpath("//*[@id='shoppingCart']");

    public By productAddToCartButton() {return productAddToCartButton;}
    private void checkAndClickIfReviewExists() {
        try {
            logger.info("Checking for reviews");
            
            if (isElementDisplayed(noReviewText)) {
                String actualText = getElementText(noReviewText);
                logger.info("Found no review text: {}", actualText);
                Assert.assertTrue(isElementDisplayed(noReviewText));
                return;
            }

            if (isElementDisplayed(reviewButton)) {
                logger.info("Found review button, clicking");
                clickWithJS(reviewButton);
                hasReview = true;
            }
            
        } catch (Exception e) {
            logger.error("Error in checkAndClickIfReviewExists: {}", e.getMessage());
        }
    }

    public ProductDetailPage switchToDegerlendirmelerTab() {
        checkAndClickIfReviewExists();
        logger.info("Has review result: {}", hasReview);

        if (!hasReview) {
            logger.warn("No reviews found. Skipping remaining steps.");
        }
        return this;
    }

    public boolean hasReviews() {
        return hasReview;
    }

    private void checkAndClickIfOtherSellersExists() {
        try {
            logger.info("Checking for other sellers...");

            if (isElementDisplayed(otherSellersText)) {
                logger.info("Found other sellers button, clicking...");
                clickWithJS(otherSellersSeeAllButton);
                waitForDOMStability(20);
                hasOtherSellers = true;
            }
        } catch (Exception e) {
            logger.error("Error in checkAndClickIfOtherSellersExists: {}", e.getMessage());
        }
    }

    public ProductDetailPage switchToOtherSellersTab() {
        checkAndClickIfOtherSellersExists();
        logger.info("Has other sellers result: {}", hasOtherSellers);
        return this;
    }

    public String getpriceText() {
        try {
            if (isElementDisplayed(specialPriceText)) {
                mainProductPrice = getElementText(specialPriceText);
                logger.info("Found special price: {}", mainProductPrice);
                return mainProductPrice;
            }
            
            mainProductPrice = getElementText(defaultPriceText);
            logger.info("Found default price: {}", mainProductPrice);
            return mainProductPrice;
            
        } catch (Exception e) {
            logger.error("Error getting price: {}", e.getMessage());
            throw e;
        }
    }

    public String getMainProductPrice() {
        if (mainProductPrice == null || mainProductPrice.isEmpty()) {
            mainProductPrice = getpriceText();
        }
        logger.info("Getting main product price: {}", mainProductPrice);
        return mainProductPrice;
    }

    public int getMainProductPriceNumber() {
        return otherSellersPage().convertPriceToInteger(mainProductPrice);
    }

    public void addProductToCart() {
        try {
            clickWithJS(productAddToCartButton);
            waitForDOMStability(5);
            
            clickWithJS(shoppingCartButton);
            waitForDOMStability(5);
        } catch (Exception e) {
            logger.error("Error adding product to cart: {}", e.getMessage());
            throw e;
        }
    }

}
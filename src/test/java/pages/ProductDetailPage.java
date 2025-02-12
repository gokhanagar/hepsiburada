package pages;

import org.junit.Assert;
import org.openqa.selenium.By;

import static utility.BrowserUtils.clickWithJS;
import static utility.BrowserUtils.getElementText;
import static utility.BrowserUtils.isElementDisplayed;
import static utility.BrowserUtils.waitForDOMStability;

public class ProductDetailPage {
    private static boolean hasReview = false;
    private static boolean hasOtherSellers = false;
    private final By noReviewText = By.xpath("//div[@data-test-id='no-review']//span[@data-test-id='no-review-text']");
    private final By reviewButton = By.xpath("//div[@data-test-id='has-review']/a");
    private final By otherSellersText = By.xpath("//div[@class='vzN3xHW3ClslJV_iYDc1']//span");
    private final By otherSellersSeeAllButton = By.xpath("//div[@class='vzN3xHW3ClslJV_iYDc1']//button[@class='M6iJLUpgHKlEPzGcOggE']");
    private final By priceText = By.xpath("//div[@data-test-id='default-price']//div[@class='z7kokklsVwh0K5zFWjIO']");
    private final By productAddToCartButton = By.cssSelector("//button[@data-test-id='addToCart']");

    public String getpriceText() {
        String mainProductPrice = getElementText(priceText);

        System.out.println("Found main product price: " + mainProductPrice);

        return mainProductPrice;
    } ;

    private void checkAndClickIfReviewExists() {
        try {
            System.out.println("Checking for reviews...");
            
            if (isElementDisplayed(noReviewText)) {
                String actualText = getElementText(noReviewText);
                System.out.println(actualText);

                Assert.assertTrue(isElementDisplayed(noReviewText));
                System.out.println("Found no review text: " + actualText);
                return;
            }

            if (isElementDisplayed(reviewButton)) {
                System.out.println("Found review button, clicking...");
                clickWithJS(reviewButton);
                hasReview = true;
            }
            
        } catch (Exception e) {
            System.out.println("Error in checkAndClickIfReviewExists: " + e.getMessage());
        }
    }

    public ProductDetailPage switchToDegerlendirmelerTab() {
        checkAndClickIfReviewExists();
        System.out.println("Has review result: " + hasReview);

        if (!hasReview) {
            System.out.println("No reviews found. Skipping remaining steps.");
        }
        return this;
    }

    public boolean hasReviews() {
        return hasReview;
    }

    private void checkAndClickIfOtherSellersExists() {
        try {
            System.out.println("Checking for other sellers...");


            if (isElementDisplayed(otherSellersText)) {
                System.out.println("Found other sellers button, clicking...");

                String price = getpriceText();
                System.out.println("price " + price);

                clickWithJS(otherSellersSeeAllButton);
                waitForDOMStability(20);
                hasOtherSellers = true;
                return;
            }
        } catch (Exception e) {
            System.out.println("Error in checkAndClickIfOtherSellersExists: " + e.getMessage());
        }
    }

    public ProductDetailPage switchToOtherSellersTab() {
        checkAndClickIfOtherSellersExists();
        System.out.println("Has other sellers result: " + hasOtherSellers);
        return this;
    }

    public boolean hasOtherSellers() {
        return hasOtherSellers;
    }

    public By productAddToCartButton(){return  productAddToCartButton;}

}

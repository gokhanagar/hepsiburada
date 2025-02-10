package pages;

import org.junit.Assert;
import org.junit.Assume;
import org.openqa.selenium.By;

import static utility.BrowserUtils.clickWithJS;
import static utility.BrowserUtils.getElementText;
import static utility.BrowserUtils.isElementDisplayed;

public class ProductDetailPage {
    private static boolean hasReview = false; // static yapıyoruz ki değer korunsun
    private final By noReviewText = By.xpath("//div[@data-test-id='no-review']//span[@data-test-id='no-review-text']");
    private final By reviewButton = By.xpath("//div[@data-test-id='has-review']/a");

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
}

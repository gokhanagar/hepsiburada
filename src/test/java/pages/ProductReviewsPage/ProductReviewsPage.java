package pages.ProductReviewsPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import static utility.BrowserUtils.*;

public class ProductReviewsPage {
    private static final Logger logger = LogManager.getLogger(ProductReviewsPage.class);
    private final By orderDefault = By.cssSelector("div[class='arrowUpOrange']");
    private final By orderTheBestReview = By.xpath("//*[text()='En yeni değerlendirme']");
    private final By thumbsUpButton = By.xpath("(//div[@class='paginationOverlay']//div[contains(@class, 'voteIcon')])[1]");
    private final By thankYouText = By.xpath("//div[@class='paginationOverlay']//*[contains(@class, 'thanksText')]");

    public By getThankYouText() {return thankYouText;}
    public ProductReviewsPage sortTheReviewsByTheBestReviews() {

        click(orderDefault);
        click(orderTheBestReview);
        waitForDOMStability(2);
        return this;
    }

    public ProductReviewsPage clickThumbsUpForReview() {
        clickWithJS(thumbsUpButton);
        waitForDOMStability(2);
        return this;
    }


}
package pages;

import org.openqa.selenium.By;

import static utility.BrowserUtils.*;

public class ProductReviewsPage {

    private final By orderDefault = By.cssSelector("div[class='arrowUpOrange']");
    private final By orderTheBestReview = By.xpath("//*[text()='En yeni değerlendirme']");
    private final By thumbsUpButton = By.xpath("(//div[@class='thumbsUp hermes-ReviewCard-module-lOsa4wAwncdp3GgzpaaV'])[1]");
    private final By thankYouText = By.xpath("//div[@class='hermes-ReviewCard-module-QA5PqdPP5EhkpY_vptfv']//*[text()='Teşekkür Ederiz.']");

    public By getThankYouText() {return thankYouText;}
    public ProductReviewsPage orderCommentsFromBestToWorst() {

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
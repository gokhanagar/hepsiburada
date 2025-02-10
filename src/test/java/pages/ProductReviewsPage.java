package pages;

import org.junit.Assert;
import org.openqa.selenium.By;

import static utility.BrowserUtils.*;

public class ProductReviewsPage {

    private final By orderDefault = By.cssSelector("div[class='hermes-Sort-module-AI2HFuJVJiCMkxgX4Rac']");
    private final By orderTheBestReview = By.xpath("//*[text()='En yeni değerlendirme']");
    private final By thumbsUpButton = By.xpath("(//div[@class='thumbsUp hermes-ReviewCard-module-lOsa4wAwncdp3GgzpaaV'])[1]");
    private final By thankYouText = By.xpath("//div[@class='hermes-ReviewCard-module-QA5PqdPP5EhkpY_vptfv']//*[text()='Teşekkür Ederiz.']");

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

    public ProductReviewsPage assertionResultForReview() {
        // Arrange ( Hazırlıklar ve beklentileri yapmak )
        String expected = "Teşekkür Ederiz.";
        // Act ( Sadece işlem yapmak )
        String actualResult = getElementText(thankYouText);
        // Assert ( Beklenen karşılaştırmaları yapmak )
        System.out.println(actualResult);
        Assert.assertNotNull(actualResult);
        Assert.assertEquals(expected, actualResult);
        return this;
    }
}

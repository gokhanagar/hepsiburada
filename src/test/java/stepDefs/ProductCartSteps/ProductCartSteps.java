package stepDefs.ProductCartSteps;

import io.cucumber.java.en.Then;

import org.junit.Assert;
import pages.BasePage;

public class ProductCartSteps extends BasePage {

    @Then("verify user is on the cart page")
    public void verifyUserIsOnTheCartPage() {
        Assert.assertTrue(productCartPage().getMyCart().isDisplayed());
    }

    @Then("compare the product price with the cart price")
    public void compareTheProductPriceWithTheCartPrice() {

        int mainProductPrice = productDetailPage().getMainProductPriceNumber();
        int productPriceOnTheCart = productCartPage().getProductPriceOnTheCartNumber();
        Assert.assertEquals(mainProductPrice,productPriceOnTheCart);

    }


}
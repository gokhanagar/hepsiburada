package stepDefs;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.BasePage;

public class CartSteps extends BasePage {

    @When("add the product to the cart")
    public void addTheProductToTheCart() {productDetailPage().addProductToCart();}


    @Then("compare the product price with the cart price")
    public void compareTheProductPriceWithTheCartPrice() {
        Assert.assertTrue(productCartPage().getMyCart().isDisplayed());

        int mainProductPrice = productDetailPage().getMainProductPriceNumber();
        int productPriceOnTheCart = productCartPage().getProductPriceOnTheCartNumber();
        Assert.assertEquals(mainProductPrice,productPriceOnTheCart);

    }
}

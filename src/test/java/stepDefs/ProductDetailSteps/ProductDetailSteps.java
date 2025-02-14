package stepDefs.ProductDetailSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import pages.BasePage;

public class ProductDetailSteps extends BasePage {

    @And("switch to the Reviews tab")
    public void switchTotheReviewstab() {
        productDetailPage().switchToReviewsTab();
    }

    @And("get main product price to compare")
    public void getMainProductPriceToCompare() {
        productDetailPage().getMainProductPriceText();
    }

    @And("switch to the other sellers tab")
    public void switchToTheOtherSellersTab() {
        productDetailPage().switchToOtherSellersTab();
    }

    @When("add the product to the cart")
    public void addTheProductToTheCart() {
        productDetailPage().addProductToCart();}

}
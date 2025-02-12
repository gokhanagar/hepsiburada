package stepDefs;

import io.cucumber.java.en.And;

import pages.BasePage;

public class PriceComparisonSteps extends BasePage {

    @And("get main product price to compare")
    public void getMainProductPriceToCompare() {
        productDetailPage().getpriceText();
    }

    @And("switch to the other sellers tab")
    public void switchToTheOtherSellersTab() {
        productDetailPage().switchToOtherSellersTab();
    }

    @And("add the cheapest product to the cart")
    public void addTheCheapestProductToTheCart() {otherSellersPage().addLowestPriceToCart();}


}
package stepDefs.OtherSellersSteps;

import io.cucumber.java.en.And;
import pages.BasePage;

public class OtherSellersSteps extends BasePage {

    @And("add the cheapest product to the cart")
    public void addTheCheapestProductToTheCart() {
        otherSellersPage().addTheCheapestProductToCart();}

}
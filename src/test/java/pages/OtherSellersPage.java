package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static stepDefs.Hooks.driver;
import static utility.BrowserUtils.clickWithJS;
import static utility.BrowserUtils.getElementText;
import static utility.BrowserUtils.waitForDOMStability;

public class OtherSellersPage extends BasePage{

    private final By closeButton = By.cssSelector("div[data-test-id='drawer-close']");
    private final By otherSellersPriceList = By.xpath("//div[@class='smcxph4R3Ehm9tRyNPen']//div[@data-test-id='price-prev-price']");
    private final By otherSellersProductAddToCartButton = By.xpath("//button[@class='sf-Axjyr Wdnsr svjvfdagm5y v5EsDEc7HGg2J9hUA2ag']");


    private List<Integer> getAllSellerPrices() {
        List<WebElement> priceElements = driver.findElements(otherSellersPriceList);
        List<Integer> prices = new ArrayList<>();

        for (WebElement element : priceElements) {
            String priceText = element.getText();
            int price = convertPriceToInteger(priceText);
            prices.add(price);
        }

        return prices;
    }

    private int convertPriceToInteger(String priceText){
        System.out.println("Found price text: " + productDetailPage().getpriceText());

        String price = productDetailPage().getpriceText()

                .replaceAll("[^0-9,]", "") // Sadece rakamları ve virgülü tut

                .replace(",", "");

        if (price.isEmpty()) {
            throw new NumberFormatException("Geçersiz fiyat formatı: " + priceText);
        }

        return Integer.parseInt(price);
    }

    private void closeModalAndAddToCart() {
        clickWithJS(closeButton);
        waitForDOMStability(10);
        clickWithJS(productDetailPage().productAddToCartButton());

    }

    private void clickSellerButton(int targetPrice, List<Integer> prices, List<WebElement> buttons) {
        for(int i=0; i<prices.size(); i++) {
            if(prices.get(i) == targetPrice) {
                buttons.get(i).click();
                waitForDOMStability(10);
                return;
            }
        }
    }

    public void addLowestPriceToCart() {
        List<Integer> otherPrices = getAllSellerPrices();
        List<WebElement> otherSellerAddButtons = driver.findElements(otherSellersProductAddToCartButton);

        int mainProductPrice = convertPriceToInteger(productDetailPage().getpriceText());
        otherPrices.add(mainProductPrice); // Ana fiyatı da karşılaştırmaya dahil et

        int minPrice = Collections.min(otherPrices);

        if(minPrice == mainProductPrice) {
            closeModalAndAddToCart();
        } else {
            clickSellerButton(minPrice, otherPrices, otherSellerAddButtons);
        }
    }


}
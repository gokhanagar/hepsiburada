package pages;

import pages.ProductCartPage.ProductCartPage;
import pages.HomePage.HomePage;
import pages.OtherSellersPage.OtherSellersPage;
import pages.ProductDetailPage.ProductDetailPage;
import pages.ProductReviewsPage.ProductReviewsPage;
import pages.SearchPage.SearchPage;
import pages.SearchResultsPage.SearchResultsPage;

public abstract class BasePage {

    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private SearchPage searchPage;
    private ProductDetailPage productDetailPage;
    private ProductReviewsPage productReviewsPage;
    private OtherSellersPage otherSellersPage;
    private ProductCartPage productCartPage;

    public HomePage homePage() {
        if (homePage == null){
            homePage = new HomePage();
        }
        return homePage;
    }

    public SearchPage searchPage(){
        if (searchPage == null){
            searchPage = new SearchPage();
        }
        return searchPage;
    }

    public SearchResultsPage searchResultsPage(){
        if (searchResultsPage == null){
            searchResultsPage = new SearchResultsPage();
        }
        return searchResultsPage;
    }

    public ProductDetailPage productDetailPage(){
        if(productDetailPage == null){
            productDetailPage = new ProductDetailPage();
        }
        return productDetailPage;
    }

    public ProductReviewsPage productReviewsPage(){
        if (productReviewsPage == null){
            productReviewsPage = new ProductReviewsPage();
        }
        return productReviewsPage;
    }

    public OtherSellersPage otherSellersPage(){
        if( otherSellersPage == null){
            otherSellersPage = new OtherSellersPage();
        }
        return otherSellersPage;
    }

    public ProductCartPage productCartPage(){
        if(productCartPage == null){
            productCartPage = new ProductCartPage();
        }
        return productCartPage;
    }

}
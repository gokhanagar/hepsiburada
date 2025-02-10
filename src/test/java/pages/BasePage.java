package pages;

public abstract class BasePage {

    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private ProductDetailPage productDetailPage;
    private ProductReviewsPage productReviewsPage;

    public HomePage homePage() {
        if (homePage == null){
            homePage = new HomePage();
        }
        return homePage;
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



}
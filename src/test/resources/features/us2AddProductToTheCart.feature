@Positiv
Feature: Product Review Sorting and Feedback

  Background:
    Given user navigate to hepsiburada.com

  Scenario: Sort product reviews by newest and give feedback
    When user search for "balata" which is popular and has comments
    And select a random "balata" from the search results and navigate to the product details page
    And switch to the other sellers tab
    And add the cheapest product to the cart
    Then confirming that the product is in the cart with ' Ürün sepetinizde' message












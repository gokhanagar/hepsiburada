@all @us3 @cart
Feature: Product Addition and Price Verification

  Background:
    Given user navigate to hepsiburada.com

  Scenario: US3 - Add a product to cart and compare product price with cart price
    Then user verify home page is displayed
    And user accepts cookies
    When user search for "iphone" which is popular and has comments
    Then user verify "iphone" is displayed in search result page
    And select a random product from the search results and navigate to the product details page
    And get main product price to compare
    When add the product to the cart
    Then verify user is on the cart page
    Then compare the product price with the cart price
@Positi
Feature: Product Addition and Price Verification

  Scenario: Add a product to cart and compare product price with cart price
    Given user navigate to hepsiburada.com
    When user search for "bisiklet" which is popular and has comments
    And select a random "bisiklet" from the search results and navigate to the product details page
    And get main product price to compare
    When add the product to the cart
    Then compare the product price with the cart price
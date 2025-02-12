@Positiv
Feature: Product Cart Operations

  Scenario: Add cheapest product to cart
    Given user navigate to hepsiburada.com
    When user search for "balata" which is popular and has comments
    And select a random "balata" from the search results and navigate to the product details page
    And get main product price to compare
    And switch to the other sellers tab
    And add the cheapest product to the cart
    Then verify product is added to cart successfully













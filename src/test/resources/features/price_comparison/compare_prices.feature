@all @us2 @price
Feature: Product Cart Operations

  Background:
    Given user navigate to hepsiburada.com

  Scenario: US2 - Add cheapest product to cart
    Then user verify home page is displayed
    And user accepts cookies
    When user search for "bisiklet" which is popular and has comments
    Then user verify "bisiklet" is displayed in search result page
    And select a random product from the search results and navigate to the product details page
    And get main product price to compare
    And switch to the other sellers tab
    And add the cheapest product to the cart













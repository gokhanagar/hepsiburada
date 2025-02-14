@all @us1 @review
Feature: Product Review Sorting and Feedback

  Background:
    Given user navigate to hepsiburada.com

  Scenario: US1 - Sort product reviews by newest and give feedback
    Then user verify home page is displayed
    And user accepts cookies
    When user search for "balata" which is popular and has comments
    Then user verify "balata" is displayed in search result page
    And select a random product from the search results and navigate to the product details page
    And switch to the Reviews tab
    And sort the reviews by The Best Reviews
    When select a review and click on thumbsUp or thumbsDown
    Then "Teşekkür Ederiz." message should be displayed















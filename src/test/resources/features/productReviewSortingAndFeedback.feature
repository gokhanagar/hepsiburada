@All
Feature: US1 - Product Review Sorting and Feedback

  Background:
    Given user navigate to hepsiburada.com

  Scenario: Sort product reviews by newest and give feedback
    When user search for "balata" which is popular and has comments
    And select a random "balata" from the search results and navigate to the product details page
    And switch to the Reviews tab
    And sort the reviews by The Best Reviews
    When select a review and click on thumbsUp or thumbsDown
    Then "Teşekkür Ederiz." message should be displayed















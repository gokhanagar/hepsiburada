@Positive
Feature: Product Review Sorting and Feedback

  Background:
    Given user navigate to hepsiburada.com

  Scenario: Sort product reviews by newest and give feedback
    When user search for a product which is popular and has comments
    And select a random product from the search results and navigate to the product details page
    And wait for the page to load completely
    And switch to the Değerlendirmeler tab
    And sort the reviews by En Yeni Değerlendirme
    When select a review and click on thumbsUp or thumbsDown
    Then the Thank You message should be displayed















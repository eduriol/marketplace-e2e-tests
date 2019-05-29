@e2e
Feature: Navigate between pages
  In order to have a global scope ogf the business
  As a market analyst
  I want to move between pages

  Scenario: Navigate to items page
    Given I enter the marketplace backoffice
    And I go to the buyers details
    When I click on "Go to items"
    Then I see the list of items with their details

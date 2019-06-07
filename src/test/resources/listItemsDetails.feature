@e2e
Feature: View list of items details
  In order to know where items are sold
  As a market analyst
  I want to get a list of items and their details

  Scenario: View list of items
    Given I enter the marketplace backoffice
    When I go to the items details
    Then I see the list of items with their details

@e2e
Feature: View list of buyers details
  In order to study my buyers behaviour
  As a market analyst
  I want to get a list of buyers and their details

  Scenario: View list of buyers
    Given I enter the marketplace backoffice
    When I go to the buyers details
    Then I see the list of buyers with their details
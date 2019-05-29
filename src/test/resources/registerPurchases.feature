@e2e
Feature: Register purchases
  In order to know buyers behaviour
  As the marketplace system
  I want to register purchases

  Scenario: Register a new purchase to new buyer
    Given a new buyer comes to the store
    And we have the following item in the marketplace:
      | id | title                  | store name          |
      | 6  | Grays Sports Almanac   | Blast from the Past |
    When he purchases the item
    Then the purchase is registered in the system

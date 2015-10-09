Feature: Losango

  Scenario: Calculate a losango
    Given I have latitude 1 and longitude 1
    When I request a losango
    Then I should see "271#741"
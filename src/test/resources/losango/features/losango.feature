Feature: Losango

  Scenario: Calculate a losango
    Given I have latitude 1 and longitude 1
    When I request a losango
    Then I should see "34513#91863"
    And The tile "34513#91863" should have being stored

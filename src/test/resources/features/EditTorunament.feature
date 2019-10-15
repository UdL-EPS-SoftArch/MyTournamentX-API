Feature: Edit Tournament
  In order to allow edit a tournament
  As an admin
  I want to edit a tournament settings

  Scenario: Edit tournament as admin Good!
    Given I login as "demoTM" with password "password"
    And I register a new tournament with name "FirstTournament"
    When I edit tournament with name "FirstTournament",level "AMATEUR" and game "LoL"
    Then The response code is 200
    And It has been edited a tournament with name "FirstTournament"

Feature: Edit Tournament
  In order to allow edit a tournament
  As an admin
  I want to edit a tournament settings


  Scenario: Edit tournament as Tournament Master
    Given I login as "demoTM" with password "password"
    And I register a new tournament with name "FirstTournament",level "AMATEUR" and game "LoL"
    When I edit tournament with name "FirstTournament",level "AMATEUR" and game "LoL"
    Then The response code is 200
    And It has been edited a tournament with name "FirstTournament",level "AMATEUR" and game "LoL"

  Scenario: Edit tournament as user
    Given I login as "demoP" with password "password"
    And I register a new tournament with name "FirstTournament",level "AMATEUR" and game "LoL"
    When I edit tournament with name "FirstTournament",level "AMATEUR" and game "LoL"
    Then The response code is 403
    And It has not been edited a tournament with name "FirstTournament",level "AMATEUR" and game "LoL"

  Scenario: Edit tournament as user
    Given I login as "demoTM" with password "password"
    And I register a new tournament with name "FirstTournament",level "AMATEUR" and game "LoL"
    Then I login as "demoP" with password "password"
    When I edit tournament with name "FirstTournament",level "AMATEUR" and game "LoL"
    Then The response code is 403
    And  It has been edited a tournament with name "FirstTournament",level "AMATEUR" and game "LoL"
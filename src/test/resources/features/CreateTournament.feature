Feature: Tournament
  In order to use the app
  As a tournament master
  I want to create a tournament


  Scenario: Create new tournament
    Given There is no tournament with name "FirstTournament"
    And I'm not logged in
    When I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL"
    Then The response code is 401
    And It has not been created a tournament with name "FirstTournament", level "AMATEUR" and game "LoL"

  Scenario: Create new tournament as a Tournament Master
    Given There is no tournament with name "FirstTournament"
    And I login as "demoTM" with password "password"
    When I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL"
    Then The response code is 201
    And It has been created a tournament with name "FirstTournament", level "AMATEUR" and game "LoL"

  Scenario: Create existing tournament name
    Given There is a tournament with name "FirstTournament", level "AMATEUR" and game "LoL"
    And I login as "demoTM" with password "password"
    When I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL"
    Then The response code is 409
    And It has not been created a tournament with name "FirstTournament", level "AMATEUR" and game "LoL"

  Scenario: Create tournament with empty name
    Given I login as "demoTM" with password "password"
    When I create a new tournament with name "", level "AMATEUR" and game "LoL"
    Then The response code is 400
    And The error message is "must not be blank"
    And There is no tournament with name "FirstTournament"

  Scenario: Create tournament with empty level
    Given I login as "demoTM" with password "password"
    When I create a new tournament with name "FirstTournament", level "" and game "LoL"
    Then The response code is 400
    And The error message is "Level must not be Null"
    And It has not been created a tournament with name "FirstTournament", level "AMATEUR" and game "LoL"

  Scenario: Create tournament with empty game
    Given I login as "demoTM" with password "password"
    When I create a new tournament with name "FirstTournament", level "AMATEUR" and game ""
    Then The response code is 400
    And The error message is "must not be blank"
    And It has not been created a tournament with name "FirstTournament", level "AMATEUR" and game "LoL"



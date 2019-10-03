Feature: Tournament
  In order to use the app
  As a tournament master
  I want to create a tournament

  Scenario: Create new tournament
    Given There is no tournament with name "FirstTournament"
    When I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL"
    Then The response code is 201
    And It has been created a tournament with name "FirstTournament", level "AMATEUR" and game "LoL"

  Scenario: Create existing tournament name
    Given There is a tournament with name "FirstTournament"
    When I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL"
    Then The response code is 409
    And I cannot create a tournament with name "FirstTournament", level "AMATEUR" and game "LoL"


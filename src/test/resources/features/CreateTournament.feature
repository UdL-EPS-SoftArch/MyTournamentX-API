Feature: Tournament
  In order to use the app
  As a tournament master
  I want to create a tournament

  Background:
    Given There is a Tournament Master with user "TournamentMaster" and password "password"


  Scenario: Create new tournament
    Given There is no tournament with name "FirstTournament"
    And I'm not logged in
    When I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL"
    Then The response code is 409
    And There is no tournament with name "FirstTournament"

  Scenario: Create new tournament
    Given There is no tournament with name "FirstTournament"
    And I log as "TournamentMaster" with password "password"
    When I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL"
    Then The response code is 201
    And It has been created a tournament with name "FirstTournament", level "AMATEUR" and game "LoL"

  Scenario: Create existing tournament name
    Given There is a tournament with name "FirstTournament"
    And I log as "TournamentMaster" with password "password"
    When I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL"
    Then The response code is 409
    And There is no tournament with name "FirstTournament"

  Scenario: Create tournament when already authenticated
    Given I log as "TournamentMaster" with password "password"
    When I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL"
    Then The response code is 201
    And It has been created a tournament with name "FirstTournament"

  Scenario: Create tournament with empty name
    Given I log as "TournamentMaster" with password "password"
    When I create a new tournament with name "", level "AMATEUR" and game "LoL"
    Then The response code is 400
    And The error message is "must not be blank"
    And There is no tournament with name "FirstTournament"

  Scenario: Create tournament with empty level
    Given I log as "TournamentMaster" with password "password"
    When I create a new tournament with name "FirstTournament", level "" and game "LoL"
    Then The response code is 400
    And The error message is "must not be blank"
    And There is no tournament with name "FirstTournament"

  Scenario: Create tournament with empty game
    Given I log as "TournamentMaster" with password "password"
    When I create a new tournament with name "FirstTournament", level "AMATEUR" and game ""
    Then The response code is 400
    And The error message is "must not be blank"
    And There is no tournament with name "FirstTournament"


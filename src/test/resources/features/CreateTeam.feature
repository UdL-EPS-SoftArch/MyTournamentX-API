Feature: Create Team
  In order to use the app
  As a player
  I want to create a team

  Scenario: Create new team
    Given I login as "demoP" with password "password"
    And There is no registered team with name "team"
    When I register a new team with name "team", game "game", level "level", maxPlayers 8
    Then The response code is 201
    And It has been created a team with name "team", game "game", level "level", maxPlayers 8

  Scenario: Create new team with Tournament Master
    Given I login as "demoTM" with password "password"
    And There is no registered team with name "team"
    When I register a new team with name "team", game "game", level "level", maxPlayers 8
    Then The response code is 403
    And I cannot create a team with name "team"

  Scenario: Create new team with existing name
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8
    And I login as "demoP" with password "password"
    When I register a new team with name "team", game "game", level "level", maxPlayers 8
    Then The response code is 409
    And I cannot create a team with name "team"

  Scenario: Create new team with blank name
    Given I login as "demoP" with password "password"
    When I register a new team with name "", game "futbol", level "amateur", maxPlayers 7
    Then The response code is 400
    And The error message is "must not be blank"
    And I cannot create a team with name ""

  Scenario: Create new team with outnumber maxPlayers
    Given I login as "demoP" with password "password"
    When I register a new team with name "team", game "game", level "amateur", maxPlayers 80
    Then The response code is 400
    And The error message is "must be less than or equal to 8"
    And I cannot create a team with name "team"

  Scenario: Create new team with zero maxPlayers
    Given I login as "demoP" with password "password"
    When I register a new team with name "team", game "game", level "amateur", maxPlayers 0
    Then The response code is 400
    And The error message is "must be greater than or equal to 1"
    And I cannot create a team with name "team"

  Scenario: Create new team without authentication
    Given I'm not logged in
    And There is no registered team with name "team"
    When I register a new team with name "team", game "game", level "level", maxPlayers 8
    Then The response code is 401
    And I cannot create a team with name "team"

  Scenario: Create new team with blank game
    Given I login as "demoP" with password "password"
    When I register a new team with name "team", game "", level "amateur", maxPlayers 7
    Then The response code is 400
    And The error message is "must not be blank"
    And I cannot create a team with name "name", game "", level "amateur", maxPlayers 7
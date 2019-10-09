Feature: Register
  In order to use the app
  As a tournament master
  I want to register myself and get an account

  Scenario: Register new tournamentmaster
    Given There is no registered tournamentmaster with username "tournamentmaster"
    And I'm not logged in
    When I register a new tournamentmaster with username "tournamentmaster", email "tournamentmaster@mytournamentx.game" and password "password"
    Then The response code is 201
    And It has been created a tournamentmaster with username "tournamentmaster" and email "tournamentmaster@mytournamentx.game", the password is not returned
    And I can login with username "tournamentmaster" and password "password"

  Scenario: Register existing username
    Given There is a registered tournamentmaster with username "tournamentmaster" and password "password" and email "tm@mytournamentx.game"
    And I'm not logged in
    When I register a new tournamentmaster with username "tournamentmaster", email "tournamentmaster@mytournamentx.game" and password "newpassword"
    Then The response code is 409
    And I cannot login with username "tournamentmaster" and password "newpassword"

  Scenario: Register tournamentmaster when already authenticated
    Given I login as "demoTM" with password "password"
    When I register a new tournamentmaster with username "tournamentmaster", email "tournamentmaster@mytournamentx.game" and password "password"
    Then The response code is 403
    And It has not been created a tournamentmaster with username "tournamentmaster"

  Scenario: Register tournamentmaster with empty password
    Given I'm not logged in
    When I register a new tournamentmaster with username "tournamentmaster", email "tournamentmaster@mytournamentx.game" and password ""
    Then The response code is 400
    And The error message is "must not be blank"
    And It has not been created a tournamentmaster with username "tournamentmaster"

  Scenario: Register tournamentmaster with empty email
    Given I'm not logged in
    When I register a new tournamentmaster with username "tournamentmaster", email "" and password "password"
    Then The response code is 400
    And The error message is "must not be blank"
    And It has not been created a tournamentmaster with username "tournamentmaster"

  Scenario: Register tournamentmaster with invalid email
    Given I'm not logged in
    When I register a new tournamentmaster with username "tournamentmaster", email "playeramytournamentx.game" and password "password"
    Then The response code is 400
    And The error message is "must be a well-formed email address"
    And It has not been created a tournamentmaster with username "tournamentmaster"

  Scenario: Register tournamentmaster with password shorter than 8 characters
    Given I'm not logged in
    When I register a new tournamentmaster with username "tournamentmaster", email "tournamentmaster@mytournamentx.game" and password "pass"
    Then The response code is 400
    And The error message is "length must be between 8 and 256"
    And It has not been created a tournamentmaster with username "tournamentmaster"

  Scenario: Register tournamentmaster with an existing email
    Given There is a registered tournamentmaster with username "tournamentmaster" and password "password" and email "tm@mytournamentx.game"
    And I'm not logged in
    When I register a new tournamentmaster with username "tournamentmaster2", email "tm@mytournamentx.game" and password "password2"
    Then The response code is 409
    And I can login with username "tournamentmaster" and password "password"

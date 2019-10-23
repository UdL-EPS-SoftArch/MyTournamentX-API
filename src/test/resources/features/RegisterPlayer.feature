Feature: Register
  In order to use the app
  As a player
  I want to register myself and get an account

  Scenario: Register new player
    Given There is no registered player with username "player"
    And I'm not logged in
    When I register a new player with username "player", email "player@mytournamentx.game" and password "password"
    Then The response code is 201
    And It has been created a player with username "player" and email "player@mytournamentx.game", the password is not returned
    And I can login with username "player" and password "password"

  Scenario: Register existing username
    Given There is a registered player with username "player" and password "existing" and email "player@mytournamentx.game"
    And I'm not logged in
    When I register a new player with username "player", email "player@mytournamentx.game" and password "newpassword"
    Then The response code is 409
    And I cannot login with username "player" and password "newpassword"

  Scenario: Register player when already authenticated
    Given I login as "demoP" with password "password"
    When I register a new player with username "player", email "player@mytournamentx.game" and password "password"
    Then The response code is 403
    And It has not been created a player with username "player"

  Scenario: Register player with empty password
    Given I'm not logged in
    When I register a new player with username "player", email "player@mytournamentx.game" and password ""
    Then The response code is 400
    And The error message is "must not be blank"
    And It has not been created a player with username "player"

  Scenario: Register player with empty email
    Given I'm not logged in
    When I register a new player with username "player", email "" and password "password"
    Then The response code is 400
    And The error message is "must not be blank"
    And It has not been created a player with username "player"

  Scenario: Register player with invalid email
    Given I'm not logged in
    When I register a new player with username "player", email "playeramytournamentx.game" and password "password"
    Then The response code is 400
    And The error message is "must be a well-formed email address"
    And It has not been created a player with username "player"

  Scenario: Register player with password shorter than 8 characters
    Given I'm not logged in
    When I register a new player with username "player", email "player@mytournamentx.game" and password "pass"
    Then The response code is 400
    And The error message is "length must be between 8 and 256"
    And It has not been created a player with username "player"

  Scenario: Register player with an existing email
    Given There is a registered player with username "player" and password "password" and email "player@mytournamentx.game"
    And I'm not logged in
    When I register a new player with username "player2", email "player@mytournamentx.game" and password "password2"
    Then The response code is 409
    And I can login with username "player" and password "password"
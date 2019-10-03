Feature: Create Team
  In order to use the app
  As a player
  I want to create a team

  Scenario: Create new team
    Given There is no registered team with name "name"
    And I'm a player with name "name"
    When I register a new team with name "team", game "game", level "level", maxPlayers 8
    Then It has been created a team with name "team", game "game", level "level", maxPlayers 8




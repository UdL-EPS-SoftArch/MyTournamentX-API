Feature: Join player or players to team
  In order to use the app
  As a player
  I want to join a team


  Scenario: Join player to team that does not exist
    Given I login as "demoP" with password "password"
    And There is no registered team with name "team"
    When I try to join a not created team with name "team"
    Then The response code is 404
    And I cannot join the team with name "name"

    
  Scenario: Join player to team that exist
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    And I login as "demoP" with password "password"
    When I am player "demoP" and I want to join to the team with name "team"
    Then The response code is 204
    And I can join the team with name "team"


  Scenario: Unsuccessfully joined a match
    Given I'm not logged in
    And There is a created team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    When I am player "demoP" and I want to join to the team with name "team"
    Then The response code is 401


  Scenario: I want to leave a team
    Given I login as "demoP" with password "password"
    And I already joined a team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    When I leave the team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    Then I successfully leave the team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"


  Scenario: Joining to two different teams
    Given I login as "demoP" with password "password"
    And There is a team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    And There is a team with name "team2", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    When I am player "demoP" and I want to join to the team with name "team"
    And I am player "demoP" and I want to join to the team with name "team2"
    Then The response code is 200

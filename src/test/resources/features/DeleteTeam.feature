Feature: Delete Team
  In order to use the app
  As a player
  I want to delete a team

  Scenario: Delete an existing team
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    And I login as "demoP" with password "password"
    And It has been created a team with name "team", game "game", level "level", maxPlayers 8
    And I am the leader of the team with name "team" and my username is "demoP"
    When I delete the team called "team"
    Then The response code is 204
    And It has been deleted a team name "team"

  Scenario: Delete an existing team login without leader role
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    And I login as "demoP1" with password "password"
    And It has been created a team with name "team", game "game", level "level", maxPlayers 8
    When I delete the team called "team"
    Then The response code is 403
    And I cannot delete team "team"

  Scenario: Delete a not existing team
    Given There is no registered team with name "team"
    And I login as "demoP" with password "password"
    When I delete the team called "team"
    Then The response code is 404
    And I cannot delete team "team", because it doesn't exist

  Scenario: Delete an existing team logged as Tournament Master
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8
    And I login as "demoTM" with password "password"
    When I delete the team called "team"
    Then The response code is 403
    And I cannot delete team with name "team",game "game", level "level", maxPlayers 8, because it doesn't have permission

  Scenario: Delete an existing team logged without loggin
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8
    And I'm not logged in
    When I delete the team called "team"
    Then The response code is 401
    And I cannot delete team with name "team",game "game", level "level", maxPlayers 8, because it doesn't have permission
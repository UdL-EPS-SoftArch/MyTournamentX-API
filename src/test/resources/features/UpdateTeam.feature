Feature: Update Team
  In order to use the app
  As a leader of a team
  I want to update the information of my team

  Scenario: Update game of my team
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    And I login as "demoP" with password "password"
    When I change the game of my team "team" to "game2"
    Then The response code is 200
    And It has been changed game of team "team" to "game2"

  Scenario: Update level of my team
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    And I login as "demoP" with password "password"
    When I change the level of my team "team" to "Begginer"
    Then The response code is 200
    And It has been changed level of team "team" to "Begginer"

  Scenario: Update maxPlayers of my team
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    And I login as "demoP" with password "password"
    When I change the maxPlayers of my team "team" to 5
    Then The response code is 200
    And It has been changed maxPlayers of team "team" to 5

  Scenario: Update maxPlayers of my team to an invalid number
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    And I login as "demoP" with password "password"
    When I change the maxPlayers of my team "team" to 50
    Then The response code is 400
    And The error message is "must be less than or equal to 8"
    And I cannot change maxPlayers of team "team" to 50, because is an invalid number

  Scenario: Update game of my team to blank
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    And I login as "demoP" with password "password"
    When I change the game of my team "team" to ""
    Then The response code is 400
    And The error message is "must not be blank"
    And I cannot change game of team "team" to ""

  Scenario: Update level of my team to blank
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    And I login as "demoP" with password "password"
    When I change the level of my team "team" to ""
    Then The response code is 400
    And The error message is "length must be between 1 and 256"
    And I cannot change level of team "team" to ""

  Scenario: Update level of my team without login
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    And I'm not logged in
    When I change the level of my team "team" to "Begginer"
    Then The response code is 401
    And I cannot change level of team "team" to "Begginer"

  Scenario: Update level of my team with TournamentMaster
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    And I login as "demoTM" with password "password"
    When I change the level of my team "team" to "Begginer"
    Then The response code is 403
    And I cannot change level of team "team" to "Begginer"


  Scenario: Update an existing team login without leader role
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    And I login as "demoP1" with password "password"
    And It has been created a team with name "team", game "game", level "level", maxPlayers 8
    When I change the level of my team "team" to "Begginer"
    Then The response code is 403
    And I cannot change level of team "team" to "Begginer"
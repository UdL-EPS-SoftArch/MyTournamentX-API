Feature: Update Team
  In order to use the app
  As a leader of a team
  I want to update the information of my team

  Scenario: Update game of my team
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8
    And I login as "demoP" with password "password"
    When I change the game of my team "team" to "game2"
    Then The response code is 200
    And It has been changed game of team "team" to "game2"

  Scenario: Update level of my team
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8
    And I login as "demoP" with password "password"
    When I change the level of my team "team" to "Begginer"
    Then The response code is 200
    And It has been changed level of team "team" to "Begginer"

  Scenario: Update maxPlayers of my team
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8
    And I login as "demoP" with password "password"
    When I change the maxPlayers of my team "team" to 5
    Then The response code is 200
    And It has been changed maxPlayers of team "team" to 5

  Scenario: Update maxPlayers of my team to an invalid number
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8
    And I login as "demoP" with password "password"
    When I change the maxPlayers of my team "team" to 50
    Then The response code is 400
    And I cannot change maxPlayers of team "team" to 50, because is an invalid number
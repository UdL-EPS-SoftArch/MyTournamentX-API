Feature: Update Team
  In order to use the app
  As a leader of a team
  I want to update the information of my team

  Scenario: Update name of my team
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8
    And I login as "demoP" with password "password"
    When I change the game of my team "team" to "game2"
    Then The response code is 200
    And It has been changed game of team "team" to "game2"
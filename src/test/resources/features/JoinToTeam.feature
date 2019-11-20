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
    When I try to join a created team with name "team"
    Then The response code is 200
    And I can join the team with name "name"

  Scenario: Join player to team that exist with invitation
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    And I login as "demoP" with password "password"
    And I have been invited to a team with name "name" and message "Invitation to my team"
    When I try to join a created team with name "team"
    And I don't have invitation to team "team"
    Then The response code is 403
    And I cannot join the team with name "name"
    
  Scenario: Join player to team that exist without invitation
    Given There is a created team with name "team", game "game", level "level", maxPlayers 8, and the team leader is "demoP"
    And I login as "demoP" with password "password"
    When I try to join a created team with name "team"
    And I don't have invitation to team "team"
    Then The response code is 403
    And I cannot join the team with name "name"
    
    
Feature: Join player or players to team
  In order to use the app
  As a player
  I want to join a team


  Scenario: Join player to team that does not exist
    Given I login as "demoP" with password "password"
    And There is no registered team with name "team"
    When I try to join team with name "team"
    Then The response code is 404
    And I cannot join the team with name "name"

  Scenario: Join player to team that exist
    Given I login as "demoP" with password "password"
    And There is a created team with name "team"
    When I try to join team with name "team"
    Then The response code is 201
    And I can join the team with name "name"
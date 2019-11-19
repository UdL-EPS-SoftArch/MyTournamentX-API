Feature: In order to play a tournament
  As a team leader
  I want to be able to join my team to a tournament

  Scenario: Join my team to a tournament
    Given There is a created team with name "team", game "LoL", level "AMATEUR", maxPlayers 8, and the team leader is "demoP"
    And I login as "demoP" with password "password"
    And There is a tournament with name "FirstTournament", level "AMATEUR", game "LoL" and bestof "1"
    When Team leader join his team called "team" to a tournament called "FirstTournament"
    Then The response code is 204
    And There is a team called "team" in a tournament called "FirstTournament"
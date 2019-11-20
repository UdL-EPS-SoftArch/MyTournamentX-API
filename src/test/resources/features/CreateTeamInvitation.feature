Feature: Create team invitation
  In order to use the app
  As a user
  I want to invite another user to my team

  Background:
    Given There is a registered player with username "player" and password "existing" and email "player@mytournamentx.game"

  Scenario: Invite existing user that doesn't participate in my team
    Given The userId "userId" is correct
    And   The teamId "teamId" is correct
    And   The user "userId" is not in the team "teamId"
    When  I create the invitation for the user "userId" to participate in team "teamId"
    Then  The sever response code is 201
    And   The invitation has been created for the user "userId" for the team "teamId"

  Scenario: Invite none existing user to my team
    Given I login as "demoPlayer" with password "demoPassword"
    And The userId "demoPlayer" is not correct
    And There is a created team with name "demoTeam", game "demoGame", level "demoLevel", maxPlayers 8
    And There is empty room in team "demoTeam"
    When I create the invitation for the user "demoPlayer" to participate in team "demoTeam"
    Then  The sever response code is 404

  Scenario: Invite existing user to a team that does not exist
    Given I login as "demoP" with password "password"
    And There is a registered player with username "player2" and password "existing2" and email "player2@mytournamentx.game"
    And There is no registered team with name "teamId"
    When I create the invitation for the user "player2" to participate in team "teamId"
    Then The sever response code is 400
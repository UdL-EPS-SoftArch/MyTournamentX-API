Feature: Create team invitation
  In order to use the app
  As a user
  I want to invite another user to my team
  Background:
    Given There is a registered player with username "player" and password "existing" and email "player@mytournamentx.game"

  Scenario: Invite existing user that doesn't participate in my team
    Given I login as "player" with password "existing"
    And   There is a registered player with username "invitedUsername" and password "invitedPassword" and email "invitedEmail@gmail.com"
    And   I register a new team with name "demoTeam", game "demoGame", level "demoLevel", maxPlayers 8
    And   The user "player@mytournamentx.game" is in the team "demoTeam"
    And   The user "invitedEmail@gmail.com" is not in the team "demoTeam"
    And   There is empty room in the team "demoTeam"
    When  I create the invitation for the user "invitedUsername" to participate in team "demoTeam"
    Then   The invitation has been created for the user "invitedUsername" for the team "demoTeam"
    And  The sever response code is 200

  Scenario: Invite none existing user to my team
    Given I login as "demoPlayer" with password "demoPassword"
    And The userId "demoPlayer" is not correct
    And There is a created team with name "demoTeam", game "demoGame", level "demoLevel", maxPlayers 8
    And There is empty room in the team "demoTeam"
    When I create the invitation for the user "demoPlayer" to participate in team "demoTeam"
    Then  The sever response code is 404

  Scenario: Invite existing user to a team that does not exist
    Given I login as "demoP" with password "password"
    And There is a registered player with username "player2" and password "existing2" and email "player2@mytournamentx.game"
    And There is no registered team with name "teamId"
    When I create the invitation for the user "player2" to participate in team "teamId"
    Then The sever response code is 400
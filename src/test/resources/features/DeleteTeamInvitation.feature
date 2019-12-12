Feature: Update team invitation
  In order to use the app
  As a user
  I want to delete created invitations
  Background:
    Given There is a registered player with username "player" and password "existing" and email "player@mytournamentx.game"
    And   There is a registered player with username "invitedUsername" and password "invitedPassword" and email "invitedEmail@gmail.com"
    And I login as "player" with password "existing"
    And I register a new team with name "demoTeam", game "demoGame", level "demoLevel", maxPlayers 8

  Scenario: Update existing invitation being the creation user
    Given The user "player@mytournamentx.game" is in the team "demoTeam"
    And   The user "invitedEmail@gmail.com" is not in the team "demoTeam"
    And   There is empty room in the team "demoTeam"
    And  I create the invitation for the user "invitedEmail@gmail.com" to participate in team "demoTeam"
    And  The invitation has been created for the user "invitedEmail@gmail.com" for the team "demoTeam"
    When I delete the invitation for the user "invitedEmail@gmail.com" for the team "demoTeam"
    Then The invitation for the user "invitedEmail@gmail.com" for the team "demoTeam" has been deleted
    And  The server response code is 404

  Scenario: Update existing invitation being the invited user
    Given The user "player@mytournamentx.game" is in the team "demoTeam"
    And   The user "invitedEmail@gmail.com" is not in the team "demoTeam"
    And   There is empty room in the team "demoTeam"
    And  I create the invitation for the user "invitedEmail@gmail.com" to participate in team "demoTeam"
    And  The invitation has been created for the user "invitedEmail@gmail.com" for the team "demoTeam"
    And I login as "invitedUsername" with password "invitedPassword"
    When I delete the invitation for the user "invitedEmail@gmail.com" for the team "demoTeam"
    Then The invitation for the user "invitedEmail@gmail.com" for the team "demoTeam" has been deleted
    And  The server response code is 404

  Scenario: Update existing invitation not being creation or invited user
    Given The user "player@mytournamentx.game" is in the team "demoTeam"
    And   The user "invitedEmail@gmail.com" is not in the team "demoTeam"
    And   There is empty room in the team "demoTeam"
    And  I create the invitation for the user "invitedEmail@gmail.com" to participate in team "demoTeam"
    And  The invitation has been created for the user "invitedEmail@gmail.com" for the team "demoTeam"
    And   There is a registered player with username "notInvitedUsername" and password "notInvitedPassword" and email "notInvitedEmail@gmail.com"
    And I login as "notInvitedUsername" with password "notInvitedPassword"
    When I delete the invitation for the user "invitedEmail@gmail.com" for the team "demoTeam"
    Then The invitation for the user "invitedEmail@gmail.com" for the team "demoTeam" has not been deleted
    And  The server response code is 200

  Scenario: Update non existing invitation not being creation or invited user
    Given The user "player@mytournamentx.game" is in the team "demoTeam"
    And   The user "invitedEmail@gmail.com" is not in the team "demoTeam"
    And   There is empty room in the team "demoTeam"
    When I delete the invitation for the user "invitedEmail@gmail.com" for the team "demoTeam"
    Then The invitation for the user "invitedEmail@gmail.com" for the team "demoTeam" has not been deleted
    And  The server response code is 200
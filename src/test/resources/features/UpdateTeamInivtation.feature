Feature: Update team invitation
  In order to use the app
  As a user
  I want to edit created invitations
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
    When I modify the invitation for the user "invitedEmail@gmail.com" for the team "demoTeam" with the new message "message"
    Then The invitation for the user "invitedEmail@gmail.com" for the team "demoTeam" has been modified with the message = "message"
    And  The server response code is 200

  Scenario: Update existing invitation being the invited user
    Given The user "player@mytournamentx.game" is in the team "demoTeam"
    And   The user "invitedEmail@gmail.com" is not in the team "demoTeam"
    And   There is empty room in the team "demoTeam"
    And  I create the invitation for the user "invitedEmail@gmail.com" to participate in team "demoTeam"
    And  The invitation has been created for the user "invitedEmail@gmail.com" for the team "demoTeam"
    When I modify the invitation for the user "invitedEmail@gmail.com" for the team "demoTeam" with the accepted status = "true"
    Then The invitation for the user "invitedEmail@gmail.com" for the team "demoTeam" has been modified with the accepted status "true"
    And  The server response code is 200

  Scenario: Update existing invitation not being creation or invited user
    Given The user "player@mytournamentx.game" is in the team "demoTeam"
    And   The user "invitedEmail@gmail.com" is not in the team "demoTeam"
    And   There is empty room in the team "demoTeam"
    And  I create the invitation for the user "invitedEmail@gmail.com" to participate in team "demoTeam"
    And  The invitation has been created for the user "invitedEmail@gmail.com" for the team "demoTeam"
    And   There is a registered player with username "notInvitedUsername" and password "notInvitedPassword" and email "notInvitedEmail@gmail.com"
    And I login as "notInvitedUsername" with password "notInvitedPassword"
    When The invitation for the user "invitedEmail@gmail.com" for the team "demoTeam" has not been modified
    And  The server response code is 404

  Scenario: Update non existing invitation not being creation or invited user
    Given The user "player@mytournamentx.game" is in the team "demoTeam"
    And   The user "invitedEmail@gmail.com" is not in the team "demoTeam"
    And   There is empty room in the team "demoTeam"
    When The invitation for the user "invitedEmail@gmail.com" for the team "demoTeam" has not been modified
    And  The server response code is 404


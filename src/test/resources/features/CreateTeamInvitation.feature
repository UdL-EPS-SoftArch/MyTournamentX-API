Feature: Create team invitation
  In order to use the app
  As a user
  I want to invite another user to my team

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
    Given The userId "userId" is correct
    And   The teamId "teamId" is not correct
    When  I create the invitation for the user "userId" to participate in team "teamId"
    Then  The sever response code is 404
    And I cannot create a invitation for the user "userId" for the team "teamId"

  Scenario: Invite existing user to a full team

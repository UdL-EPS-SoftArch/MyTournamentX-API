Feature: Create Tournament Invitation
  In order to allow a player to send invitation to another player
  As an user
  I want to send invitations

  Scenario: Create invitation
    Given I login as "user" with password "password"
    When I create an invitation with message "Want to join?"
    Then The response code is 201
    And Exists an invitation with message "Want to join?"

  Scenario: Create invitation while not logged in
    Given I'm not logged in
    When I create an invitation with message "Want to join?"
    Then The response code is 401
    And And it doesn't exist an invitation with message "Want to join?"

  Scenario: Create invitation without message
    Given I login as "user" with password "password"
    When I create an invitation with no message
    Then The response code is 400
    And The error message is "must not be blank"
    And And it exists "0" invitations

  Scenario: Create invitation as admin
    Given I login as "admin" with password "password"
    When I create an invitation with message "Want to join?"
    Then The response code is 403
    And And it exists "0" invitations

  Scenario: Create invitation with a too long message
    Given I login as "user" with password "password"
    When I create an invitation with a 300 chars long message
    Then The response code is 400
    And The error message is "size must be between 1 and 255"
    And And it exists "0" invitations

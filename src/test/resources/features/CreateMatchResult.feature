Feature: CreateMatchResult
  In order to use the app
  As a user
  I want to see the result of a Match

  Background:
    Given There is a match
    And There is a team
    And There is a round
    And There is a match

  Scenario: Assign a match to a MatchResult
    Given   There is no registered matchResult for this Match
    And   I login as "demoP" with password "password"
    When  I register a new MatchResult with Description "description"
    Then  The response code is 201
    And   There is a registered MatchResult with "description" for this match

  Scenario: Register a result with correct parameters (Winner, Description)
    Given There is no registered matchResult for this Match
    And I login as "demoP" with password "password"
    When I register a new MatchResult with a Winner and Description "description"
    Then The response code is 201
    And It has been created a MatchResult with a Winner and Description "description"

  Scenario: Register a result with wrong Winner parameter
    Given  There is no registered matchResult for this Match
    And I login as "demoP" with password "password"
    When I try to register a new result with an invalid Winner
    Then The response code is 400
    And The object is not created


  Scenario: Replace a result with correct parameters (Winner, Description)
    Given It has been created a MatchResult with a Winner and Description "description"
    And I login as "demoP" with password "password"
    When I register a new MatchResult with a Winner and Description "description"
    Then The response code is 201
    And It has been deleted the last MatchResult sent in that Match
    And It has been created a MatchResult with a Winner and Description "description"

  Scenario: Register a result without being correctly registered
    Given I'm not logged in
    When I register a new MatchResult with a Winner and Description "description"
    Then The response code is 401
    And The object is not created






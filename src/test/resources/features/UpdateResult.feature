Feature: CreateMatchResult
  In order to use the app
  As a user
  I want to see the result of a Match

  Background:
    Given There are some matchresults
    And There is a match
    And There is a round


  Scenario: Assign various matches to a Round and assign a MatchResult for each team in a Match
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
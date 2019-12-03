Feature: UpdateMatch
  In order to use the app
  As a user
  I want to see the winner of a Match and its result

  Background:
    Given There are some matchresults
    And There is a match
    And There is a round


  Scenario: Register the winner of a Match with at least half plus one of the matchresults containing the same winner
    Given At least half plus one of the matchresults of the match contain the same winner
    When I compare all the matchresults of the match
    And I set "Winner" as winner
    Then The response code is 201
    And The winner of the Match is updated

  Scenario: Try to register the winner of a Match without half plus one of the matchresults containing the same winner
    Given Less of half of the matchresults of the match contain the same winner "Winner"
    When I compare all the matchresults of the match
    And I set "Winner" as winner
    Then The response code is <number>
    And The winner of the Match is not updated
    And Throws an exception

  Scenario: Try to register the winner of a Match without at half plus one of the matchresults
    Given Less of half plus one of the matchresults
    When I compare all the matchresults of the match
    And I set "Winner" as winner
    Then The response code is <number>
    And Throws an exception


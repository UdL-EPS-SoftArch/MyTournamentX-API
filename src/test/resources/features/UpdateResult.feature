Feature: UpdateMatch
  In order to use the app
  As a user
  I want to see the winner of a Match and its result

  Background:
    Given There is a tournament with name "FirstTournament", level "AMATEUR", game "LoL" and bestof "1" UpdateResult
    And I login as "demoP" with password "password"
    And There is a created team with name "team", game "LoL", level "AMATEUR", maxPlayers "1", and the team leader is "demoP" UpdateResult
    And There is a created team with name "team2", game "LoL", level "AMATEUR", maxPlayers "1", and the team leader is "demoP" UpdateResult
    And There is a round with Round "null", bestof "1", numTeams "1", List<Team> "team,team2", tournament "FirstTournament"
    And There is a match UpdateResult


  Scenario: Register the winner of a Match with at least half plus one of the matchresults containing the same winner
    When I post a  matchResult with Match "match", Team "Sender", Team "Winner" UpdateResult
    Then The response code is 201
    And The winner of the Match is set

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


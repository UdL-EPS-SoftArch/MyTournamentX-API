Feature: UpdateMatch
  In order to use the app
  As a user
  I want to see the winner of a Match and its result


  Scenario: Register the winner of a Match with at least half plus one of the matchresults containing the same winner
    Given There is a tournament with name "FirstTournament", level "AMATEUR", game "LoL" and bestof "1" UpdateResult
    And I login as "demoP" with password "password"
    And There is a created team with name "team", game "LoL", level "AMATEUR", maxPlayers "1", and the team leader is "demoP" UpdateResult
    And There is a round with Round "null", bestof "1", numTeams "1", List<Team> "team", tournament "FirstTournament"
    And There is a match UpdateResult
    When I post a matchResult with Match "match", Team "Sender", Team "Winner" UpdateResult
    Then The response code is 201
    And The winner of the Match is set
    And The winner of the Round is set
    And The winner of the Tournament is set

  Scenario: Try to register the winner of a Match without half plus one of the matchresults containing the same winner
    Given There is a tournament with name "SecondTournament", level "AMATEUR", game "LoL" and bestof "3" UpdateResult
    And I login as "demoP" with password "password"
    And There is a created different number of teams
    And There is a round created for SecondTournament
    And There is a number of matchs for the round
    When I post nine MatchResults
    Then The response code is 201
    And The winner of the Matchs are set
    And The winner of the Round for the Matches is set
    And The winner of the Tournament for the Round is set

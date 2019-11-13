Feature: Edit Tournament
  In order to allow edit a tournament
  As an admin
  I want to edit a tournament settings


  Scenario: Edit tournament as Tournament Master
    Given I login as "demoTM" with password "password"
    And I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL" and bestof "1"
    When I edit tournament with name "FirstTournament",level "AMATEUR" and game "LoL"
    Then The response code is 200
    And It has been edited a tournament with name "FirstTournament",level "AMATEUR" and game "LoL"

  Scenario: Edit tournament as player
    Given I login as "demoP" with password "password"
    And I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL" and bestof "1"
    When I edit tournament with name "FirstTournament",level "AMATEUR" and game "LoL"
    Then The response code is 403
    And It has not been edited a tournament with name "FirstTournament",level "AMATEUR" and game "LoL"

  Scenario: Edit tournament as player
    Given I login as "demoTM" with password "password"
    And I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL" and bestof "1"
    Then I login as "demoP" with password "password"
    When I edit tournament with name "FirstTournament",level "AMATEUR" and game "LoL"
    Then The response code is 403
    And  It has been edited a tournament with name "FirstTournament",level "AMATEUR" and game "LoL"

  Scenario: Edit tournament without authenticating
    Given I'm not logged in
    When I edit tournament with name "FirstTournament",level "AMATEUR" and game "LoL"
    Then The response code is 401
    And It has not been edited a tournament with name "FirstTournament",level "AMATEUR" and game "LoL"

  Scenario: Edit tournament level as Tournament Master
    Given I login as "demoTM" with password "password"
    And I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL" and bestof "1"
    When I edit tournament with name "FirstTournament" and new level "AMATEUR"
    Then The response code is 200
    And It has been edited a tournament with name "FirstTournament",level "AMATEUR" and game "LoL"

  Scenario: Edit tournament does not exists
    Given I login as "demoTM" with password "password"
    When I edit tournament with name "FirstTournament",level "AMATEUR" and game "LoL"
    Then The response code is 404
    And It has not been edited a tournament with name "FirstTournament",level "AMATEUR" and game "LoL"

  Scenario: Edit tournament to change the starting time
    Given I login as "demoTM" with password "password"
    And I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL" and bestof "1"
    When I edit the tournament with name "FirstTournament" and set the start date "2019-10-17" at "15:46:00"
    Then The response code is 200
    And The tournament with name "FirstTournament" has the start date "2019-10-17" at "15:46:00"

  Scenario: Edit tournament to change the finishing time
    Given I login as "demoTM" with password "password"
    And I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL" and bestof "1"
    When I edit the tournament with name "FirstTournament" and set the finish date "2019-12-25" at "17:28:45"
    Then The response code is 200
    And The tournament with name "FirstTournament" has the finish date "2019-12-25" at "17:28:45"
Feature: Tournament
  In order to use the app
  As a tournament master
  I want to delete a tournament

  Scenario: Tournament Master deletes tournament
    Given I login as "demoTM" with password "password"
    And  I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL"
    When I delete a tournament with name "FirstTournament", level "AMATEUR" and game "LoL"
    Then The response code is 204
    And It does not exist a tournament with name "FirstTournament"

  Scenario: Player tries to delete a tournament
    Given I login as "demoTM" with password "password"
    And  I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL"
    Then I login as "demoP" with password "password"
    When I delete a tournament with name "FirstTournament", level "AMATEUR" and game "LoL"
    Then The response code is 403

  Scenario: Remove a tournament that does not exist
    Given I login as "demoTM" with password "password"
    When I delete a tournament with name "FirstTournament", level "AMATEUR" and game "LoL"
    Then The response code is 404

  #Scenario: Tournament Master tries to delete a started tournament
    #Given I login as "demoTM" with password "password"
    #And  I create a new tournament with name "FirstTournament", level "AMATEUR" and game "LoL"
    #And I edit the tournament with name "FirstTournament" and set the start date "2019-10-17" at "15:46:00"
    #When I delete a tournament with name "FirstTournament" and a today's date lower than "2019-10-17"
    #Then The response code is 403
    #And It has not been deleted a tournament with name "FirstTournament"



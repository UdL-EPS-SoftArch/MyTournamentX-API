package cat.udl.eps.softarch.mytournamentx.steps;

import cat.udl.eps.softarch.mytournamentx.domain.*;
import cat.udl.eps.softarch.mytournamentx.repository.*;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.it.Ma;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.params.shadow.com.univocity.parsers.tsv.TsvRoutines;
import org.junit.runner.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import cat.udl.eps.softarch.mytournamentx.service.TournamentService;


import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class UpdateResultStepDefs {

 /*   private Player leaderPlayer1 = new Player();
    private Player leaderPlayer2 = new Player();

    public Team team1 = new Team();
    public Team team2 = new Team();
    public List<Team> rivals = new ArrayList<>();



    public Tournament tournament = new Tournament();
    public Round round = new Round();
    public Match match1 =  new Match();
    //public Match match2 = new Match();
    public MatchResult matchResult1 = new MatchResult();
    public MatchResult matchResult2 = new MatchResult();
    //public MatchResult matchResult3 = new MatchResult();
    //public MatchResult matchResult4 = new MatchResult();

  */



    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private StepDefs stepDefs;

/*
    @Given("^There are some matchresults$")
    public void thereAreSomeMatchresults() throws Exception {
        team1.setName("team1");
        team1.setMaxPlayers(1);
        team1.setGame("el lol");
        team2.setName("team2");
        team2.setMaxPlayers(1);
        team2.setGame("el lol");

        teamRepository.save(team1);
        teamRepository.save(team2);

        rivals.add(team1);
        rivals.add(team2);

        tournament.setName("Working");
        tournament.setGame("el lol");
        tournament.setBestOf(1);
        tournament.setLevel(Tournament.Level.AMATEUR);
        tournament.setParticipants(rivals);
        tournamentService.createTournament(tournament);

        round.setNextRound(null);
        round.setTournament(tournament);
        round.setNumTeams(2);
        round.setBestOf(1);
        round.setRivals(rivals);
        roundRepository.save(round);

        match1.setRound(round);
        matchRepository.save(match1);
        matchResult1.setMatch(match1);
        matchResult1.setWinner(team1);
        //matchResult2.setMatch(match1);
        //matchResult2.setSender(team2);
        matchResult1.setSender(team1);

        //match2.setRound(round);
        //matchRepository.save(match2);
        //matchResult3.setMatch(match2);
        // matchResult3.setWinner(team1);
        //matchResult4.setMatch(match2);
        //matchResult4.setSender(team2);
        //matchResult3.setSender(team1);

        matchResultRepository.save(matchResult1);
        //matchResultRepository.save(matchResult2);
        //matchResultRepository.save(matchResult3);
        //matchResultRepository.save(matchResult4);

    }

 */
/*
    @Transactional
    @Given("^One match result has already been created$")
    public void oneMatchResultHasAlreadyBeenCreated() throws Throwable {

        Assert.assertEquals(matchResultRepository.findByMatchAndSender(match1,team1),matchResult1);
        //Assert.assertEquals(matchResultRepository.findByMatchAndSender(match1,team2),matchResult2);
        //Assert.assertEquals(matchResultRepository.findByMatchAndSender(match2,team1),matchResult3);
        //Assert.assertEquals(matchResultRepository.findByMatchAndSender(match2,team2),matchResult4);

    }

    @When("^I created my MatchResult as a TeamLeader and i'm the last team to submit it$")
    public void iCreatedMyMatchResultAsATeamLeaderAndIMTheLastTeamToSubmitIt() throws Throwable {
        matchResult2.setMatch(match1);
        matchResult2.setSender(team2);
        matchResult2.setWinner(team1);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matchResults")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                stepDefs.mapper.writeValueAsString(matchResult2))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^The winner of the Match is set$")
    public void theWinnerOfTheMatchIsSet() {
        Assert.assertEquals(matchResultRepository.findByMatchAndSender(match1,team2).getMatch().getWinner(), team1);
    }

 */


    Tournament tournament = new Tournament();
    Round round = new Round();
    Match match = new Match();
    MatchResult matchResult = new MatchResult();


    Team team = new Team();
    Player player = new Player();

    Team team2 = new Team();

    @Given("^There is a tournament with name \"([^\"]*)\", level \"([^\"]*)\", game \"([^\"]*)\" and bestof \"([^\"]*)\" UpdateResult$")
    public void thereIsATournamentWithNameLevelGameAndBestofUpdateResult(String name, Tournament.Level level, String game, int bestOf) throws Throwable {
        tournament.setName(name);
        tournament.setLevel(level);
        tournament.setGame(game);
        tournament.setBestOf(bestOf);
        tournamentRepository.save(tournament);
    }

    @And("^There is a created team with name \"([^\"]*)\", game \"([^\"]*)\", level \"([^\"]*)\", maxPlayers (\\d+), and the team leader is \"([^\"]*)\" UpdateResult$")
    public void thereIsACreatedTeamWithNameGameLevelMaxPlayersAndTheTeamLeaderIsUpdateResult(String name, String game, String level, int maxPlayers, String teamLeader) throws Throwable {
        team.setName("TeamA");
        team.setGame("PACYBITS");
        team.setLevel("AMATEUR");
        Set<Player> player_list = new HashSet<>();
        player_list.add(player);
        team.setPlayers(player_list);
        team.setMaxPlayers(1);
        team.setLeader(player);

        team2.setName("TeamB");
        team2.setGame("PACYBITS");
        team2.setLevel("AMATEUR");
        Set<Player> player_list2 = new HashSet<>();
        player_list.add(player);
        team2.setPlayers(player_list2);
        team2.setMaxPlayers(1);
        team2.setLeader(player);

        teamRepository.save(team);
        teamRepository.save(team2);

        //teamRepository.save(team);

    }

    @And("^There is a round with Round \"([^\"]*)\", bestof \"([^\"]*)\", numTeams \"([^\"]*)\", List<Team> \"([^\"]*)\", tournament \"([^\"]*)\"$")
    public void thereIsARoundWithRoundBestofNumTeamsListTeamTournament(String nextRound, int bestOf, int numTeams, List<Team> rivals, Tournament tournament) throws Throwable {

        round.setBestOf(bestOf);
        round.setNextRound(null);
        round.setNumTeams(numTeams);
        round.setRivals(rivals);
        round.setTournament(tournament);
        roundRepository.save(round);
    }

    @And("^There is a match UpdateResult$")
    public void thereIsAMatchUpdateResult() {
        match.setRound(round);
        matchRepository.save(match);

    }

    @And("^There is a matchResult with Match \"([^\"]*)\", Team \"([^\"]*)\", Team \"([^\"]*)\" UpdateResult$")
    public void thereIsAMatchResultWithMatchTeamTeamUpdateResult(Match match1, Team sender2, Team winner3) throws Throwable {

       /* matchResult.setMatch(match);
        matchResult.setSender(team);
        matchResult.setWinner(team);
        matchResultRepository.save(matchResult);

        */
    }

    @And("^There is a registered player with username \"([^\"]*)\" and password \"([^\"]*)\" UpdateResult$")
    public void thereIsARegisteredPlayerWithUsernameAndPasswordUpdateResult(String username, String password) throws Throwable {
        if (!playerRepository.existsById(username)) {
            player.setUsername(username);
            player.setPassword(password);
            player.encodePassword();
            playerRepository.save(player);
        }
    }
}
